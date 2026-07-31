/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys.shapes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CollisionContext {
    public static CollisionContext m_82749_() {
        return EntityCollisionContext.f_82865_;
    }

    public static CollisionContext m_82750_(Entity p_82751_) {
        return new EntityCollisionContext(p_82751_);
    }

    public boolean m_6226_();

    public boolean m_6513_(VoxelShape var1, BlockPos var2, boolean var3);

    public boolean m_7142_(Item var1);

    public boolean m_203682_(FluidState var1, FluidState var2);
}

