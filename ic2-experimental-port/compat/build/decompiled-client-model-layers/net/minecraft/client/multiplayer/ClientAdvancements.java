/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class ClientAdvancements {
    private static final Logger f_104387_ = LogUtils.getLogger();
    private final Minecraft f_104388_;
    private final AdvancementList f_104389_ = new AdvancementList();
    private final Map<Advancement, AdvancementProgress> f_104390_ = Maps.newHashMap();
    @Nullable
    private Listener f_104391_;
    @Nullable
    private Advancement f_104392_;

    public ClientAdvancements(Minecraft p_104395_) {
        this.f_104388_ = p_104395_;
    }

    public void m_104399_(ClientboundUpdateAdvancementsPacket p_104400_) {
        if (p_104400_.m_133575_()) {
            this.f_104389_.m_139332_();
            this.f_104390_.clear();
        }
        this.f_104389_.m_139335_(p_104400_.m_133573_());
        this.f_104389_.m_139333_(p_104400_.m_133570_());
        for (Map.Entry<ResourceLocation, AdvancementProgress> $$1 : p_104400_.m_133574_().entrySet()) {
            Advancement $$2 = this.f_104389_.m_139337_($$1.getKey());
            if ($$2 != null) {
                AdvancementProgress $$3 = $$1.getValue();
                $$3.m_8198_($$2.m_138325_(), $$2.m_138329_());
                this.f_104390_.put($$2, $$3);
                if (this.f_104391_ != null) {
                    this.f_104391_.m_7922_($$2, $$3);
                }
                if (p_104400_.m_133575_() || !$$3.m_8193_() || $$2.m_138320_() == null || !$$2.m_138320_().m_14995_()) continue;
                this.f_104388_.m_91300_().m_94922_(new AdvancementToast($$2));
                continue;
            }
            f_104387_.warn("Server informed client about progress for unknown advancement {}", (Object)$$1.getKey());
        }
    }

    public AdvancementList m_104396_() {
        return this.f_104389_;
    }

    public void m_104401_(@Nullable Advancement p_104402_, boolean p_104403_) {
        ClientPacketListener $$2 = this.f_104388_.m_91403_();
        if ($$2 != null && p_104402_ != null && p_104403_) {
            $$2.m_104955_(ServerboundSeenAdvancementsPacket.m_134442_(p_104402_));
        }
        if (this.f_104392_ != p_104402_) {
            this.f_104392_ = p_104402_;
            if (this.f_104391_ != null) {
                this.f_104391_.m_6896_(p_104402_);
            }
        }
    }

    public void m_104397_(@Nullable Listener p_104398_) {
        this.f_104391_ = p_104398_;
        this.f_104389_.m_139341_(p_104398_);
        if (p_104398_ != null) {
            for (Map.Entry<Advancement, AdvancementProgress> $$1 : this.f_104390_.entrySet()) {
                p_104398_.m_7922_($$1.getKey(), $$1.getValue());
            }
            p_104398_.m_6896_(this.f_104392_);
        }
    }

    public static interface Listener
    extends AdvancementList.Listener {
        public void m_7922_(Advancement var1, AdvancementProgress var2);

        public void m_6896_(@Nullable Advancement var1);
    }
}

