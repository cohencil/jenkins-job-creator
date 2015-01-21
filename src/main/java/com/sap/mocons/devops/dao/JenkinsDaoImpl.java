package com.sap.mocons.devops.dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

public class JenkinsDaoImpl implements JenkinsDao {

	// private static Logger LOGGER = Logger.getLogger(JenkinsDaoImpl.class);

	protected final JenkinsServer jenkinsServer;

	public JenkinsDaoImpl(String url, String username, String token) {
		try {
			jenkinsServer = new JenkinsServer(new URI(url), username, token);

		} catch (URISyntaxException e) {
			throw new RuntimeException("failed to initialize jenkins server", e);
		}
	}

	public Map<String, Job> getJobs() {
		Map<String, Job> jobs = null;

		try {
			jobs = jenkinsServer.getJobs();
		} catch (IOException e) {
			throw new RuntimeException("failed to get jobs", e);
		}

		return jobs;
	}

	public void createJob(String jobName, String jobXml) {
		try {
			jenkinsServer.createJob(jobName, jobXml);
		} catch (IOException e) {
			throw new RuntimeException(String.format("failed to create job '%s'", jobName), e);
		}
	}
}
