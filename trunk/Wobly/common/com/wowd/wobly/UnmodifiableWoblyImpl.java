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

import com.wowd.wobly.exceptions.WoblyException;

/**
 * Common abstract class for all objects that have unmodifiable serialization structure, 
 * that can be written to byte[] and read from it.
 * <br>
 * 
 * @author ikabiljo
 * @see Wobly, WoblyGenerator
 *
 */
public abstract class UnmodifiableWoblyImpl implements Wobly
{
	@Override
	public int getSize()
	{
		throw new WoblyException("Missing getSize implementation, should wobly run code generation");
	}
	
	@Override
	public void write(ByteBuffer buf)
	{
		throw new WoblyException("Missing write implementation, should wobly run code generation");
	}
	
	public byte[] toByteArray()
	{
		ByteBuffer buf = toByteBuffer();
		if (buf.limit()==buf.capacity())
			return buf.array();
		else return WoblyUtils.Buffers.copyOf(buf.array(), buf.limit());
	}

	public final ByteBuffer toByteBuffer()
	{
		ByteBuffer buf = ByteBuffer.allocate(getSize());
		write(buf);
		buf.flip();
		return buf;
	}

	public byte typeID() {
		return -1;
	}
	
}
