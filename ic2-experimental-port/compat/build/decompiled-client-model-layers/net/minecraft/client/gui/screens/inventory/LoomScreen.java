/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LoomScreen
extends AbstractContainerScreen<LoomMenu> {
    private static final ResourceLocation f_99060_ = new ResourceLocation("textures/gui/container/loom.png");
    private static final int f_169776_ = 4;
    private static final int f_169777_ = 4;
    private static final int f_169778_ = 12;
    private static final int f_169779_ = 15;
    private static final int f_169780_ = 14;
    private static final int f_169781_ = 56;
    private static final int f_169782_ = 60;
    private static final int f_169783_ = 13;
    private ModelPart f_99062_;
    @Nullable
    private List<Pair<Holder<BannerPattern>, DyeColor>> f_99063_;
    private ItemStack f_99064_ = ItemStack.f_41583_;
    private ItemStack f_99065_ = ItemStack.f_41583_;
    private ItemStack f_99066_ = ItemStack.f_41583_;
    private boolean f_99067_;
    private boolean f_99069_;
    private float f_99070_;
    private boolean f_99071_;
    private int f_232823_;

    public LoomScreen(LoomMenu p_99075_, Inventory p_99076_, Component p_99077_) {
        super(p_99075_, p_99076_, p_99077_);
        p_99075_.m_39878_(this::m_99112_);
        this.f_97729_ -= 2;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_99062_ = this.f_96541_.m_167973_().m_171103_(ModelLayers.f_171264_).m_171324_("flag");
    }

    @Override
    public void m_6305_(PoseStack p_99104_, int p_99105_, int p_99106_, float p_99107_) {
        super.m_6305_(p_99104_, p_99105_, p_99106_, p_99107_);
        this.m_7025_(p_99104_, p_99105_, p_99106_);
    }

    private int m_232828_() {
        return Mth.m_184652_(((LoomMenu)this.f_97732_).m_219995_().size(), 4);
    }

    @Override
    protected void m_7286_(PoseStack p_99099_, float p_99100_, int p_99101_, int p_99102_) {
        this.m_7333_(p_99099_);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_99060_);
        int $$4 = this.f_97735_;
        int $$5 = this.f_97736_;
        this.m_93228_(p_99099_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
        Slot $$6 = ((LoomMenu)this.f_97732_).m_39894_();
        Slot $$7 = ((LoomMenu)this.f_97732_).m_39895_();
        Slot $$8 = ((LoomMenu)this.f_97732_).m_39896_();
        Slot $$9 = ((LoomMenu)this.f_97732_).m_39897_();
        if (!$$6.m_6657_()) {
            this.m_93228_(p_99099_, $$4 + $$6.f_40220_, $$5 + $$6.f_40221_, this.f_97726_, 0, 16, 16);
        }
        if (!$$7.m_6657_()) {
            this.m_93228_(p_99099_, $$4 + $$7.f_40220_, $$5 + $$7.f_40221_, this.f_97726_ + 16, 0, 16, 16);
        }
        if (!$$8.m_6657_()) {
            this.m_93228_(p_99099_, $$4 + $$8.f_40220_, $$5 + $$8.f_40221_, this.f_97726_ + 32, 0, 16, 16);
        }
        int $$10 = (int)(41.0f * this.f_99070_);
        this.m_93228_(p_99099_, $$4 + 119, $$5 + 13 + $$10, 232 + (this.f_99067_ ? 0 : 12), 0, 12, 15);
        Lighting.m_84930_();
        if (this.f_99063_ != null && !this.f_99069_) {
            MultiBufferSource.BufferSource $$11 = this.f_96541_.m_91269_().m_110104_();
            p_99099_.m_85836_();
            p_99099_.m_85837_($$4 + 139, $$5 + 52, 0.0);
            p_99099_.m_85841_(24.0f, -24.0f, 1.0f);
            p_99099_.m_85837_(0.5, 0.5, 0.5);
            float $$12 = 0.6666667f;
            p_99099_.m_85841_(0.6666667f, -0.6666667f, -0.6666667f);
            this.f_99062_.f_104203_ = 0.0f;
            this.f_99062_.f_104201_ = -32.0f;
            BannerRenderer.m_112065_(p_99099_, $$11, 0xF000F0, OverlayTexture.f_118083_, this.f_99062_, ModelBakery.f_119224_, true, this.f_99063_);
            p_99099_.m_85849_();
            $$11.m_109911_();
        } else if (this.f_99069_) {
            this.m_93228_(p_99099_, $$4 + $$9.f_40220_ - 2, $$5 + $$9.f_40221_ - 2, this.f_97726_, 17, 17, 16);
        }
        if (this.f_99067_) {
            int $$13 = $$4 + 60;
            int $$14 = $$5 + 13;
            List<Holder<BannerPattern>> $$15 = ((LoomMenu)this.f_97732_).m_219995_();
            block0: for (int $$16 = 0; $$16 < 4; ++$$16) {
                for (int $$17 = 0; $$17 < 4; ++$$17) {
                    int $$25;
                    boolean $$22;
                    int $$18 = $$16 + this.f_232823_;
                    int $$19 = $$18 * 4 + $$17;
                    if ($$19 >= $$15.size()) break block0;
                    RenderSystem.m_157456_(0, f_99060_);
                    int $$20 = $$13 + $$17 * 14;
                    int $$21 = $$14 + $$16 * 14;
                    boolean bl = $$22 = p_99101_ >= $$20 && p_99102_ >= $$21 && p_99101_ < $$20 + 14 && p_99102_ < $$21 + 14;
                    if ($$19 == ((LoomMenu)this.f_97732_).m_39891_()) {
                        int $$23 = this.f_97727_ + 14;
                    } else if ($$22) {
                        int $$24 = this.f_97727_ + 28;
                    } else {
                        $$25 = this.f_97727_;
                    }
                    this.m_93228_(p_99099_, $$20, $$21, 0, $$25, 14, 14);
                    this.m_232824_($$15.get($$19), $$20, $$21);
                }
            }
        }
        Lighting.m_84931_();
    }

    private void m_232824_(Holder<BannerPattern> p_232825_, int p_232826_, int p_232827_) {
        CompoundTag $$3 = new CompoundTag();
        ListTag $$4 = new BannerPattern.Builder().m_222705_(BannerPatterns.f_222726_, DyeColor.GRAY).m_222708_(p_232825_, DyeColor.WHITE).m_58587_();
        $$3.m_128365_("Patterns", $$4);
        ItemStack $$5 = new ItemStack(Items.f_42667_);
        BlockItem.m_186338_($$5, BlockEntityType.f_58935_, $$3);
        PoseStack $$6 = new PoseStack();
        $$6.m_85836_();
        $$6.m_85837_((float)p_232826_ + 0.5f, p_232827_ + 16, 0.0);
        $$6.m_85841_(6.0f, -6.0f, 1.0f);
        $$6.m_85837_(0.5, 0.5, 0.0);
        $$6.m_85837_(0.5, 0.5, 0.5);
        float $$7 = 0.6666667f;
        $$6.m_85841_(0.6666667f, -0.6666667f, -0.6666667f);
        MultiBufferSource.BufferSource $$8 = this.f_96541_.m_91269_().m_110104_();
        this.f_99062_.f_104203_ = 0.0f;
        this.f_99062_.f_104201_ = -32.0f;
        List<Pair<Holder<BannerPattern>, DyeColor>> $$9 = BannerBlockEntity.m_58484_(DyeColor.GRAY, BannerBlockEntity.m_58487_($$5));
        BannerRenderer.m_112065_($$6, $$8, 0xF000F0, OverlayTexture.f_118083_, this.f_99062_, ModelBakery.f_119224_, true, $$9);
        $$6.m_85849_();
        $$8.m_109911_();
    }

    @Override
    public boolean m_6375_(double p_99083_, double p_99084_, int p_99085_) {
        this.f_99071_ = false;
        if (this.f_99067_) {
            int $$3 = this.f_97735_ + 60;
            int $$4 = this.f_97736_ + 13;
            for (int $$5 = 0; $$5 < 4; ++$$5) {
                for (int $$6 = 0; $$6 < 4; ++$$6) {
                    double $$7 = p_99083_ - (double)($$3 + $$6 * 14);
                    double $$8 = p_99084_ - (double)($$4 + $$5 * 14);
                    int $$9 = $$5 + this.f_232823_;
                    int $$10 = $$9 * 4 + $$6;
                    if (!($$7 >= 0.0) || !($$8 >= 0.0) || !($$7 < 14.0) || !($$8 < 14.0) || !((LoomMenu)this.f_97732_).m_6366_(this.f_96541_.f_91074_, $$10)) continue;
                    Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12491_, 1.0f));
                    this.f_96541_.f_91072_.m_105208_(((LoomMenu)this.f_97732_).f_38840_, $$10);
                    return true;
                }
            }
            $$3 = this.f_97735_ + 119;
            $$4 = this.f_97736_ + 9;
            if (p_99083_ >= (double)$$3 && p_99083_ < (double)($$3 + 12) && p_99084_ >= (double)$$4 && p_99084_ < (double)($$4 + 56)) {
                this.f_99071_ = true;
            }
        }
        return super.m_6375_(p_99083_, p_99084_, p_99085_);
    }

    @Override
    public boolean m_7979_(double p_99087_, double p_99088_, int p_99089_, double p_99090_, double p_99091_) {
        int $$5 = this.m_232828_() - 4;
        if (this.f_99071_ && this.f_99067_ && $$5 > 0) {
            int $$6 = this.f_97736_ + 13;
            int $$7 = $$6 + 56;
            this.f_99070_ = ((float)p_99088_ - (float)$$6 - 7.5f) / ((float)($$7 - $$6) - 15.0f);
            this.f_99070_ = Mth.m_14036_(this.f_99070_, 0.0f, 1.0f);
            this.f_232823_ = Math.max((int)((double)(this.f_99070_ * (float)$$5) + 0.5), 0);
            return true;
        }
        return super.m_7979_(p_99087_, p_99088_, p_99089_, p_99090_, p_99091_);
    }

    @Override
    public boolean m_6050_(double p_99079_, double p_99080_, double p_99081_) {
        int $$3 = this.m_232828_() - 4;
        if (this.f_99067_ && $$3 > 0) {
            float $$4 = (float)p_99081_ / (float)$$3;
            this.f_99070_ = Mth.m_14036_(this.f_99070_ - $$4, 0.0f, 1.0f);
            this.f_232823_ = Math.max((int)(this.f_99070_ * (float)$$3 + 0.5f), 0);
        }
        return true;
    }

    @Override
    protected boolean m_7467_(double p_99093_, double p_99094_, int p_99095_, int p_99096_, int p_99097_) {
        return p_99093_ < (double)p_99095_ || p_99094_ < (double)p_99096_ || p_99093_ >= (double)(p_99095_ + this.f_97726_) || p_99094_ >= (double)(p_99096_ + this.f_97727_);
    }

    private void m_99112_() {
        ItemStack $$0 = ((LoomMenu)this.f_97732_).m_39897_().m_7993_();
        this.f_99063_ = $$0.m_41619_() ? null : BannerBlockEntity.m_58484_(((BannerItem)$$0.m_41720_()).m_40545_(), BannerBlockEntity.m_58487_($$0));
        ItemStack $$1 = ((LoomMenu)this.f_97732_).m_39894_().m_7993_();
        ItemStack $$2 = ((LoomMenu)this.f_97732_).m_39895_().m_7993_();
        ItemStack $$3 = ((LoomMenu)this.f_97732_).m_39896_().m_7993_();
        CompoundTag $$4 = BlockItem.m_186336_($$1);
        boolean bl = this.f_99069_ = $$4 != null && $$4.m_128425_("Patterns", 9) && !$$1.m_41619_() && $$4.m_128437_("Patterns", 10).size() >= 6;
        if (this.f_99069_) {
            this.f_99063_ = null;
        }
        if (!(ItemStack.m_41728_($$1, this.f_99064_) && ItemStack.m_41728_($$2, this.f_99065_) && ItemStack.m_41728_($$3, this.f_99066_))) {
            boolean bl2 = this.f_99067_ = !$$1.m_41619_() && !$$2.m_41619_() && !this.f_99069_ && !((LoomMenu)this.f_97732_).m_219995_().isEmpty();
        }
        if (this.f_232823_ >= this.m_232828_()) {
            this.f_232823_ = 0;
            this.f_99070_ = 0.0f;
        }
        this.f_99064_ = $$1.m_41777_();
        this.f_99065_ = $$2.m_41777_();
        this.f_99066_ = $$3.m_41777_();
    }
}

