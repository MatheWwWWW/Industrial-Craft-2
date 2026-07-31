/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.ItemMeshDefinition
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.ItemIC2;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBattery
extends BaseElectricItem {
    private static int maxLevel = 4;

    public ItemBattery(ItemName name, double maxCharge, double transferLimit, int tier) {
        super(name, maxCharge, transferLimit, tier);
        this.func_77625_d(16);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(final ItemName name) {
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation func_178113_a(ItemStack stack) {
                int damage = stack.func_77952_i();
                int maxDamage = stack.func_77958_k() - 1;
                int level = maxDamage > 0 ? Util.limit((damage * maxLevel + maxDamage / 2) / maxDamage, 0, maxLevel) : 0;
                return ItemIC2.getModelLocation(name, Integer.toString(maxLevel - level));
            }
        });
        for (int level = 0; level <= maxLevel; ++level) {
            ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemBattery.getModelLocation(name, Integer.toString(level))});
        }
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (world.field_72995_K || StackUtil.getSize(stack) != 1) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        if (ElectricItem.manager.getCharge(stack) > 0.0) {
            boolean transferred = false;
            for (int i = 0; i < 9; ++i) {
                double transfer;
                ItemStack target = (ItemStack)player.field_71071_by.field_70462_a.get(i);
                if (target == null || target == stack || ElectricItem.manager.discharge(target, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true, true) > 0.0 || (transfer = ElectricItem.manager.discharge(stack, 2.0 * this.transferLimit, Integer.MAX_VALUE, true, true, true)) <= 0.0 || (transfer = ElectricItem.manager.charge(target, transfer, this.tier, true, false)) <= 0.0) continue;
                ElectricItem.manager.discharge(stack, transfer, Integer.MAX_VALUE, true, true, false);
                transferred = true;
            }
            if (transferred && !world.field_72995_K) {
                player.field_71070_bA.func_75142_b();
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
    }
}

