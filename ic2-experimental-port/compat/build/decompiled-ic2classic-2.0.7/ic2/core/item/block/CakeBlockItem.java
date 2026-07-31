/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.item.block;

import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.IC2TexturedBlockItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CakeBlockItem
extends IC2TexturedBlockItem {
    public CakeBlockItem(Block block, String textureFolder, String textureName, FoodProperties props) {
        super(block, new Item.Properties().m_41491_(IC2.FOOD_AND_DRINK_GROUP).m_41503_(7).m_41489_(props), textureFolder, textureName);
    }

    public ItemStack m_5922_(ItemStack stack, Level level, LivingEntity entity) {
        if (this.m_41472_()) {
            Player player;
            if (!(entity instanceof Player) || !(player = (Player)entity).m_7500_()) {
                stack.m_41769_(1);
            }
            entity.m_5584_(level, stack);
            stack.m_41622_(1, entity, IC2Item.get(entity.m_7655_()));
        }
        return stack;
    }
}

