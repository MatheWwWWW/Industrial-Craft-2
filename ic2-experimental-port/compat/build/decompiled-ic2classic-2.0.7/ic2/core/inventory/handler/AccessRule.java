/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.inventory.handler;

import ic2.core.utils.collection.CollectionUtils;
import net.minecraft.network.chat.Component;

public enum AccessRule implements CollectionUtils.IIndexEnum
{
    DISABLED(0, "disabled"),
    IMPORT(1, "import"),
    EXPORT(2, "export"),
    BOTH(3, "both");

    int index;
    String translation;
    static final AccessRule[] VALUES;

    private AccessRule(int index, String name) {
        this.index = index;
        this.translation = "misc.ic2.access." + name;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    public Component getName() {
        return Component.m_237115_((String)this.translation);
    }

    public boolean canImport() {
        return this == IMPORT || this == BOTH;
    }

    public boolean canExport() {
        return this == EXPORT || this == BOTH;
    }

    public static AccessRule byIndex(int index) {
        return VALUES[index % VALUES.length];
    }

    public AccessRule cycleRule(AccessRule rule) {
        if (rule == DISABLED) {
            return DISABLED;
        }
        if (rule == BOTH) {
            if (this == BOTH) {
                return DISABLED;
            }
            if (this == DISABLED) {
                return EXPORT;
            }
            if (this == EXPORT) {
                return IMPORT;
            }
            return BOTH;
        }
        if (this == rule) {
            return DISABLED;
        }
        return rule;
    }

    static {
        VALUES = (AccessRule[])CollectionUtils.createSortedArray((CollectionUtils.IIndexEnum[])AccessRule.values());
    }
}

