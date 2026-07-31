/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.util;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

public enum VanillaColorBlockId {
    WOOL("wool"),
    STAINED_GLASS("stained_glass"),
    STAINED_GLASS_PANE("stained_glass_pane"),
    BED("bed"),
    CANDLE("candle"),
    BANNER("banner"),
    WALL_BANNER("wall_banner"),
    TERRACOTTA("terracotta"),
    GLAZED_TERRACOTTA("glazed_terracotta"),
    CONCRETE("concrete"),
    CONCRETE_POWDER("concrete_powder"),
    CARPET("carpet"),
    SHULKER_BOX("shulker_box");

    public final String id;

    private VanillaColorBlockId(String string2) {
        this.id = string2;
    }

    public boolean test(Block block) {
        return Registry.f_122824_.m_7981_((Object)block).m_135815_().contains(this.id);
    }
}

