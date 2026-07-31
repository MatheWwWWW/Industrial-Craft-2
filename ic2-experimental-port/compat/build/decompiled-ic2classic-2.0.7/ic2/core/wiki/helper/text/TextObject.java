/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.FormattedCharSequence
 */
package ic2.core.wiki.helper.text;

import ic2.core.wiki.helper.text.ITextObject;
import java.util.List;
import net.minecraft.util.FormattedCharSequence;

public class TextObject
implements ITextObject {
    List<FormattedCharSequence> lines;

    public TextObject(List<FormattedCharSequence> lines) {
        this.lines = lines;
    }

    @Override
    public List<FormattedCharSequence> getText() {
        return this.lines;
    }
}

