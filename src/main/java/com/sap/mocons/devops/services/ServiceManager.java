package com.sap.mocons.devops.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.offbytwo.jenkins.model.Job;
import com.sap.mocons.devops.domain.Cookbook;

public class ServiceManager {

	private static Logger LOGGER = Logger.getLogger(ServiceManager.class);

	public static void main(String[] args) {
		try {
			LOGGER.info("Jenkins job provision - Start");

			String shelfUrl = System.getProperty("shelfUrl", "http://shelf.mo.sap.corp:8080/api/v1/cookbooks");
			String files = System.getProperty("files", ".kitchen.yml,Cheffile");

			String jenkinsUrl = System.getProperty("jenkinsUrl", "http://cb-kitchen.mo.sap.corp:8080/jenkins/");
			String username = System.getProperty("username", "asa1_mocons1");
			String token = System.getProperty("token");

			CookbookService cookbookService = new CookbookService(shelfUrl, files.split(","));
			JenkinsService jenkinsService = new JenkinsService(jenkinsUrl, username, token);

			// get all existing jobs
			Map<String, Job> jobs = jenkinsService.getJobs();

			// get all cookbooks
			List<Cookbook> cookbooks = cookbookService.getCookbooks();

			// remove existing cookbooks in jenkins
			List<Cookbook> existCookbooks = new ArrayList<>();
			for (Cookbook cookbook : cookbooks) {
				if (jobs.containsKey(cookbook.getName())) {
					existCookbooks.add(cookbook);
				}
			}
			cookbooks.removeAll(existCookbooks);
			LOGGER.info(String.format("[%d] cookbooks were already added to jenkins", existCookbooks.size()));

			// get qualified cookbooks
			cookbooks = cookbookService.getQualifiedCookbooks(cookbooks);

			// add job for qualified cookbook
			if (Boolean.getBoolean("createJob")) {
				if (cookbooks.size() == 0) {
					LOGGER.info("no new cookbooks were found");
				} else {
					LOGGER.info("creating jenkins jobs...");
					jenkinsService.createJobs(cookbooks);
				}

			} else {
				LOGGER.info("skip jenkins jobs creation, use '-DcreateJob' to enable");
			}

			LOGGER.info("Jenkins job provision - Complete");

		} catch (Exception e) {
			LOGGER.error("Jenkins job provision - Fail", e);
			throw e;
		}
	}
}
