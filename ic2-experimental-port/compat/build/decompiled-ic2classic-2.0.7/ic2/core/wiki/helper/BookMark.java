/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.wiki.helper;

import ic2.core.wiki.base.book.WikiChapter;
import ic2.core.wiki.helper.PageVisit;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BookMark {
    PageVisit location;
    int color;
    Component name;
    boolean permanent = false;

    public BookMark(PageVisit location, int color, Component name) {
        this.location = location;
        this.color = color;
        this.name = name;
    }

    public boolean isPermanent() {
        return this.permanent;
    }

    public int getColor() {
        return this.color;
    }

    public PageVisit getLocation() {
        return this.location;
    }

    public Component getName() {
        return this.name;
    }

    public BookMark setPermanent() {
        this.permanent = true;
        return this;
    }

    public CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        nbt.m_128359_("page", this.location.getChapter().getId().toString());
        nbt.m_128405_("pageIndex", this.location.getPage());
        nbt.m_128405_("color", this.color);
        nbt.m_128359_("name", Component.Serializer.m_130703_((Component)this.name));
        return nbt;
    }

    public static BookMark read(CompoundTag nbt, Map<ResourceLocation, WikiChapter> registry) {
        WikiChapter chapter = registry.get(new ResourceLocation(nbt.m_128461_("page")));
        return chapter == null ? null : new BookMark(new PageVisit(chapter, nbt.m_128451_("pageIndex")), nbt.m_128451_("color"), (Component)Component.Serializer.m_130701_((String)nbt.m_128461_("name")));
    }
}

