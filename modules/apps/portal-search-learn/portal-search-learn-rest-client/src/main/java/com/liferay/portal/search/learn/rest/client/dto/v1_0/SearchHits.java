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
import com.liferay.portal.search.learn.rest.client.serdes.v1_0.SearchHitsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Tibor Lipusz
 * @generated
 */
@Generated("")
public class SearchHits implements Cloneable, Serializable {

	public static SearchHits toDTO(String json) {
		return SearchHitsSerDes.toDTO(json);
	}

	public Hit[] getHits() {
		return hits;
	}

	public void setHits(Hit[] hits) {
		this.hits = hits;
	}

	public void setHits(UnsafeSupplier<Hit[], Exception> hitsUnsafeSupplier) {
		try {
			hits = hitsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Hit[] hits;

	public Float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}

	public void setMaxScore(
		UnsafeSupplier<Float, Exception> maxScoreUnsafeSupplier) {

		try {
			maxScore = maxScoreUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float maxScore;

	public Long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(Long totalHits) {
		this.totalHits = totalHits;
	}

	public void setTotalHits(
		UnsafeSupplier<Long, Exception> totalHitsUnsafeSupplier) {

		try {
			totalHits = totalHitsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long totalHits;

	@Override
	public SearchHits clone() throws CloneNotSupportedException {
		return (SearchHits)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchHits)) {
			return false;
		}

		SearchHits searchHits = (SearchHits)object;

		return Objects.equals(toString(), searchHits.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SearchHitsSerDes.toJSON(this);
	}

}