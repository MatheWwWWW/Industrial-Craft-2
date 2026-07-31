/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  joptsimple.AbstractOptionSpec
 *  joptsimple.ArgumentAcceptingOptionSpec
 *  joptsimple.OptionParser
 *  joptsimple.OptionSet
 *  joptsimple.OptionSpec
 *  joptsimple.OptionSpecBuilder
 */
package net.minecraft.data;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.info.BiomeParametersDumpReport;
import net.minecraft.data.info.BlockListReport;
import net.minecraft.data.info.CommandsReport;
import net.minecraft.data.info.RegistryDumpReport;
import net.minecraft.data.info.WorldgenRegistryDumpReport;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.SnbtToNbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.CatVariantTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.FlatLevelGeneratorPresetTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.data.tags.InstrumentTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.data.tags.WorldPresetTagsProvider;
import net.minecraft.obfuscate.DontObfuscate;

public class Main {
    @DontObfuscate
    public static void main(String[] p_129669_) throws IOException {
        SharedConstants.m_142977_();
        OptionParser $$1 = new OptionParser();
        AbstractOptionSpec $$2 = $$1.accepts("help", "Show the help menu").forHelp();
        OptionSpecBuilder $$3 = $$1.accepts("server", "Include server generators");
        OptionSpecBuilder $$4 = $$1.accepts("client", "Include client generators");
        OptionSpecBuilder $$5 = $$1.accepts("dev", "Include development tools");
        OptionSpecBuilder $$6 = $$1.accepts("reports", "Include data reports");
        OptionSpecBuilder $$7 = $$1.accepts("validate", "Validate inputs");
        OptionSpecBuilder $$8 = $$1.accepts("all", "Include all generators");
        ArgumentAcceptingOptionSpec $$9 = $$1.accepts("output", "Output folder").withRequiredArg().defaultsTo((Object)"generated", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$10 = $$1.accepts("input", "Input folder").withRequiredArg();
        OptionSet $$11 = $$1.parse(p_129669_);
        if ($$11.has((OptionSpec)$$2) || !$$11.hasOptions()) {
            $$1.printHelpOn((OutputStream)System.out);
            return;
        }
        Path $$12 = Paths.get((String)$$9.value($$11), new String[0]);
        boolean $$13 = $$11.has((OptionSpec)$$8);
        boolean $$14 = $$13 || $$11.has((OptionSpec)$$4);
        boolean $$15 = $$13 || $$11.has((OptionSpec)$$3);
        boolean $$16 = $$13 || $$11.has((OptionSpec)$$5);
        boolean $$17 = $$13 || $$11.has((OptionSpec)$$6);
        boolean $$18 = $$13 || $$11.has((OptionSpec)$$7);
        DataGenerator $$19 = Main.m_236679_($$12, $$11.valuesOf((OptionSpec)$$10).stream().map(p_129659_ -> Paths.get(p_129659_, new String[0])).collect(Collectors.toList()), $$14, $$15, $$16, $$17, $$18, SharedConstants.m_183709_(), true);
        $$19.m_123917_();
    }

    public static DataGenerator m_236679_(Path p_236680_, Collection<Path> p_236681_, boolean p_236682_, boolean p_236683_, boolean p_236684_, boolean p_236685_, boolean p_236686_, WorldVersion p_236687_, boolean p_236688_) {
        DataGenerator $$9 = new DataGenerator(p_236680_, p_236681_, p_236687_, p_236688_);
        $$9.m_236039_(p_236682_ || p_236683_, new SnbtToNbt($$9).m_126475_(new StructureUpdater()));
        $$9.m_236039_(p_236682_, new ModelProvider($$9));
        $$9.m_236039_(p_236683_, new AdvancementProvider($$9));
        $$9.m_236039_(p_236683_, new LootTableProvider($$9));
        $$9.m_236039_(p_236683_, new RecipeProvider($$9));
        BlockTagsProvider $$10 = new BlockTagsProvider($$9);
        $$9.m_236039_(p_236683_, $$10);
        $$9.m_236039_(p_236683_, new ItemTagsProvider($$9, $$10));
        $$9.m_236039_(p_236683_, new BannerPatternTagsProvider($$9));
        $$9.m_236039_(p_236683_, new BiomeTagsProvider($$9));
        $$9.m_236039_(p_236683_, new CatVariantTagsProvider($$9));
        $$9.m_236039_(p_236683_, new EntityTypeTagsProvider($$9));
        $$9.m_236039_(p_236683_, new FlatLevelGeneratorPresetTagsProvider($$9));
        $$9.m_236039_(p_236683_, new FluidTagsProvider($$9));
        $$9.m_236039_(p_236683_, new GameEventTagsProvider($$9));
        $$9.m_236039_(p_236683_, new InstrumentTagsProvider($$9));
        $$9.m_236039_(p_236683_, new PaintingVariantTagsProvider($$9));
        $$9.m_236039_(p_236683_, new PoiTypeTagsProvider($$9));
        $$9.m_236039_(p_236683_, new StructureTagsProvider($$9));
        $$9.m_236039_(p_236683_, new WorldPresetTagsProvider($$9));
        $$9.m_236039_(p_236684_, new NbtToSnbt($$9));
        $$9.m_236039_(p_236685_, new BiomeParametersDumpReport($$9));
        $$9.m_236039_(p_236685_, new BlockListReport($$9));
        $$9.m_236039_(p_236685_, new CommandsReport($$9));
        $$9.m_236039_(p_236685_, new RegistryDumpReport($$9));
        $$9.m_236039_(p_236685_, new WorldgenRegistryDumpReport($$9));
        return $$9;
    }
}

