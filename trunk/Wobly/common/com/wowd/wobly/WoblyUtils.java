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
package com.wowd.wobly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.wowd.wobly.exceptions.WoblyReadException;
import com.wowd.wobly.exceptions.WoblyWriteException;



/**
 * 
 * @author ikabiljo, sreckotoroman
 *
 */
public class WoblyUtils
{	
	public static enum Format {
		FIXED1(1),
		FIXED2(2),
		FIXED4(4),
		FIXED8(8),
		UNUSED1(),
		/** Format for positive numbers that are usually small */
		NUMBER_COMPRESSED(0),
		BYTES,
		BYTES_SIZE_COMPRESSED, 
		UNUSED2,
		/** just for default format */ 
		DEFAULT(null), 
		/** just like bytes, but size must be added manualy */
		NO_SIZE_FIELD(BYTES); 
			
		final int size;
		final Format realFormat;
		
		private Format() {
			this.size = -1;
			this.realFormat = this;
		}
		
		private Format(int size) {
			this.size = size;
			this.realFormat = this;
		}
		
		private Format(Format realFormat) {
			this.size = -1;
			this.realFormat = realFormat;
		}
		
		public int size() {
			return size;
		}
		
		public Format realFormat() {
			if (realFormat == null)
				throw new UnsupportedOperationException("real format for default");				
			return realFormat;
		}
	}
	

	public static final int TAG_FORMAT_BITS = 3;
	public static final int TAG_FORMAT_MASK = (1 << TAG_FORMAT_BITS) - 1;

	/** Given a tag value, determines the wire type (the lower 3 bits). */
	public static Format getFormatFromTag(int tag)
	{
		return Format.values()[tag & TAG_FORMAT_MASK];
	}

	/** Given a tag value, determines the field number (the upper 29 bits). */
	public static int getIDFromTag(int tag)
	{
		return tag >>> TAG_FORMAT_BITS;
	}

	/** Makes a tag value given a field number and wire type. */
	public static int makeTag(int id, Format format)
	{
		return (id << TAG_FORMAT_BITS) | format.ordinal();
	}
	
	public static final byte[]	NULL_BYTES 	= new byte[0];
	public static final short[]	NULL_SHORTS = new short[0];
	public static final int[]	NULL_INTS 	= new int[0]; 
	public static final long[]	NULL_LONGS 	= new long[0]; 

	public static final float[] NULL_FLOATS = new float[0]; 
	public static final double[]NULL_DOUBLES= new double[0];

	public static final char[]	NULL_CHARS	= new char[0]; 	
	public static final String[]NULL_STRINGS= new String[0]; 


	public static class Buffers {
		public static byte[] copyOf(byte[] a, int size) {
			byte[] res = new byte[size];
			System.arraycopy(a, 0, res, 0, Math.min(size, a.length));
			return res;
		}
		
		
		public static void appendVariableSize(ByteBuffer buf, int startPos) {
			int position = buf.position();
			int size = position-startPos-1;			
			int sizeSize = sizeVInt(size);
			if (sizeSize == 1) {
				buf.put(startPos, (byte)size);
			}
			else {
				byte[] array = buf.array();
				System.arraycopy(array, startPos+1, array, startPos+sizeSize, size);
				buf.position(startPos);
				putVInt(buf, size);
				buf.position(startPos+sizeSize+size);
			}
		}
		
		// STRINGS
		
		private static int rawSizeStringUTF8(String s) 
		{
			int res = 0;
			int length = s.length();
			for (int i = 0; i < length; i++)
			{
				char ch = s.charAt(i);
				// ranges from http://en.wikipedia.org/wiki/UTF-8
				if (ch <= 0x007f)
					res += 1;
				else if (ch <= 0x07FF)
					res += 2;
				else if (ch <= 0xd7ff)
					res += 3;
				else if (ch <= 0xDFFF)
				{
					res += 4;
					i++;
				}
				else res += 3;
			}
			return res;
		}
		
		public static int sizeStringUTF8(String s, boolean vSize)
		{			
			int size = rawSizeStringUTF8(s);
			return size + (vSize?sizeVInt(size):4);
		}
		
		public static void putStringUTF8(ByteBuffer buf, String s, boolean vSize) {
			try
			{
				byte[] bytes = s.getBytes("UTF-8");
				if (vSize)
					putVInt(buf, bytes.length);
				else buf.putInt(bytes.length);
				buf.put(bytes);
			}
			catch (UnsupportedEncodingException e)
			{
				throw new WoblyWriteException(e);
			}
		}		
	
		public static String getStringUTF8(ByteBuffer buf, boolean vSize) {
			try
			{
				int size = vSize?getVInt(buf):buf.getInt();
				String s = new String(buf.array(), buf.position() + buf.arrayOffset(), size, "UTF-8");
				buf.position(buf.position() + size);
				return s;
			}
			catch (UnsupportedEncodingException e)
			{
				throw new WoblyReadException(e);
			}
		}
		
		public static byte[] toByteArrayUTF8(String s, boolean vSize) {
			ByteBuffer buf = ByteBuffer.allocate(sizeStringUTF8(s, vSize));
			putStringUTF8(buf, s, vSize);
			return buf.array();
		}

	
		// -------
		
	
		// COMPRESSED INTEGERS
		
		/**
		 * Variable length integer.
		 * 
		 * @param buf
		 * @param value
		 */
		public static void putVInt(ByteBuffer buf, int value) {
			while ((value & ~0x7F) != 0) {
				buf.put((byte) ((value & 0x7F) | 0x80));
				value >>>= 7;
			}
			buf.put((byte) value);
		}
		
		public static int getVInt(ByteBuffer buf) {
			byte b = buf.get();
			if (b > 0)
				return b;
			int i = b & 0x7F;
			for (int s = 7; (b & 0x80) != 0; s += 7) {
				b = buf.get();
				i |= (b & 0x7F) << s;
			}
			return i;
		}

		public static int sizeVInt(int value) {
			if ((value & (0xffffffff << 7)) == 0)  return 1;
			if ((value & (0xffffffff << 14)) == 0) return 2;
			if ((value & (0xffffffff << 21)) == 0) return 3;
			if ((value & (0xffffffff << 28)) == 0) return 4;
			return 5;
		}
	
		public static int getVIntOrMax(ByteBuffer buf) {
			if (buf.remaining()<=0)
				return Integer.MAX_VALUE;
			return getVInt(buf);
		}
		
		/**
		 * Try to save space by writing i in variable length.
		 * <p>
		 * Negative values aren't supported.
		 * 
		 */
		public static void putVLong(ByteBuffer buf, long i) {
			if (i < 0) {
				throw new IllegalArgumentException("Positive numbers only.");
			}
			while ((i & ~0x7FL) != 0) {
				buf.put((byte) ((i & 0x7FL) | 0x80L));
				i >>>= 7;
			}
			buf.put((byte) i);
		}

		/**
		 * Read value encoded with {@link #putVLong(ByteBuffer, long)}
		 * 
		 */
		public static long getVLong(ByteBuffer buf) {
			byte b = buf.get();
			long i = b & 0x7FL;
			for (int s = 7; (b & 0x80L) != 0; s += 7) {
				b = buf.get();
				i |= (b & 0x7FL) << s;
			}
			return i;
		}

		public static int sizeVLong(long value) {
			if (value < 0) {
				throw new IllegalArgumentException("Positive numbers only.");
			}
			int count = 1;
			int shift = 7;
			while (true) {
				if ((value & ((~0L) << shift)) == 0)  
					return count;
				count++;
				shift+=7;
			}
		}

	}
}
