/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.searchtree;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.client.searchtree.IntersectionIterator;
import net.minecraft.client.searchtree.RefreshableSearchTree;
import net.minecraft.client.searchtree.ResourceLocationSearchTree;
import net.minecraft.resources.ResourceLocation;

public class IdSearchTree<T>
implements RefreshableSearchTree<T> {
    protected final Comparator<T> f_235164_;
    protected final ResourceLocationSearchTree<T> f_235165_;

    public IdSearchTree(Function<T, Stream<ResourceLocation>> p_235167_, List<T> p_235168_) {
        ToIntFunction<T> $$2 = Util.m_214686_(p_235168_);
        this.f_235164_ = Comparator.comparingInt($$2);
        this.f_235165_ = ResourceLocationSearchTree.m_235212_(p_235168_, p_235167_);
    }

    @Override
    public List<T> m_6293_(String p_235173_) {
        int $$1 = p_235173_.indexOf(58);
        if ($$1 == -1) {
            return this.m_213913_(p_235173_);
        }
        return this.m_213685_(p_235173_.substring(0, $$1).trim(), p_235173_.substring($$1 + 1).trim());
    }

    protected List<T> m_213913_(String p_235169_) {
        return this.f_235165_.m_213906_(p_235169_);
    }

    protected List<T> m_213685_(String p_235170_, String p_235171_) {
        List<T> $$2 = this.f_235165_.m_213904_(p_235170_);
        List<T> $$3 = this.f_235165_.m_213906_(p_235171_);
        return ImmutableList.copyOf(new IntersectionIterator<T>($$2.iterator(), $$3.iterator(), this.f_235164_));
    }
}

