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
package com.wowd.wobly.examples.inheritance.simple;

import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.AbstractType;
import com.wowd.wobly.annotations.WoblyField;

@AbstractType({Child1.class, Child2.class, Child3.class})
public abstract class Parent extends WoblyImpl
{
	@WoblyField(id = -1, required = true)
	protected int commonRequiredField;

	@WoblyField(id = 0)
	protected int commonField;

	
	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	protected static class WoblyInheritanceHandler
	{
		static final com.wowd.wobly.inheritance.WoblyReaderHandler<Parent> readers = new com.wowd.wobly.inheritance.WoblyReaderHandler<Parent>();
	
		static
		{
			try
			{
				readers.register(com.wowd.wobly.examples.inheritance.simple.Child1.objectReader);
				readers.register(com.wowd.wobly.examples.inheritance.simple.Child2.objectReader);
				readers.register(com.wowd.wobly.examples.inheritance.simple.Child3.objectReader);
			}
			catch (java.lang.Throwable t)
			{
				t.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@com.wowd.wobly.annotations.ReadStatic
	public static <WX extends Parent> WX readParent(java.nio.ByteBuffer buf) {
		return (WX)WoblyInheritanceHandler.readers.read(buf);
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
