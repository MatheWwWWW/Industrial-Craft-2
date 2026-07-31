/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.block.model.ItemTransforms
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.ChunkRenderTypeSet
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.platform.rendering.models;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.ParticleOverride;
import ic2.core.platform.rendering.IC2Models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

@OnlyIn(value=Dist.CLIENT)
public abstract class BaseModel
implements BakedModel {
    public static final ChunkRenderTypeSet DEFAULT_SET = ChunkRenderTypeSet.of((RenderType[])new RenderType[]{RenderType.m_110451_()});
    public static final ResourceLocation LOCATION = new ResourceLocation("ic2:internal");
    TextureAtlasSprite particles;
    ItemTransforms transforms;
    ChunkRenderTypeSet renderTypes = DEFAULT_SET;

    public abstract void init();

    public void setTransforms(ItemTransforms transforms) {
        this.transforms = transforms;
    }

    public ItemTransforms getTransformMap() {
        return this.transforms;
    }

    protected void setParticleTexture(TextureAtlasSprite sprite) {
        this.particles = IC2Models.getSafeIcon(sprite);
    }

    public void setRenderTypes(ChunkRenderTypeSet renderTypes) {
        this.renderTypes = renderTypes;
    }

    public ItemOverrides m_7343_() {
        return EmptyOverrides.INSTANCE;
    }

    public TextureAtlasSprite m_6160_() {
        return this.particles;
    }

    public TextureAtlasSprite getParticleIcon(ModelData data) {
        BlockState state = (BlockState)data.get((ModelProperty)ParticleOverride.INSTANCE);
        if (state != null) {
            return Minecraft.m_91087_().m_91289_().m_110910_(state).getParticleIcon(ModelData.EMPTY);
        }
        return super.getParticleIcon(data);
    }

    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
        return BaseModel.empty();
    }

    public List<BakedQuad> m_213637_(BlockState state, Direction side, RandomSource rand) {
        return this.getQuads(state, side, rand, ModelData.EMPTY, null);
    }

    public boolean m_7541_() {
        return true;
    }

    public boolean m_7521_() {
        return false;
    }

    public boolean m_7539_() {
        return true;
    }

    public boolean m_7547_() {
        return false;
    }

    protected boolean isTranslucent(RenderType type) {
        return CamouflageShape.isTranslucent(type);
    }

    protected static <T> List<T> empty() {
        return Collections.emptyList();
    }

    protected static List<BakedQuad>[] multiList(int count) {
        List[] array = new List[count];
        for (int i = 0; i < count; ++i) {
            array[i] = new ArrayList();
        }
        return array;
    }

    public BakedModel applyTransform(ItemTransforms.TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        return this.handlePerspective(this, transformType, poseStack, applyLeftHandTransform);
    }

    protected BakedModel handlePerspective(BakedModel model, ItemTransforms.TransformType type, PoseStack stack, boolean leftHandedness) {
        this.transforms.m_111808_(type).m_111763_(leftHandedness, stack);
        return model;
    }

    public static class EmptyOverrides
    extends ItemOverrides {
        public static final EmptyOverrides INSTANCE = new EmptyOverrides();
    }
}

