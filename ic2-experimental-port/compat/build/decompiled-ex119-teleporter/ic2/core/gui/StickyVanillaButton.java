/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.Ic2Gui;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.VanillaButton;

public class StickyVanillaButton
extends VanillaButton {
    protected boolean isOn = false;

    public StickyVanillaButton(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, IClickHandler iClickHandler) {
        super(ic2Gui, n, n2, n3, n4, iClickHandler);
    }

    public void setOn(boolean bl) {
        this.isOn = bl;
    }

    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public StickyVanillaButton withDisableHandler(IEnableHandler iEnableHandler) {
        super.withDisableHandler(iEnableHandler);
        return this;
    }

    @Override
    public StickyVanillaButton withText(String string) {
        super.withText(string);
        return this;
    }

    @Override
    public StickyVanillaButton withTooltip(String string) {
        super.withTooltip(string);
        return this;
    }

    @Override
    protected boolean isActive(int n, int n2) {
        return this.isOn || super.isActive(n, n2);
    }
}

