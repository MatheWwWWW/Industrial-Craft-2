/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.searchtree;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.searchtree.SuffixArray;

public interface PlainTextSearchTree<T> {
    public static <T> PlainTextSearchTree<T> m_235190_() {
        return p_235196_ -> List.of();
    }

    public static <T> PlainTextSearchTree<T> m_235197_(List<T> p_235198_, Function<T, Stream<String>> p_235199_) {
        if (p_235198_.isEmpty()) {
            return PlainTextSearchTree.m_235190_();
        }
        SuffixArray $$2 = new SuffixArray();
        for (Object $$3 : p_235198_) {
            p_235199_.apply($$3).forEach(p_235194_ -> $$2.m_119970_($$3, p_235194_.toLowerCase(Locale.ROOT)));
        }
        $$2.m_119967_();
        return $$2::m_119973_;
    }

    public List<T> m_235200_(String var1);
}

