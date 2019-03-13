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

package com.liferay.docs.guestbook.service.impl;

import com.liferay.docs.guestbook.model.Entry;
import com.liferay.docs.guestbook.service.base.EntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The implementation of the entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.docs.guestbook.service.EntryService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author liferay
 * @see EntryServiceBaseImpl
 * @see com.liferay.docs.guestbook.service.EntryServiceUtil
 */
public class EntryServiceImpl extends EntryServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.docs.guestbook.service.EntryServiceUtil} to access the entry remote service.
	 */

	public List<Entry> search(long companyId) throws PortalException {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustQueryClauses(
			_queries.term(Field.COMPANY_ID, companyId));

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.getSearchRequestBuilder(
				new SearchContext());

		SearchRequest searchRequest = searchRequestBuilder.entryClassNames(
			Entry.class.getName()
		).modelIndexerClasses(
			Entry.class
		).query(
			booleanQuery
		).withSearchContext(
			searchContext -> searchContext.setCompanyId(companyId)
		).build();

		SearchResponse searchResponse = _searcher.search(searchRequest);

		return getEntries(searchResponse);
	}

	protected List<Entry> getEntries(SearchResponse searchResponse) {
		Stream<Document> documentStream = searchResponse.getDocumentsStream();

		return documentStream.map(
			document -> document.getFieldValue(Field.ENTRY_CLASS_PK)
		).map(
			GetterUtil::getLong
		).map(
			entryLocalService::fetchEntry
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	@ServiceReference(type = Queries.class)
	private Queries _queries;

	@ServiceReference(type = Searcher.class)
	private Searcher _searcher;

	@ServiceReference(type = SearchRequestBuilderFactory.class)
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}