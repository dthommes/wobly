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
package com.wowd.wobly.examples.old;

import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyField;

public class Klasa3 extends WoblyImpl
{

	@WoblyField(id = 1, defaultValue = "1234")
	private final int broj;

	@WoblyField(id = 2, defaultValue = "null")
	private final Klasa1 objekat;

	@WoblyField(id = 3)
	private final char ch;
	
	@WoblyField(id = 4)
	private final byte b;

	@WoblyField(id = 5)
	private final float f;

	@WoblyField(id = 501)
	private final String s;
	
	public Klasa3()
	{
		broj = 7;
		objekat = new Klasa1();
		ch = 'z';
		b = 3;
		f = 3;
		s="zzz";
	}







	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Klasa3> objectReader = new com.wowd.wobly.WoblyReaderImpl<Klasa3>() {
		@Override
		public Klasa3 readObject(java.nio.ByteBuffer buf)
		{
			return read(buf);
		}};
	@Override
	public void write(final java.nio.ByteBuffer buf) {
		try {
			int startPositionMark = buf.position();
			buf.position(buf.position()+4);
			int unknownsCounter = 0;
			if (unknownFields == null)
				unknownsCounter = Integer.MAX_VALUE;
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 1, buf);
			if (this.broj != 1234) {
				buf.put((byte)10);
				buf.putInt(this.broj);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 2, buf);
			if (this.objekat != null) {
				buf.put((byte)23);
				this.objekat.write(buf);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 3, buf);
			if (this.ch != 0) {
				buf.put((byte)25);
				buf.putChar(this.ch);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 4, buf);
			if (this.b != 0) {
				buf.put((byte)32);
				buf.put(this.b);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 5, buf);
			if (this.f != 0) {
				buf.put((byte)42);
				buf.putFloat(this.f);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 501, buf);
			if (this.s != null) {
				buf.put((byte)-81);
				buf.put((byte)31);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.s, true);
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			buf.putInt(startPositionMark, buf.position() - startPositionMark - 4);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Klasa3(final java.nio.ByteBuffer buf) {
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.broj = 1234;
		else {
			this.broj = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 2, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 2)
			this.objekat = null;
		else {
			this.objekat = Klasa1.read(buf);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 3, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 3)
			this.ch = 0;
		else {
			this.ch = buf.getChar();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 4, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 4)
			this.b = ((byte)0);
		else {
			this.b = buf.get();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 5, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 5)
			this.f = 0;
		else {
			this.f = buf.getFloat();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 501, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 501)
			this.s = null;
		else {
			this.s = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Klasa3 read(java.nio.ByteBuffer buf) {
		try {
			int size = buf.getInt();
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Klasa3 object = new Klasa3(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Klasa3 read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		if (this.broj != 1234) {
			size += 1;
			size += 4;
		}
		if (this.objekat != null) {
			size += 1;
			size += this.objekat.getSize();
		}
		if (this.ch != 0) {
			size += 1;
			size += 2;
		}
		if (this.b != 0) {
			size += 1;
			size += 1;
		}
		if (this.f != 0) {
			size += 1;
			size += 4;
		}
		if (this.s != null) {
			size += 2;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.s, true);
		}
		if (unknownFields != null)
			for (com.wowd.wobly.unknown.UnknownField uf : unknownFields)
				size += uf.getSize();
		size += 4;
		return size;
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
