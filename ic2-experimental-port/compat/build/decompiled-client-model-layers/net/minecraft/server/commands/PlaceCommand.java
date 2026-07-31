/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Optional;
import net.minecraft.ResourceLocationException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.TemplateMirrorArgument;
import net.minecraft.commands.arguments.TemplateRotationArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class PlaceCommand {
    private static final SimpleCommandExceptionType f_214530_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.place.feature.failed"));
    private static final SimpleCommandExceptionType f_214531_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.place.jigsaw.failed"));
    private static final SimpleCommandExceptionType f_214532_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.place.structure.failed"));
    private static final DynamicCommandExceptionType f_214533_ = new DynamicCommandExceptionType(p_214582_ -> Component.m_237110_("commands.place.template.invalid", p_214582_));
    private static final SimpleCommandExceptionType f_214534_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.place.template.failed"));
    private static final SuggestionProvider<CommandSourceStack> f_214535_ = (p_214552_, p_214553_) -> {
        StructureTemplateManager $$2 = ((CommandSourceStack)p_214552_.getSource()).m_81372_().m_215082_();
        return SharedSuggestionProvider.m_82957_($$2.m_230355_(), p_214553_);
    };

    public static void m_214547_(CommandDispatcher<CommandSourceStack> p_214548_) {
        p_214548_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("place").requires(p_214560_ -> p_214560_.m_6761_(2))).then(Commands.m_82127_("feature").then(((RequiredArgumentBuilder)Commands.m_82129_("feature", ResourceKeyArgument.m_212386_(Registry.f_122881_)).executes(p_214610_ -> PlaceCommand.m_214575_((CommandSourceStack)p_214610_.getSource(), ResourceKeyArgument.m_212388_((CommandContext<CommandSourceStack>)p_214610_, "feature"), new BlockPos(((CommandSourceStack)p_214610_.getSource()).m_81371_())))).then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes(p_214608_ -> PlaceCommand.m_214575_((CommandSourceStack)p_214608_.getSource(), ResourceKeyArgument.m_212388_((CommandContext<CommandSourceStack>)p_214608_, "feature"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214608_, "pos"))))))).then(Commands.m_82127_("jigsaw").then(Commands.m_82129_("pool", ResourceKeyArgument.m_212386_(Registry.f_122884_)).then(Commands.m_82129_("target", ResourceLocationArgument.m_106984_()).then(((RequiredArgumentBuilder)Commands.m_82129_("max_depth", IntegerArgumentType.integer((int)1, (int)7)).executes(p_214606_ -> PlaceCommand.m_214569_((CommandSourceStack)p_214606_.getSource(), ResourceKeyArgument.m_233268_((CommandContext<CommandSourceStack>)p_214606_, "pool"), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214606_, "target"), IntegerArgumentType.getInteger((CommandContext)p_214606_, (String)"max_depth"), new BlockPos(((CommandSourceStack)p_214606_.getSource()).m_81371_())))).then(Commands.m_82129_("position", BlockPosArgument.m_118239_()).executes(p_214604_ -> PlaceCommand.m_214569_((CommandSourceStack)p_214604_.getSource(), ResourceKeyArgument.m_233268_((CommandContext<CommandSourceStack>)p_214604_, "pool"), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214604_, "target"), IntegerArgumentType.getInteger((CommandContext)p_214604_, (String)"max_depth"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214604_, "position"))))))))).then(Commands.m_82127_("structure").then(((RequiredArgumentBuilder)Commands.m_82129_("structure", ResourceKeyArgument.m_212386_(Registry.f_235725_)).executes(p_214602_ -> PlaceCommand.m_214587_((CommandSourceStack)p_214602_.getSource(), ResourceKeyArgument.m_233265_((CommandContext<CommandSourceStack>)p_214602_, "structure"), new BlockPos(((CommandSourceStack)p_214602_.getSource()).m_81371_())))).then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes(p_214600_ -> PlaceCommand.m_214587_((CommandSourceStack)p_214600_.getSource(), ResourceKeyArgument.m_233265_((CommandContext<CommandSourceStack>)p_214600_, "structure"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214600_, "pos"))))))).then(Commands.m_82127_("template").then(((RequiredArgumentBuilder)Commands.m_82129_("template", ResourceLocationArgument.m_106984_()).suggests(f_214535_).executes(p_214598_ -> PlaceCommand.m_214561_((CommandSourceStack)p_214598_.getSource(), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214598_, "template"), new BlockPos(((CommandSourceStack)p_214598_.getSource()).m_81371_()), Rotation.NONE, Mirror.NONE, 1.0f, 0))).then(((RequiredArgumentBuilder)Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes(p_214596_ -> PlaceCommand.m_214561_((CommandSourceStack)p_214596_.getSource(), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214596_, "template"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214596_, "pos"), Rotation.NONE, Mirror.NONE, 1.0f, 0))).then(((RequiredArgumentBuilder)Commands.m_82129_("rotation", TemplateRotationArgument.m_234414_()).executes(p_214594_ -> PlaceCommand.m_214561_((CommandSourceStack)p_214594_.getSource(), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214594_, "template"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214594_, "pos"), TemplateRotationArgument.m_234415_((CommandContext<CommandSourceStack>)p_214594_, "rotation"), Mirror.NONE, 1.0f, 0))).then(((RequiredArgumentBuilder)Commands.m_82129_("mirror", TemplateMirrorArgument.m_234343_()).executes(p_214592_ -> PlaceCommand.m_214561_((CommandSourceStack)p_214592_.getSource(), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214592_, "template"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214592_, "pos"), TemplateRotationArgument.m_234415_((CommandContext<CommandSourceStack>)p_214592_, "rotation"), TemplateMirrorArgument.m_234344_((CommandContext<CommandSourceStack>)p_214592_, "mirror"), 1.0f, 0))).then(((RequiredArgumentBuilder)Commands.m_82129_("integrity", FloatArgumentType.floatArg((float)0.0f, (float)1.0f)).executes(p_214586_ -> PlaceCommand.m_214561_((CommandSourceStack)p_214586_.getSource(), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214586_, "template"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214586_, "pos"), TemplateRotationArgument.m_234415_((CommandContext<CommandSourceStack>)p_214586_, "rotation"), TemplateMirrorArgument.m_234344_((CommandContext<CommandSourceStack>)p_214586_, "mirror"), FloatArgumentType.getFloat((CommandContext)p_214586_, (String)"integrity"), 0))).then(Commands.m_82129_("seed", IntegerArgumentType.integer()).executes(p_214550_ -> PlaceCommand.m_214561_((CommandSourceStack)p_214550_.getSource(), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_214550_, "template"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_214550_, "pos"), TemplateRotationArgument.m_234415_((CommandContext<CommandSourceStack>)p_214550_, "rotation"), TemplateMirrorArgument.m_234344_((CommandContext<CommandSourceStack>)p_214550_, "mirror"), FloatArgumentType.getFloat((CommandContext)p_214550_, (String)"integrity"), IntegerArgumentType.getInteger((CommandContext)p_214550_, (String)"seed")))))))))));
    }

    public static int m_214575_(CommandSourceStack p_214576_, Holder<ConfiguredFeature<?, ?>> p_214577_, BlockPos p_214578_) throws CommandSyntaxException {
        ServerLevel $$3 = p_214576_.m_81372_();
        ConfiguredFeature<?, ?> $$4 = p_214577_.m_203334_();
        ChunkPos $$5 = new ChunkPos(p_214578_);
        PlaceCommand.m_214543_($$3, new ChunkPos($$5.f_45578_ - 1, $$5.f_45579_ - 1), new ChunkPos($$5.f_45578_ + 1, $$5.f_45579_ + 1));
        if (!$$4.m_224953_($$3, $$3.m_7726_().m_8481_(), $$3.m_213780_(), p_214578_)) {
            throw f_214530_.create();
        }
        String $$6 = p_214577_.m_203543_().map(p_214584_ -> p_214584_.m_135782_().toString()).orElse("[unregistered]");
        p_214576_.m_81354_(Component.m_237110_("commands.place.feature.success", $$6, p_214578_.m_123341_(), p_214578_.m_123342_(), p_214578_.m_123343_()), true);
        return 1;
    }

    public static int m_214569_(CommandSourceStack p_214570_, Holder<StructureTemplatePool> p_214571_, ResourceLocation p_214572_, int p_214573_, BlockPos p_214574_) throws CommandSyntaxException {
        ServerLevel $$5 = p_214570_.m_81372_();
        if (!JigsawPlacement.m_227203_($$5, p_214571_, p_214572_, p_214573_, p_214574_, false)) {
            throw f_214531_.create();
        }
        p_214570_.m_81354_(Component.m_237110_("commands.place.jigsaw.success", p_214574_.m_123341_(), p_214574_.m_123342_(), p_214574_.m_123343_()), true);
        return 1;
    }

    public static int m_214587_(CommandSourceStack p_214588_, Holder<Structure> p_214589_, BlockPos p_214590_) throws CommandSyntaxException {
        ServerLevel $$3 = p_214588_.m_81372_();
        Structure $$4 = p_214589_.m_203334_();
        ChunkGenerator $$5 = $$3.m_7726_().m_8481_();
        StructureStart $$6 = $$4.m_226596_(p_214588_.m_5894_(), $$5, $$5.m_62218_(), $$3.m_7726_().m_214994_(), $$3.m_215082_(), $$3.m_7328_(), new ChunkPos(p_214590_), 0, $$3, p_214580_ -> true);
        if (!$$6.m_73603_()) {
            throw f_214532_.create();
        }
        BoundingBox $$7 = $$6.m_73601_();
        ChunkPos $$8 = new ChunkPos(SectionPos.m_123171_($$7.m_162395_()), SectionPos.m_123171_($$7.m_162398_()));
        ChunkPos $$9 = new ChunkPos(SectionPos.m_123171_($$7.m_162399_()), SectionPos.m_123171_($$7.m_162401_()));
        PlaceCommand.m_214543_($$3, $$8, $$9);
        ChunkPos.m_45599_($$8, $$9).forEach(p_214558_ -> $$6.m_226850_($$3, $$3.m_215010_(), $$5, $$3.m_213780_(), new BoundingBox(p_214558_.m_45604_(), $$3.m_141937_(), p_214558_.m_45605_(), p_214558_.m_45608_(), $$3.m_151558_(), p_214558_.m_45609_()), (ChunkPos)p_214558_));
        String $$10 = p_214589_.m_203543_().map(p_214539_ -> p_214539_.m_135782_().toString()).orElse("[unregistered]");
        p_214588_.m_81354_(Component.m_237110_("commands.place.structure.success", $$10, p_214590_.m_123341_(), p_214590_.m_123342_(), p_214590_.m_123343_()), true);
        return 1;
    }

    /*
     * WARNING - void declaration
     */
    public static int m_214561_(CommandSourceStack p_214562_, ResourceLocation p_214563_, BlockPos p_214564_, Rotation p_214565_, Mirror p_214566_, float p_214567_, int p_214568_) throws CommandSyntaxException {
        boolean $$14;
        void $$11;
        ServerLevel $$7 = p_214562_.m_81372_();
        StructureTemplateManager $$8 = $$7.m_215082_();
        try {
            Optional<StructureTemplate> $$9 = $$8.m_230407_(p_214563_);
        }
        catch (ResourceLocationException $$10) {
            throw f_214533_.create((Object)p_214563_);
        }
        if ($$11.isEmpty()) {
            throw f_214533_.create((Object)p_214563_);
        }
        StructureTemplate $$12 = (StructureTemplate)$$11.get();
        PlaceCommand.m_214543_($$7, new ChunkPos(p_214564_), new ChunkPos(p_214564_.m_121955_($$12.m_163801_())));
        StructurePlaceSettings $$13 = new StructurePlaceSettings().m_74377_(p_214566_).m_74379_(p_214565_);
        if (p_214567_ < 1.0f) {
            $$13.m_74394_().m_74383_(new BlockRotProcessor(p_214567_)).m_230324_(StructureBlockEntity.m_222888_(p_214568_));
        }
        if (!($$14 = $$12.m_230328_($$7, p_214564_, p_214564_, $$13, StructureBlockEntity.m_222888_(p_214568_), 2))) {
            throw f_214534_.create();
        }
        p_214562_.m_81354_(Component.m_237110_("commands.place.template.success", p_214563_, p_214564_.m_123341_(), p_214564_.m_123342_(), p_214564_.m_123343_()), true);
        return 1;
    }

    private static void m_214543_(ServerLevel p_214544_, ChunkPos p_214545_, ChunkPos p_214546_) throws CommandSyntaxException {
        if (ChunkPos.m_45599_(p_214545_, p_214546_).filter(p_214542_ -> !p_214544_.m_46749_(p_214542_.m_45615_())).findAny().isPresent()) {
            throw BlockPosArgument.f_118234_.create();
        }
    }
}

