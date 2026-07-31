/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.piston;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class PistonMath {
    public static AABB m_60328_(AABB p_60329_, Direction p_60330_, double p_60331_) {
        double $$3 = p_60331_ * (double)p_60330_.m_122421_().m_122540_();
        double $$4 = Math.min($$3, 0.0);
        double $$5 = Math.max($$3, 0.0);
        switch (p_60330_) {
            case WEST: {
                return new AABB(p_60329_.f_82288_ + $$4, p_60329_.f_82289_, p_60329_.f_82290_, p_60329_.f_82288_ + $$5, p_60329_.f_82292_, p_60329_.f_82293_);
            }
            case EAST: {
                return new AABB(p_60329_.f_82291_ + $$4, p_60329_.f_82289_, p_60329_.f_82290_, p_60329_.f_82291_ + $$5, p_60329_.f_82292_, p_60329_.f_82293_);
            }
            case DOWN: {
                return new AABB(p_60329_.f_82288_, p_60329_.f_82289_ + $$4, p_60329_.f_82290_, p_60329_.f_82291_, p_60329_.f_82289_ + $$5, p_60329_.f_82293_);
            }
            default: {
                return new AABB(p_60329_.f_82288_, p_60329_.f_82292_ + $$4, p_60329_.f_82290_, p_60329_.f_82291_, p_60329_.f_82292_ + $$5, p_60329_.f_82293_);
            }
            case NORTH: {
                return new AABB(p_60329_.f_82288_, p_60329_.f_82289_, p_60329_.f_82290_ + $$4, p_60329_.f_82291_, p_60329_.f_82292_, p_60329_.f_82290_ + $$5);
            }
            case SOUTH: 
        }
        return new AABB(p_60329_.f_82288_, p_60329_.f_82289_, p_60329_.f_82293_ + $$4, p_60329_.f_82291_, p_60329_.f_82292_, p_60329_.f_82293_ + $$5);
    }
}

