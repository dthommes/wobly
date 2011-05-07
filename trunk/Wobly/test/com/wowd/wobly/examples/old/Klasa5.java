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

import java.util.ArrayList;
import java.util.List;

import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyField;

public class Klasa5 extends WoblyImpl
{
	public static enum SomeType {
		Some1,
		Some2,
		Some3;
	}
	@WoblyField(id = 0, collectionNullToEmpty = false)
	private final List<Integer> list;
	
	@WoblyField(id = 1)
	private final SomeType mtype;
	
	@WoblyField(id = 2)
	private final ArrayList<Integer>[] list2;

	public Klasa5(List<Integer> list, SomeType mtype, ArrayList<Integer>[] list2)
	{
		this.list = list;
		this.mtype = mtype;
		this.list2 = list2;
	} 
	

	
	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Klasa5> objectReader = new com.wowd.wobly.WoblyReaderImpl<Klasa5>() {
		@Override
		public Klasa5 readObject(java.nio.ByteBuffer buf)
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
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 0, buf);
			if (this.list != null) {
				buf.put((byte)7);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.list.size() * 4);
				for (Integer v1 : this.list) {
					buf.putInt(v1);
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 1, buf);
			if (this.mtype != null) {
				buf.put((byte)13);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.mtype.ordinal());
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 2, buf);
			if (this.list2 != null) {
				buf.put((byte)22);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.list2.length);
				for (ArrayList<Integer> v1 : this.list2) {
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v1.size());
					for (Integer v2 : v1) {
						buf.putInt(v2);
					}
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			buf.putInt(startPositionMark, buf.position() - startPositionMark - 4);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Klasa5(final java.nio.ByteBuffer buf) {
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 0, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 0)
			this.list = null;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 4 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.list wanted to have size " + size1 + "/4");
			size1 /= 4;
			this.list = new java.util.ArrayList<Integer>(size1);
			for (int i1 = 0; i1 < size1; i1++) {
				Integer tmp1;
				tmp1 = buf.getInt();
				this.list.add(tmp1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.mtype = null;
		else {
			this.mtype = SomeType.values()[com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf)];
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 2, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 2)
			this.list2 = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.list2 = new ArrayList[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				int size2 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				this.list2[i1] = new ArrayList<Integer>(size2);
				for (int i2 = 0; i2 < size2; i2++) {
					Integer tmp2;
					tmp2 = buf.getInt();
					this.list2[i1].add(tmp2);
				}
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Klasa5 read(java.nio.ByteBuffer buf) {
		try {
			int size = buf.getInt();
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Klasa5 object = new Klasa5(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Klasa5 read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		if (this.list != null) {
			size += 1;
			int helpSize = size;
			size += this.list.size() * 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.mtype != null) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.mtype.ordinal());
		}
		if (this.list2 != null) {
			size += 1;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.list2.length);
			for (ArrayList<Integer> v1 : this.list2) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.size());
				size += v1.size() * 4;
			}
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
