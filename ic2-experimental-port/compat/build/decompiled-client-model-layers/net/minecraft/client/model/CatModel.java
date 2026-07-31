/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;

public class CatModel<T extends Cat>
extends OcelotModel<T> {
    private float f_102325_;
    private float f_102326_;
    private float f_102327_;

    public CatModel(ModelPart p_170478_) {
        super(p_170478_);
    }

    @Override
    public void m_6839_(T p_102343_, float p_102344_, float p_102345_, float p_102346_) {
        this.f_102325_ = ((Cat)p_102343_).m_28183_(p_102346_);
        this.f_102326_ = ((Cat)p_102343_).m_28187_(p_102346_);
        this.f_102327_ = ((Cat)p_102343_).m_28116_(p_102346_);
        if (this.f_102325_ <= 0.0f) {
            this.f_103135_.f_104203_ = 0.0f;
            this.f_103135_.f_104205_ = 0.0f;
            this.f_170755_.f_104203_ = 0.0f;
            this.f_170755_.f_104205_ = 0.0f;
            this.f_170756_.f_104203_ = 0.0f;
            this.f_170756_.f_104205_ = 0.0f;
            this.f_170756_.f_104200_ = -1.2f;
            this.f_170753_.f_104203_ = 0.0f;
            this.f_170754_.f_104203_ = 0.0f;
            this.f_170754_.f_104205_ = 0.0f;
            this.f_170754_.f_104200_ = -1.1f;
            this.f_170754_.f_104201_ = 18.0f;
        }
        super.m_6839_(p_102343_, p_102344_, p_102345_, p_102346_);
        if (((TamableAnimal)p_102343_).m_21825_()) {
            this.f_103136_.f_104203_ = 0.7853982f;
            this.f_103136_.f_104201_ += -4.0f;
            this.f_103136_.f_104202_ += 5.0f;
            this.f_103135_.f_104201_ += -3.3f;
            this.f_103135_.f_104202_ += 1.0f;
            this.f_103133_.f_104201_ += 8.0f;
            this.f_103133_.f_104202_ += -2.0f;
            this.f_103134_.f_104201_ += 2.0f;
            this.f_103134_.f_104202_ += -0.8f;
            this.f_103133_.f_104203_ = 1.7278761f;
            this.f_103134_.f_104203_ = 2.670354f;
            this.f_170755_.f_104203_ = -0.15707964f;
            this.f_170755_.f_104201_ = 16.1f;
            this.f_170755_.f_104202_ = -7.0f;
            this.f_170756_.f_104203_ = -0.15707964f;
            this.f_170756_.f_104201_ = 16.1f;
            this.f_170756_.f_104202_ = -7.0f;
            this.f_170753_.f_104203_ = -1.5707964f;
            this.f_170753_.f_104201_ = 21.0f;
            this.f_170753_.f_104202_ = 1.0f;
            this.f_170754_.f_104203_ = -1.5707964f;
            this.f_170754_.f_104201_ = 21.0f;
            this.f_170754_.f_104202_ = 1.0f;
            this.f_103137_ = 3;
        }
    }

    @Override
    public void m_6973_(T p_102348_, float p_102349_, float p_102350_, float p_102351_, float p_102352_, float p_102353_) {
        super.m_6973_(p_102348_, p_102349_, p_102350_, p_102351_, p_102352_, p_102353_);
        if (this.f_102325_ > 0.0f) {
            this.f_103135_.f_104205_ = ModelUtils.m_103125_(this.f_103135_.f_104205_, -1.2707963f, this.f_102325_);
            this.f_103135_.f_104204_ = ModelUtils.m_103125_(this.f_103135_.f_104204_, 1.2707963f, this.f_102325_);
            this.f_170755_.f_104203_ = -1.2707963f;
            this.f_170756_.f_104203_ = -0.47079635f;
            this.f_170756_.f_104205_ = -0.2f;
            this.f_170756_.f_104200_ = -0.2f;
            this.f_170753_.f_104203_ = -0.4f;
            this.f_170754_.f_104203_ = 0.5f;
            this.f_170754_.f_104205_ = -0.5f;
            this.f_170754_.f_104200_ = -0.3f;
            this.f_170754_.f_104201_ = 20.0f;
            this.f_103133_.f_104203_ = ModelUtils.m_103125_(this.f_103133_.f_104203_, 0.8f, this.f_102326_);
            this.f_103134_.f_104203_ = ModelUtils.m_103125_(this.f_103134_.f_104203_, -0.4f, this.f_102326_);
        }
        if (this.f_102327_ > 0.0f) {
            this.f_103135_.f_104203_ = ModelUtils.m_103125_(this.f_103135_.f_104203_, -0.58177644f, this.f_102327_);
        }
    }
}

