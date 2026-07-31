/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components.events;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.gui.components.events.GuiEventListener;

public interface ContainerEventHandler
extends GuiEventListener {
    public List<? extends GuiEventListener> m_6702_();

    default public Optional<GuiEventListener> m_94729_(double p_94730_, double p_94731_) {
        for (GuiEventListener guiEventListener : this.m_6702_()) {
            if (!guiEventListener.m_5953_(p_94730_, p_94731_)) continue;
            return Optional.of(guiEventListener);
        }
        return Optional.empty();
    }

    @Override
    default public boolean m_6375_(double p_94695_, double p_94696_, int p_94697_) {
        for (GuiEventListener guiEventListener : this.m_6702_()) {
            if (!guiEventListener.m_6375_(p_94695_, p_94696_, p_94697_)) continue;
            this.m_7522_(guiEventListener);
            if (p_94697_ == 0) {
                this.m_7897_(true);
            }
            return true;
        }
        return false;
    }

    @Override
    default public boolean m_6348_(double p_94722_, double p_94723_, int p_94724_) {
        this.m_7897_(false);
        return this.m_94729_(p_94722_, p_94723_).filter(p_94708_ -> p_94708_.m_6348_(p_94722_, p_94723_, p_94724_)).isPresent();
    }

    @Override
    default public boolean m_7979_(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
        if (this.m_7222_() != null && this.m_7282_() && p_94701_ == 0) {
            return this.m_7222_().m_7979_(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
        }
        return false;
    }

    public boolean m_7282_();

    public void m_7897_(boolean var1);

    @Override
    default public boolean m_6050_(double p_94686_, double p_94687_, double p_94688_) {
        return this.m_94729_(p_94686_, p_94687_).filter(p_94693_ -> p_94693_.m_6050_(p_94686_, p_94687_, p_94688_)).isPresent();
    }

    @Override
    default public boolean m_7933_(int p_94710_, int p_94711_, int p_94712_) {
        return this.m_7222_() != null && this.m_7222_().m_7933_(p_94710_, p_94711_, p_94712_);
    }

    @Override
    default public boolean m_7920_(int p_94715_, int p_94716_, int p_94717_) {
        return this.m_7222_() != null && this.m_7222_().m_7920_(p_94715_, p_94716_, p_94717_);
    }

    @Override
    default public boolean m_5534_(char p_94683_, int p_94684_) {
        return this.m_7222_() != null && this.m_7222_().m_5534_(p_94683_, p_94684_);
    }

    @Nullable
    public GuiEventListener m_7222_();

    public void m_7522_(@Nullable GuiEventListener var1);

    default public void m_94718_(@Nullable GuiEventListener p_94719_) {
        this.m_7522_(p_94719_);
        p_94719_.m_5755_(true);
    }

    default public void m_94725_(@Nullable GuiEventListener p_94726_) {
        this.m_7522_(p_94726_);
    }

    @Override
    default public boolean m_5755_(boolean p_94728_) {
        Supplier<GuiEventListener> $$10;
        BooleanSupplier $$9;
        int $$7;
        boolean $$2;
        GuiEventListener $$1 = this.m_7222_();
        boolean bl = $$2 = $$1 != null;
        if ($$2 && $$1.m_5755_(p_94728_)) {
            return true;
        }
        List<? extends GuiEventListener> $$3 = this.m_6702_();
        int $$4 = $$3.indexOf($$1);
        if ($$2 && $$4 >= 0) {
            int $$5 = $$4 + (p_94728_ ? 1 : 0);
        } else if (p_94728_) {
            boolean $$6 = false;
        } else {
            $$7 = $$3.size();
        }
        ListIterator<? extends GuiEventListener> $$8 = $$3.listIterator($$7);
        BooleanSupplier booleanSupplier = p_94728_ ? $$8::hasNext : ($$9 = $$8::hasPrevious);
        Supplier<GuiEventListener> supplier = p_94728_ ? $$8::next : ($$10 = $$8::previous);
        while ($$9.getAsBoolean()) {
            GuiEventListener $$11 = $$10.get();
            if (!$$11.m_5755_(p_94728_)) continue;
            this.m_7522_($$11);
            return true;
        }
        this.m_7522_(null);
        return false;
    }
}

