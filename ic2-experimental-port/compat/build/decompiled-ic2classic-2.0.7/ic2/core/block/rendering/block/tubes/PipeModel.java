/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.bytes.Byte2ObjectMap
 *  it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.block.tubes;

import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.rendering.block.tubes.CableModel;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CableProperty;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class PipeModel
extends CableModel {
    List<BakedQuad>[] extractionQuads = CollectionUtils.createLists(64);
    List<BakedQuad> itemQuads = CollectionUtils.createList();
    boolean generatingItems = false;

    public PipeModel(ITubeBlock tube, BlockState state) {
        super(tube, state);
    }

    @Override
    public void init() {
        super.init();
        float radius = this.tube.getRadius(this.state);
        float min = 8.0f - radius;
        float max = 8.0f + radius;
        this.generatingItems = true;
        Byte2ObjectMap<BakedQuad> coreQuads = this.generateCoreQuads(min, max);
        for (Direction dir : DirectionList.VERTICAL) {
            this.itemQuads.addAll(this.generateSideQuads(dir, min, max));
        }
        for (Direction dir : DirectionList.HORIZONTAL) {
            this.itemQuads.add((BakedQuad)coreQuads.get((byte)dir.m_122411_()));
        }
        this.generatingItems = false;
        this.generateExtractionQuads();
    }

    private void generateExtractionQuads() {
        float radius = this.tube.getRadius(this.state);
        float min = 8.0f - (radius + 2.0f);
        float max = 8.0f + (radius + 2.0f);
        float textureMin = 8.0f - radius;
        float textureMax = 8.0f + radius;
        Byte2ObjectOpenHashMap sideQuads = new Byte2ObjectOpenHashMap();
        for (Direction side : DirectionList.ALL) {
            sideQuads.put((byte)side.m_122411_(), this.generateSideQuads(side, min, max, textureMin, textureMax));
        }
        for (int i = 0; i < 64; ++i) {
            for (Direction side : DirectionList.ofNumber(i)) {
                this.extractionQuads[i].addAll((Collection)sideQuads.get((byte)side.m_122411_()));
            }
        }
    }

    @Override
    protected DirectionList selectEndSides(DirectionList list, Direction facing) {
        return this.generatingItems ? list : (this.advTube == null ? list.remove(facing) : this.advTube.selectEndSides(list, facing, this.state));
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side == null) {
            List<BakedQuad> releaseQuads;
            Integer value = (Integer)extraData.get((ModelProperty)CableProperty.INSTANCE);
            Integer extraction = (Integer)extraData.get((ModelProperty)CableProperty.PIPE_EXTRACTION);
            Function camouflage = (Function)extraData.get((ModelProperty)CamouflageProperty.INSTANCE);
            if (value == null && camouflage == null) {
                return this.itemQuads;
            }
            if (value != null && camouflage == null) {
                int index = value;
                ObjectList list = CollectionUtils.createList();
                list.addAll(this.quads[index >> 6 & 0x3F]);
                list.addAll(this.anchor[index & 0x3F]);
                if (extraction != null) {
                    list.addAll(this.extractionQuads[extraction & 0x3F]);
                }
                return list;
            }
            if (value == null) {
                List<BakedQuad> releaseQuads2 = ((CamouflageShape.QuadResults)camouflage.apply(type)).getQuads(type);
                if (releaseQuads2.size() > 0) {
                    return releaseQuads2;
                }
                return this.quads[0];
            }
            int index = value;
            ObjectList list = CollectionUtils.createList();
            list.addAll(this.quads[index >> 6 & 0x3F]);
            list.addAll(this.anchor[index & 0x3F]);
            if (extraction != null) {
                list.addAll(this.extractionQuads[extraction & 0x3F]);
            }
            if ((releaseQuads = ((CamouflageShape.QuadResults)camouflage.apply(type)).getQuads(type)).size() > 0) {
                list.addAll(releaseQuads);
            }
            return list;
        }
        return PipeModel.empty();
    }
}

