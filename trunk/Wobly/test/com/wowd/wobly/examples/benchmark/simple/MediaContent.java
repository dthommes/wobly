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
package com.wowd.wobly.examples.benchmark.simple;

import java.util.List;

import com.wowd.wobly.UnmodifiableWoblyImpl;
import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.WoblyField;
import com.wowd.wobly.annotations.WoblyTypeOptions;

@WoblyTypeOptions(unmodifiable = true, specialFormat = Format.NO_SIZE_FIELD)
public class MediaContent extends UnmodifiableWoblyImpl
{
	@WoblyField(id = -1, required = true)
	List<Image> images;

	@WoblyField(id = -2, required = true)
	Media media;

	public MediaContent(List<Image> images, Media media)
	{
		this.images = images;
		this.media = media;
	}
	//-------------- WOBLY AUTO GENERATED CODE FOR SERIALIZATION ----------
	//---------------------------------------------------------------------
	
	public static final com.wowd.wobly.WoblyReader<MediaContent> objectReader = new com.wowd.wobly.WoblyReaderImpl<MediaContent>() {
		@Override
		public MediaContent readObject(java.nio.ByteBuffer buf)
		{
			return read(buf);
		}};
	@Override
	public void write(final java.nio.ByteBuffer buf) {
		try {
			{
				this.media.write(buf);
			}
			{
				int startFieldMark = buf.position();
				buf.position(buf.position()+4);
				com.wowd.wobly.WoblyUtils.Buffers.putVInt(buf, this.images.size());
				for (Image v1 : this.images) {
					v1.write(buf);
				}
				buf.putInt(startFieldMark, buf.position() - startFieldMark - 4);
			}
		} catch (com.wowd.wobly.exceptions.WoblyWriteException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyWriteException(t);
		}
	}
	private MediaContent(final java.nio.ByteBuffer buf) {
		
		{
			this.media = Media.read(buf);
		}
		
		{
			buf.getInt(); //read size
			int size1 = com.wowd.wobly.WoblyUtils.Buffers.getVInt(buf);
			this.images = new java.util.ArrayList<Image>(size1);
			for (int i1 = 0; i1 < size1; i1++) {
				Image tmp1;
				tmp1 = Image.read(buf);
				this.images.add(tmp1);
			}
		}
	}
	@com.wowd.wobly.annotations.ReadStatic
	public static MediaContent read(java.nio.ByteBuffer buf) {
		try {
			MediaContent object = new MediaContent(buf);
			return object;
		} catch (com.wowd.wobly.exceptions.WoblyReadException e) {
			throw e;
		} catch (java.lang.Throwable t) {
			throw new com.wowd.wobly.exceptions.WoblyReadException(t);
		}
	}
	public static MediaContent read(byte[] buf) {
		return read(java.nio.ByteBuffer.wrap(buf));
	}
	@Override
	public int getSize() {
		int size = 0;
		{
			size += this.media.getSize();
		}
		{
			size += 4;
			size += com.wowd.wobly.WoblyUtils.Buffers.sizeVInt(this.images.size());
			for (Image v1 : this.images) {
				size += v1.getSize();
			}
		}
		return size;
	}
	
	//---------------------------------------------------------------------
	//-------------- END OF AUTO GENERATED CODE FOR SERIALIZATION ---------
}
