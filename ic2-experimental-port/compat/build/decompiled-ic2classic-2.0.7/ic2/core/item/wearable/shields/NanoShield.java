/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.wearable.shields;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.wearable.base.IC2ShieldBase;
import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class NanoShield
extends IC2ShieldBase
implements IDamagelessElectricItem {
    private ResourceLocation texture = new ResourceLocation(IC2.MODID, "textures/models/armor/shieldnano.png");

    public NanoShield() {
        super("nano_shield", 0);
    }

    @Override
    public boolean canBlock(ItemStack item, LivingEntity entity) {
        return ElectricItem.MANAGER.canUse(item, 50);
    }

    @Override
    public void damageShield(ItemStack stack, int amount, LivingEntity entity) {
        ElectricItem.MANAGER.use(stack, amount * 50, entity);
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, 50 * amount), Integer.MAX_VALUE, true, false, false);
        return 0;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack) {
        return this.texture;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 100000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 2;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 300;
    }

    public int m_142158_(ItemStack stack) {
        return IC2ElectricItem.getElectricWidth(stack);
    }

    public int m_142159_(ItemStack stack) {
        return IC2ElectricItem.getRGBDurability(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, items);
    }
}

