/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.wiki.components.builders;

import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.ImageComponent;
import ic2.core.wiki.components.builders.IWikiObj;
import net.minecraft.resources.ResourceLocation;

public class ImageObj
implements IWikiObj {
    ResourceLocation texture;
    float offset = 0.0f;
    float width = 118.0f;
    float height;

    public ImageObj(String mod, String name, float height) {
        this(new ResourceLocation(mod, name), height);
    }

    public ImageObj(String id, float height) {
        this(new ResourceLocation(id), height);
    }

    public ImageObj(ResourceLocation texture, float height) {
        this.texture = texture;
        this.height = height;
    }

    public ImageObj withWidth(float width) {
        this.width = width;
        return this;
    }

    public ImageObj withOffset(float offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        builder.addComponent(new ImageComponent(this.texture, this.width, this.height, this.offset));
    }
}

