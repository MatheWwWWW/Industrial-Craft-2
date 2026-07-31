/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;

public class DataPackCommand {
    private static final DynamicCommandExceptionType f_136800_ = new DynamicCommandExceptionType(p_136868_ -> Component.m_237110_("commands.datapack.unknown", p_136868_));
    private static final DynamicCommandExceptionType f_136801_ = new DynamicCommandExceptionType(p_136857_ -> Component.m_237110_("commands.datapack.enable.failed", p_136857_));
    private static final DynamicCommandExceptionType f_136802_ = new DynamicCommandExceptionType(p_136833_ -> Component.m_237110_("commands.datapack.disable.failed", p_136833_));
    private static final SuggestionProvider<CommandSourceStack> f_136803_ = (p_136848_, p_136849_) -> SharedSuggestionProvider.m_82981_(((CommandSourceStack)p_136848_.getSource()).m_81377_().m_129891_().m_10523_().stream().map(StringArgumentType::escapeIfRequired), p_136849_);
    private static final SuggestionProvider<CommandSourceStack> f_136804_ = (p_136813_, p_136814_) -> {
        PackRepository $$2 = ((CommandSourceStack)p_136813_.getSource()).m_81377_().m_129891_();
        Collection<String> $$3 = $$2.m_10523_();
        return SharedSuggestionProvider.m_82981_($$2.m_10514_().stream().filter(p_180050_ -> !$$3.contains(p_180050_)).map(StringArgumentType::escapeIfRequired), p_136814_);
    };

    public static void m_136808_(CommandDispatcher<CommandSourceStack> p_136809_) {
        p_136809_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("datapack").requires(p_136872_ -> p_136872_.m_6761_(2))).then(Commands.m_82127_("enable").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("name", StringArgumentType.string()).suggests(f_136804_).executes(p_136882_ -> DataPackCommand.m_136828_((CommandSourceStack)p_136882_.getSource(), DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136882_, "name", true), (p_180059_, p_180060_) -> p_180060_.m_10451_().m_10470_(p_180059_, p_180060_, p_180062_ -> p_180062_, false)))).then(Commands.m_82127_("after").then(Commands.m_82129_("existing", StringArgumentType.string()).suggests(f_136803_).executes(p_136880_ -> DataPackCommand.m_136828_((CommandSourceStack)p_136880_.getSource(), DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136880_, "name", true), (p_180056_, p_180057_) -> p_180056_.add(p_180056_.indexOf(DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136880_, "existing", false)) + 1, p_180057_)))))).then(Commands.m_82127_("before").then(Commands.m_82129_("existing", StringArgumentType.string()).suggests(f_136803_).executes(p_136878_ -> DataPackCommand.m_136828_((CommandSourceStack)p_136878_.getSource(), DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136878_, "name", true), (p_180046_, p_180047_) -> p_180046_.add(p_180046_.indexOf(DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136878_, "existing", false)), p_180047_)))))).then(Commands.m_82127_("last").executes(p_136876_ -> DataPackCommand.m_136828_((CommandSourceStack)p_136876_.getSource(), DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136876_, "name", true), List::add)))).then(Commands.m_82127_("first").executes(p_136874_ -> DataPackCommand.m_136828_((CommandSourceStack)p_136874_.getSource(), DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136874_, "name", true), (p_180052_, p_180053_) -> p_180052_.add(0, p_180053_))))))).then(Commands.m_82127_("disable").then(Commands.m_82129_("name", StringArgumentType.string()).suggests(f_136803_).executes(p_136870_ -> DataPackCommand.m_136825_((CommandSourceStack)p_136870_.getSource(), DataPackCommand.m_136815_((CommandContext<CommandSourceStack>)p_136870_, "name", false)))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("list").executes(p_136864_ -> DataPackCommand.m_136823_((CommandSourceStack)p_136864_.getSource()))).then(Commands.m_82127_("available").executes(p_136846_ -> DataPackCommand.m_136854_((CommandSourceStack)p_136846_.getSource())))).then(Commands.m_82127_("enabled").executes(p_136811_ -> DataPackCommand.m_136865_((CommandSourceStack)p_136811_.getSource())))));
    }

    private static int m_136828_(CommandSourceStack p_136829_, Pack p_136830_, Inserter p_136831_) throws CommandSyntaxException {
        PackRepository $$3 = p_136829_.m_81377_().m_129891_();
        ArrayList $$4 = Lists.newArrayList($$3.m_10524_());
        p_136831_.m_136883_($$4, p_136830_);
        p_136829_.m_81354_(Component.m_237110_("commands.datapack.modify.enable", p_136830_.m_10437_(true)), true);
        ReloadCommand.m_138235_($$4.stream().map(Pack::m_10446_).collect(Collectors.toList()), p_136829_);
        return $$4.size();
    }

    private static int m_136825_(CommandSourceStack p_136826_, Pack p_136827_) {
        PackRepository $$2 = p_136826_.m_81377_().m_129891_();
        ArrayList $$3 = Lists.newArrayList($$2.m_10524_());
        $$3.remove(p_136827_);
        p_136826_.m_81354_(Component.m_237110_("commands.datapack.modify.disable", p_136827_.m_10437_(true)), true);
        ReloadCommand.m_138235_($$3.stream().map(Pack::m_10446_).collect(Collectors.toList()), p_136826_);
        return $$3.size();
    }

    private static int m_136823_(CommandSourceStack p_136824_) {
        return DataPackCommand.m_136865_(p_136824_) + DataPackCommand.m_136854_(p_136824_);
    }

    private static int m_136854_(CommandSourceStack p_136855_) {
        PackRepository $$1 = p_136855_.m_81377_().m_129891_();
        $$1.m_10506_();
        Collection<Pack> $$2 = $$1.m_10524_();
        Collection<Pack> $$3 = $$1.m_10519_();
        List $$4 = $$3.stream().filter(p_136836_ -> !$$2.contains(p_136836_)).collect(Collectors.toList());
        if ($$4.isEmpty()) {
            p_136855_.m_81354_(Component.m_237115_("commands.datapack.list.available.none"), false);
        } else {
            p_136855_.m_81354_(Component.m_237110_("commands.datapack.list.available.success", $$4.size(), ComponentUtils.m_178440_($$4, p_136844_ -> p_136844_.m_10437_(false))), false);
        }
        return $$4.size();
    }

    private static int m_136865_(CommandSourceStack p_136866_) {
        PackRepository $$1 = p_136866_.m_81377_().m_129891_();
        $$1.m_10506_();
        Collection<Pack> $$2 = $$1.m_10524_();
        if ($$2.isEmpty()) {
            p_136866_.m_81354_(Component.m_237115_("commands.datapack.list.enabled.none"), false);
        } else {
            p_136866_.m_81354_(Component.m_237110_("commands.datapack.list.enabled.success", $$2.size(), ComponentUtils.m_178440_($$2, p_136807_ -> p_136807_.m_10437_(true))), false);
        }
        return $$2.size();
    }

    private static Pack m_136815_(CommandContext<CommandSourceStack> p_136816_, String p_136817_, boolean p_136818_) throws CommandSyntaxException {
        String $$3 = StringArgumentType.getString(p_136816_, (String)p_136817_);
        PackRepository $$4 = ((CommandSourceStack)p_136816_.getSource()).m_81377_().m_129891_();
        Pack $$5 = $$4.m_10507_($$3);
        if ($$5 == null) {
            throw f_136800_.create((Object)$$3);
        }
        boolean $$6 = $$4.m_10524_().contains($$5);
        if (p_136818_ && $$6) {
            throw f_136801_.create((Object)$$3);
        }
        if (!p_136818_ && !$$6) {
            throw f_136802_.create((Object)$$3);
        }
        return $$5;
    }

    static interface Inserter {
        public void m_136883_(List<Pack> var1, Pack var2) throws CommandSyntaxException;
    }
}

