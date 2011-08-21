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
package com.wowd.wobly.generation.types.impl.trove;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wowd.common.WowdUtils;
import com.wowd.common.objects.tuples.Pair;
import com.wowd.wobly.generation.WoblyGeneratorUtils;

public class TroveGeneratorUtils
{
	private static final List<String> allowedStrings = Arrays.asList("Byte", "Short", "Int", "Long", "Float", "Double", "Object");
	
	
	private static final Map<String, Class<?>> primiteveMappings = 
		WowdUtils.Wrapper.<String, Class<?>>asMap(
				Pair.create("Byte", byte.class),
				Pair.create("Short", short.class),
				Pair.create("Int", int.class),
				Pair.create("Long", long.class),
				Pair.create("Float", float.class),
				Pair.create("Double", double.class)		
		);
	
	
	private static Pair<String, String> match(String pending) {
		for (String s : allowedStrings)
			if (pending.startsWith(s))
				return Pair.create(s, pending.substring(s.length()));
		return null;
	}
	
	public static Type extractNextType(Type t, int index) {		
		Class<?> clazz = WoblyGeneratorUtils.extractClass(t);
		if (clazz.getName().startsWith("gnu.trove")) {
			String simpleName = clazz.getSimpleName();
			if (simpleName.startsWith("T")) {
				String pending = simpleName.substring(1);
				Pair<String, String> pair = null;				
				for (int i = 0;i<=index;i++) {
					pair = match(pending);
					if (pair == null)
						throw new UnsupportedOperationException(t + " " + index);
					pending = pair.second();
				}
				if (pair.first().equals("Object"))
					return ((ParameterizedType) t).getActualTypeArguments()[0];
				else return primiteveMappings.get(pair.first());
			}
		}
		throw new UnsupportedOperationException(t + " " + index);
	}
	
	public static Class<?> getUntrovized(Class<?> clazz) {
		if (clazz.getName().startsWith("gnu.trove")) {
			String simpleName = clazz.getSimpleName();
			if (simpleName.startsWith("T")) {
				if (simpleName.endsWith("HashSet")) 				
					return Set.class;
				if (simpleName.endsWith("HashMap"))
					return Map.class;
				if (simpleName.endsWith("ArrayList"))
					return List.class;
			}
		}
		return clazz;
	}
		
	public static boolean isTroveClass(Class<?> clazz) {
		if (Map.class.isAssignableFrom(clazz))
			return false;
		if (Collection.class.isAssignableFrom(clazz))
			return false;
		
		
		if (clazz.getName().startsWith("gnu.trove")) {
			String simpleName = clazz.getSimpleName();
			if (simpleName.startsWith("T")) {
				if (simpleName.endsWith("HashSet")) 				
					return true;
				if (simpleName.endsWith("HashMap"))
					return true;
				if (simpleName.endsWith("ArrayList"))
					return true;
			}
		}
		return false;
	}
			
	public static boolean isTroveType(Type t) {
		Class<?> clazz = WoblyGeneratorUtils.extractClass(t);		
		return isTroveClass(clazz);
	}
	
//			
//				String pending = simpleName.substring(1);
//				for (String s : allowedStrings)
//					if (pending.startsWith(s))
//						return 
//						
//			}
//		}
//	}
	
}
