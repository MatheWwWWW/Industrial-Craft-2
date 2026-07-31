package ru.mot.ic2exfidelity.legacy;

import ic2.api.crops.Crops;
import ic2.core.crop.TileEntityCrop;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

/** Removes a weed crop exactly as the 2.8 weeding trowel did. */
public final class LegacyWeedingTrowel extends Item {
    public LegacyWeedingTrowel(Properties properties) {
        super(properties);
    }

    // Forge adds this virtual hook to Item while transforming the game classes.
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.m_43725_();
        if (level.f_46443_) {
            return InteractionResult.PASS;
        }
        if (!(level.m_7702_(context.m_8083_()) instanceof TileEntityCrop crop)
                || crop.getCrop() != Crops.weed) {
            return InteractionResult.PASS;
        }

        int amount = crop.getCurrentAge();
        if (amount > 0) {
            Block.m_49840_(level, context.m_8083_(), new ItemStack(Ic2Items.WEED, amount));
        }
        crop.reset();
        return InteractionResult.SUCCESS;
    }
}
