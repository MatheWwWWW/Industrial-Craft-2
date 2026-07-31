/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.hash.Hashing
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.server.LanServer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class ServerSelectionList
extends ObjectSelectionList<Entry> {
    static final Logger f_99756_ = LogUtils.getLogger();
    static final ThreadPoolExecutor f_99757_ = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(f_99756_)).build());
    static final ResourceLocation f_99758_ = new ResourceLocation("textures/misc/unknown_server.png");
    static final ResourceLocation f_99759_ = new ResourceLocation("textures/gui/server_selection.png");
    static final Component f_99760_ = Component.m_237115_("lanServer.scanning");
    static final Component f_99761_ = Component.m_237115_("multiplayer.status.cannot_resolve").m_130940_(ChatFormatting.DARK_RED);
    static final Component f_99762_ = Component.m_237115_("multiplayer.status.cannot_connect").m_130940_(ChatFormatting.DARK_RED);
    static final Component f_99763_ = Component.m_237115_("multiplayer.status.incompatible");
    static final Component f_99764_ = Component.m_237115_("multiplayer.status.no_connection");
    static final Component f_99765_ = Component.m_237115_("multiplayer.status.pinging");
    private final JoinMultiplayerScreen f_99766_;
    private final List<OnlineServerEntry> f_99767_ = Lists.newArrayList();
    private final Entry f_99768_ = new LANHeader();
    private final List<NetworkServerEntry> f_99755_ = Lists.newArrayList();

    public ServerSelectionList(JoinMultiplayerScreen p_99771_, Minecraft p_99772_, int p_99773_, int p_99774_, int p_99775_, int p_99776_, int p_99777_) {
        super(p_99772_, p_99773_, p_99774_, p_99775_, p_99776_, p_99777_);
        this.f_99766_ = p_99771_;
    }

    private void m_99780_() {
        this.m_93516_();
        this.f_99767_.forEach(p_169979_ -> this.m_7085_(p_169979_));
        this.m_7085_(this.f_99768_);
        this.f_99755_.forEach(p_169976_ -> this.m_7085_(p_169976_));
    }

    @Override
    public void m_6987_(@Nullable Entry p_99790_) {
        super.m_6987_(p_99790_);
        this.f_99766_.m_99730_();
    }

    @Override
    public boolean m_7933_(int p_99782_, int p_99783_, int p_99784_) {
        Entry $$3 = (Entry)this.m_93511_();
        return $$3 != null && $$3.m_7933_(p_99782_, p_99783_, p_99784_) || super.m_7933_(p_99782_, p_99783_, p_99784_);
    }

    @Override
    protected void m_6778_(AbstractSelectionList.SelectionDirection p_99788_) {
        this.m_93464_(p_99788_, p_169973_ -> !(p_169973_ instanceof LANHeader));
    }

    public void m_99797_(ServerList p_99798_) {
        this.f_99767_.clear();
        for (int $$1 = 0; $$1 < p_99798_.m_105445_(); ++$$1) {
            this.f_99767_.add(new OnlineServerEntry(this.f_99766_, p_99798_.m_105432_($$1)));
        }
        this.m_99780_();
    }

    public void m_99799_(List<LanServer> p_99800_) {
        this.f_99755_.clear();
        for (LanServer $$1 : p_99800_) {
            this.f_99755_.add(new NetworkServerEntry(this.f_99766_, $$1));
        }
        this.m_99780_();
    }

    @Override
    protected int m_5756_() {
        return super.m_5756_() + 30;
    }

    @Override
    public int m_5759_() {
        return super.m_5759_() + 85;
    }

    @Override
    protected boolean m_5694_() {
        return this.f_99766_.m_7222_() == this;
    }

    public static class LANHeader
    extends Entry {
        private final Minecraft f_99815_ = Minecraft.m_91087_();

        @Override
        public void m_6311_(PoseStack p_99818_, int p_99819_, int p_99820_, int p_99821_, int p_99822_, int p_99823_, int p_99824_, int p_99825_, boolean p_99826_, float p_99827_) {
            int $$10 = p_99820_ + p_99823_ / 2 - this.f_99815_.f_91062_.f_92710_ / 2;
            this.f_99815_.f_91062_.m_92889_(p_99818_, f_99760_, this.f_99815_.f_91080_.f_96543_ / 2 - this.f_99815_.f_91062_.m_92852_(f_99760_) / 2, $$10, 0xFFFFFF);
            String $$11 = LoadingDotsText.m_232744_(Util.m_137550_());
            this.f_99815_.f_91062_.m_92883_(p_99818_, $$11, this.f_99815_.f_91080_.f_96543_ / 2 - this.f_99815_.f_91062_.m_92895_($$11) / 2, $$10 + this.f_99815_.f_91062_.f_92710_, 0x808080);
        }

        @Override
        public Component m_142172_() {
            return CommonComponents.f_237098_;
        }
    }

    public static abstract class Entry
    extends ObjectSelectionList.Entry<Entry> {
    }

    public class OnlineServerEntry
    extends Entry {
        private static final int f_169983_ = 32;
        private static final int f_169984_ = 32;
        private static final int f_169985_ = 0;
        private static final int f_169986_ = 32;
        private static final int f_169987_ = 64;
        private static final int f_169988_ = 96;
        private static final int f_169989_ = 0;
        private static final int f_169990_ = 32;
        private final JoinMultiplayerScreen f_99855_;
        private final Minecraft f_99856_;
        private final ServerData f_99857_;
        private final ResourceLocation f_99858_;
        @Nullable
        private String f_99859_;
        @Nullable
        private DynamicTexture f_99860_;
        private long f_99861_;

        protected OnlineServerEntry(JoinMultiplayerScreen p_99864_, ServerData p_99865_) {
            this.f_99855_ = p_99864_;
            this.f_99857_ = p_99865_;
            this.f_99856_ = Minecraft.m_91087_();
            this.f_99858_ = new ResourceLocation("servers/" + Hashing.sha1().hashUnencodedChars((CharSequence)p_99865_.f_105363_) + "/icon");
            AbstractTexture $$3 = this.f_99856_.m_91097_().m_174786_(this.f_99858_, MissingTextureAtlasSprite.m_118080_());
            if ($$3 != MissingTextureAtlasSprite.m_118080_() && $$3 instanceof DynamicTexture) {
                this.f_99860_ = (DynamicTexture)$$3;
            }
        }

        @Override
        public void m_6311_(PoseStack p_99879_, int p_99880_, int p_99881_, int p_99882_, int p_99883_, int p_99884_, int p_99885_, int p_99886_, boolean p_99887_, float p_99888_) {
            List<Component> $$31;
            Component $$30;
            int $$29;
            if (!this.f_99857_.f_105369_) {
                this.f_99857_.f_105369_ = true;
                this.f_99857_.f_105366_ = -2L;
                this.f_99857_.f_105365_ = CommonComponents.f_237098_;
                this.f_99857_.f_105364_ = CommonComponents.f_237098_;
                f_99757_.submit(() -> {
                    try {
                        this.f_99855_.m_99731_().m_105459_(this.f_99857_, () -> this.f_99856_.execute(this::m_99866_));
                    }
                    catch (UnknownHostException $$0) {
                        this.f_99857_.f_105366_ = -1L;
                        this.f_99857_.f_105365_ = f_99761_;
                    }
                    catch (Exception $$1) {
                        this.f_99857_.f_105366_ = -1L;
                        this.f_99857_.f_105365_ = f_99762_;
                    }
                });
            }
            boolean $$10 = this.f_99857_.f_105367_ != SharedConstants.m_183709_().getProtocolVersion();
            this.f_99856_.f_91062_.m_92883_(p_99879_, this.f_99857_.f_105362_, p_99882_ + 32 + 3, p_99881_ + 1, 0xFFFFFF);
            List<FormattedCharSequence> $$11 = this.f_99856_.f_91062_.m_92923_(this.f_99857_.f_105365_, p_99883_ - 32 - 2);
            for (int $$12 = 0; $$12 < Math.min($$11.size(), 2); ++$$12) {
                this.f_99856_.f_91062_.m_92877_(p_99879_, $$11.get($$12), p_99882_ + 32 + 3, p_99881_ + 12 + this.f_99856_.f_91062_.f_92710_ * $$12, 0x808080);
            }
            Component $$13 = $$10 ? this.f_99857_.f_105368_.m_6881_().m_130940_(ChatFormatting.RED) : this.f_99857_.f_105364_;
            int $$14 = this.f_99856_.f_91062_.m_92852_($$13);
            this.f_99856_.f_91062_.m_92889_(p_99879_, $$13, p_99882_ + p_99883_ - $$14 - 15 - 2, p_99881_ + 1, 0x808080);
            int $$15 = 0;
            if ($$10) {
                int $$16 = 5;
                Component $$17 = f_99763_;
                List<Component> $$18 = this.f_99857_.f_105370_;
            } else if (this.f_99857_.f_105369_ && this.f_99857_.f_105366_ != -2L) {
                if (this.f_99857_.f_105366_ < 0L) {
                    int $$19 = 5;
                } else if (this.f_99857_.f_105366_ < 150L) {
                    boolean $$20 = false;
                } else if (this.f_99857_.f_105366_ < 300L) {
                    boolean $$21 = true;
                } else if (this.f_99857_.f_105366_ < 600L) {
                    int $$22 = 2;
                } else if (this.f_99857_.f_105366_ < 1000L) {
                    int $$23 = 3;
                } else {
                    int $$24 = 4;
                }
                if (this.f_99857_.f_105366_ < 0L) {
                    Component $$25 = f_99764_;
                    List $$26 = Collections.emptyList();
                } else {
                    MutableComponent $$27 = Component.m_237110_("multiplayer.status.ping", this.f_99857_.f_105366_);
                    List<Component> $$28 = this.f_99857_.f_105370_;
                }
            } else {
                $$15 = 1;
                $$29 = (int)(Util.m_137550_() / 100L + (long)(p_99880_ * 2) & 7L);
                if ($$29 > 4) {
                    $$29 = 8 - $$29;
                }
                $$30 = f_99765_;
                $$31 = Collections.emptyList();
            }
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_157456_(0, GuiComponent.f_93098_);
            GuiComponent.m_93133_(p_99879_, p_99882_ + p_99883_ - 15, p_99881_, $$15 * 10, 176 + $$29 * 8, 10, 8, 256, 256);
            String $$32 = this.f_99857_.m_105388_();
            if (!Objects.equals($$32, this.f_99859_)) {
                if (this.m_99896_($$32)) {
                    this.f_99859_ = $$32;
                } else {
                    this.f_99857_.m_105383_(null);
                    this.m_99866_();
                }
            }
            if (this.f_99860_ == null) {
                this.m_99889_(p_99879_, p_99882_, p_99881_, f_99758_);
            } else {
                this.m_99889_(p_99879_, p_99882_, p_99881_, this.f_99858_);
            }
            int $$33 = p_99885_ - p_99882_;
            int $$34 = p_99886_ - p_99881_;
            if ($$33 >= p_99883_ - 15 && $$33 <= p_99883_ - 5 && $$34 >= 0 && $$34 <= 8) {
                this.f_99855_.m_99707_(Collections.singletonList($$30));
            } else if ($$33 >= p_99883_ - $$14 - 15 - 2 && $$33 <= p_99883_ - 15 - 2 && $$34 >= 0 && $$34 <= 8) {
                this.f_99855_.m_99707_($$31);
            }
            if (this.f_99856_.f_91066_.m_231828_().m_231551_().booleanValue() || p_99887_) {
                RenderSystem.m_157456_(0, f_99759_);
                GuiComponent.m_93172_(p_99879_, p_99882_, p_99881_, p_99882_ + 32, p_99881_ + 32, -1601138544);
                RenderSystem.m_157427_(GameRenderer::m_172817_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                int $$35 = p_99885_ - p_99882_;
                int $$36 = p_99886_ - p_99881_;
                if (this.m_99899_()) {
                    if ($$35 < 32 && $$35 > 16) {
                        GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 0.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 0.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
                if (p_99880_ > 0) {
                    if ($$35 < 16 && $$36 < 16) {
                        GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 96.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 96.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
                if (p_99880_ < this.f_99855_.m_99732_().m_105445_() - 1) {
                    if ($$35 < 16 && $$36 > 16) {
                        GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 64.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        GuiComponent.m_93133_(p_99879_, p_99882_, p_99881_, 64.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
            }
        }

        public void m_99866_() {
            this.f_99855_.m_99732_().m_105442_();
        }

        protected void m_99889_(PoseStack p_99890_, int p_99891_, int p_99892_, ResourceLocation p_99893_) {
            RenderSystem.m_157456_(0, p_99893_);
            RenderSystem.m_69478_();
            GuiComponent.m_93133_(p_99890_, p_99891_, p_99892_, 0.0f, 0.0f, 32, 32, 32, 32);
            RenderSystem.m_69461_();
        }

        private boolean m_99899_() {
            return true;
        }

        private boolean m_99896_(@Nullable String p_99897_) {
            if (p_99897_ == null) {
                this.f_99856_.m_91097_().m_118513_(this.f_99858_);
                if (this.f_99860_ != null && this.f_99860_.m_117991_() != null) {
                    this.f_99860_.m_117991_().close();
                }
                this.f_99860_ = null;
            } else {
                try {
                    NativeImage $$1 = NativeImage.m_85060_(p_99897_);
                    Validate.validState(($$1.m_84982_() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                    Validate.validState(($$1.m_85084_() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels high", (Object[])new Object[0]);
                    if (this.f_99860_ == null) {
                        this.f_99860_ = new DynamicTexture($$1);
                    } else {
                        this.f_99860_.m_117988_($$1);
                        this.f_99860_.m_117985_();
                    }
                    this.f_99856_.m_91097_().m_118495_(this.f_99858_, this.f_99860_);
                }
                catch (Throwable $$2) {
                    f_99756_.error("Invalid icon for server {} ({})", new Object[]{this.f_99857_.f_105362_, this.f_99857_.f_105363_, $$2});
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean m_7933_(int p_99875_, int p_99876_, int p_99877_) {
            if (Screen.m_96638_()) {
                ServerSelectionList $$3 = this.f_99855_.f_99673_;
                int $$4 = $$3.m_6702_().indexOf(this);
                if ($$4 == -1) {
                    return true;
                }
                if (p_99875_ == 264 && $$4 < this.f_99855_.m_99732_().m_105445_() - 1 || p_99875_ == 265 && $$4 > 0) {
                    this.m_99871_($$4, p_99875_ == 264 ? $$4 + 1 : $$4 - 1);
                    return true;
                }
            }
            return super.m_7933_(p_99875_, p_99876_, p_99877_);
        }

        private void m_99871_(int p_99872_, int p_99873_) {
            this.f_99855_.m_99732_().m_105434_(p_99872_, p_99873_);
            this.f_99855_.f_99673_.m_99797_(this.f_99855_.m_99732_());
            Entry $$2 = (Entry)this.f_99855_.f_99673_.m_6702_().get(p_99873_);
            this.f_99855_.f_99673_.m_6987_($$2);
            ServerSelectionList.this.m_93498_($$2);
        }

        @Override
        public boolean m_6375_(double p_99868_, double p_99869_, int p_99870_) {
            double $$3 = p_99868_ - (double)ServerSelectionList.this.m_5747_();
            double $$4 = p_99869_ - (double)ServerSelectionList.this.m_7610_(ServerSelectionList.this.m_6702_().indexOf(this));
            if ($$3 <= 32.0) {
                if ($$3 < 32.0 && $$3 > 16.0 && this.m_99899_()) {
                    this.f_99855_.m_99700_(this);
                    this.f_99855_.m_99729_();
                    return true;
                }
                int $$5 = this.f_99855_.f_99673_.m_6702_().indexOf(this);
                if ($$3 < 16.0 && $$4 < 16.0 && $$5 > 0) {
                    this.m_99871_($$5, $$5 - 1);
                    return true;
                }
                if ($$3 < 16.0 && $$4 > 16.0 && $$5 < this.f_99855_.m_99732_().m_105445_() - 1) {
                    this.m_99871_($$5, $$5 + 1);
                    return true;
                }
            }
            this.f_99855_.m_99700_(this);
            if (Util.m_137550_() - this.f_99861_ < 250L) {
                this.f_99855_.m_99729_();
            }
            this.f_99861_ = Util.m_137550_();
            return false;
        }

        public ServerData m_99898_() {
            return this.f_99857_;
        }

        @Override
        public Component m_142172_() {
            return Component.m_237110_("narrator.select", this.f_99857_.f_105362_);
        }
    }

    public static class NetworkServerEntry
    extends Entry {
        private static final int f_169981_ = 32;
        private static final Component f_99830_ = Component.m_237115_("lanServer.title");
        private static final Component f_99831_ = Component.m_237115_("selectServer.hiddenAddress");
        private final JoinMultiplayerScreen f_99832_;
        protected final Minecraft f_99828_;
        protected final LanServer f_99829_;
        private long f_99833_;

        protected NetworkServerEntry(JoinMultiplayerScreen p_99836_, LanServer p_99837_) {
            this.f_99832_ = p_99836_;
            this.f_99829_ = p_99837_;
            this.f_99828_ = Minecraft.m_91087_();
        }

        @Override
        public void m_6311_(PoseStack p_99844_, int p_99845_, int p_99846_, int p_99847_, int p_99848_, int p_99849_, int p_99850_, int p_99851_, boolean p_99852_, float p_99853_) {
            this.f_99828_.f_91062_.m_92889_(p_99844_, f_99830_, p_99847_ + 32 + 3, p_99846_ + 1, 0xFFFFFF);
            this.f_99828_.f_91062_.m_92883_(p_99844_, this.f_99829_.m_120078_(), p_99847_ + 32 + 3, p_99846_ + 12, 0x808080);
            if (this.f_99828_.f_91066_.f_92124_) {
                this.f_99828_.f_91062_.m_92889_(p_99844_, f_99831_, p_99847_ + 32 + 3, p_99846_ + 12 + 11, 0x303030);
            } else {
                this.f_99828_.f_91062_.m_92883_(p_99844_, this.f_99829_.m_120079_(), p_99847_ + 32 + 3, p_99846_ + 12 + 11, 0x303030);
            }
        }

        @Override
        public boolean m_6375_(double p_99840_, double p_99841_, int p_99842_) {
            this.f_99832_.m_99700_(this);
            if (Util.m_137550_() - this.f_99833_ < 250L) {
                this.f_99832_.m_99729_();
            }
            this.f_99833_ = Util.m_137550_();
            return false;
        }

        public LanServer m_99838_() {
            return this.f_99829_;
        }

        @Override
        public Component m_142172_() {
            return Component.m_237110_("narrator.select", Component.m_237119_().m_7220_(f_99830_).m_130946_(" ").m_130946_(this.f_99829_.m_120078_()));
        }
    }
}

