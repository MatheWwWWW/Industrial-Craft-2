package ru.mot.ic2exfidelity.gravisuit;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Original GraviSuite G-key binding and client-to-server toggle request. */
@Mod.EventBusSubscriber(
        modid = LegacyGravisuitMod.MOD_ID,
        value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.MOD)
public final class LegacyGravisuitClient {
    private static final KeyMapping GRAVITATION_KEY = new KeyMapping(
            "key.gravisuit.gravitation_engine", 71, "key.gravisuit.category");

    private LegacyGravisuitClient() {
    }

    @SubscribeEvent
    public static void registerKey(RegisterKeyMappingsEvent event) {
        event.register(GRAVITATION_KEY);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                LegacyGravisuitContent.PLASMA_BALL.get(),
                LegacyPlasmaBallRenderer::new);
    }

    @Mod.EventBusSubscriber(
            modid = LegacyGravisuitMod.MOD_ID,
            value = Dist.CLIENT,
            bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static final class ForgeEvents {
        private ForgeEvents() {
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }
            Minecraft minecraft = Minecraft.m_91087_();
            while (GRAVITATION_KEY.m_90857_()) {
                if (minecraft.f_91074_ != null && minecraft.f_91080_ == null) {
                    LegacyGravisuitFlight.requestToggle();
                }
            }
        }
    }
}
