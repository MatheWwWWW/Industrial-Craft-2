package ru.mot.ic2exfidelity.legacy;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

@Mod.EventBusSubscriber(
        modid = "ic2_experimental_fidelity",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public final class LegacyDynamiteClientEvents {
    private LegacyDynamiteClientEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(RestoredLegacyContent.DYNAMITE_ENTITY.get(), ThrownItemRenderer::new);
    }
}
