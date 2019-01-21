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

package com.liferay.portal.security.sso.openid.connect.internal.upgrade;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration;
import com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration;
import com.liferay.portal.security.sso.openid.connect.internal.upgrade.v2_0_0.UpgradeOpenIdConnectConfiguration;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tibor Lipusz
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class OpenIdConnectUpgrade implements UpgradeStepRegistrator {

//	@Override
//	public void register(Registry registry) {
//		registry.register(
//			"0.0.0", "2.0.0",
//			new UpgradeOpenIdConnectConfiguration(_configurationAdmin));
//	}

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.openid.connect.configuration." +
					"OpenIdConnectConfiguration",
				OpenIdConnectConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.openid.connect.internal.configuration." +
					"OpenIdConnectProviderConfiguration",
				OpenIdConnectProviderConfiguration.class.getName()));
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}