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
import java.util.Map;

import com.wowd.common.objects.tuples.Pair;
import com.wowd.common.objects.tuples.Triple;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyField;

public class Klasa6 extends WoblyImpl
{
	@WoblyField(id = 0)
	private final byte[] byteData;

	@WoblyField(id = 1)
	private final int[] intData;

	@WoblyField(id = 2)
	private final ArrayList<Long> ipsData;
	
	@WoblyField(id = 3)
	private final String[] idsData;
		
	@WoblyField(id = 4)
	private final Map<Integer, Long> mpair;
	
	@WoblyField(id = 5)
	private final Triple<Long, Long, Integer> triple;
	
	@WoblyField(id = 6)
	private final Pair<Integer, Float> pair;
	
	@WoblyField(id = 7, specialFormat = Format.FIXED4)
	private final int temp;
	
	public Klasa6()
	{
		byteData = null;
		intData = null;
		ipsData = null;
		idsData = null;
		mpair = null;
		triple = new Triple<Long, Long, Integer>(1L,1L,1);
		pair = null;
		temp = 3;
	}
	
	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Klasa6> objectReader = new com.wowd.wobly.WoblyReaderImpl<Klasa6>() {
		@Override
		public Klasa6 readObject(java.nio.ByteBuffer buf)
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
			if (this.byteData != null) {
				buf.put((byte)7);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.byteData.length);
				buf.put(this.byteData);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 1, buf);
			if (this.intData != null) {
				buf.put((byte)15);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.intData.length * 4);
				for (int v1 : this.intData) {
					buf.putInt(v1);
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 2, buf);
			if ((this.ipsData != null) && (this.ipsData.size() > 0)) {
				buf.put((byte)23);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.ipsData.size() * 8);
				for (Long v1 : this.ipsData) {
					buf.putLong(v1);
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 3, buf);
			if (this.idsData != null) {
				buf.put((byte)30);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.idsData.length);
				for (String v1 : this.idsData) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1, true);
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 4, buf);
			if ((this.mpair != null) && (this.mpair.size() > 0)) {
				buf.put((byte)39);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.mpair.size() * 12);
				for (Map.Entry<Integer, Long> v1 : this.mpair.entrySet()) {
					buf.putInt(v1.getKey());
					buf.putLong(v1.getValue());
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 5, buf);
			if (this.triple != null) {
				buf.put((byte)47);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, 20);
				buf.putLong(this.triple.first());
				buf.putLong(this.triple.second());
				buf.putInt(this.triple.third());
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 6, buf);
			if (this.pair != null) {
				buf.put((byte)51);
				buf.putInt(this.pair.first());
				buf.putFloat(this.pair.second());
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 7, buf);
			if (this.temp != 0) {
				buf.put((byte)58);
				buf.putInt(this.temp);
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			buf.putInt(startPositionMark, buf.position() - startPositionMark - 4);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Klasa6(final java.nio.ByteBuffer buf) {
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 0, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 0)
			this.byteData = null;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			this.byteData = new byte[size1];
			buf.get(this.byteData);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.intData = null;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 4 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.intData wanted to have size " + size1 + "/4");
			size1 /= 4;
			this.intData = new int[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				this.intData[i1] = buf.getInt();
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 2, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 2)
			this.ipsData = new ArrayList<Long>(1);
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 8 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.ipsData wanted to have size " + size1 + "/8");
			size1 /= 8;
			this.ipsData = new ArrayList<Long>(size1);
			for (int i1 = 0; i1 < size1; i1++) {
				Long tmp1;
				tmp1 = buf.getLong();
				this.ipsData.add(tmp1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 3, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 3)
			this.idsData = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.idsData = new String[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				this.idsData[i1] = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 4, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 4)
			this.mpair = new java.util.HashMap<Integer, Long>(1);
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 12 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.mpair wanted to have size " + size1 + "/12");
			size1 /= 12;
			this.mpair = new java.util.HashMap<Integer, Long>(size1*2);
			Integer tmpKey1;
			Long tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				tmpKey1 = buf.getInt();
				tmpVal1 = buf.getLong();
				this.mpair.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 5, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 5)
			this.triple = null;
		else {
			com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			Long tmpFirst1;
			tmpFirst1 = buf.getLong();
			Long tmpSecond1;
			tmpSecond1 = buf.getLong();
			Integer tmpThird1;
			tmpThird1 = buf.getInt();
			this.triple = new Triple<Long, Long, Integer>(tmpFirst1, tmpSecond1, tmpThird1);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 6, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 6)
			this.pair = null;
		else {
			Integer tmpFirst1;
			tmpFirst1 = buf.getInt();
			Float tmpSecond1;
			tmpSecond1 = buf.getFloat();
			this.pair = new Pair<Integer, Float>(tmpFirst1, tmpSecond1);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 7, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 7)
			this.temp = 0;
		else {
			this.temp = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Klasa6 read(java.nio.ByteBuffer buf) {
		try {
			int size = buf.getInt();
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Klasa6 object = new Klasa6(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Klasa6 read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		if (this.byteData != null) {
			size += 1;
			int helpSize = size;
			size += this.byteData.length;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.intData != null) {
			size += 1;
			int helpSize = size;
			size += this.intData.length * 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if ((this.ipsData != null) && (this.ipsData.size() > 0)) {
			size += 1;
			int helpSize = size;
			size += this.ipsData.size() * 8;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.idsData != null) {
			size += 1;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.idsData.length);
			for (String v1 : this.idsData) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1, true);
			}
		}
		if ((this.mpair != null) && (this.mpair.size() > 0)) {
			size += 1;
			int helpSize = size;
			size += this.mpair.size() * 12;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.triple != null) {
			size += 1;
			int helpSize = size;
			size += 20;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.pair != null) {
			size += 1;
			size += 8;
		}
		if (this.temp != 0) {
			size += 1;
			size += 4;
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
