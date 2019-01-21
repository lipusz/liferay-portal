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

package com.liferay.portal.security.sso.openid.connect.internal.upgrade.v2_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration;
import com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tibor Lipusz
 */
public class UpgradeOpenIdConnectConfiguration extends UpgradeProcess {

	public UpgradeOpenIdConnectConfiguration(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		doUpgradeOpenIdConnectConfigurations(
			"com.liferay.openid.connect.configuration." +
				"OpenIdConnectConfiguration",
			OpenIdConnectConfiguration.class.getName());
		doUpgradeOpenIdConnectConfigurations(
			"com.liferay.openid.connect.internal.configuration." +
				"OpenIdConnectProviderConfiguration",
			OpenIdConnectProviderConfiguration.class.getName());
	}

	protected void doUpgradeOpenIdConnectConfigurations(
			String oldConfigurationClassName, String newConfigurationClassName)
		throws Exception {

		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=", oldConfigurationClassName, ")");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		for (Configuration oldConfiguration : configurations) {
			Dictionary<String, Object> properties =
				oldConfiguration.getProperties();

			if (properties == null) {
				continue;
			}

			Configuration newConfiguration;

			if (oldConfiguration.getFactoryPid() == null) {
				newConfiguration = _configurationAdmin.getConfiguration(
					newConfigurationClassName, StringPool.QUESTION);
			}
			else {
				newConfiguration =
					_configurationAdmin.createFactoryConfiguration(
						newConfigurationClassName, StringPool.QUESTION);
			}

			newConfiguration.update(properties);

			oldConfiguration.delete();
		}
	}

	private final ConfigurationAdmin _configurationAdmin;

}