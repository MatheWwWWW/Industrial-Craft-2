/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs.resources;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceFilterSection;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

public class MultiPackResourceManager
implements CloseableResourceManager {
    private static final Logger f_215463_ = LogUtils.getLogger();
    private final Map<String, FallbackResourceManager> f_203794_;
    private final List<PackResources> f_203795_;

    public MultiPackResourceManager(PackType p_203797_, List<PackResources> p_203798_) {
        this.f_203795_ = List.copyOf(p_203798_);
        HashMap<String, FallbackResourceManager> $$2 = new HashMap<String, FallbackResourceManager>();
        List $$3 = p_203798_.stream().flatMap(p_215471_ -> p_215471_.m_5698_(p_203797_).stream()).distinct().toList();
        for (PackResources $$4 : p_203798_) {
            ResourceFilterSection $$5 = this.m_215467_($$4);
            Set<String> $$6 = $$4.m_5698_(p_203797_);
            Predicate<ResourceLocation> $$7 = $$5 != null ? p_215474_ -> $$5.m_215528_(p_215474_.m_135815_()) : null;
            for (String $$8 : $$3) {
                boolean $$10;
                boolean $$9 = $$6.contains($$8);
                boolean bl = $$10 = $$5 != null && $$5.m_215523_($$8);
                if (!$$9 && !$$10) continue;
                FallbackResourceManager $$11 = (FallbackResourceManager)$$2.get($$8);
                if ($$11 == null) {
                    $$11 = new FallbackResourceManager(p_203797_, $$8);
                    $$2.put($$8, $$11);
                }
                if ($$9 && $$10) {
                    $$11.m_215382_($$4, $$7);
                    continue;
                }
                if ($$9) {
                    $$11.m_215377_($$4);
                    continue;
                }
                $$11.m_215399_($$4.m_8017_(), $$7);
            }
        }
        this.f_203794_ = $$2;
    }

    @Nullable
    private ResourceFilterSection m_215467_(PackResources p_215468_) {
        try {
            return p_215468_.m_5550_(ResourceFilterSection.f_215512_);
        }
        catch (IOException $$1) {
            f_215463_.error("Failed to get filter section from pack {}", (Object)p_215468_.m_8017_());
            return null;
        }
    }

    @Override
    public Set<String> m_7187_() {
        return this.f_203794_.keySet();
    }

    @Override
    public Optional<Resource> m_213713_(ResourceLocation p_215482_) {
        ResourceManager $$1 = this.f_203794_.get(p_215482_.m_135827_());
        if ($$1 != null) {
            return $$1.m_213713_(p_215482_);
        }
        return Optional.empty();
    }

    @Override
    public List<Resource> m_213829_(ResourceLocation p_215466_) {
        ResourceManager $$1 = this.f_203794_.get(p_215466_.m_135827_());
        if ($$1 != null) {
            return $$1.m_213829_(p_215466_);
        }
        return List.of();
    }

    @Override
    public Map<ResourceLocation, Resource> m_214159_(String p_215476_, Predicate<ResourceLocation> p_215477_) {
        TreeMap<ResourceLocation, Resource> $$2 = new TreeMap<ResourceLocation, Resource>();
        for (FallbackResourceManager $$3 : this.f_203794_.values()) {
            $$2.putAll($$3.m_214159_(p_215476_, p_215477_));
        }
        return $$2;
    }

    @Override
    public Map<ResourceLocation, List<Resource>> m_214160_(String p_215479_, Predicate<ResourceLocation> p_215480_) {
        TreeMap<ResourceLocation, List<Resource>> $$2 = new TreeMap<ResourceLocation, List<Resource>>();
        for (FallbackResourceManager $$3 : this.f_203794_.values()) {
            $$2.putAll($$3.m_214160_(p_215479_, p_215480_));
        }
        return $$2;
    }

    @Override
    public Stream<PackResources> m_7536_() {
        return this.f_203795_.stream();
    }

    @Override
    public void close() {
        this.f_203795_.forEach(PackResources::close);
    }
}

