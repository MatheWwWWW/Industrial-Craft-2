/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.item.misc;

import ic2.api.items.IFuelableItem;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class FuelCanItem
extends IC2Item
implements IFuelableItem,
IItemModel {
    public FuelCanItem() {
        super("fuel_can", new PropertiesBuilder().maxStackSize(1));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add((Component)this.translate("tooltip.item.ic2.fuel_can.release", new ChatFormatting[]{ChatFormatting.GRAY}));
        if (this.hasFuel(stack)) {
            CompoundTag nbt = StackUtil.getNbtData(stack);
            tooltip.add((Component)this.translate("tooltip.item.ic2.fuel_can.stored", ChatFormatting.GRAY, new Object[]{nbt.m_128451_("fuel"), nbt.m_128451_("max_fuel")}));
        }
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        items.add((Object)new ItemStack((ItemLike)this));
        items.add((Object)this.fill(new ItemStack((ItemLike)this), 10000));
        items.add((Object)this.fill(new ItemStack((ItemLike)this), 1000000));
    }

    @Override
    public ItemStack fill(ItemStack stack, int amount) {
        ItemStack newStack = stack.m_41777_();
        CompoundTag nbt = newStack.m_41784_();
        nbt.m_128405_("fuel", amount += Math.max(0, nbt.m_128451_("fuel")));
        nbt.m_128405_("max_fuel", amount);
        return newStack;
    }

    @Override
    public boolean canFuel(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("fuel") == 0;
    }

    @Override
    public boolean hasFuel(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("fuel") > 0;
    }

    @Override
    public int getFuel(ItemStack stack, int requested, boolean doDrain) {
        CompoundTag nbt = stack.m_41784_();
        int stored = nbt.m_128451_("fuel");
        int removed = Math.min(requested, stored);
        if (doDrain) {
            nbt.m_128405_("fuel", stored - removed);
            if (stored - removed <= 0) {
                nbt.m_128473_("fuel");
                nbt.m_128473_("max_fuel");
                if (nbt.m_128456_()) {
                    stack.m_41751_(null);
                }
            }
        }
        return removed;
    }

    public boolean m_142522_(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("fuel") > 0;
    }

    public int m_142159_(ItemStack stack) {
        float f = (float)this.m_142158_(stack) / 13.0f;
        return Mth.m_14169_((float)(f / 3.0f), (float)1.0f, (float)1.0f);
    }

    public int m_142158_(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        return (int)Math.round(nbt.m_128459_("fuel") / nbt.m_128459_("max_fuel") * 13.0);
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return StackUtil.getNbtData(stack).m_128451_("fuel") > 0 ? 1 : 0;
    }

    @Override
    public List<ItemStack> getModelTypes() {
        ObjectList list = CollectionUtils.createList();
        list.add((ItemStack)new ItemStack((ItemLike)this));
        ItemStack stack = new ItemStack((ItemLike)this);
        stack.m_41784_().m_128405_("fuel", 1);
        return list;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        return IC2Textures.getMappedEntriesItemIC2("tools").get(StackUtil.getNbtData(input).m_128451_("fuel") > 0 ? "filled_fuel_can" : "empty_fuel_can");
    }

    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return Math.min(StackUtil.getNbtData(itemStack).m_128451_("fuel"), 500);
    }

    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        itemStack = itemStack.m_41777_();
        CompoundTag nbt = itemStack.m_41784_();
        nbt.m_128405_("fuel", Math.max(0, nbt.m_128451_("fuel") - 500));
        return itemStack;
    }
}

