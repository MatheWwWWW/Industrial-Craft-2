/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.recipes.ingridients.inputs.FluidInput;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.INullableInput;
import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.TileCache;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.block.machines.tiles.lv.PumpTileEntity;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class CompressorTileEntity
extends BasicMachineTileEntity {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_compressor.png");
    TileCache<PumpTileEntity> pump_cache = new TileCache<PumpTileEntity>(this, DirectionList.ALL, PumpTileEntity.class);
    IMachineRecipeList.RecipeEntry pumpRecipe = new IMachineRecipeList.RecipeEntry(new ResourceLocation("ic2", "dummy"), new SimpleRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)new ItemStack((ItemLike)Items.f_42452_))), new INullableInput(){
        IInput fluidInput = new FluidInput((Fluid)Fluids.f_76193_);

        @Override
        public void serialize(FriendlyByteBuf buffer) {
        }

        @Override
        public boolean matches(ItemStack stack) {
            return stack.m_41619_() && CompressorTileEntity.this.getValidPump() != null;
        }

        @Override
        public int getInputSize() {
            return 1;
        }

        @Override
        public List<ItemStack> getComponents() {
            return this.fluidInput.getComponents();
        }
    });

    public CompressorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 2, 400, 32);
        this.addCaches(this.pump_cache);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).compressor;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.COMPRESSOR_PROCESSING;
    }

    @Override
    protected void onCachesUpdated() {
        if (!this.pump_cache.isEmpty()) {
            this.addToTick();
        }
    }

    @Override
    protected void onPreTick(boolean hasRecipe, boolean canWork, boolean canOperate) {
        if (!hasRecipe && canWork && this.getValidPump() != null) {
            this.rebuildCache = true;
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(int slot, ItemStack input) {
        return this.pumpRecipe.getInputs()[0].matches(input) ? this.pumpRecipe : (input.m_41619_() ? null : this.getRecipeList().getRecipe(input, true));
    }

    @Override
    public void consumeInput(IInput[] inputs, int slot, boolean emptyContainer) {
        if (inputs[0] == this.pumpRecipe.getInputs()[0]) {
            PumpTileEntity tile = this.getValidPump();
            if (tile != null) {
                tile.tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            }
            return;
        }
        super.consumeInput(inputs, slot, emptyContainer);
    }

    public PumpTileEntity getValidPump() {
        for (Direction dir : this.pump_cache) {
            PumpTileEntity pump = this.pump_cache.getHandler(dir);
            if (pump == null || pump.tank.drain(new FluidStack((Fluid)Fluids.f_76193_, 1000), IFluidHandler.FluidAction.SIMULATE).getAmount() != 1000) continue;
            return pump;
        }
        return null;
    }

    @Override
    public boolean canWorkWithoutItems() {
        return !this.pump_cache.isEmpty();
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.COMPRESSOR;
    }

    public static void loadRecipes(IMachineRecipeList list) {
        list.addIC2SimpleRecipe("sand_to_stone", new ItemStack((ItemLike)Items.f_41856_), Items.f_41830_);
        list.addIC2SimpleRecipe("red_sand_to_stone", new ItemStack((ItemLike)Items.f_42252_), Items.f_41831_);
        list.addIC2SimpleRecipe("netherrack_to_brick", new ItemStack((ItemLike)Items.f_42095_), new ItemStack((ItemLike)Items.f_42048_, 3));
        list.addIC2SimpleRecipe("snow_to_ice", new ItemStack((ItemLike)Items.f_41980_), Items.f_42452_);
        list.addIC2SimpleRecipe("ice_to_packed", new ItemStack((ItemLike)Items.f_42201_), new ItemStack((ItemLike)Items.f_41980_, 3));
        list.addIC2SimpleRecipe("packed_to_blue", new ItemStack((ItemLike)Items.f_42363_), new ItemStack((ItemLike)Items.f_42201_, 3));
        list.addIC2SimpleRecipe("blaze_powder_compression", new ItemStack((ItemLike)Items.f_42585_), new ItemStack((ItemLike)Items.f_42593_, 5));
        list.addIC2SimpleRecipe("water_to_snow", new ItemStack((ItemLike)Items.f_42452_), new FluidInput((Fluid)Fluids.f_76193_));
        list.addIC2ChanceRecipe("flint_to_gunpowder", new ItemStack((ItemLike)Items.f_42403_), 0.0f, 0.25f, Items.f_42484_);
        list.addIC2SimpleRecipe("uranium_ingot", new ItemStack((ItemLike)IC2Items.INGOT_URANIUM), IC2Tags.RAW_URANIUM);
        list.addIC2SimpleRecipe("dense_copper_plate", new ItemStack((ItemLike)IC2Items.PLATE_DENSE_COPPER), new ItemStack((ItemLike)Items.f_151052_, 8));
        list.addIC2SimpleRecipe("plant_ball_compression", new ItemStack((ItemLike)IC2Items.PLANT_BALL_COMPRESSED), IC2Items.PLANT_BALL);
        list.addIC2SimpleRecipe("coal_dust_compression", new ItemStack((ItemLike)IC2Items.HYDRATED_COAL_COMPRESSED), IC2Items.DUST_HYDRATED_COAL);
        list.addIC2SimpleRecipe("charcoal_dust_compression", new ItemStack((ItemLike)IC2Items.HYDRATED_COAL_COMPRESSED), IC2Items.DUST_HYDRATED_CHARCOAL);
        list.addIC2SimpleRecipe("air_compression", new ItemStack((ItemLike)IC2Items.CELL_AIR), IC2Items.CELL_EMPTY);
        list.addIC2SimpleRecipe("bio_leather", new ItemStack((ItemLike)IC2Items.BIO_LEATHER_RAW), IC2Items.BIO_FLESH_BALL);
        list.addIC2SimpleRecipe("super_cable", new ItemStack((ItemLike)IC2Items.SUPER_CABLE), IC2Items.UNCOMPRESSED_SUPER_CABLE);
        list.addIC2SimpleRecipe("machine_to_pipe", new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT, 10), IC2Blocks.MACHINE_BLOCK);
        list.addIC2SimpleRecipe("magnet_creation", new ItemStack((ItemLike)IC2Items.DEAD_MAGNET), IC2Items.RARE_EARTH_CHUNK);
        list.addIC2SimpleRecipe("carbon_plate_compression", new ItemStack((ItemLike)IC2Items.CARBON_PLATE), IC2Items.CARBON_MESH);
        list.addIC2SimpleRecipe("compressed_coal_ball", new ItemStack((ItemLike)IC2Items.COAL_BALL_COMPRESSED), IC2Items.COAL_BALL);
        list.addIC2SimpleRecipe("compressed_bio_ball", new ItemStack((ItemLike)IC2Items.BIO_BALL_COMPRESSED), IC2Items.BIO_BALL);
        list.addIC2SimpleRecipe("industrial_diamond_making", new ItemStack((ItemLike)IC2Items.INDUSTRIAL_DIAMOND), IC2Items.COAL_CHUNK);
        list.addIC2SimpleRecipe("scrap_compression", new ItemStack((ItemLike)IC2Items.SCRAP_METAL_CHUNK), new ItemStack((ItemLike)IC2Items.SCRAP_METAL, 8));
        list.addIC2SimpleRecipe("obsidian_blade_melding", new ItemStack((ItemLike)IC2Items.OBSIDIAN_BLADE), IC2Items.OBSIDIAN_BLADE_RAW);
        list.addIC2SimpleRecipe("mixed_metal_plate", new ItemStack((ItemLike)IC2Items.PLATE_ADVANCED_ALLOY), IC2Items.INGOT_ADVANCED_ALLOY);
        list.addIC2SimpleRecipe("cfoam_pellets", new ItemStack((ItemLike)IC2Items.CF_PELLET), IC2Blocks.CFOAM_WET);
        list.addIC2SimpleRecipe("diamond_compression", new ItemStack((ItemLike)Items.f_42415_), new ItemStack((ItemLike)IC2Items.DUST_DIAMOND, 4));
        list.addIC2SimpleRecipe("compress_solar_panel", new ItemStack((ItemLike)IC2Blocks.SOLAR_PANEL_COMPRESSED), IC2Blocks.SOLAR_PANEL);
        list.addIC2SimpleRecipe("compress_lv_solar_panel", new ItemStack((ItemLike)IC2Blocks.SOLAR_PANEL_LV_COMPRESSED), IC2Blocks.SOLAR_PANEL_LV);
        list.addIC2SimpleRecipe("compress_mv_solar_panel", new ItemStack((ItemLike)IC2Blocks.SOLAR_PANEL_MV_COMPRESSED), IC2Blocks.SOLAR_PANEL_MV);
        list.addIC2SimpleRecipe("compress_hv_solar_panel", new ItemStack((ItemLike)IC2Blocks.SOLAR_PANEL_HV_COMPRESSED), IC2Blocks.SOLAR_PANEL_HV);
        list.addIC2SimpleRecipe("compress_wiki", new ItemStack((ItemLike)IC2Items.WIKI_ITEM), IC2Items.UNPRINTED_WIKI);
    }
}

