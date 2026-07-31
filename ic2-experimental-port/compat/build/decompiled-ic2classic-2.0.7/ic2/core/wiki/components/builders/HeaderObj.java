/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 */
package ic2.core.wiki.components.builders;

import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.HeaderComponent;
import ic2.core.wiki.components.builders.IWikiObj;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class HeaderObj
implements IWikiObj {
    Component text;

    public HeaderObj(String text) {
        this((Component)Component.m_237115_((String)text).m_130940_(ChatFormatting.BOLD));
    }

    public HeaderObj(String text, ChatFormatting ... format) {
        this((Component)Component.m_237115_((String)text).m_130944_(format));
    }

    public HeaderObj(Component text) {
        this.text = text;
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        builder.addComponent(new HeaderComponent(this.text));
    }

    @Override
    public void provideLang(Consumer<Component> comp) {
        comp.accept(this.text);
    }
}

