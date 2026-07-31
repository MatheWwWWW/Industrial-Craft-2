/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.slf4j.Logger;

public class AttributeMap {
    private static final Logger f_22138_ = LogUtils.getLogger();
    private final Map<Attribute, AttributeInstance> f_22139_ = Maps.newHashMap();
    private final Set<AttributeInstance> f_22140_ = Sets.newHashSet();
    private final AttributeSupplier f_22141_;

    public AttributeMap(AttributeSupplier p_22144_) {
        this.f_22141_ = p_22144_;
    }

    private void m_22157_(AttributeInstance p_22158_) {
        if (p_22158_.m_22099_().m_22086_()) {
            this.f_22140_.add(p_22158_);
        }
    }

    public Set<AttributeInstance> m_22145_() {
        return this.f_22140_;
    }

    public Collection<AttributeInstance> m_22170_() {
        return this.f_22139_.values().stream().filter(p_22184_ -> p_22184_.m_22099_().m_22086_()).collect(Collectors.toList());
    }

    @Nullable
    public AttributeInstance m_22146_(Attribute p_22147_) {
        return this.f_22139_.computeIfAbsent(p_22147_, p_22188_ -> this.f_22141_.m_22250_(this::m_22157_, (Attribute)p_22188_));
    }

    public boolean m_22171_(Attribute p_22172_) {
        return this.f_22139_.get(p_22172_) != null || this.f_22141_.m_22258_(p_22172_);
    }

    public boolean m_22154_(Attribute p_22155_, UUID p_22156_) {
        AttributeInstance $$2 = this.f_22139_.get(p_22155_);
        return $$2 != null ? $$2.m_22111_(p_22156_) != null : this.f_22141_.m_22255_(p_22155_, p_22156_);
    }

    public double m_22181_(Attribute p_22182_) {
        AttributeInstance $$1 = this.f_22139_.get(p_22182_);
        return $$1 != null ? $$1.m_22135_() : this.f_22141_.m_22245_(p_22182_);
    }

    public double m_22185_(Attribute p_22186_) {
        AttributeInstance $$1 = this.f_22139_.get(p_22186_);
        return $$1 != null ? $$1.m_22115_() : this.f_22141_.m_22253_(p_22186_);
    }

    public double m_22173_(Attribute p_22174_, UUID p_22175_) {
        AttributeInstance $$2 = this.f_22139_.get(p_22174_);
        return $$2 != null ? $$2.m_22111_(p_22175_).m_22218_() : this.f_22141_.m_22247_(p_22174_, p_22175_);
    }

    public void m_22161_(Multimap<Attribute, AttributeModifier> p_22162_) {
        p_22162_.asMap().forEach((p_22152_, p_22153_) -> {
            AttributeInstance $$2 = this.f_22139_.get(p_22152_);
            if ($$2 != null) {
                p_22153_.forEach($$2::m_22130_);
            }
        });
    }

    public void m_22178_(Multimap<Attribute, AttributeModifier> p_22179_) {
        p_22179_.forEach((p_22149_, p_22150_) -> {
            AttributeInstance $$2 = this.m_22146_((Attribute)p_22149_);
            if ($$2 != null) {
                $$2.m_22130_((AttributeModifier)p_22150_);
                $$2.m_22118_((AttributeModifier)p_22150_);
            }
        });
    }

    public void m_22159_(AttributeMap p_22160_) {
        p_22160_.f_22139_.values().forEach(p_22177_ -> {
            AttributeInstance $$1 = this.m_22146_(p_22177_.m_22099_());
            if ($$1 != null) {
                $$1.m_22102_((AttributeInstance)p_22177_);
            }
        });
    }

    public ListTag m_22180_() {
        ListTag $$0 = new ListTag();
        for (AttributeInstance $$1 : this.f_22139_.values()) {
            $$0.add($$1.m_22136_());
        }
        return $$0;
    }

    public void m_22168_(ListTag p_22169_) {
        for (int $$1 = 0; $$1 < p_22169_.size(); ++$$1) {
            CompoundTag $$2 = p_22169_.m_128728_($$1);
            String $$3 = $$2.m_128461_("Name");
            Util.m_137521_(Registry.f_122866_.m_6612_(ResourceLocation.m_135820_($$3)), p_22167_ -> {
                AttributeInstance $$2 = this.m_22146_((Attribute)p_22167_);
                if ($$2 != null) {
                    $$2.m_22113_($$2);
                }
            }, () -> f_22138_.warn("Ignoring unknown attribute '{}'", (Object)$$3));
        }
    }
}

