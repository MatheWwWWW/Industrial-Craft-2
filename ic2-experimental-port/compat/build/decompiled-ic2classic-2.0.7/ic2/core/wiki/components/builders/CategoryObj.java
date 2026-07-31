/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.wiki.components.builders;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.CategoryComponent;
import ic2.core.wiki.components.builders.IWikiObj;
import java.util.Collection;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class CategoryObj
implements IWikiObj {
    Link headerLink;
    Component header;
    List<Link> links = CollectionUtils.createList();

    public CategoryObj(String s, Link ... links) {
        this(Component.m_237115_((String)s), links);
    }

    public CategoryObj(String s, Object ... args) {
        this(Component.m_237110_((String)s, (Object[])args), new Link[0]);
    }

    public CategoryObj(MutableComponent header, Link ... links) {
        this.header = header.m_130940_(ChatFormatting.BOLD);
        this.addLinks(links);
    }

    public CategoryObj addLinks(Link ... links) {
        this.links.addAll((Collection<Link>)CollectionUtils.asList(links));
        return this;
    }

    public CategoryObj addHeaderLink(Link headerLink) {
        this.headerLink = headerLink;
        return this;
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        builder.addComponent(new CategoryComponent(this.header, this.headerLink, this.links));
    }

    public static class Link {
        ResourceLocation link;
        ItemStack item;
        Component tooltip;

        public Link(ItemLike stack, String id) {
            this(new ItemStack(stack), new ResourceLocation(id));
        }

        public Link(ItemLike stack, String mod, String id) {
            this(new ItemStack(stack), new ResourceLocation(mod, id));
        }

        public Link(ItemLike stack, ResourceLocation location) {
            this(new ItemStack(stack), location);
        }

        public Link(ItemStack stack, String id) {
            this(stack, new ResourceLocation(id));
        }

        public Link(ItemStack stack, String mod, String id) {
            this(stack, new ResourceLocation(mod, id));
        }

        public Link(ItemStack stack, ResourceLocation location) {
            this.item = stack;
            this.link = location;
        }

        public Link with(String s) {
            return this.with((Component)Component.m_237115_((String)s));
        }

        public Link with(String s, Object ... obj) {
            return this.with((Component)Component.m_237115_((String)s));
        }

        public Link with(Component tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public ResourceLocation getLink() {
            return this.link;
        }

        public ItemStack getItem() {
            return this.item;
        }

        public Component getTooltip() {
            return this.tooltip;
        }
    }
}

