/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.utils.tooltips.helper;

import ic2.core.utils.tooltips.helper.ITooltipProvider;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BasicTextToolTip
implements ITooltipProvider {
    protected Component comp;
    protected Supplier<Component> supplier;

    public BasicTextToolTip(String key, Object ... values) {
        this.comp = this.translate(key, values).m_130940_(ChatFormatting.GRAY);
    }

    public BasicTextToolTip(Component comp) {
        this.comp = comp.m_6881_().m_130940_(ChatFormatting.GRAY);
    }

    public BasicTextToolTip(Supplier<Component> supplier) {
        this.supplier = supplier;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addInformation(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(this.supplier != null ? this.supplier.get() : this.comp);
    }
}

