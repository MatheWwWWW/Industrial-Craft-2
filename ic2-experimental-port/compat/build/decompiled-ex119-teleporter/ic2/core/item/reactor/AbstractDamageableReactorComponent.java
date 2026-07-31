/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.Localization;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class AbstractDamageableReactorComponent
extends Item
implements IReactorComponent {
    public static final String TOOLTIP_DURABILITY = "ic2.reactoritem.durability";
    private final int maxUse;

    protected AbstractDamageableReactorComponent(Item.Properties properties, int n) {
        super(properties);
        this.maxUse = n;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int n, int n2, boolean bl) {
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack2, int n, int n2, int n3, int n4, boolean bl) {
        return false;
    }

    @Override
    public boolean canStoreHeat(ItemStack itemStack, IReactor iReactor, int n, int n2) {
        return false;
    }

    @Override
    public int getMaxHeat(ItemStack itemStack, IReactor iReactor, int n, int n2) {
        return 0;
    }

    @Override
    public int getCurrentHeat(ItemStack itemStack, IReactor iReactor, int n, int n2) {
        return 0;
    }

    @Override
    public int alterHeat(ItemStack itemStack, IReactor iReactor, int n, int n2, int n3) {
        return n3;
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor iReactor) {
        return 0.0f;
    }

    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.m_7373_(itemStack, level, list, tooltipFlag);
        list.add((Component)Component.m_237113_((String)(Localization.translate(TOOLTIP_DURABILITY) + " " + (this.getMaxUse() - this.getUse(itemStack)) + "/" + this.getMaxUse())).m_130940_(ChatFormatting.GRAY));
    }

    @Override
    public boolean canBePlacedIn(ItemStack itemStack, IReactor iReactor) {
        return true;
    }

    protected int getUse(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        return compoundTag != null ? compoundTag.m_128451_("use") : 0;
    }

    public void setUse(ItemStack itemStack, int n) {
        itemStack.m_41784_().m_128405_("use", n);
    }

    protected void incrementUse(ItemStack itemStack) {
        itemStack.m_41784_().m_128405_("use", Math.min(this.getUse(itemStack) + 1, this.maxUse));
    }

    protected int getMaxUse() {
        return this.maxUse;
    }

    public double getUseFraction(ItemStack itemStack) {
        return Util.limit((double)this.getUse(itemStack) / (double)this.maxUse, 0.0, 1.0);
    }

    public boolean m_142522_(ItemStack itemStack) {
        return true;
    }

    public int m_142158_(ItemStack itemStack) {
        return (int)Math.round((1.0 - this.getUseFraction(itemStack)) * 13.0);
    }

    public int m_142159_(ItemStack itemStack) {
        return Mth.m_14169_((float)((float)((1.0 - this.getUseFraction(itemStack)) / 3.0)), (float)1.0f, (float)1.0f);
    }
}

