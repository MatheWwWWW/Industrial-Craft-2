/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.block.base.features.ICollideable;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PickupTubeTileEntity
extends TubeTileEntity
implements ICollideable {
    boolean largeRadius = false;

    public PickupTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PICKUP_TUBE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putBoolean(compound, "large", this.largeRadius, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.largeRadius = compound.m_128471_("large");
    }

    @Override
    public void onEntityCollided(Entity entity) {
        if (this.isRendering() || !(entity instanceof ItemEntity)) {
            return;
        }
        ItemEntity item = (ItemEntity)entity;
        TransportedItem transported = new TransportedItem(item.m_32055_().m_41777_());
        item.m_142687_(Entity.RemovalReason.DISCARDED);
        this.addItem(transported, null);
        transported.setCentering();
    }

    @Override
    public void onTubeUpdate() {
        if (this.isRendering() || !this.largeRadius || this.invClock(5)) {
            return;
        }
        for (ItemEntity entity : this.f_58857_.m_45976_(ItemEntity.class, new AABB(this.m_58899_()).m_82400_(5.0))) {
            TransportedItem transported = new TransportedItem(entity.m_32055_().m_41777_());
            entity.m_142687_(Entity.RemovalReason.DISCARDED);
            this.addItem(transported, null);
            transported.setCentering();
        }
    }

    @Override
    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        if (action == TubeAction.LARGE_PICKUP_RADIUS) {
            boolean bl = this.largeRadius = !this.largeRadius;
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate(this.largeRadius ? "gui.ic2.tube.pickup.large_radius.enable" : "gui.ic2.tube.pickup.large_radius.disable"), false);
            }
            return true;
        }
        return false;
    }
}

