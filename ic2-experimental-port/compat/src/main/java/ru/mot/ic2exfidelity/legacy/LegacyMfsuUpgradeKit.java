package ru.mot.ic2exfidelity.legacy;

import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.block.wiring.tileentity.TileEntityElectricBlock;
import ic2.core.ref.Ic2Blocks;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Converts an MFE (or MFE chargepad) to its MFSU counterpart without data loss. */
public final class LegacyMfsuUpgradeKit extends Item {
    public LegacyMfsuUpgradeKit(Properties properties) {
        super(properties);
    }

    // Forge adds this virtual hook to Item while transforming the game classes.
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.m_43725_();
        if (level.f_46443_) {
            return InteractionResult.PASS;
        }
        if (!upgradeAt(level, context.m_8083_())) {
            return InteractionResult.PASS;
        }

        Player player = context.m_43723_();
        if (player == null || !player.m_150110_().f_35937_) {
            stack.m_41774_(1);
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean upgradeAt(Level level, net.minecraft.core.BlockPos position) {
        BlockState oldState = level.m_8055_(position);
        Block replacement;
        if (oldState.m_60734_() == Ic2Blocks.MFE) {
            replacement = Ic2Blocks.MFSU;
        } else if (oldState.m_60734_() == Ic2Blocks.MFE_CHARGEPAD) {
            replacement = Ic2Blocks.MFSU_CHARGEPAD;
        } else {
            return false;
        }

        BlockEntity oldBlockEntity = level.m_7702_(position);
        if (!(oldBlockEntity instanceof TileEntityElectricBlock)) {
            return false;
        }
        CompoundTag data = oldBlockEntity.m_187482_();

        BlockState newState = replacement.m_49966_();
        if (oldState.m_60734_() instanceof Ic2TileEntityBlock oldBlock
                && replacement instanceof Ic2TileEntityBlock newBlock
                && oldState.m_61138_(oldBlock.facingProperty)
                && newState.m_61138_(newBlock.facingProperty)) {
            Direction facing = oldState.m_61143_(oldBlock.facingProperty);
            if (newBlock.getSupportedFacings().contains(facing)) {
                newState = newState.m_61124_(newBlock.facingProperty, facing);
            }
        }

        if (!level.m_7731_(position, newState, 3)) {
            return false;
        }
        BlockEntity newBlockEntity = level.m_7702_(position);
        if (!(newBlockEntity instanceof TileEntityElectricBlock electricBlock)) {
            return false;
        }
        electricBlock.m_142466_(data);
        electricBlock.onUpgraded();
        electricBlock.m_6596_();
        return true;
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.m_237115_("item.ic2.upgrade_kit.info"));
    }
}
