/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap
 */
package net.minecraft.world.entity.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.entity.schedule.Keyframe;

public class Timeline {
    private final List<Keyframe> f_38055_ = Lists.newArrayList();
    private int f_38056_;

    public ImmutableList<Keyframe> m_150246_() {
        return ImmutableList.copyOf(this.f_38055_);
    }

    public Timeline m_38060_(int p_38061_, float p_38062_) {
        this.f_38055_.add(new Keyframe(p_38061_, p_38062_));
        this.m_38066_();
        return this;
    }

    public Timeline m_150247_(Collection<Keyframe> p_150248_) {
        this.f_38055_.addAll(p_150248_);
        this.m_38066_();
        return this;
    }

    private void m_38066_() {
        Int2ObjectAVLTreeMap $$0 = new Int2ObjectAVLTreeMap();
        this.f_38055_.forEach(arg_0 -> Timeline.m_38063_((Int2ObjectSortedMap)$$0, arg_0));
        this.f_38055_.clear();
        this.f_38055_.addAll((Collection<Keyframe>)$$0.values());
        this.f_38056_ = 0;
    }

    public float m_38058_(int p_38059_) {
        Keyframe $$7;
        if (this.f_38055_.size() <= 0) {
            return 0.0f;
        }
        Keyframe $$1 = this.f_38055_.get(this.f_38056_);
        Keyframe $$2 = this.f_38055_.get(this.f_38055_.size() - 1);
        boolean $$3 = p_38059_ < $$1.m_38010_();
        int $$4 = $$3 ? 0 : this.f_38056_;
        float $$5 = $$3 ? $$2.m_38011_() : $$1.m_38011_();
        int $$6 = $$4;
        while ($$6 < this.f_38055_.size() && ($$7 = this.f_38055_.get($$6)).m_38010_() <= p_38059_) {
            this.f_38056_ = $$6++;
            $$5 = $$7.m_38011_();
        }
        return $$5;
    }

    private static /* synthetic */ void m_38063_(Int2ObjectSortedMap p_38064_, Keyframe p_38065_) {
        p_38064_.put(p_38065_.m_38010_(), (Object)p_38065_);
    }
}

