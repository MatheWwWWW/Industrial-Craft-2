/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.base.managers;

import ic2.core.wiki.helper.PageVisit;
import net.minecraft.network.chat.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IWikiProvider {
    @OnlyIn(value=Dist.CLIENT)
    public boolean handleClickEvent(ClickEvent var1);

    @OnlyIn(value=Dist.CLIENT)
    public boolean openPage(PageVisit var1, boolean var2);
}

