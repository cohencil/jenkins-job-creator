package com.sap.mocons.devops.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.sap.mocons.devops.domain.Cookbook;
import com.sap.mocons.devops.services.impl.CookbookServiceImpl;
import com.sap.mocons.devops.services.impl.JenkinsServiceOffbytwoImpl;

public class ServiceManager {

	private static Logger LOGGER = Logger.getLogger(ServiceManager.class);

	public static void main(String[] args) {
		try {
			LOGGER.info("Jenkins job provision - Start");

			// TODO externalize shelf url
			CookbookService cookbookService = new CookbookServiceImpl("http://shelf.mo.sap.corp:8080/api/v1/cookbooks");
			JenkinsService jenkinsService = new JenkinsServiceOffbytwoImpl();

			List<Cookbook> cookbooks = cookbookService.getCIQualifiedCookbooks();
			jenkinsService.createJobs(cookbooks);

			LOGGER.info("Jenkins job provision - Complete");

		} catch (Exception e) {
			LOGGER.error("Jenkins job provision - Fail", e);
		}
	}
}
