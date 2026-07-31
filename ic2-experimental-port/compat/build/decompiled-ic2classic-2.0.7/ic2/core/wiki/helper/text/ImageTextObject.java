/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 */
package ic2.core.wiki.helper.text;

import ic2.core.wiki.components.ImageComponent;
import ic2.core.wiki.helper.text.ITextObject;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ImageTextObject
implements ITextObject {
    ResourceLocation texture;
    float offset = 0.0f;
    float width = 118.0f;
    float height;

    public ImageTextObject(String id, float height) {
        this(new ResourceLocation(id), height);
    }

    public ImageTextObject(String modId, String path, float height) {
        this(new ResourceLocation(modId, path), height);
    }

    public ImageTextObject(ResourceLocation texture, float height) {
        this.texture = texture;
        this.height = height;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public ImageComponent createComponent() {
        return new ImageComponent(this.texture, this.width, this.height, this.offset);
    }

    @Override
    public List<FormattedCharSequence> getText() {
        return ObjectLists.emptyList();
    }
}

