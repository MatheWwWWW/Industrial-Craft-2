/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.food_and_drink;

import ic2.api.items.IAutoEatable;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.base.PropertiesBuilder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class IC2Food
extends IC2SimpleItem
implements IAutoEatable {
    boolean autoeatable;

    public IC2Food(String itemName, PropertiesBuilder properties, String textureFolder, String textureName) {
        super(itemName, properties, textureFolder, textureName);
    }

    public static IC2Food simpleFood(String name, String textureName, FoodProperties food) {
        return new IC2Food(name, new PropertiesBuilder().food(food), "food_and_drink", textureName);
    }

    public static IC2Food simpleFood(String name, String textureFolder, String textureName, FoodProperties food) {
        return new IC2Food(name, new PropertiesBuilder().food(food), textureFolder, textureName);
    }

    public static IC2Food multiFood(String name, String textureName, int maxEats, FoodProperties food) {
        return new IC2Food(name, new PropertiesBuilder().food(food).maxDamage(maxEats).canRepair(false), "food_and_drink", textureName);
    }

    public static IC2Food multiFood(String name, String textureFolder, String textureName, int maxEats, FoodProperties food) {
        return new IC2Food(name, new PropertiesBuilder().food(food).maxDamage(maxEats).canRepair(false), textureFolder, textureName);
    }

    public IC2Food setAutoEatable() {
        this.autoeatable = true;
        return this;
    }

    @Override
    public boolean canAutoEat(ItemStack stack) {
        return this.autoeatable;
    }

    public ItemStack m_5922_(ItemStack stack, Level level, LivingEntity entity) {
        if (this.m_41472_()) {
            if (this.getMaxDamage(stack) > 1) {
                Player player;
                if (!(entity instanceof Player) || !(player = (Player)entity).m_7500_()) {
                    stack.m_41769_(1);
                }
                entity.m_5584_(level, stack);
                stack.m_41622_(1, entity, IC2Item.get(entity.m_7655_()));
            } else {
                entity.m_5584_(level, stack);
            }
        }
        return stack;
    }

    @Override
    public int getFoodValue(ItemStack stack) {
        FoodProperties props = this.getFoodProperties(stack, null);
        return props == null ? 0 : props.m_38744_();
    }

    @Override
    public ItemStack onEaten(ItemStack stack, Level level, Player player) {
        if (this.getMaxDamage(stack) > 1) {
            if (!player.m_150110_().f_35937_) {
                stack.m_41769_(1);
            }
            player.m_5584_(level, stack);
            stack.m_41622_(1, (LivingEntity)player, IC2Item.empty());
            return ItemStack.f_41583_;
        }
        return this.getCraftingRemainingItem(stack);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.f_44962_) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}

