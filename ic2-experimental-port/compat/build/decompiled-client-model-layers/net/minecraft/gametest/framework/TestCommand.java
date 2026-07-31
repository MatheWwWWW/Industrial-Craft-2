/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.gametest.framework;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestListener;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.gametest.framework.GameTestRunner;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.gametest.framework.MultipleTestTracker;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.gametest.framework.TestClassNameArgument;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.gametest.framework.TestFunctionArgument;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.io.IOUtils;

public class TestCommand {
    private static final int f_177786_ = 200;
    private static final int f_177787_ = 1024;
    private static final int f_177788_ = 15;
    private static final int f_177789_ = 200;
    private static final int f_177790_ = 3;
    private static final int f_177791_ = 10000;
    private static final int f_177792_ = 5;
    private static final int f_177793_ = 5;
    private static final int f_177794_ = 5;

    public static void m_127946_(CommandDispatcher<CommandSourceStack> p_127947_) {
        p_127947_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("test").then(Commands.m_82127_("runthis").executes(p_128057_ -> TestCommand.m_127950_((CommandSourceStack)p_128057_.getSource())))).then(Commands.m_82127_("runthese").executes(p_128055_ -> TestCommand.m_128001_((CommandSourceStack)p_128055_.getSource())))).then(((LiteralArgumentBuilder)Commands.m_82127_("runfailed").executes(p_128053_ -> TestCommand.m_127982_((CommandSourceStack)p_128053_.getSource(), false, 0, 8))).then(((RequiredArgumentBuilder)Commands.m_82129_("onlyRequiredTests", BoolArgumentType.bool()).executes(p_128051_ -> TestCommand.m_127982_((CommandSourceStack)p_128051_.getSource(), BoolArgumentType.getBool((CommandContext)p_128051_, (String)"onlyRequiredTests"), 0, 8))).then(((RequiredArgumentBuilder)Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes(p_128049_ -> TestCommand.m_127982_((CommandSourceStack)p_128049_.getSource(), BoolArgumentType.getBool((CommandContext)p_128049_, (String)"onlyRequiredTests"), IntegerArgumentType.getInteger((CommandContext)p_128049_, (String)"rotationSteps"), 8))).then(Commands.m_82129_("testsPerRow", IntegerArgumentType.integer()).executes(p_128047_ -> TestCommand.m_127982_((CommandSourceStack)p_128047_.getSource(), BoolArgumentType.getBool((CommandContext)p_128047_, (String)"onlyRequiredTests"), IntegerArgumentType.getInteger((CommandContext)p_128047_, (String)"rotationSteps"), IntegerArgumentType.getInteger((CommandContext)p_128047_, (String)"testsPerRow")))))))).then(Commands.m_82127_("run").then(((RequiredArgumentBuilder)Commands.m_82129_("testName", TestFunctionArgument.m_128088_()).executes(p_128045_ -> TestCommand.m_127978_((CommandSourceStack)p_128045_.getSource(), TestFunctionArgument.m_128091_((CommandContext<CommandSourceStack>)p_128045_, "testName"), 0))).then(Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes(p_128043_ -> TestCommand.m_127978_((CommandSourceStack)p_128043_.getSource(), TestFunctionArgument.m_128091_((CommandContext<CommandSourceStack>)p_128043_, "testName"), IntegerArgumentType.getInteger((CommandContext)p_128043_, (String)"rotationSteps"))))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("runall").executes(p_128041_ -> TestCommand.m_127955_((CommandSourceStack)p_128041_.getSource(), 0, 8))).then(((RequiredArgumentBuilder)Commands.m_82129_("testClassName", TestClassNameArgument.m_127917_()).executes(p_128039_ -> TestCommand.m_127962_((CommandSourceStack)p_128039_.getSource(), TestClassNameArgument.m_127920_((CommandContext<CommandSourceStack>)p_128039_, "testClassName"), 0, 8))).then(((RequiredArgumentBuilder)Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes(p_128037_ -> TestCommand.m_127962_((CommandSourceStack)p_128037_.getSource(), TestClassNameArgument.m_127920_((CommandContext<CommandSourceStack>)p_128037_, "testClassName"), IntegerArgumentType.getInteger((CommandContext)p_128037_, (String)"rotationSteps"), 8))).then(Commands.m_82129_("testsPerRow", IntegerArgumentType.integer()).executes(p_128035_ -> TestCommand.m_127962_((CommandSourceStack)p_128035_.getSource(), TestClassNameArgument.m_127920_((CommandContext<CommandSourceStack>)p_128035_, "testClassName"), IntegerArgumentType.getInteger((CommandContext)p_128035_, (String)"rotationSteps"), IntegerArgumentType.getInteger((CommandContext)p_128035_, (String)"testsPerRow"))))))).then(((RequiredArgumentBuilder)Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes(p_128033_ -> TestCommand.m_127955_((CommandSourceStack)p_128033_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_128033_, (String)"rotationSteps"), 8))).then(Commands.m_82129_("testsPerRow", IntegerArgumentType.integer()).executes(p_128031_ -> TestCommand.m_127955_((CommandSourceStack)p_128031_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_128031_, (String)"rotationSteps"), IntegerArgumentType.getInteger((CommandContext)p_128031_, (String)"testsPerRow"))))))).then(Commands.m_82127_("export").then(Commands.m_82129_("testName", StringArgumentType.word()).executes(p_128029_ -> TestCommand.m_128010_((CommandSourceStack)p_128029_.getSource(), StringArgumentType.getString((CommandContext)p_128029_, (String)"testName")))))).then(Commands.m_82127_("exportthis").executes(p_128027_ -> TestCommand.m_128008_((CommandSourceStack)p_128027_.getSource())))).then(Commands.m_82127_("import").then(Commands.m_82129_("testName", StringArgumentType.word()).executes(p_128025_ -> TestCommand.m_128015_((CommandSourceStack)p_128025_.getSource(), StringArgumentType.getString((CommandContext)p_128025_, (String)"testName")))))).then(((LiteralArgumentBuilder)Commands.m_82127_("pos").executes(p_128023_ -> TestCommand.m_127959_((CommandSourceStack)p_128023_.getSource(), "pos"))).then(Commands.m_82129_("var", StringArgumentType.word()).executes(p_128021_ -> TestCommand.m_127959_((CommandSourceStack)p_128021_.getSource(), StringArgumentType.getString((CommandContext)p_128021_, (String)"var")))))).then(Commands.m_82127_("create").then(((RequiredArgumentBuilder)Commands.m_82129_("testName", StringArgumentType.word()).executes(p_128019_ -> TestCommand.m_127967_((CommandSourceStack)p_128019_.getSource(), StringArgumentType.getString((CommandContext)p_128019_, (String)"testName"), 5, 5, 5))).then(((RequiredArgumentBuilder)Commands.m_82129_("width", IntegerArgumentType.integer()).executes(p_128014_ -> TestCommand.m_127967_((CommandSourceStack)p_128014_.getSource(), StringArgumentType.getString((CommandContext)p_128014_, (String)"testName"), IntegerArgumentType.getInteger((CommandContext)p_128014_, (String)"width"), IntegerArgumentType.getInteger((CommandContext)p_128014_, (String)"width"), IntegerArgumentType.getInteger((CommandContext)p_128014_, (String)"width")))).then(Commands.m_82129_("height", IntegerArgumentType.integer()).then(Commands.m_82129_("depth", IntegerArgumentType.integer()).executes(p_128007_ -> TestCommand.m_127967_((CommandSourceStack)p_128007_.getSource(), StringArgumentType.getString((CommandContext)p_128007_, (String)"testName"), IntegerArgumentType.getInteger((CommandContext)p_128007_, (String)"width"), IntegerArgumentType.getInteger((CommandContext)p_128007_, (String)"height"), IntegerArgumentType.getInteger((CommandContext)p_128007_, (String)"depth"))))))))).then(((LiteralArgumentBuilder)Commands.m_82127_("clearall").executes(p_128000_ -> TestCommand.m_127952_((CommandSourceStack)p_128000_.getSource(), 200))).then(Commands.m_82129_("radius", IntegerArgumentType.integer()).executes(p_127949_ -> TestCommand.m_127952_((CommandSourceStack)p_127949_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_127949_, (String)"radius"))))));
    }

    private static int m_127967_(CommandSourceStack p_127968_, String p_127969_, int p_127970_, int p_127971_, int p_127972_) {
        if (p_127970_ > 48 || p_127971_ > 48 || p_127972_ > 48) {
            throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
        }
        ServerLevel $$5 = p_127968_.m_81372_();
        BlockPos $$6 = new BlockPos(p_127968_.m_81371_());
        BlockPos $$7 = new BlockPos($$6.m_123341_(), p_127968_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, $$6).m_123342_(), $$6.m_123343_() + 3);
        StructureUtils.m_177764_(p_127969_.toLowerCase(), $$7, new Vec3i(p_127970_, p_127971_, p_127972_), Rotation.NONE, $$5);
        for (int $$8 = 0; $$8 < p_127970_; ++$$8) {
            for (int $$9 = 0; $$9 < p_127972_; ++$$9) {
                BlockPos $$10 = new BlockPos($$7.m_123341_() + $$8, $$7.m_123342_() + 1, $$7.m_123343_() + $$9);
                Block $$11 = Blocks.f_50387_;
                BlockInput $$12 = new BlockInput($$11.m_49966_(), Collections.emptySet(), null);
                $$12.m_114670_($$5, $$10, 2);
            }
        }
        StructureUtils.m_127875_($$7, new BlockPos(1, 0, -1), Rotation.NONE, $$5);
        return 0;
    }

    private static int m_127959_(CommandSourceStack p_127960_, String p_127961_) throws CommandSyntaxException {
        ServerLevel $$4;
        BlockHitResult $$2 = (BlockHitResult)p_127960_.m_81375_().m_19907_(10.0, 1.0f, false);
        BlockPos $$3 = $$2.m_82425_();
        Optional<BlockPos> $$5 = StructureUtils.m_127853_($$3, 15, $$4 = p_127960_.m_81372_());
        if (!$$5.isPresent()) {
            $$5 = StructureUtils.m_127853_($$3, 200, $$4);
        }
        if (!$$5.isPresent()) {
            p_127960_.m_81352_(Component.m_237113_("Can't find a structure block that contains the targeted pos " + $$3));
            return 0;
        }
        StructureBlockEntity $$6 = (StructureBlockEntity)$$4.m_7702_($$5.get());
        BlockPos $$7 = $$3.m_121996_($$5.get());
        String $$8 = $$7.m_123341_() + ", " + $$7.m_123342_() + ", " + $$7.m_123343_();
        String $$9 = $$6.m_59900_();
        MutableComponent $$10 = Component.m_237113_($$8).m_6270_(Style.f_131099_.m_131136_(true).m_131140_(ChatFormatting.GREEN).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237113_("Click to copy to clipboard"))).m_131142_(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "final BlockPos " + p_127961_ + " = new BlockPos(" + $$8 + ");")));
        p_127960_.m_81354_(Component.m_237113_("Position relative to " + $$9 + ": ").m_7220_($$10), false);
        DebugPackets.m_133682_($$4, new BlockPos($$3), $$8, -2147418368, 10000);
        return 1;
    }

    private static int m_127950_(CommandSourceStack p_127951_) {
        ServerLevel $$2;
        BlockPos $$1 = new BlockPos(p_127951_.m_81371_());
        BlockPos $$3 = StructureUtils.m_127906_($$1, 15, $$2 = p_127951_.m_81372_());
        if ($$3 == null) {
            TestCommand.m_127933_($$2, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
            return 0;
        }
        GameTestRunner.m_127685_($$2);
        TestCommand.m_127929_($$2, $$3, null);
        return 1;
    }

    private static int m_128001_(CommandSourceStack p_128002_) {
        ServerLevel $$2;
        BlockPos $$1 = new BlockPos(p_128002_.m_81371_());
        Collection<BlockPos> $$3 = StructureUtils.m_127910_($$1, 200, $$2 = p_128002_.m_81372_());
        if ($$3.isEmpty()) {
            TestCommand.m_127933_($$2, "Couldn't find any structure blocks within 200 block radius", ChatFormatting.RED);
            return 1;
        }
        GameTestRunner.m_127685_($$2);
        TestCommand.m_128003_(p_128002_, "Running " + $$3.size() + " tests...");
        MultipleTestTracker $$4 = new MultipleTestTracker();
        $$3.forEach(p_127943_ -> TestCommand.m_127929_($$2, p_127943_, $$4));
        return 1;
    }

    private static void m_127929_(ServerLevel p_127930_, BlockPos p_127931_, @Nullable MultipleTestTracker p_127932_) {
        StructureBlockEntity $$3 = (StructureBlockEntity)p_127930_.m_7702_(p_127931_);
        String $$4 = $$3.m_59900_();
        TestFunction $$5 = GameTestRegistry.m_127681_($$4);
        GameTestInfo $$6 = new GameTestInfo($$5, $$3.m_59906_(), p_127930_);
        if (p_127932_ != null) {
            p_127932_.m_127809_($$6);
            $$6.m_127624_(new TestSummaryDisplayer(p_127930_, p_127932_));
        }
        TestCommand.m_127993_($$5, p_127930_);
        AABB $$7 = StructureUtils.m_127847_($$3);
        BlockPos $$8 = new BlockPos($$7.f_82288_, $$7.f_82289_, $$7.f_82290_);
        GameTestRunner.m_127742_($$6, $$8, GameTestTicker.f_177648_);
    }

    static void m_127996_(ServerLevel p_127997_, MultipleTestTracker p_127998_) {
        if (p_127998_.m_127821_()) {
            TestCommand.m_127933_(p_127997_, "GameTest done! " + p_127998_.m_127820_() + " tests were run", ChatFormatting.WHITE);
            if (p_127998_.m_127818_()) {
                TestCommand.m_127933_(p_127997_, p_127998_.m_127803_() + " required tests failed :(", ChatFormatting.RED);
            } else {
                TestCommand.m_127933_(p_127997_, "All required tests passed :)", ChatFormatting.GREEN);
            }
            if (p_127998_.m_127819_()) {
                TestCommand.m_127933_(p_127997_, p_127998_.m_127816_() + " optional tests failed", ChatFormatting.GRAY);
            }
        }
    }

    private static int m_127952_(CommandSourceStack p_127953_, int p_127954_) {
        ServerLevel $$2 = p_127953_.m_81372_();
        GameTestRunner.m_127685_($$2);
        BlockPos $$3 = new BlockPos(p_127953_.m_81371_().f_82479_, (double)p_127953_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, new BlockPos(p_127953_.m_81371_())).m_123342_(), p_127953_.m_81371_().f_82481_);
        GameTestRunner.m_127694_($$2, $$3, GameTestTicker.f_177648_, Mth.m_14045_(p_127954_, 0, 1024));
        return 1;
    }

    private static int m_127978_(CommandSourceStack p_127979_, TestFunction p_127980_, int p_127981_) {
        ServerLevel $$3 = p_127979_.m_81372_();
        BlockPos $$4 = new BlockPos(p_127979_.m_81371_());
        int $$5 = p_127979_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, $$4).m_123342_();
        BlockPos $$6 = new BlockPos($$4.m_123341_(), $$5, $$4.m_123343_() + 3);
        GameTestRunner.m_127685_($$3);
        TestCommand.m_127993_(p_127980_, $$3);
        Rotation $$7 = StructureUtils.m_127835_(p_127981_);
        GameTestInfo $$8 = new GameTestInfo(p_127980_, $$7, $$3);
        GameTestRunner.m_127742_($$8, $$6, GameTestTicker.f_177648_);
        return 1;
    }

    private static void m_127993_(TestFunction p_127994_, ServerLevel p_127995_) {
        Consumer<ServerLevel> $$2 = GameTestRegistry.m_127676_(p_127994_.m_128081_());
        if ($$2 != null) {
            $$2.accept(p_127995_);
        }
    }

    private static int m_127955_(CommandSourceStack p_127956_, int p_127957_, int p_127958_) {
        GameTestRunner.m_127685_(p_127956_.m_81372_());
        Collection<TestFunction> $$3 = GameTestRegistry.m_127658_();
        TestCommand.m_128003_(p_127956_, "Running all " + $$3.size() + " tests...");
        GameTestRegistry.m_127678_();
        TestCommand.m_127973_(p_127956_, $$3, p_127957_, p_127958_);
        return 1;
    }

    private static int m_127962_(CommandSourceStack p_127963_, String p_127964_, int p_127965_, int p_127966_) {
        Collection<TestFunction> $$4 = GameTestRegistry.m_127659_(p_127964_);
        GameTestRunner.m_127685_(p_127963_.m_81372_());
        TestCommand.m_128003_(p_127963_, "Running " + $$4.size() + " tests from " + p_127964_ + "...");
        GameTestRegistry.m_127678_();
        TestCommand.m_127973_(p_127963_, $$4, p_127965_, p_127966_);
        return 1;
    }

    private static int m_127982_(CommandSourceStack p_127983_, boolean p_127984_, int p_127985_, int p_127986_) {
        Collection<TestFunction> $$5;
        if (p_127984_) {
            Collection $$4 = GameTestRegistry.m_127675_().stream().filter(TestFunction::m_128080_).collect(Collectors.toList());
        } else {
            $$5 = GameTestRegistry.m_127675_();
        }
        if ($$5.isEmpty()) {
            TestCommand.m_128003_(p_127983_, "No failed tests to rerun");
            return 0;
        }
        GameTestRunner.m_127685_(p_127983_.m_81372_());
        TestCommand.m_128003_(p_127983_, "Rerunning " + $$5.size() + " failed tests (" + (p_127984_ ? "only required tests" : "including optional tests") + ")");
        TestCommand.m_127973_(p_127983_, $$5, p_127985_, p_127986_);
        return 1;
    }

    private static void m_127973_(CommandSourceStack p_127974_, Collection<TestFunction> p_127975_, int p_127976_, int p_127977_) {
        BlockPos $$4 = new BlockPos(p_127974_.m_81371_());
        BlockPos $$5 = new BlockPos($$4.m_123341_(), p_127974_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, $$4).m_123342_(), $$4.m_123343_() + 3);
        ServerLevel $$6 = p_127974_.m_81372_();
        Rotation $$7 = StructureUtils.m_127835_(p_127976_);
        Collection<GameTestInfo> $$8 = GameTestRunner.m_127752_(p_127975_, $$5, $$7, $$6, GameTestTicker.f_177648_, p_127977_);
        MultipleTestTracker $$9 = new MultipleTestTracker($$8);
        $$9.m_127811_(new TestSummaryDisplayer($$6, $$9));
        $$9.m_127807_(p_127992_ -> GameTestRegistry.m_127664_(p_127992_.m_127648_()));
    }

    private static void m_128003_(CommandSourceStack p_128004_, String p_128005_) {
        p_128004_.m_81354_(Component.m_237113_(p_128005_), false);
    }

    private static int m_128008_(CommandSourceStack p_128009_) {
        ServerLevel $$2;
        BlockPos $$1 = new BlockPos(p_128009_.m_81371_());
        BlockPos $$3 = StructureUtils.m_127906_($$1, 15, $$2 = p_128009_.m_81372_());
        if ($$3 == null) {
            TestCommand.m_127933_($$2, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
            return 0;
        }
        StructureBlockEntity $$4 = (StructureBlockEntity)$$2.m_7702_($$3);
        String $$5 = $$4.m_59900_();
        return TestCommand.m_128010_(p_128009_, $$5);
    }

    private static int m_128010_(CommandSourceStack p_128011_, String p_128012_) {
        Path $$2 = Paths.get(StructureUtils.f_127833_, new String[0]);
        ResourceLocation $$3 = new ResourceLocation("minecraft", p_128012_);
        Path $$4 = p_128011_.m_81372_().m_215082_().m_230361_($$3, ".nbt");
        Path $$5 = NbtToSnbt.m_236381_(CachedOutput.f_236016_, $$4, p_128012_, $$2);
        if ($$5 == null) {
            TestCommand.m_128003_(p_128011_, "Failed to export " + $$4);
            return 1;
        }
        try {
            Files.createDirectories($$5.getParent(), new FileAttribute[0]);
        }
        catch (IOException $$6) {
            TestCommand.m_128003_(p_128011_, "Could not create folder " + $$5.getParent());
            $$6.printStackTrace();
            return 1;
        }
        TestCommand.m_128003_(p_128011_, "Exported " + p_128012_ + " to " + $$5.toAbsolutePath());
        return 0;
    }

    private static int m_128015_(CommandSourceStack p_128016_, String p_128017_) {
        Path $$2 = Paths.get(StructureUtils.f_127833_, p_128017_ + ".snbt");
        ResourceLocation $$3 = new ResourceLocation("minecraft", p_128017_);
        Path $$4 = p_128016_.m_81372_().m_215082_().m_230361_($$3, ".nbt");
        try {
            BufferedReader $$5 = Files.newBufferedReader($$2);
            String $$6 = IOUtils.toString((Reader)$$5);
            Files.createDirectories($$4.getParent(), new FileAttribute[0]);
            try (OutputStream $$7 = Files.newOutputStream($$4, new OpenOption[0]);){
                NbtIo.m_128947_(NbtUtils.m_178024_($$6), $$7);
            }
            TestCommand.m_128003_(p_128016_, "Imported to " + $$4.toAbsolutePath());
            return 0;
        }
        catch (CommandSyntaxException | IOException $$8) {
            System.err.println("Failed to load structure " + p_128017_);
            $$8.printStackTrace();
            return 1;
        }
    }

    private static void m_127933_(ServerLevel p_127934_, String p_127935_, ChatFormatting p_127936_) {
        p_127934_.m_8795_(p_127945_ -> true).forEach(p_127990_ -> p_127990_.m_213846_(Component.m_237113_(p_127936_ + p_127935_)));
    }

    static class TestSummaryDisplayer
    implements GameTestListener {
        private final ServerLevel f_128058_;
        private final MultipleTestTracker f_128059_;

        public TestSummaryDisplayer(ServerLevel p_128061_, MultipleTestTracker p_128062_) {
            this.f_128058_ = p_128061_;
            this.f_128059_ = p_128062_;
        }

        @Override
        public void m_8073_(GameTestInfo p_128064_) {
        }

        @Override
        public void m_142378_(GameTestInfo p_177797_) {
            TestCommand.m_127996_(this.f_128058_, this.f_128059_);
        }

        @Override
        public void m_8066_(GameTestInfo p_128066_) {
            TestCommand.m_127996_(this.f_128058_, this.f_128059_);
        }
    }
}

