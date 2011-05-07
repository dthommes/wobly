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
package com.wowd.wobly.inheritance;

import java.nio.ByteBuffer;

import com.wowd.wobly.Wobly;
import com.wowd.wobly.WoblyUtils.Buffers;
import com.wowd.wobly.exceptions.WoblyReadException;

public class WoblyCompressedReaderHandler<T extends Wobly> extends AbstractWoblyHandler<T>
{
	@Override
	protected byte getTypeID(ByteBuffer buf) {
		if (buf.remaining()<=0) 
			throw new WoblyReadException();
		
		int startPos = buf.position();
		Buffers.getVInt(buf);
		byte b = buf.get();
		buf.position(startPos);
		return b;		
	}
}
