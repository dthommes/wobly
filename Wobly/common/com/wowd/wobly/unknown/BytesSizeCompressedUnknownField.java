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
package com.wowd.wobly.unknown;

import java.nio.ByteBuffer;

import com.wowd.wobly.WoblyUtils.Buffers;

public class BytesSizeCompressedUnknownField extends UnknownField
{
	private final int size;
	private final byte[] bytes;
	
	public BytesSizeCompressedUnknownField(ByteBuffer buf, int tag) {
		super(tag);
		size = Buffers.getVInt(buf);
		bytes = new byte[size];
		buf.get(bytes);
	}

	@Override
	public int getSize()
	{
		return Buffers.sizeVInt(tag) + Buffers.sizeVInt(bytes.length) + bytes.length;
	}

	@Override
	public void write(ByteBuffer buf)
	{
		Buffers.putVInt(buf, tag);
		Buffers.putVInt(buf, size);
		buf.put(bytes);
	}

}
