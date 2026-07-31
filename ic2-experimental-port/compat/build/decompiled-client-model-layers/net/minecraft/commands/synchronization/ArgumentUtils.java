/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.tree.ArgumentCommandNode
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  com.mojang.brigadier.tree.RootCommandNode
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.commands.synchronization;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.Registry;
import org.slf4j.Logger;

public class ArgumentUtils {
    private static final Logger f_235397_ = LogUtils.getLogger();
    private static final byte f_235398_ = 1;
    private static final byte f_235399_ = 2;

    public static int m_235427_(boolean p_235428_, boolean p_235429_) {
        int $$2 = 0;
        if (p_235428_) {
            $$2 |= 1;
        }
        if (p_235429_) {
            $$2 |= 2;
        }
        return $$2;
    }

    public static boolean m_235402_(byte p_235403_) {
        return (p_235403_ & 1) != 0;
    }

    public static boolean m_235430_(byte p_235431_) {
        return (p_235431_ & 2) != 0;
    }

    private static <A extends ArgumentType<?>> void m_235407_(JsonObject p_235408_, ArgumentTypeInfo.Template<A> p_235409_) {
        ArgumentUtils.m_235410_(p_235408_, p_235409_.m_213709_(), p_235409_);
    }

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void m_235410_(JsonObject p_235411_, ArgumentTypeInfo<A, T> p_235412_, ArgumentTypeInfo.Template<A> p_235413_) {
        p_235412_.m_213719_(p_235413_, p_235411_);
    }

    private static <T extends ArgumentType<?>> void m_235404_(JsonObject p_235405_, T p_235406_) {
        ArgumentTypeInfo.Template<T> $$2 = ArgumentTypeInfos.m_235393_(p_235406_);
        p_235405_.addProperty("type", "argument");
        p_235405_.addProperty("parser", Registry.f_235729_.m_7981_($$2.m_213709_()).toString());
        JsonObject $$3 = new JsonObject();
        ArgumentUtils.m_235407_($$3, $$2);
        if ($$3.size() > 0) {
            p_235405_.add("properties", (JsonElement)$$3);
        }
    }

    public static <S> JsonObject m_235414_(CommandDispatcher<S> p_235415_, CommandNode<S> p_235416_) {
        Collection $$6;
        JsonObject $$2 = new JsonObject();
        if (p_235416_ instanceof RootCommandNode) {
            $$2.addProperty("type", "root");
        } else if (p_235416_ instanceof LiteralCommandNode) {
            $$2.addProperty("type", "literal");
        } else if (p_235416_ instanceof ArgumentCommandNode) {
            ArgumentCommandNode $$3 = (ArgumentCommandNode)p_235416_;
            ArgumentUtils.m_235404_($$2, $$3.getType());
        } else {
            f_235397_.error("Could not serialize node {} ({})!", p_235416_, p_235416_.getClass());
            $$2.addProperty("type", "unknown");
        }
        JsonObject $$4 = new JsonObject();
        for (CommandNode $$5 : p_235416_.getChildren()) {
            $$4.add($$5.getName(), (JsonElement)ArgumentUtils.m_235414_(p_235415_, $$5));
        }
        if ($$4.size() > 0) {
            $$2.add("children", (JsonElement)$$4);
        }
        if (p_235416_.getCommand() != null) {
            $$2.addProperty("executable", Boolean.valueOf(true));
        }
        if (p_235416_.getRedirect() != null && !($$6 = p_235415_.getPath(p_235416_.getRedirect())).isEmpty()) {
            JsonArray $$7 = new JsonArray();
            for (String $$8 : $$6) {
                $$7.add($$8);
            }
            $$2.add("redirect", (JsonElement)$$7);
        }
        return $$2;
    }

    public static <T> Set<ArgumentType<?>> m_235417_(CommandNode<T> p_235418_) {
        Set $$1 = Sets.newIdentityHashSet();
        HashSet $$2 = Sets.newHashSet();
        ArgumentUtils.m_235419_(p_235418_, $$2, $$1);
        return $$2;
    }

    private static <T> void m_235419_(CommandNode<T> p_235420_, Set<ArgumentType<?>> p_235421_, Set<CommandNode<T>> p_235422_) {
        if (!p_235422_.add(p_235420_)) {
            return;
        }
        if (p_235420_ instanceof ArgumentCommandNode) {
            ArgumentCommandNode $$3 = (ArgumentCommandNode)p_235420_;
            p_235421_.add($$3.getType());
        }
        p_235420_.getChildren().forEach(p_235426_ -> ArgumentUtils.m_235419_(p_235426_, p_235421_, p_235422_));
        CommandNode $$4 = p_235420_.getRedirect();
        if ($$4 != null) {
            ArgumentUtils.m_235419_($$4, p_235421_, p_235422_);
        }
    }
}

