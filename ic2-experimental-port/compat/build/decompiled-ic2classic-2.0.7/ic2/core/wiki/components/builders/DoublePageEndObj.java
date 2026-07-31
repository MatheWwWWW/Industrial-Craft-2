/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.wiki.components.builders;

import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.builders.IWikiObj;

public class DoublePageEndObj
implements IWikiObj {
    @Override
    public void buildComponents(IPageBuilder builder) {
        builder.setEndOfDoublePage();
    }
}

