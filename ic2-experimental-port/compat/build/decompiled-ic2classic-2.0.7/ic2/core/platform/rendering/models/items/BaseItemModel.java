/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.platform.rendering.models.items;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.platform.rendering.features.item.IColoredItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public abstract class BaseItemModel<T extends BaseItemModel<T>>
extends BaseModel {
    protected T other;
    public List<BakedQuad> quads = new ArrayList<BakedQuad>();

    protected void initOther(T other) {
        this.other = other;
        ((BaseModel)other).setTransforms(this.getTransformMap());
        ((BaseModel)other).setParticleTexture(this.m_6160_());
        for (BakedQuad quad : this.quads) {
            if (quad.m_111306_() != Direction.SOUTH) continue;
            ((BaseItemModel)other).quads.add(quad);
        }
    }

    public void syncQuads() {
        if (this.other != null) {
            ((BaseItemModel)this.other).quads.clear();
            for (BakedQuad quad : this.quads) {
                if (quad.m_111306_() != Direction.SOUTH) continue;
                ((BaseItemModel)this.other).quads.add(quad);
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        return side == null ? this.quads : BaseItemModel.empty();
    }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        if (transformType == ItemTransforms.TransformType.GUI && this.other != null) {
            return ((BaseItemModel)this.other).applyTransform(transformType, poseStack, applyLeftHandTransform);
        }
        return super.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }

    @Override
    public boolean m_7539_() {
        return false;
    }

    protected int getTintedIndex(ItemStack stack, int layer) {
        if (stack.m_41720_() instanceof IColoredItemModel) {
            return ((IColoredItemModel)stack.m_41720_()).getTintedIndexForModel(stack, layer);
        }
        return -1;
    }
}

