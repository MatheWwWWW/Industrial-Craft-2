/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.thread.ProcessorMailbox;
import org.slf4j.Logger;

public class ServerList {
    private static final Logger f_105425_ = LogUtils.getLogger();
    private static final ProcessorMailbox<Runnable> f_233836_ = ProcessorMailbox.m_18751_(Util.m_183991_(), "server-list-io");
    private static final int f_233837_ = 16;
    private final Minecraft f_105426_;
    private final List<ServerData> f_105427_ = Lists.newArrayList();
    private final List<ServerData> f_233838_ = Lists.newArrayList();

    public ServerList(Minecraft p_105430_) {
        this.f_105426_ = p_105430_;
        this.m_105431_();
    }

    public void m_105431_() {
        try {
            this.f_105427_.clear();
            this.f_233838_.clear();
            CompoundTag $$0 = NbtIo.m_128953_(new File(this.f_105426_.f_91069_, "servers.dat"));
            if ($$0 == null) {
                return;
            }
            ListTag $$1 = $$0.m_128437_("servers", 10);
            for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
                CompoundTag $$3 = $$1.m_128728_($$2);
                ServerData $$4 = ServerData.m_105385_($$3);
                if ($$3.m_128471_("hidden")) {
                    this.f_233838_.add($$4);
                    continue;
                }
                this.f_105427_.add($$4);
            }
        }
        catch (Exception $$5) {
            f_105425_.error("Couldn't load server list", (Throwable)$$5);
        }
    }

    public void m_105442_() {
        try {
            ListTag $$0 = new ListTag();
            for (ServerData $$1 : this.f_105427_) {
                CompoundTag $$2 = $$1.m_105378_();
                $$2.m_128379_("hidden", false);
                $$0.add($$2);
            }
            for (ServerData $$3 : this.f_233838_) {
                CompoundTag $$4 = $$3.m_105378_();
                $$4.m_128379_("hidden", true);
                $$0.add($$4);
            }
            CompoundTag $$5 = new CompoundTag();
            $$5.m_128365_("servers", $$0);
            File $$6 = File.createTempFile("servers", ".dat", this.f_105426_.f_91069_);
            NbtIo.m_128955_($$5, $$6);
            File $$7 = new File(this.f_105426_.f_91069_, "servers.dat_old");
            File $$8 = new File(this.f_105426_.f_91069_, "servers.dat");
            Util.m_137462_($$8, $$6, $$7);
        }
        catch (Exception $$9) {
            f_105425_.error("Couldn't save server list", (Throwable)$$9);
        }
    }

    public ServerData m_105432_(int p_105433_) {
        return this.f_105427_.get(p_105433_);
    }

    @Nullable
    public ServerData m_233845_(String p_233846_) {
        for (ServerData $$1 : this.f_105427_) {
            if (!$$1.f_105363_.equals(p_233846_)) continue;
            return $$1;
        }
        for (ServerData $$2 : this.f_233838_) {
            if (!$$2.f_105363_.equals(p_233846_)) continue;
            return $$2;
        }
        return null;
    }

    @Nullable
    public ServerData m_233847_(String p_233848_) {
        for (int $$1 = 0; $$1 < this.f_233838_.size(); ++$$1) {
            ServerData $$2 = this.f_233838_.get($$1);
            if (!$$2.f_105363_.equals(p_233848_)) continue;
            this.f_233838_.remove($$1);
            this.f_105427_.add($$2);
            return $$2;
        }
        return null;
    }

    public void m_105440_(ServerData p_105441_) {
        if (!this.f_105427_.remove(p_105441_)) {
            this.f_233838_.remove(p_105441_);
        }
    }

    public void m_233842_(ServerData p_233843_, boolean p_233844_) {
        if (p_233844_) {
            this.f_233838_.add(0, p_233843_);
            while (this.f_233838_.size() > 16) {
                this.f_233838_.remove(this.f_233838_.size() - 1);
            }
        } else {
            this.f_105427_.add(p_233843_);
        }
    }

    public int m_105445_() {
        return this.f_105427_.size();
    }

    public void m_105434_(int p_105435_, int p_105436_) {
        ServerData $$2 = this.m_105432_(p_105435_);
        this.f_105427_.set(p_105435_, this.m_105432_(p_105436_));
        this.f_105427_.set(p_105436_, $$2);
        this.m_105442_();
    }

    public void m_105437_(int p_105438_, ServerData p_105439_) {
        this.f_105427_.set(p_105438_, p_105439_);
    }

    private static boolean m_233839_(ServerData p_233840_, List<ServerData> p_233841_) {
        for (int $$2 = 0; $$2 < p_233841_.size(); ++$$2) {
            ServerData $$3 = p_233841_.get($$2);
            if (!$$3.f_105362_.equals(p_233840_.f_105362_) || !$$3.f_105363_.equals(p_233840_.f_105363_)) continue;
            p_233841_.set($$2, p_233840_);
            return true;
        }
        return false;
    }

    public static void m_105446_(ServerData p_105447_) {
        f_233836_.m_6937_(() -> {
            ServerList $$1 = new ServerList(Minecraft.m_91087_());
            $$1.m_105431_();
            if (!ServerList.m_233839_(p_105447_, $$1.f_105427_)) {
                ServerList.m_233839_(p_105447_, $$1.f_233838_);
            }
            $$1.m_105442_();
        });
    }
}

