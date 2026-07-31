/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.network;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public interface IPlayerPacket {
    default public <T extends AbstractContainerMenu> T getContainer(Player player, Class<T> clz) {
        return (T)(clz.isInstance(player.f_36096_) ? player.f_36096_ : null);
    }

    default public <T extends AbstractContainerMenu> void getContainer(Player player, Class<T> clz, Consumer<T> listener) {
        if (clz.isInstance(player.f_36096_)) {
            listener.accept(player.f_36096_);
        }
    }

    default public <T extends AbstractContainerMenu> Optional<T> getOptionalContainer(Player player, Class<T> clz) {
        return clz.isInstance(player.f_36096_) ? Optional.of(player.f_36096_) : Optional.empty();
    }

    default public ItemStack findPlayerStack(Player player, ItemStack send) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!ItemStack.m_41728_((ItemStack)send, (ItemStack)player.m_6844_(slot))) continue;
            return player.m_6844_(slot);
        }
        return ItemStack.f_41583_;
    }
}

