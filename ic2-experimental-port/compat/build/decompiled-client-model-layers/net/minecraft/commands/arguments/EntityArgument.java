/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.ImmutableStringReader
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

import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class EntityArgument
implements ArgumentType<EntitySelector> {
    private static final Collection<String> f_91442_ = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
    public static final SimpleCommandExceptionType f_91436_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.toomany"));
    public static final SimpleCommandExceptionType f_91437_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.player.toomany"));
    public static final SimpleCommandExceptionType f_91438_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.player.entities"));
    public static final SimpleCommandExceptionType f_91439_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.notfound.entity"));
    public static final SimpleCommandExceptionType f_91440_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.notfound.player"));
    public static final SimpleCommandExceptionType f_91441_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.selector.not_allowed"));
    final boolean f_91443_;
    final boolean f_91444_;

    protected EntityArgument(boolean p_91447_, boolean p_91448_) {
        this.f_91443_ = p_91447_;
        this.f_91444_ = p_91448_;
    }

    public static EntityArgument m_91449_() {
        return new EntityArgument(true, false);
    }

    public static Entity m_91452_(CommandContext<CommandSourceStack> p_91453_, String p_91454_) throws CommandSyntaxException {
        return ((EntitySelector)p_91453_.getArgument(p_91454_, EntitySelector.class)).m_121139_((CommandSourceStack)p_91453_.getSource());
    }

    public static EntityArgument m_91460_() {
        return new EntityArgument(false, false);
    }

    public static Collection<? extends Entity> m_91461_(CommandContext<CommandSourceStack> p_91462_, String p_91463_) throws CommandSyntaxException {
        Collection<? extends Entity> $$2 = EntityArgument.m_91467_(p_91462_, p_91463_);
        if ($$2.isEmpty()) {
            throw f_91439_.create();
        }
        return $$2;
    }

    public static Collection<? extends Entity> m_91467_(CommandContext<CommandSourceStack> p_91468_, String p_91469_) throws CommandSyntaxException {
        return ((EntitySelector)p_91468_.getArgument(p_91469_, EntitySelector.class)).m_121160_((CommandSourceStack)p_91468_.getSource());
    }

    public static Collection<ServerPlayer> m_91471_(CommandContext<CommandSourceStack> p_91472_, String p_91473_) throws CommandSyntaxException {
        return ((EntitySelector)p_91472_.getArgument(p_91473_, EntitySelector.class)).m_121166_((CommandSourceStack)p_91472_.getSource());
    }

    public static EntityArgument m_91466_() {
        return new EntityArgument(true, true);
    }

    public static ServerPlayer m_91474_(CommandContext<CommandSourceStack> p_91475_, String p_91476_) throws CommandSyntaxException {
        return ((EntitySelector)p_91475_.getArgument(p_91476_, EntitySelector.class)).m_121163_((CommandSourceStack)p_91475_.getSource());
    }

    public static EntityArgument m_91470_() {
        return new EntityArgument(false, true);
    }

    public static Collection<ServerPlayer> m_91477_(CommandContext<CommandSourceStack> p_91478_, String p_91479_) throws CommandSyntaxException {
        List<ServerPlayer> $$2 = ((EntitySelector)p_91478_.getArgument(p_91479_, EntitySelector.class)).m_121166_((CommandSourceStack)p_91478_.getSource());
        if ($$2.isEmpty()) {
            throw f_91440_.create();
        }
        return $$2;
    }

    public EntitySelector parse(StringReader p_91451_) throws CommandSyntaxException {
        boolean $$1 = false;
        EntitySelectorParser $$2 = new EntitySelectorParser(p_91451_);
        EntitySelector $$3 = $$2.m_121377_();
        if ($$3.m_121138_() > 1 && this.f_91443_) {
            if (this.f_91444_) {
                p_91451_.setCursor(0);
                throw f_91437_.createWithContext((ImmutableStringReader)p_91451_);
            }
            p_91451_.setCursor(0);
            throw f_91436_.createWithContext((ImmutableStringReader)p_91451_);
        }
        if ($$3.m_121159_() && this.f_91444_ && !$$3.m_121162_()) {
            p_91451_.setCursor(0);
            throw f_91438_.createWithContext((ImmutableStringReader)p_91451_);
        }
        return $$3;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_91482_, SuggestionsBuilder p_91483_) {
        Object object = p_91482_.getSource();
        if (object instanceof SharedSuggestionProvider) {
            SharedSuggestionProvider $$2 = (SharedSuggestionProvider)object;
            StringReader $$3 = new StringReader(p_91483_.getInput());
            $$3.setCursor(p_91483_.getStart());
            EntitySelectorParser $$4 = new EntitySelectorParser($$3, $$2.m_6761_(2));
            try {
                $$4.m_121377_();
            }
            catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
            return $$4.m_121249_(p_91483_, p_91457_ -> {
                Collection<String> $$2 = $$2.m_5982_();
                Collection<String> $$3 = this.f_91444_ ? $$2 : Iterables.concat($$2, $$2.m_6264_());
                SharedSuggestionProvider.m_82970_($$3, p_91457_);
            });
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_91442_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static class Info
    implements ArgumentTypeInfo<EntityArgument, Template> {
        private static final byte f_231262_ = 1;
        private static final byte f_231263_ = 2;

        @Override
        public void m_214155_(Template p_231271_, FriendlyByteBuf p_231272_) {
            int $$2 = 0;
            if (p_231271_.f_231286_) {
                $$2 |= 1;
            }
            if (p_231271_.f_231287_) {
                $$2 |= 2;
            }
            p_231272_.writeByte($$2);
        }

        @Override
        public Template m_213618_(FriendlyByteBuf p_231282_) {
            byte $$1 = p_231282_.readByte();
            return new Template(($$1 & 1) != 0, ($$1 & 2) != 0);
        }

        @Override
        public void m_213719_(Template p_231268_, JsonObject p_231269_) {
            p_231269_.addProperty("amount", p_231268_.f_231286_ ? "single" : "multiple");
            p_231269_.addProperty("type", p_231268_.f_231287_ ? "players" : "entities");
        }

        @Override
        public Template m_214163_(EntityArgument p_231274_) {
            return new Template(p_231274_.f_91443_, p_231274_.f_91444_);
        }

        @Override
        public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_213618_(friendlyByteBuf);
        }

        public final class Template
        implements ArgumentTypeInfo.Template<EntityArgument> {
            final boolean f_231286_;
            final boolean f_231287_;

            Template(boolean p_231290_, boolean p_231291_) {
                this.f_231286_ = p_231290_;
                this.f_231287_ = p_231291_;
            }

            @Override
            public EntityArgument m_213879_(CommandBuildContext p_231294_) {
                return new EntityArgument(this.f_231286_, this.f_231287_);
            }

            @Override
            public ArgumentTypeInfo<EntityArgument, ?> m_213709_() {
                return Info.this;
            }

            @Override
            public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
                return this.m_213879_(commandBuildContext);
            }
        }
    }
}

