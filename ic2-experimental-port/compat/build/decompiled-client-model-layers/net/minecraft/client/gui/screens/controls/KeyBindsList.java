/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.client.gui.screens.controls;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.ArrayUtils;

public class KeyBindsList
extends ContainerObjectSelectionList<Entry> {
    final KeyBindsScreen f_193858_;
    int f_193859_;

    public KeyBindsList(KeyBindsScreen p_193861_, Minecraft p_193862_) {
        super(p_193862_, p_193861_.f_96543_ + 45, p_193861_.f_96544_, 20, p_193861_.f_96544_ - 32, 20);
        this.f_193858_ = p_193861_;
        Object[] $$2 = (KeyMapping[])ArrayUtils.clone((Object[])p_193862_.f_91066_.f_92059_);
        Arrays.sort($$2);
        String $$3 = null;
        for (Object $$4 : $$2) {
            MutableComponent $$6;
            int $$7;
            String $$5 = ((KeyMapping)$$4).m_90858_();
            if (!$$5.equals($$3)) {
                $$3 = $$5;
                this.m_7085_(new CategoryEntry(Component.m_237115_($$5)));
            }
            if (($$7 = p_193862_.f_91062_.m_92852_($$6 = Component.m_237115_(((KeyMapping)$$4).m_90860_()))) > this.f_193859_) {
                this.f_193859_ = $$7;
            }
            this.m_7085_(new KeyEntry((KeyMapping)$$4, $$6));
        }
    }

    @Override
    protected int m_5756_() {
        return super.m_5756_() + 15;
    }

    @Override
    public int m_5759_() {
        return super.m_5759_() + 32;
    }

    public class CategoryEntry
    extends Entry {
        final Component f_193882_;
        private final int f_193883_;

        public CategoryEntry(Component p_193886_) {
            this.f_193882_ = p_193886_;
            this.f_193883_ = ((KeyBindsList)KeyBindsList.this).f_93386_.f_91062_.m_92852_(this.f_193882_);
        }

        @Override
        public void m_6311_(PoseStack p_193888_, int p_193889_, int p_193890_, int p_193891_, int p_193892_, int p_193893_, int p_193894_, int p_193895_, boolean p_193896_, float p_193897_) {
            ((KeyBindsList)KeyBindsList.this).f_93386_.f_91062_.m_92889_(p_193888_, this.f_193882_, ((KeyBindsList)KeyBindsList.this).f_93386_.f_91080_.f_96543_ / 2 - this.f_193883_ / 2, p_193890_ + p_193893_ - ((KeyBindsList)KeyBindsList.this).f_93386_.f_91062_.f_92710_ - 1, 0xFFFFFF);
        }

        @Override
        public boolean m_5755_(boolean p_193900_) {
            return false;
        }

        @Override
        public List<? extends GuiEventListener> m_6702_() {
            return Collections.emptyList();
        }

        @Override
        public List<? extends NarratableEntry> m_142437_() {
            return ImmutableList.of((Object)new NarratableEntry(){

                @Override
                public NarratableEntry.NarrationPriority m_142684_() {
                    return NarratableEntry.NarrationPriority.HOVERED;
                }

                @Override
                public void m_142291_(NarrationElementOutput p_193906_) {
                    p_193906_.m_169146_(NarratedElementType.TITLE, CategoryEntry.this.f_193882_);
                }
            });
        }
    }

    public class KeyEntry
    extends Entry {
        private final KeyMapping f_193910_;
        private final Component f_193911_;
        private final Button f_193912_;
        private final Button f_193913_;

        KeyEntry(final KeyMapping p_193916_, final Component p_193917_) {
            this.f_193910_ = p_193916_;
            this.f_193911_ = p_193917_;
            this.f_193912_ = new Button(0, 0, 75, 20, p_193917_, p_193939_ -> {
                KeyBindsList.this.f_193858_.f_193975_ = p_193916_;
            }){

                @Override
                protected MutableComponent m_5646_() {
                    if (p_193916_.m_90862_()) {
                        return Component.m_237110_("narrator.controls.unbound", p_193917_);
                    }
                    return Component.m_237110_("narrator.controls.bound", p_193917_, super.m_5646_());
                }
            };
            this.f_193913_ = new Button(0, 0, 50, 20, Component.m_237115_("controls.reset"), p_193935_ -> {
                ((KeyBindsList)KeyBindsList.this).f_93386_.f_91066_.m_92159_(p_193916_, p_193916_.m_90861_());
                KeyMapping.m_90854_();
            }){

                @Override
                protected MutableComponent m_5646_() {
                    return Component.m_237110_("narrator.controls.reset", p_193917_);
                }
            };
        }

        @Override
        public void m_6311_(PoseStack p_193923_, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            boolean $$10 = KeyBindsList.this.f_193858_.f_193975_ == this.f_193910_;
            ((KeyBindsList)KeyBindsList.this).f_93386_.f_91062_.m_92889_(p_193923_, this.f_193911_, p_193926_ + 90 - KeyBindsList.this.f_193859_, p_193925_ + p_193928_ / 2 - ((KeyBindsList)KeyBindsList.this).f_93386_.f_91062_.f_92710_ / 2, 0xFFFFFF);
            this.f_193913_.f_93620_ = p_193926_ + 190;
            this.f_193913_.f_93621_ = p_193925_;
            this.f_193913_.f_93623_ = !this.f_193910_.m_90864_();
            this.f_193913_.m_6305_(p_193923_, p_193929_, p_193930_, p_193932_);
            this.f_193912_.f_93620_ = p_193926_ + 105;
            this.f_193912_.f_93621_ = p_193925_;
            this.f_193912_.m_93666_(this.f_193910_.m_90863_());
            boolean $$11 = false;
            if (!this.f_193910_.m_90862_()) {
                for (KeyMapping $$12 : ((KeyBindsList)KeyBindsList.this).f_93386_.f_91066_.f_92059_) {
                    if ($$12 == this.f_193910_ || !this.f_193910_.m_90850_($$12)) continue;
                    $$11 = true;
                    break;
                }
            }
            if ($$10) {
                this.f_193912_.m_93666_(Component.m_237113_("> ").m_7220_(this.f_193912_.m_6035_().m_6881_().m_130940_(ChatFormatting.YELLOW)).m_130946_(" <").m_130940_(ChatFormatting.YELLOW));
            } else if ($$11) {
                this.f_193912_.m_93666_(this.f_193912_.m_6035_().m_6881_().m_130940_(ChatFormatting.RED));
            }
            this.f_193912_.m_6305_(p_193923_, p_193929_, p_193930_, p_193932_);
        }

        @Override
        public List<? extends GuiEventListener> m_6702_() {
            return ImmutableList.of((Object)this.f_193912_, (Object)this.f_193913_);
        }

        @Override
        public List<? extends NarratableEntry> m_142437_() {
            return ImmutableList.of((Object)this.f_193912_, (Object)this.f_193913_);
        }

        @Override
        public boolean m_6375_(double p_193919_, double p_193920_, int p_193921_) {
            if (this.f_193912_.m_6375_(p_193919_, p_193920_, p_193921_)) {
                return true;
            }
            return this.f_193913_.m_6375_(p_193919_, p_193920_, p_193921_);
        }

        @Override
        public boolean m_6348_(double p_193941_, double p_193942_, int p_193943_) {
            return this.f_193912_.m_6348_(p_193941_, p_193942_, p_193943_) || this.f_193913_.m_6348_(p_193941_, p_193942_, p_193943_);
        }
    }

    public static abstract class Entry
    extends ContainerObjectSelectionList.Entry<Entry> {
    }
}

