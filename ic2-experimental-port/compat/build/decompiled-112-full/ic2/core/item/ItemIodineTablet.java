/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.item.ItemIC2;
import ic2.core.profile.NotClassic;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

@NotClassic
public class ItemIodineTablet
extends ItemIC2 {
    public ItemIodineTablet() {
        super(ItemName.iodine_tablet);
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (!world.field_72995_K) {
            return this.onEaten(player, stack);
        }
        return new ActionResult(EnumActionResult.PASS, (Object)stack);
    }

    public ActionResult<ItemStack> onEaten(EntityPlayer player, ItemStack stack) {
        PotionEffect radiation = player.func_70660_b((Potion)IC2Potion.radiation);
        if (radiation == null) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        int duration = radiation.func_76459_b() / 20;
        int amount = Math.min(StackUtil.getSize(stack), duration);
        if (amount <= 0) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        player.func_184589_d((Potion)IC2Potion.radiation);
        if (amount < duration) {
            player.func_70690_d(new PotionEffect((Potion)IC2Potion.radiation, (duration - amount) * 20));
        }
        stack = StackUtil.decSize(stack, amount);
        IC2.platform.playSoundSp("Tools/eat.ogg", 1.0f, 1.0f);
        return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
    }
}

