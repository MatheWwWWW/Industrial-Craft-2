/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.api.tiles;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IInputMachine;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.ILocation;
import java.util.EnumSet;
import net.minecraft.core.Direction;
import net.minecraftforge.items.IItemHandler;

public interface IMachine
extends ILocation,
IInputMachine {
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes();

    public void onUpgradesChanged();

    public int getAvailableEnergy();

    public boolean useEnergy(int var1, boolean var2);

    public boolean isMachineWorking();

    public void setRedstoneSensitive(boolean var1);

    public boolean isRedstoneSensitive();

    public IItemHandler getConnectedInventory(Direction var1);

    public ITube getConnectedTube(Direction var1);
}

