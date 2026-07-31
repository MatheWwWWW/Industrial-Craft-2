/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;

public class VillagerProfessionLayer<T extends LivingEntity, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    private static final Int2ObjectMap<ResourceLocation> f_117622_ = (Int2ObjectMap)Util.m_137469_(new Int2ObjectOpenHashMap(), p_117657_ -> {
        p_117657_.put(1, (Object)new ResourceLocation("stone"));
        p_117657_.put(2, (Object)new ResourceLocation("iron"));
        p_117657_.put(3, (Object)new ResourceLocation("gold"));
        p_117657_.put(4, (Object)new ResourceLocation("emerald"));
        p_117657_.put(5, (Object)new ResourceLocation("diamond"));
    });
    private final Object2ObjectMap<VillagerType, VillagerMetaDataSection.Hat> f_117623_ = new Object2ObjectOpenHashMap();
    private final Object2ObjectMap<VillagerProfession, VillagerMetaDataSection.Hat> f_117624_ = new Object2ObjectOpenHashMap();
    private final ResourceManager f_117625_;
    private final String f_117626_;

    public VillagerProfessionLayer(RenderLayerParent<T, M> p_174550_, ResourceManager p_174551_, String p_174552_) {
        super(p_174550_);
        this.f_117625_ = p_174551_;
        this.f_117626_ = p_174552_;
    }

    @Override
    public void m_6494_(PoseStack p_117646_, MultiBufferSource p_117647_, int p_117648_, T p_117649_, float p_117650_, float p_117651_, float p_117652_, float p_117653_, float p_117654_, float p_117655_) {
        if (((Entity)p_117649_).m_20145_()) {
            return;
        }
        VillagerData $$10 = ((VillagerDataHolder)p_117649_).m_7141_();
        VillagerType $$11 = $$10.m_35560_();
        VillagerProfession $$12 = $$10.m_35571_();
        VillagerMetaDataSection.Hat $$13 = this.m_117658_(this.f_117623_, "type", Registry.f_122868_, $$11);
        VillagerMetaDataSection.Hat $$14 = this.m_117658_(this.f_117624_, "profession", Registry.f_122869_, $$12);
        Object $$15 = this.m_117386_();
        ((VillagerHeadModel)$$15).m_7491_($$14 == VillagerMetaDataSection.Hat.NONE || $$14 == VillagerMetaDataSection.Hat.PARTIAL && $$13 != VillagerMetaDataSection.Hat.FULL);
        ResourceLocation $$16 = this.m_117668_("type", Registry.f_122868_.m_7981_($$11));
        VillagerProfessionLayer.m_117376_($$15, $$16, p_117646_, p_117647_, p_117648_, p_117649_, 1.0f, 1.0f, 1.0f);
        ((VillagerHeadModel)$$15).m_7491_(true);
        if ($$12 != VillagerProfession.f_35585_ && !((LivingEntity)p_117649_).m_6162_()) {
            ResourceLocation $$17 = this.m_117668_("profession", Registry.f_122869_.m_7981_($$12));
            VillagerProfessionLayer.m_117376_($$15, $$17, p_117646_, p_117647_, p_117648_, p_117649_, 1.0f, 1.0f, 1.0f);
            if ($$12 != VillagerProfession.f_35596_) {
                ResourceLocation $$18 = this.m_117668_("profession_level", (ResourceLocation)f_117622_.get(Mth.m_14045_($$10.m_35576_(), 1, f_117622_.size())));
                VillagerProfessionLayer.m_117376_($$15, $$18, p_117646_, p_117647_, p_117648_, p_117649_, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    private ResourceLocation m_117668_(String p_117669_, ResourceLocation p_117670_) {
        return new ResourceLocation(p_117670_.m_135827_(), "textures/entity/" + this.f_117626_ + "/" + p_117669_ + "/" + p_117670_.m_135815_() + ".png");
    }

    public <K> VillagerMetaDataSection.Hat m_117658_(Object2ObjectMap<K, VillagerMetaDataSection.Hat> p_117659_, String p_117660_, DefaultedRegistry<K> p_117661_, K p_117662_) {
        return (VillagerMetaDataSection.Hat)((Object)p_117659_.computeIfAbsent(p_117662_, p_234880_ -> this.f_117625_.m_213713_(this.m_117668_(p_117660_, p_117661_.m_7981_(p_117662_))).flatMap(p_234875_ -> {
            try {
                return p_234875_.m_215509_().m_214059_(VillagerMetaDataSection.f_119065_).map(VillagerMetaDataSection::m_119070_);
            }
            catch (IOException $$1) {
                return Optional.empty();
            }
        }).orElse(VillagerMetaDataSection.Hat.NONE)));
    }
}

