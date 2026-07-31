/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Iterables
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.data.BlockDataAccessor;
import net.minecraft.server.commands.data.DataAccessor;
import net.minecraft.server.commands.data.EntityDataAccessor;
import net.minecraft.server.commands.data.StorageDataAccessor;
import net.minecraft.util.Mth;

public class DataCommands {
    private static final SimpleCommandExceptionType f_139352_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.data.merge.failed"));
    private static final DynamicCommandExceptionType f_139353_ = new DynamicCommandExceptionType(p_139491_ -> Component.m_237110_("commands.data.get.invalid", p_139491_));
    private static final DynamicCommandExceptionType f_139354_ = new DynamicCommandExceptionType(p_139481_ -> Component.m_237110_("commands.data.get.unknown", p_139481_));
    private static final SimpleCommandExceptionType f_139355_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.data.get.multiple"));
    private static final DynamicCommandExceptionType f_139356_ = new DynamicCommandExceptionType(p_139468_ -> Component.m_237110_("commands.data.modify.expected_list", p_139468_));
    private static final DynamicCommandExceptionType f_139357_ = new DynamicCommandExceptionType(p_139448_ -> Component.m_237110_("commands.data.modify.expected_object", p_139448_));
    private static final DynamicCommandExceptionType f_139358_ = new DynamicCommandExceptionType(p_139402_ -> Component.m_237110_("commands.data.modify.invalid_index", p_139402_));
    public static final List<Function<String, DataProvider>> f_139349_ = ImmutableList.of(EntityDataAccessor.f_139505_, BlockDataAccessor.f_139291_, StorageDataAccessor.f_139531_);
    public static final List<DataProvider> f_139350_ = (List)f_139349_.stream().map(p_139450_ -> (DataProvider)p_139450_.apply("target")).collect(ImmutableList.toImmutableList());
    public static final List<DataProvider> f_139351_ = (List)f_139349_.stream().map(p_139410_ -> (DataProvider)p_139410_.apply("source")).collect(ImmutableList.toImmutableList());

    public static void m_139365_(CommandDispatcher<CommandSourceStack> p_139366_) {
        LiteralArgumentBuilder $$1 = (LiteralArgumentBuilder)Commands.m_82127_("data").requires(p_139381_ -> p_139381_.m_6761_(2));
        for (DataProvider $$2 : f_139350_) {
            ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)$$1.then($$2.m_7621_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82127_("merge"), p_139471_ -> p_139471_.then(Commands.m_82129_("nbt", CompoundTagArgument.m_87657_()).executes(p_142857_ -> DataCommands.m_139394_((CommandSourceStack)p_142857_.getSource(), $$2.m_7018_((CommandContext<CommandSourceStack>)p_142857_), CompoundTagArgument.m_87660_(p_142857_, "nbt"))))))).then($$2.m_7621_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82127_("get"), p_139453_ -> p_139453_.executes(p_142849_ -> DataCommands.m_139382_((CommandSourceStack)p_142849_.getSource(), $$2.m_7018_((CommandContext<CommandSourceStack>)p_142849_))).then(((RequiredArgumentBuilder)Commands.m_82129_("path", NbtPathArgument.m_99487_()).executes(p_142841_ -> DataCommands.m_139443_((CommandSourceStack)p_142841_.getSource(), $$2.m_7018_((CommandContext<CommandSourceStack>)p_142841_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_142841_, "path")))).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes(p_142833_ -> DataCommands.m_139389_((CommandSourceStack)p_142833_.getSource(), $$2.m_7018_((CommandContext<CommandSourceStack>)p_142833_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_142833_, "path"), DoubleArgumentType.getDouble((CommandContext)p_142833_, (String)"scale")))))))).then($$2.m_7621_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82127_("remove"), p_139413_ -> p_139413_.then(Commands.m_82129_("path", NbtPathArgument.m_99487_()).executes(p_142820_ -> DataCommands.m_139385_((CommandSourceStack)p_142820_.getSource(), $$2.m_7018_((CommandContext<CommandSourceStack>)p_142820_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_142820_, "path"))))))).then(DataCommands.m_139403_((p_139368_, p_139369_) -> p_139368_.then(Commands.m_82127_("insert").then(Commands.m_82129_("index", IntegerArgumentType.integer()).then(p_139369_.m_139500_((p_142859_, p_142860_, p_142861_, p_142862_) -> {
                int $$4 = IntegerArgumentType.getInteger((CommandContext)p_142859_, (String)"index");
                return DataCommands.m_139360_($$4, p_142860_, p_142861_, p_142862_);
            })))).then(Commands.m_82127_("prepend").then(p_139369_.m_139500_((p_142851_, p_142852_, p_142853_, p_142854_) -> DataCommands.m_139360_(0, p_142852_, p_142853_, p_142854_)))).then(Commands.m_82127_("append").then(p_139369_.m_139500_((p_142843_, p_142844_, p_142845_, p_142846_) -> DataCommands.m_139360_(-1, p_142844_, p_142845_, p_142846_)))).then(Commands.m_82127_("set").then(p_139369_.m_139500_((p_142835_, p_142836_, p_142837_, p_142838_) -> p_142837_.m_99645_(p_142836_, ((Tag)Iterables.getLast((Iterable)p_142838_))::m_6426_)))).then(Commands.m_82127_("merge").then(p_139369_.m_139500_((p_142822_, p_142823_, p_142824_, p_142825_) -> {
                List<Tag> $$4 = p_142824_.m_99640_(p_142823_, CompoundTag::new);
                int $$5 = 0;
                for (Tag $$6 : $$4) {
                    if (!($$6 instanceof CompoundTag)) {
                        throw f_139357_.create((Object)$$6);
                    }
                    CompoundTag $$7 = (CompoundTag)$$6;
                    CompoundTag $$8 = $$7.m_6426_();
                    for (Tag $$9 : p_142825_) {
                        if (!($$9 instanceof CompoundTag)) {
                            throw f_139357_.create((Object)$$9);
                        }
                        $$7.m_128391_((CompoundTag)$$9);
                    }
                    $$5 += $$8.equals($$7) ? 0 : 1;
                }
                return $$5;
            })))));
        }
        p_139366_.register($$1);
    }

    private static int m_139360_(int p_139361_, CompoundTag p_139362_, NbtPathArgument.NbtPath p_139363_, List<Tag> p_139364_) throws CommandSyntaxException {
        List<Tag> $$4 = p_139363_.m_99640_(p_139362_, ListTag::new);
        int $$5 = 0;
        for (Tag $$6 : $$4) {
            if (!($$6 instanceof CollectionTag)) {
                throw f_139356_.create((Object)$$6);
            }
            boolean $$7 = false;
            CollectionTag $$8 = (CollectionTag)$$6;
            int $$9 = p_139361_ < 0 ? $$8.size() + p_139361_ + 1 : p_139361_;
            for (Tag $$10 : p_139364_) {
                try {
                    if (!$$8.m_7614_($$9, $$10.m_6426_())) continue;
                    ++$$9;
                    $$7 = true;
                }
                catch (IndexOutOfBoundsException $$11) {
                    throw f_139358_.create((Object)$$9);
                }
            }
            $$5 += $$7 ? 1 : 0;
        }
        return $$5;
    }

    private static ArgumentBuilder<CommandSourceStack, ?> m_139403_(BiConsumer<ArgumentBuilder<CommandSourceStack, ?>, DataManipulatorDecorator> p_139404_) {
        LiteralArgumentBuilder<CommandSourceStack> $$1 = Commands.m_82127_("modify");
        for (DataProvider $$2 : f_139350_) {
            $$2.m_7621_((ArgumentBuilder<CommandSourceStack, ?>)$$1, p_139408_ -> {
                RequiredArgumentBuilder<CommandSourceStack, NbtPathArgument.NbtPath> $$3 = Commands.m_82129_("targetPath", NbtPathArgument.m_99487_());
                for (DataProvider $$4 : f_139351_) {
                    p_139404_.accept((ArgumentBuilder<CommandSourceStack, ?>)$$3, p_142807_ -> $$4.m_7621_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82127_("from"), p_142812_ -> p_142812_.executes(p_142830_ -> {
                        List<Tag> $$4 = Collections.singletonList($$4.m_7018_((CommandContext<CommandSourceStack>)p_142830_).m_6184_());
                        return DataCommands.m_139375_((CommandContext<CommandSourceStack>)p_142830_, $$2, p_142807_, $$4);
                    }).then(Commands.m_82129_("sourcePath", NbtPathArgument.m_99487_()).executes(p_142817_ -> {
                        DataAccessor $$4 = $$4.m_7018_((CommandContext<CommandSourceStack>)p_142817_);
                        NbtPathArgument.NbtPath $$5 = NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_142817_, "sourcePath");
                        List<Tag> $$6 = $$5.m_99638_($$4.m_6184_());
                        return DataCommands.m_139375_((CommandContext<CommandSourceStack>)p_142817_, $$2, p_142807_, $$6);
                    }))));
                }
                p_139404_.accept((ArgumentBuilder<CommandSourceStack, ?>)$$3, p_142799_ -> Commands.m_82127_("value").then(Commands.m_82129_("value", NbtTagArgument.m_100659_()).executes(p_142803_ -> {
                    List<Tag> $$3 = Collections.singletonList(NbtTagArgument.m_100662_(p_142803_, "value"));
                    return DataCommands.m_139375_((CommandContext<CommandSourceStack>)p_142803_, $$2, p_142799_, $$3);
                })));
                return p_139408_.then($$3);
            });
        }
        return $$1;
    }

    private static int m_139375_(CommandContext<CommandSourceStack> p_139376_, DataProvider p_139377_, DataManipulator p_139378_, List<Tag> p_139379_) throws CommandSyntaxException {
        DataAccessor $$4 = p_139377_.m_7018_(p_139376_);
        NbtPathArgument.NbtPath $$5 = NbtPathArgument.m_99498_(p_139376_, "targetPath");
        CompoundTag $$6 = $$4.m_6184_();
        int $$7 = p_139378_.m_139495_(p_139376_, $$6, $$5, p_139379_);
        if ($$7 == 0) {
            throw f_139352_.create();
        }
        $$4.m_7603_($$6);
        ((CommandSourceStack)p_139376_.getSource()).m_81354_($$4.m_6934_(), true);
        return $$7;
    }

    private static int m_139385_(CommandSourceStack p_139386_, DataAccessor p_139387_, NbtPathArgument.NbtPath p_139388_) throws CommandSyntaxException {
        CompoundTag $$3 = p_139387_.m_6184_();
        int $$4 = p_139388_.m_99648_($$3);
        if ($$4 == 0) {
            throw f_139352_.create();
        }
        p_139387_.m_7603_($$3);
        p_139386_.m_81354_(p_139387_.m_6934_(), true);
        return $$4;
    }

    private static Tag m_139398_(NbtPathArgument.NbtPath p_139399_, DataAccessor p_139400_) throws CommandSyntaxException {
        List<Tag> $$2 = p_139399_.m_99638_(p_139400_.m_6184_());
        Iterator $$3 = $$2.iterator();
        Tag $$4 = (Tag)$$3.next();
        if ($$3.hasNext()) {
            throw f_139355_.create();
        }
        return $$4;
    }

    /*
     * WARNING - void declaration
     */
    private static int m_139443_(CommandSourceStack p_139444_, DataAccessor p_139445_, NbtPathArgument.NbtPath p_139446_) throws CommandSyntaxException {
        void $$8;
        Tag $$3 = DataCommands.m_139398_(p_139446_, p_139445_);
        if ($$3 instanceof NumericTag) {
            int $$4 = Mth.m_14107_(((NumericTag)$$3).m_7061_());
        } else if ($$3 instanceof CollectionTag) {
            int $$5 = ((CollectionTag)$$3).size();
        } else if ($$3 instanceof CompoundTag) {
            int $$6 = ((CompoundTag)$$3).m_128440_();
        } else if ($$3 instanceof StringTag) {
            int $$7 = $$3.m_7916_().length();
        } else {
            throw f_139354_.create((Object)p_139446_.toString());
        }
        p_139444_.m_81354_(p_139445_.m_7624_($$3), false);
        return (int)$$8;
    }

    private static int m_139389_(CommandSourceStack p_139390_, DataAccessor p_139391_, NbtPathArgument.NbtPath p_139392_, double p_139393_) throws CommandSyntaxException {
        Tag $$4 = DataCommands.m_139398_(p_139392_, p_139391_);
        if (!($$4 instanceof NumericTag)) {
            throw f_139353_.create((Object)p_139392_.toString());
        }
        int $$5 = Mth.m_14107_(((NumericTag)$$4).m_7061_() * p_139393_);
        p_139390_.m_81354_(p_139391_.m_6066_(p_139392_, p_139393_, $$5), false);
        return $$5;
    }

    private static int m_139382_(CommandSourceStack p_139383_, DataAccessor p_139384_) throws CommandSyntaxException {
        p_139383_.m_81354_(p_139384_.m_7624_(p_139384_.m_6184_()), false);
        return 1;
    }

    private static int m_139394_(CommandSourceStack p_139395_, DataAccessor p_139396_, CompoundTag p_139397_) throws CommandSyntaxException {
        CompoundTag $$4;
        CompoundTag $$3 = p_139396_.m_6184_();
        if ($$3.equals($$4 = $$3.m_6426_().m_128391_(p_139397_))) {
            throw f_139352_.create();
        }
        p_139396_.m_7603_($$4);
        p_139395_.m_81354_(p_139396_.m_6934_(), true);
        return 1;
    }

    public static interface DataProvider {
        public DataAccessor m_7018_(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;

        public ArgumentBuilder<CommandSourceStack, ?> m_7621_(ArgumentBuilder<CommandSourceStack, ?> var1, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> var2);
    }

    static interface DataManipulator {
        public int m_139495_(CommandContext<CommandSourceStack> var1, CompoundTag var2, NbtPathArgument.NbtPath var3, List<Tag> var4) throws CommandSyntaxException;
    }

    static interface DataManipulatorDecorator {
        public ArgumentBuilder<CommandSourceStack, ?> m_139500_(DataManipulator var1);
    }
}

