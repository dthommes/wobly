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
package com.wowd.wobly.generation.types.impl.tuple;

import com.wowd.common.objects.tuples.interfaces.IHolder;
import com.wowd.common.objects.tuples.interfaces.IPair;
import com.wowd.common.objects.tuples.interfaces.ITriple;


public class TupleGeneratorUtils
{
	public static boolean isTuple(Class<?> clazz) {
		return IHolder.class.isAssignableFrom(clazz);
	}

	public static boolean isPair(Class<?> clazz) {
		return IPair.class.isAssignableFrom(clazz);
	}

	public static boolean isTriple(Class<?> clazz) {
		return ITriple.class.isAssignableFrom(clazz);
	}
	
	
}
