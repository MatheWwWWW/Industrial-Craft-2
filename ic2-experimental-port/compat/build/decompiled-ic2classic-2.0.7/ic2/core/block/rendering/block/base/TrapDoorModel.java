/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.TrapDoorBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Half
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class TrapDoorModel
extends BaseModel {
    private static final ResourceLocation TRAPDOOR = new ResourceLocation("ic2:trapdoor");
    private static final ResourceLocation TRAPDOOR_LOCATION = new ResourceLocation("models/block/thin_block");
    private static final ShapeBuilder TOP = new ShapeBuilder().newQuad(0.0, 13.0, 0.0, 16.0, 16.0, 16.0).addFace(Direction.UP, 0.0f, 0.0f, 16.0f, 16.0f).addFace(Direction.DOWN, 0.0f, 16.0f, 16.0f, 0.0f).addFaces(DirectionList.HORIZONTAL, 16.0f, 16.0f, 0.0f, 13.0f).finish();
    private static final ShapeBuilder BOTTOM = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 16.0, 3.0, 16.0).addFace(Direction.UP, 0.0f, 0.0f, 16.0f, 16.0f).addFace(Direction.DOWN, 0.0f, 16.0f, 16.0f, 0.0f).addFaces(DirectionList.HORIZONTAL, 16.0f, 16.0f, 0.0f, 13.0f).finish();
    private BlockState state;
    private String textureName;
    private String textureFolder;
    private List<BakedQuad> quads = new ArrayList<BakedQuad>();

    public TrapDoorModel(BlockState state, String textureFolder, String textureName) {
        this.state = state;
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    @Override
    public void init() {
        Map<String, TextureAtlasSprite> textureMap = IC2Textures.getMappedEntriesBlockIC2(this.textureFolder);
        this.setTransforms(IC2Models.getTransformMap(IC2Models.BLOCK_LOCATION, TRAPDOOR, TRAPDOOR_LOCATION));
        this.setParticleTexture(textureMap.get(this.textureName));
        boolean open = (Boolean)this.state.m_61143_((Property)TrapDoorBlock.f_57514_);
        Half half = (Half)this.state.m_61143_((Property)TrapDoorBlock.f_57515_);
        (half == Half.BOTTOM ? BOTTOM : TOP).buildQuads(this.m_6160_(), BlockModelRotation.m_119153_((int)(open ? (half == Half.BOTTOM ? 90 : -90) : 0), (int)((((Direction)this.state.m_61143_((Property)DoorBlock.f_52726_)).m_122416_() + 2) * 90)), null, true, this.quads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        if (side != null) {
            return TrapDoorModel.empty();
        }
        return this.quads;
    }

    @Override
    public boolean m_7541_() {
        return true;
    }

    @Override
    public boolean m_7539_() {
        return false;
    }

    @Override
    public boolean m_7521_() {
        return false;
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

