/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  com.mojang.authlib.GameProfile
 *  gnu.trove.TIntCollection
 *  gnu.trove.iterator.TIntIterator
 *  gnu.trove.list.array.TIntArrayList
 *  gnu.trove.procedure.TIntProcedure
 *  gnu.trove.set.TIntSet
 *  gnu.trove.set.hash.TIntHashSet
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockChest$Type
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.ISidedInventory
 *  net.minecraft.inventory.InventoryLargeChest
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemBlockSpecial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.ILockableContainer
 *  net.minecraft.world.World
 *  net.minecraftforge.items.CapabilityItemHandler
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mojang.authlib.GameProfile;
import gnu.trove.TIntCollection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Player;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.util.LogCategory;
import ic2.core.util.Tuple;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public final class StackUtil {
    public static final Predicate<ItemStack> anyStack = Predicates.alwaysTrue();
    static final Set<String> ignoredNbtKeys = new HashSet<String>(Arrays.asList("damage", "charge", "energy", "advDmg"));
    public static final ItemStack emptyStack = ItemStack.field_190927_a;
    private static final int[] emptySlotArray = new int[0];

    public static boolean isInventoryTile(TileEntity te, EnumFacing side) {
        return te instanceof IInventory || te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
    }

    public static IInventory findDoubleChest(TileEntityChest chest) {
        World world = chest.func_145831_w();
        BlockPos pos = chest.func_174877_v();
        if (world == null || pos == null || !world.func_175667_e(pos)) {
            return null;
        }
        BlockChest.Type type = chest.func_145980_j();
        for (EnumFacing facing : EnumFacing.field_176754_o) {
            TileEntityChest right;
            TileEntityChest left;
            TileEntity te = world.func_175625_s(pos.func_177972_a(facing));
            if (!(te instanceof TileEntityChest) || ((TileEntityChest)te).func_145980_j() != type) continue;
            if (facing == EnumFacing.WEST || facing == EnumFacing.NORTH) {
                left = (TileEntityChest)te;
                right = chest;
            } else {
                left = chest;
                right = (TileEntityChest)te;
            }
            return new InventoryLargeChest("container.chestDouble", (ILockableContainer)left, (ILockableContainer)right);
        }
        return chest;
    }

    public static AdjacentInv getAdjacentInventory(TileEntity source, EnumFacing dir) {
        GameProfile srcOwner;
        TileEntity target = source.func_145831_w().func_175625_s(source.func_174877_v().func_177972_a(dir));
        if (!StackUtil.isInventoryTile(target, dir)) {
            return null;
        }
        if (target instanceof IPersonalBlock && source instanceof IPersonalBlock && (srcOwner = ((IPersonalBlock)source).getOwner()) != null) {
            return new PersonalAdjacentInv(target, dir, srcOwner);
        }
        if (target instanceof TileEntityChest && StackUtil.findDoubleChest((TileEntityChest)target) == null) {
            return null;
        }
        return new AdjacentInv(target, dir);
    }

    public static List<AdjacentInv> getAdjacentInventories(TileEntity source) {
        ArrayList<AdjacentInv> inventories = new ArrayList<AdjacentInv>();
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            AdjacentInv inventory = StackUtil.getAdjacentInventory(source, dir);
            if (inventory == null) continue;
            inventories.add(inventory);
        }
        Collections.sort(inventories, new Comparator<AdjacentInv>(){

            @Override
            public int compare(AdjacentInv a, AdjacentInv b) {
                if (a.te instanceof IPersonalBlock || !(b.te instanceof IPersonalBlock)) {
                    return -1;
                }
                if (b.te instanceof IPersonalBlock || !(a.te instanceof IPersonalBlock)) {
                    return 1;
                }
                return StackUtil.getInventorySize(b.te, b.dir.func_176734_d(), b.getAccessor()) - StackUtil.getInventorySize(a.te, a.dir.func_176734_d(), a.getAccessor());
            }
        });
        return inventories;
    }

    public static GameProfile getOwner(TileEntity te) {
        if (te instanceof IPersonalBlock) {
            return ((IPersonalBlock)te).getOwner();
        }
        return null;
    }

    public static int getInventorySize(TileEntity te, EnumFacing side, GameProfile accessor) {
        if (te instanceof IInventory) {
            IInventory inv = StackUtil.getInventory(te, accessor);
            return inv == null ? 0 : inv.func_70302_i_();
        }
        if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (handler == null) {
                return 0;
            }
            return handler.getSlots();
        }
        return 0;
    }

    private static IInventory getInventory(TileEntity te, GameProfile accessor) {
        if (te instanceof TileEntityChest) {
            return StackUtil.findDoubleChest((TileEntityChest)te);
        }
        if (te instanceof IPersonalBlock) {
            return ((IPersonalBlock)te).getPrivilegedInventory(accessor);
        }
        if (te instanceof IInventory) {
            return (IInventory)te;
        }
        return null;
    }

    public static int distribute(TileEntity source, ItemStack stack, boolean simulate) {
        AdjacentInv inventory;
        int amount;
        ItemStack remaining = StackUtil.copy(stack);
        Iterator<AdjacentInv> iterator = StackUtil.getAdjacentInventories(source).iterator();
        while (iterator.hasNext() && !StackUtil.isEmpty(remaining = StackUtil.decSize(remaining, amount = StackUtil.putInInventory(source, inventory = iterator.next(), remaining, simulate)))) {
        }
        return StackUtil.getSize(stack) - StackUtil.getSize(remaining);
    }

    public static int fetch(TileEntity source, ItemStack stack, boolean simulate) {
        AdjacentInv inventory;
        ItemStack transferred;
        ItemStack remaining = StackUtil.copy(stack);
        Iterator<AdjacentInv> iterator = StackUtil.getAdjacentInventories(source).iterator();
        while (iterator.hasNext() && (StackUtil.isEmpty(transferred = StackUtil.getFromInventory(source, inventory = iterator.next(), remaining, true, simulate)) || !StackUtil.isEmpty(remaining = StackUtil.decSize(remaining, StackUtil.getSize(transferred))))) {
        }
        return StackUtil.getSize(stack) - StackUtil.getSize(remaining);
    }

    public static int transfer(TileEntity src, TileEntity dst, EnumFacing dir, int amount) {
        return StackUtil.transfer(src, dst, dir, amount, (Predicate<ItemStack>)Predicates.alwaysTrue(), true);
    }

    public static int transfer(TileEntity src, TileEntity dst, EnumFacing dir, int amount, Predicate<ItemStack> checker) {
        return StackUtil.transfer(src, dst, dir, amount, checker, checker == null || Predicates.alwaysTrue().equals(checker));
    }

    private static int transfer(TileEntity src, TileEntity dst, EnumFacing dir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
        if (amount <= 0) {
            return 0;
        }
        GameProfile srcOwner = StackUtil.getOwner(src);
        GameProfile dstOwner = StackUtil.getOwner(dst);
        EnumFacing reverseDir = dir.func_176734_d();
        int[] srcSlots = StackUtil.getInventorySlots(src, dir, false, true, dstOwner);
        if (srcSlots.length == 0) {
            return 0;
        }
        int[] dstSlots = StackUtil.getInventorySlots(dst, reverseDir, true, false, srcOwner);
        if (dstSlots.length == 0) {
            return 0;
        }
        if (src instanceof IInventory) {
            IInventory srcInv = StackUtil.getInventory(src, dstOwner);
            if (srcInv == null) {
                return 0;
            }
            if (dst instanceof IInventory) {
                IInventory dstInv = StackUtil.getInventory(dst, srcOwner);
                if (dstInv == null) {
                    return 0;
                }
                return StackUtil.transfer(srcInv, srcSlots, dstInv, dstSlots, dir, reverseDir, amount, checker, skipChecker);
            }
            if (dst.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d())) {
                IItemHandler dstHandler = (IItemHandler)dst.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d());
                if (dstHandler == null) {
                    return 0;
                }
                return StackUtil.transfer(srcInv, srcSlots, dstHandler, dstSlots, reverseDir, amount, checker, skipChecker);
            }
            return 0;
        }
        if (src.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir)) {
            IItemHandler srcHandler = (IItemHandler)src.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir);
            if (srcHandler == null) {
                return 0;
            }
            if (dst instanceof IInventory) {
                IInventory dstInv = StackUtil.getInventory(dst, srcOwner);
                if (dstInv == null) {
                    return 0;
                }
                return StackUtil.transfer(srcHandler, srcSlots, dstInv, dstSlots, reverseDir, amount, checker, skipChecker);
            }
            if (dst.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d())) {
                IItemHandler dstHandler = (IItemHandler)dst.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d());
                if (dstHandler == null) {
                    return 0;
                }
                return StackUtil.transfer(srcHandler, srcSlots, dstHandler, dstSlots, amount, checker, skipChecker);
            }
            return 0;
        }
        return 0;
    }

    private static int transfer(IInventory src, int[] srcSlots, IInventory dst, int[] dstSlots, EnumFacing dir, EnumFacing reverseDir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
        ISidedInventory dstSided = dst instanceof ISidedInventory ? (ISidedInventory)dst : null;
        int total = amount;
        for (int srcSlot : srcSlots) {
            int transferred;
            ItemStack srcStack = src.func_70301_a(srcSlot);
            if (StackUtil.isEmpty(srcStack) || !skipChecker && !checker.apply((Object)srcStack) || (transferred = StackUtil.insert(srcStack, amount, dst, dstSided, reverseDir, dstSlots)) <= 0) continue;
            src.func_70299_a(srcSlot, StackUtil.decSize(srcStack, transferred));
            if ((amount -= transferred) <= 0) break;
        }
        amount = total - amount;
        assert (amount >= 0);
        if (amount > 0) {
            src.func_70296_d();
            dst.func_70296_d();
        }
        return amount;
    }

    private static int transfer(IItemHandler src, int[] srcSlots, IInventory dst, int[] dstSlots, EnumFacing reverseDir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
        ISidedInventory dstSided = dst instanceof ISidedInventory ? (ISidedInventory)dst : null;
        int total = amount;
        for (int srcSlot : srcSlots) {
            int transferred;
            ItemStack srcStack = src.extractItem(srcSlot, amount, true);
            if (StackUtil.isEmpty(srcStack) || !skipChecker && !checker.apply((Object)srcStack) || (transferred = StackUtil.insert(srcStack, amount, dst, dstSided, reverseDir, dstSlots)) <= 0) continue;
            src.extractItem(srcSlot, transferred, false);
            if ((amount -= transferred) <= 0) break;
        }
        amount = total - amount;
        assert (amount >= 0);
        if (amount > 0) {
            dst.func_70296_d();
        }
        return amount;
    }

    private static int insert(ItemStack stack, int maxAmount, IInventory dst, ISidedInventory dstSided, EnumFacing side, int[] dstSlots) {
        int total;
        int sizeLimit = Math.min(stack.func_77976_d(), dst.func_70297_j_());
        int remaining = total = Math.min(maxAmount, StackUtil.getSize(stack));
        for (int pass = 0; pass < 2; ++pass) {
            for (int i = 0; i < dstSlots.length; ++i) {
                int dstSlot = dstSlots[i];
                if (dstSlot < 0) continue;
                ItemStack dstStack = dst.func_70301_a(dstSlot);
                if (pass == 0 && (StackUtil.isEmpty(dstStack) || !StackUtil.checkItemEqualityStrict(stack, dstStack)) || pass == 1 && !StackUtil.isEmpty(dstStack) || !dst.func_94041_b(dstSlot, stack) || dstSided != null && !dstSided.func_180462_a(dstSlot, stack, side)) continue;
                int amount = Math.min(remaining, sizeLimit - StackUtil.getSize(dstStack));
                if (StackUtil.isEmpty(dstStack)) {
                    dst.func_70299_a(dstSlot, StackUtil.copyWithSize(stack, amount));
                } else {
                    if (amount <= 0) {
                        dstSlots[i] = -1;
                        continue;
                    }
                    dst.func_70299_a(dstSlot, StackUtil.incSize(dstStack, amount));
                }
                assert (amount > 0);
                if ((remaining -= amount) > 0) continue;
                return total;
            }
        }
        return total - remaining;
    }

    private static int transfer(IItemHandler src, int[] srcSlots, IItemHandler dst, int[] dstSlots, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
        int total = amount;
        for (int srcSlot : srcSlots) {
            int transferred;
            ItemStack srcStack = src.extractItem(srcSlot, amount, true);
            if (StackUtil.isEmpty(srcStack) || !skipChecker && !checker.apply((Object)srcStack) || (transferred = StackUtil.insert(srcStack, Integer.MAX_VALUE, dst, dstSlots)) <= 0) continue;
            src.extractItem(srcSlot, transferred, false);
            if ((amount -= transferred) <= 0) break;
        }
        amount = total - amount;
        assert (amount >= 0);
        return amount;
    }

    private static int transfer(IInventory src, int[] srcSlots, IItemHandler dst, int[] dstSlots, EnumFacing dir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
        int total = amount;
        for (int srcSlot : srcSlots) {
            int transferred;
            ItemStack srcStack = src.func_70301_a(srcSlot);
            if (StackUtil.isEmpty(srcStack) || !skipChecker && !checker.apply((Object)srcStack) || (transferred = StackUtil.insert(srcStack, amount, dst, dstSlots)) <= 0) continue;
            src.func_70299_a(srcSlot, StackUtil.decSize(srcStack, transferred));
            if ((amount -= transferred) <= 0) break;
        }
        amount = total - amount;
        assert (amount >= 0);
        if (amount > 0) {
            src.func_70296_d();
        }
        return amount;
    }

    private static int insert(ItemStack stack, int maxAmount, IItemHandler dst, int[] dstSlots) {
        int total;
        int remaining = total = Math.min(maxAmount, StackUtil.getSize(stack));
        assert (!StackUtil.isEmpty(stack));
        for (int pass = 0; pass < 2; ++pass) {
            for (int dstSlot : dstSlots) {
                ItemStack leftOver;
                int transferred;
                if (dstSlot < 0) continue;
                ItemStack dstStack = dst.getStackInSlot(dstSlot);
                if (pass == 0 && (StackUtil.isEmpty(dstStack) || !StackUtil.checkItemEqualityStrict(stack, dstStack)) || pass == 1 && !StackUtil.isEmpty(dstStack) || (remaining -= (transferred = remaining - StackUtil.getSize(leftOver = dst.insertItem(dstSlot, StackUtil.copyWithSize(stack, remaining), false)))) > 0) continue;
                return total;
            }
        }
        return total - remaining;
    }

    public static void distributeDrops(TileEntity source, List<ItemStack> stacks) {
        ListIterator<ItemStack> it = stacks.listIterator();
        while (it.hasNext()) {
            ItemStack stack = it.next();
            int amount = StackUtil.distribute(source, stack, false);
            if (amount == StackUtil.getSize(stack)) {
                it.remove();
                continue;
            }
            it.set(StackUtil.decSize(stack, amount));
        }
        for (ItemStack stack : stacks) {
            StackUtil.dropAsEntity(source.func_145831_w(), source.func_174877_v(), stack);
        }
        stacks.clear();
    }

    private static ItemStack getFromInventory(TileEntity source, AdjacentInv inventory, ItemStack stack, boolean ignoreMaxStackSize, boolean simulate) {
        return StackUtil.getFromInventory(inventory.te, inventory.dir.func_176734_d(), stack, StackUtil.getSize(stack), ignoreMaxStackSize, inventory.getAccessor(), simulate);
    }

    public static ItemStack getFromInventory(TileEntity te, EnumFacing side, ItemStack stackDestination, int max, boolean ignoreMaxStackSize, boolean simulate) {
        return StackUtil.getFromInventory(te, side, stackDestination, max, ignoreMaxStackSize, null, simulate);
    }

    public static ItemStack getFromInventory(TileEntity te, EnumFacing side, ItemStack stackDestination, int max, boolean ignoreMaxStackSize, GameProfile accessor, boolean simulate) {
        int[] slots;
        if (!StackUtil.isEmpty(stackDestination) && !ignoreMaxStackSize) {
            max = Math.min(max, stackDestination.func_77976_d() - StackUtil.getSize(stackDestination));
        }
        if ((slots = StackUtil.getInventorySlots(te, side, false, true, accessor)).length == 0) {
            return emptyStack;
        }
        ItemStack ret = emptyStack;
        if (te instanceof IInventory) {
            IInventory inv = StackUtil.getInventory(te, accessor);
            if (inv == null) {
                return emptyStack;
            }
            for (int slot : slots) {
                if (max <= 0) break;
                ItemStack stack = inv.func_70301_a(slot);
                if (StackUtil.isEmpty(stack) || !StackUtil.isEmpty(stackDestination) && !StackUtil.checkItemEqualityStrict(stack, stackDestination)) continue;
                boolean extra = StackUtil.isEmpty(ret);
                if (extra) {
                    ret = StackUtil.copyWithSize(stack, 1);
                    if (StackUtil.isEmpty(stackDestination)) {
                        if (!ignoreMaxStackSize) {
                            max = Math.min(max, ret.func_77976_d());
                        }
                        stackDestination = ret;
                    }
                }
                int transfer = Math.min(max, StackUtil.getSize(stack));
                if (!simulate) {
                    stack = StackUtil.decSize(stack, transfer);
                    inv.func_70299_a(slot, stack);
                }
                max -= transfer;
                ret = StackUtil.incSize(ret, extra ? transfer - 1 : transfer);
            }
            if (!simulate && !StackUtil.isEmpty(ret)) {
                inv.func_70296_d();
            }
        } else if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (handler == null) {
                return emptyStack;
            }
            for (int slot : slots) {
                ItemStack stack;
                if (max <= 0) break;
                if (!StackUtil.isEmpty(stackDestination) && (StackUtil.isEmpty(stack = handler.getStackInSlot(slot)) || !StackUtil.checkItemEqualityStrict(stack, stackDestination)) || StackUtil.isEmpty(stack = handler.extractItem(slot, max, simulate))) continue;
                boolean extra = StackUtil.isEmpty(ret);
                if (extra) {
                    ret = StackUtil.copyWithSize(stack, 1);
                    if (StackUtil.isEmpty(stackDestination)) {
                        if (!ignoreMaxStackSize) {
                            max = Math.min(max, ret.func_77976_d());
                        }
                        stackDestination = ret;
                    }
                } else assert (StackUtil.checkItemEqualityStrict(stack, ret));
                int transfer = StackUtil.getSize(stack);
                max -= transfer;
                ret = StackUtil.incSize(ret, extra ? transfer - 1 : transfer);
            }
        }
        return ret;
    }

    private static int putInInventory(TileEntity source, AdjacentInv inventory, ItemStack stackSource, boolean simulate) {
        return StackUtil.putInInventory(inventory.te, inventory.dir.func_176734_d(), stackSource, inventory.getAccessor(), simulate);
    }

    public static int putInInventory(TileEntity te, EnumFacing side, ItemStack stackSource, boolean simulate) {
        return StackUtil.putInInventory(te, side, stackSource, null, simulate);
    }

    public static int putInInventory(TileEntity te, EnumFacing side, ItemStack stackSource, GameProfile accessor, boolean simulate) {
        if (StackUtil.isEmpty(stackSource)) {
            return 0;
        }
        int[] slots = StackUtil.getInventorySlots(te, side, true, false, accessor);
        if (slots.length == 0) {
            return 0;
        }
        if (te instanceof IInventory) {
            int transfer;
            ItemStack stack;
            IInventory inv = StackUtil.getInventory(te, accessor);
            if (inv == null) {
                return 0;
            }
            int toTransfer = StackUtil.getSize(stackSource);
            for (int slot : slots) {
                if (toTransfer <= 0) break;
                if (!inv.func_94041_b(slot, stackSource) || inv instanceof ISidedInventory && !((ISidedInventory)inv).func_180462_a(slot, stackSource, side) || StackUtil.isEmpty(stack = inv.func_70301_a(slot)) || !StackUtil.checkItemEqualityStrict(stack, stackSource)) continue;
                transfer = Math.min(toTransfer, Math.min(inv.func_70297_j_(), stack.func_77976_d()) - StackUtil.getSize(stack));
                if (!simulate) {
                    inv.func_70299_a(slot, StackUtil.incSize(stack, transfer));
                }
                toTransfer -= transfer;
            }
            for (int slot : slots) {
                if (toTransfer <= 0) break;
                if (!inv.func_94041_b(slot, stackSource) || inv instanceof ISidedInventory && !((ISidedInventory)inv).func_180462_a(slot, stackSource, side) || !StackUtil.isEmpty(stack = inv.func_70301_a(slot))) continue;
                transfer = Math.min(toTransfer, Math.min(inv.func_70297_j_(), stackSource.func_77976_d()));
                if (!simulate) {
                    ItemStack dest = StackUtil.copyWithSize(stackSource, transfer);
                    inv.func_70299_a(slot, dest);
                }
                toTransfer -= transfer;
            }
            if (!simulate && toTransfer != StackUtil.getSize(stackSource)) {
                inv.func_70296_d();
            }
            return StackUtil.getSize(stackSource) - toTransfer;
        }
        if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            ItemStack remaining;
            ItemStack stack;
            IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (handler == null) {
                return 0;
            }
            ItemStack src = stackSource.func_77946_l();
            for (int slot : slots) {
                if (StackUtil.isEmpty(src)) break;
                stack = handler.getStackInSlot(slot);
                if (StackUtil.isEmpty(stack)) continue;
                remaining = handler.insertItem(slot, src.func_77946_l(), simulate);
                if (StackUtil.isEmpty(remaining)) {
                    src = emptyStack;
                    continue;
                }
                if (StackUtil.getSize(remaining) >= StackUtil.getSize(src)) continue;
                src = StackUtil.setSize(src, StackUtil.getSize(remaining));
            }
            for (int slot : slots) {
                if (StackUtil.isEmpty(src)) break;
                stack = handler.getStackInSlot(slot);
                if (!StackUtil.isEmpty(stack)) continue;
                remaining = handler.insertItem(slot, src.func_77946_l(), simulate);
                if (StackUtil.isEmpty(remaining)) {
                    src = emptyStack;
                    continue;
                }
                if (StackUtil.getSize(remaining) >= StackUtil.getSize(src)) continue;
                src = StackUtil.setSize(src, StackUtil.getSize(remaining));
            }
            return StackUtil.getSize(stackSource) - StackUtil.getSize(src);
        }
        return 0;
    }

    private static int[] getInventorySlots(TileEntity te, EnumFacing side, boolean checkInsert, boolean checkExtract, GameProfile accessor) {
        if (te instanceof IInventory) {
            int[] ret;
            ISidedInventory sidedInv;
            IInventory inv = StackUtil.getInventory(te, accessor);
            if (inv == null || inv.func_70297_j_() <= 0) {
                return emptySlotArray;
            }
            if (inv instanceof ISidedInventory) {
                sidedInv = (ISidedInventory)inv;
                ret = sidedInv.func_180463_a(side);
                if (ret.length == 0) {
                    return emptySlotArray;
                }
                ret = Arrays.copyOf(ret, ret.length);
            } else {
                int size = inv.func_70302_i_();
                if (size <= 0) {
                    return emptySlotArray;
                }
                sidedInv = null;
                ret = new int[size];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = i;
                }
            }
            if (checkInsert || checkExtract) {
                int writeIdx = 0;
                for (int readIdx = 0; readIdx < ret.length; ++readIdx) {
                    int slot = ret[readIdx];
                    ItemStack stack = inv.func_70301_a(slot);
                    if (checkExtract && (StackUtil.isEmpty(stack) || sidedInv != null && !sidedInv.func_180461_b(slot, stack, side)) || checkInsert && !StackUtil.isEmpty(stack) && (StackUtil.getSize(stack) >= stack.func_77976_d() || StackUtil.getSize(stack) >= inv.func_70297_j_() || sidedInv != null && !sidedInv.func_180462_a(slot, stack, side))) continue;
                    ret[writeIdx] = slot;
                    ++writeIdx;
                }
                if (writeIdx != ret.length) {
                    ret = Arrays.copyOf(ret, writeIdx);
                }
            }
            return ret;
        }
        if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (handler == null) {
                return emptySlotArray;
            }
            int size = handler.getSlots();
            if (size <= 0) {
                return emptySlotArray;
            }
            int[] ret = new int[size];
            for (int i = 0; i < ret.length; ++i) {
                ret[i] = i;
            }
            if (checkInsert || checkExtract) {
                int writeIdx = 0;
                for (int readIdx = 0; readIdx < ret.length; ++readIdx) {
                    int slot = ret[readIdx];
                    ItemStack stack = handler.getStackInSlot(slot);
                    if (checkExtract && (StackUtil.isEmpty(stack) || StackUtil.isEmpty(handler.extractItem(slot, Integer.MAX_VALUE, true))) || checkInsert && !StackUtil.checkInsert(handler, slot, stack)) continue;
                    ret[writeIdx] = slot;
                    ++writeIdx;
                }
                if (writeIdx != ret.length) {
                    ret = Arrays.copyOf(ret, writeIdx);
                }
            }
            return ret;
        }
        return emptySlotArray;
    }

    private static boolean checkInsert(IItemHandler handler, int slot, ItemStack stack) {
        if (StackUtil.isEmpty(stack) || StackUtil.getSize(stack) >= stack.func_77976_d()) {
            return true;
        }
        int testSize = Integer.MAX_VALUE;
        ItemStack result = handler.insertItem(slot, StackUtil.copyWithSize(stack, Integer.MAX_VALUE), true);
        return StackUtil.isEmpty(result) || StackUtil.getSize(result) < Integer.MAX_VALUE;
    }

    public static boolean consumeFromPlayerInventory(EntityPlayer player, Predicate<ItemStack> request, int amount, boolean simulate) {
        NonNullList contents = player.field_71071_by.field_70462_a;
        for (int pass = 0; pass < 2; ++pass) {
            int amountNeeded = amount;
            for (int i = 0; i < contents.size(); ++i) {
                ItemStack stack = (ItemStack)contents.get(i);
                if (!request.apply((Object)stack)) continue;
                if (player.field_71075_bZ.field_75098_d) {
                    return true;
                }
                int cAmount = Math.min(StackUtil.getSize(stack), amountNeeded);
                amountNeeded -= cAmount;
                if (pass == 1) {
                    contents.set(i, (Object)StackUtil.decSize(stack, cAmount));
                }
                if (amountNeeded <= 0) break;
            }
            if (amountNeeded > 0) {
                if (pass == 1) {
                    IC2.log.warn(LogCategory.General, "Inconsistent inventory transaction for player %s, request %s: %d missing", player, request, amountNeeded);
                }
                return false;
            }
            if (!simulate) continue;
            return true;
        }
        return true;
    }

    public static Predicate<ItemStack> sameStack(final ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            throw new IllegalArgumentException("empty stack");
        }
        return new Predicate<ItemStack>(){

            public boolean apply(ItemStack input) {
                return StackUtil.checkItemEquality(input, stack);
            }

            public String toString() {
                return "stack==" + stack;
            }
        };
    }

    public static Predicate<ItemStack> sameItem(final Item item) {
        if (item == null) {
            throw new NullPointerException("null item");
        }
        return new Predicate<ItemStack>(){

            public boolean apply(ItemStack input) {
                return input.func_77973_b() == item;
            }

            public String toString() {
                return "item==" + item;
            }
        };
    }

    public static Predicate<ItemStack> sameItem(Block block) {
        if (block == null) {
            throw new NullPointerException("null block");
        }
        Item item = Item.func_150898_a((Block)block);
        if (item == null || item == Items.field_190931_a && block != Blocks.field_150350_a) {
            throw new IllegalArgumentException("block " + block + " doesn't have an associated item");
        }
        return StackUtil.sameItem(item);
    }

    public static Predicate<ItemStack> oreDict(String name) {
        return StackUtil.recipeInput(Recipes.inputFactory.forOreDict(name));
    }

    public static Predicate<ItemStack> recipeInput(final IRecipeInput item) {
        return new Predicate<ItemStack>(){

            public boolean apply(ItemStack input) {
                return item.matches(input);
            }

            public String toString() {
                return item.toString();
            }
        };
    }

    public static boolean consume(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
        return StackUtil.consume0(player, hand, request, amount, false) != emptyStack;
    }

    public static ItemStack consumeAndGet(EntityPlayer player, Predicate<ItemStack> request, int amount) {
        return StackUtil.consumeAndGet(player, EnumHand.MAIN_HAND, request, amount);
    }

    public static ItemStack consumeAndGet(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
        return StackUtil.consume0(player, hand, request, amount, true);
    }

    public static void consumeOrError(EntityPlayer player, EnumHand hand, int amount) {
        StackUtil.consumeOrError(player, hand, anyStack, amount);
    }

    public static void consumeOrError(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
        if (!StackUtil.consume(player, hand, request, amount)) {
            throw new IllegalStateException("consume failed");
        }
    }

    private static ItemStack consume0(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount, boolean copyOutput) {
        ItemStack ret;
        if (amount <= 0) {
            throw new IllegalArgumentException("negative/zero amount");
        }
        ItemStack stack = StackUtil.get(player, hand);
        if (StackUtil.isEmpty(stack)) {
            return emptyStack;
        }
        if (!request.apply((Object)stack)) {
            return emptyStack;
        }
        if (player.field_71075_bZ.field_75098_d) {
            return copyOutput ? StackUtil.copyWithSize(stack, amount) : stack;
        }
        if (StackUtil.getSize(stack) < amount) {
            return emptyStack;
        }
        if (StackUtil.getSize(stack) == amount) {
            ret = stack;
            StackUtil.clear(player, hand);
        } else {
            ret = copyOutput ? StackUtil.copyWithSize(stack, amount) : stack;
            StackUtil.set(player, hand, StackUtil.decSize(stack, amount));
        }
        return ret;
    }

    public static boolean damage(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
        return StackUtil.damage0(player, hand, request, amount, false) != emptyStack;
    }

    public static void damageOrError(EntityPlayer player, EnumHand hand, int amount) {
        StackUtil.damageOrError(player, hand, anyStack, amount);
    }

    public static void damageOrError(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
        if (!StackUtil.damage(player, hand, request, amount)) {
            throw new IllegalStateException("damage failed");
        }
    }

    private static ItemStack damage0(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount, boolean copyOutput) {
        ItemStack ret;
        if (amount <= 0) {
            throw new IllegalArgumentException("negative/zero amount");
        }
        ItemStack stack = StackUtil.get(player, hand);
        if (StackUtil.isEmpty(stack)) {
            return emptyStack;
        }
        int maxDamage = stack.func_77958_k();
        if (maxDamage <= 0) {
            return emptyStack;
        }
        if (!request.apply((Object)stack)) {
            return emptyStack;
        }
        if (player.field_71075_bZ.field_75098_d || !stack.func_77984_f()) {
            return copyOutput ? StackUtil.copy(stack) : stack;
        }
        stack.func_77972_a(amount, (EntityLivingBase)player);
        if (StackUtil.isEmpty(stack)) {
            ret = stack;
            StackUtil.clear(player, hand);
        } else {
            ret = copyOutput ? StackUtil.copy(stack) : stack;
            StackUtil.set(player, hand, stack);
        }
        return ret;
    }

    public static ItemStack get(EntityPlayer player, EnumHand hand) {
        return player.func_184586_b(hand);
    }

    public static void set(EntityPlayer player, EnumHand hand, ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            stack = emptyStack;
        }
        InventoryPlayer inv = player.field_71071_by;
        if (hand == EnumHand.MAIN_HAND) {
            inv.field_70462_a.set(inv.field_70461_c, (Object)stack);
        } else if (hand == EnumHand.OFF_HAND) {
            inv.field_184439_c.set(0, (Object)stack);
        } else {
            throw new IllegalArgumentException("invalid hand: " + hand);
        }
    }

    public static void clear(EntityPlayer player, EnumHand hand) {
        StackUtil.set(player, hand, emptyStack);
    }

    public static void clearEmpty(EntityPlayer player, EnumHand hand) {
        if (StackUtil.isEmpty(player, hand)) {
            StackUtil.clear(player, hand);
        }
    }

    public static void dropAsEntity(World world, BlockPos pos, ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return;
        }
        double f = 0.7;
        double dx = (double)world.field_73012_v.nextFloat() * f + (1.0 - f) * 0.5;
        double dy = (double)world.field_73012_v.nextFloat() * f + (1.0 - f) * 0.5;
        double dz = (double)world.field_73012_v.nextFloat() * f + (1.0 - f) * 0.5;
        EntityItem entityItem = new EntityItem(world, (double)pos.func_177958_n() + dx, (double)pos.func_177956_o() + dy, (double)pos.func_177952_p() + dz, stack.func_77946_l());
        entityItem.func_174869_p();
        world.func_72838_d((Entity)entityItem);
    }

    public static ItemStack copy(ItemStack stack) {
        return stack.func_77946_l();
    }

    public static ItemStack copyWithSize(ItemStack stack, int newSize) {
        if (StackUtil.isEmpty(stack)) {
            throw new IllegalArgumentException("empty stack: " + StackUtil.toStringSafe(stack));
        }
        return StackUtil.setSize(StackUtil.copy(stack), newSize);
    }

    public static ItemStack copyShrunk(ItemStack stack, int amount) {
        if (StackUtil.isEmpty(stack)) {
            throw new IllegalArgumentException("empty stack: " + StackUtil.toStringSafe(stack));
        }
        return StackUtil.setSize(StackUtil.copy(stack), StackUtil.getSize(stack) - amount);
    }

    public static ItemStack copyWithWildCard(ItemStack stack) {
        ItemStack ret = StackUtil.copy(stack);
        StackUtil.setRawMeta(ret, Short.MAX_VALUE);
        return ret;
    }

    public static Collection<ItemStack> copy(Collection<ItemStack> c) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>(c.size());
        for (ItemStack stack : c) {
            ret.add(StackUtil.copy(stack));
        }
        return ret;
    }

    public static NBTTagCompound getOrCreateNbtData(ItemStack stack) {
        NBTTagCompound ret = stack.func_77978_p();
        if (ret == null) {
            ret = new NBTTagCompound();
            stack.func_77982_d(ret);
        }
        return ret;
    }

    public static boolean checkItemEquality(ItemStack a, ItemStack b) {
        return StackUtil.isEmpty(a) && StackUtil.isEmpty(b) || !StackUtil.isEmpty(a) && !StackUtil.isEmpty(b) && a.func_77973_b() == b.func_77973_b() && (!a.func_77981_g() || a.func_77960_j() == b.func_77960_j()) && StackUtil.checkNbtEquality(a, b);
    }

    public static boolean checkItemEquality(ItemStack a, Item b) {
        return StackUtil.isEmpty(a) && b == null || !StackUtil.isEmpty(a) && b != null && a.func_77973_b() == b;
    }

    public static boolean checkItemEqualityStrict(ItemStack a, ItemStack b) {
        return StackUtil.isEmpty(a) && StackUtil.isEmpty(b) || !StackUtil.isEmpty(a) && !StackUtil.isEmpty(b) && a.func_77969_a(b) && StackUtil.checkNbtEqualityStrict(a, b);
    }

    private static boolean checkNbtEquality(ItemStack a, ItemStack b) {
        return StackUtil.checkNbtEquality(a.func_77978_p(), b.func_77978_p());
    }

    public static boolean checkNbtEquality(NBTTagCompound a, NBTTagCompound b) {
        if (a == b) {
            return true;
        }
        Set keysA = a != null ? a.func_150296_c() : Collections.emptySet();
        Set keysB = b != null ? b.func_150296_c() : Collections.emptySet();
        HashSet<String> toCheck = new HashSet<String>(Math.max(keysA.size(), keysB.size()));
        for (String key : keysA) {
            if (ignoredNbtKeys.contains(key)) continue;
            if (!keysB.contains(key)) {
                return false;
            }
            toCheck.add(key);
        }
        for (String key : keysB) {
            if (ignoredNbtKeys.contains(key)) continue;
            if (!keysA.contains(key)) {
                return false;
            }
            toCheck.add(key);
        }
        for (String key : toCheck) {
            if (a.func_74781_a(key).equals((Object)b.func_74781_a(key))) continue;
            return false;
        }
        return true;
    }

    public static boolean checkNbtEqualityStrict(ItemStack a, ItemStack b) {
        NBTTagCompound nbtB;
        NBTTagCompound nbtA = a.func_77978_p();
        if (nbtA == (nbtB = b.func_77978_p())) {
            return true;
        }
        return nbtA != null && nbtB != null && nbtA.equals((Object)nbtB);
    }

    public static ItemStack getPickStack(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        RayTraceResult target = new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d((Vec3i)pos), EnumFacing.DOWN, pos);
        ItemStack ret = state.func_177230_c().getPickBlock(state, target, world, pos, player);
        if (StackUtil.isEmpty(ret)) {
            return emptyStack;
        }
        return ret;
    }

    public static List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return StackUtil.getDrops(world, pos, state, state.func_177230_c(), fortune);
    }

    public static List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, Block block, int fortune) {
        NonNullList drops = NonNullList.func_191196_a();
        assert (world.func_180495_p(pos).func_177230_c() == block);
        block.getDrops(drops, world, pos, state, fortune);
        return drops;
    }

    public static List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, EntityPlayer player, int fortune, boolean silkTouch) {
        ItemStack drop;
        Block block = state.func_177230_c();
        if (block.isAir(state, world, pos)) {
            return Collections.emptyList();
        }
        World rawWorld = null;
        if (silkTouch) {
            rawWorld = Util.getWorld(world);
            if (rawWorld == null) {
                throw new IllegalArgumentException("invalid world for silk touch: " + world);
            }
            if (player == null) {
                player = Ic2Player.get(rawWorld);
            }
        }
        if (silkTouch && block.canSilkHarvest(rawWorld, pos, state, player) && !StackUtil.isEmpty(drop = StackUtil.getPickStack(rawWorld, pos, state, player))) {
            return Collections.singletonList(drop);
        }
        return StackUtil.getDrops(world, pos, state, block, fortune);
    }

    public static boolean placeBlock(ItemStack stack, World world, BlockPos pos) {
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        Item item = stack.func_77973_b();
        if (item instanceof ItemBlock || item instanceof ItemBlockSpecial) {
            int oldSize = StackUtil.getSize(stack);
            EntityPlayer player = Ic2Player.get(world);
            EnumHand hand = EnumHand.MAIN_HAND;
            ItemStack prev = player.func_184586_b(hand);
            player.func_184611_a(hand, stack);
            EnumActionResult result = item.func_180614_a(player, world, pos, hand, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
            player.func_184611_a(hand, prev);
            stack = StackUtil.setSize(stack, oldSize);
            return result == EnumActionResult.SUCCESS;
        }
        return false;
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack == emptyStack || stack == null || stack.func_77973_b() == null || stack.func_190916_E() <= 0;
    }

    public static boolean isEmpty(EntityPlayer player, EnumHand hand) {
        return StackUtil.isEmpty(player.func_184586_b(hand));
    }

    public static int getSize(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return 0;
        }
        return stack.func_190916_E();
    }

    public static ItemStack setSize(ItemStack stack, int size) {
        if (size <= 0) {
            return emptyStack;
        }
        stack.func_190920_e(size);
        return stack;
    }

    public static ItemStack incSize(ItemStack stack) {
        return StackUtil.incSize(stack, 1);
    }

    public static ItemStack incSize(ItemStack stack, int amount) {
        return StackUtil.setSize(stack, StackUtil.getSize(stack) + amount);
    }

    public static ItemStack decSize(ItemStack stack) {
        return StackUtil.decSize(stack, 1);
    }

    public static ItemStack decSize(ItemStack stack, int amount) {
        return StackUtil.incSize(stack, -amount);
    }

    public static boolean check2(Iterable<List<ItemStack>> list) {
        for (List<ItemStack> list2 : list) {
            if (StackUtil.check(list2)) continue;
            return false;
        }
        return true;
    }

    public static boolean check(ItemStack[] array) {
        return StackUtil.check(Arrays.asList(array));
    }

    public static boolean check(Iterable<ItemStack> list) {
        for (ItemStack stack : list) {
            if (StackUtil.check(stack)) continue;
            return false;
        }
        return true;
    }

    public static boolean check(ItemStack stack) {
        return stack.func_77973_b() != null;
    }

    public static String toStringSafe2(Iterable<List<ItemStack>> list) {
        StringBuilder ret = new StringBuilder("[");
        for (List<ItemStack> list2 : list) {
            if (ret.length() > 1) {
                ret.append(", ");
            }
            ret.append(StackUtil.toStringSafe(list2));
        }
        return ret.append(']').toString();
    }

    public static String toStringSafe(ItemStack[] array) {
        return StackUtil.toStringSafe(Arrays.asList(array));
    }

    public static String toStringSafe(Iterable<ItemStack> list) {
        StringBuilder ret = new StringBuilder("[");
        for (ItemStack stack : list) {
            if (ret.length() > 1) {
                ret.append(", ");
            }
            ret.append(StackUtil.toStringSafe(stack));
        }
        return ret.append(']').toString();
    }

    public static String toStringSafe(ItemStack stack) {
        if (stack == null) {
            return "(null)";
        }
        if (stack.func_77973_b() == null) {
            return StackUtil.getSize(stack) + "x(null)@(unknown)";
        }
        return stack.toString();
    }

    public static boolean storeInventoryItem(ItemStack stack, EntityPlayer player, boolean simulate) {
        if (simulate) {
            int sizeLeft = StackUtil.getSize(stack);
            int maxStackSize = Math.min(player.field_71071_by.func_70297_j_(), stack.func_77976_d());
            for (int i = 0; i < player.field_71071_by.field_70462_a.size() && sizeLeft > 0; ++i) {
                ItemStack invStack = (ItemStack)player.field_71071_by.field_70462_a.get(i);
                if (StackUtil.isEmpty(invStack)) {
                    sizeLeft -= maxStackSize;
                    continue;
                }
                if (!StackUtil.checkItemEqualityStrict(stack, invStack) || StackUtil.getSize(invStack) >= maxStackSize) continue;
                sizeLeft -= maxStackSize - StackUtil.getSize(invStack);
            }
            return sizeLeft <= 0;
        }
        return player.field_71071_by.func_70441_a(stack);
    }

    public static int getRawMeta(ItemStack stack) {
        return Items.field_151100_aR.getDamage(stack);
    }

    public static void setRawMeta(ItemStack stack, int meta) {
        if (meta < 0) {
            throw new IllegalArgumentException("negative meta");
        }
        Items.field_151100_aR.setDamage(stack, meta);
    }

    public static TIntSet getSlotsFromInv(IInventory inv) {
        TIntHashSet set = new TIntHashSet();
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            set.add(i);
        }
        return set;
    }

    public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory craftMatrix) {
        return StackUtil.balanceStacks(craftMatrix, Collections.emptySet());
    }

    public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory craftMatrix, ItemStack sourceItemStack) {
        return StackUtil.balanceStacks(craftMatrix, Collections.singleton(sourceItemStack));
    }

    public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(final IInventory inv, Collection<ItemStack> additionalItems) {
        return StackUtil.balanceStacks(inv, new Predicate<Tuple.T2<ItemStack, Integer>>(){

            public boolean apply(Tuple.T2<ItemStack, Integer> input) {
                return !StackUtil.isEmpty(inv.func_70301_a(((Integer)input.b).intValue()));
            }
        }, StackUtil.getSlotsFromInv(inv), additionalItems);
    }

    public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory inv, Predicate<Tuple.T2<ItemStack, Integer>> canInsert) {
        return StackUtil.balanceStacks(inv, canInsert, StackUtil.getSlotsFromInv(inv), Collections.emptySet());
    }

    public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory inv, Predicate<Tuple.T2<ItemStack, Integer>> canInsert, TIntSet originalAvailableSlots, Collection<ItemStack> additionalStacksOriginal) {
        int amount;
        LinkedList<ItemStack> additionalStacks = new LinkedList<ItemStack>(additionalStacksOriginal);
        TIntHashSet availableSlots = new TIntHashSet((TIntCollection)originalAvailableSlots);
        ArrayList<ItemStack> leftOvers = new ArrayList<ItemStack>();
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            int size;
            ItemStack stack;
            if (!availableSlots.contains(i) || StackUtil.isEmpty(stack = inv.func_70301_a(i))) continue;
            amount = 0;
            ListIterator iter = additionalStacks.listIterator();
            while (iter.hasNext()) {
                ItemStack currentStack = (ItemStack)iter.next();
                if (!StackUtil.checkItemEqualityStrict(currentStack, stack)) continue;
                iter.remove();
                amount += StackUtil.getSize(currentStack);
            }
            for (amount = StackUtil.distributeStackToSlots(inv, stack, (TIntSet)availableSlots, canInsert, amount); amount > 0; amount -= size) {
                size = Math.min(stack.func_77976_d(), amount);
                leftOvers.add(StackUtil.copyWithSize(stack, size));
            }
        }
        for (ItemStack stack : additionalStacks) {
            amount = StackUtil.distributeStackToSlots(inv, stack, (TIntSet)availableSlots, canInsert, StackUtil.getSize(stack));
            if (amount <= 0) continue;
            leftOvers.add(StackUtil.copyWithSize(stack, amount));
        }
        originalAvailableSlots.removeAll((TIntCollection)availableSlots);
        return new Tuple.T2(leftOvers, originalAvailableSlots);
    }

    private static int distributeStackToSlots(final IInventory inv, ItemStack stack, TIntSet availableSlots, Predicate<Tuple.T2<ItemStack, Integer>> canInsert, int amount) {
        TIntArrayList currentWorkingSet = new TIntArrayList();
        TIntIterator iter = availableSlots.iterator();
        while (iter.hasNext()) {
            int currentSlot = iter.next();
            ItemStack currentStack = inv.func_70301_a(currentSlot);
            if (!StackUtil.checkItemEqualityStrict(stack, currentStack) && !StackUtil.isEmpty(currentStack) || !canInsert.apply(new Tuple.T2<ItemStack, Integer>(stack, currentSlot))) continue;
            amount += StackUtil.getSize(currentStack);
            currentWorkingSet.add(currentSlot);
            iter.remove();
        }
        currentWorkingSet.sort();
        int maxStackSize = Math.min(stack.func_77976_d(), inv.func_70297_j_());
        int slotsLeft = currentWorkingSet.size();
        TIntIterator iter2 = currentWorkingSet.iterator();
        while (iter2.hasNext() && amount > 0) {
            int currentSlot = iter2.next();
            int itemsToPut = amount / slotsLeft;
            if (amount % slotsLeft > 0) {
                ++itemsToPut;
            }
            itemsToPut = Math.min(itemsToPut, maxStackSize);
            inv.func_70299_a(currentSlot, StackUtil.copyWithSize(stack, itemsToPut));
            amount -= itemsToPut;
            --slotsLeft;
            iter2.remove();
        }
        if (!currentWorkingSet.isEmpty()) {
            assert (amount <= 0);
            currentWorkingSet.forEach(new TIntProcedure(){

                public boolean execute(int currentSlot) {
                    inv.func_70299_a(currentSlot, emptyStack);
                    return true;
                }
            });
        }
        assert (amount <= 0 || slotsLeft == 0);
        return amount;
    }

    public static ItemStack setImmutableSize(ItemStack stack, int size) {
        if (StackUtil.getSize(stack) != size) {
            stack = StackUtil.copyWithSize(stack, size);
        }
        return stack;
    }

    public static boolean matchesNBT(NBTTagCompound subject, NBTTagCompound target) {
        if (subject == null) {
            return target == null || target.func_82582_d();
        }
        if (target == null) {
            return true;
        }
        for (String key : target.func_150296_c()) {
            NBTBase targetNBT = target.func_74781_a(key);
            if (!subject.func_74764_b(key) || targetNBT.func_74732_a() != subject.func_150299_b(key)) {
                return false;
            }
            NBTBase subjectNBT = subject.func_74781_a(key);
            if (targetNBT.equals((Object)subjectNBT)) continue;
            return false;
        }
        return true;
    }

    public static ItemStack wrapEmpty(ItemStack stack) {
        return stack == null ? emptyStack : stack;
    }

    public static class PersonalAdjacentInv
    extends AdjacentInv {
        public final GameProfile accessor;

        PersonalAdjacentInv(TileEntity te, EnumFacing dir, GameProfile accessor) {
            super(te, dir);
            this.accessor = accessor;
        }

        @Override
        public GameProfile getAccessor() {
            return this.accessor;
        }
    }

    public static class AdjacentInv {
        public final TileEntity te;
        public final EnumFacing dir;

        AdjacentInv(TileEntity te, EnumFacing dir) {
            this.te = te;
            this.dir = dir;
        }

        public GameProfile getAccessor() {
            return null;
        }
    }
}

