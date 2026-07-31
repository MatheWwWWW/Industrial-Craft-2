/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.gui.config;

import ic2.core.utils.config.gui.config.Element;

public interface IIgnoreSearch {
    public boolean shouldIgnoreSearch();

    public static boolean shouldIgnoreSearch(Element element) {
        if (element instanceof IIgnoreSearch) {
            return ((IIgnoreSearch)((Object)element)).shouldIgnoreSearch();
        }
        return false;
    }
}

