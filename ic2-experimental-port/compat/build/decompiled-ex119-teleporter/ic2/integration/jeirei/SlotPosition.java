/*
 * Decompiled with CFR 0.152.
 */
package ic2.integration.jeirei;

import ic2.core.gui.SlotGrid;

public class SlotPosition {
    private final int x;
    private final int y;
    private final SlotGrid.SlotStyle style;

    public SlotPosition(int n, int n2) {
        this(n, n2, SlotGrid.SlotStyle.Normal);
    }

    public SlotPosition(SlotPosition slotPosition, int n, int n2) {
        this(slotPosition.x + n, slotPosition.y + n2, slotPosition.style);
    }

    public SlotPosition(int n, int n2, SlotGrid.SlotStyle slotStyle) {
        this.x = n;
        this.y = n2;
        this.style = slotStyle;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public SlotGrid.SlotStyle getStyle() {
        return this.style;
    }
}

