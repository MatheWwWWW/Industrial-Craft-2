/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.upgrade;

import ic2.core.Ic2Gui;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.TextBox;

class MoveableTextBox
extends TextBox {
    private IEnableHandler moveHandler;
    protected int normalX;
    protected int normalY;
    protected int shiftedX;
    protected int shiftedY;

    public MoveableTextBox(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, int n5, int n6, String string) {
        super(ic2Gui, n, n2, n5, n6, string);
        this.normalX = n;
        this.normalY = n2;
        this.shiftedX = n3;
        this.shiftedY = n4;
    }

    public MoveableTextBox withMoveHandler(IEnableHandler iEnableHandler) {
        this.moveHandler = iEnableHandler;
        return this;
    }

    public boolean isMoved() {
        return this.moveHandler != null && this.moveHandler.isEnabled();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isMoved()) {
            this.x = this.shiftedX;
            this.y = this.shiftedY;
        } else {
            this.x = this.normalX;
            this.y = this.normalY;
        }
    }
}

