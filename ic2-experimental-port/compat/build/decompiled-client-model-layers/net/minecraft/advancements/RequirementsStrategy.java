/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.advancements;

import java.util.Collection;

public interface RequirementsStrategy {
    public static final RequirementsStrategy f_15978_ = p_15984_ -> {
        String[][] $$1 = new String[p_15984_.size()][];
        int $$2 = 0;
        for (String $$3 : p_15984_) {
            $$1[$$2++] = new String[]{$$3};
        }
        return $$1;
    };
    public static final RequirementsStrategy f_15979_ = p_15982_ -> new String[][]{p_15982_.toArray(new String[0])};

    public String[][] m_15985_(Collection<String> var1);
}

