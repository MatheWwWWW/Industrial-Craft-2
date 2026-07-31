/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.bytes.Byte2ObjectMap
 *  it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.block.tubes;

import com.mojang.math.Vector3f;
import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.IAdvancedTubeBlock;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.rendering.props.CableProperty;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

@OnlyIn(value=Dist.CLIENT)
public class CableModel
extends BaseModel {
    protected List<BakedQuad>[] quads = CollectionUtils.createLists(64);
    protected List<BakedQuad>[] anchor = CollectionUtils.createLists(64);
    protected ITubeBlock tube;
    protected IAdvancedTubeBlock advTube;
    protected BlockState state;

    public CableModel(ITubeBlock tube, BlockState state) {
        this.tube = tube;
        this.advTube = tube instanceof IAdvancedTubeBlock ? (IAdvancedTubeBlock)((Object)tube) : null;
        this.state = state;
    }

    @Override
    public void init() {
        this.setParticleTexture(this.tube.getTexture(this.state, false, null, Direction.NORTH));
        float radius = this.tube.getRadius(this.state);
        float min = 8.0f - radius;
        float max = 8.0f + radius;
        TextureAtlasSprite sprite = IC2Models.getSafeIcon(IC2Textures.getMappedEntriesBlockIC2("misc").get("mining_pipe_shaft"));
        Byte2ObjectMap<BakedQuad> coreQuads = this.generateCoreQuads(min, max);
        Byte2ObjectOpenHashMap sideQuads = new Byte2ObjectOpenHashMap();
        Byte2ObjectOpenHashMap anchorQuadList = new Byte2ObjectOpenHashMap();
        for (Direction side : DirectionList.ALL) {
            sideQuads.put((byte)side.m_122411_(), this.generateSideQuads(side, min, max));
            anchorQuadList.put((byte)side.m_122411_(), this.generateAnchorQuads(sprite, side, min, max));
        }
        for (int i = 0; i < 64; ++i) {
            DirectionList rotation = DirectionList.ofNumber(i);
            List<BakedQuad> quadList = this.quads[i];
            for (Direction side : rotation) {
                quadList.addAll((Collection)sideQuads.get((byte)side.m_122411_()));
                this.anchor[i].addAll((Collection)anchorQuadList.get((byte)side.m_122411_()));
            }
            for (Direction side : rotation.invert()) {
                quadList.add((BakedQuad)coreQuads.get((byte)side.m_122411_()));
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side == null && !this.isTranslucent(type)) {
            if (extraData.has((ModelProperty)CableProperty.INSTANCE)) {
                int index = (Integer)extraData.get((ModelProperty)CableProperty.INSTANCE);
                ObjectList list = CollectionUtils.createList();
                list.addAll(this.quads[index >> 6 & 0x3F]);
                list.addAll(this.anchor[index & 0x3F]);
                return list;
            }
            return this.quads[0];
        }
        return CableModel.empty();
    }

    @Override
    public boolean m_7547_() {
        return false;
    }

    @Override
    public boolean m_7541_() {
        return false;
    }

    protected DirectionList selectEndSides(DirectionList list, Direction facing) {
        return this.advTube == null ? list : this.advTube.selectEndSides(list, facing, this.state);
    }

    protected List<BakedQuad> generateAnchorQuads(TextureAtlasSprite sprite, Direction dir, float min, float max) {
        ObjectList quads = CollectionUtils.createList();
        Tuple<Vector3f, Vector3f> position = CableModel.getPosForSide(dir, min, max, true);
        for (Direction side : DirectionList.ALL.remove(dir.m_122424_())) {
            quads.add((BakedQuad)QuadBaker.BAKER.m_111600_((Vector3f)position.m_14418_(), (Vector3f)position.m_14419_(), this.getFace(dir, side, min, max), sprite, side, (ModelState)BlockModelRotation.X0_Y0, null, true, LOCATION));
        }
        return quads;
    }

    protected List<BakedQuad> generateSideQuads(Direction dir, float min, float max) {
        ObjectList quads = CollectionUtils.createList();
        Tuple<Vector3f, Vector3f> position = CableModel.getPosForSide(dir, min, max, false);
        for (Direction side : this.selectEndSides(DirectionList.ALL.remove(dir.m_122424_()), dir)) {
            quads.add((BakedQuad)QuadBaker.BAKER.m_111600_((Vector3f)position.m_14418_(), (Vector3f)position.m_14419_(), this.getFace(dir, side, min, max), this.tube.getTexture(this.state, false, dir, side), side, (ModelState)BlockModelRotation.X0_Y0, null, true, LOCATION));
        }
        return quads;
    }

    protected List<BakedQuad> generateSideQuads(Direction dir, float min, float max, float textureMin, float textureMax) {
        ObjectList quads = CollectionUtils.createList();
        Tuple<Vector3f, Vector3f> position = CableModel.getPosForSide(dir, min, max, false);
        for (Direction side : this.selectEndSides(DirectionList.ALL.remove(dir), dir)) {
            quads.add((BakedQuad)QuadBaker.BAKER.m_111600_((Vector3f)position.m_14418_(), (Vector3f)position.m_14419_(), this.getFace(dir, side, textureMin, textureMax), this.tube.getTexture(this.state, false, dir, side), side, (ModelState)BlockModelRotation.X0_Y0, null, true, LOCATION));
        }
        return quads;
    }

    protected Byte2ObjectMap<BakedQuad> generateCoreQuads(float min, float max) {
        Vector3f minF = new Vector3f(min, min, min);
        Vector3f maxF = new Vector3f(max, max, max);
        BlockElementFace face = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{min, min, max, max}, 0));
        Byte2ObjectOpenHashMap quads = new Byte2ObjectOpenHashMap();
        for (Direction side : DirectionList.ALL) {
            quads.put((byte)side.m_122411_(), (Object)QuadBaker.BAKER.m_111600_(minF, maxF, face, this.tube.getTexture(this.state, true, null, side), side, (ModelState)BlockModelRotation.X0_Y0, null, true, LOCATION));
        }
        return quads;
    }

    public static Tuple<Vector3f, Vector3f> getPosForSide(Direction facing, float min, float max, boolean pipe) {
        switch (facing) {
            case DOWN: {
                return new Tuple((Object)new Vector3f(min, 0.0f, min), (Object)new Vector3f(max, min, max));
            }
            case UP: {
                return new Tuple((Object)new Vector3f(min, max, min), (Object)new Vector3f(max, 16.0f, max));
            }
            case NORTH: {
                return new Tuple((Object)new Vector3f(min, min, 0.0f), (Object)new Vector3f(max, max, min));
            }
            case SOUTH: {
                return new Tuple((Object)new Vector3f(min, min, max), (Object)new Vector3f(max, max, 16.0f));
            }
            case WEST: {
                return new Tuple((Object)new Vector3f(0.0f, min, min), (Object)new Vector3f(min, max, max));
            }
            case EAST: {
                return new Tuple((Object)new Vector3f(max, min, min), (Object)new Vector3f(16.0f, max, max));
            }
        }
        return new Tuple((Object)new Vector3f(min, min, min), (Object)new Vector3f(max, max, max));
    }

    public BlockElementFace getFace(Direction dir, Direction side, float min, float max) {
        if (side == dir) {
            return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{min, min, max, max}, 0));
        }
        if (side.m_122434_().m_122478_()) {
            if (side.m_122421_() == Direction.AxisDirection.NEGATIVE) {
                return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{min, 0.0f, max, min}, dir.m_122416_() * 90 + (dir.m_122434_() == Direction.Axis.X ? 180 : 0)));
            }
            return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{min, max, max, 16.0f}, dir.m_122416_() * 90));
        }
        if (side.m_122434_().m_122479_()) {
            if (dir.m_122434_().m_122478_()) {
                if (side.m_122421_() == Direction.AxisDirection.NEGATIVE) {
                    return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, min, min, max}, dir.m_122421_() == Direction.AxisDirection.NEGATIVE ? 270 : 90));
                }
                return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{max, min, 16.0f, max}, dir.m_122421_() == Direction.AxisDirection.NEGATIVE ? 90 : 270));
            }
            if (side.m_122421_() == Direction.AxisDirection.NEGATIVE) {
                return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, min, min, max}, dir.m_122416_() * 180 + (dir.m_122421_() == Direction.AxisDirection.NEGATIVE ? 0 : 180)));
            }
            return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{max, min, 16.0f, max}, dir.m_122416_() * 180 + (dir.m_122421_() == Direction.AxisDirection.NEGATIVE ? 0 : 180)));
        }
        return null;
    }
}

