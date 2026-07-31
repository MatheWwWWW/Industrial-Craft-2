/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentScreen
extends AbstractContainerScreen<EnchantmentMenu> {
    private static final ResourceLocation f_98747_ = new ResourceLocation("textures/gui/container/enchanting_table.png");
    private static final ResourceLocation f_98748_ = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private final RandomSource f_98750_ = RandomSource.m_216327_();
    private BookModel f_169756_;
    public int f_98740_;
    public float f_98741_;
    public float f_98742_;
    public float f_98743_;
    public float f_98744_;
    public float f_98745_;
    public float f_98746_;
    private ItemStack f_98751_ = ItemStack.f_41583_;

    public EnchantmentScreen(EnchantmentMenu p_98754_, Inventory p_98755_, Component p_98756_) {
        super(p_98754_, p_98755_, p_98756_);
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_169756_ = new BookModel(this.f_96541_.m_167973_().m_171103_(ModelLayers.f_171271_));
    }

    @Override
    public void m_181908_() {
        super.m_181908_();
        this.m_98772_();
    }

    @Override
    public boolean m_6375_(double p_98758_, double p_98759_, int p_98760_) {
        int $$3 = (this.f_96543_ - this.f_97726_) / 2;
        int $$4 = (this.f_96544_ - this.f_97727_) / 2;
        for (int $$5 = 0; $$5 < 3; ++$$5) {
            double $$6 = p_98758_ - (double)($$3 + 60);
            double $$7 = p_98759_ - (double)($$4 + 14 + 19 * $$5);
            if (!($$6 >= 0.0) || !($$7 >= 0.0) || !($$6 < 108.0) || !($$7 < 19.0) || !((EnchantmentMenu)this.f_97732_).m_6366_(this.f_96541_.f_91074_, $$5)) continue;
            this.f_96541_.f_91072_.m_105208_(((EnchantmentMenu)this.f_97732_).f_38840_, $$5);
            return true;
        }
        return super.m_6375_(p_98758_, p_98759_, p_98760_);
    }

    @Override
    protected void m_7286_(PoseStack p_98762_, float p_98763_, int p_98764_, int p_98765_) {
        Lighting.m_84930_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_98747_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        this.m_93228_(p_98762_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
        int $$6 = (int)this.f_96541_.m_91268_().m_85449_();
        RenderSystem.m_69949_((this.f_96543_ - 320) / 2 * $$6, (this.f_96544_ - 240) / 2 * $$6, 320 * $$6, 240 * $$6);
        Matrix4f $$7 = Matrix4f.m_27653_(-0.34f, 0.23f, 0.0f);
        $$7.m_27644_(Matrix4f.m_27625_(90.0, 1.3333334f, 9.0f, 80.0f));
        RenderSystem.m_157183_();
        RenderSystem.m_157425_($$7);
        p_98762_.m_85836_();
        PoseStack.Pose $$8 = p_98762_.m_85850_();
        $$8.m_85861_().m_27624_();
        $$8.m_85864_().m_8180_();
        p_98762_.m_85837_(0.0, 3.3f, 1984.0);
        float $$9 = 5.0f;
        p_98762_.m_85841_(5.0f, 5.0f, 5.0f);
        p_98762_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
        p_98762_.m_85845_(Vector3f.f_122223_.m_122240_(20.0f));
        float $$10 = Mth.m_14179_(p_98763_, this.f_98746_, this.f_98745_);
        p_98762_.m_85837_((1.0f - $$10) * 0.2f, (1.0f - $$10) * 0.1f, (1.0f - $$10) * 0.25f);
        float $$11 = -(1.0f - $$10) * 90.0f - 90.0f;
        p_98762_.m_85845_(Vector3f.f_122225_.m_122240_($$11));
        p_98762_.m_85845_(Vector3f.f_122223_.m_122240_(180.0f));
        float $$12 = Mth.m_14179_(p_98763_, this.f_98742_, this.f_98741_) + 0.25f;
        float $$13 = Mth.m_14179_(p_98763_, this.f_98742_, this.f_98741_) + 0.75f;
        $$12 = ($$12 - (float)Mth.m_14080_($$12)) * 1.6f - 0.3f;
        $$13 = ($$13 - (float)Mth.m_14080_($$13)) * 1.6f - 0.3f;
        if ($$12 < 0.0f) {
            $$12 = 0.0f;
        }
        if ($$13 < 0.0f) {
            $$13 = 0.0f;
        }
        if ($$12 > 1.0f) {
            $$12 = 1.0f;
        }
        if ($$13 > 1.0f) {
            $$13 = 1.0f;
        }
        this.f_169756_.m_102292_(0.0f, $$12, $$13, $$10);
        MultiBufferSource.BufferSource $$14 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
        VertexConsumer $$15 = $$14.m_6299_(this.f_169756_.m_103119_(f_98748_));
        this.f_169756_.m_7695_(p_98762_, $$15, 0xF000F0, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        $$14.m_109911_();
        p_98762_.m_85849_();
        RenderSystem.m_69949_(0, 0, this.f_96541_.m_91268_().m_85441_(), this.f_96541_.m_91268_().m_85442_());
        RenderSystem.m_157424_();
        Lighting.m_84931_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNames.m_98734_().m_98735_(((EnchantmentMenu)this.f_97732_).m_39493_());
        int $$16 = ((EnchantmentMenu)this.f_97732_).m_39492_();
        for (int $$17 = 0; $$17 < 3; ++$$17) {
            int $$18 = $$4 + 60;
            int $$19 = $$18 + 20;
            this.m_93250_(0);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_98747_);
            int $$20 = ((EnchantmentMenu)this.f_97732_).f_39446_[$$17];
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            if ($$20 == 0) {
                this.m_93228_(p_98762_, $$18, $$5 + 14 + 19 * $$17, 0, 185, 108, 19);
                continue;
            }
            String $$21 = "" + $$20;
            int $$22 = 86 - this.f_96547_.m_92895_($$21);
            FormattedText $$23 = EnchantmentNames.m_98734_().m_98737_(this.f_96547_, $$22);
            int $$24 = 6839882;
            if (!($$16 >= $$17 + 1 && this.f_96541_.f_91074_.f_36078_ >= $$20 || this.f_96541_.f_91074_.m_150110_().f_35937_)) {
                this.m_93228_(p_98762_, $$18, $$5 + 14 + 19 * $$17, 0, 185, 108, 19);
                this.m_93228_(p_98762_, $$18 + 1, $$5 + 15 + 19 * $$17, 16 * $$17, 239, 16, 16);
                this.f_96547_.m_92857_($$23, $$19, $$5 + 16 + 19 * $$17, $$22, ($$24 & 0xFEFEFE) >> 1);
                $$24 = 4226832;
            } else {
                int $$25 = p_98764_ - ($$4 + 60);
                int $$26 = p_98765_ - ($$5 + 14 + 19 * $$17);
                if ($$25 >= 0 && $$26 >= 0 && $$25 < 108 && $$26 < 19) {
                    this.m_93228_(p_98762_, $$18, $$5 + 14 + 19 * $$17, 0, 204, 108, 19);
                    $$24 = 0xFFFF80;
                } else {
                    this.m_93228_(p_98762_, $$18, $$5 + 14 + 19 * $$17, 0, 166, 108, 19);
                }
                this.m_93228_(p_98762_, $$18 + 1, $$5 + 15 + 19 * $$17, 16 * $$17, 223, 16, 16);
                this.f_96547_.m_92857_($$23, $$19, $$5 + 16 + 19 * $$17, $$22, $$24);
                $$24 = 8453920;
            }
            this.f_96547_.m_92750_(p_98762_, $$21, $$19 + 86 - this.f_96547_.m_92895_($$21), $$5 + 16 + 19 * $$17 + 7, $$24);
        }
    }

    @Override
    public void m_6305_(PoseStack p_98767_, int p_98768_, int p_98769_, float p_98770_) {
        p_98770_ = this.f_96541_.m_91296_();
        this.m_7333_(p_98767_);
        super.m_6305_(p_98767_, p_98768_, p_98769_, p_98770_);
        this.m_7025_(p_98767_, p_98768_, p_98769_);
        boolean $$4 = this.f_96541_.f_91074_.m_150110_().f_35937_;
        int $$5 = ((EnchantmentMenu)this.f_97732_).m_39492_();
        for (int $$6 = 0; $$6 < 3; ++$$6) {
            int $$7 = ((EnchantmentMenu)this.f_97732_).f_39446_[$$6];
            Enchantment $$8 = Enchantment.m_44697_(((EnchantmentMenu)this.f_97732_).f_39447_[$$6]);
            int $$9 = ((EnchantmentMenu)this.f_97732_).f_39448_[$$6];
            int $$10 = $$6 + 1;
            if (!this.m_6774_(60, 14 + 19 * $$6, 108, 17, p_98768_, p_98769_) || $$7 <= 0 || $$9 < 0 || $$8 == null) continue;
            ArrayList $$11 = Lists.newArrayList();
            $$11.add(Component.m_237110_("container.enchant.clue", $$8.m_44700_($$9)).m_130940_(ChatFormatting.WHITE));
            if (!$$4) {
                $$11.add(CommonComponents.f_237098_);
                if (this.f_96541_.f_91074_.f_36078_ < $$7) {
                    $$11.add(Component.m_237110_("container.enchant.level.requirement", ((EnchantmentMenu)this.f_97732_).f_39446_[$$6]).m_130940_(ChatFormatting.RED));
                } else {
                    MutableComponent $$15;
                    MutableComponent $$13;
                    if ($$10 == 1) {
                        MutableComponent $$12 = Component.m_237115_("container.enchant.lapis.one");
                    } else {
                        $$13 = Component.m_237110_("container.enchant.lapis.many", $$10);
                    }
                    $$11.add($$13.m_130940_($$5 >= $$10 ? ChatFormatting.GRAY : ChatFormatting.RED));
                    if ($$10 == 1) {
                        MutableComponent $$14 = Component.m_237115_("container.enchant.level.one");
                    } else {
                        $$15 = Component.m_237110_("container.enchant.level.many", $$10);
                    }
                    $$11.add($$15.m_130940_(ChatFormatting.GRAY));
                }
            }
            this.m_96597_(p_98767_, $$11, p_98768_, p_98769_);
            break;
        }
    }

    public void m_98772_() {
        ItemStack $$0 = ((EnchantmentMenu)this.f_97732_).m_38853_(0).m_7993_();
        if (!ItemStack.m_41728_($$0, this.f_98751_)) {
            this.f_98751_ = $$0;
            do {
                this.f_98743_ += (float)(this.f_98750_.m_188503_(4) - this.f_98750_.m_188503_(4));
            } while (this.f_98741_ <= this.f_98743_ + 1.0f && this.f_98741_ >= this.f_98743_ - 1.0f);
        }
        ++this.f_98740_;
        this.f_98742_ = this.f_98741_;
        this.f_98746_ = this.f_98745_;
        boolean $$1 = false;
        for (int $$2 = 0; $$2 < 3; ++$$2) {
            if (((EnchantmentMenu)this.f_97732_).f_39446_[$$2] == 0) continue;
            $$1 = true;
        }
        this.f_98745_ = $$1 ? (this.f_98745_ += 0.2f) : (this.f_98745_ -= 0.2f);
        this.f_98745_ = Mth.m_14036_(this.f_98745_, 0.0f, 1.0f);
        float $$3 = (this.f_98743_ - this.f_98741_) * 0.4f;
        float $$4 = 0.2f;
        $$3 = Mth.m_14036_($$3, -0.2f, 0.2f);
        this.f_98744_ += ($$3 - this.f_98744_) * 0.9f;
        this.f_98741_ += this.f_98744_;
    }
}

