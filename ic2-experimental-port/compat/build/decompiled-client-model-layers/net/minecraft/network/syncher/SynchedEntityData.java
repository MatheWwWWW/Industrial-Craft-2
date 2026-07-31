/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  io.netty.handler.codec.DecoderException
 *  io.netty.handler.codec.EncoderException
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.ObjectUtils
 *  org.slf4j.Logger
 */
package net.minecraft.network.syncher;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class SynchedEntityData {
    private static final Logger f_135342_ = LogUtils.getLogger();
    private static final Object2IntMap<Class<? extends Entity>> f_135343_ = new Object2IntOpenHashMap();
    private static final int f_179842_ = 255;
    private static final int f_179843_ = 254;
    private final Entity f_135344_;
    private final Int2ObjectMap<DataItem<?>> f_135345_ = new Int2ObjectOpenHashMap();
    private final ReadWriteLock f_135346_ = new ReentrantReadWriteLock();
    private boolean f_135347_ = true;
    private boolean f_135348_;

    public SynchedEntityData(Entity p_135351_) {
        this.f_135344_ = p_135351_;
    }

    public static <T> EntityDataAccessor<T> m_135353_(Class<? extends Entity> p_135354_, EntityDataSerializer<T> p_135355_) {
        int $$6;
        if (f_135342_.isDebugEnabled()) {
            try {
                Class<?> $$2 = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
                if (!$$2.equals(p_135354_)) {
                    f_135342_.debug("defineId called for: {} from {}", new Object[]{p_135354_, $$2, new RuntimeException()});
                }
            }
            catch (ClassNotFoundException $$2) {
                // empty catch block
            }
        }
        if (f_135343_.containsKey(p_135354_)) {
            int $$3 = f_135343_.getInt(p_135354_) + 1;
        } else {
            int $$4 = 0;
            Class<? extends Entity> $$5 = p_135354_;
            while ($$5 != Entity.class) {
                if (!f_135343_.containsKey($$5 = $$5.getSuperclass())) continue;
                $$4 = f_135343_.getInt($$5) + 1;
                break;
            }
            $$6 = $$4;
        }
        if ($$6 > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + $$6 + "! (Max is 254)");
        }
        f_135343_.put(p_135354_, $$6);
        return p_135355_.m_135021_($$6);
    }

    public <T> void m_135372_(EntityDataAccessor<T> p_135373_, T p_135374_) {
        int $$2 = p_135373_.m_135015_();
        if ($$2 > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + $$2 + "! (Max is 254)");
        }
        if (this.f_135345_.containsKey($$2)) {
            throw new IllegalArgumentException("Duplicate id value for " + $$2 + "!");
        }
        if (EntityDataSerializers.m_135052_(p_135373_.m_135016_()) < 0) {
            throw new IllegalArgumentException("Unregistered serializer " + p_135373_.m_135016_() + " for " + $$2 + "!");
        }
        this.m_135385_(p_135373_, p_135374_);
    }

    private <T> void m_135385_(EntityDataAccessor<T> p_135386_, T p_135387_) {
        DataItem<T> $$2 = new DataItem<T>(p_135386_, p_135387_);
        this.f_135346_.writeLock().lock();
        this.f_135345_.put(p_135386_.m_135015_(), $$2);
        this.f_135347_ = false;
        this.f_135346_.writeLock().unlock();
    }

    /*
     * WARNING - void declaration
     */
    private <T> DataItem<T> m_135379_(EntityDataAccessor<T> p_135380_) {
        void $$5;
        this.f_135346_.readLock().lock();
        try {
            DataItem $$1 = (DataItem)this.f_135345_.get(p_135380_.m_135015_());
        }
        catch (Throwable $$2) {
            CrashReport $$3 = CrashReport.m_127521_($$2, "Getting synched entity data");
            CrashReportCategory $$4 = $$3.m_127514_("Synched entity data");
            $$4.m_128159_("Data ID", p_135380_);
            throw new ReportedException($$3);
        }
        finally {
            this.f_135346_.readLock().unlock();
        }
        return $$5;
    }

    public <T> T m_135370_(EntityDataAccessor<T> p_135371_) {
        return this.m_135379_(p_135371_).m_135403_();
    }

    public <T> void m_135381_(EntityDataAccessor<T> p_135382_, T p_135383_) {
        DataItem<T> $$2 = this.m_135379_(p_135382_);
        if (ObjectUtils.notEqual(p_135383_, $$2.m_135403_())) {
            $$2.m_135397_(p_135383_);
            this.f_135344_.m_7350_(p_135382_);
            $$2.m_135401_(true);
            this.f_135348_ = true;
        }
    }

    public boolean m_135352_() {
        return this.f_135348_;
    }

    public static void m_135358_(@Nullable List<DataItem<?>> p_135359_, FriendlyByteBuf p_135360_) {
        if (p_135359_ != null) {
            for (DataItem<?> $$2 : p_135359_) {
                SynchedEntityData.m_135367_(p_135360_, $$2);
            }
        }
        p_135360_.writeByte(255);
    }

    @Nullable
    public List<DataItem<?>> m_135378_() {
        ArrayList $$0 = null;
        if (this.f_135348_) {
            this.f_135346_.readLock().lock();
            for (DataItem $$1 : this.f_135345_.values()) {
                if (!$$1.m_135406_()) continue;
                $$1.m_135401_(false);
                if ($$0 == null) {
                    $$0 = Lists.newArrayList();
                }
                $$0.add($$1.m_135407_());
            }
            this.f_135346_.readLock().unlock();
        }
        this.f_135348_ = false;
        return $$0;
    }

    @Nullable
    public List<DataItem<?>> m_135384_() {
        ArrayList $$0 = null;
        this.f_135346_.readLock().lock();
        for (DataItem $$1 : this.f_135345_.values()) {
            if ($$0 == null) {
                $$0 = Lists.newArrayList();
            }
            $$0.add($$1.m_135407_());
        }
        this.f_135346_.readLock().unlock();
        return $$0;
    }

    private static <T> void m_135367_(FriendlyByteBuf p_135368_, DataItem<T> p_135369_) {
        EntityDataAccessor<T> $$2 = p_135369_.m_135396_();
        int $$3 = EntityDataSerializers.m_135052_($$2.m_135016_());
        if ($$3 < 0) {
            throw new EncoderException("Unknown serializer type " + $$2.m_135016_());
        }
        p_135368_.writeByte($$2.m_135015_());
        p_135368_.m_130130_($$3);
        $$2.m_135016_().m_6856_(p_135368_, p_135369_.m_135403_());
    }

    @Nullable
    public static List<DataItem<?>> m_135361_(FriendlyByteBuf p_135362_) {
        short $$2;
        ArrayList $$1 = null;
        while (($$2 = p_135362_.readUnsignedByte()) != 255) {
            int $$3;
            EntityDataSerializer<?> $$4;
            if ($$1 == null) {
                $$1 = Lists.newArrayList();
            }
            if (($$4 = EntityDataSerializers.m_135048_($$3 = p_135362_.m_130242_())) == null) {
                throw new DecoderException("Unknown serializer type " + $$3);
            }
            $$1.add(SynchedEntityData.m_135363_(p_135362_, $$2, $$4));
        }
        return $$1;
    }

    private static <T> DataItem<T> m_135363_(FriendlyByteBuf p_135364_, int p_135365_, EntityDataSerializer<T> p_135366_) {
        return new DataItem<T>(p_135366_.m_135021_(p_135365_), p_135366_.m_6709_(p_135364_));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_135356_(List<DataItem<?>> p_135357_) {
        this.f_135346_.writeLock().lock();
        try {
            for (DataItem<?> $$1 : p_135357_) {
                DataItem $$2 = (DataItem)this.f_135345_.get($$1.m_135396_().m_135015_());
                if ($$2 == null) continue;
                this.m_135375_($$2, $$1);
                this.f_135344_.m_7350_($$1.m_135396_());
            }
        }
        finally {
            this.f_135346_.writeLock().unlock();
        }
        this.f_135348_ = true;
    }

    private <T> void m_135375_(DataItem<T> p_135376_, DataItem<?> p_135377_) {
        if (!Objects.equals(p_135377_.f_135390_.m_135016_(), p_135376_.f_135390_.m_135016_())) {
            throw new IllegalStateException(String.format(Locale.ROOT, "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", p_135376_.f_135390_.m_135015_(), this.f_135344_, p_135376_.f_135391_, p_135376_.f_135391_.getClass(), p_135377_.f_135391_, p_135377_.f_135391_.getClass()));
        }
        p_135376_.m_135397_(p_135377_.m_135403_());
    }

    public boolean m_135388_() {
        return this.f_135347_;
    }

    public void m_135389_() {
        this.f_135348_ = false;
        this.f_135346_.readLock().lock();
        for (DataItem $$0 : this.f_135345_.values()) {
            $$0.m_135401_(false);
        }
        this.f_135346_.readLock().unlock();
    }

    public static class DataItem<T> {
        final EntityDataAccessor<T> f_135390_;
        T f_135391_;
        private boolean f_135392_;

        public DataItem(EntityDataAccessor<T> p_135394_, T p_135395_) {
            this.f_135390_ = p_135394_;
            this.f_135391_ = p_135395_;
            this.f_135392_ = true;
        }

        public EntityDataAccessor<T> m_135396_() {
            return this.f_135390_;
        }

        public void m_135397_(T p_135398_) {
            this.f_135391_ = p_135398_;
        }

        public T m_135403_() {
            return this.f_135391_;
        }

        public boolean m_135406_() {
            return this.f_135392_;
        }

        public void m_135401_(boolean p_135402_) {
            this.f_135392_ = p_135402_;
        }

        public DataItem<T> m_135407_() {
            return new DataItem<T>(this.f_135390_, this.f_135390_.m_135016_().m_7020_(this.f_135391_));
        }
    }
}

