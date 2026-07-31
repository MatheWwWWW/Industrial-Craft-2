/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.ItemMeshDefinition
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.block;

import ic2.api.item.IBoxable;
import ic2.core.IC2;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.block.wiring.TileEntityCableDetector;
import ic2.core.block.wiring.TileEntityCableSplitter;
import ic2.core.block.wiring.TileEntityClassicCable;
import ic2.core.init.Localization;
import ic2.core.item.ItemIC2;
import ic2.core.item.block.ItemBlockTileEntity;
import ic2.core.item.tool.ItemToolPainter;
import ic2.core.ref.BlockName;
import ic2.core.ref.IMultiItem;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.Ic2Color;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCable
extends ItemIC2
implements IMultiItem<CableType>,
IBoxable {
    private final List<ItemStack> variants = new ArrayList<ItemStack>();
    private static final NumberFormat lossFormat = new DecimalFormat("0.00#");

    public ItemCable() {
        super(ItemName.cable);
        this.func_77627_a(true);
        for (CableType type : CableType.values) {
            for (int insulation = 0; insulation <= type.maxInsulation; ++insulation) {
                this.variants.add(ItemCable.getCable(type, insulation));
            }
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        final ResourceLocation loc = Util.getName(this);
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation func_178113_a(ItemStack stack) {
                return ItemCable.getModelLocation(loc, stack);
            }
        });
        for (ItemStack stack : this.variants) {
            ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemCable.getModelLocation(loc, stack)});
        }
    }

    static ModelResourceLocation getModelLocation(ResourceLocation loc, ItemStack stack) {
        return new ModelResourceLocation(new ResourceLocation(loc.func_110624_b(), loc.func_110623_a() + "/" + ItemCable.getName(stack)), null);
    }

    @Override
    public ItemStack getItemStack(CableType type) {
        return ItemCable.getCable(type, 0);
    }

    @Override
    public ItemStack getItemStack(String variant) {
        int pos = 0;
        CableType type = null;
        int insulation = 0;
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
                type = CableType.get(value);
                if (type == null) {
                    IC2.log.warn(LogCategory.Item, "Invalid cable type: %s", value);
                }
            } else if (key.equals("insulation")) {
                try {
                    insulation = Integer.valueOf(value);
                }
                catch (NumberFormatException e) {
                    IC2.log.warn(LogCategory.Item, "Invalid cable insulation: %s", value);
                }
            }
            pos = nextPos + 1;
        }
        if (type == null) {
            return null;
        }
        if (insulation < 0 || insulation > type.maxInsulation) {
            IC2.log.warn(LogCategory.Item, "Invalid cable insulation: %d", insulation);
            return null;
        }
        return ItemCable.getCable(type, insulation);
    }

    @Override
    public String getVariant(ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("null stack");
        }
        if (stack.func_77973_b() != this) {
            throw new IllegalArgumentException("The stack " + stack + " doesn't match " + this);
        }
        CableType type = ItemCable.getCableType(stack);
        int insulation = ItemCable.getInsulation(stack);
        return "type:" + type.getName() + ",insulation:" + insulation;
    }

    public static ItemStack getCable(CableType type, int insulation) {
        ItemStack ret = new ItemStack(ItemName.cable.getInstance(), 1, type.getId());
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(ret);
        nbt.func_74774_a("type", (byte)type.ordinal());
        nbt.func_74774_a("insulation", (byte)insulation);
        return ret;
    }

    private static CableType getCableType(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        int type = nbt.func_74771_c("type") & 0xFF;
        if (type < CableType.values.length) {
            return CableType.values[type];
        }
        return CableType.copper;
    }

    private static int getInsulation(ItemStack stack) {
        CableType type = ItemCable.getCableType(stack);
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        int insulation = nbt.func_74771_c("insulation") & 0xFF;
        return Math.min(insulation, type.maxInsulation);
    }

    private static String getName(ItemStack stack) {
        CableType type = ItemCable.getCableType(stack);
        int insulation = ItemCable.getInsulation(stack);
        return type.getName(insulation, null);
    }

    @Override
    public String func_77667_c(ItemStack stack) {
        return super.func_77667_c(stack) + "." + ItemCable.getName(stack);
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, World world, List<String> info, ITooltipFlag b) {
        double loss;
        int capacity;
        CableType type = ItemCable.getCableType(stack);
        if (!IC2.version.isClassic()) {
            capacity = type.capacity;
            loss = type.loss;
        } else {
            capacity = TileEntityClassicCable.getCableCapacity(type);
            loss = TileEntityClassicCable.getConductionLoss(type, ItemCable.getInsulation(stack));
        }
        info.add(capacity + " " + Localization.translate("ic2.generic.text.EUt"));
        info.add(Localization.translate("ic2.cable.tooltip.loss", lossFormat.format(loss)));
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntityCable te;
        ItemStack stack = StackUtil.get(player, hand);
        IBlockState oldState = world.func_180495_p(pos);
        Block oldBlock = oldState.func_177230_c();
        if (!oldBlock.func_176200_f((IBlockAccess)world, pos)) {
            pos = pos.func_177972_a(side);
        }
        Object newBlock = BlockName.te.getInstance();
        if (StackUtil.isEmpty(stack) || !player.func_175151_a(pos, side, stack) || !world.func_190527_a(newBlock, pos, false, side, (Entity)player) || !((BlockTileEntity)newBlock).canReplace(world, pos, side, BlockName.te.getItemStack(TeBlock.cable))) {
            return EnumActionResult.PASS;
        }
        newBlock.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, 0, (EntityLivingBase)player, hand);
        CableType type = ItemCable.getCableType(stack);
        int insulation = ItemCable.getInsulation(stack);
        Runnable damage = null;
        switch (type) {
            case detector: {
                te = TileEntityBlock.instantiate(TileEntityCableDetector.delegate());
                break;
            }
            case splitter: {
                te = TileEntityBlock.instantiate(TileEntityCableSplitter.delegate());
                break;
            }
            default: {
                ItemToolPainter painter;
                Ic2Color color;
                ItemStack offStack;
                if (hand == EnumHand.MAIN_HAND && !StackUtil.isEmpty(offStack = StackUtil.get(player, EnumHand.OFF_HAND)) && offStack.func_77973_b() == ItemName.painter.getInstance() && (color = (painter = (ItemToolPainter)offStack.func_77973_b()).getColor(offStack)) != null) {
                    damage = () -> painter.damagePainter(player, EnumHand.OFF_HAND, color);
                    te = TileEntityCable.delegate(type, insulation, color);
                    break;
                }
                te = TileEntityCable.delegate(type, insulation);
            }
        }
        if (ItemBlockTileEntity.placeTeBlock(stack, (EntityLivingBase)player, world, pos, side, te)) {
            SoundType soundtype = newBlock.getSoundType(world.func_180495_p(pos), world, pos, (Entity)player);
            world.func_184133_a(player, pos, soundtype.func_185841_e(), SoundCategory.BLOCKS, (soundtype.func_185843_a() + 1.0f) / 2.0f, soundtype.func_185847_b() * 0.8f);
            StackUtil.consumeOrError(player, hand, 1);
            if (damage != null) {
                damage.run();
            }
        }
        return EnumActionResult.SUCCESS;
    }

    public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> itemList) {
        if (!this.func_194125_a(tab)) {
            return;
        }
        ArrayList<ItemStack> variants = new ArrayList<ItemStack>(this.variants);
        if (IC2.version.isClassic()) {
            variants.remove(11);
        }
        itemList.addAll(variants);
    }

    @Override
    public Set<CableType> getAllTypes() {
        return EnumSet.allOf(CableType.class);
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

