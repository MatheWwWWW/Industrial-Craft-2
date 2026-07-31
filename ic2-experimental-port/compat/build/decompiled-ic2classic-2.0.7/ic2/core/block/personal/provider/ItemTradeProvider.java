/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.personal.provider;

import ic2.core.block.personal.provider.IItemTradeProvider;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.inv.SimpleInventory;
import java.util.UUID;

public class ItemTradeProvider
implements IItemTradeProvider {
    UUID id;
    IHasInventory input = new SimpleInventory(4);
    IHasInventory output = new SimpleInventory(8);

    public ItemTradeProvider(UUID id) {
        this.id = id;
    }

    @Override
    public IHasInventory getTradeOutput() {
        return this.output;
    }

    @Override
    public IHasInventory getTradeInput() {
        return this.input;
    }

    @Override
    public UUID getTradeOwner() {
        return this.id;
    }
}

