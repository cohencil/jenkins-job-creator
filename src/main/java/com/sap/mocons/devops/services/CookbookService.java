package com.sap.mocons.devops.services;

import java.util.List;

import com.sap.mocons.devops.domain.Cookbook;

public interface CookbookService {

	List<Cookbook> getCIQualifiedCookbooks();

}
