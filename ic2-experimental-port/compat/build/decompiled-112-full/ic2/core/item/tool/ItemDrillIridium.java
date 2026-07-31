/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Enchantments
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.item.tool.HarvestLevel;
import ic2.core.item.tool.ItemDrill;
import ic2.core.profile.NotClassic;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.IdentityHashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@NotClassic
public class ItemDrillIridium
extends ItemDrill {
    public ItemDrillIridium() {
        super(ItemName.iridium_drill, 800, HarvestLevel.Iridium, 300000, 1000, 3, 24.0f);
    }

    @Override
    protected ItemStack getItemStack(double charge) {
        ItemStack ret = super.getItemStack(charge);
        IdentityHashMap<Enchantment, Integer> enchantmentMap = new IdentityHashMap<Enchantment, Integer>();
        enchantmentMap.put(Enchantments.field_185308_t, 3);
        EnchantmentHelper.func_82782_a(enchantmentMap, (ItemStack)ret);
        return ret;
    }

    @Override
    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        if (!world.field_72995_K && IC2.keyboard.isModeSwitchKeyDown(player)) {
            IdentityHashMap<Enchantment, Integer> enchantmentMap = new IdentityHashMap<Enchantment, Integer>();
            enchantmentMap.put(Enchantments.field_185308_t, 3);
            ItemStack stack = StackUtil.get(player, hand);
            if (EnchantmentHelper.func_77506_a((Enchantment)Enchantments.field_185306_r, (ItemStack)stack) == 0) {
                enchantmentMap.put(Enchantments.field_185306_r, 1);
                IC2.platform.messagePlayer(player, "ic2.tooltip.mode", "ic2.tooltip.mode.silkTouch");
            } else {
                IC2.platform.messagePlayer(player, "ic2.tooltip.mode", "ic2.tooltip.mode.normal");
            }
            EnchantmentHelper.func_82782_a(enchantmentMap, (ItemStack)stack);
        }
        return super.func_77659_a(world, player, hand);
    }

    @Override
    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            return EnumActionResult.PASS;
        }
        return super.func_180614_a(player, world, pos, hand, side, xOffset, yOffset, zOffset);
    }
}

