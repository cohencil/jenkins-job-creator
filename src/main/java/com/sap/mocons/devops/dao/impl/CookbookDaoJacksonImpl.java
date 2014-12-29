package com.sap.mocons.devops.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sap.mocons.devops.dao.CookbookDao;
import com.sap.mocons.devops.domain.Cookbook;

public class CookbookDaoJacksonImpl implements CookbookDao {

	private ObjectMapper objectMapper;
	private URL url;

	public CookbookDaoJacksonImpl(String url) {
		try {
			this.url = new URL(url);
			objectMapper = new ObjectMapper();

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Cookbook> getCookbooks() {
		List<Cookbook> cookbooks = null;

		try {
			cookbooks = objectMapper.readValue(url, new TypeReference<List<Cookbook>>() {});

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return cookbooks;
	}

}
