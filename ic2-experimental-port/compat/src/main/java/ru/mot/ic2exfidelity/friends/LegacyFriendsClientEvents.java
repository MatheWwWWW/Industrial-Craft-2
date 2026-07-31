package ru.mot.ic2exfidelity.friends;

import ic2.core.IC2;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;

/** Opens friend settings once per X+V key chord, as in IC2 Classic. */
@Mod.EventBusSubscriber(
        modid = Ic2ExperimentalFidelity.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT)
public final class LegacyFriendsClientEvents {
    private static boolean chordWasDown;

    private LegacyFriendsClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.m_91087_();
        boolean chord = minecraft.f_91074_ != null
                && minecraft.f_91080_ == null
                && LegacyFriendsClient.TOGGLE_KEY.m_90857_()
                && IC2.keyboard.isHudModeKeyDown(minecraft.f_91074_);
        if (chord && !chordWasDown) {
            LegacyFriendNetwork.open();
        }
        chordWasDown = chord;
    }
}
