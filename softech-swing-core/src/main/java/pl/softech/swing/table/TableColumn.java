/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.swing.table;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {

	/**
	 * Policy to extract metadata
	 */
	public enum Policy {
		/** Follow only this node */
		DONT_FOLLOW,
		/** Follow only children nodes */
		CHILDREN_FOLLOW,
		/** Follow this and children nodes */
		FOLLOW
	}

	public String name();
	
	public String delimiter() default ".";

	public int order() default 0;

	public boolean editable() default false;

	public Policy policy() default Policy.DONT_FOLLOW;

	/**
	 * Header renderer
	 */
	public String hrenderer() default "";

	/**
	 * Cell renderer
	 */
	public String crenderer() default "";

	/**
	 * Cell editor
	 */
	public String ceditor() default "";

}
