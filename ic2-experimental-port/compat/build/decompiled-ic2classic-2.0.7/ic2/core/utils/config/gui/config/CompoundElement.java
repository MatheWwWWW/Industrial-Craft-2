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
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.screen.CompoundScreen;
import ic2.core.utils.config.gui.widgets.CarbonIconButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class CompoundElement
extends ConfigElement {
    Button textBox;
    ICompoundNode compound;

    public CompoundElement(IConfigNode node) {
        super(node);
        this.compound = node.asCompound();
    }

    public CompoundElement(IConfigNode node, IArrayNode parent, ICompoundNode compound) {
        super(node, parent);
        this.compound = compound;
    }

    @Override
    public void init() {
        super.init();
        this.textBox = (Button)this.addChild(new ExtendedButton(0, 0, this.isArray() ? 144 : 72, 18, (Component)Component.m_237115_((String)"gui.ic2.edit"), this::onPress));
    }

    private void onPress(Button button) {
        this.mc.m_91152_((Screen)new CompoundScreen(this.node, this.compound, this.mc.f_91080_, this.owner.getCustomTexture()));
    }

    @Override
    protected boolean createResetButtons(IValueNode value) {
        return true;
    }

    @Override
    protected int indexOf() {
        return this.array.indexOf(this.compound);
    }

    @Override
    protected boolean isReset() {
        return this.isArray() || this.compound.isChanged();
    }

    @Override
    public boolean isChanged() {
        return this.compound.isChanged();
    }

    @Override
    public boolean isDefault() {
        return this.compound.isDefault();
    }

    @Override
    protected void onDefault(CarbonIconButton button) {
        this.compound.setDefault();
        this.updateValues();
    }

    @Override
    protected void onReset(CarbonIconButton button) {
        this.compound.setPrevious();
        this.updateValues();
    }
}

