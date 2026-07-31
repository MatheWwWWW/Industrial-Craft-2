/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 */
package ic2.core.block.generator.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.core.block.generator.tileentity.TileEntityConversionGenerator;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.util.ConfigUtil;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@NotClassic
public class TileEntityStirlingGenerator
extends TileEntityConversionGenerator {
    private final double productionpeerheat = 0.5f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Stirling");
    protected IHeatSource source;

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateSource();
    }

    @Override
    protected void setFacing(EnumFacing facing) {
        super.setFacing(facing);
        this.updateSource();
    }

    @Override
    protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(neighbor, neighborPos);
        if (this.func_174877_v().func_177972_a(this.getFacing()).equals((Object)neighborPos)) {
            this.updateSource();
        }
    }

    protected void updateSource() {
        if (this.source == null || ((TileEntity)this.source).func_145837_r()) {
            TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(this.getFacing()));
            this.source = te instanceof IHeatSource ? (IHeatSource)te : null;
        }
    }

    @Override
    protected int getEnergyAvailable() {
        if (this.source == null) {
            return 0;
        }
        assert (!((TileEntity)this.source).func_145837_r());
        return this.source.drawHeat(this.getFacing().func_176734_d(), this.source.getConnectionBandwidth(this.getFacing().func_176734_d()), true);
    }

    @Override
    protected void drawEnergyAvailable(int amount) {
        if (this.source != null) {
            assert (!((TileEntity)this.source).func_145837_r());
            this.source.drawHeat(this.getFacing().func_176734_d(), amount, false);
        } else assert (false);
    }

    @Override
    protected double getMultiplier() {
        return this.productionpeerheat;
    }
}

