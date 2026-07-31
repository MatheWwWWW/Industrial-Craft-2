/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.ref;

import ic2.core.block.state.IIdProvider;

public enum Materials implements IIdProvider
{
    brass(16757760, true),
    bronze(0xFF8000, true),
    copper(16737280, false),
    gold(0xFFFF1E, true),
    iridium(0xEEEEEE, false),
    iron(0xC8C8C8, false),
    lead(9200780, false),
    silver(0xDCDCFF, true),
    steel(0x808080, false),
    tin(0xDCDCDC, false),
    zinc(0xFAF0F0, true);

    private final int color;
    private final boolean shiny;

    private Materials(int color, boolean shiny) {
        this.color = color;
        this.shiny = shiny;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public String getModelName() {
        return this.shiny ? "shiny" : "normal";
    }
}

