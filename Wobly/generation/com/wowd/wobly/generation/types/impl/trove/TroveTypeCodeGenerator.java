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

import static com.wowd.wobly.generation.WoblyCodeGenerator.*;
import static com.wowd.wobly.generation.WoblyGeneratorUtils.*;
import static com.wowd.wobly.generation.types.TypeCodeHandler.*;

import java.lang.reflect.Type;

import com.wowd.common.WowdUtils;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.generation.CodeBuilder;
import com.wowd.wobly.generation.types.TypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.CollectionTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.MapTypeCodeGenerator;

public class TroveTypeCodeGenerator implements TypeCodeGenerator
{

	@Override
	public String appendWriteTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		String sizeString = null;
		if (TroveGeneratorUtils.isTroveClass(clazz)) {
			Class<?> troveClazz = TroveGeneratorUtils.getUntrovized(clazz);
			if (isCollection(troveClazz)) {
				Type elType = extractNextType(type, 0);
				Object elGen = extractComponentsDetails(componentsDetails, 0);
				Format elFormat =  extractFormat(componentsDetails, 0);

				int potentialStaticSize = calculateStaticSize(elType, elGen, elFormat);
				sizeString = appendWriteSizeOptimizedCode(result, potentialStaticSize, obj, of, false, depth);

				String var = "v" + depth;
				Class<?> procedureClazz = WowdUtils.Reflection.getMethodWithName(clazz, "forEach").getParameterTypes()[0];
				
				result.println(obj + ".forEach(new " + procedureClazz.getName() + toShortGenericPart(type) + "() {");
				result.addTab();
				result.println("@Override");
				result.println("public boolean execute(" + extractClass(elType).getName()+ " " +var+") {");				
				result.addTab();
				result.append(generateWriteTypeCode(elType, var, elGen, depth+1, elFormat, of).first());
				result.println("return true;");
				result.removeTab();
				result.println("}");
				result.removeTab();
				result.println("});");	
			}
			else if (isMap(troveClazz)) {
				Type keyType = extractNextType(type, 0);
				Type valType = extractNextType(type, 1);
				
				Object keyGen = extractComponentsDetails(componentsDetails, 0);
				Object valueGen = extractComponentsDetails(componentsDetails, 1);
				Format keyFormat =  extractFormat(componentsDetails, 0);
				Format valueFormat =  extractFormat(componentsDetails, 1);
				
				int keyPotentialStaticSize = calculateStaticSize(keyType, keyGen, keyFormat);
				int valuePotentialStaticSize = calculateStaticSize(valType, valueGen, valueFormat);
				int potentialStaticSize;
				if (keyPotentialStaticSize > 0 && valuePotentialStaticSize > 0) 
					potentialStaticSize = keyPotentialStaticSize + valuePotentialStaticSize;
				else potentialStaticSize = -1;
				
				sizeString = appendWriteSizeOptimizedCode(result, potentialStaticSize, obj, of, false, depth);
					
				String var = "v" + depth;
				String varKey = var + "key";
				String varValue = var + "value";
				Class<?> procedureClazz = WowdUtils.Reflection.getMethodWithName(clazz, "forEachEntry").getParameterTypes()[0];
				
				result.println(obj + ".forEachEntry(new " + procedureClazz.getName() + toShortGenericPart(type) + "() {");
				result.addTab();
				result.println("@Override");
				result.println("public boolean execute("+extractClass(keyType).getName()+ " " + varKey + ", " +extractClass(valType).getName()+ " " +varValue+") {");				
				result.addTab();
				result.append(generateWriteTypeCode(keyType, varKey, keyGen, depth+1, keyFormat, of).first());
				result.append(generateWriteTypeCode(valType, varValue, valueGen, depth+100, valueFormat, of).first());
				result.println("return true;");
				result.removeTab();
				result.println("}");
				result.removeTab();
				result.println("});");			
			}
		}
		else throw new IllegalArgumentException();
		return sizeString;
	}

	@Override
	public String appendReadTypeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		String sizeVariableName = null;
		if (isCollection(clazz)) {			
			sizeVariableName = CollectionTypeCodeGenerator.appendReadTypeCodeImpl(result, type, clazz, obj, componentsDetails, of, depth);
		} else if (isMap(clazz)) {
			sizeVariableName = MapTypeCodeGenerator.appendReadTypeCodeImpl(result, type, clazz, obj, componentsDetails, of, depth);
		} else throw new IllegalArgumentException();
		return sizeVariableName;
	}

	@Override
	public void appendTypeSizeCode(CodeBuilder result, Type type, Class<?> clazz, String obj, Object componentsDetails,
			Format special, WoblyField of, int depth)
	{
		if (TroveGeneratorUtils.isTroveClass(clazz)) {
			Class<?> troveClazz = TroveGeneratorUtils.getUntrovized(clazz);
			if (isCollection(troveClazz)) {
				Type elType = extractNextType(type, 0);
				Object elGen = extractComponentsDetails(componentsDetails, 0);
				Format elFormat =  extractFormat(componentsDetails, 0);
				int potentialStaticSize = calculateStaticSize(elType, elGen, elFormat);
				
				if (!(potentialStaticSize > 0 && depth == 1)) 
					result.println(sizeSizeVarCode(obj, of, false));

				if (potentialStaticSize > 0) {
					result.println("size += "+multiplyString(obj+".size()", potentialStaticSize) + ";");
				}
				else {
					String iter = "iter" + depth;
					String var = "v" + depth;
					
					Class<?> iteratorClazz = WowdUtils.Reflection.getMethodWithName(clazz, "iterator").getReturnType();
					
					result.println("for (" + iteratorClazz.getName() + toShortGenericPart(type) + " " + iter + " = " + obj + ".iterator();" + iter +".hasNext();) {"); 							
					result.addTab();
					result.append(toShortString(elType) + " " + var + " = " + iter +".next();");
					result.append(generateTypeSizeCode(elType, iter +".value()", elGen, depth+1, elFormat, of));
					result.removeTab();
					result.println("}");
				}
			}
			else if (isMap(troveClazz)) {
				Type keyType = extractNextType(type, 0);
				Type valType = extractNextType(type, 1);
				
				Object keyGen = extractComponentsDetails(componentsDetails, 0);
				Object valueGen = extractComponentsDetails(componentsDetails, 1);
				Format keyFormat =  extractFormat(componentsDetails, 0);
				Format valueFormat =  extractFormat(componentsDetails, 1);
				
				int keyPotentialStaticSize = calculateStaticSize(keyType, keyGen, keyFormat);
				int valuePotentialStaticSize = calculateStaticSize(valType, valueGen, valueFormat);
				
				if (!(keyPotentialStaticSize > 0 && valuePotentialStaticSize > 0 && depth == 1)) 
					result.println(sizeSizeVarCode(obj, of, false));
				
				if (keyPotentialStaticSize > 0 && valuePotentialStaticSize > 0) {
					result.println("size += "+multiplyString(obj+".size()", keyPotentialStaticSize + valuePotentialStaticSize) + ";");
				}
				else {
					String iter = "iter" + depth;
					Class<?> iteratorClazz = WowdUtils.Reflection.getMethodWithName(clazz, "iterator").getReturnType();
					result.println("for (" + iteratorClazz.getName() + toShortGenericPart(type) + " " + iter + " = " + obj + ".iterator();" + iter +".hasNext();) {"); 							

					result.addTab();
					result.append(iter + ".advance();");
					result.append(generateTypeSizeCode(keyType, iter + ".key()", keyGen, depth+1, keyFormat, of));
					result.append(generateTypeSizeCode(valType, iter + ".value()", valueGen, depth+100, valueFormat, of));
					result.removeTab();
					result.println("}");
				}
			}
		}
		else throw new IllegalArgumentException();
	}

	@Override
	public Format defaultTypeFormat(Type type, Object componentsDetails)
	{
		return null;
	}
	
	@Override
	public boolean generatingFor(Class<?> clazz)
	{
		return TroveGeneratorUtils.isTroveClass(clazz);
	}

}
