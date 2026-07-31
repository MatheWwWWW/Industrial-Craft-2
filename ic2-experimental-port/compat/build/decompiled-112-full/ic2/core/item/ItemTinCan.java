/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.core.IC2;
import ic2.core.item.ItemIC2;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemTinCan
extends ItemIC2 {
    public ItemTinCan() {
        super(ItemName.filled_tin_can);
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (!world.field_72995_K && player.func_71024_bL().func_75121_c()) {
            return this.onEaten(player, stack);
        }
        return new ActionResult(EnumActionResult.PASS, (Object)stack);
    }

    public ActionResult<ItemStack> onEaten(EntityPlayer player, ItemStack stack) {
        int amount = Math.min(StackUtil.getSize(stack), 20 - player.func_71024_bL().func_75116_a());
        if (amount <= 0) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        ItemStack emptyStack = StackUtil.copyWithSize(ItemName.crafting.getItemStack(CraftingItemType.tin_can), amount);
        if (StackUtil.storeInventoryItem(emptyStack, player, true)) {
            player.func_71024_bL().func_75122_a(amount, (float)amount);
            stack = StackUtil.decSize(stack, amount);
            StackUtil.storeInventoryItem(emptyStack, player, false);
            IC2.platform.playSoundSp("Tools/eat.ogg", 1.0f, 1.0f);
            return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
        }
        return new ActionResult(EnumActionResult.PASS, (Object)stack);
    }
}

