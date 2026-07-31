/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.wearable.jetpacks;

import ic2.api.items.IFuelableItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2JetpackBase;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class FuelJetpack
extends IC2JetpackBase
implements IFuelableItem {
    public FuelJetpack() {
        super("fuel_jetpack", EquipmentSlot.CHEST, new PropertiesBuilder().maxDamage(18002));
    }

    @Override
    public boolean canDisable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canDoRocketMode(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canDoAdvHoverMode(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isElectricJetpack(ItemStack stack) {
        return false;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 1.0f;
    }

    @Override
    public float getThruster(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        return mode == IC2JetpackBase.HoverMode.BASIC ? 0.5f : 0.15f;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return 0.5f;
    }

    @Override
    public int getMaxHeight(ItemStack stack, int worldHeight) {
        return worldHeight;
    }

    @Override
    public int getFuel(ItemStack stack) {
        return Math.max(stack.m_41776_() - stack.m_41773_() - 1, 0);
    }

    @Override
    public int getMaxFuel(ItemStack stack) {
        return stack.m_41776_();
    }

    @Override
    public int getMaxRocketCharge(ItemStack stack) {
        return 0;
    }

    @Override
    public int getFuelCost(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        return mode == IC2JetpackBase.HoverMode.BASIC ? 6 : 9;
    }

    @Override
    public void useEnergy(Player player, ItemStack stack, int amount) {
        stack.m_41721_(Math.min(stack.m_41773_() + amount, stack.m_41776_() - 1));
    }

    @Override
    public String getTextureFolder() {
        return "armor/jetpack";
    }

    @Override
    public String getTextureName() {
        return "base";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/jetpack_fuel";
    }

    @Override
    public ItemStack fill(ItemStack stack, int amount) {
        stack.m_41721_(Math.max(1, stack.m_41773_() - amount));
        return stack;
    }

    @Override
    public boolean canFuel(ItemStack stack) {
        return stack.m_41773_() > 1;
    }

    @Override
    public boolean hasFuel(ItemStack stack) {
        return false;
    }

    @Override
    public int getFuel(ItemStack stack, int requested, boolean doDrain) {
        return 0;
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        ItemStack full = new ItemStack((ItemLike)this);
        ItemStack empty = new ItemStack((ItemLike)this);
        full.m_41721_(1);
        empty.m_41721_(18001);
        items.add((Object)full);
        items.add((Object)empty);
    }
}

