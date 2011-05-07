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
package com.wowd.wobly.updater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.wowd.common.functions.ObjectFilter;
import com.wowd.common.functions.Procedure;
import com.wowd.common.functions.impl.FilterProcedure;
import com.wowd.common.objects.tuples.Triple;
import com.wowd.wobly.WoblyUtils;
import com.wowd.wobly.annotations.AbstractType;
import com.wowd.wobly.annotations.InlineSuperclass;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;
import com.wowd.wobly.generation.WoblyCodeGenerator;
import com.wowd.wobly.generation.WoblyGeneratorUtils;

/**
 * 
 * @author ikabiljo, marsavic
 *
 */
public class GenerateAndReplace {
	static {
		System.out.println("Some source files are going to be changed, don't forget to rebuild.\n");
	}
	

//	/**
//	 * Updates serialization code for the class from which this method was called. 
//	 */
//	public static void update() {
//		try {
//			StackTraceElement e[] = Thread.currentThread().getStackTrace();
//			update(Class.forName(e[e.length - 1].getClassName()), "src", false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	/**
	 * Updates serialization code for specified class. 
	 */
	public static void update(Class<?> c, String sourceFolder, boolean clear) throws Exception {
		String filePath = classToPath(c, sourceFolder);
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		ArrayList<String> newContent = new ArrayList<String>();
		
		boolean inSerializationCode = false;
		boolean hasObjectField = false;
		boolean hadSerializationCode = false;
		boolean error = false;
		boolean hasInheritanceHandler = false;
		boolean hasInlineSuperclass = false;
		boolean hasAbstractType = false;
		boolean hasSpecialFormat = false;
		
		String line;
		while ((line = in.readLine()) != null) {
			if (line.contains("--- AUTO GENERATED CODE FOR SERIALIZATION ---")
					|| line.contains("--- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ---")) {
				inSerializationCode = true;
				hadSerializationCode = true;
				
				if (hasObjectField || hasInlineSuperclass || hasAbstractType || hasSpecialFormat) {
					try {
						String[] serialization = clear?WoblyUtils.NULL_STRINGS:WoblyCodeGenerator.generateCode(c).split("\n");

						for (String s : serialization)
							newContent.add("\t" + s);
					} catch (Exception e) {
						e.printStackTrace();
						error = true;
					}
				}
			}

			if (!inSerializationCode)
				newContent.add(line);
			
			if (line.contains("--- END OF AUTO GENERATED CODE FOR SERIALIZATION ---"))
				inSerializationCode = false;
			
			if (line.contains("@"+WoblyField.class.getSimpleName()))
				hasObjectField = true;
			
			if (line.contains("private static class WoblyInheritanceHandler"))
				hasInheritanceHandler = true;

			if (line.contains("@"+InlineSuperclass.class.getSimpleName()))
				hasInlineSuperclass = true;
			
			if (line.contains("@"+WoblyTypeOptions.class.getSimpleName()))
				hasSpecialFormat = true;

			if (line.contains("@"+AbstractType.class.getSimpleName()))
				hasAbstractType = true;
			
		}
		
		in.close();

		if (!hadSerializationCode && (hasObjectField || hasInlineSuperclass || hasAbstractType || hasSpecialFormat) && !hasInheritanceHandler) {
				try {
					String[] serialization = clear?WoblyUtils.NULL_STRINGS:WoblyCodeGenerator.generateCode(c).split("\n");
					int lastLine = 0;
					for (int i = newContent.size() - 1; i >= 0; i--)
						if (newContent.get(i).equals("}")) {
							lastLine = i;
							break;
						}

					for (int i = 0; i < serialization.length;i++)
						newContent.add(i + lastLine, "\t" + serialization[i]);

				} catch (Exception e) {
					e.printStackTrace();
					error = true;
				}
			
		}
		
		System.out.format("    %-40s", c.getSimpleName());

		if (error)
			System.out.print("error");
		else
			if (hadSerializationCode) {
				writeNewContent(filePath, newContent);
				System.out.print("updated");
			}
			else
//				if (hasManualSerialization)
//					System.out.println("manual serialization");
//				else 
					if (!(hasObjectField || hasInlineSuperclass || hasAbstractType || hasSpecialFormat))
						System.out.print("not serializable");
					else {
						writeNewContent(filePath, newContent);
						System.out.print("generated");
					}
		System.out.println();
	}


	private static void writeNewContent(String filePath, ArrayList<String> newContent) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		
		for (String s : newContent) {
			out.write(s);
			out.write("\n");
		}
		
		out.close();
	}


	static String classToPath(Class<?> c, String sourceFolder) {
		return sourceFolder + "/" + c.getCanonicalName().replaceAll("\\.", "/") + ".java";
	}
	
	
	public static void updateInPackage(String sourceFolder, String packageName, final boolean clear) {
		SourceFilesVisitor.visitPackage(sourceFolder, packageName, 
				FilterProcedure.create(new Procedure<Triple<Class<?>, String, File>>() {

					@Override
					public void execute(Triple<Class<?>, String, File> t)
					{
						try
						{
							update(t.first(), t.second(), clear);
						}
						catch (Throwable e)
						{
							e.printStackTrace();
						}
					}}
				, new ObjectFilter<Triple<Class<?>, String, File>>() {
					
					File last = null;
					@Override
					public boolean valid(Triple<Class<?>, String, File> t)
					{
						Class<?> c = t.first();
						if (WoblyGeneratorUtils.isClassForAutomaticSerialization(c)) {
							if (last != t.third()) {
								System.out.println("Scanning " + t.third().getPath());
								last = t.third();
							}
							return true;
						}
						return false;
					}
				}));
		
	}
}
