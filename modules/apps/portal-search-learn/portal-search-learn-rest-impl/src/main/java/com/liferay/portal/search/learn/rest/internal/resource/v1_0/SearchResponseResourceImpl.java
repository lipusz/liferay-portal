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

package com.liferay.portal.search.learn.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.learn.rest.dto.v1_0.Hit;
import com.liferay.portal.search.learn.rest.dto.v1_0.SearchHits;
import com.liferay.portal.search.learn.rest.dto.v1_0.SearchResponse;
import com.liferay.portal.search.learn.rest.resource.v1_0.SearchResponseResource;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Kartunnen
 * @author Tibor Lipusz
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/search-response.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchResponseResource.class
)
public class SearchResponseResourceImpl extends BaseSearchResponseResourceImpl {

	@Override
	public SearchResponse postSearch(String queryString, Pagination pagination)
		throws Exception {

		MatchQuery titleQuery = _queries.match(
			StringBundler.concat(
				"localized_", Field.TITLE, StringPool.UNDERLINE, LocaleUtil.US),
			queryString);

		TermQuery rootFolderQuery = _queries.term(Field.FOLDER_ID, "0");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustQueryClauses(rootFolderQuery, titleQuery);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setEntryClassNames(
					new String[] {"com.liferay.journal.model.JournalArticle"});
				searchContext.setKeywords(queryString);
			});

		SearchRequest searchRequest = searchRequestBuilder.query(
			booleanQuery
		).build();

		return toSearchResponse(_searcher.search(searchRequest));
	}

	protected SearchResponse toSearchResponse(
			com.liferay.portal.search.searcher.SearchResponse searchResponse)
		throws Exception {

		SearchRequest portalSearchRequest = searchResponse.getRequest();

		return SearchResponse.unsafeToDTO(
			String.valueOf(
				new SearchResponse() {
					{
						page = portalSearchRequest.getFrom();
						pageSize = portalSearchRequest.getSize();
						request = _createJSONObject(
							searchResponse.getRequestString());
						requestString = searchResponse.getRequestString();
						response = _createJSONObject(
							searchResponse.getResponseString());
						responseString = searchResponse.getResponseString();
						searchHits = _toSearchHits(
							searchResponse.getSearchHits());
					}
				}));
	}

	private JSONObject _createJSONObject(String string) {
		try {
			return _jsonFactory.createJSONObject(string);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	private Float _getScore(float score) {
		if (Float.isNaN(score)) {
			return null;
		}

		return score;
	}

	private Hit[] _toHits(List<SearchHit> searchHits) {
		List<Hit> hits = new ArrayList<>();

		for (SearchHit searchHit : searchHits) {
			hits.add(
				new Hit() {
					{
						explanation = searchHit.getExplanation();
						id = searchHit.getId();
						score = _getScore(searchHit.getScore());
						version = searchHit.getVersion();
					}
				});
		}

		return hits.toArray(new Hit[0]);
	}

	private SearchHits _toSearchHits(
		com.liferay.portal.search.hits.SearchHits searchHits) {

		return new SearchHits() {
			{
				hits = _toHits(searchHits.getSearchHits());
				maxScore = _getScore(searchHits.getMaxScore());
				totalHits = searchHits.getTotalHits();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResponseResourceImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}