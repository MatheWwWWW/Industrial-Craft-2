/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.level.chunk.DataLayer;

public abstract class DataLayerStorageMap<M extends DataLayerStorageMap<M>> {
    private static final int f_164421_ = 2;
    private final long[] f_75519_ = new long[2];
    private final DataLayer[] f_75520_ = new DataLayer[2];
    private boolean f_75521_;
    protected final Long2ObjectOpenHashMap<DataLayer> f_75518_;

    protected DataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> p_75523_) {
        this.f_75518_ = p_75523_;
        this.m_75531_();
        this.f_75521_ = true;
    }

    public abstract M m_5972_();

    public void m_75524_(long p_75525_) {
        this.f_75518_.put(p_75525_, (Object)((DataLayer)this.f_75518_.get(p_75525_)).m_62569_());
        this.m_75531_();
    }

    public boolean m_75529_(long p_75530_) {
        return this.f_75518_.containsKey(p_75530_);
    }

    @Nullable
    public DataLayer m_75532_(long p_75533_) {
        DataLayer $$2;
        if (this.f_75521_) {
            for (int $$1 = 0; $$1 < 2; ++$$1) {
                if (p_75533_ != this.f_75519_[$$1]) continue;
                return this.f_75520_[$$1];
            }
        }
        if (($$2 = (DataLayer)this.f_75518_.get(p_75533_)) != null) {
            if (this.f_75521_) {
                for (int $$3 = 1; $$3 > 0; --$$3) {
                    this.f_75519_[$$3] = this.f_75519_[$$3 - 1];
                    this.f_75520_[$$3] = this.f_75520_[$$3 - 1];
                }
                this.f_75519_[0] = p_75533_;
                this.f_75520_[0] = $$2;
            }
            return $$2;
        }
        return null;
    }

    @Nullable
    public DataLayer m_75535_(long p_75536_) {
        return (DataLayer)this.f_75518_.remove(p_75536_);
    }

    public void m_75526_(long p_75527_, DataLayer p_75528_) {
        this.f_75518_.put(p_75527_, (Object)p_75528_);
    }

    public void m_75531_() {
        for (int $$0 = 0; $$0 < 2; ++$$0) {
            this.f_75519_[$$0] = Long.MAX_VALUE;
            this.f_75520_[$$0] = null;
        }
    }

    public void m_75534_() {
        this.f_75521_ = false;
    }
}

