/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.commands.arguments.coordinates;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public interface Coordinates {
    public Vec3 m_6955_(CommandSourceStack var1);

    public Vec2 m_6970_(CommandSourceStack var1);

    default public BlockPos m_119568_(CommandSourceStack p_119569_) {
        return new BlockPos(this.m_6955_(p_119569_));
    }

    public boolean m_6888_();

    public boolean m_6892_();

    public boolean m_6900_();
}

