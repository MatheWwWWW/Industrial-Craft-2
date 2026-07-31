/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.searchtree;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.searchtree.IdSearchTree;
import net.minecraft.client.searchtree.IntersectionIterator;
import net.minecraft.client.searchtree.MergingUniqueIterator;
import net.minecraft.client.searchtree.PlainTextSearchTree;
import net.minecraft.resources.ResourceLocation;

public class FullTextSearchTree<T>
extends IdSearchTree<T> {
    private final List<T> f_235151_;
    private final Function<T, Stream<String>> f_235152_;
    private PlainTextSearchTree<T> f_235153_ = PlainTextSearchTree.m_235190_();

    public FullTextSearchTree(Function<T, Stream<String>> p_235155_, Function<T, Stream<ResourceLocation>> p_235156_, List<T> p_235157_) {
        super(p_235156_, p_235157_);
        this.f_235151_ = p_235157_;
        this.f_235152_ = p_235155_;
    }

    @Override
    public void m_214078_() {
        super.m_214078_();
        this.f_235153_ = PlainTextSearchTree.m_235197_(this.f_235151_, this.f_235152_);
    }

    @Override
    protected List<T> m_213913_(String p_235160_) {
        return this.f_235153_.m_235200_(p_235160_);
    }

    @Override
    protected List<T> m_213685_(String p_235162_, String p_235163_) {
        List $$2 = this.f_235165_.m_213904_(p_235162_);
        List $$3 = this.f_235165_.m_213906_(p_235163_);
        List<T> $$4 = this.f_235153_.m_235200_(p_235163_);
        MergingUniqueIterator $$5 = new MergingUniqueIterator($$3.iterator(), $$4.iterator(), this.f_235164_);
        return ImmutableList.copyOf(new IntersectionIterator($$2.iterator(), $$5, this.f_235164_));
    }
}

