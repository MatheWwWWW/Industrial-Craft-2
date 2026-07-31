/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator;

import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

final class DefaultComparator
extends BaseComparator {
    static final BaseComparator INSTANCE = new DefaultComparator();

    private DefaultComparator() {
        super("default", (Component)Component.m_237115_((String)"comparator.ic2.default"));
    }

    @Override
    protected int createValue() {
        return 0;
    }
}

