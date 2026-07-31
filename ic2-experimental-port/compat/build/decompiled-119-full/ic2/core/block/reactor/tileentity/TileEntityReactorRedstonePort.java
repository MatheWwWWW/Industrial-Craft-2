/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.reactor.tileentity;

import ic2.core.block.comp.Redstone;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.reactor.tileentity.TileEntityReactorVessel;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityReactorRedstonePort
extends TileEntityReactorVessel {
    public final Redstone redstone = this.addComponent(new Redstone(this));

    public TileEntityReactorRedstonePort(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityReactorVessel>)Ic2BlockEntities.REACTOR_REDSTONE_PORT, blockPos, blockState);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateRedstoneLink();
    }

    private void updateRedstoneLink() {
        if (this.m_58904_().f_46443_) {
            return;
        }
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.lookup.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            this.redstone.linkTo(tileEntityNuclearReactorElectric.redstone);
        }
    }
}

