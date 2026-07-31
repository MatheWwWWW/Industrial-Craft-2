/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class ContainerObjectSelectionList<E extends Entry<E>>
extends AbstractSelectionList<E> {
    private boolean f_168849_;

    public ContainerObjectSelectionList(Minecraft p_94010_, int p_94011_, int p_94012_, int p_94013_, int p_94014_, int p_94015_) {
        super(p_94010_, p_94011_, p_94012_, p_94013_, p_94014_, p_94015_);
    }

    @Override
    public boolean m_5755_(boolean p_94017_) {
        this.f_168849_ = super.m_5755_(p_94017_);
        if (this.f_168849_) {
            this.m_93498_((Entry)this.m_7222_());
        }
        return this.f_168849_;
    }

    @Override
    public NarratableEntry.NarrationPriority m_142684_() {
        if (this.f_168849_) {
            return NarratableEntry.NarrationPriority.FOCUSED;
        }
        return super.m_142684_();
    }

    @Override
    protected boolean m_7987_(int p_94019_) {
        return false;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_168851_) {
        Entry $$1 = (Entry)this.m_168795_();
        if ($$1 != null) {
            $$1.m_168854_(p_168851_.m_142047_());
            this.m_168790_(p_168851_, $$1);
        } else {
            Entry $$2 = (Entry)this.m_7222_();
            if ($$2 != null) {
                $$2.m_168854_(p_168851_.m_142047_());
                this.m_168790_(p_168851_, $$2);
            }
        }
        p_168851_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.component_list.usage"));
    }

    public static abstract class Entry<E extends Entry<E>>
    extends AbstractSelectionList.Entry<E>
    implements ContainerEventHandler {
        @Nullable
        private GuiEventListener f_94020_;
        @Nullable
        private NarratableEntry f_168853_;
        private boolean f_94021_;

        @Override
        public boolean m_7282_() {
            return this.f_94021_;
        }

        @Override
        public void m_7897_(boolean p_94028_) {
            this.f_94021_ = p_94028_;
        }

        @Override
        public void m_7522_(@Nullable GuiEventListener p_94024_) {
            this.f_94020_ = p_94024_;
        }

        @Override
        @Nullable
        public GuiEventListener m_7222_() {
            return this.f_94020_;
        }

        public abstract List<? extends NarratableEntry> m_142437_();

        void m_168854_(NarrationElementOutput p_168855_) {
            List<NarratableEntry> $$1 = this.m_142437_();
            Screen.NarratableSearchResult $$2 = Screen.m_169400_($$1, this.f_168853_);
            if ($$2 != null) {
                if ($$2.f_169422_.m_169123_()) {
                    this.f_168853_ = $$2.f_169420_;
                }
                if ($$1.size() > 1) {
                    p_168855_.m_169146_(NarratedElementType.POSITION, Component.m_237110_("narrator.position.object_list", $$2.f_169421_ + 1, $$1.size()));
                    if ($$2.f_169422_ == NarratableEntry.NarrationPriority.FOCUSED) {
                        p_168855_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.component_list.usage"));
                    }
                }
                $$2.f_169420_.m_142291_(p_168855_.m_142047_());
            }
        }
    }
}

