package com.sap.mocons.devops.services;

import java.util.List;

import com.sap.mocons.devops.domain.Cookbook;
import com.sap.mocons.devops.services.impl.CookbookServiceImpl;
import com.sap.mocons.devops.services.impl.JenkinsServiceOffbytwoImpl;

public class ServiceManager {

	public static void main(String[] args) {
		// TODO externalize shelf url
		CookbookService cookbookService = new CookbookServiceImpl("http://shelf.mo.sap.corp:8080/api/v1/cookbooks");
		JenkinsService jenkinsService = new JenkinsServiceOffbytwoImpl();

		List<Cookbook> cookbooks = cookbookService.getCIQualifiedCookbooks();
		jenkinsService.createJobs(cookbooks);

	}
}
