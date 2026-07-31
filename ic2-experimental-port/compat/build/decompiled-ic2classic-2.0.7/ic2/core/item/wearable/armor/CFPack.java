/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.armor;

import ic2.api.items.armor.IFoamSupplier;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CFPack
extends IC2AdvancedArmorBase
implements IFoamSupplier {
    public CFPack() {
        super("cf_pack", EquipmentSlot.CHEST, new PropertiesBuilder().setNoRepair().maxDamage(3400));
    }

    @Override
    public boolean canProvideFoam(Player player, ItemStack stack, IFoamSupplier.InventoryType inv, int amount) {
        if (inv != IFoamSupplier.InventoryType.ARMOR) {
            return false;
        }
        return stack.m_41776_() - stack.m_41773_() >= amount;
    }

    @Override
    public void useFoam(Player player, ItemStack stack, int amount) {
        stack.m_41721_(Math.min(stack.m_41776_(), stack.m_41773_() + amount));
    }

    @Override
    public int getFreeFoamSpace(ItemStack stack) {
        return stack.m_41773_();
    }

    @Override
    public void fillFoam(ItemStack stack, int amount) {
        stack.m_41721_(Math.max(0, stack.m_41773_() - amount));
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.f_43901_;
    }

    @Override
    public String getTextureFolder() {
        return "armor/packs";
    }

    @Override
    public String getTextureName() {
        return "cf";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/batpack";
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        ItemStack empty = new ItemStack((ItemLike)this);
        empty.m_41721_(3400);
        items.add((Object)empty);
        items.add((Object)new ItemStack((ItemLike)this));
    }

    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add((Component)this.translate("tooltip.item.ic2.cf_pack.sprayer_uses", new Object[]{stack.m_41776_() - stack.m_41773_()}).m_130940_(ChatFormatting.GRAY));
    }
}

