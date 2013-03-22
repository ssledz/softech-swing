package pl.softech.swing.table;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author Sławomir Śledź
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
