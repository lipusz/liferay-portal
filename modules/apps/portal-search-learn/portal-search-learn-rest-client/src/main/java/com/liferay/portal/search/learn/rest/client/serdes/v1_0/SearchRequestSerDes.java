/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.learn.rest.client.serdes.v1_0;

import com.liferay.portal.search.learn.rest.client.dto.v1_0.SearchRequest;
import com.liferay.portal.search.learn.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Tibor Lipusz
 * @generated
 */
@Generated("")
public class SearchRequestSerDes {

	public static SearchRequest toDTO(String json) {
		SearchRequestJSONParser searchRequestJSONParser =
			new SearchRequestJSONParser();

		return searchRequestJSONParser.parseToDTO(json);
	}

	public static SearchRequest[] toDTOs(String json) {
		SearchRequestJSONParser searchRequestJSONParser =
			new SearchRequestJSONParser();

		return searchRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SearchRequest searchRequest) {
		if (searchRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (searchRequest.getQueryString() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queryString\": ");

			sb.append("\"");

			sb.append(_escape(searchRequest.getQueryString()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SearchRequestJSONParser searchRequestJSONParser =
			new SearchRequestJSONParser();

		return searchRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SearchRequest searchRequest) {
		if (searchRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (searchRequest.getQueryString() == null) {
			map.put("queryString", null);
		}
		else {
			map.put(
				"queryString", String.valueOf(searchRequest.getQueryString()));
		}

		return map;
	}

	public static class SearchRequestJSONParser
		extends BaseJSONParser<SearchRequest> {

		@Override
		protected SearchRequest createDTO() {
			return new SearchRequest();
		}

		@Override
		protected SearchRequest[] createDTOArray(int size) {
			return new SearchRequest[size];
		}

		@Override
		protected void setField(
			SearchRequest searchRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "queryString")) {
				if (jsonParserFieldValue != null) {
					searchRequest.setQueryString((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}