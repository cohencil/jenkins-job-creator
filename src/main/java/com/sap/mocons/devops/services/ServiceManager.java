package com.sap.mocons.devops.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.sap.mocons.devops.domain.Cookbook;

public class ServiceManager {

	private static Logger LOGGER = Logger.getLogger(ServiceManager.class);

	public static void main(String[] args) {
		try {
			LOGGER.info("Jenkins job provision - Start");

			String shelfUrl = System.getProperty("shelfUrl", "http://shelf.mo.sap.corp:8080/api/v1/cookbooks");
			String files = System.getProperty("files", ".kitchen.yml,Cheffile");

			String jenkinsUrl = System.getProperty("jenkinsUrl", "http://mo-26ab3d335.mo.sap.corp:8080/jenkins/");

			CookbookService cookbookService = new CookbookService(shelfUrl, files.split(","));
			JenkinsService jenkinsService = new JenkinsService(jenkinsUrl);

			List<Cookbook> cookbooks = cookbookService.getCIQualifiedCookbooks();
			if (Boolean.getBoolean("createJob")) {
				LOGGER.info("creating jenkins jobs");
				jenkinsService.createJobs(cookbooks);
			} else {
				LOGGER.info("skip jenkins jobs creation, use '-DcreateJob' to enable");
			}

			LOGGER.info("Jenkins job provision - Complete");

		} catch (Exception e) {
			LOGGER.error("Jenkins job provision - Fail", e);
		}
	}
}
