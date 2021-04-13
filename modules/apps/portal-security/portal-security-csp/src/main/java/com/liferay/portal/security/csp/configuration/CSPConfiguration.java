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

package com.liferay.portal.security.csp.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tibor Lipusz
 */
@ExtendedObjectClassDefinition(category = "security-tools")
@Meta.OCD(
	id = "com.liferay.portal.security.csp.configuration.CSPConfiguration",
	localization = "content/Language", name = "csp"
)
public interface CSPConfiguration {

	@Meta.AD(
		deflt = "default-src 'self'; font-src 'self'; media-src 'self'; script-src 'self'; style-src 'self'", description = "csp-directives",
		name = "csp-directives", required = false
	)
	public String directives();

	@Meta.AD(
		deflt = "true", description = "csp-enabled",
		name = "csp-enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "false", description = "csp-rerport-only",
		name = "csp-rerport-only", required = false
	)
	public boolean reportOnly();

}