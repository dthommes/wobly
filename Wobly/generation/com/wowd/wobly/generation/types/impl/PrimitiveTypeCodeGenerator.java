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

import static com.wowd.wobly.generation.WoblyGeneratorUtils.*;

import java.lang.reflect.Type;

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.generation.CodeBuilder;
import com.wowd.wobly.generation.types.TypeCodeGenerator;

public class PrimitiveTypeCodeGenerator implements TypeCodeGenerator
{

	@Override
	public String appendWriteTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		Class<?> primitive = primitivize(clazz);
		if (primitive.equals(byte.class)) {
			if (special == Format.NUMBER_COMPRESSED)
				throw new UnsupportedOperationException("byte field cannot be compressed number (and no need for that)");
			result.println("buf.put(" + obj + ");");
		}
		else if (primitive.equals(boolean.class)) {
			if (special == Format.NUMBER_COMPRESSED)
				throw new UnsupportedOperationException("boolean field cannot be compressed number (and no need for that)");
			result.println("buf.put((byte) (" + obj + " ? 1 : 0));");
		}
		else if (primitive.isPrimitive()) {
			if (special == Format.NUMBER_COMPRESSED) {
				if (primitive.equals(long.class))
					result.println(BUFFERS + ".putVLong(buf," + obj + ");");
				else if (primitive.equals(float.class) || primitive.equals(double.class))
					throw new IllegalArgumentException("NUMBER_COMPRESSED is not allowed with floating point numbers (" + type +" " + obj +")");
				else result.println(BUFFERS + ".putVInt(buf," + obj + ");");
			}
			else 
			{
				String ptype = primitive.getSimpleName();
				String tmp = ptype.substring(0, 1).toUpperCase() + ptype.substring(1);
				result.println("buf.put" + tmp + "(" + obj + ");");				
			}			
		}
		else throw new IllegalArgumentException();
		return null;
	}

	@Override
	public String appendReadTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		Class<?> primitive = primitivize(clazz);

		if (primitive.equals(byte.class))
			result.println(obj + " = buf.get();");
		else if (primitive.equals(boolean.class))
			result.println(obj + " = (buf.get() == 1 ? true : false);");
		else if (primitive.isPrimitive()) {
			if (special == Format.NUMBER_COMPRESSED) {
				if (primitive.equals(int.class))
					result.println(obj + " = " + BUFFERS + ".getVInt(buf);");
				else if (primitive.equals(long.class))
					result.println(obj + " = " + BUFFERS + ".getVLong(buf);");
				else result.println(obj + " = ("+primitive.getSimpleName()+") " + BUFFERS + ".getVInt(buf);");				
			}
			else {
				String ptype = primitive.getSimpleName();
				String tmp = ptype.substring(0, 1).toUpperCase() + ptype.substring(1);
				result.println(obj + " = buf.get" + tmp + "();");
			}
		}
		else throw new IllegalArgumentException();
		return null;
	}
	
	@Override
	public void appendTypeSizeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		Class<?> primitive = primitivize(clazz);
		if (primitive.isPrimitive()) {
			if (special == Format.NUMBER_COMPRESSED) {
				if (primitive.equals(long.class))
					result.println("size += " + BUFFERS +".sizeVLong(" +obj + ");");
				else result.println("size += " + BUFFERS +".sizeVInt(" +obj + ");");
			}
			else
				result.println("size += " + getPrimitiveFormat(primitive).size() + ";");			
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
		return primitivize(clazz).isPrimitive();
	}

}
