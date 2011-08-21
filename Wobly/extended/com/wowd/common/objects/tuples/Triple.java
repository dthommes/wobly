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
package com.wowd.common.objects.tuples;

import java.util.Comparator;

import com.wowd.common.WowdUtils;
import com.wowd.common.objects.tuples.interfaces.ITriple;

public class Triple<P, Q, R> extends Pair<P, Q> implements ITriple<P, Q, R>
{
	protected final R r;

	public Triple(P p, Q q, R r)
	{
		super(p,q);
		this.r = r;
	}

	public Triple(ITriple<P, Q, R> triple) {
		super(triple);
		this.r = triple.third();
	}
	
	public R third()
	{
		return r;
	}


	
	@Override
	public String toString()
	{
		return "[" + p +", "+ q +", "+ r +"]";
	}

	@Override
	public int hashCode()
	{		
		return WowdUtils.Common.hashCodeTriple(p, q, r);
	}
	
	
	@Override
	public boolean equals(Object obj)
	{	
		if (obj==this) return true;
		if (obj==null) return false;
		if (obj.getClass().equals(this.getClass()))
		{
			Triple<?, ?, ?> triple = (Triple<?, ?, ?>) obj;
			return WowdUtils.Common.equals(p,triple.p) && WowdUtils.Common.equals(q, triple.q) && WowdUtils.Common.equals(r, triple.r);
		}
		return false;
	}

	public static <P, Q, R> Triple<P, Q, R> create(P p, Q q, R r) {
		return new Triple<P, Q, R>(p,q,r);
	}

	public static class ThirdComparator<R> implements Comparator<ITriple<?, ?, R>> {

		private final Comparator<R> cmp;
		
		public ThirdComparator(Comparator<R> cmp)
		{
			this.cmp = cmp;
		}
		
		public int compare(ITriple<?, ?, R> o1, ITriple<?, ?, R> o2)
		{
			return cmp.compare(o1.third(),o2.third());
		}
	}
	
	public static class ThirdAscendingComparator<R extends Comparable<R>> implements Comparator<ITriple<?, ?, R>> {

		public int compare(ITriple<?, ?, R> o1, ITriple<?, ?, R> o2)
		{
			return o1.third().compareTo(o2.third());
		}
	}

	public static class ThirdDescendingComparator<R extends Comparable<R>> implements Comparator<ITriple<?, ?, R>> {

		public int compare(ITriple<?, ?, R> o1, ITriple<?, ?, R> o2)
		{
			return -o1.third().compareTo(o2.third());
		}
	}
	

	public static <R> Comparator<ITriple<?, ?, R>> thirdComparator(Comparator<R> cmp) {
		return new ThirdComparator<R>(cmp);
	}
	
	public static <R extends Comparable<R>> Comparator<ITriple<?, ?, R>> thirdAscendingComparator() {
		return new ThirdAscendingComparator<R>();
	}
	
	public static <R extends Comparable<R>> Comparator<ITriple<?, ?, R>> thirdDescendingComparator() {
		return new ThirdDescendingComparator<R>();
	}
	

}
