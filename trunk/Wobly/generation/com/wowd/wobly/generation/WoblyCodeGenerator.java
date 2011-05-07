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


import static com.wowd.wobly.generation.WoblyGeneratorUtils.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

import com.wowd.common.WowdUtils;
import com.wowd.common.objects.tuples.Pair;
import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.WoblyReader;
import com.wowd.wobly.WoblyReaderImpl;
import com.wowd.wobly.WoblyUtils;
import com.wowd.wobly.WoblyUtils.Buffers;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.AbstractType;
import com.wowd.wobly.annotations.InlineSuperclass;
import com.wowd.wobly.annotations.ReadStatic;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;
import com.wowd.wobly.annotations.code.AfterConstructor;
import com.wowd.wobly.annotations.code.AfterConstructorCode;
import com.wowd.wobly.annotations.code.BeforeConstructor;
import com.wowd.wobly.annotations.code.BeforeConstructorCode;
import com.wowd.wobly.annotations.code.BeforeWrite;
import com.wowd.wobly.exceptions.WoblyReadException;
import com.wowd.wobly.exceptions.WoblyWriteException;
import com.wowd.wobly.generation.types.TypeCodeHandler;
import com.wowd.wobly.inheritance.WoblyCompressedReaderHandler;
import com.wowd.wobly.inheritance.WoblyReaderHandler;
import com.wowd.wobly.unknown.UnknownField;


/**
 *
 * @author ikabiljo, relja.petrovic 
 *
 */
public class WoblyCodeGenerator
{
	private static final String UTILITES = WoblyUtils.class.getCanonicalName();
	private static final String MAIN_EXCEPTION = Throwable.class.getCanonicalName();
	private static final String OBJECT_READER = WoblyReader.class.getCanonicalName();
	private static final String OBJECT_READER_IMPL = WoblyReaderImpl.class.getCanonicalName();
	private static final String UNKNOWN_FIELD = UnknownField.class.getCanonicalName();
	private static final String READ_STATIC = ReadStatic.class.getCanonicalName();
	
	private static final String UNKNOWN_FIELD_LINE = "private " + ArrayList.class.getName() + "<" + UNKNOWN_FIELD +"> unknownFields;";
	private static final ArrayList<String> UNKNOWN_METHODS= new ArrayList<String>(); 
	

	static {
		
		CodeBuilder m1 = new CodeBuilder();
		m1.println("private void addToUnknown(" + UNKNOWN_FIELD +" field) {");
		m1.addTab();
		m1.println("if (unknownFields == null)");
		m1.addTab();
		m1.println("unknownFields = new " + ArrayList.class.getName() + "<" + UNKNOWN_FIELD +">();");
		m1.removeTab();
		m1.println("unknownFields.add(field);");
		m1.removeTab();
		m1.println("}");
		UNKNOWN_METHODS.add(m1.toString());
		
		CodeBuilder m2 = new CodeBuilder();
		m2.println("private int readUnknownsUpTo(int current, int target, " + BYTE_BUFFER +" buf) {");
		m2.addTab();
		m2.println("while ("+UTILITES+".getIDFromTag(current)<target && buf.hasRemaining()) {");
		m2.addTab();
		m2.println("addToUnknown(" + UNKNOWN_FIELD +".read(buf, current));");
		m2.println("current = "+BUFFERS+".getVInt(buf);");
		m2.removeTab();
		m2.println("}");
		m2.println("if (!buf.hasRemaining())");
		m2.addTab();
		m2.println("return Integer.MAX_VALUE;");
		m2.removeTab();
		m2.println("return current;");
		m2.removeTab();
		m2.println("}");
		UNKNOWN_METHODS.add(m2.toString());

//		CodeBuilder m3 = new CodeBuilder();
//		m3.println("private int writeUnknownsUpTo(int unknownsCounter, int target, " + BYTE_BUFFER +" buf) {");
//		m3.addTab();
//		m3.println("while (unknownsCounter < target && unknownsCounter < unknownFields.size()) {");
//		m3.addTab();
//		m3.println("" + UNKNOWN_FIELD +" next = unknownFields.get(unknownsCounter);");
//		m3.println("if ("+UTILITES+".getIDFromTag(next.getTag())>=target)");
//		m3.addTab();
//		m3.println("return unknownsCounter;");
//		m3.removeTab();
//		m3.println("next.write(buf);");
//		m3.println("unknownsCounter++;");
//		m3.removeTab();
//		m3.println("}");
//		m3.println("return unknownsCounter;");
//		m3.removeTab();
//		m3.println("}");
//		UNKNOWN_METHODS.add(m3.toString());
		
	}
	

	private static String generateObjectReaderField(Class<?> c) {
		CodeBuilder result = new CodeBuilder();
		String cName = c.getSimpleName();
		if (c.getTypeParameters().length >0)
			result.println("@SuppressWarnings(\"unchecked\")");
		result.println("public static final "+OBJECT_READER+"<"+cName+"> objectReader = new "+OBJECT_READER_IMPL+"<"+cName+">() {");
		result.addTab();
		result.println("@Override");
		result.println("public "+cName+" readObject("+ BYTE_BUFFER + " buf)");
		result.println("{");
		result.addTab();
		result.println("return read(buf);");
		result.removeTab();
		String typeID = getTypeIDString(c);
		if (!typeID.equals("-1"))
		{
			result.println("}");
			result.println("@Override");
			result.println("public int targetTypeID()");
			result.println("{");
			result.addTab();
			result.println("return 255&" + typeID + ";");
			result.removeTab();
		}
		result.println("}};");
		
		return result.toString();
	}


	
	private static void putVInt(CodeBuilder result, int value) {
		while ((value & ~0x7F) != 0) {
			result.println("buf.put((byte)"+((byte) ((value & 0x7F) | 0x80))+");");		
			value >>>= 7;
		}
		result.println("buf.put((byte)"+value+");");		
	}
	
	
	private static String generateWriteFieldCode(Field f, Class<?> clazz) {
		CodeBuilder result = new CodeBuilder();
		
		WoblyField a = f.getAnnotation(WoblyField.class);
		Type t = f.getGenericType();
		Class<?> c = f.getType();
		
		String fieldName = "this."+f.getName();

		String recheckWriteLine = recheckWriteLine(c, a, fieldName); 
		Format format = adjusFormatForCompressed(t, c, a);

		if (!a.required()) {
			result.println();
			result.println("unknownsCounter = " + writeUnknowns(a.id()) + ";");
			result.println(recheckWriteLine);
			result.addTab();
			
			putVInt(result, WoblyUtils.makeTag(a.id(), format.realFormat()));
		}
		else {
			result.println("{");
			result.addTab();
		}
		
		boolean shouldSetSize = shouldSetSize(c, format, a.required());
		boolean compressedSize = format==Format.BYTES_SIZE_COMPRESSED;

		Pair<String, String> generated = TypeCodeHandler.generateWriteTypeCode(t, fieldName, a.componentsDetails(), 1, format, a);
		String writeCode = generated.first();
		String sizeCode = generated.second();
		
		if (shouldSetSize) 
		{
			if (sizeCode != null) 
			{
				if (compressedSize)
					result.println(BUFFERS + ".putVInt(buf, " + sizeCode +");");
				else {
					result.println("buf.putInt(" + sizeCode + ");");
					System.out.println("In class " + clazz.getSimpleName() + " field " + f.getName() + " could have compressed size with no penalty (if class is not already being used in cloud)");
				}
				result.append(writeCode);
			}
			else
			{
				result.println("int startFieldMark = buf.position();");

				if (compressedSize)
				{
					result.println("buf.position(buf.position()+1);");
					result.append(writeCode);
					result.println(BUFFERS + ".appendVariableSize(buf, startFieldMark);");
				}
				else
				{
					result.println("buf.position(buf.position()+4);");
					result.append(writeCode);
					result.println("buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);");
				}
			}
		}
		else result.append(writeCode);
			
		result.removeTab();
		result.println("}");
			
		return result.toString();
	}
	
	private static String generateWriteMethod(Class<?> clazz, ArrayList<Field> fields, boolean unmodifiable) {		
		CodeBuilder result = new CodeBuilder();
		result.println("@Override");
		result.println("public void write(final " + BYTE_BUFFER +" buf) {");
	
		result.addTab();
		result.println("try {");

		result.addTab();
		
		Method before = WowdUtils.Reflection.getMethodWithAnotation(clazz, BeforeWrite.class);
		if (before!=null)
			result.println(before.getName()+"();");
		
		boolean compressedSize = compressedSizeField(clazz);
		if (!noSizeField(clazz))
		{
			result.println("int startPositionMark = buf.position();");
			if (compressedSize)
				result.println("buf.position(buf.position()+1);");
			else result.println("buf.position(buf.position()+4);");
		}
		
		if (getTypeAnnotation(clazz, AbstractType.class) != null)
			result.println("buf.put(typeID());");
		if (!unmodifiable) {
			result.println("int unknownsCounter = 0;");
			result.println("if (unknownFields == null)");
			result.addTab().println("unknownsCounter = Integer.MAX_VALUE;").removeTab();
		}

		for (Field f : fields)
			result.append(generateWriteFieldCode(f, clazz));
		
		if (!unmodifiable) 
			result.println(writeUnknowns("Integer.MAX_VALUE")+";");
		
		if (!noSizeField(clazz)) {
			if (compressedSize)
				result.println(BUFFERS + ".appendVariableSize(buf, startPositionMark);");
			else result.println("buf.putInt(startPositionMark, buf.position() - startPositionMark - 4);");
		}
		result.removeTab();
		
		result.println("} catch (" + WoblyWriteException.class.getName() + " e) {");
		result.addTab();
		result.println("throw e;");
		result.removeTab();
		result.println("} catch (" + MAIN_EXCEPTION + " t) {");
		result.addTab();
		result.println("throw new " + WoblyWriteException.class.getName() + "(t);");
		result.removeTab();
		result.println("}");
		result.removeTab();
		
		result.println("}");
		
		return result.toString();
	}
	
	private static String writeUnknowns(Object upTo) {
		return "writeUnknownsUpTo(unknownsCounter, " + upTo + ", buf)";
	}
	
	
	
	private static String generateReadFieldCode(Field f) {
		Type t = f.getGenericType();
		Class<?> c = f.getType();
		WoblyField a = f.getAnnotation(WoblyField.class);
		String fieldName = "this." + f.getName();
		Object componentsDetails = a.componentsDetails();
		CodeBuilder result = new CodeBuilder();
		
		if (!a.required()) {
			result.println("tag = readUnknownsUpTo(tag, " + a.id() + ", buf);");
			result.println("if ("+UTILITES+".getIDFromTag(tag) > " + a.id() +")");
			result.addTab();
			String createString = defaultValueCreate(a, c, toShortGenericPart(t));
			result.println(fieldName + " = " + createString + ";");
			result.removeTab();
			result.println("else {");
			
			result.addTab();
		}
		else {
			result.println("{");			
			result.addTab();
		}
 
		Format format = adjusFormatForCompressed(t, c, a);

		boolean shouldSetSize = shouldSetSize(c, format, a.required());
		boolean compressedSize = format==Format.BYTES_SIZE_COMPRESSED;

		Pair<String, String> generated = TypeCodeHandler.generateReadTypeCode(t, fieldName, componentsDetails, 1, format, a);
		String readCode = generated.first();
		String sizeVariableName = generated.second();
		
		if (shouldSetSize) {
			String prefix;
			if (sizeVariableName != null)
				prefix = "int " + sizeVariableName + " = ";
			else prefix = "";
			
			if (compressedSize) 
				result.println(prefix + BUFFERS + ".getVInt(buf); //read size");
			else result.println(prefix + "buf.getInt(); //read size");
		}
		
		result.append(readCode);

		if (!a.required()) {
			result.println("tag = "+BUFFERS+".getVIntOrMax(buf);");
		}
		result.removeTab();
		result.println("}");
		return result.toString();
	}
	
	private static String generateConstructor(Class<?> c, ArrayList<Field> fields, boolean unmodifiable) {
		CodeBuilder result = new CodeBuilder();
		
		result.println("private " + c.getSimpleName() + "(final " + BYTE_BUFFER +" buf) {\n");				
		result.addTab();
		BeforeConstructorCode beforeCode = getTypeAnnotation(c, BeforeConstructorCode.class);
		if (beforeCode!=null && beforeCode.value().length>0)
			for (String line : beforeCode.value())
				result.printlnWithTabs(line);
		Method before = WowdUtils.Reflection.getMethodWithAnotation(c, BeforeConstructor.class);
		if (before!=null)
			result.println(before.getName()+"();");
		
		if (getTypeAnnotation(c, AbstractType.class) != null) {
			result.println("if(buf.get() != typeID())");
			result.addTab();
			result.println("throw new " + WoblyReadException.class.getName() + "();");
			result.removeTab();
		}
		
		boolean firstNonRequired = true;
		
		for (Field f : fields) {
			if (!f.getAnnotation(WoblyField.class).required() && firstNonRequired) {
				result.println("int tag = "+BUFFERS+".getVIntOrMax(buf);");
				firstNonRequired = false;
			}
			result.append("\n" + generateReadFieldCode(f) + "\n");
		}
		
		if (!unmodifiable) {
			if (firstNonRequired)
				result.println("int tag = "+BUFFERS+".getVIntOrMax(buf);");
			result.println("readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);");
		}
		
		Method after = WowdUtils.Reflection.getMethodWithAnotation(c, AfterConstructor.class);
		if (after!=null)
			result.println(after.getName()+"();");
		AfterConstructorCode afterCode = getTypeAnnotation(c, AfterConstructorCode.class);
		if (afterCode!=null && afterCode.value().length>0)
			for (String line : afterCode.value())
				result.printlnWithTabs(line);
		result.removeTab();
		result.println("}");
		return result.toString();
	}
	
	
	// Generate read static method
	private static String generateReadStatic(Class<?> c, boolean unmodifiable) {
		CodeBuilder result = new CodeBuilder();
		
		result.println("@" + READ_STATIC);
		result.println("public static " + c.getSimpleName() + " read(" + BYTE_BUFFER +" buf) {");
		result.addTab();
		result.println("try {");
		result.addTab();
		boolean compressedSize = compressedSizeField(c);
		if (!noSizeField(c)) {
			if (compressedSize)
				result.println("int size = " + BUFFERS + ".getVInt(buf);");
			else result.println("int size = buf.getInt();");
			result.println("int originalLimit = buf.limit();");
			result.println("int newLimit = buf.position() + size;");
			result.println("if (newLimit > originalLimit)");
			result.addTab();
			result.println("throw new " + WoblyReadException.class.getName() + "(newLimit + \" \" + originalLimit);");
			result.removeTab();
			result.println("buf.limit(newLimit);");
		}
		else if (!unmodifiable) 
			throw new UnsupportedOperationException("unsupported autogenerating class " + c);
		result.println(c.getSimpleName() + " object = new " + c.getSimpleName() + "(buf);");
		if (!noSizeField(c))
			result.println("buf.limit(originalLimit);");
		result.println("return object;");
		result.removeTab();
		result.println("} catch (" + WoblyReadException.class.getName() + " e) {");
		result.addTab();
		result.println("throw e;");
		result.removeTab();
		result.println("} catch (" + MAIN_EXCEPTION + " t) {");
		result.addTab();
		result.println("throw new " + WoblyReadException.class.getName() + "(t);");
		result.removeTab();
		result.println("}");
		result.removeTab();
		result.println("}");
		return result.toString();
	}
	
	// Generate read static method
	private static String generateReadStaticFromBytes(Class<?> c) {
		CodeBuilder result = new CodeBuilder();
		
		result.println("public static " + c.getSimpleName() + " read(byte[] buf) {");
		result.addTab();
		result.println("return read(" + BYTE_BUFFER +".wrap(buf));");
		result.removeTab();
		result.println("}");
		return result.toString();
	}

	
	// Generate getSize() method
	private static String generateGetSize(Class<?> c, ArrayList<Field> fields, boolean unmodifiable) {
		CodeBuilder result = new CodeBuilder();
		result.println("@Override");
		result.println("public int getSize() {");
		result.addTab();
		result.println("int size = 0;");
		if (getTypeAnnotation(c, AbstractType.class) != null)
			result.println("size += 1;");

		for (Field f : fields)
			result.append(generateGetFieldSizeCode(f));

		if (!unmodifiable) {
			result.println("if (unknownFields != null)");
			result.addTab();
			result.println("for (" + UNKNOWN_FIELD +" uf : unknownFields)");
			result.addTab();
			result.println("size += uf.getSize();");
			result.removeTab();
			result.removeTab();
		}
		if (!noSizeField(c)) {
			if (compressedSizeField(c))
				result.println("size += " + BUFFERS + ".sizeVInt(size);");
			else result.println("size += 4;");
		}
		result.println("return size;");
		result.removeTab();
		result.println("}");
		return result.toString();
	}
	

	
	
	private static String generateGetFieldSizeCode(Field f) {
		CodeBuilder result = new CodeBuilder();
		
		Type t = f.getGenericType();
		Class<?> c = f.getType();
		WoblyField a = f.getAnnotation(WoblyField.class);
		String fieldName = "this."+f.getName();
		
		Format format = adjusFormatForCompressed(t, c, a);

		if (!a.required()) {
			result.println(recheckWriteLine(c, a, fieldName));
			result.addTab();		
			result.println("size += " + Buffers.sizeVInt(WoblyUtils.makeTag(a.id(), format.realFormat())) + ";");
		}
		else {
			result.println("{");
			result.addTab();			
		}
				
		boolean shouldSetSize = shouldSetSize(c, format, a.required());
		boolean compressedSize = format==Format.BYTES_SIZE_COMPRESSED;

		String sizeCode = TypeCodeHandler.generateTypeSizeCode(t, fieldName, a.componentsDetails(), 1, format, a);
		
		if (shouldSetSize) 
			if (compressedSize)
				result.println("int helpSize = size;");
			else result.println("size += 4;");
			
		if (format.size()>0)
			result.println("size += "+format.size()+";");
		else result.append(sizeCode);
		
		if (shouldSetSize) 
			if (compressedSize)
				result.println("size += "+BUFFERS + ".sizeVInt(size-helpSize);");
		
//		if (!a.required()) {
		result.removeTab();
		result.println("}");
//		}
		
		return result.toString();
	}
	
	private static String getTypeIDString(Class<?> c) {
		if (getTypeAnnotation(c, AbstractType.class) != null) {
			byte typeID = getTypeAnnotation(c, InlineSuperclass.class).value();
			if (typeID ==-1)
				throw new UnsupportedOperationException("you must declare @TypeID for " + c + "!!");
			return "" + typeID;
		}
		else
			return "-1";
	}
	
	private static String generateTypeID(Class<?> c) {
		CodeBuilder res = new CodeBuilder();
		res.println("@Override");
		res.println("public byte typeID() {");
		res.addTab();
		res.println("return "+getTypeIDString(c)+";");
		res.removeTab();
		res.println("}");
		return res.toString();
	}
	

	public static String appendWriteSizeOptimizedCode(CodeBuilder result, int potentialStaticSize, String obj, WoblyField of, boolean array, int depth) {
		String sizeString = null;
		String size;
		if (array)
			size = ".length";
		else size = ".size()";
		if (potentialStaticSize > 0)  
		{
			if (depth == 1)
				sizeString = multiplyString(obj + size, potentialStaticSize);
			else {
				sizeString = multiplyString(obj + size, potentialStaticSize)
						+ " + " + sizeStringSizeVarCode(obj, of, array);
				result.println(writeSizeVarCode(obj, of, array));
			}
		}
		else result.println(writeSizeVarCode(obj, of, array));
		return sizeString;
	}
	
	public static String appendChangeSizeOptimizedCode(CodeBuilder result, String sizeVar, int potentialStaticSize, String obj, WoblyField of, int depth) {
		String sizeVariableName = null;
		if (potentialStaticSize > 0 && depth == 1) {
			sizeVariableName = sizeVar;
			if (potentialStaticSize > 1)
			{
				result.println("if (" + sizeVar + " % " + potentialStaticSize +" != 0)");
				result.addTab();
				result.println("throw new "+WoblyReadException.class.getName()+"(\""+obj +" wanted to have size \" + "+sizeVar + " + \"/" + potentialStaticSize + "\");");
				result.removeTab();
				result.println(sizeVar + " /= " + potentialStaticSize + ";");
			}
		}			
		else result.println(readSizeVarCode(sizeVar, of));
		return sizeVariableName;
	}
	
	private static String readSizeVarCode(String sizeVar, WoblyField of) {
		return "int " + sizeVar + " = " + BUFFERS + ".getVInt(buf);";
	}
	private static String writeSizeVarCode(String obj, WoblyField of, boolean array) {
		String size;
		if (array)
			size = ".length";
		else size = ".size()";
		return BUFFERS + ".putVInt(buf, "+obj + size + ");";
	}
	public static String sizeSizeVarCode(String obj, WoblyField of, boolean array) {
		return "size += "+sizeStringSizeVarCode(obj, of, array)+";"; 
	}
	
	private static String sizeStringSizeVarCode(String obj, WoblyField of, boolean array) {
		String size;
		if (array)
			size = ".length";
		else size = ".size()";
		return BUFFERS + ".sizeVInt("+obj + size + ")";
	}
	


	
	public static Pair<ArrayList<String>, ArrayList<String>> generateFieldsAndMethods(Class<?> c) {
		// fetch fields for writing and sort them by id
		
		ArrayList<Field> fields = getFields(c);
		
		boolean isWoblyImpl = WoblyImpl.class.isAssignableFrom(c);
				
		WoblyTypeOptions options = getTypeAnnotation(c, WoblyTypeOptions.class);
		boolean unmodifiable = options != null && options.unmodifiable();
		if (unmodifiable 
				&& (fields.size()<=0 || !fields.get(fields.size()-1).getAnnotation(WoblyField.class).required()))
			throw new UnsupportedOperationException("Unmodifiable class with non-required fields");
		
		ArrayList<String> methodStrings = new ArrayList<String>();
		
		if (!isWoblyImpl && !unmodifiable)
			methodStrings.addAll(UNKNOWN_METHODS);
		methodStrings.add(generateWriteMethod(c, fields, unmodifiable));
		methodStrings.add(generateConstructor(c, fields, unmodifiable));
		methodStrings.add(generateReadStatic(c, unmodifiable));
		methodStrings.add(generateReadStaticFromBytes(c));
		methodStrings.add(generateGetSize(c, fields, unmodifiable));
		if (getTypeAnnotation(c, AbstractType.class) != null)
			methodStrings.add(generateTypeID(c));
		
		
		ArrayList<String> fieldStrings = new ArrayList<String>();
		
		fieldStrings.add(generateObjectReaderField(c));
		if (!isWoblyImpl && !unmodifiable)
			fieldStrings.add(UNKNOWN_FIELD_LINE);
		return new Pair<ArrayList<String>, ArrayList<String>>(fieldStrings, methodStrings);
	}
	
	
	public static String generateCode(Class<?> c) {
		if (!isClassForAutomaticSerialization(c))
			throw new UnsupportedOperationException("Class " + c + " doesn't support autogeneration");
		checkClassHierarchy(c);
		
		CodeBuilder result = new CodeBuilder();
		result.println("//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------");
		result.println("//---------------------------------------------------------------------");
		result.println();
		if (isClassAbstract(c))
			generateAbstractCode(c, result);
		else 
			generateRegularCode(c, result);
		result.println();
		result.println("//---------------------------------------------------------------------");
		result.println("//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------");
		return result.toString();
	}



	private static void checkClassHierarchy(Class<?> c)
	{
		InlineSuperclass inline = c.getAnnotation(InlineSuperclass.class);
		
		boolean foundAbstract = false;
		Class<?> parent = c.getSuperclass();
		while (parent != Object.class && parent != null)
		{
			AbstractType aType = parent.getAnnotation(AbstractType.class);
			if (aType != null) {
				foundAbstract = true;
				if (inline == null)
					throw new UnsupportedOperationException("Missing " + InlineSuperclass.class.getSimpleName());
				
				boolean hasMeAsChild = false;
				for (Class<?> child : aType.value())
					if (child == c)
						hasMeAsChild = true;
				
				if (!hasMeAsChild)
					System.err.println("Warning : " + c + " not in child type list in " + parent);
			}
			
			parent = parent.getSuperclass();
		} 
		
		if (inline != null && !foundAbstract) 
			throw new UnsupportedOperationException("Missing " + AbstractType.class.getSimpleName() + " on some parent class");
		
	}



	public static void generateRegularCode(Class<?> c, CodeBuilder result)
	{
		Pair<ArrayList<String>, ArrayList<String>> generated = generateFieldsAndMethods(c);
		for(String s : generated.first())
			result.append(s);
		for(String s : generated.second())
			result.append(s);
	}

	public static void generateAbstractCode(Class<?> c, CodeBuilder result) {
		AbstractType aType = c.getAnnotation(AbstractType.class);
		if (aType == null)
			return ;
		
		result.println("protected static class WoblyInheritanceHandler");
		result.println("{");
		result.addTab();
		
		boolean compressed = compressedSizeField(c);
		String readerName = compressed
				?WoblyCompressedReaderHandler.class.getCanonicalName()
				:WoblyReaderHandler.class.getCanonicalName();
				
		String readerString = readerName +"<"+c.getSimpleName()+">";
		
		result.println("static final " + readerString+" readers = new " + readerString +"();");
		result.println();
		result.println("static");
		result.println("{");
		result.addTab();
		result.println("try");
		result.println("{");
		result.addTab();
		
		HashSet<Integer> ids = new HashSet<Integer>();
		for (Class<?> child : aType.value()) {
			InlineSuperclass i = getTypeAnnotation(child, InlineSuperclass.class);
			if (i==null)
				throw new UnsupportedOperationException("Child " + child + " doesn't have @" + InlineSuperclass.class.getSimpleName());
			int id = i.value();
			if (!ids.add(id))
				throw new UnsupportedOperationException("Multiple childs have typeID = " + id);
			
			result.println("readers.register("+child.getCanonicalName()+".objectReader);");
		}
		result.removeTab();
		result.println("}");
		result.println("catch ("+MAIN_EXCEPTION+" t)");
		result.println("{");
		result.addTab();
		result.println("t.printStackTrace();");
		result.removeTab();
		result.println("}");
		result.removeTab();
		result.println("}");
		result.removeTab();
		result.println("}");
		result.println();
		result.println("@SuppressWarnings(\"unchecked\")");
		result.println("@" + READ_STATIC);
		result.println("public static <WX extends "+c.getSimpleName()+"> WX read"+c.getSimpleName()+"("+BYTE_BUFFER+" buf) {");
		result.addTab();
		result.println("return (WX)WoblyInheritanceHandler.readers.read(buf);");
		result.removeTab();
		result.println("}");

	}
	
}
