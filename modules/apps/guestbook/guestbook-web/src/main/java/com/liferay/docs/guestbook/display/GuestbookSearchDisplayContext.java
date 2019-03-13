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

package com.liferay.docs.guestbook.display;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tibor Lipusz
 */
@Component(immediate = true)
public class GuestbookSearchDisplayContext {

	public GuestbookSearchDisplayContext() {
	}

	public GuestbookSearchDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_log.debug("init");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GuestbookSearchDisplayContext.class);

	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;

}