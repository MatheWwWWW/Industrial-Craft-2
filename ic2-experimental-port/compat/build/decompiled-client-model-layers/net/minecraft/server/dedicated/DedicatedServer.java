/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.dedicated;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.ConsoleInput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.dedicated.ServerWatchdog;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.network.TextFilterClient;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.rcon.RconConsoleSource;
import net.minecraft.server.rcon.thread.QueryThreadGs4;
import net.minecraft.server.rcon.thread.RconThread;
import net.minecraft.util.Mth;
import net.minecraft.util.monitoring.jmx.MinecraftServerStatistics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;

public class DedicatedServer
extends MinecraftServer
implements ServerInterface {
    static final Logger f_139598_ = LogUtils.getLogger();
    private static final int f_142864_ = 5000;
    private static final int f_142865_ = 2;
    private final List<ConsoleInput> f_139600_ = Collections.synchronizedList(Lists.newArrayList());
    @Nullable
    private QueryThreadGs4 f_139601_;
    private final RconConsoleSource f_139602_;
    @Nullable
    private RconThread f_139603_;
    private final DedicatedServerSettings f_139604_;
    @Nullable
    private MinecraftServerGui f_139605_;
    @Nullable
    private final TextFilterClient f_139606_;

    public DedicatedServer(Thread p_214789_, LevelStorageSource.LevelStorageAccess p_214790_, PackRepository p_214791_, WorldStem p_214792_, DedicatedServerSettings p_214793_, DataFixer p_214794_, Services p_214795_, ChunkProgressListenerFactory p_214796_) {
        super(p_214789_, p_214790_, p_214791_, p_214792_, Proxy.NO_PROXY, p_214794_, p_214795_, p_214796_);
        this.f_139604_ = p_214793_;
        this.f_139602_ = new RconConsoleSource(this);
        this.f_139606_ = TextFilterClient.m_143736_(p_214793_.m_139777_().f_139724_);
    }

    @Override
    public boolean m_7038_() throws IOException {
        Thread $$0 = new Thread("Server console handler"){

            @Override
            public void run() {
                BufferedReader $$0 = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                try {
                    String $$1;
                    while (!DedicatedServer.this.m_129918_() && DedicatedServer.this.m_130010_() && ($$1 = $$0.readLine()) != null) {
                        DedicatedServer.this.m_139645_($$1, DedicatedServer.this.m_129893_());
                    }
                }
                catch (IOException $$2) {
                    f_139598_.error("Exception handling console input", (Throwable)$$2);
                }
            }
        };
        $$0.setDaemon(true);
        $$0.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_139598_));
        $$0.start();
        f_139598_.info("Starting minecraft server version {}", (Object)SharedConstants.m_183709_().getName());
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            f_139598_.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
        f_139598_.info("Loading properties");
        DedicatedServerProperties $$1 = this.f_139604_.m_139777_();
        if (this.m_129792_()) {
            this.m_129913_("127.0.0.1");
        } else {
            this.m_129985_($$1.f_139728_);
            this.m_129993_($$1.f_139729_);
            this.m_129913_($$1.f_139730_);
        }
        this.m_129997_($$1.f_139733_);
        this.m_129999_($$1.f_139734_);
        this.m_129989_($$1.f_139736_);
        super.m_7196_($$1.f_139725_.get());
        this.m_130004_($$1.f_139738_);
        this.f_129749_.m_5458_($$1.f_139740_);
        f_139598_.info("Default game type: {}", (Object)$$1.f_139740_);
        InetAddress $$2 = null;
        if (!this.m_130009_().isEmpty()) {
            $$2 = InetAddress.getByName(this.m_130009_());
        }
        if (this.m_7010_() < 0) {
            this.m_129801_($$1.f_139742_);
        }
        this.m_129793_();
        f_139598_.info("Starting Minecraft server on {}:{}", (Object)(this.m_130009_().isEmpty() ? "*" : this.m_130009_()), (Object)this.m_7010_());
        try {
            this.m_129919_().m_9711_($$2, this.m_7010_());
        }
        catch (IOException $$3) {
            f_139598_.warn("**** FAILED TO BIND TO PORT!");
            f_139598_.warn("The exception was: {}", (Object)$$3.toString());
            f_139598_.warn("Perhaps a server is already running on that port?");
            return false;
        }
        if (!this.m_129797_()) {
            f_139598_.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            f_139598_.warn("The server will make no attempt to authenticate usernames. Beware.");
            f_139598_.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            f_139598_.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }
        if (this.m_139668_()) {
            this.m_129927_().m_11006_();
        }
        if (!OldUsersConverter.m_11106_(this)) {
            return false;
        }
        this.m_129823_(new DedicatedPlayerList(this, this.m_206579_(), this.f_129745_));
        long $$4 = Util.m_137569_();
        SkullBlockEntity.m_222885_(this.f_236721_, this);
        GameProfileCache.m_11004_(this.m_129797_());
        f_139598_.info("Preparing level \"{}\"", (Object)this.m_7123_());
        this.m_130006_();
        long $$5 = Util.m_137569_() - $$4;
        String $$6 = String.format(Locale.ROOT, "%.3fs", (double)$$5 / 1.0E9);
        f_139598_.info("Done ({})! For help, type \"help\"", (Object)$$6);
        if ($$1.f_139744_ != null) {
            this.m_129900_().m_46170_(GameRules.f_46153_).m_46246_($$1.f_139744_, this);
        }
        if ($$1.f_139745_) {
            f_139598_.info("Starting GS4 status listener");
            this.f_139601_ = QueryThreadGs4.m_11553_(this);
        }
        if ($$1.f_139747_) {
            f_139598_.info("Starting remote control listener");
            this.f_139603_ = RconThread.m_11615_(this);
        }
        if (this.m_139669_() > 0L) {
            Thread $$7 = new Thread(new ServerWatchdog(this));
            $$7.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(f_139598_));
            $$7.setName("Server Watchdog");
            $$7.setDaemon(true);
            $$7.start();
        }
        Items.f_41852_.m_6787_(CreativeModeTab.f_40754_, NonNullList.m_122779_());
        if ($$1.f_139721_) {
            MinecraftServerStatistics.m_18328_(this);
            f_139598_.info("JMX monitoring enabled");
        }
        return true;
    }

    @Override
    public boolean m_6998_() {
        return this.m_7913_().f_139731_ && super.m_6998_();
    }

    @Override
    public boolean m_7004_() {
        return this.f_139604_.m_139777_().f_139705_ && super.m_7004_();
    }

    @Override
    public boolean m_6997_() {
        return this.f_139604_.m_139777_().f_139732_ && super.m_6997_();
    }

    @Override
    public DedicatedServerProperties m_7913_() {
        return this.f_139604_.m_139777_();
    }

    @Override
    public void m_7044_() {
        this.m_129827_(this.m_7913_().f_139739_, true);
    }

    @Override
    public boolean m_7035_() {
        return this.m_7913_().f_139752_;
    }

    @Override
    public SystemReport m_142424_(SystemReport p_142870_) {
        p_142870_.m_143522_("Is Modded", () -> this.m_183471_().m_184605_());
        p_142870_.m_143522_("Type", () -> "Dedicated Server (map_server.txt)");
        return p_142870_;
    }

    @Override
    public void m_142116_(Path p_142872_) throws IOException {
        DedicatedServerProperties $$1 = this.m_7913_();
        try (BufferedWriter $$2 = Files.newBufferedWriter(p_142872_, new OpenOption[0]);){
            $$2.write(String.format(Locale.ROOT, "sync-chunk-writes=%s%n", $$1.f_139720_));
            $$2.write(String.format(Locale.ROOT, "gamemode=%s%n", new Object[]{$$1.f_139740_}));
            $$2.write(String.format(Locale.ROOT, "spawn-monsters=%s%n", $$1.f_139705_));
            $$2.write(String.format(Locale.ROOT, "entity-broadcast-range-percentage=%d%n", $$1.f_139723_));
            $$2.write(String.format(Locale.ROOT, "max-world-size=%d%n", $$1.f_139719_));
            $$2.write(String.format(Locale.ROOT, "spawn-npcs=%s%n", $$1.f_139732_));
            $$2.write(String.format(Locale.ROOT, "view-distance=%d%n", $$1.f_139714_));
            $$2.write(String.format(Locale.ROOT, "simulation-distance=%d%n", $$1.f_183715_));
            $$2.write(String.format(Locale.ROOT, "spawn-animals=%s%n", $$1.f_139731_));
            $$2.write(String.format(Locale.ROOT, "generate-structures=%s%n", $$1.m_180927_(this.m_206579_()).m_224677_()));
            $$2.write(String.format(Locale.ROOT, "use-native=%s%n", $$1.f_139707_));
            $$2.write(String.format(Locale.ROOT, "rate-limit=%d%n", $$1.f_139713_));
        }
    }

    @Override
    public void m_6988_() {
        if (this.f_139606_ != null) {
            this.f_139606_.close();
        }
        if (this.f_139605_ != null) {
            this.f_139605_.m_139925_();
        }
        if (this.f_139603_ != null) {
            this.f_139603_.m_7530_();
        }
        if (this.f_139601_ != null) {
            this.f_139601_.m_7530_();
        }
    }

    @Override
    public void m_5703_(BooleanSupplier p_139661_) {
        super.m_5703_(p_139661_);
        this.m_139665_();
    }

    @Override
    public boolean m_7079_() {
        return this.m_7913_().f_139753_;
    }

    public void m_139645_(String p_139646_, CommandSourceStack p_139647_) {
        this.f_139600_.add(new ConsoleInput(p_139646_, p_139647_));
    }

    public void m_139665_() {
        while (!this.f_139600_.isEmpty()) {
            ConsoleInput $$0 = this.f_139600_.remove(0);
            this.m_129892_().m_230957_($$0.f_135929_, $$0.f_135928_);
        }
    }

    @Override
    public boolean m_6982_() {
        return true;
    }

    @Override
    public int m_7032_() {
        return this.m_7913_().f_139713_;
    }

    @Override
    public boolean m_6994_() {
        return this.m_7913_().f_139707_;
    }

    @Override
    public boolean m_214000_() {
        return this.m_7913_().f_214803_;
    }

    @Override
    public DedicatedPlayerList m_6846_() {
        return (DedicatedPlayerList)super.m_6846_();
    }

    @Override
    public boolean m_6992_() {
        return true;
    }

    @Override
    public String m_6866_() {
        return this.m_130009_();
    }

    @Override
    public int m_7448_() {
        return this.m_7010_();
    }

    @Override
    public String m_6995_() {
        return this.m_129916_();
    }

    public void m_139667_() {
        if (this.f_139605_ == null) {
            this.f_139605_ = MinecraftServerGui.m_139921_(this);
        }
    }

    @Override
    public boolean m_6370_() {
        return this.f_139605_ != null;
    }

    @Override
    public boolean m_6993_() {
        return this.m_7913_().f_139708_;
    }

    @Override
    public int m_6396_() {
        return this.m_7913_().f_139709_;
    }

    @Override
    public boolean m_7762_(ServerLevel p_139630_, BlockPos p_139631_, Player p_139632_) {
        int $$5;
        if (p_139630_.m_46472_() != Level.f_46428_) {
            return false;
        }
        if (this.m_6846_().m_11307_().m_11390_()) {
            return false;
        }
        if (this.m_6846_().m_11303_(p_139632_.m_36316_())) {
            return false;
        }
        if (this.m_6396_() <= 0) {
            return false;
        }
        BlockPos $$3 = p_139630_.m_220360_();
        int $$4 = Mth.m_14040_(p_139631_.m_123341_() - $$3.m_123341_());
        int $$6 = Math.max($$4, $$5 = Mth.m_14040_(p_139631_.m_123343_() - $$3.m_123343_()));
        return $$6 <= this.m_6396_();
    }

    @Override
    public boolean m_6373_() {
        return this.m_7913_().f_139722_;
    }

    @Override
    public boolean m_183306_() {
        return this.m_7913_().f_183716_;
    }

    @Override
    public int m_7022_() {
        return this.m_7913_().f_139710_;
    }

    @Override
    public int m_7034_() {
        return this.m_7913_().f_139711_;
    }

    @Override
    public void m_7196_(int p_139676_) {
        super.m_7196_(p_139676_);
        this.f_139604_.m_139778_(p_139628_ -> (DedicatedServerProperties)p_139628_.f_139725_.m_139895_(this.m_206579_(), p_139676_));
    }

    @Override
    public boolean m_6983_() {
        return this.m_7913_().f_139717_;
    }

    @Override
    public boolean m_6102_() {
        return this.m_7913_().f_139718_;
    }

    @Override
    public int m_6329_() {
        return this.m_7913_().f_139719_;
    }

    @Override
    public int m_6328_() {
        return this.m_7913_().f_139716_;
    }

    @Override
    public boolean m_214005_() {
        return this.m_7913_().f_214804_ && this.m_7913_().f_139728_;
    }

    protected boolean m_139668_() {
        int $$1;
        boolean $$0 = false;
        for ($$1 = 0; !$$0 && $$1 <= 2; ++$$1) {
            if ($$1 > 0) {
                f_139598_.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
                this.m_139671_();
            }
            $$0 = OldUsersConverter.m_11081_(this);
        }
        boolean $$2 = false;
        for ($$1 = 0; !$$2 && $$1 <= 2; ++$$1) {
            if ($$1 > 0) {
                f_139598_.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
                this.m_139671_();
            }
            $$2 = OldUsersConverter.m_11098_(this);
        }
        boolean $$3 = false;
        for ($$1 = 0; !$$3 && $$1 <= 2; ++$$1) {
            if ($$1 > 0) {
                f_139598_.warn("Encountered a problem while converting the op list, retrying in a few seconds");
                this.m_139671_();
            }
            $$3 = OldUsersConverter.m_11102_(this);
        }
        boolean $$4 = false;
        for ($$1 = 0; !$$4 && $$1 <= 2; ++$$1) {
            if ($$1 > 0) {
                f_139598_.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
                this.m_139671_();
            }
            $$4 = OldUsersConverter.m_11104_(this);
        }
        boolean $$5 = false;
        for ($$1 = 0; !$$5 && $$1 <= 2; ++$$1) {
            if ($$1 > 0) {
                f_139598_.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
                this.m_139671_();
            }
            $$5 = OldUsersConverter.m_11090_(this);
        }
        return $$0 || $$2 || $$3 || $$4 || $$5;
    }

    private void m_139671_() {
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException $$0) {
            return;
        }
    }

    public long m_139669_() {
        return this.m_7913_().f_139712_;
    }

    @Override
    public int m_213994_() {
        return this.m_7913_().f_214801_;
    }

    @Override
    public String m_7138_() {
        return "";
    }

    @Override
    public String m_7261_(String p_139644_) {
        this.f_139602_.m_11512_();
        this.m_18709_(() -> this.m_129892_().m_230957_(this.f_139602_.m_11514_(), p_139644_));
        return this.f_139602_.m_11513_();
    }

    public void m_139688_(boolean p_139689_) {
        this.f_139604_.m_139778_(p_139650_ -> (DedicatedServerProperties)p_139650_.f_139726_.m_139895_(this.m_206579_(), p_139689_));
    }

    @Override
    public void m_7041_() {
        super.m_7041_();
        Util.m_137580_();
        SkullBlockEntity.m_196704_();
    }

    @Override
    public boolean m_7779_(GameProfile p_139642_) {
        return false;
    }

    @Override
    public int m_7186_(int p_139659_) {
        return this.m_7913_().f_139723_ * p_139659_ / 100;
    }

    @Override
    public String m_7123_() {
        return this.f_129744_.m_78277_();
    }

    @Override
    public boolean m_6365_() {
        return this.f_139604_.m_139777_().f_139720_;
    }

    @Override
    public TextFilter m_7950_(ServerPlayer p_139634_) {
        if (this.f_139606_ != null) {
            return this.f_139606_.m_10134_(p_139634_.m_36316_());
        }
        return TextFilter.f_143703_;
    }

    @Override
    @Nullable
    public GameType m_142359_() {
        return this.f_139604_.m_139777_().f_139737_ ? this.f_129749_.m_5464_() : null;
    }

    @Override
    public Optional<MinecraftServer.ServerResourcePackInfo> m_214042_() {
        return this.f_139604_.m_139777_().f_214802_;
    }

    @Override
    public /* synthetic */ PlayerList m_6846_() {
        return this.m_6846_();
    }
}

