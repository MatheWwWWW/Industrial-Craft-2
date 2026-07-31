/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.renderer.block.model.FaceBakery
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.renderer;

import com.mojang.math.Vector3f;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRenderer;
import ic2.api.util.DirectionList;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DefaultCropRenderer
implements ICropRenderer {
    @OnlyIn(value=Dist.CLIENT)
    public static final Vector3f[][] SIDES = new Vector3f[][]{{new Vector3f(0.0f, -1.0f, 12.0f), new Vector3f(16.0f, 15.0f, 12.0f)}, {new Vector3f(4.0f, -1.0f, 0.0f), new Vector3f(4.0f, 15.0f, 16.0f)}, {new Vector3f(0.0f, -1.0f, 4.0f), new Vector3f(16.0f, 15.0f, 4.0f)}, {new Vector3f(12.0f, -1.0f, 0.0f), new Vector3f(12.0f, 15.0f, 16.0f)}};
    ICrop crop;

    public DefaultCropRenderer(ICrop crop) {
        this.crop = crop;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public List<BakedQuad> createQuadsForStage(int stage, boolean fancy, FaceBakery baker) {
        List<ResourceLocation> list = this.crop.getTextures();
        if (list.size() <= stage) {
            return null;
        }
        ResourceLocation location = new ResourceLocation("ic2:crop_model");
        ObjectList quads = CollectionUtils.createList();
        BlockElementFace face = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0));
        TextureAtlasSprite sprite = (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(list.get(stage));
        for (Direction facing : DirectionList.HORIZONTAL) {
            Vector3f min = SIDES[facing.m_122416_()][0];
            Vector3f max = SIDES[facing.m_122416_()][1];
            if (facing.m_122421_() == Direction.AxisDirection.POSITIVE) {
                offset = 0.002f;
                max = new Vector3f(max.m_122239_(), max.m_122260_(), max.m_122269_());
                max.m_122272_((float)facing.m_122429_() * offset, (float)facing.m_122430_() * offset, (float)facing.m_122431_() * offset);
            } else {
                offset = 0.002f;
                min = new Vector3f(min.m_122239_(), min.m_122260_(), min.m_122269_());
                min.m_122272_((float)facing.m_122429_() * offset, (float)facing.m_122430_() * offset, (float)facing.m_122431_() * offset);
            }
            quads.add((BakedQuad)baker.m_111600_(min, max, face, sprite, facing, (ModelState)BlockModelRotation.X0_Y0, null, false, location));
            quads.add((BakedQuad)baker.m_111600_(min, max, face, sprite, facing.m_122424_(), (ModelState)BlockModelRotation.X0_Y0, null, false, location));
        }
        return quads;
    }
}

