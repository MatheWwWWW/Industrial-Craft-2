/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package com.mojang.realmsclient.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.realmsclient.dto.RealmsServer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;

public class RealmsServerList {
    private final Minecraft f_238634_;
    private final Set<RealmsServer> f_238560_ = Sets.newHashSet();
    private List<RealmsServer> f_238698_ = Lists.newArrayList();

    public RealmsServerList(Minecraft p_239233_) {
        this.f_238634_ = p_239233_;
    }

    public List<RealmsServer> m_239868_(List<RealmsServer> p_239869_) {
        ArrayList<RealmsServer> $$1 = new ArrayList<RealmsServer>(p_239869_);
        $$1.sort(new RealmsServer.McoServerComparator(this.f_238634_.m_91094_().m_92546_()));
        boolean $$2 = $$1.removeAll(this.f_238560_);
        if (!$$2) {
            this.f_238560_.clear();
        }
        this.f_238698_ = $$1;
        return List.copyOf(this.f_238698_);
    }

    public synchronized List<RealmsServer> m_240076_(RealmsServer p_240077_) {
        this.f_238698_.remove(p_240077_);
        this.f_238560_.add(p_240077_);
        return List.copyOf(this.f_238698_);
    }
}

