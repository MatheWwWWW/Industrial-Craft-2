/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import ic2.api.item.IBackupElectricItemManager;
import ic2.api.item.IElectricItemManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public final class ElectricItem {
    public static IElectricItemManager manager;
    public static IElectricItemManager rawManager;
    private static final List<IBackupElectricItemManager> backupManagers;

    public static void registerBackupManager(IBackupElectricItemManager iBackupElectricItemManager) {
        backupManagers.add(iBackupElectricItemManager);
    }

    public static IBackupElectricItemManager getBackupManager(ItemStack itemStack) {
        for (IBackupElectricItemManager iBackupElectricItemManager : backupManagers) {
            if (!iBackupElectricItemManager.handles(itemStack)) continue;
            return iBackupElectricItemManager;
        }
        return null;
    }

    static {
        backupManagers = new ArrayList<IBackupElectricItemManager>();
    }
}

