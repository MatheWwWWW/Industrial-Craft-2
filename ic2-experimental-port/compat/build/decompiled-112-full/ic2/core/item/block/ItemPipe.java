/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.block;

import ic2.api.item.IBoxable;
import ic2.core.IC2;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.item.ItemIC2;
import ic2.core.ref.IMultiItem;
import ic2.core.ref.ItemName;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPipe
extends ItemIC2
implements IMultiItem<PipeType>,
IBoxable {
    private final List<ItemStack> variants = new ArrayList<ItemStack>();

    public ItemPipe() {
        super(ItemName.pipe);
        this.func_77627_a(true);
        for (PipeType type : PipeType.values) {
            for (PipeSize pipeSize : PipeSize.values) {
                this.variants.add(ItemPipe.getPipe(type, pipeSize));
            }
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ResourceLocation loc = Util.getName(this);
        ModelLoader.setCustomMeshDefinition((Item)this, stack -> ItemPipe.getModelLocation(loc, stack));
        for (ItemStack stack2 : this.variants) {
            ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemPipe.getModelLocation(loc, stack2)});
        }
    }

    static ModelResourceLocation getModelLocation(ResourceLocation loc, ItemStack itemStack) {
        return new ModelResourceLocation(new ResourceLocation(loc.func_110624_b(), loc.func_110623_a() + "/" + ItemPipe.getName(itemStack)), null);
    }

    @Override
    public ItemStack getItemStack(PipeType type) {
        return ItemPipe.getPipe(type, PipeSize.small);
    }

    @Override
    public ItemStack getItemStack(String variant) {
        int pos = 0;
        PipeType type = null;
        PipeSize size = null;
        while (pos < variant.length()) {
            int sepPos;
            int nextPos = variant.indexOf(44, pos);
            if (nextPos == -1) {
                nextPos = variant.length();
            }
            if ((sepPos = variant.indexOf(58, pos)) == -1 || sepPos >= nextPos) {
                return null;
            }
            String key = variant.substring(pos, sepPos);
            String value = variant.substring(sepPos + 1, nextPos);
            if (key.equals("type")) {
                type = PipeType.get(value);
                if (type == null) {
                    IC2.log.warn(LogCategory.Item, "Invalid pipe type: %s", value);
                }
            } else if (key.equals("size") && (size = PipeSize.get(value)) == null) {
                IC2.log.warn(LogCategory.Item, "Invalid pipe size: %s", value);
            }
            pos = nextPos + 1;
        }
        if (type == null) {
            return null;
        }
        return ItemPipe.getPipe(type, size);
    }

    @Override
    public String getVariant(ItemStack itemStack) {
        if (itemStack == null) {
            throw new NullPointerException("null stack");
        }
        if (itemStack.func_77973_b() != this) {
            throw new IllegalArgumentException("The stack " + itemStack + " doesn't match " + this);
        }
        PipeType type = ItemPipe.getPipeType(itemStack);
        PipeSize size = ItemPipe.getSize(itemStack);
        return "type:" + type.getName() + ", size:" + size.getName();
    }

    public static ItemStack getPipe(PipeType type, PipeSize size) {
        ItemStack ret = new ItemStack(ItemName.pipe.getInstance(), 1, type.getId());
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(ret);
        nbt.func_74774_a("type", (byte)type.ordinal());
        nbt.func_74774_a("size", (byte)size.ordinal());
        return ret;
    }

    private static PipeType getPipeType(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        int type = nbt.func_74771_c("type") & 0xFF;
        if (type < PipeType.values.length) {
            return PipeType.values[type];
        }
        return PipeType.bronze;
    }

    private static PipeSize getSize(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        int size = nbt.func_74771_c("size") & 0xFF;
        if (size < PipeSize.values.length) {
            return PipeSize.values[size];
        }
        return PipeSize.small;
    }

    private static String getName(ItemStack stack) {
        PipeType type = ItemPipe.getPipeType(stack);
        PipeSize size = ItemPipe.getSize(stack);
        return type.getName(size);
    }

    @Override
    public String func_77667_c(ItemStack stack) {
        return super.func_77667_c(stack) + "." + ItemPipe.getName(stack);
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack itemStack, World world, List<String> info, ITooltipFlag b) {
        PipeType type = ItemPipe.getPipeType(itemStack);
        PipeSize size = ItemPipe.getSize(itemStack);
        info.add("Transfer rate: " + type.transferRate + " stacks/second");
        info.add("Max stack size: " + size.maxStackSize);
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return EnumActionResult.SUCCESS;
    }

    public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> itemList) {
        if (!this.func_194125_a(tab)) {
            return;
        }
        ArrayList<ItemStack> variants = new ArrayList<ItemStack>(this.variants);
        itemList.addAll(variants);
    }

    @Override
    public Set<PipeType> getAllTypes() {
        return EnumSet.allOf(PipeType.class);
    }

    @Override
    public Set<ItemStack> getAllStacks() {
        return new HashSet<ItemStack>(this.variants);
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemstack) {
        return true;
    }
}

