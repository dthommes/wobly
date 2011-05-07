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

import com.wowd.common.WowdUtils;
import com.wowd.wobly.examples.benchmark.simple.Image.Size;
import com.wowd.wobly.examples.benchmark.simple.Media.Player;

public class BenchTest
{
	
	public static void main(String[] args)
	{
		Media media = new Media(
				"http://javaone.com/keynote.mpg",
			    "Javaone Keynote",
			    640,
			    480,
			    "video/mpg4",
			    18000000,    // half hour in milliseconds
			    58982400,        // bitrate * duration in seconds / 8 bits per byte
			    262144,       // 256k
			    WowdUtils.Wrapper.asList("Bill Gates", "Steve Jobs"),
		        Player.JAVA,
		        null
			);
		
		Image image1 = new Image(
		        "http://javaone.com/keynote_large.jpg",
			    "Javaone Keynote",
			    1024,
			    768,
			    Size.LARGE
			);
		Image image2 = new Image(
				"http://javaone.com/keynote_small.jpg",
				"Javaone Keynote",
			    320,
			    240,
			    Size.SMALL
			);
		
		MediaContent content = new MediaContent(WowdUtils.Wrapper.asList(image1, image2), media);
		System.out.println(content.getSize());
	}
}
