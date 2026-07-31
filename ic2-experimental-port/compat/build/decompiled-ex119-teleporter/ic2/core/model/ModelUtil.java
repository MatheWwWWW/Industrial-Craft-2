/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.block.BlockModelShaper
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.ModelManager
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.model;

import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class ModelUtil {
    public static ModelResourceLocation getModelLocation(ResourceLocation resourceLocation, BlockState blockState) {
        return new ModelResourceLocation(resourceLocation, ModelUtil.getVariant(blockState));
    }

    public static String getVariant(BlockState blockState) {
        return BlockModelShaper.m_110887_((Map)blockState.m_61148_());
    }

    public static BakedModel getMissingModel() {
        return ModelUtil.getModelManager().m_119409_();
    }

    public static BakedModel getModel(ModelResourceLocation modelResourceLocation) {
        return ModelUtil.getModelManager().m_119422_(modelResourceLocation);
    }

    public static BakedModel getBlockModel(BlockState blockState) {
        return Minecraft.m_91087_().m_91289_().m_110907_().m_110893_(blockState);
    }

    private static ModelManager getModelManager() {
        return Minecraft.m_91087_().m_91291_().m_115103_().m_109393_();
    }
}

