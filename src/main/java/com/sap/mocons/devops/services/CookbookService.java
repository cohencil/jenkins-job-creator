package com.sap.mocons.devops.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.sap.mocons.devops.dao.CookbookDao;
import com.sap.mocons.devops.dao.CookbookDaoImpl;
import com.sap.mocons.devops.domain.Cookbook;

public class CookbookService {

	private static Logger LOGGER = Logger.getLogger(CookbookService.class);
	private static final int CONNECTION_TIMEOUT = 5000;

	private CookbookDao cookbookDao;
	private String[] files;

	public CookbookService(String url, String... files) {
		LOGGER.debug(String.format("initializing CookbookService with url=%s, files=%s", url, Arrays.toString(files)));

		cookbookDao = new CookbookDaoImpl(url);
		this.files = files;
	}

	public List<Cookbook> getCookbooks() {
		List<Cookbook> cookbooks = cookbookDao.getCookbooks();
		LOGGER.info(String.format("[%d] cookbooks were loaded", cookbooks.size()));

		return cookbooks;
	}

	public List<Cookbook> getQualifiedCookbooks(List<Cookbook> cookbooks) {

		List<Cookbook> qualifiedCookbooks = new ArrayList<>();
		List<Cookbook> unqualifiedCookbooks = new ArrayList<>();
		List<Cookbook> failedCookbooks = new ArrayList<>();

		for (Cookbook cookbook : cookbooks) {
			String url = cookbook.getExternal_url();
			String name = cookbook.getName();

			LOGGER.info(String.format("process repository: '%s', %s", name, url));

			if (url != null && !url.isEmpty()) {
				if (isQualified(url)) {
					qualifiedCookbooks.add(cookbook);
				} else {
					unqualifiedCookbooks.add(cookbook);
				}
			} else {
				failedCookbooks.add(cookbook);
			}
		}

		logCookbooks(qualifiedCookbooks, "qualified");
		logCookbooks(unqualifiedCookbooks, "unqualified");
		logCookbooks(failedCookbooks, "failed");

		LOGGER.info(String.format("summary: [%d / %d / %d] (qualified / failed / available)",
				qualifiedCookbooks.size(), failedCookbooks.size(), cookbooks.size()));

		return qualifiedCookbooks;
	}

	private void logCookbooks(List<Cookbook> cookbooks, String status) {
		LOGGER.info(String.format("[%d] %s cookbooks were found", cookbooks.size(), status));
		for (Cookbook cookbook : cookbooks) {
			LOGGER.info(String.format("%s cookbook: '%s', %s", status, cookbook.getName(), cookbook.getExternal_url()));
		}
	}

	private boolean isQualified(String url) {
		for (String file : files) {
			String filePath = url.concat("/blob/master/").concat(file);
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(filePath).openConnection();
				con.setRequestMethod("HEAD");
				con.setConnectTimeout(CONNECTION_TIMEOUT);
				if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return false;
				}
			} catch (IOException e) {
				LOGGER.error(String.format("failed to qualify url: '%s'", url), e);
			}
		}

		return true;
	}
}
