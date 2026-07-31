package ru.mot.ic2exfidelity.friends;

import ic2.core.proxy.SideProxyClient;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;

/** Client registrations matching IC2 Classic's V toggle key. */
@Mod.EventBusSubscriber(
        modid = Ic2ExperimentalFidelity.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public final class LegacyFriendsClient {
    public static final KeyMapping TOGGLE_KEY = new KeyMapping(
            "key.ic2.toggle", 86, "IC2");

    private LegacyFriendsClient() {
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> SideProxyClient.envProxy.registerScreen(
                LegacyFriendContent.FRIENDS_MENU.get(), LegacyFriendsScreen::new));
    }
}
