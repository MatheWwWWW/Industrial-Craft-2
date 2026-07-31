/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.wiki.components.builders;

import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.builders.IWikiObj;

public class NoSearchObj
implements IWikiObj {
    boolean page;

    public NoSearchObj(boolean page) {
        this.page = page;
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        builder.disableSearch(this.page);
    }
}

