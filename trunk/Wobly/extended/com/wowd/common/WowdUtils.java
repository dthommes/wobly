/*******************************************************************************
 * Wobly - Wowd's byte-level serialization protocol
 * Copyright 2008-2011 Wowd Inc. All rights reserved.
 * http://code.google.com/p/wobly/
 *
 * Wobly is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * Wobly is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Wobly. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.wowd.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.wowd.common.objects.tuples.Pair;
import com.wowd.wobly.Wobly;
import com.wowd.wobly.annotations.ReadStatic;
import com.wowd.wobly.exceptions.WoblyReadException;

public class WowdUtils {

	private static final ThreadLocal<Charset> defaultCharset = new ThreadLocal<Charset>(){
		@Override
		protected Charset initialValue() {
			return Charset.forName("UTF-8");
		}
	};

	public static Charset DEFAULT_CHARSET() {
		return defaultCharset.get();
	}


	public static class Common {

		public static <T extends Comparable<T>> int compare(T o1, T o2) {
			return o1 == null?(o2==null?0:-1):o1.compareTo(o2);
		}

		public static boolean equals(Object o1, Object o2) {
			return o1 == null?(o2==null):o1.equals(o2);
		}

		public static int hashCode(Object o) {
			return o==null?0:o.hashCode();
		}

		public static int hashCodePair(Object o1, Object o2) {
			return hashCode(o1)+31*hashCode(o2);
		}

		public static int hashCodeTriple(Object o1, Object o2, Object o3) {
			return hashCode(o1)+31*hashCode(o2)+127*hashCode(o3);
		}

	}

	public static class Reflection {
		public static Field getFieldWithAnotation(Class<?> clazz, Class<? extends Annotation> annotation)
		{
			Field[] fields = clazz.getDeclaredFields();

			for (int i = 0; i < fields.length; i++)
			{
				if (fields[i].isAnnotationPresent(annotation))
					return fields[i];
			}
			return null;
		}

		public static Method getMethodWithAnotation(Class<?> clazz, Class<? extends Annotation> annotation)
		{
			Method[] methods = clazz.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++)
			{
				if (methods[i].isAnnotationPresent(annotation))
					return methods[i];
			}
			return null;
		}

		public static Method getMethodWithName(Class<?> clazz, String name)
		{
			Method[] methods = clazz.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++)
			{
				if (methods[i].getName().equals(name))
					return methods[i];
			}

			clazz = clazz.getSuperclass();
			if (clazz != null)
				return getMethodWithName(clazz, name);
			return null;
		}

		public static <T> T invokeMethodSafe(Method method, Object... params)
		{
			if (method == null) return null;
			try
			{
				method.setAccessible(true);
				return (T)method.invoke(null, params);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
				return null;
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
				return null;
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public static <T extends Wobly> T readObject(Class<T> clazz, ByteBuffer buf) {
			Method method = WowdUtils.Reflection.getMethodWithAnotation(clazz, ReadStatic.class);
			Object result = WowdUtils.Reflection.invokeMethodSafe(method, buf);
			if (result == null)
				throw new WoblyReadException(clazz.toString());
			return (T)result;
		}
	}

	public static class Wrapper {
		public static <P, Q> Map<P, Q> asMap(Pair<? extends P, ? extends Q>... a)
		{
			HashMap<P, Q> res = new HashMap<P, Q>(2 * a.length);
			for (int i = 0; i < a.length; i++)
				res.put(a[i].first(), a[i].second());
			return res;
		}

		public static <T> String collectionToString(Collection<T> collection, String between) {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for(T t : collection) {
				if (first)
					first = false;
				else sb.append(between);
				sb.append(t);
			}
			return sb.toString();
		}

		public static int size(Wobly object) {
			if (object == null)
				return 0;
			else return object.getSize();
		}

		public static int sizeCollection(Collection<? extends Wobly> collection) {
			if (collection == null || collection.size()==0)
				return 0;
			int size = 0;
			size += 1;
			size += 4;
			size += 4;
			for (Wobly v : collection)
				size += v.getSize();
			return size;
		}

	}

}