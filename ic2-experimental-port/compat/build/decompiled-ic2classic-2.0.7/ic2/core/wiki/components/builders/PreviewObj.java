/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.wiki.components.builders;

import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.builders.IWikiObj;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class PreviewObj
implements IWikiObj {
    ItemLike[] array;

    public PreviewObj(ItemLike ... array) {
        this.array = array;
    }

    public PreviewObj(ItemStack ... array) {
        this.array = new ItemLike[array.length];
        int m = array.length;
        for (int i = 0; i < m; ++i) {
            this.array[i] = array[i].m_41720_();
        }
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        for (int i = 0; i < this.array.length; ++i) {
            builder.markPreview(this.array[i]);
        }
    }
}

