/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.base.MoreObjects
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.google.common.io.Files
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  it.unimi.dsi.fastutil.objects.Object2FloatMap
 *  it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.AmbientOcclusionStatus;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.CameraType;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.PrioritizeChunkUpdates;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.ChatPreviewStatus;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class Options {
    static final Logger f_92077_ = LogUtils.getLogger();
    static final Gson f_92078_ = new Gson();
    private static final TypeToken<List<String>> f_92079_ = new TypeToken<List<String>>(){};
    public static final int f_168406_ = 2;
    public static final int f_168407_ = 4;
    public static final int f_168409_ = 8;
    public static final int f_168410_ = 12;
    public static final int f_168411_ = 16;
    public static final int f_168412_ = 32;
    private static final Splitter f_92107_ = Splitter.on((char)':').limit(2);
    private static final float f_168408_ = 1.0f;
    public static final String f_193766_ = "";
    private static final Component f_231789_ = Component.m_237115_("options.darkMojangStudiosBackgroundColor.tooltip");
    private final OptionInstance<Boolean> f_168413_ = OptionInstance.m_231516_("options.darkMojangStudiosBackgroundColor", OptionInstance.m_231535_(f_231789_), false);
    private static final Component f_231790_ = Component.m_237115_("options.hideLightningFlashes.tooltip");
    private final OptionInstance<Boolean> f_231791_ = OptionInstance.m_231516_("options.hideLightningFlashes", OptionInstance.m_231535_(f_231790_), false);
    private final OptionInstance<Double> f_92053_ = new OptionInstance<Double>("options.sensitivity", OptionInstance.m_231498_(), (p_232096_, p_232097_) -> {
        if (p_232097_ == 0.0) {
            return Options.m_231921_(p_232096_, Component.m_237115_("options.sensitivity.min"));
        }
        if (p_232097_ == 1.0) {
            return Options.m_231921_(p_232096_, Component.m_237115_("options.sensitivity.max"));
        }
        return Options.m_231897_(p_232096_, 2.0 * p_232097_);
    }, OptionInstance.UnitDouble.INSTANCE, 0.5, p_232115_ -> {});
    private final OptionInstance<Integer> f_92106_;
    private final OptionInstance<Integer> f_193768_;
    private int f_193765_ = 0;
    private final OptionInstance<Double> f_92112_ = new OptionInstance<Double>("options.entityDistanceScaling", OptionInstance.m_231498_(), Options::m_231897_, new OptionInstance.IntRange(2, 20).m_231657_(p_232020_ -> (double)p_232020_ / 4.0, p_232112_ -> (int)(p_232112_ * 4.0)), Codec.doubleRange((double)0.5, (double)5.0), 1.0, p_232109_ -> {});
    public static final int f_231811_ = 260;
    private final OptionInstance<Integer> f_92113_ = new OptionInstance<Integer>("options.framerateLimit", OptionInstance.m_231498_(), (p_232048_, p_232049_) -> {
        if (p_232049_ == 260) {
            return Options.m_231921_(p_232048_, Component.m_237115_("options.framerateLimit.max"));
        }
        return Options.m_231921_(p_232048_, Component.m_237110_("options.framerate", p_232049_));
    }, new OptionInstance.IntRange(1, 26).m_231657_(p_232003_ -> p_232003_ * 10, p_232094_ -> p_232094_ / 10), Codec.intRange((int)10, (int)260), 120, p_232086_ -> Minecraft.m_91087_().m_91268_().m_85380_((int)p_232086_));
    private final OptionInstance<CloudStatus> f_231792_ = new OptionInstance<CloudStatus>("options.renderClouds", OptionInstance.m_231498_(), OptionInstance.m_231546_(), new OptionInstance.Enum<CloudStatus>(Arrays.asList(CloudStatus.values()), Codec.either((Codec)Codec.BOOL, (Codec)Codec.STRING).xmap(p_231939_ -> (CloudStatus)p_231939_.map(p_232082_ -> p_232082_ != false ? CloudStatus.FANCY : CloudStatus.OFF, p_232043_ -> switch (p_232043_) {
        case "true" -> CloudStatus.FANCY;
        case "fast" -> CloudStatus.FAST;
        default -> CloudStatus.OFF;
    }), p_231941_ -> Either.right((Object)(switch (p_231941_) {
        default -> throw new IncompatibleClassChangeError();
        case CloudStatus.FANCY -> "true";
        case CloudStatus.FAST -> "fast";
        case CloudStatus.OFF -> "false";
    })))), CloudStatus.FANCY, p_231854_ -> {
        RenderTarget $$1;
        if (Minecraft.m_91085_() && ($$1 = Minecraft.m_91087_().f_91060_.m_109832_()) != null) {
            $$1.m_83954_(Minecraft.f_91002_);
        }
    });
    private static final Component f_231793_ = Component.m_237115_("options.graphics.fast.tooltip");
    private static final Component f_231794_ = Component.m_237110_("options.graphics.fabulous.tooltip", Component.m_237115_("options.graphics.fabulous").m_130940_(ChatFormatting.ITALIC));
    private static final Component f_231785_ = Component.m_237115_("options.graphics.fancy.tooltip");
    private final OptionInstance<GraphicsStatus> f_92115_ = new OptionInstance<GraphicsStatus>("options.graphics", p_231968_ -> {
        List<FormattedCharSequence> $$1 = OptionInstance.m_231501_(p_231968_, f_231793_);
        List<FormattedCharSequence> $$2 = OptionInstance.m_231501_(p_231968_, f_231785_);
        List<FormattedCharSequence> $$3 = OptionInstance.m_231501_(p_231968_, f_231794_);
        return p_231888_ -> switch (p_231888_) {
            default -> throw new IncompatibleClassChangeError();
            case GraphicsStatus.FANCY -> $$2;
            case GraphicsStatus.FAST -> $$1;
            case GraphicsStatus.FABULOUS -> $$3;
        };
    }, (p_231904_, p_231905_) -> {
        MutableComponent $$2 = Component.m_237115_(p_231905_.m_35968_());
        if (p_231905_ == GraphicsStatus.FABULOUS) {
            return $$2.m_130940_(ChatFormatting.ITALIC);
        }
        return $$2;
    }, new OptionInstance.AltEnum<GraphicsStatus>(Arrays.asList(GraphicsStatus.values()), Stream.of(GraphicsStatus.values()).filter(p_231943_ -> p_231943_ != GraphicsStatus.FABULOUS).collect(Collectors.toList()), () -> Minecraft.m_91087_().m_91396_() && Minecraft.m_91087_().m_91105_().m_109251_(), (p_231862_, p_231863_) -> {
        Minecraft $$2 = Minecraft.m_91087_();
        GpuWarnlistManager $$3 = $$2.m_91105_();
        if (p_231863_ == GraphicsStatus.FABULOUS && $$3.m_109240_()) {
            $$3.m_109247_();
            return;
        }
        p_231862_.m_231514_(p_231863_);
        $$2.f_91060_.m_109818_();
    }, Codec.INT.xmap(GraphicsStatus::m_90774_, GraphicsStatus::m_35965_)), GraphicsStatus.FANCY, p_231856_ -> {});
    private final OptionInstance<AmbientOcclusionStatus> f_92116_ = new OptionInstance<AmbientOcclusionStatus>("options.ao", OptionInstance.m_231498_(), OptionInstance.m_231546_(), new OptionInstance.Enum<AmbientOcclusionStatus>(Arrays.asList(AmbientOcclusionStatus.values()), Codec.either((Codec)Codec.BOOL.xmap(p_232072_ -> p_232072_ != false ? AmbientOcclusionStatus.MAX.m_35965_() : AmbientOcclusionStatus.OFF.m_35965_(), p_232076_ -> p_232076_.intValue() == AmbientOcclusionStatus.MAX.m_35965_()), (Codec)Codec.INT).xmap(p_231846_ -> (Integer)p_231846_.map(p_232066_ -> p_232066_, p_232056_ -> p_232056_), Either::right).xmap(AmbientOcclusionStatus::m_90487_, AmbientOcclusionStatus::m_35965_)), AmbientOcclusionStatus.MAX, p_231850_ -> Minecraft.m_91087_().f_91060_.m_109818_());
    private static final Component f_231786_ = Component.m_237115_("options.prioritizeChunkUpdates.none.tooltip");
    private static final Component f_231787_ = Component.m_237115_("options.prioritizeChunkUpdates.byPlayer.tooltip");
    private static final Component f_231788_ = Component.m_237115_("options.prioritizeChunkUpdates.nearby.tooltip");
    private final OptionInstance<PrioritizeChunkUpdates> f_193769_ = new OptionInstance<PrioritizeChunkUpdates>("options.prioritizeChunkUpdates", p_231945_ -> {
        List<FormattedCharSequence> $$1 = OptionInstance.m_231501_(p_231945_, f_231786_);
        List<FormattedCharSequence> $$2 = OptionInstance.m_231501_(p_231945_, f_231787_);
        List<FormattedCharSequence> $$3 = OptionInstance.m_231501_(p_231945_, f_231788_);
        return p_231893_ -> switch (p_231893_) {
            default -> throw new IncompatibleClassChangeError();
            case PrioritizeChunkUpdates.NONE -> $$1;
            case PrioritizeChunkUpdates.PLAYER_AFFECTED -> $$2;
            case PrioritizeChunkUpdates.NEARBY -> $$3;
        };
    }, OptionInstance.m_231546_(), new OptionInstance.Enum<PrioritizeChunkUpdates>(Arrays.asList(PrioritizeChunkUpdates.values()), Codec.INT.xmap(PrioritizeChunkUpdates::m_193787_, PrioritizeChunkUpdates::m_35965_)), PrioritizeChunkUpdates.NONE, p_231871_ -> {});
    public List<String> f_92117_ = Lists.newArrayList();
    public List<String> f_92118_ = Lists.newArrayList();
    private final OptionInstance<ChatVisiblity> f_92119_ = new OptionInstance<ChatVisiblity>("options.chat.visibility", OptionInstance.m_231498_(), OptionInstance.m_231546_(), new OptionInstance.Enum<ChatVisiblity>(Arrays.asList(ChatVisiblity.values()), Codec.INT.xmap(ChatVisiblity::m_35966_, ChatVisiblity::m_35965_)), ChatVisiblity.FULL, p_231844_ -> {});
    private final OptionInstance<Double> f_92120_ = new OptionInstance<Double>("options.chat.opacity", OptionInstance.m_231498_(), (p_232088_, p_232089_) -> Options.m_231897_(p_232088_, p_232089_ * 0.9 + 0.1), OptionInstance.UnitDouble.INSTANCE, 1.0, p_232106_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_());
    private final OptionInstance<Double> f_92121_ = new OptionInstance<Double>("options.chat.line_spacing", OptionInstance.m_231498_(), Options::m_231897_, OptionInstance.UnitDouble.INSTANCE, 0.0, p_232103_ -> {});
    private final OptionInstance<Double> f_92122_ = new OptionInstance<Double>("options.accessibility.text_background_opacity", OptionInstance.m_231498_(), Options::m_231897_, OptionInstance.UnitDouble.INSTANCE, 0.5, p_232100_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_());
    @Nullable
    public String f_92123_;
    public boolean f_92124_;
    public boolean f_92125_;
    public boolean f_92126_ = true;
    private final Set<PlayerModelPart> f_92108_ = EnumSet.allOf(PlayerModelPart.class);
    private final OptionInstance<HumanoidArm> f_92127_ = new OptionInstance<HumanoidArm>("options.mainHand", OptionInstance.m_231498_(), OptionInstance.m_231546_(), new OptionInstance.Enum<HumanoidArm>(Arrays.asList(HumanoidArm.values()), Codec.STRING.xmap(p_232028_ -> "left".equals(p_232028_) ? HumanoidArm.LEFT : HumanoidArm.RIGHT, p_231937_ -> p_231937_ == HumanoidArm.LEFT ? "left" : "right")), HumanoidArm.RIGHT, p_231842_ -> this.m_92172_());
    public int f_92128_;
    public int f_92129_;
    public boolean f_92130_ = true;
    private final OptionInstance<Double> f_92131_ = new OptionInstance<Double>("options.chat.scale", OptionInstance.m_231498_(), (p_232078_, p_232079_) -> {
        if (p_232079_ == 0.0) {
            return CommonComponents.m_130663_(p_232078_, false);
        }
        return Options.m_231897_(p_232078_, p_232079_);
    }, OptionInstance.UnitDouble.INSTANCE, 1.0, p_232092_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_());
    private final OptionInstance<Double> f_92132_ = new OptionInstance<Double>("options.chat.width", OptionInstance.m_231498_(), (p_232068_, p_232069_) -> Options.m_231952_(p_232068_, ChatComponent.m_93798_(p_232069_)), OptionInstance.UnitDouble.INSTANCE, 1.0, p_232084_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_());
    private final OptionInstance<Double> f_92133_ = new OptionInstance<Double>("options.chat.height.unfocused", OptionInstance.m_231498_(), (p_232058_, p_232059_) -> Options.m_231952_(p_232058_, ChatComponent.m_93811_(p_232059_)), OptionInstance.UnitDouble.INSTANCE, ChatComponent.m_232477_(), p_232074_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_());
    private final OptionInstance<Double> f_92134_ = new OptionInstance<Double>("options.chat.height.focused", OptionInstance.m_231498_(), (p_232045_, p_232046_) -> Options.m_231952_(p_232045_, ChatComponent.m_93811_(p_232046_)), OptionInstance.UnitDouble.INSTANCE, 1.0, p_232064_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_());
    private final OptionInstance<Double> f_92135_ = new OptionInstance<Double>("options.chat.delay_instant", OptionInstance.m_231498_(), (p_232030_, p_232031_) -> {
        if (p_232031_ <= 0.0) {
            return Component.m_237115_("options.chat.delay_none");
        }
        return Component.m_237110_("options.chat.delay", String.format(Locale.ROOT, "%.1f", p_232031_));
    }, new OptionInstance.IntRange(0, 60).m_231657_(p_231986_ -> (double)p_231986_ / 10.0, p_232054_ -> (int)(p_232054_ * 10.0)), Codec.doubleRange((double)0.0, (double)6.0), 0.0, p_232039_ -> Minecraft.m_91087_().m_240442_().m_240692_((double)p_232039_));
    private final OptionInstance<Integer> f_92027_ = new OptionInstance<Integer>("options.mipmapLevels", OptionInstance.m_231498_(), (p_232033_, p_232034_) -> {
        if (p_232034_ == 0) {
            return CommonComponents.m_130663_(p_232033_, false);
        }
        return Options.m_231900_(p_232033_, p_232034_);
    }, new OptionInstance.IntRange(0, 4), 4, p_232041_ -> {});
    private final Object2FloatMap<SoundSource> f_92109_ = (Object2FloatMap)Util.m_137469_(new Object2FloatOpenHashMap(), p_231873_ -> p_231873_.defaultReturnValue(1.0f));
    public boolean f_92028_ = true;
    private final OptionInstance<AttackIndicatorStatus> f_92029_ = new OptionInstance<AttackIndicatorStatus>("options.attackIndicator", OptionInstance.m_231498_(), OptionInstance.m_231546_(), new OptionInstance.Enum<AttackIndicatorStatus>(Arrays.asList(AttackIndicatorStatus.values()), Codec.INT.xmap(AttackIndicatorStatus::m_90509_, AttackIndicatorStatus::m_35965_)), AttackIndicatorStatus.CROSSHAIR, p_231852_ -> {});
    public TutorialSteps f_92030_ = TutorialSteps.MOVEMENT;
    public boolean f_92031_ = false;
    public boolean f_168405_ = false;
    private final OptionInstance<Integer> f_92032_ = new OptionInstance<Integer>("options.biomeBlendRadius", OptionInstance.m_231498_(), (p_232016_, p_232017_) -> {
        int $$2 = p_232017_ * 2 + 1;
        return Options.m_231921_(p_232016_, Component.m_237115_("options.biomeBlendRadius." + $$2));
    }, new OptionInstance.IntRange(0, 7), 2, p_232026_ -> Minecraft.m_91087_().f_91060_.m_109818_());
    private final OptionInstance<Double> f_92033_ = new OptionInstance<Double>("options.mouseWheelSensitivity", OptionInstance.m_231498_(), (p_232013_, p_232014_) -> Options.m_231921_(p_232013_, Component.m_237113_(String.format(Locale.ROOT, "%.2f", p_232014_))), new OptionInstance.IntRange(-200, 100).m_231657_(Options::m_231965_, Options::m_231839_), Codec.doubleRange((double)Options.m_231965_(-200), (double)Options.m_231965_(100)), Options.m_231965_(0), p_232024_ -> {});
    private final OptionInstance<Boolean> f_92034_ = OptionInstance.m_231528_("options.rawMouseInput", true, p_232062_ -> {
        Window $$1 = Minecraft.m_91087_().m_91268_();
        if ($$1 != null) {
            $$1.m_85424_((boolean)p_232062_);
        }
    });
    public int f_92035_ = 1;
    private final OptionInstance<Boolean> f_92036_ = OptionInstance.m_231525_("options.autoJump", true);
    private final OptionInstance<Boolean> f_92037_ = OptionInstance.m_231525_("options.autoSuggestCommands", true);
    private final OptionInstance<Boolean> f_92038_ = OptionInstance.m_231525_("options.chat.color", true);
    private final OptionInstance<Boolean> f_92039_ = OptionInstance.m_231525_("options.chat.links", true);
    private final OptionInstance<Boolean> f_92040_ = OptionInstance.m_231525_("options.chat.links.prompt", true);
    private final OptionInstance<Boolean> f_92041_ = OptionInstance.m_231528_("options.vsync", true, p_232052_ -> {
        if (Minecraft.m_91087_().m_91268_() != null) {
            Minecraft.m_91087_().m_91268_().m_85409_((boolean)p_232052_);
        }
    });
    private final OptionInstance<Boolean> f_92042_ = OptionInstance.m_231525_("options.entityShadows", true);
    private final OptionInstance<Boolean> f_92043_ = OptionInstance.m_231528_("options.forceUnicodeFont", false, p_232037_ -> {
        Minecraft $$1 = Minecraft.m_91087_();
        if ($$1.m_91268_() != null) {
            $$1.m_91336_((boolean)p_232037_);
            $$1.m_5741_();
        }
    });
    private final OptionInstance<Boolean> f_92044_ = OptionInstance.m_231525_("options.invertMouse", false);
    private final OptionInstance<Boolean> f_92045_ = OptionInstance.m_231525_("options.discrete_mouse_scroll", false);
    private final OptionInstance<Boolean> f_92046_ = OptionInstance.m_231525_("options.realmsNotifications", true);
    private static final Component f_231804_ = Component.m_237115_("options.allowServerListing.tooltip");
    private final OptionInstance<Boolean> f_193762_ = OptionInstance.m_231520_("options.allowServerListing", OptionInstance.m_231535_(f_231804_), true, p_232022_ -> this.m_92172_());
    private final OptionInstance<Boolean> f_92047_ = OptionInstance.m_231525_("options.reducedDebugInfo", false);
    private final OptionInstance<Boolean> f_92049_ = OptionInstance.m_231525_("options.showSubtitles", false);
    private static final Component f_231805_ = Component.m_237115_("options.directionalAudio.on.tooltip");
    private static final Component f_231806_ = Component.m_237115_("options.directionalAudio.off.tooltip");
    private final OptionInstance<Boolean> f_231807_ = OptionInstance.m_231520_("options.directionalAudio", p_231858_ -> {
        List<FormattedCharSequence> $$1 = OptionInstance.m_231501_(p_231858_, f_231805_);
        List<FormattedCharSequence> $$2 = OptionInstance.m_231501_(p_231858_, f_231806_);
        return p_231883_ -> p_231883_ != false ? $$1 : $$2;
    }, false, p_232005_ -> {
        SoundManager $$1 = Minecraft.m_91087_().m_91106_();
        $$1.m_194526_();
        $$1.m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0f));
    });
    private final OptionInstance<Boolean> f_92050_ = new OptionInstance<Boolean>("options.accessibility.text_background", OptionInstance.m_231498_(), (p_231976_, p_231977_) -> p_231977_ != false ? Component.m_237115_("options.accessibility.text_background.chat") : Component.m_237115_("options.accessibility.text_background.everywhere"), OptionInstance.f_231471_, true, p_231988_ -> {});
    private final OptionInstance<Boolean> f_92051_ = OptionInstance.m_231525_("options.touchscreen", false);
    private final OptionInstance<Boolean> f_92052_ = OptionInstance.m_231528_("options.fullscreen", false, p_231970_ -> {
        Minecraft $$1 = Minecraft.m_91087_();
        if ($$1.m_91268_() != null && $$1.m_91268_().m_85440_() != p_231970_.booleanValue()) {
            $$1.m_91268_().m_85438_();
            this.m_231829_().m_231514_($$1.m_91268_().m_85440_());
        }
    });
    private final OptionInstance<Boolean> f_92080_ = OptionInstance.m_231525_("options.viewBobbing", true);
    private static final Component f_231808_ = Component.m_237115_("options.key.toggle");
    private static final Component f_231809_ = Component.m_237115_("options.key.hold");
    private final OptionInstance<Boolean> f_92081_ = new OptionInstance<Boolean>("key.sneak", OptionInstance.m_231498_(), (p_231956_, p_231957_) -> p_231957_ != false ? f_231808_ : f_231809_, OptionInstance.f_231471_, false, p_231947_ -> {});
    private final OptionInstance<Boolean> f_92082_ = new OptionInstance<Boolean>("key.sprint", OptionInstance.m_231498_(), (p_231910_, p_231911_) -> p_231911_ != false ? f_231808_ : f_231809_, OptionInstance.f_231471_, false, p_231875_ -> {});
    public boolean f_92083_;
    public boolean f_210816_;
    private static final Component f_231810_ = Component.m_237115_("options.hideMatchedNames.tooltip");
    private final OptionInstance<Boolean> f_92084_ = OptionInstance.m_231516_("options.hideMatchedNames", OptionInstance.m_231535_(f_231810_), true);
    private final OptionInstance<Boolean> f_193763_ = OptionInstance.m_231525_("options.autosaveIndicator", true);
    private static final Component f_241666_ = Component.m_237115_("options.chatPreview.tooltip.off");
    private static final Component f_241664_ = Component.m_237115_("options.chatPreview.tooltip.live");
    private static final Component f_241642_ = Component.m_237115_("options.chatPreview.tooltip.confirm");
    private final OptionInstance<ChatPreviewStatus> f_231796_ = new OptionInstance<ChatPreviewStatus>("options.chatPreview", p_242047_ -> p_242049_ -> switch (p_242049_) {
        default -> throw new IncompatibleClassChangeError();
        case ChatPreviewStatus.OFF -> OptionInstance.m_231501_(p_242047_, f_241666_);
        case ChatPreviewStatus.LIVE -> OptionInstance.m_231501_(p_242047_, f_241664_);
        case ChatPreviewStatus.CONFIRM -> OptionInstance.m_231501_(p_242047_, f_241642_);
    }, OptionInstance.m_231546_(), new OptionInstance.Enum<ChatPreviewStatus>(Arrays.asList(ChatPreviewStatus.values()), Codec.INT.xmap(ChatPreviewStatus::m_241779_, ChatPreviewStatus::m_35965_)), ChatPreviewStatus.LIVE, p_242263_ -> {});
    private static final Component f_231797_ = Component.m_237115_("options.onlyShowSecureChat.tooltip");
    private final OptionInstance<Boolean> f_231798_ = OptionInstance.m_231516_("options.onlyShowSecureChat", OptionInstance.m_231535_(f_231797_), false);
    public final KeyMapping f_92085_ = new KeyMapping("key.forward", 87, "key.categories.movement");
    public final KeyMapping f_92086_ = new KeyMapping("key.left", 65, "key.categories.movement");
    public final KeyMapping f_92087_ = new KeyMapping("key.back", 83, "key.categories.movement");
    public final KeyMapping f_92088_ = new KeyMapping("key.right", 68, "key.categories.movement");
    public final KeyMapping f_92089_ = new KeyMapping("key.jump", 32, "key.categories.movement");
    public final KeyMapping f_92090_ = new ToggleKeyMapping("key.sneak", 340, "key.categories.movement", this.f_92081_::m_231551_);
    public final KeyMapping f_92091_ = new ToggleKeyMapping("key.sprint", 341, "key.categories.movement", this.f_92082_::m_231551_);
    public final KeyMapping f_92092_ = new KeyMapping("key.inventory", 69, "key.categories.inventory");
    public final KeyMapping f_92093_ = new KeyMapping("key.swapOffhand", 70, "key.categories.inventory");
    public final KeyMapping f_92094_ = new KeyMapping("key.drop", 81, "key.categories.inventory");
    public final KeyMapping f_92095_ = new KeyMapping("key.use", InputConstants.Type.MOUSE, 1, "key.categories.gameplay");
    public final KeyMapping f_92096_ = new KeyMapping("key.attack", InputConstants.Type.MOUSE, 0, "key.categories.gameplay");
    public final KeyMapping f_92097_ = new KeyMapping("key.pickItem", InputConstants.Type.MOUSE, 2, "key.categories.gameplay");
    public final KeyMapping f_92098_ = new KeyMapping("key.chat", 84, "key.categories.multiplayer");
    public final KeyMapping f_92099_ = new KeyMapping("key.playerlist", 258, "key.categories.multiplayer");
    public final KeyMapping f_92100_ = new KeyMapping("key.command", 47, "key.categories.multiplayer");
    public final KeyMapping f_92101_ = new KeyMapping("key.socialInteractions", 80, "key.categories.multiplayer");
    public final KeyMapping f_92102_ = new KeyMapping("key.screenshot", 291, "key.categories.misc");
    public final KeyMapping f_92103_ = new KeyMapping("key.togglePerspective", 294, "key.categories.misc");
    public final KeyMapping f_92104_ = new KeyMapping("key.smoothCamera", InputConstants.f_84822_.m_84873_(), "key.categories.misc");
    public final KeyMapping f_92105_ = new KeyMapping("key.fullscreen", 300, "key.categories.misc");
    public final KeyMapping f_92054_ = new KeyMapping("key.spectatorOutlines", InputConstants.f_84822_.m_84873_(), "key.categories.misc");
    public final KeyMapping f_92055_ = new KeyMapping("key.advancements", 76, "key.categories.misc");
    public final KeyMapping[] f_92056_ = new KeyMapping[]{new KeyMapping("key.hotbar.1", 49, "key.categories.inventory"), new KeyMapping("key.hotbar.2", 50, "key.categories.inventory"), new KeyMapping("key.hotbar.3", 51, "key.categories.inventory"), new KeyMapping("key.hotbar.4", 52, "key.categories.inventory"), new KeyMapping("key.hotbar.5", 53, "key.categories.inventory"), new KeyMapping("key.hotbar.6", 54, "key.categories.inventory"), new KeyMapping("key.hotbar.7", 55, "key.categories.inventory"), new KeyMapping("key.hotbar.8", 56, "key.categories.inventory"), new KeyMapping("key.hotbar.9", 57, "key.categories.inventory")};
    public final KeyMapping f_92057_ = new KeyMapping("key.saveToolbarActivator", 67, "key.categories.creative");
    public final KeyMapping f_92058_ = new KeyMapping("key.loadToolbarActivator", 88, "key.categories.creative");
    public final KeyMapping[] f_92059_ = (KeyMapping[])ArrayUtils.addAll((Object[])new KeyMapping[]{this.f_92096_, this.f_92095_, this.f_92085_, this.f_92086_, this.f_92087_, this.f_92088_, this.f_92089_, this.f_92090_, this.f_92091_, this.f_92094_, this.f_92092_, this.f_92098_, this.f_92099_, this.f_92097_, this.f_92100_, this.f_92101_, this.f_92102_, this.f_92103_, this.f_92104_, this.f_92105_, this.f_92054_, this.f_92093_, this.f_92057_, this.f_92058_, this.f_92055_}, (Object[])this.f_92056_);
    protected Minecraft f_92060_;
    private final File f_92110_;
    public boolean f_92062_;
    private CameraType f_92111_ = CameraType.FIRST_PERSON;
    public boolean f_92063_;
    public boolean f_92064_;
    public boolean f_92065_;
    public String f_92066_ = "";
    public boolean f_92067_;
    private final OptionInstance<Integer> f_92068_ = new OptionInstance<Integer>("options.fov", OptionInstance.m_231498_(), (p_231999_, p_232000_) -> switch (p_232000_) {
        case 70 -> Options.m_231921_(p_231999_, Component.m_237115_("options.fov.min"));
        case 110 -> Options.m_231921_(p_231999_, Component.m_237115_("options.fov.max"));
        default -> Options.m_231900_(p_231999_, p_232000_);
    }, new OptionInstance.IntRange(30, 110), Codec.DOUBLE.xmap(p_232007_ -> (int)(p_232007_ * 40.0 + 70.0), p_232009_ -> ((double)p_232009_.intValue() - 70.0) / 40.0), 70, p_231992_ -> Minecraft.m_91087_().f_91060_.m_109826_());
    private static final Component f_231799_ = Component.m_237115_("options.screenEffectScale.tooltip");
    private final OptionInstance<Double> f_92069_ = new OptionInstance<Double>("options.screenEffectScale", OptionInstance.m_231535_(f_231799_), (p_231996_, p_231997_) -> {
        if (p_231997_ == 0.0) {
            return Options.m_231921_(p_231996_, CommonComponents.f_130654_);
        }
        return Options.m_231897_(p_231996_, p_231997_);
    }, OptionInstance.UnitDouble.INSTANCE, 1.0, p_231990_ -> {});
    private static final Component f_231800_ = Component.m_237115_("options.fovEffectScale.tooltip");
    private final OptionInstance<Double> f_92070_ = new OptionInstance<Double>("options.fovEffectScale", OptionInstance.m_231535_(f_231800_), (p_231979_, p_231980_) -> {
        if (p_231980_ == 0.0) {
            return Options.m_231921_(p_231979_, CommonComponents.f_130654_);
        }
        return Options.m_231897_(p_231979_, p_231980_);
    }, OptionInstance.UnitDouble.INSTANCE.m_231750_(Mth::m_144952_, Math::sqrt), Codec.doubleRange((double)0.0, (double)1.0), 1.0, p_231972_ -> {});
    private static final Component f_231801_ = Component.m_237115_("options.darknessEffectScale.tooltip");
    private final OptionInstance<Double> f_231802_ = new OptionInstance<Double>("options.darknessEffectScale", OptionInstance.m_231535_(f_231801_), (p_231959_, p_231960_) -> {
        if (p_231960_ == 0.0) {
            return Options.m_231921_(p_231959_, CommonComponents.f_130654_);
        }
        return Options.m_231897_(p_231959_, p_231960_);
    }, OptionInstance.UnitDouble.INSTANCE.m_231750_(Mth::m_144952_, Math::sqrt), 1.0, p_231949_ -> {});
    private final OptionInstance<Double> f_92071_ = new OptionInstance<Double>("options.gamma", OptionInstance.m_231498_(), (p_231913_, p_231914_) -> {
        int $$2 = (int)(p_231914_ * 100.0);
        if ($$2 == 0) {
            return Options.m_231921_(p_231913_, Component.m_237115_("options.gamma.min"));
        }
        if ($$2 == 50) {
            return Options.m_231921_(p_231913_, Component.m_237115_("options.gamma.default"));
        }
        if ($$2 == 100) {
            return Options.m_231921_(p_231913_, Component.m_237115_("options.gamma.max"));
        }
        return Options.m_231900_(p_231913_, $$2);
    }, OptionInstance.UnitDouble.INSTANCE, 0.5, p_231877_ -> {});
    private final OptionInstance<Integer> f_92072_ = new OptionInstance<Integer>("options.guiScale", OptionInstance.m_231498_(), (p_231982_, p_231983_) -> p_231983_ == 0 ? Component.m_237115_("options.guiScale.auto") : Component.m_237113_(Integer.toString(p_231983_)), new OptionInstance.ClampingLazyMaxIntRange(0, () -> {
        Minecraft $$0 = Minecraft.m_91087_();
        if (!$$0.m_91396_()) {
            return 0x7FFFFFFE;
        }
        return $$0.m_91268_().m_85385_(0, $$0.m_91390_());
    }), 0, p_231974_ -> {});
    private final OptionInstance<ParticleStatus> f_92073_ = new OptionInstance<ParticleStatus>("options.particles", OptionInstance.m_231498_(), OptionInstance.m_231546_(), new OptionInstance.Enum<ParticleStatus>(Arrays.asList(ParticleStatus.values()), Codec.INT.xmap(ParticleStatus::m_92196_, ParticleStatus::m_35965_)), ParticleStatus.ALL, p_231869_ -> {});
    private final OptionInstance<NarratorStatus> f_231803_ = new OptionInstance<NarratorStatus>("options.narrator", OptionInstance.m_231498_(), (p_231907_, p_231908_) -> {
        if (this.f_92060_.m_240477_().m_93316_()) {
            return p_231908_.m_91621_();
        }
        return Component.m_237115_("options.narrator.notavailable");
    }, new OptionInstance.Enum<NarratorStatus>(Arrays.asList(NarratorStatus.values()), Codec.INT.xmap(NarratorStatus::m_91619_, NarratorStatus::m_91618_)), NarratorStatus.OFF, p_231860_ -> this.f_92060_.m_240477_().m_93317_((NarratorStatus)((Object)p_231860_)));
    public String f_92075_ = "en_us";
    private final OptionInstance<String> f_193764_ = new OptionInstance<String>("options.audioDevice", OptionInstance.m_231498_(), (p_231919_, p_231920_) -> {
        if (f_193766_.equals(p_231920_)) {
            return Component.m_237115_("options.audioDevice.default");
        }
        if (p_231920_.startsWith("OpenAL Soft on ")) {
            return Component.m_237113_(p_231920_.substring(SoundEngine.f_194470_));
        }
        return Component.m_237113_(p_231920_);
    }, new OptionInstance.LazyEnum<String>(() -> Stream.concat(Stream.of(f_193766_), Minecraft.m_91087_().m_91106_().m_194525_().stream()).toList(), (Function<String, Optional<String>>)((Function<String, Optional>)p_232011_ -> {
        if (!Minecraft.m_91087_().m_91396_() || p_232011_ == f_193766_ || Minecraft.m_91087_().m_91106_().m_194525_().contains(p_232011_)) {
            return Optional.of(p_232011_);
        }
        return Optional.empty();
    }), (Codec<String>)Codec.STRING), "", p_231994_ -> {
        SoundManager $$1 = Minecraft.m_91087_().m_91106_();
        $$1.m_194526_();
        $$1.m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0f));
    });
    public boolean f_92076_;

    public OptionInstance<Boolean> m_231838_() {
        return this.f_168413_;
    }

    public OptionInstance<Boolean> m_231935_() {
        return this.f_231791_;
    }

    public OptionInstance<Double> m_231964_() {
        return this.f_92053_;
    }

    public OptionInstance<Integer> m_231984_() {
        return this.f_92106_;
    }

    public OptionInstance<Integer> m_232001_() {
        return this.f_193768_;
    }

    public OptionInstance<Double> m_232018_() {
        return this.f_92112_;
    }

    public OptionInstance<Integer> m_232035_() {
        return this.f_92113_;
    }

    public OptionInstance<CloudStatus> m_232050_() {
        return this.f_231792_;
    }

    public OptionInstance<GraphicsStatus> m_232060_() {
        return this.f_92115_;
    }

    public OptionInstance<AmbientOcclusionStatus> m_232070_() {
        return this.f_92116_;
    }

    public OptionInstance<PrioritizeChunkUpdates> m_232080_() {
        return this.f_193769_;
    }

    public OptionInstance<ChatVisiblity> m_232090_() {
        return this.f_92119_;
    }

    public OptionInstance<Double> m_232098_() {
        return this.f_92120_;
    }

    public OptionInstance<Double> m_232101_() {
        return this.f_92121_;
    }

    public OptionInstance<Double> m_232104_() {
        return this.f_92122_;
    }

    public OptionInstance<HumanoidArm> m_232107_() {
        return this.f_92127_;
    }

    public OptionInstance<Double> m_232110_() {
        return this.f_92131_;
    }

    public OptionInstance<Double> m_232113_() {
        return this.f_92132_;
    }

    public OptionInstance<Double> m_232116_() {
        return this.f_92133_;
    }

    public OptionInstance<Double> m_232117_() {
        return this.f_92134_;
    }

    public OptionInstance<Double> m_232118_() {
        return this.f_92135_;
    }

    public OptionInstance<Integer> m_232119_() {
        return this.f_92027_;
    }

    public OptionInstance<AttackIndicatorStatus> m_232120_() {
        return this.f_92029_;
    }

    public OptionInstance<Integer> m_232121_() {
        return this.f_92032_;
    }

    private static double m_231965_(int p_231966_) {
        return Math.pow(10.0, (double)p_231966_ / 100.0);
    }

    private static int m_231839_(double p_231840_) {
        return Mth.m_14107_(Math.log10(p_231840_) * 100.0);
    }

    public OptionInstance<Double> m_232122_() {
        return this.f_92033_;
    }

    public OptionInstance<Boolean> m_232123_() {
        return this.f_92034_;
    }

    public OptionInstance<Boolean> m_231812_() {
        return this.f_92036_;
    }

    public OptionInstance<Boolean> m_231813_() {
        return this.f_92037_;
    }

    public OptionInstance<Boolean> m_231814_() {
        return this.f_92038_;
    }

    public OptionInstance<Boolean> m_231815_() {
        return this.f_92039_;
    }

    public OptionInstance<Boolean> m_231816_() {
        return this.f_92040_;
    }

    public OptionInstance<Boolean> m_231817_() {
        return this.f_92041_;
    }

    public OptionInstance<Boolean> m_231818_() {
        return this.f_92042_;
    }

    public OptionInstance<Boolean> m_231819_() {
        return this.f_92043_;
    }

    public OptionInstance<Boolean> m_231820_() {
        return this.f_92044_;
    }

    public OptionInstance<Boolean> m_231821_() {
        return this.f_92045_;
    }

    public OptionInstance<Boolean> m_231822_() {
        return this.f_92046_;
    }

    public OptionInstance<Boolean> m_231823_() {
        return this.f_193762_;
    }

    public OptionInstance<Boolean> m_231824_() {
        return this.f_92047_;
    }

    public OptionInstance<Boolean> m_231825_() {
        return this.f_92049_;
    }

    public OptionInstance<Boolean> m_231826_() {
        return this.f_231807_;
    }

    public OptionInstance<Boolean> m_231827_() {
        return this.f_92050_;
    }

    public OptionInstance<Boolean> m_231828_() {
        return this.f_92051_;
    }

    public OptionInstance<Boolean> m_231829_() {
        return this.f_92052_;
    }

    public OptionInstance<Boolean> m_231830_() {
        return this.f_92080_;
    }

    public OptionInstance<Boolean> m_231831_() {
        return this.f_92081_;
    }

    public OptionInstance<Boolean> m_231832_() {
        return this.f_92082_;
    }

    public OptionInstance<Boolean> m_231833_() {
        return this.f_92084_;
    }

    public OptionInstance<Boolean> m_231834_() {
        return this.f_193763_;
    }

    public OptionInstance<ChatPreviewStatus> m_231835_() {
        return this.f_231796_;
    }

    public OptionInstance<Boolean> m_231836_() {
        return this.f_231798_;
    }

    public OptionInstance<Integer> m_231837_() {
        return this.f_92068_;
    }

    public OptionInstance<Double> m_231924_() {
        return this.f_92069_;
    }

    public OptionInstance<Double> m_231925_() {
        return this.f_92070_;
    }

    public OptionInstance<Double> m_231926_() {
        return this.f_231802_;
    }

    public OptionInstance<Double> m_231927_() {
        return this.f_92071_;
    }

    public OptionInstance<Integer> m_231928_() {
        return this.f_92072_;
    }

    public OptionInstance<ParticleStatus> m_231929_() {
        return this.f_92073_;
    }

    public OptionInstance<NarratorStatus> m_231930_() {
        return this.f_231803_;
    }

    public OptionInstance<String> m_231931_() {
        return this.f_193764_;
    }

    public Options(Minecraft p_92138_, File p_92139_) {
        this.f_92060_ = p_92138_;
        this.f_92110_ = new File(p_92139_, "options.txt");
        boolean $$2 = p_92138_.m_91103_();
        boolean $$3 = $$2 && Runtime.getRuntime().maxMemory() >= 1000000000L;
        this.f_92106_ = new OptionInstance<Integer>("options.renderDistance", OptionInstance.m_231498_(), (p_231962_, p_231963_) -> Options.m_231921_(p_231962_, Component.m_237110_("options.chunks", p_231963_)), new OptionInstance.IntRange(2, $$3 ? 32 : 16), $$2 ? 12 : 8, p_231951_ -> Minecraft.m_91087_().f_91060_.m_109826_());
        this.f_193768_ = new OptionInstance<Integer>("options.simulationDistance", OptionInstance.m_231498_(), (p_231916_, p_231917_) -> Options.m_231921_(p_231916_, Component.m_237110_("options.chunks", p_231917_)), new OptionInstance.IntRange(5, $$3 ? 32 : 16), $$2 ? 12 : 8, p_231879_ -> {});
        this.f_92076_ = Util.m_137581_() == Util.OS.WINDOWS;
        this.m_92140_();
    }

    public float m_92141_(float p_92142_) {
        return this.f_92050_.m_231551_() != false ? p_92142_ : this.m_232104_().m_231551_().floatValue();
    }

    public int m_92170_(float p_92171_) {
        return (int)(this.m_92141_(p_92171_) * 255.0f) << 24 & 0xFF000000;
    }

    public int m_92143_(int p_92144_) {
        return this.f_92050_.m_231551_() != false ? p_92144_ : (int)(this.f_92122_.m_231551_() * 255.0) << 24 & 0xFF000000;
    }

    public void m_92159_(KeyMapping p_92160_, InputConstants.Key p_92161_) {
        p_92160_.m_90848_(p_92161_);
        this.m_92169_();
    }

    private void m_168427_(FieldAccess p_168428_) {
        p_168428_.m_213982_("autoJump", this.f_92036_);
        p_168428_.m_213982_("autoSuggestions", this.f_92037_);
        p_168428_.m_213982_("chatColors", this.f_92038_);
        p_168428_.m_213982_("chatLinks", this.f_92039_);
        p_168428_.m_213982_("chatLinksPrompt", this.f_92040_);
        p_168428_.m_213982_("enableVsync", this.f_92041_);
        p_168428_.m_213982_("entityShadows", this.f_92042_);
        p_168428_.m_213982_("forceUnicodeFont", this.f_92043_);
        p_168428_.m_213982_("discrete_mouse_scroll", this.f_92045_);
        p_168428_.m_213982_("invertYMouse", this.f_92044_);
        p_168428_.m_213982_("realmsNotifications", this.f_92046_);
        p_168428_.m_213982_("reducedDebugInfo", this.f_92047_);
        p_168428_.m_213982_("showSubtitles", this.f_92049_);
        p_168428_.m_213982_("directionalAudio", this.f_231807_);
        p_168428_.m_213982_("touchscreen", this.f_92051_);
        p_168428_.m_213982_("fullscreen", this.f_92052_);
        p_168428_.m_213982_("bobView", this.f_92080_);
        p_168428_.m_213982_("toggleCrouch", this.f_92081_);
        p_168428_.m_213982_("toggleSprint", this.f_92082_);
        p_168428_.m_213982_("darkMojangStudiosBackground", this.f_168413_);
        p_168428_.m_213982_("hideLightningFlashes", this.f_231791_);
        p_168428_.m_213982_("mouseSensitivity", this.f_92053_);
        p_168428_.m_213982_("fov", this.f_92068_);
        p_168428_.m_213982_("screenEffectScale", this.f_92069_);
        p_168428_.m_213982_("fovEffectScale", this.f_92070_);
        p_168428_.m_213982_("darknessEffectScale", this.f_231802_);
        p_168428_.m_213982_("gamma", this.f_92071_);
        p_168428_.m_213982_("renderDistance", this.f_92106_);
        p_168428_.m_213982_("simulationDistance", this.f_193768_);
        p_168428_.m_213982_("entityDistanceScaling", this.f_92112_);
        p_168428_.m_213982_("guiScale", this.f_92072_);
        p_168428_.m_213982_("particles", this.f_92073_);
        p_168428_.m_213982_("maxFps", this.f_92113_);
        p_168428_.m_213982_("graphicsMode", this.f_92115_);
        p_168428_.m_213982_("ao", this.f_92116_);
        p_168428_.m_213982_("prioritizeChunkUpdates", this.f_193769_);
        p_168428_.m_213982_("biomeBlendRadius", this.f_92032_);
        p_168428_.m_213982_("renderClouds", this.f_231792_);
        this.f_92117_ = p_168428_.m_142634_("resourcePacks", this.f_92117_, Options::m_168442_, arg_0 -> ((Gson)f_92078_).toJson(arg_0));
        this.f_92118_ = p_168428_.m_142634_("incompatibleResourcePacks", this.f_92118_, Options::m_168442_, arg_0 -> ((Gson)f_92078_).toJson(arg_0));
        this.f_92066_ = p_168428_.m_141943_("lastServer", this.f_92066_);
        this.f_92075_ = p_168428_.m_141943_("lang", this.f_92075_);
        p_168428_.m_213982_("soundDevice", this.f_193764_);
        p_168428_.m_213982_("chatVisibility", this.f_92119_);
        p_168428_.m_213982_("chatOpacity", this.f_92120_);
        p_168428_.m_213982_("chatLineSpacing", this.f_92121_);
        p_168428_.m_213982_("textBackgroundOpacity", this.f_92122_);
        p_168428_.m_213982_("backgroundForChatOnly", this.f_92050_);
        this.f_92124_ = p_168428_.m_142682_("hideServerAddress", this.f_92124_);
        this.f_92125_ = p_168428_.m_142682_("advancedItemTooltips", this.f_92125_);
        this.f_92126_ = p_168428_.m_142682_("pauseOnLostFocus", this.f_92126_);
        this.f_92128_ = p_168428_.m_142708_("overrideWidth", this.f_92128_);
        this.f_92129_ = p_168428_.m_142708_("overrideHeight", this.f_92129_);
        this.f_92130_ = p_168428_.m_142682_("heldItemTooltips", this.f_92130_);
        p_168428_.m_213982_("chatHeightFocused", this.f_92134_);
        p_168428_.m_213982_("chatDelay", this.f_92135_);
        p_168428_.m_213982_("chatHeightUnfocused", this.f_92133_);
        p_168428_.m_213982_("chatScale", this.f_92131_);
        p_168428_.m_213982_("chatWidth", this.f_92132_);
        p_168428_.m_213982_("mipmapLevels", this.f_92027_);
        this.f_92028_ = p_168428_.m_142682_("useNativeTransport", this.f_92028_);
        p_168428_.m_213982_("mainHand", this.f_92127_);
        p_168428_.m_213982_("attackIndicator", this.f_92029_);
        p_168428_.m_213982_("narrator", this.f_231803_);
        this.f_92030_ = p_168428_.m_142634_("tutorialStep", this.f_92030_, TutorialSteps::m_120642_, TutorialSteps::m_120639_);
        p_168428_.m_213982_("mouseWheelSensitivity", this.f_92033_);
        p_168428_.m_213982_("rawMouseInput", this.f_92034_);
        this.f_92035_ = p_168428_.m_142708_("glDebugVerbosity", this.f_92035_);
        this.f_92083_ = p_168428_.m_142682_("skipMultiplayerWarning", this.f_92083_);
        this.f_210816_ = p_168428_.m_142682_("skipRealms32bitWarning", this.f_210816_);
        p_168428_.m_213982_("hideMatchedNames", this.f_92084_);
        this.f_92031_ = p_168428_.m_142682_("joinedFirstServer", this.f_92031_);
        this.f_168405_ = p_168428_.m_142682_("hideBundleTutorial", this.f_168405_);
        this.f_92076_ = p_168428_.m_142682_("syncChunkWrites", this.f_92076_);
        p_168428_.m_213982_("showAutosaveIndicator", this.f_193763_);
        p_168428_.m_213982_("allowServerListing", this.f_193762_);
        p_168428_.m_213982_("chatPreview", this.f_231796_);
        p_168428_.m_213982_("onlyShowSecureChat", this.f_231798_);
        for (KeyMapping keyMapping : this.f_92059_) {
            String $$3;
            String $$2 = keyMapping.m_90865_();
            if ($$2.equals($$3 = p_168428_.m_141943_("key_" + keyMapping.m_90860_(), $$2))) continue;
            keyMapping.m_90848_(InputConstants.m_84851_($$3));
        }
        for (SoundSource soundSource : SoundSource.values()) {
            this.f_92109_.computeFloat((Object)soundSource, (p_231866_, p_231867_) -> Float.valueOf(p_168428_.m_142432_("soundCategory_" + p_231866_.m_12676_(), p_231867_ != null ? p_231867_.floatValue() : 1.0f)));
        }
        for (PlayerModelPart playerModelPart : PlayerModelPart.values()) {
            boolean $$6 = this.f_92108_.contains((Object)playerModelPart);
            boolean $$7 = p_168428_.m_142682_("modelPart_" + playerModelPart.m_36446_(), $$6);
            if ($$7 == $$6) continue;
            this.m_92154_(playerModelPart, $$7);
        }
    }

    public void m_92140_() {
        try {
            if (!this.f_92110_.exists()) {
                return;
            }
            this.f_92109_.clear();
            CompoundTag $$0 = new CompoundTag();
            try (BufferedReader $$1 = Files.newReader((File)this.f_92110_, (Charset)Charsets.UTF_8);){
                $$1.lines().forEach(p_231896_ -> {
                    try {
                        Iterator $$2 = f_92107_.split((CharSequence)p_231896_).iterator();
                        $$0.m_128359_((String)$$2.next(), (String)$$2.next());
                    }
                    catch (Exception $$3) {
                        f_92077_.warn("Skipping bad option: {}", p_231896_);
                    }
                });
            }
            final CompoundTag $$2 = this.m_92164_($$0);
            if (!$$2.m_128441_("graphicsMode") && $$2.m_128441_("fancyGraphics")) {
                if (Options.m_168435_($$2.m_128461_("fancyGraphics"))) {
                    this.f_92115_.m_231514_(GraphicsStatus.FANCY);
                } else {
                    this.f_92115_.m_231514_(GraphicsStatus.FAST);
                }
            }
            this.m_168427_(new FieldAccess(){

                @Nullable
                private String m_168458_(String p_168459_) {
                    return $$2.m_128441_(p_168459_) ? $$2.m_128461_(p_168459_) : null;
                }

                @Override
                public <T> void m_213982_(String p_232125_, OptionInstance<T> p_232126_) {
                    String $$22 = this.m_168458_(p_232125_);
                    if ($$22 != null) {
                        JsonReader $$3 = new JsonReader((Reader)new StringReader($$22.isEmpty() ? "\"\"" : $$22));
                        JsonElement $$4 = JsonParser.parseReader((JsonReader)$$3);
                        DataResult $$5 = p_232126_.m_231554_().parse((DynamicOps)JsonOps.INSTANCE, (Object)$$4);
                        $$5.error().ifPresent(p_232130_ -> f_92077_.error("Error parsing option value " + $$22 + " for option " + p_232126_ + ": " + p_232130_.message()));
                        $$5.result().ifPresent(p_232126_::m_231514_);
                    }
                }

                @Override
                public int m_142708_(String p_168467_, int p_168468_) {
                    String $$22 = this.m_168458_(p_168467_);
                    if ($$22 != null) {
                        try {
                            return Integer.parseInt($$22);
                        }
                        catch (NumberFormatException $$3) {
                            f_92077_.warn("Invalid integer value for option {} = {}", new Object[]{p_168467_, $$22, $$3});
                        }
                    }
                    return p_168468_;
                }

                @Override
                public boolean m_142682_(String p_168483_, boolean p_168484_) {
                    String $$22 = this.m_168458_(p_168483_);
                    return $$22 != null ? Options.m_168435_($$22) : p_168484_;
                }

                @Override
                public String m_141943_(String p_168480_, String p_168481_) {
                    return (String)MoreObjects.firstNonNull((Object)this.m_168458_(p_168480_), (Object)p_168481_);
                }

                @Override
                public float m_142432_(String p_168464_, float p_168465_) {
                    String $$22 = this.m_168458_(p_168464_);
                    if ($$22 != null) {
                        if (Options.m_168435_($$22)) {
                            return 1.0f;
                        }
                        if (Options.m_168440_($$22)) {
                            return 0.0f;
                        }
                        try {
                            return Float.parseFloat($$22);
                        }
                        catch (NumberFormatException $$3) {
                            f_92077_.warn("Invalid floating point value for option {} = {}", new Object[]{p_168464_, $$22, $$3});
                        }
                    }
                    return p_168465_;
                }

                @Override
                public <T> T m_142634_(String p_168470_, T p_168471_, Function<String, T> p_168472_, Function<T, String> p_168473_) {
                    String $$4 = this.m_168458_(p_168470_);
                    return $$4 == null ? p_168471_ : p_168472_.apply($$4);
                }
            });
            if ($$2.m_128441_("fullscreenResolution")) {
                this.f_92123_ = $$2.m_128461_("fullscreenResolution");
            }
            if (this.f_92060_.m_91268_() != null) {
                this.f_92060_.m_91268_().m_85380_(this.f_92113_.m_231551_());
            }
            KeyMapping.m_90854_();
        }
        catch (Exception $$3) {
            f_92077_.error("Failed to load options", (Throwable)$$3);
        }
    }

    static boolean m_168435_(String p_168436_) {
        return "true".equals(p_168436_);
    }

    static boolean m_168440_(String p_168441_) {
        return "false".equals(p_168441_);
    }

    private CompoundTag m_92164_(CompoundTag p_92165_) {
        int $$1 = 0;
        try {
            $$1 = Integer.parseInt(p_92165_.m_128461_("version"));
        }
        catch (RuntimeException runtimeException) {
            // empty catch block
        }
        return NbtUtils.m_129213_(this.f_92060_.m_91295_(), DataFixTypes.OPTIONS, p_92165_, $$1);
    }

    public void m_92169_() {
        try (final PrintWriter $$0 = new PrintWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(this.f_92110_), StandardCharsets.UTF_8));){
            $$0.println("version:" + SharedConstants.m_183709_().getWorldVersion());
            this.m_168427_(new FieldAccess(){

                public void m_168490_(String p_168491_) {
                    $$0.print(p_168491_);
                    $$0.print(':');
                }

                @Override
                public <T> void m_213982_(String p_232135_, OptionInstance<T> p_232136_) {
                    DataResult $$2 = p_232136_.m_231554_().encodeStart((DynamicOps)JsonOps.INSTANCE, p_232136_.m_231551_());
                    $$2.error().ifPresent(p_232133_ -> f_92077_.error("Error saving option " + p_232136_ + ": " + p_232133_));
                    $$2.result().ifPresent(p_232140_ -> {
                        this.m_168490_(p_232135_);
                        $$0.println(f_92078_.toJson(p_232140_));
                    });
                }

                @Override
                public int m_142708_(String p_168499_, int p_168500_) {
                    this.m_168490_(p_168499_);
                    $$0.println(p_168500_);
                    return p_168500_;
                }

                @Override
                public boolean m_142682_(String p_168515_, boolean p_168516_) {
                    this.m_168490_(p_168515_);
                    $$0.println(p_168516_);
                    return p_168516_;
                }

                @Override
                public String m_141943_(String p_168512_, String p_168513_) {
                    this.m_168490_(p_168512_);
                    $$0.println(p_168513_);
                    return p_168513_;
                }

                @Override
                public float m_142432_(String p_168496_, float p_168497_) {
                    this.m_168490_(p_168496_);
                    $$0.println(p_168497_);
                    return p_168497_;
                }

                @Override
                public <T> T m_142634_(String p_168502_, T p_168503_, Function<String, T> p_168504_, Function<T, String> p_168505_) {
                    this.m_168490_(p_168502_);
                    $$0.println(p_168505_.apply(p_168503_));
                    return p_168503_;
                }
            });
            if (this.f_92060_.m_91268_().m_85436_().isPresent()) {
                $$0.println("fullscreenResolution:" + this.f_92060_.m_91268_().m_85436_().get().m_85342_());
            }
        }
        catch (Exception $$1) {
            f_92077_.error("Failed to save options", (Throwable)$$1);
        }
        this.m_92172_();
    }

    public float m_92147_(SoundSource p_92148_) {
        return this.f_92109_.getFloat((Object)p_92148_);
    }

    public void m_92149_(SoundSource p_92150_, float p_92151_) {
        this.f_92109_.put((Object)p_92150_, p_92151_);
        this.f_92060_.m_91106_().m_120358_(p_92150_, p_92151_);
    }

    public void m_92172_() {
        if (this.f_92060_.f_91074_ != null) {
            int $$0 = 0;
            for (PlayerModelPart $$1 : this.f_92108_) {
                $$0 |= $$1.m_36445_();
            }
            this.f_92060_.f_91074_.f_108617_.m_104955_(new ServerboundClientInformationPacket(this.f_92075_, this.f_92106_.m_231551_(), this.f_92119_.m_231551_(), this.f_92038_.m_231551_(), $$0, this.f_92127_.m_231551_(), this.f_92060_.m_167974_(), this.f_193762_.m_231551_()));
        }
    }

    private void m_92154_(PlayerModelPart p_92155_, boolean p_92156_) {
        if (p_92156_) {
            this.f_92108_.add(p_92155_);
        } else {
            this.f_92108_.remove((Object)p_92155_);
        }
    }

    public boolean m_168416_(PlayerModelPart p_168417_) {
        return this.f_92108_.contains((Object)p_168417_);
    }

    public void m_168418_(PlayerModelPart p_168419_, boolean p_168420_) {
        this.m_92154_(p_168419_, p_168420_);
        this.m_92172_();
    }

    public CloudStatus m_92174_() {
        if (this.m_193772_() >= 4) {
            return this.f_231792_.m_231551_();
        }
        return CloudStatus.OFF;
    }

    public boolean m_92175_() {
        return this.f_92028_;
    }

    public void m_92145_(PackRepository p_92146_) {
        LinkedHashSet $$1 = Sets.newLinkedHashSet();
        Iterator<String> $$2 = this.f_92117_.iterator();
        while ($$2.hasNext()) {
            String $$3 = $$2.next();
            Pack $$4 = p_92146_.m_10507_($$3);
            if ($$4 == null && !$$3.startsWith("file/")) {
                $$4 = p_92146_.m_10507_("file/" + $$3);
            }
            if ($$4 == null) {
                f_92077_.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", (Object)$$3);
                $$2.remove();
                continue;
            }
            if (!$$4.m_10443_().m_10489_() && !this.f_92118_.contains($$3)) {
                f_92077_.warn("Removed resource pack {} from options because it is no longer compatible", (Object)$$3);
                $$2.remove();
                continue;
            }
            if ($$4.m_10443_().m_10489_() && this.f_92118_.contains($$3)) {
                f_92077_.info("Removed resource pack {} from incompatibility list because it's now compatible", (Object)$$3);
                this.f_92118_.remove($$3);
                continue;
            }
            $$1.add($$4.m_10446_());
        }
        p_92146_.m_10509_($$1);
    }

    public CameraType m_92176_() {
        return this.f_92111_;
    }

    public void m_92157_(CameraType p_92158_) {
        this.f_92111_ = p_92158_;
    }

    private static List<String> m_168442_(String p_168443_) {
        ArrayList $$1 = GsonHelper.m_13785_(f_92078_, p_168443_, f_92079_);
        return $$1 != null ? $$1 : Lists.newArrayList();
    }

    public File m_168450_() {
        return this.f_92110_;
    }

    public String m_168451_() {
        Stream<Pair> $$0 = Stream.builder().add(Pair.of((Object)"ao", (Object)this.f_92116_.m_231551_())).add(Pair.of((Object)"biomeBlendRadius", (Object)this.f_92032_.m_231551_())).add(Pair.of((Object)"enableVsync", (Object)this.f_92041_.m_231551_())).add(Pair.of((Object)"entityDistanceScaling", (Object)this.f_92112_.m_231551_())).add(Pair.of((Object)"entityShadows", (Object)this.f_92042_.m_231551_())).add(Pair.of((Object)"forceUnicodeFont", (Object)this.f_92043_.m_231551_())).add(Pair.of((Object)"fov", (Object)this.f_92068_.m_231551_())).add(Pair.of((Object)"fovEffectScale", (Object)this.f_92070_.m_231551_())).add(Pair.of((Object)"darknessEffectScale", (Object)this.f_231802_.m_231551_())).add(Pair.of((Object)"prioritizeChunkUpdates", (Object)this.f_193769_.m_231551_())).add(Pair.of((Object)"fullscreen", (Object)this.f_92052_.m_231551_())).add(Pair.of((Object)"fullscreenResolution", (Object)String.valueOf(this.f_92123_))).add(Pair.of((Object)"gamma", (Object)this.f_92071_.m_231551_())).add(Pair.of((Object)"glDebugVerbosity", (Object)this.f_92035_)).add(Pair.of((Object)"graphicsMode", (Object)this.f_92115_.m_231551_())).add(Pair.of((Object)"guiScale", (Object)this.f_92072_.m_231551_())).add(Pair.of((Object)"maxFps", (Object)this.f_92113_.m_231551_())).add(Pair.of((Object)"mipmapLevels", (Object)this.f_92027_.m_231551_())).add(Pair.of((Object)"narrator", (Object)((Object)this.f_231803_.m_231551_()))).add(Pair.of((Object)"overrideHeight", (Object)this.f_92129_)).add(Pair.of((Object)"overrideWidth", (Object)this.f_92128_)).add(Pair.of((Object)"particles", (Object)this.f_92073_.m_231551_())).add(Pair.of((Object)"reducedDebugInfo", (Object)this.f_92047_.m_231551_())).add(Pair.of((Object)"renderClouds", (Object)this.f_231792_.m_231551_())).add(Pair.of((Object)"renderDistance", (Object)this.f_92106_.m_231551_())).add(Pair.of((Object)"simulationDistance", (Object)this.f_193768_.m_231551_())).add(Pair.of((Object)"resourcePacks", this.f_92117_)).add(Pair.of((Object)"screenEffectScale", (Object)this.f_92069_.m_231551_())).add(Pair.of((Object)"syncChunkWrites", (Object)this.f_92076_)).add(Pair.of((Object)"useNativeTransport", (Object)this.f_92028_)).add(Pair.of((Object)"soundDevice", (Object)this.f_193764_.m_231551_())).build();
        return $$0.map(p_231848_ -> (String)p_231848_.getFirst() + ": " + p_231848_.getSecond()).collect(Collectors.joining(System.lineSeparator()));
    }

    public void m_193770_(int p_193771_) {
        this.f_193765_ = p_193771_;
    }

    public int m_193772_() {
        return this.f_193765_ > 0 ? Math.min(this.f_92106_.m_231551_(), this.f_193765_) : this.f_92106_.m_231551_();
    }

    private static Component m_231952_(Component p_231953_, int p_231954_) {
        return Component.m_237110_("options.pixel_value", p_231953_, p_231954_);
    }

    private static Component m_231897_(Component p_231898_, double p_231899_) {
        return Component.m_237110_("options.percent_value", p_231898_, (int)(p_231899_ * 100.0));
    }

    public static Component m_231921_(Component p_231922_, Component p_231923_) {
        return Component.m_237110_("options.generic_value", p_231922_, p_231923_);
    }

    public static Component m_231900_(Component p_231901_, int p_231902_) {
        return Options.m_231921_(p_231901_, Component.m_237113_(Integer.toString(p_231902_)));
    }

    static interface FieldAccess {
        public <T> void m_213982_(String var1, OptionInstance<T> var2);

        public int m_142708_(String var1, int var2);

        public boolean m_142682_(String var1, boolean var2);

        public String m_141943_(String var1, String var2);

        public float m_142432_(String var1, float var2);

        public <T> T m_142634_(String var1, T var2, Function<String, T> var3, Function<T, String> var4);
    }
}

