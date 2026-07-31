/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.type;

import ic2.core.block.state.IIdProvider;

public enum MiscResourceType implements IIdProvider
{
    ashes(0),
    iridium_ore(1),
    iridium_shard(2),
    matter(3),
    resin(4),
    slag(5),
    iodine(6),
    water_sheet(7),
    lava_sheet(8);

    private final int id;

    private MiscResourceType(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public int getId() {
        return this.id;
    }
}

