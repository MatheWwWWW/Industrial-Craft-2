/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Locale;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class AxolotlRenderer
extends MobRenderer<Axolotl, AxolotlModel<Axolotl>> {
    private static final Map<Axolotl.Variant, ResourceLocation> f_173918_ = Util.m_137469_(Maps.newHashMap(), p_242076_ -> {
        for (Axolotl.Variant $$1 : Axolotl.Variant.f_149230_) {
            p_242076_.put($$1, new ResourceLocation(String.format(Locale.ROOT, "textures/entity/axolotl/axolotl_%s.png", $$1.m_149253_())));
        }
    });

    public AxolotlRenderer(EntityRendererProvider.Context p_173921_) {
        super(p_173921_, new AxolotlModel(p_173921_.m_174023_(ModelLayers.f_171263_)), 0.5f);
    }

    @Override
    public ResourceLocation m_5478_(Axolotl p_173925_) {
        return f_173918_.get((Object)p_173925_.m_149179_());
    }
}

