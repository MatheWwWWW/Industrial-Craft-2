/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.common.Tags$Blocks
 */
package ic2.core.platform.recipes.helpers;

import ic2.api.blocks.BlockRegistries;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class DefaultOreSearcher
implements Runnable {
    public static final Set<TagKey<Block>> IGNORED = CollectionUtils.createSet();
    final Set<TagKey<Block>> TEMP_IGNORED = CollectionUtils.createSet();

    @Override
    public void run() {
        this.TEMP_IGNORED.clear();
        this.add((TagKey<Block>)BlockTags.f_144264_, 3);
        this.add((TagKey<Block>)TagKey.m_203882_((ResourceKey)Registry.f_122901_, (ResourceLocation)new ResourceLocation("forge", "ores/tin")), 3);
        this.add((TagKey<Block>)TagKey.m_203882_((ResourceKey)Registry.f_122901_, (ResourceLocation)new ResourceLocation("forge", "ores/silver")), 5);
        this.add((TagKey<Block>)TagKey.m_203882_((ResourceKey)Registry.f_122901_, (ResourceLocation)new ResourceLocation("forge", "ores/uranium")), 6);
        this.add((TagKey<Block>)TagKey.m_203882_((ResourceKey)Registry.f_122901_, (ResourceLocation)new ResourceLocation("forge", "ores/aluminium")), 4);
        this.add((TagKey<Block>)Tags.Blocks.ORES_COAL, 2);
        this.add((TagKey<Block>)Tags.Blocks.ORES_DIAMOND, 6);
        this.add((TagKey<Block>)Tags.Blocks.ORES_EMERALD, 6);
        this.add((TagKey<Block>)Tags.Blocks.ORES_NETHERITE_SCRAP, 6);
        this.add((TagKey<Block>)Tags.Blocks.ORES_GOLD, 5);
        this.add((TagKey<Block>)Tags.Blocks.ORES_IRON, 3);
        this.add((TagKey<Block>)Tags.Blocks.ORES_LAPIS, 4);
        this.add((TagKey<Block>)Tags.Blocks.ORES_QUARTZ, 4);
        this.add((TagKey<Block>)Tags.Blocks.ORES_REDSTONE, 5);
        Registry.f_122824_.m_203613_().forEach(T -> {
            if (T.f_203868_().m_135815_().startsWith("ores/")) {
                this.add((TagKey<Block>)T, 1);
            }
        });
    }

    public void add(TagKey<Block> location, int level) {
        if (IGNORED.contains(location) || !this.TEMP_IGNORED.add(location)) {
            return;
        }
        BlockRegistries.registerOre(level, location);
    }
}

