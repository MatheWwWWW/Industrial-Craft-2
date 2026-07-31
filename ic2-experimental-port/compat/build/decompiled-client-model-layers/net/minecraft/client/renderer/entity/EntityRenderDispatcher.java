/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EntityRenderDispatcher
implements ResourceManagerReloadListener {
    private static final RenderType f_114361_ = RenderType.m_110485_(new ResourceLocation("textures/misc/shadow.png"));
    private Map<EntityType<?>, EntityRenderer<?>> f_114362_ = ImmutableMap.of();
    private Map<String, EntityRenderer<? extends Player>> f_114363_ = ImmutableMap.of();
    public final TextureManager f_114357_;
    private Level f_114366_;
    public Camera f_114358_;
    private Quaternion f_114367_;
    public Entity f_114359_;
    private final ItemRenderer f_173995_;
    private final BlockRenderDispatcher f_234576_;
    private final ItemInHandRenderer f_234577_;
    private final Font f_114365_;
    public final Options f_114360_;
    private final EntityModelSet f_173996_;
    private boolean f_114368_ = true;
    private boolean f_114369_;

    public <E extends Entity> int m_114394_(E p_114395_, float p_114396_) {
        return this.m_114382_(p_114395_).m_114505_(p_114395_, p_114396_);
    }

    public EntityRenderDispatcher(Minecraft p_234579_, TextureManager p_234580_, ItemRenderer p_234581_, BlockRenderDispatcher p_234582_, Font p_234583_, Options p_234584_, EntityModelSet p_234585_) {
        this.f_114357_ = p_234580_;
        this.f_173995_ = p_234581_;
        this.f_234577_ = new ItemInHandRenderer(p_234579_, this, p_234581_);
        this.f_234576_ = p_234582_;
        this.f_114365_ = p_234583_;
        this.f_114360_ = p_234584_;
        this.f_173996_ = p_234585_;
    }

    public <T extends Entity> EntityRenderer<? super T> m_114382_(T p_114383_) {
        if (p_114383_ instanceof AbstractClientPlayer) {
            String $$1 = ((AbstractClientPlayer)p_114383_).m_108564_();
            EntityRenderer<? extends Player> $$2 = this.f_114363_.get($$1);
            if ($$2 != null) {
                return $$2;
            }
            return this.f_114363_.get("default");
        }
        return this.f_114362_.get(p_114383_.m_6095_());
    }

    public void m_114408_(Level p_114409_, Camera p_114410_, Entity p_114411_) {
        this.f_114366_ = p_114409_;
        this.f_114358_ = p_114410_;
        this.f_114367_ = p_114410_.m_90591_();
        this.f_114359_ = p_114411_;
    }

    public void m_114412_(Quaternion p_114413_) {
        this.f_114367_ = p_114413_;
    }

    public void m_114468_(boolean p_114469_) {
        this.f_114368_ = p_114469_;
    }

    public void m_114473_(boolean p_114474_) {
        this.f_114369_ = p_114474_;
    }

    public boolean m_114377_() {
        return this.f_114369_;
    }

    public <E extends Entity> boolean m_114397_(E p_114398_, Frustum p_114399_, double p_114400_, double p_114401_, double p_114402_) {
        EntityRenderer<E> $$5 = this.m_114382_(p_114398_);
        return $$5.m_5523_(p_114398_, p_114399_, p_114400_, p_114401_, p_114402_);
    }

    public <E extends Entity> void m_114384_(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_) {
        EntityRenderer<E> $$9 = this.m_114382_(p_114385_);
        try {
            double $$14;
            float $$15;
            Vec3 $$10 = $$9.m_7860_(p_114385_, p_114390_);
            double $$11 = p_114386_ + $$10.m_7096_();
            double $$12 = p_114387_ + $$10.m_7098_();
            double $$13 = p_114388_ + $$10.m_7094_();
            p_114391_.m_85836_();
            p_114391_.m_85837_($$11, $$12, $$13);
            $$9.m_7392_(p_114385_, p_114389_, p_114390_, p_114391_, p_114392_, p_114393_);
            if (p_114385_.m_6051_()) {
                this.m_114453_(p_114391_, p_114392_, p_114385_);
            }
            p_114391_.m_85837_(-$$10.m_7096_(), -$$10.m_7098_(), -$$10.m_7094_());
            if (this.f_114360_.m_231818_().m_231551_().booleanValue() && this.f_114368_ && $$9.f_114477_ > 0.0f && !p_114385_.m_20145_() && ($$15 = (float)((1.0 - ($$14 = this.m_114378_(p_114385_.m_20185_(), p_114385_.m_20186_(), p_114385_.m_20189_())) / 256.0) * (double)$$9.f_114478_)) > 0.0f) {
                EntityRenderDispatcher.m_114457_(p_114391_, p_114392_, p_114385_, $$15, p_114390_, this.f_114366_, $$9.f_114477_);
            }
            if (this.f_114369_ && !p_114385_.m_20145_() && !Minecraft.m_91087_().m_91299_()) {
                EntityRenderDispatcher.m_114441_(p_114391_, p_114392_.m_6299_(RenderType.m_110504_()), p_114385_, p_114390_);
            }
            p_114391_.m_85849_();
        }
        catch (Throwable $$16) {
            CrashReport $$17 = CrashReport.m_127521_($$16, "Rendering entity in world");
            CrashReportCategory $$18 = $$17.m_127514_("Entity being rendered");
            p_114385_.m_7976_($$18);
            CrashReportCategory $$19 = $$17.m_127514_("Renderer details");
            $$19.m_128159_("Assigned renderer", $$9);
            $$19.m_128159_("Location", CrashReportCategory.m_178937_(this.f_114366_, p_114386_, p_114387_, p_114388_));
            $$19.m_128159_("Rotation", Float.valueOf(p_114389_));
            $$19.m_128159_("Delta", Float.valueOf(p_114390_));
            throw new ReportedException($$17);
        }
    }

    private static void m_114441_(PoseStack p_114442_, VertexConsumer p_114443_, Entity p_114444_, float p_114445_) {
        AABB $$4 = p_114444_.m_20191_().m_82386_(-p_114444_.m_20185_(), -p_114444_.m_20186_(), -p_114444_.m_20189_());
        LevelRenderer.m_109646_(p_114442_, p_114443_, $$4, 1.0f, 1.0f, 1.0f, 1.0f);
        if (p_114444_ instanceof EnderDragon) {
            double $$5 = -Mth.m_14139_(p_114445_, p_114444_.f_19790_, p_114444_.m_20185_());
            double $$6 = -Mth.m_14139_(p_114445_, p_114444_.f_19791_, p_114444_.m_20186_());
            double $$7 = -Mth.m_14139_(p_114445_, p_114444_.f_19792_, p_114444_.m_20189_());
            for (EnderDragonPart $$8 : ((EnderDragon)p_114444_).m_31156_()) {
                p_114442_.m_85836_();
                double $$9 = $$5 + Mth.m_14139_(p_114445_, $$8.f_19790_, $$8.m_20185_());
                double $$10 = $$6 + Mth.m_14139_(p_114445_, $$8.f_19791_, $$8.m_20186_());
                double $$11 = $$7 + Mth.m_14139_(p_114445_, $$8.f_19792_, $$8.m_20189_());
                p_114442_.m_85837_($$9, $$10, $$11);
                LevelRenderer.m_109646_(p_114442_, p_114443_, $$8.m_20191_().m_82386_(-$$8.m_20185_(), -$$8.m_20186_(), -$$8.m_20189_()), 0.25f, 1.0f, 0.0f, 1.0f);
                p_114442_.m_85849_();
            }
        }
        if (p_114444_ instanceof LivingEntity) {
            float $$12 = 0.01f;
            LevelRenderer.m_109608_(p_114442_, p_114443_, $$4.f_82288_, p_114444_.m_20192_() - 0.01f, $$4.f_82290_, $$4.f_82291_, p_114444_.m_20192_() + 0.01f, $$4.f_82293_, 1.0f, 0.0f, 0.0f, 1.0f);
        }
        Vec3 $$13 = p_114444_.m_20252_(p_114445_);
        Matrix4f $$14 = p_114442_.m_85850_().m_85861_();
        Matrix3f $$15 = p_114442_.m_85850_().m_85864_();
        p_114443_.m_85982_($$14, 0.0f, p_114444_.m_20192_(), 0.0f).m_6122_(0, 0, 255, 255).m_85977_($$15, (float)$$13.f_82479_, (float)$$13.f_82480_, (float)$$13.f_82481_).m_5752_();
        p_114443_.m_85982_($$14, (float)($$13.f_82479_ * 2.0), (float)((double)p_114444_.m_20192_() + $$13.f_82480_ * 2.0), (float)($$13.f_82481_ * 2.0)).m_6122_(0, 0, 255, 255).m_85977_($$15, (float)$$13.f_82479_, (float)$$13.f_82480_, (float)$$13.f_82481_).m_5752_();
    }

    private void m_114453_(PoseStack p_114454_, MultiBufferSource p_114455_, Entity p_114456_) {
        TextureAtlasSprite $$3 = ModelBakery.f_119219_.m_119204_();
        TextureAtlasSprite $$4 = ModelBakery.f_119220_.m_119204_();
        p_114454_.m_85836_();
        float $$5 = p_114456_.m_20205_() * 1.4f;
        p_114454_.m_85841_($$5, $$5, $$5);
        float $$6 = 0.5f;
        float $$7 = 0.0f;
        float $$8 = p_114456_.m_20206_() / $$5;
        float $$9 = 0.0f;
        p_114454_.m_85845_(Vector3f.f_122225_.m_122240_(-this.f_114358_.m_90590_()));
        p_114454_.m_85837_(0.0, 0.0, -0.3f + (float)((int)$$8) * 0.02f);
        float $$10 = 0.0f;
        int $$11 = 0;
        VertexConsumer $$12 = p_114455_.m_6299_(Sheets.m_110790_());
        PoseStack.Pose $$13 = p_114454_.m_85850_();
        while ($$8 > 0.0f) {
            TextureAtlasSprite $$14 = $$11 % 2 == 0 ? $$3 : $$4;
            float $$15 = $$14.m_118409_();
            float $$16 = $$14.m_118411_();
            float $$17 = $$14.m_118410_();
            float $$18 = $$14.m_118412_();
            if ($$11 / 2 % 2 == 0) {
                float $$19 = $$17;
                $$17 = $$15;
                $$15 = $$19;
            }
            EntityRenderDispatcher.m_114414_($$13, $$12, $$6 - 0.0f, 0.0f - $$9, $$10, $$17, $$18);
            EntityRenderDispatcher.m_114414_($$13, $$12, -$$6 - 0.0f, 0.0f - $$9, $$10, $$15, $$18);
            EntityRenderDispatcher.m_114414_($$13, $$12, -$$6 - 0.0f, 1.4f - $$9, $$10, $$15, $$16);
            EntityRenderDispatcher.m_114414_($$13, $$12, $$6 - 0.0f, 1.4f - $$9, $$10, $$17, $$16);
            $$8 -= 0.45f;
            $$9 -= 0.45f;
            $$6 *= 0.9f;
            $$10 += 0.03f;
            ++$$11;
        }
        p_114454_.m_85849_();
    }

    private static void m_114414_(PoseStack.Pose p_114415_, VertexConsumer p_114416_, float p_114417_, float p_114418_, float p_114419_, float p_114420_, float p_114421_) {
        p_114416_.m_85982_(p_114415_.m_85861_(), p_114417_, p_114418_, p_114419_).m_6122_(255, 255, 255, 255).m_7421_(p_114420_, p_114421_).m_7122_(0, 10).m_85969_(240).m_85977_(p_114415_.m_85864_(), 0.0f, 1.0f, 0.0f).m_5752_();
    }

    private static void m_114457_(PoseStack p_114458_, MultiBufferSource p_114459_, Entity p_114460_, float p_114461_, float p_114462_, LevelReader p_114463_, float p_114464_) {
        Mob $$8;
        float $$7 = p_114464_;
        if (p_114460_ instanceof Mob && ($$8 = (Mob)p_114460_).m_6162_()) {
            $$7 *= 0.5f;
        }
        double $$9 = Mth.m_14139_(p_114462_, p_114460_.f_19790_, p_114460_.m_20185_());
        double $$10 = Mth.m_14139_(p_114462_, p_114460_.f_19791_, p_114460_.m_20186_());
        double $$11 = Mth.m_14139_(p_114462_, p_114460_.f_19792_, p_114460_.m_20189_());
        int $$12 = Mth.m_14107_($$9 - (double)$$7);
        int $$13 = Mth.m_14107_($$9 + (double)$$7);
        int $$14 = Mth.m_14107_($$10 - (double)$$7);
        int $$15 = Mth.m_14107_($$10);
        int $$16 = Mth.m_14107_($$11 - (double)$$7);
        int $$17 = Mth.m_14107_($$11 + (double)$$7);
        PoseStack.Pose $$18 = p_114458_.m_85850_();
        VertexConsumer $$19 = p_114459_.m_6299_(f_114361_);
        for (BlockPos $$20 : BlockPos.m_121940_(new BlockPos($$12, $$14, $$16), new BlockPos($$13, $$15, $$17))) {
            EntityRenderDispatcher.m_114431_($$18, $$19, p_114463_, $$20, $$9, $$10, $$11, $$7, p_114461_);
        }
    }

    private static void m_114431_(PoseStack.Pose p_114432_, VertexConsumer p_114433_, LevelReader p_114434_, BlockPos p_114435_, double p_114436_, double p_114437_, double p_114438_, float p_114439_, float p_114440_) {
        BlockPos $$9 = p_114435_.m_7495_();
        BlockState $$10 = p_114434_.m_8055_($$9);
        if ($$10.m_60799_() == RenderShape.INVISIBLE || p_114434_.m_46803_(p_114435_) <= 3) {
            return;
        }
        if (!$$10.m_60838_(p_114434_, $$9)) {
            return;
        }
        VoxelShape $$11 = $$10.m_60808_(p_114434_, p_114435_.m_7495_());
        if ($$11.m_83281_()) {
            return;
        }
        float $$12 = LightTexture.m_234316_(p_114434_.m_6042_(), p_114434_.m_46803_(p_114435_));
        float $$13 = (float)(((double)p_114440_ - (p_114437_ - (double)p_114435_.m_123342_()) / 2.0) * 0.5 * (double)$$12);
        if ($$13 >= 0.0f) {
            if ($$13 > 1.0f) {
                $$13 = 1.0f;
            }
            AABB $$14 = $$11.m_83215_();
            double $$15 = (double)p_114435_.m_123341_() + $$14.f_82288_;
            double $$16 = (double)p_114435_.m_123341_() + $$14.f_82291_;
            double $$17 = (double)p_114435_.m_123342_() + $$14.f_82289_;
            double $$18 = (double)p_114435_.m_123343_() + $$14.f_82290_;
            double $$19 = (double)p_114435_.m_123343_() + $$14.f_82293_;
            float $$20 = (float)($$15 - p_114436_);
            float $$21 = (float)($$16 - p_114436_);
            float $$22 = (float)($$17 - p_114437_);
            float $$23 = (float)($$18 - p_114438_);
            float $$24 = (float)($$19 - p_114438_);
            float $$25 = -$$20 / 2.0f / p_114439_ + 0.5f;
            float $$26 = -$$21 / 2.0f / p_114439_ + 0.5f;
            float $$27 = -$$23 / 2.0f / p_114439_ + 0.5f;
            float $$28 = -$$24 / 2.0f / p_114439_ + 0.5f;
            EntityRenderDispatcher.m_114422_(p_114432_, p_114433_, $$13, $$20, $$22, $$23, $$25, $$27);
            EntityRenderDispatcher.m_114422_(p_114432_, p_114433_, $$13, $$20, $$22, $$24, $$25, $$28);
            EntityRenderDispatcher.m_114422_(p_114432_, p_114433_, $$13, $$21, $$22, $$24, $$26, $$28);
            EntityRenderDispatcher.m_114422_(p_114432_, p_114433_, $$13, $$21, $$22, $$23, $$26, $$27);
        }
    }

    private static void m_114422_(PoseStack.Pose p_114423_, VertexConsumer p_114424_, float p_114425_, float p_114426_, float p_114427_, float p_114428_, float p_114429_, float p_114430_) {
        p_114424_.m_85982_(p_114423_.m_85861_(), p_114426_, p_114427_, p_114428_).m_85950_(1.0f, 1.0f, 1.0f, p_114425_).m_7421_(p_114429_, p_114430_).m_86008_(OverlayTexture.f_118083_).m_85969_(0xF000F0).m_85977_(p_114423_.m_85864_(), 0.0f, 1.0f, 0.0f).m_5752_();
    }

    public void m_114406_(@Nullable Level p_114407_) {
        this.f_114366_ = p_114407_;
        if (p_114407_ == null) {
            this.f_114358_ = null;
        }
    }

    public double m_114471_(Entity p_114472_) {
        return this.f_114358_.m_90583_().m_82557_(p_114472_.m_20182_());
    }

    public double m_114378_(double p_114379_, double p_114380_, double p_114381_) {
        return this.f_114358_.m_90583_().m_82531_(p_114379_, p_114380_, p_114381_);
    }

    public Quaternion m_114470_() {
        return this.f_114367_;
    }

    public ItemInHandRenderer m_234586_() {
        return this.f_234577_;
    }

    @Override
    public void m_6213_(ResourceManager p_174004_) {
        EntityRendererProvider.Context $$1 = new EntityRendererProvider.Context(this, this.f_173995_, this.f_234576_, this.f_234577_, p_174004_, this.f_173996_, this.f_114365_);
        this.f_114362_ = EntityRenderers.m_174049_($$1);
        this.f_114363_ = EntityRenderers.m_174051_($$1);
    }
}

