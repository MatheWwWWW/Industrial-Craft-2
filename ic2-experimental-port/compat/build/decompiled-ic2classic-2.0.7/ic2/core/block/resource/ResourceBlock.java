/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.resource;

import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.helpers.Tool;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ResourceBlock
extends IC2Block
implements IBlockModel {
    private String name;

    public ResourceBlock(String blockName, Resource resource) {
        super(blockName, ResourceBlock.getProperties(resource));
        this.setHarvestTool(Tool.PICKAXE.withLevel(resource.harvestLevel));
        this.name = blockName;
    }

    private static BlockBehaviour.Properties getProperties(Resource resource) {
        return BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60918_(SoundType.f_56743_).m_60913_(resource.hardness, resource.explosion).m_60999_();
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.singletonList(this.m_49966_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("resources/storage").get(this.name.equals("uranium_block") ? (side == Direction.UP ? this.name + "_top" : this.name + "_sides") : this.name);
    }

    public static class Resource {
        public static final Resource CHARCOAL = new Resource(0, 5.0f, 6.0f);
        public static final Resource WEAK_METAL = new Resource(1, 4.0f, 6.0f);
        public static final Resource STRONG_METAL = new Resource(2, 5.0f, 6.0f);
        int harvestLevel;
        float hardness;
        float explosion;

        public Resource(int harvestLevel, float hardness, float explosion) {
            this.harvestLevel = harvestLevel;
            this.hardness = hardness;
            this.explosion = explosion;
        }
    }
}

