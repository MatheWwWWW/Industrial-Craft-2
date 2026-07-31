/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.block;

import ic2.api.util.DirectionList;
import ic2.core.block.crops.PlanterPotBlock;
import ic2.core.block.rendering.props.BlockProperty;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class PlanterPotModel
extends BaseModel {
    static final ShapeBuilder SHAPE = new ShapeBuilder().newQuad(2.0, 0.0, 2.0, 14.0, 4.0, 14.0).addFaces(DirectionList.VERTICAL, 2.0f, 2.0f, 14.0f, 14.0f).addFaces(DirectionList.HORIZONTAL, 2.0f, 12.0f, 14.0f, 16.0f).finish().newQuad(1.0, 4.0, 1.0, 15.0, 9.0, 15.0).addFaces(DirectionList.VERTICAL, 1.0f, 1.0f, 15.0f, 15.0f).addFaces(DirectionList.HORIZONTAL, 1.0f, 7.0f, 15.0f, 12.0f).finish().newQuad(0.0, 9.0, 0.0, 16.0, 15.0, 16.0).addFaces(DirectionList.DOWN, 0.0f, 0.0f, 16.0f, 16.0f).addFaces(DirectionList.HORIZONTAL, 0.0f, 1.0f, 16.0f, 7.0f).finish().newQuad(0.0, 15.0, 0.0, 16.0, 16.0, 1.0).addCube(new float[][]{{0.0f, 0.0f, 16.0f, 1.0f}, {0.0f, 0.0f, 16.0f, 1.0f}, {0.0f, 0.0f, 16.0f, 1.0f}, {0.0f, 0.0f, 16.0f, 1.0f}, {15.0f, 0.0f, 16.0f, 1.0f}, {0.0f, 0.0f, 1.0f, 1.0f}}).finish().newQuad(0.0, 15.0, 15.0, 16.0, 16.0, 16.0).addCube(new float[][]{{0.0f, 15.0f, 16.0f, 16.0f}, {0.0f, 15.0f, 16.0f, 16.0f}, {0.0f, 0.0f, 16.0f, 1.0f}, {0.0f, 0.0f, 16.0f, 1.0f}, {0.0f, 0.0f, 1.0f, 1.0f}, {15.0f, 0.0f, 16.0f, 1.0f}}).finish().newQuad(0.0, 15.0, 1.0, 1.0, 16.0, 15.0).addCube(new float[][]{{0.0f, 1.0f, 1.0f, 15.0f}, {0.0f, 1.0f, 1.0f, 15.0f}, {0.0f, 0.0f, 1.0f, 1.0f}, {15.0f, 0.0f, 16.0f, 1.0f}, {1.0f, 0.0f, 15.0f, 1.0f}, {1.0f, 0.0f, 15.0f, 1.0f}}).finish().newQuad(15.0, 15.0, 1.0, 16.0, 16.0, 15.0).addCube(new float[][]{{15.0f, 1.0f, 16.0f, 15.0f}, {15.0f, 1.0f, 16.0f, 15.0f}, {0.0f, 0.0f, 1.0f, 1.0f}, {15.0f, 0.0f, 16.0f, 1.0f}, {1.0f, 0.0f, 15.0f, 1.0f}, {1.0f, 0.0f, 15.0f, 1.0f}}).finish();
    static final ShapeBuilder SOIL = new ShapeBuilder().newQuad(0.0, 9.0, 0.0, 16.0, 15.0, 16.0).addFace(Direction.UP, 0.0f, 0.0f, 16.0f, 16.0f).finish();
    TextureAtlasSprite[] textures;
    Block block;
    Map<Block, List<BakedQuad>> mappedQuads = CollectionUtils.createMap();
    List<BakedQuad> quads = CollectionUtils.createList();

    public PlanterPotModel(TextureAtlasSprite[] textures) {
        this.textures = textures;
        this.setParticleTexture(textures[0]);
    }

    @Override
    public void init() {
        SHAPE.buildQuads(this.textures, BlockModelRotation.X0_Y0, null, true, this.quads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side != null) {
            return PlanterPotModel.empty();
        }
        ObjectArrayList result = new ObjectArrayList(this.quads);
        if (extraData.has((ModelProperty)BlockProperty.INSTANCE)) {
            this.block = (Block)extraData.get((ModelProperty)BlockProperty.INSTANCE);
        }
        if (this.block != null && this.block != Blocks.f_50016_) {
            ObjectArrayList mapped = this.mappedQuads.get(this.block);
            if (mapped == null) {
                mapped = new ObjectArrayList();
                this.mappedQuads.put(this.block, (List<BakedQuad>)mapped);
                for (BakedQuad quad : Minecraft.m_91087_().m_91289_().m_110910_(this.block.m_49966_()).getQuads(this.block.m_49966_(), Direction.UP, rand, ModelData.EMPTY, type)) {
                    SOIL.buildQuads(quad.m_173410_(), BlockModelRotation.X0_Y0, null, true, (List<BakedQuad>)mapped);
                }
            }
            result.addAll(mapped);
        }
        return result;
    }

    @Override
    public boolean m_7547_() {
        return true;
    }

    @Override
    public ItemOverrides m_7343_() {
        return new PlotOverride();
    }

    public class PlotOverride
    extends ItemOverrides {
        public BakedModel m_173464_(BakedModel p_239290_1_, ItemStack p_239290_2_, ClientLevel p_239290_3_, LivingEntity p_239290_4_, int randomValue) {
            PlanterPotModel.this.block = PlanterPotBlock.getId(p_239290_2_);
            return super.m_173464_(p_239290_1_, p_239290_2_, p_239290_3_, p_239290_4_, randomValue);
        }
    }
}

