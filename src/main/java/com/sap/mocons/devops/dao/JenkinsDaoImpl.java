package com.sap.mocons.devops.dao;

import java.util.Map;

import com.offbytwo.jenkins.model.Job;

public class JenkinsDaoImpl extends GenericDaoJenkinsImpl implements JenkinsDao {

	// private static Logger LOGGER = Logger.getLogger(JenkinsDaoImpl.class);

	public JenkinsDaoImpl(String url, String username, String token) {
		super(url, username, token);
	}

	@Override
	public void createJob(String jobName, String jobXml) {
		super.createJob(jobName, jobXml);
	}

	@Override
	public Map<String, Job> getJobs() {
		return super.getJobs();
	}
}
