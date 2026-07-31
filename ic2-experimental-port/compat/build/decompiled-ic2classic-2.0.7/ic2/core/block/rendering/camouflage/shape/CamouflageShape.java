/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.BlockModelShaper
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.camouflage.shape;

import com.google.common.base.Objects;
import ic2.api.events.RetextureEvent;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.IQuadCache;
import ic2.core.block.rendering.camouflage.shape.FullCubeShape;
import ic2.core.block.rendering.camouflage.shape.SlabShape;
import ic2.core.block.rendering.camouflage.shape.StairShape;
import ic2.core.block.rendering.camouflage.shape.WallShape;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.misc.UVHelper;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

public abstract class CamouflageShape {
    public static final CamouflageShape FULL_CUBE = new FullCubeShape();
    public static final CamouflageShape SLAB = new SlabShape();
    public static final CamouflageShape STAIRS = new StairShape();
    public static final CamouflageShape WALL = new WallShape();

    protected abstract boolean isSideFull(BlockState var1, Direction var2);

    @OnlyIn(value=Dist.CLIENT)
    protected abstract void generateBakedQuads(BlockState var1, Direction var2, RetextureEvent.Rotation[] var3, List<BakedQuad> var4, List<BakedQuad>[] var5);

    @OnlyIn(value=Dist.CLIENT)
    public QuadResults generateQuads(CamouflageStorage storage, BlockState state, Level world, BlockPos pos, RenderType type) {
        BlockState theState;
        BlockModelShaper shapes = Minecraft.m_91087_().m_91289_().m_110907_();
        List<K>[] fullQuads = CollectionUtils.createLists(7);
        List<K>[] translucentQuads = CollectionUtils.createLists(7);
        for (Direction side : DirectionList.ALL) {
            IQuadCache cache;
            BlockEntity tile;
            if (this.isSideFull(state, side) && (tile = world.m_7702_(pos.m_121945_(side))) instanceof ICamouflagable && ((ICamouflagable)tile).isSideEnabled(side.m_122424_()) && storage.isColored(side) || (theState = (cache = storage.getCache(side)).getDisplayBlock()) == null) continue;
            CamouflageShape.getTypesForRender(theState, pos, shapes, fullQuads, translucentQuads, T -> {
                List quads = shapes.m_110893_(theState).getQuads(theState, cache.getSide(), RandomSource.m_216335_((long)theState.m_60726_(pos)), ModelData.EMPTY, type);
                if (quads.isEmpty()) {
                    return;
                }
                this.generateBakedQuads(state, side, cache.getRotations(), quads, (List<BakedQuad>[])T);
            });
        }
        List<K>[] quadList = CollectionUtils.createLists(2);
        for (Direction side : DirectionList.ALL) {
            theState = storage.getCache(side).getDisplayBlock();
            if (!Block.m_152444_((BlockState)(theState == null ? state : theState), (BlockGetter)world, (BlockPos)pos.m_121945_(side), (Direction)side, (BlockPos)pos) && pos != BlockPos.f_121853_) continue;
            quadList[0].addAll(fullQuads[side.m_122411_()]);
            quadList[1].addAll(translucentQuads[side.m_122411_()]);
        }
        quadList[0].addAll(fullQuads[6]);
        quadList[1].addAll(translucentQuads[6]);
        return new QuadResults(quadList[0], quadList[1]);
    }

    @OnlyIn(value=Dist.CLIENT)
    protected static BakedQuad createQuad(BakedQuad original, ShapeBuilder.Quad quad, int index, Direction dir, RetextureEvent.Rotation rotation) {
        return QuadBaker.createQuad(quad.getBox(), dir, new BlockElementFace(null, original.m_111304_() ? dir.m_122411_() * 50 + index : -1, "", new BlockFaceUV(UVHelper.rotateUVs(quad.getFace().f_111357_.f_111387_, rotation), rotation.getRotation())), original.m_173410_(), BlockModelRotation.X0_Y0, null, true);
    }

    @OnlyIn(value=Dist.CLIENT)
    protected static void getTypesForRender(BlockState state, BlockPos pos, BlockModelShaper shapes, List<BakedQuad>[] quads, List<BakedQuad>[] translucentQuads, Consumer<List<BakedQuad>[]> result) {
        for (RenderType type : IC2Models.getRenderTypes(state, RandomSource.m_216335_((long)state.m_60726_(pos)))) {
            result.accept(CamouflageShape.isTranslucent(type) ? translucentQuads : quads);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static boolean isTranslucent(RenderType type) {
        return Objects.equal((Object)type, (Object)RenderType.m_110466_()) || Objects.equal((Object)type, (Object)RenderType.m_110503_());
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class QuadResults {
        List<BakedQuad> normalQuads;
        List<BakedQuad> translucentQuads;

        public QuadResults(List<BakedQuad> normalQuads, List<BakedQuad> translucentQuads) {
            this.normalQuads = normalQuads;
            this.translucentQuads = translucentQuads;
        }

        public List<BakedQuad> getQuads(RenderType type) {
            return CamouflageShape.isTranslucent(type) ? this.translucentQuads : this.normalQuads;
        }
    }
}

