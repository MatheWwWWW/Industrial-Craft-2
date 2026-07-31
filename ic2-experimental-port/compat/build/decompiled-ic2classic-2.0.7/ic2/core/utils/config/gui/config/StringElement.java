/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.screen.EditStringScreen;
import ic2.core.utils.config.gui.widgets.CarbonEditBox;
import ic2.core.utils.config.utils.ParseResult;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class StringElement
extends ConfigElement {
    EditBox edit;
    ParseResult<Boolean> result;

    public StringElement(IConfigNode node, IValueNode value) {
        super(node, value);
    }

    public StringElement(IConfigNode node, IArrayNode array, int index) {
        super(node, array, index);
    }

    @Override
    public void init() {
        super.init();
        if (this.isArray()) {
            this.edit = this.addChild(new CarbonEditBox(this.font, 0, 0, 150, 18), ConfigElement.GuiAlign.CENTER, 0);
            this.edit.m_94144_(this.value.get());
            this.edit.m_94151_(T -> {
                this.edit.m_94202_(0xE0E0E0);
                this.result = null;
                if (!T.isEmpty() && !(this.result = this.value.isValid((String)T)).getValue().booleanValue()) {
                    this.edit.m_94202_(0xFF0000);
                    return;
                }
                this.value.set((String)T);
            });
        } else {
            this.addChild(new ExtendedButton(0, 0, 72, 18, (Component)Component.m_237115_((String)"gui.ic2.edit"), this::onPress));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.edit != null) {
            this.edit.m_94120_();
        }
    }

    @Override
    public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
        super.m_6311_(poseStack, x, top, left, width, height, mouseX, mouseY, selected, partialTicks);
        if (this.edit != null && this.edit.m_5953_((double)mouseX, (double)mouseY) && this.result != null && !this.result.getValue().booleanValue()) {
            this.owner.addTooltips((Component)Component.m_237113_((String)this.result.getError().getMessage()));
        }
    }

    @Override
    protected void updateValues() {
        if (this.edit != null) {
            this.edit.m_94144_(this.value.get());
        }
    }

    private void onPress(Button button) {
        this.mc.m_91152_((Screen)new EditStringScreen(this.mc.f_91080_, this.name, this.node, this.value, this.owner.getCustomTexture()));
    }
}

