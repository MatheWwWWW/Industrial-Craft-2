/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.ItemMeshDefinition
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumRarity
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

import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.ItemIC2;
import ic2.core.item.tool.ContainerToolbox;
import ic2.core.item.tool.HandHeldToolbox;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
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

public class ItemToolbox
extends ItemIC2
implements IHandHeldInventory {
    public ItemToolbox() {
        super(ItemName.tool_box);
        this.func_77625_d(1);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(final ItemName name) {
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation func_178113_a(ItemStack stack) {
                ItemStack mainHandItem;
                EntityPlayer player = IC2.platform.getPlayerInstance();
                boolean open = player != null && player.field_71070_bA instanceof ContainerToolbox && (StackUtil.checkItemEquality(mainHandItem = player.func_184614_ca(), stack) || StackUtil.checkItemEquality(player.func_184592_cb(), stack) && (mainHandItem == null || !(mainHandItem.func_77973_b() instanceof IHandHeldInventory)));
                return ItemIC2.getModelLocation(name, open ? "open" : null);
            }
        });
        ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemToolbox.getModelLocation(name, null)});
        ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemToolbox.getModelLocation(name, "open")});
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (IC2.platform.isSimulating()) {
            IC2.platform.launchGui(player, this.getInventory(player, stack));
        }
        return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
    }

    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
        HandHeldToolbox toolbox;
        if (!player.func_130014_f_().field_72995_K && !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof ContainerToolbox && (toolbox = (HandHeldToolbox)((ContainerToolbox)player.field_71070_bA).base).isThisContainer(stack)) {
            toolbox.saveAsThrown(stack);
            player.func_71053_j();
        }
        return true;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public EnumRarity func_77613_e(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
        return new HandHeldToolbox(player, stack, 9);
    }
}

