/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.reactor;

import ic2.api.network.item.INetworkItemEvent;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.inv.container.HeatPumpContainer;
import ic2.core.item.inv.inventory.HeatPumpInventory;
import ic2.core.item.reactor.base.ItemStackCoord;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedHeatPump;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReactorHeatPumpItem
extends ReactorComponentBase
implements INetworkItemEvent,
IHasHeldSlotInventory,
IItemModel {
    public static final String DIRECTIONS = "directions";
    protected int transferRate;
    protected short compId;

    public ReactorHeatPumpItem(String itemName, String textureFolder, String textureName, int transferRate, int compId) {
        super(itemName, textureFolder, textureName, null);
        this.transferRate = transferRate;
        this.compId = (short)compId;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        int dirs = this.getDirection(stack);
        helper.addDataTooltip(this.translate("misc.ic2.side.up").m_130946_(": ").m_7220_((Component)this.translate((dirs & 1) != 0 ? "tooltip.ic2.heat_pump.pull" : "tooltip.ic2.heat_pump.push")));
        helper.addDataTooltip(this.translate("misc.ic2.side.down").m_130946_(": ").m_7220_((Component)this.translate((dirs & 2) != 0 ? "tooltip.ic2.heat_pump.pull" : "tooltip.ic2.heat_pump.push")));
        helper.addDataTooltip(this.translate("misc.ic2.side.left").m_130946_(": ").m_7220_((Component)this.translate((dirs & 4) != 0 ? "tooltip.ic2.heat_pump.pull" : "tooltip.ic2.heat_pump.push")));
        helper.addDataTooltip(this.translate("misc.ic2.side.right").m_130946_(": ").m_7220_((Component)this.translate((dirs & 8) != 0 ? "tooltip.ic2.heat_pump.pull" : "tooltip.ic2.heat_pump.push")));
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return this.getDirection(stack);
    }

    @Override
    public List<ItemStack> getModelTypes() {
        ObjectList item = CollectionUtils.createList();
        for (int i = 1; i < 16; ++i) {
            ItemStack stack = new ItemStack((ItemLike)this);
            stack.m_41700_(DIRECTIONS, (Tag)IntTag.m_128679_((int)i));
            item.add((ItemStack)stack);
        }
        return item;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.getTextureFolder()).get(this.getTextureName() + "_" + this.getDirection(input));
    }

    @Override
    public ItemStack applyStackSize(ItemStack newStack, int size) {
        newStack.m_41700_(DIRECTIONS, (Tag)IntTag.m_128679_((int)(size - 1)));
        return newStack;
    }

    @Override
    public int getStackSize(ItemStack input) {
        return this.getDirection(input) + 1;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (IC2.PLATFORM.isSimulating()) {
            IC2.PLATFORM.launchGui(playerIn, handIn, null, this.getInventory(playerIn, handIn, stack));
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    private int getDirection(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_(DIRECTIONS);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer() && player.f_36096_ instanceof HeatPumpContainer) {
            ((HeatPumpInventory)((HeatPumpContainer)player.f_36096_).getHolder()).onDataReceived(key, value);
        }
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new HeatPumpInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new HeatPumpInventory(player, this, stack, slot).load(stack);
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
        int dirs = this.getDirection(stack);
        if (dirs == 0 || dirs == 15) {
            return;
        }
        ObjectArrayList toSend = new ObjectArrayList();
        ObjectArrayList toPull = new ObjectArrayList();
        int heatToSend = 0;
        heatToSend += this.checkHeatAcceptor(reactor, x, y - 1, (dirs & 1) != 0, (List<ItemStackCoord>)toSend, (List<ItemStackCoord>)toPull);
        heatToSend += this.checkHeatAcceptor(reactor, x, y + 1, (dirs & 2) != 0, (List<ItemStackCoord>)toSend, (List<ItemStackCoord>)toPull);
        heatToSend += this.checkHeatAcceptor(reactor, x - 1, y, (dirs & 4) != 0, (List<ItemStackCoord>)toSend, (List<ItemStackCoord>)toPull);
        if (toPull.isEmpty() || toSend.isEmpty() || (heatToSend += this.checkHeatAcceptor(reactor, x + 1, y, (dirs & 8) != 0, (List<ItemStackCoord>)toSend, (List<ItemStackCoord>)toPull)) <= 0) {
            return;
        }
        int toAdd = heatToSend / toSend.size();
        heatToSend = 0;
        for (ItemStackCoord coord : toSend) {
            heatToSend += toAdd - coord.storeHeat((IReactorComponent)coord.stack.m_41720_(), reactor, toAdd);
        }
        toAdd = heatToSend / toPull.size();
        for (ItemStackCoord coord : toPull) {
            heatToSend -= toAdd - coord.storeHeat((IReactorComponent)coord.stack.m_41720_(), reactor, -toAdd);
        }
        if (heatToSend > 0) {
            ItemStackCoord coord;
            for (int i = 0; heatToSend > 0 && i < 8; heatToSend -= 1 - coord.storeHeat((IReactorComponent)coord.stack.m_41720_(), reactor, -1), ++i) {
                coord = (ItemStackCoord)toPull.get(i % toPull.size());
            }
        }
    }

    private int checkHeatAcceptor(IReactor reactor, int x, int y, boolean provide, List<ItemStackCoord> heatAcceptors, List<ItemStackCoord> heatProviders) {
        IReactorComponent comp;
        ItemStack thing = reactor.getStackInReactor(x, y);
        if (thing.m_41720_() instanceof IReactorComponent && (comp = (IReactorComponent)thing.m_41720_()).canStoreHeat(thing, reactor, x, y)) {
            if (provide) {
                int heat = Math.min(comp.getStoredHeat(thing, reactor, x, y), this.transferRate);
                if (heat > 0) {
                    heatProviders.add(new ItemStackCoord(thing, x, y));
                }
                return heat;
            }
            heatAcceptors.add(new ItemStackCoord(thing, x, y));
        }
        return 0;
    }

    @Override
    public void addAffectedSlots(int x, int y, BiPredicate<Integer, Integer> slots) {
        slots.test(x, y);
        slots.test(x + 1, y);
        slots.test(x - 1, y);
        slots.test(x, y + 1);
        slots.test(x, y - 1);
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack source, int myX, int myY, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        return false;
    }

    @Override
    public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public int getStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int getMaxStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int storeHeat(ItemStack stack, IReactor reactor, int x, int y, int heatChange) {
        return 0;
    }

    @Override
    public float getExplosionInfluence(ItemStack stack, IReactor reactor) {
        return 0.0f;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedHeatPump(this.compId, this.transferRate, this.getDirection(self));
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return this.compId;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.HEAT_PUMP;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList stats = CollectionUtils.createList();
        stats.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.PART_BALANCING);
        return stats;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.PART_BALANCING) {
            return IntTag.m_128679_((int)this.transferRate);
        }
        return NULL_VALUE;
    }
}

