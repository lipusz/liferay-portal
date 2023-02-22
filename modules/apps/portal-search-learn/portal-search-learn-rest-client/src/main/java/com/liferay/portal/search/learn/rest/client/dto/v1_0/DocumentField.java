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

package com.liferay.portal.search.learn.rest.client.dto.v1_0;

import com.liferay.portal.search.learn.rest.client.function.UnsafeSupplier;
import com.liferay.portal.search.learn.rest.client.serdes.v1_0.DocumentFieldSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Tibor Lipusz
 * @generated
 */
@Generated("")
public class DocumentField implements Cloneable, Serializable {

	public static DocumentField toDTO(String json) {
		return DocumentFieldSerDes.toDTO(json);
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public void setValues(
		UnsafeSupplier<Object[], Exception> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object[] values;

	@Override
	public DocumentField clone() throws CloneNotSupportedException {
		return (DocumentField)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentField)) {
			return false;
		}

		DocumentField documentField = (DocumentField)object;

		return Objects.equals(toString(), documentField.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DocumentFieldSerDes.toJSON(this);
	}

}