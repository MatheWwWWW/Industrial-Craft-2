/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.tool;

import java.util.Set;

public interface IToolClass {
    public String getName();

    public Set<Object> getWhitelist();

    public Set<Object> getBlacklist();
}

