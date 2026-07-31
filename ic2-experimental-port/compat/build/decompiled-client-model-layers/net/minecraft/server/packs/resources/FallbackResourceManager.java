/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;
import org.slf4j.Logger;

public class FallbackResourceManager
implements ResourceManager {
    static final Logger f_10600_ = LogUtils.getLogger();
    protected final List<PackEntry> f_10599_ = Lists.newArrayList();
    final PackType f_10601_;
    private final String f_10602_;

    public FallbackResourceManager(PackType p_10605_, String p_10606_) {
        this.f_10601_ = p_10605_;
        this.f_10602_ = p_10606_;
    }

    public void m_215377_(PackResources p_215378_) {
        this.m_215395_(p_215378_.m_8017_(), p_215378_, null);
    }

    public void m_215382_(PackResources p_215383_, Predicate<ResourceLocation> p_215384_) {
        this.m_215395_(p_215383_.m_8017_(), p_215383_, p_215384_);
    }

    public void m_215399_(String p_215400_, Predicate<ResourceLocation> p_215401_) {
        this.m_215395_(p_215400_, null, p_215401_);
    }

    private void m_215395_(String p_215396_, @Nullable PackResources p_215397_, @Nullable Predicate<ResourceLocation> p_215398_) {
        this.f_10599_.add(new PackEntry(p_215396_, p_215397_, p_215398_));
    }

    @Override
    public Set<String> m_7187_() {
        return ImmutableSet.of((Object)this.f_10602_);
    }

    @Override
    public Optional<Resource> m_213713_(ResourceLocation p_215419_) {
        if (!this.m_10628_(p_215419_)) {
            return Optional.empty();
        }
        for (int $$1 = this.f_10599_.size() - 1; $$1 >= 0; --$$1) {
            PackEntry $$2 = this.f_10599_.get($$1);
            PackResources $$3 = $$2.f_215433_;
            if ($$3 != null && $$3.m_7211_(this.f_10601_, p_215419_)) {
                return Optional.of(new Resource($$3.m_8017_(), this.m_215374_(p_215419_, $$3), this.m_215368_(p_215419_, $$1)));
            }
            if (!$$2.m_215440_(p_215419_)) continue;
            f_10600_.warn("Resource {} not found, but was filtered by pack {}", (Object)p_215419_, (Object)$$2.f_215432_);
            return Optional.empty();
        }
        return Optional.empty();
    }

    Resource.IoSupplier<InputStream> m_215374_(ResourceLocation p_215375_, PackResources p_215376_) {
        if (f_10600_.isDebugEnabled()) {
            return () -> {
                InputStream $$2 = p_215376_.m_8031_(this.f_10601_, p_215375_);
                return new LeakedResourceWarningInputStream($$2, p_215375_, p_215376_.m_8017_());
            };
        }
        return () -> p_215376_.m_8031_(this.f_10601_, p_215375_);
    }

    private boolean m_10628_(ResourceLocation p_10629_) {
        return !p_10629_.m_135815_().contains("..");
    }

    @Override
    public List<Resource> m_213829_(ResourceLocation p_215367_) {
        if (!this.m_10628_(p_215367_)) {
            return List.of();
        }
        ArrayList $$1 = Lists.newArrayList();
        ResourceLocation $$2 = FallbackResourceManager.m_10624_(p_215367_);
        String $$3 = null;
        for (PackEntry $$4 : this.f_10599_) {
            PackResources $$5;
            if ($$4.m_215440_(p_215367_)) {
                if (!$$1.isEmpty()) {
                    $$3 = $$4.f_215432_;
                }
                $$1.clear();
            } else if ($$4.m_215440_($$2)) {
                $$1.forEach(SinglePackResourceThunkSupplier::m_215460_);
            }
            if (($$5 = $$4.f_215433_) == null || !$$5.m_7211_(this.f_10601_, p_215367_)) continue;
            $$1.add(new SinglePackResourceThunkSupplier(p_215367_, $$2, $$5));
        }
        if ($$1.isEmpty() && $$3 != null) {
            f_10600_.info("Resource {} was filtered by pack {}", (Object)p_215367_, $$3);
        }
        return $$1.stream().map(SinglePackResourceThunkSupplier::m_215461_).toList();
    }

    @Override
    public Map<ResourceLocation, Resource> m_214159_(String p_215413_, Predicate<ResourceLocation> p_215414_) {
        Object2IntOpenHashMap $$2 = new Object2IntOpenHashMap();
        int $$3 = this.f_10599_.size();
        for (int $$4 = 0; $$4 < $$3; ++$$4) {
            PackEntry $$5 = this.f_10599_.get($$4);
            $$5.m_215442_((Collection<ResourceLocation>)$$2.keySet());
            if ($$5.f_215433_ == null) continue;
            for (ResourceLocation $$6 : $$5.f_215433_.m_214146_(this.f_10601_, this.f_10602_, p_215413_, p_215414_)) {
                $$2.put((Object)$$6, $$4);
            }
        }
        TreeMap $$7 = Maps.newTreeMap();
        for (Object2IntMap.Entry $$8 : Object2IntMaps.fastIterable((Object2IntMap)$$2)) {
            int $$9 = $$8.getIntValue();
            ResourceLocation $$10 = (ResourceLocation)$$8.getKey();
            PackResources $$11 = this.f_10599_.get((int)$$9).f_215433_;
            $$7.put($$10, new Resource($$11.m_8017_(), this.m_215374_($$10, $$11), this.m_215368_($$10, $$9)));
        }
        return $$7;
    }

    private Resource.IoSupplier<ResourceMetadata> m_215368_(ResourceLocation p_215369_, int p_215370_) {
        return () -> {
            ResourceLocation $$2 = FallbackResourceManager.m_10624_(p_215369_);
            for (int $$3 = this.f_10599_.size() - 1; $$3 >= p_215370_; --$$3) {
                PackEntry $$4 = this.f_10599_.get($$3);
                PackResources $$5 = $$4.f_215433_;
                if ($$5 != null && $$5.m_7211_(this.f_10601_, $$2)) {
                    try (InputStream $$6 = $$5.m_8031_(this.f_10601_, $$2);){
                        ResourceMetadata resourceMetadata = ResourceMetadata.m_215580_($$6);
                        return resourceMetadata;
                    }
                }
                if ($$4.m_215440_($$2)) break;
            }
            return ResourceMetadata.f_215577_;
        };
    }

    private static void m_215392_(PackEntry p_215393_, Map<ResourceLocation, EntryStack> p_215394_) {
        Iterator<Map.Entry<ResourceLocation, EntryStack>> $$2 = p_215394_.entrySet().iterator();
        while ($$2.hasNext()) {
            Map.Entry<ResourceLocation, EntryStack> $$3 = $$2.next();
            ResourceLocation $$4 = $$3.getKey();
            EntryStack $$5 = $$3.getValue();
            if (p_215393_.m_215440_($$4)) {
                $$2.remove();
                continue;
            }
            if (!p_215393_.m_215440_($$5.f_215420_())) continue;
            $$5.f_215421_.forEach(SinglePackResourceThunkSupplier::m_215460_);
        }
    }

    private void m_215387_(PackEntry p_215388_, String p_215389_, Predicate<ResourceLocation> p_215390_, Map<ResourceLocation, EntryStack> p_215391_) {
        PackResources $$4 = p_215388_.f_215433_;
        if ($$4 == null) {
            return;
        }
        for (ResourceLocation $$5 : $$4.m_214146_(this.f_10601_, this.f_10602_, p_215389_, p_215390_)) {
            ResourceLocation $$6 = FallbackResourceManager.m_10624_($$5);
            p_215391_.computeIfAbsent($$5, p_215373_ -> new EntryStack($$6, Lists.newArrayList())).f_215421_().add(new SinglePackResourceThunkSupplier($$5, $$6, $$4));
        }
    }

    @Override
    public Map<ResourceLocation, List<Resource>> m_214160_(String p_215416_, Predicate<ResourceLocation> p_215417_) {
        HashMap $$2 = Maps.newHashMap();
        for (PackEntry $$3 : this.f_10599_) {
            FallbackResourceManager.m_215392_($$3, $$2);
            this.m_215387_($$3, p_215416_, p_215417_, $$2);
        }
        TreeMap $$4 = Maps.newTreeMap();
        $$2.forEach((p_215404_, p_215405_) -> $$4.put(p_215404_, p_215405_.m_215427_()));
        return $$4;
    }

    @Override
    public Stream<PackResources> m_7536_() {
        return this.f_10599_.stream().map(p_215386_ -> p_215386_.f_215433_).filter(Objects::nonNull);
    }

    static ResourceLocation m_10624_(ResourceLocation p_10625_) {
        return new ResourceLocation(p_10625_.m_135827_(), p_10625_.m_135815_() + ".mcmeta");
    }

    record PackEntry(String f_215432_, @Nullable PackResources f_215433_, @Nullable Predicate<ResourceLocation> f_215434_) {
        public void m_215442_(Collection<ResourceLocation> p_215443_) {
            if (this.f_215434_ != null) {
                p_215443_.removeIf(this.f_215434_);
            }
        }

        public boolean m_215440_(ResourceLocation p_215441_) {
            return this.f_215434_ != null && this.f_215434_.test(p_215441_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PackEntry.class, "name;resources;filter", "f_215432_", "f_215433_", "f_215434_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PackEntry.class, "name;resources;filter", "f_215432_", "f_215433_", "f_215434_"}, this);
        }

        @Override
        public final boolean equals(Object p_215447_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PackEntry.class, "name;resources;filter", "f_215432_", "f_215433_", "f_215434_"}, this, p_215447_);
        }
    }

    class SinglePackResourceThunkSupplier {
        private final ResourceLocation f_215451_;
        private final ResourceLocation f_215452_;
        private final PackResources f_215453_;
        private boolean f_215454_ = true;

        SinglePackResourceThunkSupplier(ResourceLocation p_215457_, ResourceLocation p_215458_, PackResources p_215459_) {
            this.f_215453_ = p_215459_;
            this.f_215451_ = p_215457_;
            this.f_215452_ = p_215458_;
        }

        public void m_215460_() {
            this.f_215454_ = false;
        }

        public Resource m_215461_() {
            String $$0 = this.f_215453_.m_8017_();
            if (this.f_215454_) {
                return new Resource($$0, FallbackResourceManager.this.m_215374_(this.f_215451_, this.f_215453_), () -> {
                    if (this.f_215453_.m_7211_(FallbackResourceManager.this.f_10601_, this.f_215452_)) {
                        try (InputStream $$0 = this.f_215453_.m_8031_(FallbackResourceManager.this.f_10601_, this.f_215452_);){
                            ResourceMetadata resourceMetadata = ResourceMetadata.m_215580_($$0);
                            return resourceMetadata;
                        }
                    }
                    return ResourceMetadata.f_215577_;
                });
            }
            return new Resource($$0, FallbackResourceManager.this.m_215374_(this.f_215451_, this.f_215453_));
        }
    }

    record EntryStack(ResourceLocation f_215420_, List<SinglePackResourceThunkSupplier> f_215421_) {
        List<Resource> m_215427_() {
            return this.f_215421_().stream().map(SinglePackResourceThunkSupplier::m_215461_).toList();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{EntryStack.class, "metadataLocation;entries", "f_215420_", "f_215421_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{EntryStack.class, "metadataLocation;entries", "f_215420_", "f_215421_"}, this);
        }

        @Override
        public final boolean equals(Object p_215429_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{EntryStack.class, "metadataLocation;entries", "f_215420_", "f_215421_"}, this, p_215429_);
        }
    }

    static class LeakedResourceWarningInputStream
    extends FilterInputStream {
        private final String f_10630_;
        private boolean f_10631_;

        public LeakedResourceWarningInputStream(InputStream p_10633_, ResourceLocation p_10634_, String p_10635_) {
            super(p_10633_);
            ByteArrayOutputStream $$3 = new ByteArrayOutputStream();
            new Exception().printStackTrace(new PrintStream($$3));
            this.f_10630_ = "Leaked resource: '" + p_10634_ + "' loaded from pack: '" + p_10635_ + "'\n" + $$3;
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.f_10631_ = true;
        }

        protected void finalize() throws Throwable {
            if (!this.f_10631_) {
                f_10600_.warn(this.f_10630_);
            }
            super.finalize();
        }
    }
}

