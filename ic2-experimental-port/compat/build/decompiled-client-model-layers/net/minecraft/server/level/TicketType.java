/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.level;

import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;

public class TicketType<T> {
    private final String f_9450_;
    private final Comparator<T> f_9451_;
    private final long f_9452_;
    public static final TicketType<Unit> f_9442_ = TicketType.m_9462_("start", (p_9471_, p_9472_) -> 0);
    public static final TicketType<Unit> f_9443_ = TicketType.m_9462_("dragon", (p_9460_, p_9461_) -> 0);
    public static final TicketType<ChunkPos> f_9444_ = TicketType.m_9462_("player", Comparator.comparingLong(ChunkPos::m_45588_));
    public static final TicketType<ChunkPos> f_9445_ = TicketType.m_9462_("forced", Comparator.comparingLong(ChunkPos::m_45588_));
    public static final TicketType<ChunkPos> f_9446_ = TicketType.m_9462_("light", Comparator.comparingLong(ChunkPos::m_45588_));
    public static final TicketType<BlockPos> f_9447_ = TicketType.m_9465_("portal", Vec3i::compareTo, 300);
    public static final TicketType<Integer> f_9448_ = TicketType.m_9465_("post_teleport", Integer::compareTo, 5);
    public static final TicketType<ChunkPos> f_9449_ = TicketType.m_9465_("unknown", Comparator.comparingLong(ChunkPos::m_45588_), 1);

    public static <T> TicketType<T> m_9462_(String p_9463_, Comparator<T> p_9464_) {
        return new TicketType<T>(p_9463_, p_9464_, 0L);
    }

    public static <T> TicketType<T> m_9465_(String p_9466_, Comparator<T> p_9467_, int p_9468_) {
        return new TicketType<T>(p_9466_, p_9467_, p_9468_);
    }

    protected TicketType(String p_9455_, Comparator<T> p_9456_, long p_9457_) {
        this.f_9450_ = p_9455_;
        this.f_9451_ = p_9456_;
        this.f_9452_ = p_9457_;
    }

    public String toString() {
        return this.f_9450_;
    }

    public Comparator<T> m_9458_() {
        return this.f_9451_;
    }

    public long m_9469_() {
        return this.f_9452_;
    }
}

