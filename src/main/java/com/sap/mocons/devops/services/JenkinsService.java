package com.sap.mocons.devops.services;

import java.util.Map;

import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

public interface JenkinsService {

	void createJob(String jobName, String jobXml);

	Map<String, Job> getJobs();

	JobWithDetails getJob(String name);

	public abstract String getConfigXML();

	public abstract void setGitRepositoryUrl(String gitUrl);
}
