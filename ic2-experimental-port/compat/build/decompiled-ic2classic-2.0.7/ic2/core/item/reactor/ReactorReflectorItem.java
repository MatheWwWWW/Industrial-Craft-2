/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedReflector;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ReactorReflectorItem
extends ReactorComponentBase {
    private boolean isUnbreakable;
    private short componentId;

    public ReactorReflectorItem(String itemName, String textureFolder, String textureName, int maxDamage, boolean isUnBreakable, int componentId) {
        super(itemName, textureFolder, textureName, new PropertiesBuilder().maxDamage(maxDamage));
        this.isUnbreakable = isUnBreakable;
        this.componentId = (short)componentId;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack source, int myX, int myY, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        if (this.isUnbreakable && this.getDamage(stack) >= this.getMaxDamage(stack)) {
            return false;
        }
        if (reactor instanceof ISteamReactor) {
            if (damageTick) {
                if (this.getDamage(stack) >= this.getMaxDamage(stack)) {
                    reactor.setStackInReactor(myX, myY, ItemStack.f_41583_);
                } else {
                    this.setDamage(stack, this.getDamage(stack) + 1);
                }
            }
            return true;
        }
        if (!heatRun) {
            reactor.addOutput(1.0f);
        }
        if (damageTick) {
            if (this.getDamage(stack) >= this.getMaxDamage(stack)) {
                reactor.setStackInReactor(myX, myY, ItemStack.f_41583_);
            } else {
                this.setDamage(stack, this.getDamage(stack) + 1);
            }
        }
        return true;
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
        return -1.0f;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedReflector(this.componentId, this.getMaxDamage(self), this.isUnbreakable);
    }

    @Override
    public void addToolTip(ItemStack stack, Consumer<Component> tooltips) {
        int refilled = StackUtil.getNbtData(stack).m_128451_("refilled");
        if (refilled > 0) {
            tooltips.accept((Component)Component.m_237110_((String)"gui.ic2.reactor_planner.component.refills", (Object[])new Object[]{refilled}));
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
        return IReactorPlannerComponent.ComponentType.REFLECTORS;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY) {
            return IntTag.m_128679_((int)this.getMaxDamage(stack));
        }
        return NULL_VALUE;
    }
}

