/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.gui.RealmsNewsManager;
import com.mojang.realmsclient.gui.task.DataFetcher;
import com.mojang.realmsclient.gui.task.RepeatedDelayStrategy;
import com.mojang.realmsclient.util.RealmsPersistence;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.Util;

public class RealmsDataFetcher {
    public final DataFetcher f_238549_ = new DataFetcher(Util.m_183992_(), TimeUnit.MILLISECONDS, Util.f_137440_);
    public final DataFetcher.Task<List<RealmsServer>> f_87797_;
    public final DataFetcher.Task<RealmsServerPlayerLists> f_87800_;
    public final DataFetcher.Task<Integer> f_238709_;
    public final DataFetcher.Task<Boolean> f_87799_;
    public final DataFetcher.Task<RealmsNews> f_238681_;
    public final RealmsNewsManager f_238737_ = new RealmsNewsManager(new RealmsPersistence());

    public RealmsDataFetcher(RealmsClient p_238853_) {
        this.f_87797_ = this.f_238549_.m_239622_("server list", () -> p_238854_.m_87235_().f_87573_, Duration.ofSeconds(60L), RepeatedDelayStrategy.f_238691_);
        this.f_87800_ = this.f_238549_.m_239622_("live stats", p_238853_::m_87241_, Duration.ofSeconds(10L), RepeatedDelayStrategy.f_238691_);
        this.f_238709_ = this.f_238549_.m_239622_("pending invite count", p_238853_::m_87260_, Duration.ofSeconds(10L), RepeatedDelayStrategy.m_239255_(360));
        this.f_87799_ = this.f_238549_.m_239622_("trial availablity", p_238853_::m_87264_, Duration.ofSeconds(60L), RepeatedDelayStrategy.m_239255_(60));
        this.f_238681_ = this.f_238549_.m_239622_("unread news", p_238853_::m_87263_, Duration.ofMinutes(5L), RepeatedDelayStrategy.f_238691_);
    }
}

