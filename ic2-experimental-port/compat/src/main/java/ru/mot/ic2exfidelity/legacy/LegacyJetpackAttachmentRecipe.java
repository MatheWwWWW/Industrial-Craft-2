package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.ElectricItem;
import ic2.core.ref.Ic2Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import ru.mot.ic2exfidelity.integration.LegacyJetpackContent;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Exact special shapeless attachment recipe from IC2 2.8.222. */
public final class LegacyJetpackAttachmentRecipe extends CustomRecipe {
    public LegacyJetpackAttachmentRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean m_5818_(CraftingContainer inventory, Level level) {
        return !assemble(inventory).m_41619_();
    }

    @Override
    public ItemStack m_5874_(CraftingContainer inventory) {
        return assemble(inventory);
    }

    private static ItemStack assemble(CraftingContainer inventory) {
        ItemStack jetpack = ItemStack.f_41583_;
        ItemStack armor = ItemStack.f_41583_;
        boolean attachmentPlate = false;

        for (int slot = 0; slot < inventory.m_6643_(); slot++) {
            ItemStack current = inventory.m_8020_(slot);
            if (current.m_41619_()) {
                continue;
            }

            Item item = current.m_41720_();
            if (item == RestoredLegacyContent.ELECTRIC_JETPACK.get()) {
                if (!jetpack.m_41619_()) {
                    return ItemStack.f_41583_;
                }
                jetpack = current;
            } else if (LivingEntity.m_147233_(current) == EquipmentSlot.CHEST
                    && !LegacyJetpackHandler.isBlacklisted(item)) {
                if (!armor.m_41619_()) {
                    return ItemStack.f_41583_;
                }
                armor = current;
            } else if (item == Ic2Items.JETPACK_ATTACHMENT_PLATE) {
                if (attachmentPlate) {
                    return ItemStack.f_41583_;
                }
                attachmentPlate = true;
            } else {
                return ItemStack.f_41583_;
            }
        }

        if (jetpack.m_41619_()
                || armor.m_41619_()
                || !attachmentPlate
                || LegacyJetpackHandler.hasJetpackAttached(armor)) {
            return ItemStack.f_41583_;
        }

        ItemStack result = armor.m_41777_();
        LegacyJetpackHandler.setJetpackAttached(result, true);
        ElectricItem.manager.charge(
                result,
                ElectricItem.manager.getCharge(jetpack),
                Integer.MAX_VALUE,
                true,
                false);
        return result;
    }

    @Override
    public boolean m_8004_(int width, int height) {
        return width * height >= 3;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return LegacyJetpackContent.JETPACK_ATTACHMENT.get();
    }
}
