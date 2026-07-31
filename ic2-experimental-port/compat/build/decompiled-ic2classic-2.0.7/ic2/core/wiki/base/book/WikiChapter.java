/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.wiki.base.book;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.wiki.base.book.WikiPage;
import ic2.core.wiki.helper.PageVisit;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class WikiChapter {
    ResourceLocation id;
    WikiChapter prev;
    WikiChapter next;
    List<WikiPage> pages = CollectionUtils.createList();
    boolean isSearchable = true;

    public WikiChapter(ResourceLocation id) {
        this.id = id;
    }

    public void addPage(WikiPage page) {
        this.pages.add(page);
        page.setOwner(this);
    }

    public void setNext(WikiChapter next) {
        if (this.next != null) {
            next.prev = null;
        }
        this.next = next;
        if (next != null) {
            next.prev = this;
        }
    }

    public void setSearchable(boolean value) {
        this.isSearchable = value;
    }

    public WikiChapter getPrev() {
        return this.prev;
    }

    public WikiChapter getNext() {
        return this.next;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public int size() {
        return this.pages.size();
    }

    public WikiPage getPage(int index) {
        return index < 0 || index >= this.pages.size() ? null : this.pages.get(index);
    }

    public Iterable<WikiChapter> iteratePages() {
        WikiChapter chapter = this;
        while (chapter.getPrev() != null) {
            chapter = chapter.getPrev();
        }
        final WikiChapter finalChapter = chapter;
        return IterableWrapper.wrap(new Iterator<WikiChapter>(){
            WikiChapter entry;
            {
                this.entry = finalChapter;
            }

            @Override
            public boolean hasNext() {
                return this.entry != null;
            }

            @Override
            public WikiChapter next() {
                WikiChapter value = this.entry;
                this.entry = this.entry.getNext();
                return value;
            }
        });
    }

    public Component getHeader(int basePage) {
        while (basePage >= 0) {
            Component comp;
            WikiPage page = this.getPage(basePage);
            if (page != null && (comp = page.getHeader()) != null) {
                return comp;
            }
            --basePage;
        }
        return Component.m_237113_((String)"Unknown Page");
    }

    public void buildSearchTree(BiConsumer<PageVisit, String> result, Map<ResourceLocation, WikiChapter> chapter) {
        if (!this.isSearchable) {
            return;
        }
        int m = this.pages.size();
        for (int i = 0; i < m; ++i) {
            String s = this.pages.get(i).getSearchString(chapter);
            if (s.trim().isEmpty()) continue;
            result.accept(new PageVisit(this, i), s.toLowerCase(Locale.ROOT));
        }
    }
}

