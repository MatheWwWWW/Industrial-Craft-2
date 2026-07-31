/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public class OptionsList
extends ContainerObjectSelectionList<Entry> {
    public OptionsList(Minecraft p_94465_, int p_94466_, int p_94467_, int p_94468_, int p_94469_, int p_94470_) {
        super(p_94465_, p_94466_, p_94467_, p_94468_, p_94469_, p_94470_);
        this.f_93394_ = false;
    }

    public int m_232528_(OptionInstance<?> p_232529_) {
        return this.m_7085_(Entry.m_232537_(this.f_93386_.f_91066_, this.f_93388_, p_232529_));
    }

    public void m_232530_(OptionInstance<?> p_232531_, @Nullable OptionInstance<?> p_232532_) {
        this.m_7085_(Entry.m_232541_(this.f_93386_.f_91066_, this.f_93388_, p_232531_, p_232532_));
    }

    public void m_232533_(OptionInstance<?>[] p_232534_) {
        for (int $$1 = 0; $$1 < p_232534_.length; $$1 += 2) {
            this.m_232530_(p_232534_[$$1], $$1 < p_232534_.length - 1 ? p_232534_[$$1 + 1] : null);
        }
    }

    @Override
    public int m_5759_() {
        return 400;
    }

    @Override
    protected int m_5756_() {
        return super.m_5756_() + 32;
    }

    @Nullable
    public AbstractWidget m_232535_(OptionInstance<?> p_232536_) {
        for (Entry $$1 : this.m_6702_()) {
            AbstractWidget $$2 = $$1.f_169045_.get(p_232536_);
            if ($$2 == null) continue;
            return $$2;
        }
        return null;
    }

    public Optional<AbstractWidget> m_94480_(double p_94481_, double p_94482_) {
        for (Entry $$2 : this.m_6702_()) {
            for (AbstractWidget $$3 : $$2.f_94485_) {
                if (!$$3.m_5953_(p_94481_, p_94482_)) continue;
                return Optional.of($$3);
            }
        }
        return Optional.empty();
    }

    protected static class Entry
    extends ContainerObjectSelectionList.Entry<Entry> {
        final Map<OptionInstance<?>, AbstractWidget> f_169045_;
        final List<AbstractWidget> f_94485_;

        private Entry(Map<OptionInstance<?>, AbstractWidget> p_169047_) {
            this.f_169045_ = p_169047_;
            this.f_94485_ = ImmutableList.copyOf(p_169047_.values());
        }

        public static Entry m_232537_(Options p_232538_, int p_232539_, OptionInstance<?> p_232540_) {
            return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of(p_232540_, (Object)p_232540_.m_231507_(p_232538_, p_232539_ / 2 - 155, 0, 310)));
        }

        public static Entry m_232541_(Options p_232542_, int p_232543_, OptionInstance<?> p_232544_, @Nullable OptionInstance<?> p_232545_) {
            AbstractWidget $$4 = p_232544_.m_231507_(p_232542_, p_232543_ / 2 - 155, 0, 150);
            if (p_232545_ == null) {
                return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of(p_232544_, (Object)$$4));
            }
            return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of(p_232544_, (Object)$$4, p_232545_, (Object)p_232545_.m_231507_(p_232542_, p_232543_ / 2 - 155 + 160, 0, 150)));
        }

        @Override
        public void m_6311_(PoseStack p_94496_, int p_94497_, int p_94498_, int p_94499_, int p_94500_, int p_94501_, int p_94502_, int p_94503_, boolean p_94504_, float p_94505_) {
            this.f_94485_.forEach(p_94494_ -> {
                p_94494_.f_93621_ = p_94498_;
                p_94494_.m_6305_(p_94496_, p_94502_, p_94503_, p_94505_);
            });
        }

        @Override
        public List<? extends GuiEventListener> m_6702_() {
            return this.f_94485_;
        }

        @Override
        public List<? extends NarratableEntry> m_142437_() {
            return this.f_94485_;
        }
    }
}

