/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.wiki.gui;

import ic2.core.utils.collection.FlagHolder;

public class SharedWikiInfo
extends FlagHolder {
    public static final int FLAG_BOOK_MARKING = 1;
    public static final int FLAG_SEARCHING = 2;

    public void setBookMarking(boolean value) {
        this.setFlag(1, value);
    }

    public boolean isBookmarking() {
        return this.isFlagSet(1);
    }

    public boolean hasOverlayOpen() {
        return this.isAnyFlagSet(3);
    }

    public void setSearching(boolean value) {
        this.setFlag(2, value);
    }

    public boolean isSearching() {
        return this.isFlagSet(2);
    }
}

