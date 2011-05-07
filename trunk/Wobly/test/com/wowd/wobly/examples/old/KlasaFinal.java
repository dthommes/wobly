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

import com.wowd.common.objects.tuples.Pair;
import com.wowd.wobly.UnmodifiableWoblyImpl;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;

@WoblyTypeOptions(unmodifiable = true)
public class KlasaFinal extends UnmodifiableWoblyImpl
{
	@WoblyField(id = 0, required = true)
	private final Pair<Integer, Integer> pair;
	
	@WoblyField(id = 1, required = true)
	private final Long address;
	
	@WoblyField(id = 2, required = true)
	private final ArrayList<Integer> list;

	public KlasaFinal()
	{
		this.pair = new Pair<Integer, Integer>(3, 65);
		this.address = 142321321321L;
		this.list = new ArrayList<Integer>();
	}
	
	
	
	
	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<KlasaFinal> objectReader = new com.wowd.wobly.WoblyReaderImpl<KlasaFinal>() {
		@Override
		public KlasaFinal readObject(java.nio.ByteBuffer buf)
		{
			return read(buf);
		}};
	@Override
	public void write(final java.nio.ByteBuffer buf) {
		try {
			int startPositionMark = buf.position();
			buf.position(buf.position()+4);
			{
				buf.putInt(this.pair.first());
				buf.putInt(this.pair.second());
			}
			{
				buf.putLong(this.address);
			}
			{
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.list.size() * 4);
				for (Integer v1 : this.list) {
					buf.putInt(v1);
				}
			}
			buf.putInt(startPositionMark, buf.position() - startPositionMark - 4);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private KlasaFinal(final java.nio.ByteBuffer buf) {
		
		{
			Integer tmpFirst1;
			tmpFirst1 = buf.getInt();
			Integer tmpSecond1;
			tmpSecond1 = buf.getInt();
			this.pair = new Pair<Integer, Integer>(tmpFirst1, tmpSecond1);
		}
		
		{
			this.address = buf.getLong();
		}
		
		{
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf); //read size
			if (size1 % 4 != 0)
				throw new com.wowd.wobly.exceptions.WoblyReadException("this.list wanted to have size " + size1 + "/4");
			size1 /= 4;
			this.list = new ArrayList<Integer>(size1);
			for (int i1 = 0; i1 < size1; i1++) {
				Integer tmp1;
				tmp1 = buf.getInt();
				this.list.add(tmp1);
			}
		}
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static KlasaFinal read(java.nio.ByteBuffer buf) {
		try {
			int size = buf.getInt();
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			KlasaFinal object = new KlasaFinal(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static KlasaFinal read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		{
			size += 8;
		}
		{
			size += 8;
		}
		{
			int helpSize = size;
			size += this.list.size() * 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size-helpSize);
		}
		size += 4;
		return size;
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
