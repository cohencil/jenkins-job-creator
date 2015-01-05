package com.sap.mocons.devops.dao;

import java.util.List;

public interface GenericDao<T> {

	List<T> findAll();
}
