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

import static com.wowd.wobly.generation.WoblyGeneratorUtils.*;
import static com.wowd.wobly.generation.types.TypeCodeHandler.*;
import static com.wowd.wobly.generation.types.impl.tuple.TupleGeneratorUtils.*;

import java.lang.reflect.Type;

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.generation.CodeBuilder;
import com.wowd.wobly.generation.types.TypeCodeGenerator;

public class TupleTypeCodeGenerator implements TypeCodeGenerator
{

	@Override
	public String appendWriteTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		if (isTuple(clazz)){
			Type firstType = extractNextType(type, 0);
			result.append(generateWriteTypeCode(firstType, obj + ".first()", extractComponentsDetails(componentsDetails, 0), depth+1, extractFormat(componentsDetails, 0), of).first());
			
			if (isPair(clazz)) {
				Type secondType =extractNextType(type, 1);
				result.append(generateWriteTypeCode(secondType, obj + ".second()", extractComponentsDetails(componentsDetails, 1), depth+100, extractFormat(componentsDetails, 1), of).first());
				
				if (isTriple(clazz)) {
					Type thirdType = extractNextType(type, 2);
					result.append(generateWriteTypeCode(thirdType, obj + ".third()", extractComponentsDetails(componentsDetails, 2), depth+10000, extractFormat(componentsDetails, 2), of).first());					
				}
			}
		} 
		else throw new IllegalArgumentException();
		return null;
	}

	@Override
	public String appendReadTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		if (isTuple(clazz)){
			String tmpFirst = "tmpFirst" + depth;
			String tmpSecond = "tmpSecond" + depth;
			String tmpThird = "tmpThird" + depth;


			Type firstType = extractNextType(type, 0);
			Object firstGenDesc = extractComponentsDetails(componentsDetails, 0);
			result.println(toShortString(firstType) + " " + tmpFirst + ";");
			result.append(generateReadTypeCode(firstType, tmpFirst, firstGenDesc, depth+1, extractFormat(componentsDetails, 0), of).first());
			
			if (isPair(clazz)) {
				Type secondType = extractNextType(type, 1);
				Object secondGenDesc = extractComponentsDetails(componentsDetails, 1);
				result.println(toShortString(secondType) + " " + tmpSecond + ";");
				result.append(generateReadTypeCode(secondType, tmpSecond, secondGenDesc, depth+100, extractFormat(componentsDetails, 1), of).first());
				
				if (isTriple(clazz)) {
					Type thirdType = extractNextType(type, 2);
					Object thirdGenDesc = extractComponentsDetails(componentsDetails, 2);
					result.println(toShortString(thirdType) + " " + tmpThird + ";");
					result.append(generateReadTypeCode(thirdType, tmpThird, thirdGenDesc, depth+10000, extractFormat(componentsDetails, 2), of).first());
				}
			}
			
			if (isTriple(clazz)) 
				result.println(obj + " = new " + toShortString(type) + "(" + tmpFirst + ", " + tmpSecond + ", " + tmpThird + ");");
			else if (isPair(clazz))
				result.println(obj + " = new " + toShortString(type) + "(" + tmpFirst + ", " + tmpSecond + ");");
			else
				result.println(obj + " = new " + toShortString(type) + "(" + tmpFirst + ");");
		} 
		else throw new IllegalArgumentException();
		return null;
	}
	
	@Override
	public void appendTypeSizeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		if (isTuple(clazz)){
			Type firstType = extractNextType(type, 0);
			result.append(generateTypeSizeCode(firstType, obj + ".first()", extractComponentsDetails(componentsDetails, 0), depth+1, extractFormat(componentsDetails, 0), of));
			
			if (isPair(clazz)) {
				Type secondType = extractNextType(type, 1);
				result.append(generateTypeSizeCode(secondType, obj + ".second()", extractComponentsDetails(componentsDetails, 1), depth+100, extractFormat(componentsDetails, 1), of));
				
				if (isTriple(clazz)) {
					Type thirdType = extractNextType(type, 2);
					result.append(generateTypeSizeCode(thirdType, obj + ".third()", extractComponentsDetails(componentsDetails, 2), depth+10000, extractFormat(componentsDetails, 2), of));
				}
			}
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
		return isTuple(clazz);
	}

}
