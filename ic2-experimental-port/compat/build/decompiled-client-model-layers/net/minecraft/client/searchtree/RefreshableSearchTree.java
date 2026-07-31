/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.searchtree;

import java.util.List;
import net.minecraft.client.searchtree.SearchTree;

public interface RefreshableSearchTree<T>
extends SearchTree<T> {
    public static <T> RefreshableSearchTree<T> m_235204_() {
        return p_235203_ -> List.of();
    }

    default public void m_214078_() {
    }
}

