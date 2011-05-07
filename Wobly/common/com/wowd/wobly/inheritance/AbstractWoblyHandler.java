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
import com.wowd.wobly.WoblyReader;
import com.wowd.wobly.exceptions.WoblyReadException;

public abstract class AbstractWoblyHandler<T extends Wobly>
{
	private static final int MAX_TYPE_ID = 256;

	private final WoblyReader<? extends T>[] readers;
	
	@SuppressWarnings("unchecked")
	public AbstractWoblyHandler() {
		readers = (WoblyReader<? extends T>[]) new WoblyReader<?>[MAX_TYPE_ID];
	}
	
	public void register(WoblyReader<? extends T> reader) {
		int typeID = reader.targetTypeID();
		if (readers[typeID]==null)
			readers[typeID] = reader;
		else 
		{
			if (!readers[typeID].getClass().equals(reader.getClass()))
				throw new RuntimeException("tried to register two handlers for " + typeID + ", " + readers[typeID].getClass() + " != " + reader.getClass());
		}
	}
	protected abstract byte getTypeID(ByteBuffer buf);
	
	public T read(ByteBuffer buf) {
		byte type = getTypeID(buf);
		if (readers[type]==null)
			throw new WoblyReadException(Byte.toString(type));
		else return readers[type].readObject(buf);
	}
}
