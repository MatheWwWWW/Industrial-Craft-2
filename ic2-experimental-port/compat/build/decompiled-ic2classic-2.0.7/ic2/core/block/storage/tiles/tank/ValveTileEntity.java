/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.block.storage.tiles.tank;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.core.block.storage.tiles.tank.BaseValveTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.apache.commons.lang3.mutable.MutableObject;

public class ValveTileEntity
extends BaseValveTileEntity {
    @NetworkInfo
    public RenderData render = new RenderData();

    public ValveTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("render");
        this.wrapper.setMapper(this::handleFluids);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ANIMATED_VALVE;
    }

    private MutableObject<Object> handleFluids(Capability<?> cap, MutableObject<Object> obj) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && obj.getValue() instanceof IFluidHandler) {
            obj.setValue((Object)new LinkingTank((IFluidHandler)obj.getValue(), this.render));
        }
        return obj;
    }

    public IFluidHandler getHandler(IFluidHandler handler) {
        return new LinkingTank(handler, this.render);
    }

    public class RenderData
    implements INetworkDataBuffer {
        long lastTime;

        public void addFluid() {
            if (this.lastTime >= ValveTileEntity.this.f_58857_.m_46467_()) {
                return;
            }
            this.lastTime = ValveTileEntity.this.f_58857_.m_46467_();
            ValveTileEntity.this.updateTileField("render");
        }

        @Override
        public void write(IOutputBuffer buffer) {
            buffer.writeLong(this.lastTime);
        }

        @Override
        public void read(IInputBuffer buffer) {
            this.lastTime = buffer.readLong();
        }

        public boolean shouldRender() {
            return ValveTileEntity.this.f_58857_.m_46467_() <= this.lastTime + 20L;
        }
    }

    static class LinkingTank
    implements IFluidHandler {
        IFluidHandler master;
        RenderData render;

        public LinkingTank(IFluidHandler master, RenderData render) {
            this.master = master;
            this.render = render;
        }

        public int getTanks() {
            return this.master.getTanks();
        }

        public FluidStack getFluidInTank(int tank) {
            return this.master.getFluidInTank(tank);
        }

        public int getTankCapacity(int tank) {
            return this.master.getTankCapacity(tank);
        }

        public boolean isFluidValid(int tank, FluidStack stack) {
            return this.master.isFluidValid(tank, stack);
        }

        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            int added = this.master.fill(resource, action);
            if (added > 0) {
                this.render.addFluid();
            }
            return added;
        }

        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            return this.master.drain(resource, action);
        }

        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return this.master.drain(maxDrain, action);
        }
    }
}

