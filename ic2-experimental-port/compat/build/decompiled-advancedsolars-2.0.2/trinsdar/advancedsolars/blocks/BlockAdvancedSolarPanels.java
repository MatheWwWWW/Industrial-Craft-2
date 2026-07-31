/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.block.base.drops.IBlockDropProvider
 *  ic2.core.block.generators.BaseGeneratorBlock
 *  ic2.core.platform.registries.IC2Blocks
 *  ic2.core.platform.rendering.IC2Textures
 *  ic2.core.platform.rendering.features.ITextureProvider
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package trinsdar.advancedsolars.blocks;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.generators.BaseGeneratorBlock;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.ITextureProvider;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockAdvancedSolarPanels
extends BaseGeneratorBlock {
    public BlockAdvancedSolarPanels(String blockName, BlockEntityType<? extends BlockEntity> type) {
        super(blockName, IBlockDropProvider.SELF, (ITextureProvider)new SolarTextureProvider("advanced_solars", blockName), type);
        IC2Blocks.registerBlock((Block)this);
    }

    public static class SolarTextureProvider
    implements ITextureProvider {
        String path;
        String mod;

        public SolarTextureProvider(String mod, String path) {
            this.mod = mod;
            this.path = path;
        }

        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState blockState, Direction direction) {
            Map map = IC2Textures.getMappedEntriesBlock((String)this.mod, (String)this.path);
            return switch (direction) {
                case Direction.UP -> (TextureAtlasSprite)map.get("top");
                case Direction.DOWN -> (TextureAtlasSprite)map.get("bottom");
                default -> (TextureAtlasSprite)map.get("side");
            };
        }
    }
}

