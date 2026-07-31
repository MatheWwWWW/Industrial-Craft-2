/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 */
package ic2.forge;

import ic2.forge.EnvFluidHandlerForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class ClientEnvFluidHandlerForge
extends EnvFluidHandlerForge {
    @Override
    public ResourceLocation getStillSpriteId(Fluid fluid) {
        return IClientFluidTypeExtensions.of((Fluid)fluid).getStillTexture();
    }

    @Override
    public ResourceLocation getFlowingSpriteId(Fluid fluid) {
        return IClientFluidTypeExtensions.of((Fluid)fluid).getFlowingTexture();
    }

    @Override
    public int getColor(Fluid fluid) {
        return IClientFluidTypeExtensions.of((Fluid)fluid).getTintColor();
    }
}

