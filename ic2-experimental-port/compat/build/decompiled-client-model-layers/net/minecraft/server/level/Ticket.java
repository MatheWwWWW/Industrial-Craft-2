/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.level;

import java.util.Objects;
import net.minecraft.server.level.TicketType;

public final class Ticket<T>
implements Comparable<Ticket<?>> {
    private final TicketType<T> f_9420_;
    private final int f_9421_;
    private final T f_9422_;
    private long f_9423_;

    protected Ticket(TicketType<T> p_9425_, int p_9426_, T p_9427_) {
        this.f_9420_ = p_9425_;
        this.f_9421_ = p_9426_;
        this.f_9422_ = p_9427_;
    }

    @Override
    public int compareTo(Ticket<?> p_9432_) {
        int $$1 = Integer.compare(this.f_9421_, p_9432_.f_9421_);
        if ($$1 != 0) {
            return $$1;
        }
        int $$2 = Integer.compare(System.identityHashCode(this.f_9420_), System.identityHashCode(p_9432_.f_9420_));
        if ($$2 != 0) {
            return $$2;
        }
        return this.f_9420_.m_9458_().compare(this.f_9422_, p_9432_.f_9422_);
    }

    public boolean equals(Object p_9439_) {
        if (this == p_9439_) {
            return true;
        }
        if (!(p_9439_ instanceof Ticket)) {
            return false;
        }
        Ticket $$1 = (Ticket)p_9439_;
        return this.f_9421_ == $$1.f_9421_ && Objects.equals(this.f_9420_, $$1.f_9420_) && Objects.equals(this.f_9422_, $$1.f_9422_);
    }

    public int hashCode() {
        return Objects.hash(this.f_9420_, this.f_9421_, this.f_9422_);
    }

    public String toString() {
        return "Ticket[" + this.f_9420_ + " " + this.f_9421_ + " (" + this.f_9422_ + ")] at " + this.f_9423_;
    }

    public TicketType<T> m_9428_() {
        return this.f_9420_;
    }

    public int m_9433_() {
        return this.f_9421_;
    }

    protected void m_9429_(long p_9430_) {
        this.f_9423_ = p_9430_;
    }

    protected boolean m_9434_(long p_9435_) {
        long $$1 = this.f_9420_.m_9469_();
        return $$1 != 0L && p_9435_ - this.f_9423_ > $$1;
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((Ticket)object);
    }
}

