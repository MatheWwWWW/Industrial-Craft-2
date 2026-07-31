/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.block.tubes;

import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.rendering.block.tubes.TubeModel;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CableProperty;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class MultiTubeModel
extends TubeModel {
    protected List<BakedQuad>[] filledSides = CollectionUtils.createLists(6);

    public MultiTubeModel(ITubeBlock tube, BlockState state) {
        super(tube, state);
    }

    @Override
    public void init() {
        super.init();
        float radius = this.tube.getRadius(this.state);
        float min = 8.0f - radius;
        float max = 8.0f + radius;
        BlockState original = this.state;
        for (Direction dir : DirectionList.ALL) {
            this.state = (BlockState)this.state.m_61124_((Property)IC2Properties.ALL_FACINGS, (Comparable)dir);
            this.filledSides[dir.m_122411_()] = this.generateSideQuads(dir, min, max);
        }
        this.state = original;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side == null) {
            Integer value = (Integer)extraData.get((ModelProperty)CableProperty.INSTANCE);
            Integer extraValue = (Integer)extraData.get((ModelProperty)CableProperty.TUBE_EXTRA_SIDE);
            Function camouflage = (Function)extraData.get((ModelProperty)CamouflageProperty.INSTANCE);
            if (value == null && camouflage == null) {
                return this.itemQuads;
            }
            if (value != null && camouflage == null) {
                int index = value;
                ObjectList list = CollectionUtils.createList();
                if (extraValue != null) {
                    index &= ~(extraValue << 6);
                    for (Direction dir : DirectionList.ofNumber(extraValue)) {
                        list.addAll(this.filledSides[dir.ordinal()]);
                    }
                }
                list.addAll(this.quads[index >> 6 & 0x3F]);
                list.addAll(this.anchor[index & 0x3F]);
                return list;
            }
            if (value == null) {
                List<BakedQuad> releaseQuads = ((CamouflageShape.QuadResults)camouflage.apply(type)).getQuads(type);
                if (releaseQuads.size() > 0) {
                    return releaseQuads;
                }
                return this.quads[0];
            }
            int index = value;
            ObjectList list = CollectionUtils.createList();
            if (extraValue != null) {
                index &= ~(extraValue << 6);
                for (Direction dir : DirectionList.ofNumber(extraValue)) {
                    list.addAll(this.filledSides[dir.ordinal()]);
                }
            }
            list.addAll(this.quads[index >> 6 & 0x3F]);
            list.addAll(this.anchor[index & 0x3F]);
            List<BakedQuad> releaseQuads = ((CamouflageShape.QuadResults)camouflage.apply(type)).getQuads(type);
            if (releaseQuads.size() > 0) {
                list.addAll(releaseQuads);
            }
            return list;
        }
        return MultiTubeModel.empty();
    }
}

