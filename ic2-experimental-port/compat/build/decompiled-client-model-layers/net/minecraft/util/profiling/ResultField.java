/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling;

public final class ResultField
implements Comparable<ResultField> {
    public final double f_18607_;
    public final double f_18608_;
    public final long f_18609_;
    public final String f_18610_;

    public ResultField(String p_18612_, double p_18613_, double p_18614_, long p_18615_) {
        this.f_18610_ = p_18612_;
        this.f_18607_ = p_18613_;
        this.f_18608_ = p_18614_;
        this.f_18609_ = p_18615_;
    }

    @Override
    public int compareTo(ResultField p_18618_) {
        if (p_18618_.f_18607_ < this.f_18607_) {
            return -1;
        }
        if (p_18618_.f_18607_ > this.f_18607_) {
            return 1;
        }
        return p_18618_.f_18610_.compareTo(this.f_18610_);
    }

    public int m_18616_() {
        return (this.f_18610_.hashCode() & 0xAAAAAA) + 0x444444;
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((ResultField)object);
    }
}

