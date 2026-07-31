/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.wiki.components.builders;

import ic2.core.wiki.base.IPageBuilder;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;

public interface IWikiObj {
    public void buildComponents(IPageBuilder var1);

    default public void provideLang(Consumer<Component> comp) {
    }
}

