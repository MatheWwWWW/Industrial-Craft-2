/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.Tags$Items
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.base.features.IParticleSpawner;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class MaceratorTileEntity
extends BasicMachineTileEntity
implements IParticleSpawner {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_macerator.png");

    public MaceratorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 2, 400, 32);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).macerator;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.MACERATOR_PROCESSING;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MACERATOR;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void animationTick(RandomSource rand) {
        if (this.isActive()) {
            float x = (float)this.f_58858_.m_123341_() + 1.0f;
            float y = (float)this.f_58858_.m_123342_() + 1.0f;
            float z = (float)this.f_58858_.m_123343_() + 1.0f;
            for (int i = 0; i < 4; ++i) {
                float xOffset = -0.2f - rand.m_188501_() * 0.6f;
                float yOffset = -0.1f + rand.m_188501_() * 0.2f;
                float zOffset = -0.2f - rand.m_188501_() * 0.6f;
                this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, (double)(x + xOffset), (double)(y + yOffset), (double)(z + zOffset), 0.0, 0.0, 0.0);
            }
        }
    }

    public static void loadRecipes(IMachineRecipeList list) {
        list.addIC2XPRecipe("coal_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COAL, 1), 0.1f, Items.f_42413_);
        list.addIC2XPRecipe("coal_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COAL, 9), 0.1f, Items.f_42200_);
        list.addIC2XPRecipe("charcoal_to_dust", new ItemStack((ItemLike)IC2Items.DUST_CHARCOAL, 1), 0.05f, Items.f_42414_);
        list.addIC2XPRecipe("charcoal_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_CHARCOAL, 9), 0.1f, IC2Blocks.CHARCOAL_BLOCK);
        list.addIC2XPRecipe("coal_ore_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COAL, 2), 0.5f, Tags.Items.ORES_COAL);
        list.addIC2XPRecipe("iron_ore_to_dust", new ItemStack((ItemLike)IC2Items.DUST_IRON, 2), 0.7f, Tags.Items.ORES_IRON);
        list.addIC2XPRecipe("gold_ore_to_dust", new ItemStack((ItemLike)IC2Items.DUST_GOLD, 2), 1.0f, Tags.Items.ORES_GOLD);
        list.addIC2XPRecipe("copper_ore_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COPPER, 2), 0.3f, IC2Tags.ORE_COPPER);
        list.addIC2XPRecipe("tin_ore_to_dust", new ItemStack((ItemLike)IC2Items.DUST_TIN, 2), 0.4f, IC2Tags.ORE_TIN);
        list.addIC2XPRecipe("silver_ore_to_dust", new ItemStack((ItemLike)IC2Items.DUST_SILVER, 2), 0.8f, IC2Tags.ORE_SILVER);
        list.addIC2SimpleRecipe("quartz_ore_to_dust", new ItemStack((ItemLike)Items.f_42692_, 2), Tags.Items.ORES_QUARTZ);
        list.addIC2XPRecipe("aluminum_ore_nether_to_dust", new ItemStack((ItemLike)IC2Items.DUST_ALUMINIUM, 2), 1.0f, IC2Tags.ORE_ALUMINIUM);
        list.addIC2XPRecipe("raw_iron_to_dust", new ItemStack((ItemLike)IC2Items.DUST_IRON, 2), 0.7f, Tags.Items.RAW_MATERIALS_IRON);
        list.addIC2XPRecipe("raw_gold_to_dust", new ItemStack((ItemLike)IC2Items.DUST_GOLD, 2), 1.0f, Tags.Items.RAW_MATERIALS_GOLD);
        list.addIC2XPRecipe("raw_copper_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COPPER, 2), 0.3f, Tags.Items.RAW_MATERIALS_COPPER);
        list.addIC2XPRecipe("raw_tin_to_dust", new ItemStack((ItemLike)IC2Items.DUST_TIN, 2), 0.4f, IC2Tags.RAW_TIN);
        list.addIC2XPRecipe("raw_silver_to_dust", new ItemStack((ItemLike)IC2Items.DUST_SILVER, 2), 0.8f, IC2Tags.RAW_SILVER);
        list.addIC2XPRecipe("raw_aluminum_to_dust", new ItemStack((ItemLike)IC2Items.DUST_ALUMINIUM, 2), 1.0f, IC2Tags.RAW_ALUMINIUM);
        list.addIC2SimpleRecipe("diamond_to_dust", new ItemStack((ItemLike)IC2Items.DUST_DIAMOND), Tags.Items.GEMS_DIAMOND);
        list.addIC2XPRecipe("raw_iron_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_IRON, 18), 6.3f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{Tags.Items.STORAGE_BLOCKS_RAW_IRON});
        list.addIC2XPRecipe("raw_gold_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_GOLD, 18), 9.0f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{Tags.Items.STORAGE_BLOCKS_RAW_GOLD});
        list.addIC2XPRecipe("raw_copper_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COPPER, 18), 2.7f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{Tags.Items.STORAGE_BLOCKS_RAW_COPPER});
        list.addIC2XPRecipe("raw_tin_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_TIN, 18), 3.6f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{IC2Tags.STORAGE_RAW_TIN});
        list.addIC2XPRecipe("raw_silver_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_SILVER, 18), 7.2f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{IC2Tags.STORAGE_RAW_SILVER});
        list.addIC2XPRecipe("raw_aluminum_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_ALUMINIUM, 18), 9.0f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{IC2Tags.STORAGE_RAW_ALUMINIUM});
        list.addIC2XPRecipe("iron_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_IRON, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{Tags.Items.STORAGE_BLOCKS_IRON});
        list.addIC2XPRecipe("gold_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_GOLD, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{Tags.Items.STORAGE_BLOCKS_GOLD});
        list.addIC2XPRecipe("copper_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COPPER, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{Tags.Items.STORAGE_BLOCKS_COPPER});
        list.addIC2XPRecipe("tin_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_TIN, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{IC2Tags.STORAGE_TIN});
        list.addIC2XPRecipe("silver_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_SILVER, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{IC2Tags.STORAGE_SILVER});
        list.addIC2XPRecipe("aluminum_block_to_dust", new ItemStack((ItemLike)IC2Items.DUST_ALUMINIUM, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{IC2Tags.STORAGE_ALUMINIUM});
        list.addIC2XPRecipe("iron_ingot_to_dust", new ItemStack((ItemLike)IC2Items.DUST_IRON), 0.1f, Tags.Items.INGOTS_IRON);
        list.addIC2XPRecipe("refined_iron_ingot_to_dust", new ItemStack((ItemLike)IC2Items.DUST_IRON), 0.1f, IC2Tags.INGOT_REFINED_IRON);
        list.addIC2XPRecipe("gold_ingot_to_dust", new ItemStack((ItemLike)IC2Items.DUST_GOLD), 0.1f, Tags.Items.INGOTS_GOLD);
        list.addIC2XPRecipe("copper_ingot_to_dust", new ItemStack((ItemLike)IC2Items.DUST_COPPER), 0.1f, IC2Tags.INGOT_COPPER);
        list.addIC2XPRecipe("tin_ingot_to_dust", new ItemStack((ItemLike)IC2Items.DUST_TIN), 0.1f, IC2Tags.INGOT_TIN);
        list.addIC2XPRecipe("silver_ingot_to_dust", new ItemStack((ItemLike)IC2Items.DUST_SILVER), 0.1f, IC2Tags.INGOT_SILVER);
        list.addIC2XPRecipe("aluminum_ingot_to_dust", new ItemStack((ItemLike)IC2Items.DUST_ALUMINIUM), 0.1f, IC2Tags.INGOT_ALUMINIUM);
        list.addIC2XPRecipe("blaze_to_dust", new ItemStack((ItemLike)Items.f_42593_, 5), 0.3f, Items.f_42585_);
        list.addIC2XPRecipe("wool_to_string", new ItemStack((ItemLike)Items.f_42401_, 4), 0.1f, ItemTags.f_13167_);
        list.addIC2XPRecipe("spider_to_grin", new ItemStack((ItemLike)IC2Items.GRIN_POWDER, 2), 0.4f, Items.f_42591_);
        list.addIC2XPRecipe("fish_to_grin", new ItemStack((ItemLike)IC2Items.GRIN_POWDER, 3), 0.4f, Items.f_42529_);
        list.addIC2XPRecipe("potato_to_grin", new ItemStack((ItemLike)IC2Items.GRIN_POWDER, 2), 0.4f, Items.f_42675_);
        list.addIC2XPRecipe("stone_to_gravel", new ItemStack((ItemLike)Items.f_41832_), 0.1f, Items.f_41905_);
        list.addIC2XPRecipe("cobble_to_sand", new ItemStack((ItemLike)Items.f_41830_), 0.1f, Items.f_42594_);
        list.addIC2XPRecipe("sandstone_to_sand", new ItemStack((ItemLike)Items.f_41830_), 0.1f, Items.f_41856_);
        list.addIC2XPRecipe("red_sandstone_to_sand", new ItemStack((ItemLike)Items.f_41831_), 0.1f, Items.f_42252_);
        list.addIC2XPRecipe("ice_to_snowball", new ItemStack((ItemLike)Items.f_42452_), 0.1f, Items.f_41980_);
        list.addIC2XPRecipe("clay_to_dust", new ItemStack((ItemLike)IC2Items.DUST_CLAY, 2), 0.3f, Items.f_41983_);
        list.addIC2XPRecipe("glowstone_to_dust", new ItemStack((ItemLike)Items.f_42525_, 4), 0.15f, Items.f_42054_);
        list.addIC2XPRecipe("bone_to_meal", new ItemStack((ItemLike)Items.f_42499_, 5), 0.2f, Items.f_42500_);
        list.addIC2XPRecipe("plant_to_dirt", new ItemStack((ItemLike)Items.f_42329_, 8), 0.1f, IC2Items.PLANT_BALL);
        list.addIC2XPRecipe("coffee_to_powder", new ItemStack((ItemLike)IC2Items.COFFEE_POWDER, 3), 0.4f, IC2Items.COFFEE_BEANS);
        list.addIC2XPRecipe("obsidian_to_dust", new ItemStack((ItemLike)IC2Items.DUST_OBSIDIAN), 0.1f, Items.f_41999_);
        list.addIC2XPRecipe("potatoe_to_flour", new ItemStack((ItemLike)IC2Items.FLOUR), 0.1f, Tags.Items.CROPS_POTATO);
        list.addIC2XPRecipe("wheat_to_flour", new ItemStack((ItemLike)IC2Items.FLOUR), 0.3f, Tags.Items.CROPS_WHEAT);
        list.addIC2XPRecipe("hay_to_flour", new ItemStack((ItemLike)IC2Items.FLOUR, 9), 2.7f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{Items.f_42129_});
        list.addIC2XPRecipe("bio_ball_to_fiber", new ItemStack((ItemLike)IC2Items.BIO_FIBER_RAW, 2), 0.1f, IC2Items.BIO_BALL);
        list.addIC2SimpleRecipe("redstone_ore_to_dust", new ItemStack((ItemLike)Items.f_42451_, 16), Tags.Items.ORES_REDSTONE);
        list.addIC2SimpleRecipe("netherrack_to_dust", new ItemStack((ItemLike)IC2Items.DUST_NETHERRACK), Items.f_42048_);
    }
}

