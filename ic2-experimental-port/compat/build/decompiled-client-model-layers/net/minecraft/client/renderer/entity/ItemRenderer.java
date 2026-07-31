/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;

public class ItemRenderer
implements ResourceManagerReloadListener {
    public static final ResourceLocation f_115092_ = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static final Set<Item> f_115094_ = Sets.newHashSet((Object[])new Item[]{Items.f_41852_});
    private static final int f_174221_ = 8;
    private static final int f_174222_ = 8;
    public static final int f_174218_ = 200;
    public static final float f_174219_ = 0.5f;
    public static final float f_174220_ = 0.75f;
    public float f_115093_;
    private final ItemModelShaper f_115095_;
    private final TextureManager f_115096_;
    private final ItemColors f_115097_;
    private final BlockEntityWithoutLevelRenderer f_174223_;

    public ItemRenderer(TextureManager p_174225_, ModelManager p_174226_, ItemColors p_174227_, BlockEntityWithoutLevelRenderer p_174228_) {
        this.f_115096_ = p_174225_;
        this.f_115095_ = new ItemModelShaper(p_174226_);
        this.f_174223_ = p_174228_;
        for (Item $$4 : Registry.f_122827_) {
            if (f_115094_.contains($$4)) continue;
            this.f_115095_.m_109396_($$4, new ModelResourceLocation(Registry.f_122827_.m_7981_($$4), "inventory"));
        }
        this.f_115097_ = p_174227_;
    }

    public ItemModelShaper m_115103_() {
        return this.f_115095_;
    }

    private void m_115189_(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_) {
        RandomSource $$6 = RandomSource.m_216327_();
        long $$7 = 42L;
        for (Direction $$8 : Direction.values()) {
            $$6.m_188584_(42L);
            this.m_115162_(p_115194_, p_115195_, p_115190_.m_213637_(null, $$8, $$6), p_115191_, p_115192_, p_115193_);
        }
        $$6.m_188584_(42L);
        this.m_115162_(p_115194_, p_115195_, p_115190_.m_213637_(null, null, $$6), p_115191_, p_115192_, p_115193_);
    }

    public void m_115143_(ItemStack p_115144_, ItemTransforms.TransformType p_115145_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_) {
        boolean $$8;
        if (p_115144_.m_41619_()) {
            return;
        }
        p_115147_.m_85836_();
        boolean bl = $$8 = p_115145_ == ItemTransforms.TransformType.GUI || p_115145_ == ItemTransforms.TransformType.GROUND || p_115145_ == ItemTransforms.TransformType.FIXED;
        if ($$8) {
            if (p_115144_.m_150930_(Items.f_42713_)) {
                p_115151_ = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:trident#inventory"));
            } else if (p_115144_.m_150930_(Items.f_151059_)) {
                p_115151_ = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:spyglass#inventory"));
            }
        }
        p_115151_.m_7442_().m_111808_(p_115145_).m_111763_(p_115146_, p_115147_);
        p_115147_.m_85837_(-0.5, -0.5, -0.5);
        if (p_115151_.m_7521_() || p_115144_.m_150930_(Items.f_42713_) && !$$8) {
            this.f_174223_.m_108829_(p_115144_, p_115145_, p_115147_, p_115148_, p_115149_, p_115150_);
        } else {
            VertexConsumer $$17;
            boolean $$11;
            if (p_115145_ != ItemTransforms.TransformType.GUI && !p_115145_.m_111841_() && p_115144_.m_41720_() instanceof BlockItem) {
                Block $$9 = ((BlockItem)p_115144_.m_41720_()).m_40614_();
                boolean $$10 = !($$9 instanceof HalfTransparentBlock) && !($$9 instanceof StainedGlassPaneBlock);
            } else {
                $$11 = true;
            }
            RenderType $$12 = ItemBlockRenderTypes.m_109279_(p_115144_, $$11);
            if (p_115144_.m_204117_(ItemTags.f_215866_) && p_115144_.m_41790_()) {
                p_115147_.m_85836_();
                PoseStack.Pose $$13 = p_115147_.m_85850_();
                if (p_115145_ == ItemTransforms.TransformType.GUI) {
                    $$13.m_85861_().m_27630_(0.5f);
                } else if (p_115145_.m_111841_()) {
                    $$13.m_85861_().m_27630_(0.75f);
                }
                if ($$11) {
                    VertexConsumer $$14 = ItemRenderer.m_115207_(p_115148_, $$12, $$13);
                } else {
                    VertexConsumer $$15 = ItemRenderer.m_115180_(p_115148_, $$12, $$13);
                }
                p_115147_.m_85849_();
            } else if ($$11) {
                VertexConsumer $$16 = ItemRenderer.m_115222_(p_115148_, $$12, true, p_115144_.m_41790_());
            } else {
                $$17 = ItemRenderer.m_115211_(p_115148_, $$12, true, p_115144_.m_41790_());
            }
            this.m_115189_(p_115151_, p_115144_, p_115149_, p_115150_, p_115147_, $$17);
        }
        p_115147_.m_85849_();
    }

    public static VertexConsumer m_115184_(MultiBufferSource p_115185_, RenderType p_115186_, boolean p_115187_, boolean p_115188_) {
        if (p_115188_) {
            return VertexMultiConsumer.m_86168_(p_115185_.m_6299_(p_115187_ ? RenderType.m_110481_() : RenderType.m_110484_()), p_115185_.m_6299_(p_115186_));
        }
        return p_115185_.m_6299_(p_115186_);
    }

    public static VertexConsumer m_115180_(MultiBufferSource p_115181_, RenderType p_115182_, PoseStack.Pose p_115183_) {
        return VertexMultiConsumer.m_86168_(new SheetedDecalTextureGenerator(p_115181_.m_6299_(RenderType.m_110490_()), p_115183_.m_85861_(), p_115183_.m_85864_()), p_115181_.m_6299_(p_115182_));
    }

    public static VertexConsumer m_115207_(MultiBufferSource p_115208_, RenderType p_115209_, PoseStack.Pose p_115210_) {
        return VertexMultiConsumer.m_86168_(new SheetedDecalTextureGenerator(p_115208_.m_6299_(RenderType.m_110493_()), p_115210_.m_85861_(), p_115210_.m_85864_()), p_115208_.m_6299_(p_115209_));
    }

    public static VertexConsumer m_115211_(MultiBufferSource p_115212_, RenderType p_115213_, boolean p_115214_, boolean p_115215_) {
        if (p_115215_) {
            if (Minecraft.m_91085_() && p_115213_ == Sheets.m_110791_()) {
                return VertexMultiConsumer.m_86168_(p_115212_.m_6299_(RenderType.m_110487_()), p_115212_.m_6299_(p_115213_));
            }
            return VertexMultiConsumer.m_86168_(p_115212_.m_6299_(p_115214_ ? RenderType.m_110490_() : RenderType.m_110496_()), p_115212_.m_6299_(p_115213_));
        }
        return p_115212_.m_6299_(p_115213_);
    }

    public static VertexConsumer m_115222_(MultiBufferSource p_115223_, RenderType p_115224_, boolean p_115225_, boolean p_115226_) {
        if (p_115226_) {
            return VertexMultiConsumer.m_86168_(p_115223_.m_6299_(p_115225_ ? RenderType.m_110493_() : RenderType.m_110499_()), p_115223_.m_6299_(p_115224_));
        }
        return p_115223_.m_6299_(p_115224_);
    }

    private void m_115162_(PoseStack p_115163_, VertexConsumer p_115164_, List<BakedQuad> p_115165_, ItemStack p_115166_, int p_115167_, int p_115168_) {
        boolean $$6 = !p_115166_.m_41619_();
        PoseStack.Pose $$7 = p_115163_.m_85850_();
        for (BakedQuad $$8 : p_115165_) {
            int $$9 = -1;
            if ($$6 && $$8.m_111304_()) {
                $$9 = this.f_115097_.m_92676_(p_115166_, $$8.m_111305_());
            }
            float $$10 = (float)($$9 >> 16 & 0xFF) / 255.0f;
            float $$11 = (float)($$9 >> 8 & 0xFF) / 255.0f;
            float $$12 = (float)($$9 & 0xFF) / 255.0f;
            p_115164_.m_85987_($$7, $$8, $$10, $$11, $$12, p_115167_, p_115168_);
        }
    }

    public BakedModel m_174264_(ItemStack p_174265_, @Nullable Level p_174266_, @Nullable LivingEntity p_174267_, int p_174268_) {
        BakedModel $$6;
        if (p_174265_.m_150930_(Items.f_42713_)) {
            BakedModel $$4 = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
        } else if (p_174265_.m_150930_(Items.f_151059_)) {
            BakedModel $$5 = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:spyglass_in_hand#inventory"));
        } else {
            $$6 = this.f_115095_.m_109406_(p_174265_);
        }
        ClientLevel $$7 = p_174266_ instanceof ClientLevel ? (ClientLevel)p_174266_ : null;
        BakedModel $$8 = $$6.m_7343_().m_173464_($$6, p_174265_, $$7, p_174267_, p_174268_);
        return $$8 == null ? this.f_115095_.m_109393_().m_119409_() : $$8;
    }

    public void m_174269_(ItemStack p_174270_, ItemTransforms.TransformType p_174271_, int p_174272_, int p_174273_, PoseStack p_174274_, MultiBufferSource p_174275_, int p_174276_) {
        this.m_174242_(null, p_174270_, p_174271_, false, p_174274_, p_174275_, null, p_174272_, p_174273_, p_174276_);
    }

    public void m_174242_(@Nullable LivingEntity p_174243_, ItemStack p_174244_, ItemTransforms.TransformType p_174245_, boolean p_174246_, PoseStack p_174247_, MultiBufferSource p_174248_, @Nullable Level p_174249_, int p_174250_, int p_174251_, int p_174252_) {
        if (p_174244_.m_41619_()) {
            return;
        }
        BakedModel $$10 = this.m_174264_(p_174244_, p_174249_, p_174243_, p_174252_);
        this.m_115143_(p_174244_, p_174245_, p_174246_, p_174247_, p_174248_, p_174250_, p_174251_, $$10);
    }

    public void m_115123_(ItemStack p_115124_, int p_115125_, int p_115126_) {
        this.m_115127_(p_115124_, p_115125_, p_115126_, this.m_174264_(p_115124_, null, null, 0));
    }

    protected void m_115127_(ItemStack p_115128_, int p_115129_, int p_115130_, BakedModel p_115131_) {
        boolean $$7;
        this.f_115096_.m_118506_(TextureAtlas.f_118259_).m_117960_(false, false);
        RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
        RenderSystem.m_69478_();
        RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        PoseStack $$4 = RenderSystem.m_157191_();
        $$4.m_85836_();
        $$4.m_85837_(p_115129_, p_115130_, 100.0f + this.f_115093_);
        $$4.m_85837_(8.0, 8.0, 0.0);
        $$4.m_85841_(1.0f, -1.0f, 1.0f);
        $$4.m_85841_(16.0f, 16.0f, 16.0f);
        RenderSystem.m_157182_();
        PoseStack $$5 = new PoseStack();
        MultiBufferSource.BufferSource $$6 = Minecraft.m_91087_().m_91269_().m_110104_();
        boolean bl = $$7 = !p_115131_.m_7547_();
        if ($$7) {
            Lighting.m_84930_();
        }
        this.m_115143_(p_115128_, ItemTransforms.TransformType.GUI, false, $$5, $$6, 0xF000F0, OverlayTexture.f_118083_, p_115131_);
        $$6.m_109911_();
        RenderSystem.m_69482_();
        if ($$7) {
            Lighting.m_84931_();
        }
        $$4.m_85849_();
        RenderSystem.m_157182_();
    }

    public void m_115203_(ItemStack p_115204_, int p_115205_, int p_115206_) {
        this.m_174277_(Minecraft.m_91087_().f_91074_, p_115204_, p_115205_, p_115206_, 0);
    }

    public void m_174253_(ItemStack p_174254_, int p_174255_, int p_174256_, int p_174257_) {
        this.m_174277_(Minecraft.m_91087_().f_91074_, p_174254_, p_174255_, p_174256_, p_174257_);
    }

    public void m_174258_(ItemStack p_174259_, int p_174260_, int p_174261_, int p_174262_, int p_174263_) {
        this.m_174235_(Minecraft.m_91087_().f_91074_, p_174259_, p_174260_, p_174261_, p_174262_, p_174263_);
    }

    public void m_115218_(ItemStack p_115219_, int p_115220_, int p_115221_) {
        this.m_174277_(null, p_115219_, p_115220_, p_115221_, 0);
    }

    public void m_174229_(LivingEntity p_174230_, ItemStack p_174231_, int p_174232_, int p_174233_, int p_174234_) {
        this.m_174277_(p_174230_, p_174231_, p_174232_, p_174233_, p_174234_);
    }

    private void m_174277_(@Nullable LivingEntity p_174278_, ItemStack p_174279_, int p_174280_, int p_174281_, int p_174282_) {
        this.m_174235_(p_174278_, p_174279_, p_174280_, p_174281_, p_174282_, 0);
    }

    private void m_174235_(@Nullable LivingEntity p_174236_, ItemStack p_174237_, int p_174238_, int p_174239_, int p_174240_, int p_174241_) {
        if (p_174237_.m_41619_()) {
            return;
        }
        BakedModel $$6 = this.m_174264_(p_174237_, null, p_174236_, p_174240_);
        this.f_115093_ = $$6.m_7539_() ? this.f_115093_ + 50.0f + (float)p_174241_ : this.f_115093_ + 50.0f;
        try {
            this.m_115127_(p_174237_, p_174238_, p_174239_, $$6);
        }
        catch (Throwable $$7) {
            CrashReport $$8 = CrashReport.m_127521_($$7, "Rendering item");
            CrashReportCategory $$9 = $$8.m_127514_("Item being rendered");
            $$9.m_128165_("Item Type", () -> String.valueOf(p_174237_.m_41720_()));
            $$9.m_128165_("Item Damage", () -> String.valueOf(p_174237_.m_41773_()));
            $$9.m_128165_("Item NBT", () -> String.valueOf(p_174237_.m_41783_()));
            $$9.m_128165_("Item Foil", () -> String.valueOf(p_174237_.m_41790_()));
            throw new ReportedException($$8);
        }
        this.f_115093_ = $$6.m_7539_() ? this.f_115093_ - 50.0f - (float)p_174241_ : this.f_115093_ - 50.0f;
    }

    public void m_115169_(Font p_115170_, ItemStack p_115171_, int p_115172_, int p_115173_) {
        this.m_115174_(p_115170_, p_115171_, p_115172_, p_115173_, null);
    }

    public void m_115174_(Font p_115175_, ItemStack p_115176_, int p_115177_, int p_115178_, @Nullable String p_115179_) {
        LocalPlayer $$12;
        float $$13;
        if (p_115176_.m_41619_()) {
            return;
        }
        PoseStack $$5 = new PoseStack();
        if (p_115176_.m_41613_() != 1 || p_115179_ != null) {
            String $$6 = p_115179_ == null ? String.valueOf(p_115176_.m_41613_()) : p_115179_;
            $$5.m_85837_(0.0, 0.0, this.f_115093_ + 200.0f);
            MultiBufferSource.BufferSource $$7 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
            p_115175_.m_92811_($$6, p_115177_ + 19 - 2 - p_115175_.m_92895_($$6), p_115178_ + 6 + 3, 0xFFFFFF, true, $$5.m_85850_().m_85861_(), $$7, false, 0, 0xF000F0);
            $$7.m_109911_();
        }
        if (p_115176_.m_150947_()) {
            RenderSystem.m_69465_();
            RenderSystem.m_69472_();
            RenderSystem.m_69461_();
            Tesselator $$8 = Tesselator.m_85913_();
            BufferBuilder $$9 = $$8.m_85915_();
            int $$10 = p_115176_.m_150948_();
            int $$11 = p_115176_.m_150949_();
            this.m_115152_($$9, p_115177_ + 2, p_115178_ + 13, 13, 2, 0, 0, 0, 255);
            this.m_115152_($$9, p_115177_ + 2, p_115178_ + 13, $$10, 1, $$11 >> 16 & 0xFF, $$11 >> 8 & 0xFF, $$11 & 0xFF, 255);
            RenderSystem.m_69478_();
            RenderSystem.m_69493_();
            RenderSystem.m_69482_();
        }
        float f = $$13 = ($$12 = Minecraft.m_91087_().f_91074_) == null ? 0.0f : $$12.m_36335_().m_41521_(p_115176_.m_41720_(), Minecraft.m_91087_().m_91296_());
        if ($$13 > 0.0f) {
            RenderSystem.m_69465_();
            RenderSystem.m_69472_();
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            Tesselator $$14 = Tesselator.m_85913_();
            BufferBuilder $$15 = $$14.m_85915_();
            this.m_115152_($$15, p_115177_, p_115178_ + Mth.m_14143_(16.0f * (1.0f - $$13)), 16, Mth.m_14167_(16.0f * $$13), 255, 255, 255, 127);
            RenderSystem.m_69493_();
            RenderSystem.m_69482_();
        }
    }

    private void m_115152_(BufferBuilder p_115153_, int p_115154_, int p_115155_, int p_115156_, int p_115157_, int p_115158_, int p_115159_, int p_115160_, int p_115161_) {
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        p_115153_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        p_115153_.m_5483_(p_115154_ + 0, p_115155_ + 0, 0.0).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
        p_115153_.m_5483_(p_115154_ + 0, p_115155_ + p_115157_, 0.0).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
        p_115153_.m_5483_(p_115154_ + p_115156_, p_115155_ + p_115157_, 0.0).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
        p_115153_.m_5483_(p_115154_ + p_115156_, p_115155_ + 0, 0.0).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
        BufferUploader.m_231202_(p_115153_.m_231175_());
    }

    @Override
    public void m_6213_(ResourceManager p_115105_) {
        this.f_115095_.m_109403_();
    }
}

