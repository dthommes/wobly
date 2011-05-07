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

public class CodeBuilder
{

	private final StringBuilder sb;
	
	private final String tabsString = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
	
	private final int initialIdentation;
	private int tabs;
	
	
	public CodeBuilder() {
		sb = new StringBuilder(1000);
		initialIdentation = 0;
		tabs = initialIdentation;
	}
	
	public CodeBuilder(CodeBuilder sourceForIdentation) {
		sb = new StringBuilder(1000);
		initialIdentation = sourceForIdentation.tabs;
		tabs = initialIdentation;
	}
	
	public CodeBuilder(int initialIdentation) {
		sb = new StringBuilder(1000);
		this.initialIdentation = initialIdentation;
		tabs = this.initialIdentation;
	}
	
	public CodeBuilder addTab() {
		tabs++;
		return this;
	}
	
	public CodeBuilder removeTab() {
		tabs--;
		if (tabs<initialIdentation)
			throw new UnsupportedOperationException("wrong number of remove tabs");
		return this;
	}

	public CodeBuilder println() {
		sb.append("\n");
		return this;
	}
	
	public CodeBuilder printlnWithTabs(String s) {
		sb.append(tabsString.substring(0, tabs)).append(s).append("\n");
		return this;
	}
	
	public CodeBuilder println(String s) {
		printlnWithTabs(s.trim());
		return this;
	}
	
	public CodeBuilder append(String s) {
		String[] ss = s.split("\n");
		for(String line : ss) 
			printlnWithTabs(line);
		return this;
	}
	
	@Override
	public String toString()
	{
		return sb.toString();
	}
}
