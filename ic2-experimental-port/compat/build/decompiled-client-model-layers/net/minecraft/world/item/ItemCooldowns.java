/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;

public class ItemCooldowns {
    private final Map<Item, CooldownInstance> f_41515_ = Maps.newHashMap();
    private int f_41516_;

    public boolean m_41519_(Item p_41520_) {
        return this.m_41521_(p_41520_, 0.0f) > 0.0f;
    }

    public float m_41521_(Item p_41522_, float p_41523_) {
        CooldownInstance $$2 = this.f_41515_.get(p_41522_);
        if ($$2 != null) {
            float $$3 = $$2.f_41534_ - $$2.f_41533_;
            float $$4 = (float)$$2.f_41534_ - ((float)this.f_41516_ + p_41523_);
            return Mth.m_14036_($$4 / $$3, 0.0f, 1.0f);
        }
        return 0.0f;
    }

    public void m_41518_() {
        ++this.f_41516_;
        if (!this.f_41515_.isEmpty()) {
            Iterator<Map.Entry<Item, CooldownInstance>> $$0 = this.f_41515_.entrySet().iterator();
            while ($$0.hasNext()) {
                Map.Entry<Item, CooldownInstance> $$1 = $$0.next();
                if ($$1.getValue().f_41534_ > this.f_41516_) continue;
                $$0.remove();
                this.m_7432_($$1.getKey());
            }
        }
    }

    public void m_41524_(Item p_41525_, int p_41526_) {
        this.f_41515_.put(p_41525_, new CooldownInstance(this.f_41516_, this.f_41516_ + p_41526_));
        this.m_6899_(p_41525_, p_41526_);
    }

    public void m_41527_(Item p_41528_) {
        this.f_41515_.remove(p_41528_);
        this.m_7432_(p_41528_);
    }

    protected void m_6899_(Item p_41529_, int p_41530_) {
    }

    protected void m_7432_(Item p_41531_) {
    }

    static class CooldownInstance {
        final int f_41533_;
        final int f_41534_;

        CooldownInstance(int p_186358_, int p_186359_) {
            this.f_41533_ = p_186358_;
            this.f_41534_ = p_186359_;
        }
    }
}

