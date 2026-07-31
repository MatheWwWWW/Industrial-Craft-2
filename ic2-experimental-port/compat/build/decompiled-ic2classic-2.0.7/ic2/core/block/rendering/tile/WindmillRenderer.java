/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Quaternion
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.resources.model.Material
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import ic2.core.block.generators.tiles.WindmillTileEntity;
import ic2.core.block.rendering.models.RotorModel;
import ic2.core.item.misc.WindmillBladeItem;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class WindmillRenderer
implements BlockEntityRenderer<WindmillTileEntity> {
    Int2ObjectMap<RotorModel> models = new Int2ObjectOpenHashMap();
    static final Map<ResourceLocation, Material> MATERIALS = CollectionUtils.createMap();

    public WindmillRenderer(BlockEntityRendererProvider.Context context) {
    }

    public boolean shouldRenderOffScreen(WindmillTileEntity p_188185_1_) {
        return true;
    }

    public void render(WindmillTileEntity tile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ResourceLocation location = tile.getTexture();
        int radius = tile.getRadius();
        if (radius <= 0 || location == null) {
            return;
        }
        tile.rotation = (tile.rotation + tile.speed) % 360.0f;
        RotorModel model = (RotorModel)((Object)this.models.computeIfAbsent(radius, RotorModel::new));
        matrixStackIn.m_85836_();
        matrixStackIn.m_85837_(0.5, 0.5, 0.5);
        matrixStackIn.m_85845_(new Quaternion(0.0f, -tile.getFacing().m_122435_() + 90.0f, 0.0f, true));
        matrixStackIn.m_85845_(new Quaternion(tile.rotation, 0.0f, 0.0f, true));
        matrixStackIn.m_85837_(-0.25, 0.0, 0.0);
        model.m_7695_(matrixStackIn, WindmillRenderer.getMaterial(location).m_119194_(bufferIn, arg_0 -> ((RotorModel)model).m_103119_(arg_0)), LevelRenderer.m_109541_((BlockAndTintGetter)tile.m_58904_(), (BlockPos)tile.m_58899_().m_121945_(tile.getFacing())), combinedOverlayIn, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStackIn.m_85849_();
    }

    public static Material getMaterial(ResourceLocation location) {
        return MATERIALS.computeIfAbsent(location, WindmillRenderer::createMaterial);
    }

    private static Material createMaterial(ResourceLocation location) {
        return new Material(WindmillBladeItem.ROTOR_SHEET, location);
    }
}

