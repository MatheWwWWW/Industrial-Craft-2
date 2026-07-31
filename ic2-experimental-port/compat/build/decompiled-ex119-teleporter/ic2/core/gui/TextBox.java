/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  com.mojang.blaze3d.platform.GlStateManager$LogicOp
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.SharedConstants
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 */
package ic2.core.gui;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import ic2.core.proxy.SideProxyClient;
import ic2.core.util.Util;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;

public class TextBox
extends GuiElement<TextBox> {
    protected String text;
    protected boolean focused;
    protected int cursor;
    protected int cursorTick;
    protected int scrollOffset;
    protected int selectionEnd;
    protected int maxTextLength = 32;
    protected IEnableHandler enableHandler;
    protected Predicate<String> validator = Predicates.alwaysTrue();
    protected ITextBoxWatcher watcher;
    protected final boolean drawBackground;
    protected static final int enabledColour = 0xE0E0E0;
    protected static final int disabledColour = 0x707070;
    protected static final int invalidColour = -3092272;

    public TextBox(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4) {
        this(ic2Gui, n, n2, n3, n4, "");
    }

    public TextBox(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, String string) {
        this(ic2Gui, n, n2, n3, n4, string, true);
    }

    public TextBox(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, boolean bl) {
        this(ic2Gui, n, n2, n3, n4, "", bl);
    }

    public TextBox(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, String string, boolean bl) {
        super(ic2Gui, n, n2, n3, n4);
        this.text = string;
        this.drawBackground = bl;
        this.selectionEnd = this.cursor = string.length();
    }

    public TextBox withTextEnableHandler(IEnableHandler iEnableHandler) {
        this.enableHandler = iEnableHandler;
        return this;
    }

    public TextBox withTextValidator(Predicate<String> predicate) {
        this.validator = predicate;
        return this;
    }

    public TextBox withTextWatcher(ITextBoxWatcher iTextBoxWatcher) {
        this.watcher = iTextBoxWatcher;
        return this;
    }

    public boolean willDraw() {
        return this.enableHandler == null || this.enableHandler.isEnabled();
    }

    public void setFocused(boolean bl) {
        if (bl && !this.focused) {
            this.cursorTick = 0;
        }
        this.focused = bl;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void setMaxTextLength(int n) {
        if (n >= 0) {
            this.maxTextLength = n;
        }
    }

    public String getText() {
        return this.text;
    }

    public boolean setText(String string) {
        if (this.setText(string, true)) {
            this.setCursorPositionEnd();
            return true;
        }
        return false;
    }

    public boolean setText(String string, boolean bl) {
        assert (string != null);
        if (this.validator.apply((Object)string) && (string.length() <= this.maxTextLength || bl)) {
            String string2 = this.text;
            String string3 = this.text = string.length() <= this.maxTextLength ? string : string.substring(0, this.maxTextLength);
            if (this.watcher != null) {
                this.watcher.onChanged(string2, string);
            }
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        ++this.cursorTick;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        super.drawBackground(poseStack, n, n2);
        if (this.drawBackground) {
            this.gui.drawColoredRect(poseStack, this.x - 1, this.y - 1, this.width + 2, this.height + 2, -6250336);
            this.gui.drawColoredRect(poseStack, this.x, this.y, this.width, this.height, -16777216);
        }
    }

    @Override
    public void drawForeground(PoseStack poseStack, int n, int n2) {
        super.drawForeground(poseStack, n, n2);
        int n3 = this.willDraw() ? 0xE0E0E0 : 0x707070;
        int n4 = this.cursor - this.scrollOffset;
        int n5 = this.selectionEnd - this.scrollOffset;
        String string = this.gui.trimStringToWidth(this.text.substring(this.scrollOffset), this.drawBackground ? this.width - 8 : this.width);
        boolean bl = n4 >= 0 && n4 <= string.length();
        int n6 = (this.drawBackground ? this.x + 4 : this.x) - this.gui.getX();
        int n7 = (this.drawBackground ? this.y + (this.height - 8) / 2 : this.y) - this.gui.getY();
        int n8 = n6;
        if (n5 > string.length()) {
            n5 = string.length();
        }
        if (!string.isEmpty()) {
            n8 = this.gui.drawString(poseStack, n6, n7, bl ? string.substring(0, n4) : string, n3, true);
        }
        boolean bl2 = this.cursor < this.text.length() || this.text.length() >= this.maxTextLength;
        int n9 = n8;
        if (!bl) {
            n9 = n4 > 0 ? n6 + this.width : n6;
        } else if (bl2) {
            n9 = n8 - 1;
            --n8;
        }
        if (!string.isEmpty() && bl && n4 < string.length()) {
            n8 = this.gui.drawString(poseStack, n8, n7, string.substring(n4), n3, true);
        }
        if (this.focused && this.cursorTick / 6 % 2 == 0 && bl) {
            if (bl2) {
                this.gui.drawColoredRect(poseStack, n9, n7 - 1, 1, 10, -3092272);
            } else {
                this.gui.drawString(poseStack, n9, n7, "_", n3, true);
            }
        }
        if (n5 != n4) {
            int n10 = n6 + this.gui.getStringWidth(string.substring(0, n5));
            this.drawHighlightedArea(n9, n7 - 1, n10 - 1, n7 + 1 + 8);
        }
    }

    protected void drawHighlightedArea(int n, int n2, int n3, int n4) {
        int n5;
        if (n < n3) {
            n5 = n;
            n = n3;
            n3 = n5;
        }
        if (n2 < n4) {
            n5 = n2;
            n2 = n4;
            n4 = n5;
        }
        n += this.gui.getX();
        n2 += this.gui.getY();
        n4 += this.gui.getY();
        if ((n3 += this.gui.getX()) > this.x + this.width) {
            n3 = this.x + this.width;
        }
        if (n > this.x + this.width) {
            n = this.x + this.width;
        }
        Tesselator tesselator = Tesselator.m_85913_();
        BufferBuilder bufferBuilder = tesselator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172808_);
        RenderSystem.m_157429_((float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f);
        RenderSystem.m_69472_();
        RenderSystem.m_69479_();
        RenderSystem.m_69835_((GlStateManager.LogicOp)GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        bufferBuilder.m_5483_((double)n, (double)n4, 0.0).m_5752_();
        bufferBuilder.m_5483_((double)n3, (double)n4, 0.0).m_5752_();
        bufferBuilder.m_5483_((double)n3, (double)n2, 0.0).m_5752_();
        bufferBuilder.m_5483_((double)n, (double)n2, 0.0).m_5752_();
        tesselator.m_85914_();
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.m_69462_();
        RenderSystem.m_69493_();
    }

    @Override
    public boolean onMouseClick(int n, int n2, MouseButton mouseButton, boolean bl) {
        this.setFocused(bl);
        if (this.focused && bl && MouseButton.left == mouseButton) {
            int n3 = n - this.x;
            if (this.drawBackground) {
                n3 -= 4;
            }
            String string = this.gui.trimStringToWidth(this.text.substring(this.scrollOffset), this.drawBackground ? this.width - 8 : this.width);
            this.setCursorPosition(this.gui.trimStringToWidth(string, n3).length() + this.scrollOffset);
        }
        return bl;
    }

    @Override
    public boolean onKeyTyped(char c, int n) {
        if (!this.focused) {
            return super.onKeyTyped(c, n);
        }
        if (Screen.m_96634_((int)n)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
        } else if (Screen.m_96632_((int)n)) {
            SideProxyClient.mc.f_91068_.m_90911_(this.getSelectedText());
        } else if (Screen.m_96630_((int)n)) {
            if (this.willDraw()) {
                this.writeText(SideProxyClient.mc.f_91068_.m_90876_());
            }
        } else if (Screen.m_96628_((int)n)) {
            SideProxyClient.mc.f_91068_.m_90911_(this.getSelectedText());
            if (this.willDraw()) {
                this.writeText("");
            }
        } else {
            switch (n) {
                case 259: {
                    if (Screen.m_96637_()) {
                        if (!this.willDraw()) break;
                        this.deleteWords(-1);
                        break;
                    }
                    if (!this.willDraw()) break;
                    this.deleteFromCursor(-1);
                    break;
                }
                case 268: {
                    if (Screen.m_96638_()) {
                        this.setSelectionPos(0);
                        break;
                    }
                    this.setCursorPositionStart();
                    break;
                }
                case 263: {
                    if (Screen.m_96638_()) {
                        if (Screen.m_96637_()) {
                            this.setSelectionPos(this.getNthWordFromPos(-1, this.selectionEnd));
                            break;
                        }
                        this.setSelectionPos(this.selectionEnd - 1);
                        break;
                    }
                    if (Screen.m_96637_()) {
                        this.setCursorPosition(this.getNthWordFromCursor(-1));
                        break;
                    }
                    this.moveCursorBy(-1);
                    break;
                }
                case 262: {
                    if (Screen.m_96638_()) {
                        if (Screen.m_96637_()) {
                            this.setSelectionPos(this.getNthWordFromPos(1, this.selectionEnd));
                            break;
                        }
                        this.setSelectionPos(this.selectionEnd + 1);
                        break;
                    }
                    if (Screen.m_96637_()) {
                        this.setCursorPosition(this.getNthWordFromCursor(1));
                        break;
                    }
                    this.moveCursorBy(1);
                    break;
                }
                case 269: {
                    if (Screen.m_96638_()) {
                        this.setSelectionPos(this.text.length());
                        break;
                    }
                    this.setCursorPositionEnd();
                    break;
                }
                case 261: {
                    if (Screen.m_96637_()) {
                        if (!this.willDraw()) break;
                        this.deleteWords(1);
                        break;
                    }
                    if (!this.willDraw()) break;
                    this.deleteFromCursor(1);
                    break;
                }
                default: {
                    if (SharedConstants.m_136188_((char)c) && this.willDraw()) {
                        this.writeText(String.valueOf(c));
                        break;
                    }
                    return super.onKeyTyped(c, n);
                }
            }
        }
        return true;
    }

    public void writeText(String string) {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = SharedConstants.m_136190_((String)string);
        int n2 = Math.min(this.cursor, this.selectionEnd);
        int n3 = Math.max(this.cursor, this.selectionEnd);
        int n4 = this.maxTextLength - this.text.length() - (n2 - n3);
        if (!this.text.isEmpty()) {
            stringBuilder.append(this.text.substring(0, n2));
        }
        if (n4 < string2.length()) {
            stringBuilder.append(string2.substring(0, n4));
            n = n4;
        } else {
            stringBuilder.append(string2);
            n = string2.length();
        }
        if (!this.text.isEmpty() && n3 < this.text.length()) {
            stringBuilder.append(this.text.substring(n3));
        }
        if (this.setText(stringBuilder.toString(), true)) {
            this.moveCursorBy(n2 - this.selectionEnd + n);
        }
    }

    public void deleteWords(int n) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.cursor) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(n) - this.cursor);
            }
        }
    }

    public void deleteFromCursor(int n) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.cursor) {
                this.writeText("");
            } else {
                int n2;
                int n3;
                if (n < 0) {
                    n3 = this.cursor;
                    n2 = this.cursor + n;
                } else {
                    n3 = this.cursor + n;
                    n2 = this.cursor;
                }
                StringBuilder stringBuilder = new StringBuilder();
                if (n2 >= 0) {
                    stringBuilder.append(this.text.substring(0, n2));
                }
                if (n3 < this.text.length()) {
                    stringBuilder.append(this.text.substring(n3));
                }
                if (this.validator.apply((Object)stringBuilder.toString())) {
                    String string = this.text;
                    this.text = stringBuilder.toString();
                    if (this.watcher != null) {
                        this.watcher.onChanged(string, this.text);
                    }
                    if (n < 0) {
                        this.moveCursorBy(n);
                    }
                }
            }
        }
    }

    protected int getNthWordFromCursor(int n) {
        return this.getNthWordFromPos(n, this.cursor);
    }

    protected int getNthWordFromPos(int n, int n2) {
        return this.getNthWordFromPosWS(n, n2, true);
    }

    protected int getNthWordFromPosWS(int n, int n2, boolean bl) {
        boolean bl2 = n >= 0;
        int n3 = Math.abs(n);
        for (int i = 0; i < n3; ++i) {
            if (bl2) {
                int n4 = this.text.length();
                if ((n2 = this.text.indexOf(32, n2)) == -1) {
                    n2 = n4;
                    continue;
                }
                while (bl && n2 < n4 && this.text.charAt(n2) == ' ') {
                    ++n2;
                }
                continue;
            }
            while (bl && n2 > 0 && this.text.charAt(n2 - 1) == ' ') {
                --n2;
            }
            while (n2 > 0 && this.text.charAt(n2 - 1) != ' ') {
                --n2;
            }
        }
        return n2;
    }

    public String getSelectedText() {
        return this.text.substring(Math.min(this.cursor, this.selectionEnd), Math.max(this.cursor, this.selectionEnd));
    }

    protected void setCursorPositionStart() {
        this.setCursorPosition(0);
    }

    protected void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    protected void moveCursorBy(int n) {
        this.setCursorPosition(this.selectionEnd + n);
    }

    protected void setCursorPosition(int n) {
        this.cursor = Util.limit(n, 0, this.text.length());
        this.setSelectionPos(this.cursor);
    }

    protected void setSelectionPos(int n) {
        int n2 = this.text.length();
        this.selectionEnd = n = Util.limit(n, 0, n2);
        if (this.scrollOffset > n2) {
            this.scrollOffset = n2;
        }
        int n3 = this.drawBackground ? this.width - 8 : this.width;
        int n4 = this.gui.trimStringToWidth(this.text.substring(this.scrollOffset), n3).length() + this.scrollOffset;
        if (n == this.scrollOffset) {
            this.scrollOffset -= this.gui.trimStringToWidthReverse(this.text, n3).length();
        }
        if (n > n4) {
            this.scrollOffset += n - n4;
        } else if (n <= this.scrollOffset) {
            this.scrollOffset -= this.scrollOffset - n;
        }
        this.scrollOffset = Util.limit(this.scrollOffset, 0, n2);
    }

    public static interface ITextBoxWatcher {
        public void onChanged(String var1, String var2);
    }
}

