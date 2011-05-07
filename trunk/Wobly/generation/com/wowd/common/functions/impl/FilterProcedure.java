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
package com.wowd.common.functions.impl;

import com.wowd.common.functions.ObjectFilter;
import com.wowd.common.functions.Procedure;


public class FilterProcedure<T> implements Procedure<T>
{
	private final Procedure<? super T> procedure;
	private final ObjectFilter<? super T> filter;

	public FilterProcedure(Procedure<? super T> procedure, ObjectFilter<? super T> filter)
	{
		this.procedure = procedure;
		this.filter = filter;
	}
	
	@Override
	public void execute(T object)
	{
		if (filter.valid(object))
			procedure.execute(object);	
	}

	public static <T> FilterProcedure<T> create(Procedure<? super T> procedure, ObjectFilter<? super T> filter) {
		return new FilterProcedure<T>(procedure, filter);
	}

	@Override
	public String toString()
	{
		return "{"+filter+" ? " + procedure + "}";
	}
}
