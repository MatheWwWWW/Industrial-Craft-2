/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.server.network;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.server.network.FilteredText;

public interface TextFilter {
    public static final TextFilter f_143703_ = new TextFilter(){

        @Override
        public void m_7674_() {
        }

        @Override
        public void m_7670_() {
        }

        @Override
        public CompletableFuture<FilteredText> m_6770_(String p_143708_) {
            return CompletableFuture.completedFuture(FilteredText.m_243054_(p_143708_));
        }

        @Override
        public CompletableFuture<List<FilteredText>> m_5925_(List<String> p_143710_) {
            return CompletableFuture.completedFuture((List)p_143710_.stream().map(FilteredText::m_243054_).collect(ImmutableList.toImmutableList()));
        }
    };

    public void m_7674_();

    public void m_7670_();

    public CompletableFuture<FilteredText> m_6770_(String var1);

    public CompletableFuture<List<FilteredText>> m_5925_(List<String> var1);
}

