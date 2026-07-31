/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public abstract class Behavior<E extends LivingEntity> {
    public static final int f_147431_ = 60;
    protected final Map<MemoryModuleType<?>, MemoryStatus> f_22522_;
    private Status f_22523_ = Status.STOPPED;
    private long f_22524_;
    private final int f_22525_;
    private final int f_22526_;

    public Behavior(Map<MemoryModuleType<?>, MemoryStatus> p_22528_) {
        this(p_22528_, 60);
    }

    public Behavior(Map<MemoryModuleType<?>, MemoryStatus> p_22530_, int p_22531_) {
        this(p_22530_, p_22531_, p_22531_);
    }

    public Behavior(Map<MemoryModuleType<?>, MemoryStatus> p_22533_, int p_22534_, int p_22535_) {
        this.f_22525_ = p_22534_;
        this.f_22526_ = p_22535_;
        this.f_22522_ = p_22533_;
    }

    public Status m_22536_() {
        return this.f_22523_;
    }

    public final boolean m_22554_(ServerLevel p_22555_, E p_22556_, long p_22557_) {
        if (this.m_22543_(p_22556_) && this.m_6114_(p_22555_, p_22556_)) {
            this.f_22523_ = Status.RUNNING;
            int $$3 = this.f_22525_ + p_22555_.m_213780_().m_188503_(this.f_22526_ + 1 - this.f_22525_);
            this.f_22524_ = p_22557_ + (long)$$3;
            this.m_6735_(p_22555_, p_22556_, p_22557_);
            return true;
        }
        return false;
    }

    protected void m_6735_(ServerLevel p_22540_, E p_22541_, long p_22542_) {
    }

    public final void m_22558_(ServerLevel p_22559_, E p_22560_, long p_22561_) {
        if (!this.m_7773_(p_22561_) && this.m_6737_(p_22559_, p_22560_, p_22561_)) {
            this.m_6725_(p_22559_, p_22560_, p_22561_);
        } else {
            this.m_22562_(p_22559_, p_22560_, p_22561_);
        }
    }

    protected void m_6725_(ServerLevel p_22551_, E p_22552_, long p_22553_) {
    }

    public final void m_22562_(ServerLevel p_22563_, E p_22564_, long p_22565_) {
        this.f_22523_ = Status.STOPPED;
        this.m_6732_(p_22563_, p_22564_, p_22565_);
    }

    protected void m_6732_(ServerLevel p_22548_, E p_22549_, long p_22550_) {
    }

    protected boolean m_6737_(ServerLevel p_22545_, E p_22546_, long p_22547_) {
        return false;
    }

    protected boolean m_7773_(long p_22537_) {
        return p_22537_ > this.f_22524_;
    }

    protected boolean m_6114_(ServerLevel p_22538_, E p_22539_) {
        return true;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private boolean m_22543_(E p_22544_) {
        for (Map.Entry<MemoryModuleType<?>, MemoryStatus> $$1 : this.f_22522_.entrySet()) {
            MemoryModuleType<?> $$2 = $$1.getKey();
            MemoryStatus $$3 = $$1.getValue();
            if (((LivingEntity)p_22544_).m_6274_().m_21876_($$2, $$3)) continue;
            return false;
        }
        return true;
    }

    public static final class Status
    extends Enum<Status> {
        public static final /* enum */ Status STOPPED = new Status();
        public static final /* enum */ Status RUNNING = new Status();
        private static final /* synthetic */ Status[] $VALUES;

        public static Status[] values() {
            return (Status[])$VALUES.clone();
        }

        public static Status valueOf(String p_22575_) {
            return Enum.valueOf(Status.class, p_22575_);
        }

        private static /* synthetic */ Status[] m_147432_() {
            return new Status[]{STOPPED, RUNNING};
        }

        static {
            $VALUES = Status.m_147432_();
        }
    }
}

