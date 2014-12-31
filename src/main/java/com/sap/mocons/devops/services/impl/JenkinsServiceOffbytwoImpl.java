package com.sap.mocons.devops.services.impl;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.sap.mocons.devops.domain.Cookbook;
import com.sap.mocons.devops.services.JenkinsService;

public class JenkinsServiceOffbytwoImpl implements JenkinsService {

	private static Logger LOGGER = Logger.getLogger(JenkinsServiceOffbytwoImpl.class);

	private JenkinsServer jenkinsServer;
	private Document doc;

	public JenkinsServiceOffbytwoImpl(String url, String username, String token) {
		LOGGER.debug(String.format("initialize JenkinsServiceOffbytwoImpl with url=%s, username=%s, token=%s", url,
				username, token));

		try {
			jenkinsServer = new JenkinsServer(new URI(url), username, token);
			doc = new SAXReader().read(this.getClass().getResource("config.xml"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createJob(String jobName, String jobXml) {
		try {
			jenkinsServer.createJob(jobName, jobXml);
		} catch (IOException e) {
			LOGGER.error(String.format("failed to create job '%s'", jobName), e);
		}
	}

	@Override
	public void createJobs(List<Cookbook> cookbooks) {
		int newJobsCounter = 0;
		Map<String, Job> jobs = getJobs();

		for (Cookbook cookbook : cookbooks) {
			String jobName = cookbook.getName();
			if (!jobs.keySet().contains(jobName)) {
				// add non-existing jobs
				LOGGER.info(String.format("adding new jenkins job: %s", cookbook));

				setGitRepositoryUrl(cookbook.getGitRepositoryUrl());
				createJob(jobName, getConfigXML());
				newJobsCounter++;

				LOGGER.info(String.format("new job was added to jenkins: %s", jobName));
			}
		}

		LOGGER.info(String.format("%d new jobs were added to jenkins", newJobsCounter));
	}

	@Override
	public Map<String, Job> getJobs() {
		Map<String, Job> jobs = null;

		try {
			jobs = jenkinsServer.getJobs();
		} catch (IOException e) {
			LOGGER.error("failed to get jobs", e);
		}

		return jobs;
	}

	@Override
	public String getConfigXML() {
		return doc.asXML();
	}

	private void setGitRepositoryUrl(String gitUrl) {
		Node node = doc.selectSingleNode("//hudson.plugins.git.UserRemoteConfig/url");
		node.setText(gitUrl);
	}
}
