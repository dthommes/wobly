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

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.WoblyImpl;
import com.wowd.wobly.annotations.WoblyTypeOptions;

@WoblyTypeOptions(specialFormat = Format.BYTES_SIZE_COMPRESSED)
public class DummyCompSizeEmptyObject extends WoblyImpl
{



	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<DummyCompSizeEmptyObject> objectReader = new com.wowd.wobly.WoblyReaderImpl<DummyCompSizeEmptyObject>() {
		@Override
		public DummyCompSizeEmptyObject readObject(java.nio.ByteBuffer buf)
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
			writeUnknownsUpTo(unknownsCounter, Integer.MAX_VALUE, buf);
			com.wowd.wobly.WoblyUtils.Buffers.appendVariableSize(buf, startPositionMark);
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private DummyCompSizeEmptyObject(final java.nio.ByteBuffer buf) {
		int tag = com.wowd.wobly.WoblyUtils.Buffers.getVIntOrMax(buf);
		readUnknownsUpTo(tag, Integer.MAX_VALUE, buf);
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static DummyCompSizeEmptyObject read(java.nio.ByteBuffer buf) {
		try {
			int size = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			int originalLimit = buf.limit();
			int newLimit = buf.position() + size;
			if (newLimit > originalLimit)
				throw new com.wowd.wobly.exceptions.WoblyReadException(newLimit + " " + originalLimit);
			buf.limit(newLimit);
			DummyCompSizeEmptyObject object = new DummyCompSizeEmptyObject(buf);
			buf.limit(originalLimit);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static DummyCompSizeEmptyObject read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		if (unknownFields != null)
			for (com.wowd.wobly.unknown.UnknownField uf : unknownFields)
				size += uf.getSize();
		size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(size);
		return size;
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
