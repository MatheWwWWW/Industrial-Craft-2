/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.storage.tiles.tank;

import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.block.base.features.ISpecialWrenchable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.storage.tiles.tank.BaseValveTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TeleportValveEntity
extends BaseValveTileEntity
implements ITickListener,
ISpecialWrenchable {
    public TeleporterTarget target = null;
    private BlockEntity targetCache = null;
    boolean emit = false;

    public TeleportValveEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TELEPORTER_VALVE;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.target = TeleporterTarget.read(compound.m_128469_("target"));
        this.emit = compound.m_128471_("emit");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        if (this.target != null) {
            compound.m_128365_("target", (Tag)this.target.write());
        }
        compound.m_128379_("emit", this.emit);
    }

    @Override
    public void onTick() {
        if (!this.emit || this.target == null || this.invClock(5)) {
            return;
        }
        IFluidHandler source = this.getTank(this);
        if (source == null) {
            return;
        }
        IFluidHandler target = this.getTank(this.getTargetCache());
        if (target == null) {
            return;
        }
        source.drain(target.fill(source.drain(500, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
    }

    private IFluidHandler getTank(BlockEntity target) {
        if (!(target instanceof TeleportValveEntity)) {
            return null;
        }
        TeleportValveEntity teleport = (TeleportValveEntity)target;
        BlockEntity tile = teleport.getMaster();
        if (tile == null) {
            return null;
        }
        return (IFluidHandler)tile.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.DOWN).orElse(null);
    }

    private BlockEntity getTargetCache() {
        if (this.target == null) {
            return null;
        }
        if (this.targetCache == null || this.targetCache.m_58901_()) {
            this.targetCache = this.target.getTile();
        }
        return this.targetCache;
    }

    @Override
    public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        boolean bl = this.emit = !this.emit;
        if (this.isSimulating()) {
            player.m_213846_((Component)this.translate(this.emit ? "gui.ic2.teleport_valve.send" : "gui.ic2.teleport_valve.receive"));
        }
        return true;
    }

    @Override
    public AABB hasSpecialAction(Direction side, Vec3 hit, Player player) {
        return new AABB(BlockPos.f_121853_);
    }

    public boolean setTarget(TeleporterTarget target) {
        if (this.target == null || !this.target.equals(target)) {
            this.target = new TeleporterTarget(target);
            this.targetCache = null;
            return true;
        }
        return false;
    }

    public boolean hasTarget(TeleporterTarget target) {
        return this.target != null && this.target.equals(target);
    }
}

