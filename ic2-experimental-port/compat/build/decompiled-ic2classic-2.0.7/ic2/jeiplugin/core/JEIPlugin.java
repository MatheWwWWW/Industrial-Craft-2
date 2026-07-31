/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mezz.jei.api.IModPlugin
 *  mezz.jei.api.JeiPlugin
 *  mezz.jei.api.constants.RecipeTypes
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.handlers.IGuiContainerHandler
 *  mezz.jei.api.ingredients.IIngredientType
 *  mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter
 *  mezz.jei.api.recipe.IFocus
 *  mezz.jei.api.recipe.IRecipeManager
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  mezz.jei.api.recipe.transfer.IRecipeTransferHandler
 *  mezz.jei.api.registration.IGuiHandlerRegistration
 *  mezz.jei.api.registration.IRecipeCatalystRegistration
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  mezz.jei.api.registration.IRecipeRegistration
 *  mezz.jei.api.registration.IRecipeTransferRegistration
 *  mezz.jei.api.registration.ISubtypeRegistration
 *  mezz.jei.api.runtime.IJeiRuntime
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.jeiplugin.core;

import ic2.api.items.electric.IElectricItem;
import ic2.api.recipes.ingridients.inputs.IngredientInput;
import ic2.api.recipes.misc.RecipeFlags;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.api.recipes.registries.IFluidFuelRegistry;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.recipes.registries.IRareEarthRegistry;
import ic2.api.recipes.registries.IRefiningRecipeList;
import ic2.api.recipes.registries.IScrapBoxRegistry;
import ic2.core.IC2;
import ic2.core.block.generators.containers.LiquidFuelGenContainer;
import ic2.core.block.generators.tiles.LiquidFuelGenTileEntity;
import ic2.core.block.machines.containers.hv.UraniumEnricherContainer;
import ic2.core.block.machines.containers.lv.CannerContainer;
import ic2.core.block.machines.containers.lv.RareEarthExtractorContainer;
import ic2.core.block.machines.containers.mv.RefineryContainer;
import ic2.core.block.machines.logic.planner.encoder.ReactorSetup;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.block.machines.recipes.misc.ScrapOutput;
import ic2.core.block.machines.tiles.ev.ColossalCompressor;
import ic2.core.block.machines.tiles.ev.ColossalExtractor;
import ic2.core.block.machines.tiles.ev.ColossalFurnace;
import ic2.core.block.machines.tiles.ev.ColossalMacerator;
import ic2.core.block.machines.tiles.ev.ColossalRecycler;
import ic2.core.block.machines.tiles.hv.UraniumEnchricherTileEntity;
import ic2.core.block.machines.tiles.lv.AlloySmelterTileEntity;
import ic2.core.block.machines.tiles.lv.CannerTileEntity;
import ic2.core.block.machines.tiles.lv.CompressorTileEntity;
import ic2.core.block.machines.tiles.lv.ElectrolyzerTileEntity;
import ic2.core.block.machines.tiles.lv.ExtractorTileEntity;
import ic2.core.block.machines.tiles.lv.MaceratorTileEntity;
import ic2.core.block.machines.tiles.lv.RareEarthExtractorTileEntity;
import ic2.core.block.machines.tiles.lv.RecyclerTileEntity;
import ic2.core.block.machines.tiles.lv.SawmillTileEntity;
import ic2.core.block.machines.tiles.mv.RareEarthCentrifugeTileEntity;
import ic2.core.block.machines.tiles.mv.RefineryTileEntity;
import ic2.core.block.machines.tiles.mv.VacuumCannerTileEntity;
import ic2.core.block.machines.tiles.nv.StoneCannerTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.player.ReactorSubmissions;
import ic2.core.platform.recipes.crafting.RecipeIC2Base;
import ic2.core.platform.recipes.helpers.ItemStackCache;
import ic2.core.platform.recipes.misc.GlobalRecipes;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.plugins.IJEIHelper;
import ic2.jeiplugin.JEIModule;
import ic2.jeiplugin.core.IC2GuiHandler;
import ic2.jeiplugin.core.misc.BeerSubInterpreter;
import ic2.jeiplugin.core.misc.CropSubInterpreter;
import ic2.jeiplugin.core.misc.EUSubTypeInterpreter;
import ic2.jeiplugin.core.recipes.categories.BasicMachineCategory;
import ic2.jeiplugin.core.recipes.categories.CannerCategory;
import ic2.jeiplugin.core.recipes.categories.ElectrolyzerCategory;
import ic2.jeiplugin.core.recipes.categories.LiquidFuelGeneratorCategory;
import ic2.jeiplugin.core.recipes.categories.RareEarthCategory;
import ic2.jeiplugin.core.recipes.categories.ReactorCategory;
import ic2.jeiplugin.core.recipes.categories.RefineryCategory;
import ic2.jeiplugin.core.recipes.categories.ScrapBoxCategory;
import ic2.jeiplugin.core.recipes.categories.UraniumEnricherCategory;
import ic2.jeiplugin.core.recipes.categories.brew.BeerBrewCategory;
import ic2.jeiplugin.core.recipes.categories.brew.PotionBrewCategory;
import ic2.jeiplugin.core.recipes.categories.brew.RumBrewCategory;
import ic2.jeiplugin.core.transferhandler.CraftingUpgradeTransferHandler;
import ic2.jeiplugin.core.transferhandler.IndustrialWorkbenchTransferHandler;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@JeiPlugin
public class JEIPlugin
implements IModPlugin,
IJEIHelper {
    public static final RecipeType<IMachineRecipeList.RecipeEntry> MACERATOR = RecipeType.create((String)"ic2", (String)"macerator", IMachineRecipeList.RecipeEntry.class);
    public static final RecipeType<IMachineRecipeList.RecipeEntry> COMPRESSOR = RecipeType.create((String)"ic2", (String)"compressor", IMachineRecipeList.RecipeEntry.class);
    public static final RecipeType<IMachineRecipeList.RecipeEntry> EXTRACTOR = RecipeType.create((String)"ic2", (String)"extractor", IMachineRecipeList.RecipeEntry.class);
    public static final RecipeType<IMachineRecipeList.RecipeEntry> RECYCLER = RecipeType.create((String)"ic2", (String)"recycler", IMachineRecipeList.RecipeEntry.class);
    public static final RecipeType<IMachineRecipeList.RecipeEntry> SAWMILL = RecipeType.create((String)"ic2", (String)"sawmill", IMachineRecipeList.RecipeEntry.class);
    public static final RecipeType<IMachineRecipeList.RecipeEntry> ALLOY_SMELTER = RecipeType.create((String)"ic2", (String)"alloy_smelter", IMachineRecipeList.RecipeEntry.class);
    public static final RecipeType<IRareEarthRegistry.RareEntry> RARE_EARTH = RecipeType.create((String)"ic2", (String)"rare_earth", IRareEarthRegistry.RareEntry.class);
    public static final RecipeType<IScrapBoxRegistry.IDrop> SCRAP_BOX = RecipeType.create((String)"ic2", (String)"scrap_box", IScrapBoxRegistry.IDrop.class);
    public static final RecipeType<CannerCategory.CannerRecipe> CANNER = RecipeType.create((String)"ic2", (String)"canner", CannerCategory.CannerRecipe.class);
    public static final RecipeType<BeerBrewCategory.Brew> BEER_BREWING = RecipeType.create((String)"ic2", (String)"beer_brewing", BeerBrewCategory.Brew.class);
    public static final RecipeType<PotionBrewCategory.PotionBrew> POTION_BREWING = RecipeType.create((String)"ic2", (String)"potion_brewing", PotionBrewCategory.PotionBrew.class);
    public static final RecipeType<RumBrewCategory.CustomBrew> RUM_BREWING = RecipeType.create((String)"ic2", (String)"rum_brewing", RumBrewCategory.CustomBrew.class);
    public static final RecipeType<IElectrolyzerRecipeList.ElectrolyzerRecipe> ELECTROLYZER = RecipeType.create((String)"ic2", (String)"electrolyzer", IElectrolyzerRecipeList.ElectrolyzerRecipe.class);
    public static final RecipeType<IRefiningRecipeList.FluidRecipe> REFINERY = RecipeType.create((String)"ic2", (String)"refinery", IRefiningRecipeList.FluidRecipe.class);
    public static final RecipeType<IFluidFuelRegistry.FuelEntry> LIQUID_FUEL = RecipeType.create((String)"ic2", (String)"liquid_fuel", IFluidFuelRegistry.FuelEntry.class);
    public static final RecipeType<EnrichRecipe> URANIUM_ENRICH = RecipeType.create((String)"ic2", (String)"uranium_enricher", EnrichRecipe.class);
    public static final RecipeType<ReactorCategory.ReactorRecipe> REACTOR = RecipeType.create((String)"ic2", (String)"reactor", ReactorCategory.ReactorRecipe.class);
    IJeiRuntime runtime;

    public ResourceLocation getPluginUid() {
        return new ResourceLocation("ic2", "core");
    }

    @Override
    public boolean isValid() {
        return this.runtime != null;
    }

    @Override
    public void triggerRecipe(ItemStack stack) {
        if (this.runtime == null) {
            return;
        }
        IFocus focus = this.runtime.getJeiHelpers().getFocusFactory().createFocus(RecipeIngredientRole.OUTPUT, (IIngredientType)VanillaTypes.ITEM_STACK, (Object)stack);
        this.runtime.getRecipesGui().show(focus);
    }

    @Override
    public void triggerUsage(ItemStack stack) {
        if (this.runtime == null) {
            return;
        }
        ObjectList list = CollectionUtils.createList();
        list.add((IFocus)this.runtime.getJeiHelpers().getFocusFactory().createFocus(RecipeIngredientRole.INPUT, (IIngredientType)VanillaTypes.ITEM_STACK, (Object)stack));
        list.add((IFocus)this.runtime.getJeiHelpers().getFocusFactory().createFocus(RecipeIngredientRole.CATALYST, (IIngredientType)VanillaTypes.ITEM_STACK, (Object)stack));
        this.runtime.getRecipesGui().show(list);
    }

    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        if (!JEIModule.ALLOWS_LOADING) {
            return;
        }
        this.runtime = jeiRuntime;
        IC2.JEI_PLUGIN = this;
        if (IC2.CONFIG.recipeHiding.get()) {
            this.doJEIHiding(jeiRuntime, true);
        }
        IC2.CONFIG.addLoadedListener(() -> this.doJEIHiding(jeiRuntime, IC2.CONFIG.recipeHiding.get()));
    }

    @OnlyIn(value=Dist.CLIENT)
    private void doJEIHiding(IJeiRuntime jeiRuntime, boolean hidden) {
        if (Minecraft.m_91087_().f_91074_ == null) {
            return;
        }
        IRecipeManager manager = jeiRuntime.getRecipeManager();
        ObjectList recipeList = CollectionUtils.createList();
        for (CraftingRecipe entry : Minecraft.m_91087_().f_91074_.f_108617_.m_105141_().m_44013_(net.minecraft.world.item.crafting.RecipeType.f_44107_)) {
            if (!(entry instanceof RecipeIC2Base) || !((RecipeIC2Base)entry).isHidden()) continue;
            recipeList.add((CraftingRecipe)entry);
        }
        if (hidden) {
            manager.hideRecipes(RecipeTypes.CRAFTING, recipeList);
        } else {
            manager.unhideRecipes(RecipeTypes.CRAFTING, recipeList);
        }
        GlobalRecipes recipes = IC2.RECIPES.get(false);
        this.setHidden(recipes.macerator, hidden, manager, MACERATOR);
        this.setHidden(recipes.extractor, hidden, manager, EXTRACTOR);
        this.setHidden(recipes.compressor, hidden, manager, COMPRESSOR);
        this.setHidden(recipes.mixingFurnace, hidden, manager, ALLOY_SMELTER);
        this.setHidden(recipes.recycler, hidden, manager, RECYCLER);
        this.setHidden(recipes.sawmill, hidden, manager, SAWMILL);
    }

    public void setHidden(IMachineRecipeList list, boolean hide, IRecipeManager manager, RecipeType<IMachineRecipeList.RecipeEntry> id) {
        ObjectList recipeList = CollectionUtils.createList();
        for (IMachineRecipeList.RecipeEntry entry : list.getAllEntries()) {
            if (!RecipeFlags.HIDE_RECIPE.getFlag(entry.getOutput().getMetadata(), false)) continue;
            recipeList.add((IMachineRecipeList.RecipeEntry)entry);
        }
        if (hide) {
            manager.hideRecipes(id, recipeList);
        } else {
            manager.unhideRecipes(id, recipeList);
        }
    }

    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) {
            return;
        }
        registration.addRecipeTransferHandler((IRecipeTransferHandler)new IndustrialWorkbenchTransferHandler(), RecipeTypes.CRAFTING);
        registration.addRecipeTransferHandler((IRecipeTransferHandler)new CraftingUpgradeTransferHandler(), RecipeTypes.CRAFTING);
    }

    public void registerItemSubtypes(ISubtypeRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) {
            return;
        }
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof IElectricItem)) continue;
            registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, (Object)item, (IIngredientSubtypeInterpreter)EUSubTypeInterpreter.INSTANCE);
        }
        registration.useNbtForSubtypes(new Item[]{IC2Blocks.PLANTER_POT.m_5456_()});
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, (Object)IC2Items.GLASS_BEER, (IIngredientSubtypeInterpreter)BeerSubInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, (Object)IC2Items.MUG_BEER, (IIngredientSubtypeInterpreter)BeerSubInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, (Object)IC2Items.CROP_SEED, (IIngredientSubtypeInterpreter)CropSubInterpreter.INSTANCE);
        registration.useNbtForSubtypes(new Item[]{IC2Items.CF_PACK, IC2Items.CF_SPRAYER, IC2Items.GLASS_WHISKY, IC2Items.MUG_WHISKY, IC2Items.GLASS_RUM, IC2Items.MUG_RUM});
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) {
            return;
        }
        registration.addRecipeCategories(new IRecipeCategory[]{new BasicMachineCategory(registration.getJeiHelpers().getGuiHelper(), MACERATOR, MaceratorTileEntity.TEXTURE, (ItemLike)IC2Blocks.MACERATOR)});
        registration.addRecipeCategories(new IRecipeCategory[]{new BasicMachineCategory(registration.getJeiHelpers().getGuiHelper(), COMPRESSOR, CompressorTileEntity.TEXTURE, (ItemLike)IC2Blocks.COMPRESSOR)});
        registration.addRecipeCategories(new IRecipeCategory[]{new BasicMachineCategory(registration.getJeiHelpers().getGuiHelper(), EXTRACTOR, ExtractorTileEntity.TEXTURE, (ItemLike)IC2Blocks.EXTRACTOR)});
        registration.addRecipeCategories(new IRecipeCategory[]{new BasicMachineCategory(registration.getJeiHelpers().getGuiHelper(), RECYCLER, RecyclerTileEntity.TEXTURE, (ItemLike)IC2Blocks.RECYCLER)});
        registration.addRecipeCategories(new IRecipeCategory[]{new BasicMachineCategory(registration.getJeiHelpers().getGuiHelper(), SAWMILL, SawmillTileEntity.TEXTURE, (ItemLike)IC2Blocks.SAWMILL)});
        registration.addRecipeCategories(new IRecipeCategory[]{new BasicMachineCategory.AlloySmelterCategory(registration.getJeiHelpers().getGuiHelper(), ALLOY_SMELTER, AlloySmelterTileEntity.TEXTURE, (ItemLike)IC2Blocks.ALLOY_SMELTER)});
        registration.addRecipeCategories(new IRecipeCategory[]{new RareEarthCategory(registration.getJeiHelpers().getGuiHelper(), RARE_EARTH, RareEarthExtractorContainer.TEXTURE, (ItemLike)IC2Blocks.RARE_EARTH_EXTRACTOR)});
        registration.addRecipeCategories(new IRecipeCategory[]{new ScrapBoxCategory(registration.getJeiHelpers().getGuiHelper(), SCRAP_BOX, new ResourceLocation("ic2", "textures/gui_sprites/misc/scrap_box_jei.png"), (ItemLike)IC2Items.SCRAPBOX)});
        registration.addRecipeCategories(new IRecipeCategory[]{new CannerCategory(registration.getJeiHelpers().getGuiHelper(), CANNER, CannerContainer.TEXTURE, (ItemLike)IC2Blocks.CANNER)});
        registration.addRecipeCategories(new IRecipeCategory[]{new BeerBrewCategory(registration.getJeiHelpers().getGuiHelper(), BEER_BREWING, new ResourceLocation("ic2", "textures/gui_sprites/misc/beer_brew_jei.png"), (ItemLike)IC2Blocks.BARREL)});
        registration.addRecipeCategories(new IRecipeCategory[]{new PotionBrewCategory(registration.getJeiHelpers().getGuiHelper(), POTION_BREWING, new ResourceLocation("ic2", "textures/gui_sprites/misc/potion_brew_jei.png"), (ItemLike)IC2Blocks.BARREL)});
        registration.addRecipeCategories(new IRecipeCategory[]{new RumBrewCategory(registration.getJeiHelpers().getGuiHelper(), RUM_BREWING, new ResourceLocation("ic2", "textures/gui_sprites/misc/rum_brew_jei.png"), (ItemLike)IC2Blocks.BARREL)});
        registration.addRecipeCategories(new IRecipeCategory[]{new ElectrolyzerCategory(registration.getJeiHelpers().getGuiHelper(), ELECTROLYZER, ElectrolyzerTileEntity.TEXTURE, (ItemLike)IC2Blocks.ELECTROLYZER)});
        registration.addRecipeCategories(new IRecipeCategory[]{new RefineryCategory(registration.getJeiHelpers().getGuiHelper(), REFINERY, RefineryContainer.TEXTURE, (ItemLike)IC2Blocks.REFINERY)});
        registration.addRecipeCategories(new IRecipeCategory[]{new LiquidFuelGeneratorCategory(registration.getJeiHelpers().getGuiHelper(), LIQUID_FUEL, LiquidFuelGenContainer.TEXTURE, (ItemLike)IC2Blocks.LIQUID_FUEL_GENERATOR)});
        registration.addRecipeCategories(new IRecipeCategory[]{new UraniumEnricherCategory(registration.getJeiHelpers().getGuiHelper(), URANIUM_ENRICH, UraniumEnricherContainer.TEXTURE, (ItemLike)IC2Blocks.URANIUM_ENRICHER)});
        registration.addRecipeCategories(new IRecipeCategory[]{new ReactorCategory(registration.getJeiHelpers().getGuiHelper(), REACTOR, new ResourceLocation("ic2", "textures/gui_sprites/misc/jei_reactor.png"), (ItemLike)IC2Blocks.NUCLEAR_REACTOR)});
    }

    public void registerRecipes(IRecipeRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) {
            return;
        }
        GlobalRecipes recipes = IC2.RECIPES.get(false);
        registration.addRecipes(MACERATOR, recipes.macerator.getAllEntries());
        registration.addRecipes(COMPRESSOR, recipes.compressor.getAllEntries());
        registration.addRecipes(EXTRACTOR, recipes.extractor.getAllEntries());
        registration.addRecipes(RECYCLER, JEIPlugin.createRecyclerRecipes(recipes));
        registration.addRecipes(SAWMILL, recipes.sawmill.getAllEntries());
        registration.addRecipes(ALLOY_SMELTER, recipes.mixingFurnace.getAllEntries());
        registration.addRecipes(RARE_EARTH, recipes.rare_earth.getAllRecipes());
        registration.addRecipes(SCRAP_BOX, recipes.scrapBoxes.getAllDrops());
        registration.addRecipes(CANNER, CannerCategory.CannerRecipe.createRecipes(recipes));
        registration.addRecipes(BEER_BREWING, BeerBrewCategory.Brew.createBrew());
        registration.addRecipes(POTION_BREWING, PotionBrewCategory.PotionBrew.createList(recipes));
        registration.addRecipes(RUM_BREWING, RumBrewCategory.CustomBrew.create());
        registration.addRecipes(ELECTROLYZER, recipes.electrolyzer.getRecipes());
        registration.addRecipes(REFINERY, recipes.refining.getAllRecipes());
        registration.addRecipes(LIQUID_FUEL, recipes.fluid_fuel.getFuels());
        registration.addRecipes(URANIUM_ENRICH, recipes.enricher.getRecipes());
        ObjectList instances = CollectionUtils.createList();
        ReactorSubmissions.INSTANCE.getSubmissions(T -> {
            ReactorCategory.ReactorRecipe recipe = new ReactorCategory.ReactorRecipe((String)T.getKey(), (ReactorSetup)T.getValue());
            if (recipe.isValid()) {
                instances.add(recipe);
            }
        });
        registration.addRecipes(REACTOR, instances);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) {
            return;
        }
        GlobalRecipes recipes = IC2.RECIPES.get(false);
        Object2ObjectMap<IRecipeList, Object> locations = CollectionUtils.createMap();
        locations.put(recipes.furnace, RecipeTypes.SMELTING);
        locations.put(recipes.macerator, MACERATOR);
        locations.put(recipes.compressor, COMPRESSOR);
        locations.put(recipes.extractor, EXTRACTOR);
        locations.put(recipes.recycler, RECYCLER);
        locations.put(recipes.blast_furnace, RecipeTypes.BLASTING);
        locations.put(recipes.smoker, RecipeTypes.SMOKING);
        locations.put(recipes.sawmill, SAWMILL);
        locations.put(recipes.mixingFurnace, ALLOY_SMELTER);
        Object2ObjectMap<Class<UraniumEnchricherTileEntity>, Object> classBased = CollectionUtils.createMap();
        classBased.put(StoneCannerTileEntity.class, CANNER);
        classBased.put(CannerTileEntity.class, CANNER);
        classBased.put(VacuumCannerTileEntity.class, CANNER);
        classBased.put(ColossalFurnace.class, RecipeTypes.SMELTING);
        classBased.put(ColossalCompressor.class, COMPRESSOR);
        classBased.put(ColossalExtractor.class, EXTRACTOR);
        classBased.put(ColossalMacerator.class, MACERATOR);
        classBased.put(ColossalRecycler.class, RECYCLER);
        classBased.put(RareEarthExtractorTileEntity.class, RARE_EARTH);
        classBased.put(RareEarthCentrifugeTileEntity.class, RARE_EARTH);
        classBased.put(RefineryTileEntity.class, REFINERY);
        classBased.put(LiquidFuelGenTileEntity.class, LIQUID_FUEL);
        classBased.put(UraniumEnchricherTileEntity.class, URANIUM_ENRICH);
        registration.addGuiContainerHandler(IC2Screen.class, (IGuiContainerHandler)new IC2GuiHandler((Map<IMachineRecipeList, RecipeType<?>>)locations, (Map<Class<?>, RecipeType<?>>)classBased));
    }

    private static List<IMachineRecipeList.RecipeEntry> createRecyclerRecipes(GlobalRecipes recipe) {
        ObjectList result = CollectionUtils.createList();
        ObjectList scrapItems = CollectionUtils.createList();
        ObjectList scrapMetalItems = CollectionUtils.createList();
        ObjectArrayList<ItemStack> lists = ItemStackCache.INSTANCE.getCache();
        int m = lists.size();
        for (int i = 0; i < m; ++i) {
            IMachineRecipeList.RecipeEntry entry = recipe.recycler.getRecipe((ItemStack)lists.get(i), false);
            if (entry == null || !(entry.getOutput() instanceof ScrapOutput)) continue;
            String path = entry.getLocation().m_135815_();
            if (path.equalsIgnoreCase("scrap")) {
                scrapItems.add((ItemStack)((ItemStack)lists.get(i)));
                continue;
            }
            if (!path.equalsIgnoreCase("scrap_metal")) continue;
            scrapMetalItems.add((ItemStack)((ItemStack)lists.get(i)));
        }
        if (IC2.CONFIG.showDefaultRecyclerRecipe.get()) {
            result.add((IMachineRecipeList.RecipeEntry)new IMachineRecipeList.RecipeEntry(new ResourceLocation("ic2", "scrap"), new ScrapOutput(new ItemStack((ItemLike)IC2Items.SCRAP), 4), new IngredientInput(Ingredient.m_43927_((ItemStack[])scrapItems.toArray((ItemStack[])new ItemStack[scrapItems.size()])), 1)));
        }
        result.add((IMachineRecipeList.RecipeEntry)new IMachineRecipeList.RecipeEntry(new ResourceLocation("ic2", "scrap_metal"), new ScrapOutput(new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 8), new IngredientInput(Ingredient.m_43927_((ItemStack[])scrapMetalItems.toArray((ItemStack[])new ItemStack[scrapMetalItems.size()])), 1)));
        result.add((IMachineRecipeList.RecipeEntry)ScrapOutput.createEntry(0));
        result.addAll(recipe.recycler.getAllEntries());
        return result;
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) {
            return;
        }
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.INDUSTRIAL_WORKBENCH), new RecipeType[]{RecipeTypes.CRAFTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Items.CRAFTING_UPGRADE), new RecipeType[]{RecipeTypes.CRAFTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.STONE_MACERATOR), new RecipeType[]{MACERATOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.MACERATOR), new RecipeType[]{MACERATOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.ROTARY_MACERATOR), new RecipeType[]{MACERATOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.COLOSSAL_MACERATOR), new RecipeType[]{MACERATOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.COMPRESSOR), new RecipeType[]{COMPRESSOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.SINGULARITY_COMPRESSOR), new RecipeType[]{COMPRESSOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.COLOSSAL_COMPRESSOR), new RecipeType[]{COMPRESSOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.EXTRACTOR), new RecipeType[]{EXTRACTOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.CENTRIFUGAL_EXTRACTOR), new RecipeType[]{EXTRACTOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.COLOSSAL_EXTRACTOR), new RecipeType[]{EXTRACTOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.RECYCLER), new RecipeType[]{RECYCLER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.SLOW_GRINDER), new RecipeType[]{RECYCLER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.COMPACTING_RECYCLER), new RecipeType[]{RECYCLER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.COLOSSAL_RECYCLER), new RecipeType[]{RECYCLER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.SAWMILL), new RecipeType[]{SAWMILL});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.STONE_CANNER), new RecipeType[]{CANNER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.CANNER), new RecipeType[]{CANNER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.VACUUM_CANNER), new RecipeType[]{CANNER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.ELECTROLYZER), new RecipeType[]{ELECTROLYZER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.CHARGED_ELECTROLYZER), new RecipeType[]{ELECTROLYZER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.REFINERY), new RecipeType[]{REFINERY});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.URANIUM_ENRICHER), new RecipeType[]{URANIUM_ENRICH});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.LIQUID_FUEL_GENERATOR), new RecipeType[]{LIQUID_FUEL});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.ALLOY_SMELTER), new RecipeType[]{ALLOY_SMELTER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.PRESSURE_ALLOY_FURNACE), new RecipeType[]{ALLOY_SMELTER});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.RARE_EARTH_EXTRACTOR), new RecipeType[]{RARE_EARTH});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.CENTRIFUGAL_RARE_EARTH_EXTRACTOR), new RecipeType[]{RARE_EARTH});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Items.SCRAPBOX), new RecipeType[]{SCRAP_BOX});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.BARREL), new RecipeType[]{BEER_BREWING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.BARREL), new RecipeType[]{POTION_BREWING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.IRON_FURNACE), new RecipeType[]{RecipeTypes.SMELTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.ELECTRIC_FURNACE), new RecipeType[]{RecipeTypes.SMELTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.INDUCTION_FURNACE), new RecipeType[]{RecipeTypes.SMELTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.COLOSSAL_FURNACE), new RecipeType[]{RecipeTypes.SMELTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.ELECTRIC_BLAST_FURNACE), new RecipeType[]{RecipeTypes.BLASTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.INDUCTION_BLAST_FURNACE), new RecipeType[]{RecipeTypes.BLASTING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.ELECTRIC_SMOKER), new RecipeType[]{RecipeTypes.SMOKING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.INDUCTION_SMOKER), new RecipeType[]{RecipeTypes.SMOKING});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.ELECTRIC_ENCHANTER), new RecipeType[]{RecipeTypes.ANVIL});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.NUCLEAR_REACTOR), new RecipeType[]{REACTOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.STEAM_REACTOR), new RecipeType[]{REACTOR});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.REACTOR_PLANNER), new RecipeType[]{REACTOR});
    }
}

