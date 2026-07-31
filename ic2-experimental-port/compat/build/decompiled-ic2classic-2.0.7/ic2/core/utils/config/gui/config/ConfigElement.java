/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.widgets.CarbonIconButton;
import ic2.core.utils.config.gui.widgets.GuiUtils;
import ic2.core.utils.config.gui.widgets.IOwnable;
import ic2.core.utils.config.gui.widgets.Icon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class ConfigElement
extends Element {
    private static final Component DELETE = Component.m_237115_((String)"gui.ic2.delete");
    private static final Component REVERT = Component.m_237115_((String)"gui.ic2.revert");
    private static final Component DEFAULT = Component.m_237115_((String)"gui.ic2.default");
    private static final Component RELOAD = Component.m_237115_((String)"gui.ic2.reload").m_130940_(ChatFormatting.YELLOW);
    private static final Component RESTART = Component.m_237115_((String)"gui.ic2.restart").m_130940_(ChatFormatting.YELLOW);
    protected List<GuiEventListener> listeners = new ObjectArrayList();
    protected List<Map.Entry<AbstractWidget, AlignOffset>> mappedListeners = new ObjectArrayList();
    protected IConfigNode node;
    protected IValueNode value;
    protected IArrayNode array;
    protected CarbonIconButton setReset;
    protected CarbonIconButton setDefault;

    public ConfigElement(IConfigNode node) {
        super(node.getName());
        this.node = node;
        this.value = node.asValue();
    }

    public ConfigElement(IConfigNode node, IValueNode value) {
        super(node.getName());
        this.node = node;
        this.value = value;
    }

    public ConfigElement(IConfigNode node, IArrayNode array) {
        super(node.getName());
        this.node = node;
        this.array = array;
    }

    public ConfigElement(IConfigNode node, IArrayNode array, int index) {
        super(node.getName());
        this.node = node;
        this.array = array;
        this.value = array.asValue(index);
    }

    protected <T extends AbstractWidget> T addChild(T element) {
        return this.addChild(element, GuiAlign.RIGHT, 0);
    }

    protected <T extends AbstractWidget> T addChild(T element, int xOffset) {
        return this.addChild(element, GuiAlign.RIGHT, xOffset);
    }

    protected <T extends AbstractWidget> T addChild(T element, GuiAlign align, int xOffset) {
        this.listeners.add((GuiEventListener)element);
        this.mappedListeners.add(new AbstractMap.SimpleEntry<T, AlignOffset>(element, new AlignOffset(align, -xOffset)));
        if (this.owner != null && element instanceof IOwnable) {
            ((IOwnable)element).setOwner(this.owner);
        }
        return element;
    }

    @Override
    public void init() {
        super.init();
        if (this.createResetButtons(this.value)) {
            if (this.isArray()) {
                this.setReset = this.addChild(new CarbonIconButton(0, 0, 18, 18, Icon.DELETE, (Component)Component.m_237119_(), this::onDeleted).setIconOnly(), -31);
                this.setReset.f_93623_ = this.isReset();
            } else {
                this.setReset = this.addChild(new CarbonIconButton(0, 0, 18, 18, Icon.REVERT, (Component)Component.m_237119_(), this::onReset).setIconOnly(), -21);
                this.setDefault = this.addChild(new CarbonIconButton(0, 0, 18, 18, Icon.SET_DEFAULT, (Component)Component.m_237119_(), this::onDefault).setIconOnly(), -40);
                this.setReset.f_93623_ = this.isReset();
                boolean bl = this.setDefault.f_93623_ = !this.isDefault();
            }
        }
        if (this.owner != null) {
            for (GuiEventListener entry : this.listeners) {
                if (!(entry instanceof IOwnable)) continue;
                ((IOwnable)entry).setOwner(this.owner);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.setReset != null) {
            this.setReset.f_93623_ = this.isReset();
        }
        if (this.setDefault != null) {
            this.setDefault.f_93623_ = !this.isDefault();
        }
    }

    @Override
    public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
        if (this.renderName() && !this.isArray()) {
            this.renderName(poseStack, left, top + 6, this.isChanged(), width - 115);
            if (!this.isCompound()) {
                if (this.node.requiresReload()) {
                    GuiUtils.drawTextureRegion(poseStack, left - 16, top + height / 2 - 6, 12.0f, 12.0f, Icon.RELOAD, 16.0f, 16.0f);
                    if (mouseX >= left - 16 && mouseX <= left - 4 && mouseY >= top && mouseY <= top + height && this.owner.isInsideList(mouseX, mouseY)) {
                        this.owner.addTooltips(RELOAD);
                    }
                }
                if (this.node.requiresRestart()) {
                    GuiUtils.drawTextureRegion(poseStack, left - 16, top + height / 2 - 6, 12.0f, 12.0f, Icon.RESTART, 16.0f, 16.0f);
                    if (mouseX >= left - 16 && mouseX <= left - 4 && mouseY >= top && mouseY <= top + height && this.owner.isInsideList(mouseX, mouseY)) {
                        this.owner.addTooltips(RESTART);
                    }
                }
            }
        }
        int maxX = Integer.MAX_VALUE;
        if (this.renderChildren()) {
            for (Map.Entry<AbstractWidget, AlignOffset> entry : this.mappedListeners) {
                AbstractWidget widget = entry.getKey();
                AlignOffset offset = entry.getValue();
                widget.f_93620_ = offset.align.align(left, width, widget.m_5711_()) + offset.offset;
                widget.f_93621_ = top;
                widget.m_6305_(poseStack, mouseX, mouseY, partialTicks);
                maxX = Math.min(maxX, widget.f_93620_);
            }
        }
        maxX = this.getMaxX(maxX);
        if (this.isArray()) {
            MutableComponent comp = Component.m_237113_((String)(this.indexOf() + ":"));
            this.renderText(poseStack, (Component)comp, maxX - 15 - this.font.m_92852_((FormattedText)comp), top + 6, -1);
        }
        if (mouseY >= top && mouseY <= top + height && mouseX >= left && mouseX <= maxX - 2 && this.owner.isInsideList(mouseX, mouseY)) {
            this.owner.addTooltips(this.node.getTooltip());
        }
        if (this.isArray()) {
            if (this.setReset.m_198029_() && this.owner.isInsideList(mouseX, mouseY)) {
                this.owner.addTooltips(DELETE);
            }
        } else {
            if (this.setReset.m_198029_() && this.owner.isInsideList(mouseX, mouseY)) {
                this.owner.addTooltips(REVERT);
            }
            if (this.setDefault.m_198029_() && this.owner.isInsideList(mouseX, mouseY)) {
                this.owner.addTooltips(DEFAULT);
            }
        }
    }

    protected int getMaxX(int prevMaxX) {
        return prevMaxX;
    }

    protected boolean isArray() {
        return this.array != null;
    }

    protected boolean isCompound() {
        return this.value != null && this.value.isCompoundNode();
    }

    protected boolean renderName() {
        return true;
    }

    protected boolean renderChildren() {
        return true;
    }

    protected boolean createResetButtons(IValueNode value) {
        return value != null;
    }

    protected int indexOf() {
        return this.array.indexOf(this.value);
    }

    protected boolean isReset() {
        return this.isArray() || this.value.isChanged();
    }

    @Override
    public boolean isChanged() {
        return this.value.isChanged();
    }

    @Override
    public boolean isDefault() {
        return this.value.isDefault();
    }

    protected void onDeleted(CarbonIconButton button) {
        if (!this.isArray()) {
            return;
        }
        this.owner.removeEntry(this);
    }

    protected void onReset(CarbonIconButton button) {
        this.value.setPrevious();
        this.updateValues();
    }

    protected void onDefault(CarbonIconButton button) {
        if (this.value == null) {
            return;
        }
        this.value.setDefault();
        this.updateValues();
    }

    protected void updateValues() {
    }

    @Override
    public List<? extends GuiEventListener> m_6702_() {
        return this.listeners;
    }

    public static enum GuiAlign {
        LEFT,
        CENTER,
        RIGHT;


        public int align(int left, int bounds, int width) {
            switch (this) {
                case LEFT: {
                    return left;
                }
                case CENTER: {
                    return left + bounds / 2 - width / 2;
                }
                case RIGHT: {
                    return left + bounds - width - 42;
                }
            }
            return left;
        }
    }

    public static class AlignOffset {
        GuiAlign align;
        int offset;

        public AlignOffset(GuiAlign align, int offset) {
            this.align = align;
            this.offset = offset;
        }
    }
}

