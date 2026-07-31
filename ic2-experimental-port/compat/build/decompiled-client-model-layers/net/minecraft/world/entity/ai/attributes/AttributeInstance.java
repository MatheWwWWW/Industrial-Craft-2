/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  it.unimi.dsi.fastutil.objects.ObjectArraySet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeInstance {
    private final Attribute f_22088_;
    private final Map<AttributeModifier.Operation, Set<AttributeModifier>> f_22089_ = Maps.newEnumMap(AttributeModifier.Operation.class);
    private final Map<UUID, AttributeModifier> f_22090_ = new Object2ObjectArrayMap();
    private final Set<AttributeModifier> f_22091_ = new ObjectArraySet();
    private double f_22092_;
    private boolean f_22093_ = true;
    private double f_22094_;
    private final Consumer<AttributeInstance> f_22095_;

    public AttributeInstance(Attribute p_22097_, Consumer<AttributeInstance> p_22098_) {
        this.f_22088_ = p_22097_;
        this.f_22095_ = p_22098_;
        this.f_22092_ = p_22097_.m_22082_();
    }

    public Attribute m_22099_() {
        return this.f_22088_;
    }

    public double m_22115_() {
        return this.f_22092_;
    }

    public void m_22100_(double p_22101_) {
        if (p_22101_ == this.f_22092_) {
            return;
        }
        this.f_22092_ = p_22101_;
        this.m_22129_();
    }

    public Set<AttributeModifier> m_22104_(AttributeModifier.Operation p_22105_) {
        return this.f_22089_.computeIfAbsent(p_22105_, p_22124_ -> Sets.newHashSet());
    }

    public Set<AttributeModifier> m_22122_() {
        return ImmutableSet.copyOf(this.f_22090_.values());
    }

    @Nullable
    public AttributeModifier m_22111_(UUID p_22112_) {
        return this.f_22090_.get(p_22112_);
    }

    public boolean m_22109_(AttributeModifier p_22110_) {
        return this.f_22090_.get(p_22110_.m_22209_()) != null;
    }

    private void m_22133_(AttributeModifier p_22134_) {
        AttributeModifier $$1 = this.f_22090_.putIfAbsent(p_22134_.m_22209_(), p_22134_);
        if ($$1 != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        this.m_22104_(p_22134_.m_22217_()).add(p_22134_);
        this.m_22129_();
    }

    public void m_22118_(AttributeModifier p_22119_) {
        this.m_22133_(p_22119_);
    }

    public void m_22125_(AttributeModifier p_22126_) {
        this.m_22133_(p_22126_);
        this.f_22091_.add(p_22126_);
    }

    protected void m_22129_() {
        this.f_22093_ = true;
        this.f_22095_.accept(this);
    }

    public void m_22130_(AttributeModifier p_22131_) {
        this.m_22104_(p_22131_.m_22217_()).remove(p_22131_);
        this.f_22090_.remove(p_22131_.m_22209_());
        this.f_22091_.remove(p_22131_);
        this.m_22129_();
    }

    public void m_22120_(UUID p_22121_) {
        AttributeModifier $$1 = this.m_22111_(p_22121_);
        if ($$1 != null) {
            this.m_22130_($$1);
        }
    }

    public boolean m_22127_(UUID p_22128_) {
        AttributeModifier $$1 = this.m_22111_(p_22128_);
        if ($$1 != null && this.f_22091_.contains($$1)) {
            this.m_22130_($$1);
            return true;
        }
        return false;
    }

    public void m_22132_() {
        for (AttributeModifier $$0 : this.m_22122_()) {
            this.m_22130_($$0);
        }
    }

    public double m_22135_() {
        if (this.f_22093_) {
            this.f_22094_ = this.m_22137_();
            this.f_22093_ = false;
        }
        return this.f_22094_;
    }

    private double m_22137_() {
        double $$0 = this.m_22115_();
        for (AttributeModifier $$1 : this.m_22116_(AttributeModifier.Operation.ADDITION)) {
            $$0 += $$1.m_22218_();
        }
        double $$2 = $$0;
        for (AttributeModifier $$3 : this.m_22116_(AttributeModifier.Operation.MULTIPLY_BASE)) {
            $$2 += $$0 * $$3.m_22218_();
        }
        for (AttributeModifier $$4 : this.m_22116_(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
            $$2 *= 1.0 + $$4.m_22218_();
        }
        return this.f_22088_.m_6740_($$2);
    }

    private Collection<AttributeModifier> m_22116_(AttributeModifier.Operation p_22117_) {
        return this.f_22089_.getOrDefault((Object)p_22117_, Collections.emptySet());
    }

    public void m_22102_(AttributeInstance p_22103_) {
        this.f_22092_ = p_22103_.f_22092_;
        this.f_22090_.clear();
        this.f_22090_.putAll(p_22103_.f_22090_);
        this.f_22091_.clear();
        this.f_22091_.addAll(p_22103_.f_22091_);
        this.f_22089_.clear();
        p_22103_.f_22089_.forEach((p_22107_, p_22108_) -> this.m_22104_((AttributeModifier.Operation)((Object)p_22107_)).addAll((Collection<AttributeModifier>)p_22108_));
        this.m_22129_();
    }

    public CompoundTag m_22136_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128359_("Name", Registry.f_122866_.m_7981_(this.f_22088_).toString());
        $$0.m_128347_("Base", this.f_22092_);
        if (!this.f_22091_.isEmpty()) {
            ListTag $$1 = new ListTag();
            for (AttributeModifier $$2 : this.f_22091_) {
                $$1.add($$2.m_22219_());
            }
            $$0.m_128365_("Modifiers", $$1);
        }
        return $$0;
    }

    public void m_22113_(CompoundTag p_22114_) {
        this.f_22092_ = p_22114_.m_128459_("Base");
        if (p_22114_.m_128425_("Modifiers", 9)) {
            ListTag $$1 = p_22114_.m_128437_("Modifiers", 10);
            for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
                AttributeModifier $$3 = AttributeModifier.m_22212_($$1.m_128728_($$2));
                if ($$3 == null) continue;
                this.f_22090_.put($$3.m_22209_(), $$3);
                this.m_22104_($$3.m_22217_()).add($$3);
                this.f_22091_.add($$3);
            }
        }
        this.m_22129_();
    }
}

