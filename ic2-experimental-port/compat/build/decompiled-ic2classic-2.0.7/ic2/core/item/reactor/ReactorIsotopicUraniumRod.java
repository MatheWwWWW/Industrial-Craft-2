/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.IUraniumRod;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedIsotopicCell;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ReactorIsotopicUraniumRod
extends ReactorComponentBase {
    private IUraniumRod uranium;
    private short componentId;

    public ReactorIsotopicUraniumRod(String itemName, String textureFolder, String textureName, IUraniumRod uranium, int componentId) {
        super(itemName, textureFolder, textureName, new PropertiesBuilder().maxDamage(uranium.getRodDurability()));
        this.uranium = uranium;
        this.componentId = (short)componentId;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack source, int myX, int myY, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        if (reactor instanceof ISteamReactor) {
            if (damageTick) {
                int level = this.getDamage(stack) - 1 - reactor.getHeat() / 3000;
                if (level <= 0) {
                    reactor.setStackInReactor(myX, myY, this.uranium.createReEnrichedRod());
                } else {
                    this.setDamage(stack, level);
                }
            }
            return true;
        }
        int level = this.getDamage(stack) - 1 - reactor.getHeat() / 3000;
        if (level <= 0) {
            reactor.setStackInReactor(myX, myY, this.uranium.createReEnrichedRod());
        } else {
            this.setDamage(stack, level);
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
        return 0.0f;
    }

    @Override
    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        items.add((Object)this.uranium.createIsotopicRod());
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedIsotopicCell(this.componentId, this.getMaxDamage(self), 3000);
    }

    @Override
    public void addToolTip(ItemStack stack, Consumer<Component> tooltips) {
        int value = StackUtil.getNbtData(stack).m_128451_("breed");
        if (value > 0) {
            tooltips.accept((Component)this.translate("gui.ic2.reactor_planner.component.breed", new Object[]{value}));
        }
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return this.componentId;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.ELECTRIC;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.ISOTOPE_CELL;
    }

    @Override
    public void provideComponents(NonNullList<ItemStack> list) {
        ItemStack stack = new ItemStack((ItemLike)this);
        stack.m_41721_(stack.m_41776_());
        list.add((Object)stack);
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
            return IntTag.m_128679_((int)stack.m_41776_());
        }
        return NULL_VALUE;
    }
}

