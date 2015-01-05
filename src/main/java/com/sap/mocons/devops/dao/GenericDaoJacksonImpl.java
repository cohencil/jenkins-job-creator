package com.sap.mocons.devops.dao;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;

public class GenericDaoJacksonImpl<T> implements GenericDao<T> {

	private Class<T> persistentClass;
	private ObjectMapper objectMapper;

	protected URL url;

	@SuppressWarnings("unchecked")
	public GenericDaoJacksonImpl(String url) {

		ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
		this.persistentClass = (Class<T>) pt.getActualTypeArguments()[0];

		try {
			this.url = new URL(url);
			objectMapper = new ObjectMapper();

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<T> findAll() {
		List<T> list = null;

		try {
			CollectionType ct = objectMapper.getTypeFactory().constructCollectionType(List.class, persistentClass);
			list = objectMapper.readValue(url, ct);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return list;
	}
}
