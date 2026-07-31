/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.utils.tooltips.helper;

import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.helper.BasicTextToolTip;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EUReaderTooltip
extends BasicTextToolTip {
    public EUReaderTooltip(String key, Object ... values) {
        super(key, values);
    }

    public EUReaderTooltip(Component comp) {
        super(comp);
    }

    public EUReaderTooltip(Supplier<Component> supplier) {
        super(supplier);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addInformation(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (PlayerHandler.getClientHandler().hasEUReader()) {
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    }
}

