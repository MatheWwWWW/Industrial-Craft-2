/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.core.block.generator.tileentity.TileEntityConversionGenerator;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.ConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityStirlingGenerator
extends TileEntityConversionGenerator {
    private final double productionpeerheat = 0.5f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Stirling");
    protected IHeatSource source;

    public TileEntityStirlingGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityConversionGenerator>)Ic2BlockEntities.STIRLING_GENERATOR, blockPos, blockState);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateSource();
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.updateSource();
    }

    @Override
    protected void onNeighborChange(Block block, BlockPos blockPos) {
        super.onNeighborChange(block, blockPos);
        if (this.m_58899_().m_121945_(this.getFacing()).equals((Object)blockPos)) {
            this.updateSource();
        }
    }

    protected void updateSource() {
        if (this.source == null || ((BlockEntity)this.source).m_58901_()) {
            BlockEntity blockEntity = this.f_58857_.m_7702_(this.f_58858_.m_121945_(this.getFacing()));
            this.source = blockEntity instanceof IHeatSource ? (IHeatSource)blockEntity : null;
        }
    }

    @Override
    protected int getEnergyAvailable() {
        if (this.source == null) {
            return 0;
        }
        assert (!((BlockEntity)this.source).m_58901_());
        return this.source.drawHeat(this.getFacing().m_122424_(), this.source.getConnectionBandwidth(this.getFacing().m_122424_()), true);
    }

    @Override
    protected void drawEnergyAvailable(int n) {
        if (this.source != null) {
            assert (!((BlockEntity)this.source).m_58901_());
            this.source.drawHeat(this.getFacing().m_122424_(), n, false);
        } else assert (false);
    }

    @Override
    protected double getMultiplier() {
        return this.productionpeerheat;
    }
}

