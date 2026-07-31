/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 */
package net.minecraft.world.entity.ai.sensing;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class Sensing {
    private final Mob f_26784_;
    private final IntSet f_26785_ = new IntOpenHashSet();
    private final IntSet f_26786_ = new IntOpenHashSet();

    public Sensing(Mob p_26788_) {
        this.f_26784_ = p_26788_;
    }

    public void m_26789_() {
        this.f_26785_.clear();
        this.f_26786_.clear();
    }

    public boolean m_148306_(Entity p_148307_) {
        int $$1 = p_148307_.m_19879_();
        if (this.f_26785_.contains($$1)) {
            return true;
        }
        if (this.f_26786_.contains($$1)) {
            return false;
        }
        this.f_26784_.f_19853_.m_46473_().m_6180_("hasLineOfSight");
        boolean $$2 = this.f_26784_.m_142582_(p_148307_);
        this.f_26784_.f_19853_.m_46473_().m_7238_();
        if ($$2) {
            this.f_26785_.add($$1);
        } else {
            this.f_26786_.add($$1);
        }
        return $$2;
    }
}

