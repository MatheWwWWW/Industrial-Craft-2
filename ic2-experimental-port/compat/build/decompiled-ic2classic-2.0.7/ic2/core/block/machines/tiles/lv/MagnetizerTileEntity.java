/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package ic2.core.block.machines.tiles.lv;

import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.misc.IronFenceBlock;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MagnetizerTileEntity
extends BaseElectricTileEntity
implements ITickListener,
IClickable {
    int delay = 0;
    boolean upgraded = false;

    public MagnetizerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0, 32, 100);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128379_("upgraded", this.upgraded);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.upgraded = compound.m_128471_("upgraded");
        this.maxEnergy = this.upgraded ? 1000 : 100;
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public boolean allowsUI() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MAGNETIZER;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return false;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.8;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        if (!this.upgraded && player.m_21120_(hand).m_41720_() == IC2Items.OVERCLOCKER_UPGRADE) {
            this.upgraded = true;
            int n = this.maxEnergy = this.upgraded ? 1000 : 100;
            if (!player.m_7500_()) {
                player.m_21120_(hand).m_41774_(1);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onTick() {
        Block charge2;
        int needed;
        IronFenceBlock block;
        BlockPos.MutableBlockPos pos;
        BlockState state;
        Block block2;
        if (this.energy <= 0 || this.delay-- > 0) {
            return;
        }
        if (this.delay < 0) {
            ++this.delay;
        }
        this.handleRedstone();
        if (this.isRedstonePowered()) {
            this.delay = 5;
            return;
        }
        boolean notChanged = true;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int i = 1;
        while (this.isInRange(i) && (block2 = (state = this.f_58857_.m_8055_((BlockPos)(pos = mutable.m_122154_((Vec3i)this.m_58899_(), 0, i, 0)))).m_60734_()) instanceof IronFenceBlock) {
            block = (IronFenceBlock)block2;
            int charge2 = block.getCharge(state);
            needed = 15 - charge2;
            if ((needed = (int)Math.min((float)needed, (float)this.energy / (float)(this.upgraded ? Math.min(100, needed * 10) : needed))) > 0) {
                block.setCharge(this.f_58857_, (BlockPos)pos, state, charge2 + needed);
                this.useEnergy(this.upgraded ? needed * 10 : needed);
                notChanged = false;
            }
            ++i;
        }
        i = 1;
        while (this.isInRange(i) && (charge2 = (state = this.f_58857_.m_8055_((BlockPos)(pos = mutable.m_122154_((Vec3i)this.m_58899_(), 0, -i, 0)))).m_60734_()) instanceof IronFenceBlock) {
            block = (IronFenceBlock)charge2;
            int charge3 = block.getCharge(state);
            needed = 15 - charge3;
            if ((needed = (int)Math.min((float)needed, (float)this.energy / (float)(this.upgraded ? Math.min(100, needed * 10) : needed))) > 0) {
                block.setCharge(this.f_58857_, (BlockPos)pos, state, charge3 + needed);
                this.useEnergy(this.upgraded ? needed * 10 : needed);
                notChanged = false;
            }
            ++i;
        }
        if (notChanged) {
            this.delay = 5;
        } else if (this.delay == 0) {
            IC2.AUDIO.playSound(this, IC2Sounds.MAGNETIZER_PROCESSING);
            this.delay = -5;
        }
    }

    public boolean isInRange(int i) {
        return (this.upgraded || i <= 20) && this.energy >= (this.upgraded ? 10 : 1);
    }
}

