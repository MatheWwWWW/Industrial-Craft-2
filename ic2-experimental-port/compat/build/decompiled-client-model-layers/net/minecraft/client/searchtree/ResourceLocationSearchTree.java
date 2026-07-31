/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.searchtree;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.searchtree.SuffixArray;
import net.minecraft.resources.ResourceLocation;

public interface ResourceLocationSearchTree<T> {
    public static <T> ResourceLocationSearchTree<T> m_235205_() {
        return new ResourceLocationSearchTree<T>(){

            @Override
            public List<T> m_213904_(String p_235218_) {
                return List.of();
            }

            @Override
            public List<T> m_213906_(String p_235220_) {
                return List.of();
            }
        };
    }

    public static <T> ResourceLocationSearchTree<T> m_235212_(List<T> p_235213_, Function<T, Stream<ResourceLocation>> p_235214_) {
        if (p_235213_.isEmpty()) {
            return ResourceLocationSearchTree.m_235205_();
        }
        final SuffixArray $$2 = new SuffixArray();
        final SuffixArray $$3 = new SuffixArray();
        for (Object $$4 : p_235213_) {
            p_235214_.apply($$4).forEach(p_235210_ -> {
                $$2.m_119970_($$4, p_235210_.m_135827_().toLowerCase(Locale.ROOT));
                $$3.m_119970_($$4, p_235210_.m_135815_().toLowerCase(Locale.ROOT));
            });
        }
        $$2.m_119967_();
        $$3.m_119967_();
        return new ResourceLocationSearchTree<T>(){

            @Override
            public List<T> m_213904_(String p_235227_) {
                return $$2.m_119973_(p_235227_);
            }

            @Override
            public List<T> m_213906_(String p_235229_) {
                return $$3.m_119973_(p_235229_);
            }
        };
    }

    public List<T> m_213904_(String var1);

    public List<T> m_213906_(String var1);
}

