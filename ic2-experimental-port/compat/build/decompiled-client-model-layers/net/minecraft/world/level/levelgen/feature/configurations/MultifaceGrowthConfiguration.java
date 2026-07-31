/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class MultifaceGrowthConfiguration
implements FeatureConfiguration {
    public static final Codec<MultifaceGrowthConfiguration> f_225381_ = RecordCodecBuilder.create(p_225407_ -> p_225407_.group((App)Registry.f_122824_.m_194605_().fieldOf("block").flatXmap(MultifaceGrowthConfiguration::m_225404_, DataResult::success).orElse((Object)((MultifaceBlock)Blocks.f_152475_)).forGetter(p_225424_ -> p_225424_.f_225382_), (App)Codec.intRange((int)1, (int)64).fieldOf("search_range").orElse((Object)10).forGetter(p_225422_ -> p_225422_.f_225383_), (App)Codec.BOOL.fieldOf("can_place_on_floor").orElse((Object)false).forGetter(p_225420_ -> p_225420_.f_225384_), (App)Codec.BOOL.fieldOf("can_place_on_ceiling").orElse((Object)false).forGetter(p_225418_ -> p_225418_.f_225385_), (App)Codec.BOOL.fieldOf("can_place_on_wall").orElse((Object)false).forGetter(p_225416_ -> p_225416_.f_225386_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("chance_of_spreading").orElse((Object)Float.valueOf(0.5f)).forGetter(p_225414_ -> Float.valueOf(p_225414_.f_225387_)), (App)RegistryCodecs.m_206277_(Registry.f_122901_).fieldOf("can_be_placed_on").forGetter(p_225409_ -> p_225409_.f_225388_)).apply((Applicative)p_225407_, MultifaceGrowthConfiguration::new));
    public final MultifaceBlock f_225382_;
    public final int f_225383_;
    public final boolean f_225384_;
    public final boolean f_225385_;
    public final boolean f_225386_;
    public final float f_225387_;
    public final HolderSet<Block> f_225388_;
    private final ObjectArrayList<Direction> f_225389_;

    private static DataResult<MultifaceBlock> m_225404_(Block p_225405_) {
        DataResult dataResult;
        if (p_225405_ instanceof MultifaceBlock) {
            MultifaceBlock $$1 = (MultifaceBlock)p_225405_;
            dataResult = DataResult.success((Object)$$1);
        } else {
            dataResult = DataResult.error((String)"Growth block should be a multiface block");
        }
        return dataResult;
    }

    public MultifaceGrowthConfiguration(MultifaceBlock p_225392_, int p_225393_, boolean p_225394_, boolean p_225395_, boolean p_225396_, float p_225397_, HolderSet<Block> p_225398_) {
        this.f_225382_ = p_225392_;
        this.f_225383_ = p_225393_;
        this.f_225384_ = p_225394_;
        this.f_225385_ = p_225395_;
        this.f_225386_ = p_225396_;
        this.f_225387_ = p_225397_;
        this.f_225388_ = p_225398_;
        this.f_225389_ = new ObjectArrayList(6);
        if (p_225395_) {
            this.f_225389_.add((Object)Direction.UP);
        }
        if (p_225394_) {
            this.f_225389_.add((Object)Direction.DOWN);
        }
        if (p_225396_) {
            Direction.Plane.HORIZONTAL.forEach(arg_0 -> this.f_225389_.add(arg_0));
        }
    }

    public List<Direction> m_225401_(RandomSource p_225402_, Direction p_225403_) {
        return Util.m_214661_(this.f_225389_.stream().filter(p_225412_ -> p_225412_ != p_225403_), p_225402_);
    }

    public List<Direction> m_225399_(RandomSource p_225400_) {
        return Util.m_214611_(this.f_225389_, p_225400_);
    }
}

