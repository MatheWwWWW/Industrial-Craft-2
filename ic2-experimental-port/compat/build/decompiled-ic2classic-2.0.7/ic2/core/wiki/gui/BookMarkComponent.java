/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Quaternion
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.wiki.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import ic2.core.inventory.gui.IC2Font;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.MathUtils;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.wiki.base.book.BuildWiki;
import ic2.core.wiki.gui.SharedWikiInfo;
import ic2.core.wiki.helper.BookMark;
import ic2.core.wiki.helper.PageVisit;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class BookMarkComponent
extends GuiWidget {
    public static final int[] COLOR_SELECTION = new int[]{-1, -16777216, -16087809, -13577896, -10593050, -24822, -51366, -4236558, -47814, -10169601, -10742};
    public static final Box2i NEW_MARK = new Box2i(10, 186, 10, 29);
    public static final Predicate<BookMark> DELETER = BookMark::isPermanent;
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/wiki/book_components.png");
    public static final int TEXT_COLOR = -12829907;
    List<BookMark> bookmarks = CollectionUtils.createList();
    SharedWikiInfo info;
    BuildWiki wiki;

    public BookMarkComponent(BuildWiki wiki, SharedWikiInfo info) {
        super(Box2i.EMPTY_BOX);
        this.wiki = wiki;
        this.info = info;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        if (this.wiki == null) {
            return;
        }
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        EditBox text = gui.addRenderableWidget(0, new ImprovedTextWidget(new IC2Font(gui.getFont()), 100 + gui.getGuiLeft(), 51 + gui.getGuiTop(), 90, 20));
        text.m_93250_(950);
        text.m_94199_(13);
        text.m_94182_(false);
        text.m_94190_(true);
        text.m_94202_(-12829907);
        text.m_94205_(-12829907);
        text = gui.addRenderableWidget(1, new ImprovedTextWidget(new IC2Font(gui.getFont()), 102 + gui.getGuiLeft(), 66 + gui.getGuiTop(), 90, 20));
        text.m_93250_(950);
        text.m_94153_(MathUtils::isHexNumber);
        text.m_94199_(6);
        text.m_94182_(false);
        text.m_94190_(true);
        text.m_94202_(-12829907);
        text.m_94205_(-12829907);
        gui.addRenderableWidget(2, new ExtendedButton(77 + gui.getGuiLeft(), 95 + gui.getGuiTop(), 50, 15, (Component)this.translate("tooltip.ic2.wiki.bookmark.confirm"), T -> {
            BookMark mark = new BookMark(new PageVisit(this.wiki.getCurrentChapter(), this.wiki.getPage()), BookMarkComponent.color(gui.getCastedButton(1, EditBox.class).m_94155_()), (Component)this.string(gui.getCastedButton(0, EditBox.class).m_94155_()));
            this.bookmarks.add(mark);
            this.toggleMark(gui, false);
            this.onMarksChanged();
        }));
        gui.addRenderableWidget(3, new ExtendedButton(130 + gui.getGuiLeft(), 95 + gui.getGuiTop(), 50, 15, (Component)this.translate("tooltip.ic2.wiki.bookmark.cancel"), T -> this.toggleMark(gui, false)));
        this.toggleMark(gui, false);
        this.bookmarks.clear();
        this.bookmarks.add(new BookMark(this.wiki.getMainPage(), ColorUtils.YELLOW, (Component)this.translate("tooltip.ic2.wiki.bookmark.main")).setPermanent());
        this.bookmarks.addAll(this.wiki.getBookMarks());
        while (this.bookmarks.size() > 14) {
            this.bookmarks.remove(this.bookmarks.size() - 1);
        }
    }

    public void onMarksChanged() {
        ObjectArrayList marks = new ObjectArrayList(this.bookmarks);
        marks.removeIf(DELETER);
        this.wiki.updateLocations((List<BookMark>)marks);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int i;
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop() + 10;
        RenderSystem.m_69465_();
        int m = this.bookmarks.size();
        for (i = 0; i < m; ++i) {
            int width;
            BookMark mark = this.bookmarks.get(i);
            if (i > 0 && this.bookmarks.get(i - 1).isPermanent() && !mark.isPermanent()) {
                y += 6;
            }
            if (this.gui.isOverArea(mouseX, mouseY, -(width = this.gui.getFont().m_92724_(mark.getName().m_7532_())) - 5, y - this.gui.getGuiTop(), width + 10, 12)) {
                this.gui.drawTextureRegion(matrix, x - 4 - width, y, 0.0f, 238.0f, width + 10, 10.0f, mark.getColor());
                this.gui.drawString(matrix, mark.getName(), x - width + 5, y + 1, ColorUtils.needsDarkColor(mark.getColor()) ? -12829907 : -1);
                this.gui.bindDefaultTexture();
            } else {
                this.gui.drawTextureRegion(matrix, x - 8, y, 0.0f, 238.0f, 14.0f, 10.0f, mark.getColor());
            }
            y += 12;
        }
        if (this.bookmarks.size() < 14) {
            int currentY = this.gui.getYSize() + this.gui.getGuiTop() - 20;
            this.gui.bindDefaultTexture();
            matrix.m_85836_();
            matrix.m_85837_((double)x, (double)currentY, 0.0);
            matrix.m_85845_(new Quaternion(0.0f, 0.0f, -90.0f, true));
            matrix.m_85837_((double)(-x), (double)(-currentY), 0.0);
            boolean extend = NEW_MARK.isInBox(mouseX, mouseY);
            this.gui.drawTextureRegion(matrix, x - 34 - (extend ? 8 : 0), currentY + 10, 0.0f, 238.0f, 20 + (extend ? 8 : 0), 11.0f, -16737997);
            this.gui.drawString(matrix, (Component)this.string("+"), x - 23 - (extend ? 8 : 0), currentY + 11, -12829907);
            matrix.m_85849_();
        }
        if (this.info.isBookmarking()) {
            matrix.m_85836_();
            matrix.m_85837_(0.0, 0.0, 1000.0);
            x = this.gui.getGuiLeft();
            y = this.gui.getGuiTop();
            this.gui.bindTexture(TEXTURE);
            this.gui.drawTextureRegion(matrix, x + 65, y + 30, 0.0f, 0.0f, 124.0f, 93.0f);
            this.gui.drawTextureRegion(matrix, x + 98, y + 49, 0.0f, 94.0f, 81.0f, 12.0f, 74.0f, 11.0f);
            this.gui.drawTextureRegion(matrix, x + 100, y + 64, 0.0f, 94.0f, 40.0f, 12.0f, 74.0f, 11.0f);
            for (i = 0; i < COLOR_SELECTION.length; ++i) {
                int posX = x + 72 + 10 * i;
                int posY = y + 80;
                this.gui.drawTextureRegion(matrix, posX, posY, 76.0f, 95.0f, 9.0f, 9.0f);
                this.gui.drawColoredRegion(matrix, posX + 1, posY + 1, 7.0f, 7.0f, COLOR_SELECTION[i]);
            }
            this.gui.drawTextureRegion(matrix, x + 145, y + 63, 76.0f, 95.0f, 13.0f, 13.0f, 9.0f, 9.0f);
            this.gui.drawColoredRegion(matrix, x + 146, y + 64, 11.0f, 11.0f, BookMarkComponent.color(this.gui.getCastedButton(1, EditBox.class).m_94155_()));
            this.gui.drawCenterString(matrix, (Component)this.translate("tooltip.ic2.wiki.bookmark.new"), x + 127, y + 35, -12829907);
            this.gui.drawString(matrix, (Component)this.translate("tooltip.ic2.wiki.bookmark.name"), x + 70, y + 50, -12829907);
            this.gui.drawString(matrix, (Component)this.translate("tooltip.ic2.wiki.bookmark.color"), x + 70, y + 65, -12829907);
            matrix.m_85849_();
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.info.isBookmarking()) {
            for (int i = 0; i < COLOR_SELECTION.length; ++i) {
                int posX = 72 + 10 * i;
                if (mouseX < posX || mouseX > posX + 9 || mouseY < 80 || mouseY > 89) continue;
                this.gui.getCastedButton(1, EditBox.class).m_94144_(Integer.toHexString(0x1000000 | COLOR_SELECTION[i] & 0xFFFFFF).substring(1).toUpperCase(Locale.ROOT));
                return true;
            }
        } else {
            if (NEW_MARK.isInBox(mouseX, mouseY) && this.bookmarks.size() < 14) {
                this.toggleMark(this.gui, !this.info.hasOverlayOpen());
                if (this.info.hasOverlayOpen()) {
                    Component comp;
                    this.gui.getCastedButton(0, EditBox.class).m_94144_((comp = this.wiki.getCurrentHeader()) != null ? ChatFormatting.m_126649_((String)comp.getString()) : "");
                }
                return true;
            }
            boolean moved = false;
            for (int i = 0; i < this.bookmarks.size(); ++i) {
                BookMark mark = this.bookmarks.get(i);
                int width = this.gui.getFont().m_92724_(mark.getName().m_7532_());
                if (this.gui.isOverArea(mouseX, mouseY, 4 - width, 10 + i * 12 + (moved ? 6 : 0), width + 10, 12)) {
                    if (Screen.m_96638_()) {
                        if (mark.isPermanent()) break;
                        this.bookmarks.remove(i--);
                        this.onMarksChanged();
                    } else {
                        this.wiki.openPage(mark.getLocation(), true);
                    }
                    return true;
                }
                if (i <= 0 || !this.bookmarks.get(i - 1).isPermanent() || mark.isPermanent()) continue;
                moved = true;
            }
        }
        return false;
    }

    @Override
    public boolean onKeyTyped(int keyCode) {
        if (this.info.isBookmarking()) {
            if (keyCode == 69) {
                return true;
            }
            if (keyCode == 256) {
                this.toggleMark(this.gui, false);
                return true;
            }
        }
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void toggleMark(IC2Screen screen, boolean result) {
        this.info.setBookMarking(result);
        screen.getButton((int)0).f_93624_ = result;
        screen.getButton((int)1).f_93624_ = result;
        screen.getButton((int)2).f_93624_ = result;
        screen.getButton((int)3).f_93624_ = result;
    }

    public static int color(String s) {
        try {
            return Integer.decode("#" + s) | 0xFF000000;
        }
        catch (Exception e) {
            return -12829907;
        }
    }
}

