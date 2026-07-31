/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.ChunkRenderTypeSet
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.block;

import ic2.api.util.DirectionList;
import ic2.core.block.cables.luminator.LuminatorBlock;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class LuminatorModel
extends BaseModel {
    public static final ShapeBuilder FULL = new ShapeBuilder().newQuad(0.16f, 0.16f, 0.01f, 15.84f, 15.84f, 1.0).addFaces(DirectionList.Z_AXIS, 0.0f, 0.0f, 16.0f, 16.0f).addFaces(DirectionList.VERTICAL, 0.0f, 15.0f, 16.0f, 16.0f).addFaces(DirectionList.X_AXIS, 15.0f, 0.0f, 16.0f, 16.0f).finish();
    public static final ShapeBuilder SMALL = new ShapeBuilder().newQuad(4.0, 13.0, 4.0, 12.0, 15.99f, 12.0).addFaces(DirectionList.VERTICAL, 0.0f, 0.0f, 16.0f, 16.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 15.0f, 16.0f, 16.0f).addFaces(DirectionList.X_AXIS, 15.0f, 0.0f, 16.0f, 16.0f).finish().newQuad(0.16f, 0.01f, 0.16f, 15.84f, 1.0, 15.84f).addFaces(DirectionList.VERTICAL, 0.0f, 0.0f, 16.0f, 16.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 15.0f, 16.0f, 16.0f).addFaces(DirectionList.X_AXIS, 15.0f, 0.0f, 16.0f, 16.0f).finish().newQuad(6.0, 3.0, 0.0, 10.0, 13.0, 2.0).addFaces(DirectionList.Z_AXIS, 0.0f, 0.0f, 16.0f, 16.0f).addFaces(DirectionList.VERTICAL, 0.0f, 15.0f, 16.0f, 16.0f).addFaces(DirectionList.X_AXIS, 15.0f, 0.0f, 16.0f, 16.0f).finish();
    BlockState state;
    boolean isItem;
    List<BakedQuad> itemQuads = CollectionUtils.createList();
    List<BakedQuad> baseQuads = CollectionUtils.createList();

    public LuminatorModel(BlockState state, TextureAtlasSprite sprite) {
        this.state = state;
        this.isItem = state.m_60734_().m_49966_() == state;
        this.setParticleTexture(sprite);
    }

    @Override
    public void init() {
        Direction side = (Direction)this.state.m_61143_((Property)LuminatorBlock.FACING);
        Tuple<Integer, Integer> result = QuadBaker.getModelRotation(side);
        if (((Boolean)this.state.m_61143_((Property)LuminatorBlock.BIG)).booleanValue()) {
            FULL.buildQuads(this.m_6160_(), BlockModelRotation.m_119153_((int)((Integer)result.m_14418_()), (int)((Integer)result.m_14419_())), null, true, this.baseQuads);
        } else {
            int index = side.m_122434_().m_122478_() ? side.m_122411_() : 2;
            SMALL.buildQuads(this.m_6160_(), index != 2 ? BlockModelRotation.X0_Y0 : BlockModelRotation.m_119153_((int)((Integer)result.m_14418_()), (int)((Integer)result.m_14419_())), null, true, index * 6, index * 6 + 6, this.baseQuads);
        }
        if (this.isItem) {
            this.itemQuads.addAll(IC2Models.generateItemQuads(-1, this.m_6160_(), IC2Models.getItemTransforms().f_111792_));
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side != null) {
            return LuminatorModel.empty();
        }
        if (state == null) {
            return this.itemQuads;
        }
        Function glass = (Function)extraData.get((ModelProperty)CamouflageProperty.INSTANCE);
        if (glass != null) {
            ObjectArrayList quads = new ObjectArrayList();
            if (!CamouflageShape.isTranslucent(type)) {
                quads.addAll(this.baseQuads);
            }
            quads.addAll(((CamouflageShape.QuadResults)glass.apply(type)).getQuads(type));
            return quads;
        }
        return this.baseQuads;
    }

    @OnlyIn(value=Dist.CLIENT)
    public static CamouflageShape.QuadResults createQuads(Level world, BlockPos pos, BlockState state) {
        DirectionList drawList = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            if (!Block.m_152444_((BlockState)state, (BlockGetter)world, (BlockPos)pos.m_121945_(dir), (Direction)dir, (BlockPos)pos)) continue;
            drawList = drawList.add(dir);
        }
        return LuminatorModel.createQuads(state, RandomSource.m_216335_((long)state.m_60726_(pos)), drawList);
    }

    public static CamouflageShape.QuadResults createQuads(BlockState state, RandomSource rand, DirectionList drawList) {
        List<K>[] quads = CollectionUtils.createLists(2);
        BakedModel model = Minecraft.m_91087_().m_91289_().m_110910_(state);
        ChunkRenderTypeSet set = IC2Models.getRenderTypes(state, rand);
        for (RenderType type : RenderType.m_110506_()) {
            List subQuads;
            if (!set.contains(type)) continue;
            int index = CamouflageShape.isTranslucent(type) ? 1 : 0;
            boolean added = false;
            for (Direction dir : drawList) {
                List subQuads2 = model.getQuads(state, dir, rand, ModelData.EMPTY, type);
                if (subQuads2.size() <= 0) continue;
                quads[index].addAll(subQuads2);
                added = true;
            }
            if (added || (subQuads = model.getQuads(state, null, rand, ModelData.EMPTY, type)).size() <= 0) continue;
            quads[index].addAll(subQuads);
            added = true;
        }
        return new CamouflageShape.QuadResults(quads[0], quads[1]);
    }

    @Override
    public boolean m_7547_() {
        return false;
    }

    @Override
    public boolean m_7541_() {
        return false;
    }
}

