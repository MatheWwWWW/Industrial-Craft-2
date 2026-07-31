/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 */
package ic2.core.platform.registries;

import ic2.api.blocks.DyeableMap;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class ColorMaps {
    public static final DyeableMap CFOAM_BLOCKS = new DyeableMap();
    public static final DyeableMap CFOAM_STAIRS = new DyeableMap();
    public static final DyeableMap CFOAM_SLABS = new DyeableMap();
    public static final DyeableMap CFOAM_SURFACE = new DyeableMap();
    public static final DyeableMap CFOAM_PANELS = new DyeableMap();
    public static final DyeableMap CFOAM_WALLS = new DyeableMap();
    public static final DyeableMap CFOAM_WOOL = new DyeableMap();
    public static final DyeableMap CFOAM_CARPET = new DyeableMap();
    public static final Map<DyeColor, Item> PAINTER = CollectionUtils.createMap();
    static TagKey<Item>[] COLOR_TAGS = null;

    public static TagKey<Item> getTagForDye(DyeColor myColor) {
        if (COLOR_TAGS == null) {
            COLOR_TAGS = new TagKey[16];
            for (DyeColor color : DyeColor.values()) {
                ColorMaps.COLOR_TAGS[color.m_41060_()] = ItemTags.create((ResourceLocation)new ResourceLocation("forge", "dyes/" + color.m_41065_()));
            }
        }
        return COLOR_TAGS[myColor.m_41060_()];
    }
}

