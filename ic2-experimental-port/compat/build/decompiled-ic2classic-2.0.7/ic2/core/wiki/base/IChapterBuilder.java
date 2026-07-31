/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.wiki.base;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.components.builders.IWikiObj;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public interface IChapterBuilder {
    default public void startBuildChapter(String id, String name) {
        this.startBuildChapter(new ResourceLocation(id, name));
    }

    public void startBuildChapter(ResourceLocation var1);

    default public void addSubChapter(String id, String name) {
        this.addSubChapter(new ResourceLocation(id, name));
    }

    public void addSubChapter(ResourceLocation var1);

    default public void addSimplePage(IWikiObj ... objects) {
        this.addSimplePage((List<IWikiObj>)CollectionUtils.asList(objects));
    }

    public void addSimplePage(List<IWikiObj> var1);

    public void addSubPages(ItemLike ... var1);

    public void addSubPages(ResourceLocation ... var1);

    public void addPageRedirector(ResourceLocation var1, ItemLike ... var2);

    public void addPageRedirector(ResourceLocation var1, ResourceLocation ... var2);

    default public void addPreviewBookMark(String mod, String name) {
        this.addPreviewBookMark(new ResourceLocation(mod, name));
    }

    public void addPreviewBookMark(ResourceLocation var1);

    public void finishBuildChapter(boolean var1);

    public void finishSubChapter();
}

