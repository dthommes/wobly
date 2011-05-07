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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;
import com.wowd.wobly.annotations.components.ComponentsDetails;

@WoblyTypeOptions(specialFormat = Format.BYTES_SIZE_COMPRESSED)
public class Klasa2 extends WoblyImpl
{
	@WoblyField(id = 1, defaultValue = "123")
	private final int broj;


	@WoblyField(id = 15, componentsDetails = @ComponentsDetails( 
			specialFormat = {Format.BYTES_SIZE_COMPRESSED, Format.NUMBER_COMPRESSED}))
	private final Map<String, Long> map;

	@WoblyField(id = 35, defaultValue = "null")
	private final Map<Map<Integer, Double>, Set<Map<Integer, Integer>>> mapp;


	@WoblyField(id = 19, defaultValue = "null")
	private final Integer[] integerArray;

	@WoblyField(id = 20000, defaultValue = "200")
	private final int broj2;

	@WoblyField(id = 20005, defaultValue = "400")
	private final int broj4;


	public Klasa2() {
		broj = 14;
		map = new HashMap<String, Long>();
		map.put("Aba", 77L);
		mapp = new HashMap<Map<Integer,Double>, Set<Map<Integer,Integer>>>();
		HashMap<Integer, Double> pom1 = new HashMap<Integer, Double>();
		pom1.put(3, 2.2);
		HashSet<Map<Integer,Integer>> pom2 = new HashSet<Map<Integer,Integer>>();
		pom2.add(new HashMap<Integer, Integer>());
		mapp.put(pom1, pom2);
		integerArray = new Integer[]{1,2,3};
		broj2 = 1;
		broj4 = 9;
	}


	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Klasa2> objectReader = new com.wowd.wobly.WoblyReaderImpl<Klasa2>() {
		@Override
		public Klasa2 readObject(java.nio.ByteBuffer buf)
		{
			return read(buf);
		}};
	@Override
	public void write(final java.nio.ByteBuffer buf) {
		try {
			int startPositionMark = buf.position();
			buf.position(buf.position()+1);
			int unknownsCounter = 0;
			if (unknownFields == null)
				unknownsCounter = Integer.MAX_VALUE;
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 1, buf);
			if (this.broj != 123) {
				buf.put((byte)10);
				buf.putInt(this.broj);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 15, buf);
			if ((this.map != null) && (this.map.size() > 0)) {
				buf.put((byte)126);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.map.size());
				for (Map.Entry<String, Long> v1 : this.map.entrySet()) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1.getKey(), true);
					com.wowd.wobly.WoblyUtils.Buffers.putVLong(buf,v1.getValue());
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 19, buf);
			if (this.integerArray != null) {
				buf.put((byte)-97);
				buf.put((byte)1);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.integerArray.length * 4);
				for (Integer v1 : this.integerArray) {
					buf.putInt(v1);
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 35, buf);
			if (this.mapp != null) {
				buf.put((byte)-98);
				buf.put((byte)2);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.mapp.size());
				for (Map.Entry<Map<Integer, Double>, Set<Map<Integer, Integer>>> v1 : this.mapp.entrySet()) {
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v1.getKey().size());
					for (Map.Entry<Integer, Double> v2 : v1.getKey().entrySet()) {
						buf.putInt(v2.getKey());
						buf.putDouble(v2.getValue());
					}
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v1.getValue().size());
					for (Map<Integer, Integer> v101 : v1.getValue()) {
						com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v101.size());
						for (Map.Entry<Integer, Integer> v102 : v101.entrySet()) {
							buf.putInt(v102.getKey());
							buf.putInt(v102.getValue());
						}
					}
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 20000, buf);
			if (this.broj2 != 200) {
				buf.put((byte)-126);
				buf.put((byte)-30);
				buf.put((byte)9);
				buf.putInt(this.broj2);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 20005, buf);
			if (this.broj4 != 400) {
				buf.put((byte)-86);
				buf.put((byte)-30);
				buf.put((byte)9);
				buf.putInt(this.broj4);
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			com.wowd.wobly.WoblyUtils.Buffers.appendVariableSize(buf, startPositionMark);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Klasa2(final java.nio.ByteBuffer buf) {
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.broj = 123;
		else {
			this.broj = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 15, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 15)
			this.map = new java.util.HashMap<String, Long>(1);
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.map = new java.util.HashMap<String, Long>(size1*2);
			String tmpKey1;
			Long tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				tmpKey1 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
				tmpVal1 = com.wowd.wobly.WoblyUtils.Buffers.getVLong(buf);
				this.map.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 19, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 19)
			this.integerArray = null;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 4 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.integerArray wanted to have size " + size1 + "/4");
			size1 /= 4;
			this.integerArray = new Integer[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				this.integerArray[i1] = buf.getInt();
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 35, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 35)
			this.mapp = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.mapp = new java.util.HashMap<Map<Integer, Double>, Set<Map<Integer, Integer>>>(size1*2);
			Map<Integer, Double> tmpKey1;
			Set<Map<Integer, Integer>> tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				int size2 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				tmpKey1 = new java.util.HashMap<Integer, Double>(size2*2);
				Integer tmpKey2;
				Double tmpVal2;
				for (int i2 = 0; i2 < size2; i2++) {
					tmpKey2 = buf.getInt();
					tmpVal2 = buf.getDouble();
					tmpKey1.put(tmpKey2, tmpVal2);
				}
				int size101 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				tmpVal1 = new java.util.HashSet<Map<Integer, Integer>>(size101*2);
				for (int i101 = 0; i101 < size101; i101++) {
					Map<Integer, Integer> tmp101;
					int size102 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
					tmp101 = new java.util.HashMap<Integer, Integer>(size102*2);
					Integer tmpKey102;
					Integer tmpVal102;
					for (int i102 = 0; i102 < size102; i102++) {
						tmpKey102 = buf.getInt();
						tmpVal102 = buf.getInt();
						tmp101.put(tmpKey102, tmpVal102);
					}
					tmpVal1.add(tmp101);
				}
				this.mapp.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 20000, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 20000)
			this.broj2 = 200;
		else {
			this.broj2 = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 20005, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 20005)
			this.broj4 = 400;
		else {
			this.broj4 = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Klasa2 read(java.nio.ByteBuffer buf) {
		try {
			int size = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Klasa2 object = new Klasa2(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Klasa2 read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		if (this.broj != 123) {
			size += 1;
			size += 4;
		}
		if ((this.map != null) && (this.map.size() > 0)) {
			size += 1;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.map.size());
			for (Map.Entry<String, Long> v1 : this.map.entrySet()) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1.getKey(), true);
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVLong(v1.getValue());
			}
		}
		if (this.integerArray != null) {
			size += 2;
			int helpSize = size;
			size += this.integerArray.length * 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.mapp != null) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.mapp.size());
			for (Map.Entry<Map<Integer, Double>, Set<Map<Integer, Integer>>> v1 : this.mapp.entrySet()) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.getKey().size());
				size += v1.getKey().size() * 12;
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.getValue().size());
				for (Map<Integer, Integer> v101 : v1.getValue()) {
					size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v101.size());
					size += v101.size() * 8;
				}
			}
		}
		if (this.broj2 != 200) {
			size += 3;
			size += 4;
		}
		if (this.broj4 != 400) {
			size += 3;
			size += 4;
		}
		if (unknownFields != null)
			for (com.wowd.wobly.unknown.UnknownField uf : unknownFields)
				size += uf.getSize();
		size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size);
		return size;
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
