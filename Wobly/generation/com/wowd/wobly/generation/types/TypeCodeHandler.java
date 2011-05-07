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
package com.wowd.wobly.generation.types;


import static com.wowd.wobly.generation.WoblyGeneratorUtils.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.wowd.common.objects.tuples.Pair;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.generation.CodeBuilder;
import com.wowd.wobly.generation.types.impl.ArrayTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.CollectionTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.EnumTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.MapTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.PrimitiveTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.StringTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.WoblyTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.trove.TroveTypeCodeGenerator;
import com.wowd.wobly.generation.types.impl.tuple.TupleTypeCodeGenerator;

public class TypeCodeHandler
{
	private static final ArrayList<TypeCodeGenerator> typeGenerators;
	
	static {
		typeGenerators = new ArrayList<TypeCodeGenerator>();
		typeGenerators.add(new PrimitiveTypeCodeGenerator());
		typeGenerators.add(new EnumTypeCodeGenerator());
		typeGenerators.add(new StringTypeCodeGenerator());
		typeGenerators.add(new ArrayTypeCodeGenerator());
		typeGenerators.add(new TroveTypeCodeGenerator());
		typeGenerators.add(new CollectionTypeCodeGenerator());
		typeGenerators.add(new MapTypeCodeGenerator());
		typeGenerators.add(new TupleTypeCodeGenerator());
		typeGenerators.add(new WoblyTypeCodeGenerator());
	}
	

	// Generate write method
	public static Pair<String, String> generateWriteTypeCode(Type type, String obj, Object componentsDetails, int depth, Format special, WoblyField of) {
		Class<?> clazz = extractClass(type);
		CodeBuilder result = new CodeBuilder();
		String sizeString = null;
		
		int size = calculateStaticSize(type, componentsDetails, special);
		if (size > 0)
			sizeString = String.valueOf(size);		

		for (TypeCodeGenerator gen : typeGenerators) {
			if (gen.generatingFor(clazz)) {
				String curSizeString = gen.appendWriteTypeCode(result, type, clazz, obj, componentsDetails, special, of, depth);
				if (curSizeString != null)
					sizeString = curSizeString;
				return new Pair<String, String>(result.toString(), sizeString);
			}
		}
		throw new IllegalArgumentException("Couldn't find type generator for field " + type + " " + obj);
	}
	
	// Generate read method, in constructor
	public static Pair<String, String> generateReadTypeCode(Type type, String obj, Object componentsDetails, int depth, Format special, WoblyField of) {
		Class<?> clazz = extractClass(type);
		String sizeVariableName = null;
		
		CodeBuilder result = new CodeBuilder();

		for (TypeCodeGenerator gen : typeGenerators) {
			if (gen.generatingFor(clazz)) {
				String curSizeVariableName = gen.appendReadTypeCode(result, type, clazz, obj, componentsDetails, special, of, depth);
				if (curSizeVariableName != null)
					sizeVariableName = curSizeVariableName;
				return new Pair<String, String>(result.toString(), sizeVariableName);
			}
		}
		throw new IllegalArgumentException("Couldn't find type generator for field " + type + " " + obj);
	}

	
	public static String generateTypeSizeCode(Type type, String obj, Object componentsDetails, int depth, Format special, WoblyField of) {
		Class<?> clazz = extractClass(type);
		CodeBuilder result = new CodeBuilder();
		
		int size = calculateStaticSize(type, componentsDetails, special);
		if (size > 0) {
			result.println("size += " + size +";");
			return result.toString();
		} else {
			for (TypeCodeGenerator gen : typeGenerators) {
				if (gen.generatingFor(clazz)) {
					gen.appendTypeSizeCode(result, type, clazz, obj, componentsDetails, special, of, depth);
					return result.toString();
				}
			}
			throw new IllegalArgumentException("Couldn't find type generator for field " + type + " " + obj);
		}
	}

	public static Format defaultTypeFormat(Type type, Class<?> clazz, Object componentsDetails) {
		for (TypeCodeGenerator gen : typeGenerators)
			if (gen.generatingFor(clazz)) { 
				Format format = gen.defaultTypeFormat(type, componentsDetails);
				if (format != null)
					return format;
			}
			
		return Format.BYTES;
	}

}
