/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class RandomizedIntStateProvider
extends BlockStateProvider {
    public static final Codec<RandomizedIntStateProvider> f_161555_ = RecordCodecBuilder.create(p_161576_ -> p_161576_.group((App)BlockStateProvider.f_68747_.fieldOf("source").forGetter(p_161592_ -> p_161592_.f_161556_), (App)Codec.STRING.fieldOf("property").forGetter(p_161590_ -> p_161590_.f_161557_), (App)IntProvider.f_146531_.fieldOf("values").forGetter(p_161578_ -> p_161578_.f_161559_)).apply((Applicative)p_161576_, RandomizedIntStateProvider::new));
    private final BlockStateProvider f_161556_;
    private final String f_161557_;
    @Nullable
    private IntegerProperty f_161558_;
    private final IntProvider f_161559_;

    public RandomizedIntStateProvider(BlockStateProvider p_161562_, IntegerProperty p_161563_, IntProvider p_161564_) {
        this.f_161556_ = p_161562_;
        this.f_161558_ = p_161563_;
        this.f_161557_ = p_161563_.m_61708_();
        this.f_161559_ = p_161564_;
        Collection<Integer> $$3 = p_161563_.m_6908_();
        for (int $$4 = p_161564_.m_142739_(); $$4 <= p_161564_.m_142737_(); ++$$4) {
            if ($$3.contains($$4)) continue;
            throw new IllegalArgumentException("Property value out of range: " + p_161563_.m_61708_() + ": " + $$4);
        }
    }

    public RandomizedIntStateProvider(BlockStateProvider p_161566_, String p_161567_, IntProvider p_161568_) {
        this.f_161556_ = p_161566_;
        this.f_161557_ = p_161567_;
        this.f_161559_ = p_161568_;
    }

    @Override
    protected BlockStateProviderType<?> m_5923_() {
        return BlockStateProviderType.f_161554_;
    }

    @Override
    public BlockState m_213972_(RandomSource p_225919_, BlockPos p_225920_) {
        BlockState $$2 = this.f_161556_.m_213972_(p_225919_, p_225920_);
        if (this.f_161558_ == null || !$$2.m_61138_(this.f_161558_)) {
            this.f_161558_ = RandomizedIntStateProvider.m_161570_($$2, this.f_161557_);
        }
        return (BlockState)$$2.m_61124_(this.f_161558_, this.f_161559_.m_214085_(p_225919_));
    }

    private static IntegerProperty m_161570_(BlockState p_161571_, String p_161572_) {
        Collection<Property<?>> $$2 = p_161571_.m_61147_();
        Optional<IntegerProperty> $$3 = $$2.stream().filter(p_161583_ -> p_161583_.m_61708_().equals(p_161572_)).filter(p_161588_ -> p_161588_ instanceof IntegerProperty).map(p_161574_ -> (IntegerProperty)p_161574_).findAny();
        return $$3.orElseThrow(() -> new IllegalArgumentException("Illegal property: " + p_161572_));
    }
}

