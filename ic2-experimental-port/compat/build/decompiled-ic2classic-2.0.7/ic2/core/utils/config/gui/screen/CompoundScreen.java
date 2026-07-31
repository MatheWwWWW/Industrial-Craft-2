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
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.ListScreen;
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

public class CompoundScreen
extends ListScreen {
    Screen prev;
    IConfigNode entry;
    ICompoundNode compound;
    List<DataType> type;
    Button applyValue;
    Runnable closeListener = null;

    public CompoundScreen(IConfigNode entry, ICompoundNode node, Screen prev, BackgroundTexture customTexture) {
        super(entry.getName(), customTexture);
        this.prev = prev;
        this.entry = entry;
        this.compound = node;
        this.type = entry.getDataType();
        this.compound.createTemp();
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        int x = this.f_96543_ / 2;
        int y = this.f_96544_;
        this.applyValue = (Button)this.m_142416_((GuiEventListener)new ExtendedButton(x - 82, y - 27, 80, 20, (Component)Component.m_237115_((String)"gui.ic2.apply"), this::apply));
        this.m_142416_((GuiEventListener)new ExtendedButton(x + 2, y - 27, 80, 20, (Component)Component.m_237115_((String)"gui.ic2.back"), this::goBack));
    }

    @Override
    protected boolean shouldHaveTooltips() {
        return true;
    }

    public void setAbortListener(Runnable run) {
        this.closeListener = run;
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        this.applyValue.f_93623_ = this.compound.isValid();
    }

    public void m_7379_() {
        this.notifyClose();
        this.f_96541_.m_91152_(this.prev);
    }

    private void apply(Button button) {
        this.compound.apply();
        this.f_96541_.m_91152_(this.prev);
    }

    private void notifyClose() {
        this.compound.setPrevious();
        if (this.closeListener == null) {
            return;
        }
        this.closeListener.run();
    }

    private void goBack(Button button) {
        if (this.compound.isChanged()) {
            this.f_96541_.m_91152_((Screen)new ConfirmScreen(T -> {
                if (T) {
                    this.notifyClose();
                }
                this.f_96541_.m_91152_((Screen)(T ? this.prev : this));
            }, (Component)Component.m_237115_((String)"gui.ic2.warn.changed"), (Component)Component.m_237115_((String)"gui.ic2.warn.changed.desc").m_130940_(ChatFormatting.GRAY)));
            return;
        }
        this.notifyClose();
        this.f_96541_.m_91152_(this.prev);
    }

    @Override
    protected void collectElements(Consumer<Element> elements) {
        List<IValueNode> values = this.compound.getValues();
        int m = this.type.size();
        for (int i = 0; i < m; ++i) {
            ConfigElement element = this.type.get(i).create(this.entry, values.get(i));
            if (element == null) continue;
            element.setName(this.compound.getName(i));
            elements.accept(element);
        }
    }

    @Override
    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, this.f_96539_, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)this.f_96539_) / 2), 8.0f, -1);
    }
}

