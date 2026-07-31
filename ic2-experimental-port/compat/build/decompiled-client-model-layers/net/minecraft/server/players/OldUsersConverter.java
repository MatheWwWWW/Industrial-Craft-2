/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.ProfileLookupCallback
 *  com.mojang.authlib.yggdrasil.ProfileNotFoundException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.players.BanListEntry;
import net.minecraft.server.players.IpBanList;
import net.minecraft.server.players.IpBanListEntry;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.ServerOpList;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.storage.LevelResource;
import org.slf4j.Logger;

public class OldUsersConverter {
    static final Logger f_11066_ = LogUtils.getLogger();
    public static final File f_11062_ = new File("banned-ips.txt");
    public static final File f_11063_ = new File("banned-players.txt");
    public static final File f_11064_ = new File("ops.txt");
    public static final File f_11065_ = new File("white-list.txt");

    static List<String> m_11073_(File p_11074_, Map<String, String[]> p_11075_) throws IOException {
        List $$2 = Files.readLines((File)p_11074_, (Charset)StandardCharsets.UTF_8);
        for (String $$3 : $$2) {
            if (($$3 = $$3.trim()).startsWith("#") || $$3.length() < 1) continue;
            String[] $$4 = $$3.split("\\|");
            p_11075_.put($$4[0].toLowerCase(Locale.ROOT), $$4);
        }
        return $$2;
    }

    private static void m_11086_(MinecraftServer p_11087_, Collection<String> p_11088_, ProfileLookupCallback p_11089_) {
        String[] $$3 = (String[])p_11088_.stream().filter(p_11077_ -> !StringUtil.m_14408_(p_11077_)).toArray(String[]::new);
        if (p_11087_.m_129797_()) {
            p_11087_.m_129926_().findProfilesByNames($$3, Agent.MINECRAFT, p_11089_);
        } else {
            for (String $$4 : $$3) {
                UUID $$5 = UUIDUtil.m_235875_(new GameProfile(null, $$4));
                GameProfile $$6 = new GameProfile($$5, $$4);
                p_11089_.onProfileLookupSucceeded($$6);
            }
        }
    }

    public static boolean m_11081_(final MinecraftServer p_11082_) {
        final UserBanList $$1 = new UserBanList(PlayerList.f_11189_);
        if (f_11063_.exists() && f_11063_.isFile()) {
            if ($$1.m_11385_().exists()) {
                try {
                    $$1.m_11399_();
                }
                catch (IOException $$2) {
                    f_11066_.warn("Could not load existing file {}", (Object)$$1.m_11385_().getName(), (Object)$$2);
                }
            }
            try {
                final HashMap $$3 = Maps.newHashMap();
                OldUsersConverter.m_11073_(f_11063_, $$3);
                ProfileLookupCallback $$4 = new ProfileLookupCallback(){

                    public void onProfileLookupSucceeded(GameProfile p_11123_) {
                        p_11082_.m_129927_().m_10991_(p_11123_);
                        String[] $$12 = (String[])$$3.get(p_11123_.getName().toLowerCase(Locale.ROOT));
                        if ($$12 == null) {
                            f_11066_.warn("Could not convert user banlist entry for {}", (Object)p_11123_.getName());
                            throw new ConversionError("Profile not in the conversionlist");
                        }
                        Date $$2 = $$12.length > 1 ? OldUsersConverter.m_11095_($$12[1], null) : null;
                        String $$32 = $$12.length > 2 ? $$12[2] : null;
                        Date $$4 = $$12.length > 3 ? OldUsersConverter.m_11095_($$12[3], null) : null;
                        String $$5 = $$12.length > 4 ? $$12[4] : null;
                        $$1.m_11381_(new UserBanListEntry(p_11123_, $$2, $$32, $$4, $$5));
                    }

                    public void onProfileLookupFailed(GameProfile p_11120_, Exception p_11121_) {
                        f_11066_.warn("Could not lookup user banlist entry for {}", (Object)p_11120_.getName(), (Object)p_11121_);
                        if (!(p_11121_ instanceof ProfileNotFoundException)) {
                            throw new ConversionError("Could not request user " + p_11120_.getName() + " from backend systems", p_11121_);
                        }
                    }
                };
                OldUsersConverter.m_11086_(p_11082_, $$3.keySet(), $$4);
                $$1.m_11398_();
                OldUsersConverter.m_11100_(f_11063_);
            }
            catch (IOException $$5) {
                f_11066_.warn("Could not read old user banlist to convert it!", (Throwable)$$5);
                return false;
            }
            catch (ConversionError $$6) {
                f_11066_.error("Conversion failed, please try again later", (Throwable)$$6);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean m_11098_(MinecraftServer p_11099_) {
        IpBanList $$1 = new IpBanList(PlayerList.f_11190_);
        if (f_11062_.exists() && f_11062_.isFile()) {
            if ($$1.m_11385_().exists()) {
                try {
                    $$1.m_11399_();
                }
                catch (IOException $$2) {
                    f_11066_.warn("Could not load existing file {}", (Object)$$1.m_11385_().getName(), (Object)$$2);
                }
            }
            try {
                HashMap $$3 = Maps.newHashMap();
                OldUsersConverter.m_11073_(f_11062_, $$3);
                for (String $$4 : $$3.keySet()) {
                    String[] $$5 = (String[])$$3.get($$4);
                    Date $$6 = $$5.length > 1 ? OldUsersConverter.m_11095_($$5[1], null) : null;
                    String $$7 = $$5.length > 2 ? $$5[2] : null;
                    Date $$8 = $$5.length > 3 ? OldUsersConverter.m_11095_($$5[3], null) : null;
                    String $$9 = $$5.length > 4 ? $$5[4] : null;
                    $$1.m_11381_(new IpBanListEntry($$4, $$6, $$7, $$8, $$9));
                }
                $$1.m_11398_();
                OldUsersConverter.m_11100_(f_11062_);
            }
            catch (IOException $$10) {
                f_11066_.warn("Could not parse old ip banlist to convert it!", (Throwable)$$10);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean m_11102_(final MinecraftServer p_11103_) {
        final ServerOpList $$1 = new ServerOpList(PlayerList.f_11191_);
        if (f_11064_.exists() && f_11064_.isFile()) {
            if ($$1.m_11385_().exists()) {
                try {
                    $$1.m_11399_();
                }
                catch (IOException $$2) {
                    f_11066_.warn("Could not load existing file {}", (Object)$$1.m_11385_().getName(), (Object)$$2);
                }
            }
            try {
                List $$3 = Files.readLines((File)f_11064_, (Charset)StandardCharsets.UTF_8);
                ProfileLookupCallback $$4 = new ProfileLookupCallback(){

                    public void onProfileLookupSucceeded(GameProfile p_11133_) {
                        p_11103_.m_129927_().m_10991_(p_11133_);
                        $$1.m_11381_(new ServerOpListEntry(p_11133_, p_11103_.m_7022_(), false));
                    }

                    public void onProfileLookupFailed(GameProfile p_11130_, Exception p_11131_) {
                        f_11066_.warn("Could not lookup oplist entry for {}", (Object)p_11130_.getName(), (Object)p_11131_);
                        if (!(p_11131_ instanceof ProfileNotFoundException)) {
                            throw new ConversionError("Could not request user " + p_11130_.getName() + " from backend systems", p_11131_);
                        }
                    }
                };
                OldUsersConverter.m_11086_(p_11103_, $$3, $$4);
                $$1.m_11398_();
                OldUsersConverter.m_11100_(f_11064_);
            }
            catch (IOException $$5) {
                f_11066_.warn("Could not read old oplist to convert it!", (Throwable)$$5);
                return false;
            }
            catch (ConversionError $$6) {
                f_11066_.error("Conversion failed, please try again later", (Throwable)$$6);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean m_11104_(final MinecraftServer p_11105_) {
        final UserWhiteList $$1 = new UserWhiteList(PlayerList.f_11192_);
        if (f_11065_.exists() && f_11065_.isFile()) {
            if ($$1.m_11385_().exists()) {
                try {
                    $$1.m_11399_();
                }
                catch (IOException $$2) {
                    f_11066_.warn("Could not load existing file {}", (Object)$$1.m_11385_().getName(), (Object)$$2);
                }
            }
            try {
                List $$3 = Files.readLines((File)f_11065_, (Charset)StandardCharsets.UTF_8);
                ProfileLookupCallback $$4 = new ProfileLookupCallback(){

                    public void onProfileLookupSucceeded(GameProfile p_11143_) {
                        p_11105_.m_129927_().m_10991_(p_11143_);
                        $$1.m_11381_(new UserWhiteListEntry(p_11143_));
                    }

                    public void onProfileLookupFailed(GameProfile p_11140_, Exception p_11141_) {
                        f_11066_.warn("Could not lookup user whitelist entry for {}", (Object)p_11140_.getName(), (Object)p_11141_);
                        if (!(p_11141_ instanceof ProfileNotFoundException)) {
                            throw new ConversionError("Could not request user " + p_11140_.getName() + " from backend systems", p_11141_);
                        }
                    }
                };
                OldUsersConverter.m_11086_(p_11105_, $$3, $$4);
                $$1.m_11398_();
                OldUsersConverter.m_11100_(f_11065_);
            }
            catch (IOException $$5) {
                f_11066_.warn("Could not read old whitelist to convert it!", (Throwable)$$5);
                return false;
            }
            catch (ConversionError $$6) {
                f_11066_.error("Conversion failed, please try again later", (Throwable)$$6);
                return false;
            }
            return true;
        }
        return true;
    }

    @Nullable
    public static UUID m_11083_(final MinecraftServer p_11084_, String p_11085_) {
        if (StringUtil.m_14408_(p_11085_) || p_11085_.length() > 16) {
            try {
                return UUID.fromString(p_11085_);
            }
            catch (IllegalArgumentException $$2) {
                return null;
            }
        }
        Optional<UUID> $$3 = p_11084_.m_129927_().m_10996_(p_11085_).map(GameProfile::getId);
        if ($$3.isPresent()) {
            return $$3.get();
        }
        if (p_11084_.m_129792_() || !p_11084_.m_129797_()) {
            return UUIDUtil.m_235875_(new GameProfile(null, p_11085_));
        }
        final ArrayList $$4 = Lists.newArrayList();
        ProfileLookupCallback $$5 = new ProfileLookupCallback(){

            public void onProfileLookupSucceeded(GameProfile p_11153_) {
                p_11084_.m_129927_().m_10991_(p_11153_);
                $$4.add(p_11153_);
            }

            public void onProfileLookupFailed(GameProfile p_11150_, Exception p_11151_) {
                f_11066_.warn("Could not lookup user whitelist entry for {}", (Object)p_11150_.getName(), (Object)p_11151_);
            }
        };
        OldUsersConverter.m_11086_(p_11084_, Lists.newArrayList((Object[])new String[]{p_11085_}), $$5);
        if (!$$4.isEmpty() && ((GameProfile)$$4.get(0)).getId() != null) {
            return ((GameProfile)$$4.get(0)).getId();
        }
        return null;
    }

    public static boolean m_11090_(final DedicatedServer p_11091_) {
        final File $$1 = OldUsersConverter.m_11110_(p_11091_);
        final File $$2 = new File($$1.getParentFile(), "playerdata");
        final File $$3 = new File($$1.getParentFile(), "unknownplayers");
        if (!$$1.exists() || !$$1.isDirectory()) {
            return true;
        }
        File[] $$4 = $$1.listFiles();
        ArrayList $$5 = Lists.newArrayList();
        for (File $$6 : $$4) {
            String $$8;
            String $$7 = $$6.getName();
            if (!$$7.toLowerCase(Locale.ROOT).endsWith(".dat") || ($$8 = $$7.substring(0, $$7.length() - ".dat".length())).isEmpty()) continue;
            $$5.add($$8);
        }
        try {
            Object[] $$9 = $$5.toArray(new String[$$5.size()]);
            ProfileLookupCallback $$10 = new ProfileLookupCallback(){
                final /* synthetic */ String[] f_11158_;
                {
                    this.f_11158_ = stringArray;
                }

                public void onProfileLookupSucceeded(GameProfile p_11175_) {
                    p_11091_.m_129927_().m_10991_(p_11175_);
                    UUID $$12 = p_11175_.getId();
                    if ($$12 == null) {
                        throw new ConversionError("Missing UUID for user profile " + p_11175_.getName());
                    }
                    this.m_11167_($$2, this.m_11165_(p_11175_), $$12.toString());
                }

                public void onProfileLookupFailed(GameProfile p_11172_, Exception p_11173_) {
                    f_11066_.warn("Could not lookup user uuid for {}", (Object)p_11172_.getName(), (Object)p_11173_);
                    if (!(p_11173_ instanceof ProfileNotFoundException)) {
                        throw new ConversionError("Could not request user " + p_11172_.getName() + " from backend systems", p_11173_);
                    }
                    String $$22 = this.m_11165_(p_11172_);
                    this.m_11167_($$3, $$22, $$22);
                }

                private void m_11167_(File p_11168_, String p_11169_, String p_11170_) {
                    File $$32 = new File($$1, p_11169_ + ".dat");
                    File $$4 = new File(p_11168_, p_11170_ + ".dat");
                    OldUsersConverter.m_11093_(p_11168_);
                    if (!$$32.renameTo($$4)) {
                        throw new ConversionError("Could not convert file for " + p_11169_);
                    }
                }

                private String m_11165_(GameProfile p_11166_) {
                    String $$12 = null;
                    for (String $$22 : this.f_11158_) {
                        if ($$22 == null || !$$22.equalsIgnoreCase(p_11166_.getName())) continue;
                        $$12 = $$22;
                        break;
                    }
                    if ($$12 == null) {
                        throw new ConversionError("Could not find the filename for " + p_11166_.getName() + " anymore");
                    }
                    return $$12;
                }
            };
            OldUsersConverter.m_11086_(p_11091_, Lists.newArrayList((Object[])$$9), $$10);
        }
        catch (ConversionError $$11) {
            f_11066_.error("Conversion failed, please try again later", (Throwable)$$11);
            return false;
        }
        return true;
    }

    static void m_11093_(File p_11094_) {
        if (p_11094_.exists()) {
            if (p_11094_.isDirectory()) {
                return;
            }
            throw new ConversionError("Can't create directory " + p_11094_.getName() + " in world save directory.");
        }
        if (!p_11094_.mkdirs()) {
            throw new ConversionError("Can't create directory " + p_11094_.getName() + " in world save directory.");
        }
    }

    public static boolean m_11106_(MinecraftServer p_11107_) {
        boolean $$1 = OldUsersConverter.m_11092_();
        $$1 = $$1 && OldUsersConverter.m_11108_(p_11107_);
        return $$1;
    }

    private static boolean m_11092_() {
        boolean $$0 = false;
        if (f_11063_.exists() && f_11063_.isFile()) {
            $$0 = true;
        }
        boolean $$1 = false;
        if (f_11062_.exists() && f_11062_.isFile()) {
            $$1 = true;
        }
        boolean $$2 = false;
        if (f_11064_.exists() && f_11064_.isFile()) {
            $$2 = true;
        }
        boolean $$3 = false;
        if (f_11065_.exists() && f_11065_.isFile()) {
            $$3 = true;
        }
        if ($$0 || $$1 || $$2 || $$3) {
            f_11066_.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
            f_11066_.warn("** please remove the following files and restart the server:");
            if ($$0) {
                f_11066_.warn("* {}", (Object)f_11063_.getName());
            }
            if ($$1) {
                f_11066_.warn("* {}", (Object)f_11062_.getName());
            }
            if ($$2) {
                f_11066_.warn("* {}", (Object)f_11064_.getName());
            }
            if ($$3) {
                f_11066_.warn("* {}", (Object)f_11065_.getName());
            }
            return false;
        }
        return true;
    }

    private static boolean m_11108_(MinecraftServer p_11109_) {
        File $$1 = OldUsersConverter.m_11110_(p_11109_);
        if ($$1.exists() && $$1.isDirectory() && ($$1.list().length > 0 || !$$1.delete())) {
            f_11066_.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
            f_11066_.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
            f_11066_.warn("** please restart the server and if the problem persists, remove the directory '{}'", (Object)$$1.getPath());
            return false;
        }
        return true;
    }

    private static File m_11110_(MinecraftServer p_11111_) {
        return p_11111_.m_129843_(LevelResource.f_78177_).toFile();
    }

    private static void m_11100_(File p_11101_) {
        File $$1 = new File(p_11101_.getName() + ".converted");
        p_11101_.renameTo($$1);
    }

    static Date m_11095_(String p_11096_, Date p_11097_) {
        Date $$4;
        try {
            Date $$2 = BanListEntry.f_10943_.parse(p_11096_);
        }
        catch (ParseException $$3) {
            $$4 = p_11097_;
        }
        return $$4;
    }

    static class ConversionError
    extends RuntimeException {
        ConversionError(String p_11182_, Throwable p_11183_) {
            super(p_11182_, p_11183_);
        }

        ConversionError(String p_11177_) {
            super(p_11177_);
        }
    }
}

