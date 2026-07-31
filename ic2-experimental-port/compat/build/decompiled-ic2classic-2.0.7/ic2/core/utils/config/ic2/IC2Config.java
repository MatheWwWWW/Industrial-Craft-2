/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.forgespi.language.IModInfo
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.utils.config.ic2;

import ic2.core.IC2;
import ic2.core.item.renders.features.EnergyHudRenderer;
import ic2.core.utils.config.config.Config;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.config.ConfigSection;
import ic2.core.utils.config.config.ConfigSettings;
import ic2.core.utils.config.ic2.CropDifficulty;
import ic2.core.utils.config.ic2.CropParticles;
import ic2.core.utils.config.ic2.HashSetCache;
import ic2.core.utils.config.ic2.PassiveGeneratorSetting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.registries.ForgeRegistries;

public class IC2Config {
    private ConfigHandler handler;
    public ConfigEntry.BoolValue disableBronzeTools;
    public ConfigEntry.BoolValue disableAluminumTools;
    public ConfigEntry.BoolValue disableBronzeArmor;
    public ConfigEntry.BoolValue disableAlternateVanillaCrafting;
    public ConfigEntry.BoolValue disableGlowstoneCrafting;
    public ConfigEntry.BoolValue disableGunpowderCrafting;
    public ConfigEntry.BoolValue disableExplosiveCrafting;
    public ConfigEntry.BoolValue disableEnergyNetCrash;
    public ConfigEntry.BoolValue tileProfiler;
    public ConfigEntry.BoolValue itemNBT;
    public ConfigEntry.BoolValue recipeHiding;
    public ConfigEntry.BoolValue showUndamagedDurability;
    public ConfigEntry.BoolValue fancyFluidExpansion;
    public ConfigEntry.BoolValue showDefaultRecyclerRecipe;
    public ConfigEntry.BoolValue boostOnSprint;
    public ConfigEntry.BoolValue pauseWiki;
    public ConfigEntry.BoolValue showCropInfo;
    public ConfigEntry.DoubleValue masterVolume;
    public ConfigEntry.DoubleValue blockVolume;
    public ConfigEntry.DoubleValue itemVolume;
    public ConfigEntry.DoubleValue backVolume;
    public ConfigEntry.EnumValue<CropParticles> cropParticles;
    public ConfigEntry.EnumValue<EnergyHudRenderer.HorizontalDocks> dockHorizontal;
    public ConfigEntry.EnumValue<EnergyHudRenderer.VerticalDocks> dockVertical;
    public ConfigEntry.EnumValue<EnergyHudRenderer.RenderOrientation> hudOrientation;
    public ConfigEntry.IntValue hudXOffset;
    public ConfigEntry.IntValue hudYOffset;
    public ConfigEntry.IntValue reactorOutput;
    public ConfigEntry.DoubleValue steamReactorMultiplier;
    public ConfigEntry.IntValue generatorOutput;
    public ConfigEntry.IntValue slagGenOutput;
    public ConfigEntry.IntValue geothermalOutput;
    public ConfigEntry.DoubleValue fluidGenMultiplier;
    public ConfigEntry.DoubleValue solarTurbine;
    public ConfigEntry.IntValue oceanGenOutput;
    public ConfigEntry.IntValue waveGenOutput;
    public ConfigEntry.IntValue steamTunnelOutput;
    public ConfigEntry.IntValue steamTurbineOutput;
    public ConfigEntry.IntValue windTurbinePassive;
    public ConfigEntry.IntValue windMillOutput;
    public PassiveGeneratorSetting thermalGenerator;
    public PassiveGeneratorSetting waterMill;
    public PassiveGeneratorSetting lvWaterMill;
    public PassiveGeneratorSetting mvWaterMill;
    public PassiveGeneratorSetting hvWaterMill;
    public ConfigEntry.IntValue solarPanel;
    public ConfigEntry.IntValue lvSolarPanel;
    public ConfigEntry.IntValue mvSolarPanel;
    public ConfigEntry.IntValue hvSolarPanel;
    public ConfigEntry.EnumValue<CropDifficulty> cropDifficulty;
    public ConfigEntry.DoubleValue electricSuitAbsorptionScale;
    public ConfigEntry.DoubleValue electricSuitEnergyCostModifier;
    public ConfigEntry.IntValue fluxBalance;
    public ConfigEntry.IntValue industrialWorkbenchExpansionLimit;
    public ConfigEntry.IntValue maxReactorTicks;
    public ConfigEntry.BoolValue reducedWhiskyBrewTime;
    public ConfigEntry.DoubleValue nukeDamage;
    public ConfigEntry.DoubleValue reactorDamage;
    public ConfigEntry.IntValue windChangeTime;
    public ConfigEntry.IntValue windStreams;
    public ConfigEntry.BoolValue oreTin;
    public ConfigEntry.BoolValue oreSilver;
    public ConfigEntry.BoolValue oreUranium;
    public ConfigEntry.BoolValue oreNetherSilver;
    public ConfigEntry.BoolValue oreNetherAluminium;
    public ConfigEntry.BoolValue oreRubberTree;
    public ConfigEntry.BoolValue enableSpecialElectricArmor;
    public ConfigEntry.BoolValue enableHardEnrichedUranium;
    public ConfigEntry.BoolValue teleporterKeepItems;
    public HashSetCache<String> ignoreEnchantabilityCheck;

    public IC2Config() {
        Config config = new Config("ic2");
        this.handler = IC2.FILE_WATCHER.createConfig(config, ConfigSettings.withFolder("ic2c"));
        ConfigSection balance = config.add("balance");
        ConfigSection generators = balance.addSubSection("generators");
        this.reactorOutput = generators.addInt("nuclearGeneratorMultiplier", 20);
        this.steamReactorMultiplier = (ConfigEntry.DoubleValue)generators.addDouble("steamReactorMultiplier", 2.0).setMin(0.1).setServerSynced();
        this.generatorOutput = (ConfigEntry.IntValue)generators.addInt("fuelGeneratorBase", 10).setServerSynced();
        this.slagGenOutput = (ConfigEntry.IntValue)generators.addInt("slagGenBase", 60).setServerSynced();
        this.geothermalOutput = (ConfigEntry.IntValue)generators.addInt("geoGeneratorBase", 20).setServerSynced();
        this.solarTurbine = (ConfigEntry.DoubleValue)generators.addDouble("solarTurbineMultiplier", 1.0).setMin(0.1).setServerSynced();
        this.steamTurbineOutput = (ConfigEntry.IntValue)generators.addInt("steamTurbineBase", 64).setServerSynced();
        this.steamTunnelOutput = (ConfigEntry.IntValue)generators.addInt("steamTunnelBase", 512).setServerSynced();
        this.fluidGenMultiplier = (ConfigEntry.DoubleValue)generators.addDouble("fluidGeneratorMultiplier", 1.0).setServerSynced();
        this.oceanGenOutput = (ConfigEntry.IntValue)generators.addInt("oceanGenBase", 100).setServerSynced();
        this.waveGenOutput = (ConfigEntry.IntValue)generators.addInt("waveGenBase", 15).setServerSynced();
        this.windTurbinePassive = (ConfigEntry.IntValue)generators.addInt("windTubrinePassive", 100, "How much passive energy should be required per fuel. Lower => more Production").setServerSynced();
        this.windMillOutput = (ConfigEntry.IntValue)generators.addInt("windmillOutput", 256).setServerSynced();
        this.thermalGenerator = new PassiveGeneratorSetting(generators, "thermalGenerator", 30, 2000);
        this.waterMill = new PassiveGeneratorSetting(generators, "waterMill", 1, 100);
        this.lvWaterMill = new PassiveGeneratorSetting(generators, "lvWaterMill", 4, 100);
        this.mvWaterMill = new PassiveGeneratorSetting(generators, "mvWaterMill", 32, 100);
        this.hvWaterMill = new PassiveGeneratorSetting(generators, "hvWaterMill", 256, 100);
        this.solarPanel = (ConfigEntry.IntValue)generators.addInt("solarPanelBase", 1).setServerSynced();
        this.lvSolarPanel = (ConfigEntry.IntValue)generators.addInt("lvSolarPanelBase", 8).setServerSynced();
        this.mvSolarPanel = (ConfigEntry.IntValue)generators.addInt("mvSolarPanelBase", 64).setServerSynced();
        this.hvSolarPanel = (ConfigEntry.IntValue)generators.addInt("hvSolarPanelBase", 512).setServerSynced();
        ConfigSection blocks = balance.addSubSection("blocks");
        ConfigSection items = balance.addSubSection("items");
        this.electricSuitAbsorptionScale = items.addDouble("electricSuitAbsorptionScale", 1.0, "Sets the Absorption Ratio of Electric Armor");
        this.electricSuitEnergyCostModifier = items.addDouble("electricSuitEnergyCostModifier", 1.0, "Modifies the Energy Cost per damage");
        this.fluxBalance = blocks.addInt("fluxBalance", 4, "RF per EU Produced");
        this.industrialWorkbenchExpansionLimit = (ConfigEntry.IntValue)blocks.addInt("expansion_limit", 6, "Defines the Expansion Limit for the Industrial Worktable").setRange(0, 20).setServerSynced();
        this.maxReactorTicks = blocks.addInt("maxSimulationTicks", 100, "This limits how many simulation ticks can be done in the reactor planner. This logic is offthread and is dumped into a threadpool.").setRange(0, 10000);
        this.reducedWhiskyBrewTime = blocks.addBool("reducedWhiskyBrewtime", false, "Reduces the Whisky Brew time by the factor of 10, This will only work in Singleplayer");
        this.cropDifficulty = blocks.addEnum("cropDifficulty", CropDifficulty.NORMAL, CropDifficulty.class, "Decides how hard IC2 Crops are. This affects, growth speed/stat breeding/requirements and a few other things.");
        ConfigSection client = config.add("client");
        this.showUndamagedDurability = client.addBool("showUndamagedDurability", true, "Show durability for undamaged, damageable items in advanced tooltip");
        this.fancyFluidExpansion = client.addBool("enableFluidExpansionRenderer", true, "Show the Tanks contents in the World");
        this.showDefaultRecyclerRecipe = client.addBool("showDefaultRecyclerRecipe", true, "Shows the default recycle recipe in JEI");
        this.boostOnSprint = client.addBool("boostOnSprint", true, "Decides if you use Speed Module Boost on Sprinting or requring a dedicated key");
        this.pauseWiki = client.addBool("pauseWiki", false, "Decides if the Wiki Pauses the game in singleplayer or not. Reduces the chance to be blown up by creepers while reading");
        this.cropParticles = client.addEnum("crop particles", CropParticles.ALL, CropParticles.class, "Decides how many crop particles are shown when a ic2 crop grows");
        this.showCropInfo = client.addBool("showCropInfo", true, "Displays the Farmland/SubSoil information on blocks that have them for IC2 Crops");
        ConfigSection audio = client.addSubSection("audio");
        this.masterVolume = audio.addDouble("master", 0.5);
        this.blockVolume = audio.addDouble("block", 1.0);
        this.itemVolume = audio.addDouble("item", 1.0);
        this.backVolume = audio.addDouble("backpack", 1.0);
        ConfigSection armorHud = client.addSubSection("armorHUD");
        this.dockHorizontal = armorHud.addEnum("dockHorizontal", EnergyHudRenderer.HorizontalDocks.LEFT, EnergyHudRenderer.HorizontalDocks.class);
        this.dockVertical = armorHud.addEnum("dockVertical", EnergyHudRenderer.VerticalDocks.CENTER, EnergyHudRenderer.VerticalDocks.class);
        this.hudOrientation = armorHud.addEnum("hudOrientation", EnergyHudRenderer.RenderOrientation.VERTICAL, EnergyHudRenderer.RenderOrientation.class);
        this.hudXOffset = armorHud.addInt("xOffset", 0);
        this.hudYOffset = armorHud.addInt("yOffset", 0);
        ConfigSection crafting = config.add("crafting");
        this.disableBronzeTools = crafting.addBool("disableBronzeTools", false, "Disable the recipes for bronze tools");
        this.disableAluminumTools = crafting.addBool("disableAluminumTools", false, "Disable the recipes for aluminum tools");
        this.disableBronzeArmor = crafting.addBool("disableBronzeArmor", false, "Disable the recipes for bronze armor");
        this.disableAlternateVanillaCrafting = crafting.addBool("disableAlternateVanillaRecipes", false, "Disable the alternate crafting recipes for Vanilla stuff");
        this.recipeHiding = crafting.addBool("enableSecretRecipeHiding", true, "Enable hiding of secret recipes in CraftGuide/NEI/JEI");
        this.disableGlowstoneCrafting = crafting.addBool("disableGlowstoneRecipe", false, "Disable the Glowstone crafting recipe");
        this.disableGunpowderCrafting = crafting.addBool("disableGunpowderRecipe", false, "Disable the Gunpowder crafting recipe");
        this.disableExplosiveCrafting = crafting.addBool("disableExplosiveRecipes", false, "Disable the crafting recipes for IC2 Explosives");
        ConfigSection features = config.add("features");
        this.enableSpecialElectricArmor = features.addBool("enableSpecialElectricArmor", true, "Enables Electric Damage absorption on Electric Armor");
        this.enableHardEnrichedUranium = features.addBool("enableHardEnrichedUranium", true, "Enable Harder Enriched Uranium Processing. Making it harder to create the enriched rods");
        this.teleporterKeepItems = features.addBool("enableTeleporterInventory", true, "Enable calculation of inventory weight when going through a teleporter");
        ConfigSection world = config.add("world");
        this.nukeDamage = world.addDouble("nukeDamage", 35.0, "Sets the strength of a Nuclear Explosion");
        this.reactorDamage = world.addDouble("reactorDamage", 45.0, "Sets the max strength of a Nuclear Reactor Explosion");
        this.windChangeTime = world.addInt("windUpdateRate", 6000, "Sets the time in Ticks for the Wind Update Cycle");
        this.windStreams = world.addInt("windStreams", 5, "Sets how many Wind Streams there are").setRange(1, 20);
        ConfigSection gen = world.addSubSection("gen");
        this.oreTin = gen.addBool("Tin Ore Gen", true);
        this.oreSilver = gen.addBool("Silver Ore Gen", true);
        this.oreUranium = gen.addBool("Uranium Ore Gen", true);
        this.oreRubberTree = gen.addBool("Rubber Tree Gen", true);
        this.oreNetherSilver = gen.addBool("Nether Silver Ore Gen", true);
        this.oreNetherAluminium = gen.addBool("Nether Aluminium Ore Gen", true);
        ConfigSection compat = config.add("compat");
        this.ignoreEnchantabilityCheck = HashSetCache.create(compat.addArray("ignoreEnchantmentCompatibilityCheck", "Enchantment/Mod IDs for enchantments where canApplyAtEnchantingTable should be ignored").setServerSynced(), this.handler);
        ConfigSection debug = config.add("debug");
        this.tileProfiler = debug.addBool("Enable Tile Profiler", false, "Enables the Tile Profiler, Doubles Lag in worst cases. Requires TheOneProbe to see data, Uses Hud Key when looking at a block shows lag of Ticking Block");
        this.itemNBT = debug.addBool("Display Item NBT", false, "Enables that Ctrl shows Item NBTData and Shift Prettifies it.");
        this.disableEnergyNetCrash = debug.addBool("Disable Crash Validation", false, "Just a Temp Config that exists as a failsafe if a fix didn't work so it can be solved temporarily. Will be removed if validated that this crash was solved");
        this.handler.register();
        this.addLoadedListener(IC2.PLATFORM::sendPlayerSpecificConfig);
    }

    public void loadSuggestions() {
        ConfigEntry.ArrayValue value = (ConfigEntry.ArrayValue)this.ignoreEnchantabilityCheck.configEntry;
        for (IModInfo info : ModList.get().getMods()) {
            value.addSuggestion(info.getDisplayName(), info.getModId());
        }
        for (Enchantment ench : ForgeRegistries.ENCHANTMENTS) {
            value.addSuggestion(I18n.m_118938_((String)ench.m_44704_(), (Object[])new Object[0]), ForgeRegistries.ENCHANTMENTS.getKey((Object)ench).toString(), Enchantment.class);
        }
    }

    public void load() {
        this.handler.load();
    }

    public void save() {
        this.handler.save();
    }

    public void addLoadedListener(Runnable listener) {
        this.handler.addLoadedListener(listener);
    }

    public Config getConfig() {
        return this.handler.getConfig();
    }

    public ConfigHandler getHandler() {
        return this.handler;
    }
}

