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

package poc.db.service.impl;

import java.util.Date;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import poc.db.model.Foo;
import poc.db.service.base.FooServiceBaseImpl;

/**
 * The implementation of the foo remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link poc.db.service.FooService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have
 * security checks based on the propagated JAAS credentials because this service
 * can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FooServiceBaseImpl
 * @see poc.db.service.FooServiceUtil
 */
public class FooServiceImpl extends FooServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link poc.db.service.FooServiceUtil} to access the foo remote service.
	 */
	Log log = LogFactoryUtil.getLog(getClass());

	public Foo generateFooRow() {
		try {
			Foo foo = fooLocalService.createFoo(counterLocalService.increment(getModelClassName()));
			foo.setField1("test");
			foo.setField2(true);
			foo.setField3(12);
			foo.setField4(new Date());
			foo.setField5("test2");
			return foo;
		} catch (Exception e) {
			log.error(e.fillInStackTrace());
		}
		return null;
	}

	public void updateFoobyParam(Foo foo) {
		try {
			fooLocalService.updateFoo(foo);
		} catch (Exception e) {
			log.error(e.fillInStackTrace());
		}
	}
}