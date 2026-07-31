/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.gui.components.simple;

import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.slot.IFluidSlot;
import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.client.custom.FilterComponentPacket;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;

public interface IFilterTarget {
    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();

    public boolean isValid(ItemStack var1);

    public void accept(ItemStack var1);

    public boolean allowEmptyStack();

    public boolean isFluidSelection();

    public static IFilterTarget ghost(IGhostSlot slot) {
        return new GhostTarget(slot);
    }

    public static IFilterTarget simple(int x, int y, int width, int height, IFilter filter, Consumer<ItemStack> acceptor) {
        return new SimpleTarget(x, y, width, height, filter, acceptor);
    }

    public static IFilterTarget simpleNonEmpty(int x, int y, int width, int height, IFilter filter, Consumer<ItemStack> acceptor) {
        return new SimpleTarget(x, y, width, height, filter, acceptor).disableEmpty();
    }

    public static class GhostTarget
    implements IFilterTarget {
        IGhostSlot slot;

        public GhostTarget(IGhostSlot slot) {
            this.slot = slot;
        }

        @Override
        public int getX() {
            return this.slot.getXPos() - 2;
        }

        @Override
        public int getY() {
            return this.slot.getYPos() - 2;
        }

        @Override
        public int getWidth() {
            return 20;
        }

        @Override
        public int getHeight() {
            return 20;
        }

        @Override
        public boolean isFluidSelection() {
            return this.slot instanceof IFluidSlot;
        }

        @Override
        public boolean allowEmptyStack() {
            return this.slot.getType() == IGhostSlot.GhostType.FILTER;
        }

        @Override
        public boolean isValid(ItemStack stack) {
            return this.slot.isStackValid(stack);
        }

        @Override
        public void accept(ItemStack stack) {
            PacketManager.INSTANCE.sendToServer(new FilterComponentPacket(stack, this.slot.getSlotID()));
        }
    }

    public static class SimpleTarget
    implements IFilterTarget {
        int x;
        int y;
        int width;
        int height;
        IFilter filter;
        Consumer<ItemStack> acceptor;
        boolean allowEmpty = true;
        boolean fluid = false;

        public SimpleTarget(int x, int y, int width, int height, IFilter filter, Consumer<ItemStack> acceptor) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.filter = filter;
            this.acceptor = acceptor;
        }

        public IFilterTarget disableEmpty() {
            this.allowEmpty = false;
            return this;
        }

        public IFilterTarget setFluidSlot() {
            this.fluid = true;
            return this;
        }

        @Override
        public int getX() {
            return this.x;
        }

        @Override
        public int getY() {
            return this.y;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public boolean isFluidSelection() {
            return this.fluid;
        }

        @Override
        public boolean isValid(ItemStack stack) {
            return this.filter.matches(stack);
        }

        @Override
        public void accept(ItemStack stack) {
            this.acceptor.accept(stack);
        }

        @Override
        public boolean allowEmptyStack() {
            return this.allowEmpty;
        }
    }
}

