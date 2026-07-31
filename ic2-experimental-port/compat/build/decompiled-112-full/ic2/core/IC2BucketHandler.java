/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraftforge.event.entity.player.FillBucketEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ic2.core;

import ic2.core.block.BlockIC2Fluid;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IC2BucketHandler {
    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        Block block;
        if (event.getTarget() != null && (block = event.getWorld().func_180495_p(event.getTarget().func_178782_a()).func_177230_c()) instanceof BlockIC2Fluid && event.isCancelable()) {
            event.setCanceled(true);
        }
    }
}

