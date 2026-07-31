/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;

public class CustomHeadLayer<T extends LivingEntity, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    private final float f_116709_;
    private final float f_116710_;
    private final float f_116711_;
    private final Map<SkullBlock.Type, SkullModelBase> f_174473_;
    private final ItemInHandRenderer f_234820_;

    public CustomHeadLayer(RenderLayerParent<T, M> p_234829_, EntityModelSet p_234830_, ItemInHandRenderer p_234831_) {
        this(p_234829_, p_234830_, 1.0f, 1.0f, 1.0f, p_234831_);
    }

    public CustomHeadLayer(RenderLayerParent<T, M> p_234822_, EntityModelSet p_234823_, float p_234824_, float p_234825_, float p_234826_, ItemInHandRenderer p_234827_) {
        super(p_234822_);
        this.f_116709_ = p_234824_;
        this.f_116710_ = p_234825_;
        this.f_116711_ = p_234826_;
        this.f_174473_ = SkullBlockRenderer.m_173661_(p_234823_);
        this.f_234820_ = p_234827_;
    }

    @Override
    public void m_6494_(PoseStack p_116731_, MultiBufferSource p_116732_, int p_116733_, T p_116734_, float p_116735_, float p_116736_, float p_116737_, float p_116738_, float p_116739_, float p_116740_) {
        boolean $$12;
        ItemStack $$10 = ((LivingEntity)p_116734_).m_6844_(EquipmentSlot.HEAD);
        if ($$10.m_41619_()) {
            return;
        }
        Item $$11 = $$10.m_41720_();
        p_116731_.m_85836_();
        p_116731_.m_85841_(this.f_116709_, this.f_116710_, this.f_116711_);
        boolean bl = $$12 = p_116734_ instanceof Villager || p_116734_ instanceof ZombieVillager;
        if (((LivingEntity)p_116734_).m_6162_() && !(p_116734_ instanceof Villager)) {
            float $$13 = 2.0f;
            float $$14 = 1.4f;
            p_116731_.m_85837_(0.0, 0.03125, 0.0);
            p_116731_.m_85841_(0.7f, 0.7f, 0.7f);
            p_116731_.m_85837_(0.0, 1.0, 0.0);
        }
        ((HeadedModel)this.m_117386_()).m_5585_().m_104299_(p_116731_);
        if ($$11 instanceof BlockItem && ((BlockItem)$$11).m_40614_() instanceof AbstractSkullBlock) {
            CompoundTag $$17;
            float $$15 = 1.1875f;
            p_116731_.m_85841_(1.1875f, -1.1875f, -1.1875f);
            if ($$12) {
                p_116731_.m_85837_(0.0, 0.0625, 0.0);
            }
            GameProfile $$16 = null;
            if ($$10.m_41782_() && ($$17 = $$10.m_41783_()).m_128425_("SkullOwner", 10)) {
                $$16 = NbtUtils.m_129228_($$17.m_128469_("SkullOwner"));
            }
            p_116731_.m_85837_(-0.5, 0.0, -0.5);
            SkullBlock.Type $$18 = ((AbstractSkullBlock)((BlockItem)$$11).m_40614_()).m_48754_();
            SkullModelBase $$19 = this.f_174473_.get($$18);
            RenderType $$20 = SkullBlockRenderer.m_112523_($$18, $$16);
            SkullBlockRenderer.m_173663_(null, 180.0f, p_116735_, p_116731_, p_116732_, p_116733_, $$19, $$20);
        } else if (!($$11 instanceof ArmorItem) || ((ArmorItem)$$11).m_40402_() != EquipmentSlot.HEAD) {
            CustomHeadLayer.m_174483_(p_116731_, $$12);
            this.f_234820_.m_109322_((LivingEntity)p_116734_, $$10, ItemTransforms.TransformType.HEAD, false, p_116731_, p_116732_, p_116733_);
        }
        p_116731_.m_85849_();
    }

    public static void m_174483_(PoseStack p_174484_, boolean p_174485_) {
        float $$2 = 0.625f;
        p_174484_.m_85837_(0.0, -0.25, 0.0);
        p_174484_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        p_174484_.m_85841_(0.625f, -0.625f, -0.625f);
        if (p_174485_) {
            p_174484_.m_85837_(0.0, 0.1875, 0.0);
        }
    }
}

