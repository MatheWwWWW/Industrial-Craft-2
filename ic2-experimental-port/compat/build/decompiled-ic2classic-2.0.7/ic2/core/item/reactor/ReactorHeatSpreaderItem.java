/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.api.util.DirectionList;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedSpreader;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.core.Direction;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class ReactorHeatSpreaderItem
extends ReactorComponentBase {
    public ReactorHeatSpreaderItem(String itemName, String textureFolder, String textureName) {
        super(itemName, textureFolder, textureName, null);
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
        if (heatCalculation) {
            this.cool(reactor, x - 1, y);
            this.cool(reactor, x + 1, y);
            this.cool(reactor, x, y - 1);
            this.cool(reactor, x, y + 1);
        }
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
        return heatChange;
    }

    @Override
    public float getExplosionInfluence(ItemStack stack, IReactor reactor) {
        return 0.0f;
    }

    private void cool(IReactor reactor, int x, int y) {
        IReactorComponent comp;
        ItemStack stack = reactor.getStackInReactor(x, y);
        if (stack.m_41720_() instanceof IReactorComponent && (comp = (IReactorComponent)stack.m_41720_()).canStoreHeat(stack, reactor, x, y)) {
            comp.storeHeat(stack, reactor, x, y, -4);
        }
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedSpreader(26, 4);
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return 26;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.HEAT_SPREAD;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.PART_COOLING);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.PART_COOLING) {
            return IntTag.m_128679_((int)4);
        }
        return NULL_VALUE;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack, IReactor planner, int x, int y) {
        if (stat == IReactorPlannerComponent.ReactorStat.PART_COOLING) {
            int sides = 0;
            for (Direction direction : DirectionList.HORIZONTAL) {
                IReactorComponent comp;
                int newY;
                int newX = x + direction.m_122429_();
                ItemStack other = planner.getStackInReactor(newX, newY = y + direction.m_122431_());
                if (!(other.m_41720_() instanceof IReactorComponent) || !(comp = (IReactorComponent)other.m_41720_()).canStoreHeat(other, planner, x, y)) continue;
                ++sides;
            }
            return IntTag.m_128679_((int)(4 * sides));
        }
        return NULL_VALUE;
    }
}

