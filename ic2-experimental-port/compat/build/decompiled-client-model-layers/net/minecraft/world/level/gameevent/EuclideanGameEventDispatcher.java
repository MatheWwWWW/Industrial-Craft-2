/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.world.level.gameevent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.Vec3;

public class EuclideanGameEventDispatcher
implements GameEventDispatcher {
    private final List<GameEventListener> f_157750_ = Lists.newArrayList();
    private final Set<GameEventListener> f_223682_ = Sets.newHashSet();
    private final List<GameEventListener> f_223683_ = Lists.newArrayList();
    private boolean f_223684_;
    private final ServerLevel f_157751_;

    public EuclideanGameEventDispatcher(ServerLevel p_223686_) {
        this.f_157751_ = p_223686_;
    }

    @Override
    public boolean m_142086_() {
        return this.f_157750_.isEmpty();
    }

    @Override
    public void m_142501_(GameEventListener p_157766_) {
        if (this.f_223684_) {
            this.f_223683_.add(p_157766_);
        } else {
            this.f_157750_.add(p_157766_);
        }
        DebugPackets.m_179507_(this.f_157751_, p_157766_);
    }

    @Override
    public void m_142500_(GameEventListener p_157768_) {
        if (this.f_223684_) {
            this.f_223682_.add(p_157768_);
        } else {
            this.f_157750_.remove(p_157768_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean m_213682_(GameEvent p_223692_, Vec3 p_223693_, GameEvent.Context p_223694_, BiConsumer<GameEventListener, Vec3> p_223695_) {
        this.f_223684_ = true;
        boolean $$4 = false;
        try {
            Iterator<GameEventListener> $$5 = this.f_157750_.iterator();
            while ($$5.hasNext()) {
                GameEventListener $$6 = $$5.next();
                if (this.f_223682_.remove($$6)) {
                    $$5.remove();
                    continue;
                }
                Optional<Vec3> $$7 = EuclideanGameEventDispatcher.m_223687_(this.f_157751_, p_223693_, $$6);
                if (!$$7.isPresent()) continue;
                p_223695_.accept($$6, $$7.get());
                $$4 = true;
            }
        }
        finally {
            this.f_223684_ = false;
        }
        if (!this.f_223683_.isEmpty()) {
            this.f_157750_.addAll(this.f_223683_);
            this.f_223683_.clear();
        }
        if (!this.f_223682_.isEmpty()) {
            this.f_157750_.removeAll(this.f_223682_);
            this.f_223682_.clear();
        }
        return $$4;
    }

    private static Optional<Vec3> m_223687_(ServerLevel p_223688_, Vec3 p_223689_, GameEventListener p_223690_) {
        int $$5;
        Optional<Vec3> $$3 = p_223690_.m_142460_().m_142502_(p_223688_);
        if ($$3.isEmpty()) {
            return Optional.empty();
        }
        double $$4 = $$3.get().m_82557_(p_223689_);
        if ($$4 > (double)($$5 = p_223690_.m_142078_() * p_223690_.m_142078_())) {
            return Optional.empty();
        }
        return $$3;
    }
}

