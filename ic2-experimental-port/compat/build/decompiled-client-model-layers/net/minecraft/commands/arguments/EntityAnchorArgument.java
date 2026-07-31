/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class EntityAnchorArgument
implements ArgumentType<Anchor> {
    private static final Collection<String> f_90346_ = Arrays.asList("eyes", "feet");
    private static final DynamicCommandExceptionType f_90347_ = new DynamicCommandExceptionType(p_90357_ -> Component.m_237110_("argument.anchor.invalid", p_90357_));

    public static Anchor m_90353_(CommandContext<CommandSourceStack> p_90354_, String p_90355_) {
        return (Anchor)((Object)p_90354_.getArgument(p_90355_, Anchor.class));
    }

    public static EntityAnchorArgument m_90350_() {
        return new EntityAnchorArgument();
    }

    public Anchor parse(StringReader p_90352_) throws CommandSyntaxException {
        int $$1 = p_90352_.getCursor();
        String $$2 = p_90352_.readUnquotedString();
        Anchor $$3 = Anchor.m_90384_($$2);
        if ($$3 == null) {
            p_90352_.setCursor($$1);
            throw f_90347_.createWithContext((ImmutableStringReader)p_90352_, (Object)$$2);
        }
        return $$3;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_90360_, SuggestionsBuilder p_90361_) {
        return SharedSuggestionProvider.m_82970_(Anchor.f_90366_.keySet(), p_90361_);
    }

    public Collection<String> getExamples() {
        return f_90346_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static final class Anchor
    extends Enum<Anchor> {
        public static final /* enum */ Anchor FEET = new Anchor("feet", (p_90389_, p_90390_) -> p_90389_);
        public static final /* enum */ Anchor EYES = new Anchor("eyes", (p_90382_, p_90383_) -> new Vec3(p_90382_.f_82479_, p_90382_.f_82480_ + (double)p_90383_.m_20192_(), p_90382_.f_82481_));
        static final Map<String, Anchor> f_90366_;
        private final String f_90367_;
        private final BiFunction<Vec3, Entity, Vec3> f_90368_;
        private static final /* synthetic */ Anchor[] $VALUES;

        public static Anchor[] values() {
            return (Anchor[])$VALUES.clone();
        }

        public static Anchor valueOf(String p_90392_) {
            return Enum.valueOf(Anchor.class, p_90392_);
        }

        private Anchor(String p_90374_, BiFunction<Vec3, Entity, Vec3> p_90375_) {
            this.f_90367_ = p_90374_;
            this.f_90368_ = p_90375_;
        }

        @Nullable
        public static Anchor m_90384_(String p_90385_) {
            return f_90366_.get(p_90385_);
        }

        public Vec3 m_90377_(Entity p_90378_) {
            return this.f_90368_.apply(p_90378_.m_20182_(), p_90378_);
        }

        public Vec3 m_90379_(CommandSourceStack p_90380_) {
            Entity $$1 = p_90380_.m_81373_();
            if ($$1 == null) {
                return p_90380_.m_81371_();
            }
            return this.f_90368_.apply(p_90380_.m_81371_(), $$1);
        }

        private static /* synthetic */ Anchor[] m_167593_() {
            return new Anchor[]{FEET, EYES};
        }

        static {
            $VALUES = Anchor.m_167593_();
            f_90366_ = Util.m_137469_(Maps.newHashMap(), p_90387_ -> {
                for (Anchor $$1 : Anchor.values()) {
                    p_90387_.put($$1.f_90367_, $$1);
                }
            });
        }
    }
}

