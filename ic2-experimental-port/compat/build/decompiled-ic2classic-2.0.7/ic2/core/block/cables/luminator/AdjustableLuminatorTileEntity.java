/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package ic2.core.block.cables.luminator;

import ic2.api.network.buffer.NetworkInfo;
import ic2.core.IC2;
import ic2.core.block.cables.luminator.LuminatorTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AdjustableLuminatorTileEntity
extends LuminatorTileEntity {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    int lightLevel = 5;

    public AdjustableLuminatorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("lightLevel");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "light", this.lightLevel, 5);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.lightLevel = NBTUtils.getInt(compound, "light", 5);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(player) && player.m_21120_(hand).m_41619_()) {
            if (IC2.PLATFORM.isSimulating()) {
                this.lightLevel = Mth.m_14045_((int)(this.lightLevel + (player.m_6144_() ? -1 : 1)), (int)0, (int)15);
                this.onStateChanged();
                player.m_5661_((Component)this.translate("info.block.ic2.light_level", new Object[]{this.lightLevel}), false);
            }
            return true;
        }
        return super.onRightClick(player, hand, side, hit);
    }

    @Override
    public int getLightLevel() {
        return this.isActive() ? this.lightLevel : 0;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.LUMINATOR_ADJUSTABLE;
    }
}

