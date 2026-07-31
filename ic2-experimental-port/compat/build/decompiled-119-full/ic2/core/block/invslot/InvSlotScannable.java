/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.IC2;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.network.IRpcProvider;
import ic2.core.network.Rpc;
import ic2.core.network.RpcHandler;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.uu.UuGraph;
import ic2.core.uu.UuIndex;
import java.util.concurrent.TimeUnit;
import net.minecraft.world.item.ItemStack;

public class InvSlotScannable
extends InvSlotConsumable {
    public InvSlotScannable(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n) {
        super(iInventorySlotHolder, string, n);
        this.setStackSizeLimit(1);
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        if (IC2.sideProxy.isSimulating()) {
            return InvSlotScannable.isValidStack(itemStack);
        }
        Rpc rpc = RpcHandler.run(ServerScannableCheck.class, itemStack);
        try {
            return (Boolean)rpc.get(1L, TimeUnit.SECONDS);
        }
        catch (Exception exception) {
            IC2.log.debug(LogCategory.Block, exception, "Scannability check failed.");
            return false;
        }
    }

    private static boolean isValidStack(ItemStack itemStack) {
        return !StackUtil.isEmpty(itemStack = UuGraph.find(itemStack)) && UuIndex.instance.get(itemStack) < Double.POSITIVE_INFINITY;
    }

    static {
        RpcHandler.registerProvider(new ServerScannableCheck());
    }

    public static class ServerScannableCheck
    implements IRpcProvider<Boolean> {
        @Override
        public Boolean executeRpc(Object ... objectArray) {
            ItemStack itemStack = (ItemStack)objectArray[0];
            return InvSlotScannable.isValidStack(itemStack);
        }
    }
}

