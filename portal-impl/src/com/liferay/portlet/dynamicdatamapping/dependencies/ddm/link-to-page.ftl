<#include "../init.ftl">

<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

<#macro getLayoutsOptions
	groupId
	parentLayoutId
	privateLayout
	selectedPlid
	level = 0
>
	<#assign layouts = {}>

	<#assign selectedLayout = layoutLocalService.fetchLayout(selectedPlid)!"">

	<#assign selectedLayoutAncerstorsIds = {}>

	<#if (validator.isNotNull(selectedLayout))>
		<#assign layouts = layoutLocalService.getLayouts(groupId, privateLayout, parentLayoutId)>

		<#assign selectedLayoutAncerstorsIds = listUtil.toList(selectedLayout.getAncestors(), staticUtil["com.liferay.portal.model.Layout"].LAYOUT_ID_ACCESSOR)>
	<#else>
		<#assign layoutService = serviceLocator.findService("com.liferay.portal.service.LayoutService")>

		<#assign layouts = layoutService.getLayouts(groupId, privateLayout, parentLayoutId)>
	</#if>

	<#if (layouts?size > 0)>
		<#if (level == 0)>
			<optgroup label="<#if (privateLayout)>${languageUtil.get(requestedLocale, "private-pages")}<#else>${languageUtil.get(requestedLocale, "public-pages")}</#if>">
		</#if>

		<#list layouts as curLayout>
			<#assign curLayoutJSON = escapeAttribute("{ \"layoutId\": ${curLayout.getLayoutId()}, \"groupId\": ${groupId}, \"privateLayout\": ${privateLayout?string} }")>

			<#if validator.isNull(selectedLayout) || layoutPermission.contains(permissionChecker, curLayout, "VIEW") || (selectedPlid == curLayout.getPlid()) || (selectedLayoutAncerstorsIds?has_content && selectedLayoutAncerstorsIds?seq_contains(curLayout.getLayoutId()))>
				<#assign selected = (selectedPlid == curLayout.getPlid())>

				<@aui.option selected=selected useModelValue=false value=curLayoutJSON>
					<#list 0..level as i>
						&ndash;&nbsp;
					</#list>

					${escape(curLayout.getName(requestedLocale))}
				</@>

				<@getLayoutsOptions
					groupId = scopeGroupId
					level = level + 1
					parentLayoutId = curLayout.getLayoutId()
					privateLayout = privateLayout
					selectedPlid = selectedPlid
				/>
			</#if>
		</#list>

		<#if (level == 0)>
			</optgroup>
		</#if>

	</#if>

</#macro>

<@aui["field-wrapper"] data=data>
	<#assign selectedPlid = 0>

	<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

	<#if (fieldRawValue?? && fieldRawValue != "")>
		<#assign fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

		<#if (fieldLayoutJSONObject.getLong("groupId") > 0)>
			<#assign selectedLayoutGroupId = fieldLayoutJSONObject.getLong("groupId")>
		<#else>
			<#assign selectedLayoutGroupId = scopeGroupId>
		</#if>

		<#assign selectedLayout = layoutLocalService.fetchLayout(selectedLayoutGroupId, fieldLayoutJSONObject.getBoolean("privateLayout"), fieldLayoutJSONObject.getLong("layoutId"))!"">

		<#if (selectedLayout?? && selectedLayout != "")>
			<#assign selectedPlid = selectedLayout.getPlid()>
		</#if>
	</#if>

	<@aui.select helpMessage=escape(fieldStructure.tip) name=namespacedFieldName label=escape(label) required=required>
		<@getLayoutsOptions
			groupId = scopeGroupId
			parentLayoutId = 0
			privateLayout = false
			selectedPlid = selectedPlid
		/>

		<@getLayoutsOptions
			groupId = scopeGroupId
			parentLayoutId = 0
			privateLayout = true
			selectedPlid = selectedPlid
		/>
	</@aui.select>

	${fieldStructure.children}
</@>