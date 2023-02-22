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

package com.liferay.portal.search.learn.rest.client.dto.v1_0;

import com.liferay.portal.search.learn.rest.client.function.UnsafeSupplier;
import com.liferay.portal.search.learn.rest.client.serdes.v1_0.SearchResponseSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Tibor Lipusz
 * @generated
 */
@Generated("")
public class SearchResponse implements Cloneable, Serializable {

	public static SearchResponse toDTO(String json) {
		return SearchResponseSerDes.toDTO(json);
	}

	public Map[] getErrors() {
		return errors;
	}

	public void setErrors(Map[] errors) {
		this.errors = errors;
	}

	public void setErrors(
		UnsafeSupplier<Map[], Exception> errorsUnsafeSupplier) {

		try {
			errors = errorsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map[] errors;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setPage(UnsafeSupplier<Integer, Exception> pageUnsafeSupplier) {
		try {
			page = pageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer page;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageSize(
		UnsafeSupplier<Integer, Exception> pageSizeUnsafeSupplier) {

		try {
			pageSize = pageSizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer pageSize;

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public void setRequest(
		UnsafeSupplier<Object, Exception> requestUnsafeSupplier) {

		try {
			request = requestUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object request;

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

	public void setRequestString(
		UnsafeSupplier<String, Exception> requestStringUnsafeSupplier) {

		try {
			requestString = requestStringUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String requestString;

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public void setResponse(
		UnsafeSupplier<Object, Exception> responseUnsafeSupplier) {

		try {
			response = responseUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object response;

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public void setResponseString(
		UnsafeSupplier<String, Exception> responseStringUnsafeSupplier) {

		try {
			responseString = responseStringUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String responseString;

	public SearchHits getSearchHits() {
		return searchHits;
	}

	public void setSearchHits(SearchHits searchHits) {
		this.searchHits = searchHits;
	}

	public void setSearchHits(
		UnsafeSupplier<SearchHits, Exception> searchHitsUnsafeSupplier) {

		try {
			searchHits = searchHitsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SearchHits searchHits;

	public SearchRequest getSearchRequest() {
		return searchRequest;
	}

	public void setSearchRequest(SearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}

	public void setSearchRequest(
		UnsafeSupplier<SearchRequest, Exception> searchRequestUnsafeSupplier) {

		try {
			searchRequest = searchRequestUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SearchRequest searchRequest;

	@Override
	public SearchResponse clone() throws CloneNotSupportedException {
		return (SearchResponse)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchResponse)) {
			return false;
		}

		SearchResponse searchResponse = (SearchResponse)object;

		return Objects.equals(toString(), searchResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SearchResponseSerDes.toJSON(this);
	}

}