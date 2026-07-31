/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;

public class ReloadCommand {
    private static final Logger f_138220_ = LogUtils.getLogger();

    public static void m_138235_(Collection<String> p_138236_, CommandSourceStack p_138237_) {
        p_138237_.m_81377_().m_129861_(p_138236_).exceptionally(p_138234_ -> {
            f_138220_.warn("Failed to execute reload", p_138234_);
            p_138237_.m_81352_(Component.m_237115_("commands.reload.failure"));
            return null;
        });
    }

    private static Collection<String> m_138222_(PackRepository p_138223_, WorldData p_138224_, Collection<String> p_138225_) {
        p_138223_.m_10506_();
        ArrayList $$3 = Lists.newArrayList(p_138225_);
        List<String> $$4 = p_138224_.m_7513_().m_45855_();
        for (String $$5 : p_138223_.m_10514_()) {
            if ($$4.contains($$5) || $$3.contains($$5)) continue;
            $$3.add($$5);
        }
        return $$3;
    }

    public static void m_138226_(CommandDispatcher<CommandSourceStack> p_138227_) {
        p_138227_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("reload").requires(p_138231_ -> p_138231_.m_6761_(2))).executes(p_138229_ -> {
            CommandSourceStack $$1 = (CommandSourceStack)p_138229_.getSource();
            MinecraftServer $$2 = $$1.m_81377_();
            PackRepository $$3 = $$2.m_129891_();
            WorldData $$4 = $$2.m_129910_();
            Collection<String> $$5 = $$3.m_10523_();
            Collection<String> $$6 = ReloadCommand.m_138222_($$3, $$4, $$5);
            $$1.m_81354_(Component.m_237115_("commands.reload.success"), true);
            ReloadCommand.m_138235_($$6, $$1);
            return 0;
        }));
    }
}

