/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.gameevent;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.phys.Vec3;

public class EntityPositionSource
implements PositionSource {
    public static final Codec<EntityPositionSource> f_157725_ = RecordCodecBuilder.create(p_223664_ -> p_223664_.group((App)ExtraCodecs.f_216157_.fieldOf("source_entity").forGetter(EntityPositionSource::m_223674_), (App)Codec.FLOAT.fieldOf("y_offset").orElse((Object)Float.valueOf(0.0f)).forGetter(p_223666_ -> Float.valueOf(p_223666_.f_223646_))).apply((Applicative)p_223664_, (p_223672_, p_223673_) -> new EntityPositionSource((Either<Entity, Either<UUID, Integer>>)Either.right((Object)Either.left((Object)p_223672_)), p_223673_.floatValue())));
    private Either<Entity, Either<UUID, Integer>> f_223645_;
    final float f_223646_;

    public EntityPositionSource(Entity p_223648_, float p_223649_) {
        this((Either<Entity, Either<UUID, Integer>>)Either.left((Object)p_223648_), p_223649_);
    }

    EntityPositionSource(Either<Entity, Either<UUID, Integer>> p_223651_, float p_223652_) {
        this.f_223645_ = p_223651_;
        this.f_223646_ = p_223652_;
    }

    @Override
    public Optional<Vec3> m_142502_(Level p_157733_) {
        if (this.f_223645_.left().isEmpty()) {
            this.m_223677_(p_157733_);
        }
        return this.f_223645_.left().map(p_223676_ -> p_223676_.m_20182_().m_82520_(0.0, this.f_223646_, 0.0));
    }

    private void m_223677_(Level p_223678_) {
        ((Optional)this.f_223645_.map(Optional::of, p_223657_ -> Optional.ofNullable((Entity)p_223657_.map(p_223660_ -> {
            Entity entity;
            if (p_223678_ instanceof ServerLevel) {
                ServerLevel $$2 = (ServerLevel)p_223678_;
                entity = $$2.m_8791_((UUID)p_223660_);
            } else {
                entity = null;
            }
            return entity;
        }, p_223678_::m_6815_)))).ifPresent(p_223654_ -> {
            this.f_223645_ = Either.left((Object)p_223654_);
        });
    }

    private UUID m_223674_() {
        return (UUID)this.f_223645_.map(Entity::m_20148_, p_223680_ -> (UUID)p_223680_.map(Function.identity(), p_223668_ -> {
            throw new RuntimeException("Unable to get entityId from uuid");
        }));
    }

    int m_223681_() {
        return (Integer)this.f_223645_.map(Entity::m_19879_, p_223662_ -> (Integer)p_223662_.map(p_223670_ -> {
            throw new IllegalStateException("Unable to get entityId from uuid");
        }, Function.identity()));
    }

    @Override
    public PositionSourceType<?> m_142510_() {
        return PositionSourceType.f_157872_;
    }

    public static class Type
    implements PositionSourceType<EntityPositionSource> {
        @Override
        public EntityPositionSource m_142281_(FriendlyByteBuf p_157741_) {
            return new EntityPositionSource((Either<Entity, Either<UUID, Integer>>)Either.right((Object)Either.right((Object)p_157741_.m_130242_())), p_157741_.readFloat());
        }

        @Override
        public void m_142235_(FriendlyByteBuf p_157743_, EntityPositionSource p_157744_) {
            p_157743_.m_130130_(p_157744_.m_223681_());
            p_157743_.writeFloat(p_157744_.f_223646_);
        }

        @Override
        public Codec<EntityPositionSource> m_142341_() {
            return f_157725_;
        }

        @Override
        public /* synthetic */ PositionSource m_142281_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_142281_(friendlyByteBuf);
        }
    }
}

