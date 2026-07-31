/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.core.Direction
 *  net.minecraft.world.Container
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
 *  net.minecraftforge.items.ItemStackHandler
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package ic2.forge;

import com.mojang.authlib.GameProfile;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.item.EnvItemHandler;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EnvItemHandlerForge
implements EnvItemHandler {
    EnvItemHandlerForge() {
    }

    @Nullable
    private static Container getStorage(BlockEntity blockEntity, Direction direction, GameProfile gameProfile, @Nullable Set<Container> set) {
        if (blockEntity instanceof IPersonalBlock && gameProfile != null) {
            Container container = ((IPersonalBlock)blockEntity).getPrivilegedInventory(gameProfile);
            if (set != null) {
                set.add(container);
            }
            return container;
        }
        if (blockEntity instanceof RandomizableContainerBlockEntity) {
            RandomizableContainerBlockEntity randomizableContainerBlockEntity = (RandomizableContainerBlockEntity)blockEntity;
            return randomizableContainerBlockEntity;
        }
        if (blockEntity instanceof TileEntityInventory) {
            TileEntityInventory tileEntityInventory = (TileEntityInventory)blockEntity;
            return tileEntityInventory;
        }
        return null;
    }

    @Nullable
    private static HandlerForge getStorage(BlockEntity blockEntity, Direction direction, @Nullable Set<Container> set) {
        BlockEntity blockEntity2 = blockEntity.m_58904_().m_7702_(blockEntity.m_58899_().m_121945_(direction));
        GameProfile gameProfile = blockEntity instanceof IPersonalBlock ? ((IPersonalBlock)blockEntity).getOwner() : null;
        return HandlerForge.ofNullable(EnvItemHandlerForge.getStorage(blockEntity2, direction.m_122424_(), gameProfile, set), direction);
    }

    private ItemStack extractItemFrom(HandlerForge handlerForge, int n, boolean bl) {
        for (int i = 0; i < handlerForge.getSlots(); ++i) {
            ItemStack itemStack = handlerForge.getStackInSlot(i);
            if (itemStack.m_41619_() || !handlerForge.canExtractItem(i, itemStack)) continue;
            return handlerForge.extractItem(i, n, bl);
        }
        return ItemStack.f_41583_;
    }

    private ItemStack extractItemFrom(HandlerForge handlerForge, int n, Predicate<ItemStack> predicate, boolean bl) {
        for (int i = 0; i < handlerForge.getSlots(); ++i) {
            ItemStack itemStack = handlerForge.getStackInSlot(i);
            if (itemStack.m_41619_() || !handlerForge.canExtractItem(i, itemStack) || !predicate.test(itemStack)) continue;
            return handlerForge.extractItem(i, n, bl);
        }
        return ItemStack.f_41583_;
    }

    private int insertItemTo(HandlerForge handlerForge, ItemStack itemStack, boolean bl) {
        for (int i = 0; i < handlerForge.getSlots(); ++i) {
            ItemStack itemStack2 = handlerForge.getStackInSlot(i);
            if (!handlerForge.canInsertItem(i, itemStack) || !itemStack2.m_41619_() && (!itemStack2.m_41726_(itemStack) || itemStack2.m_41613_() >= itemStack2.m_41741_())) continue;
            ItemStack itemStack3 = handlerForge.insertItem(i, itemStack, bl);
            return itemStack.m_41613_() - itemStack3.m_41613_();
        }
        return 0;
    }

    @Override
    public int fetch(BlockEntity blockEntity, ItemStack itemStack, boolean bl) {
        return 0;
    }

    @Override
    public int deposit(BlockEntity blockEntity, Direction direction, ItemStack itemStack, GameProfile gameProfile, boolean bl) {
        return this.deposit(HandlerForge.ofNullable(EnvItemHandlerForge.getStorage(blockEntity, direction, gameProfile, null), direction), itemStack, bl);
    }

    @Override
    public int deposit(EnvItemHandler.AdjacentInventory adjacentInventory, ItemStack itemStack, boolean bl) {
        return this.deposit((HandlerForge)adjacentInventory, itemStack, bl);
    }

    private int deposit(HandlerForge handlerForge, ItemStack itemStack, boolean bl) {
        if (handlerForge == null) {
            return 0;
        }
        System.out.println("stackName: " + itemStack.m_41786_());
        return this.insertItemTo(handlerForge, itemStack, bl);
    }

    @Override
    public int distribute(BlockEntity blockEntity, ItemStack itemStack, boolean bl) {
        return 0;
    }

    @Override
    @Nullable
    public EnvItemHandler.AdjacentInventory getAdjacentInventory(BlockEntity blockEntity, Direction direction) {
        return EnvItemHandlerForge.getStorage(blockEntity, direction, null);
    }

    @Override
    public List<? extends EnvItemHandler.AdjacentInventory> getAdjacentInventories(BlockEntity blockEntity) {
        ArrayList<HandlerForge> arrayList = new ArrayList<HandlerForge>();
        HashSet<Container> hashSet = new HashSet<Container>();
        for (Direction direction : Util.ALL_DIRS) {
            HandlerForge handlerForge2 = EnvItemHandlerForge.getStorage(blockEntity, direction, hashSet);
            if (handlerForge2 == null) continue;
            arrayList.add(handlerForge2);
        }
        arrayList.sort(Comparator.comparing(handlerForge -> hashSet.contains(handlerForge.getDelegate())).thenComparingInt(handlerForge -> {
            int n = 0;
            return -(n += handlerForge.getSlots());
        }));
        return arrayList;
    }

    @Override
    public EnvItemHandler.AdjacentInventory wrapInventory(BlockEntity blockEntity, Direction direction) {
        return new HandlerForge((Container)blockEntity, direction);
    }

    @Override
    public int transfer(EnvItemHandler.AdjacentInventory adjacentInventory, EnvItemHandler.AdjacentInventory adjacentInventory2, int n) {
        HandlerForge handlerForge = (HandlerForge)adjacentInventory;
        HandlerForge handlerForge2 = (HandlerForge)adjacentInventory2;
        ItemStack itemStack = this.extractItemFrom(handlerForge, n, true);
        if (itemStack.m_41613_() > 0 && this.insertItemTo(handlerForge2, itemStack, true) > 0) {
            this.extractItemFrom(handlerForge, n, false);
            this.insertItemTo(handlerForge2, itemStack, false);
            return itemStack.m_41613_();
        }
        return 0;
    }

    @Override
    public int transfer(EnvItemHandler.AdjacentInventory adjacentInventory, EnvItemHandler.AdjacentInventory adjacentInventory2, int n, Predicate<ItemStack> predicate) {
        HandlerForge handlerForge = (HandlerForge)adjacentInventory;
        HandlerForge handlerForge2 = (HandlerForge)adjacentInventory2;
        ItemStack itemStack = this.extractItemFrom(handlerForge, n, predicate, true);
        if (itemStack.m_41613_() > 0 && this.insertItemTo(handlerForge2, itemStack, true) > 0) {
            this.extractItemFrom(handlerForge, n, predicate, false);
            this.insertItemTo(handlerForge2, itemStack, false);
            return itemStack.m_41613_();
        }
        return 0;
    }

    private static class HandlerForge
    extends ItemStackHandler
    implements EnvItemHandler.AdjacentInventory {
        final Direction side;
        Container inventory;

        private HandlerForge(Container container, Direction direction) {
            super(container.m_6643_());
            this.side = direction;
            this.inventory = container;
            for (int i = 0; i < container.m_6643_(); ++i) {
                this.stacks.set(i, (Object)container.m_8020_(i));
            }
        }

        public void setStackInSlot(int n, @NotNull ItemStack itemStack) {
            super.setStackInSlot(n, itemStack);
            this.inventory.m_6836_(n, itemStack);
        }

        public int size() {
            return this.inventory.m_6643_();
        }

        @NotNull
        public ItemStack insertItem(int n, @NotNull ItemStack itemStack, boolean bl) {
            ItemStack itemStack2 = super.insertItem(n, itemStack, bl);
            if (!bl) {
                for (int i = 0; i < this.inventory.m_6643_(); ++i) {
                    this.inventory.m_6836_(i, this.getStackInSlot(i));
                }
            }
            return itemStack2;
        }

        @NotNull
        public ItemStack extractItem(int n, int n2, boolean bl) {
            ItemStack itemStack = super.extractItem(n, n2, bl);
            if (!bl) {
                for (int i = 0; i < this.inventory.m_6643_(); ++i) {
                    this.inventory.m_6836_(i, this.getStackInSlot(i));
                }
            }
            return itemStack;
        }

        public boolean canInsertItem(int n, @NotNull ItemStack itemStack) {
            WorldlyContainer worldlyContainer;
            Container container = this.inventory;
            return !(container instanceof WorldlyContainer) || (worldlyContainer = (WorldlyContainer)container).m_7155_(n, itemStack, this.getSide());
        }

        public boolean canExtractItem(int n, @NotNull ItemStack itemStack) {
            WorldlyContainer worldlyContainer;
            Container container = this.inventory;
            return !(container instanceof WorldlyContainer) || (worldlyContainer = (WorldlyContainer)container).m_7157_(n, itemStack, this.getSide());
        }

        @Override
        public Direction getSide() {
            return this.side;
        }

        private Container getDelegate() {
            return this.inventory;
        }

        static HandlerForge ofNullable(Container container, Direction direction) {
            return container == null ? null : new HandlerForge(container, direction);
        }
    }
}

