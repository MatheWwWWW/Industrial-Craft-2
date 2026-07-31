/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MangroveRootPlacement(HolderSet<Block> f_225773_, HolderSet<Block> f_225774_, BlockStateProvider f_225775_, int f_225776_, int f_225777_, float f_225778_) {
    public static final Codec<MangroveRootPlacement> f_225772_ = RecordCodecBuilder.create(p_225789_ -> p_225789_.group((App)RegistryCodecs.m_206277_(Registry.f_122901_).fieldOf("can_grow_through").forGetter(p_225808_ -> p_225808_.f_225773_), (App)RegistryCodecs.m_206277_(Registry.f_122901_).fieldOf("muddy_roots_in").forGetter(p_225803_ -> p_225803_.f_225774_), (App)BlockStateProvider.f_68747_.fieldOf("muddy_roots_provider").forGetter(p_225800_ -> p_225800_.f_225775_), (App)Codec.intRange((int)1, (int)12).fieldOf("max_root_width").forGetter(p_225797_ -> p_225797_.f_225776_), (App)Codec.intRange((int)1, (int)64).fieldOf("max_root_length").forGetter(p_225794_ -> p_225794_.f_225777_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("random_skew_chance").forGetter(p_225791_ -> Float.valueOf(p_225791_.f_225778_))).apply((Applicative)p_225789_, MangroveRootPlacement::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{MangroveRootPlacement.class, "canGrowThrough;muddyRootsIn;muddyRootsProvider;maxRootWidth;maxRootLength;randomSkewChance", "f_225773_", "f_225774_", "f_225775_", "f_225776_", "f_225777_", "f_225778_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{MangroveRootPlacement.class, "canGrowThrough;muddyRootsIn;muddyRootsProvider;maxRootWidth;maxRootLength;randomSkewChance", "f_225773_", "f_225774_", "f_225775_", "f_225776_", "f_225777_", "f_225778_"}, this);
    }

    @Override
    public final boolean equals(Object p_225805_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{MangroveRootPlacement.class, "canGrowThrough;muddyRootsIn;muddyRootsProvider;maxRootWidth;maxRootLength;randomSkewChance", "f_225773_", "f_225774_", "f_225775_", "f_225776_", "f_225777_", "f_225778_"}, this, p_225805_);
    }
}

