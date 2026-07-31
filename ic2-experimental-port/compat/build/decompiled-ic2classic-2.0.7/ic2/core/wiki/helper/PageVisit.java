/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.Font
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.helper;

import ic2.core.wiki.base.book.WikiChapter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class PageVisit {
    final WikiChapter chapter;
    final int page;
    Component cache = null;

    public PageVisit(WikiChapter chapter, int page) {
        this.chapter = chapter;
        this.page = page;
    }

    public WikiChapter getChapter() {
        return this.chapter;
    }

    public int getPage() {
        return this.page;
    }

    public boolean isValid() {
        return this.chapter != null && this.page >= 0 && this.page < this.chapter.size();
    }

    @OnlyIn(value=Dist.CLIENT)
    public Component getSearch(Font font) {
        if (this.cache != null) {
            return this.cache;
        }
        MutableComponent comp = this.getHeader().m_6881_().m_130940_(ChatFormatting.BOLD);
        int strWidth = font.m_92852_((FormattedText)comp);
        int ellipsisWidth = font.m_92895_("...");
        if (strWidth > 112 && strWidth > ellipsisWidth) {
            comp = Component.m_237113_((String)(font.m_92854_((FormattedText)comp, 112 - ellipsisWidth).getString() + "..."));
        }
        this.cache = comp.m_130948_(Style.f_131099_.m_131136_(Boolean.valueOf(true)).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, (Object)Component.m_237113_((String)"Open: ").m_7220_((Component)this.getHeader().m_6881_().m_6270_(Style.f_131099_.m_131136_(Boolean.valueOf(false)))))));
        return this.cache;
    }

    public Component getHeader() {
        return this.chapter.getHeader(this.page);
    }
}

