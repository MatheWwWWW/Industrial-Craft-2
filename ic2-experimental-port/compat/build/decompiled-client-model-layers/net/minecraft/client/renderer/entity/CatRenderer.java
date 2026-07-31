/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.List;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class CatRenderer
extends MobRenderer<Cat, CatModel<Cat>> {
    public CatRenderer(EntityRendererProvider.Context p_173943_) {
        super(p_173943_, new CatModel(p_173943_.m_174023_(ModelLayers.f_171272_)), 0.4f);
        this.m_115326_(new CatCollarLayer(this, p_173943_.m_174027_()));
    }

    @Override
    public ResourceLocation m_5478_(Cat p_113950_) {
        return p_113950_.m_28162_();
    }

    @Override
    protected void m_7546_(Cat p_113952_, PoseStack p_113953_, float p_113954_) {
        super.m_7546_(p_113952_, p_113953_, p_113954_);
        p_113953_.m_85841_(0.8f, 0.8f, 0.8f);
    }

    @Override
    protected void m_7523_(Cat p_113956_, PoseStack p_113957_, float p_113958_, float p_113959_, float p_113960_) {
        super.m_7523_(p_113956_, p_113957_, p_113958_, p_113959_, p_113960_);
        float $$5 = p_113956_.m_28183_(p_113960_);
        if ($$5 > 0.0f) {
            p_113957_.m_85837_(0.4f * $$5, 0.15f * $$5, 0.1f * $$5);
            p_113957_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14189_($$5, 0.0f, 90.0f)));
            BlockPos $$6 = p_113956_.m_20183_();
            List<Player> $$7 = p_113956_.f_19853_.m_45976_(Player.class, new AABB($$6).m_82377_(2.0, 2.0, 2.0));
            for (Player $$8 : $$7) {
                if (!$$8.m_5803_()) continue;
                p_113957_.m_85837_(0.15f * $$5, 0.0, 0.0);
                break;
            }
        }
    }
}

