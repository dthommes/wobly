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

package com.wowd.wobly;

import com.wowd.wobly.updater.GenerateAndReplace;


/**
 * Main class for Wobly generating serialization code 
 * (Wowd's byte-level serialization protocol)
 * <br><br>
 * Notes:<br>
 * - all classes that should be serialized, 
 * need to be in the classpath when calling update<br>
 * - if you have serialization dependencies in your code, 
 * you might need to repeat calling update (and rebuilding the project). 
 * This is mostly needed only the first time when dependencies are created. 
 * Once you don't have compile errors, 
 * that should mean everything was generated properly.
 * 
 * @author ikabiljo, relja.petrovic
 */
public class WoblyGenerator
{
	/**
	 * Traverses all files in given sourceFolder and 
	 * generates serialization code for all applicable classes.
	 * 
	 * @param sourceFolder Path (relative or absolute) to source folder
	 */
	public static void updateSourceFolder(String sourceFolder) {
		updatePackage(sourceFolder, "");
	}
	
	/**
	 * Traverses all files from given sourceFolder that are in given package,
	 * and generates serialization code for all applicable classes.
	 * 
	 * @param sourceFolder Path (relative or absolute) to source folder
	 * @param packageName name of the package  
	 */
	public static void updatePackage(String sourceFolder, String packageName) {
		GenerateAndReplace.updateInPackage(sourceFolder, packageName, false);
	}

	public static void clearSourceFolder(String sourceFolder) {
		clearPackage(sourceFolder, "");
	}

	public static void clearPackage(String sourceFolder, String packageName) {
		GenerateAndReplace.updateInPackage(sourceFolder, packageName, true);
	}
	
	public static void main(String[] args)
	{
		String sourceFolder = "";
		String packageName = "";
		if (args.length > 0) {
			sourceFolder = args[0];
			if (args.length > 1) {
				packageName = args[1];
			}
		}
		else System.out.println("You must specify path to the source folder");
		updatePackage(sourceFolder, packageName);
			                 
	}
}
