/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArraySet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.util.ThreadingDetector;
import net.minecraft.util.ZeroBitStorage;
import net.minecraft.world.level.chunk.GlobalPalette;
import net.minecraft.world.level.chunk.HashMapPalette;
import net.minecraft.world.level.chunk.LinearPalette;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PaletteResize;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.chunk.SingleValuePalette;

public class PalettedContainer<T>
implements PaletteResize<T>,
PalettedContainerRO<T> {
    private static final int f_188031_ = 0;
    private final PaletteResize<T> f_63070_ = (p_238275_, p_238276_) -> 0;
    private final IdMap<T> f_63071_;
    private volatile Data<T> f_188032_;
    private final Strategy f_188033_;
    private final ThreadingDetector f_199441_ = new ThreadingDetector("PalettedContainer");

    public void m_63084_() {
        this.f_199441_.m_199416_();
    }

    public void m_63120_() {
        this.f_199441_.m_199422_();
    }

    public static <T> Codec<PalettedContainer<T>> m_238371_(IdMap<T> p_238372_, Codec<T> p_238373_, Strategy p_238374_, T p_238375_) {
        PalettedContainerRO.Unpacker $$4 = PalettedContainer::m_188067_;
        return PalettedContainer.m_238427_(p_238372_, p_238373_, p_238374_, p_238375_, $$4);
    }

    public static <T> Codec<PalettedContainerRO<T>> m_238418_(IdMap<T> p_238419_, Codec<T> p_238420_, Strategy p_238421_, T p_238422_) {
        PalettedContainerRO.Unpacker $$4 = (p_238265_, p_238266_, p_238267_) -> PalettedContainer.m_188067_(p_238265_, p_238266_, p_238267_).map(p_238264_ -> p_238264_);
        return PalettedContainer.m_238427_(p_238419_, p_238420_, p_238421_, p_238422_, $$4);
    }

    private static <T, C extends PalettedContainerRO<T>> Codec<C> m_238427_(IdMap<T> p_238428_, Codec<T> p_238429_, Strategy p_238430_, T p_238431_, PalettedContainerRO.Unpacker<T, C> p_238432_) {
        return RecordCodecBuilder.create(p_188047_ -> p_188047_.group((App)p_238429_.mapResult(ExtraCodecs.m_184381_(p_238431_)).listOf().fieldOf("palette").forGetter(PalettedContainerRO.PackedData::f_238184_), (App)Codec.LONG_STREAM.optionalFieldOf("data").forGetter(PalettedContainerRO.PackedData::f_238179_)).apply((Applicative)p_188047_, PalettedContainerRO.PackedData::new)).comapFlatMap(p_238262_ -> p_238432_.m_238363_(p_238428_, p_238430_, (PalettedContainerRO.PackedData)p_238262_), p_238263_ -> p_238263_.m_188064_(p_238428_, p_238430_));
    }

    public PalettedContainer(IdMap<T> p_188035_, Strategy p_188036_, Configuration<T> p_188037_, BitStorage p_188038_, List<T> p_188039_) {
        this.f_63071_ = p_188035_;
        this.f_188033_ = p_188036_;
        this.f_188032_ = new Data<T>(p_188037_, p_188038_, p_188037_.f_188085_().m_188026_(p_188037_.f_188086_(), p_188035_, this, p_188039_));
    }

    private PalettedContainer(IdMap<T> p_199928_, Strategy p_199929_, Data<T> p_199930_) {
        this.f_63071_ = p_199928_;
        this.f_188033_ = p_199929_;
        this.f_188032_ = p_199930_;
    }

    public PalettedContainer(IdMap<T> p_188041_, T p_188042_, Strategy p_188043_) {
        this.f_188033_ = p_188043_;
        this.f_63071_ = p_188041_;
        this.f_188032_ = this.m_188051_(null, 0);
        this.f_188032_.f_188102_.m_6796_(p_188042_);
    }

    private Data<T> m_188051_(@Nullable Data<T> p_188052_, int p_188053_) {
        Configuration<T> $$2 = this.f_188033_.m_183248_(this.f_63071_, p_188053_);
        if (p_188052_ != null && $$2.equals(p_188052_.f_188100_())) {
            return p_188052_;
        }
        return $$2.m_188091_(this.f_63071_, this, this.f_188033_.m_188144_());
    }

    @Override
    public int m_7248_(int p_63142_, T p_63143_) {
        Data<T> $$2 = this.f_188032_;
        Data $$3 = this.m_188051_($$2, p_63142_);
        $$3.m_188111_($$2.f_188102_, $$2.f_188101_);
        this.f_188032_ = $$3;
        return $$3.f_188102_.m_6796_(p_63143_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public T m_63091_(int p_63092_, int p_63093_, int p_63094_, T p_63095_) {
        this.m_63084_();
        try {
            T t = this.m_63096_(this.f_188033_.m_188145_(p_63092_, p_63093_, p_63094_), p_63095_);
            return t;
        }
        finally {
            this.m_63120_();
        }
    }

    public T m_63127_(int p_63128_, int p_63129_, int p_63130_, T p_63131_) {
        return this.m_63096_(this.f_188033_.m_188145_(p_63128_, p_63129_, p_63130_), p_63131_);
    }

    private T m_63096_(int p_63097_, T p_63098_) {
        int $$2 = this.f_188032_.f_188102_.m_6796_(p_63098_);
        int $$3 = this.f_188032_.f_188101_.m_13516_(p_63097_, $$2);
        return this.f_188032_.f_188102_.m_5795_($$3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_156470_(int p_156471_, int p_156472_, int p_156473_, T p_156474_) {
        this.m_63084_();
        try {
            this.m_63132_(this.f_188033_.m_188145_(p_156471_, p_156472_, p_156473_), p_156474_);
        }
        finally {
            this.m_63120_();
        }
    }

    private void m_63132_(int p_63133_, T p_63134_) {
        int $$2 = this.f_188032_.f_188102_.m_6796_(p_63134_);
        this.f_188032_.f_188101_.m_13524_(p_63133_, $$2);
    }

    @Override
    public T m_63087_(int p_63088_, int p_63089_, int p_63090_) {
        return this.m_63085_(this.f_188033_.m_188145_(p_63088_, p_63089_, p_63090_));
    }

    protected T m_63085_(int p_63086_) {
        Data<T> $$1 = this.f_188032_;
        return $$1.f_188102_.m_5795_($$1.f_188101_.m_13514_(p_63086_));
    }

    @Override
    public void m_196879_(Consumer<T> p_196880_) {
        Palette $$1 = this.f_188032_.f_188102_();
        IntArraySet $$2 = new IntArraySet();
        this.f_188032_.f_188101_.m_13519_(arg_0 -> ((IntSet)$$2).add(arg_0));
        $$2.forEach(p_238274_ -> p_196880_.accept($$1.m_5795_(p_238274_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_63118_(FriendlyByteBuf p_63119_) {
        this.m_63084_();
        try {
            byte $$1 = p_63119_.readByte();
            Data<T> $$2 = this.m_188051_(this.f_188032_, $$1);
            $$2.f_188102_.m_5680_(p_63119_);
            p_63119_.m_130105_($$2.f_188101_.m_13513_());
            this.f_188032_ = $$2;
        }
        finally {
            this.m_63120_();
        }
    }

    @Override
    public void m_63135_(FriendlyByteBuf p_63136_) {
        this.m_63084_();
        try {
            this.f_188032_.m_188114_(p_63136_);
        }
        finally {
            this.m_63120_();
        }
    }

    /*
     * WARNING - void declaration
     */
    private static <T> DataResult<PalettedContainer<T>> m_188067_(IdMap<T> p_188068_, Strategy p_188069_, PalettedContainerRO.PackedData<T> p_238258_) {
        void $$16;
        List<T> $$3 = p_238258_.f_238184_();
        int $$4 = p_188069_.m_188144_();
        int $$5 = p_188069_.m_188151_(p_188068_, $$3.size());
        Configuration<T> $$6 = p_188069_.m_183248_(p_188068_, $$5);
        if ($$5 == 0) {
            ZeroBitStorage $$7 = new ZeroBitStorage($$4);
        } else {
            Optional<LongStream> $$8 = p_238258_.f_238179_();
            if ($$8.isEmpty()) {
                return DataResult.error((String)"Missing values for non-zero storage");
            }
            long[] $$9 = $$8.get().toArray();
            try {
                if ($$6.f_188085_() == Strategy.f_188139_) {
                    HashMapPalette<Object> $$10 = new HashMapPalette<Object>(p_188068_, $$5, (p_238278_, p_238279_) -> 0, $$3);
                    SimpleBitStorage $$11 = new SimpleBitStorage($$5, $$4, $$9);
                    int[] $$12 = new int[$$4];
                    $$11.m_197970_($$12);
                    PalettedContainer.m_198189_($$12, p_238283_ -> p_188068_.m_7447_($$10.m_5795_(p_238283_)));
                    SimpleBitStorage $$13 = new SimpleBitStorage($$6.f_188086_(), $$4, $$12);
                } else {
                    SimpleBitStorage $$14 = new SimpleBitStorage($$6.f_188086_(), $$4, $$9);
                }
            }
            catch (SimpleBitStorage.InitializationException $$15) {
                return DataResult.error((String)("Failed to read PalettedContainer: " + $$15.getMessage()));
            }
        }
        return DataResult.success(new PalettedContainer<T>(p_188068_, p_188069_, $$6, (BitStorage)$$16, $$3));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PalettedContainerRO.PackedData<T> m_188064_(IdMap<T> p_188065_, Strategy p_188066_) {
        this.m_63084_();
        try {
            Optional<LongStream> $$8;
            HashMapPalette<T> $$2 = new HashMapPalette<T>(p_188065_, this.f_188032_.f_188101_.m_144604_(), this.f_63070_);
            int $$3 = p_188066_.m_188144_();
            int[] $$4 = new int[$$3];
            this.f_188032_.f_188101_.m_197970_($$4);
            PalettedContainer.m_198189_($$4, p_198178_ -> $$2.m_6796_(this.f_188032_.f_188102_.m_5795_(p_198178_)));
            int $$5 = p_188066_.m_188151_(p_188065_, $$2.m_62680_());
            if ($$5 != 0) {
                SimpleBitStorage $$6 = new SimpleBitStorage($$5, $$3, $$4);
                Optional<LongStream> $$7 = Optional.of(Arrays.stream($$6.m_13513_()));
            } else {
                $$8 = Optional.empty();
            }
            PalettedContainerRO.PackedData<T> packedData = new PalettedContainerRO.PackedData<T>($$2.m_187917_(), $$8);
            return packedData;
        }
        finally {
            this.m_63120_();
        }
    }

    private static <T> void m_198189_(int[] p_198190_, IntUnaryOperator p_198191_) {
        int $$2 = -1;
        int $$3 = -1;
        for (int $$4 = 0; $$4 < p_198190_.length; ++$$4) {
            int $$5 = p_198190_[$$4];
            if ($$5 != $$2) {
                $$2 = $$5;
                $$3 = p_198191_.applyAsInt($$5);
            }
            p_198190_[$$4] = $$3;
        }
    }

    @Override
    public int m_63137_() {
        return this.f_188032_.m_188107_();
    }

    @Override
    public boolean m_63109_(Predicate<T> p_63110_) {
        return this.f_188032_.f_188102_.m_6419_(p_63110_);
    }

    public PalettedContainer<T> m_199931_() {
        return new PalettedContainer<T>(this.f_63071_, this.f_188033_, this.f_188032_.m_238361_());
    }

    @Override
    public PalettedContainer<T> m_238334_() {
        return new PalettedContainer<T>(this.f_63071_, this.f_188032_.f_188102_.m_5795_(0), this.f_188033_);
    }

    @Override
    public void m_63099_(CountConsumer<T> p_63100_) {
        if (this.f_188032_.f_188102_.m_62680_() == 1) {
            p_63100_.m_63144_(this.f_188032_.f_188102_.m_5795_(0), this.f_188032_.f_188101_.m_13521_());
            return;
        }
        Int2IntOpenHashMap $$1 = new Int2IntOpenHashMap();
        this.f_188032_.f_188101_.m_13519_(p_238269_ -> $$1.addTo(p_238269_, 1));
        $$1.int2IntEntrySet().forEach(p_238271_ -> p_63100_.m_63144_(this.f_188032_.f_188102_.m_5795_(p_238271_.getIntKey()), p_238271_.getIntValue()));
    }

    public static abstract class Strategy {
        public static final Palette.Factory f_188134_ = SingleValuePalette::m_188213_;
        public static final Palette.Factory f_188135_ = LinearPalette::m_188019_;
        public static final Palette.Factory f_188136_ = HashMapPalette::m_187912_;
        static final Palette.Factory f_188139_ = GlobalPalette::m_187898_;
        public static final Strategy f_188137_ = new Strategy(4){

            @Override
            public <A> Configuration<A> m_183248_(IdMap<A> p_188157_, int p_188158_) {
                return switch (p_188158_) {
                    case 0 -> new Configuration(f_188134_, p_188158_);
                    case 1, 2, 3, 4 -> new Configuration(f_188135_, 4);
                    case 5, 6, 7, 8 -> new Configuration(f_188136_, p_188158_);
                    default -> new Configuration(f_188139_, Mth.m_14163_(p_188157_.m_13562_()));
                };
            }
        };
        public static final Strategy f_188138_ = new Strategy(2){

            @Override
            public <A> Configuration<A> m_183248_(IdMap<A> p_188162_, int p_188163_) {
                return switch (p_188163_) {
                    case 0 -> new Configuration(f_188134_, p_188163_);
                    case 1, 2, 3 -> new Configuration(f_188135_, p_188163_);
                    default -> new Configuration(f_188139_, Mth.m_14163_(p_188162_.m_13562_()));
                };
            }
        };
        private final int f_188140_;

        Strategy(int p_188143_) {
            this.f_188140_ = p_188143_;
        }

        public int m_188144_() {
            return 1 << this.f_188140_ * 3;
        }

        public int m_188145_(int p_188146_, int p_188147_, int p_188148_) {
            return (p_188147_ << this.f_188140_ | p_188148_) << this.f_188140_ | p_188146_;
        }

        public abstract <A> Configuration<A> m_183248_(IdMap<A> var1, int var2);

        <A> int m_188151_(IdMap<A> p_188152_, int p_188153_) {
            int $$2 = Mth.m_14163_(p_188153_);
            Configuration<A> $$3 = this.m_183248_(p_188152_, $$2);
            return $$3.f_188085_() == f_188139_ ? $$2 : $$3.f_188086_();
        }
    }

    record Data<T>(Configuration<T> f_188100_, BitStorage f_188101_, Palette<T> f_188102_) {
        public void m_188111_(Palette<T> p_188112_, BitStorage p_188113_) {
            for (int $$2 = 0; $$2 < p_188113_.m_13521_(); ++$$2) {
                T $$3 = p_188112_.m_5795_(p_188113_.m_13514_($$2));
                this.f_188101_.m_13524_($$2, this.f_188102_.m_6796_($$3));
            }
        }

        public int m_188107_() {
            return 1 + this.f_188102_.m_6429_() + FriendlyByteBuf.m_130053_(this.f_188101_.m_13521_()) + this.f_188101_.m_13513_().length * 8;
        }

        public void m_188114_(FriendlyByteBuf p_188115_) {
            p_188115_.writeByte(this.f_188101_.m_144604_());
            this.f_188102_.m_5678_(p_188115_);
            p_188115_.m_130091_(this.f_188101_.m_13513_());
        }

        public Data<T> m_238361_() {
            return new Data<T>(this.f_188100_, this.f_188101_.m_199833_(), this.f_188102_.m_199814_());
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Data.class, "configuration;storage;palette", "f_188100_", "f_188101_", "f_188102_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Data.class, "configuration;storage;palette", "f_188100_", "f_188101_", "f_188102_"}, this);
        }

        @Override
        public final boolean equals(Object p_188120_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Data.class, "configuration;storage;palette", "f_188100_", "f_188101_", "f_188102_"}, this, p_188120_);
        }
    }

    record Configuration<T>(Palette.Factory f_188085_, int f_188086_) {
        public Data<T> m_188091_(IdMap<T> p_188092_, PaletteResize<T> p_188093_, int p_188094_) {
            BitStorage $$3 = this.f_188086_ == 0 ? new ZeroBitStorage(p_188094_) : new SimpleBitStorage(this.f_188086_, p_188094_);
            Palette<T> $$4 = this.f_188085_.m_188026_(this.f_188086_, p_188092_, p_188093_, List.of());
            return new Data<T>(this, $$3, $$4);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Configuration.class, "factory;bits", "f_188085_", "f_188086_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Configuration.class, "factory;bits", "f_188085_", "f_188086_"}, this);
        }

        @Override
        public final boolean equals(Object p_188097_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Configuration.class, "factory;bits", "f_188085_", "f_188086_"}, this, p_188097_);
        }
    }

    @FunctionalInterface
    public static interface CountConsumer<T> {
        public void m_63144_(T var1, int var2);
    }
}

