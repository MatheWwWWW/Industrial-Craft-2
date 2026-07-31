/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.ItemMeshDefinition
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.block;

import ic2.core.block.BlockScaffold;
import ic2.core.block.TileEntityBarrel;
import ic2.core.init.Localization;
import ic2.core.item.ItemBooze;
import ic2.core.item.ItemIC2;
import ic2.core.item.block.ItemBlockTileEntity;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBarrel
extends ItemIC2 {
    public ItemBarrel() {
        super(ItemName.barrel);
        this.func_77625_d(1);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(final ItemName name) {
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation func_178113_a(ItemStack stack) {
                return ItemIC2.getModelLocation(name, null);
            }
        });
        ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemBarrel.getModelLocation(name, null)});
    }

    @Override
    public String func_77653_i(ItemStack itemstack) {
        int v = ItemBooze.getAmountOfValue(itemstack.func_77952_i());
        if (v > 0) {
            return "" + v + Localization.translate("ic2.item.LBoozeBarrel");
        }
        return Localization.translate("ic2.item.EmptyBoozeBarrel");
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float a, float b, float c) {
        ItemStack stack = StackUtil.get(player, hand);
        if (world.func_180495_p(pos) == BlockName.scaffold.getBlockState(BlockScaffold.ScaffoldType.wood) && ItemBlockTileEntity.placeTeBlock(stack, (EntityLivingBase)player, world, pos, side, new TileEntityBarrel(stack.func_77952_i()))) {
            StackUtil.consumeOrError(player, hand, 1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}

