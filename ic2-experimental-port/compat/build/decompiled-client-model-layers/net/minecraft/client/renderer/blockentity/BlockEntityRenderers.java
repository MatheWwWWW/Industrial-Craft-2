/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.blockentity.LecternRenderer;
import net.minecraft.client.renderer.blockentity.PistonHeadRenderer;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.blockentity.SpawnerRenderer;
import net.minecraft.client.renderer.blockentity.StructureBlockRenderer;
import net.minecraft.client.renderer.blockentity.TheEndGatewayRenderer;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRenderers {
    private static final Map<BlockEntityType<?>, BlockEntityRendererProvider<?>> f_173587_ = Maps.newHashMap();

    private static <T extends BlockEntity> void m_173590_(BlockEntityType<? extends T> p_173591_, BlockEntityRendererProvider<T> p_173592_) {
        f_173587_.put(p_173591_, p_173592_);
    }

    public static Map<BlockEntityType<?>, BlockEntityRenderer<?>> m_173598_(BlockEntityRendererProvider.Context p_173599_) {
        ImmutableMap.Builder $$1 = ImmutableMap.builder();
        f_173587_.forEach((p_173596_, p_173597_) -> {
            try {
                $$1.put(p_173596_, p_173597_.m_173570_(p_173599_));
            }
            catch (Exception $$4) {
                throw new IllegalStateException("Failed to create model for " + Registry.f_122830_.m_7981_((BlockEntityType<?>)p_173596_), $$4);
            }
        });
        return $$1.build();
    }

    static {
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58924_, SignRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58925_, SpawnerRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58926_, PistonHeadRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58918_, ChestRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58920_, ChestRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58919_, ChestRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58928_, EnchantTableRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58908_, LecternRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58929_, TheEndPortalRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58937_, TheEndGatewayRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58930_, BeaconRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58931_, SkullBlockRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58935_, BannerRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58936_, StructureBlockRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58939_, ShulkerBoxRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58940_, BedRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58941_, ConduitRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58909_, BellRenderer::new);
        BlockEntityRenderers.m_173590_(BlockEntityType.f_58911_, CampfireRenderer::new);
    }
}

