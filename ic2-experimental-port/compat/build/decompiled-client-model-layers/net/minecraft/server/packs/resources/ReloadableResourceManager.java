/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadInstance;
import net.minecraft.util.Unit;
import org.slf4j.Logger;

public class ReloadableResourceManager
implements ResourceManager,
AutoCloseable {
    private static final Logger f_203814_ = LogUtils.getLogger();
    private CloseableResourceManager f_203815_;
    private final List<PreparableReloadListener> f_203816_ = Lists.newArrayList();
    private final PackType f_203817_;

    public ReloadableResourceManager(PackType p_203820_) {
        this.f_203817_ = p_203820_;
        this.f_203815_ = new MultiPackResourceManager(p_203820_, List.of());
    }

    @Override
    public void close() {
        this.f_203815_.close();
    }

    public void m_7217_(PreparableReloadListener p_10714_) {
        this.f_203816_.add(p_10714_);
    }

    public ReloadInstance m_142463_(Executor p_143930_, Executor p_143931_, CompletableFuture<Unit> p_143932_, List<PackResources> p_143933_) {
        f_203814_.info("Reloading ResourceManager: {}", LogUtils.defer(() -> p_143933_.stream().map(PackResources::m_8017_).collect(Collectors.joining(", "))));
        this.f_203815_.close();
        this.f_203815_ = new MultiPackResourceManager(this.f_203817_, p_143933_);
        return SimpleReloadInstance.m_203834_(this.f_203815_, this.f_203816_, p_143930_, p_143931_, p_143932_, f_203814_.isDebugEnabled());
    }

    @Override
    public Optional<Resource> m_213713_(ResourceLocation p_215494_) {
        return this.f_203815_.m_213713_(p_215494_);
    }

    @Override
    public Set<String> m_7187_() {
        return this.f_203815_.m_7187_();
    }

    @Override
    public List<Resource> m_213829_(ResourceLocation p_215486_) {
        return this.f_203815_.m_213829_(p_215486_);
    }

    @Override
    public Map<ResourceLocation, Resource> m_214159_(String p_215488_, Predicate<ResourceLocation> p_215489_) {
        return this.f_203815_.m_214159_(p_215488_, p_215489_);
    }

    @Override
    public Map<ResourceLocation, List<Resource>> m_214160_(String p_215491_, Predicate<ResourceLocation> p_215492_) {
        return this.f_203815_.m_214160_(p_215491_, p_215492_);
    }

    @Override
    public Stream<PackResources> m_7536_() {
        return this.f_203815_.m_7536_();
    }
}

