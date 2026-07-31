/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.core.IHasGui;
import ic2.core.Ic2Player;
import ic2.core.init.OreValues;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.tool.ContainerToolScanner;
import ic2.core.item.tool.HandHeldScanner;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ItemComparableItemStack;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;

public class ItemScanner
extends BaseElectricItem
implements IBoxable,
IHandHeldInventory {
    public ItemScanner(Item.Properties properties, double d, double d2, int n) {
        super(properties, d, d2, n);
    }

    @Environment(value=EnvType.CLIENT)
    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.m_7373_(itemStack, level, list, tooltipFlag);
        list.add((Component)Component.m_237110_((String)"ic2.scanner.range", (Object[])new Object[]{"" + this.getScanRange()}).m_130940_(ChatFormatting.GRAY));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (this.tier == 1 && !ElectricItem.manager.use(itemStack, 50.0, (LivingEntity)player) || this.tier == 2 && !ElectricItem.manager.use(itemStack, 250.0, (LivingEntity)player)) {
            return new InteractionResultHolder(InteractionResult.FAIL, (Object)itemStack);
        }
        if (!level.f_46443_) {
            if (this.getInventory(player, interactionHand, itemStack).openManagedItem(player, interactionHand, null) && player.f_36096_ instanceof ContainerToolScanner) {
                ContainerToolScanner containerToolScanner = (ContainerToolScanner)player.f_36096_;
                Map<ItemComparableItemStack, Integer> map = this.scan(player.m_20193_(), player.m_20183_(), this.getScanRange());
                containerToolScanner.setResults(this.scanMapToSortedList(map));
            }
        } else {
            player.m_5496_(Ic2SoundEvents.ITEM_SCANNER_USE, 1.0f, 1.0f);
        }
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
    }

    public boolean onDroppedByPlayer(ItemStack itemStack, Player player) {
        HandHeldScanner handHeldScanner;
        if (!player.m_20193_().f_46443_ && !StackUtil.isEmpty(itemStack) && player.f_36096_ instanceof ContainerToolScanner && (handHeldScanner = (HandHeldScanner)((ContainerToolScanner)player.f_36096_).base).isThisContainer(itemStack)) {
            handHeldScanner.saveAsThrown(itemStack);
            ((ServerPlayer)player).m_6915_();
        }
        return true;
    }

    public int startLayerScan(ItemStack itemStack) {
        return ElectricItem.manager.use(itemStack, 50.0, null) ? this.getScanRange() / 2 : 0;
    }

    public int getScanRange() {
        return 6;
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }

    @Override
    public IHasGui getInventory(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return new HandHeldScanner(player, interactionHand, itemStack);
    }

    private Map<ItemComparableItemStack, Integer> scan(Level level, BlockPos blockPos, int n) {
        HashMap<ItemComparableItemStack, Integer> hashMap = new HashMap<ItemComparableItemStack, Integer>();
        PathNavigationRegion pathNavigationRegion = new PathNavigationRegion(level, blockPos.m_7918_(-n, -n, -n), blockPos.m_7918_(n, n, n));
        Player player = Ic2Player.get(level);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = blockPos.m_123342_() - n; i <= blockPos.m_123342_() + n; ++i) {
            for (int j = blockPos.m_123343_() - n; j <= blockPos.m_123343_() + n; ++j) {
                for (int k = blockPos.m_123341_() - n; k <= blockPos.m_123341_() + n; ++k) {
                    List<ItemStack> list;
                    mutableBlockPos.m_122178_(k, i, j);
                    BlockState blockState = pathNavigationRegion.m_8055_((BlockPos)mutableBlockPos);
                    if (blockState.m_60795_()) continue;
                    ItemStack itemStack = StackUtil.getPickStack(level, (BlockPos)mutableBlockPos, blockState, player);
                    if (itemStack != null && OreValues.get(itemStack) > 0) {
                        list = Arrays.asList(itemStack);
                    } else {
                        list = StackUtil.getDrops((BlockGetter)pathNavigationRegion, (BlockPos)mutableBlockPos, blockState, 0);
                        if (list.isEmpty() || OreValues.get(list) <= 0) continue;
                    }
                    for (ItemStack itemStack2 : list) {
                        ItemComparableItemStack itemComparableItemStack = new ItemComparableItemStack(itemStack2, true);
                        Integer n2 = (Integer)hashMap.get(itemComparableItemStack);
                        if (n2 == null) {
                            n2 = 0;
                        }
                        n2 = n2 + StackUtil.getSize(itemStack2);
                        hashMap.put(itemComparableItemStack, n2);
                    }
                }
            }
        }
        return hashMap;
    }

    private List<Tuple.T2<ItemStack, Integer>> scanMapToSortedList(Map<ItemComparableItemStack, Integer> map) {
        ArrayList<Tuple.T2<ItemStack, Integer>> arrayList = new ArrayList<Tuple.T2<ItemStack, Integer>>(map.size());
        for (Map.Entry<ItemComparableItemStack, Integer> entry : map.entrySet()) {
            arrayList.add(new Tuple.T2<ItemStack, Integer>(entry.getKey().toStack(), entry.getValue()));
        }
        Collections.sort(arrayList, new Comparator<Tuple.T2<ItemStack, Integer>>(){

            @Override
            public int compare(Tuple.T2<ItemStack, Integer> t2, Tuple.T2<ItemStack, Integer> t22) {
                return (Integer)t22.b - (Integer)t2.b;
            }
        });
        return arrayList;
    }
}

