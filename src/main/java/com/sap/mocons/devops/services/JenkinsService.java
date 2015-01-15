package com.sap.mocons.devops.services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.offbytwo.jenkins.model.Job;
import com.sap.mocons.devops.dao.JenkinsDao;
import com.sap.mocons.devops.dao.JenkinsDaoImpl;
import com.sap.mocons.devops.domain.Cookbook;

public class JenkinsService {

	private static Logger LOGGER = Logger.getLogger(JenkinsService.class);
	private final Document doc;
	private JenkinsDao jenkinsDao;

	public JenkinsService() {
		try {
			doc = new SAXReader().read(this.getClass().getResource("config.xml"));

		} catch (DocumentException e) {
			throw new RuntimeException("failed to load config.xml", e);
		}
	}

	public JenkinsService(String jenkinsUrl) {
		this();

		LOGGER.debug(String.format("initializing JenkinsService with url=%s", jenkinsUrl));
		jenkinsDao = new JenkinsDaoImpl(jenkinsUrl);
	}

	public JenkinsService(String jenkinsUrl, String username, String token) {
		this();

		LOGGER.debug(String.format("initializing JenkinsService with url=%s, username=%s, token=%s", jenkinsUrl,
				username, token));
		jenkinsDao = new JenkinsDaoImpl(jenkinsUrl, username, token);
	}

	public int createJobs(List<Cookbook> cookbooks) {
		int createdJobs = 0;

		Map<String, Job> jobs = jenkinsDao.getJobs();

		for (Cookbook cookbook : cookbooks) {
			String jobName = cookbook.getName();
			if (!jobs.keySet().contains(jobName)) {
				// add non-existing jobs

				LOGGER.info(String.format("adding new jenkins job: %s", cookbook));

				setGitRepositoryUrl(cookbook.getGitRepositoryUrl());
				jenkinsDao.createJob(jobName, doc.asXML());
				createdJobs++;

				LOGGER.info(String.format("new job was added to jenkins: %s", jobName));
			}
		}
		LOGGER.info(String.format("%d new jobs were added to jenkins", createdJobs));

		return createdJobs;
	}

	private void setGitRepositoryUrl(String gitUrl) {
		Node node = doc.selectSingleNode("//hudson.plugins.git.UserRemoteConfig/url");
		node.setText(gitUrl);
	}
}
