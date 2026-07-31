/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.inventory.handler;

import ic2.core.inventory.handler.InventoryHandler;

public interface IHasInventoryHandler {
    public InventoryHandler getInventoryHandler();

    public boolean allowsUI();
}

