/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GameProfileArgument
implements ArgumentType<Result> {
    private static final Collection<String> f_94581_ = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
    public static final SimpleCommandExceptionType f_94580_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.player.unknown"));

    public static Collection<GameProfile> m_94590_(CommandContext<CommandSourceStack> p_94591_, String p_94592_) throws CommandSyntaxException {
        return ((Result)p_94591_.getArgument(p_94592_, Result.class)).m_6474_((CommandSourceStack)p_94591_.getSource());
    }

    public static GameProfileArgument m_94584_() {
        return new GameProfileArgument();
    }

    public Result parse(StringReader p_94586_) throws CommandSyntaxException {
        if (p_94586_.canRead() && p_94586_.peek() == '@') {
            EntitySelectorParser $$1 = new EntitySelectorParser(p_94586_);
            EntitySelector $$2 = $$1.m_121377_();
            if ($$2.m_121159_()) {
                throw EntityArgument.f_91438_.create();
            }
            return new SelectorResult($$2);
        }
        int $$3 = p_94586_.getCursor();
        while (p_94586_.canRead() && p_94586_.peek() != ' ') {
            p_94586_.skip();
        }
        String $$4 = p_94586_.getString().substring($$3, p_94586_.getCursor());
        return p_94595_ -> {
            Optional<GameProfile> $$2 = p_94595_.m_81377_().m_129927_().m_10996_($$4);
            return Collections.singleton($$2.orElseThrow(() -> ((SimpleCommandExceptionType)f_94580_).create()));
        };
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_94598_, SuggestionsBuilder p_94599_) {
        if (p_94598_.getSource() instanceof SharedSuggestionProvider) {
            StringReader $$2 = new StringReader(p_94599_.getInput());
            $$2.setCursor(p_94599_.getStart());
            EntitySelectorParser $$3 = new EntitySelectorParser($$2);
            try {
                $$3.m_121377_();
            }
            catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
            return $$3.m_121249_(p_94599_, p_94589_ -> SharedSuggestionProvider.m_82970_(((SharedSuggestionProvider)p_94598_.getSource()).m_5982_(), p_94589_));
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_94581_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    @FunctionalInterface
    public static interface Result {
        public Collection<GameProfile> m_6474_(CommandSourceStack var1) throws CommandSyntaxException;
    }

    public static class SelectorResult
    implements Result {
        private final EntitySelector f_94603_;

        public SelectorResult(EntitySelector p_94605_) {
            this.f_94603_ = p_94605_;
        }

        @Override
        public Collection<GameProfile> m_6474_(CommandSourceStack p_94607_) throws CommandSyntaxException {
            List<ServerPlayer> $$1 = this.f_94603_.m_121166_(p_94607_);
            if ($$1.isEmpty()) {
                throw EntityArgument.f_91440_.create();
            }
            ArrayList $$2 = Lists.newArrayList();
            for (ServerPlayer $$3 : $$1) {
                $$2.add($$3.m_36316_());
            }
            return $$2;
        }
    }
}

