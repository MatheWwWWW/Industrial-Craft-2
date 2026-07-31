/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.Tags$Blocks
 *  net.minecraftforge.common.TierSortingRegistry
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.utils.helpers;

import ic2.core.platform.registries.IC2Tags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.ForgeRegistries;

public final class Tool {
    public static final Tool PICKAXE = new Tool(ToolType.PICKAXE);
    public static final Tool AXE = new Tool(ToolType.AXE);
    public static final Tool SHOVEL = new Tool(ToolType.SHOVEL);
    public static final Tool HOE = new Tool(ToolType.HOE);
    final ToolType type;
    final int level;

    private Tool(ToolType type) {
        this(type, 0);
    }

    private Tool(ToolType type, int level) {
        this.type = type;
        this.level = level;
    }

    public Tool withLevel(int level) {
        return new Tool(this.type, level);
    }

    public int getLevel() {
        return this.level;
    }

    public ToolType getType() {
        return this.type;
    }

    public boolean isCorrectTool(BlockState state) {
        return this.type.isCorrectTool(state, this.level);
    }

    public boolean isCorrectTool(BlockState state, Tier tier) {
        return this.type.isCorrectTool(state, tier);
    }

    public void register(Block block) {
        this.type.registerBlock(block, this.level);
    }

    public static enum ToolType {
        PICKAXE((TagKey<Block>)BlockTags.f_144282_),
        AXE((TagKey<Block>)BlockTags.f_144280_),
        SHOVEL((TagKey<Block>)BlockTags.f_144283_),
        HOE((TagKey<Block>)BlockTags.f_144281_);

        TagKey<Block> type;

        private ToolType(TagKey<Block> type) {
            this.type = type;
        }

        void registerBlock(Block block, int level) {
            ResourceLocation tag = this.getTagForLevel(level).f_203868_();
            IC2Tags.registerTags(new ResourceLocation(tag.m_135827_(), "tags/blocks/" + tag.m_135815_()), ForgeRegistries.BLOCKS, block);
            IC2Tags.registerTags(new ResourceLocation(this.type.f_203868_().m_135827_(), "tags/blocks/" + this.type.f_203868_().m_135815_()), ForgeRegistries.BLOCKS, block);
        }

        boolean isCorrectTool(BlockState state, int level) {
            return this.isCorrectTool(state, this.getTierForLevel(level));
        }

        boolean isCorrectTool(BlockState state, Tier tier) {
            return state.m_204336_(this.type) && TierSortingRegistry.isCorrectTierForDrops((Tier)tier, (BlockState)state);
        }

        private Tier getTierForLevel(int level) {
            switch (level) {
                case 0: {
                    return Tiers.WOOD;
                }
                case 1: {
                    return Tiers.STONE;
                }
                case 2: {
                    return Tiers.IRON;
                }
                case 3: {
                    return Tiers.DIAMOND;
                }
                case 4: {
                    return Tiers.NETHERITE;
                }
            }
            throw new IllegalStateException("Unsupported Tier");
        }

        private TagKey<Block> getTagForLevel(int level) {
            switch (level) {
                case 0: {
                    return Tags.Blocks.NEEDS_WOOD_TOOL;
                }
                case 1: {
                    return BlockTags.f_144286_;
                }
                case 2: {
                    return BlockTags.f_144285_;
                }
                case 3: {
                    return BlockTags.f_144284_;
                }
                case 4: {
                    return Tags.Blocks.NEEDS_NETHERITE_TOOL;
                }
            }
            throw new IllegalStateException("Unsupported Tier");
        }
    }
}

