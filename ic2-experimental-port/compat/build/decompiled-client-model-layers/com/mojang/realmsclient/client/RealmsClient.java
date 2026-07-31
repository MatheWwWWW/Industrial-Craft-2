/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.client;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClientConfig;
import com.mojang.realmsclient.client.RealmsError;
import com.mojang.realmsclient.client.Request;
import com.mojang.realmsclient.dto.BackupList;
import com.mojang.realmsclient.dto.GuardedSerializer;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.dto.PendingInvitesList;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsDescriptionDto;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.dto.RealmsServerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.RealmsWorldResetDto;
import com.mojang.realmsclient.dto.ServerActivityList;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsHttpException;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import org.slf4j.Logger;

public class RealmsClient {
    public static Environment f_87157_ = Environment.PRODUCTION;
    private static boolean f_87158_;
    private static final Logger f_87159_;
    private final String f_87160_;
    private final String f_87161_;
    private final Minecraft f_87162_;
    private static final String f_167255_ = "worlds";
    private static final String f_167256_ = "invites";
    private static final String f_167257_ = "mco";
    private static final String f_167258_ = "subscriptions";
    private static final String f_167259_ = "activities";
    private static final String f_167260_ = "ops";
    private static final String f_167261_ = "regions/ping/stat";
    private static final String f_167262_ = "trial";
    private static final String f_167263_ = "/$WORLD_ID/initialize";
    private static final String f_167264_ = "/$WORLD_ID";
    private static final String f_167265_ = "/liveplayerlist";
    private static final String f_167266_ = "/$WORLD_ID";
    private static final String f_167267_ = "/$WORLD_ID/$PROFILE_UUID";
    private static final String f_167268_ = "/minigames/$MINIGAME_ID/$WORLD_ID";
    private static final String f_167269_ = "/available";
    private static final String f_167270_ = "/templates/$WORLD_TYPE";
    private static final String f_167271_ = "/v1/$ID/join/pc";
    private static final String f_167272_ = "/$ID";
    private static final String f_167273_ = "/$WORLD_ID";
    private static final String f_167274_ = "/$WORLD_ID/invite/$UUID";
    private static final String f_167237_ = "/count/pending";
    private static final String f_167238_ = "/pending";
    private static final String f_167239_ = "/accept/$INVITATION_ID";
    private static final String f_167240_ = "/reject/$INVITATION_ID";
    private static final String f_167241_ = "/$WORLD_ID";
    private static final String f_167242_ = "/$WORLD_ID";
    private static final String f_167243_ = "/$WORLD_ID/slot/$SLOT_ID";
    private static final String f_167244_ = "/$WORLD_ID/open";
    private static final String f_167245_ = "/$WORLD_ID/close";
    private static final String f_167246_ = "/$WORLD_ID/reset";
    private static final String f_167247_ = "/$WORLD_ID";
    private static final String f_167248_ = "/$WORLD_ID/backups";
    private static final String f_167249_ = "/$WORLD_ID/slot/$SLOT_ID/download";
    private static final String f_167250_ = "/$WORLD_ID/backups/upload";
    private static final String f_167251_ = "/client/compatible";
    private static final String f_167252_ = "/tos/agreed";
    private static final String f_167253_ = "/v1/news";
    private static final String f_167254_ = "/stageAvailable";
    private static final GuardedSerializer f_87163_;

    public static RealmsClient m_87169_() {
        Minecraft $$0 = Minecraft.m_91087_();
        return RealmsClient.m_239151_($$0);
    }

    public static RealmsClient m_239151_(Minecraft p_239152_) {
        String $$1 = p_239152_.m_91094_().m_92546_();
        String $$2 = p_239152_.m_91094_().m_92544_();
        if (!f_87158_) {
            f_87158_ = true;
            String $$3 = System.getenv("realms.environment");
            if ($$3 == null) {
                $$3 = System.getProperty("realms.environment");
            }
            if ($$3 != null) {
                if ("LOCAL".equals($$3)) {
                    RealmsClient.m_87229_();
                } else if ("STAGE".equals($$3)) {
                    RealmsClient.m_87206_();
                }
            }
        }
        return new RealmsClient($$2, $$1, p_239152_);
    }

    public static void m_87206_() {
        f_87157_ = Environment.STAGE;
    }

    public static void m_87221_() {
        f_87157_ = Environment.PRODUCTION;
    }

    public static void m_87229_() {
        f_87157_ = Environment.LOCAL;
    }

    public RealmsClient(String p_87166_, String p_87167_, Minecraft p_87168_) {
        this.f_87160_ = p_87166_;
        this.f_87161_ = p_87167_;
        this.f_87162_ = p_87168_;
        RealmsClientConfig.m_87293_(p_87168_.m_91096_());
    }

    public RealmsServerList m_87235_() throws RealmsServiceException {
        String $$0 = this.m_87227_(f_167255_);
        String $$1 = this.m_87195_(Request.m_87316_($$0));
        return RealmsServerList.m_87577_($$1);
    }

    public RealmsServer m_87174_(long p_87175_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167255_ + f_167272_.replace("$ID", String.valueOf(p_87175_)));
        String $$2 = this.m_87195_(Request.m_87316_($$1));
        return RealmsServer.m_87518_($$2);
    }

    public ServerActivityList m_167278_(long p_167279_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167259_ + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_167279_)));
        String $$2 = this.m_87195_(Request.m_87316_($$1));
        return ServerActivityList.m_167321_($$2);
    }

    public RealmsServerPlayerLists m_87241_() throws RealmsServiceException {
        String $$0 = this.m_87227_("activities/liveplayerlist");
        String $$1 = this.m_87195_(Request.m_87316_($$0));
        return RealmsServerPlayerLists.m_87596_($$1);
    }

    public RealmsServerAddress m_87207_(long p_87208_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167255_ + f_167271_.replace("$ID", "" + p_87208_));
        String $$2 = this.m_87195_(Request.m_87318_($$1, 5000, 30000));
        return RealmsServerAddress.m_87571_($$2);
    }

    public void m_87191_(long p_87192_, String p_87193_, String p_87194_) throws RealmsServiceException {
        RealmsDescriptionDto $$3 = new RealmsDescriptionDto(p_87193_, p_87194_);
        String $$4 = this.m_87227_(f_167255_ + f_167263_.replace("$WORLD_ID", String.valueOf(p_87192_)));
        String $$5 = f_87163_.m_87413_($$3);
        this.m_87195_(Request.m_87325_($$4, $$5, 5000, 10000));
    }

    public Boolean m_87247_() throws RealmsServiceException {
        String $$0 = this.m_87227_("mco/available");
        String $$1 = this.m_87195_(Request.m_87316_($$0));
        return Boolean.valueOf($$1);
    }

    public Boolean m_87253_() throws RealmsServiceException {
        String $$0 = this.m_87227_("mco/stageAvailable");
        String $$1 = this.m_87195_(Request.m_87316_($$0));
        return Boolean.valueOf($$1);
    }

    /*
     * WARNING - void declaration
     */
    public CompatibleVersionResponse m_87259_() throws RealmsServiceException {
        void $$4;
        String $$0 = this.m_87227_("mco/client/compatible");
        String $$1 = this.m_87195_(Request.m_87316_($$0));
        try {
            CompatibleVersionResponse $$2 = CompatibleVersionResponse.valueOf($$1);
        }
        catch (IllegalArgumentException $$3) {
            throw new RealmsServiceException(500, "Could not check compatible version, got response: " + $$1);
        }
        return $$4;
    }

    public void m_87183_(long p_87184_, String p_87185_) throws RealmsServiceException {
        String $$2 = this.m_87227_(f_167256_ + f_167274_.replace("$WORLD_ID", String.valueOf(p_87184_)).replace("$UUID", p_87185_));
        this.m_87195_(Request.m_87340_($$2));
    }

    public void m_87222_(long p_87223_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167256_ + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87223_)));
        this.m_87195_(Request.m_87340_($$1));
    }

    public RealmsServer m_87212_(long p_87213_, String p_87214_) throws RealmsServiceException {
        PlayerInfo $$2 = new PlayerInfo();
        $$2.m_87448_(p_87214_);
        String $$3 = this.m_87227_(f_167256_ + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87213_)));
        String $$4 = this.m_87195_(Request.m_87342_($$3, f_87163_.m_87413_($$2)));
        return RealmsServer.m_87518_($$4);
    }

    public BackupList m_87230_(long p_87231_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167255_ + f_167248_.replace("$WORLD_ID", String.valueOf(p_87231_)));
        String $$2 = this.m_87195_(Request.m_87316_($$1));
        return BackupList.m_87409_($$2);
    }

    public void m_87215_(long p_87216_, String p_87217_, String p_87218_) throws RealmsServiceException {
        RealmsDescriptionDto $$3 = new RealmsDescriptionDto(p_87217_, p_87218_);
        String $$4 = this.m_87227_(f_167255_ + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87216_)));
        this.m_87195_(Request.m_87342_($$4, f_87163_.m_87413_($$3)));
    }

    public void m_87179_(long p_87180_, int p_87181_, RealmsWorldOptions p_87182_) throws RealmsServiceException {
        String $$3 = this.m_87227_(f_167255_ + f_167243_.replace("$WORLD_ID", String.valueOf(p_87180_)).replace("$SLOT_ID", String.valueOf(p_87181_)));
        String $$4 = p_87182_.m_87635_();
        this.m_87195_(Request.m_87342_($$3, $$4));
    }

    public boolean m_87176_(long p_87177_, int p_87178_) throws RealmsServiceException {
        String $$2 = this.m_87227_(f_167255_ + f_167243_.replace("$WORLD_ID", String.valueOf(p_87177_)).replace("$SLOT_ID", String.valueOf(p_87178_)));
        String $$3 = this.m_87195_(Request.m_87353_($$2, ""));
        return Boolean.valueOf($$3);
    }

    public void m_87224_(long p_87225_, String p_87226_) throws RealmsServiceException {
        String $$2 = this.m_87203_(f_167255_ + f_167248_.replace("$WORLD_ID", String.valueOf(p_87225_)), "backupId=" + p_87226_);
        this.m_87195_(Request.m_87345_($$2, "", 40000, 600000));
    }

    public WorldTemplatePaginatedList m_87170_(int p_87171_, int p_87172_, RealmsServer.WorldType p_87173_) throws RealmsServiceException {
        String $$3 = this.m_87203_(f_167255_ + f_167270_.replace("$WORLD_TYPE", p_87173_.toString()), String.format(Locale.ROOT, "page=%d&pageSize=%d", p_87171_, p_87172_));
        String $$4 = this.m_87195_(Request.m_87316_($$3));
        return WorldTemplatePaginatedList.m_87762_($$4);
    }

    public Boolean m_87232_(long p_87233_, String p_87234_) throws RealmsServiceException {
        String $$2 = f_167268_.replace("$MINIGAME_ID", p_87234_).replace("$WORLD_ID", String.valueOf(p_87233_));
        String $$3 = this.m_87227_(f_167255_ + $$2);
        return Boolean.valueOf(this.m_87195_(Request.m_87353_($$3, "")));
    }

    public Ops m_87238_(long p_87239_, String p_87240_) throws RealmsServiceException {
        String $$2 = f_167267_.replace("$WORLD_ID", String.valueOf(p_87239_)).replace("$PROFILE_UUID", p_87240_);
        String $$3 = this.m_87227_(f_167260_ + $$2);
        return Ops.m_87420_(this.m_87195_(Request.m_87342_($$3, "")));
    }

    public Ops m_87244_(long p_87245_, String p_87246_) throws RealmsServiceException {
        String $$2 = f_167267_.replace("$WORLD_ID", String.valueOf(p_87245_)).replace("$PROFILE_UUID", p_87246_);
        String $$3 = this.m_87227_(f_167260_ + $$2);
        return Ops.m_87420_(this.m_87195_(Request.m_87340_($$3)));
    }

    public Boolean m_87236_(long p_87237_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167255_ + f_167244_.replace("$WORLD_ID", String.valueOf(p_87237_)));
        String $$2 = this.m_87195_(Request.m_87353_($$1, ""));
        return Boolean.valueOf($$2);
    }

    public Boolean m_87242_(long p_87243_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167255_ + f_167245_.replace("$WORLD_ID", String.valueOf(p_87243_)));
        String $$2 = this.m_87195_(Request.m_87353_($$1, ""));
        return Boolean.valueOf($$2);
    }

    public Boolean m_167275_(long p_167276_, WorldGenerationInfo p_167277_) throws RealmsServiceException {
        RealmsWorldResetDto $$2 = new RealmsWorldResetDto(p_167277_.m_167634_(), -1L, p_167277_.m_167635_().m_167608_(), p_167277_.m_167636_());
        String $$3 = this.m_87227_(f_167255_ + f_167246_.replace("$WORLD_ID", String.valueOf(p_167276_)));
        String $$4 = this.m_87195_(Request.m_87325_($$3, f_87163_.m_87413_($$2), 30000, 80000));
        return Boolean.valueOf($$4);
    }

    public Boolean m_87250_(long p_87251_, String p_87252_) throws RealmsServiceException {
        RealmsWorldResetDto $$2 = new RealmsWorldResetDto(null, Long.valueOf(p_87252_), -1, false);
        String $$3 = this.m_87227_(f_167255_ + f_167246_.replace("$WORLD_ID", String.valueOf(p_87251_)));
        String $$4 = this.m_87195_(Request.m_87325_($$3, f_87163_.m_87413_($$2), 30000, 80000));
        return Boolean.valueOf($$4);
    }

    public Subscription m_87248_(long p_87249_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167258_ + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87249_)));
        String $$2 = this.m_87195_(Request.m_87316_($$1));
        return Subscription.m_87672_($$2);
    }

    public int m_87260_() throws RealmsServiceException {
        return this.m_87261_().f_87432_.size();
    }

    public PendingInvitesList m_87261_() throws RealmsServiceException {
        String $$0 = this.m_87227_("invites/pending");
        String $$1 = this.m_87195_(Request.m_87316_($$0));
        PendingInvitesList $$2 = PendingInvitesList.m_87436_($$1);
        $$2.f_87432_.removeIf(this::m_87197_);
        return $$2;
    }

    private boolean m_87197_(PendingInvite p_87198_) {
        try {
            UUID $$1 = UUID.fromString(p_87198_.f_87425_);
            return this.f_87162_.m_91266_().m_100688_($$1);
        }
        catch (IllegalArgumentException $$2) {
            return false;
        }
    }

    public void m_87201_(String p_87202_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167256_ + f_167239_.replace("$INVITATION_ID", p_87202_));
        this.m_87195_(Request.m_87353_($$1, ""));
    }

    public WorldDownload m_87209_(long p_87210_, int p_87211_) throws RealmsServiceException {
        String $$2 = this.m_87227_(f_167255_ + f_167249_.replace("$WORLD_ID", String.valueOf(p_87210_)).replace("$SLOT_ID", String.valueOf(p_87211_)));
        String $$3 = this.m_87195_(Request.m_87316_($$2));
        return WorldDownload.m_87724_($$3);
    }

    @Nullable
    public UploadInfo m_87256_(long p_87257_, @Nullable String p_87258_) throws RealmsServiceException {
        String $$2 = this.m_87227_(f_167255_ + f_167250_.replace("$WORLD_ID", String.valueOf(p_87257_)));
        return UploadInfo.m_87700_(this.m_87195_(Request.m_87353_($$2, UploadInfo.m_87709_(p_87258_))));
    }

    public void m_87219_(String p_87220_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167256_ + f_167240_.replace("$INVITATION_ID", p_87220_));
        this.m_87195_(Request.m_87353_($$1, ""));
    }

    public void m_87262_() throws RealmsServiceException {
        String $$0 = this.m_87227_("mco/tos/agreed");
        this.m_87195_(Request.m_87342_($$0, ""));
    }

    public RealmsNews m_87263_() throws RealmsServiceException {
        String $$0 = this.m_87227_("mco/v1/news");
        String $$1 = this.m_87195_(Request.m_87318_($$0, 5000, 10000));
        return RealmsNews.m_87471_($$1);
    }

    public void m_87199_(PingResult p_87200_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167261_);
        this.m_87195_(Request.m_87342_($$1, f_87163_.m_87413_(p_87200_)));
    }

    public Boolean m_87264_() throws RealmsServiceException {
        String $$0 = this.m_87227_(f_167262_);
        String $$1 = this.m_87195_(Request.m_87316_($$0));
        return Boolean.valueOf($$1);
    }

    public void m_87254_(long p_87255_) throws RealmsServiceException {
        String $$1 = this.m_87227_(f_167255_ + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87255_)));
        this.m_87195_(Request.m_87340_($$1));
    }

    private String m_87227_(String p_87228_) {
        return this.m_87203_(p_87228_, null);
    }

    private String m_87203_(String p_87204_, @Nullable String p_87205_) {
        try {
            return new URI(RealmsClient.f_87157_.f_87280_, RealmsClient.f_87157_.f_87279_, "/" + p_87204_, p_87205_, null).toASCIIString();
        }
        catch (URISyntaxException $$2) {
            throw new IllegalArgumentException(p_87204_, $$2);
        }
    }

    private String m_87195_(Request<?> p_87196_) throws RealmsServiceException {
        p_87196_.m_87322_("sid", this.f_87160_);
        p_87196_.m_87322_("user", this.f_87161_);
        p_87196_.m_87322_("version", SharedConstants.m_183709_().getName());
        try {
            int $$1 = p_87196_.m_87339_();
            if ($$1 == 503 || $$1 == 277) {
                int $$2 = p_87196_.m_87313_();
                throw new RetryCallException($$2, $$1);
            }
            String $$3 = p_87196_.m_87350_();
            if ($$1 < 200 || $$1 >= 300) {
                if ($$1 == 401) {
                    String $$4 = p_87196_.m_87351_("WWW-Authenticate");
                    f_87159_.info("Could not authorize you against Realms server: {}", (Object)$$4);
                    throw new RealmsServiceException($$1, $$4);
                }
                RealmsError $$5 = RealmsError.m_87303_($$3);
                if ($$5 != null) {
                    f_87159_.error("Realms http code: {} -  error code: {} -  message: {} - raw body: {}", new Object[]{$$1, $$5.m_87305_(), $$5.m_87302_(), $$3});
                    throw new RealmsServiceException($$1, $$3, $$5);
                }
                f_87159_.error("Realms http code: {} - raw body (message failed to parse): {}", (Object)$$1, (Object)$$3);
                String $$6 = RealmsClient.m_200936_($$1);
                throw new RealmsServiceException($$1, $$6);
            }
            return $$3;
        }
        catch (RealmsHttpException $$7) {
            throw new RealmsServiceException(500, "Could not connect to Realms: " + $$7.getMessage());
        }
    }

    private static String m_200936_(int p_200937_) {
        return switch (p_200937_) {
            case 429 -> I18n.m_118938_("mco.errorMessage.serviceBusy", new Object[0]);
            default -> "Unknown error";
        };
    }

    static {
        f_87159_ = LogUtils.getLogger();
        f_87163_ = new GuardedSerializer();
    }

    public static final class Environment
    extends Enum<Environment> {
        public static final /* enum */ Environment PRODUCTION = new Environment("pc.realms.minecraft.net", "https");
        public static final /* enum */ Environment STAGE = new Environment("pc-stage.realms.minecraft.net", "https");
        public static final /* enum */ Environment LOCAL = new Environment("localhost:8080", "http");
        public String f_87279_;
        public String f_87280_;
        private static final /* synthetic */ Environment[] $VALUES;

        public static Environment[] values() {
            return (Environment[])$VALUES.clone();
        }

        public static Environment valueOf(String p_87289_) {
            return Enum.valueOf(Environment.class, p_87289_);
        }

        private Environment(String p_87286_, String p_87287_) {
            this.f_87279_ = p_87286_;
            this.f_87280_ = p_87287_;
        }

        private static /* synthetic */ Environment[] m_167281_() {
            return new Environment[]{PRODUCTION, STAGE, LOCAL};
        }

        static {
            $VALUES = Environment.m_167281_();
        }
    }

    public static final class CompatibleVersionResponse
    extends Enum<CompatibleVersionResponse> {
        public static final /* enum */ CompatibleVersionResponse COMPATIBLE = new CompatibleVersionResponse();
        public static final /* enum */ CompatibleVersionResponse OUTDATED = new CompatibleVersionResponse();
        public static final /* enum */ CompatibleVersionResponse OTHER = new CompatibleVersionResponse();
        private static final /* synthetic */ CompatibleVersionResponse[] $VALUES;

        public static CompatibleVersionResponse[] values() {
            return (CompatibleVersionResponse[])$VALUES.clone();
        }

        public static CompatibleVersionResponse valueOf(String p_87274_) {
            return Enum.valueOf(CompatibleVersionResponse.class, p_87274_);
        }

        private static /* synthetic */ CompatibleVersionResponse[] m_167280_() {
            return new CompatibleVersionResponse[]{COMPATIBLE, OUTDATED, OTHER};
        }

        static {
            $VALUES = CompatibleVersionResponse.m_167280_();
        }
    }
}

