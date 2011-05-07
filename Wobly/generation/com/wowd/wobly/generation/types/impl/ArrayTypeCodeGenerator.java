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

import static com.wowd.wobly.generation.WoblyCodeGenerator.*;
import static com.wowd.wobly.generation.WoblyGeneratorUtils.*;
import static com.wowd.wobly.generation.types.TypeCodeHandler.*;

import java.lang.reflect.Type;

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.generation.CodeBuilder;
import com.wowd.wobly.generation.types.TypeCodeGenerator;

public class ArrayTypeCodeGenerator implements TypeCodeGenerator
{

	@Override
	public String appendWriteTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		String sizeString = null;
		if (isArray(clazz)) {
			Type elType = extractNextType(type, 0);
			Object elGen = extractGenericForArray(componentsDetails, 0);								
			Format elFormat =  extractFormatForArray(componentsDetails, 0);
			
			int potentialStaticSize = calculateStaticSize(elType, elGen, elFormat);
			sizeString = appendWriteSizeOptimizedCode(result, potentialStaticSize, obj, of, true, depth);

			if (elType.equals(byte.class)) {
				result.println("buf.put(" + obj + ");");
			}
			else {
				String var = "v" + depth;
				result.println("for (" + toShortString(elType) + " " + var + " : " + obj + ") {");
				result.addTab();
				result.append(generateWriteTypeCode(elType, var, elGen, depth+1, elFormat, of).first());
				result.removeTab();
				result.println("}\n");
			}
		}
		else throw new IllegalArgumentException();
		return sizeString;
	}
	
	@Override
	public String appendReadTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		String typeName = clazz.getSimpleName();

		String sizeVariableName = null;
		if (isArray(clazz)) {
			Type elType = extractNextType(type, 0);
			Object elGen = extractGenericForArray(componentsDetails, 0);
			Format elFormat =  extractFormatForArray(componentsDetails, 0);
			String sizeVar = "size" + depth;
			int potentialStaticSize = calculateStaticSize(elType, elGen, elFormat);
			sizeVariableName = appendChangeSizeOptimizedCode(result, sizeVar, potentialStaticSize, obj, of, depth);

			result.println(obj + " = new " + typeName.replaceFirst("\\[\\]", "[" + sizeVar + "]") + ";");
			if (elType.equals(byte.class)) {
				result.println("buf.get("+obj+");");
			}
			else {
				String cntr = "i" + depth;
				result.println("for (int " + cntr + " = 0; " + cntr + " < " + sizeVar + "; " + cntr + "++) {");
				result.addTab();
				result.append(generateReadTypeCode(elType, obj + "[" + cntr + "]", elGen, depth+1, elFormat, of).first());
				result.removeTab();
				result.println("}");
			}
		}
		else throw new IllegalArgumentException();
		return sizeVariableName;
	}

	@Override
	public void appendTypeSizeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		if (isArray(clazz)) {			
			Type elType = extractNextType(type, 0);
			Object elGen = extractGenericForArray(componentsDetails, 0);
			Format elFormat =  extractFormatForArray(componentsDetails, 0);
			int potentialStaticSize = calculateStaticSize(elType, elGen, elFormat);
			
			if (!(potentialStaticSize > 0 && depth == 1)) 
				result.println(sizeSizeVarCode(obj, of, true));
			
			if (potentialStaticSize > 0) 
			{
				result.println("size += "+ multiplyString(obj+".length", potentialStaticSize) + ";");
			}
			else 
			{
				String var = "v" + depth;
				result.println("for (" + toShortString(elType) + " " + var + " : " + obj + ") {");
				result.addTab();
				result.append(generateTypeSizeCode(elType, var, elGen, depth+1, elFormat, of));
				result.removeTab();
				result.println("}");
			}
		}
		else throw new IllegalArgumentException();
	}
	
	@Override
	public Format defaultTypeFormat(Type type, Object componentsDetails)
	{
		Type elType = extractNextType(type, 0);
		Object elGen = extractGenericForArray(componentsDetails, 0);								
		Format elFormat =  extractFormatForArray(componentsDetails, 0);
		
		int potentialStaticSize = calculateStaticSize(elType, elGen, elFormat, false);
		if (potentialStaticSize > 0)
			return Format.BYTES_SIZE_COMPRESSED;
		
		return Format.BYTES;
	}
	
	@Override
	public boolean generatingFor(Class<?> clazz)
	{
		return isArray(clazz);
	}

}
