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

import com.wowd.wobly.WoblyUtils;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.exceptions.WoblyReadException;

/**
 * 
 * 
 */
public abstract class UnknownField 
{
	protected final int tag;
	
	protected UnknownField(int tag) {
		this.tag = tag;
	}
	
	
	public static UnknownField read(ByteBuffer buf, int tag) {
		Format format = WoblyUtils.getFormatFromTag(tag);
		
		int size = format.size();
		if (size>0)
			return new FixedUnknownField(buf, size, tag);
		
		if (format.equals(Format.NUMBER_COMPRESSED))
			return new CompressedUnknownField(buf, tag);
		
		if (format.equals(Format.BYTES))
			return new BytesUnknownField(buf, tag);
		
		if (format.equals(Format.BYTES_SIZE_COMPRESSED))
			return new BytesSizeCompressedUnknownField(buf, tag);
		
		throw new WoblyReadException("wrong format in read unknown "+ format);
	}
	
	
	public byte typeID()
	{
		return -1;
	}


	public int getTag()
	{
		return tag;
	}
	
	public abstract int getSize();
	
	public abstract void write(ByteBuffer buf);
}
