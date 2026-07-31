/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.base;

import ic2.core.IC2;
import ic2.core.block.base.drops.IRarityProvider;
import ic2.core.block.base.misc.color.IColorListener;
import ic2.core.block.base.misc.color.IItemColorListener;
import ic2.core.utils.plugins.IRegistryProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

public class IC2BlockItem
extends BlockItem
implements IItemColorListener,
IRegistryProvider {
    boolean noCreative;
    protected ResourceLocation id;

    public IC2BlockItem(Block block, Item.Properties properties) {
        super(block, properties);
        this.id = ForgeRegistries.BLOCKS.getKey((Object)block);
    }

    public IC2BlockItem(Block block) {
        this(block, new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP));
    }

    public IC2BlockItem(Block block, String name) {
        super(block, new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP));
        this.id = GameData.checkPrefix((String)name, (boolean)false);
    }

    public IC2BlockItem disableCreative() {
        this.noCreative = true;
        return this;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public Rarity m_41460_(ItemStack stack) {
        Rarity rarity;
        if (this.m_40614_() instanceof IRarityProvider && (rarity = ((IRarityProvider)this.m_40614_()).getRarity(stack)) != null) {
            return rarity;
        }
        return super.m_41460_(stack);
    }

    @Override
    public boolean needsColoring() {
        Block block = this.m_40614_();
        return block instanceof IColorListener && ((IColorListener)block).needsColoring();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getItemColor(ItemStack stack, int tintIndex) {
        Block block = this.m_40614_();
        return block instanceof IItemColorListener ? ((IItemColorListener)block).getItemColor(stack, tintIndex) : -1;
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.noCreative) {
            return;
        }
        super.m_6787_(group, items);
    }
}

