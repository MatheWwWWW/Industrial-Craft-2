/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.vertex.PoseStack
 *  org.lwjgl.opengl.GL11
 */
package ic2.core.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollDirection;
import ic2.core.proxy.SideProxyClient;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public abstract class AbstractScrollingList<T extends AbstractScrollingList<T, I>, I extends IListItem>
extends GuiElement<T> {
    protected float scroll = 0.0f;
    protected boolean scrolling = false;
    protected int mouseScrollOffset = -1;
    protected final List<I> items;
    private static final int SCROLL_BAR_WIDTH = 5;
    private static final int LIST_AREA_WIDTH = 7;
    private static final int ITEM_HEIGHT = 10;
    private static final int ITEM_GAP = 11;
    private static final int SCROLL_SPEED = 3;
    private static final boolean DEBUG_SCISSOR = false;

    public AbstractScrollingList(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4) {
        this(ic2Gui, n, n2, n3, n4, new ArrayList());
    }

    protected AbstractScrollingList(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, List<I> list) {
        super(ic2Gui, n, n2, n3, n4);
        this.items = list;
    }

    public T addItem(I i) {
        assert (i != null);
        this.items.add(i);
        this.scroll = Util.limit(this.scroll, 0.0f, (float)this.getMaxScroll());
        return (T)this;
    }

    public T addItem(int n, I i) {
        assert (i != null);
        this.items.add(n, i);
        this.scroll = Util.limit(this.scroll, 0.0f, (float)this.getMaxScroll());
        return (T)this;
    }

    public T removeItem(I i) {
        assert (i != null);
        this.items.remove(i);
        this.scroll = Util.limit(this.scroll, 0.0f, (float)this.getMaxScroll());
        return (T)this;
    }

    public T removeItem(int n) {
        assert (n >= 0 && n < this.items.size());
        this.items.remove(n);
        this.scroll = Util.limit(this.scroll, 0.0f, (float)this.getMaxScroll());
        return (T)this;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        int n3;
        int n4;
        AbstractScrollingList.bindCommonTexture();
        this.gui.drawColoredRect(poseStack, this.x, this.y, this.width, this.height, -16777216);
        this.gui.drawColoredRect(poseStack, this.x + 1, this.y + 1, this.width - 7 - 1, this.height - 2, -6250336);
        this.gui.drawColoredRect(poseStack, this.x + this.width - 7 + 1, this.y + 1, 5, this.height - 2, -6250241);
        if (this.items.size() * 11 - 1 <= this.height - 2) {
            n4 = this.y + 1;
            n3 = this.height - 2;
        } else {
            n3 = Math.max(this.height - 2 - (this.items.size() * 11 - 1 - (this.height - 2)), 1);
            n4 = (int)Util.lerp(this.y + 1, this.y + this.height - 1 - n3, this.scroll / (float)this.getMaxScroll());
        }
        this.gui.drawColoredRect(poseStack, this.x + this.width - 7 + 1, n4, 5, n3, -16777216);
    }

    @Override
    public void drawForeground(PoseStack poseStack, int n, int n2) {
        super.drawForeground(poseStack, n, n2);
        int n3 = this.gui.getX();
        int n4 = this.gui.getY();
        this.doScissor(n3, n4);
        int n5 = this.x + 1 - n3;
        int n6 = -((int)this.scroll) + 1;
        n -= this.x + 1;
        n2 -= this.y;
        for (IListItem iListItem : this.items) {
            if (n6 > -10) {
                iListItem.draw(poseStack, n5, this.y - n4 + n6, this.width - 7 - 1, 10, n, n2 - n6);
            }
            if ((n6 += 11) <= 0) continue;
            int n7 = n6 - this.height;
            if (n7 >= 0) break;
            this.gui.drawColoredRect(poseStack, n5, this.y - n4 + n6 - 1, this.width - 7, 1, -16777216);
            if (n7 != -1) continue;
            break;
        }
        assert (GL11.glIsEnabled((int)3089));
        GL11.glDisable((int)3089);
    }

    private void doScissor(int n, int n2) {
        int n3 = n + this.x + 1;
        int n4 = n2 + this.y + this.height - 1;
        int n5 = this.width - 7 - 1;
        int n6 = this.height - 2;
        Window window = SideProxyClient.mc.m_91268_();
        int n7 = (int)window.m_85449_();
        GL11.glEnable((int)3089);
        GL11.glScissor((int)(n3 * n7), (int)(window.m_85444_() - n4 * n7), (int)(n5 * n7), (int)(n6 * n7));
    }

    @Override
    protected boolean onMouseClick(int n, int n2, MouseButton mouseButton) {
        if (!this.items.isEmpty() && (n -= this.x) > 0 && (n2 -= this.y) > 0 && n2 < this.height - 1) {
            if (n < this.width - 7) {
                int n3 = (n2 + (int)this.scroll) / 11;
                if (n3 >= 0 && n3 < this.items.size()) {
                    int n4 = n - 1;
                    int n5 = (n2 + (int)this.scroll) % 11 - 1;
                    if (n5 >= 0) {
                        return this.onItemClick((IListItem)this.items.get(n3), mouseButton, n4, n5);
                    }
                }
            } else if (n > this.width - 7 && n < this.width - 1 && (double)n2 >= Math.floor(this.scroll * this.getScrollScale())) {
                float f = n2;
                float f2 = this.scroll * this.getScrollScale();
                int n6 = this.items.size() * 11 - 1 <= this.height - 2 ? this.height - 2 : Math.max(this.height - 2 - (this.items.size() * 11 - 1 - (this.height - 2)), 1);
                if (f <= f2 + (float)n6) {
                    this.mouseScrollOffset = n2;
                    this.scrolling = true;
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract boolean onItemClick(I var1, MouseButton var2, int var3, int var4);

    @Override
    public boolean onMouseDrag(int n, int n2, MouseButton mouseButton, boolean bl) {
        if (this.scrolling) {
            assert (this.mouseScrollOffset >= 0);
            float f = this.scroll;
            float f2 = this.scroll + (float)((n2 -= this.y) - this.mouseScrollOffset) * this.getInverseScrollScale();
            this.scroll = Util.limit(f2, 0.0f, (float)this.getMaxScroll());
            if (!Util.isSimilar(f2, this.scroll)) {
                if (!Util.isSimilar(f, this.scroll)) {
                    this.mouseScrollOffset = (int)((float)this.mouseScrollOffset + (this.scroll - f) * this.getScrollScale());
                    assert (this.mouseScrollOffset >= 0) : "Left the scroll bar dragging from " + f + " to " + this.scroll;
                }
            } else {
                this.mouseScrollOffset = n2;
                if (n2 == 0) {
                    this.scroll = 0.0f;
                }
                assert (n2 >= 0) : "Left the scroll bar dragging to " + n2;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseRelease(int n, int n2, MouseButton mouseButton, boolean bl) {
        if (this.scrolling) {
            this.scrolling = false;
            this.mouseScrollOffset = -1;
        }
        return false;
    }

    @Override
    public void onMouseScroll(int n, int n2, ScrollDirection scrollDirection) {
        this.scroll = Util.limit(this.scroll + (float)(scrollDirection.multiplier * 3), 0.0f, (float)this.getMaxScroll());
    }

    protected int getMaxScroll() {
        return Math.max(this.items.size() * 11 - 1 - (this.height - 2), 0);
    }

    private float getScrollScale() {
        int n;
        int n2 = this.getMaxScroll();
        return n2 <= (n = this.height - 2) ? 1.0f : (float)n / (float)n2;
    }

    private float getInverseScrollScale() {
        int n;
        int n2 = this.getMaxScroll();
        return n2 <= (n = this.height - 2) ? 1.0f : (float)n2 / (float)n;
    }

    public static interface IListItem {
        public void draw(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7);
    }
}

