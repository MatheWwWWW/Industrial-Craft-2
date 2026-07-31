/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
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
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ElementList;
import ic2.core.utils.config.utils.ParseResult;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class EditStringScreen
extends Screen {
    private static final BackgroundTexture DEFAULT = BackgroundTexture.of(f_93096_).build();
    Screen parent;
    IConfigNode node;
    IValueNode value;
    EditBox textBox;
    boolean valid = true;
    BackgroundTexture texture;
    ParseResult<Boolean> result;

    public EditStringScreen(Screen parent, Component name, IConfigNode node, IValueNode value, BackgroundTexture texture) {
        super(name);
        this.parent = parent;
        this.node = node;
        this.value = value;
        this.value.createTemp();
        this.texture = texture == null ? DEFAULT : texture;
    }

    protected void m_7856_() {
        super.m_7856_();
        int x = this.f_96543_ / 2 - 100;
        Button apply = (Button)this.m_142416_((GuiEventListener)new ExtendedButton(x + 10, 160, 85, 20, (Component)Component.m_237115_((String)"gui.ic2.apply"), this::save));
        this.m_142416_((GuiEventListener)new ExtendedButton(x + 105, 160, 85, 20, (Component)Component.m_237115_((String)"gui.ic2.cancel"), this::cancel));
        this.textBox = new EditBox(this.f_96547_, x, 113, 200, 18, (Component)Component.m_237119_());
        this.m_142416_((GuiEventListener)this.textBox);
        this.textBox.m_94144_(this.value.get());
        this.textBox.m_94151_(T -> {
            this.textBox.m_94202_(0xE0E0E0);
            this.valid = true;
            this.result = this.value.isValid((String)T);
            if (!this.result.getValue().booleanValue()) {
                this.textBox.m_94202_(0xFF0000);
                this.valid = false;
            }
            apply.f_93623_ = this.valid;
            if (this.valid) {
                this.value.set(this.textBox.m_94155_());
            }
        });
    }

    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        ElementList.renderBackground(0, this.f_96543_, 0, this.f_96544_, 0.0f, this.texture);
        ElementList.renderListOverlay(0, this.f_96543_, 103, 142, this.f_96543_, this.f_96544_, this.texture);
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, this.f_96539_, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)this.f_96539_) / 2), 85.0f, -1);
        if (this.textBox.m_5953_((double)mouseX, (double)mouseY) && this.result != null && !this.result.getValue().booleanValue()) {
            this.m_96602_(stack, (Component)Component.m_237113_((String)this.result.getError().getMessage()), mouseX, mouseY);
        }
    }

    public void m_7379_() {
        this.value.setPrevious();
        this.f_96541_.m_91152_(this.parent);
    }

    private void save(Button button) {
        if (!this.valid) {
            return;
        }
        this.value.apply();
        this.f_96541_.m_91152_(this.parent);
    }

    private void cancel(Button button) {
        if (this.value.isChanged()) {
            this.f_96541_.m_91152_((Screen)new ConfirmScreen(T -> {
                if (T) {
                    this.value.setPrevious();
                }
                this.f_96541_.m_91152_((Screen)(T ? this.parent : this));
            }, (Component)Component.m_237115_((String)"gui.ic2.warn.changed"), (Component)Component.m_237115_((String)"gui.ic2.warn.changed.desc").m_130940_(ChatFormatting.GRAY)));
            return;
        }
        this.value.setPrevious();
        this.f_96541_.m_91152_(this.parent);
    }
}

