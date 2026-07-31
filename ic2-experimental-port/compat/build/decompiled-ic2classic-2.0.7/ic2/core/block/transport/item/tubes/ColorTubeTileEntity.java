/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.block.base.features.ISpecialWrenchable;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ColorTubeTileEntity
extends TubeTileEntity
implements ISpecialWrenchable,
ICopyableSettings {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int color;
    @NetworkInfo
    public boolean requests = true;

    public ColorTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("color", "requests");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.DYEING_TUBE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "color", this.color, 0);
        NBTUtils.putBoolean(compound, "state", this.requests, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.color = compound.m_128451_("color");
        this.requests = compound.m_128471_("state");
    }

    @Override
    public void saveSettings(CompoundTag tag) {
        NBTUtils.putByte(tag, "color", this.color, 0);
        NBTUtils.putBoolean(tag, "state", this.requests, false);
        this.updateTileFields("color", "requests");
    }

    @Override
    public void loadSettings(CompoundTag tag) {
        this.color = tag.m_128451_("color");
        this.requests = tag.m_128471_("state");
    }

    @Override
    public boolean onCenterReached(TransportedItem item) {
        if (!this.requests && item.getRequestId() != null) {
            return false;
        }
        item.setColor(this.color == 0 ? null : DyeColor.m_41053_((int)(this.color - 1)));
        return false;
    }

    @Override
    public boolean setColor(Direction dir, DyeColor color) {
        if (this.foamed < 2) {
            int newValue;
            int n = newValue = color == null ? 0 : color.m_41060_() + 1;
            if (this.color == newValue) {
                return false;
            }
            this.color = newValue;
            this.updateTileField("color");
            return true;
        }
        return super.setColor(dir, color);
    }

    @Override
    public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        if (super.doSpecialAction(side, hit, player)) {
            return true;
        }
        if (player.m_6144_()) {
            this.requests = !this.requests;
            this.updateTileField("requests");
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate(this.requests ? "gui.ic2.color_tube.request.handle" : "gui.ic2.color_tube.request.not_handle"), false);
            }
        }
        return false;
    }
}

