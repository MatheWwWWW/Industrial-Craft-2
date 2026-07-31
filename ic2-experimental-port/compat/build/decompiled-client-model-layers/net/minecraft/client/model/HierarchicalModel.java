/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

public abstract class HierarchicalModel<E extends Entity>
extends EntityModel<E> {
    private static final Vector3f f_233379_ = new Vector3f();

    public HierarchicalModel() {
        this(RenderType::m_110458_);
    }

    public HierarchicalModel(Function<ResourceLocation, RenderType> p_170623_) {
        super(p_170623_);
    }

    @Override
    public void m_7695_(PoseStack p_170625_, VertexConsumer p_170626_, int p_170627_, int p_170628_, float p_170629_, float p_170630_, float p_170631_, float p_170632_) {
        this.m_142109_().m_104306_(p_170625_, p_170626_, p_170627_, p_170628_, p_170629_, p_170630_, p_170631_, p_170632_);
    }

    public abstract ModelPart m_142109_();

    public Optional<ModelPart> m_233393_(String p_233394_) {
        return this.m_142109_().m_171331_().filter(p_233400_ -> p_233400_.m_233562_(p_233394_)).findFirst().map(p_233397_ -> p_233397_.m_171324_(p_233394_));
    }

    protected void m_233381_(AnimationState p_233382_, AnimationDefinition p_233383_, float p_233384_) {
        this.m_233385_(p_233382_, p_233383_, p_233384_, 1.0f);
    }

    protected void m_233385_(AnimationState p_233386_, AnimationDefinition p_233387_, float p_233388_, float p_233389_) {
        p_233386_.m_216974_(p_233388_, p_233389_);
        p_233386_.m_216979_(p_233392_ -> KeyframeAnimations.m_232319_(this, p_233387_, p_233392_.m_216981_(), 1.0f, f_233379_));
    }
}

