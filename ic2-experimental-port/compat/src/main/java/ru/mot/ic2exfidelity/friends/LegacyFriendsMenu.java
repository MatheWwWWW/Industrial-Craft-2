package ru.mot.ic2exfidelity.friends;

import ic2.core.ContainerBase;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

/** Slotless global friend-settings menu. */
public final class LegacyFriendsMenu extends ContainerBase<SimpleContainer> {
    public LegacyFriendsMenu(int syncId, Inventory inventory) {
        super(LegacyFriendContent.FRIENDS_MENU.get(), syncId,
                inventory, new SimpleContainer(0));
    }

    @Override
    public boolean m_6875_(Player player) {
        return player.m_6084_();
    }
}
