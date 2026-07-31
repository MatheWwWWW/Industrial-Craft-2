/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.MultiLineLabel
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.Mth
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class MultiChoiceScreen
extends Screen {
    private final Component message;
    private MultiLineLabel multilineMessage = MultiLineLabel.f_94331_;
    protected Component mainButton;
    protected Component otherButton;
    protected Component cancelButton;
    protected final Consumer<Result> callback;

    public MultiChoiceScreen(Consumer<Result> callback, Component title, Component message, Component mainButton) {
        this(callback, title, message, mainButton, null, null);
    }

    public MultiChoiceScreen(Consumer<Result> callback, Component title, Component message, Component mainButton, Component otherButton, Component cancelButton) {
        super(title);
        this.callback = callback;
        this.message = message;
        this.mainButton = mainButton;
        this.otherButton = otherButton;
        this.cancelButton = cancelButton;
    }

    public Component m_142562_() {
        return CommonComponents.m_178398_((Component)super.m_142562_(), (Component)this.message);
    }

    protected void m_7856_() {
        super.m_7856_();
        this.multilineMessage = MultiLineLabel.m_94341_((Font)this.f_96547_, (FormattedText)this.message, (int)(this.f_96543_ - 50));
        this.addButtons(Mth.m_14045_((int)(this.messageTop() + this.messageHeight() + 20), (int)(this.f_96544_ / 6 + 96), (int)(this.f_96544_ - 24)));
    }

    protected void addButtons(int y) {
        boolean singleOption = this.otherButton == null && this.cancelButton == null;
        this.m_142416_((GuiEventListener)new ExtendedButton(this.f_96543_ / 2 - 50 - (singleOption ? 50 : 105), y, singleOption ? 200 : 100, 20, this.mainButton, T -> this.callback.accept(Result.MAIN)));
        if (singleOption) {
            return;
        }
        this.m_142416_((GuiEventListener)new ExtendedButton(this.f_96543_ / 2 - 50, y, 100, 20, this.otherButton, T -> this.callback.accept(Result.OTHER)));
        this.m_142416_((GuiEventListener)new ExtendedButton(this.f_96543_ / 2 - 50 + 105, y, 100, 20, this.cancelButton, T -> this.callback.accept(Result.CANCEL)));
    }

    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.m_7333_(stack);
        MultiChoiceScreen.m_93215_((PoseStack)stack, (Font)this.f_96547_, (Component)this.f_96539_, (int)(this.f_96543_ / 2), (int)this.titleTop(), (int)0xFFFFFF);
        this.multilineMessage.m_6276_(stack, this.f_96543_ / 2, this.messageTop());
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
    }

    private int titleTop() {
        int i = (this.f_96544_ - this.messageHeight()) / 2;
        return Mth.m_14045_((int)(i - 20 - 9), (int)10, (int)80);
    }

    private int messageTop() {
        return this.titleTop() + 20;
    }

    private int messageHeight() {
        return this.multilineMessage.m_5770_() * 9;
    }

    public boolean m_6913_() {
        return false;
    }

    public boolean m_7933_(int mouseButton, int mouseX, int mouseY) {
        if (mouseButton == 256) {
            this.callback.accept(Result.CANCEL);
            return true;
        }
        return super.m_7933_(mouseButton, mouseX, mouseY);
    }

    public static enum Result {
        MAIN,
        OTHER,
        CANCEL;


        public boolean isCancel() {
            return this == CANCEL;
        }

        public boolean isMain() {
            return this == MAIN;
        }

        public boolean isOther() {
            return this == OTHER;
        }
    }
}

