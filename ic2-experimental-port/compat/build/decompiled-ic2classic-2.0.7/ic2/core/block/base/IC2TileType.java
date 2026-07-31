/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base;

import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.util.Set;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class IC2TileType<T extends BlockEntity>
extends BlockEntityType<T> {
    public IC2TileType(BlockEntityType.BlockEntitySupplier<T> factoryIn) {
        super(factoryIn, (Set)ObjectSets.emptySet(), null);
    }

    public boolean m_155262_(BlockState blockIn) {
        return true;
    }
}

