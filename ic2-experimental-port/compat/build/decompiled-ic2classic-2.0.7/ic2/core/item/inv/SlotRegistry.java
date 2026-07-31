/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv;

import ic2.core.block.machines.containers.mv.ReactorPlannerContainer;
import ic2.core.block.machines.containers.nv.ArmorConfiguratorContainer;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class SlotRegistry {
    public static final SlotRegistry INSTANCE = new SlotRegistry();
    List<BiPredicate<AbstractContainerMenu, Slot>> invalidators = CollectionUtils.createList();

    private SlotRegistry() {
        this.registerInvalidator(new PortableInventoryInvalidator());
        this.registerInvalidator(new ContainerInvalidator(ArmorConfiguratorContainer.class));
        this.registerInvalidator(new ContainerInvalidator(ReactorPlannerContainer.class));
    }

    public void registerInvalidator(BiPredicate<AbstractContainerMenu, Slot> invalidator) {
        this.invalidators.add(invalidator);
    }

    public boolean isInvalidSlot(AbstractContainerMenu container, Slot slot) {
        int m = this.invalidators.size();
        for (int i = 0; i < m; ++i) {
            if (!this.invalidators.get(i).test(container, slot)) continue;
            return true;
        }
        return false;
    }

    static class PortableInventoryInvalidator
    implements BiPredicate<AbstractContainerMenu, Slot> {
        PortableInventoryInvalidator() {
        }

        @Override
        public boolean test(AbstractContainerMenu t, Slot u) {
            SlotBase slot;
            if (u instanceof SlotBase && (slot = (SlotBase)u).getInventory() instanceof IPortableInventory) {
                return true;
            }
            ItemStack stack = u.m_7993_();
            if (!(stack.m_41720_() instanceof IHasHeldGui)) {
                return false;
            }
            return StackUtil.getNbtData(stack).m_128441_("GUI_ID");
        }
    }

    public static class ContainerInvalidator
    implements BiPredicate<AbstractContainerMenu, Slot> {
        Class<? extends AbstractContainerMenu> clz;

        public ContainerInvalidator(Class<? extends AbstractContainerMenu> clz) {
            this.clz = clz;
        }

        @Override
        public boolean test(AbstractContainerMenu arg0, Slot arg1) {
            return this.clz.isInstance(arg0);
        }
    }
}

