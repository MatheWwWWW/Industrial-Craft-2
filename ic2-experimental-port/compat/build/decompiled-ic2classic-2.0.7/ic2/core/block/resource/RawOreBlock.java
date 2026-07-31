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

public class RawOreBlock
extends IC2Block
implements IBlockModel {
    private String name;

    public RawOreBlock(String blockName, float hardness, float resistance, int harvestLevel) {
        super(blockName, BlockBehaviour.Properties.m_60939_((Material)Material.f_76278_).m_60918_(SoundType.f_56742_).m_60913_(hardness, resistance).m_60999_());
        this.setHarvestTool(Tool.PICKAXE.withLevel(harvestLevel));
        this.name = blockName;
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
        return IC2Textures.getMappedEntriesBlockIC2("resources/raw").get(this.name);
    }
}

