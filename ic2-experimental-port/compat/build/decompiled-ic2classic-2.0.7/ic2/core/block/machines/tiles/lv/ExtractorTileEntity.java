/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.Tags$Items
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.recipes.misc.RecipeFlags;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.block.machines.recipes.misc.ScrapBoxOutput;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class ExtractorTileEntity
extends BasicMachineTileEntity {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_extractor.png");

    public ExtractorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 2, 400, 32);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).extractor;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.EXTRACTOR_PROCESSING;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.EXTRACTOR;
    }

    public static void loadRecipes(IMachineRecipeList list) {
        list.addIC2XPRecipe("coal_extraction", new ItemStack((ItemLike)Items.f_42413_, 5), 0.3f, Tags.Items.ORES_COAL);
        list.addIC2XPRecipe("lapis_extraction", new ItemStack((ItemLike)Items.f_42534_, 10), 0.5f, Tags.Items.ORES_LAPIS);
        list.addIC2XPRecipe("diamond_extraction", new ItemStack((ItemLike)Items.f_42415_, 3), 1.0f, Tags.Items.ORES_DIAMOND);
        list.addIC2XPRecipe("emerald_extraction", new ItemStack((ItemLike)Items.f_42616_, 3), 1.0f, Tags.Items.ORES_EMERALD);
        list.addIC2XPRecipe("quartz_extraction", new ItemStack((ItemLike)Items.f_42692_, 3), 1.0f, Tags.Items.ORES_QUARTZ);
        list.addIC2SimpleRecipe("uranium_extraction", new ItemStack((ItemLike)IC2Items.ORE_URANIUM_DROP, 3), IC2Tags.ORE_URANIUM);
        list.addIC2SimpleRecipe("flint_extraction", new ItemStack((ItemLike)Items.f_42484_, 2), Items.f_41832_);
        list.addIC2SimpleRecipe("sticky_extraction", new ItemStack((ItemLike)IC2Items.RUBBER, 3), IC2Items.STICKY_RESIN);
        list.addIC2SimpleRecipe("rubber_log_extraction", new ItemStack((ItemLike)IC2Items.RUBBER), IC2Blocks.RUBBERWOOD_LOG);
        list.addIC2SimpleRecipe("rubber_sapling_extraction", new ItemStack((ItemLike)IC2Items.RUBBER), IC2Blocks.RUBBER_SAPLING);
        list.addIC2SimpleRecipe("poppy_extraction", new ItemStack((ItemLike)Items.f_42497_, 3), Items.f_41940_);
        list.addIC2SimpleRecipe("dandelion_extraction", new ItemStack((ItemLike)Items.f_42539_, 3), Items.f_41939_);
        list.addIC2SimpleRecipe("blue_orchid_extraction", new ItemStack((ItemLike)Items.f_42538_, 3), Items.f_41941_);
        list.addIC2SimpleRecipe("allium_extraction", new ItemStack((ItemLike)Items.f_42537_, 3), Items.f_41942_);
        list.addIC2SimpleRecipe("azure_bluet_extraction", new ItemStack((ItemLike)Items.f_42491_, 3), Items.f_41943_);
        list.addIC2SimpleRecipe("red_tulip_extraction", new ItemStack((ItemLike)Items.f_42497_, 3), Items.f_41944_);
        list.addIC2SimpleRecipe("orange_tulip_extraction", new ItemStack((ItemLike)Items.f_42536_, 3), Items.f_41945_);
        list.addIC2SimpleRecipe("white_tulip_extraction", new ItemStack((ItemLike)Items.f_42491_, 3), Items.f_41946_);
        list.addIC2SimpleRecipe("pink_tulip_extraction", new ItemStack((ItemLike)Items.f_42489_, 3), Items.f_41947_);
        list.addIC2SimpleRecipe("oxeye_daisy_extraction", new ItemStack((ItemLike)Items.f_42491_, 3), Items.f_41948_);
        list.addIC2SimpleRecipe("cornflower_extraction", new ItemStack((ItemLike)Items.f_42494_, 3), Items.f_41949_);
        list.addIC2SimpleRecipe("lily_of_the_valley_extraction", new ItemStack((ItemLike)Items.f_42535_, 3), Items.f_41950_);
        list.addIC2SimpleRecipe("wither_rose_extraction", new ItemStack((ItemLike)Items.f_42498_, 3), Items.f_41951_);
        list.addIC2SimpleRecipe("beetroot_extraction", new ItemStack((ItemLike)Items.f_42497_, 3), Items.f_42732_);
        list.addIC2SimpleRecipe("sunflower_extraction", new ItemStack((ItemLike)Items.f_42539_, 6), Items.f_42206_);
        list.addIC2SimpleRecipe("lilac_extraction", new ItemStack((ItemLike)Items.f_42537_, 6), Items.f_42207_);
        list.addIC2SimpleRecipe("rose_bush_extraction", new ItemStack((ItemLike)Items.f_42497_, 6), Items.f_42208_);
        list.addIC2SimpleRecipe("peony_extraction", new ItemStack((ItemLike)Items.f_42489_, 6), Items.f_42209_);
        list.addIC2SimpleRecipe("hydration_cell", new ItemStack((ItemLike)IC2Items.HYDRATION_CELL), RecipeFlags.CONSUME_CONTAINERS.apply(), new Object[]{IC2Items.CELL_WATER});
        list.addIC2XPRecipe("bio_cell_refinement", new ItemStack((ItemLike)IC2Items.CELL_BIO_FUEL), 0.1f, IC2Items.CELL_BIO);
        list.addIC2XPRecipe("wood_gas_refinement", new ItemStack((ItemLike)IC2Items.CELL_BIO_FUEL), 0.1f, RecipeFlags.CONSUME_CONTAINERS.apply(), new Object[]{IC2Items.CELL_WOODGAS});
        list.addIC2XPRecipe("coal_cell_refinement", new ItemStack((ItemLike)IC2Items.CELL_COAL_FUEL), 0.1f, IC2Items.CELL_HYDRATED_COAL);
        list.addIC2XPRecipe("water_cell_refinement", new ItemStack((ItemLike)IC2Items.CELL_WATER), 0.1f, IC2Items.HYDRATION_CELL);
        list.addIC2SimpleRecipe("resin_removal", new ItemStack((ItemLike)IC2Blocks.DYNAMITE), IC2Items.STICKY_DYNAMITE);
        list.addIC2Recipe("scrapbox_unpacking", new ScrapBoxOutput(), IC2Items.SCRAPBOX);
        list.addIC2SimpleRecipe("slimy_buisness", new ItemStack((ItemLike)Items.f_42518_), Items.f_42542_);
    }
}

