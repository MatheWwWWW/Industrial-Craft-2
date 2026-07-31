/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.ISuggestionRenderer;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.widgets.CarbonEditBox;
import ic2.core.utils.config.utils.ParseResult;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class RegistryElement
extends ConfigElement {
    EditBox edit;
    ParseResult<Boolean> result;
    ISuggestionRenderer renderer;

    public RegistryElement(IConfigNode node, IValueNode value, ISuggestionRenderer renderer) {
        super(node, value);
        this.renderer = renderer;
    }

    public RegistryElement(IConfigNode node, IArrayNode array, int index, ISuggestionRenderer renderer) {
        super(node, array, index);
        this.renderer = renderer;
    }

    public static DataType createForType(Class<?> clz, String defaultValue) {
        ISuggestionRenderer renderer = ISuggestionRenderer.Registry.getRendererForType(clz);
        return new DataType(false, defaultValue, (K, V) -> new RegistryElement((IConfigNode)K, (IValueNode)V, renderer), (K, V, E) -> new RegistryElement(K, V, E, renderer));
    }

    @Override
    public void init() {
        super.init();
        if (this.isArray()) {
            this.edit = this.addChild(new CarbonEditBox(this.font, 0, 0, 130, 18), ConfigElement.GuiAlign.CENTER, 0);
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
        }
    }

    @Override
    protected int getMaxX(int prevMaxX) {
        return prevMaxX - (this.renderer == null ? 0 : 25);
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
        Component result;
        super.m_6311_(poseStack, x, top, left, width, height, mouseX, mouseY, selected, partialTicks);
        if (this.renderer != null && (result = this.renderer.renderSuggestion(poseStack, this.value.get(), left + 20, top)) != null && mouseX >= left + 20 && mouseX <= left + 40 && mouseY >= top && mouseY <= top + 20) {
            this.owner.addTooltips(result);
        }
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
}

