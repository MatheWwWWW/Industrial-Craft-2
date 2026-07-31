/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.gametest.framework;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestListener;
import net.minecraft.gametest.framework.GameTestSequence;
import net.minecraft.gametest.framework.GameTestTimeoutException;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

public class GameTestInfo {
    private final TestFunction f_127598_;
    @Nullable
    private BlockPos f_127599_;
    private final ServerLevel f_127600_;
    private final Collection<GameTestListener> f_127601_ = Lists.newArrayList();
    private final int f_127602_;
    private final Collection<GameTestSequence> f_127603_ = Lists.newCopyOnWriteArrayList();
    private final Object2LongMap<Runnable> f_127604_ = new Object2LongOpenHashMap();
    private long f_127605_;
    private long f_127606_;
    private boolean f_127607_;
    private final Stopwatch f_127608_ = Stopwatch.createUnstarted();
    private boolean f_127609_;
    private final Rotation f_127610_;
    @Nullable
    private Throwable f_127611_;
    @Nullable
    private StructureBlockEntity f_177469_;

    public GameTestInfo(TestFunction p_127613_, Rotation p_127614_, ServerLevel p_127615_) {
        this.f_127598_ = p_127613_;
        this.f_127600_ = p_127615_;
        this.f_127602_ = p_127613_.m_128079_();
        this.f_127610_ = p_127613_.m_128083_().m_55952_(p_127614_);
    }

    void m_127617_(BlockPos p_127618_) {
        this.f_127599_ = p_127618_;
    }

    void m_127616_() {
        this.f_127605_ = this.f_127600_.m_46467_() + 1L + this.f_127598_.m_128082_();
        this.f_127608_.start();
    }

    public void m_127628_() {
        if (this.m_127641_()) {
            return;
        }
        this.m_177470_();
        if (this.m_127641_()) {
            if (this.f_127611_ != null) {
                this.f_127601_.forEach(p_177482_ -> p_177482_.m_8066_(this));
            } else {
                this.f_127601_.forEach(p_177480_ -> p_177480_.m_142378_(this));
            }
        }
    }

    private void m_177470_() {
        this.f_127606_ = this.f_127600_.m_46467_() - this.f_127605_;
        if (this.f_127606_ < 0L) {
            return;
        }
        if (this.f_127606_ == 0L) {
            this.m_127649_();
        }
        ObjectIterator $$0 = this.f_127604_.object2LongEntrySet().iterator();
        while ($$0.hasNext()) {
            Object2LongMap.Entry $$1 = (Object2LongMap.Entry)$$0.next();
            if ($$1.getLongValue() > this.f_127606_) continue;
            try {
                ((Runnable)$$1.getKey()).run();
            }
            catch (Exception $$2) {
                this.m_127622_($$2);
            }
            $$0.remove();
        }
        if (this.f_127606_ > (long)this.f_127602_) {
            if (this.f_127603_.isEmpty()) {
                this.m_127622_(new GameTestTimeoutException("Didn't succeed or fail within " + this.f_127598_.m_128079_() + " ticks"));
            } else {
                this.f_127603_.forEach(p_177478_ -> p_177478_.m_127779_(this.f_127606_));
                if (this.f_127611_ == null) {
                    this.m_127622_(new GameTestTimeoutException("No sequences finished"));
                }
            }
        } else {
            this.f_127603_.forEach(p_177476_ -> p_177476_.m_127777_(this.f_127606_));
        }
    }

    private void m_127649_() {
        if (this.f_127607_) {
            throw new IllegalStateException("Test already started");
        }
        this.f_127607_ = true;
        try {
            this.f_127598_.m_128076_(new GameTestHelper(this));
        }
        catch (Exception $$0) {
            this.m_127622_($$0);
        }
    }

    public void m_177472_(long p_177473_, Runnable p_177474_) {
        this.f_127604_.put((Object)p_177474_, p_177473_);
    }

    public String m_127633_() {
        return this.f_127598_.m_128075_();
    }

    public BlockPos m_127636_() {
        return this.f_127599_;
    }

    @Nullable
    public Vec3i m_177483_() {
        StructureBlockEntity $$0 = this.m_177471_();
        if ($$0 == null) {
            return null;
        }
        return $$0.m_155805_();
    }

    @Nullable
    public AABB m_177484_() {
        StructureBlockEntity $$0 = this.m_177471_();
        if ($$0 == null) {
            return null;
        }
        return StructureUtils.m_127847_($$0);
    }

    @Nullable
    private StructureBlockEntity m_177471_() {
        return (StructureBlockEntity)this.f_127600_.m_7702_(this.f_127599_);
    }

    public ServerLevel m_127637_() {
        return this.f_127600_;
    }

    public boolean m_127638_() {
        return this.f_127609_ && this.f_127611_ == null;
    }

    public boolean m_127639_() {
        return this.f_127611_ != null;
    }

    public boolean m_127640_() {
        return this.f_127607_;
    }

    public boolean m_127641_() {
        return this.f_127609_;
    }

    public long m_177485_() {
        return this.f_127608_.elapsed(TimeUnit.MILLISECONDS);
    }

    private void m_127650_() {
        if (!this.f_127609_) {
            this.f_127609_ = true;
            this.f_127608_.stop();
        }
    }

    public void m_177486_() {
        if (this.f_127611_ == null) {
            this.m_127650_();
        }
    }

    public void m_127622_(Throwable p_127623_) {
        this.f_127611_ = p_127623_;
        this.m_127650_();
    }

    @Nullable
    public Throwable m_127642_() {
        return this.f_127611_;
    }

    public String toString() {
        return this.m_127633_();
    }

    public void m_127624_(GameTestListener p_127625_) {
        this.f_127601_.add(p_127625_);
    }

    public void m_127619_(BlockPos p_127620_, int p_127621_) {
        this.f_177469_ = StructureUtils.m_127883_(this.m_127645_(), p_127620_, this.m_127646_(), p_127621_, this.f_127600_, false);
        this.f_127599_ = this.f_177469_.m_58899_();
        this.f_177469_.m_59868_(this.m_127633_());
        StructureUtils.m_127875_(this.f_127599_, new BlockPos(1, 0, -1), this.m_127646_(), this.f_127600_);
        this.f_127601_.forEach(p_127630_ -> p_127630_.m_8073_(this));
    }

    public void m_177487_() {
        if (this.f_177469_ == null) {
            throw new IllegalStateException("Expected structure to be initialized, but it was null");
        }
        BoundingBox $$0 = StructureUtils.m_127904_(this.f_177469_);
        StructureUtils.m_127849_($$0, this.f_127599_.m_123342_(), this.f_127600_);
    }

    long m_177488_() {
        return this.f_127606_;
    }

    GameTestSequence m_177489_() {
        GameTestSequence $$0 = new GameTestSequence(this);
        this.f_127603_.add($$0);
        return $$0;
    }

    public boolean m_127643_() {
        return this.f_127598_.m_128080_();
    }

    public boolean m_127644_() {
        return !this.f_127598_.m_128080_();
    }

    public String m_127645_() {
        return this.f_127598_.m_128078_();
    }

    public Rotation m_127646_() {
        return this.f_127610_;
    }

    public TestFunction m_127648_() {
        return this.f_127598_;
    }

    public int m_177490_() {
        return this.f_127602_;
    }

    public boolean m_177491_() {
        return this.f_127598_.m_177828_();
    }

    public int m_177492_() {
        return this.f_127598_.m_177829_();
    }

    public int m_177493_() {
        return this.f_127598_.m_177830_();
    }
}

