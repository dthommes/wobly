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

import com.wowd.wobly.UnmodifiableWoblyImpl;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;

@WoblyTypeOptions(unmodifiable = true, specialFormat = Format.NO_SIZE_FIELD)
public class KlasaFinalParIntFloat extends UnmodifiableWoblyImpl
{
	@WoblyField(id = 0, required = true)
	private final int value;
	
	@WoblyField(id = 1, required = true)
	private final float number;

	public KlasaFinalParIntFloat()
	{
		this.value = 5;
		this.number = 151.2f;
	} 

	
	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<KlasaFinalParIntFloat> objectReader = new com.wowd.wobly.WoblyReaderImpl<KlasaFinalParIntFloat>() {
		@Override
		public KlasaFinalParIntFloat readObject(java.nio.ByteBuffer buf)
		{
			return read(buf);
		}};
	@Override
	public void write(final java.nio.ByteBuffer buf) {
		try {
			{
				buf.putInt(this.value);
			}
			{
				buf.putFloat(this.number);
			}
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private KlasaFinalParIntFloat(final java.nio.ByteBuffer buf) {
		
		{
			this.value = buf.getInt();
		}
		
		{
			this.number = buf.getFloat();
		}
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static KlasaFinalParIntFloat read(java.nio.ByteBuffer buf) {
		try {
			KlasaFinalParIntFloat object = new KlasaFinalParIntFloat(buf);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static KlasaFinalParIntFloat read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		{
			size += 4;
		}
		{
			size += 4;
		}
		return size;
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
