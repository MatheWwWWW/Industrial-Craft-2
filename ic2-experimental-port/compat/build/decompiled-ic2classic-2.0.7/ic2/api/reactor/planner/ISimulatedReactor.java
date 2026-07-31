/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.reactor.planner;

import ic2.api.reactor.planner.SimulatedStack;

public interface ISimulatedReactor {
    public SimulatedStack getItem(int var1, int var2);

    public void markBroken(int var1, int var2);

    public void addBreedingPulse();

    public void addFuelPulse();

    public int getHeat();

    public void setHeat(int var1);

    public void addHeat(int var1);

    public int getMaxHeat();

    public void setMaxHeat(int var1);

    public float getHeatEffectModifier();

    public void setHeatEffectModifier(float var1);

    public void addOutput(float var1);

    public float getEnergyOutput();

    public void addSteam(int var1);

    public int consumeWater(int var1);

    public boolean isProducingEnergy();

    public boolean isSteamReactor();

    public boolean isSimulatingPulses();

    public long getGameTime();
}

