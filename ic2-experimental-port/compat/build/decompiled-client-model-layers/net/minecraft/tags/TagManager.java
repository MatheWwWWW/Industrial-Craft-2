/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import net.minecraft.util.profiling.ProfilerFiller;

public class TagManager
implements PreparableReloadListener {
    private static final Map<ResourceKey<? extends Registry<?>>, String> f_203902_ = Map.of(Registry.f_122901_, "tags/blocks", Registry.f_122903_, "tags/entity_types", Registry.f_122899_, "tags/fluids", Registry.f_175423_, "tags/game_events", Registry.f_122904_, "tags/items");
    private final RegistryAccess f_144569_;
    private List<LoadResult<?>> f_203903_ = List.of();

    public TagManager(RegistryAccess p_144572_) {
        this.f_144569_ = p_144572_;
    }

    public List<LoadResult<?>> m_203904_() {
        return this.f_203903_;
    }

    public static String m_203918_(ResourceKey<? extends Registry<?>> p_203919_) {
        String $$1 = f_203902_.get(p_203919_);
        if ($$1 != null) {
            return $$1;
        }
        return "tags/" + p_203919_.m_135782_().m_135815_();
    }

    @Override
    public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_13482_, ResourceManager p_13483_, ProfilerFiller p_13484_, ProfilerFiller p_13485_, Executor p_13486_, Executor p_13487_) {
        List<CompletableFuture> $$6 = this.f_144569_.m_206193_().map(p_203927_ -> this.m_203907_(p_13483_, p_13486_, (RegistryAccess.RegistryEntry)p_203927_)).toList();
        return ((CompletableFuture)CompletableFuture.allOf((CompletableFuture[])$$6.toArray(CompletableFuture[]::new)).thenCompose(p_13482_::m_6769_)).thenAcceptAsync(p_203917_ -> {
            this.f_203903_ = $$6.stream().map(CompletableFuture::join).collect(Collectors.toUnmodifiableList());
        }, p_13487_);
    }

    private <T> CompletableFuture<LoadResult<T>> m_203907_(ResourceManager p_203908_, Executor p_203909_, RegistryAccess.RegistryEntry<T> p_203910_) {
        ResourceKey $$3 = p_203910_.f_206233_();
        Registry $$4 = p_203910_.f_206234_();
        TagLoader $$5 = new TagLoader(p_203914_ -> $$4.m_203636_(ResourceKey.m_135785_($$3, p_203914_)), TagManager.m_203918_($$3));
        return CompletableFuture.supplyAsync(() -> new LoadResult($$3, $$5.m_203900_(p_203908_)), p_203909_);
    }

    public record LoadResult<T>(ResourceKey<? extends Registry<T>> f_203928_, Map<ResourceLocation, Collection<Holder<T>>> f_203929_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{LoadResult.class, "key;tags", "f_203928_", "f_203929_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LoadResult.class, "key;tags", "f_203928_", "f_203929_"}, this);
        }

        @Override
        public final boolean equals(Object p_203936_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LoadResult.class, "key;tags", "f_203928_", "f_203929_"}, this, p_203936_);
        }
    }
}

