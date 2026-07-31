/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.item.tool.ItemDrill;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import java.util.IdentityHashMap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

@NotClassic
public class ItemDrillIridium
extends ItemDrill {
    private static final Tier IRIDIUM_TOOL_MATERIAL = new Tier(){

        public int m_6609_() {
            return 3000;
        }

        public float m_6624_() {
            return 15.0f;
        }

        public float m_6631_() {
            return 5.0f;
        }

        public int m_6604_() {
            return 100;
        }

        public int m_6601_() {
            return 20;
        }

        public Ingredient m_6282_() {
            return Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.IRIDIUM});
        }
    };

    public ItemDrillIridium(Item.Properties properties) {
        super(properties, 800, IRIDIUM_TOOL_MATERIAL, 300000, 1000, 3, 24.0f);
    }

    @Override
    protected ItemStack getItemStack(double d) {
        ItemStack itemStack = super.getItemStack(d);
        IdentityHashMap<Enchantment, Integer> identityHashMap = new IdentityHashMap<Enchantment, Integer>();
        identityHashMap.put(Enchantments.f_44987_, 3);
        EnchantmentHelper.m_44865_(identityHashMap, (ItemStack)itemStack);
        return itemStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        if (!level.f_46443_ && IC2.keyboard.isModeSwitchKeyDown(player)) {
            IdentityHashMap<Enchantment, Integer> identityHashMap = new IdentityHashMap<Enchantment, Integer>();
            identityHashMap.put(Enchantments.f_44987_, 3);
            ItemStack itemStack = StackUtil.get(player, interactionHand);
            if (EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44985_, (ItemStack)itemStack) == 0) {
                identityHashMap.put(Enchantments.f_44985_, 1);
                IC2.sideProxy.messagePlayer(player, "ic2.tooltip.mode", "ic2.tooltip.mode.silkTouch");
            } else {
                IC2.sideProxy.messagePlayer(player, "ic2.tooltip.mode", "ic2.tooltip.mode.normal");
            }
            EnchantmentHelper.m_44865_(identityHashMap, (ItemStack)itemStack);
        }
        return super.m_7203_(level, player, interactionHand);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext useOnContext) {
        if (IC2.keyboard.isModeSwitchKeyDown(useOnContext.m_43723_())) {
            return InteractionResult.PASS;
        }
        return super.m_6225_(useOnContext);
    }
}

