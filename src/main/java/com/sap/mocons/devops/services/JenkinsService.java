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

	public JenkinsService(String jenkinsUrl, String username, String token) {
		LOGGER.debug(String.format("initializing JenkinsService with url=%s, username=%s, token=%s", jenkinsUrl,
				username, token));

		jenkinsDao = new JenkinsDaoImpl(jenkinsUrl, username, token);

		try {
			doc = new SAXReader().read(this.getClass().getResource("config.xml"));

		} catch (DocumentException e) {
			throw new RuntimeException("failed to load config.xml", e);
		}
	}

	public int createJobs(List<Cookbook> cookbooks) {
		int createdJobs = 0;

		for (Cookbook cookbook : cookbooks) {
			String cookbookName = cookbook.getName();

			LOGGER.info(String.format("adding new jenkins job for cookbook: '%s'", cookbookName));

			setGitRepositoryUrl(cookbook.getGitRepositoryUrl());
			jenkinsDao.createJob(cookbookName, doc.asXML());
			createdJobs++;

			LOGGER.info(String.format("new job was added to jenkins: '%s'", cookbookName));
		}
		LOGGER.info(String.format("[%d] new jobs were added to jenkins", createdJobs));

		return createdJobs;
	}

	private void setGitRepositoryUrl(String gitUrl) {
		Node node = doc.selectSingleNode("//hudson.plugins.git.UserRemoteConfig/url");
		node.setText(gitUrl);
	}

	public Map<String, Job> getJobs() {
		Map<String, Job> jobs = jenkinsDao.getJobs();
		LOGGER.info(String.format("[%d] jenkins jobs were loaded", jobs.size()));

		return jobs;
	}
}
