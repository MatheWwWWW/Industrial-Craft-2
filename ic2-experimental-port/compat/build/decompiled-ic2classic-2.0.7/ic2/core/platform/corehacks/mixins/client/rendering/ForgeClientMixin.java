/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.model.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.ForgeHooksClient
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package ic2.core.platform.corehacks.mixins.client.rendering;

import ic2.core.platform.rendering.IC2Textures;
import java.util.Set;
import net.minecraft.client.resources.model.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(value=Dist.CLIENT)
@Mixin(value={ForgeHooksClient.class})
public class ForgeClientMixin {
    @Inject(method={"gatherFluidTextures"}, at={@At(value="RETURN")}, remap=false)
    private static void onReload(Set<Material> materials, CallbackInfo info) {
        IC2Textures.INSTANCE.loadRenderMaterials(materials::add);
    }
}

