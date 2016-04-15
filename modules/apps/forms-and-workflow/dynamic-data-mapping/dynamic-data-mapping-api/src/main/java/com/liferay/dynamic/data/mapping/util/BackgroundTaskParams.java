package com.liferay.dynamic.data.mapping.util;

import java.io.Serializable;

public class BackgroundTaskParams implements Serializable {

	private String field1;

	public BackgroundTaskParams() {
	}

	public BackgroundTaskParams(String field1) {
		super();

		this.field1 = field1;
	}

	public String getField1() {

		return field1;
	}

}