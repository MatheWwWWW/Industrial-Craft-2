/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class ServerFunctionLibrary
implements PreparableReloadListener {
    private static final Logger f_136043_ = LogUtils.getLogger();
    private static final String f_179927_ = ".mcfunction";
    private static final int f_136044_ = "functions/".length();
    private static final int f_136045_ = ".mcfunction".length();
    private volatile Map<ResourceLocation, CommandFunction> f_136046_ = ImmutableMap.of();
    private final TagLoader<CommandFunction> f_136047_ = new TagLoader(this::m_136089_, "tags/functions");
    private volatile Map<ResourceLocation, Collection<CommandFunction>> f_136048_ = Map.of();
    private final int f_136049_;
    private final CommandDispatcher<CommandSourceStack> f_136050_;

    public Optional<CommandFunction> m_136089_(ResourceLocation p_136090_) {
        return Optional.ofNullable(this.f_136046_.get(p_136090_));
    }

    public Map<ResourceLocation, CommandFunction> m_136055_() {
        return this.f_136046_;
    }

    public Collection<CommandFunction> m_214327_(ResourceLocation p_214328_) {
        return this.f_136048_.getOrDefault(p_214328_, List.of());
    }

    public Iterable<ResourceLocation> m_206891_() {
        return this.f_136048_.keySet();
    }

    public ServerFunctionLibrary(int p_136053_, CommandDispatcher<CommandSourceStack> p_136054_) {
        this.f_136049_ = p_136053_;
        this.f_136050_ = p_136054_;
    }

    @Override
    public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_136057_, ResourceManager p_136058_, ProfilerFiller p_136059_, ProfilerFiller p_136060_, Executor p_136061_, Executor p_136062_) {
        CompletableFuture<Map> $$6 = CompletableFuture.supplyAsync(() -> this.f_136047_.m_144495_(p_136058_), p_136061_);
        CompletionStage $$7 = CompletableFuture.supplyAsync(() -> p_136058_.m_214159_("functions", p_214330_ -> p_214330_.m_135815_().endsWith(f_179927_)), p_136061_).thenCompose(p_214326_ -> {
            HashMap $$2 = Maps.newHashMap();
            CommandSourceStack $$3 = new CommandSourceStack(CommandSource.f_80164_, Vec3.f_82478_, Vec2.f_82462_, null, this.f_136049_, "", CommonComponents.f_237098_, null, null);
            for (Map.Entry $$4 : p_214326_.entrySet()) {
                ResourceLocation $$5 = (ResourceLocation)$$4.getKey();
                String $$6 = $$5.m_135815_();
                ResourceLocation $$7 = new ResourceLocation($$5.m_135827_(), $$6.substring(f_136044_, $$6.length() - f_136045_));
                $$2.put($$7, CompletableFuture.supplyAsync(() -> {
                    List<String> $$3 = ServerFunctionLibrary.m_214316_((Resource)$$4.getValue());
                    return CommandFunction.m_77984_($$7, this.f_136050_, $$3, $$3);
                }, p_136061_));
            }
            CompletableFuture[] $$8 = $$2.values().toArray(new CompletableFuture[0]);
            return CompletableFuture.allOf($$8).handle((p_179949_, p_179950_) -> $$2);
        });
        return ((CompletableFuture)((CompletableFuture)$$6.thenCombine($$7, Pair::of)).thenCompose(p_136057_::m_6769_)).thenAcceptAsync(p_179944_ -> {
            Map $$1 = (Map)p_179944_.getSecond();
            ImmutableMap.Builder $$2 = ImmutableMap.builder();
            $$1.forEach((p_179941_, p_179942_) -> ((CompletableFuture)p_179942_.handle((p_179954_, p_179955_) -> {
                if (p_179955_ != null) {
                    f_136043_.error("Failed to load function {}", p_179941_, p_179955_);
                } else {
                    $$2.put(p_179941_, p_179954_);
                }
                return null;
            })).join());
            this.f_136046_ = $$2.build();
            this.f_136048_ = this.f_136047_.m_203898_((Map)p_179944_.getFirst());
        }, p_136062_);
    }

    private static List<String> m_214316_(Resource p_214317_) {
        List<String> list;
        block8: {
            BufferedReader $$1 = p_214317_.m_215508_();
            try {
                list = $$1.lines().toList();
                if ($$1 == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if ($$1 != null) {
                        try {
                            $$1.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException $$2) {
                    throw new CompletionException($$2);
                }
            }
            $$1.close();
        }
        return list;
    }
}

