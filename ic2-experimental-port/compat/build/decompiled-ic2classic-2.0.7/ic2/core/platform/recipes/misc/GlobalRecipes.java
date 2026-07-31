/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 */
package ic2.core.platform.recipes.misc;

import com.google.gson.JsonObject;
import ic2.api.recipes.RecipeRegistry;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.CookingMachineRecipeList;
import ic2.core.block.machines.recipes.ElectrolyzerRecipeList;
import ic2.core.block.machines.recipes.EnricherRecipeList;
import ic2.core.block.machines.recipes.FusionRecipeList;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.block.machines.recipes.MachineRecipeList;
import ic2.core.block.machines.recipes.MultiMachineRecipeList;
import ic2.core.block.machines.recipes.RareEarthRegistry;
import ic2.core.block.machines.recipes.RecyclerRecipeList;
import ic2.core.block.machines.recipes.RefiningRecipeList;
import ic2.core.block.machines.recipes.SawMillRecipeList;
import ic2.core.block.machines.tiles.hv.MassFabricatorTileEntity;
import ic2.core.block.machines.tiles.hv.UraniumEnchricherTileEntity;
import ic2.core.block.machines.tiles.luv.FusionReactorTileEntity;
import ic2.core.block.machines.tiles.lv.AlloySmelterTileEntity;
import ic2.core.block.machines.tiles.lv.CannerTileEntity;
import ic2.core.block.machines.tiles.lv.CompressorTileEntity;
import ic2.core.block.machines.tiles.lv.ElectrolyzerTileEntity;
import ic2.core.block.machines.tiles.lv.ExtractorTileEntity;
import ic2.core.block.machines.tiles.lv.MaceratorTileEntity;
import ic2.core.block.machines.tiles.lv.RareEarthExtractorTileEntity;
import ic2.core.block.machines.tiles.lv.RecyclerTileEntity;
import ic2.core.block.machines.tiles.lv.SawmillTileEntity;
import ic2.core.block.machines.tiles.mv.RefineryTileEntity;
import ic2.core.item.misc.ScrapBoxItem;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.RecipeUpdatePacket;
import ic2.core.platform.recipes.crafting.RecipeIC2Base;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.platform.recipes.misc.CannerRegistry;
import ic2.core.platform.recipes.misc.FluidFuelRegistry;
import ic2.core.platform.recipes.misc.FoodCanRegistry;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.platform.recipes.misc.PotionBrewRegistry;
import ic2.core.platform.recipes.misc.ScrapboxRegistry;
import ic2.core.platform.recipes.misc.UUMatterRegistry;
import ic2.core.platform.registries.IC2Fuels;
import ic2.core.platform.registries.IC2Potions;
import ic2.core.platform.registries.IC2Recipes;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.SimpleRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class GlobalRecipes {
    List<IRecipeList> recipes = CollectionUtils.createList();
    public MachineRecipeList furnace = this.createRecipes(new CookingMachineRecipeList(RecipeType.f_44108_, 200));
    public MachineRecipeList blast_furnace = this.createRecipes(new CookingMachineRecipeList(RecipeType.f_44109_, 100));
    public MachineRecipeList smoker = this.createRecipes(new CookingMachineRecipeList(RecipeType.f_44110_, 100));
    public MachineRecipeList macerator = this.createRecipes(new MachineRecipeList("macerator", MaceratorTileEntity::loadRecipes));
    public MachineRecipeList extractor = this.createRecipes(new MachineRecipeList("extractor", ExtractorTileEntity::loadRecipes));
    public MachineRecipeList compressor = this.createRecipes(new MachineRecipeList("compressor", CompressorTileEntity::loadRecipes));
    public RecyclerRecipeList recycler = this.createRecipes(new RecyclerRecipeList(RecyclerTileEntity::loadRecipes));
    public SawMillRecipeList sawmill = this.createRecipes(new SawMillRecipeList(SawmillTileEntity::loadRecipes));
    public RareEarthRegistry rare_earth = this.createRecipes(new RareEarthRegistry(RareEarthExtractorTileEntity::loadRecipes));
    public CannerRegistry canner = this.createRecipes(new CannerRegistry(CannerTileEntity::loadRecipes));
    public ElectrolyzerRecipeList electrolyzer = this.createRecipes(new ElectrolyzerRecipeList(ElectrolyzerTileEntity::loadRecipe));
    public MultiMachineRecipeList mixingFurnace = this.createRecipes(new MultiMachineRecipeList("mixing_furnace", 2, true, AlloySmelterTileEntity::loadRecipes));
    public ScrapboxRegistry scrapBoxes = this.createRecipes(new ScrapboxRegistry(ScrapBoxItem::loadBoxes));
    public UUMatterRegistry UU = this.createRecipes(new UUMatterRegistry(IC2Recipes::addUU));
    public FluidFuelRegistry fluid_fuel = this.createRecipes(new FluidFuelRegistry(IC2Fuels::registerFuels));
    public MachineRecipeList massFabricator = this.createRecipes(new MachineRecipeList("massfab", MassFabricatorTileEntity::loadAmplifiers));
    public EnricherRecipeList enricher = this.createRecipes(new EnricherRecipeList(UraniumEnchricherTileEntity::registerRecipes));
    public PotionBrewRegistry potions = this.createRecipes(new PotionBrewRegistry(IC2Potions::registerBrewing));
    public RefiningRecipeList refining = this.createRecipes(new RefiningRecipeList(RefineryTileEntity::loadRecipes));
    public FusionRecipeList fusionFuel = this.createRecipes(new FusionRecipeList(FusionReactorTileEntity::loadRecipes));

    private <T extends IRecipeList> T createRecipes(T recipe) {
        this.recipes.add(recipe);
        return recipe;
    }

    public void init() {
        IngredientRegistry.INSTANCE.init();
        AdvRecipeRegistry.INSTANCE.init();
        AdvRecipeRegistry.INSTANCE.registerListener(IC2.INSTANCE::loadRecipes);
        AdvRecipeRegistry.INSTANCE.registerListener(this.UU);
        this.setRecipes(true);
        FoodCanRegistry.INSTANCE.preInit();
    }

    public void setRecipes(boolean side) {
        RecipeRegistry.UU_SHAPES.set(this.UU, side);
        RecipeRegistry.FURNACE.set(this.furnace, side);
        RecipeRegistry.BLAST_FURNACE.set(this.blast_furnace, side);
        RecipeRegistry.SMOKER.set(this.smoker, side);
        RecipeRegistry.MACERATOR.set(this.macerator, side);
        RecipeRegistry.EXTRACTOR.set(this.extractor, side);
        RecipeRegistry.COMPRESSOR.set(this.compressor, side);
        RecipeRegistry.RECYCLER.set(this.recycler, side);
        RecipeRegistry.SAWMILL.set(this.sawmill, side);
        RecipeRegistry.RARE_EARTH.set(this.rare_earth, side);
        RecipeRegistry.CANNER.set(this.canner, side);
        RecipeRegistry.ELECTROLYZER.set(this.electrolyzer, side);
        RecipeRegistry.MIXING_FURNACE.set(this.mixingFurnace, side);
        RecipeRegistry.SCRAP_BOX.set(this.scrapBoxes, side);
        RecipeRegistry.FLUID_FUELS.set(this.fluid_fuel, side);
        RecipeRegistry.MASS_FABRICATOR.set(this.massFabricator, side);
        RecipeRegistry.POTION_BREWING.set(this.potions, side);
        RecipeRegistry.FUSION_REACTOR.set(this.fusionFuel, side);
    }

    public void reloadRecipes() {
        AdvRecipeRegistry.INSTANCE.reload();
        this.macerator.reload();
        this.compressor.reload();
        this.extractor.reload();
        this.furnace.reload();
        this.blast_furnace.reload();
        this.smoker.reload();
        this.recycler.reload();
        this.sawmill.reload();
        this.rare_earth.reload();
        this.canner.reload();
        this.electrolyzer.reload();
        this.mixingFurnace.reload();
        this.scrapBoxes.reload();
        this.fluid_fuel.reload();
        this.massFabricator.reload();
        this.enricher.reload();
        this.potions.reload();
        this.refining.reload();
        this.fusionFuel.reload();
    }

    public void loadDataPackRecipes(Map<String, Map<ResourceLocation, JsonObject>> files) {
        for (IRecipeList list : this.recipes) {
            Map<ResourceLocation, JsonObject> map;
            String folder = list.getFolder();
            if (folder.equals("Empty") || (map = files.get(folder)) == null) continue;
            for (Map.Entry<ResourceLocation, JsonObject> entry : map.entrySet()) {
                this.readRecipes(list, entry.getKey(), entry.getValue());
            }
        }
    }

    protected void readRecipes(IRecipeList list, ResourceLocation location, JsonObject obj) {
        try {
            list.readRecipe(location, obj);
        }
        catch (Exception e) {
            IC2.LOGGER.error("Recipe [" + location + "] for Recipe List [" + list.getFolder() + "] wasn't able to load. Json Data: [" + obj + "]");
            IC2.LOGGER.catching((Throwable)e);
        }
    }

    public Map<ResourceLocation, JsonObject> createRecipeFiles() {
        IC2.LOGGER.info("Starting Serializing IC2 Recipes");
        Object2ObjectSortedMap map = CollectionUtils.createLinkedMap();
        for (IRecipeList list : this.recipes) {
            String folder = list.getFolder();
            if (folder.equals("Empty")) continue;
            IC2.LOGGER.info("Starting Serializing [" + folder + "] Recipes");
            list.writeRecipes().forEach((K, V) -> map.put(new ResourceLocation(K.m_135827_(), "ic2_recipes/" + folder + "/" + K.m_135815_()), V));
            IC2.LOGGER.info("Finished Serializing [" + folder + "] Recipes");
        }
        IC2.LOGGER.info("Starting Serializing [Crafting] Recipes");
        GlobalRecipes.serialize(AdvRecipeRegistry.INSTANCE.recipes, map, RecipeIC2Base::serialize);
        IC2.LOGGER.info("Finished Serializing [Crafting] Recipes");
        IC2.LOGGER.info("Finished Serializing IC2 Recipes");
        return map;
    }

    private static <T> void serialize(SimpleRegistry<T> registry, Map<ResourceLocation, JsonObject> result, Function<T, JsonObject> valueGen) {
        for (Map.Entry<ResourceLocation, T> entry : registry.entrySet()) {
            ResourceLocation key = entry.getKey();
            result.put(new ResourceLocation(key.m_135827_(), "recipes/" + key.m_135815_()), valueGen.apply(entry.getValue()));
        }
    }

    public int getFuel(ItemStack stack, boolean lava) {
        if (stack.m_41619_() || !lava && FluidUtil.getFluidContained((ItemStack)stack).orElse(FluidStack.EMPTY).getFluid() == Fluids.f_76195_) {
            return 0;
        }
        return ForgeHooks.getBurnTime((ItemStack)stack, (RecipeType)RecipeType.f_44108_);
    }

    public void sendRecipes() {
        PacketManager.INSTANCE.sendToAllPlayers(new RecipeUpdatePacket(this.recipes));
    }

    public void sendRecipesToPlayer(Player player) {
        PacketManager.INSTANCE.sendToPlayer(new RecipeUpdatePacket(this.recipes), player);
    }

    public List<IRecipeList> getLists() {
        return this.recipes;
    }
}

