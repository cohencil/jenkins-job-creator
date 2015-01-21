package com.sap.mocons.devops.dao;

import java.util.Map;

import com.offbytwo.jenkins.model.Job;

public interface JenkinsDao {

	Map<String, Job> getJobs();

	void createJob(String jobName, String jobXml);
}
