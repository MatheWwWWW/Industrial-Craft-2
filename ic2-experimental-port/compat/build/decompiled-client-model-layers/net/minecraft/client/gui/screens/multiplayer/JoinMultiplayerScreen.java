/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.multiplayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DirectJoinServerScreen;
import net.minecraft.client.gui.screens.EditServerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.ServerStatusPinger;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.LanServer;
import net.minecraft.client.server.LanServerDetection;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.slf4j.Logger;

public class JoinMultiplayerScreen
extends Screen {
    private static final Logger f_99674_ = LogUtils.getLogger();
    private final ServerStatusPinger f_99675_ = new ServerStatusPinger();
    private final Screen f_99676_;
    protected ServerSelectionList f_99673_;
    private ServerList f_99677_;
    private Button f_99678_;
    private Button f_99679_;
    private Button f_99680_;
    @Nullable
    private List<Component> f_99681_;
    private ServerData f_99682_;
    private LanServerDetection.LanServerList f_99683_;
    @Nullable
    private LanServerDetection.LanServerDetector f_99684_;
    private boolean f_99685_;

    public JoinMultiplayerScreen(Screen p_99688_) {
        super(Component.m_237115_("multiplayer.title"));
        this.f_99676_ = p_99688_;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_96541_.f_91068_.m_90926_(true);
        if (this.f_99685_) {
            this.f_99673_.m_93437_(this.f_96543_, this.f_96544_, 32, this.f_96544_ - 64);
        } else {
            this.f_99685_ = true;
            this.f_99677_ = new ServerList(this.f_96541_);
            this.f_99677_.m_105431_();
            this.f_99683_ = new LanServerDetection.LanServerList();
            try {
                this.f_99684_ = new LanServerDetection.LanServerDetector(this.f_99683_);
                this.f_99684_.start();
            }
            catch (Exception $$0) {
                f_99674_.warn("Unable to start LAN server detection: {}", (Object)$$0.getMessage());
            }
            this.f_99673_ = new ServerSelectionList(this, this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 64, 36);
            this.f_99673_.m_99797_(this.f_99677_);
        }
        this.m_7787_(this.f_99673_);
        this.f_99679_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 52, 100, 20, Component.m_237115_("selectServer.select"), p_99728_ -> this.m_99729_()));
        this.m_142416_(new Button(this.f_96543_ / 2 - 50, this.f_96544_ - 52, 100, 20, Component.m_237115_("selectServer.direct"), p_99724_ -> {
            this.f_99682_ = new ServerData(I18n.m_118938_("selectServer.defaultName", new Object[0]), "", false);
            this.f_96541_.m_91152_(new DirectJoinServerScreen(this, this::m_99725_, this.f_99682_));
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4 + 50, this.f_96544_ - 52, 100, 20, Component.m_237115_("selectServer.add"), p_99720_ -> {
            this.f_99682_ = new ServerData(I18n.m_118938_("selectServer.defaultName", new Object[0]), "", false);
            this.f_96541_.m_91152_(new EditServerScreen(this, this::m_99721_, this.f_99682_));
        }));
        this.f_99678_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 28, 70, 20, Component.m_237115_("selectServer.edit"), p_99715_ -> {
            ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.f_99673_.m_93511_();
            if ($$1 instanceof ServerSelectionList.OnlineServerEntry) {
                ServerData $$2 = ((ServerSelectionList.OnlineServerEntry)$$1).m_99898_();
                this.f_99682_ = new ServerData($$2.f_105362_, $$2.f_105363_, false);
                this.f_99682_.m_105381_($$2);
                this.f_96541_.m_91152_(new EditServerScreen(this, this::m_99716_, this.f_99682_));
            }
        }));
        this.f_99680_ = this.m_142416_(new Button(this.f_96543_ / 2 - 74, this.f_96544_ - 28, 70, 20, Component.m_237115_("selectServer.delete"), p_99710_ -> {
            String $$2;
            ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.f_99673_.m_93511_();
            if ($$1 instanceof ServerSelectionList.OnlineServerEntry && ($$2 = ((ServerSelectionList.OnlineServerEntry)$$1).m_99898_().f_105362_) != null) {
                MutableComponent $$3 = Component.m_237115_("selectServer.deleteQuestion");
                MutableComponent $$4 = Component.m_237110_("selectServer.deleteWarning", $$2);
                MutableComponent $$5 = Component.m_237115_("selectServer.deleteButton");
                Component $$6 = CommonComponents.f_130656_;
                this.f_96541_.m_91152_(new ConfirmScreen(this::m_99711_, $$3, $$4, $$5, $$6));
            }
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 28, 70, 20, Component.m_237115_("selectServer.refresh"), p_99706_ -> this.m_99733_()));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4 + 76, this.f_96544_ - 28, 75, 20, CommonComponents.f_130656_, p_99699_ -> this.f_96541_.m_91152_(this.f_99676_)));
        this.m_99730_();
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        if (this.f_99683_.m_120095_()) {
            List<LanServer> $$0 = this.f_99683_.m_120100_();
            this.f_99683_.m_120099_();
            this.f_99673_.m_99799_($$0);
        }
        this.f_99675_.m_105453_();
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
        if (this.f_99684_ != null) {
            this.f_99684_.interrupt();
            this.f_99684_ = null;
        }
        this.f_99675_.m_105465_();
    }

    private void m_99733_() {
        this.f_96541_.m_91152_(new JoinMultiplayerScreen(this.f_99676_));
    }

    private void m_99711_(boolean p_99712_) {
        ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.f_99673_.m_93511_();
        if (p_99712_ && $$1 instanceof ServerSelectionList.OnlineServerEntry) {
            this.f_99677_.m_105440_(((ServerSelectionList.OnlineServerEntry)$$1).m_99898_());
            this.f_99677_.m_105442_();
            this.f_99673_.m_6987_((ServerSelectionList.Entry)null);
            this.f_99673_.m_99797_(this.f_99677_);
        }
        this.f_96541_.m_91152_(this);
    }

    private void m_99716_(boolean p_99717_) {
        ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.f_99673_.m_93511_();
        if (p_99717_ && $$1 instanceof ServerSelectionList.OnlineServerEntry) {
            ServerData $$2 = ((ServerSelectionList.OnlineServerEntry)$$1).m_99898_();
            $$2.f_105362_ = this.f_99682_.f_105362_;
            $$2.f_105363_ = this.f_99682_.f_105363_;
            $$2.m_105381_(this.f_99682_);
            this.f_99677_.m_105442_();
            this.f_99673_.m_99797_(this.f_99677_);
        }
        this.f_96541_.m_91152_(this);
    }

    private void m_99721_(boolean p_99722_) {
        if (p_99722_) {
            ServerData $$1 = this.f_99677_.m_233847_(this.f_99682_.f_105363_);
            if ($$1 != null) {
                $$1.m_233803_(this.f_99682_);
                this.f_99677_.m_105442_();
            } else {
                this.f_99677_.m_233842_(this.f_99682_, false);
                this.f_99677_.m_105442_();
            }
            this.f_99673_.m_6987_((ServerSelectionList.Entry)null);
            this.f_99673_.m_99797_(this.f_99677_);
        }
        this.f_96541_.m_91152_(this);
    }

    private void m_99725_(boolean p_99726_) {
        if (p_99726_) {
            ServerData $$1 = this.f_99677_.m_233845_(this.f_99682_.f_105363_);
            if ($$1 == null) {
                this.f_99677_.m_233842_(this.f_99682_, true);
                this.f_99677_.m_105442_();
                this.m_99702_(this.f_99682_);
            } else {
                this.m_99702_($$1);
            }
        } else {
            this.f_96541_.m_91152_(this);
        }
    }

    @Override
    public boolean m_7933_(int p_99690_, int p_99691_, int p_99692_) {
        if (super.m_7933_(p_99690_, p_99691_, p_99692_)) {
            return true;
        }
        if (p_99690_ == 294) {
            this.m_99733_();
            return true;
        }
        if (this.f_99673_.m_93511_() != null) {
            if (p_99690_ == 257 || p_99690_ == 335) {
                this.m_99729_();
                return true;
            }
            return this.f_99673_.m_7933_(p_99690_, p_99691_, p_99692_);
        }
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_99694_, int p_99695_, int p_99696_, float p_99697_) {
        this.f_99681_ = null;
        this.m_7333_(p_99694_);
        this.f_99673_.m_6305_(p_99694_, p_99695_, p_99696_, p_99697_);
        JoinMultiplayerScreen.m_93215_(p_99694_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 0xFFFFFF);
        super.m_6305_(p_99694_, p_99695_, p_99696_, p_99697_);
        if (this.f_99681_ != null) {
            this.m_96597_(p_99694_, this.f_99681_, p_99695_, p_99696_);
        }
    }

    public void m_99729_() {
        ServerSelectionList.Entry $$0 = (ServerSelectionList.Entry)this.f_99673_.m_93511_();
        if ($$0 instanceof ServerSelectionList.OnlineServerEntry) {
            this.m_99702_(((ServerSelectionList.OnlineServerEntry)$$0).m_99898_());
        } else if ($$0 instanceof ServerSelectionList.NetworkServerEntry) {
            LanServer $$1 = ((ServerSelectionList.NetworkServerEntry)$$0).m_99838_();
            this.m_99702_(new ServerData($$1.m_120078_(), $$1.m_120079_(), true));
        }
    }

    private void m_99702_(ServerData p_99703_) {
        ConnectScreen.m_169267_(this, this.f_96541_, ServerAddress.m_171864_(p_99703_.f_105363_), p_99703_);
    }

    public void m_99700_(ServerSelectionList.Entry p_99701_) {
        this.f_99673_.m_6987_(p_99701_);
        this.m_99730_();
    }

    protected void m_99730_() {
        this.f_99679_.f_93623_ = false;
        this.f_99678_.f_93623_ = false;
        this.f_99680_.f_93623_ = false;
        ServerSelectionList.Entry $$0 = (ServerSelectionList.Entry)this.f_99673_.m_93511_();
        if ($$0 != null && !($$0 instanceof ServerSelectionList.LANHeader)) {
            this.f_99679_.f_93623_ = true;
            if ($$0 instanceof ServerSelectionList.OnlineServerEntry) {
                this.f_99678_.f_93623_ = true;
                this.f_99680_.f_93623_ = true;
            }
        }
    }

    public ServerStatusPinger m_99731_() {
        return this.f_99675_;
    }

    public void m_99707_(List<Component> p_99708_) {
        this.f_99681_ = p_99708_;
    }

    public ServerList m_99732_() {
        return this.f_99677_;
    }
}

