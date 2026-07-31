/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.api.items.ICoinItem;
import ic2.api.items.ItemRegistries;
import ic2.core.item.base.IC2Item;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CoinItem
extends IC2Item
implements ICoinItem,
ISimpleItemModel {
    int value;
    String texture;

    public CoinItem(String itemName, int value, String texture) {
        super(itemName);
        this.value = value;
        this.texture = texture;
    }

    public CoinItem register() {
        ItemRegistries.registerCoin(this);
        return this;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add((Component)this.translate("tooltip.item.ic2.coin", ChatFormatting.GRAY, new Object[]{this.value}));
    }

    @Override
    public int getMoneyValue(ItemStack stack) {
        return this.value;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "coins").get(this.texture);
    }
}

