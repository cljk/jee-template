package de.csmp.jeetemplate.common.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {
    /**
     * Bundle key
     * 
     * @return a valid bundle key or ""
     */
    @Nonbinding
    String key() default "";

    /**
     * Is it a mandatory property
     * 
     * @return true if mandator
     */
    @Nonbinding
    boolean mandatory() default false;

    /**
     * Default value if not provided
     * 
     * @return default value or ""
     */
    @Nonbinding
    String defaultValue() default "";
}
