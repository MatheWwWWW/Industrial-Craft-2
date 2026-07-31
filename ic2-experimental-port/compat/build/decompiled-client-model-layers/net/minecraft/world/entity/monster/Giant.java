/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class Giant
extends Monster {
    public Giant(EntityType<? extends Giant> p_32788_, Level p_32789_) {
        super((EntityType<? extends Monster>)p_32788_, p_32789_);
    }

    @Override
    protected float m_6431_(Pose p_32794_, EntityDimensions p_32795_) {
        return 10.440001f;
    }

    public static AttributeSupplier.Builder m_32796_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 100.0).m_22268_(Attributes.f_22279_, 0.5).m_22268_(Attributes.f_22281_, 50.0);
    }

    @Override
    public float m_5610_(BlockPos p_32791_, LevelReader p_32792_) {
        return p_32792_.m_220419_(p_32791_);
    }
}

