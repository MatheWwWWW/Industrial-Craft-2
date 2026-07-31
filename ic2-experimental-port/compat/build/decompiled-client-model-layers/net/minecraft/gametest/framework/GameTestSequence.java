/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestEvent;
import net.minecraft.gametest.framework.GameTestInfo;

public class GameTestSequence {
    final GameTestInfo f_127774_;
    private final List<GameTestEvent> f_127775_ = Lists.newArrayList();
    private long f_127776_;

    GameTestSequence(GameTestInfo p_177542_) {
        this.f_127774_ = p_177542_;
        this.f_127776_ = p_177542_.m_177488_();
    }

    public GameTestSequence m_177552_(Runnable p_177553_) {
        this.f_127775_.add(GameTestEvent.m_177097_(p_177553_));
        return this;
    }

    public GameTestSequence m_177549_(long p_177550_, Runnable p_177551_) {
        this.f_127775_.add(GameTestEvent.m_177094_(p_177550_, p_177551_));
        return this;
    }

    public GameTestSequence m_177544_(int p_177545_) {
        return this.m_177546_(p_177545_, () -> {});
    }

    public GameTestSequence m_177562_(Runnable p_177563_) {
        this.f_127775_.add(GameTestEvent.m_177097_(() -> this.m_177570_(p_177563_)));
        return this;
    }

    public GameTestSequence m_177546_(int p_177547_, Runnable p_177548_) {
        this.f_127775_.add(GameTestEvent.m_177097_(() -> {
            if (this.f_127774_.m_177488_() < this.f_127776_ + (long)p_177547_) {
                throw new GameTestAssertException("Waiting");
            }
            this.m_177570_(p_177548_);
        }));
        return this;
    }

    public GameTestSequence m_177559_(int p_177560_, Runnable p_177561_) {
        this.f_127775_.add(GameTestEvent.m_177097_(() -> {
            if (this.f_127774_.m_177488_() < this.f_127776_ + (long)p_177560_) {
                this.m_177570_(p_177561_);
                throw new GameTestAssertException("Waiting");
            }
        }));
        return this;
    }

    public void m_177543_() {
        this.f_127775_.add(GameTestEvent.m_177097_(this.f_127774_::m_177486_));
    }

    public void m_177554_(Supplier<Exception> p_177555_) {
        this.f_127775_.add(GameTestEvent.m_177097_(() -> this.f_127774_.m_127622_((Throwable)p_177555_.get())));
    }

    public Condition m_177558_() {
        Condition $$0 = new Condition();
        this.f_127775_.add(GameTestEvent.m_177097_(() -> $$0.m_177583_(this.f_127774_.m_177488_())));
        return $$0;
    }

    public void m_127777_(long p_127778_) {
        try {
            this.m_127781_(p_127778_);
        }
        catch (GameTestAssertException gameTestAssertException) {
            // empty catch block
        }
    }

    public void m_127779_(long p_127780_) {
        try {
            this.m_127781_(p_127780_);
        }
        catch (GameTestAssertException $$1) {
            this.f_127774_.m_127622_($$1);
        }
    }

    private void m_177570_(Runnable p_177571_) {
        try {
            p_177571_.run();
        }
        catch (GameTestAssertException $$1) {
            this.f_127774_.m_127622_($$1);
        }
    }

    private void m_127781_(long p_127782_) {
        Iterator<GameTestEvent> $$1 = this.f_127775_.iterator();
        while ($$1.hasNext()) {
            GameTestEvent $$2 = $$1.next();
            $$2.f_127594_.run();
            $$1.remove();
            long $$3 = p_127782_ - this.f_127776_;
            long $$4 = this.f_127776_;
            this.f_127776_ = p_127782_;
            if ($$2.f_127593_ == null || $$2.f_127593_ == $$3) continue;
            this.f_127774_.m_127622_(new GameTestAssertException("Succeeded in invalid tick: expected " + ($$4 + $$2.f_127593_) + ", but current tick is " + p_127782_));
            break;
        }
    }

    public class Condition {
        private static final long f_177578_ = -1L;
        private long f_177579_ = -1L;

        void m_177583_(long p_177584_) {
            if (this.f_177579_ != -1L) {
                throw new IllegalStateException("Condition already triggered at " + this.f_177579_);
            }
            this.f_177579_ = p_177584_;
        }

        public void m_177582_() {
            long $$0 = GameTestSequence.this.f_127774_.m_177488_();
            if (this.f_177579_ != $$0) {
                if (this.f_177579_ == -1L) {
                    throw new GameTestAssertException("Condition not triggered (t=" + $$0 + ")");
                }
                throw new GameTestAssertException("Condition triggered at " + this.f_177579_ + ", (t=" + $$0 + ")");
            }
        }
    }
}

