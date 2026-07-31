/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.ClipboardManager;
import com.mojang.blaze3d.platform.InputConstants;
import java.text.MessageFormat;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.NativeModuleLister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class KeyboardHandler {
    public static final int f_167812_ = 10000;
    private final Minecraft f_90867_;
    private boolean f_90868_;
    private final ClipboardManager f_90869_ = new ClipboardManager();
    private long f_90870_ = -1L;
    private long f_90871_ = -1L;
    private long f_90872_ = -1L;
    private boolean f_90873_;

    public KeyboardHandler(Minecraft p_90875_) {
        this.f_90867_ = p_90875_;
    }

    private boolean m_167813_(int p_167814_) {
        switch (p_167814_) {
            case 69: {
                this.f_90867_.f_90978_ = !this.f_90867_.f_90978_;
                this.m_167837_("ChunkPath: {0}", this.f_90867_.f_90978_ ? "shown" : "hidden");
                return true;
            }
            case 76: {
                this.f_90867_.f_90980_ = !this.f_90867_.f_90980_;
                this.m_167837_("SmartCull: {0}", this.f_90867_.f_90980_ ? "enabled" : "disabled");
                return true;
            }
            case 85: {
                if (Screen.m_96638_()) {
                    this.f_90867_.f_91060_.m_173019_();
                    this.m_167837_("Killed frustum", new Object[0]);
                } else {
                    this.f_90867_.f_91060_.m_173018_();
                    this.m_167837_("Captured frustum", new Object[0]);
                }
                return true;
            }
            case 86: {
                this.f_90867_.f_90979_ = !this.f_90867_.f_90979_;
                this.m_167837_("ChunkVisibility: {0}", this.f_90867_.f_90979_ ? "enabled" : "disabled");
                return true;
            }
            case 87: {
                this.f_90867_.f_167842_ = !this.f_90867_.f_167842_;
                this.m_167837_("WireFrame: {0}", this.f_90867_.f_167842_ ? "enabled" : "disabled");
                return true;
            }
        }
        return false;
    }

    private void m_167824_(ChatFormatting p_167825_, Component p_167826_) {
        this.f_90867_.f_91065_.m_93076_().m_93785_(Component.m_237119_().m_7220_(Component.m_237115_("debug.prefix").m_130944_(p_167825_, ChatFormatting.BOLD)).m_130946_(" ").m_7220_(p_167826_));
    }

    private void m_167822_(Component p_167823_) {
        this.m_167824_(ChatFormatting.YELLOW, p_167823_);
    }

    private void m_90913_(String p_90914_, Object ... p_90915_) {
        this.m_167822_(Component.m_237110_(p_90914_, p_90915_));
    }

    private void m_90948_(String p_90949_, Object ... p_90950_) {
        this.m_167824_(ChatFormatting.RED, Component.m_237110_(p_90949_, p_90950_));
    }

    private void m_167837_(String p_167838_, Object ... p_167839_) {
        this.m_167822_(Component.m_237113_(MessageFormat.format(p_167838_, p_167839_)));
    }

    private boolean m_90932_(int p_90933_) {
        if (this.f_90870_ > 0L && this.f_90870_ < Util.m_137550_() - 100L) {
            return true;
        }
        switch (p_90933_) {
            case 65: {
                this.f_90867_.f_91060_.m_109818_();
                this.m_90913_("debug.reload_chunks.message", new Object[0]);
                return true;
            }
            case 66: {
                boolean $$1 = !this.f_90867_.m_91290_().m_114377_();
                this.f_90867_.m_91290_().m_114473_($$1);
                this.m_90913_($$1 ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off", new Object[0]);
                return true;
            }
            case 68: {
                if (this.f_90867_.f_91065_ != null) {
                    this.f_90867_.f_91065_.m_93076_().m_93795_(false);
                }
                return true;
            }
            case 71: {
                boolean $$2 = this.f_90867_.f_91064_.m_113506_();
                this.m_90913_($$2 ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off", new Object[0]);
                return true;
            }
            case 72: {
                this.f_90867_.f_91066_.f_92125_ = !this.f_90867_.f_91066_.f_92125_;
                this.m_90913_(this.f_90867_.f_91066_.f_92125_ ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off", new Object[0]);
                this.f_90867_.f_91066_.m_92169_();
                return true;
            }
            case 73: {
                if (!this.f_90867_.f_91074_.m_36330_()) {
                    this.m_90928_(this.f_90867_.f_91074_.m_20310_(2), !Screen.m_96638_());
                }
                return true;
            }
            case 78: {
                if (!this.f_90867_.f_91074_.m_20310_(2)) {
                    this.m_90913_("debug.creative_spectator.error", new Object[0]);
                } else if (!this.f_90867_.f_91074_.m_5833_()) {
                    this.f_90867_.f_91074_.m_242614_("gamemode spectator");
                } else {
                    this.f_90867_.f_91074_.m_242614_("gamemode " + ((GameType)((Object)MoreObjects.firstNonNull((Object)((Object)this.f_90867_.f_91072_.m_105294_()), (Object)((Object)GameType.CREATIVE)))).m_46405_());
                }
                return true;
            }
            case 293: {
                if (!this.f_90867_.f_91074_.m_20310_(2)) {
                    this.m_90913_("debug.gamemodes.error", new Object[0]);
                } else {
                    this.f_90867_.m_91152_(new GameModeSwitcherScreen());
                }
                return true;
            }
            case 80: {
                this.f_90867_.f_91066_.f_92126_ = !this.f_90867_.f_91066_.f_92126_;
                this.f_90867_.f_91066_.m_92169_();
                this.m_90913_(this.f_90867_.f_91066_.f_92126_ ? "debug.pause_focus.on" : "debug.pause_focus.off", new Object[0]);
                return true;
            }
            case 81: {
                this.m_90913_("debug.help.message", new Object[0]);
                ChatComponent $$3 = this.f_90867_.f_91065_.m_93076_();
                $$3.m_93785_(Component.m_237115_("debug.reload_chunks.help"));
                $$3.m_93785_(Component.m_237115_("debug.show_hitboxes.help"));
                $$3.m_93785_(Component.m_237115_("debug.copy_location.help"));
                $$3.m_93785_(Component.m_237115_("debug.clear_chat.help"));
                $$3.m_93785_(Component.m_237115_("debug.chunk_boundaries.help"));
                $$3.m_93785_(Component.m_237115_("debug.advanced_tooltips.help"));
                $$3.m_93785_(Component.m_237115_("debug.inspect.help"));
                $$3.m_93785_(Component.m_237115_("debug.profiling.help"));
                $$3.m_93785_(Component.m_237115_("debug.creative_spectator.help"));
                $$3.m_93785_(Component.m_237115_("debug.pause_focus.help"));
                $$3.m_93785_(Component.m_237115_("debug.help.help"));
                $$3.m_93785_(Component.m_237115_("debug.reload_resourcepacks.help"));
                $$3.m_93785_(Component.m_237115_("debug.pause.help"));
                $$3.m_93785_(Component.m_237115_("debug.gamemodes.help"));
                return true;
            }
            case 84: {
                this.m_90913_("debug.reload_resourcepacks.message", new Object[0]);
                this.f_90867_.m_91391_();
                return true;
            }
            case 76: {
                if (this.f_90867_.m_167946_(this::m_167822_)) {
                    this.m_90913_("debug.profiling.start", 10);
                }
                return true;
            }
            case 67: {
                if (this.f_90867_.f_91074_.m_36330_()) {
                    return false;
                }
                ClientPacketListener $$4 = this.f_90867_.f_91074_.f_108617_;
                if ($$4 == null) {
                    return false;
                }
                this.m_90913_("debug.copy_location.message", new Object[0]);
                this.m_90911_(String.format(Locale.ROOT, "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f", this.f_90867_.f_91074_.f_19853_.m_46472_().m_135782_(), this.f_90867_.f_91074_.m_20185_(), this.f_90867_.f_91074_.m_20186_(), this.f_90867_.f_91074_.m_20189_(), Float.valueOf(this.f_90867_.f_91074_.m_146908_()), Float.valueOf(this.f_90867_.f_91074_.m_146909_())));
                return true;
            }
        }
        return false;
    }

    private void m_90928_(boolean p_90929_, boolean p_90930_) {
        HitResult $$2 = this.f_90867_.f_91077_;
        if ($$2 == null) {
            return;
        }
        switch ($$2.m_6662_()) {
            case BLOCK: {
                BlockPos $$3 = ((BlockHitResult)$$2).m_82425_();
                BlockState $$4 = this.f_90867_.f_91074_.f_19853_.m_8055_($$3);
                if (p_90929_) {
                    if (p_90930_) {
                        this.f_90867_.f_91074_.f_108617_.m_105149_().m_90708_($$3, p_90947_ -> {
                            this.m_90899_($$4, $$3, (CompoundTag)p_90947_);
                            this.m_90913_("debug.inspect.server.block", new Object[0]);
                        });
                        break;
                    }
                    BlockEntity $$5 = this.f_90867_.f_91074_.f_19853_.m_7702_($$3);
                    CompoundTag $$6 = $$5 != null ? $$5.m_187482_() : null;
                    this.m_90899_($$4, $$3, $$6);
                    this.m_90913_("debug.inspect.client.block", new Object[0]);
                    break;
                }
                this.m_90899_($$4, $$3, null);
                this.m_90913_("debug.inspect.client.block", new Object[0]);
                break;
            }
            case ENTITY: {
                Entity $$7 = ((EntityHitResult)$$2).m_82443_();
                ResourceLocation $$8 = Registry.f_122826_.m_7981_($$7.m_6095_());
                if (p_90929_) {
                    if (p_90930_) {
                        this.f_90867_.f_91074_.f_108617_.m_105149_().m_90702_($$7.m_19879_(), p_90921_ -> {
                            this.m_90922_($$8, $$7.m_20182_(), (CompoundTag)p_90921_);
                            this.m_90913_("debug.inspect.server.entity", new Object[0]);
                        });
                        break;
                    }
                    CompoundTag $$9 = $$7.m_20240_(new CompoundTag());
                    this.m_90922_($$8, $$7.m_20182_(), $$9);
                    this.m_90913_("debug.inspect.client.entity", new Object[0]);
                    break;
                }
                this.m_90922_($$8, $$7.m_20182_(), null);
                this.m_90913_("debug.inspect.client.entity", new Object[0]);
                break;
            }
        }
    }

    private void m_90899_(BlockState p_90900_, BlockPos p_90901_, @Nullable CompoundTag p_90902_) {
        StringBuilder $$3 = new StringBuilder(BlockStateParser.m_116769_(p_90900_));
        if (p_90902_ != null) {
            $$3.append(p_90902_);
        }
        String $$4 = String.format(Locale.ROOT, "/setblock %d %d %d %s", p_90901_.m_123341_(), p_90901_.m_123342_(), p_90901_.m_123343_(), $$3);
        this.m_90911_($$4);
    }

    private void m_90922_(ResourceLocation p_90923_, Vec3 p_90924_, @Nullable CompoundTag p_90925_) {
        String $$5;
        if (p_90925_ != null) {
            p_90925_.m_128473_("UUID");
            p_90925_.m_128473_("Pos");
            p_90925_.m_128473_("Dimension");
            String $$3 = NbtUtils.m_178061_(p_90925_).getString();
            String $$4 = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", p_90923_.toString(), p_90924_.f_82479_, p_90924_.f_82480_, p_90924_.f_82481_, $$3);
        } else {
            $$5 = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", p_90923_.toString(), p_90924_.f_82479_, p_90924_.f_82480_, p_90924_.f_82481_);
        }
        this.m_90911_($$5);
    }

    public void m_90893_(long p_90894_, int p_90895_, int p_90896_, int p_90897_, int p_90898_) {
        if (p_90894_ != this.f_90867_.m_91268_().m_85439_()) {
            return;
        }
        if (this.f_90870_ > 0L) {
            if (!InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 67) || !InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292)) {
                this.f_90870_ = -1L;
            }
        } else if (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 67) && InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292)) {
            this.f_90873_ = true;
            this.f_90870_ = Util.m_137550_();
            this.f_90871_ = Util.m_137550_();
            this.f_90872_ = 0L;
        }
        Screen $$5 = this.f_90867_.f_91080_;
        if (!(p_90897_ != 1 || this.f_90867_.f_91080_ instanceof KeyBindsScreen && ((KeyBindsScreen)$$5).f_193976_ > Util.m_137550_() - 20L)) {
            if (this.f_90867_.f_91066_.f_92105_.m_90832_(p_90895_, p_90896_)) {
                this.f_90867_.m_91268_().m_85438_();
                this.f_90867_.f_91066_.m_231829_().m_231514_(this.f_90867_.m_91268_().m_85440_());
                return;
            }
            if (this.f_90867_.f_91066_.f_92102_.m_90832_(p_90895_, p_90896_)) {
                if (Screen.m_96637_()) {
                    // empty if block
                }
                Screenshot.m_92289_(this.f_90867_.f_91069_, this.f_90867_.m_91385_(), p_90917_ -> this.f_90867_.execute(() -> this.f_90867_.f_91065_.m_93076_().m_93785_((Component)p_90917_)));
                return;
            }
        }
        if (this.f_90867_.m_240477_().m_93316_()) {
            boolean $$6;
            boolean bl = $$6 = $$5 == null || !($$5.m_7222_() instanceof EditBox) || !((EditBox)$$5.m_7222_()).m_94204_();
            if (p_90897_ != 0 && p_90895_ == 66 && Screen.m_96637_() && $$6) {
                boolean $$7 = this.f_90867_.f_91066_.m_231930_().m_231551_() == NarratorStatus.OFF;
                this.f_90867_.f_91066_.m_231930_().m_231514_(NarratorStatus.m_91619_(this.f_90867_.f_91066_.m_231930_().m_231551_().m_91618_() + 1));
                if ($$5 instanceof SimpleOptionsSubScreen) {
                    ((SimpleOptionsSubScreen)$$5).m_96682_();
                }
                if ($$7 && $$5 != null) {
                    $$5.m_169418_();
                }
            }
        }
        if ($$5 != null) {
            boolean[] $$8 = new boolean[]{false};
            Screen.m_96579_(() -> {
                if (p_90897_ == 1 || p_90897_ == 2 && this.f_90868_) {
                    $$5.m_169416_();
                    p_167818_[0] = $$5.m_7933_(p_90895_, p_90896_, p_90898_);
                } else if (p_90897_ == 0) {
                    p_167818_[0] = $$5.m_7920_(p_90895_, p_90896_, p_90898_);
                }
            }, "keyPressed event handler", $$5.getClass().getCanonicalName());
            if ($$8[0]) {
                return;
            }
        }
        if (this.f_90867_.f_91080_ == null || this.f_90867_.f_91080_.f_96546_) {
            InputConstants.Key $$9 = InputConstants.m_84827_(p_90895_, p_90896_);
            if (p_90897_ == 0) {
                KeyMapping.m_90837_($$9, false);
                if (p_90895_ == 292) {
                    if (this.f_90873_) {
                        this.f_90873_ = false;
                    } else {
                        this.f_90867_.f_91066_.f_92063_ = !this.f_90867_.f_91066_.f_92063_;
                        this.f_90867_.f_91066_.f_92064_ = this.f_90867_.f_91066_.f_92063_ && Screen.m_96638_();
                        this.f_90867_.f_91066_.f_92065_ = this.f_90867_.f_91066_.f_92063_ && Screen.m_96639_();
                    }
                }
            } else {
                if (p_90895_ == 293 && this.f_90867_.f_91063_ != null) {
                    this.f_90867_.f_91063_.m_109130_();
                }
                boolean $$10 = false;
                if (this.f_90867_.f_91080_ == null) {
                    if (p_90895_ == 256) {
                        boolean $$11 = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292);
                        this.f_90867_.m_91358_($$11);
                    }
                    $$10 = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292) && this.m_90932_(p_90895_);
                    this.f_90873_ |= $$10;
                    if (p_90895_ == 290) {
                        boolean bl = this.f_90867_.f_91066_.f_92062_ = !this.f_90867_.f_91066_.f_92062_;
                    }
                }
                if ($$10) {
                    KeyMapping.m_90837_($$9, false);
                } else {
                    KeyMapping.m_90837_($$9, true);
                    KeyMapping.m_90835_($$9);
                }
                if (this.f_90867_.f_91066_.f_92064_ && p_90895_ >= 48 && p_90895_ <= 57) {
                    this.f_90867_.m_91111_(p_90895_ - 48);
                }
            }
        }
    }

    private void m_90889_(long p_90890_, int p_90891_, int p_90892_) {
        if (p_90890_ != this.f_90867_.m_91268_().m_85439_()) {
            return;
        }
        Screen $$3 = this.f_90867_.f_91080_;
        if ($$3 == null || this.f_90867_.m_91265_() != null) {
            return;
        }
        if (Character.charCount(p_90891_) == 1) {
            Screen.m_96579_(() -> $$3.m_5534_((char)p_90891_, p_90892_), "charTyped event handler", $$3.getClass().getCanonicalName());
        } else {
            for (char $$4 : Character.toChars(p_90891_)) {
                Screen.m_96579_(() -> $$3.m_5534_($$4, p_90892_), "charTyped event handler", $$3.getClass().getCanonicalName());
            }
        }
    }

    public void m_90926_(boolean p_90927_) {
        this.f_90868_ = p_90927_;
    }

    public void m_90887_(long p_90888_) {
        InputConstants.m_84844_(p_90888_, (p_90939_, p_90940_, p_90941_, p_90942_, p_90943_) -> this.f_90867_.execute(() -> this.m_90893_(p_90939_, p_90940_, p_90941_, p_90942_, p_90943_)), (p_90935_, p_90936_, p_90937_) -> this.f_90867_.execute(() -> this.m_90889_(p_90935_, p_90936_, p_90937_)));
    }

    public String m_90876_() {
        return this.f_90869_.m_83995_(this.f_90867_.m_91268_().m_85439_(), (p_90878_, p_90879_) -> {
            if (p_90878_ != 65545) {
                this.f_90867_.m_91268_().m_85382_(p_90878_, p_90879_);
            }
        });
    }

    public void m_90911_(String p_90912_) {
        if (!p_90912_.isEmpty()) {
            this.f_90869_.m_83988_(this.f_90867_.m_91268_().m_85439_(), p_90912_);
        }
    }

    public void m_90931_() {
        if (this.f_90870_ > 0L) {
            long $$0 = Util.m_137550_();
            long $$1 = 10000L - ($$0 - this.f_90870_);
            long $$2 = $$0 - this.f_90871_;
            if ($$1 < 0L) {
                if (Screen.m_96637_()) {
                    Blaze3D.m_83639_();
                }
                String $$3 = "Manually triggered debug crash";
                CrashReport $$4 = new CrashReport("Manually triggered debug crash", new Throwable("Manually triggered debug crash"));
                CrashReportCategory $$5 = $$4.m_127514_("Manual crash details");
                NativeModuleLister.m_184679_($$5);
                throw new ReportedException($$4);
            }
            if ($$2 >= 1000L) {
                if (this.f_90872_ == 0L) {
                    this.m_90913_("debug.crash.message", new Object[0]);
                } else {
                    this.m_90948_("debug.crash.warning", Mth.m_14167_((float)$$1 / 1000.0f));
                }
                this.f_90871_ = $$0;
                ++this.f_90872_;
            }
        }
    }
}

