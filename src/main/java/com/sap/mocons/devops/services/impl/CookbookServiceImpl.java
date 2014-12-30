package com.sap.mocons.devops.services.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sap.mocons.devops.dao.CookbookDao;
import com.sap.mocons.devops.dao.impl.CookbookDaoJacksonImpl;
import com.sap.mocons.devops.domain.Cookbook;
import com.sap.mocons.devops.services.CookbookService;

public class CookbookServiceImpl implements CookbookService {

	private static final int CONNECTION_TIMEOUT = 5000;
	private CookbookDao cookbookDao;

	public CookbookServiceImpl(String url) {
		cookbookDao = new CookbookDaoJacksonImpl(url);
	}

	@Override
	public List<Cookbook> getCIQualifiedCookbooks() {
		List<Cookbook> qualifiedCookbooks = new ArrayList<Cookbook>();

		List<Cookbook> cookbooks = cookbookDao.getCookbooks();
		for (Cookbook cookbook : cookbooks) {
			String url = cookbook.getExternal_url();
			if (url != null && !url.isEmpty()) {
				System.out.println(String.format("process repository '%s': %s", cookbook.getName(), url));
				if (isQualified(url)) {
					qualifiedCookbooks.add(cookbook);
				}
			}
		}

		System.out.println(String.format("%d / %d ci-qualified cookbooks were found", qualifiedCookbooks.size(),
				cookbooks.size()));
		return qualifiedCookbooks;
	}

	private boolean isQualified(String url) {
		// TODO externalize files
		List<String> files = Arrays.asList(".kitchen.yml", "Cheffile");

		for (String file : files) {
			String filePath = url.concat("/blob/master/").concat(file);
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(filePath).openConnection();
				con.setRequestMethod("HEAD");
				con.setConnectTimeout(CONNECTION_TIMEOUT);
				if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
					System.err.println(String.format("[UNQUALIFIED] - unqualified repository: '%s'", url));

					return false;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			}
		}

		System.out.println(String.format("[QUALIFIED] - qualified repository: '%s'", url));

		return true;
	}
}
