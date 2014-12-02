package com.liferay.portal.kernel.util;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
public class ParamUtilTest {
	@Test
	public void testGetDouble() {
		testWith("4.7", 4.7, Locale.ENGLISH);
		testWith("4,7", 4.7, new Locale("pt"));
		testWith("-4,7", -4.7, new Locale("pt"));
		testWith("e4.7", _DEFAULT_VALUE, Locale.ENGLISH);
		testWith("e4.7", _DEFAULT_VALUE, new Locale("hu"));
		testWith("-4.7", -4.7, Locale.ENGLISH);
	}

	private void testWith(String expectedStr, double expected, Locale locale) {
		String paramName = "value";
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter(paramName, expectedStr);
		double actual = ParamUtil.getDouble(
			request, paramName, _DEFAULT_VALUE, locale);
		Assert.assertEquals(expected, actual, _DEFAULT_DELTA);
	}

	private static final double _DEFAULT_DELTA = 0.001;

	private static final double _DEFAULT_VALUE = -1.0;

}