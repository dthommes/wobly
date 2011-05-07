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
import com.wowd.wobly.annotations.AbstractType;
import com.wowd.wobly.annotations.ReadStatic;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;

@AbstractType({KlasaB.class, KlasaC.class})
@WoblyTypeOptions(specialFormat = Format.BYTES_SIZE_COMPRESSED)
public abstract class KlasaA extends WoblyImpl
{
	@WoblyField(id=2)
	protected long z;
	
	@WoblyField(id=3)
	protected String id;

	
	public KlasaA(long z, String id)
	{
		this.z = z;
		this.id = id;
	}
	
	public KlasaA() {}



	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	protected static class WoblyInheritanceHandler
	{
		static final com.wowd.wobly.inheritance.WoblyCompressedReaderHandler<KlasaA> readers = new com.wowd.wobly.inheritance.WoblyCompressedReaderHandler<KlasaA>();
	
		static
		{
			try
			{
				readers.register(com.wowd.wobly.examples.old.KlasaB.objectReader);
				readers.register(com.wowd.wobly.examples.old.KlasaC.objectReader);
			}
			catch (java.lang.Throwable t)
			{
				t.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@com.wowd.wobly.annotations.ReadStatic
	public static <WX extends KlasaA> WX readKlasaA(java.nio.ByteBuffer buf) {
		return (WX)WoblyInheritanceHandler.readers.read(buf);
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
