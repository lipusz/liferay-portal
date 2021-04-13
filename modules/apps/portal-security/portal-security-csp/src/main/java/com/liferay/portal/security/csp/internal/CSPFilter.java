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

package com.liferay.portal.security.csp.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.security.csp.configuration.CSPConfiguration;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tibor Lipusz
 */
@Component(
	configurationPid = "com.liferay.portal.security.csp.configuration.CSPConfiguration",
	immediate = true,
	property = {
		"servlet-context-name=", "servlet-filter-name=CSP Filter",
		"url-pattern=/*"
	},
	service = Filter.class
)
public class CSPFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _cspConfiguration.enabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cspConfiguration = ConfigurableUtil.createConfigurable(
			CSPConfiguration.class, properties);
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			String logName, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Filter applied");
		}

		httpServletResponse.addHeader(
			"Content-Security-Policy" + (
				_cspConfiguration.reportOnly() ? "-Report-Only" : ""),
			_cspConfiguration.directives());
	}

	private static final Log _log = LogFactoryUtil.getLog(CSPFilter.class);

	private volatile CSPConfiguration _cspConfiguration;

}