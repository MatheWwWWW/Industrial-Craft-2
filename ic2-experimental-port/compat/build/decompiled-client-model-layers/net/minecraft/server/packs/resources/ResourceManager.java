/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;

public interface ResourceManager
extends ResourceProvider {
    public Set<String> m_7187_();

    public List<Resource> m_213829_(ResourceLocation var1);

    public Map<ResourceLocation, Resource> m_214159_(String var1, Predicate<ResourceLocation> var2);

    public Map<ResourceLocation, List<Resource>> m_214160_(String var1, Predicate<ResourceLocation> var2);

    public Stream<PackResources> m_7536_();

    public static final class Empty
    extends Enum<Empty>
    implements ResourceManager {
        public static final /* enum */ Empty INSTANCE = new Empty();
        private static final /* synthetic */ Empty[] $VALUES;

        public static Empty[] values() {
            return (Empty[])$VALUES.clone();
        }

        public static Empty valueOf(String p_10749_) {
            return Enum.valueOf(Empty.class, p_10749_);
        }

        @Override
        public Set<String> m_7187_() {
            return Set.of();
        }

        @Override
        public Optional<Resource> m_213713_(ResourceLocation p_215576_) {
            return Optional.empty();
        }

        @Override
        public List<Resource> m_213829_(ResourceLocation p_215568_) {
            return List.of();
        }

        @Override
        public Map<ResourceLocation, Resource> m_214159_(String p_215570_, Predicate<ResourceLocation> p_215571_) {
            return Map.of();
        }

        @Override
        public Map<ResourceLocation, List<Resource>> m_214160_(String p_215573_, Predicate<ResourceLocation> p_215574_) {
            return Map.of();
        }

        @Override
        public Stream<PackResources> m_7536_() {
            return Stream.of(new PackResources[0]);
        }

        private static /* synthetic */ Empty[] m_143934_() {
            return new Empty[]{INSTANCE};
        }

        static {
            $VALUES = Empty.m_143934_();
        }
    }
}

