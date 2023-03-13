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

package com.liferay.portal.search.internal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.configuration.CategoryFacetFieldConfiguration;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tibor Lipusz
 */
@Component(
	property = "initial.deployment=true",
	service = {
		CategoryFacetFieldConfigurationVerifyProcess.class, VerifyProcess.class
	}
)
public class CategoryFacetFieldConfigurationVerifyProcess
	extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + CategoryFacetFieldConfiguration.class.getName() +
				"*)");

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				continue;
			}

			String categoryFacetField = GetterUtil.getString(
				properties.get("categoryFacetField"));

			if (!categoryFacetField.equals("assetVocabularyCategoryIds")) {
				properties.put(
					"categoryFacetField", "assetVocabularyCategoryIds");

				_log.info(properties);

				configuration.update(properties);
			}
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private static final Log _log = LogFactoryUtil.getLog(
		CategoryFacetFieldConfigurationVerifyProcess.class);

}