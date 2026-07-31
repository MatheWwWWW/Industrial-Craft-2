/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.server.bossevents;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.level.ServerPlayer;

public class CustomBossEvents {
    private final Map<ResourceLocation, CustomBossEvent> f_136290_ = Maps.newHashMap();

    @Nullable
    public CustomBossEvent m_136297_(ResourceLocation p_136298_) {
        return this.f_136290_.get(p_136298_);
    }

    public CustomBossEvent m_136299_(ResourceLocation p_136300_, Component p_136301_) {
        CustomBossEvent $$2 = new CustomBossEvent(p_136300_, p_136301_);
        this.f_136290_.put(p_136300_, $$2);
        return $$2;
    }

    public void m_136302_(CustomBossEvent p_136303_) {
        this.f_136290_.remove(p_136303_.m_136263_());
    }

    public Collection<ResourceLocation> m_136292_() {
        return this.f_136290_.keySet();
    }

    public Collection<CustomBossEvent> m_136304_() {
        return this.f_136290_.values();
    }

    public CompoundTag m_136307_() {
        CompoundTag $$0 = new CompoundTag();
        for (CustomBossEvent $$1 : this.f_136290_.values()) {
            $$0.m_128365_($$1.m_136263_().toString(), $$1.m_136289_());
        }
        return $$0;
    }

    public void m_136295_(CompoundTag p_136296_) {
        for (String $$1 : p_136296_.m_128431_()) {
            ResourceLocation $$2 = new ResourceLocation($$1);
            this.f_136290_.put($$2, CustomBossEvent.m_136272_(p_136296_.m_128469_($$1), $$2));
        }
    }

    public void m_136293_(ServerPlayer p_136294_) {
        for (CustomBossEvent $$1 : this.f_136290_.values()) {
            $$1.m_136283_(p_136294_);
        }
    }

    public void m_136305_(ServerPlayer p_136306_) {
        for (CustomBossEvent $$1 : this.f_136290_.values()) {
            $$1.m_136286_(p_136306_);
        }
    }
}

