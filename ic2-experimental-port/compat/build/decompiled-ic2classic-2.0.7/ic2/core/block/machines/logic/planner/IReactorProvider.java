/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.machines.logic.planner;

import ic2.core.block.machines.logic.planner.UserSettings;
import ic2.core.inventory.base.IHasInventory;

public interface IReactorProvider {
    public UserSettings getSettings();

    public int getReactorSize();

    public boolean isSteamReactor();

    public IHasInventory getComponents();

    public long getGameTime(long var1);

    public static IReactorProvider dummy(int size, boolean isSteam) {
        return new DummyProvider(size, isSteam);
    }

    public static class DummyProvider
    implements IReactorProvider {
        int size;
        boolean isSteam;

        public DummyProvider(int size, boolean isSteam) {
            this.size = size;
            this.isSteam = isSteam;
        }

        @Override
        public UserSettings getSettings() {
            return new UserSettings();
        }

        @Override
        public int getReactorSize() {
            return this.size;
        }

        @Override
        public boolean isSteamReactor() {
            return this.isSteam;
        }

        @Override
        public IHasInventory getComponents() {
            return null;
        }

        @Override
        public long getGameTime(long tick) {
            return tick;
        }
    }
}

