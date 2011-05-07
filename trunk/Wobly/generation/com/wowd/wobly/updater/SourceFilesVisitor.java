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

import java.io.File;
import java.io.FileFilter;

import com.wowd.common.functions.Procedure;
import com.wowd.common.objects.tuples.Triple;

/**
 * 
 * @author ikabiljo, marsavic
 *
 */
public class SourceFilesVisitor
{
	/**
	 * Visits all classes in specified package and its sub-packages.
	 */
	public static void visitPackage(String sourceFolder, String packageName, Procedure<? super Triple<Class<?>, String, File>> procedure) {
		visitDir(sourceFolder, new File(sourceFolder + "/" + packageName.replaceAll("\\.", "/")), procedure);
	}
	
	
	static FileFilter dirFilter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory() && !pathname.getName().endsWith(".svn");
		}
	};

	
	static FileFilter javaFilter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".java");
		}
	};

	
	private static void visitDir(String baseDir, File currentDir, Procedure<? super Triple<Class<?>, String, File>> procedure) {
		for (File dir : currentDir.listFiles(dirFilter))
			visitDir(baseDir, dir, procedure);
		
		for (File javaFile : currentDir.listFiles(javaFilter)) {
			try {
				String className = javaFile.getPath();
				className = className.substring(baseDir.length() + 1, className.length() - 5);
				className = className.replaceAll("\\\\", "/");
				className = className.replaceAll("/", ".");
				Class<?> c = Class.forName(className);
				procedure.execute(new Triple<Class<?>, String, File>(c, baseDir, currentDir));				
			} catch (Throwable e) {
				System.err.println(javaFile);
//				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
