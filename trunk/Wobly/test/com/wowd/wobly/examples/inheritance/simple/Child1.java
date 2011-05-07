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

import com.wowd.wobly.annotations.InlineSuperclass;
import com.wowd.wobly.annotations.WoblyField;

@InlineSuperclass(value = 1)
public class Child1 extends Parent
{
	@WoblyField(id = -2, required = true)
	String c1;
	
	@WoblyField(id = 1)
	String c2;
	
		
	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Child1> objectReader = new com.wowd.wobly.WoblyReaderImpl<Child1>() {
		@Override
		public Child1 readObject(java.nio.ByteBuffer buf)
		{
			return read(buf);
		}
		@Override
		public int targetTypeID()
		{
			return 255&1;
		}};
	@Override
	public void write(final java.nio.ByteBuffer buf) {
		try {
			int startPositionMark = buf.position();
			buf.position(buf.position()+4);
			buf.put(typeID());
			int unknownsCounter = 0;
			if (unknownFields == null)
				unknownsCounter = Integer.MAX_VALUE;
			{
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.c1, true);
			}
			{
				buf.putInt(this.commonRequiredField);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 0, buf);
			if (this.commonField != 0) {
				buf.put((byte)2);
				buf.putInt(this.commonField);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 1, buf);
			if (this.c2 != null) {
				buf.put((byte)15);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.c2, true);
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			buf.putInt(startPositionMark, buf.position() - startPositionMark - 4);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Child1(final java.nio.ByteBuffer buf) {
		if(buf.get() != typeID())
			throw new com.wowd.wobly.exceptions.WoblyReadException();
		
		{
			this.c1 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
		}
		
		{
			this.commonRequiredField = buf.getInt();
		}
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 0, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 0)
			this.commonField = 0;
		else {
			this.commonField = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.c2 = null;
		else {
			this.c2 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Child1 read(java.nio.ByteBuffer buf) {
		try {
			int size = buf.getInt();
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Child1 object = new Child1(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Child1 read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		size += 1;
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.c1, true);
		}
		{
			size += 4;
		}
		if (this.commonField != 0) {
			size += 1;
			size += 4;
		}
		if (this.c2 != null) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.c2, true);
		}
		if (unknownFields != null)
			for (com.wowd.wobly.unknown.UnknownField uf : unknownFields)
				size += uf.getSize();
		size += 4;
		return size;
	}
	@Override
	public byte typeID() {
		return 1;
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
