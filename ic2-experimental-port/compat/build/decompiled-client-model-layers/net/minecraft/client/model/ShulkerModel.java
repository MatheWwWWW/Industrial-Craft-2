/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Shulker;

public class ShulkerModel<T extends Shulker>
extends ListModel<T> {
    private static final String f_170919_ = "lid";
    private static final String f_170920_ = "base";
    private final ModelPart f_103722_;
    private final ModelPart f_103723_;
    private final ModelPart f_103724_;

    public ShulkerModel(ModelPart p_170922_) {
        super(RenderType::m_110464_);
        this.f_103723_ = p_170922_.m_171324_(f_170919_);
        this.f_103722_ = p_170922_.m_171324_(f_170920_);
        this.f_103724_ = p_170922_.m_171324_("head");
    }

    public static LayerDefinition m_170923_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_170919_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -16.0f, -8.0f, 16.0f, 12.0f, 16.0f), PartPose.m_171419_(0.0f, 24.0f, 0.0f));
        $$1.m_171599_(f_170920_, CubeListBuilder.m_171558_().m_171514_(0, 28).m_171481_(-8.0f, -8.0f, -8.0f, 16.0f, 8.0f, 16.0f), PartPose.m_171419_(0.0f, 24.0f, 0.0f));
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 52).m_171481_(-3.0f, 0.0f, -3.0f, 6.0f, 6.0f, 6.0f), PartPose.m_171419_(0.0f, 12.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6973_(T p_103735_, float p_103736_, float p_103737_, float p_103738_, float p_103739_, float p_103740_) {
        float $$6 = p_103738_ - (float)((Shulker)p_103735_).f_19797_;
        float $$7 = (0.5f + ((Shulker)p_103735_).m_33480_($$6)) * (float)Math.PI;
        float $$8 = -1.0f + Mth.m_14031_($$7);
        float $$9 = 0.0f;
        if ($$7 > (float)Math.PI) {
            $$9 = Mth.m_14031_(p_103738_ * 0.1f) * 0.7f;
        }
        this.f_103723_.m_104227_(0.0f, 16.0f + Mth.m_14031_($$7) * 8.0f + $$9, 0.0f);
        this.f_103723_.f_104204_ = ((Shulker)p_103735_).m_33480_($$6) > 0.3f ? $$8 * $$8 * $$8 * $$8 * (float)Math.PI * 0.125f : 0.0f;
        this.f_103724_.f_104203_ = p_103740_ * ((float)Math.PI / 180);
        this.f_103724_.f_104204_ = (((Shulker)p_103735_).f_20885_ - 180.0f - ((Shulker)p_103735_).f_20883_) * ((float)Math.PI / 180);
    }

    @Override
    public Iterable<ModelPart> m_6195_() {
        return ImmutableList.of((Object)this.f_103722_, (Object)this.f_103723_);
    }

    public ModelPart m_103742_() {
        return this.f_103723_;
    }

    public ModelPart m_103743_() {
        return this.f_103724_;
    }
}

