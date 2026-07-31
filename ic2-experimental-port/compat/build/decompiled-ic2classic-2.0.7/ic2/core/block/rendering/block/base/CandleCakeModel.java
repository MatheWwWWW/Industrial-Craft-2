/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementRotation
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.rendering.block.base;

import com.mojang.math.Vector3f;
import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;

public class CandleCakeModel
extends SimpleBlockModel {
    static final ShapeBuilder CANDLE = new ShapeBuilder().newQuad(7.0, 8.0, 7.0, 9.0, 14.0, 9.0).addFaces(DirectionList.HORIZONTAL, 0.0f, 8.0f, 2.0f, 14.0f).addFace(Direction.UP, 0.0f, 6.0f, 2.0f, 8.0f).addFace(Direction.DOWN, 0.0f, 14.0f, 2.0f, 16.0f).finish();
    static final ShapeBuilder CANDLE_WIRE = new ShapeBuilder().newQuad(7.5, 14.0, 8.0, 8.5, 15.0, 8.0).addFaces(DirectionList.Z_AXIS, 0.0f, 5.0f, 1.0f, 6.0f).finish();
    ResourceLocation candleTexture;

    public CandleCakeModel(BlockState state, IBlockModel model, ResourceLocation candleTexture) {
        super(state, model);
        this.candleTexture = candleTexture;
    }

    @Override
    public void init() {
        super.init();
        TextureAtlasSprite sprite = (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(this.candleTexture);
        CANDLE.buildQuads(sprite, BlockModelRotation.X0_Y0, null, true, (List<BakedQuad>)this.quads[6]);
        CANDLE_WIRE.buildQuads(sprite, BlockModelRotation.X0_Y0, new BlockElementRotation(new Vector3f(0.5f, 0.875f, 0.5f), Direction.Axis.Y, -45.0f, false), true, (List<BakedQuad>)this.quads[6]);
        CANDLE_WIRE.buildQuads(sprite, BlockModelRotation.X0_Y0, new BlockElementRotation(new Vector3f(0.5f, 0.875f, 0.5f), Direction.Axis.Y, 45.0f, false), true, (List<BakedQuad>)this.quads[6]);
    }
}

