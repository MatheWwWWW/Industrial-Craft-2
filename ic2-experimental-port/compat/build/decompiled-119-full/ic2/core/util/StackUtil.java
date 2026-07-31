/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.util;

import com.mojang.authlib.GameProfile;
import ic2.api.recipe.IRecipeInput;
import ic2.core.IC2;
import ic2.core.Ic2Player;
import ic2.core.item.EnvItemHandler;
import ic2.core.util.LogCategory;
import ic2.core.util.Tuple;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class StackUtil {
    static final Set<String> ignoredNbtKeys = new HashSet<String>(Arrays.asList("damage", "charge", "energy", "advDmg"));
    public static final Predicate<ItemStack> anyStack = itemStack -> true;
    public static final ItemStack emptyStack = ItemStack.f_41583_;
    private static final int[] emptySlotArray = new int[0];
    public static final EnvItemHandler ENV = IC2.envProxy.createItemHandler();

    public static boolean isEmpty(ItemStack itemStack) {
        return itemStack == emptyStack || itemStack == null || itemStack.m_41720_() == null || itemStack.m_41613_() <= 0;
    }

    public static boolean isEmpty(Player player, InteractionHand interactionHand) {
        return StackUtil.isEmpty(player.m_21120_(interactionHand));
    }

    public static int getSize(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        return itemStack.m_41613_();
    }

    public static ItemStack setSize(ItemStack itemStack, int n) {
        itemStack.m_41764_(n);
        return n <= 0 ? emptyStack : itemStack;
    }

    public static ItemStack incSize(ItemStack itemStack) {
        return StackUtil.incSize(itemStack, 1);
    }

    public static ItemStack incSize(ItemStack itemStack, int n) {
        return StackUtil.setSize(itemStack, StackUtil.getSize(itemStack) + n);
    }

    public static ItemStack decSize(ItemStack itemStack) {
        return StackUtil.decSize(itemStack, 1);
    }

    public static ItemStack decSize(ItemStack itemStack, int n) {
        return StackUtil.incSize(itemStack, -n);
    }

    public static ItemStack wrapEmpty(ItemStack itemStack) {
        return itemStack == null ? emptyStack : itemStack;
    }

    public static boolean check2(Iterable<List<ItemStack>> iterable) {
        for (List<ItemStack> list : iterable) {
            if (StackUtil.check(list)) continue;
            return false;
        }
        return true;
    }

    public static boolean check(ItemStack[] itemStackArray) {
        return StackUtil.check(Arrays.asList(itemStackArray));
    }

    public static boolean check(Iterable<ItemStack> iterable) {
        for (ItemStack itemStack : iterable) {
            if (StackUtil.check(itemStack)) continue;
            return false;
        }
        return true;
    }

    public static boolean check(ItemStack itemStack) {
        return itemStack.m_41720_() != null;
    }

    public static String toStringSafe2(Iterable<List<ItemStack>> iterable) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (List<ItemStack> list : iterable) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(StackUtil.toStringSafe(list));
        }
        return stringBuilder.append(']').toString();
    }

    public static String toStringSafe(ItemStack[] itemStackArray) {
        return StackUtil.toStringSafe(Arrays.asList(itemStackArray));
    }

    public static String toStringSafe(Iterable<ItemStack> iterable) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (ItemStack itemStack : iterable) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(StackUtil.toStringSafe(itemStack));
        }
        return stringBuilder.append(']').toString();
    }

    public static String toStringSafe(ItemStack itemStack) {
        if (itemStack == null) {
            return "(null)";
        }
        if (itemStack.m_41720_() == null) {
            return StackUtil.getSize(itemStack) + "x(null)@(unknown)";
        }
        return itemStack.toString();
    }

    public static ItemStack copy(ItemStack itemStack) {
        return itemStack.m_41777_();
    }

    public static ItemStack copyWithSize(ItemStack itemStack, int n) {
        if (StackUtil.isEmpty(itemStack)) {
            throw new IllegalArgumentException("empty stack: " + StackUtil.toStringSafe(itemStack));
        }
        return StackUtil.setSize(StackUtil.copy(itemStack), n);
    }

    public static ItemStack copyShrunk(ItemStack itemStack, int n) {
        if (StackUtil.isEmpty(itemStack)) {
            throw new IllegalArgumentException("empty stack: " + StackUtil.toStringSafe(itemStack));
        }
        return StackUtil.setSize(StackUtil.copy(itemStack), StackUtil.getSize(itemStack) - n);
    }

    public static Collection<ItemStack> copy(Collection<ItemStack> collection) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(collection.size());
        for (ItemStack itemStack : collection) {
            arrayList.add(StackUtil.copy(itemStack));
        }
        return arrayList;
    }

    public static CompoundTag getOrCreateNbtData(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            compoundTag = new CompoundTag();
            itemStack.m_41751_(compoundTag);
        }
        return compoundTag;
    }

    public static boolean checkItemEquality(ItemStack itemStack, ItemStack itemStack2) {
        return StackUtil.isEmpty(itemStack) && StackUtil.isEmpty(itemStack2) || !StackUtil.isEmpty(itemStack) && !StackUtil.isEmpty(itemStack2) && itemStack.m_41720_() == itemStack2.m_41720_() && StackUtil.checkNbtEquality(itemStack, itemStack2);
    }

    public static boolean checkItemEquality(ItemStack itemStack, Item item) {
        return StackUtil.isEmpty(itemStack) && item == null || !StackUtil.isEmpty(itemStack) && item != null && itemStack.m_41720_() == item;
    }

    public static boolean checkItemEqualityStrict(ItemStack itemStack, ItemStack itemStack2) {
        return StackUtil.isEmpty(itemStack) && StackUtil.isEmpty(itemStack2) || !StackUtil.isEmpty(itemStack) && !StackUtil.isEmpty(itemStack2) && itemStack.m_41656_(itemStack2) && StackUtil.checkNbtEqualityStrict(itemStack, itemStack2);
    }

    private static boolean checkNbtEquality(ItemStack itemStack, ItemStack itemStack2) {
        return StackUtil.checkNbtEquality(itemStack.m_41783_(), itemStack2.m_41783_());
    }

    public static boolean checkNbtEquality(CompoundTag compoundTag, CompoundTag compoundTag2) {
        if (compoundTag == compoundTag2) {
            return true;
        }
        Set set = compoundTag != null ? compoundTag.m_128431_() : Collections.emptySet();
        Set set2 = compoundTag2 != null ? compoundTag2.m_128431_() : Collections.emptySet();
        HashSet<String> hashSet = new HashSet<String>(Math.max(set.size(), set2.size()));
        for (String string : set) {
            if (ignoredNbtKeys.contains(string)) continue;
            if (!set2.contains(string)) {
                return false;
            }
            hashSet.add(string);
        }
        for (String string : set2) {
            if (ignoredNbtKeys.contains(string)) continue;
            if (!set.contains(string)) {
                return false;
            }
            hashSet.add(string);
        }
        for (String string : hashSet) {
            if (compoundTag.m_128423_(string).equals(compoundTag2.m_128423_(string))) continue;
            return false;
        }
        return true;
    }

    public static boolean checkNbtEqualityStrict(ItemStack itemStack, ItemStack itemStack2) {
        CompoundTag compoundTag;
        CompoundTag compoundTag2 = itemStack.m_41783_();
        if (compoundTag2 == (compoundTag = itemStack2.m_41783_())) {
            return true;
        }
        return compoundTag2 != null && compoundTag != null && compoundTag2.equals((Object)compoundTag);
    }

    public static Predicate<ItemStack> sameStack(final ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            throw new IllegalArgumentException("empty stack");
        }
        return new Predicate<ItemStack>(){

            @Override
            public boolean test(ItemStack itemStack2) {
                return StackUtil.checkItemEquality(itemStack2, itemStack);
            }

            public String toString() {
                return "stack==" + itemStack;
            }
        };
    }

    public static Predicate<ItemStack> sameItem(final Item item) {
        if (item == null) {
            throw new NullPointerException("null item");
        }
        return new Predicate<ItemStack>(){

            @Override
            public boolean test(ItemStack itemStack) {
                return itemStack.m_41720_() == item;
            }

            public String toString() {
                return "item==" + item;
            }
        };
    }

    public static Predicate<ItemStack> sameItem(ItemLike itemLike) {
        if (itemLike == null) {
            throw new NullPointerException("null block");
        }
        Item item = itemLike.m_5456_();
        if (item == null || item == Items.f_41852_ && itemLike != Blocks.f_50016_) {
            throw new IllegalArgumentException("block " + itemLike + " doesn't have an associated item");
        }
        return StackUtil.sameItem(item);
    }

    public static Predicate<ItemStack> recipeInput(final IRecipeInput iRecipeInput) {
        return new Predicate<ItemStack>(){

            @Override
            public boolean test(ItemStack itemStack) {
                return iRecipeInput.matches(itemStack);
            }

            public String toString() {
                return iRecipeInput.toString();
            }
        };
    }

    public static boolean consume(Player player, InteractionHand interactionHand, Predicate<ItemStack> predicate, int n) {
        return StackUtil.consume0(player, interactionHand, predicate, n, false) != emptyStack;
    }

    public static ItemStack consumeAndGet(Player player, Predicate<ItemStack> predicate, int n) {
        return StackUtil.consumeAndGet(player, InteractionHand.MAIN_HAND, predicate, n);
    }

    public static ItemStack consumeAndGet(Player player, InteractionHand interactionHand, Predicate<ItemStack> predicate, int n) {
        return StackUtil.consume0(player, interactionHand, predicate, n, true);
    }

    public static void consumeOrError(Player player, InteractionHand interactionHand, int n) {
        StackUtil.consumeOrError(player, interactionHand, anyStack, n);
    }

    public static void consumeOrError(Player player, InteractionHand interactionHand, Predicate<ItemStack> predicate, int n) {
        if (!StackUtil.consume(player, interactionHand, predicate, n)) {
            throw new IllegalStateException("consume failed");
        }
    }

    private static ItemStack consume0(Player player, InteractionHand interactionHand, Predicate<ItemStack> predicate, int n, boolean bl) {
        ItemStack itemStack;
        if (n <= 0) {
            throw new IllegalArgumentException("negative/zero amount");
        }
        ItemStack itemStack2 = StackUtil.get(player, interactionHand);
        if (StackUtil.isEmpty(itemStack2)) {
            return emptyStack;
        }
        if (!predicate.test(itemStack2)) {
            return emptyStack;
        }
        if (player.m_150110_().f_35937_) {
            return bl ? StackUtil.copyWithSize(itemStack2, n) : itemStack2;
        }
        if (StackUtil.getSize(itemStack2) < n) {
            return emptyStack;
        }
        if (StackUtil.getSize(itemStack2) == n) {
            itemStack = itemStack2;
            StackUtil.clear(player, interactionHand);
        } else {
            itemStack = bl ? StackUtil.copyWithSize(itemStack2, n) : itemStack2;
            StackUtil.set(player, interactionHand, StackUtil.decSize(itemStack2, n));
        }
        return itemStack;
    }

    public static boolean consumeFromPlayerInventory(Player player, Predicate<ItemStack> predicate, int n, boolean bl) {
        NonNullList nonNullList = player.m_150109_().f_35974_;
        for (int i = 0; i < 2; ++i) {
            int n2 = n;
            for (int j = 0; j < nonNullList.size(); ++j) {
                ItemStack itemStack = (ItemStack)nonNullList.get(j);
                if (!predicate.test(itemStack)) continue;
                if (player.m_150110_().f_35937_) {
                    return true;
                }
                int n3 = Math.min(StackUtil.getSize(itemStack), n2);
                n2 -= n3;
                if (i == 1) {
                    nonNullList.set(j, (Object)StackUtil.decSize(itemStack, n3));
                }
                if (n2 <= 0) break;
            }
            if (n2 > 0) {
                if (i == 1) {
                    IC2.log.warn(LogCategory.General, "Inconsistent inventory transaction for player %s, request %s: %d missing", player, predicate, n2);
                }
                return false;
            }
            if (!bl) continue;
            return true;
        }
        return true;
    }

    public static boolean damage(Player player, InteractionHand interactionHand, Predicate<ItemStack> predicate, int n) {
        return StackUtil.damage0(player, interactionHand, predicate, n, false) != emptyStack;
    }

    public static void damageOrError(Player player, InteractionHand interactionHand, int n) {
        StackUtil.damageOrError(player, interactionHand, anyStack, n);
    }

    public static void damageOrError(Player player, InteractionHand interactionHand, Predicate<ItemStack> predicate, int n) {
        if (!StackUtil.damage(player, interactionHand, predicate, n)) {
            throw new IllegalStateException("damage failed");
        }
    }

    private static ItemStack damage0(Player player2, InteractionHand interactionHand, Predicate<ItemStack> predicate, int n, boolean bl) {
        ItemStack itemStack;
        if (n <= 0) {
            throw new IllegalArgumentException("negative/zero amount");
        }
        ItemStack itemStack2 = StackUtil.get(player2, interactionHand);
        if (StackUtil.isEmpty(itemStack2)) {
            return emptyStack;
        }
        int n2 = itemStack2.m_41776_();
        if (n2 <= 0) {
            return emptyStack;
        }
        if (!predicate.test(itemStack2)) {
            return emptyStack;
        }
        if (player2.m_150110_().f_35937_ || !itemStack2.m_41763_()) {
            return bl ? StackUtil.copy(itemStack2) : itemStack2;
        }
        itemStack2.m_41622_(n, (LivingEntity)player2, player -> player.m_21190_(interactionHand));
        if (StackUtil.isEmpty(itemStack2)) {
            itemStack = itemStack2;
            StackUtil.clear(player2, interactionHand);
        } else {
            itemStack = bl ? StackUtil.copy(itemStack2) : itemStack2;
            StackUtil.set(player2, interactionHand, itemStack2);
        }
        return itemStack;
    }

    public static ItemStack get(Player player, InteractionHand interactionHand) {
        return player.m_21120_(interactionHand);
    }

    public static void set(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            itemStack = emptyStack;
        }
        Inventory inventory = player.m_150109_();
        if (interactionHand == InteractionHand.MAIN_HAND) {
            inventory.f_35974_.set(inventory.f_35977_, (Object)itemStack);
        } else if (interactionHand == InteractionHand.OFF_HAND) {
            inventory.f_35976_.set(0, (Object)itemStack);
        } else {
            throw new IllegalArgumentException("invalid hand: " + interactionHand);
        }
    }

    public static void clear(Player player, InteractionHand interactionHand) {
        StackUtil.set(player, interactionHand, emptyStack);
    }

    public static void clearEmpty(Player player, InteractionHand interactionHand) {
        if (StackUtil.isEmpty(player, interactionHand)) {
            StackUtil.clear(player, interactionHand);
        }
    }

    public static boolean storeInventoryItem(ItemStack itemStack, Player player, boolean bl) {
        if (bl) {
            int n = StackUtil.getSize(itemStack);
            int n2 = Math.min(player.m_150109_().m_6893_(), itemStack.m_41741_());
            for (int i = 0; i < player.m_150109_().f_35974_.size() && n > 0; ++i) {
                ItemStack itemStack2 = (ItemStack)player.m_150109_().f_35974_.get(i);
                if (StackUtil.isEmpty(itemStack2)) {
                    n -= n2;
                    continue;
                }
                if (!StackUtil.checkItemEqualityStrict(itemStack, itemStack2) || StackUtil.getSize(itemStack2) >= n2) continue;
                n -= n2 - StackUtil.getSize(itemStack2);
            }
            return n <= 0;
        }
        return player.m_150109_().m_36054_(itemStack);
    }

    public static void dropAsEntity(Level level, BlockPos blockPos, ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return;
        }
        double d = 0.7;
        double d2 = (double)level.f_46441_.m_188501_() * d + (1.0 - d) * 0.5;
        double d3 = (double)level.f_46441_.m_188501_() * d + (1.0 - d) * 0.5;
        double d4 = (double)level.f_46441_.m_188501_() * d + (1.0 - d) * 0.5;
        ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.m_123341_() + d2, (double)blockPos.m_123342_() + d3, (double)blockPos.m_123343_() + d4, itemStack.m_41777_());
        itemEntity.m_32060_();
        level.m_7967_((Entity)itemEntity);
    }

    public static ItemStack setImmutableSize(ItemStack itemStack, int n) {
        if (StackUtil.getSize(itemStack) != n) {
            itemStack = StackUtil.copyWithSize(itemStack, n);
        }
        return itemStack;
    }

    public static int fetch(BlockEntity blockEntity, ItemStack itemStack, boolean bl) {
        return ENV.fetch(blockEntity, itemStack, bl);
    }

    public static int distribute(BlockEntity blockEntity, ItemStack itemStack, boolean bl) {
        return ENV.distribute(blockEntity, itemStack, bl);
    }

    public static void distributeDrops(BlockEntity blockEntity, List<ItemStack> list) {
        Iterator<ItemStack> iterator = list.listIterator();
        while (iterator.hasNext()) {
            ItemStack itemStack = iterator.next();
            int n = StackUtil.distribute(blockEntity, itemStack, false);
            if (n == StackUtil.getSize(itemStack)) {
                iterator.remove();
                continue;
            }
            iterator.set(StackUtil.decSize(itemStack, n));
        }
        for (ItemStack itemStack : list) {
            StackUtil.dropAsEntity(blockEntity.m_58904_(), blockEntity.m_58899_(), itemStack);
        }
        list.clear();
    }

    public static boolean placeBlock(ItemStack itemStack, Level level, BlockPos blockPos) {
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof BlockItem) {
            int n = StackUtil.getSize(itemStack);
            Player player = Ic2Player.get(level);
            InteractionHand interactionHand = InteractionHand.MAIN_HAND;
            ItemStack itemStack2 = player.m_21120_(interactionHand);
            player.m_21008_(interactionHand, itemStack);
            InteractionResult interactionResult = item.m_6225_(new UseOnContext(player, interactionHand, new BlockHitResult(Vec3.m_82528_((Vec3i)blockPos).m_82520_(0.5, 1.0, 0.5), Direction.DOWN, blockPos, false)));
            player.m_21008_(interactionHand, itemStack2);
            itemStack = StackUtil.setSize(itemStack, n);
            return interactionResult == InteractionResult.SUCCESS || interactionResult == InteractionResult.CONSUME;
        }
        return false;
    }

    public static int putInInventory(BlockEntity blockEntity, Direction direction, ItemStack itemStack, boolean bl) {
        return StackUtil.putInInventory(blockEntity, direction, itemStack, null, bl);
    }

    public static int putInInventory(BlockEntity blockEntity, Direction direction, ItemStack itemStack, GameProfile gameProfile, boolean bl) {
        return ENV.deposit(blockEntity, direction, itemStack, gameProfile, bl);
    }

    public static ItemStack getPickStack(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        ItemStack itemStack = blockState.m_60734_().m_7397_((BlockGetter)level, blockPos, blockState);
        if (StackUtil.isEmpty(itemStack)) {
            return emptyStack;
        }
        return itemStack;
    }

    public static List<ItemStack> getDrops(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, int n) {
        return StackUtil.getDrops(blockGetter, blockPos, blockState, blockState.m_60734_(), n);
    }

    public static List<ItemStack> getDrops(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Block block, int n) {
        return StackUtil.getDrops(blockGetter, blockPos, blockState, null, n, false);
    }

    public static List<ItemStack> getDrops(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Player player, int n, boolean bl) {
        if (blockState.m_60795_()) {
            return Collections.emptyList();
        }
        ItemStack itemStack = new ItemStack((ItemLike)Items.f_42390_);
        if (bl) {
            EnchantmentHelper.m_44865_(Collections.singletonMap(Enchantments.f_44985_, n), (ItemStack)itemStack);
        } else if (n > 0) {
            EnchantmentHelper.m_44865_(Collections.singletonMap(Enchantments.f_44987_, n), (ItemStack)itemStack);
        }
        return Block.m_49874_((BlockState)blockState, (ServerLevel)((ServerLevel)blockGetter), (BlockPos)blockPos, (BlockEntity)blockGetter.m_7702_(blockPos), (Entity)player, (ItemStack)itemStack);
    }

    public static IntSet getSlotsFromInv(Container container) {
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
        for (int i = 0; i < container.m_6643_(); ++i) {
            intOpenHashSet.add(i);
        }
        return intOpenHashSet;
    }

    public static Tuple.T2<List<ItemStack>, ? extends IntCollection> balanceStacks(Container container) {
        return StackUtil.balanceStacks(container, Collections.emptySet());
    }

    public static Tuple.T2<List<ItemStack>, ? extends IntCollection> balanceStacks(Container container, ItemStack itemStack) {
        return StackUtil.balanceStacks(container, Collections.singleton(itemStack));
    }

    public static Tuple.T2<List<ItemStack>, ? extends IntCollection> balanceStacks(final Container container, Collection<ItemStack> collection) {
        return StackUtil.balanceStacks(container, new Predicate<Tuple.T2<ItemStack, Integer>>(){

            @Override
            public boolean test(Tuple.T2<ItemStack, Integer> t2) {
                return !StackUtil.isEmpty(container.m_8020_(((Integer)t2.b).intValue()));
            }
        }, StackUtil.getSlotsFromInv(container), collection);
    }

    public static Tuple.T2<List<ItemStack>, ? extends IntCollection> balanceStacks(Container container, Predicate<Tuple.T2<ItemStack, Integer>> predicate) {
        return StackUtil.balanceStacks(container, predicate, StackUtil.getSlotsFromInv(container), Collections.emptySet());
    }

    public static Tuple.T2<List<ItemStack>, ? extends IntCollection> balanceStacks(Container container, Predicate<Tuple.T2<ItemStack, Integer>> predicate, IntSet intSet, Collection<ItemStack> collection) {
        int n;
        LinkedList<ItemStack> linkedList = new LinkedList<ItemStack>(collection);
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet((IntCollection)intSet);
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        for (int i = 0; i < container.m_6643_(); ++i) {
            int n2;
            ItemStack itemStack;
            if (!intOpenHashSet.contains(i) || StackUtil.isEmpty(itemStack = container.m_8020_(i))) continue;
            n = 0;
            ListIterator listIterator = linkedList.listIterator();
            while (listIterator.hasNext()) {
                ItemStack itemStack2 = (ItemStack)listIterator.next();
                if (!StackUtil.checkItemEqualityStrict(itemStack2, itemStack)) continue;
                listIterator.remove();
                n += StackUtil.getSize(itemStack2);
            }
            for (n = StackUtil.distributeStackToSlots(container, itemStack, (IntSet)intOpenHashSet, predicate, n); n > 0; n -= n2) {
                n2 = Math.min(itemStack.m_41741_(), n);
                arrayList.add(StackUtil.copyWithSize(itemStack, n2));
            }
        }
        for (ItemStack itemStack : linkedList) {
            n = StackUtil.distributeStackToSlots(container, itemStack, (IntSet)intOpenHashSet, predicate, StackUtil.getSize(itemStack));
            if (n <= 0) continue;
            arrayList.add(StackUtil.copyWithSize(itemStack, n));
        }
        intSet.removeAll((IntCollection)intOpenHashSet);
        return new Tuple.T2(arrayList, intSet);
    }

    private static int distributeStackToSlots(Container container, ItemStack itemStack, IntSet intSet, Predicate<Tuple.T2<ItemStack, Integer>> predicate, int n) {
        int n2;
        ItemStack itemStack2;
        int n3;
        IntArrayList intArrayList = new IntArrayList();
        IntIterator intIterator = intSet.iterator();
        while (intIterator.hasNext()) {
            n3 = intIterator.nextInt();
            itemStack2 = container.m_8020_(n3);
            if (!StackUtil.checkItemEqualityStrict(itemStack, itemStack2) && !StackUtil.isEmpty(itemStack2) || !predicate.test(new Tuple.T2<ItemStack, Integer>(itemStack, n3))) continue;
            n += StackUtil.getSize(itemStack2);
            intArrayList.add(n3);
            intIterator.remove();
        }
        intArrayList.sort(Comparator.naturalOrder());
        int n4 = Math.min(itemStack.m_41741_(), container.m_6893_());
        n3 = intArrayList.size();
        itemStack2 = intArrayList.iterator();
        while (itemStack2.hasNext() && n > 0) {
            n2 = itemStack2.nextInt();
            int n5 = n / n3;
            if (n % n3 > 0) {
                ++n5;
            }
            n5 = Math.min(n5, n4);
            container.m_6836_(n2, StackUtil.copyWithSize(itemStack, n5));
            n -= n5;
            --n3;
            itemStack2.remove();
        }
        if (!intArrayList.isEmpty()) {
            assert (n <= 0);
            itemStack2 = intArrayList.iterator();
            while (itemStack2.hasNext()) {
                n2 = (Integer)itemStack2.next();
                container.m_6836_(n2, emptyStack);
            }
        }
        assert (n <= 0 || n3 == 0);
        return n;
    }
}

