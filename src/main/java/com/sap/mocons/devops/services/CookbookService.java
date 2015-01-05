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

	public List<Cookbook> getCIQualifiedCookbooks() {
		List<Cookbook> qualifiedCookbooks = new ArrayList<Cookbook>();

		List<Cookbook> cookbooks = cookbookDao.getCookbooks();
		for (Cookbook cookbook : cookbooks) {
			String url = cookbook.getExternal_url();
			if (url != null && !url.isEmpty()) {
				LOGGER.info(String.format("process repository '%s': %s", cookbook.getName(), url));
				if (isQualified(url)) {
					qualifiedCookbooks.add(cookbook);
				}
			}
		}

		LOGGER.info(String.format("%d / %d ci-qualified cookbooks were found", qualifiedCookbooks.size(),
				cookbooks.size()));

		return qualifiedCookbooks;
	}

	private boolean isQualified(String url) {
		for (String file : files) {
			String filePath = url.concat("/blob/master/").concat(file);
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(filePath).openConnection();
				con.setRequestMethod("HEAD");
				con.setConnectTimeout(CONNECTION_TIMEOUT);
				if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
					LOGGER.warn(String.format("[UNQUALIFIED] - unqualified repository: '%s'", url));

					return false;
				}
			} catch (IOException e) {
				LOGGER.error(String.format("failed to qualify url: '%s'", url), e);
			}
		}

		LOGGER.info(String.format("[QUALIFIED] - qualified repository: '%s'", url));

		return true;
	}
}
