/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ResultConsumer
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.mojang.brigadier.ResultConsumer;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class BaseCommandBlock
implements CommandSource {
    private static final SimpleDateFormat f_45397_ = new SimpleDateFormat("HH:mm:ss");
    private static final Component f_45398_ = Component.m_237113_("@");
    private long f_45399_ = -1L;
    private boolean f_45400_ = true;
    private int f_45401_;
    private boolean f_45402_ = true;
    @Nullable
    private Component f_45403_;
    private String f_45404_ = "";
    private Component f_45405_ = f_45398_;

    public int m_45436_() {
        return this.f_45401_;
    }

    public void m_45410_(int p_45411_) {
        this.f_45401_ = p_45411_;
    }

    public Component m_45437_() {
        return this.f_45403_ == null ? CommonComponents.f_237098_ : this.f_45403_;
    }

    public CompoundTag m_45421_(CompoundTag p_45422_) {
        p_45422_.m_128359_("Command", this.f_45404_);
        p_45422_.m_128405_("SuccessCount", this.f_45401_);
        p_45422_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_45405_));
        p_45422_.m_128379_("TrackOutput", this.f_45402_);
        if (this.f_45403_ != null && this.f_45402_) {
            p_45422_.m_128359_("LastOutput", Component.Serializer.m_130703_(this.f_45403_));
        }
        p_45422_.m_128379_("UpdateLastExecution", this.f_45400_);
        if (this.f_45400_ && this.f_45399_ > 0L) {
            p_45422_.m_128356_("LastExecution", this.f_45399_);
        }
        return p_45422_;
    }

    public void m_45431_(CompoundTag p_45432_) {
        this.f_45404_ = p_45432_.m_128461_("Command");
        this.f_45401_ = p_45432_.m_128451_("SuccessCount");
        if (p_45432_.m_128425_("CustomName", 8)) {
            this.m_45423_(Component.Serializer.m_130701_(p_45432_.m_128461_("CustomName")));
        }
        if (p_45432_.m_128425_("TrackOutput", 1)) {
            this.f_45402_ = p_45432_.m_128471_("TrackOutput");
        }
        if (p_45432_.m_128425_("LastOutput", 8) && this.f_45402_) {
            try {
                this.f_45403_ = Component.Serializer.m_130701_(p_45432_.m_128461_("LastOutput"));
            }
            catch (Throwable $$1) {
                this.f_45403_ = Component.m_237113_($$1.getMessage());
            }
        } else {
            this.f_45403_ = null;
        }
        if (p_45432_.m_128441_("UpdateLastExecution")) {
            this.f_45400_ = p_45432_.m_128471_("UpdateLastExecution");
        }
        this.f_45399_ = this.f_45400_ && p_45432_.m_128441_("LastExecution") ? p_45432_.m_128454_("LastExecution") : -1L;
    }

    public void m_6590_(String p_45420_) {
        this.f_45404_ = p_45420_;
        this.f_45401_ = 0;
    }

    public String m_45438_() {
        return this.f_45404_;
    }

    public boolean m_45414_(Level p_45415_) {
        if (p_45415_.f_46443_ || p_45415_.m_46467_() == this.f_45399_) {
            return false;
        }
        if ("Searge".equalsIgnoreCase(this.f_45404_)) {
            this.f_45403_ = Component.m_237113_("#itzlipofutzli");
            this.f_45401_ = 1;
            return true;
        }
        this.f_45401_ = 0;
        MinecraftServer $$1 = this.m_5991_().m_7654_();
        if ($$1.m_6993_() && !StringUtil.m_14408_(this.f_45404_)) {
            try {
                this.f_45403_ = null;
                CommandSourceStack $$2 = this.m_6712_().m_81334_((ResultConsumer<CommandSourceStack>)((ResultConsumer)(p_45417_, p_45418_, p_45419_) -> {
                    if (p_45418_) {
                        ++this.f_45401_;
                    }
                }));
                $$1.m_129892_().m_230957_($$2, this.f_45404_);
            }
            catch (Throwable $$3) {
                CrashReport $$4 = CrashReport.m_127521_($$3, "Executing command block");
                CrashReportCategory $$5 = $$4.m_127514_("Command to be executed");
                $$5.m_128165_("Command", this::m_45438_);
                $$5.m_128165_("Name", () -> this.m_45439_().getString());
                throw new ReportedException($$4);
            }
        }
        this.f_45399_ = this.f_45400_ ? p_45415_.m_46467_() : -1L;
        return true;
    }

    public Component m_45439_() {
        return this.f_45405_;
    }

    public void m_45423_(@Nullable Component p_45424_) {
        this.f_45405_ = p_45424_ != null ? p_45424_ : f_45398_;
    }

    @Override
    public void m_213846_(Component p_220330_) {
        if (this.f_45402_) {
            this.f_45403_ = Component.m_237113_("[" + f_45397_.format(new Date()) + "] ").m_7220_(p_220330_);
            this.m_7368_();
        }
    }

    public abstract ServerLevel m_5991_();

    public abstract void m_7368_();

    public void m_45433_(@Nullable Component p_45434_) {
        this.f_45403_ = p_45434_;
    }

    public void m_45428_(boolean p_45429_) {
        this.f_45402_ = p_45429_;
    }

    public boolean m_45440_() {
        return this.f_45402_;
    }

    public InteractionResult m_45412_(Player p_45413_) {
        if (!p_45413_.m_36337_()) {
            return InteractionResult.PASS;
        }
        if (p_45413_.m_20193_().f_46443_) {
            p_45413_.m_7907_(this);
        }
        return InteractionResult.m_19078_(p_45413_.f_19853_.f_46443_);
    }

    public abstract Vec3 m_6607_();

    public abstract CommandSourceStack m_6712_();

    @Override
    public boolean m_6999_() {
        return this.m_5991_().m_46469_().m_46207_(GameRules.f_46144_) && this.f_45402_;
    }

    @Override
    public boolean m_7028_() {
        return this.f_45402_;
    }

    @Override
    public boolean m_6102_() {
        return this.m_5991_().m_46469_().m_46207_(GameRules.f_46138_);
    }
}

