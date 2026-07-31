/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class ServerData {
    private static final Logger f_233799_ = LogUtils.getLogger();
    public String f_105362_;
    public String f_105363_;
    public Component f_105364_;
    public Component f_105365_;
    public long f_105366_;
    public int f_105367_ = SharedConstants.m_183709_().getProtocolVersion();
    public Component f_105368_ = Component.m_237113_(SharedConstants.m_183709_().getName());
    public boolean f_105369_;
    public List<Component> f_105370_ = Collections.emptyList();
    private ServerPackStatus f_105371_ = ServerPackStatus.PROMPT;
    @Nullable
    private String f_105372_;
    private boolean f_105373_;
    @Nullable
    private ChatPreview f_233800_;
    private boolean f_233801_ = true;
    private boolean f_242950_;

    public ServerData(String p_105375_, String p_105376_, boolean p_105377_) {
        this.f_105362_ = p_105375_;
        this.f_105363_ = p_105376_;
        this.f_105373_ = p_105377_;
    }

    public CompoundTag m_105378_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128359_("name", this.f_105362_);
        $$0.m_128359_("ip", this.f_105363_);
        if (this.f_105372_ != null) {
            $$0.m_128359_("icon", this.f_105372_);
        }
        if (this.f_105371_ == ServerPackStatus.ENABLED) {
            $$0.m_128379_("acceptTextures", true);
        } else if (this.f_105371_ == ServerPackStatus.DISABLED) {
            $$0.m_128379_("acceptTextures", false);
        }
        if (this.f_233800_ != null) {
            ChatPreview.f_233819_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_233800_).result().ifPresent(p_233812_ -> $$0.m_128365_("chatPreview", (Tag)p_233812_));
        }
        return $$0;
    }

    public ServerPackStatus m_105387_() {
        return this.f_105371_;
    }

    public void m_105379_(ServerPackStatus p_105380_) {
        this.f_105371_ = p_105380_;
    }

    public static ServerData m_105385_(CompoundTag p_105386_) {
        ServerData $$1 = new ServerData(p_105386_.m_128461_("name"), p_105386_.m_128461_("ip"), false);
        if (p_105386_.m_128425_("icon", 8)) {
            $$1.m_105383_(p_105386_.m_128461_("icon"));
        }
        if (p_105386_.m_128425_("acceptTextures", 1)) {
            if (p_105386_.m_128471_("acceptTextures")) {
                $$1.m_105379_(ServerPackStatus.ENABLED);
            } else {
                $$1.m_105379_(ServerPackStatus.DISABLED);
            }
        } else {
            $$1.m_105379_(ServerPackStatus.PROMPT);
        }
        if (p_105386_.m_128425_("chatPreview", 10)) {
            ChatPreview.f_233819_.parse((DynamicOps)NbtOps.f_128958_, (Object)p_105386_.m_128469_("chatPreview")).resultOrPartial(arg_0 -> ((Logger)f_233799_).error(arg_0)).ifPresent(p_233807_ -> {
                p_233806_.f_233800_ = p_233807_;
            });
        }
        return $$1;
    }

    @Nullable
    public String m_105388_() {
        return this.f_105372_;
    }

    public static String m_233808_(String p_233809_) throws ParseException {
        if (p_233809_.startsWith("data:image/png;base64,")) {
            return p_233809_.substring("data:image/png;base64,".length());
        }
        throw new ParseException("Unknown format", 0);
    }

    public void m_105383_(@Nullable String p_105384_) {
        this.f_105372_ = p_105384_;
    }

    public boolean m_105389_() {
        return this.f_105373_;
    }

    public void m_233813_(boolean p_233814_) {
        if (p_233814_ && this.f_233800_ == null) {
            this.f_233800_ = new ChatPreview(false, false);
        } else if (!p_233814_ && this.f_233800_ != null) {
            this.f_233800_ = null;
        }
    }

    @Nullable
    public ChatPreview m_233817_() {
        return this.f_233800_;
    }

    public void m_233815_(boolean p_233816_) {
        this.f_233801_ = p_233816_;
    }

    public boolean m_233818_() {
        return this.f_233801_ && this.f_233800_ != null;
    }

    public void m_242965_(boolean p_242972_) {
        this.f_242950_ = p_242972_;
    }

    public boolean m_242962_() {
        return this.f_242950_;
    }

    public void m_233803_(ServerData p_233804_) {
        this.f_105363_ = p_233804_.f_105363_;
        this.f_105362_ = p_233804_.f_105362_;
        this.f_105372_ = p_233804_.f_105372_;
    }

    public void m_105381_(ServerData p_105382_) {
        this.m_233803_(p_105382_);
        this.m_105379_(p_105382_.m_105387_());
        this.f_105373_ = p_105382_.f_105373_;
        this.f_233800_ = Util.m_214614_(p_105382_.f_233800_, ChatPreview::m_233835_);
        this.f_242950_ = p_105382_.f_242950_;
    }

    public static final class ServerPackStatus
    extends Enum<ServerPackStatus> {
        public static final /* enum */ ServerPackStatus ENABLED = new ServerPackStatus("enabled");
        public static final /* enum */ ServerPackStatus DISABLED = new ServerPackStatus("disabled");
        public static final /* enum */ ServerPackStatus PROMPT = new ServerPackStatus("prompt");
        private final Component f_105393_;
        private static final /* synthetic */ ServerPackStatus[] $VALUES;

        public static ServerPackStatus[] values() {
            return (ServerPackStatus[])$VALUES.clone();
        }

        public static ServerPackStatus valueOf(String p_105402_) {
            return Enum.valueOf(ServerPackStatus.class, p_105402_);
        }

        private ServerPackStatus(String p_105399_) {
            this.f_105393_ = Component.m_237115_("addServer.resourcePack." + p_105399_);
        }

        public Component m_105400_() {
            return this.f_105393_;
        }

        private static /* synthetic */ ServerPackStatus[] m_171809_() {
            return new ServerPackStatus[]{ENABLED, DISABLED, PROMPT};
        }

        static {
            $VALUES = ServerPackStatus.m_171809_();
        }
    }

    public static class ChatPreview {
        public static final Codec<ChatPreview> f_233819_ = RecordCodecBuilder.create(p_233828_ -> p_233828_.group((App)Codec.BOOL.optionalFieldOf("acknowledged", (Object)false).forGetter(p_233833_ -> p_233833_.f_233820_), (App)Codec.BOOL.optionalFieldOf("toastShown", (Object)false).forGetter(p_233830_ -> p_233830_.f_233821_)).apply((Applicative)p_233828_, ChatPreview::new));
        private boolean f_233820_;
        private boolean f_233821_;

        ChatPreview(boolean p_233824_, boolean p_233825_) {
            this.f_233820_ = p_233824_;
            this.f_233821_ = p_233825_;
        }

        public void m_233826_() {
            this.f_233820_ = true;
        }

        public boolean m_233831_() {
            if (!this.f_233821_) {
                this.f_233821_ = true;
                return true;
            }
            return false;
        }

        public boolean m_233834_() {
            return this.f_233820_;
        }

        private ChatPreview m_233835_() {
            return new ChatPreview(this.f_233820_, this.f_233821_);
        }
    }
}

