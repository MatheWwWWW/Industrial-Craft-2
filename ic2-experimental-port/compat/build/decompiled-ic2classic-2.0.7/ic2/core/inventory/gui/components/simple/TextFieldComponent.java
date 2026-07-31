/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.blaze3d.platform.GlStateManager$LogicOp
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.SharedConstants
 *  net.minecraft.Util
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.StringSplitter
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiComponent
 *  net.minecraft.client.gui.font.TextFieldHelper
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package ic2.core.inventory.gui.components.simple;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class TextFieldComponent
extends GuiWidget {
    @OnlyIn(value=Dist.CLIENT)
    TextFieldHelper text;
    Supplier<String> textGetter;
    Consumer<String> textSetter;
    Predicate<String> textValdiator;
    int visibleLanes = 0;
    long lastClickTime = 0L;
    int lastPos = -1;
    Cache cache = new Cache();
    boolean dirtyCache = false;
    int offset = 0;
    int tick = 0;
    boolean focused = false;
    boolean canLoseFocus = true;

    public TextFieldComponent(Box2i box, Supplier<String> textGetter, Consumer<String> textSetter, Predicate<String> textValdiator) {
        super(box);
        this.textGetter = textGetter;
        this.textSetter = textSetter;
        this.textValdiator = textValdiator;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.GUI_CLOSE);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return super.isMouseOver(x, y) || this.focused && this.canLoseFocus;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getMaxLanes() {
        Cache cache = this.getCache();
        return cache.lines.length + (cache.cursorAtEnd && cache.cursor.getX() == 0 ? 1 : 0);
    }

    public int getVisibleLanes() {
        return this.visibleLanes;
    }

    public TextFieldComponent setOffset(int offset) {
        this.offset = Mth.m_14045_((int)offset, (int)0, (int)Math.min(Math.max(0, this.getCache().lines.length - this.visibleLanes + 1), offset));
        return this;
    }

    public TextFieldComponent setFocused(boolean value) {
        this.focused = value;
        return this;
    }

    public TextFieldComponent setCanLoseFocus(boolean value) {
        this.canLoseFocus = true;
        return this;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        Minecraft mc = gui.getMinecraft();
        this.text = new TextFieldHelper(this::getText, this::setText, TextFieldHelper.m_95153_((Minecraft)mc), TextFieldHelper.m_95182_((Minecraft)mc), this.textValdiator);
        this.dirtyCache = true;
        float f = this.getBox().getHeight();
        Objects.requireNonNull(gui.getFont());
        this.visibleLanes = Mth.m_14143_((float)(f / 9.0f));
        gui.setRepeatingKeys(true);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        ++this.tick;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onClose() {
        this.gui.setRepeatingKeys(false);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        Line[] lines = this.getCache().lines;
        matrix.m_85836_();
        int n = -this.offset;
        Objects.requireNonNull(this.gui.getFont());
        matrix.m_85837_(0.0, (double)(n * 9), 0.0);
        for (int i = 0; i < this.visibleLanes && i + this.offset < lines.length; ++i) {
            Line line = lines[i + this.offset];
            this.gui.getFont().m_92889_(matrix, line.asComponent, (float)line.x, (float)line.y, -16777216);
        }
        if (this.focused) {
            int maxLine;
            int minLine;
            int length = this.cache.selection.length;
            if (length > 0 && length - (minLine = Math.abs(Math.min(Math.min(this.cache.cursorLine, this.cache.selectionLine) - this.offset, 0))) - (maxLine = Math.abs(Math.max(Math.max(this.cache.cursorLine, this.cache.selectionLine) - (this.offset + this.visibleLanes), 0))) > 0) {
                this.renderHighlight(matrix, this.cache.selection, minLine, length - maxLine);
            }
            if (this.cache.cursorLine >= this.offset && this.cache.cursorLine - this.visibleLanes <= this.offset) {
                this.renderCursor(matrix, this.cache.localCursor, this.cache.cursorAtEnd);
            }
        }
        matrix.m_85849_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        if (!this.focused) {
            return false;
        }
        if (keyCode == 69) {
            return true;
        }
        if (this.onTextTyped(keyCode)) {
            this.dirtyCache = true;
            return true;
        }
        return super.onKeyTyped(keyCode);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onCharTyped(char letter, int letterCode) {
        if (SharedConstants.m_136188_((char)letter)) {
            this.text.m_95158_(Character.toString(letter));
            this.dirtyCache = true;
            return true;
        }
        return super.onCharTyped(letter, letterCode);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!super.isMouseOver(mouseX, mouseY) && this.focused && this.canLoseFocus) {
            this.focused = false;
            return false;
        }
        this.focused = true;
        if (mouseButton == 0) {
            long clickTime = Util.m_137550_();
            Cache cache = this.getCache();
            int pos = cache.getIndexAtPosition(this.gui.getFont(), this.convertScreenToLocal(new Vec2i(mouseX, mouseY)));
            if (pos >= 0) {
                if (pos == this.lastPos && clickTime - this.lastClickTime < 250L) {
                    if (!this.text.m_95198_()) {
                        this.text.m_95147_(StringSplitter.m_92355_((String)this.getText(), (int)-1, (int)pos, (boolean)false), StringSplitter.m_92355_((String)this.getText(), (int)1, (int)pos, (boolean)false));
                    } else {
                        this.text.m_95188_();
                    }
                } else {
                    this.text.m_95179_(pos, Screen.m_96638_());
                }
                this.dirtyCache = true;
            }
            this.lastPos = pos;
            this.lastClickTime = clickTime;
        }
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseDragged(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            Cache cache = this.getCache();
            int line = cache.getIndexAtPosition(this.gui.getFont(), this.convertScreenToLocal(new Vec2i(mouseX, mouseY)));
            this.text.m_95179_(line, true);
            this.dirtyCache = true;
        }
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    private boolean onTextTyped(int keyCode) {
        if (Screen.m_96634_((int)keyCode)) {
            this.text.m_95188_();
            return true;
        }
        if (Screen.m_96632_((int)keyCode)) {
            this.text.m_95178_();
            return true;
        }
        if (Screen.m_96630_((int)keyCode)) {
            this.text.m_95158_(TextFieldHelper.m_95169_((Minecraft)Minecraft.m_91087_()).replaceAll("\t", "   "));
            return true;
        }
        if (Screen.m_96628_((int)keyCode)) {
            this.text.m_95142_();
            return true;
        }
        switch (keyCode) {
            case 257: 
            case 335: {
                this.text.m_95158_("\n");
                return true;
            }
            case 258: {
                this.text.m_95158_("   ");
                return true;
            }
            case 259: {
                this.text.m_95189_(-1);
                return true;
            }
            case 261: {
                this.text.m_95189_(1);
                return true;
            }
            case 262: {
                this.text.m_95150_(1, Screen.m_96638_());
                return true;
            }
            case 263: {
                this.text.m_95150_(-1, Screen.m_96638_());
                return true;
            }
            case 264: {
                this.keyDown();
                return true;
            }
            case 265: {
                this.keyUp();
                return true;
            }
            case 268: {
                this.keyHome();
                return true;
            }
            case 269: {
                this.keyEnd();
                return true;
            }
        }
        return false;
    }

    private void keyUp() {
        this.changeLine(-1);
    }

    private void keyDown() {
        this.changeLine(1);
    }

    private void changeLine(int offset) {
        this.text.m_95179_(this.getCache().changeLine(this.text.m_95194_(), offset), Screen.m_96638_());
    }

    private void keyHome() {
        this.text.m_95179_(this.getCache().findLineStart(this.text.m_95194_()), Screen.m_96638_());
    }

    private void keyEnd() {
        this.text.m_95179_(this.getCache().findLineEnd(this.text.m_95194_()), Screen.m_96638_());
    }

    private String getText() {
        return this.textGetter.get();
    }

    private void setText(String s) {
        this.textSetter.accept(s);
    }

    private Cache getCache() {
        if (this.dirtyCache) {
            this.dirtyCache = false;
            this.rebuildDisplayCache();
        }
        return this.cache;
    }

    private void rebuildDisplayCache() {
        Vec2i cursor;
        boolean isEnd;
        String s = this.getText();
        if (s.isEmpty()) {
            this.cache.clear();
            return;
        }
        int cursorPos = this.text.m_95194_();
        int selectedPos = this.text.m_95197_();
        IntArrayList lineStarts = new IntArrayList();
        ObjectList lines = CollectionUtils.createList();
        MutableInt lineCount = new MutableInt();
        MutableBoolean isNewLine = new MutableBoolean();
        StringSplitter splitter = this.gui.getFont().m_92865_();
        splitter.m_92364_(s, this.getBox().getWidth() - 5, Style.f_131099_, true, (arg_0, arg_1, arg_2) -> this.lambda$rebuildDisplayCache$0(s, isNewLine, (IntList)lineStarts, lines, lineCount, arg_0, arg_1, arg_2));
        int[] lineStartArray = lineStarts.toIntArray();
        boolean bl = isEnd = cursorPos == s.length();
        if (isEnd && isNewLine.isTrue()) {
            cursor = new Vec2i(0, lines.size() * 9);
        } else {
            int k = this.findLineFromPos(lineStartArray, cursorPos);
            cursor = new Vec2i(this.gui.getFont().m_92895_(s.substring(lineStartArray[k], cursorPos)), k * 9);
        }
        ArrayList bounds = Lists.newArrayList();
        if (cursorPos != selectedPos) {
            int maxLine;
            int minPos = Math.min(cursorPos, selectedPos);
            int maxPos = Math.max(cursorPos, selectedPos);
            int minLine = this.findLineFromPos(lineStartArray, minPos);
            if (minLine == (maxLine = this.findLineFromPos(lineStartArray, maxPos))) {
                bounds.add(this.createPartialLineSelection(s, splitter, minPos, maxPos, minLine * 9, lineStartArray[minLine]));
            } else {
                bounds.add(this.createPartialLineSelection(s, splitter, minPos, minLine + 1 > lineStartArray.length ? s.length() : lineStartArray[minLine + 1], minLine * 9, lineStartArray[minLine]));
                for (int line = minLine + 1; line < maxLine; ++line) {
                    bounds.add(new Bounds(this.convertLocalToScreen(new Vec2i(0, line * 9)), this.convertLocalToScreen(new Vec2i((int)splitter.m_92353_(s.substring(lineStartArray[line], lineStartArray[line + 1])), line * 9 + 9))));
                }
                bounds.add(this.createPartialLineSelection(s, splitter, lineStartArray[maxLine], maxPos, maxLine * 9, lineStartArray[maxLine]));
            }
        }
        this.cache.setCache(s, cursor, this.findLineFromPos(lineStartArray, cursorPos), this.findLineFromPos(lineStartArray, selectedPos), isEnd, lineStartArray, lines.toArray((Line[])new Line[lines.size()]), bounds.toArray(new Bounds[bounds.size()]));
        int cursorY = this.findLineFromPos(lineStartArray, cursorPos);
        if (cursorY == lines.size() - 1 && isEnd && isNewLine.isTrue()) {
            ++cursorY;
        }
        if (cursorY < this.offset) {
            this.offset = Math.max(0, cursorY);
        } else if (cursorY > 6 && cursorY - 6 > this.offset || isEnd) {
            this.offset = Math.max(0, cursorY - 6);
        }
    }

    private void renderCursor(PoseStack matrix, Vec2i pos, boolean end) {
        if (this.tick / 6 % 2 == 0) {
            if (!end) {
                GuiComponent.m_93172_((PoseStack)matrix, (int)pos.getX(), (int)(pos.getY() - 1), (int)(pos.getX() + 1), (int)(pos.getY() + 9), (int)-16777216);
            } else {
                this.gui.getFont().m_92883_(matrix, "_", (float)pos.getX(), (float)pos.getY(), 0);
            }
        }
    }

    private void renderHighlight(PoseStack matrix, Bounds[] bounds, int start, int end) {
        Tesselator tes = Tesselator.m_85913_();
        BufferBuilder buffer = tes.m_85915_();
        RenderSystem.m_69472_();
        RenderSystem.m_69479_();
        RenderSystem.m_69835_((GlStateManager.LogicOp)GlStateManager.LogicOp.OR_REVERSE);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Matrix4f pos = matrix.m_85850_().m_85861_();
        for (int i = start; i < end && i < bounds.length; ++i) {
            Bounds bound = bounds[i];
            buffer.m_85982_(pos, (float)bound.xPos, (float)(bound.yPos + bound.height), 0.0f).m_193479_(-16776961).m_5752_();
            buffer.m_85982_(pos, (float)(bound.xPos + bound.width), (float)(bound.yPos + bound.height), 0.0f).m_193479_(-16776961).m_5752_();
            buffer.m_85982_(pos, (float)(bound.xPos + bound.width), (float)bound.yPos, 0.0f).m_193479_(-16776961).m_5752_();
            buffer.m_85982_(pos, (float)bound.xPos, (float)bound.yPos, 0.0f).m_193479_(-16776961).m_5752_();
        }
        tes.m_85914_();
        RenderSystem.m_69462_();
        RenderSystem.m_69493_();
    }

    @OnlyIn(value=Dist.CLIENT)
    private Vec2i convertScreenToLocal(Vec2i pos) {
        pos.set(pos.getX() - (this.getBox().getX() + 1), pos.getY() - (this.getBox().getY() + 1));
        return pos;
    }

    @OnlyIn(value=Dist.CLIENT)
    private Vec2i convertLocalToScreen(Vec2i pos) {
        pos.set(pos.getX() + (this.getBox().getX() + 1), pos.getY() + (this.getBox().getY() + 1));
        return pos;
    }

    @OnlyIn(value=Dist.CLIENT)
    private Bounds createPartialLineSelection(String s, StringSplitter characters, int minPos, int maxPos, int minLine, int maxLine) {
        return new Bounds(this.convertLocalToScreen(new Vec2i((int)characters.m_92353_(s.substring(maxLine, minPos)), minLine)), this.convertLocalToScreen(new Vec2i((int)characters.m_92353_(s.substring(maxLine, maxPos)), minLine + 9)));
    }

    @OnlyIn(value=Dist.CLIENT)
    private Bounds createSelection(Vec2i from, Vec2i to) {
        return new Bounds(this.convertLocalToScreen(from), this.convertLocalToScreen(to));
    }

    private int findLineFromPos(int[] lineStarts, int pos) {
        int i = Arrays.binarySearch(lineStarts, pos);
        return i < 0 ? -(i + 2) : i;
    }

    private /* synthetic */ void lambda$rebuildDisplayCache$0(String s, MutableBoolean isNewLine, IntList lineStarts, List lines, MutableInt lineCount, Style style, int from, int to) {
        String cutText = s.substring(from, to);
        isNewLine.setValue(cutText.endsWith("\n"));
        lineStarts.add(from);
        String string = StringUtils.stripEnd((String)cutText, (String)" \n");
        int n = lineCount.getAndIncrement();
        Objects.requireNonNull(this.gui.getFont());
        lines.add(new Line(style, string, this.convertLocalToScreen(new Vec2i(0, n * 9))));
    }

    class Cache {
        private String fullText = "";
        private Vec2i cursor = new Vec2i();
        private Vec2i localCursor = new Vec2i();
        private boolean cursorAtEnd = true;
        private int cursorLine = 0;
        private int selectionLine = 0;
        private int[] lineStarts = new int[1];
        private Line[] lines = new Line[]{Line.EMPTY_LINE};
        private Bounds[] selection = new Bounds[0];

        Cache() {
        }

        public void setCache(String fullText, Vec2i cursor, int cursorLine, int selectionLine, boolean cursorAtEnd, int[] lineStarts, Line[] lines, Bounds[] selection) {
            this.fullText = fullText;
            this.cursor.set(cursor);
            this.localCursor.set(cursor);
            TextFieldComponent.this.convertLocalToScreen(this.localCursor);
            this.cursorLine = cursorLine;
            this.selectionLine = selectionLine;
            this.cursorAtEnd = cursorAtEnd;
            this.lineStarts = lineStarts;
            this.lines = lines;
            this.selection = selection;
        }

        public void clear() {
            this.fullText = "";
            this.cursor.set(0, 0);
            this.localCursor.set(0, 0);
            TextFieldComponent.this.convertLocalToScreen(this.localCursor);
            this.cursorLine = 0;
            this.selectionLine = 0;
            this.cursorAtEnd = true;
            this.lineStarts = new int[1];
            this.lines = new Line[]{Line.EMPTY_LINE};
            this.selection = new Bounds[0];
        }

        public int getIndexAtPosition(Font font, Vec2i pos) {
            int n = pos.getY();
            Objects.requireNonNull(font);
            int line = n / 9 + TextFieldComponent.this.offset;
            if (line < 0) {
                return 0;
            }
            if (line >= this.lines.length) {
                return this.fullText.length();
            }
            return this.lineStarts[line] + font.m_92865_().m_92360_(this.lines[line].contents, pos.getX(), this.lines[line].style);
        }

        public int changeLine(int x, int y) {
            int offset = TextFieldComponent.this.findLineFromPos(this.lineStarts, x);
            int line = offset + y;
            if (0 <= line && line < this.lineStarts.length) {
                return this.lineStarts[line] + Math.min(x - this.lineStarts[offset], this.lines[line].contents.length());
            }
            return x;
        }

        public int findLineStart(int pos) {
            return this.lineStarts[TextFieldComponent.this.findLineFromPos(this.lineStarts, pos)];
        }

        public int findLineEnd(int pos) {
            int line = TextFieldComponent.this.findLineFromPos(this.lineStarts, pos);
            return this.lineStarts[line] + this.lines[line].contents.length();
        }
    }

    public static class Line {
        private static final Line EMPTY_LINE = new Line(Style.f_131099_, "", new Vec2i(0, 0));
        private final Style style;
        private final String contents;
        private final Component asComponent;
        private final int x;
        private final int y;

        public Line(Style style, String contents, Vec2i pos) {
            this.style = style;
            this.contents = contents;
            this.x = pos.getX();
            this.y = pos.getY();
            this.asComponent = Component.m_237113_((String)contents).m_6270_(style);
        }
    }

    public static class Bounds {
        int xPos;
        int yPos;
        int width;
        int height;

        public Bounds(Vec2i from, Vec2i to) {
            this.xPos = from.getX();
            this.yPos = from.getY();
            this.width = to.getX() - this.xPos;
            this.height = to.getY() - this.yPos;
        }
    }
}

