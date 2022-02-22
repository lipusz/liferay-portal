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

package com.liferay.object.web.internal.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContextFactory;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ObjectWebKeys.OBJECT_ENTRY,
	service = AssetRendererFactory.class
)
public class ObjectEntryAssetRendererFactory
	extends BaseAssetRendererFactory<ObjectEntry> {

	public ObjectEntryAssetRendererFactory(
		ObjectDefinition objectDefinition,
		ObjectEntryDisplayContextFactory objectEntryDisplayContextFactory,
		ObjectEntryService objectEntryService, ServletContext servletContext) {

		setClassName(objectDefinition.getClassName());
		setPortletId(objectDefinition.getPortletId());

		_objectDefinition = objectDefinition;
		_objectEntryDisplayContextFactory = objectEntryDisplayContextFactory;
		_objectEntryService = objectEntryService;
		_servletContext = servletContext;
	}

	@Override
	public AssetRenderer<ObjectEntry> getAssetRenderer(long classPK, int type)
		throws PortalException {

		ObjectEntryAssetRenderer objectEntryAssetRenderer =
			new ObjectEntryAssetRenderer(
				_objectDefinition, _objectEntryService.getObjectEntry(classPK),
				_objectEntryDisplayContextFactory, _objectEntryService);

		objectEntryAssetRenderer.setAssetDisplayPageFriendlyURLProvider(
			_assetDisplayPageFriendlyURLProvider);
		objectEntryAssetRenderer.setServletContext(_servletContext);

		return objectEntryAssetRenderer;
	}

	@Override
	public String getType() {
		return _objectDefinition.getClassName();
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryDisplayContextFactory
		_objectEntryDisplayContextFactory;
	private final ObjectEntryService _objectEntryService;
	private final ServletContext _servletContext;

}