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
import java.util.Map.Entry;

import com.wowd.common.WowdUtils;
import com.wowd.common.objects.tuples.interfaces.IPair;

public class Pair<P,Q> extends Holder<P> implements IPair<P, Q> {

	protected final Q q;

	public Pair(Entry<P, Q> entry) {
		super(entry.getKey());
		this.q = entry.getValue();
	}
	
	public Pair(P p, Q q)
	{
		super(p);
		this.q = q;
	}

	public Pair(IPair<P, Q> pair) {
		super(pair);
		this.q = pair.second();
	}
		
	public Q second() {
		return q;
	}



	@Override
	public String toString()
	{
		return "[" + p +", "+ q +"]";
	}

	@Override
	public int hashCode()
	{		
		return WowdUtils.Common.hashCodePair(p, q);
	}
	
	
	@Override
	public boolean equals(Object obj)
	{	
		if (obj==this) return true;
		if (obj==null) return false;
		if (obj.getClass().equals(this.getClass()))
		{
			Pair<?, ?> pair = (Pair<?, ?>) obj;
			return WowdUtils.Common.equals(p,pair.p) && WowdUtils.Common.equals(q, pair.q);
		}
		return false;
	}

	public static <P, Q> Pair<P, Q> create(P p, Q q) {
		return new Pair<P, Q>(p,q);
	}

	
	public static class SecondComparator<Q> implements Comparator<IPair<?, Q>> {

		private final Comparator<Q> cmp;
		
		public SecondComparator(Comparator<Q> cmp)
		{
			this.cmp = cmp;
		}
		
		public int compare(IPair<?, Q> o1, IPair<?, Q> o2)
		{
			return cmp.compare(o1.second(),o2.second());
		}
	}
	
	public static class SecondAscendingComparator<Q extends Comparable<Q>> implements Comparator<IPair<?, Q>> {

		public int compare(IPair<?, Q> o1, IPair<?, Q> o2)
		{
			return o1.second().compareTo(o2.second());
		}
	}

	public static class SecondDescendingComparator<Q extends Comparable<Q>> implements Comparator<IPair<?, Q>> {

		public int compare(IPair<?, Q> o1, IPair<?, Q> o2)
		{
			return -o1.second().compareTo(o2.second());
		}
	}
	

	public static <Q> Comparator<IPair<?, Q>> secondComparator(Comparator<Q> cmp) {
		return new SecondComparator<Q>(cmp);
	}
	
	public static <Q extends Comparable<Q>> Comparator<IPair<?, Q>> secondAscendingComparator() {
		return new SecondAscendingComparator<Q>();
	}
	
	public static <Q extends Comparable<Q>> Comparator<IPair<?, Q>> secondDescendingComparator() {
		return new SecondDescendingComparator<Q>();
	}
}
