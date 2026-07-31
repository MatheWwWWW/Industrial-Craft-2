/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  javax.annotation.Nullable
 */
package net.minecraft.world.ticks;

import it.unimi.dsi.fastutil.Hash;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickPriority;

public record SavedTick<T>(T f_193311_, BlockPos f_193312_, int f_193313_, TickPriority f_193314_) {
    private static final String f_193315_ = "i";
    private static final String f_193316_ = "x";
    private static final String f_193317_ = "y";
    private static final String f_193318_ = "z";
    private static final String f_193319_ = "t";
    private static final String f_193320_ = "p";
    public static final Hash.Strategy<SavedTick<?>> f_193310_ = new Hash.Strategy<SavedTick<?>>(){

        public int hashCode(SavedTick<?> p_193364_) {
            return 31 * p_193364_.f_193312_().hashCode() + p_193364_.f_193311_().hashCode();
        }

        public boolean equals(@Nullable SavedTick<?> p_193366_, @Nullable SavedTick<?> p_193367_) {
            if (p_193366_ == p_193367_) {
                return true;
            }
            if (p_193366_ == null || p_193367_ == null) {
                return false;
            }
            return p_193366_.f_193311_() == p_193367_.f_193311_() && p_193366_.f_193312_().equals(p_193367_.f_193312_());
        }

        public /* synthetic */ boolean equals(@Nullable Object object, @Nullable Object object2) {
            return this.equals((SavedTick)object, (SavedTick)object2);
        }

        public /* synthetic */ int hashCode(Object object) {
            return this.hashCode((SavedTick)object);
        }
    };

    public static <T> void m_193350_(ListTag p_193351_, Function<String, Optional<T>> p_193352_, ChunkPos p_193353_, Consumer<SavedTick<T>> p_193354_) {
        long $$4 = p_193353_.m_45588_();
        for (int $$5 = 0; $$5 < p_193351_.size(); ++$$5) {
            CompoundTag $$6 = p_193351_.m_128728_($$5);
            SavedTick.m_210669_($$6, p_193352_).ifPresent(p_210665_ -> {
                if (ChunkPos.m_151388_(p_210665_.f_193312_()) == $$4) {
                    p_193354_.accept((SavedTick)p_210665_);
                }
            });
        }
    }

    public static <T> Optional<SavedTick<T>> m_210669_(CompoundTag p_210670_, Function<String, Optional<T>> p_210671_) {
        return p_210671_.apply(p_210670_.m_128461_(f_193315_)).map(p_210668_ -> {
            BlockPos $$2 = new BlockPos(p_210670_.m_128451_(f_193316_), p_210670_.m_128451_(f_193317_), p_210670_.m_128451_(f_193318_));
            return new SavedTick<Object>(p_210668_, $$2, p_210670_.m_128451_(f_193319_), TickPriority.m_193446_(p_210670_.m_128451_(f_193320_)));
        });
    }

    private static CompoundTag m_193338_(String p_193339_, BlockPos p_193340_, int p_193341_, TickPriority p_193342_) {
        CompoundTag $$4 = new CompoundTag();
        $$4.m_128359_(f_193315_, p_193339_);
        $$4.m_128405_(f_193316_, p_193340_.m_123341_());
        $$4.m_128405_(f_193317_, p_193340_.m_123342_());
        $$4.m_128405_(f_193318_, p_193340_.m_123343_());
        $$4.m_128405_(f_193319_, p_193341_);
        $$4.m_128405_(f_193320_, p_193342_.m_193445_());
        return $$4;
    }

    public static <T> CompoundTag m_193331_(ScheduledTick<T> p_193332_, Function<T, String> p_193333_, long p_193334_) {
        return SavedTick.m_193338_(p_193333_.apply(p_193332_.f_193376_()), p_193332_.f_193377_(), (int)(p_193332_.f_193378_() - p_193334_), p_193332_.f_193379_());
    }

    public CompoundTag m_193343_(Function<T, String> p_193344_) {
        return SavedTick.m_193338_(p_193344_.apply(this.f_193311_), this.f_193312_, this.f_193313_, this.f_193314_);
    }

    public ScheduledTick<T> m_193328_(long p_193329_, long p_193330_) {
        return new ScheduledTick<T>(this.f_193311_, this.f_193312_, p_193329_ + (long)this.f_193313_, this.f_193314_, p_193330_);
    }

    public static <T> SavedTick<T> m_193335_(T p_193336_, BlockPos p_193337_) {
        return new SavedTick<T>(p_193336_, p_193337_, 0, TickPriority.NORMAL);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{SavedTick.class, "type;pos;delay;priority", "f_193311_", "f_193312_", "f_193313_", "f_193314_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SavedTick.class, "type;pos;delay;priority", "f_193311_", "f_193312_", "f_193313_", "f_193314_"}, this);
    }

    @Override
    public final boolean equals(Object p_193359_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SavedTick.class, "type;pos;delay;priority", "f_193311_", "f_193312_", "f_193313_", "f_193314_"}, this, p_193359_);
    }
}

