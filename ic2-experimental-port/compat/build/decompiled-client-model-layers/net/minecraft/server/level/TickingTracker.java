/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 */
package net.minecraft.server.level;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.ArrayList;
import net.minecraft.server.level.ChunkTracker;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.SortedArraySet;
import net.minecraft.world.level.ChunkPos;

public class TickingTracker
extends ChunkTracker {
    private static final int f_184142_ = 4;
    protected final Long2ByteMap f_184141_ = new Long2ByteOpenHashMap();
    private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> f_184143_ = new Long2ObjectOpenHashMap();

    public TickingTracker() {
        super(34, 16, 256);
        this.f_184141_.defaultReturnValue((byte)33);
    }

    private SortedArraySet<Ticket<?>> m_184177_(long p_184178_) {
        return (SortedArraySet)this.f_184143_.computeIfAbsent(p_184178_, p_184180_ -> SortedArraySet.m_14246_(4));
    }

    private int m_184159_(SortedArraySet<Ticket<?>> p_184160_) {
        return p_184160_.isEmpty() ? 34 : p_184160_.m_14262_().m_9433_();
    }

    public void m_184151_(long p_184152_, Ticket<?> p_184153_) {
        SortedArraySet<Ticket<?>> $$2 = this.m_184177_(p_184152_);
        int $$3 = this.m_184159_($$2);
        $$2.add(p_184153_);
        if (p_184153_.m_9433_() < $$3) {
            this.m_140715_(p_184152_, p_184153_.m_9433_(), true);
        }
    }

    public void m_184165_(long p_184166_, Ticket<?> p_184167_) {
        SortedArraySet<Ticket<?>> $$2 = this.m_184177_(p_184166_);
        $$2.remove(p_184167_);
        if ($$2.isEmpty()) {
            this.f_184143_.remove(p_184166_);
        }
        this.m_140715_(p_184166_, this.m_184159_($$2), false);
    }

    public <T> void m_184154_(TicketType<T> p_184155_, ChunkPos p_184156_, int p_184157_, T p_184158_) {
        this.m_184151_(p_184156_.m_45588_(), new Ticket<T>(p_184155_, p_184157_, p_184158_));
    }

    public <T> void m_184168_(TicketType<T> p_184169_, ChunkPos p_184170_, int p_184171_, T p_184172_) {
        Ticket<T> $$4 = new Ticket<T>(p_184169_, p_184171_, p_184172_);
        this.m_184165_(p_184170_.m_45588_(), $$4);
    }

    public void m_184146_(int p_184147_) {
        ArrayList<Pair> $$1 = new ArrayList<Pair>();
        for (Long2ObjectMap.Entry $$2 : this.f_184143_.long2ObjectEntrySet()) {
            for (Ticket $$3 : (SortedArraySet)$$2.getValue()) {
                if ($$3.m_9428_() != TicketType.f_9444_) continue;
                $$1.add(Pair.of((Object)$$3, (Object)$$2.getLongKey()));
            }
        }
        for (Pair $$4 : $$1) {
            Long $$5 = (Long)$$4.getSecond();
            Ticket $$6 = (Ticket)$$4.getFirst();
            this.m_184165_($$5, $$6);
            ChunkPos $$7 = new ChunkPos($$5);
            TicketType $$8 = $$6.m_9428_();
            this.m_184154_($$8, $$7, p_184147_, $$7);
        }
    }

    @Override
    protected int m_7031_(long p_184164_) {
        SortedArraySet $$1 = (SortedArraySet)this.f_184143_.get(p_184164_);
        if ($$1 == null || $$1.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        return ((Ticket)$$1.m_14262_()).m_9433_();
    }

    public int m_184161_(ChunkPos p_184162_) {
        return this.m_6172_(p_184162_.m_45588_());
    }

    @Override
    protected int m_6172_(long p_184174_) {
        return this.f_184141_.get(p_184174_);
    }

    @Override
    protected void m_7351_(long p_184149_, int p_184150_) {
        if (p_184150_ > 33) {
            this.f_184141_.remove(p_184149_);
        } else {
            this.f_184141_.put(p_184149_, (byte)p_184150_);
        }
    }

    public void m_184145_() {
        this.m_75588_(Integer.MAX_VALUE);
    }

    public String m_184175_(long p_184176_) {
        SortedArraySet $$1 = (SortedArraySet)this.f_184143_.get(p_184176_);
        if ($$1 == null || $$1.isEmpty()) {
            return "no_ticket";
        }
        return ((Ticket)$$1.m_14262_()).toString();
    }
}

