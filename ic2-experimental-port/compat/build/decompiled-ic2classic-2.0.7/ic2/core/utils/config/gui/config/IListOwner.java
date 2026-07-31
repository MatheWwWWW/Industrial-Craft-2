/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.config;

import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.config.Element;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

public interface IListOwner {
    public void addTooltips(Component var1);

    public boolean isInsideList(double var1, double var3);

    public BackgroundTexture getCustomTexture();

    public boolean isActiveWidget(AbstractWidget var1);

    public void setActiveWidget(AbstractWidget var1);

    public void removeEntry(Element var1);
}

