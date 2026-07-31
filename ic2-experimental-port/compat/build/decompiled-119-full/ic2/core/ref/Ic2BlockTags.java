/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.ref;

import ic2.core.IC2;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class Ic2BlockTags {
    public static final TagKey<Block> EMPTY = Ic2BlockTags.create("ic2:empty", "ic2:empty");
    public static final TagKey<Block> ORES = Ic2BlockTags.create("c:ores", "forge:ores");
    public static final TagKey<Block> LEAD_ORES = Ic2BlockTags.create("c:lead_ores", "forge:ores/lead");
    public static final TagKey<Block> SILVER_ORES = Ic2BlockTags.create("c:silver_ores", "forge:ores/silver");
    public static final TagKey<Block> TIN_ORES = Ic2BlockTags.create("c:tin_ores", "forge:ores/tin");
    public static final TagKey<Block> COPPER_BLOCKS = Ic2BlockTags.create("c:copper_blocks", "forge:storage_blocks/copper");
    public static final TagKey<Block> GOLD_BLOCKS = Ic2BlockTags.create("c:gold_blocks", "forge:storage_blocks/gold");
    public static final TagKey<Block> IRON_BLOCKS = Ic2BlockTags.create("c:iron_blocks", "forge:storage_blocks/iron");
    public static final TagKey<Block> LEAD_BLOCKS = Ic2BlockTags.create("c:lead_blocks", "forge:storage_blocks/lead");
    public static final TagKey<Block> SILVER_BLOCKS = Ic2BlockTags.create("c:silver_blocks", "forge:storage_blocks/silver");
    public static final TagKey<Block> TIN_BLOCKS = Ic2BlockTags.create("c:tin_blocks", "forge:storage_blocks/tin");
    public static final TagKey<Block> CABLE_CONNECTABLE = Ic2BlockTags.create("c:cable_connectable", "forge:cable_connectable");

    public static void init() {
    }

    private static TagKey<Block> create(String string, String string2) {
        ResourceLocation resourceLocation = new ResourceLocation(IC2.envProxy.isFabricEnv() ? string : string2);
        return TagKey.m_203882_((ResourceKey)Registry.f_122901_, (ResourceLocation)resourceLocation);
    }
}

