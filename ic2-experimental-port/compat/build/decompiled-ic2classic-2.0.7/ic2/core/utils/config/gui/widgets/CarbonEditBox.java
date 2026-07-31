/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.config.IListOwner;
import ic2.core.utils.config.gui.widgets.IOwnable;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class CarbonEditBox
extends EditBox
implements IOwnable {
    IListOwner owner;
    boolean f_94096_ = true;
    int innerDiff = 8;

    public CarbonEditBox(Font font, int x, int y, int width, int height) {
        super(font, x, y, width, height, (Component)Component.m_237119_());
    }

    public CarbonEditBox setInnerDiff(int innerDiff) {
        this.innerDiff = innerDiff;
        return this;
    }

    @Override
    public void setOwner(IListOwner owner) {
        this.owner = owner;
    }

    public void m_94178_(boolean focus) {
        super.m_94178_(focus);
        if (focus && this.owner != null) {
            this.owner.setActiveWidget((AbstractWidget)this);
        }
    }

    public int m_94210_() {
        return this.f_94096_ ? this.f_93618_ - this.innerDiff : this.f_93618_;
    }

    public void m_94182_(boolean value) {
        super.m_94182_(value);
        this.f_94096_ = value;
    }

    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.m_93696_() && this.owner != null && !this.owner.isActiveWidget((AbstractWidget)this)) {
            this.m_94178_(false);
        }
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
    }
}

