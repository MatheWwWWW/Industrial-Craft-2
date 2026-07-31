/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.config.CompoundElement;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.ListScreen;
import ic2.core.utils.config.gui.screen.CompoundScreen;
import ic2.core.utils.config.gui.screen.ListSelectionScreen;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ArrayScreen
extends ListScreen {
    Screen prev;
    IConfigNode entry;
    IArrayNode array;
    List<DataType> type;

    public ArrayScreen(IConfigNode entry, Screen prev, BackgroundTexture customTexture) {
        super(entry.getName(), customTexture);
        this.prev = prev;
        this.entry = entry;
        this.array = entry.asArray();
        this.type = entry.getDataType();
        this.array.createTemp();
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        int x = this.f_96543_ / 2;
        int y = this.f_96544_;
        this.m_142416_((GuiEventListener)new ExtendedButton(x - 92, y - 27, 80, 20, (Component)Component.m_237115_((String)"gui.ic2.apply"), this::apply));
        this.m_142416_((GuiEventListener)new ExtendedButton(x - 10, y - 27, 20, 20, (Component)Component.m_237113_((String)"+"), this::createEntry));
        this.m_142416_((GuiEventListener)new ExtendedButton(x + 12, y - 27, 80, 20, (Component)Component.m_237115_((String)"gui.ic2.back"), this::goBack));
    }

    @Override
    protected boolean shouldHaveTooltips() {
        return true;
    }

    @Override
    protected boolean shouldHaveSearch() {
        return false;
    }

    public void m_7379_() {
        this.array.setPrevious();
        this.f_96541_.m_91152_(this.prev);
    }

    private void apply(Button button) {
        this.array.apply();
        this.f_96541_.m_91152_(this.prev);
    }

    private void goBack(Button button) {
        if (this.array.isChanged()) {
            this.f_96541_.m_91152_((Screen)new ConfirmScreen(T -> {
                if (T) {
                    this.array.setPrevious();
                }
                this.f_96541_.m_91152_((Screen)(T ? this.prev : this));
            }, (Component)Component.m_237115_((String)"gui.ic2.warn.changed"), (Component)Component.m_237115_((String)"gui.ic2.warn.changed.desc").m_130940_(ChatFormatting.GRAY)));
            return;
        }
        this.array.setPrevious();
        this.f_96541_.m_91152_(this.prev);
    }

    @Override
    protected void collectElements(Consumer<Element> elements) {
        if (this.type.size() > 1) {
            int m = this.array.size();
            for (int i = 0; i < m; ++i) {
                elements.accept(new CompoundElement(this.entry, this.array, this.array.asCompound(i)));
            }
            return;
        }
        int m = this.array.size();
        for (int i = 0; i < m; ++i) {
            ConfigElement element = this.type.get(0).create(this.entry, this.array, i);
            if (element == null) continue;
            elements.accept(element);
        }
    }

    @Override
    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, this.f_96539_, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)this.f_96539_) / 2), 30.0f, -1);
    }

    public void createEntry(Button button) {
        int size = this.array.size();
        this.array.createNode();
        if (this.entry.getValidValues().size() > 0) {
            ListSelectionScreen screen = this.entry.getDataType().size() > 1 ? ListSelectionScreen.ofCompound(this.prev, this.entry, this.array.asCompound(size), this.getCustomTexture()) : ListSelectionScreen.ofValue(this.prev, this.entry, this.array.asValue(size), this.getCustomTexture());
            screen.withListener(() -> this.postCreate(size, true), () -> this.array.removeNode(size)).disableAbortWarning();
            this.f_96541_.m_91152_((Screen)screen);
            return;
        }
        this.postCreate(size, false);
    }

    private void postCreate(int size, boolean reopen) {
        if (this.type.size() > 1) {
            CompoundScreen screen = new CompoundScreen(this.entry, this.array.asCompound(size), this, this.getCustomTexture());
            screen.setAbortListener(() -> this.array.removeNode(size));
            this.f_96541_.m_91152_((Screen)screen);
            this.lastScroll = Double.MAX_VALUE;
            return;
        }
        ConfigElement element = this.type.get(0).create(this.entry, this.array, size);
        if (element != null) {
            this.addEntry(element);
            this.visibleList.addElement(element);
            this.visibleList.setScrollAmount(this.visibleList.m_93518_(), true);
        }
        if (reopen) {
            this.f_96541_.m_91152_((Screen)this);
        }
    }

    @Override
    public void removeEntry(Element element) {
        int index = this.allEntries.indexOf((Object)element);
        if (index == -1) {
            return;
        }
        this.visibleList.removeElement(element);
        this.allEntries.remove(index);
        this.array.removeNode(index);
        this.visibleList.m_93410_(this.visibleList.m_93517_());
    }
}

