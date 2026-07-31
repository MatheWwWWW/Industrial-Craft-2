package ru.mot.ic2exfidelity.friends;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;

/** Registry holder for the friend manager's non-world-facing menu. */
public final class LegacyFriendContent {
    private static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(
                    ForgeRegistries.MENU_TYPES, Ic2ExperimentalFidelity.MOD_ID);

    public static final RegistryObject<MenuType<LegacyFriendsMenu>> FRIENDS_MENU =
            MENUS.register("friends", () -> IForgeMenuType.create(
                    (syncId, inventory, buffer) ->
                            new LegacyFriendsMenu(syncId, inventory)));

    private LegacyFriendContent() {
    }

    public static void register(IEventBus modBus) {
        MENUS.register(modBus);
    }
}
