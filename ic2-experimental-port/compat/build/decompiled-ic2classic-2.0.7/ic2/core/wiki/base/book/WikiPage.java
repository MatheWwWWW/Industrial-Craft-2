/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.wiki.base.book;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.base.book.WikiChapter;
import ic2.core.wiki.components.HeaderComponent;
import ic2.core.wiki.components.IWikiComponent;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class WikiPage {
    WikiChapter owner;
    ResourceLocation link;
    List<IWikiComponent> components = CollectionUtils.createList();
    boolean isSearchable = true;

    public void addComponent(IWikiComponent component) {
        this.components.add(component);
    }

    public WikiPage setOwner(WikiChapter owner) {
        this.owner = owner;
        return this;
    }

    public void setSearchable(boolean value) {
        this.isSearchable = value;
    }

    public WikiPage setLink(ResourceLocation link) {
        this.link = link;
        return this;
    }

    public ResourceLocation getLink() {
        return this.link;
    }

    public List<IWikiComponent> getComponents() {
        return this.components;
    }

    public Component getHeader() {
        for (int i = 0; i < this.components.size(); ++i) {
            if (!(this.components.get(i) instanceof HeaderComponent)) continue;
            return ((HeaderComponent)this.components.get(i)).getHeader();
        }
        return null;
    }

    public String getSearchString(Map<ResourceLocation, WikiChapter> provider) {
        WikiChapter chapter;
        if (!this.isSearchable) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int m = this.components.size();
        for (int i = 0; i < m; ++i) {
            this.components.get(i).addSearchString(T -> builder.append(T.getString()));
        }
        if (this.link != null && (chapter = provider.get(this.link)) != null) {
            chapter.buildSearchTree((P, S) -> builder.append((String)S), provider);
        }
        return builder.toString();
    }
}

