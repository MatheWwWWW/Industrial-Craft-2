/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

interface IndexMerger {
    public DoubleList m_6241_();

    public boolean m_6200_(IndexConsumer var1);

    public int size();

    public static interface IndexConsumer {
        public boolean m_82908_(int var1, int var2, int var3);
    }
}

