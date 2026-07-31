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
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block;

import ic2.core.block.misc.PlayerDetectorBlock;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class PlayerDetectorModel
extends BaseModel {
    private static final ShapeBuilder MODEL = new ShapeBuilder().newQuad(5.0, 5.0, 0.0, 11.0, 11.0, 2.0).addCube(new float[][]{{2.0f, 8.0f, 8.0f, 10.0f}, {2.0f, 0.0f, 8.0f, 2.0f}, {10.0f, 2.0f, 16.0f, 8.0f}, {2.0f, 2.0f, 8.0f, 8.0f}, {0.0f, 2.0f, 2.0f, 8.0f}, {8.0f, 2.0f, 10.0f, 8.0f}}).finish();
    private static final ShapeBuilder ITEM = new ShapeBuilder().newQuad(5.0, 5.0, 7.0, 11.0, 11.0, 9.0).addCube(new float[][]{{2.0f, 8.0f, 8.0f, 10.0f}, {2.0f, 0.0f, 8.0f, 2.0f}, {10.0f, 2.0f, 16.0f, 8.0f}, {2.0f, 2.0f, 8.0f, 8.0f}, {0.0f, 2.0f, 2.0f, 8.0f}, {8.0f, 2.0f, 10.0f, 8.0f}}).finish();
    private BlockState state;
    private String activeTextureName;
    private String inactiveTextureName;
    private String textureFolder;
    private List<BakedQuad> blockQuads = new ArrayList<BakedQuad>();
    private List<BakedQuad> itemQuads = new ArrayList<BakedQuad>();

    public PlayerDetectorModel(BlockState state, String textureFolder, String activeTextureName, String inactiveTextureName) {
        this.state = state;
        this.textureFolder = textureFolder;
        this.activeTextureName = activeTextureName;
        this.inactiveTextureName = inactiveTextureName;
    }

    @Override
    public void init() {
        boolean powered = (Boolean)this.state.m_61143_((Property)PlayerDetectorBlock.ACTIVE);
        TextureAtlasSprite texture = IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(powered ? this.activeTextureName : this.inactiveTextureName);
        this.setParticleTexture(texture);
        Direction facing = (Direction)this.state.m_61143_((Property)PlayerDetectorBlock.FACING);
        BlockModelRotation rotation = BlockModelRotation.m_119153_((int)(facing.m_122434_().m_122478_() ? (facing.m_122411_() == 0 ? -90 : 90) : 0), (int)(facing.m_122416_() == -1 ? 0 : facing.m_122416_() * 90));
        MODEL.buildQuads(texture, rotation, null, true, this.blockQuads);
        ITEM.buildQuads(texture, rotation, null, true, this.itemQuads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        if (side != null) {
            return PlayerDetectorModel.empty();
        }
        return state == null ? this.itemQuads : this.blockQuads;
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

