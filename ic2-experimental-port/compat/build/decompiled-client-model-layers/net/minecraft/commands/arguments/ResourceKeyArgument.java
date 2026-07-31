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
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ResourceKeyArgument<T>
implements ArgumentType<ResourceKey<T>> {
    private static final Collection<String> f_212361_ = Arrays.asList("foo", "foo:bar", "012");
    private static final DynamicCommandExceptionType f_212362_ = new DynamicCommandExceptionType(p_212392_ -> Component.m_237110_("attribute.unknown", p_212392_));
    private static final DynamicCommandExceptionType f_212363_ = new DynamicCommandExceptionType(p_212385_ -> Component.m_237110_("commands.place.feature.invalid", p_212385_));
    private static final DynamicCommandExceptionType f_233246_ = new DynamicCommandExceptionType(p_233264_ -> Component.m_237110_("commands.place.structure.invalid", p_233264_));
    private static final DynamicCommandExceptionType f_233247_ = new DynamicCommandExceptionType(p_233252_ -> Component.m_237110_("commands.place.jigsaw.invalid", p_233252_));
    final ResourceKey<? extends Registry<T>> f_212364_;

    public ResourceKeyArgument(ResourceKey<? extends Registry<T>> p_212367_) {
        this.f_212364_ = p_212367_;
    }

    public static <T> ResourceKeyArgument<T> m_212386_(ResourceKey<? extends Registry<T>> p_212387_) {
        return new ResourceKeyArgument<T>(p_212387_);
    }

    private static <T> ResourceKey<T> m_212373_(CommandContext<CommandSourceStack> p_212374_, String p_212375_, ResourceKey<Registry<T>> p_212376_, DynamicCommandExceptionType p_212377_) throws CommandSyntaxException {
        ResourceKey $$4 = (ResourceKey)p_212374_.getArgument(p_212375_, ResourceKey.class);
        Optional<ResourceKey<T>> $$5 = $$4.m_195975_(p_212376_);
        return $$5.orElseThrow(() -> p_212377_.create((Object)$$4));
    }

    private static <T> Registry<T> m_212378_(CommandContext<CommandSourceStack> p_212379_, ResourceKey<? extends Registry<T>> p_212380_) {
        return ((CommandSourceStack)p_212379_.getSource()).m_81377_().m_206579_().m_175515_(p_212380_);
    }

    private static <T> Holder<T> m_233255_(CommandContext<CommandSourceStack> p_233256_, String p_233257_, ResourceKey<Registry<T>> p_233258_, DynamicCommandExceptionType p_233259_) throws CommandSyntaxException {
        ResourceKey $$4 = ResourceKeyArgument.m_212373_(p_233256_, p_233257_, p_233258_, p_233259_);
        return ResourceKeyArgument.m_212378_(p_233256_, p_233258_).m_203636_($$4).orElseThrow(() -> p_233259_.create((Object)$$4.m_135782_()));
    }

    public static Attribute m_212370_(CommandContext<CommandSourceStack> p_212371_, String p_212372_) throws CommandSyntaxException {
        ResourceKey $$2 = ResourceKeyArgument.m_212373_(p_212371_, p_212372_, Registry.f_122916_, f_212362_);
        return ResourceKeyArgument.m_212378_(p_212371_, Registry.f_122916_).m_123009_($$2).orElseThrow(() -> f_212362_.create((Object)$$2.m_135782_()));
    }

    public static Holder<ConfiguredFeature<?, ?>> m_212388_(CommandContext<CommandSourceStack> p_212389_, String p_212390_) throws CommandSyntaxException {
        return ResourceKeyArgument.m_233255_(p_212389_, p_212390_, Registry.f_122881_, f_212363_);
    }

    public static Holder<Structure> m_233265_(CommandContext<CommandSourceStack> p_233266_, String p_233267_) throws CommandSyntaxException {
        return ResourceKeyArgument.m_233255_(p_233266_, p_233267_, Registry.f_235725_, f_233246_);
    }

    public static Holder<StructureTemplatePool> m_233268_(CommandContext<CommandSourceStack> p_233269_, String p_233270_) throws CommandSyntaxException {
        return ResourceKeyArgument.m_233255_(p_233269_, p_233270_, Registry.f_122884_, f_233247_);
    }

    public ResourceKey<T> parse(StringReader p_212369_) throws CommandSyntaxException {
        ResourceLocation $$1 = ResourceLocation.m_135818_(p_212369_);
        return ResourceKey.m_135785_(this.f_212364_, $$1);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_212399_, SuggestionsBuilder p_212400_) {
        Object object = p_212399_.getSource();
        if (object instanceof SharedSuggestionProvider) {
            SharedSuggestionProvider $$2 = (SharedSuggestionProvider)object;
            return $$2.m_212095_(this.f_212364_, SharedSuggestionProvider.ElementSuggestionType.ELEMENTS, p_212400_, p_212399_);
        }
        return p_212400_.buildFuture();
    }

    public Collection<String> getExamples() {
        return f_212361_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static class Info<T>
    implements ArgumentTypeInfo<ResourceKeyArgument<T>, Template> {
        @Override
        public void m_214155_(Template p_233278_, FriendlyByteBuf p_233279_) {
            p_233279_.m_130085_(p_233278_.f_233293_.m_135782_());
        }

        @Override
        public Template m_213618_(FriendlyByteBuf p_233289_) {
            ResourceLocation $$1 = p_233289_.m_130281_();
            return new Template(ResourceKey.m_135788_($$1));
        }

        @Override
        public void m_213719_(Template p_233275_, JsonObject p_233276_) {
            p_233276_.addProperty("registry", p_233275_.f_233293_.m_135782_().toString());
        }

        @Override
        public Template m_214163_(ResourceKeyArgument<T> p_233281_) {
            return new Template(p_233281_.f_212364_);
        }

        @Override
        public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_213618_(friendlyByteBuf);
        }

        public final class Template
        implements ArgumentTypeInfo.Template<ResourceKeyArgument<T>> {
            final ResourceKey<? extends Registry<T>> f_233293_;

            Template(ResourceKey<? extends Registry<T>> p_233296_) {
                this.f_233293_ = p_233296_;
            }

            @Override
            public ResourceKeyArgument<T> m_213879_(CommandBuildContext p_233299_) {
                return new ResourceKeyArgument(this.f_233293_);
            }

            @Override
            public ArgumentTypeInfo<ResourceKeyArgument<T>, ?> m_213709_() {
                return Info.this;
            }

            @Override
            public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
                return this.m_213879_(commandBuildContext);
            }
        }
    }
}

