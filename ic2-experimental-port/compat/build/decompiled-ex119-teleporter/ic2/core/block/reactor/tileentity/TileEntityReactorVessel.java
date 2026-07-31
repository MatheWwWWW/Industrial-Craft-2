/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.reactor.tileentity;

import ic2.api.reactor.IReactorChamber;
import ic2.core.block.comp.FluidReactorLookup;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.tileentity.Ic2TileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityReactorVessel
extends Ic2TileEntity
implements IReactorChamber {
    protected final FluidReactorLookup lookup = this.addComponent(new FluidReactorLookup(this));

    protected TileEntityReactorVessel(BlockEntityType<? extends TileEntityReactorVessel> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public TileEntityNuclearReactorElectric getReactorInstance() {
        return this.lookup.getReactor();
    }

    @Override
    public boolean isWall() {
        return true;
    }
}

