/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedHeatPack;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class ReactorHeatPackItem
extends ReactorComponentBase {
    public ReactorHeatPackItem(String itemName, String textureFolder, String textureName) {
        super(itemName, textureFolder, textureName, new PropertiesBuilder().setRepair().maxStackSize(64));
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
        if (reactor instanceof ISteamReactor) {
            return;
        }
        int want = 1000 * stack.m_41613_();
        if (reactor.getHeat() >= want) {
            return;
        }
        reactor.addHeat(stack.m_41613_());
    }

    @Override
    public boolean isValidForReactor(ItemStack stack, IReactor reactor) {
        return !(reactor instanceof ISteamReactor);
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
        return (float)stack.m_41613_() / 10.0f;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedHeatPack(17, self.m_41613_());
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return 17;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.ELECTRIC;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.HEAT_PACK;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.HEAT_PRODUCTION);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.HEAT_PRODUCTION) {
            return IntTag.m_128679_((int)stack.m_41613_());
        }
        return NULL_VALUE;
    }
}

