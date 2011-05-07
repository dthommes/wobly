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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.wowd.common.objects.tuples.Pair;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;
import com.wowd.wobly.annotations.components.ComponentsDetails;
import com.wowd.wobly.annotations.components.ComponentsDetailsLvl2;
import com.wowd.wobly.annotations.components.ComponentsDetailsLvl3;

@WoblyTypeOptions(specialFormat = Format.BYTES_SIZE_COMPRESSED)
public class Klasa1 extends WoblyImpl
{
	@WoblyField(id = 1)
	private final int broj;

	@WoblyField(id = 12)
	private final String[] stringArray;

	@WoblyField(id = 15, componentsDetails = @ComponentsDetails( 
			specialFormat = {Format.BYTES_SIZE_COMPRESSED, Format.NUMBER_COMPRESSED}))
	private final Map<String, Long> map;

	@WoblyField(id = 16, componentsDetails = @ComponentsDetails(
			specialFormat = {Format.DEFAULT, Format.NUMBER_COMPRESSED}))
	private final TreeMap<String, Integer> map2;

	@WoblyField(id = 17)
	private final HashMap<String, Integer> map3;

	@WoblyField(id = 18)
	private final ConcurrentSkipListMap<String, Integer> map4;

	@WoblyField(id = 20000)
	private final int broj2;

	@WoblyField(id = 20004)
	private final byte[] change;

	
	@WoblyField(id=100,
			componentsDetails = @ComponentsDetails(
					componentsDetails = {@ComponentsDetailsLvl2(
							specialFormat = {Format.NUMBER_COMPRESSED, Format.BYTES_SIZE_COMPRESSED},
							componentsDetails = {@ComponentsDetailsLvl3(), @ComponentsDetailsLvl3(specialFormat = {Format.NUMBER_COMPRESSED})})}))
	private ArrayList<Pair<Long, ArrayList<Integer>>> list;
	
	public Klasa1()
	{
		broj = 3;
		stringArray = new String[] { "aaa", "sss" };
		map = new HashMap<String, Long>(6);
		map.put("bbb", 3L);
		map.put("zzz", 7L);
		map.put("zzzz", 0L);
		map2 = new TreeMap<String, Integer>();
		map2.put("", 0);
		map3 = new HashMap<String, Integer>(2);
		map3.put("a", 0);
		map4 = new ConcurrentSkipListMap<String, Integer>();
		map3.put("aba", 1);
		broj2 = 1000;
		change = new byte[] { 2, 3, 3, 54 };
	}



	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Klasa1> objectReader = new com.wowd.wobly.WoblyReaderImpl<Klasa1>() {
		@Override
		public Klasa1 readObject(java.nio.ByteBuffer buf)
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
			if (this.broj != 0) {
				buf.put((byte)10);
				buf.putInt(this.broj);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 12, buf);
			if (this.stringArray != null) {
				buf.put((byte)102);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.stringArray.length);
				for (String v1 : this.stringArray) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1, true);
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
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
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 16, buf);
			if ((this.map2 != null) && (this.map2.size() > 0)) {
				buf.put((byte)-122);
				buf.put((byte)1);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.map2.size());
				for (Map.Entry<String, Integer> v1 : this.map2.entrySet()) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1.getKey(), true);
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,v1.getValue());
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 17, buf);
			if ((this.map3 != null) && (this.map3.size() > 0)) {
				buf.put((byte)-114);
				buf.put((byte)1);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.map3.size());
				for (Map.Entry<String, Integer> v1 : this.map3.entrySet()) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1.getKey(), true);
					buf.putInt(v1.getValue());
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 18, buf);
			if ((this.map4 != null) && (this.map4.size() > 0)) {
				buf.put((byte)-106);
				buf.put((byte)1);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.map4.size());
				for (Map.Entry<String, Integer> v1 : this.map4.entrySet()) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1.getKey(), true);
					buf.putInt(v1.getValue());
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 100, buf);
			if ((this.list != null) && (this.list.size() > 0)) {
				buf.put((byte)-90);
				buf.put((byte)6);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.list.size());
				for (Pair<Long, ArrayList<Integer>> v1 : this.list) {
					com.wowd.wobly.WoblyUtils.Buffers.putVLong(buf,v1.first());
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v1.second().size());
					for (Integer v102 : v1.second()) {
						com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,v102);
					}
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 20000, buf);
			if (this.broj2 != 0) {
				buf.put((byte)-126);
				buf.put((byte)-30);
				buf.put((byte)9);
				buf.putInt(this.broj2);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 20004, buf);
			if (this.change != null) {
				buf.put((byte)-89);
				buf.put((byte)-30);
				buf.put((byte)9);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.change.length);
				buf.put(this.change);
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			com.wowd.wobly.WoblyUtils.Buffers.appendVariableSize(buf, startPositionMark);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Klasa1(final java.nio.ByteBuffer buf) {
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.broj = 0;
		else {
			this.broj = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 12, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 12)
			this.stringArray = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.stringArray = new String[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				this.stringArray[i1] = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			}
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
		
		tag = readUnknownsUpTo(tag, 16, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 16)
			this.map2 = new TreeMap<String, Integer>();
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.map2 = new TreeMap<String, Integer>();
			String tmpKey1;
			Integer tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				tmpKey1 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
				tmpVal1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				this.map2.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 17, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 17)
			this.map3 = new HashMap<String, Integer>(1);
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.map3 = new HashMap<String, Integer>(size1*2);
			String tmpKey1;
			Integer tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				tmpKey1 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
				tmpVal1 = buf.getInt();
				this.map3.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 18, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 18)
			this.map4 = new ConcurrentSkipListMap<String, Integer>();
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.map4 = new ConcurrentSkipListMap<String, Integer>();
			String tmpKey1;
			Integer tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				tmpKey1 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
				tmpVal1 = buf.getInt();
				this.map4.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 100, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 100)
			this.list = new ArrayList<Pair<Long, ArrayList<Integer>>>(1);
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.list = new ArrayList<Pair<Long, ArrayList<Integer>>>(size1);
			for (int i1 = 0; i1 < size1; i1++) {
				Pair<Long, ArrayList<Integer>> tmp1;
				Long tmpFirst2;
				tmpFirst2 = com.wowd.wobly.WoblyUtils.Buffers.getVLong(buf);
				ArrayList<Integer> tmpSecond2;
				int size102 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				tmpSecond2 = new ArrayList<Integer>(size102);
				for (int i102 = 0; i102 < size102; i102++) {
					Integer tmp102;
					tmp102 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
					tmpSecond2.add(tmp102);
				}
				tmp1 = new Pair<Long, ArrayList<Integer>>(tmpFirst2, tmpSecond2);
				this.list.add(tmp1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 20000, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 20000)
			this.broj2 = 0;
		else {
			this.broj2 = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 20004, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 20004)
			this.change = null;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			this.change = new byte[size1];
			buf.get(this.change);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Klasa1 read(java.nio.ByteBuffer buf) {
		try {
			int size = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Klasa1 object = new Klasa1(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Klasa1 read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		if (this.broj != 0) {
			size += 1;
			size += 4;
		}
		if (this.stringArray != null) {
			size += 1;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.stringArray.length);
			for (String v1 : this.stringArray) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1, true);
			}
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
		if ((this.map2 != null) && (this.map2.size() > 0)) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.map2.size());
			for (Map.Entry<String, Integer> v1 : this.map2.entrySet()) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1.getKey(), true);
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.getValue());
			}
		}
		if ((this.map3 != null) && (this.map3.size() > 0)) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.map3.size());
			for (Map.Entry<String, Integer> v1 : this.map3.entrySet()) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1.getKey(), true);
				size += 4;
			}
		}
		if ((this.map4 != null) && (this.map4.size() > 0)) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.map4.size());
			for (Map.Entry<String, Integer> v1 : this.map4.entrySet()) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1.getKey(), true);
				size += 4;
			}
		}
		if ((this.list != null) && (this.list.size() > 0)) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.list.size());
			for (Pair<Long, ArrayList<Integer>> v1 : this.list) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVLong(v1.first());
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.second().size());
				for (Integer v102 : v1.second()) {
					size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v102);
				}
			}
		}
		if (this.broj2 != 0) {
			size += 3;
			size += 4;
		}
		if (this.change != null) {
			size += 3;
			int helpSize = size;
			size += this.change.length;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
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
