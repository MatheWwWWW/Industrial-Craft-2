/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.syncher;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EntityDataSerializers {
    private static final CrudeIncrementalIntIdentityHashBiMap<EntityDataSerializer<?>> f_135046_ = CrudeIncrementalIntIdentityHashBiMap.m_184237_(16);
    public static final EntityDataSerializer<Byte> f_135027_ = EntityDataSerializer.m_238095_((p_238118_, p_238119_) -> p_238118_.writeByte(p_238119_.byteValue()), FriendlyByteBuf::readByte);
    public static final EntityDataSerializer<Integer> f_135028_ = EntityDataSerializer.m_238095_(FriendlyByteBuf::m_130130_, FriendlyByteBuf::m_130242_);
    public static final EntityDataSerializer<Float> f_135029_ = EntityDataSerializer.m_238095_(FriendlyByteBuf::writeFloat, FriendlyByteBuf::readFloat);
    public static final EntityDataSerializer<String> f_135030_ = EntityDataSerializer.m_238095_(FriendlyByteBuf::m_130070_, FriendlyByteBuf::m_130277_);
    public static final EntityDataSerializer<Component> f_135031_ = EntityDataSerializer.m_238095_(FriendlyByteBuf::m_130083_, FriendlyByteBuf::m_130238_);
    public static final EntityDataSerializer<Optional<Component>> f_135032_ = EntityDataSerializer.m_238098_(FriendlyByteBuf::m_130083_, FriendlyByteBuf::m_130238_);
    public static final EntityDataSerializer<ItemStack> f_135033_ = new EntityDataSerializer<ItemStack>(){

        @Override
        public void m_6856_(FriendlyByteBuf p_238123_, ItemStack p_238124_) {
            p_238123_.m_130055_(p_238124_);
        }

        @Override
        public ItemStack m_6709_(FriendlyByteBuf p_238126_) {
            return p_238126_.m_130267_();
        }

        @Override
        public ItemStack m_7020_(ItemStack p_238121_) {
            return p_238121_.m_41777_();
        }

        @Override
        public /* synthetic */ Object m_6709_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_6709_(friendlyByteBuf);
        }
    };
    public static final EntityDataSerializer<Optional<BlockState>> f_135034_ = new EntityDataSerializer.ForValueType<Optional<BlockState>>(){

        @Override
        public void m_6856_(FriendlyByteBuf p_238128_, Optional<BlockState> p_238129_) {
            if (p_238129_.isPresent()) {
                p_238128_.m_130130_(Block.m_49956_(p_238129_.get()));
            } else {
                p_238128_.m_130130_(0);
            }
        }

        @Override
        public Optional<BlockState> m_6709_(FriendlyByteBuf p_238131_) {
            int $$1 = p_238131_.m_130242_();
            if ($$1 == 0) {
                return Optional.empty();
            }
            return Optional.of(Block.m_49803_($$1));
        }

        @Override
        public /* synthetic */ Object m_6709_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_6709_(friendlyByteBuf);
        }
    };
    public static final EntityDataSerializer<Boolean> f_135035_ = EntityDataSerializer.m_238095_(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean);
    public static final EntityDataSerializer<ParticleOptions> f_135036_ = new EntityDataSerializer.ForValueType<ParticleOptions>(){

        @Override
        public void m_6856_(FriendlyByteBuf p_238133_, ParticleOptions p_238134_) {
            p_238133_.m_236818_(Registry.f_122829_, p_238134_.m_6012_());
            p_238134_.m_7711_(p_238133_);
        }

        @Override
        public ParticleOptions m_6709_(FriendlyByteBuf p_238139_) {
            return this.m_238135_(p_238139_, p_238139_.m_236816_(Registry.f_122829_));
        }

        private <T extends ParticleOptions> T m_238135_(FriendlyByteBuf p_238136_, ParticleType<T> p_238137_) {
            return p_238137_.m_123743_().m_6507_(p_238137_, p_238136_);
        }

        @Override
        public /* synthetic */ Object m_6709_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_6709_(friendlyByteBuf);
        }
    };
    public static final EntityDataSerializer<Rotations> f_135037_ = new EntityDataSerializer.ForValueType<Rotations>(){

        @Override
        public void m_6856_(FriendlyByteBuf p_238141_, Rotations p_238142_) {
            p_238141_.writeFloat(p_238142_.m_123156_());
            p_238141_.writeFloat(p_238142_.m_123157_());
            p_238141_.writeFloat(p_238142_.m_123158_());
        }

        @Override
        public Rotations m_6709_(FriendlyByteBuf p_238144_) {
            return new Rotations(p_238144_.readFloat(), p_238144_.readFloat(), p_238144_.readFloat());
        }

        @Override
        public /* synthetic */ Object m_6709_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_6709_(friendlyByteBuf);
        }
    };
    public static final EntityDataSerializer<BlockPos> f_135038_ = EntityDataSerializer.m_238095_(FriendlyByteBuf::m_130064_, FriendlyByteBuf::m_130135_);
    public static final EntityDataSerializer<Optional<BlockPos>> f_135039_ = EntityDataSerializer.m_238098_(FriendlyByteBuf::m_130064_, FriendlyByteBuf::m_130135_);
    public static final EntityDataSerializer<Direction> f_135040_ = EntityDataSerializer.m_238090_(Direction.class);
    public static final EntityDataSerializer<Optional<UUID>> f_135041_ = EntityDataSerializer.m_238098_(FriendlyByteBuf::m_130077_, FriendlyByteBuf::m_130259_);
    public static final EntityDataSerializer<Optional<GlobalPos>> f_238113_ = EntityDataSerializer.m_238098_(FriendlyByteBuf::m_236814_, FriendlyByteBuf::m_236872_);
    public static final EntityDataSerializer<CompoundTag> f_135042_ = new EntityDataSerializer<CompoundTag>(){

        @Override
        public void m_6856_(FriendlyByteBuf p_238148_, CompoundTag p_238149_) {
            p_238148_.m_130079_(p_238149_);
        }

        @Override
        public CompoundTag m_6709_(FriendlyByteBuf p_238151_) {
            return p_238151_.m_130260_();
        }

        @Override
        public CompoundTag m_7020_(CompoundTag p_238146_) {
            return p_238146_.m_6426_();
        }

        @Override
        public /* synthetic */ Object m_6709_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_6709_(friendlyByteBuf);
        }
    };
    public static final EntityDataSerializer<VillagerData> f_135043_ = new EntityDataSerializer.ForValueType<VillagerData>(){

        @Override
        public void m_6856_(FriendlyByteBuf p_238153_, VillagerData p_238154_) {
            p_238153_.m_236818_(Registry.f_122868_, p_238154_.m_35560_());
            p_238153_.m_236818_(Registry.f_122869_, p_238154_.m_35571_());
            p_238153_.m_130130_(p_238154_.m_35576_());
        }

        @Override
        public VillagerData m_6709_(FriendlyByteBuf p_238156_) {
            return new VillagerData(p_238156_.m_236816_(Registry.f_122868_), p_238156_.m_236816_(Registry.f_122869_), p_238156_.m_130242_());
        }

        @Override
        public /* synthetic */ Object m_6709_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_6709_(friendlyByteBuf);
        }
    };
    public static final EntityDataSerializer<OptionalInt> f_135044_ = new EntityDataSerializer.ForValueType<OptionalInt>(){

        @Override
        public void m_6856_(FriendlyByteBuf p_238158_, OptionalInt p_238159_) {
            p_238158_.m_130130_(p_238159_.orElse(-1) + 1);
        }

        @Override
        public OptionalInt m_6709_(FriendlyByteBuf p_243339_) {
            int $$1 = p_243339_.m_130242_();
            return $$1 == 0 ? OptionalInt.empty() : OptionalInt.of($$1 - 1);
        }

        @Override
        public /* synthetic */ Object m_6709_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_6709_(friendlyByteBuf);
        }
    };
    public static final EntityDataSerializer<Pose> f_135045_ = EntityDataSerializer.m_238090_(Pose.class);
    public static final EntityDataSerializer<CatVariant> f_238114_ = EntityDataSerializer.m_238081_(Registry.f_235732_);
    public static final EntityDataSerializer<FrogVariant> f_238115_ = EntityDataSerializer.m_238081_(Registry.f_235734_);
    public static final EntityDataSerializer<Holder<PaintingVariant>> f_238116_ = EntityDataSerializer.m_238081_(Registry.f_235728_.m_206115_());

    public static void m_135050_(EntityDataSerializer<?> p_135051_) {
        f_135046_.m_13569_(p_135051_);
    }

    @Nullable
    public static EntityDataSerializer<?> m_135048_(int p_135049_) {
        return f_135046_.m_7942_(p_135049_);
    }

    public static int m_135052_(EntityDataSerializer<?> p_135053_) {
        return f_135046_.m_7447_(p_135053_);
    }

    private EntityDataSerializers() {
    }

    static {
        EntityDataSerializers.m_135050_(f_135027_);
        EntityDataSerializers.m_135050_(f_135028_);
        EntityDataSerializers.m_135050_(f_135029_);
        EntityDataSerializers.m_135050_(f_135030_);
        EntityDataSerializers.m_135050_(f_135031_);
        EntityDataSerializers.m_135050_(f_135032_);
        EntityDataSerializers.m_135050_(f_135033_);
        EntityDataSerializers.m_135050_(f_135035_);
        EntityDataSerializers.m_135050_(f_135037_);
        EntityDataSerializers.m_135050_(f_135038_);
        EntityDataSerializers.m_135050_(f_135039_);
        EntityDataSerializers.m_135050_(f_135040_);
        EntityDataSerializers.m_135050_(f_135041_);
        EntityDataSerializers.m_135050_(f_135034_);
        EntityDataSerializers.m_135050_(f_135042_);
        EntityDataSerializers.m_135050_(f_135036_);
        EntityDataSerializers.m_135050_(f_135043_);
        EntityDataSerializers.m_135050_(f_135044_);
        EntityDataSerializers.m_135050_(f_135045_);
        EntityDataSerializers.m_135050_(f_238114_);
        EntityDataSerializers.m_135050_(f_238115_);
        EntityDataSerializers.m_135050_(f_238113_);
        EntityDataSerializers.m_135050_(f_238116_);
    }
}

