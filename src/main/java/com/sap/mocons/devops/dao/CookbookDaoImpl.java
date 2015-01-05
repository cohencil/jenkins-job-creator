package com.sap.mocons.devops.dao;

import java.util.List;

import com.sap.mocons.devops.domain.Cookbook;

public class CookbookDaoImpl extends GenericDaoJacksonImpl<Cookbook> implements CookbookDao {

	// private static Logger LOGGER = Logger.getLogger(CookbookDaoImpl.class);

	public CookbookDaoImpl(String url) {
		super(url);
	}

	@Override
	public List<Cookbook> getCookbooks() {
		return findAll();
	}
}
