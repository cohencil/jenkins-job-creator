package com.sap.mocons.devops.services.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.sap.mocons.devops.services.JenkinsService;

public class JenkinsServiceOffbytwoImpl implements JenkinsService {

	private static final String SERVER_URL = "http://mo-26ab3d335.mo.sap.corp:8080/jenkins/";
	private static final String USERNAME = "asa1_mocons1";
	private static final String TOKEN = "1f3a52012d2c86baadf1af8658ae02e5";

	private JenkinsServer jenkinsServer;
	private Document doc;

	public JenkinsServiceOffbytwoImpl() {
		try {
			// TODO externalize server_url
			jenkinsServer = new JenkinsServer(new URI(SERVER_URL), USERNAME, TOKEN);
			doc = new SAXReader().read(this.getClass().getResource("config.xml"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createJob(String jobName, String jobXml) {
		try {
			jenkinsServer.createJob(jobName, jobXml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Job> getJobs() {
		Map<String, Job> jobs = null;

		try {
			jobs = jenkinsServer.getJobs();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jobs;
	}

	@Override
	public JobWithDetails getJob(String name) {
		JobWithDetails job = null;

		try {
			job = jenkinsServer.getJob(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return job;
	}

	@Override
	public String getConfigXML() {
		return doc.asXML();
	}

	@Override
	public void setGitRepositoryUrl(String gitUrl) {
		Node node = doc.selectSingleNode("//hudson.plugins.git.UserRemoteConfig/url");
		node.setText(gitUrl);
	}
}
