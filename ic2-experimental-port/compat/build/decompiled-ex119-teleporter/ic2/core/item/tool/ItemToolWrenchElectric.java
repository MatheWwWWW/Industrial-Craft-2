/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.core.item.PriorityUsableItem;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class ItemToolWrenchElectric
extends ItemElectricTool
implements PriorityUsableItem,
IBoxable {
    public ItemToolWrenchElectric(Item.Properties properties) {
        super(properties, 100);
        this.tier = 1;
        this.maxCharge = 12000;
        this.transferLimit = 250;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext useOnContext) {
        if (!this.canTakeDamage(itemStack, 1.0)) {
            return InteractionResult.FAIL;
        }
        Player player = useOnContext.m_43723_();
        if (player == null) {
            return InteractionResult.PASS;
        }
        int n = ItemToolWrench.onWrenchUse(player, itemStack, useOnContext, this.canTakeDamage(itemStack, 10.0));
        switch (n) {
            case -2: {
                return InteractionResult.PASS;
            }
            case -1: {
                return InteractionResult.FAIL;
            }
        }
        this.consumeEnergy(itemStack, n, (LivingEntity)player);
        return InteractionResult.SUCCESS;
    }

    public boolean canTakeDamage(ItemStack itemStack, double d) {
        return ElectricItem.manager.getCharge(itemStack) >= (d *= 100.0);
    }

    @Override
    public boolean consumeEnergy(ItemStack itemStack, double d, LivingEntity livingEntity) {
        double d2 = 100.0 * d;
        return super.consumeEnergy(itemStack, d2, livingEntity);
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }
}

