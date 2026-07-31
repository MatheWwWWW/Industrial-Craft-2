/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ILocation {
    default public Level getWorldObj() {
        if (this instanceof BlockEntity) {
            return ((BlockEntity)this).m_58904_();
        }
        throw new RuntimeException("ILocation needs to be implemented explicitly");
    }

    default public BlockPos getPosition() {
        if (this instanceof BlockEntity) {
            return ((BlockEntity)this).m_58899_();
        }
        throw new RuntimeException("ILocation needs to be implemented explicitly");
    }
}

