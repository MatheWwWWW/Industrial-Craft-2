/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.util.FormattedCharSequence
 */
package ic2.core.wiki.helper.text;

import ic2.core.wiki.helper.text.ITextObject;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.util.FormattedCharSequence;

public class NewPageObject
implements ITextObject {
    @Override
    public List<FormattedCharSequence> getText() {
        return ObjectLists.emptyList();
    }
}

