/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.expando.kernel.service.permission.ExpandoColumnPermissionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.internal.blueprint.parameter.BooleanArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.DoubleArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.FloatArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.FloatSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.StringArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.StringSXPParameter;
import com.liferay.segments.SegmentsEntryRetriever;
import java.beans.ExceptionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class AccountSXPParameterContributor implements SXPParameterContributor {

	public AccountSXPParameterContributor(
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoValueLocalService expandoValueLocalService,
		Language language,
		SegmentsEntryRetriever segmentsEntryRetriever,
		UserLocalService userLocalService,
		AccountEntryLocalService accountEntryLocalService) {

		_expandoColumnLocalService = expandoColumnLocalService;
		_expandoValueLocalService = expandoValueLocalService;

		_language = language;

		_segmentsEntryRetriever = segmentsEntryRetriever;
		_userLocalService = userLocalService;
		this._accountEntryLocalService = accountEntryLocalService;
	}

	@Override
	public void contribute(
		ExceptionListener exceptionListener, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		try {
			_contribute(searchContext, sxpParameters);
		}
		catch (PortalException portalException) {
			exceptionListener.exceptionThrown(portalException);

			_log.error(portalException);
		}
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "account";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions(long companyId, Locale locale) {

		return _getSXPParameterContributorDefinitions(
			companyId, locale,
			ListUtil.fromArray(

				new SXPParameterContributorDefinition(
					StringSXPParameter.class, "external-reference-code",
					"account.external_reference_code"),
				new SXPParameterContributorDefinition(
					StringSXPParameter.class, "name", "account.name")
				));
	}



	private void _addExpandoSXPParameters(
			SearchContext searchContext, Set<SXPParameter> sxpParameters,
			AccountEntry accountEntry)
		throws PortalException {

		List<ExpandoColumn> expandoColumns =
			_expandoColumnLocalService.getDefaultTableColumns(
				searchContext.getCompanyId(), AccountEntry.class.getName());

		if (ListUtil.isEmpty(expandoColumns)) {
			return;
		}

		Map<Long, ExpandoValue> expandoValues = new HashMap<>();

		for (ExpandoValue expandoValue :
				_expandoValueLocalService.getRowValues(
					searchContext.getCompanyId(), AccountEntry.class.getName(),
					ExpandoTableConstants.DEFAULT_TABLE_NAME,
					accountEntry.getPrimaryKey(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			expandoValues.put(expandoValue.getColumnId(), expandoValue);
		}

		for (ExpandoColumn expandoColumn : expandoColumns) {
			ExpandoValue expandoValue = expandoValues.get(
				expandoColumn.getColumnId());

			if (expandoValue == null) {
				expandoValue = _expandoValueLocalService.createExpandoValue(0);

				expandoValue.setData(expandoColumn.getDefaultData());
			}

			String expandoSXPParameterName = _getExpandoSXPParameterName(
				expandoColumn);

			int type = expandoColumn.getType();

			if (type == ExpandoColumnConstants.BOOLEAN) {
				sxpParameters.add(
					new BooleanSXPParameter(
						expandoSXPParameterName, true,
						expandoValue.getBoolean()));
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				sxpParameters.add(
					new BooleanArraySXPParameter(
						expandoSXPParameterName, true,
						ArrayUtil.toArray(expandoValue.getBooleanArray())));
			}
			else if (type == ExpandoColumnConstants.DATE) {
				sxpParameters.add(
					new DateSXPParameter(
						expandoSXPParameterName, true, expandoValue.getDate()));
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				sxpParameters.add(
					new DoubleSXPParameter(
						expandoSXPParameterName, true,
						expandoValue.getDouble()));
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				sxpParameters.add(
					new DoubleArraySXPParameter(
						expandoSXPParameterName, true,
						ArrayUtil.toArray(expandoValue.getDoubleArray())));
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				sxpParameters.add(
					new FloatSXPParameter(
						expandoSXPParameterName, true,
						expandoValue.getFloat()));
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				sxpParameters.add(
					new FloatArraySXPParameter(
						expandoSXPParameterName, true,
						ArrayUtil.toArray(expandoValue.getFloatArray())));
			}
			else if (type == ExpandoColumnConstants.GEOLOCATION) {
				JSONObject jsonObject = expandoValue.getGeolocationJSONObject();

				sxpParameters.add(
					new DoubleSXPParameter(
						expandoSXPParameterName + ".latitude", true,
						jsonObject.getDouble("latitude")));
				sxpParameters.add(
					new DoubleSXPParameter(
						expandoSXPParameterName + ".longitude", true,
						jsonObject.getDouble("longitude")));
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				sxpParameters.add(
					new IntegerSXPParameter(
						expandoSXPParameterName, true,
						expandoValue.getInteger()));
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				sxpParameters.add(
					new IntegerArraySXPParameter(
						expandoSXPParameterName, true,
						ArrayUtil.toArray(expandoValue.getIntegerArray())));
			}
			else if (type == ExpandoColumnConstants.LONG) {
				sxpParameters.add(
					new LongSXPParameter(
						expandoSXPParameterName, true, expandoValue.getLong()));
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				sxpParameters.add(
					new LongArraySXPParameter(
						expandoSXPParameterName, true,
						ArrayUtil.toArray(expandoValue.getLongArray())));
			}
			else if (type == ExpandoColumnConstants.NUMBER) {
				sxpParameters.add(
					new StringSXPParameter(
						expandoSXPParameterName, true, expandoValue.getData()));
			}
			else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
				sxpParameters.add(
					new StringArraySXPParameter(
						expandoSXPParameterName, true,
						StringUtil.split(expandoValue.getData())));
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				sxpParameters.add(
					new IntegerSXPParameter(
						expandoSXPParameterName, true,
						GetterUtil.getInteger(expandoValue.getShort())));
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				short[] shortArray = expandoValue.getShortArray();

				Integer[] integerArray = new Integer[shortArray.length];

				for (int i = 0; i < shortArray.length; i++) {
					integerArray[i] = (int)shortArray[i];
				}

				sxpParameters.add(
					new IntegerArraySXPParameter(
						expandoSXPParameterName, true, integerArray));
			}
			else if (type == ExpandoColumnConstants.STRING) {
				sxpParameters.add(
					new StringSXPParameter(
						expandoSXPParameterName, true,
						expandoValue.getString()));
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				sxpParameters.add(
					new StringArraySXPParameter(
						expandoSXPParameterName, true,
						expandoValue.getStringArray()));
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED) {
				sxpParameters.add(
					new StringArraySXPParameter(
						StringBundler.concat(
							expandoSXPParameterName, StringPool.UNDERLINE,
							_language.getLanguageId(searchContext.getLocale())),
						true,
						expandoValue.getStringArray(
							searchContext.getLocale())));
			}
			else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
				sxpParameters.add(
					new StringSXPParameter(
						StringBundler.concat(
							expandoSXPParameterName, StringPool.UNDERLINE,
							_language.getLanguageId(searchContext.getLocale())),
						true,
						expandoValue.getString(searchContext.getLocale())));
			}
		}
	}

	private void _contribute(
			SearchContext searchContext, Set<SXPParameter> sxpParameters)
		throws PortalException {

		long userId = searchContext.getUserId();

		if (userId == 0) {
			return;
		}

		User user = _userLocalService.fetchUserById(userId);

		if (user == null) {
			return;
		}

		long accountEntryId = GetterUtil.get(searchContext.getAttribute("accountEntryId"), -1l);
		if(accountEntryId == -1){
			return;
		}

		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(accountEntryId);

		if(accountEntry == null){
			return;
		}

		_addExpandoSXPParameters(searchContext, sxpParameters, accountEntry);
	}


	private String _getExpandoSXPParameterName(ExpandoColumn expandoColumn) {
		StringBundler sb = new StringBundler(2);

		sb.append("account.custom.field.");
		sb.append(
			StringUtil.toLowerCase(
				StringUtil.replace(
					expandoColumn.getName(), StringPool.BLANK, "_")));

		return sb.toString();
	}

	private String _getExpandoSXPParameterName(
		ExpandoColumn expandoColumn, Locale locale) {

		return StringBundler.concat(
			_getExpandoSXPParameterName(expandoColumn), StringPool.UNDERLINE,
			_language.getLanguageId(locale));
	}



	private List<SXPParameterContributorDefinition>
		_getSXPParameterContributorDefinitions(
			long companyId, Locale locale,
			List<SXPParameterContributorDefinition>
				sxpParameterContributorDefinitions) {

		List<ExpandoColumn> expandoColumns =
			_expandoColumnLocalService.getDefaultTableColumns(
				companyId, User.class.getName());

		if (ListUtil.isEmpty(expandoColumns)) {
			return sxpParameterContributorDefinitions;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		for (ExpandoColumn expandoColumn : expandoColumns) {
			if (PropsValues.
					PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT &&
				!ExpandoColumnPermissionUtil.contains(
					permissionChecker, companyId, User.class.getName(),
					ExpandoTableConstants.DEFAULT_TABLE_NAME,
					expandoColumn.getName(), ActionKeys.VIEW)) {

				continue;
			}

			String expandoSXPParameterName = _getExpandoSXPParameterName(
				expandoColumn);

			int type = expandoColumn.getType();

			if (type == ExpandoColumnConstants.BOOLEAN) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						BooleanSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.DATE) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						DateSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						DoubleSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						DoubleArraySXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						FloatSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						FloatArraySXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.GEOLOCATION) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						FloatSXPParameter.class,
						StringBundler.concat(
							expandoColumn.getDisplayName(locale), " (",
							_language.get(locale, "latitude"), ")"),
						expandoSXPParameterName + ".latitude"));
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						FloatSXPParameter.class,
						StringBundler.concat(
							expandoColumn.getDisplayName(locale), " (",
							_language.get(locale, "longitude"), ")"),
						expandoSXPParameterName + ".longitude"));
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						IntegerSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						IntegerArraySXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.LONG) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						LongSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						LongArraySXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.NUMBER) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						StringSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						StringArraySXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						IntegerSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						IntegerArraySXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.STRING) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						StringSXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						StringArraySXPParameter.class,
						expandoColumn.getDisplayName(locale),
						expandoSXPParameterName));
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						StringArraySXPParameter.class,
						StringBundler.concat(
							expandoColumn.getDisplayName(locale), " (",
							_language.get(locale, "localized"), ")"),
						_getExpandoSXPParameterName(expandoColumn, locale)));
			}
			else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
				sxpParameterContributorDefinitions.add(
					new SXPParameterContributorDefinition(
						StringSXPParameter.class,
						StringBundler.concat(
							expandoColumn.getDisplayName(locale), " (",
							_language.get(locale, "localized"), ")"),
						_getExpandoSXPParameterName(expandoColumn, locale)));
			}
		}

		return sxpParameterContributorDefinitions;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountSXPParameterContributor.class);

	private final ExpandoColumnLocalService _expandoColumnLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;
	private final Language _language;
	private final SegmentsEntryRetriever _segmentsEntryRetriever;

	private final UserLocalService _userLocalService;

	private final AccountEntryLocalService _accountEntryLocalService;

}