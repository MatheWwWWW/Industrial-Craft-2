/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.block.base;

import ic2.core.block.rendering.props.ColorProperty;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class CornerRailingModel
extends BaseModel {
    static final ShapeBuilder INNER = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 2.0, 16.0, 2.0).addCube(new float[][]{{1.0f, 1.0f, 3.0f, 3.0f}, {1.0f, 1.0f, 3.0f, 3.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish();
    private Map<String, TextureAtlasSprite> sprites;

    public CornerRailingModel(String path) {
        this.sprites = IC2Textures.getMappedEntriesBlockIC2(path);
        this.setParticleTexture(this.sprites.get(DyeColor.BLUE.m_41065_()));
    }

    @Override
    public void init() {
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side != null) {
            return CornerRailingModel.empty();
        }
        ObjectList quads = CollectionUtils.createList();
        DyeColor color = (DyeColor)extraData.get((ModelProperty)ColorProperty.FIRST);
        if (color != null) {
            INNER.buildQuads(this.sprites.get(color.m_41065_()), BlockModelRotation.X0_Y0, null, true, (List<BakedQuad>)quads);
        }
        if ((color = (DyeColor)extraData.get((ModelProperty)ColorProperty.SECOND)) != null) {
            INNER.buildQuads(this.sprites.get(color.m_41065_()), BlockModelRotation.X0_Y90, null, true, (List<BakedQuad>)quads);
        }
        if ((color = (DyeColor)extraData.get((ModelProperty)ColorProperty.THIRD)) != null) {
            INNER.buildQuads(this.sprites.get(color.m_41065_()), BlockModelRotation.X0_Y180, null, true, (List<BakedQuad>)quads);
        }
        if ((color = (DyeColor)extraData.get((ModelProperty)ColorProperty.FOURTH)) != null) {
            INNER.buildQuads(this.sprites.get(color.m_41065_()), BlockModelRotation.X0_Y270, null, true, (List<BakedQuad>)quads);
        }
        return quads;
    }

    @Override
    public TextureAtlasSprite getParticleIcon(ModelData data) {
        DyeColor color = (DyeColor)data.get((ModelProperty)ColorProperty.FIRST);
        if (color != null) {
            return this.sprites.get(color.m_41065_());
        }
        color = (DyeColor)data.get((ModelProperty)ColorProperty.SECOND);
        if (color != null) {
            return this.sprites.get(color.m_41065_());
        }
        color = (DyeColor)data.get((ModelProperty)ColorProperty.THIRD);
        if (color != null) {
            return this.sprites.get(color.m_41065_());
        }
        color = (DyeColor)data.get((ModelProperty)ColorProperty.FOURTH);
        if (color != null) {
            return this.sprites.get(color.m_41065_());
        }
        return super.getParticleIcon(data);
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

