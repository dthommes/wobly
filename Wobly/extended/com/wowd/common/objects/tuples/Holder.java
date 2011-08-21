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
import com.wowd.common.objects.tuples.interfaces.IHolder;

public class Holder<P> implements IHolder<P>
{
	protected final P p;
	
	public Holder() {
		p = null;
	}
	
	public Holder(P p) {
		this.p = p;
	}	
	
	public Holder(IHolder<P> holder) {
		p = holder.first();
	}
	
	public P get() {
		return p;
	}
	
	public P first() {
		return p;
	}


	@Override
	public String toString()
	{
		return "[" + p + "]";
	}
	
	@Override
	public int hashCode()
	{		
		return WowdUtils.Common.hashCode(p);
	}
	
	
	@Override
	public boolean equals(Object obj)
	{		
		if (obj==this) return true;
		if (obj==null) return false;
		if (this.getClass().equals(obj.getClass()))
		{
			Holder<?> v = (Holder<?>) obj;
			return WowdUtils.Common.equals(p, v.p);
		}
		return false;
	}

	
	public static class FirstComparator<P> implements Comparator<IHolder<P>> {

		private final Comparator<P> cmp;
		
		public FirstComparator(Comparator<P> cmp)
		{
			this.cmp = cmp;
		}
		
		public int compare(IHolder<P> o1, IHolder<P> o2)
		{
			return cmp.compare(o1.first(),o2.first());
		}
	}
	
	public static class FirstAscendingComparator<P extends Comparable<P>> implements Comparator<IHolder<P>> {

		public int compare(IHolder<P> o1, IHolder<P> o2)
		{
			return o1.first().compareTo(o2.first());
		}
	}

	public static class FirstDescendingComparator<P extends Comparable<P>> implements Comparator<IHolder<P>> {

		public int compare(IHolder<P> o1, IHolder<P> o2)
		{
			return -o1.first().compareTo(o2.first());
		}
	}

	
	public static <P> Comparator<IHolder<P>> firstComparator(Comparator<P> cmp) {
		return new FirstComparator<P>(cmp);
	}
	
	public static <P extends Comparable<P>> Comparator<IHolder<P>> firstAscendingComparator() {
		return new FirstAscendingComparator<P>();
	}
	
	public static <P extends Comparable<P>> Comparator<IHolder<P>> firstDescendingComparator() {
		return new FirstDescendingComparator<P>();
	}
	

}
