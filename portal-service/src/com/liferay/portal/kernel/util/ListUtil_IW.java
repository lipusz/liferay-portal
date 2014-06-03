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

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class ListUtil_IW {
	public static ListUtil_IW getInstance() {
		return _instance;
	}

	public <E> java.util.List<E> copy(java.util.List<E> master) {
		return ListUtil.copy(master);
	}

	public <E> void copy(java.util.List<E> master, java.util.List<E> copy) {
		ListUtil.copy(master, copy);
	}

	public <E> int count(java.util.List<E> list,
		com.liferay.portal.kernel.util.PredicateFilter<E> predicateFilter) {
		return ListUtil.count(list, predicateFilter);
	}

	public <E> void distinct(java.util.List<E> list,
		java.util.Comparator<E> comparator) {
		ListUtil.distinct(list, comparator);
	}

	public void distinct(java.util.List<?> list) {
		ListUtil.distinct(list);
	}

	public <E> boolean exists(java.util.List<E> list,
		com.liferay.portal.kernel.util.PredicateFilter<E> predicateFilter) {
		return ListUtil.exists(list, predicateFilter);
	}

	public <T> java.util.List<T> filter(java.util.List<T> inputList,
		java.util.List<T> outputList,
		com.liferay.portal.kernel.util.PredicateFilter<T> predicateFilter) {
		return ListUtil.filter(inputList, outputList, predicateFilter);
	}

	public <T> java.util.List<T> filter(java.util.List<T> inputList,
		com.liferay.portal.kernel.util.PredicateFilter<T> predicateFilter) {
		return ListUtil.filter(inputList, predicateFilter);
	}

	public <E> java.util.List<E> fromArray(E[] array) {
		return ListUtil.fromArray(array);
	}

	public <E> java.util.List<E> fromCollection(java.util.Collection<E> c) {
		return ListUtil.fromCollection(c);
	}

	public <E> java.util.List<E> fromEnumeration(java.util.Enumeration<E> enu) {
		return ListUtil.fromEnumeration(enu);
	}

	public java.util.List<java.lang.String> fromFile(java.io.File file)
		throws java.io.IOException {
		return ListUtil.fromFile(file);
	}

	public java.util.List<java.lang.String> fromFile(java.lang.String fileName)
		throws java.io.IOException {
		return ListUtil.fromFile(fileName);
	}

	public <E> java.util.List<E> fromMapKeys(java.util.Map<E, ?> map) {
		return ListUtil.fromMapKeys(map);
	}

	public <E> java.util.List<E> fromMapValues(java.util.Map<?, E> map) {
		return ListUtil.fromMapValues(map);
	}

	public java.util.List<java.lang.String> fromString(java.lang.String s) {
		return ListUtil.fromString(s);
	}

	public java.util.List<java.lang.String> fromString(java.lang.String s,
		java.lang.String delimiter) {
		return ListUtil.fromString(s, delimiter);
	}

	public boolean isEmpty(java.util.List<?> list) {
		return ListUtil.isEmpty(list);
	}

	public boolean isNotEmpty(java.util.List<?> list) {
		return ListUtil.isNotEmpty(list);
	}

	public boolean isUnmodifiableList(java.util.List<?> list) {
		return ListUtil.isUnmodifiableList(list);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public <E> boolean remove(java.util.List<E> list, E element) {
		return ListUtil.remove(list, element);
	}

	public <E> java.util.List<E> remove(java.util.List<E> list,
		java.util.List<E> remove) {
		return ListUtil.remove(list, remove);
	}

	public <E> java.util.List<E> sort(java.util.List<E> list) {
		return ListUtil.sort(list);
	}

	public <E> java.util.List<E> sort(java.util.List<E> list,
		java.util.Comparator<E> comparator) {
		return ListUtil.sort(list, comparator);
	}

	public <E> java.util.List<E> subList(java.util.List<E> list, int start,
		int end) {
		return ListUtil.subList(list, start, end);
	}

	public java.util.List<java.lang.Boolean> toList(boolean[] array) {
		return ListUtil.toList(array);
	}

	public java.util.List<java.lang.Character> toList(char[] array) {
		return ListUtil.toList(array);
	}

	public java.util.List<java.lang.Double> toList(double[] array) {
		return ListUtil.toList(array);
	}

	public <E> java.util.List<E> toList(E[] array) {
		return ListUtil.toList(array);
	}

	public java.util.List<java.lang.Float> toList(float[] array) {
		return ListUtil.toList(array);
	}

	public java.util.List<java.lang.Integer> toList(int[] array) {
		return ListUtil.toList(array);
	}

	public <T, A> java.util.List<A> toList(java.util.List<T> list,
		com.liferay.portal.kernel.util.Accessor<T, A> accessor) {
		return ListUtil.toList(list, accessor);
	}

	public java.util.List<java.lang.Long> toList(long[] array) {
		return ListUtil.toList(array);
	}

	public java.util.List<java.lang.Short> toList(short[] array) {
		return ListUtil.toList(array);
	}

	public <T, A> java.lang.String toString(java.util.List<T> list,
		com.liferay.portal.kernel.util.Accessor<T, A> accessor) {
		return ListUtil.toString(list, accessor);
	}

	public <T, A> java.lang.String toString(java.util.List<T> list,
		com.liferay.portal.kernel.util.Accessor<T, A> accessor,
		java.lang.String delimiter) {
		return ListUtil.toString(list, accessor, delimiter);
	}

	public java.lang.String toString(java.util.List<?> list,
		java.lang.String param) {
		return ListUtil.toString(list, param);
	}

	public java.lang.String toString(java.util.List<?> list,
		java.lang.String param, java.lang.String delimiter) {
		return ListUtil.toString(list, param, delimiter);
	}

	private ListUtil_IW() {
	}

	private static ListUtil_IW _instance = new ListUtil_IW();
}