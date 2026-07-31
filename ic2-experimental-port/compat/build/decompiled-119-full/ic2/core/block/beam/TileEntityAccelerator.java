/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.beam;

import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityAccelerator
extends TileEntityElectricMachine {
    private static final Map<Direction.Axis, List<AABB>> FACING_AABBs = TileEntityAccelerator.makeAABBMap();

    public TileEntityAccelerator(BlockEntityType<? extends TileEntityAccelerator> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState, 5000, 2);
    }

    @Override
    protected List<AABB> getAabbs(boolean bl) {
        return FACING_AABBs.get(this.getFacing().m_122434_());
    }

    @Override
    protected int getLightOpacity() {
        return 0;
    }

    private static Map<Direction.Axis, List<AABB>> makeAABBMap() {
        EnumMap<Direction.Axis, List<AABB>> enumMap = new EnumMap<Direction.Axis, List<AABB>>(Direction.Axis.class);
        enumMap.put(Direction.Axis.X, Collections.singletonList(new AABB(0.0, 0.0, 0.25, 1.0, 1.0, 0.75)));
        enumMap.put(Direction.Axis.Y, Collections.singletonList(new AABB(0.0, 0.25, 0.0, 1.0, 0.75, 1.0)));
        enumMap.put(Direction.Axis.Z, Collections.singletonList(new AABB(0.25, 0.0, 0.0, 0.75, 1.0, 1.0)));
        return enumMap;
    }
}

