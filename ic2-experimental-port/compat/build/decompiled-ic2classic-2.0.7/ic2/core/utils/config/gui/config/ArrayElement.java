/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.config;

import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.screen.ArrayScreen;
import ic2.core.utils.config.gui.widgets.CarbonIconButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ArrayElement
extends ConfigElement {
    Button textBox = (Button)this.addChild(new ExtendedButton(0, 0, 72, 18, (Component)Component.m_237115_((String)"gui.ic2.edit"), this::onPress));
    IArrayNode array;

    public ArrayElement(IConfigNode node) {
        super(node);
        this.array = node.asArray();
    }

    private void onPress(Button button) {
        this.mc.m_91152_((Screen)new ArrayScreen(this.node, this.mc.f_91080_, this.owner.getCustomTexture()));
    }

    @Override
    protected boolean createResetButtons(IValueNode value) {
        return true;
    }

    @Override
    protected boolean isReset() {
        return this.array.isChanged();
    }

    @Override
    public boolean isChanged() {
        return this.array.isChanged();
    }

    @Override
    public boolean isDefault() {
        return this.array.isDefault();
    }

    @Override
    protected void onDefault(CarbonIconButton button) {
        this.array.setDefault();
        this.updateValues();
    }

    @Override
    protected void onReset(CarbonIconButton button) {
        this.array.setPrevious();
        this.updateValues();
    }
}

