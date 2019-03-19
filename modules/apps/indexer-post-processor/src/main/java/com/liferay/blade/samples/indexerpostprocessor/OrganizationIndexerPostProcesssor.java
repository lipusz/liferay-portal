/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.samples.indexerpostprocessor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tibor Lipusz
 */
@Component(
	immediate = true,
	property = {
		"indexer.class.name=com.liferay.portal.kernel.model.Organization"
	},
	service = IndexerPostProcessor.class
)
public class OrganizationIndexerPostProcesssor implements IndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("postProcessContextBooleanFilter");
		}
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("postProcessContextQuery");
		}
	}

	@Override
	public void postProcessDocument(Document document, Object obj)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("postProcessDocument");
		}

		Organization organization = (Organization)obj;

		document.addText(
			"organizationNameAutocomplete", organization.getName());
	}

	@Override
	public void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("postProcessDocument");
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter booleanFilter,
			SearchContext searchContext)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("postProcessDocument");
		}

		if (Validator.isNotNull(searchContext.getKeywords())) {
			searchQuery.addTerm(
				"organizationNameAutocomplete", searchContext.getKeywords(),
				false);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("postProcessDocument");
		}
	}

	@Override
	public void postProcessSummary(
		Summary summary, Document document, Locale locale, String snippet) {

		if (_log.isDebugEnabled()) {
			_log.debug("postProcessSummary");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationIndexerPostProcesssor.class);

}