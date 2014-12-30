package com.sap.mocons.devops.services;

import java.util.List;
import java.util.Map;

import com.offbytwo.jenkins.model.Job;
import com.sap.mocons.devops.domain.Cookbook;

public interface JenkinsService {

	void createJob(String jobName, String jobXml);

	void createJobs(List<Cookbook> cookbooks);

	Map<String, Job> getJobs();

	String getConfigXML();

}
