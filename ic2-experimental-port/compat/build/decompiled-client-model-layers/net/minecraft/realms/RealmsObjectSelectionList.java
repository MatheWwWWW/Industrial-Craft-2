/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.realms;

import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;

public abstract class RealmsObjectSelectionList<E extends ObjectSelectionList.Entry<E>>
extends ObjectSelectionList<E> {
    protected RealmsObjectSelectionList(int p_120745_, int p_120746_, int p_120747_, int p_120748_, int p_120749_) {
        super(Minecraft.m_91087_(), p_120745_, p_120746_, p_120747_, p_120748_, p_120749_);
    }

    public void m_120767_(int p_120768_) {
        if (p_120768_ == -1) {
            this.m_6987_(null);
        } else if (super.m_5773_() != 0) {
            this.m_6987_((ObjectSelectionList.Entry)this.m_93500_(p_120768_));
        }
    }

    public void m_7109_(int p_120750_) {
        this.m_120767_(p_120750_);
    }

    public void m_7980_(int p_120751_, int p_120752_, double p_120753_, double p_120754_, int p_120755_) {
    }

    @Override
    public int m_5775_() {
        return 0;
    }

    @Override
    public int m_5756_() {
        return this.m_5747_() + this.m_5759_();
    }

    @Override
    public int m_5759_() {
        return (int)((double)this.f_93388_ * 0.6);
    }

    @Override
    public void m_5988_(Collection<E> p_120759_) {
        super.m_5988_(p_120759_);
    }

    @Override
    public int m_5773_() {
        return super.m_5773_();
    }

    @Override
    public int m_7610_(int p_120766_) {
        return super.m_7610_(p_120766_);
    }

    @Override
    public int m_5747_() {
        return super.m_5747_();
    }

    @Override
    public int m_7085_(E p_120757_) {
        return super.m_7085_(p_120757_);
    }

    public void m_7178_() {
        this.m_93516_();
    }

    @Override
    public /* synthetic */ int m_7085_(AbstractSelectionList.Entry entry) {
        return this.m_7085_((E)((ObjectSelectionList.Entry)entry));
    }
}

