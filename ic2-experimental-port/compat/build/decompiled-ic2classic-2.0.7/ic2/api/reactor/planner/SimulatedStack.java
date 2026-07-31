/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.reactor.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.ISimulatedReactor;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public interface SimulatedStack {
    public static final NumericTag NULL_VALUE = IntTag.m_128679_((int)0);

    public ItemStack syncStack(ItemStack var1);

    public CompoundTag save();

    public void load(CompoundTag var1);

    public void commitState();

    public void reset();

    public void simulate(ISimulatedReactor var1, int var2, int var3, boolean var4, boolean var5);

    public boolean acceptUraniumPulse(ISimulatedReactor var1, int var2, int var3, SimulatedStack var4, int var5, int var6, boolean var7, boolean var8);

    public boolean canStoreHeat(ISimulatedReactor var1, int var2, int var3);

    public int getStoredHeat(ISimulatedReactor var1, int var2, int var3);

    public int getMaxStoredHeat(ISimulatedReactor var1, int var2, int var3);

    public int storeHeat(ISimulatedReactor var1, int var2, int var3, int var4);

    public boolean canViewHeat(ISimulatedReactor var1, int var2, int var3);

    public float getExplosionInfluence(ISimulatedReactor var1);

    public short getId();

    public List<IReactorPlannerComponent.ReactorStat> getStats();

    public IReactorPlannerComponent.ReactorType getValidType();

    public IReactorPlannerComponent.ComponentType getComponentType();

    public NumericTag getStat(IReactorPlannerComponent.ReactorStat var1);

    default public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat, ISimulatedReactor reactor, int x, int y) {
        return this.getStat(stat);
    }
}

