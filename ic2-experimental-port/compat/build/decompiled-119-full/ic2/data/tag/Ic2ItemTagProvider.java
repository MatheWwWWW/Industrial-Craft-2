/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.tags.BlockTagsProvider
 *  net.minecraft.data.tags.TagsProvider$TagAppender
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 */
package ic2.data.tag;

import ic2.compat.AbstractItemTagProvider;
import ic2.core.IC2;
import ic2.core.ref.Ic2ItemTags;
import ic2.core.ref.Ic2Items;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Ic2ItemTagProvider
extends AbstractItemTagProvider {
    public Ic2ItemTagProvider(DataGenerator dataGenerator) {
        super(dataGenerator, new BlockTagsProvider(dataGenerator));
    }

    protected TagsProvider.TagAppender<Item> tag(TagKey<Item> tagKey) {
        return this.m_206424_(tagKey);
    }

    protected void m_6577_() {
        this.tag(Ic2ItemTags.COAL_DUSTS).m_126582_((Object)Ic2Items.COAL_DUST);
        this.tag(Ic2ItemTags.COPPER_DUSTS).m_126582_((Object)Ic2Items.COPPER_DUST);
        this.tag(Ic2ItemTags.DIAMOND_DUSTS).m_126582_((Object)Ic2Items.DIAMOND_DUST);
        this.tag(Ic2ItemTags.GOLD_DUSTS).m_126582_((Object)Ic2Items.GOLD_DUST);
        this.tag(Ic2ItemTags.IRON_DUSTS).m_126582_((Object)Ic2Items.IRON_DUST);
        this.tag(Ic2ItemTags.LAPIS_DUSTS).m_126582_((Object)Ic2Items.LAPIS_DUST);
        this.tag(Ic2ItemTags.LEAD_DUSTS).m_126582_((Object)Ic2Items.LEAD_DUST);
        this.tag(Ic2ItemTags.OBSIDIAN_DUSTS).m_126582_((Object)Ic2Items.OBSIDIAN_DUST);
        this.tag(Ic2ItemTags.SILVER_DUSTS).m_126582_((Object)Ic2Items.SILVER_DUST);
        this.tag(Ic2ItemTags.STONE_DUSTS).m_126582_((Object)Ic2Items.STONE_DUST);
        this.tag(Ic2ItemTags.SULFUR_DUSTS).m_126582_((Object)Ic2Items.SULFUR_DUST);
        this.tag(Ic2ItemTags.TIN_DUSTS).m_126582_((Object)Ic2Items.TIN_DUST);
        this.tag(Ic2ItemTags.IRIDIUM_NUGGETS).m_126582_((Object)Ic2Items.IRIDIUM_SHARD);
        this.tag(Ic2ItemTags.IRON_PLATES).m_126582_((Object)Ic2Items.IRON_PLATE);
        this.tag(Ic2ItemTags.GOLD_PLATES).m_126582_((Object)Ic2Items.GOLD_PLATE);
        this.tag(Ic2ItemTags.LEAD_PLATES).m_126582_((Object)Ic2Items.LEAD_PLATE);
        this.tag(Ic2ItemTags.BRONZE_PLATES).m_126582_((Object)Ic2Items.BRONZE_PLATE);
        this.tag(Ic2ItemTags.TIN_PLATES).m_126582_((Object)Ic2Items.TIN_PLATE);
        this.tag(Ic2ItemTags.COPPER_PLATES).m_126582_((Object)Ic2Items.COPPER_PLATE);
        this.tag(Ic2ItemTags.LAPIS_PLATES).m_126582_((Object)Ic2Items.LAPIS_PLATE);
        this.tag(Ic2ItemTags.OBSIDIAN_PLATES).m_126582_((Object)Ic2Items.OBSIDIAN_PLATE);
        this.tag(Ic2ItemTags.STEEL_PLATES).m_126582_((Object)Ic2Items.STEEL_PLATE);
        this.tag(Ic2ItemTags.BRONZE_INGOTS).m_126582_((Object)Ic2Items.BRONZE_INGOT);
        this.tag(Ic2ItemTags.LEAD_INGOTS).m_126582_((Object)Ic2Items.LEAD_INGOT);
        this.tag(Ic2ItemTags.PLUTONIUM_INGOTS);
        this.tag(Ic2ItemTags.SILVER_INGOTS).m_126582_((Object)Ic2Items.SILVER_INGOT);
        this.tag(Ic2ItemTags.STEEL_INGOTS).m_126582_((Object)Ic2Items.STEEL_INGOT);
        this.tag(Ic2ItemTags.TIN_INGOTS).m_126582_((Object)Ic2Items.TIN_INGOT);
        this.tag(Ic2ItemTags.URANIUM_INGOTS).m_126582_((Object)Ic2Items.URANIUM_INGOT);
        this.tag(Ic2ItemTags.LEAD_ORES).m_126582_((Object)Ic2Items.LEAD_ORE);
        this.tag(Ic2ItemTags.LEAD_ORES).m_126582_((Object)Ic2Items.DEEPSLATE_LEAD_ORE);
        this.tag(Ic2ItemTags.LEAD_RAW_ORES).m_126582_((Object)Ic2Items.RAW_LEAD);
        this.tag(Ic2ItemTags.SILVER_ORES);
        this.tag(Ic2ItemTags.TIN_ORES).m_126582_((Object)Ic2Items.TIN_ORE);
        this.tag(Ic2ItemTags.TIN_ORES).m_126582_((Object)Ic2Items.DEEPSLATE_TIN_ORE);
        this.tag(Ic2ItemTags.TIN_RAW_ORES).m_126582_((Object)Ic2Items.RAW_TIN);
        this.tag(Ic2ItemTags.URANIUM_ORES).m_126582_((Object)Ic2Items.URANIUM_ORE);
        this.tag(Ic2ItemTags.URANIUM_ORES).m_126582_((Object)Ic2Items.DEEPSLATE_URANIUM_ORE);
        this.tag(Ic2ItemTags.URANIUM_RAW_ORES).m_126582_((Object)Ic2Items.RAW_URANIUM);
        this.tag(Ic2ItemTags.ORES).m_206428_(Ic2ItemTags.LEAD_ORES).m_206428_(Ic2ItemTags.SILVER_ORES).m_206428_(Ic2ItemTags.TIN_ORES).m_206428_(Ic2ItemTags.URANIUM_ORES);
        this.tag(Ic2ItemTags.BRONZE_BLOCKS).m_126582_((Object)Ic2Items.BRONZE_BLOCK);
        this.tag(Ic2ItemTags.STEEL_BLOCKS).m_126582_((Object)Ic2Items.STEEL_BLOCK);
        this.tag(Ic2ItemTags.DIAMONDS).m_126582_((Object)Ic2Items.INDUTRIAL_DIAMOND);
        if (IC2.envProxy.isFabricEnv()) {
            this.tag(Ic2ItemTags.WOODEN_CHESTS).m_126582_((Object)Items.f_42009_);
        }
    }
}

