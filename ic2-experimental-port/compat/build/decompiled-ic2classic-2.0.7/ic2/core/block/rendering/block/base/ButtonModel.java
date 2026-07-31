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
 *  net.minecraft.world.level.block.ButtonBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.AttachFace
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class ButtonModel
extends BaseModel {
    private static final ShapeBuilder ACTIVE = new ShapeBuilder().newQuad(5.0, 0.0, 6.0, 11.0, 1.0, 10.0).addHorizontalCube({5.0f, 14.0f, 11.0f, 15.0f}, {5.0f, 14.0f, 11.0f, 15.0f}, {6.0f, 14.0f, 10.0f, 16.0f}, {6.0f, 14.0f, 10.0f, 16.0f}).addFace(Direction.DOWN, 5.0f, 6.0f, 11.0f, 10.0f).addFace(Direction.UP, 5.0f, 10.0f, 11.0f, 6.0f).finish();
    private static final ShapeBuilder INACTIVE = new ShapeBuilder().newQuad(5.0, 0.0, 6.0, 11.0, 2.0, 10.0).addHorizontalCube({5.0f, 14.0f, 11.0f, 16.0f}, {5.0f, 14.0f, 11.0f, 16.0f}, {6.0f, 14.0f, 10.0f, 16.0f}, {6.0f, 14.0f, 10.0f, 16.0f}).addFace(Direction.DOWN, 5.0f, 6.0f, 11.0f, 10.0f).addFace(Direction.UP, 5.0f, 10.0f, 11.0f, 6.0f).finish();
    private static final ShapeBuilder ITEM = new ShapeBuilder().newQuad(5.0, 6.0, 6.0, 11.0, 10.0, 10.0).addHorizontalCube({5.0f, 12.0f, 11.0f, 16.0f}, {5.0f, 12.0f, 11.0f, 16.0f}, {6.0f, 12.0f, 10.0f, 16.0f}, {6.0f, 12.0f, 10.0f, 16.0f}).addFace(Direction.DOWN, 5.0f, 6.0f, 11.0f, 10.0f).addFace(Direction.UP, 5.0f, 10.0f, 11.0f, 6.0f).finish();
    private BlockState state;
    private String textureName;
    private String textureFolder;
    private List<BakedQuad> blockQuads = CollectionUtils.createList();
    private List<BakedQuad> itemQuads = CollectionUtils.createList();

    public ButtonModel(BlockState state, String textureFolder, String textureName) {
        this.state = state;
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    @Override
    public void init() {
        TextureAtlasSprite texture = IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.textureName);
        this.setParticleTexture(texture);
        boolean powered = (Boolean)this.state.m_61143_((Property)ButtonBlock.f_51045_);
        AttachFace face = (AttachFace)this.state.m_61143_((Property)ButtonBlock.f_53179_);
        BlockModelRotation rotation = BlockModelRotation.m_119153_((int)(face == AttachFace.WALL ? -90 : (face == AttachFace.CEILING ? 180 : 0)), (int)((((Direction)this.state.m_61143_((Property)ButtonBlock.f_54117_)).m_122416_() + (face == AttachFace.FLOOR ? 2 : 0)) * 90));
        (powered ? ACTIVE : INACTIVE).buildQuads(texture, rotation, null, true, this.blockQuads);
        ITEM.buildQuads(texture, rotation, null, true, this.itemQuads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        if (side != null) {
            return ButtonModel.empty();
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

