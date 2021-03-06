package com.sap.mocons.devops.dao;

import java.util.List;

import com.sap.mocons.devops.domain.Cookbook;

public interface CookbookDao extends GenericDao<Cookbook> {

	List<Cookbook> getCookbooks();
}
