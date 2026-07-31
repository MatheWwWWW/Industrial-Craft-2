/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.block.machines.recipes.misc.ScrapOutput;
import ic2.core.platform.recipes.crafting.helpers.DamagedInput;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RecyclerTileEntity
extends BasicMachineTileEntity {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_recycler.png");
    public static final IMachineRecipeList.RecipeEntry SCRAP_BOX = ScrapOutput.createEntry(68);

    public RecyclerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 1, 45, 32);
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(int slot, ItemStack input) {
        return input.m_41720_() == IC2Items.SCRAP ? (input.m_41613_() >= 9 ? SCRAP_BOX : null) : this.getRecipeList().getRecipe(input, true);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.RECYCLER_PROCESSING;
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).recycler;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.RECYCLER;
    }

    @Override
    protected boolean consumeContainers() {
        return true;
    }

    public static void loadRecipes(IMachineRecipeList list) {
        list.addIC2RangeRecipe("flesh_composting", new ItemStack((ItemLike)IC2Items.FERTILIZER), 0, 1, Items.f_42583_);
        list.addIC2Recipe("rare_earth_recycling", new SimpleRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)new ItemStack((ItemLike)IC2Items.SCRAP_METAL)), RecipeMods.RECIPE_TIME.create(10.0), 0.0f), IC2Items.RARE_EARTH_CHUNK);
        list.addIC2ChanceRecipe("basalt_fertilizer", new ItemStack((ItemLike)IC2Items.FERTILIZER), 0.0f, 0.25f, Items.f_42051_);
        RecyclerTileEntity.addRecycleRecipes(list);
    }

    public static void addRecycleRecipes(IMachineRecipeList list) {
        int i;
        list.addIC2XPRecipe("recycle_iron_horse_armor", new ItemStack((ItemLike)Items.f_42416_, 5), 0.2f, Items.f_42651_);
        list.addIC2XPRecipe("recycle_gold_horse_armor", new ItemStack((ItemLike)Items.f_42417_, 5), 0.5f, Items.f_42652_);
        list.addIC2XPRecipe("recycle_diamond_horse_armor", new ItemStack((ItemLike)Items.f_42415_, 5), 1.0f, Items.f_42653_);
        String[] type = new String[]{"diamond", "iron", "gold", "bronze"};
        Item[] axe = new Item[]{Items.f_42391_, Items.f_42386_, Items.f_42433_, IC2Items.AXE_BRONZE};
        Item[] pickaxe = new Item[]{Items.f_42390_, Items.f_42385_, Items.f_42432_, IC2Items.PICKAXE_BRONZE};
        Item[] shovel = new Item[]{Items.f_42389_, Items.f_42384_, Items.f_42431_, IC2Items.SHOVEL_BRONZE};
        Item[] hoe = new Item[]{Items.f_42392_, Items.f_42387_, Items.f_42434_, IC2Items.HOE_BRONZE};
        Item[] sword = new Item[]{Items.f_42388_, Items.f_42383_, Items.f_42430_, IC2Items.SWORD_BRONZE};
        Item[] helmet = new Item[]{Items.f_42472_, Items.f_42468_, Items.f_42476_, IC2Items.BRONZE_HELMET};
        Item[] chestplate = new Item[]{Items.f_42473_, Items.f_42469_, Items.f_42477_, IC2Items.BRONZE_CHEST};
        Item[] leggings = new Item[]{Items.f_42474_, Items.f_42470_, Items.f_42478_, IC2Items.BRONZE_LEGGINGS};
        Item[] boots = new Item[]{Items.f_42475_, Items.f_42471_, Items.f_42479_, IC2Items.BRONZE_BOOTS};
        ItemStack[] results = new ItemStack[]{new ItemStack((ItemLike)Items.f_42415_), new ItemStack((ItemLike)Items.f_42416_), new ItemStack((ItemLike)Items.f_42417_), new ItemStack((ItemLike)IC2Items.INGOT_BRONZE)};
        for (i = 0; i < type.length; ++i) {
            list.addIC2RangeRecipe("recycling_axe_" + type[i], results[i].m_41777_(), 0, 3, new DamagedInput(axe[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_pickaxe_" + type[i], results[i].m_41777_(), 0, 3, new DamagedInput(pickaxe[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_shovel_" + type[i], results[i].m_41777_(), 0, 1, new DamagedInput(shovel[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_hoe_" + type[i], results[i].m_41777_(), 0, 2, new DamagedInput(hoe[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_sword_" + type[i], results[i].m_41777_(), 0, 2, new DamagedInput(sword[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_helmet_" + type[i], results[i].m_41777_(), 0, 5, new DamagedInput(helmet[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_chestplate_" + type[i], results[i].m_41777_(), 0, 8, new DamagedInput(chestplate[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_leggings_" + type[i], results[i].m_41777_(), 0, 7, new DamagedInput(leggings[i]).setUndamaged());
            list.addIC2RangeRecipe("recycling_boots_" + type[i], results[i].m_41777_(), 0, 4, new DamagedInput(boots[i]).setUndamaged());
        }
        for (i = 0; i < type.length; ++i) {
            list.addIC2RangeRecipe("recycling_damaged_axe_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 3, new DamagedInput(axe[i]));
            list.addIC2RangeRecipe("recycling_damaged_pickaxe_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 3, new DamagedInput(pickaxe[i]));
            list.addIC2RangeRecipe("recycling_damaged_shovel_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 1, new DamagedInput(shovel[i]));
            list.addIC2RangeRecipe("recycling_damaged_hoe_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 2, new DamagedInput(hoe[i]));
            list.addIC2RangeRecipe("recycling_damaged_sword_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 2, new DamagedInput(sword[i]));
            list.addIC2RangeRecipe("recycling_damaged_helmet_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 5, new DamagedInput(helmet[i]));
            list.addIC2RangeRecipe("recycling_damaged_chestplate_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 8, new DamagedInput(chestplate[i]));
            list.addIC2RangeRecipe("recycling_damaged_leggings_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 7, new DamagedInput(leggings[i]));
            list.addIC2RangeRecipe("recycling_damaged_boots_" + type[i], new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 0, 4, new DamagedInput(boots[i]));
        }
        list.addIC2RangeRecipe("recycling_ancient_debris", new ItemStack((ItemLike)Items.f_42419_), 1, 5, Blocks.f_50722_);
    }
}

