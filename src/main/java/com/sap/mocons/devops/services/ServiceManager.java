package com.sap.mocons.devops.services;

import java.util.List;
import java.util.Map;

import com.offbytwo.jenkins.model.Job;
import com.sap.mocons.devops.domain.Cookbook;
import com.sap.mocons.devops.services.impl.CookbookServiceImpl;
import com.sap.mocons.devops.services.impl.JenkinsServiceOffbytwoImpl;

public class ServiceManager {

	public static void main(String[] args) {
		// TODO externalize shelf url
		CookbookService cookbookService = new CookbookServiceImpl("http://shelf.mo.sap.corp:8080/api/v1/cookbooks");
		List<Cookbook> cookbooks = cookbookService.getCIQualifiedCookbooks();

		JenkinsService jenkinsService = new JenkinsServiceOffbytwoImpl();
		Map<String, Job> jobs = jenkinsService.getJobs();

		int newJobsCounter = 0;
		for (Cookbook cookbook : cookbooks) {
			String jobName = cookbook.getName();
			if (!jobs.keySet().contains(jobName)) {
				// add non-existing jobs
				System.out.println(String.format("adding new jenkins job: %s", cookbook));
				jenkinsService.setGitRepositoryUrl(cookbook.getGitRepositoryUrl());
				jenkinsService.createJob(jobName, jenkinsService.getConfigXML());
				newJobsCounter++;
				System.out.println(String.format("new jenkins job added: %s", jobName));
			}
		}
		System.out.println(String.format("%d new jobs were added to jenkins", newJobsCounter));
	}
}
