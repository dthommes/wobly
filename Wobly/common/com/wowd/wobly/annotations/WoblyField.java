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
package com.wowd.wobly.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wowd.wobly.WoblyUtils.Format;
import com.wowd.wobly.annotations.components.ComponentsDetails;



/**
 * Annotation should be placed on each field 
 * that should be serialized.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WoblyField {
	/**
	 * Id must be set for all fields. In a single class no two fields can have same value.<br>
	 * Scope for required and non-required fields is separate 
	 * (so required and non-required field can have same id).<br>
	 * Recommendation is to use negative id's for required, and >= 0 for non-required fields.<br>
	 * <br>
	 * Notes (only for non required fields):<br>
	 * - ids up to id = 15 have smallest overhead, (and up to id = 16k have medium overhead).<br>
	 * - id for all non-required fields must be >=0.<br>
	 */
	int id();
	
	/**
	 * Default value for a field. <br>
	 * Has double use-case:<br>
	 * - default value doesn't take any space, 
	 * so if this value is frequent, 
	 * it will decrease average byte size<br>
	 * - when new code reads object created by old code, 
	 * field is populated with default value.
	 * 
	 */
	String defaultValue() default "";
		
	ComponentsDetails componentsDetails() default @ComponentsDetails();
	
	Format specialFormat() default Format.DEFAULT;
	
	/**
	 * Should null and empty arrays mean the same thing,
	 * and if so, deserialization will always have 
	 * empty array, and never null array.<br>
	 * Default is <b>false</b>
	 * @return
	 */
	boolean arrayNullToEmpty() default false;
	
	/**
	 * Should null and empty collections mean the same thing,
	 * and if so, deserialization will always have 
	 * empty array, and never null array.<br>
	 * Default is <b>true</b>
	 * @return
	 */
	boolean collectionNullToEmpty() default true;
		
	/**
	 * Required fields are always present, cannot be removed later,
	 * or new ones added after object is first serialized. 
	 * (without loosing compatibility)
	 * <br>
	 * It uses less space, while sacrificing flexibility.
	 * <br> 
	 * Use only if you are certain that you are never going to wish to remove such a field.
	 * @return
	 */
	boolean required() default false;
}
