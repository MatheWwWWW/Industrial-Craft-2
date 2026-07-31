/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.base.features;

import ic2.api.tiles.IMachine;
import ic2.core.inventory.base.IHasInventory;

public interface IInventoryMachine
extends IMachine {
    public IHasInventory getInputInventory();

    public IHasInventory getOutputInventory();
}

