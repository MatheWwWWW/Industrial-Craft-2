/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue
 */
package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.client.renderer.chunk.VisibilitySet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class VisGraph {
    private static final int f_173723_ = 4;
    private static final int f_173724_ = 16;
    private static final int f_173725_ = 15;
    private static final int f_173726_ = 4096;
    private static final int f_173727_ = 0;
    private static final int f_173728_ = 4;
    private static final int f_173729_ = 8;
    private static final int f_112949_ = (int)Math.pow(16.0, 0.0);
    private static final int f_112950_ = (int)Math.pow(16.0, 1.0);
    private static final int f_112951_ = (int)Math.pow(16.0, 2.0);
    private static final int f_173730_ = -1;
    private static final Direction[] f_112952_ = Direction.values();
    private final BitSet f_112953_ = new BitSet(4096);
    private static final int[] f_112954_ = Util.m_137469_(new int[1352], p_112974_ -> {
        boolean $$1 = false;
        int $$2 = 15;
        int $$3 = 0;
        for (int $$4 = 0; $$4 < 16; ++$$4) {
            for (int $$5 = 0; $$5 < 16; ++$$5) {
                for (int $$6 = 0; $$6 < 16; ++$$6) {
                    if ($$4 != 0 && $$4 != 15 && $$5 != 0 && $$5 != 15 && $$6 != 0 && $$6 != 15) continue;
                    p_112974_[$$3++] = VisGraph.m_112961_($$4, $$5, $$6);
                }
            }
        }
    });
    private int f_112955_ = 4096;

    public void m_112971_(BlockPos p_112972_) {
        this.f_112953_.set(VisGraph.m_112975_(p_112972_), true);
        --this.f_112955_;
    }

    private static int m_112975_(BlockPos p_112976_) {
        return VisGraph.m_112961_(p_112976_.m_123341_() & 0xF, p_112976_.m_123342_() & 0xF, p_112976_.m_123343_() & 0xF);
    }

    private static int m_112961_(int p_112962_, int p_112963_, int p_112964_) {
        return p_112962_ << 0 | p_112963_ << 8 | p_112964_ << 4;
    }

    public VisibilitySet m_112958_() {
        VisibilitySet $$0 = new VisibilitySet();
        if (4096 - this.f_112955_ < 256) {
            $$0.m_112992_(true);
        } else if (this.f_112955_ == 0) {
            $$0.m_112992_(false);
        } else {
            for (int $$1 : f_112954_) {
                if (this.f_112953_.get($$1)) continue;
                $$0.m_112990_(this.m_112959_($$1));
            }
        }
        return $$0;
    }

    private Set<Direction> m_112959_(int p_112960_) {
        EnumSet<Direction> $$1 = EnumSet.noneOf(Direction.class);
        IntArrayFIFOQueue $$2 = new IntArrayFIFOQueue();
        $$2.enqueue(p_112960_);
        this.f_112953_.set(p_112960_, true);
        while (!$$2.isEmpty()) {
            int $$3 = $$2.dequeueInt();
            this.m_112968_($$3, $$1);
            for (Direction $$4 : f_112952_) {
                int $$5 = this.m_112965_($$3, $$4);
                if ($$5 < 0 || this.f_112953_.get($$5)) continue;
                this.f_112953_.set($$5, true);
                $$2.enqueue($$5);
            }
        }
        return $$1;
    }

    private void m_112968_(int p_112969_, Set<Direction> p_112970_) {
        int $$2 = p_112969_ >> 0 & 0xF;
        if ($$2 == 0) {
            p_112970_.add(Direction.WEST);
        } else if ($$2 == 15) {
            p_112970_.add(Direction.EAST);
        }
        int $$3 = p_112969_ >> 8 & 0xF;
        if ($$3 == 0) {
            p_112970_.add(Direction.DOWN);
        } else if ($$3 == 15) {
            p_112970_.add(Direction.UP);
        }
        int $$4 = p_112969_ >> 4 & 0xF;
        if ($$4 == 0) {
            p_112970_.add(Direction.NORTH);
        } else if ($$4 == 15) {
            p_112970_.add(Direction.SOUTH);
        }
    }

    private int m_112965_(int p_112966_, Direction p_112967_) {
        switch (p_112967_) {
            case DOWN: {
                if ((p_112966_ >> 8 & 0xF) == 0) {
                    return -1;
                }
                return p_112966_ - f_112951_;
            }
            case UP: {
                if ((p_112966_ >> 8 & 0xF) == 15) {
                    return -1;
                }
                return p_112966_ + f_112951_;
            }
            case NORTH: {
                if ((p_112966_ >> 4 & 0xF) == 0) {
                    return -1;
                }
                return p_112966_ - f_112950_;
            }
            case SOUTH: {
                if ((p_112966_ >> 4 & 0xF) == 15) {
                    return -1;
                }
                return p_112966_ + f_112950_;
            }
            case WEST: {
                if ((p_112966_ >> 0 & 0xF) == 0) {
                    return -1;
                }
                return p_112966_ - f_112949_;
            }
            case EAST: {
                if ((p_112966_ >> 0 & 0xF) == 15) {
                    return -1;
                }
                return p_112966_ + f_112949_;
            }
        }
        return -1;
    }
}

