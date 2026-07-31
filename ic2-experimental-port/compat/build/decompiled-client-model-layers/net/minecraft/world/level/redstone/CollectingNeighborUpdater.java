/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.redstone;

import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.NeighborUpdater;
import org.slf4j.Logger;

public class CollectingNeighborUpdater
implements NeighborUpdater {
    private static final Logger f_230635_ = LogUtils.getLogger();
    private final Level f_230636_;
    private final int f_230637_;
    private final ArrayDeque<NeighborUpdates> f_230638_ = new ArrayDeque();
    private final List<NeighborUpdates> f_230639_ = new ArrayList<NeighborUpdates>();
    private int f_230640_ = 0;

    public CollectingNeighborUpdater(Level p_230643_, int p_230644_) {
        this.f_230636_ = p_230643_;
        this.f_230637_ = p_230644_;
    }

    @Override
    public void m_213547_(Direction p_230664_, BlockState p_230665_, BlockPos p_230666_, BlockPos p_230667_, int p_230668_, int p_230669_) {
        this.m_230660_(p_230666_, new ShapeUpdate(p_230664_, p_230665_, p_230666_.m_7949_(), p_230667_.m_7949_(), p_230668_));
    }

    @Override
    public void m_214026_(BlockPos p_230653_, Block p_230654_, BlockPos p_230655_) {
        this.m_230660_(p_230653_, new SimpleNeighborUpdate(p_230653_, p_230654_, p_230655_.m_7949_()));
    }

    @Override
    public void m_213858_(BlockState p_230647_, BlockPos p_230648_, Block p_230649_, BlockPos p_230650_, boolean p_230651_) {
        this.m_230660_(p_230648_, new FullNeighborUpdate(p_230647_, p_230648_.m_7949_(), p_230649_, p_230650_.m_7949_(), p_230651_));
    }

    @Override
    public void m_214152_(BlockPos p_230657_, Block p_230658_, @Nullable Direction p_230659_) {
        this.m_230660_(p_230657_, new MultiNeighborUpdate(p_230657_.m_7949_(), p_230658_, p_230659_));
    }

    private void m_230660_(BlockPos p_230661_, NeighborUpdates p_230662_) {
        boolean $$2 = this.f_230640_ > 0;
        boolean $$3 = this.f_230637_ >= 0 && this.f_230640_ >= this.f_230637_;
        ++this.f_230640_;
        if (!$$3) {
            if ($$2) {
                this.f_230639_.add(p_230662_);
            } else {
                this.f_230638_.push(p_230662_);
            }
        } else if (this.f_230640_ - 1 == this.f_230637_) {
            f_230635_.error("Too many chained neighbor updates. Skipping the rest. First skipped position: " + p_230661_.m_123344_());
        }
        if (!$$2) {
            this.m_230645_();
        }
    }

    private void m_230645_() {
        try {
            block3: while (!this.f_230638_.isEmpty() || !this.f_230639_.isEmpty()) {
                for (int $$0 = this.f_230639_.size() - 1; $$0 >= 0; --$$0) {
                    this.f_230638_.push(this.f_230639_.get($$0));
                }
                this.f_230639_.clear();
                NeighborUpdates $$1 = this.f_230638_.peek();
                while (this.f_230639_.isEmpty()) {
                    if ($$1.m_213563_(this.f_230636_)) continue;
                    this.f_230638_.pop();
                    continue block3;
                }
            }
        }
        finally {
            this.f_230638_.clear();
            this.f_230639_.clear();
            this.f_230640_ = 0;
        }
    }

    record ShapeUpdate(Direction f_230703_, BlockState f_230704_, BlockPos f_230705_, BlockPos f_230706_, int f_230707_) implements NeighborUpdates
    {
        @Override
        public boolean m_213563_(Level p_230716_) {
            NeighborUpdater.m_230770_(p_230716_, this.f_230703_, this.f_230704_, this.f_230705_, this.f_230706_, this.f_230707_, 512);
            return false;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ShapeUpdate.class, "direction;state;pos;neighborPos;updateFlags", "f_230703_", "f_230704_", "f_230705_", "f_230706_", "f_230707_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ShapeUpdate.class, "direction;state;pos;neighborPos;updateFlags", "f_230703_", "f_230704_", "f_230705_", "f_230706_", "f_230707_"}, this);
        }

        @Override
        public final boolean equals(Object p_230722_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ShapeUpdate.class, "direction;state;pos;neighborPos;updateFlags", "f_230703_", "f_230704_", "f_230705_", "f_230706_", "f_230707_"}, this, p_230722_);
        }
    }

    static interface NeighborUpdates {
        public boolean m_213563_(Level var1);
    }

    record SimpleNeighborUpdate(BlockPos f_230725_, Block f_230726_, BlockPos f_230727_) implements NeighborUpdates
    {
        @Override
        public boolean m_213563_(Level p_230734_) {
            BlockState $$1 = p_230734_.m_8055_(this.f_230725_);
            NeighborUpdater.m_230763_(p_230734_, $$1, this.f_230725_, this.f_230726_, this.f_230727_, false);
            return false;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SimpleNeighborUpdate.class, "pos;block;neighborPos", "f_230725_", "f_230726_", "f_230727_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SimpleNeighborUpdate.class, "pos;block;neighborPos", "f_230725_", "f_230726_", "f_230727_"}, this);
        }

        @Override
        public final boolean equals(Object p_230738_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SimpleNeighborUpdate.class, "pos;block;neighborPos", "f_230725_", "f_230726_", "f_230727_"}, this, p_230738_);
        }
    }

    record FullNeighborUpdate(BlockState f_230670_, BlockPos f_230671_, Block f_230672_, BlockPos f_230673_, boolean f_230674_) implements NeighborUpdates
    {
        @Override
        public boolean m_213563_(Level p_230683_) {
            NeighborUpdater.m_230763_(p_230683_, this.f_230670_, this.f_230671_, this.f_230672_, this.f_230673_, this.f_230674_);
            return false;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{FullNeighborUpdate.class, "state;pos;block;neighborPos;movedByPiston", "f_230670_", "f_230671_", "f_230672_", "f_230673_", "f_230674_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FullNeighborUpdate.class, "state;pos;block;neighborPos;movedByPiston", "f_230670_", "f_230671_", "f_230672_", "f_230673_", "f_230674_"}, this);
        }

        @Override
        public final boolean equals(Object p_230689_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FullNeighborUpdate.class, "state;pos;block;neighborPos;movedByPiston", "f_230670_", "f_230671_", "f_230672_", "f_230673_", "f_230674_"}, this, p_230689_);
        }
    }

    static final class MultiNeighborUpdate
    implements NeighborUpdates {
        private final BlockPos f_230692_;
        private final Block f_230693_;
        @Nullable
        private final Direction f_230694_;
        private int f_230695_ = 0;

        MultiNeighborUpdate(BlockPos p_230697_, Block p_230698_, @Nullable Direction p_230699_) {
            this.f_230692_ = p_230697_;
            this.f_230693_ = p_230698_;
            this.f_230694_ = p_230699_;
            if (NeighborUpdater.f_230761_[this.f_230695_] == p_230699_) {
                ++this.f_230695_;
            }
        }

        @Override
        public boolean m_213563_(Level p_230701_) {
            BlockPos $$1 = this.f_230692_.m_121945_(NeighborUpdater.f_230761_[this.f_230695_++]);
            BlockState $$2 = p_230701_.m_8055_($$1);
            $$2.m_60690_(p_230701_, $$1, this.f_230693_, this.f_230692_, false);
            if (this.f_230695_ < NeighborUpdater.f_230761_.length && NeighborUpdater.f_230761_[this.f_230695_] == this.f_230694_) {
                ++this.f_230695_;
            }
            return this.f_230695_ < NeighborUpdater.f_230761_.length;
        }
    }
}

