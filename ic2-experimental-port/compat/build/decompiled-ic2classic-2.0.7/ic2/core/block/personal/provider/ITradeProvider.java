/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.personal.provider;

import ic2.core.inventory.base.IHasInventory;
import java.util.UUID;

public interface ITradeProvider {
    public IHasInventory getTradeInput();

    public IHasInventory getTradeOutput();

    public UUID getTradeOwner();
}

