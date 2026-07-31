/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class AgeableListModel<E extends Entity>
extends EntityModel<E> {
    private final boolean f_102007_;
    private final float f_170338_;
    private final float f_170339_;
    private final float f_102010_;
    private final float f_102011_;
    private final float f_102012_;

    protected AgeableListModel(boolean p_102023_, float p_102024_, float p_102025_) {
        this(p_102023_, p_102024_, p_102025_, 2.0f, 2.0f, 24.0f);
    }

    protected AgeableListModel(boolean p_102027_, float p_102028_, float p_102029_, float p_102030_, float p_102031_, float p_102032_) {
        this(RenderType::m_110458_, p_102027_, p_102028_, p_102029_, p_102030_, p_102031_, p_102032_);
    }

    protected AgeableListModel(Function<ResourceLocation, RenderType> p_102015_, boolean p_102016_, float p_102017_, float p_102018_, float p_102019_, float p_102020_, float p_102021_) {
        super(p_102015_);
        this.f_102007_ = p_102016_;
        this.f_170338_ = p_102017_;
        this.f_170339_ = p_102018_;
        this.f_102010_ = p_102019_;
        this.f_102011_ = p_102020_;
        this.f_102012_ = p_102021_;
    }

    protected AgeableListModel() {
        this(false, 5.0f, 2.0f);
    }

    @Override
    public void m_7695_(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
        if (this.f_102610_) {
            p_102034_.m_85836_();
            if (this.f_102007_) {
                float $$8 = 1.5f / this.f_102010_;
                p_102034_.m_85841_($$8, $$8, $$8);
            }
            p_102034_.m_85837_(0.0, this.f_170338_ / 16.0f, this.f_170339_ / 16.0f);
            this.m_5607_().forEach(p_102081_ -> p_102081_.m_104306_(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_));
            p_102034_.m_85849_();
            p_102034_.m_85836_();
            float $$9 = 1.0f / this.f_102011_;
            p_102034_.m_85841_($$9, $$9, $$9);
            p_102034_.m_85837_(0.0, this.f_102012_ / 16.0f, 0.0);
            this.m_5608_().forEach(p_102071_ -> p_102071_.m_104306_(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_));
            p_102034_.m_85849_();
        } else {
            this.m_5607_().forEach(p_102061_ -> p_102061_.m_104306_(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_));
            this.m_5608_().forEach(p_102051_ -> p_102051_.m_104306_(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_));
        }
    }

    protected abstract Iterable<ModelPart> m_5607_();

    protected abstract Iterable<ModelPart> m_5608_();
}

