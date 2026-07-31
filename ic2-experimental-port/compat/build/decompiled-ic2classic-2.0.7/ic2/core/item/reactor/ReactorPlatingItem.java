/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedReactorPlating;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class ReactorPlatingItem
extends ReactorComponentBase {
    private int heatModifier;
    private float effectModifier;
    private short componentId;

    public ReactorPlatingItem(String itemName, String textureFolder, String textureName, int heatModifier, float effectModifier, int componentId) {
        super(itemName, textureFolder, textureName, null);
        this.heatModifier = heatModifier;
        this.effectModifier = effectModifier;
        this.componentId = (short)componentId;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
        if (heatCalculation) {
            reactor.setMaxHeat(reactor.getMaxHeat() + this.heatModifier);
            reactor.setHeatEffectModifier(reactor.getHeatEffectModifier() * this.effectModifier);
        }
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
        if (this.effectModifier >= 1.0f) {
            return 0.0f;
        }
        return this.effectModifier;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedReactorPlating(this.componentId, this.heatModifier, this.effectModifier);
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
        return IReactorPlannerComponent.ComponentType.PLATING;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.MAX_HEAT_STORAGE);
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.REACTOR_HEAT_EFFECT_MULTIPLIER);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.MAX_HEAT_STORAGE) {
            return IntTag.m_128679_((int)this.heatModifier);
        }
        if (stat == IReactorPlannerComponent.ReactorStat.REACTOR_HEAT_EFFECT_MULTIPLIER) {
            return FloatTag.m_128566_((float)this.effectModifier);
        }
        return NULL_VALUE;
    }
}

