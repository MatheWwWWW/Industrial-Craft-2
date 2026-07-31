/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  com.mojang.datafixers.util.Either
 */
package net.minecraft.commands.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class ResourceOrTagLocationArgument<T>
implements ArgumentType<Result<T>> {
    private static final Collection<String> f_210943_ = Arrays.asList("foo", "foo:bar", "012", "#skeletons", "#minecraft:skeletons");
    final ResourceKey<? extends Registry<T>> f_210946_;

    public ResourceOrTagLocationArgument(ResourceKey<? extends Registry<T>> p_210949_) {
        this.f_210946_ = p_210949_;
    }

    public static <T> ResourceOrTagLocationArgument<T> m_210968_(ResourceKey<? extends Registry<T>> p_210969_) {
        return new ResourceOrTagLocationArgument<T>(p_210969_);
    }

    public static <T> Result<T> m_210955_(CommandContext<CommandSourceStack> p_210956_, String p_210957_, ResourceKey<Registry<T>> p_210958_, DynamicCommandExceptionType p_210959_) throws CommandSyntaxException {
        Result $$4 = (Result)p_210956_.getArgument(p_210957_, Result.class);
        Optional<Result<T>> $$5 = $$4.m_207209_(p_210958_);
        return $$5.orElseThrow(() -> p_210959_.create((Object)$$4));
    }

    public Result<T> parse(StringReader p_210951_) throws CommandSyntaxException {
        if (p_210951_.canRead() && p_210951_.peek() == '#') {
            int $$1 = p_210951_.getCursor();
            try {
                p_210951_.skip();
                ResourceLocation $$2 = ResourceLocation.m_135818_(p_210951_);
                return new TagResult(TagKey.m_203882_(this.f_210946_, $$2));
            }
            catch (CommandSyntaxException $$3) {
                p_210951_.setCursor($$1);
                throw $$3;
            }
        }
        ResourceLocation $$4 = ResourceLocation.m_135818_(p_210951_);
        return new ResourceResult(ResourceKey.m_135785_(this.f_210946_, $$4));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_210977_, SuggestionsBuilder p_210978_) {
        Object object = p_210977_.getSource();
        if (object instanceof SharedSuggestionProvider) {
            SharedSuggestionProvider $$2 = (SharedSuggestionProvider)object;
            return $$2.m_212095_(this.f_210946_, SharedSuggestionProvider.ElementSuggestionType.ALL, p_210978_, p_210977_);
        }
        return p_210978_.buildFuture();
    }

    public Collection<String> getExamples() {
        return f_210943_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static interface Result<T>
    extends Predicate<Holder<T>> {
        public Either<ResourceKey<T>, TagKey<T>> m_207418_();

        public <E> Optional<Result<E>> m_207209_(ResourceKey<? extends Registry<E>> var1);

        public String m_207276_();
    }

    record TagResult<T>(TagKey<T> f_211015_) implements Result<T>
    {
        @Override
        public Either<ResourceKey<T>, TagKey<T>> m_207418_() {
            return Either.right(this.f_211015_);
        }

        @Override
        public <E> Optional<Result<E>> m_207209_(ResourceKey<? extends Registry<E>> p_211022_) {
            return this.f_211015_.m_207647_(p_211022_).map(TagResult::new);
        }

        @Override
        public boolean test(Holder<T> p_211020_) {
            return p_211020_.m_203656_(this.f_211015_);
        }

        @Override
        public String m_207276_() {
            return "#" + this.f_211015_.f_203868_();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TagResult.class, "key", "f_211015_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TagResult.class, "key", "f_211015_"}, this);
        }

        @Override
        public final boolean equals(Object p_211026_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TagResult.class, "key", "f_211015_"}, this, p_211026_);
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((Holder)object);
        }
    }

    record ResourceResult<T>(ResourceKey<T> f_210981_) implements Result<T>
    {
        @Override
        public Either<ResourceKey<T>, TagKey<T>> m_207418_() {
            return Either.left(this.f_210981_);
        }

        @Override
        public <E> Optional<Result<E>> m_207209_(ResourceKey<? extends Registry<E>> p_210988_) {
            return this.f_210981_.m_195975_(p_210988_).map(ResourceResult::new);
        }

        @Override
        public boolean test(Holder<T> p_210986_) {
            return p_210986_.m_203565_(this.f_210981_);
        }

        @Override
        public String m_207276_() {
            return this.f_210981_.m_135782_().toString();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ResourceResult.class, "key", "f_210981_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ResourceResult.class, "key", "f_210981_"}, this);
        }

        @Override
        public final boolean equals(Object p_210992_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ResourceResult.class, "key", "f_210981_"}, this, p_210992_);
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((Holder)object);
        }
    }

    public static class Info<T>
    implements ArgumentTypeInfo<ResourceOrTagLocationArgument<T>, Template> {
        @Override
        public void m_214155_(Template p_233414_, FriendlyByteBuf p_233415_) {
            p_233415_.m_130085_(p_233414_.f_233429_.m_135782_());
        }

        @Override
        public Template m_213618_(FriendlyByteBuf p_233425_) {
            ResourceLocation $$1 = p_233425_.m_130281_();
            return new Template(ResourceKey.m_135788_($$1));
        }

        @Override
        public void m_213719_(Template p_233411_, JsonObject p_233412_) {
            p_233412_.addProperty("registry", p_233411_.f_233429_.m_135782_().toString());
        }

        @Override
        public Template m_214163_(ResourceOrTagLocationArgument<T> p_233417_) {
            return new Template(p_233417_.f_210946_);
        }

        @Override
        public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_213618_(friendlyByteBuf);
        }

        public final class Template
        implements ArgumentTypeInfo.Template<ResourceOrTagLocationArgument<T>> {
            final ResourceKey<? extends Registry<T>> f_233429_;

            Template(ResourceKey<? extends Registry<T>> p_233432_) {
                this.f_233429_ = p_233432_;
            }

            @Override
            public ResourceOrTagLocationArgument<T> m_213879_(CommandBuildContext p_233435_) {
                return new ResourceOrTagLocationArgument(this.f_233429_);
            }

            @Override
            public ArgumentTypeInfo<ResourceOrTagLocationArgument<T>, ?> m_213709_() {
                return Info.this;
            }

            @Override
            public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
                return this.m_213879_(commandBuildContext);
            }
        }
    }
}

