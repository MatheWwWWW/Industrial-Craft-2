/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Containers {
    public static void m_19002_(Level p_19003_, BlockPos p_19004_, Container p_19005_) {
        Containers.m_18986_(p_19003_, p_19004_.m_123341_(), p_19004_.m_123342_(), p_19004_.m_123343_(), p_19005_);
    }

    public static void m_18998_(Level p_18999_, Entity p_19000_, Container p_19001_) {
        Containers.m_18986_(p_18999_, p_19000_.m_20185_(), p_19000_.m_20186_(), p_19000_.m_20189_(), p_19001_);
    }

    private static void m_18986_(Level p_18987_, double p_18988_, double p_18989_, double p_18990_, Container p_18991_) {
        for (int $$5 = 0; $$5 < p_18991_.m_6643_(); ++$$5) {
            Containers.m_18992_(p_18987_, p_18988_, p_18989_, p_18990_, p_18991_.m_8020_($$5));
        }
    }

    public static void m_19010_(Level p_19011_, BlockPos p_19012_, NonNullList<ItemStack> p_19013_) {
        p_19013_.forEach(p_19009_ -> Containers.m_18992_(p_19011_, p_19012_.m_123341_(), p_19012_.m_123342_(), p_19012_.m_123343_(), p_19009_));
    }

    public static void m_18992_(Level p_18993_, double p_18994_, double p_18995_, double p_18996_, ItemStack p_18997_) {
        double $$5 = EntityType.f_20461_.m_20678_();
        double $$6 = 1.0 - $$5;
        double $$7 = $$5 / 2.0;
        double $$8 = Math.floor(p_18994_) + p_18993_.f_46441_.m_188500_() * $$6 + $$7;
        double $$9 = Math.floor(p_18995_) + p_18993_.f_46441_.m_188500_() * $$6;
        double $$10 = Math.floor(p_18996_) + p_18993_.f_46441_.m_188500_() * $$6 + $$7;
        while (!p_18997_.m_41619_()) {
            ItemEntity $$11 = new ItemEntity(p_18993_, $$8, $$9, $$10, p_18997_.m_41620_(p_18993_.f_46441_.m_188503_(21) + 10));
            float $$12 = 0.05f;
            $$11.m_20334_(p_18993_.f_46441_.m_216328_(0.0, 0.11485000171139836), p_18993_.f_46441_.m_216328_(0.2, 0.11485000171139836), p_18993_.f_46441_.m_216328_(0.0, 0.11485000171139836));
            p_18993_.m_7967_($$11);
        }
    }
}

