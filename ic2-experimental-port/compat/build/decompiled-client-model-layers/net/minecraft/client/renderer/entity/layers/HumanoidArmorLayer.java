/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

public class HumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
extends RenderLayer<T, M> {
    private static final Map<String, ResourceLocation> f_117070_ = Maps.newHashMap();
    private final A f_117071_;
    private final A f_117072_;

    public HumanoidArmorLayer(RenderLayerParent<T, M> p_117075_, A p_117076_, A p_117077_) {
        super(p_117075_);
        this.f_117071_ = p_117076_;
        this.f_117072_ = p_117077_;
    }

    @Override
    public void m_6494_(PoseStack p_117096_, MultiBufferSource p_117097_, int p_117098_, T p_117099_, float p_117100_, float p_117101_, float p_117102_, float p_117103_, float p_117104_, float p_117105_) {
        this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.CHEST, p_117098_, this.m_117078_(EquipmentSlot.CHEST));
        this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.LEGS, p_117098_, this.m_117078_(EquipmentSlot.LEGS));
        this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.FEET, p_117098_, this.m_117078_(EquipmentSlot.FEET));
        this.m_117118_(p_117096_, p_117097_, p_117099_, EquipmentSlot.HEAD, p_117098_, this.m_117078_(EquipmentSlot.HEAD));
    }

    private void m_117118_(PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_, A p_117124_) {
        ItemStack $$6 = ((LivingEntity)p_117121_).m_6844_(p_117122_);
        if (!($$6.m_41720_() instanceof ArmorItem)) {
            return;
        }
        ArmorItem $$7 = (ArmorItem)$$6.m_41720_();
        if ($$7.m_40402_() != p_117122_) {
            return;
        }
        ((HumanoidModel)this.m_117386_()).m_102872_(p_117124_);
        this.m_117125_(p_117124_, p_117122_);
        boolean $$8 = this.m_117128_(p_117122_);
        boolean $$9 = $$6.m_41790_();
        if ($$7 instanceof DyeableArmorItem) {
            int $$10 = ((DyeableArmorItem)$$7).m_41121_($$6);
            float $$11 = (float)($$10 >> 16 & 0xFF) / 255.0f;
            float $$12 = (float)($$10 >> 8 & 0xFF) / 255.0f;
            float $$13 = (float)($$10 & 0xFF) / 255.0f;
            this.m_117106_(p_117119_, p_117120_, p_117123_, $$7, $$9, p_117124_, $$8, $$11, $$12, $$13, null);
            this.m_117106_(p_117119_, p_117120_, p_117123_, $$7, $$9, p_117124_, $$8, 1.0f, 1.0f, 1.0f, "overlay");
        } else {
            this.m_117106_(p_117119_, p_117120_, p_117123_, $$7, $$9, p_117124_, $$8, 1.0f, 1.0f, 1.0f, null);
        }
    }

    protected void m_117125_(A p_117126_, EquipmentSlot p_117127_) {
        ((HumanoidModel)p_117126_).m_8009_(false);
        switch (p_117127_) {
            case HEAD: {
                ((HumanoidModel)p_117126_).f_102808_.f_104207_ = true;
                ((HumanoidModel)p_117126_).f_102809_.f_104207_ = true;
                break;
            }
            case CHEST: {
                ((HumanoidModel)p_117126_).f_102810_.f_104207_ = true;
                ((HumanoidModel)p_117126_).f_102811_.f_104207_ = true;
                ((HumanoidModel)p_117126_).f_102812_.f_104207_ = true;
                break;
            }
            case LEGS: {
                ((HumanoidModel)p_117126_).f_102810_.f_104207_ = true;
                ((HumanoidModel)p_117126_).f_102813_.f_104207_ = true;
                ((HumanoidModel)p_117126_).f_102814_.f_104207_ = true;
                break;
            }
            case FEET: {
                ((HumanoidModel)p_117126_).f_102813_.f_104207_ = true;
                ((HumanoidModel)p_117126_).f_102814_.f_104207_ = true;
            }
        }
    }

    private void m_117106_(PoseStack p_117107_, MultiBufferSource p_117108_, int p_117109_, ArmorItem p_117110_, boolean p_117111_, A p_117112_, boolean p_117113_, float p_117114_, float p_117115_, float p_117116_, @Nullable String p_117117_) {
        VertexConsumer $$11 = ItemRenderer.m_115184_(p_117108_, RenderType.m_110431_(this.m_117080_(p_117110_, p_117113_, p_117117_)), false, p_117111_);
        ((AgeableListModel)p_117112_).m_7695_(p_117107_, $$11, p_117109_, OverlayTexture.f_118083_, p_117114_, p_117115_, p_117116_, 1.0f);
    }

    private A m_117078_(EquipmentSlot p_117079_) {
        return this.m_117128_(p_117079_) ? this.f_117071_ : this.f_117072_;
    }

    private boolean m_117128_(EquipmentSlot p_117129_) {
        return p_117129_ == EquipmentSlot.LEGS;
    }

    private ResourceLocation m_117080_(ArmorItem p_117081_, boolean p_117082_, @Nullable String p_117083_) {
        String $$3 = "textures/models/armor/" + p_117081_.m_40401_().m_6082_() + "_layer_" + (p_117082_ ? 2 : 1) + (String)(p_117083_ == null ? "" : "_" + p_117083_) + ".png";
        return f_117070_.computeIfAbsent($$3, ResourceLocation::new);
    }
}

