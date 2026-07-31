/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.api.item;

import ic2.api.item.IBackupElectricItemManager;
import ic2.api.item.IElectricItemManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class ElectricItem {
    public static IElectricItemManager manager;
    public static IElectricItemManager rawManager;
    private static final List<IBackupElectricItemManager> backupManagers;

    public static void registerBackupManager(IBackupElectricItemManager manager) {
        backupManagers.add(manager);
    }

    public static IBackupElectricItemManager getBackupManager(ItemStack stack) {
        for (IBackupElectricItemManager manager : backupManagers) {
            if (!manager.handles(stack)) continue;
            return manager;
        }
        return null;
    }

    static {
        backupManagers = new ArrayList<IBackupElectricItemManager>();
    }
}

