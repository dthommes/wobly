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
package com.wowd.wobly.examples;

import java.nio.ByteBuffer;
import java.util.Random;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.wowd.wobly.WoblyUtils;
import com.wowd.wobly.WoblyUtils.Buffers;


public class StringSpeedTest
{
	static String s;
	static ByteBuffer buf = ByteBuffer.allocate(1000);

	private static final int TOTAL = 100000000;
	private static final int N = 50;
	private static final int LOOPS = TOTAL/N;
	
	@BeforeClass
	public static void generateString() {
		StringBuilder sb = new StringBuilder(100);
		Random rand = new Random (991);
		for (int i = 0;i<N;i++)
			sb.append((char)(rand.nextInt(10) *rand.nextInt(17)));
		s = sb.toString();
		System.out.println(s);
		for (int i = 0; i< 2; i++) {
			new StringSpeedTest().testJavaPut();
			new StringSpeedTest().testJavaGet();
//		new StringSpeedTest().testBetterPut();
//		new StringSpeedTest().testBetterGet();
//		new StringSpeedTest().testNewPut();
//		new StringSpeedTest().testNewGet();
		}
	}
	

	@Test 
	public void testJavaPut() {
		int total = 0;
		for (int i = 0;i< LOOPS;i++)
		{
			buf.clear();
			WoblyUtils.Buffers.putStringUTF8(buf, s, false);
			total += buf.position();
		}
		System.out.println("testJavaPut:" + total);
	}
	
	@Test 
	public void testJavaGet() {
		int total = 0;
		buf.clear();
		WoblyUtils.Buffers.putStringUTF8(buf, s, false);

		for (int i = 0;i< LOOPS;i++)
		{
			buf.clear();
			Assert.assertEquals(s, WoblyUtils.Buffers.getStringUTF8(buf, false));
			total += buf.position();
		}
		System.out.println("testJavaGet:" + total);
	}
	
//	@Test 
//	public void testBetterPut() {
//		int total = 0;
//		for (int i = 0;i< LOOPS;i++)
//		{
//			buf.clear();
//			WoblyUtils.Buffers.putVString(buf, s);
//			total += buf.position();
//		}
//		System.out.println("testBetterPut:" + total);
//	}
//	
//	@Test 
//	public void testBetterGet() {
//		int total = 0;
//		buf.clear();
//		WoblyUtils.Buffers.putVString(buf, s);
//
//		for (int i = 0;i< LOOPS;i++)
//		{
//			buf.clear();
//			WoblyUtils.Buffers.getVString(buf);
//			total += buf.position();
//		}
//		System.out.println("testBetterGet:" + total);
//	}
//	
//	
//	@Test 
//	public void testNewPut() {
//		int total = 0;
//		for (int i = 0;i< LOOPS;i++)
//		{
//			buf.clear();
//			WoblyUtils.Buffers.putNewVString(buf, s);
//			total += buf.position();
//		}
//		System.out.println("testNewPut:" + total);
//	}
//	
//	@Test 
//	public void testNewGet() {
//		int total = 0;
//		buf.clear();
//		WoblyUtils.Buffers.putNewVString(buf, s);
//
//		for (int i = 0;i< LOOPS;i++)
//		{
//			buf.clear();
//			Assert.assertEquals(s, WoblyUtils.Buffers.getNewVString(buf));
//			total += buf.position();
//		}
//		System.out.println("testNewGet:" + total);
//	}
}
