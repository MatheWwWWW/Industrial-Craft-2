/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.items.IRepairable;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedCondensator;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ReactorCondensatorItem
extends ReactorComponentBase
implements IRepairable {
    private short componentId;

    public ReactorCondensatorItem(String itemName, int maxHeat, String textureFolder, String textureName, int componentId) {
        super(itemName, textureFolder, textureName, new PropertiesBuilder().maxDamage(maxHeat));
        this.componentId = (short)componentId;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack source, int myX, int myY, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        return false;
    }

    @Override
    public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return this.getDamage(stack) + 1 < this.getMaxDamage(stack);
    }

    @Override
    public int getStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int getMaxStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return this.getMaxDamage(stack);
    }

    @Override
    public int storeHeat(ItemStack stack, IReactor reactor, int x, int y, int heatChange) {
        int can = this.getMaxDamage(stack) - (this.getDamage(stack) + 1);
        if (can > heatChange) {
            can = heatChange;
        }
        this.setDamage(stack, this.getDamage(stack) + can);
        return heatChange - can;
    }

    @Override
    public float getExplosionInfluence(ItemStack stack, IReactor reactor) {
        return 0.0f;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedCondensator(this.componentId, this.getMaxDamage(self));
    }

    @Override
    public void addToolTip(ItemStack stack, Consumer<Component> tooltips) {
        CompoundTag tag = StackUtil.getNbtData(stack);
        if (tag.m_128451_("total") > 0) {
            tooltips.accept((Component)Component.m_237110_((String)"gui.ic2.reactor_planner.component.condensator.total", (Object[])new Object[]{tag.m_128451_("total")}));
            tooltips.accept((Component)Component.m_237110_((String)"gui.ic2.reactor_planner.component.condensator.average", (Object[])new Object[]{Float.valueOf(tag.m_128457_("average"))}));
            tooltips.accept((Component)Component.m_237110_((String)"gui.ic2.reactor_planner.component.condensator.reset", (Object[])new Object[]{tag.m_128451_("reset")}));
        }
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return this.componentId;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.CONDENSATOR;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.HEAT_STORAGE);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.HEAT_STORAGE) {
            return IntTag.m_128679_((int)this.getMaxDamage(stack));
        }
        return NULL_VALUE;
    }

    @Override
    public boolean repairDamage(ItemStack stack, int amount) {
        if (stack.m_41773_() > 0) {
            stack.m_41721_(Math.max(0, stack.m_41773_() - amount));
            return true;
        }
        return false;
    }
}

