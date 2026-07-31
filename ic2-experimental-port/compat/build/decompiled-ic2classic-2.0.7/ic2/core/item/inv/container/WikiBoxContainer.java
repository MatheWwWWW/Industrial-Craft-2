/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.container;

import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.item.inv.inventory.WikiInventory;
import ic2.core.wiki.base.book.BuildWiki;
import ic2.core.wiki.gui.BookMarkComponent;
import ic2.core.wiki.gui.PageComponent;
import ic2.core.wiki.gui.SearchComponent;
import ic2.core.wiki.gui.SharedWikiInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WikiBoxContainer
extends ItemContainer<WikiInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/wiki/book.png");

    public WikiBoxContainer(WikiInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        this.addHiddenPlayerInventory(player.m_150109_());
        SharedWikiInfo info = new SharedWikiInfo();
        BuildWiki wiki = key.getWiki();
        this.addComponent(new SearchComponent(wiki, info));
        this.addComponent(new BookMarkComponent(wiki, info));
        this.addComponent(new PageComponent(wiki, info));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        super.onGuiLoaded(screen);
        screen.setFlag(16);
        screen.modifySize(80, 26);
        screen.clearFlag(3);
    }
}

