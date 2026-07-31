/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  mcjty.theoneprobe.api.Color
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.probeplugin.info.transport;

import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.tubes.RequestTubeTileEntity;
import ic2.core.block.transport.item.tubes.StackingTubeTileEntity;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import mcjty.theoneprobe.api.Color;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TubeProvider
implements ITileInfoComponent<TubeTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, TubeTileEntity tile) {
        NonNullList list;
        Object item;
        Panel panel = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        Panel inner = new Panel(IC2Styles.SLOTS_STYLE.copy().borderColor((Color)null), Panel.Type.VERTICAL);
        Object2ObjectSortedMap<MutableComponent, NonNullList> slots = CollectionUtils.createLinkedMap();
        if (tile.items.size() > 0) {
            Object2IntLinkedOpenCustomHashMap mapped = new Object2IntLinkedOpenCustomHashMap((Hash.Strategy)ItemStackStrategy.INSTANCE);
            int m = tile.items.size();
            for (int i = 0; i < m; ++i) {
                item = (TransportedItem)tile.items.get(i);
                mapped.addTo((Object)StackUtil.copyWithSize(((TransportedItem)item).getServerStack(), 1), ((TransportedItem)item).getServerStack().m_41613_());
            }
            list = NonNullList.m_122779_();
            mapped.forEach((K, V) -> list.add((Object)StackUtil.copyWithSize(K, V)));
            slots.put(this.translate("ic2.probe.tube.transported"), list);
        }
        if (tile instanceof StackingTubeTileEntity) {
            StackingTubeTileEntity stackingTile = (StackingTubeTileEntity)tile;
            if (!stackingTile.cached.isEmpty()) {
                NonNullList cached = NonNullList.m_122779_();
                ObjectListIterator objectListIterator = stackingTile.cached.iterator();
                while (objectListIterator.hasNext()) {
                    item = (StackingTubeTileEntity.StackingStack)objectListIterator.next();
                    cached.add((Object)StackUtil.copyWithSize(((StackingTubeTileEntity.StackingStack)item).getStack(), ((StackingTubeTileEntity.StackingStack)item).getAmount()));
                }
                slots.put(this.translate("ic2.probe.tube.cached"), cached);
            }
        }
        if (tile instanceof RequestTubeTileEntity) {
            RequestTubeTileEntity request = (RequestTubeTileEntity)tile;
            list = NonNullList.m_122779_();
            ObjectListIterator objectListIterator = request.filters.iterator();
            while (objectListIterator.hasNext()) {
                RequestTubeTileEntity.RequestEntry entry = (RequestTubeTileEntity.RequestEntry)objectListIterator.next();
                list.add((Object)StackUtil.copyWithSize(entry.getStack(), entry.getAmount()));
            }
            if (!list.isEmpty()) {
                slots.put(this.translate("ic2.probe.tube.requests"), list);
            }
            list = NonNullList.m_122779_();
            request.getMissing((NonNullList<ItemStack>)list);
            if (!list.isEmpty()) {
                slots.put(this.translate("ic2.probe.tube.missing"), list);
            }
            list = NonNullList.m_122779_();
            for (RequestTubeTileEntity.RequestEntry entry : Object2IntMaps.fastIterable(request.requested)) {
                list.add((Object)StackUtil.copyWithSize((ItemStack)entry.getKey(), entry.getIntValue()));
            }
            if (!list.isEmpty()) {
                slots.put(this.translate("ic2.probe.tube.requesting"), list);
            }
            if (request.toInsert.size() > 0) {
                list = NonNullList.m_122779_();
                for (ItemStack stack : request.toInsert) {
                    list.add((Object)stack.m_41777_());
                }
                slots.put(this.translate("ic2.probe.tube.stuck"), list);
            }
        }
        ProbePluginHelper.generateSlotInfo(slots, inner);
        panel.element(inner);
        info.getElements().add(1, panel);
    }
}

