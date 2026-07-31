/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.IItemHandlerModifiable
 *  net.minecraftforge.items.wrapper.EmptyHandler
 */
package ic2.core.inventory.base;

import ic2.core.inventory.container.ContainerHasGui;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.EmptyHandler;

public interface ICurioGetter {
    public IItemHandler getCurioHandler(Player var1);

    public IItemHandlerModifiable getCurioInv(Player var1, String var2);

    public ItemStack getFoamSupplier(Player var1, int var2);

    public ItemStack getJetpack(Player var1);

    public int chargeFromArmor(Player var1, ItemStack var2, int var3);

    public boolean addCurioSlots(ContainerHasGui<?> var1, Player var2, Vec2i var3, Vec2i var4, int var5, boolean var6);

    public static class DummyGetter
    implements ICurioGetter {
        @Override
        public IItemHandler getCurioHandler(Player player) {
            return EmptyHandler.INSTANCE;
        }

        @Override
        public IItemHandlerModifiable getCurioInv(Player player, String id) {
            return (IItemHandlerModifiable)EmptyHandler.INSTANCE;
        }

        @Override
        public ItemStack getFoamSupplier(Player player, int amount) {
            return ItemStack.f_41583_;
        }

        @Override
        public ItemStack getJetpack(Player player) {
            return ItemStack.f_41583_;
        }

        @Override
        public int chargeFromArmor(Player player, ItemStack stack, int needed) {
            return 0;
        }

        @Override
        public boolean addCurioSlots(ContainerHasGui<?> container, Player player, Vec2i pos, Vec2i buttonPos, int offset, boolean update) {
            return false;
        }
    }
}

