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
package com.wowd.wobly.generation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.wowd.common.WowdUtils;
import com.wowd.wobly.Wobly;
import com.wowd.wobly.WoblyUtils;
import com.wowd.wobly.WoblyUtils.Buffers;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.AbstractType;
import com.wowd.wobly.annotations.InlineSuperclass;
import com.wowd.wobly.annotations.ReadStatic;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;
import com.wowd.wobly.annotations.components.ComponentsDetails;
import com.wowd.wobly.annotations.components.ComponentsDetailsLvl2;
import com.wowd.wobly.annotations.components.ComponentsDetailsLvl3;
import com.wowd.wobly.generation.types.TypeCodeHandler;
import com.wowd.wobly.generation.types.impl.trove.TroveGeneratorUtils;
import com.wowd.wobly.generation.types.impl.tuple.TupleGeneratorUtils;

/**
*
* @author ikabiljo, relja.petrovic 
*
*/
public class WoblyGeneratorUtils
{
	public static final String BUFFERS = Buffers.class.getCanonicalName();
	public static final String BYTE_BUFFER = ByteBuffer.class.getCanonicalName();
	public static final String NULL_ARRAY_PREFIX = WoblyUtils.class.getCanonicalName()+".NULL_";
	
	// ------------- GENERICS -------------
	
	// ------------- GENERICS -------------
	
	public static Class<?> extractClass(Type t) {
		if (t instanceof Class<?>)
			return (Class<?>)t;
		else if (t instanceof ParameterizedType)
			return (Class<?>)((ParameterizedType) t).getRawType();
		else if (t instanceof GenericArrayType)
    	    return Array.newInstance(extractClass(((GenericArrayType)t).getGenericComponentType()), 0).getClass();
		else if (t instanceof TypeVariable<?>)
			return extractClass(((TypeVariable<?>)t).getBounds()[0]);			
		throw new IllegalArgumentException("extract class for " + t + ", " + t.getClass());
	}
	
	
	public static Type extractNextType(Type t, int index) {
		if (TroveGeneratorUtils.isTroveType(t))
			return TroveGeneratorUtils.extractNextType(t, index);
		if (t instanceof Class<?>)
			if (((Class<?>) t).isArray())
				return ((Class<?>) t).getComponentType();
			else throw new IllegalArgumentException("next type for " + t + " of index " + index+ ", " + t.getClass());
		else if (t instanceof ParameterizedType)
			return ((ParameterizedType) t).getActualTypeArguments()[index];
		else if (t instanceof GenericArrayType)			
			if (index==0)
				return ((GenericArrayType) t).getGenericComponentType();
		throw new IllegalArgumentException("next type for " + t + " of index " + index+ ", " + t.getClass());
	}
	
	public static String toShortGenericPart(Type[] types) {
		ArrayList<String> simple = new ArrayList<String>();
		for(Type t : types) 
			simple.add(toShortString(t));
		return "<"+WowdUtils.Wrapper.collectionToString(simple, ", ")+">";
	}
	
	public static String toShortGenericPart(Type type) {
		if (type instanceof ParameterizedType)
			return toShortGenericPart(((ParameterizedType) type).getActualTypeArguments());
		return "";
	}
	
	public static String toShortString(Type t) {
		if (t instanceof Class<?>)
			return ((Class<?>) t).getSimpleName();
		else if (t instanceof ParameterizedType)
			return extractClass(t).getSimpleName() + toShortGenericPart(((ParameterizedType) t).getActualTypeArguments());
		else if (t instanceof GenericArrayType) 
			return toShortString(((GenericArrayType) t).getGenericComponentType())+"[]";
		else if (t instanceof TypeVariable<?>)
			return ((TypeVariable<?>) t).getName();
		throw new IllegalArgumentException("to short string " + t + ", " + t.getClass());			
	}
	
	public static boolean isClassForAutomaticSerialization(Class<?> c)
	{
		return Wobly.class.isAssignableFrom(c) && notManualSerialization(c);
	}
	
	public static boolean isClassAbstract(Class<?> c) {
		 return Modifier.isAbstract(c.getModifiers());
	}
	
	// -----------------------------------
	
	
	
	
	
	@WoblyField(id=0,defaultValue="", componentsDetails=@ComponentsDetails())
	public static final Object emptyGenericDescription = new Object();
	
	public static ComponentsDetails getEmptyGenericDescription() {
		try
		{
			return WoblyGeneratorUtils.class.getDeclaredField("emptyGenericDescription").getAnnotation(WoblyField.class).componentsDetails();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public static Object extractComponentsDetails(Object desc, int position) {
		if (desc instanceof ComponentsDetails)		
		{
			ComponentsDetails d = ((ComponentsDetails) desc);
			if (d.componentsDetails().length<=position)
				return getEmptyGenericDescription();
			else return d.componentsDetails()[position];
		}
		else if (desc instanceof ComponentsDetailsLvl2)
		{
			ComponentsDetailsLvl2 d = ((ComponentsDetailsLvl2) desc);
			if (d.componentsDetails().length<=position)
				return getEmptyGenericDescription();
			else return d.componentsDetails()[position];
		}
		else if (desc instanceof ComponentsDetailsLvl3)
			return getEmptyGenericDescription();
		else throw new UnsupportedOperationException(desc.getClass().toString());
	}
	
	public static Format extractFormat(Object desc, int position) {
		if (desc instanceof ComponentsDetails)		
		{
			ComponentsDetails d = ((ComponentsDetails) desc);
			if (d.specialFormat().length<=position)
				return Format.DEFAULT;
			else return d.specialFormat()[position];
		}
		else if (desc instanceof ComponentsDetailsLvl2)
		{
			ComponentsDetailsLvl2 d = ((ComponentsDetailsLvl2) desc);
			if (d.specialFormat().length<=position)
				return Format.DEFAULT;
			else return d.specialFormat()[position];
		}
		else if (desc instanceof ComponentsDetailsLvl3)
		{
			ComponentsDetailsLvl3 d = ((ComponentsDetailsLvl3) desc);
			if (d.specialFormat().length<=position)
				return Format.DEFAULT;
			else return d.specialFormat()[position];
		}
		else throw new UnsupportedOperationException(desc.getClass().toString());
	}
	
	public static Object extractGenericForArray(Object desc, int position) {
		if (desc==null)
			return getEmptyGenericDescription();
		else return extractComponentsDetails(desc, position);
	}
	
	public static Format extractFormatForArray(Object desc, int position) {
		if (desc==null)
			return Format.DEFAULT;
		else return extractFormat(desc, position);
	}
	
	// -----------------------------------
	
	public static String getReadStaticName(Class<?> c) {
		Method m = WowdUtils.Reflection.getMethodWithAnotation(c, ReadStatic.class);
		if (m !=null)
			return m.getName();
		if (c.getAnnotation(InlineSuperclass.class)!=null)
			return getReadStaticName(c.getSuperclass());
		throw new UnsupportedOperationException(c + " needed @ReadStatic method. Try running Wobly code generation again, after recompile");
	}	
	
	public static String getIType(Class<?> clazz) {
		if (!isCollection(clazz) && !isMap(clazz) && !isSet(clazz))
			throw new UnsupportedOperationException(clazz.getName());
		if (isAbstract(clazz)) {
			if (isSet(clazz))
				return "java.util.HashSet";
			else if (isMap(clazz))
				return "java.util.HashMap";
			else
				return "java.util.ArrayList";
		} else {
			return clazz.getSimpleName();
		}
	}
	
	public static boolean isAbstract(Class<?> clazz) {		
		if (isSet(clazz))
			return clazz.equals(Set.class);
		
		if (isMap(clazz))
			return clazz.equals(Map.class);
		
		if (isList(clazz))
			return clazz.equals(List.class);
		
		if (isCollection(clazz))
			return clazz.equals(Collection.class);
		
		return false;
	}
	
	public static boolean isSet(Class<?> clazz) {
		return (Set.class.isAssignableFrom(TroveGeneratorUtils.getUntrovized(clazz)));
	}
	
	public static boolean isList(Class<?> clazz) {
		return (List.class.isAssignableFrom(TroveGeneratorUtils.getUntrovized(clazz)));
	}
	
	public static boolean isCollection(Class<?> clazz) {
		return (Collection.class.isAssignableFrom(TroveGeneratorUtils.getUntrovized(clazz)));
	}		
	
	public static boolean isMap(Class<?> clazz) {
		return (Map.class.isAssignableFrom(TroveGeneratorUtils.getUntrovized(clazz)));
	}
	
	public static boolean isArray(Class<?> clazz) {
		return clazz.isArray();
	}
	

	
	public static boolean shouldSetSize(Class<?> clazz, Format format, boolean required) {
		WoblyTypeOptions  options = getTypeAnnotation(clazz, WoblyTypeOptions.class);
		return format.size()<=0 && 
				( clazz.isArray() || isCollection(clazz) || isMap(clazz) 
				|| ((TupleGeneratorUtils.isTuple(clazz) || (format == Format.NO_SIZE_FIELD) || (options!=null && options.specialFormat() == Format.NO_SIZE_FIELD)) && !required));
	}
	
	public static Format adjusFormatForCompressed(Type type, Class<?> c, WoblyField of) {
		Format format = getFormat(c, of);
		
		if (of.specialFormat() != Format.DEFAULT)
			return format;		
		
		Object componentsDetails = of.componentsDetails();
		if (format == Format.DEFAULT)
			throw new IllegalArgumentException();
		
		if (format != Format.BYTES)
			return format;
			
		Class<?> clazz = extractClass(type);
		
		int size = calculateStaticSize(type, componentsDetails, format);
		if (size > 0) {
			switch (size)
			{
				case 1: return Format.FIXED1;
				case 2: return Format.FIXED2;
				case 4: return Format.FIXED4;
				case 8: return Format.FIXED8;
					
				default: return Format.BYTES_SIZE_COMPRESSED;
			}
					
		}

		return TypeCodeHandler.defaultTypeFormat(type, clazz, componentsDetails);
	}
	
	public static int getStaticSize(Class<?> clazz, Format format) {
		if (format == Format.DEFAULT) 
			format = getFormat(clazz);
		if (format != null && format.size() > 0) 
			return format.size();
		return -1;
	}

	public static int calculateStaticSize(Type t, Object componentsDetails, Format special) {
		return calculateStaticSize(t, componentsDetails, special, true);
	}
	public static int calculateStaticSize(Type t, Object componentsDetails, Format special, boolean firstCall) {
		Class<?> clazz = extractClass(t);
		
		WoblyTypeOptions options = getTypeAnnotation(clazz, WoblyTypeOptions.class);

		{
			int size = getStaticSize(clazz, special);
			if (size > 0) 
				return size;
		}
		if (TupleGeneratorUtils.isTuple(clazz)){
			Type firstType = extractNextType(t, 0);
			int firstSize = calculateStaticSize(firstType, extractComponentsDetails(componentsDetails, 0), extractFormat(componentsDetails, 0), false);
			if (firstSize <= 0) 
				return -1;
			
			if (TupleGeneratorUtils.isPair(clazz)) {
				Type secondType = extractNextType(t, 1);
				int secondSize = calculateStaticSize(secondType, extractComponentsDetails(componentsDetails, 1), extractFormat(componentsDetails, 1), false);
				if (secondSize <= 0)
					return -1;
				
				if (TupleGeneratorUtils.isTriple(clazz)) {
					Type thirdType = extractNextType(t, 2);
					int thirdSize = calculateStaticSize(thirdType, extractComponentsDetails(componentsDetails, 2), extractFormat(componentsDetails, 2), false);
					if (thirdSize <= 0)
						return -1;
					else return firstSize + secondSize + thirdSize;
				}
				else return firstSize + secondSize;
			}
			else return firstSize;
		}
		else {
			if (!isClassForAutomaticSerialization(clazz))
				return -1;
			if (isClassAbstract(clazz))
				return -1;
			
			if (options != null && options.unmodifiable()) {
				int size = 0;
				if (! firstCall)
				{
					if (options.specialFormat() == Format.BYTES_SIZE_COMPRESSED) 
						return -1;
					else if (!(options.specialFormat() == Format.NO_SIZE_FIELD)) {
						size += 4;
					}
				}
				AbstractType abstractType = getTypeAnnotation(clazz, AbstractType.class);
				if (abstractType != null)
					size ++;
				
				ArrayList<Field> fields = getFields(clazz);
				for (Field field : fields) {
					WoblyField a = field.getAnnotation(WoblyField.class);
					Type typeF = field.getGenericType();
					Class<?> clazzF = field.getType();
					Format format = adjusFormatForCompressed(typeF, clazzF, a);

					if (a.required()) {
						int cur = calculateStaticSize(typeF, a.componentsDetails(), format, false);
						if (cur <= 0)
							return -1;
						size += cur;
					}
					else return -1;
				}
				return size;
			}
			return -1;
		}			
	}
	
	
	public static Class<?> primitivize(Class<?> clazz) {
		if (clazz.isPrimitive())
			return clazz;
		try
		{
			Field field = clazz.getDeclaredField("TYPE");
			Object toReturn = field.get(null);
			if (toReturn instanceof Class<?>)
			{
				if (((Class<?>) toReturn).isPrimitive())
					return ((Class<?>) toReturn);
				else return clazz;
			}
			else return clazz;			
		}
		catch (Throwable e)
		{
			return clazz;
		}		
	}
	
	public static Format getPrimitiveFormat(Class<?> clazz) {
		if (clazz.equals(byte.class) || clazz.equals(boolean.class))
			return Format.FIXED1;
		else if (clazz.equals(short.class) || clazz.equals(char.class))
			return Format.FIXED2;
		else if (clazz.equals(int.class) || clazz.equals(float.class))
			return Format.FIXED4;
		else if (clazz.equals(long.class) || clazz.equals(double.class))
			return Format.FIXED8;	
		throw new UnsupportedOperationException("get primitve format with " + clazz);
	}
	
	public static Format getFormat(Class<?> clazz) {
		clazz = primitivize(clazz);
		if (clazz.isPrimitive())
			return getPrimitiveFormat(clazz);	
		else if (clazz.isEnum())
			return Format.NUMBER_COMPRESSED;
		else {
			WoblyTypeOptions special = getTypeAnnotation(clazz, WoblyTypeOptions.class);
			if (special!=null)
				return special.specialFormat();
			else return Format.BYTES;
		}	
	}
	
	public static Format getFormat(Class<?> clazz, WoblyField ann) {
		Format f = getFormat(clazz);
		if (ann.specialFormat()==Format.DEFAULT) 
			return f;
		else if (f == null || f == Format.BYTES) 			
			return ann.specialFormat();
		else if (ann.specialFormat()!=f && !primitivize(clazz).isPrimitive() && !clazz.isEnum()
				&& (!(f== Format.NO_SIZE_FIELD && ann.specialFormat()==Format.BYTES_SIZE_COMPRESSED)))
			System.err.println("Warning - different format for field of type " + clazz + " : " + ann.specialFormat() + "!=" + f);
		return ann.specialFormat();
	}

	
	private static String recheckWriteString(Class<?> c, WoblyField a, String fieldName) {
		String defValue = defaultValueWrite(a, c);
		if (c.isPrimitive())
			return fieldName + " != " + defValue;
		else if (defValue.isEmpty()) {
			if ((isMap(c) || isCollection(c) || isSet(c))  && a.collectionNullToEmpty())
				return "(" + fieldName + " != null) && (" + fieldName + ".size() > 0)";
			else if (c.isArray() && a.arrayNullToEmpty())
				return "(" + fieldName + " != null) && (" + fieldName + ".length > 0)";
			else return  fieldName + " != null";
		}
		else if (defValue.equals("null"))
			return fieldName + " != null";
		else if (c.equals(String.class)) 
			return "(" + fieldName + " != null) && (!" + fieldName + ".equals(" + defValue + "))";
		else return "(" + fieldName + " != null) && (" + fieldName + " != " + defValue + ")";
		
	}
	
	public static String recheckWriteLine(Class<?> c, WoblyField a, String fieldName) {
		return "if (" +recheckWriteString(c, a, fieldName) + ") {";
	}
	
	
	public static String defaultValueCreate(WoblyField a, Class<?> c , String genericDesc) {
		if (a.defaultValue().isEmpty())
		{
			if (!c.isPrimitive())
			{
				if ((isMap(c) || isCollection(c) || isSet(c)) && a.collectionNullToEmpty()) {
					String type =  getIType(c);
					if (type.contains("Hash") || type.contains("Array"))
						return "new " + type + genericDesc+"(1)";
					else return "new " + type + genericDesc+"()";
				}
				else if (isArray(c) && a.arrayNullToEmpty()) {
					Class<?> component = c.getComponentType();
					if (component.isPrimitive())
						return NULL_ARRAY_PREFIX + component.getSimpleName().toUpperCase(Locale.ENGLISH) + "S";
					else if (component.equals(String.class))
						return NULL_ARRAY_PREFIX + "STRINGS";
					else return "new " + c.getComponentType().getSimpleName() + "[0]";
				}				
				else return "null";
			}
			else if (c.equals(boolean.class))
			{
				return "false";
			}
			else if (c.equals(byte.class) || c.equals(short.class))
				return ("(("+c.getSimpleName()+")0)");
			else return "0";
		}
		else return a.defaultValue();
	}

	public static String defaultValueWrite(WoblyField a, Class<?> c) {
		if (a.defaultValue().isEmpty())
		{
			if (!c.isPrimitive())
			{
				return "";
			}
			else if (c.equals(boolean.class))
			{
				return "false";
			}
			else return "0";
		}
		else return a.defaultValue();
	}

	
	public static <T extends Annotation> T getTypeAnnotation(Class<?> clazz, Class<T> annotation)
	{
		if (clazz.getAnnotation(annotation) != null)
			return clazz.getAnnotation(annotation);
		if (clazz.getAnnotation(InlineSuperclass.class) != null)
			return getTypeAnnotation(clazz.getSuperclass(), annotation);
		else return null;
	}

	public static Field getFieldWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation)
	{
		Field field = WowdUtils.Reflection.getFieldWithAnotation(clazz, annotation);
		if (field != null)
			return field;
		if (clazz.getAnnotation(InlineSuperclass.class) != null)
			return getFieldWithAnnotation(clazz.getSuperclass(), annotation);
		else return null;
	}

	public static boolean noSizeField(Class<?> clazz) {
		WoblyTypeOptions specFormat = getTypeAnnotation(clazz, WoblyTypeOptions.class);
		
		return (specFormat!=null && specFormat.specialFormat()==Format.NO_SIZE_FIELD);
	}

	public static boolean compressedSizeField(Class<?> clazz) {
		WoblyTypeOptions specFormat = getTypeAnnotation(clazz, WoblyTypeOptions.class);
		
		return (specFormat!=null && specFormat.specialFormat()==Format.BYTES_SIZE_COMPRESSED);
	}

	public static boolean notManualSerialization(Class<?> clazz) {
		WoblyTypeOptions specFormat = getTypeAnnotation(clazz, WoblyTypeOptions.class);
		if (specFormat == null)
			return true;
		if (specFormat.manualSerializatoin())
			return false;
		return true;
//		boolean rez = specFormat.specialFormat()==Format.DEFAULT || specFormat.specialFormat() == Format.BYTES_SIZE_COMPRESSED;
//		if (!rez)
//			System.err.println(clazz);
//		return rez;
	}

	public static String multiplyString(String left, int right) {
		if (right == 1)
			return left;
		if (right <= 0)
			throw new IllegalArgumentException();
		return left + " * " + right;
	}


	private static void addFields(Class<?> clazz, ArrayList<Field> fields) {
		for (Field f : clazz.getDeclaredFields())
			if (f.getAnnotation(WoblyField.class) != null)
				fields.add(f);
		if (clazz.getAnnotation(InlineSuperclass.class)!=null)
			addFields(clazz.getSuperclass(), fields);
	}
	
	public static ArrayList<Field> getFields(Class<?> clazz) {
		ArrayList<Field> fields = new ArrayList<Field>();
		addFields(clazz, fields);
		Collections.sort(fields, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2)
			{
				WoblyField ai = o1.getAnnotation(WoblyField.class);
				WoblyField aj = o2.getAnnotation(WoblyField.class);
				int dr = (ai.required() ? 1 : 0) - (aj.required() ? 1 : 0);
				if (dr != 0)
					return -dr;
				return WowdUtils.Common.compare(ai.id(), aj.id());
			}
		});

		for (int i = 0; i < fields.size() - 1; i++)
			for (int j = i + 1; j < fields.size(); j++)
			{
					WoblyField ai = fields.get(i).getAnnotation(WoblyField.class);
					WoblyField aj = fields.get(j).getAnnotation(WoblyField.class);
					if (ai.id() == aj.id() && ai.required() == aj.required())
						throw new UnsupportedOperationException("Two fields with same ID!" + fields.get(i) + "\t"
								+ fields.get(j));
					if (ai.id() < 0 && !ai.required())
						throw new UnsupportedOperationException("Field id is negative (for non-required field) : " + fields.get(i));
					if (aj.id() < 0 && !aj.required())
						throw new UnsupportedOperationException("Field id is negative (for non-required field) : " + fields.get(j));
			}
		return fields;
	}
}
