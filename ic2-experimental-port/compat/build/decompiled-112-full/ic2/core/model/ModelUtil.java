/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.block.model.ModelManager
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.client.renderer.block.statemap.DefaultStateMapper
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.model;

import ic2.core.block.state.ISkippableProperty;
import java.util.Map;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.util.ResourceLocation;

public class ModelUtil {
    private static final DefaultStateMapper defaultStateMapper = new DefaultStateMapper();
    private static final DefaultStateMapper skippingStateMapper = new DefaultStateMapper(){

        public String func_178131_a(Map<IProperty<?>, Comparable<?>> values) {
            StringBuilder propString = new StringBuilder();
            for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
                IProperty<?> prop = entry.getKey();
                if (prop instanceof ISkippableProperty) continue;
                if (propString.length() != 0) {
                    propString.append(',');
                }
                propString.append(prop.func_177701_a());
                propString.append('=');
                propString.append(this.getPropertyName(prop, entry.getValue()));
            }
            if (propString.length() == 0) {
                return "normal";
            }
            return propString.toString();
        }

        private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
            return property.func_177702_a(value);
        }
    };

    public static ModelResourceLocation getModelLocation(ResourceLocation loc, IBlockState state) {
        return new ModelResourceLocation(loc, ModelUtil.getVariant(state));
    }

    public static ModelResourceLocation getTEBlockModelLocation(ResourceLocation loc, IBlockState state) {
        return new ModelResourceLocation(loc, skippingStateMapper.func_178131_a((Map)state.func_177228_b()));
    }

    public static String getVariant(IBlockState state) {
        return defaultStateMapper.func_178131_a((Map)state.func_177228_b());
    }

    public static IBakedModel getMissingModel() {
        return ModelUtil.getModelManager().func_174951_a();
    }

    public static IBakedModel getModel(ModelResourceLocation loc) {
        return ModelUtil.getModelManager().func_174953_a(loc);
    }

    public static IBakedModel getBlockModel(IBlockState state) {
        return Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178125_b(state);
    }

    private static ModelManager getModelManager() {
        return Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178083_a();
    }
}

