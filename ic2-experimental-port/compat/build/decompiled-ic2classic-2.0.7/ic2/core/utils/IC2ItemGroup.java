/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.utils;

import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class IC2ItemGroup
extends CreativeModeTab {
    public IC2ItemGroup() {
        super("ic2");
    }

    public ItemStack m_6976_() {
        ItemStack stack = new ItemStack((ItemLike)IC2Items.MINING_LASER);
        stack.m_41784_().m_128379_("hide_charge_bar", true);
        return stack;
    }

    public static class IC2CFoamItemGroup
    extends CreativeModeTab {
        public IC2CFoamItemGroup() {
            super("ic2.cfoam");
        }

        public ItemStack m_6976_() {
            return new ItemStack((ItemLike)IC2Blocks.CFOAM_BLOCK_LIGHT_GRAY);
        }
    }

    public static class IC2FoodAndDinkItemGroup
    extends CreativeModeTab {
        public IC2FoodAndDinkItemGroup() {
            super("ic2.food_and_drink");
        }

        public ItemStack m_6976_() {
            return new ItemStack((ItemLike)IC2Items.MUG_TEA);
        }
    }
}

