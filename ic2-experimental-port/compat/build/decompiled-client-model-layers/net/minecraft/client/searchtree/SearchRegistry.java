/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.searchtree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.searchtree.RefreshableSearchTree;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;

public class SearchRegistry
implements ResourceManagerReloadListener {
    public static final Key<ItemStack> f_119941_ = new Key();
    public static final Key<ItemStack> f_119942_ = new Key();
    public static final Key<RecipeCollection> f_119943_ = new Key();
    private final Map<Key<?>, TreeEntry<?>> f_119944_ = new HashMap();

    @Override
    public void m_6213_(ResourceManager p_119948_) {
        for (TreeEntry<?> $$1 : this.f_119944_.values()) {
            $$1.m_235244_();
        }
    }

    public <T> void m_235232_(Key<T> p_235233_, TreeBuilderSupplier<T> p_235234_) {
        this.f_119944_.put(p_235233_, new TreeEntry<T>(p_235234_));
    }

    private <T> TreeEntry<T> m_235238_(Key<T> p_235239_) {
        TreeEntry<?> $$1 = this.f_119944_.get(p_235239_);
        if ($$1 == null) {
            throw new IllegalStateException("Tree builder not registered");
        }
        return $$1;
    }

    public <T> void m_235235_(Key<T> p_235236_, List<T> p_235237_) {
        this.m_235238_(p_235236_).m_235245_(p_235237_);
    }

    public <T> SearchTree<T> m_235230_(Key<T> p_235231_) {
        return this.m_235238_(p_235231_).f_235241_;
    }

    static class TreeEntry<T> {
        private final TreeBuilderSupplier<T> f_235240_;
        RefreshableSearchTree<T> f_235241_ = RefreshableSearchTree.m_235204_();

        TreeEntry(TreeBuilderSupplier<T> p_235243_) {
            this.f_235240_ = p_235243_;
        }

        void m_235245_(List<T> p_235246_) {
            this.f_235241_ = (RefreshableSearchTree)this.f_235240_.apply(p_235246_);
            this.f_235241_.m_214078_();
        }

        void m_235244_() {
            this.f_235241_.m_214078_();
        }
    }

    public static interface TreeBuilderSupplier<T>
    extends Function<List<T>, RefreshableSearchTree<T>> {
    }

    public static class Key<T> {
    }
}

