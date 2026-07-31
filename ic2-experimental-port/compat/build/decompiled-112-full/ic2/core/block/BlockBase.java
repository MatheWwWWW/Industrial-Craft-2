/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.client.renderer.block.statemap.IStateMapper
 *  net.minecraft.init.Items
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.core.IC2;
import ic2.core.init.BlocksItems;
import ic2.core.init.Localization;
import ic2.core.item.block.ItemBlockIC2;
import ic2.core.model.ModelUtil;
import ic2.core.ref.BlockName;
import ic2.core.ref.IBlockModelProvider;
import ic2.core.util.Util;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockBase
extends Block
implements IBlockModelProvider {
    protected BlockBase(BlockName name, Material material) {
        this(name, material, ItemBlockIC2.supplier);
    }

    protected BlockBase(BlockName name, Material material, Class<? extends ItemBlock> itemClass) {
        this(name, material, BlockBase.createItemBlockSupplier(itemClass));
    }

    protected BlockBase(BlockName name, Material material, Function<Block, Item> itemSupplier) {
        super(material);
        this.func_149647_a(IC2.tabIC2);
        if (name != null) {
            this.register(name.name(), IC2.getIdentifier(name.name()), itemSupplier);
            name.setInstance(this);
        }
    }

    protected void register(String name, ResourceLocation identifier, Function<Block, Item> itemSupplier) {
        this.func_149663_c(name);
        BlocksItems.registerBlock(this, identifier);
        if (itemSupplier != null) {
            BlocksItems.registerItem(itemSupplier.apply(this), identifier);
        }
    }

    protected static Function<Block, Item> createItemBlockSupplier(final Class<? extends ItemBlock> cls) {
        if (cls == null) {
            throw new NullPointerException("null item class");
        }
        return new Function<Block, Item>(){

            @Override
            public Item apply(Block input) {
                try {
                    return (Item)cls.getConstructor(Block.class).newInstance(input);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(BlockName name) {
        BlockBase.registerDefaultItemModel(this);
    }

    @SideOnly(value=Side.CLIENT)
    public static void registerDefaultItemModel(Block block) {
        BlockBase.registerItemModels(block, Arrays.asList(block.func_176223_P()));
    }

    @SideOnly(value=Side.CLIENT)
    public static void registerItemModels(Block block, Iterable<IBlockState> states) {
        BlockBase.registerItemModels(block, states, null);
    }

    @SideOnly(value=Side.CLIENT)
    public static void registerItemModels(Block block, Iterable<IBlockState> states, IStateMapper mapper) {
        Item item = Item.func_150898_a((Block)block);
        if (item == null || item == Items.field_190931_a) {
            return;
        }
        ResourceLocation loc = Util.getName(item);
        if (loc == null) {
            return;
        }
        Map locations = mapper != null ? mapper.func_178130_a(block) : null;
        for (IBlockState state : states) {
            ModelResourceLocation location;
            int meta = block.func_176201_c(state);
            ModelResourceLocation modelResourceLocation = location = locations != null ? (ModelResourceLocation)locations.get(state) : ModelUtil.getModelLocation(loc, state);
            if (location == null) {
                throw new RuntimeException("can't map state " + state);
            }
            ModelLoader.setCustomModelResourceLocation((Item)item, (int)meta, (ModelResourceLocation)location);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public static void registerDefaultVanillaItemModel(Block block, String path) {
        Item item = Item.func_150898_a((Block)block);
        if (item == null || item == Items.field_190931_a) {
            return;
        }
        ResourceLocation loc = Util.getName(item);
        if (loc == null) {
            return;
        }
        path = path == null || path.isEmpty() ? loc.toString() : path + '/' + loc.toString();
        ModelLoader.setCustomModelResourceLocation((Item)item, (int)0, (ModelResourceLocation)new ModelResourceLocation(path, null));
    }

    public String func_149739_a() {
        return "ic2." + super.func_149739_a().substring(5);
    }

    public String func_149732_F() {
        return Localization.translate(this.func_149739_a());
    }

    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }
}

