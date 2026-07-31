/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class AdvancementList {
    private static final Logger f_139325_ = LogUtils.getLogger();
    private final Map<ResourceLocation, Advancement> f_139326_ = Maps.newHashMap();
    private final Set<Advancement> f_139327_ = Sets.newLinkedHashSet();
    private final Set<Advancement> f_139328_ = Sets.newLinkedHashSet();
    @Nullable
    private Listener f_139329_;

    private void m_139339_(Advancement p_139340_) {
        for (Advancement $$1 : p_139340_.m_138322_()) {
            this.m_139339_($$1);
        }
        f_139325_.info("Forgot about advancement {}", (Object)p_139340_.m_138327_());
        this.f_139326_.remove(p_139340_.m_138327_());
        if (p_139340_.m_138319_() == null) {
            this.f_139327_.remove(p_139340_);
            if (this.f_139329_ != null) {
                this.f_139329_.m_5504_(p_139340_);
            }
        } else {
            this.f_139328_.remove(p_139340_);
            if (this.f_139329_ != null) {
                this.f_139329_.m_5516_(p_139340_);
            }
        }
    }

    public void m_139335_(Set<ResourceLocation> p_139336_) {
        for (ResourceLocation $$1 : p_139336_) {
            Advancement $$2 = this.f_139326_.get($$1);
            if ($$2 == null) {
                f_139325_.warn("Told to remove advancement {} but I don't know what that is", (Object)$$1);
                continue;
            }
            this.m_139339_($$2);
        }
    }

    public void m_139333_(Map<ResourceLocation, Advancement.Builder> p_139334_) {
        HashMap $$1 = Maps.newHashMap(p_139334_);
        while (!$$1.isEmpty()) {
            boolean $$2 = false;
            Iterator $$3 = $$1.entrySet().iterator();
            while ($$3.hasNext()) {
                Map.Entry $$4 = $$3.next();
                ResourceLocation $$5 = (ResourceLocation)$$4.getKey();
                Advancement.Builder $$6 = (Advancement.Builder)$$4.getValue();
                if (!$$6.m_138392_(this.f_139326_::get)) continue;
                Advancement $$7 = $$6.m_138403_($$5);
                this.f_139326_.put($$5, $$7);
                $$2 = true;
                $$3.remove();
                if ($$7.m_138319_() == null) {
                    this.f_139327_.add($$7);
                    if (this.f_139329_ == null) continue;
                    this.f_139329_.m_5513_($$7);
                    continue;
                }
                this.f_139328_.add($$7);
                if (this.f_139329_ == null) continue;
                this.f_139329_.m_5505_($$7);
            }
            if ($$2) continue;
            for (Map.Entry $$8 : $$1.entrySet()) {
                f_139325_.error("Couldn't load advancement {}: {}", $$8.getKey(), $$8.getValue());
            }
        }
        f_139325_.info("Loaded {} advancements", (Object)this.f_139326_.size());
    }

    public void m_139332_() {
        this.f_139326_.clear();
        this.f_139327_.clear();
        this.f_139328_.clear();
        if (this.f_139329_ != null) {
            this.f_139329_.m_7204_();
        }
    }

    public Iterable<Advancement> m_139343_() {
        return this.f_139327_;
    }

    public Collection<Advancement> m_139344_() {
        return this.f_139326_.values();
    }

    @Nullable
    public Advancement m_139337_(ResourceLocation p_139338_) {
        return this.f_139326_.get(p_139338_);
    }

    public void m_139341_(@Nullable Listener p_139342_) {
        this.f_139329_ = p_139342_;
        if (p_139342_ != null) {
            for (Advancement $$1 : this.f_139327_) {
                p_139342_.m_5513_($$1);
            }
            for (Advancement $$2 : this.f_139328_) {
                p_139342_.m_5505_($$2);
            }
        }
    }

    public static interface Listener {
        public void m_5513_(Advancement var1);

        public void m_5504_(Advancement var1);

        public void m_5505_(Advancement var1);

        public void m_5516_(Advancement var1);

        public void m_7204_();
    }
}

