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

package com.liferay.support.example.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Tibor Lipusz
 */
@Component(
	configurationPid = "com.liferay.support.example.web.configuration.SupportExampleConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"javax.portlet.display-name=Support Example",
		"javax.portlet.init-param.view-template=/view.jsp"
	},
	service = Portlet.class
)
public class SupportExamplePortlet extends MVCPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		viewTemplate = "/view.jsp";
	}

}