/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package ic2.api.tile;

import ic2.api.util.CoreAccessRef;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RotorRegistry {
    public static <T extends BlockEntity> void registerRotorProvider(BlockEntityType<T> blockEntityType) {
        CoreAccessRef.get().registerRotorProvider(blockEntityType);
    }
}

