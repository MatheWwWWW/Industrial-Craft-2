/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.logging.log4j.util.Strings
 */
package ic2.core.item.misc;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropSeed;
import ic2.core.block.base.misc.color.IItemColorListener;
import ic2.core.block.crops.CropRegistry;
import ic2.core.item.base.IC2Item;
import ic2.core.item.renders.models.CropItemModel;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.features.item.ICustomItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.OptionalLong;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.util.Strings;

public class CropSeedItem
extends IC2Item
implements ICropSeed,
ICustomItemModel,
IItemColorListener {
    public static final String BREED_RESULT = "breed";

    public CropSeedItem() {
        super("crop_seed");
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        int[] result;
        int level = this.getScanLevel(stack);
        if (level >= 4) {
            tooltip.add((Component)this.translate("gui.ic2.crop_analyzer.growth", new ChatFormatting[]{ChatFormatting.DARK_GREEN}).m_7220_((Component)this.string(" " + this.getGrowth(stack), new ChatFormatting[]{ChatFormatting.WHITE})));
            tooltip.add((Component)this.translate("gui.ic2.crop_analyzer.gain", new ChatFormatting[]{ChatFormatting.GOLD}).m_7220_((Component)this.string(" " + this.getGain(stack), new ChatFormatting[]{ChatFormatting.WHITE})));
            tooltip.add((Component)this.translate("gui.ic2.crop_analyzer.resistance", new ChatFormatting[]{ChatFormatting.DARK_AQUA}).m_7220_((Component)this.string(" " + this.getResistance(stack), new ChatFormatting[]{ChatFormatting.WHITE})));
        }
        if (level > 0 && !CropSeedItem.isVisible(stack) && !Screen.m_96637_()) {
            tooltip.add((Component)this.translate("tooltip.item.ic2.crop.item", new ChatFormatting[]{ChatFormatting.GRAY}));
        }
        if ((result = StackUtil.getNbtData(stack).m_128465_(BREED_RESULT)).length == 5) {
            double total = result[0];
            tooltip.add((Component)this.translate("info.crop.ic2.data.points", new Object[]{result[1]}));
            tooltip.add((Component)this.translate("info.crop.ic2.data.points.props", new Object[]{result[2]}));
            tooltip.add((Component)this.translate("info.crop.ic2.data.points.attr", new Object[]{result[3]}));
            tooltip.add((Component)this.translate("info.crop.ic2.data.points.tier", new Object[]{result[4]}));
            tooltip.add((Component)this.translate("info.crop.ic2.data.chance", new Object[]{ItemStack.f_41584_.format((double)result[1] / total * 100.0)}));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getItemColor(ItemStack stack, int tintIndex) {
        ItemStack entry = CropSeedItem.getDisplayItem(stack);
        return entry.m_41619_() ? -1 : Minecraft.m_91087_().getItemColors().m_92676_(entry, tintIndex);
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        return 0;
    }

    public Component m_7626_(ItemStack stack) {
        CompoundTag tag = StackUtil.getNbtData(stack);
        if ("ic2:random".equals(tag.m_128461_("crop"))) {
            return ((MutableComponent)super.m_7626_(stack)).m_130946_(": Random Crop");
        }
        ICrop crop = this.getCrop(stack);
        return ((MutableComponent)super.m_7626_(stack)).m_130946_(": ").m_7220_((Component)(crop == null || this.getScanLevel(stack) < 1 ? this.translate("info.crop.ic2.data.unknown") : crop.getName()));
    }

    @Override
    public List<ItemStack> getCustomTypes() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getModel(ItemStack stack) {
        return new CropItemModel();
    }

    @Override
    public ICrop getCrop(ItemStack stack) {
        CompoundTag tag = StackUtil.getNbtData(stack);
        String id = tag.m_128461_("crop");
        if (Strings.isEmpty((CharSequence)id)) {
            return null;
        }
        if (id.equalsIgnoreCase("ic2:random")) {
            return ICropRegistry.INSTANCE.getRandomCrop(tag.m_128441_("seed") ? OptionalLong.of(tag.m_128454_("seed")) : OptionalLong.empty());
        }
        return ICropRegistry.INSTANCE.getCrop(new ResourceLocation(id));
    }

    @Override
    public int getScanLevel(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("scan");
    }

    @Override
    public void setScanLevel(ItemStack stack, int scan) {
        stack.m_41784_().m_128344_("scan", (byte)scan);
    }

    @Override
    public int getGrowth(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("growth");
    }

    @Override
    public void setGrowth(ItemStack stack, int growth) {
        stack.m_41784_().m_128344_("growth", (byte)growth);
    }

    @Override
    public int getGain(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("gain");
    }

    @Override
    public void setGain(ItemStack stack, int gain) {
        stack.m_41784_().m_128344_("gain", (byte)gain);
    }

    @Override
    public int getResistance(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("resistance");
    }

    @Override
    public void setResistance(ItemStack stack, int resistance) {
        stack.m_41784_().m_128344_("resistance", (byte)resistance);
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        ItemStack stack;
        if (!this.m_220152_(group)) {
            return;
        }
        for (ICrop crop : ICropRegistry.INSTANCE.getCrops()) {
            if (crop == ICropRegistry.WEED || crop == CropRegistry.SEA_WEED || (stack = CropSeedItem.createStack(crop, 1, 1, 1, 4)).m_41619_()) continue;
            items.add((Object)stack);
        }
        items.add((Object)CropSeedItem.createRandomSeed(OptionalLong.empty(), 4));
        if (group == CreativeModeTab.f_40754_) {
            for (ICrop crop : ICropRegistry.INSTANCE.getCrops()) {
                if (crop == ICropRegistry.WEED || crop == CropRegistry.SEA_WEED || (stack = CropSeedItem.createStack(crop, 1, 1, 1, 0)).m_41619_()) continue;
                items.add((Object)stack);
            }
            items.add((Object)CropSeedItem.createRandomSeed(OptionalLong.empty(), 0));
        }
    }

    public static void setVisible(ItemStack stack, boolean visible) {
        stack.m_41784_().m_128379_("visible", visible);
    }

    public static boolean isVisible(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128471_("visible");
    }

    public static ItemStack createStack(ICrop crop, int growth, int gain, int resistance, int scan) {
        if (crop == null) {
            return ItemStack.f_41583_;
        }
        ItemStack stack = new ItemStack((ItemLike)IC2Items.CROP_SEED);
        CompoundTag nbt = stack.m_41784_();
        nbt.m_128359_("crop", crop.id().toString());
        nbt.m_128344_("growth", (byte)growth);
        nbt.m_128344_("gain", (byte)gain);
        nbt.m_128344_("resistance", (byte)resistance);
        nbt.m_128344_("scan", (byte)scan);
        return stack;
    }

    public static ItemStack createRandomSeed(OptionalLong seed, int scan) {
        ItemStack stack = new ItemStack((ItemLike)IC2Items.CROP_SEED);
        CompoundTag nbt = stack.m_41784_();
        nbt.m_128359_("crop", "ic2:random");
        seed.ifPresent(T -> nbt.m_128356_("seed", T));
        nbt.m_128344_("scan", (byte)scan);
        return stack;
    }

    @OnlyIn(value=Dist.CLIENT)
    public static ItemStack getDisplayItem(ItemStack base) {
        if (!Screen.m_96637_() && !CropSeedItem.isVisible(base) || !(base.m_41720_() instanceof CropSeedItem)) {
            return ItemStack.f_41583_;
        }
        CropSeedItem item = (CropSeedItem)base.m_41720_();
        if (item.getScanLevel(base) < 1 && !CropSeedItem.isVisible(base)) {
            return ItemStack.f_41583_;
        }
        ICrop crop = item.getCrop(base);
        return crop == null ? ItemStack.f_41583_ : ICropRegistry.INSTANCE.getDisplayItem(crop.id());
    }
}

