/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.FormattedCharSequence
 */
package ic2.core.wiki.helper.text;

import ic2.core.wiki.helper.text.ITextObject;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class HeaderTextObject
implements ITextObject {
    Component header;
    boolean applyInstantly;

    public HeaderTextObject(Component header, boolean applyInstantly) {
        this.header = header;
        this.applyInstantly = applyInstantly;
    }

    public boolean shouldApplyInstantly() {
        return this.applyInstantly;
    }

    public Component getHeader() {
        return this.header;
    }

    @Override
    public List<FormattedCharSequence> getText() {
        return ObjectLists.emptyList();
    }
}

