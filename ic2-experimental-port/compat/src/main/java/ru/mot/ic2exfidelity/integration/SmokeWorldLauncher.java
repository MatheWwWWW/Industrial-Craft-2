package ru.mot.ic2exfidelity.integration;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;

/** Opens a disposable copied world only when the smoke launcher requests it. */
@Mod.EventBusSubscriber(modid = Ic2ExperimentalFidelity.MOD_ID, value = Dist.CLIENT)
public final class SmokeWorldLauncher {
    private static boolean launched;

    private SmokeWorldLauncher() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        String worldName = System.getProperty("ic2.fidelity.smokeWorld");
        if (launched || worldName == null || worldName.isBlank()
                || event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.m_91087_();
        if (minecraft.f_91073_ == null && minecraft.f_91080_ instanceof TitleScreen) {
            launched = true;
            System.out.println("[IC2-FIDELITY-SMOKE-WORLD] opening " + worldName);
            minecraft.m_231466_().m_233133_(minecraft.f_91080_, worldName);
        }
    }
}
