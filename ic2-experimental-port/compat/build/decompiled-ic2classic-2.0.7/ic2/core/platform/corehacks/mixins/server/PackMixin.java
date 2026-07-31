/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.repository.RepositorySource
 *  net.minecraftforge.forgespi.locating.IModFile
 *  net.minecraftforge.resource.PathPackResources
 *  net.minecraftforge.resource.ResourcePackLoader
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package ic2.core.platform.corehacks.mixins.server;

import ic2.core.platform.recipes.PackHack;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.resource.ResourcePackLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={ResourcePackLoader.class})
public class PackMixin {
    @Inject(method={"loadResourcePacks(Lnet/minecraft/server/packs/repository/PackRepository;Ljava/util/function/Function;)V"}, at={@At(value="RETURN")}, remap=false, locals=LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void onResourceCallBack(PackRepository resourcePacks, Function<Map<IModFile, ? extends PathPackResources>, ? extends RepositorySource> packFinder, CallbackInfo info) {
        PackHack.loadHack(resourcePacks);
    }
}

