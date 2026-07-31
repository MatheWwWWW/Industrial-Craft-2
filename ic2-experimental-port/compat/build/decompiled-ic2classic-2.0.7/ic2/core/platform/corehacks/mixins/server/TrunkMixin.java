/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package ic2.core.platform.corehacks.mixins.server;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={TrunkPlacerType.class})
public interface TrunkMixin {
    @Invoker(value="register")
    public static <P extends TrunkPlacer> TrunkPlacerType<P> register(String name, Codec<P> codec) {
        throw new AssertionError();
    }
}

