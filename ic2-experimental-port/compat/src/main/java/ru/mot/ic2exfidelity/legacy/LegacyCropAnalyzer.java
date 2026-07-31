package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.ElectricItem;
import ic2.core.IHasGui;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.IHandHeldInventory;
import ic2.core.crop.TileEntityCrop;
import ic2.core.util.StackUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/** The 2.8 crop analyzer, backed by the crop API still present in ex119. */
public final class LegacyCropAnalyzer extends BaseElectricItem implements IHandHeldInventory {
    public LegacyCropAnalyzer(Item.Properties properties) {
        super(properties, 100_000.0, 128.0, 2);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (!level.f_46443_) {
            getInventory(player, hand, stack).openManagedItem(player, hand, null);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        Player player = context.m_43723_();
        if (level.f_46443_ || player == null || player.m_6047_()) {
            return InteractionResult.PASS;
        }
        if (!(level.m_7702_(context.m_8083_()) instanceof TileEntityCrop cropTile)
                || cropTile.getCrop() == null) {
            return InteractionResult.PASS;
        }

        // The original requests tier 3 and accepts any positive discharge here.
        double used = ElectricItem.manager.discharge(
                context.m_43722_(), LegacyHandHeldCropAnalyzer.energyForLevel(2), 3,
                true, false, false);
        if (used <= 0.0) {
            return InteractionResult.PASS;
        }

        var crop = cropTile.getCrop();
        message(player, "Crop name: " + Component.m_237115_(crop.getUnlocalizedName()).getString()
                + " (by " + crop.getDiscoveredBy() + ")");
        message(player, "Crop size: " + cropTile.getCurrentAge() + "/" + crop.getMaxAge());
        message(player, "Nutrient storage: " + cropTile.getStorageNutrients() + "/100");
        message(player, "Water storage: " + cropTile.getStorageWater() + "/200");
        message(player, "Weed-Ex storage: " + cropTile.getStorageWeedEX() + "/100");
        message(player, "Growth points: " + cropTile.getGrowthPoints() + "/"
                + crop.getGrowthDuration(cropTile));
        return InteractionResult.SUCCESS;
    }

    @Override
    public Rarity m_41460_(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public IHasGui getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new LegacyHandHeldCropAnalyzer(player, hand, stack);
    }

    private static void message(Player player, String text) {
        player.m_5661_(Component.m_237113_(text), false);
    }
}
