/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.tubes.ITube;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class DirectionalTubeTileEntity
extends TubeTileEntity
implements IRedstoneListener {
    boolean lastRedstone = false;
    int invalids = 0;
    @NetworkInfo
    boolean blockFully;

    public DirectionalTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("facing", "blockFully");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putBoolean(compound, "last", this.lastRedstone, false);
        NBTUtils.putBoolean(compound, "full", this.blockFully, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.lastRedstone = compound.m_128471_("last");
        this.blockFully = compound.m_128471_("full");
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack;
        if (!this.blockFully && (stack = player.m_21120_(hand)).m_41720_() == Items.f_41832_) {
            this.blockFully = true;
            stack.m_41774_(1);
            this.updateTileField("blockFully");
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate("gui.ic2.tube.directional_tube.enable"), false);
            }
            return true;
        }
        return super.onRightClick(player, hand, side, hit);
    }

    @Override
    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        if (action == TubeAction.BLOCK_OUTPUT) {
            this.blockFully = !this.blockFully;
            this.updateTileField("blockFully");
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate(this.blockFully ? "gui.ic2.tube.directional_tube.enable" : "gui.ic2.tube.directional_tube.disable"), false);
            }
            return true;
        }
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.DIRECTIONAL_TUBE;
    }

    @Override
    public boolean canLoseUpdateTick() {
        return false;
    }

    @Override
    protected void onCachesUpdated() {
        super.onCachesUpdated();
        this.invalids = 0;
        for (Direction dir : this.tubes) {
            if (((ITube)this.tubes.getHandler(dir)).getTubeType() != ITube.TubeType.EXTRACTION) continue;
            this.invalids |= 1 << dir.m_122411_();
        }
    }

    @Override
    public boolean canAddItem(TransportedItem item, Direction side) {
        return !this.blockFully || side != this.getFacing();
    }

    @Override
    public DirectionList getValidDirections(TransportedItem item) {
        return DirectionList.ofFacing(this.getFacing()).remove(DirectionList.ofNumber(this.connectivity).invert());
    }

    @Override
    public void onTubeUpdate() {
        if (this.isRendering()) {
            return;
        }
        this.handleRedstone();
        if (this.lastRedstone != this.isRedstonePowered()) {
            this.lastRedstone = this.isRedstonePowered();
            this.setFacing(DirectionList.ofNumber(this.connectivity).remove(DirectionList.ofNumber(this.invalids)).getNextFacing(this.getFacing()));
        }
    }

    @Override
    public boolean isRotatable() {
        return true;
    }

    @Override
    public boolean canSetFacingInternal(Direction dir) {
        return dir != this.getFacing() && (this.connectivity & 1 << dir.m_122411_()) != 0;
    }

    @Override
    public boolean allowWeakSignal(Direction dir) {
        return true;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }

    @Override
    protected boolean needsInitialRedstoneCheck() {
        return true;
    }
}

