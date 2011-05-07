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
package com.wowd.wobly.generation.types.impl;

import static com.wowd.wobly.generation.WoblyGeneratorUtils.BUFFERS;

import java.lang.reflect.Type;

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.generation.CodeBuilder;
import com.wowd.wobly.generation.types.TypeCodeGenerator;

public class EnumTypeCodeGenerator implements TypeCodeGenerator
{

	@Override
	public String appendWriteTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		if (clazz.isEnum()) {
			result.println(BUFFERS + ".putVInt(buf," + obj + ".ordinal());");
		}
		else throw new IllegalArgumentException();
		return null;
	}

	@Override
	public String appendReadTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		String typeName = clazz.getSimpleName();
		if (typeName.contains("$")) {
			typeName = clazz.getCanonicalName();
		}
		if (clazz.isEnum()) {
			result.println(obj + " = " + typeName + ".values()["+ BUFFERS + ".getVInt(buf)];");
		}
		else throw new IllegalArgumentException();
		return null;
	}
	
	@Override
	public void appendTypeSizeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		if (clazz.isEnum()) {
			result.println("size += " + BUFFERS +".sizeVInt(" +obj + ".ordinal());");
		} 
		else throw new IllegalArgumentException();
	}
	
	@Override
	public Format defaultTypeFormat(Type type, Object componentsDetails)
	{
		return Format.BYTES;
	}
	
	@Override
	public boolean generatingFor(Class<?> clazz)
	{
		return clazz.isEnum();
	}

}
