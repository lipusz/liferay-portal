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

package com.liferay.portal.search.test.util.groupby;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.Assert;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author André de Oliveira
 * @author Tibor Lipusz
 */
public abstract class BaseGroupByTestCase extends BaseIndexingTestCase {

	protected void addDocuments(final String name, int count) throws Exception {
		final String field = GROUP_FIELD;

		for (int i = 1; i <= count; i++) {
			addDocument(DocumentCreationHelpers.singleKeyword(field, name));
		}
	}

	protected void assertGroup(
		String key, int hitsCount, int docsCount,
		Map<String, Hits> groupedHitsMap, SearchContext searchContext) {

		Hits hits = groupedHitsMap.get(key);

		Assert.assertNotNull(hits);
		Assert.assertEquals(hits.toString(), hitsCount, hits.getLength());

		Document[] docs = hits.getDocs();

		Assert.assertEquals(Arrays.toString(docs), docsCount, docs.length);

		assertGroupedHitsFields(docs, searchContext);
	}

	protected void assertGroup(
		String key, int count, Map<String, Hits> groupedHitsMap,
		SearchContext searchContext) {

		assertGroup(key, count, count, groupedHitsMap, searchContext);
	}

	protected void assertGroupedHitsFields(
		Document[] documents, SearchContext searchContext) {

		Stream<Document> documentsStream = Arrays.stream(documents);

		documentsStream.forEach(
			document -> assertSelectedFields(
				document.getFields(), searchContext));
	}

	protected void assertSelectedFields(
		Map<String, Field> fields, SearchContext searchContext) {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (queryConfig.isAllFieldsSelected()) {
			Assert.assertFalse(fields.isEmpty());
		}
		else {
			String[] selectedFieldNames = queryConfig.getSelectedFieldNames();

			Assert.assertEquals(
				String.valueOf(fields.size()), selectedFieldNames.length,
				fields.size());

			Stream<String> fieldNamesStream = Arrays.stream(selectedFieldNames);

			fieldNamesStream.forEach(
				fieldName -> Assert.assertTrue(fields.containsKey(fieldName)));
		}
	}

	protected Map<String, Hits> searchGroups(SearchContext searchContext)
		throws Exception {

		Hits hits = search(searchContext);

		Map<String, Hits> groupedHitsMap = hits.getGroupedHits();

		Assert.assertNotNull(groupedHitsMap);

		return groupedHitsMap;
	}

	protected void testGroupBy() throws Exception {
		addDocuments("sixteen", 16);
		addDocuments("three", 3);
		addDocuments("two", 2);

		final SearchContext searchContext = createSearchContext();

		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Map<String, Hits> groupedHitsMap = searchGroups(
						searchContext);

					Assert.assertEquals(
						groupedHitsMap.toString(), 3, groupedHitsMap.size());

					assertGroup("sixteen", 16, groupedHitsMap, searchContext);
					assertGroup("three", 3, groupedHitsMap, searchContext);
					assertGroup("two", 2, groupedHitsMap, searchContext);

					return null;
				}

			});
	}

	protected void testGroupByWithSelectedFields() throws Exception {
		addDocuments("sixteen", 16);
		addDocuments("three", 3);
		addDocuments("two", 2);

		final SearchContext searchContext = createSearchContext();

		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(Field.COMPANY_ID, Field.UID);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Map<String, Hits> groupedHitsMap = searchGroups(
						searchContext);

					Assert.assertEquals(
						groupedHitsMap.toString(), 3, groupedHitsMap.size());

					assertGroup("sixteen", 16, groupedHitsMap, searchContext);
					assertGroup("three", 3, groupedHitsMap, searchContext);
					assertGroup("two", 2, groupedHitsMap, searchContext);

					return null;
				}

			});
	}

	protected void testStartAndEnd() throws Exception {
		addDocuments("sixteen", 16);

		final SearchContext searchContext = createSearchContext();

		searchContext.setEnd(9);
		searchContext.setGroupBy(new GroupBy(GROUP_FIELD));
		searchContext.setStart(4);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Map<String, Hits> groupedHitsMap = searchGroups(
						searchContext);

					assertGroup(
						"sixteen", 16, 6, groupedHitsMap, searchContext);

					return null;
				}

			});
	}

	protected void testStartAndSize() throws Exception {
		addDocuments("sixteen", 16);

		final SearchContext searchContext = createSearchContext();

		GroupBy groupBy = new GroupBy(GROUP_FIELD);

		groupBy.setSize(3);
		groupBy.setStart(8);

		searchContext.setGroupBy(groupBy);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Map<String, Hits> groupedHitsMap = searchGroups(
						searchContext);

					assertGroup(
						"sixteen", 16, 3, groupedHitsMap, searchContext);

					return null;
				}

			});
	}

	protected static final String GROUP_FIELD = Field.USER_NAME;

}