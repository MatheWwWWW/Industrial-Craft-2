/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.widgets.CarbonEditBox;
import ic2.core.utils.config.utils.ParseResult;
import net.minecraft.network.chat.Component;

public class NumberElement
extends ConfigElement {
    CarbonEditBox textBox;
    ParseResult<Boolean> result;

    public NumberElement(IConfigNode node, IValueNode value) {
        super(node, value);
    }

    public NumberElement(IConfigNode node, IArrayNode array, int index) {
        super(node, array, index);
    }

    @Override
    public void init() {
        super.init();
        this.textBox = this.addChild(new CarbonEditBox(this.font, 0, 0, this.isArray() ? 150 : 70, 18), this.isArray() ? ConfigElement.GuiAlign.CENTER : ConfigElement.GuiAlign.RIGHT, 1);
        this.textBox.m_94144_(this.value.get());
        this.textBox.m_94151_(T -> {
            this.textBox.m_94202_(0xE0E0E0);
            this.result = null;
            if (!T.isEmpty()) {
                this.result = this.value.isValid((String)T);
                if (!this.result.getValue().booleanValue()) {
                    this.textBox.m_94202_(0xFF0000);
                    return;
                }
                this.value.set((String)T);
            }
        });
    }

    @Override
    public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
        super.m_6311_(poseStack, x, top, left, width, height, mouseX, mouseY, selected, partialTicks);
        if (this.textBox.m_5953_(mouseX, mouseY) && this.result != null && !this.result.getValue().booleanValue()) {
            this.owner.addTooltips((Component)Component.m_237113_((String)this.result.getError().getMessage()));
        }
    }

    @Override
    protected void updateValues() {
        this.textBox.m_94144_(this.value.get());
    }
}

