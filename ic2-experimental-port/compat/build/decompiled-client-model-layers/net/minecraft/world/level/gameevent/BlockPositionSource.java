/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.gameevent;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.phys.Vec3;

public class BlockPositionSource
implements PositionSource {
    public static final Codec<BlockPositionSource> f_157699_ = RecordCodecBuilder.create(p_157710_ -> p_157710_.group((App)BlockPos.f_121852_.fieldOf("pos").forGetter(p_223611_ -> p_223611_.f_157700_)).apply((Applicative)p_157710_, BlockPositionSource::new));
    final BlockPos f_157700_;

    public BlockPositionSource(BlockPos p_157703_) {
        this.f_157700_ = p_157703_;
    }

    @Override
    public Optional<Vec3> m_142502_(Level p_157708_) {
        return Optional.of(Vec3.m_82512_(this.f_157700_));
    }

    @Override
    public PositionSourceType<?> m_142510_() {
        return PositionSourceType.f_157871_;
    }

    public static class Type
    implements PositionSourceType<BlockPositionSource> {
        @Override
        public BlockPositionSource m_142281_(FriendlyByteBuf p_157716_) {
            return new BlockPositionSource(p_157716_.m_130135_());
        }

        @Override
        public void m_142235_(FriendlyByteBuf p_157718_, BlockPositionSource p_157719_) {
            p_157718_.m_130064_(p_157719_.f_157700_);
        }

        @Override
        public Codec<BlockPositionSource> m_142341_() {
            return f_157699_;
        }

        @Override
        public /* synthetic */ PositionSource m_142281_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_142281_(friendlyByteBuf);
        }
    }
}

