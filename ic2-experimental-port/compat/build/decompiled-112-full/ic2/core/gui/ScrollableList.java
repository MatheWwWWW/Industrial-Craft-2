/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.IStringSerializable
 *  org.lwjgl.opengl.GL11
 */
package ic2.core.gui;

import ic2.core.GuiIC2;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollDirection;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.IStringSerializable;
import org.lwjgl.opengl.GL11;

public class ScrollableList
extends GuiElement<ScrollableList> {
    protected int scroll = 0;
    protected boolean scrolling = false;
    protected int mouseScrollOffset = -1;
    protected final List<IListItem> items;
    private static final int SCROLL_BAR_WIDTH = 5;
    private static final int LIST_AREA_WIDTH = 7;
    private static final int ITEM_HEIGHT = 11;
    private static final int SCROLL_SPEED = 3;
    private static final boolean DEBUG_SCISSOR = false;

    public ScrollableList(GuiIC2<?> gui, int x, int y, int width, int height) {
        this(gui, x, y, width, height, new ArrayList<IListItem>());
    }

    public ScrollableList(GuiIC2<?> gui, int x, int y, int width, int height, List<IListItem> items) {
        super(gui, x, y, width, height);
        this.items = items;
    }

    public ScrollableList addItem(IListItem item) {
        assert (item != null);
        this.items.add(item);
        this.scroll = Util.limit(this.scroll, 0, this.getMaxScroll());
        return this;
    }

    public ScrollableList removeItem(IListItem item) {
        assert (item != null);
        this.items.remove(item);
        this.scroll = Util.limit(this.scroll, 0, this.getMaxScroll());
        return this;
    }

    public ScrollableList removeItem(int index) {
        assert (index >= 0 && index < this.items.size());
        this.items.remove(index);
        this.scroll = Util.limit(this.scroll, 0, this.getMaxScroll());
        return this;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        int scrollHeight;
        int scrollStart;
        ScrollableList.bindCommonTexture();
        this.gui.drawColoredRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2, -6250336);
        this.gui.drawColoredRect(this.x, this.y, this.width, 1, -16777216);
        this.gui.drawColoredRect(this.x, this.y + this.height - 1, this.width, 1, -16777216);
        this.gui.drawColoredRect(this.x, this.y, 1, this.height, -16777216);
        this.gui.drawColoredRect(this.x + this.width - 1, this.y, 1, this.height, -16777216);
        this.gui.drawColoredRect(this.x + this.width - 7, this.y, 1, this.height, -16777216);
        if (this.items.size() * 11 < this.height) {
            scrollStart = this.y + 1;
            scrollHeight = this.height - 2;
        } else {
            scrollHeight = Math.max(this.height - 2 - (this.items.size() * 11 - this.height), 1);
            scrollStart = (int)Util.lerp(this.y + 1, this.y + this.height - scrollHeight - 1, (float)this.scroll / (float)this.getMaxScroll());
        }
        this.gui.drawColoredRect(this.x + this.width - 7 + 1, scrollStart, 5, scrollHeight, -16777216);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
        int left = this.gui.getGuiLeft();
        int top = this.gui.getGuiTop();
        this.doScissor(left, top);
        int currentX = this.x - left;
        int currentY = -this.scroll + this.y + 2 - top;
        for (IListItem item : this.items) {
            this.gui.drawString(currentX + 3, currentY, item.func_176610_l(), 0xFFFFFF, false);
            this.gui.drawColoredRect(currentX, (currentY += 11) - 2, this.width - 7, 1, -16777216);
        }
        assert (GL11.glIsEnabled((int)3089));
        GL11.glDisable((int)3089);
    }

    private void doScissor(int GUIwidth, int GUIheight) {
        int left = GUIwidth + this.x + 1;
        int bottom = GUIheight + this.y + this.height - 1;
        int viewWidth = this.width - 7 - 1;
        int viewHeight = this.height - 2;
        int scale = new ScaledResolution(this.gui.field_146297_k).func_78325_e();
        GL11.glEnable((int)3089);
        GL11.glScissor((int)(left * scale), (int)(this.gui.field_146297_k.field_71440_d - bottom * scale), (int)(viewWidth * scale), (int)(viewHeight * scale));
    }

    @Override
    protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
        if (!this.items.isEmpty() && (mouseX -= this.x) > 0 && (mouseY -= this.y) > 0) {
            if (mouseX < this.width - 7) {
                int index = (mouseY += this.scroll % 11) / 11 + this.scroll / 11;
                if (index >= 0 && index < this.items.size()) {
                    this.items.get(index).onClick(button);
                }
            } else if (mouseX > this.width - 7 && mouseX < this.width - 1 && mouseY >= this.scroll && mouseY <= this.scroll + (this.items.size() * 11 < this.height ? this.height : this.height - 2 - (this.items.size() * 11 - this.height))) {
                this.mouseScrollOffset = mouseY;
                this.scrolling = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMouseDrag(int mouseX, int mouseY, MouseButton button, long timeFromLastClick, boolean onThis) {
        if (this.scrolling) {
            assert (this.mouseScrollOffset >= 0);
            mouseX -= this.x;
            int startingScroll = this.scroll;
            int mouseMovement = this.scroll + (mouseY -= this.y) - this.mouseScrollOffset;
            this.scroll = Util.limit(mouseMovement, 0, this.getMaxScroll());
            if (mouseMovement != this.scroll) {
                if (startingScroll != this.scroll) {
                    this.mouseScrollOffset += this.scroll - startingScroll;
                    assert (this.mouseScrollOffset >= 0) : "Left the scroll bar dragging from " + startingScroll + " to " + this.scroll;
                }
            } else {
                this.mouseScrollOffset = mouseY;
                assert (mouseY >= 0) : "Left the scroll bar dragging to " + mouseY;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseRelease(int mouseX, int mouseY, MouseButton button, boolean onThis) {
        if (this.scrolling) {
            this.scrolling = false;
            this.mouseScrollOffset = -1;
        }
        return false;
    }

    @Override
    public void onMouseScroll(int mouseX, int mouseY, ScrollDirection direction) {
        this.scroll = Util.limit(this.scroll + direction.multiplier * 3, 0, this.getMaxScroll());
    }

    protected int getMaxScroll() {
        return Math.max(this.items.size() * 11 - (this.height - 1), 0);
    }

    public static interface IListItem
    extends IStringSerializable,
    IClickHandler {
    }
}

