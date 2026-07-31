/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.wiki.helper;

import ic2.core.wiki.components.IWikiComponent;
import net.minecraft.resources.ResourceLocation;

public class ProgressBar {
    ResourceLocation texture = IWikiComponent.TEXTURE;
    int activeX;
    int activeY;
    int inActiveX;
    int inActiveY;
    int width;
    int height;

    public ProgressBar(int inActiveX, int inActiveY, int width, int height) {
        this(inActiveX, inActiveY, inActiveX, inActiveY, width, height);
    }

    public ProgressBar(int activeX, int activeY, int inActiveX, int inActiveY, int width, int height) {
        this.activeX = activeX;
        this.activeY = activeY;
        this.inActiveX = inActiveX;
        this.inActiveY = inActiveY;
        this.width = width;
        this.height = height;
    }

    public ProgressBar withTexture(String mod, String name) {
        return this.withTexture(new ResourceLocation(mod, name));
    }

    public ProgressBar withTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public int getMinX(boolean active) {
        return active ? this.activeX : this.inActiveX;
    }

    public int getMinY(boolean active) {
        return active ? this.activeY : this.inActiveY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

