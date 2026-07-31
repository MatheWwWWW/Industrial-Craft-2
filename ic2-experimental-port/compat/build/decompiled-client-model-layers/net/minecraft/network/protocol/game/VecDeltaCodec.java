/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class VecDeltaCodec {
    private static final double f_238014_ = 4096.0;
    private Vec3 f_238015_ = Vec3.f_82478_;

    private static long m_238017_(double p_238018_) {
        return Mth.m_14134_(p_238018_ * 4096.0);
    }

    private static double m_238019_(long p_238020_) {
        return (double)p_238020_ / 4096.0;
    }

    public Vec3 m_238021_(long p_238022_, long p_238023_, long p_238024_) {
        if (p_238022_ == 0L && p_238023_ == 0L && p_238024_ == 0L) {
            return this.f_238015_;
        }
        double $$3 = p_238022_ == 0L ? this.f_238015_.f_82479_ : VecDeltaCodec.m_238019_(VecDeltaCodec.m_238017_(this.f_238015_.f_82479_) + p_238022_);
        double $$4 = p_238023_ == 0L ? this.f_238015_.f_82480_ : VecDeltaCodec.m_238019_(VecDeltaCodec.m_238017_(this.f_238015_.f_82480_) + p_238023_);
        double $$5 = p_238024_ == 0L ? this.f_238015_.f_82481_ : VecDeltaCodec.m_238019_(VecDeltaCodec.m_238017_(this.f_238015_.f_82481_) + p_238024_);
        return new Vec3($$3, $$4, $$5);
    }

    public long m_238025_(Vec3 p_238026_) {
        return VecDeltaCodec.m_238017_(p_238026_.f_82479_ - this.f_238015_.f_82479_);
    }

    public long m_238027_(Vec3 p_238028_) {
        return VecDeltaCodec.m_238017_(p_238028_.f_82480_ - this.f_238015_.f_82480_);
    }

    public long m_238029_(Vec3 p_238030_) {
        return VecDeltaCodec.m_238017_(p_238030_.f_82481_ - this.f_238015_.f_82481_);
    }

    public Vec3 m_238031_(Vec3 p_238032_) {
        return p_238032_.m_82546_(this.f_238015_);
    }

    public void m_238033_(Vec3 p_238034_) {
        this.f_238015_ = p_238034_;
    }
}

