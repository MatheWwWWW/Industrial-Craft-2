/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedPlayerList;
import net.minecraft.client.server.LanServerPinger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.stats.Stats;
import net.minecraft.util.ModCheck;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;

public class IntegratedServer
extends MinecraftServer {
    private static final Logger f_120014_ = LogUtils.getLogger();
    private static final int f_194466_ = 2;
    private final Minecraft f_120015_;
    private boolean f_120016_ = true;
    private int f_120017_ = -1;
    @Nullable
    private GameType f_174966_;
    @Nullable
    private LanServerPinger f_120018_;
    @Nullable
    private UUID f_120019_;
    private int f_194467_ = 0;

    public IntegratedServer(Thread p_235248_, Minecraft p_235249_, LevelStorageSource.LevelStorageAccess p_235250_, PackRepository p_235251_, WorldStem p_235252_, Services p_235253_, ChunkProgressListenerFactory p_235254_) {
        super(p_235248_, p_235250_, p_235251_, p_235252_, p_235249_.m_91096_(), p_235249_.m_91295_(), p_235253_, p_235254_);
        this.m_236740_(p_235249_.m_91094_().m_92548_());
        this.m_129975_(p_235249_.m_91402_());
        this.m_129823_(new IntegratedPlayerList(this, this.m_206579_(), this.f_129745_));
        this.f_120015_ = p_235249_;
    }

    @Override
    public boolean m_7038_() {
        f_120014_.info("Starting integrated minecraft server version {}", (Object)SharedConstants.m_183709_().getName());
        this.m_129985_(true);
        this.m_129997_(true);
        this.m_129999_(true);
        this.m_129793_();
        this.m_130006_();
        GameProfile $$0 = this.m_236731_();
        String $$1 = this.m_129910_().m_5462_();
        this.m_129989_((String)($$0 != null ? $$0.getName() + " - " + $$1 : $$1));
        return true;
    }

    @Override
    public void m_5705_(BooleanSupplier p_120049_) {
        int $$5;
        boolean $$3;
        boolean $$1 = this.f_120016_;
        this.f_120016_ = Minecraft.m_91087_().m_91104_();
        ProfilerFiller $$2 = this.m_129905_();
        if (!$$1 && this.f_120016_) {
            $$2.m_6180_("autoSave");
            f_120014_.info("Saving and pausing game...");
            this.m_195514_(false, false, false);
            $$2.m_7238_();
        }
        boolean bl = $$3 = Minecraft.m_91087_().m_91403_() != null;
        if ($$3 && this.f_120016_) {
            this.m_174968_();
            return;
        }
        super.m_5705_(p_120049_);
        int $$4 = Math.max(2, this.f_120015_.f_91066_.m_231984_().m_231551_());
        if ($$4 != this.m_6846_().m_11312_()) {
            f_120014_.info("Changing view distance to {}, from {}", (Object)$$4, (Object)this.m_6846_().m_11312_());
            this.m_6846_().m_11217_($$4);
        }
        if (($$5 = Math.max(2, this.f_120015_.f_91066_.m_232001_().m_231551_())) != this.f_194467_) {
            f_120014_.info("Changing simulation distance to {}, from {}", (Object)$$5, (Object)this.f_194467_);
            this.m_6846_().m_184211_($$5);
            this.f_194467_ = $$5;
        }
    }

    private void m_174968_() {
        for (ServerPlayer $$0 : this.m_6846_().m_11314_()) {
            $$0.m_36220_(Stats.f_144256_);
        }
    }

    @Override
    public boolean m_6983_() {
        return true;
    }

    @Override
    public boolean m_6102_() {
        return true;
    }

    @Override
    public File m_6237_() {
        return this.f_120015_.f_91069_;
    }

    @Override
    public boolean m_6982_() {
        return false;
    }

    @Override
    public int m_7032_() {
        return 0;
    }

    @Override
    public boolean m_6994_() {
        return false;
    }

    @Override
    public void m_7268_(CrashReport p_120051_) {
        this.f_120015_.m_231439_(p_120051_);
    }

    @Override
    public SystemReport m_142424_(SystemReport p_174970_) {
        p_174970_.m_143519_("Type", "Integrated Server (map_client.txt)");
        p_174970_.m_143522_("Is Modded", () -> this.m_183471_().m_184605_());
        p_174970_.m_143522_("Launched Version", this.f_120015_::m_91388_);
        return p_174970_;
    }

    @Override
    public ModCheck m_183471_() {
        return Minecraft.m_193589_().m_184598_(super.m_183471_());
    }

    @Override
    public boolean m_7386_(@Nullable GameType p_120041_, boolean p_120042_, int p_120043_) {
        try {
            this.f_120015_.m_193588_();
            this.m_129919_().m_9711_(null, p_120043_);
            f_120014_.info("Started serving on {}", (Object)p_120043_);
            this.f_120017_ = p_120043_;
            this.f_120018_ = new LanServerPinger(this.m_129916_(), "" + p_120043_);
            this.f_120018_.start();
            this.f_174966_ = p_120041_;
            this.m_6846_().m_11284_(p_120042_);
            int $$3 = this.m_129944_(this.f_120015_.f_91074_.m_36316_());
            this.f_120015_.f_91074_.m_108648_($$3);
            for (ServerPlayer $$4 : this.m_6846_().m_11314_()) {
                this.m_129892_().m_82095_($$4);
            }
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public void m_7041_() {
        super.m_7041_();
        if (this.f_120018_ != null) {
            this.f_120018_.interrupt();
            this.f_120018_ = null;
        }
    }

    @Override
    public void m_7570_(boolean p_120053_) {
        this.m_18709_(() -> {
            ArrayList $$0 = Lists.newArrayList(this.m_6846_().m_11314_());
            for (ServerPlayer $$1 : $$0) {
                if ($$1.m_20148_().equals(this.f_120019_)) continue;
                this.m_6846_().m_11286_($$1);
            }
        });
        super.m_7570_(p_120053_);
        if (this.f_120018_ != null) {
            this.f_120018_.interrupt();
            this.f_120018_ = null;
        }
    }

    @Override
    public boolean m_6992_() {
        return this.f_120017_ > -1;
    }

    @Override
    public int m_7010_() {
        return this.f_120017_;
    }

    @Override
    public void m_7835_(GameType p_120039_) {
        super.m_7835_(p_120039_);
        this.f_174966_ = null;
    }

    @Override
    public boolean m_6993_() {
        return true;
    }

    @Override
    public int m_7022_() {
        return 2;
    }

    @Override
    public int m_7034_() {
        return 2;
    }

    public void m_120046_(UUID p_120047_) {
        this.f_120019_ = p_120047_;
    }

    @Override
    public boolean m_7779_(GameProfile p_120045_) {
        return this.m_236731_() != null && p_120045_.getName().equalsIgnoreCase(this.m_236731_().getName());
    }

    @Override
    public int m_7186_(int p_120056_) {
        return (int)(this.f_120015_.f_91066_.m_232018_().m_231551_() * (double)p_120056_);
    }

    @Override
    public boolean m_6365_() {
        return this.f_120015_.f_91066_.f_92076_;
    }

    @Override
    @Nullable
    public GameType m_142359_() {
        if (this.m_6992_()) {
            return (GameType)((Object)MoreObjects.firstNonNull((Object)((Object)this.f_174966_), (Object)((Object)this.f_129749_.m_5464_())));
        }
        return null;
    }
}

