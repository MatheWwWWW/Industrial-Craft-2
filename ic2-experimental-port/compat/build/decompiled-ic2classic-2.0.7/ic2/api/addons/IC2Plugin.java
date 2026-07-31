/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.addons;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface IC2Plugin {
    public String name();

    public String id();

    public String version();

    public int requiredAPIVersion() default 0;
}

