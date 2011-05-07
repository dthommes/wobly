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
package com.wowd.wobly.examples.coverage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.components.ComponentsDetails;

public class ManyFields extends WoblyImpl
{
	@WoblyField(id = 0)
	private int f0;

	@WoblyField(id = 1)
	private byte f1;

	@WoblyField(id = 2)
	private long f2;
	
	@WoblyField(id = 3)
	private short f3;

	@WoblyField(id = 4)
	private float f4;

	@WoblyField(id = 5)
	private double f5;

	@WoblyField(id = 6)
	private char f6;

	@WoblyField(id = 7)
	private boolean f7;

	@WoblyField(id = 10, required = true)
	private int f10;

	@WoblyField(id = 11, required = true)
	private byte f11;

	@WoblyField(id = 12, required = true)
	private long f12;
	
	@WoblyField(id = 13, required = true)
	private short f13;

	@WoblyField(id = 14, required = true)
	private float f14;

	@WoblyField(id = 15, required = true)
	private double f15;

	@WoblyField(id = 16, required = true)
	private char f16;

	@WoblyField(id = 17, required = true)
	private boolean f17;

	@WoblyField(id = 20, specialFormat = Format.NUMBER_COMPRESSED)
	private int f20;

	@WoblyField(id = 22, specialFormat = Format.NUMBER_COMPRESSED)
	private long f22;
	
	@WoblyField(id = 23, specialFormat = Format.NUMBER_COMPRESSED)
	private short f23;

	@WoblyField(id = 26, specialFormat = Format.NUMBER_COMPRESSED)
	private char f26;
	
	
	@WoblyField(id = 30)
	private String f30;

	@WoblyField(id = 31, specialFormat = Format.BYTES)
	private String f31;
	

	
	@WoblyField(id = 40)
	private Map<Integer, Integer> f40;
	
	@WoblyField(id = 41)
	private Map<List<Integer>, Integer> f41;
	
	@WoblyField(id = 42, collectionNullToEmpty = false)
	private Set<String[]> f42;
	
	@WoblyField(id = 43, collectionNullToEmpty = false)
	private ConcurrentHashMap<Integer, Long> f43;
	
	
	@WoblyField(id = 50)
	private String[] f50;
	
	@WoblyField(id = 51)
	private Map<String, Integer>[] f51;

	@WoblyField(id = 52)
	private int[] f52;

	@WoblyField(id = 53, arrayNullToEmpty = true)
	private byte[] f53;
	
	@WoblyField(id = 54, componentsDetails = @ComponentsDetails(specialFormat = Format.NUMBER_COMPRESSED))
	private int[] f54; 

	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<ManyFields> objectReader = new com.wowd.wobly.WoblyReaderImpl<ManyFields>() {
		@Override
		public ManyFields readObject(java.nio.ByteBuffer buf)
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
			{
				buf.putInt(this.f10);
			}
			{
				buf.put(this.f11);
			}
			{
				buf.putLong(this.f12);
			}
			{
				buf.putShort(this.f13);
			}
			{
				buf.putFloat(this.f14);
			}
			{
				buf.putDouble(this.f15);
			}
			{
				buf.putChar(this.f16);
			}
			{
				buf.put((byte) (this.f17 ? 1 : 0));
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 0, buf);
			if (this.f0 != 0) {
				buf.put((byte)2);
				buf.putInt(this.f0);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 1, buf);
			if (this.f1 != 0) {
				buf.put((byte)8);
				buf.put(this.f1);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 2, buf);
			if (this.f2 != 0) {
				buf.put((byte)19);
				buf.putLong(this.f2);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 3, buf);
			if (this.f3 != 0) {
				buf.put((byte)25);
				buf.putShort(this.f3);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 4, buf);
			if (this.f4 != 0) {
				buf.put((byte)34);
				buf.putFloat(this.f4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 5, buf);
			if (this.f5 != 0) {
				buf.put((byte)43);
				buf.putDouble(this.f5);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 6, buf);
			if (this.f6 != 0) {
				buf.put((byte)49);
				buf.putChar(this.f6);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 7, buf);
			if (this.f7 != false) {
				buf.put((byte)56);
				buf.put((byte) (this.f7 ? 1 : 0));
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 20, buf);
			if (this.f20 != 0) {
				buf.put((byte)-91);
				buf.put((byte)1);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.f20);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 22, buf);
			if (this.f22 != 0) {
				buf.put((byte)-75);
				buf.put((byte)1);
				com.wowd.wobly.WoblyUtils.Buffers.putVLong(buf,this.f22);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 23, buf);
			if (this.f23 != 0) {
				buf.put((byte)-67);
				buf.put((byte)1);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.f23);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 26, buf);
			if (this.f26 != 0) {
				buf.put((byte)-43);
				buf.put((byte)1);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.f26);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 30, buf);
			if (this.f30 != null) {
				buf.put((byte)-9);
				buf.put((byte)1);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.f30, true);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 31, buf);
			if (this.f31 != null) {
				buf.put((byte)-2);
				buf.put((byte)1);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.f31, false);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 40, buf);
			if ((this.f40 != null) && (this.f40.size() > 0)) {
				buf.put((byte)-57);
				buf.put((byte)2);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f40.size() * 8);
				for (Map.Entry<Integer, Integer> v1 : this.f40.entrySet()) {
					buf.putInt(v1.getKey());
					buf.putInt(v1.getValue());
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 41, buf);
			if ((this.f41 != null) && (this.f41.size() > 0)) {
				buf.put((byte)-50);
				buf.put((byte)2);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f41.size());
				for (Map.Entry<List<Integer>, Integer> v1 : this.f41.entrySet()) {
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v1.getKey().size());
					for (Integer v2 : v1.getKey()) {
						buf.putInt(v2);
					}
					buf.putInt(v1.getValue());
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 42, buf);
			if (this.f42 != null) {
				buf.put((byte)-42);
				buf.put((byte)2);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f42.size());
				for (String[] v1 : this.f42) {
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v1.length);
					for (String v2 : v1) {
						com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v2, true);
					}
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 43, buf);
			if (this.f43 != null) {
				buf.put((byte)-33);
				buf.put((byte)2);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f43.size() * 12);
				for (Map.Entry<Integer, Long> v1 : this.f43.entrySet()) {
					buf.putInt(v1.getKey());
					buf.putLong(v1.getValue());
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 50, buf);
			if (this.f50 != null) {
				buf.put((byte)-106);
				buf.put((byte)3);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f50.length);
				for (String v1 : this.f50) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1, true);
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 51, buf);
			if (this.f51 != null) {
				buf.put((byte)-98);
				buf.put((byte)3);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f51.length);
				for (Map<String, Integer> v1 : this.f51) {
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, v1.size());
					for (Map.Entry<String, Integer> v2 : v1.entrySet()) {
						com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v2.getKey(), true);
						buf.putInt(v2.getValue());
					}
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 52, buf);
			if (this.f52 != null) {
				buf.put((byte)-89);
				buf.put((byte)3);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f52.length * 4);
				for (int v1 : this.f52) {
					buf.putInt(v1);
				}
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 53, buf);
			if ((this.f53 != null) && (this.f53.length > 0)) {
				buf.put((byte)-81);
				buf.put((byte)3);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f53.length);
				buf.put(this.f53);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 54, buf);
			if (this.f54 != null) {
				buf.put((byte)-74);
				buf.put((byte)3);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.f54.length);
				for (int v1 : this.f54) {
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,v1);
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
	private ManyFields(final java.nio.ByteBuffer buf) {
		
		{
			this.f10 = buf.getInt();
		}
		
		{
			this.f11 = buf.get();
		}
		
		{
			this.f12 = buf.getLong();
		}
		
		{
			this.f13 = buf.getShort();
		}
		
		{
			this.f14 = buf.getFloat();
		}
		
		{
			this.f15 = buf.getDouble();
		}
		
		{
			this.f16 = buf.getChar();
		}
		
		{
			this.f17 = (buf.get() == 1 ? true : false);
		}
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 0, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 0)
			this.f0 = 0;
		else {
			this.f0 = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.f1 = ((byte)0);
		else {
			this.f1 = buf.get();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 2, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 2)
			this.f2 = 0;
		else {
			this.f2 = buf.getLong();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 3, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 3)
			this.f3 = ((short)0);
		else {
			this.f3 = buf.getShort();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 4, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 4)
			this.f4 = 0;
		else {
			this.f4 = buf.getFloat();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 5, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 5)
			this.f5 = 0;
		else {
			this.f5 = buf.getDouble();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 6, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 6)
			this.f6 = 0;
		else {
			this.f6 = buf.getChar();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 7, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 7)
			this.f7 = false;
		else {
			this.f7 = (buf.get() == 1 ? true : false);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 20, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 20)
			this.f20 = 0;
		else {
			this.f20 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 22, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 22)
			this.f22 = 0;
		else {
			this.f22 = com.wowd.wobly.WoblyUtils.Buffers.getVLong(buf);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 23, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 23)
			this.f23 = ((short)0);
		else {
			this.f23 = (short) com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 26, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 26)
			this.f26 = 0;
		else {
			this.f26 = (char) com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 30, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 30)
			this.f30 = null;
		else {
			this.f30 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 31, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 31)
			this.f31 = null;
		else {
			this.f31 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, false);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 40, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 40)
			this.f40 = new java.util.HashMap<Integer, Integer>(1);
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 8 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.f40 wanted to have size " + size1 + "/8");
			size1 /= 8;
			this.f40 = new java.util.HashMap<Integer, Integer>(size1*2);
			Integer tmpKey1;
			Integer tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				tmpKey1 = buf.getInt();
				tmpVal1 = buf.getInt();
				this.f40.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 41, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 41)
			this.f41 = new java.util.HashMap<List<Integer>, Integer>(1);
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.f41 = new java.util.HashMap<List<Integer>, Integer>(size1*2);
			List<Integer> tmpKey1;
			Integer tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				int size2 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				tmpKey1 = new java.util.ArrayList<Integer>(size2);
				for (int i2 = 0; i2 < size2; i2++) {
					Integer tmp2;
					tmp2 = buf.getInt();
					tmpKey1.add(tmp2);
				}
				tmpVal1 = buf.getInt();
				this.f41.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 42, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 42)
			this.f42 = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.f42 = new java.util.HashSet<String[]>(size1*2);
			for (int i1 = 0; i1 < size1; i1++) {
				String[] tmp1;
				int size2 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				tmp1 = new String[size2];
				for (int i2 = 0; i2 < size2; i2++) {
					tmp1[i2] = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
				}
				this.f42.add(tmp1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 43, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 43)
			this.f43 = null;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 12 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.f43 wanted to have size " + size1 + "/12");
			size1 /= 12;
			this.f43 = new ConcurrentHashMap<Integer, Long>(size1*2);
			Integer tmpKey1;
			Long tmpVal1;
			for (int i1 = 0; i1 < size1; i1++) {
				tmpKey1 = buf.getInt();
				tmpVal1 = buf.getLong();
				this.f43.put(tmpKey1, tmpVal1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 50, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 50)
			this.f50 = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.f50 = new String[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				this.f50[i1] = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 51, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 51)
			this.f51 = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.f51 = new Map[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				int size2 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				this.f51[i1] = new java.util.HashMap<String, Integer>(size2*2);
				String tmpKey2;
				Integer tmpVal2;
				for (int i2 = 0; i2 < size2; i2++) {
					tmpKey2 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
					tmpVal2 = buf.getInt();
					this.f51[i1].put(tmpKey2, tmpVal2);
				}
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 52, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 52)
			this.f52 = null;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 4 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.f52 wanted to have size " + size1 + "/4");
			size1 /= 4;
			this.f52 = new int[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				this.f52[i1] = buf.getInt();
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 53, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 53)
			this.f53 = com.wowd.wobly.WoblyUtils.NULL_BYTES;
		else {
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			this.f53 = new byte[size1];
			buf.get(this.f53);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 54, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 54)
			this.f54 = null;
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.f54 = new int[size1];
			for (int i1 = 0; i1 < size1; i1++) {
				this.f54[i1] = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static ManyFields read(java.nio.ByteBuffer buf) {
		try {
			int size = buf.getInt();
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			ManyFields object = new ManyFields(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static ManyFields read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		{
			size += 4;
		}
		{
			size += 1;
		}
		{
			size += 8;
		}
		{
			size += 2;
		}
		{
			size += 4;
		}
		{
			size += 8;
		}
		{
			size += 2;
		}
		{
			size += 1;
		}
		if (this.f0 != 0) {
			size += 1;
			size += 4;
		}
		if (this.f1 != 0) {
			size += 1;
			size += 1;
		}
		if (this.f2 != 0) {
			size += 1;
			size += 8;
		}
		if (this.f3 != 0) {
			size += 1;
			size += 2;
		}
		if (this.f4 != 0) {
			size += 1;
			size += 4;
		}
		if (this.f5 != 0) {
			size += 1;
			size += 8;
		}
		if (this.f6 != 0) {
			size += 1;
			size += 2;
		}
		if (this.f7 != false) {
			size += 1;
			size += 1;
		}
		if (this.f20 != 0) {
			size += 2;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f20);
		}
		if (this.f22 != 0) {
			size += 2;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVLong(this.f22);
		}
		if (this.f23 != 0) {
			size += 2;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f23);
		}
		if (this.f26 != 0) {
			size += 2;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f26);
		}
		if (this.f30 != null) {
			size += 2;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.f30, true);
		}
		if (this.f31 != null) {
			size += 2;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.f31, false);
		}
		if ((this.f40 != null) && (this.f40.size() > 0)) {
			size += 2;
			int helpSize = size;
			size += this.f40.size() * 8;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if ((this.f41 != null) && (this.f41.size() > 0)) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f41.size());
			for (Map.Entry<List<Integer>, Integer> v1 : this.f41.entrySet()) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.getKey().size());
				size += v1.getKey().size() * 4;
				size += 4;
			}
		}
		if (this.f42 != null) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f42.size());
			for (String[] v1 : this.f42) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.length);
				for (String v2 : v1) {
					size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v2, true);
				}
			}
		}
		if (this.f43 != null) {
			size += 2;
			int helpSize = size;
			size += this.f43.size() * 12;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.f50 != null) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f50.length);
			for (String v1 : this.f50) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1, true);
			}
		}
		if (this.f51 != null) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f51.length);
			for (Map<String, Integer> v1 : this.f51) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.size());
				for (Map.Entry<String, Integer> v2 : v1.entrySet()) {
					size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v2.getKey(), true);
					size += 4;
				}
			}
		}
		if (this.f52 != null) {
			size += 2;
			int helpSize = size;
			size += this.f52.length * 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if ((this.f53 != null) && (this.f53.length > 0)) {
			size += 2;
			int helpSize = size;
			size += this.f53.length;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.f54 != null) {
			size += 2;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.f54.length);
			for (int v1 : this.f54) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1);
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
