package ru.mot.ic2exfidelity.legacy;

import com.mojang.datafixers.util.Pair;
import ic2.api.item.ElectricItem;
import ic2.core.item.tool.ItemElectricTool;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlockState;

/** IC2 2.8 electric hoe semantics adapted to Forge's 1.19 tilling hooks. */
public final class LegacyElectricHoe extends ItemElectricTool {
    private static final int ENERGY_PER_OPERATION = 50;
    private static final float LEGACY_EFFICIENCY = 16.0F;

    public LegacyElectricHoe(Item.Properties properties) {
        super(properties, ENERGY_PER_OPERATION, Tiers.IRON, List.of(BlockTags.f_144281_));
        maxCharge = 10_000;
        transferLimit = 100;
        tier = 1;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        ItemStack stack = context.m_43722_();
        if (!ElectricItem.manager.canUse(stack, operationEnergyCost)) {
            return InteractionResult.PASS;
        }

        Level level = context.m_43725_();
        BlockPos position = context.m_8083_();
        BlockState original = level.m_8055_(position);

        // Forge lets modded blocks provide their own HOE_TILL state. Retain that
        // extension point before falling back to the complete vanilla 1.19 map.
        BlockState modified = ((IForgeBlockState) (Object) original)
                .getToolModifiedState(context, ToolActions.HOE_TILL, false);
        Consumer<UseOnContext> tillAction = null;
        Predicate<UseOnContext> tillPredicate = null;
        if (modified != null) {
            tillPredicate = ignored -> true;
            tillAction = HoeItem.m_150858_(modified);
        } else {
            Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> vanilla =
                    VanillaHoeAccess.lookup(original.m_60734_());
            if (vanilla != null) {
                tillPredicate = vanilla.getFirst();
                tillAction = vanilla.getSecond();
            }
        }

        if (tillPredicate == null || !tillPredicate.test(context)) {
            return InteractionResult.PASS;
        }

        level.m_5594_(context.m_43723_(), position, SoundEvents.f_11955_, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!level.f_46443_) {
            tillAction.accept(context);
            consumeEnergy(stack, operationEnergyCost, context.m_43723_());
        }
        return InteractionResult.m_19078_(level.f_46443_);
    }

    @Override
    public float m_8102_(ItemStack stack, BlockState state) {
        return state.m_204336_(BlockTags.f_144281_) && canUse(stack) ? LEGACY_EFFICIENCY : 1.0F;
    }

    // Kept without @Override because the method is injected into Item by Forge
    // after the vanilla SRG compile jar is produced.
    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return ToolActions.DEFAULT_HOE_ACTIONS.contains(action);
    }

    /** Protected vanilla tilling data is exposed without copying its block table. */
    private static final class VanillaHoeAccess extends HoeItem {
        private VanillaHoeAccess() {
            super(Tiers.WOOD, 0, 0.0F, new Item.Properties());
        }

        private static Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> lookup(Block block) {
            return f_41332_.get(block);
        }
    }
}
