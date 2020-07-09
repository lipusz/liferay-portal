package com.liferay.multi.factor.authentication.poc.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
	category = "multi-factor-authentication",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.multi.factor.authentication.poc.internal.configuration.MFAPocConfiguration",
	localization = "content/Language", name = "mfa-poc-configuration-name"
)
public interface MFAPocConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "400", id = "service.ranking", name = "order", required = false
	)
	public int order();

}