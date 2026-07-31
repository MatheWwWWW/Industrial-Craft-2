/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.entity.npc;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;

public class VillagerData {
    public static final int f_150017_ = 1;
    public static final int f_150018_ = 5;
    private static final int[] f_35551_ = new int[]{0, 10, 70, 150, 250};
    public static final Codec<VillagerData> f_35550_ = RecordCodecBuilder.create(p_35570_ -> p_35570_.group((App)Registry.f_122868_.m_194605_().fieldOf("type").orElseGet(() -> VillagerType.f_35821_).forGetter(p_150024_ -> p_150024_.f_35552_), (App)Registry.f_122869_.m_194605_().fieldOf("profession").orElseGet(() -> VillagerProfession.f_35585_).forGetter(p_150022_ -> p_150022_.f_35553_), (App)Codec.INT.fieldOf("level").orElse((Object)1).forGetter(p_150020_ -> p_150020_.f_35554_)).apply((Applicative)p_35570_, VillagerData::new));
    private final VillagerType f_35552_;
    private final VillagerProfession f_35553_;
    private final int f_35554_;

    public VillagerData(VillagerType p_35557_, VillagerProfession p_35558_, int p_35559_) {
        this.f_35552_ = p_35557_;
        this.f_35553_ = p_35558_;
        this.f_35554_ = Math.max(1, p_35559_);
    }

    public VillagerType m_35560_() {
        return this.f_35552_;
    }

    public VillagerProfession m_35571_() {
        return this.f_35553_;
    }

    public int m_35576_() {
        return this.f_35554_;
    }

    public VillagerData m_35567_(VillagerType p_35568_) {
        return new VillagerData(p_35568_, this.f_35553_, this.f_35554_);
    }

    public VillagerData m_35565_(VillagerProfession p_35566_) {
        return new VillagerData(this.f_35552_, p_35566_, this.f_35554_);
    }

    public VillagerData m_35561_(int p_35562_) {
        return new VillagerData(this.f_35552_, this.f_35553_, p_35562_);
    }

    public static int m_35572_(int p_35573_) {
        return VillagerData.m_35582_(p_35573_) ? f_35551_[p_35573_ - 1] : 0;
    }

    public static int m_35577_(int p_35578_) {
        return VillagerData.m_35582_(p_35578_) ? f_35551_[p_35578_] : 0;
    }

    public static boolean m_35582_(int p_35583_) {
        return p_35583_ >= 1 && p_35583_ < 5;
    }
}

