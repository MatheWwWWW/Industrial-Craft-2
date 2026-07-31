/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.inventory.ContainerData;

public abstract class DataSlot {
    private int f_39399_;

    public static DataSlot m_39403_(final ContainerData p_39404_, final int p_39405_) {
        return new DataSlot(){

            @Override
            public int m_6501_() {
                return p_39404_.m_6413_(p_39405_);
            }

            @Override
            public void m_6422_(int p_39416_) {
                p_39404_.m_8050_(p_39405_, p_39416_);
            }
        };
    }

    public static DataSlot m_39406_(final int[] p_39407_, final int p_39408_) {
        return new DataSlot(){

            @Override
            public int m_6501_() {
                return p_39407_[p_39408_];
            }

            @Override
            public void m_6422_(int p_39424_) {
                p_39407_[p_39408_] = p_39424_;
            }
        };
    }

    public static DataSlot m_39401_() {
        return new DataSlot(){
            private int f_39426_;

            @Override
            public int m_6501_() {
                return this.f_39426_;
            }

            @Override
            public void m_6422_(int p_39429_) {
                this.f_39426_ = p_39429_;
            }
        };
    }

    public abstract int m_6501_();

    public abstract void m_6422_(int var1);

    public boolean m_39409_() {
        int $$0 = this.m_6501_();
        boolean $$1 = $$0 != this.f_39399_;
        this.f_39399_ = $$0;
        return $$1;
    }
}

