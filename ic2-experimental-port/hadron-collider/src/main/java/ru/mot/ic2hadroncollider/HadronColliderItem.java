package ru.mot.ic2hadroncollider;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

final class HadronColliderItem extends BlockItem {
    HadronColliderItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.m_7373_(stack, level, tooltip, flag);
        tooltip.add(Component.m_237110_("tooltip.ic2_hadron_collider.hadron_collider.tier",
                HadronColliderBlockEntity.BASE_TIER).m_130940_(ChatFormatting.GRAY));
        tooltip.add(Component.m_237110_("tooltip.ic2_hadron_collider.hadron_collider.upkeep",
                HadronColliderBlockEntity.UPKEEP_EU_PER_TICK).m_130940_(ChatFormatting.GRAY));
        tooltip.add(Component.m_237110_("tooltip.ic2_hadron_collider.hadron_collider.cost",
                Math.round(HadronColliderBlockEntity.energyPerMb(0) / 1_000.0),
                Math.round(HadronColliderBlockEntity.energyPerMb(HadronColliderBlockEntity.BEAM_MAX) / 1_000.0))
                .m_130940_(ChatFormatting.GRAY));
        tooltip.add(Component.m_237110_("tooltip.ic2_hadron_collider.hadron_collider.beam",
                HadronColliderBlockEntity.BEAM_MAX / HadronColliderBlockEntity.BEAM_RISE_PER_TICK / 20)
                .m_130940_(ChatFormatting.DARK_PURPLE));
    }
}
