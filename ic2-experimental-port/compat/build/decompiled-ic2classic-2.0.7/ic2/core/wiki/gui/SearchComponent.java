/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Quaternion
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import ic2.core.inventory.gui.IC2Font;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.wiki.base.book.BuildWiki;
import ic2.core.wiki.gui.BookMarkComponent;
import ic2.core.wiki.gui.SharedWikiInfo;
import ic2.core.wiki.helper.PageVisit;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SearchComponent
extends GuiWidget {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/wiki/book_search.png");
    public Box2i NEW_MARK = new Box2i(25, 186, 10, 29);
    public Box2i CONTROLS = new Box2i(40, 186, 10, 29);
    BuildWiki wiki;
    SharedWikiInfo info;
    List<PageVisit> foundElements = CollectionUtils.createList();

    public SearchComponent(BuildWiki wiki, SharedWikiInfo info) {
        super(Box2i.EMPTY_BOX);
        this.wiki = wiki;
        this.info = info;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        EditBox text = gui.addRenderableWidget(-1, new ImprovedTextWidget(new IC2Font(gui.getFont()), 11 + gui.getGuiLeft(), 23 + gui.getGuiTop(), 108, 12));
        text.m_93250_(950);
        text.m_94199_(128);
        text.m_94182_(false);
        text.m_94190_(true);
        text.m_94144_(this.wiki.getSearchKey());
        text.m_94202_(-12829907);
        text.m_94205_(-12829907);
        text.m_94151_(T -> {
            this.foundElements.clear();
            this.foundElements.addAll(this.wiki.getPages((String)T));
            this.wiki.setSearchKey((String)T);
        });
        text.f_93624_ = false;
        text.f_93623_ = false;
        this.foundElements.clear();
        this.foundElements.addAll(this.wiki.getPages(this.wiki.getSearchKey()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        AbstractWidget widget = gui.getButton(-1);
        widget.f_93624_ = this.info.isSearching();
        widget.f_93623_ = this.info.isSearching();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        int currentY = this.gui.getYSize() + this.gui.getGuiTop() - 20;
        this.gui.bindDefaultTexture();
        matrix.m_85836_();
        matrix.m_85837_((double)x, (double)currentY, 0.0);
        matrix.m_85845_(new Quaternion(0.0f, 0.0f, -90.0f, true));
        matrix.m_85837_((double)(-x), (double)(-currentY), 0.0);
        boolean extend = this.CONTROLS.isInBox(mouseX, mouseY);
        if (this.wiki.getControls() != null) {
            this.gui.drawTextureRegion(matrix, x - 34 - (extend ? 8 : 0), currentY + 40, 0.0f, 238.0f, 20 + (extend ? 8 : 0), 11.0f, -4827644);
            this.gui.bindTexture(BookMarkComponent.TEXTURE);
            matrix.m_85849_();
            this.gui.drawTextureRegion(matrix, (float)x + 42.5f, currentY + 15 + (extend ? 8 : 0), 124.01f, 57.0f, 4.0f, 10.0f, 6.0f, 15.0f);
            matrix.m_85836_();
            matrix.m_85837_((double)x, (double)currentY, 0.0);
            matrix.m_85845_(new Quaternion(0.0f, 0.0f, -90.0f, true));
            matrix.m_85837_((double)(-x), (double)(-currentY), 0.0);
            this.gui.bindDefaultTexture();
        }
        this.gui.drawTextureRegion(matrix, x - 34 - ((extend = this.NEW_MARK.isInBox(mouseX, mouseY)) ? 8 : 0), currentY + 25, 0.0f, 238.0f, 20 + (extend ? 8 : 0), 11.0f, -16664603);
        this.gui.bindTexture(BookMarkComponent.TEXTURE);
        this.gui.drawTextureRegion(matrix, x - 25 - (extend ? 8 : 0), currentY + 26, 124.0f, 30.0f, 10.0f, 10.0f, 12.0f, 12.0f);
        matrix.m_85849_();
        if (!this.info.isSearching()) {
            return;
        }
        this.gui.bindDefaultTexture();
        this.gui.drawTextureRegion(matrix, x + 6, y + 6, 6.0f, 6.0f, 122.0f, 180.0f);
        this.gui.drawTextureRegion(matrix, x + 9, y + 35, 0.0f, 248.0f, 116.0f, 4.0f);
        this.gui.drawTextureRegion(matrix, x + 9, y + 180, 0.0f, 252.0f, 116.0f, 4.0f);
        this.gui.bindTexture(BookMarkComponent.TEXTURE);
        this.gui.drawTextureRegion(matrix, x + 10, y + 20, 0.0f, 94.0f, 114.0f, 12.0f, 74.0f, 11.0f);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (!this.info.isSearching()) {
            return;
        }
        this.gui.drawString(matrix, (Component)this.string("Search:"), 10, 10, -12829907);
        for (int i = 0; i < this.foundElements.size() && i < 14; ++i) {
            this.gui.drawString(matrix, this.foundElements.get(i).getSearch(this.gui.getFont()), 10, 40 + i * 10, -12829907);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.NEW_MARK.isInBox(mouseX, mouseY)) {
            this.info.setSearching(!this.info.isSearching());
            return true;
        }
        if (this.wiki.getControls() != null && this.CONTROLS.isInBox(mouseX, mouseY)) {
            this.wiki.openPage(this.wiki.getControls(), true);
            return true;
        }
        if (!this.info.isSearching()) {
            return false;
        }
        if (mouseX < 10 && mouseX > 118) {
            return false;
        }
        int index = (mouseY - 40) / 10;
        if (index < 0 || index >= this.foundElements.size()) {
            return false;
        }
        if (this.wiki.openPage(this.foundElements.get(index), true)) {
            this.info.setSearching(false);
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        if (!this.info.isSearching()) {
            return false;
        }
        EditBox text = this.gui.getCastedButton(-1, EditBox.class);
        return text != null && text.m_93696_() && keyCode == 69;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (!this.info.isSearching()) {
            return;
        }
        if (mouseX < 10 && mouseX > 118) {
            return;
        }
        int index = (mouseY - 40) / 10;
        if (index < 0 || index >= this.foundElements.size()) {
            return;
        }
        Style style = IC2Screen.getComponentNew((List<FormattedCharSequence>)ObjectLists.singleton((Object)this.foundElements.get(index).getSearch(this.gui.getFont()).m_7532_()), mouseX - 10, mouseY - (40 + index * 10));
        if (style == null) {
            return;
        }
        HoverEvent event = style.m_131186_();
        if (event == null) {
            return;
        }
        Component comp = (Component)event.m_130823_(HoverEvent.Action.f_130831_);
        if (comp != null) {
            tooltips.accept(comp);
        }
    }
}

