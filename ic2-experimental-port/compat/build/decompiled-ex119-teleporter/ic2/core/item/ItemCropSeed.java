/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropSeed;
import ic2.core.crop.TileEntityCrop;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ItemCropSeed
extends Item
implements ICropSeed {
    public ItemCropSeed(Item.Properties properties) {
        super(properties);
    }

    public String m_5671_(ItemStack itemStack) {
        if (itemStack == null) {
            return "ic2.crop.unknown";
        }
        CropCard cropCard = Crops.instance.getCropCard(itemStack);
        int n = this.getScannedFromStack(itemStack);
        if (n == 0) {
            return "ic2.crop.unknown";
        }
        if (n < 0 || cropCard == null) {
            return "ic2.crop.invalid";
        }
        return cropCard.getUnlocalizedName();
    }

    public Component m_7626_(ItemStack itemStack) {
        CropCard cropCard = Crops.instance.getCropCard(itemStack);
        return Component.m_237110_((String)(cropCard == null ? "ic2.crop.seeds" : cropCard.getSeedType()), (Object[])new Object[]{super.m_7626_(itemStack)});
    }

    @Environment(value=EnvType.CLIENT)
    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if (this.getScannedFromStack(itemStack) >= 4) {
            list.add((Component)Component.m_237113_((String)("\u00a72Gr\u00a77 " + this.getGrowthFromStack(itemStack))));
            list.add((Component)Component.m_237113_((String)("\u00a76Ga\u00a77 " + this.getGainFromStack(itemStack))));
            list.add((Component)Component.m_237113_((String)("\u00a73Re\u00a77 " + this.getResistanceFromStack(itemStack))));
        }
    }

    public InteractionResult m_6225_(UseOnContext useOnContext) {
        ItemStack itemStack;
        TileEntityCrop tileEntityCrop;
        BlockEntity blockEntity = useOnContext.m_43725_().m_7702_(useOnContext.m_8083_());
        if (blockEntity instanceof TileEntityCrop && (tileEntityCrop = (TileEntityCrop)blockEntity).tryPlantIn(Crops.instance.getCropCard(itemStack = useOnContext.m_43722_()), 0, this.getGrowthFromStack(itemStack), this.getGainFromStack(itemStack), this.getResistanceFromStack(itemStack), this.getScannedFromStack(itemStack))) {
            Player player = useOnContext.m_43723_();
            if (!player.m_150110_().f_35937_) {
                player.m_150109_().f_35974_.set(player.m_150109_().f_35977_, (Object)StackUtil.emptyStack);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void m_6787_(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        if (!this.m_220152_(creativeModeTab)) {
            return;
        }
        for (CropCard cropCard : Crops.instance.getCrops()) {
            nonNullList.add((Object)ItemCropSeed.generateItemStackFromValues(cropCard, 1, 1, 1, 4));
        }
    }

    public static ItemStack generateItemStackFromValues(CropCard cropCard, int n, int n2, int n3, int n4) {
        ItemStack itemStack = new ItemStack((ItemLike)Ic2Items.CROP_SEED_BACK);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.m_128359_("owner", cropCard.getOwner());
        compoundTag.m_128359_("id", cropCard.getId());
        compoundTag.m_128344_("growth", (byte)n);
        compoundTag.m_128344_("gain", (byte)n2);
        compoundTag.m_128344_("resistance", (byte)n3);
        compoundTag.m_128344_("scan", (byte)n4);
        itemStack.m_41751_(compoundTag);
        return itemStack;
    }

    @Override
    public CropCard getCropFromStack(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null || !compoundTag.m_128425_("owner", 8) || !compoundTag.m_128425_("id", 8)) {
            return null;
        }
        String string = compoundTag.m_128461_("owner");
        String string2 = compoundTag.m_128461_("id");
        return Crops.instance.getCropCard(string, string2);
    }

    @Override
    public void setCropFromStack(ItemStack itemStack, CropCard cropCard) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return;
        }
        compoundTag.m_128359_("owner", cropCard.getOwner());
        compoundTag.m_128359_("id", cropCard.getId());
    }

    @Override
    public int getGrowthFromStack(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return -1;
        }
        return compoundTag.m_128445_("growth");
    }

    @Override
    public void setGrowthFromStack(ItemStack itemStack, int n) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return;
        }
        compoundTag.m_128344_("growth", (byte)n);
    }

    @Override
    public int getGainFromStack(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return -1;
        }
        return compoundTag.m_128445_("gain");
    }

    @Override
    public void setGainFromStack(ItemStack itemStack, int n) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return;
        }
        compoundTag.m_128344_("gain", (byte)n);
    }

    @Override
    public int getResistanceFromStack(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return -1;
        }
        return compoundTag.m_128445_("resistance");
    }

    @Override
    public void setResistanceFromStack(ItemStack itemStack, int n) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return;
        }
        compoundTag.m_128344_("resistance", (byte)n);
    }

    @Override
    public int getScannedFromStack(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return -1;
        }
        return compoundTag.m_128445_("scan");
    }

    @Override
    public void setScannedFromStack(ItemStack itemStack, int n) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return;
        }
        compoundTag.m_128344_("scan", (byte)n);
    }

    @Override
    public void incrementScannedFromStack(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return;
        }
        compoundTag.m_128344_("scan", (byte)(this.getScannedFromStack(itemStack) + 1));
    }
}

