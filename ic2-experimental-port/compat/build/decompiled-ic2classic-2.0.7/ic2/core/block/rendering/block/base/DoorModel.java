/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.DoorHingeSide
 *  net.minecraft.world.level.block.state.properties.DoubleBlockHalf
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class DoorModel
extends BaseModel {
    private static final ShapeBuilder OPEN_MODEL = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 3.0, 16.0, 16.0).addCube(new float[][]{{13.0f, 0.0f, 16.0f, 16.0f}, {13.0f, 0.0f, 16.0f, 16.0f}, {3.0f, 0.0f, 0.0f, 16.0f}, {0.0f, 0.0f, 3.0f, 16.0f}, {16.0f, 0.0f, 0.0f, 16.0f}, {0.0f, 0.0f, 16.0f, 16.0f}}).finish();
    private static final ShapeBuilder CLOSE_MODEL = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 3.0, 16.0, 16.0).addCube(new float[][]{{13.0f, 0.0f, 16.0f, 16.0f}, {13.0f, 0.0f, 16.0f, 16.0f}, {3.0f, 0.0f, 0.0f, 16.0f}, {0.0f, 0.0f, 3.0f, 16.0f}, {0.0f, 0.0f, 16.0f, 16.0f}, {16.0f, 0.0f, 0.0f, 16.0f}}).finish();
    private BlockState state;
    private String textureNameUpper;
    private String textureNameLower;
    private String textureFolder;
    private List<BakedQuad> quads = new ArrayList<BakedQuad>();

    public DoorModel(BlockState state, String textureFolder, String textureNameUpper, String textureNameLower) {
        this.state = state;
        this.textureFolder = textureFolder;
        this.textureNameUpper = textureNameUpper;
        this.textureNameLower = textureNameLower;
    }

    @Override
    public void init() {
        Map<String, TextureAtlasSprite> textureMap = IC2Textures.getMappedEntriesBlockIC2(this.textureFolder);
        this.setParticleTexture(this.state.m_61143_((Property)DoorBlock.f_52730_) == DoubleBlockHalf.UPPER ? textureMap.get(this.textureNameUpper) : textureMap.get(this.textureNameLower));
        boolean up = this.state.m_61143_((Property)DoorBlock.f_52730_) == DoubleBlockHalf.UPPER;
        boolean open = (Boolean)this.state.m_61143_((Property)DoorBlock.f_52727_);
        boolean leftSided = this.state.m_61143_((Property)DoorBlock.f_52728_) == DoorHingeSide.LEFT;
        BlockModelRotation rotation = BlockModelRotation.m_119153_((int)0, (int)((((Direction)this.state.m_61143_((Property)DoorBlock.f_52726_)).m_122416_() + 1 + (open ? (leftSided ? 1 : 3) : 0) & 3) * 90));
        (leftSided ? (open ? OPEN_MODEL : CLOSE_MODEL) : (open ? CLOSE_MODEL : OPEN_MODEL)).buildQuads(textureMap.get(up ? this.textureNameUpper : this.textureNameLower), rotation, null, true, this.quads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        return state == null || side != null ? DoorModel.empty() : this.quads;
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

