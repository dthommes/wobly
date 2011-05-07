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
import java.util.LinkedList;
import java.util.Random;

import com.wowd.common.objects.tuples.Pair;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.components.ComponentsDetails;
import com.wowd.wobly.annotations.components.ComponentsDetailsLvl2;

public class Klasa4 extends WoblyImpl
{

	@WoblyField(id = 1)
	private final String id;


	@WoblyField(id = 2)
	private final String key;

	@WoblyField(id = 3, specialFormat=Format.NUMBER_COMPRESSED)
	private final int broj;
	
	@WoblyField(id = 4, specialFormat=Format.NUMBER_COMPRESSED)
	private final short mali;

	@WoblyField(id = 5, componentsDetails = @ComponentsDetails( 
			specialFormat={Format.NUMBER_COMPRESSED}), specialFormat = Format.BYTES_SIZE_COMPRESSED)
	private final ArrayList<Integer> lista;
	
	@WoblyField(id=10, componentsDetails=@ComponentsDetails(//classes={Pair.class}, 
			componentsDetails={@ComponentsDetailsLvl2(//classes={Long.class, Integer.class}, 
					specialFormat={Format.FIXED8, Format.NUMBER_COMPRESSED})}))
	protected LinkedList<Pair<Long, Integer>> listaParova;
	
	
	@WoblyField(id=15, specialFormat = Format.BYTES_SIZE_COMPRESSED)
	protected Pair<Long, Long> par;
	
	
	public Klasa4() {
		id = " " + new Random().nextFloat();
		key = " " + new Random().nextFloat();
		broj = 100000;
		mali = 4;	
		lista = new ArrayList<Integer>();
		lista.add(1000000000);
		lista.add(1);
		lista.add(4);
		lista.add(1);
		lista.add(4);
		lista.add(1);
		lista.add(4);
		lista.add(0);
		lista.add(40000);
		listaParova = new LinkedList<Pair<Long,Integer>>();
		listaParova.add(new Pair<Long,Integer>(10L,100));
		listaParova.add(new Pair<Long,Integer>(10L,1));
		listaParova.add(new Pair<Long,Integer>(10L,0));
		listaParova.add(new Pair<Long,Integer>(100000000L,5000000));
		par = new Pair<Long, Long>(12L,12L);
	}
	


	


	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Klasa4> objectReader = new com.wowd.wobly.WoblyReaderImpl<Klasa4>() {
		@Override
		public Klasa4 readObject(java.nio.ByteBuffer buf)
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
			if (this.id != null) {
				buf.put((byte)15);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.id, true);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 2, buf);
			if (this.key != null) {
				buf.put((byte)23);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.key, true);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 3, buf);
			if (this.broj != 0) {
				buf.put((byte)29);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.broj);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 4, buf);
			if (this.mali != 0) {
				buf.put((byte)37);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.mali);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 5, buf);
			if ((this.lista != null) && (this.lista.size() > 0)) {
				buf.put((byte)47);
				int startFieldMark = buf.position();
				buf.position(buf.position()+1);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.lista.size());
				for (Integer v1 : this.lista) {
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,v1);
				}
				com.wowd.wobly.WoblyUtils.Buffers.appendVariableSize(buf, startFieldMark);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 10, buf);
			if ((this.listaParova != null) && (this.listaParova.size() > 0)) {
				buf.put((byte)86);
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.listaParova.size());
				for (Pair<Long, Integer> v1 : this.listaParova) {
					buf.putLong(v1.first());
					com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,v1.second());
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 15, buf);
			if (this.par != null) {
				buf.put((byte)127);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, 16);
				buf.putLong(this.par.first());
				buf.putLong(this.par.second());
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			buf.putInt(startPositionMark, buf.position() - startPositionMark - 4);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Klasa4(final java.nio.ByteBuffer buf) {
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.id = null;
		else {
			this.id = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 2, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 2)
			this.key = null;
		else {
			this.key = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 3, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 3)
			this.broj = 0;
		else {
			this.broj = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 4, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 4)
			this.mali = ((short)0);
		else {
			this.mali = (short) com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 5, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 5)
			this.lista = new ArrayList<Integer>(1);
		else {
			com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.lista = new ArrayList<Integer>(size1);
			for (int i1 = 0; i1 < size1; i1++) {
				Integer tmp1;
				tmp1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				this.lista.add(tmp1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 10, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 10)
			this.listaParova = new LinkedList<Pair<Long, Integer>>();
		else {
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.listaParova = new LinkedList<Pair<Long, Integer>>();
			for (int i1 = 0; i1 < size1; i1++) {
				Pair<Long, Integer> tmp1;
				Long tmpFirst2;
				tmpFirst2 = buf.getLong();
				Integer tmpSecond2;
				tmpSecond2 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
				tmp1 = new Pair<Long, Integer>(tmpFirst2, tmpSecond2);
				this.listaParova.add(tmp1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 15, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 15)
			this.par = null;
		else {
			com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			Long tmpFirst1;
			tmpFirst1 = buf.getLong();
			Long tmpSecond1;
			tmpSecond1 = buf.getLong();
			this.par = new Pair<Long, Long>(tmpFirst1, tmpSecond1);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Klasa4 read(java.nio.ByteBuffer buf) {
		try {
			int size = buf.getInt();
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Klasa4 object = new Klasa4(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Klasa4 read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		if (this.id != null) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.id, true);
		}
		if (this.key != null) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.key, true);
		}
		if (this.broj != 0) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.broj);
		}
		if (this.mali != 0) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.mali);
		}
		if ((this.lista != null) && (this.lista.size() > 0)) {
			size += 1;
			int helpSize = size;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.lista.size());
			for (Integer v1 : this.lista) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1);
			}
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if ((this.listaParova != null) && (this.listaParova.size() > 0)) {
			size += 1;
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.listaParova.size());
			for (Pair<Long, Integer> v1 : this.listaParova) {
				size += 8;
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(v1.second());
			}
		}
		if (this.par != null) {
			size += 1;
			int helpSize = size;
			size += 16;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
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
