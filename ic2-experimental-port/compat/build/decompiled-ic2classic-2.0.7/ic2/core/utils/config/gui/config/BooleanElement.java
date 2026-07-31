/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.config;

import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class BooleanElement
extends ConfigElement {
    private Button trueButton;
    private Button falseButton;

    public BooleanElement(IConfigNode node, IValueNode value) {
        super(node, value);
    }

    public BooleanElement(IConfigNode node, IArrayNode array, int index) {
        super(node, array, index);
    }

    @Override
    public void init() {
        super.init();
        int width = this.isArray() ? 72 : 36;
        this.trueButton = (Button)this.addChild(new ExtendedButton(0, 0, width, 18, (Component)Component.m_237115_((String)"gui.ic2.boolean.true"), this::onTrue), this.isArray() ? ConfigElement.GuiAlign.CENTER : ConfigElement.GuiAlign.RIGHT, this.isArray() ? width / 2 : width);
        this.falseButton = (Button)this.addChild(new ExtendedButton(0, 0, width, 18, (Component)Component.m_237115_((String)"gui.ic2.boolean.false"), this::onFalse), this.isArray() ? ConfigElement.GuiAlign.CENTER : ConfigElement.GuiAlign.RIGHT, this.isArray() ? -(width / 2) : 0);
        this.updateData();
    }

    private void updateData() {
        boolean isTrue = Boolean.parseBoolean(this.value.get());
        this.trueButton.f_93623_ = !isTrue;
        this.falseButton.f_93623_ = isTrue;
    }

    @Override
    public void tick() {
        super.tick();
        this.updateData();
    }

    protected void onTrue(Button button) {
        this.value.set("true");
    }

    protected void onFalse(Button button) {
        this.value.set("false");
    }
}

