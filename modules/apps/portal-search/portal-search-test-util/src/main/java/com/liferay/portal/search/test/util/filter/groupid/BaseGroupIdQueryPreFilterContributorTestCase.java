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

package com.liferay.portal.search.test.util.filter.groupid;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupWrapper;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
import com.liferay.portal.search.internal.spi.model.query.contributor.GroupIdQueryPreFilterContributor;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tibor Lipusz
 */
public abstract class BaseGroupIdQueryPreFilterContributorTestCase
	extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpGroupLocalService();
	}

	@Test
	public void testGroupIdQueryPreFilterScopeEverythingWithInactiveGroups()
		throws Exception {

		doTestGroupIdQueryPreFilter(0, 3);
	}

	@Test
	public void testGroupIdQueryPreFilterScopeSingleGroup() throws Exception {
		doTestGroupIdQueryPreFilter(1, 1);
	}

	protected void addDocument(long groupId) throws Exception {
		addDocument(
			document -> {
				document.addKeyword(Field.GROUP_ID, groupId);
				document.addKeyword(Field.SCOPE_GROUP_ID, groupId);
			});
	}

	protected void doTestGroupIdQueryPreFilter(
			long scopeGroupId, int expectedCount)
		throws Exception {

		addDocument(1);
		addDocument(2);
		addDocument(3);
		addDocument(INACTIVE_GROUP_ID1);
		addDocument(INACTIVE_GROUP_ID2);

		assertSearch(
			indexingTestHelper -> {
				SearchContext searchContext =
					indexingTestHelper.getSearchContext();

				searchContext.setGroupIds(new long[] {scopeGroupId});

				GroupIdQueryPreFilterContributor contributor =
					new GroupIdQueryPreFilterContributor();

				BooleanFilter booleanFilter = new BooleanFilter();

				contributor.setGroupLocalService(groupLocalService);

				contributor.contribute(
					booleanFilter, indexingTestHelper.getSearchContext());

				indexingTestHelper.setFilter(booleanFilter);

				indexingTestHelper.search();

				indexingTestHelper.assertResultCount(expectedCount);
			});
	}

	protected void setUpGroupLocalService() {
		groupLocalService = new GroupLocalServiceWrapper(null) {

			@Override
			public List<Long> getActiveGroupIds(
				long companyId, boolean active) {

				return inactiveGroupIds;
			}

			@Override
			public Group getGroup(long groupId) throws PortalException {
				return new GroupWrapper(null) {

					@Override
					public long getGroupId() {
						return groupId;
					}

					@Override
					public boolean isLayout() {
						return false;
					}

				};
			}

			@Override
			public boolean isLiveGroupActive(Group group) {
				return true;
			}

		};
	}

	protected static final long INACTIVE_GROUP_ID1 = 4L;

	protected static final long INACTIVE_GROUP_ID2 = 5L;

	protected static final List<Long> inactiveGroupIds = Arrays.asList(
		INACTIVE_GROUP_ID1, INACTIVE_GROUP_ID2);

	protected GroupLocalService groupLocalService;

}