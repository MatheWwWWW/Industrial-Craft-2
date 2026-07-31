/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.gui.narration;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationThunk;
import net.minecraft.network.chat.Component;

public interface NarrationElementOutput {
    default public void m_169146_(NarratedElementType p_169147_, Component p_169148_) {
        this.m_142549_(p_169147_, NarrationThunk.m_169160_(p_169148_.getString()));
    }

    default public void m_169143_(NarratedElementType p_169144_, String p_169145_) {
        this.m_142549_(p_169144_, NarrationThunk.m_169160_(p_169145_));
    }

    default public void m_169149_(NarratedElementType p_169150_, Component ... p_169151_) {
        this.m_142549_(p_169150_, NarrationThunk.m_169162_((List<Component>)ImmutableList.copyOf((Object[])p_169151_)));
    }

    public void m_142549_(NarratedElementType var1, NarrationThunk<?> var2);

    public NarrationElementOutput m_142047_();
}

