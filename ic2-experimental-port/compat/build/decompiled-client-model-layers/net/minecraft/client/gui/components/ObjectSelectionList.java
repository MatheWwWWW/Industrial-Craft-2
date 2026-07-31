/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarrationSupplier;
import net.minecraft.network.chat.Component;

public abstract class ObjectSelectionList<E extends Entry<E>>
extends AbstractSelectionList<E> {
    private static final Component f_169039_ = Component.m_237115_("narration.selection.usage");
    private boolean f_94440_;

    public ObjectSelectionList(Minecraft p_94442_, int p_94443_, int p_94444_, int p_94445_, int p_94446_, int p_94447_) {
        super(p_94442_, p_94443_, p_94444_, p_94445_, p_94446_, p_94447_);
    }

    @Override
    public boolean m_5755_(boolean p_94449_) {
        if (!this.f_94440_ && this.m_5773_() == 0) {
            return false;
        }
        boolean bl = this.f_94440_ = !this.f_94440_;
        if (this.f_94440_ && this.m_93511_() == null && this.m_5773_() > 0) {
            this.m_6778_(AbstractSelectionList.SelectionDirection.DOWN);
        } else if (this.f_94440_ && this.m_93511_() != null) {
            this.m_93519_();
        }
        return this.f_94440_;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_169042_) {
        Entry $$1 = (Entry)this.m_168795_();
        if ($$1 != null) {
            this.m_168790_(p_169042_.m_142047_(), $$1);
            $$1.m_142291_(p_169042_);
        } else {
            Entry $$2 = (Entry)this.m_93511_();
            if ($$2 != null) {
                this.m_168790_(p_169042_.m_142047_(), $$2);
                $$2.m_142291_(p_169042_);
            }
        }
        if (this.m_5694_()) {
            p_169042_.m_169146_(NarratedElementType.USAGE, f_169039_);
        }
    }

    public static abstract class Entry<E extends Entry<E>>
    extends AbstractSelectionList.Entry<E>
    implements NarrationSupplier {
        @Override
        public boolean m_5755_(boolean p_94452_) {
            return false;
        }

        public abstract Component m_142172_();

        @Override
        public void m_142291_(NarrationElementOutput p_169044_) {
            p_169044_.m_169146_(NarratedElementType.TITLE, this.m_142172_());
        }
    }
}

