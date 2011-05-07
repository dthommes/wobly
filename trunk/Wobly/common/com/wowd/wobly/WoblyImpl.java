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
import java.util.ArrayList;

import com.wowd.wobly.WoblyUtils.Buffers;
import com.wowd.wobly.exceptions.WoblyException;
import com.wowd.wobly.unknown.UnknownField;

/**
 * Common class for all objects that can be written to byte[] and read from it.
 * <br>
 * 
 * @author ikabiljo
 * @see Wobly, WoblyGenerator
 *
 */
public class WoblyImpl implements Wobly
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
	
	
	public final byte[] toByteArray()
	{
		ByteBuffer buf = toByteBuffer();
		if (buf.limit()==buf.capacity())
			return buf.array();
		else return WoblyUtils.Buffers.copyOf(buf.array(), buf.limit());
	}

	public final ByteBuffer toByteBuffer()
	{
		try {
			ByteBuffer buf = ByteBuffer.allocate(getSize());
			write(buf);
			buf.flip();
			return buf;
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}

	public byte typeID() {
		return -1;
	}
	
	protected ArrayList<UnknownField> unknownFields;
	protected final void addToUnknown(UnknownField field) {
		if (unknownFields == null)
			unknownFields = new java.util.ArrayList<UnknownField>(3);
		unknownFields.add(field);
	}
	protected final int readUnknownsUpTo(int current, int target, ByteBuffer buf) {
		while (WoblyUtils.getIDFromTag(current)<target && buf.hasRemaining()) {
			addToUnknown(UnknownField.read(buf, current));
			current = Buffers.getVIntOrMax(buf);
		}
		if (!buf.hasRemaining())
			return Integer.MAX_VALUE;
		return current;
	}
	protected final int writeUnknownsUpTo(int unknownsCounter, int target, ByteBuffer buf) {
		while (unknownsCounter < target && unknownsCounter < unknownFields.size()) {
			UnknownField next = unknownFields.get(unknownsCounter);
			if (WoblyUtils.getIDFromTag(next.getTag())>=target)
				return unknownsCounter;
			next.write(buf);
			unknownsCounter++;
		}
		return unknownsCounter;
	}
}
