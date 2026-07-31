/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.props;

import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelProperty;

@OnlyIn(value=Dist.CLIENT)
public class CamouflageProperty
extends ModelProperty<Function<RenderType, CamouflageShape.QuadResults>> {
    public static final CamouflageProperty INSTANCE = new CamouflageProperty();

    private CamouflageProperty() {
    }
}

