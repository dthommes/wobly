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
package com.wowd.wobly.examples.benchmark.optimized;

import java.util.List;

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;

@WoblyTypeOptions(specialFormat = Format.BYTES_SIZE_COMPRESSED)
public class Media extends WoblyImpl
{
	public static enum Player { JAVA, FLASH, };
	@WoblyField(id = -1, required = true)
	String uri;
	@WoblyField(id = 0, specialFormat = Format.BYTES_SIZE_COMPRESSED)
	String title;
	@WoblyField(id = -2, required = true, specialFormat = Format.NUMBER_COMPRESSED)
	int width;
	@WoblyField(id = -3, required = true, specialFormat = Format.NUMBER_COMPRESSED)
	int height;
	@WoblyField(id = -4, required = true)
	String format;
	@WoblyField(id = -5, required = true, specialFormat = Format.NUMBER_COMPRESSED)
	long duration;
	@WoblyField(id = -6, required = true, specialFormat = Format.NUMBER_COMPRESSED)
	long size;
	@WoblyField(id = 1)
	int bitrate;
	@WoblyField(id = 2, specialFormat = Format.BYTES_SIZE_COMPRESSED)
	List<String> persons;
	@WoblyField(id = -7, required = true)
	Player player;
	@WoblyField(id = 3)
	String copyright;
	
	public Media(String uri, String title, int width, int height, String format, long duration, long size, int bitrate,
			List<String> persons, Player player, String copyright)
	{
		this.uri = uri;
		this.title = title;
		this.width = width;
		this.height = height;
		this.format = format;
		this.duration = duration;
		this.size = size;
		this.bitrate = bitrate;
		this.persons = persons;
		this.player = player;
		this.copyright = copyright;
	}
	

	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<Media> objectReader = new com.wowd.wobly.WoblyReaderImpl<Media>() {
		@Override
		public Media readObject(java.nio.ByteBuffer buf)
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
			{
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.player.ordinal());
			}
			{
				com.wowd.wobly.WoblyUtils.Buffers.putVLong(buf,this.size);
			}
			{
				com.wowd.wobly.WoblyUtils.Buffers.putVLong(buf,this.duration);
			}
			{
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.format, true);
			}
			{
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.height);
			}
			{
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf,this.width);
			}
			{
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.uri, true);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 0, buf);
			if (this.title != null) {
				buf.put((byte)7);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.title, true);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 1, buf);
			if (this.bitrate != 0) {
				buf.put((byte)10);
				buf.putInt(this.bitrate);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 2, buf);
			if ((this.persons != null) && (this.persons.size() > 0)) {
				buf.put((byte)23);
				int startFieldMark = buf.position();
				buf.position(buf.position()+1);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.persons.size());
				for (String v1 : this.persons) {
					com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, v1, true);
				}
				com.wowd.wobly.WoblyUtils.Buffers.appendVariableSize(buf, startFieldMark);
			}
			
			unknownsCounter = writeUnknownsUpTo(unknownsCounter, 3, buf);
			if (this.copyright != null) {
				buf.put((byte)31);
				com.wowd.wobly.WoblyUtils.Buffers.putStringUTF8(buf, this.copyright, true);
			}
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			com.wowd.wobly.WoblyUtils.Buffers.appendVariableSize(buf, startPositionMark);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private Media(final java.nio.ByteBuffer buf) {
		
		{
			this.player = Player.values()[com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf)];
		}
		
		{
			this.size = com.wowd.wobly.WoblyUtils.Buffers.getVLong(buf);
		}
		
		{
			this.duration = com.wowd.wobly.WoblyUtils.Buffers.getVLong(buf);
		}
		
		{
			this.format = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
		}
		
		{
			this.height = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
		}
		
		{
			this.width = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
		}
		
		{
			this.uri = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
		}
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		
		tag = readUnknownsUpTo(tag, 0, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 0)
			this.title = null;
		else {
			this.title = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 1, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 1)
			this.bitrate = 0;
		else {
			this.bitrate = buf.getInt();
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 2, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 2)
			this.persons = new java.util.ArrayList<String>(1);
		else {
			com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.persons = new java.util.ArrayList<String>(size1);
			for (int i1 = 0; i1 < size1; i1++) {
				String tmp1;
				tmp1 = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
				this.persons.add(tmp1);
			}
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		
		tag = readUnknownsUpTo(tag, 3, buf);
		if (com.wowd.wobly.WoblyUtils.getIDFromTag(tag) > 3)
			this.copyright = null;
		else {
			this.copyright = com.wowd.wobly.WoblyUtils.Buffers.getStringUTF8(buf, true);
			tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		}
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static Media read(java.nio.ByteBuffer buf) {
		try {
			int size = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			Media object = new Media(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static Media read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.player.ordinal());
		}
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVLong(this.size);
		}
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVLong(this.duration);
		}
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.format, true);
		}
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.height);
		}
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.width);
		}
		{
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.uri, true);
		}
		if (this.title != null) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.title, true);
		}
		if (this.bitrate != 0) {
			size += 1;
			size += 4;
		}
		if ((this.persons != null) && (this.persons.size() > 0)) {
			size += 1;
			int helpSize = size;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.persons.size());
			for (String v1 : this.persons) {
				size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(v1, true);
			}
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		if (this.copyright != null) {
			size += 1;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeStringUTF8(this.copyright, true);
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


