/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
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
import net.minecraft.item.ItemStack;

public class InvSlotScannable
extends InvSlotConsumable {
    public InvSlotScannable(IInventorySlotHolder<?> base1, String name1, int count) {
        super(base1, name1, count);
        this.setStackSizeLimit(1);
    }

    @Override
    public boolean accepts(ItemStack stack) {
        if (IC2.platform.isSimulating()) {
            return InvSlotScannable.isValidStack(stack);
        }
        Rpc rpc = RpcHandler.run(ServerScannableCheck.class, stack);
        try {
            return (Boolean)rpc.get(1L, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            IC2.log.debug(LogCategory.Block, e, "Scannability check failed.");
            return false;
        }
    }

    private static boolean isValidStack(ItemStack stack) {
        return !StackUtil.isEmpty(stack = UuGraph.find(stack)) && UuIndex.instance.get(stack) < Double.POSITIVE_INFINITY;
    }

    static {
        RpcHandler.registerProvider(new ServerScannableCheck());
    }

    public static class ServerScannableCheck
    implements IRpcProvider<Boolean> {
        @Override
        public Boolean executeRpc(Object ... args) {
            ItemStack stack = (ItemStack)args[0];
            return InvSlotScannable.isValidStack(stack);
        }
    }
}

