/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.platform.events.impl;

import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.base.managers.WikiBuilder;
import ic2.core.wiki.components.builders.IWikiObj;
import java.util.function.Consumer;
import net.minecraftforge.eventbus.api.Event;

public class WikiEvent
extends Event {

    public static class AddonPageEvent
    extends WikiEvent {
        Consumer<IWikiObj[]> wikiPages;

        public AddonPageEvent(Consumer<IWikiObj[]> wikiPages) {
            this.wikiPages = wikiPages;
        }

        public void registerChapters(IWikiObj ... pageInfo) {
            this.wikiPages.accept(pageInfo);
        }
    }

    public static class AddonSetupEvent
    extends WikiEvent {
        boolean hasAddons = false;

        public void enableAddons() {
            this.hasAddons = true;
        }

        public boolean areAddonsEnabled() {
            return this.hasAddons;
        }
    }

    public static class TableOfContentsSetupEvent
    extends WikiEvent {
        public final IChapterBuilder chapterBuilder;

        public TableOfContentsSetupEvent(IChapterBuilder chapterBuilder) {
            this.chapterBuilder = chapterBuilder;
        }
    }

    public static class WikiSetupEvent
    extends WikiEvent {
        public final WikiBuilder builder;

        public WikiSetupEvent(WikiBuilder builder) {
            this.builder = builder;
        }
    }
}

