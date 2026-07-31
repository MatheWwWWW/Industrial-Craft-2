/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.block.comp;

import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.util.WorldSearchUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidReactorLookup
extends TileEntityComponent {
    private TileEntityNuclearReactorElectric reactor;
    private long lastReactorUpdate;

    public FluidReactorLookup(TileEntityBlock parent) {
        super(parent);
    }

    public TileEntityNuclearReactorElectric getReactor() {
        long time = this.parent.func_145831_w().func_82737_E();
        if (time != this.lastReactorUpdate) {
            this.updateReactor();
            this.lastReactorUpdate = time;
        } else if (this.reactor != null && (this.reactor.func_145837_r() || !this.reactor.isFluidCooled())) {
            this.reactor = null;
        }
        return this.reactor;
    }

    private void updateReactor() {
        BlockPos pos;
        int dist = 2;
        World world = this.parent.func_145831_w();
        if (!world.func_175697_a(pos = this.parent.func_174877_v(), 2)) {
            this.reactor = null;
            return;
        }
        if (this.reactor != null && !this.reactor.func_145837_r() && this.reactor.isFluidCooled() && this.reactor.func_145831_w() == world && world.func_175625_s(this.reactor.func_174877_v()) == this.reactor) {
            BlockPos reactorPos = this.reactor.func_174877_v();
            int dx = Math.abs(pos.func_177958_n() - reactorPos.func_177958_n());
            int dy = Math.abs(pos.func_177956_o() - reactorPos.func_177956_o());
            int dz = Math.abs(pos.func_177952_p() - reactorPos.func_177952_p());
            if (dx <= 2 && dy <= 2 && dz <= 2 && (dx == 2 || dy == 2 || dz == 2)) {
                return;
            }
        }
        this.reactor = null;
        WorldSearchUtil.findTileEntities(world, pos, 2, new WorldSearchUtil.ITileEntityResultHandler(){

            @Override
            public boolean onMatch(TileEntity te) {
                TileEntityNuclearReactorElectric cReactor;
                if (te instanceof TileEntityNuclearReactorElectric && (cReactor = (TileEntityNuclearReactorElectric)te).isFluidCooled()) {
                    FluidReactorLookup.this.reactor = cReactor;
                    return true;
                }
                return false;
            }
        });
    }
}

