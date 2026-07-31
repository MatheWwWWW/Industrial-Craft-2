/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.gametest.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface GameTest {
    public int m_177042_() default 100;

    public String m_177043_() default "defaultBatch";

    public int m_177044_() default 0;

    public boolean m_177045_() default true;

    public String m_177046_() default "";

    public long m_177047_() default 0L;

    public int m_177048_() default 1;

    public int m_177049_() default 1;
}

