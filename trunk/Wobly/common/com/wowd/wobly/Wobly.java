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

import java.nio.ByteBuffer;


/**
 * Interface for all objects that can be written to byte[] and read from it.
 * <br>
 * Note: all methods from this interface can be automatically generated
 * via WoblyGenerator.
 * 
 * @author ikabiljo
 * @see WoblyImpl, WoblyGenerator
 *
 */
public interface Wobly {

	/** Creates byte[] representation of this object */
	public byte[] toByteArray();

	/** Writes object to buffer, should throw WoblyWriteException if fails */
	public void write(ByteBuffer buf);

	/**
	 * Creates ByteBuffer filled with current object, ready to be read.
	 */
	public ByteBuffer toByteBuffer();

	/** Exact size of this object when serialized */
	public int getSize();
	

	/** 
	 * Returns typeID that represents this object, or -1 if this isn't used <br>
	 * When reading/writting, this byte must be first one (if it is not -1).
	 */
	public byte typeID();

}
