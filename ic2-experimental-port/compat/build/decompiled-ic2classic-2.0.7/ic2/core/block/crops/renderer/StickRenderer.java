/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.renderer.block.model.FaceBakery
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.renderer;

import com.mojang.math.Vector3f;
import ic2.api.crops.ICropRenderer;
import ic2.api.util.DirectionList;
import ic2.core.block.crops.renderer.DefaultCropRenderer;
import ic2.core.platform.rendering.IC2Textures;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StickRenderer
implements ICropRenderer {
    @Override
    @OnlyIn(value=Dist.CLIENT)
    public List<BakedQuad> createQuadsForStage(int stage, boolean fancy, FaceBakery baker) {
        ResourceLocation location = new ResourceLocation("ic2:crop_sticks");
        ArrayList<BakedQuad> quads = new ArrayList<BakedQuad>();
        TextureAtlasSprite sprite = IC2Textures.getMappedEntriesBlockIC2("crops").get(stage == 0 ? "cropsticks" : "crossed_cropsticks");
        BlockElementFace face = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0));
        for (Direction facing : DirectionList.HORIZONTAL) {
            Vector3f min = DefaultCropRenderer.SIDES[facing.m_122416_()][0];
            Vector3f max = DefaultCropRenderer.SIDES[facing.m_122416_()][1];
            quads.add(baker.m_111600_(min, max, face, sprite, facing, (ModelState)BlockModelRotation.X0_Y0, null, false, location));
            quads.add(baker.m_111600_(min, max, face, sprite, facing.m_122424_(), (ModelState)BlockModelRotation.X0_Y0, null, false, location));
        }
        return quads;
    }
}

