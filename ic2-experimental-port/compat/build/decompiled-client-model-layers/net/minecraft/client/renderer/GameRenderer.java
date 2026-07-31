/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class GameRenderer
implements ResourceManagerReloadListener,
AutoCloseable {
    private static final ResourceLocation f_109057_ = new ResourceLocation("textures/misc/nausea.png");
    private static final Logger f_109058_ = LogUtils.getLogger();
    private static final boolean f_172636_ = false;
    public static final float f_172592_ = 0.05f;
    private final Minecraft f_109059_;
    private final ResourceManager f_109060_;
    private final RandomSource f_109061_ = RandomSource.m_216327_();
    private float f_109062_;
    public final ItemInHandRenderer f_109055_;
    private final MapRenderer f_109063_;
    private final RenderBuffers f_109064_;
    private int f_109065_;
    private float f_109066_;
    private float f_109067_;
    private float f_109068_;
    private float f_109069_;
    private boolean f_109070_ = true;
    private boolean f_109071_ = true;
    private long f_109072_;
    private boolean f_182638_;
    private long f_109073_ = Util.m_137550_();
    private final LightTexture f_109074_;
    private final OverlayTexture f_109075_ = new OverlayTexture();
    private boolean f_109076_;
    private float f_109077_ = 1.0f;
    private float f_109078_;
    private float f_109079_;
    public static final int f_172634_ = 40;
    @Nullable
    private ItemStack f_109080_;
    private int f_109047_;
    private float f_109048_;
    private float f_109049_;
    @Nullable
    private PostChain f_109050_;
    private static final ResourceLocation[] f_109051_ = new ResourceLocation[]{new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json")};
    public static final int f_109056_ = f_109051_.length;
    private int f_109052_ = f_109056_;
    private boolean f_109053_;
    private final Camera f_109054_ = new Camera();
    public ShaderInstance f_172635_;
    private final Map<String, ShaderInstance> f_172578_ = Maps.newHashMap();
    @Nullable
    private static ShaderInstance f_172579_;
    @Nullable
    private static ShaderInstance f_172580_;
    @Nullable
    private static ShaderInstance f_172581_;
    @Nullable
    private static ShaderInstance f_172582_;
    @Nullable
    private static ShaderInstance f_172583_;
    @Nullable
    private static ShaderInstance f_172584_;
    @Nullable
    private static ShaderInstance f_172585_;
    @Nullable
    private static ShaderInstance f_172586_;
    @Nullable
    private static ShaderInstance f_172587_;
    @Nullable
    private static ShaderInstance f_172588_;
    @Nullable
    private static ShaderInstance f_172589_;
    @Nullable
    private static ShaderInstance f_172590_;
    @Nullable
    private static ShaderInstance f_172591_;
    @Nullable
    private static ShaderInstance f_172608_;
    @Nullable
    private static ShaderInstance f_172609_;
    @Nullable
    private static ShaderInstance f_172610_;
    @Nullable
    private static ShaderInstance f_172611_;
    @Nullable
    private static ShaderInstance f_172612_;
    @Nullable
    private static ShaderInstance f_172613_;
    @Nullable
    private static ShaderInstance f_172614_;
    @Nullable
    private static ShaderInstance f_172615_;
    @Nullable
    private static ShaderInstance f_172616_;
    @Nullable
    private static ShaderInstance f_172617_;
    @Nullable
    private static ShaderInstance f_172618_;
    @Nullable
    private static ShaderInstance f_172619_;
    @Nullable
    private static ShaderInstance f_172620_;
    @Nullable
    private static ShaderInstance f_234217_;
    @Nullable
    private static ShaderInstance f_172621_;
    @Nullable
    private static ShaderInstance f_172622_;
    @Nullable
    private static ShaderInstance f_172623_;
    @Nullable
    private static ShaderInstance f_172624_;
    @Nullable
    private static ShaderInstance f_172625_;
    @Nullable
    private static ShaderInstance f_172626_;
    @Nullable
    private static ShaderInstance f_172627_;
    @Nullable
    private static ShaderInstance f_172628_;
    @Nullable
    private static ShaderInstance f_172629_;
    @Nullable
    private static ShaderInstance f_172630_;
    @Nullable
    private static ShaderInstance f_172631_;
    @Nullable
    private static ShaderInstance f_172632_;
    @Nullable
    private static ShaderInstance f_172633_;
    @Nullable
    private static ShaderInstance f_172593_;
    @Nullable
    private static ShaderInstance f_172594_;
    @Nullable
    private static ShaderInstance f_172595_;
    @Nullable
    private static ShaderInstance f_172596_;
    @Nullable
    private static ShaderInstance f_172597_;
    @Nullable
    private static ShaderInstance f_172598_;
    @Nullable
    private static ShaderInstance f_172599_;
    @Nullable
    private static ShaderInstance f_172600_;
    @Nullable
    private static ShaderInstance f_172601_;
    @Nullable
    private static ShaderInstance f_172602_;
    @Nullable
    private static ShaderInstance f_172603_;
    @Nullable
    private static ShaderInstance f_172604_;
    @Nullable
    private static ShaderInstance f_172605_;
    @Nullable
    private static ShaderInstance f_172606_;
    @Nullable
    private static ShaderInstance f_172607_;

    public GameRenderer(Minecraft p_234219_, ItemInHandRenderer p_234220_, ResourceManager p_234221_, RenderBuffers p_234222_) {
        this.f_109059_ = p_234219_;
        this.f_109060_ = p_234221_;
        this.f_109055_ = p_234220_;
        this.f_109063_ = new MapRenderer(p_234219_.m_91097_());
        this.f_109074_ = new LightTexture(this, p_234219_);
        this.f_109064_ = p_234222_;
        this.f_109050_ = null;
    }

    @Override
    public void close() {
        this.f_109074_.close();
        this.f_109063_.close();
        this.f_109075_.close();
        this.m_109086_();
        this.m_172759_();
        if (this.f_172635_ != null) {
            this.f_172635_.close();
        }
    }

    public void m_172736_(boolean p_172737_) {
        this.f_109070_ = p_172737_;
    }

    public void m_172775_(boolean p_172776_) {
        this.f_109071_ = p_172776_;
    }

    public void m_172779_(boolean p_172780_) {
        this.f_109076_ = p_172780_;
    }

    public boolean m_172715_() {
        return this.f_109076_;
    }

    public void m_109086_() {
        if (this.f_109050_ != null) {
            this.f_109050_.close();
        }
        this.f_109050_ = null;
        this.f_109052_ = f_109056_;
    }

    public void m_109130_() {
        this.f_109053_ = !this.f_109053_;
    }

    public void m_109106_(@Nullable Entity p_109107_) {
        if (this.f_109050_ != null) {
            this.f_109050_.close();
        }
        this.f_109050_ = null;
        if (p_109107_ instanceof Creeper) {
            this.m_109128_(new ResourceLocation("shaders/post/creeper.json"));
        } else if (p_109107_ instanceof Spider) {
            this.m_109128_(new ResourceLocation("shaders/post/spider.json"));
        } else if (p_109107_ instanceof EnderMan) {
            this.m_109128_(new ResourceLocation("shaders/post/invert.json"));
        }
    }

    public void m_172783_() {
        if (!(this.f_109059_.m_91288_() instanceof Player)) {
            return;
        }
        if (this.f_109050_ != null) {
            this.f_109050_.close();
        }
        this.f_109052_ = (this.f_109052_ + 1) % (f_109051_.length + 1);
        if (this.f_109052_ == f_109056_) {
            this.f_109050_ = null;
        } else {
            this.m_109128_(f_109051_[this.f_109052_]);
        }
    }

    private void m_109128_(ResourceLocation p_109129_) {
        if (this.f_109050_ != null) {
            this.f_109050_.close();
        }
        try {
            this.f_109050_ = new PostChain(this.f_109059_.m_91097_(), this.f_109060_, this.f_109059_.m_91385_(), p_109129_);
            this.f_109050_.m_110025_(this.f_109059_.m_91268_().m_85441_(), this.f_109059_.m_91268_().m_85442_());
            this.f_109053_ = true;
        }
        catch (IOException $$1) {
            f_109058_.warn("Failed to load shader: {}", (Object)p_109129_, (Object)$$1);
            this.f_109052_ = f_109056_;
            this.f_109053_ = false;
        }
        catch (JsonSyntaxException $$2) {
            f_109058_.warn("Failed to parse shader: {}", (Object)p_109129_, (Object)$$2);
            this.f_109052_ = f_109056_;
            this.f_109053_ = false;
        }
    }

    @Override
    public void m_6213_(ResourceManager p_109105_) {
        this.m_172767_(p_109105_);
        if (this.f_109050_ != null) {
            this.f_109050_.close();
        }
        this.f_109050_ = null;
        if (this.f_109052_ == f_109056_) {
            this.m_109106_(this.f_109059_.m_91288_());
        } else {
            this.m_109128_(f_109051_[this.f_109052_]);
        }
    }

    public void m_172722_(ResourceProvider p_172723_) {
        if (this.f_172635_ != null) {
            throw new RuntimeException("Blit shader already preloaded");
        }
        try {
            this.f_172635_ = new ShaderInstance(p_172723_, "blit_screen", DefaultVertexFormat.f_166850_);
        }
        catch (IOException $$1) {
            throw new RuntimeException("could not preload blit shader", $$1);
        }
        f_172579_ = this.m_172724_(p_172723_, "position", DefaultVertexFormat.f_85814_);
        f_172580_ = this.m_172724_(p_172723_, "position_color", DefaultVertexFormat.f_85815_);
        f_172581_ = this.m_172724_(p_172723_, "position_color_tex", DefaultVertexFormat.f_85818_);
        f_172582_ = this.m_172724_(p_172723_, "position_tex", DefaultVertexFormat.f_85817_);
        f_172583_ = this.m_172724_(p_172723_, "position_tex_color", DefaultVertexFormat.f_85819_);
        f_172598_ = this.m_172724_(p_172723_, "rendertype_text", DefaultVertexFormat.f_85820_);
    }

    private ShaderInstance m_172724_(ResourceProvider p_172725_, String p_172726_, VertexFormat p_172727_) {
        try {
            ShaderInstance $$3 = new ShaderInstance(p_172725_, p_172726_, p_172727_);
            this.f_172578_.put(p_172726_, $$3);
            return $$3;
        }
        catch (Exception $$4) {
            throw new IllegalStateException("could not preload shader " + p_172726_, $$4);
        }
    }

    public void m_172767_(ResourceManager p_172768_) {
        RenderSystem.m_187554_();
        ArrayList $$1 = Lists.newArrayList();
        $$1.addAll(Program.Type.FRAGMENT.m_85570_().values());
        $$1.addAll(Program.Type.VERTEX.m_85570_().values());
        $$1.forEach(Program::m_85543_);
        ArrayList $$2 = Lists.newArrayListWithCapacity((int)this.f_172578_.size());
        try {
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "block", DefaultVertexFormat.f_85811_), p_172743_ -> {
                f_172584_ = p_172743_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "new_entity", DefaultVertexFormat.f_85812_), p_172740_ -> {
                f_172585_ = p_172740_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "particle", DefaultVertexFormat.f_85813_), p_172714_ -> {
                f_172586_ = p_172714_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position", DefaultVertexFormat.f_85814_), p_172711_ -> {
                f_172579_ = p_172711_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_color", DefaultVertexFormat.f_85815_), p_172708_ -> {
                f_172580_ = p_172708_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_color_lightmap", DefaultVertexFormat.f_85816_), p_172705_ -> {
                f_172587_ = p_172705_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_color_tex", DefaultVertexFormat.f_85818_), p_172702_ -> {
                f_172581_ = p_172702_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_color_tex_lightmap", DefaultVertexFormat.f_85820_), p_172699_ -> {
                f_172588_ = p_172699_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_tex", DefaultVertexFormat.f_85817_), p_172696_ -> {
                f_172582_ = p_172696_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_tex_color", DefaultVertexFormat.f_85819_), p_172693_ -> {
                f_172583_ = p_172693_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_tex_color_normal", DefaultVertexFormat.f_85822_), p_172690_ -> {
                f_172589_ = p_172690_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "position_tex_lightmap_color", DefaultVertexFormat.f_85821_), p_172687_ -> {
                f_172590_ = p_172687_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_solid", DefaultVertexFormat.f_85811_), p_172684_ -> {
                f_172591_ = p_172684_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_cutout_mipped", DefaultVertexFormat.f_85811_), p_172681_ -> {
                f_172608_ = p_172681_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_cutout", DefaultVertexFormat.f_85811_), p_172678_ -> {
                f_172609_ = p_172678_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_translucent", DefaultVertexFormat.f_85811_), p_172675_ -> {
                f_172610_ = p_172675_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_translucent_moving_block", DefaultVertexFormat.f_85811_), p_172672_ -> {
                f_172611_ = p_172672_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_translucent_no_crumbling", DefaultVertexFormat.f_85811_), p_172669_ -> {
                f_172612_ = p_172669_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_armor_cutout_no_cull", DefaultVertexFormat.f_85812_), p_172666_ -> {
                f_172613_ = p_172666_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_solid", DefaultVertexFormat.f_85812_), p_172663_ -> {
                f_172614_ = p_172663_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_cutout", DefaultVertexFormat.f_85812_), p_172660_ -> {
                f_172615_ = p_172660_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_cutout_no_cull", DefaultVertexFormat.f_85812_), p_172657_ -> {
                f_172616_ = p_172657_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_cutout_no_cull_z_offset", DefaultVertexFormat.f_85812_), p_172654_ -> {
                f_172617_ = p_172654_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_item_entity_translucent_cull", DefaultVertexFormat.f_85812_), p_172651_ -> {
                f_172618_ = p_172651_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_translucent_cull", DefaultVertexFormat.f_85812_), p_172648_ -> {
                f_172619_ = p_172648_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_translucent", DefaultVertexFormat.f_85812_), p_172645_ -> {
                f_172620_ = p_172645_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_translucent_emissive", DefaultVertexFormat.f_85812_), p_172642_ -> {
                f_234217_ = p_172642_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_smooth_cutout", DefaultVertexFormat.f_85812_), p_172639_ -> {
                f_172621_ = p_172639_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_beacon_beam", DefaultVertexFormat.f_85811_), p_172840_ -> {
                f_172622_ = p_172840_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_decal", DefaultVertexFormat.f_85812_), p_172837_ -> {
                f_172623_ = p_172837_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_no_outline", DefaultVertexFormat.f_85812_), p_172834_ -> {
                f_172624_ = p_172834_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_shadow", DefaultVertexFormat.f_85812_), p_172831_ -> {
                f_172625_ = p_172831_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_alpha", DefaultVertexFormat.f_85812_), p_172828_ -> {
                f_172626_ = p_172828_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_eyes", DefaultVertexFormat.f_85812_), p_172825_ -> {
                f_172627_ = p_172825_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_energy_swirl", DefaultVertexFormat.f_85812_), p_172822_ -> {
                f_172628_ = p_172822_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_leash", DefaultVertexFormat.f_85816_), p_172819_ -> {
                f_172629_ = p_172819_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_water_mask", DefaultVertexFormat.f_85814_), p_172816_ -> {
                f_172630_ = p_172816_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_outline", DefaultVertexFormat.f_85818_), p_172813_ -> {
                f_172631_ = p_172813_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_armor_glint", DefaultVertexFormat.f_85817_), p_172810_ -> {
                f_172632_ = p_172810_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_armor_entity_glint", DefaultVertexFormat.f_85817_), p_172807_ -> {
                f_172633_ = p_172807_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_glint_translucent", DefaultVertexFormat.f_85817_), p_172805_ -> {
                f_172593_ = p_172805_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_glint", DefaultVertexFormat.f_85817_), p_172803_ -> {
                f_172594_ = p_172803_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_glint_direct", DefaultVertexFormat.f_85817_), p_172801_ -> {
                f_172595_ = p_172801_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_glint", DefaultVertexFormat.f_85817_), p_172799_ -> {
                f_172596_ = p_172799_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_entity_glint_direct", DefaultVertexFormat.f_85817_), p_172796_ -> {
                f_172597_ = p_172796_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_text", DefaultVertexFormat.f_85820_), p_172794_ -> {
                f_172598_ = p_172794_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_text_intensity", DefaultVertexFormat.f_85820_), p_172792_ -> {
                f_172599_ = p_172792_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_text_see_through", DefaultVertexFormat.f_85820_), p_172789_ -> {
                f_172600_ = p_172789_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_text_intensity_see_through", DefaultVertexFormat.f_85820_), p_172787_ -> {
                f_172601_ = p_172787_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_lightning", DefaultVertexFormat.f_85815_), p_172785_ -> {
                f_172602_ = p_172785_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_tripwire", DefaultVertexFormat.f_85811_), p_172782_ -> {
                f_172603_ = p_172782_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_end_portal", DefaultVertexFormat.f_85814_), p_172778_ -> {
                f_172604_ = p_172778_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_end_gateway", DefaultVertexFormat.f_85814_), p_172774_ -> {
                f_172605_ = p_172774_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_lines", DefaultVertexFormat.f_166851_), p_172733_ -> {
                f_172606_ = p_172733_;
            }));
            $$2.add(Pair.of((Object)new ShaderInstance(p_172768_, "rendertype_crumbling", DefaultVertexFormat.f_85811_), p_234230_ -> {
                f_172607_ = p_234230_;
            }));
        }
        catch (IOException $$3) {
            $$2.forEach(p_172729_ -> ((ShaderInstance)p_172729_.getFirst()).close());
            throw new RuntimeException("could not reload shaders", $$3);
        }
        this.m_172759_();
        $$2.forEach(p_234225_ -> {
            ShaderInstance $$1 = (ShaderInstance)p_234225_.getFirst();
            this.f_172578_.put($$1.m_173365_(), $$1);
            ((Consumer)p_234225_.getSecond()).accept($$1);
        });
    }

    private void m_172759_() {
        RenderSystem.m_187554_();
        this.f_172578_.values().forEach(ShaderInstance::close);
        this.f_172578_.clear();
    }

    @Nullable
    public ShaderInstance m_172734_(@Nullable String p_172735_) {
        if (p_172735_ == null) {
            return null;
        }
        return this.f_172578_.get(p_172735_);
    }

    public void m_109148_() {
        this.m_109156_();
        this.f_109074_.m_109880_();
        if (this.f_109059_.m_91288_() == null) {
            this.f_109059_.m_91118_(this.f_109059_.f_91074_);
        }
        this.f_109054_.m_90565_();
        ++this.f_109065_;
        this.f_109055_.m_109311_();
        this.f_109059_.f_91060_.m_109693_(this.f_109054_);
        this.f_109069_ = this.f_109068_;
        if (this.f_109059_.f_91065_.m_93090_().m_93714_()) {
            this.f_109068_ += 0.05f;
            if (this.f_109068_ > 1.0f) {
                this.f_109068_ = 1.0f;
            }
        } else if (this.f_109068_ > 0.0f) {
            this.f_109068_ -= 0.0125f;
        }
        if (this.f_109047_ > 0) {
            --this.f_109047_;
            if (this.f_109047_ == 0) {
                this.f_109080_ = null;
            }
        }
    }

    @Nullable
    public PostChain m_109149_() {
        return this.f_109050_;
    }

    public void m_109097_(int p_109098_, int p_109099_) {
        if (this.f_109050_ != null) {
            this.f_109050_.m_110025_(p_109098_, p_109099_);
        }
        this.f_109059_.f_91060_.m_109487_(p_109098_, p_109099_);
    }

    public void m_109087_(float p_109088_) {
        Entity $$1 = this.f_109059_.m_91288_();
        if ($$1 == null) {
            return;
        }
        if (this.f_109059_.f_91073_ == null) {
            return;
        }
        this.f_109059_.m_91307_().m_6180_("pick");
        this.f_109059_.f_91076_ = null;
        double $$2 = this.f_109059_.f_91072_.m_105286_();
        this.f_109059_.f_91077_ = $$1.m_19907_($$2, p_109088_, false);
        Vec3 $$3 = $$1.m_20299_(p_109088_);
        boolean $$4 = false;
        int $$5 = 3;
        double $$6 = $$2;
        if (this.f_109059_.f_91072_.m_105291_()) {
            $$2 = $$6 = 6.0;
        } else {
            if ($$6 > 3.0) {
                $$4 = true;
            }
            $$2 = $$6;
        }
        $$6 *= $$6;
        if (this.f_109059_.f_91077_ != null) {
            $$6 = this.f_109059_.f_91077_.m_82450_().m_82557_($$3);
        }
        Vec3 $$7 = $$1.m_20252_(1.0f);
        Vec3 $$8 = $$3.m_82520_($$7.f_82479_ * $$2, $$7.f_82480_ * $$2, $$7.f_82481_ * $$2);
        float $$9 = 1.0f;
        AABB $$10 = $$1.m_20191_().m_82369_($$7.m_82490_($$2)).m_82377_(1.0, 1.0, 1.0);
        EntityHitResult $$11 = ProjectileUtil.m_37287_($$1, $$3, $$8, $$10, p_234237_ -> !p_234237_.m_5833_() && p_234237_.m_6087_(), $$6);
        if ($$11 != null) {
            Entity $$12 = $$11.m_82443_();
            Vec3 $$13 = $$11.m_82450_();
            double $$14 = $$3.m_82557_($$13);
            if ($$4 && $$14 > 9.0) {
                this.f_109059_.f_91077_ = BlockHitResult.m_82426_($$13, Direction.m_122366_($$7.f_82479_, $$7.f_82480_, $$7.f_82481_), new BlockPos($$13));
            } else if ($$14 < $$6 || this.f_109059_.f_91077_ == null) {
                this.f_109059_.f_91077_ = $$11;
                if ($$12 instanceof LivingEntity || $$12 instanceof ItemFrame) {
                    this.f_109059_.f_91076_ = $$12;
                }
            }
        }
        this.f_109059_.m_91307_().m_7238_();
    }

    private void m_109156_() {
        float $$0 = 1.0f;
        if (this.f_109059_.m_91288_() instanceof AbstractClientPlayer) {
            AbstractClientPlayer $$1 = (AbstractClientPlayer)this.f_109059_.m_91288_();
            $$0 = $$1.m_108565_();
        }
        this.f_109067_ = this.f_109066_;
        this.f_109066_ += ($$0 - this.f_109066_) * 0.5f;
        if (this.f_109066_ > 1.5f) {
            this.f_109066_ = 1.5f;
        }
        if (this.f_109066_ < 0.1f) {
            this.f_109066_ = 0.1f;
        }
    }

    private double m_109141_(Camera p_109142_, float p_109143_, boolean p_109144_) {
        FogType $$5;
        if (this.f_109076_) {
            return 90.0;
        }
        double $$3 = 70.0;
        if (p_109144_) {
            $$3 = this.f_109059_.f_91066_.m_231837_().m_231551_().intValue();
            $$3 *= (double)Mth.m_14179_(p_109143_, this.f_109067_, this.f_109066_);
        }
        if (p_109142_.m_90592_() instanceof LivingEntity && ((LivingEntity)p_109142_.m_90592_()).m_21224_()) {
            float $$4 = Math.min((float)((LivingEntity)p_109142_.m_90592_()).f_20919_ + p_109143_, 20.0f);
            $$3 /= (double)((1.0f - 500.0f / ($$4 + 500.0f)) * 2.0f + 1.0f);
        }
        if (($$5 = p_109142_.m_167685_()) == FogType.LAVA || $$5 == FogType.WATER) {
            $$3 *= Mth.m_14139_(this.f_109059_.f_91066_.m_231925_().m_231551_(), 1.0, 0.8571428656578064);
        }
        return $$3;
    }

    private void m_109117_(PoseStack p_109118_, float p_109119_) {
        if (this.f_109059_.m_91288_() instanceof LivingEntity) {
            LivingEntity $$2 = (LivingEntity)this.f_109059_.m_91288_();
            float $$3 = (float)$$2.f_20916_ - p_109119_;
            if ($$2.m_21224_()) {
                float $$4 = Math.min((float)$$2.f_20919_ + p_109119_, 20.0f);
                p_109118_.m_85845_(Vector3f.f_122227_.m_122240_(40.0f - 8000.0f / ($$4 + 200.0f)));
            }
            if ($$3 < 0.0f) {
                return;
            }
            $$3 /= (float)$$2.f_20917_;
            $$3 = Mth.m_14031_($$3 * $$3 * $$3 * $$3 * (float)Math.PI);
            float $$5 = $$2.f_20918_;
            p_109118_.m_85845_(Vector3f.f_122225_.m_122240_(-$$5));
            p_109118_.m_85845_(Vector3f.f_122227_.m_122240_(-$$3 * 14.0f));
            p_109118_.m_85845_(Vector3f.f_122225_.m_122240_($$5));
        }
    }

    private void m_109138_(PoseStack p_109139_, float p_109140_) {
        if (!(this.f_109059_.m_91288_() instanceof Player)) {
            return;
        }
        Player $$2 = (Player)this.f_109059_.m_91288_();
        float $$3 = $$2.f_19787_ - $$2.f_19867_;
        float $$4 = -($$2.f_19787_ + $$3 * p_109140_);
        float $$5 = Mth.m_14179_(p_109140_, $$2.f_36099_, $$2.f_36100_);
        p_109139_.m_85837_(Mth.m_14031_($$4 * (float)Math.PI) * $$5 * 0.5f, -Math.abs(Mth.m_14089_($$4 * (float)Math.PI) * $$5), 0.0);
        p_109139_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14031_($$4 * (float)Math.PI) * $$5 * 3.0f));
        p_109139_.m_85845_(Vector3f.f_122223_.m_122240_(Math.abs(Mth.m_14089_($$4 * (float)Math.PI - 0.2f) * $$5) * 5.0f));
    }

    public void m_172718_(float p_172719_, float p_172720_, float p_172721_) {
        this.f_109077_ = p_172719_;
        this.f_109078_ = p_172720_;
        this.f_109079_ = p_172721_;
        this.m_172775_(false);
        this.m_172736_(false);
        this.m_109089_(1.0f, 0L, new PoseStack());
        this.f_109077_ = 1.0f;
    }

    private void m_109120_(PoseStack p_109121_, Camera p_109122_, float p_109123_) {
        boolean $$4;
        if (this.f_109076_) {
            return;
        }
        this.m_109111_(this.m_172716_(this.m_109141_(p_109122_, p_109123_, false)));
        PoseStack.Pose $$3 = p_109121_.m_85850_();
        $$3.m_85861_().m_27624_();
        $$3.m_85864_().m_8180_();
        p_109121_.m_85836_();
        this.m_109117_(p_109121_, p_109123_);
        if (this.f_109059_.f_91066_.m_231830_().m_231551_().booleanValue()) {
            this.m_109138_(p_109121_, p_109123_);
        }
        boolean bl = $$4 = this.f_109059_.m_91288_() instanceof LivingEntity && ((LivingEntity)this.f_109059_.m_91288_()).m_5803_();
        if (this.f_109059_.f_91066_.m_92176_().m_90612_() && !$$4 && !this.f_109059_.f_91066_.f_92062_ && this.f_109059_.f_91072_.m_105295_() != GameType.SPECTATOR) {
            this.f_109074_.m_109896_();
            this.f_109055_.m_109314_(p_109123_, p_109121_, this.f_109064_.m_110104_(), this.f_109059_.f_91074_, this.f_109059_.m_91290_().m_114394_(this.f_109059_.f_91074_, p_109123_));
            this.f_109074_.m_109891_();
        }
        p_109121_.m_85849_();
        if (this.f_109059_.f_91066_.m_92176_().m_90612_() && !$$4) {
            ScreenEffectRenderer.m_110718_(this.f_109059_, p_109121_);
            this.m_109117_(p_109121_, p_109123_);
        }
        if (this.f_109059_.f_91066_.m_231830_().m_231551_().booleanValue()) {
            this.m_109138_(p_109121_, p_109123_);
        }
    }

    public void m_109111_(Matrix4f p_109112_) {
        RenderSystem.m_157425_(p_109112_);
    }

    public Matrix4f m_172716_(double p_172717_) {
        PoseStack $$1 = new PoseStack();
        $$1.m_85850_().m_85861_().m_27624_();
        if (this.f_109077_ != 1.0f) {
            $$1.m_85837_(this.f_109078_, -this.f_109079_, 0.0);
            $$1.m_85841_(this.f_109077_, this.f_109077_, 1.0f);
        }
        $$1.m_85850_().m_85861_().m_27644_(Matrix4f.m_27625_(p_172717_, (float)this.f_109059_.m_91268_().m_85441_() / (float)this.f_109059_.m_91268_().m_85442_(), 0.05f, this.m_172790_()));
        return $$1.m_85850_().m_85861_();
    }

    public float m_172790_() {
        return this.f_109062_ * 4.0f;
    }

    public static float m_109108_(LivingEntity p_109109_, float p_109110_) {
        int $$2 = p_109109_.m_21124_(MobEffects.f_19611_).m_19557_();
        if ($$2 > 200) {
            return 1.0f;
        }
        return 0.7f + Mth.m_14031_(((float)$$2 - p_109110_) * (float)Math.PI * 0.2f) * 0.3f;
    }

    public void m_109093_(float p_109094_, long p_109095_, boolean p_109096_) {
        if (this.f_109059_.m_91302_() || !this.f_109059_.f_91066_.f_92126_ || this.f_109059_.f_91066_.m_231828_().m_231551_().booleanValue() && this.f_109059_.f_91067_.m_91584_()) {
            this.f_109073_ = Util.m_137550_();
        } else if (Util.m_137550_() - this.f_109073_ > 500L) {
            this.f_109059_.m_91358_(false);
        }
        if (this.f_109059_.f_91079_) {
            return;
        }
        int $$3 = (int)(this.f_109059_.f_91067_.m_91589_() * (double)this.f_109059_.m_91268_().m_85445_() / (double)this.f_109059_.m_91268_().m_85443_());
        int $$4 = (int)(this.f_109059_.f_91067_.m_91594_() * (double)this.f_109059_.m_91268_().m_85446_() / (double)this.f_109059_.m_91268_().m_85444_());
        RenderSystem.m_69949_(0, 0, this.f_109059_.m_91268_().m_85441_(), this.f_109059_.m_91268_().m_85442_());
        if (p_109096_ && this.f_109059_.f_91073_ != null) {
            this.f_109059_.m_91307_().m_6180_("level");
            this.m_109089_(p_109094_, p_109095_, new PoseStack());
            this.m_182644_();
            this.f_109059_.f_91060_.m_109769_();
            if (this.f_109050_ != null && this.f_109053_) {
                RenderSystem.m_69461_();
                RenderSystem.m_69465_();
                RenderSystem.m_69493_();
                RenderSystem.m_157423_();
                this.f_109050_.m_110023_(p_109094_);
            }
            this.f_109059_.m_91385_().m_83947_(true);
        }
        Window $$5 = this.f_109059_.m_91268_();
        RenderSystem.m_69421_(256, Minecraft.f_91002_);
        Matrix4f $$6 = Matrix4f.m_162203_(0.0f, (float)((double)$$5.m_85441_() / $$5.m_85449_()), 0.0f, (float)((double)$$5.m_85442_() / $$5.m_85449_()), 1000.0f, 3000.0f);
        RenderSystem.m_157425_($$6);
        PoseStack $$7 = RenderSystem.m_157191_();
        $$7.m_166856_();
        $$7.m_85837_(0.0, 0.0, -2000.0);
        RenderSystem.m_157182_();
        Lighting.m_84931_();
        PoseStack $$8 = new PoseStack();
        if (p_109096_ && this.f_109059_.f_91073_ != null) {
            this.f_109059_.m_91307_().m_6182_("gui");
            if (this.f_109059_.f_91074_ != null) {
                float $$9 = Mth.m_14179_(p_109094_, this.f_109059_.f_91074_.f_108590_, this.f_109059_.f_91074_.f_108589_);
                float $$10 = this.f_109059_.f_91066_.m_231924_().m_231551_().floatValue();
                if ($$9 > 0.0f && this.f_109059_.f_91074_.m_21023_(MobEffects.f_19604_) && $$10 < 1.0f) {
                    this.m_109145_($$9 * (1.0f - $$10));
                }
            }
            if (!this.f_109059_.f_91066_.f_92062_ || this.f_109059_.f_91080_ != null) {
                this.m_109100_(this.f_109059_.m_91268_().m_85445_(), this.f_109059_.m_91268_().m_85446_(), p_109094_);
                this.f_109059_.f_91065_.m_93030_($$8, p_109094_);
                RenderSystem.m_69421_(256, Minecraft.f_91002_);
            }
            this.f_109059_.m_91307_().m_7238_();
        }
        if (this.f_109059_.m_91265_() != null) {
            try {
                this.f_109059_.m_91265_().m_6305_($$8, $$3, $$4, this.f_109059_.m_91297_());
            }
            catch (Throwable $$11) {
                CrashReport $$12 = CrashReport.m_127521_($$11, "Rendering overlay");
                CrashReportCategory $$13 = $$12.m_127514_("Overlay render details");
                $$13.m_128165_("Overlay name", () -> this.f_109059_.m_91265_().getClass().getCanonicalName());
                throw new ReportedException($$12);
            }
        }
        if (this.f_109059_.f_91080_ != null) {
            try {
                this.f_109059_.f_91080_.m_6305_($$8, $$3, $$4, this.f_109059_.m_91297_());
            }
            catch (Throwable $$14) {
                CrashReport $$15 = CrashReport.m_127521_($$14, "Rendering screen");
                CrashReportCategory $$16 = $$15.m_127514_("Screen render details");
                $$16.m_128165_("Screen name", () -> this.f_109059_.f_91080_.getClass().getCanonicalName());
                $$16.m_128165_("Mouse location", () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", $$3, $$4, this.f_109059_.f_91067_.m_91589_(), this.f_109059_.f_91067_.m_91594_()));
                $$16.m_128165_("Screen size", () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f", this.f_109059_.m_91268_().m_85445_(), this.f_109059_.m_91268_().m_85446_(), this.f_109059_.m_91268_().m_85441_(), this.f_109059_.m_91268_().m_85442_(), this.f_109059_.m_91268_().m_85449_()));
                throw new ReportedException($$15);
            }
            try {
                if (this.f_109059_.f_91080_ != null) {
                    this.f_109059_.f_91080_.m_169417_();
                }
            }
            catch (Throwable $$17) {
                CrashReport $$18 = CrashReport.m_127521_($$17, "Narrating screen");
                CrashReportCategory $$19 = $$18.m_127514_("Screen details");
                $$19.m_128165_("Screen name", () -> this.f_109059_.f_91080_.getClass().getCanonicalName());
                throw new ReportedException($$18);
            }
        }
    }

    private void m_182644_() {
        if (this.f_182638_ || !this.f_109059_.m_91090_()) {
            return;
        }
        long $$0 = Util.m_137550_();
        if ($$0 - this.f_109072_ < 1000L) {
            return;
        }
        this.f_109072_ = $$0;
        IntegratedServer $$1 = this.f_109059_.m_91092_();
        if ($$1 == null || $$1.m_129918_()) {
            return;
        }
        $$1.m_182649_().ifPresent(p_234239_ -> {
            if (Files.isRegularFile(p_234239_, new LinkOption[0])) {
                this.f_182638_ = true;
            } else {
                this.m_182642_((Path)p_234239_);
            }
        });
    }

    private void m_182642_(Path p_182643_) {
        if (this.f_109059_.f_91060_.m_109821_() > 10 && this.f_109059_.f_91060_.m_109825_()) {
            NativeImage $$1 = Screenshot.m_92279_(this.f_109059_.m_91385_());
            Util.m_183992_().execute(() -> {
                int $$2 = $$1.m_84982_();
                int $$3 = $$1.m_85084_();
                int $$4 = 0;
                int $$5 = 0;
                if ($$2 > $$3) {
                    $$4 = ($$2 - $$3) / 2;
                    $$2 = $$3;
                } else {
                    $$5 = ($$3 - $$2) / 2;
                    $$3 = $$2;
                }
                try (NativeImage $$6 = new NativeImage(64, 64, false);){
                    $$1.m_85034_($$4, $$5, $$2, $$3, $$6);
                    $$6.m_85066_(p_182643_);
                }
                catch (IOException $$7) {
                    f_109058_.warn("Couldn't save auto screenshot", (Throwable)$$7);
                }
                finally {
                    $$1.close();
                }
            });
        }
    }

    private boolean m_109158_() {
        boolean $$1;
        if (!this.f_109071_) {
            return false;
        }
        Entity $$0 = this.f_109059_.m_91288_();
        boolean bl = $$1 = $$0 instanceof Player && !this.f_109059_.f_91066_.f_92062_;
        if ($$1 && !((Player)$$0).m_150110_().f_35938_) {
            ItemStack $$2 = ((LivingEntity)$$0).m_21205_();
            HitResult $$3 = this.f_109059_.f_91077_;
            if ($$3 != null && $$3.m_6662_() == HitResult.Type.BLOCK) {
                BlockPos $$4 = ((BlockHitResult)$$3).m_82425_();
                BlockState $$5 = this.f_109059_.f_91073_.m_8055_($$4);
                if (this.f_109059_.f_91072_.m_105295_() == GameType.SPECTATOR) {
                    $$1 = $$5.m_60750_(this.f_109059_.f_91073_, $$4) != null;
                } else {
                    BlockInWorld $$6 = new BlockInWorld(this.f_109059_.f_91073_, $$4, false);
                    Registry<Block> $$7 = this.f_109059_.f_91073_.m_5962_().m_175515_(Registry.f_122901_);
                    $$1 = !$$2.m_41619_() && ($$2.m_204128_($$7, $$6) || $$2.m_204121_($$7, $$6));
                }
            }
        }
        return $$1;
    }

    public void m_109089_(float p_109090_, long p_109091_, PoseStack p_109092_) {
        this.f_109074_.m_109881_(p_109090_);
        if (this.f_109059_.m_91288_() == null) {
            this.f_109059_.m_91118_(this.f_109059_.f_91074_);
        }
        this.m_109087_(p_109090_);
        this.f_109059_.m_91307_().m_6180_("center");
        boolean $$3 = this.m_109158_();
        this.f_109059_.m_91307_().m_6182_("camera");
        Camera $$4 = this.f_109054_;
        this.f_109062_ = this.f_109059_.f_91066_.m_193772_() * 16;
        PoseStack $$5 = new PoseStack();
        double $$6 = this.m_109141_($$4, p_109090_, true);
        $$5.m_85850_().m_85861_().m_27644_(this.m_172716_($$6));
        this.m_109117_($$5, p_109090_);
        if (this.f_109059_.f_91066_.m_231830_().m_231551_().booleanValue()) {
            this.m_109138_($$5, p_109090_);
        }
        float $$7 = this.f_109059_.f_91066_.m_231924_().m_231551_().floatValue();
        float $$8 = Mth.m_14179_(p_109090_, this.f_109059_.f_91074_.f_108590_, this.f_109059_.f_91074_.f_108589_) * ($$7 * $$7);
        if ($$8 > 0.0f) {
            int $$9 = this.f_109059_.f_91074_.m_21023_(MobEffects.f_19604_) ? 7 : 20;
            float $$10 = 5.0f / ($$8 * $$8 + 5.0f) - $$8 * 0.04f;
            $$10 *= $$10;
            Vector3f $$11 = new Vector3f(0.0f, Mth.f_13994_ / 2.0f, Mth.f_13994_ / 2.0f);
            $$5.m_85845_($$11.m_122240_(((float)this.f_109065_ + p_109090_) * (float)$$9));
            $$5.m_85841_(1.0f / $$10, 1.0f, 1.0f);
            float $$12 = -((float)this.f_109065_ + p_109090_) * (float)$$9;
            $$5.m_85845_($$11.m_122240_($$12));
        }
        Matrix4f $$13 = $$5.m_85850_().m_85861_();
        this.m_109111_($$13);
        $$4.m_90575_(this.f_109059_.f_91073_, this.f_109059_.m_91288_() == null ? this.f_109059_.f_91074_ : this.f_109059_.m_91288_(), !this.f_109059_.f_91066_.m_92176_().m_90612_(), this.f_109059_.f_91066_.m_92176_().m_90613_(), p_109090_);
        p_109092_.m_85845_(Vector3f.f_122223_.m_122240_($$4.m_90589_()));
        p_109092_.m_85845_(Vector3f.f_122225_.m_122240_($$4.m_90590_() + 180.0f));
        Matrix3f $$14 = p_109092_.m_85850_().m_85864_().m_8183_();
        if ($$14.m_8187_()) {
            RenderSystem.m_200918_($$14);
        }
        this.f_109059_.f_91060_.m_172961_(p_109092_, $$4.m_90583_(), this.m_172716_(Math.max($$6, (double)this.f_109059_.f_91066_.m_231837_().m_231551_().intValue())));
        this.f_109059_.f_91060_.m_109599_(p_109092_, p_109090_, p_109091_, $$3, $$4, this, this.f_109074_, $$13);
        this.f_109059_.m_91307_().m_6182_("hand");
        if (this.f_109070_) {
            RenderSystem.m_69421_(256, Minecraft.f_91002_);
            this.m_109120_(p_109092_, $$4, p_109090_);
        }
        this.f_109059_.m_91307_().m_7238_();
    }

    public void m_109150_() {
        this.f_109080_ = null;
        this.f_109063_.m_93260_();
        this.f_109054_.m_90598_();
        this.f_182638_ = false;
    }

    public MapRenderer m_109151_() {
        return this.f_109063_;
    }

    public void m_109113_(ItemStack p_109114_) {
        this.f_109080_ = p_109114_;
        this.f_109047_ = 40;
        this.f_109048_ = this.f_109061_.m_188501_() * 2.0f - 1.0f;
        this.f_109049_ = this.f_109061_.m_188501_() * 2.0f - 1.0f;
    }

    private void m_109100_(int p_109101_, int p_109102_, float p_109103_) {
        if (this.f_109080_ == null || this.f_109047_ <= 0) {
            return;
        }
        int $$3 = 40 - this.f_109047_;
        float $$4 = ((float)$$3 + p_109103_) / 40.0f;
        float $$5 = $$4 * $$4;
        float $$6 = $$4 * $$5;
        float $$7 = 10.25f * $$6 * $$5 - 24.95f * $$5 * $$5 + 25.5f * $$6 - 13.8f * $$5 + 4.0f * $$4;
        float $$8 = $$7 * (float)Math.PI;
        float $$9 = this.f_109048_ * (float)(p_109101_ / 4);
        float $$10 = this.f_109049_ * (float)(p_109102_ / 4);
        RenderSystem.m_69482_();
        RenderSystem.m_69464_();
        PoseStack $$11 = new PoseStack();
        $$11.m_85836_();
        $$11.m_85837_((float)(p_109101_ / 2) + $$9 * Mth.m_14154_(Mth.m_14031_($$8 * 2.0f)), (float)(p_109102_ / 2) + $$10 * Mth.m_14154_(Mth.m_14031_($$8 * 2.0f)), -50.0);
        float $$12 = 50.0f + 175.0f * Mth.m_14031_($$8);
        $$11.m_85841_($$12, -$$12, $$12);
        $$11.m_85845_(Vector3f.f_122225_.m_122240_(900.0f * Mth.m_14154_(Mth.m_14031_($$8))));
        $$11.m_85845_(Vector3f.f_122223_.m_122240_(6.0f * Mth.m_14089_($$4 * 8.0f)));
        $$11.m_85845_(Vector3f.f_122227_.m_122240_(6.0f * Mth.m_14089_($$4 * 8.0f)));
        MultiBufferSource.BufferSource $$13 = this.f_109064_.m_110104_();
        this.f_109059_.m_91291_().m_174269_(this.f_109080_, ItemTransforms.TransformType.FIXED, 0xF000F0, OverlayTexture.f_118083_, $$11, $$13, 0);
        $$11.m_85849_();
        $$13.m_109911_();
        RenderSystem.m_69481_();
        RenderSystem.m_69465_();
    }

    private void m_109145_(float p_109146_) {
        int $$1 = this.f_109059_.m_91268_().m_85445_();
        int $$2 = this.f_109059_.m_91268_().m_85446_();
        double $$3 = Mth.m_14139_(p_109146_, 2.0, 1.0);
        float $$4 = 0.2f * p_109146_;
        float $$5 = 0.4f * p_109146_;
        float $$6 = 0.2f * p_109146_;
        double $$7 = (double)$$1 * $$3;
        double $$8 = (double)$$2 * $$3;
        double $$9 = ((double)$$1 - $$7) / 2.0;
        double $$10 = ((double)$$2 - $$8) / 2.0;
        RenderSystem.m_69465_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_69478_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        RenderSystem.m_157429_($$4, $$5, $$6, 1.0f);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_109057_);
        Tesselator $$11 = Tesselator.m_85913_();
        BufferBuilder $$12 = $$11.m_85915_();
        $$12.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$12.m_5483_($$9, $$10 + $$8, -90.0).m_7421_(0.0f, 1.0f).m_5752_();
        $$12.m_5483_($$9 + $$7, $$10 + $$8, -90.0).m_7421_(1.0f, 1.0f).m_5752_();
        $$12.m_5483_($$9 + $$7, $$10, -90.0).m_7421_(1.0f, 0.0f).m_5752_();
        $$12.m_5483_($$9, $$10, -90.0).m_7421_(0.0f, 0.0f).m_5752_();
        $$11.m_85914_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69453_();
        RenderSystem.m_69461_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69482_();
    }

    public Minecraft m_172797_() {
        return this.f_109059_;
    }

    public float m_109131_(float p_109132_) {
        return Mth.m_14179_(p_109132_, this.f_109069_, this.f_109068_);
    }

    public float m_109152_() {
        return this.f_109062_;
    }

    public Camera m_109153_() {
        return this.f_109054_;
    }

    public LightTexture m_109154_() {
        return this.f_109074_;
    }

    public OverlayTexture m_109155_() {
        return this.f_109075_;
    }

    @Nullable
    public static ShaderInstance m_172808_() {
        return f_172579_;
    }

    @Nullable
    public static ShaderInstance m_172811_() {
        return f_172580_;
    }

    @Nullable
    public static ShaderInstance m_172814_() {
        return f_172581_;
    }

    @Nullable
    public static ShaderInstance m_172817_() {
        return f_172582_;
    }

    @Nullable
    public static ShaderInstance m_172820_() {
        return f_172583_;
    }

    @Nullable
    public static ShaderInstance m_172823_() {
        return f_172584_;
    }

    @Nullable
    public static ShaderInstance m_172826_() {
        return f_172585_;
    }

    @Nullable
    public static ShaderInstance m_172829_() {
        return f_172586_;
    }

    @Nullable
    public static ShaderInstance m_172832_() {
        return f_172587_;
    }

    @Nullable
    public static ShaderInstance m_172835_() {
        return f_172588_;
    }

    @Nullable
    public static ShaderInstance m_172838_() {
        return f_172589_;
    }

    @Nullable
    public static ShaderInstance m_172637_() {
        return f_172590_;
    }

    @Nullable
    public static ShaderInstance m_172640_() {
        return f_172591_;
    }

    @Nullable
    public static ShaderInstance m_172643_() {
        return f_172608_;
    }

    @Nullable
    public static ShaderInstance m_172646_() {
        return f_172609_;
    }

    @Nullable
    public static ShaderInstance m_172649_() {
        return f_172610_;
    }

    @Nullable
    public static ShaderInstance m_172652_() {
        return f_172611_;
    }

    @Nullable
    public static ShaderInstance m_172655_() {
        return f_172612_;
    }

    @Nullable
    public static ShaderInstance m_172658_() {
        return f_172613_;
    }

    @Nullable
    public static ShaderInstance m_172661_() {
        return f_172614_;
    }

    @Nullable
    public static ShaderInstance m_172664_() {
        return f_172615_;
    }

    @Nullable
    public static ShaderInstance m_172667_() {
        return f_172616_;
    }

    @Nullable
    public static ShaderInstance m_172670_() {
        return f_172617_;
    }

    @Nullable
    public static ShaderInstance m_172673_() {
        return f_172618_;
    }

    @Nullable
    public static ShaderInstance m_172676_() {
        return f_172619_;
    }

    @Nullable
    public static ShaderInstance m_172679_() {
        return f_172620_;
    }

    @Nullable
    public static ShaderInstance m_234223_() {
        return f_234217_;
    }

    @Nullable
    public static ShaderInstance m_172682_() {
        return f_172621_;
    }

    @Nullable
    public static ShaderInstance m_172685_() {
        return f_172622_;
    }

    @Nullable
    public static ShaderInstance m_172688_() {
        return f_172623_;
    }

    @Nullable
    public static ShaderInstance m_172691_() {
        return f_172624_;
    }

    @Nullable
    public static ShaderInstance m_172694_() {
        return f_172625_;
    }

    @Nullable
    public static ShaderInstance m_172697_() {
        return f_172626_;
    }

    @Nullable
    public static ShaderInstance m_172700_() {
        return f_172627_;
    }

    @Nullable
    public static ShaderInstance m_172703_() {
        return f_172628_;
    }

    @Nullable
    public static ShaderInstance m_172706_() {
        return f_172629_;
    }

    @Nullable
    public static ShaderInstance m_172709_() {
        return f_172630_;
    }

    @Nullable
    public static ShaderInstance m_172712_() {
        return f_172631_;
    }

    @Nullable
    public static ShaderInstance m_172738_() {
        return f_172632_;
    }

    @Nullable
    public static ShaderInstance m_172741_() {
        return f_172633_;
    }

    @Nullable
    public static ShaderInstance m_172744_() {
        return f_172593_;
    }

    @Nullable
    public static ShaderInstance m_172745_() {
        return f_172594_;
    }

    @Nullable
    public static ShaderInstance m_172746_() {
        return f_172595_;
    }

    @Nullable
    public static ShaderInstance m_172747_() {
        return f_172596_;
    }

    @Nullable
    public static ShaderInstance m_172748_() {
        return f_172597_;
    }

    @Nullable
    public static ShaderInstance m_172749_() {
        return f_172598_;
    }

    @Nullable
    public static ShaderInstance m_172750_() {
        return f_172599_;
    }

    @Nullable
    public static ShaderInstance m_172751_() {
        return f_172600_;
    }

    @Nullable
    public static ShaderInstance m_172752_() {
        return f_172601_;
    }

    @Nullable
    public static ShaderInstance m_172753_() {
        return f_172602_;
    }

    @Nullable
    public static ShaderInstance m_172754_() {
        return f_172603_;
    }

    @Nullable
    public static ShaderInstance m_172755_() {
        return f_172604_;
    }

    @Nullable
    public static ShaderInstance m_172756_() {
        return f_172605_;
    }

    @Nullable
    public static ShaderInstance m_172757_() {
        return f_172606_;
    }

    @Nullable
    public static ShaderInstance m_172758_() {
        return f_172607_;
    }
}

