/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.valueproviders.IntProvider
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.level.material.MaterialColor
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.resource;

import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.helpers.Tool;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OreBlock
extends IC2Block
implements IBlockModel {
    private String name;
    private IntProvider dropRate;
    private Ore drop;

    public OreBlock(String blockName, float hardness, float resistance, int harvestLevel, boolean deepslate, IntProvider dropRate, Ore drop) {
        super(blockName, BlockBehaviour.Properties.m_60939_((Material)Material.f_76278_).m_60918_(SoundType.f_56742_).m_155949_(deepslate ? MaterialColor.f_164534_ : MaterialColor.f_76409_).m_60913_(hardness, resistance).m_60999_());
        this.setHarvestTool(Tool.PICKAXE.withLevel(harvestLevel));
        this.name = blockName;
        this.dropRate = dropRate;
        this.drop = drop;
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, @Nullable BlockEntity tile) {
        if (EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44985_, (ItemStack)tool) > 0) {
            return new ItemStack((ItemLike)this);
        }
        return new ItemStack((ItemLike)this.drop.getDrop(), this.generateLoot(rand, EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44987_, (ItemStack)tool)));
    }

    private int generateLoot(RandomSource source, int lootLevel) {
        return this.dropRate.m_214085_(source) * (Math.max(0, source.m_188503_(lootLevel + 2) - 1) + 1);
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.singletonList(this.m_49966_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("resources/ore").get(this.name);
    }

    public static enum Ore {
        TIN,
        SILVER,
        URANIUM,
        ALUMINIUM;


        public Item getDrop() {
            switch (this) {
                case ALUMINIUM: {
                    return IC2Items.RAW_ALUMINIUM;
                }
                case SILVER: {
                    return IC2Items.RAW_SILVER;
                }
                case TIN: {
                    return IC2Items.RAW_TIN;
                }
                case URANIUM: {
                    return IC2Items.ORE_URANIUM_DROP;
                }
            }
            throw new IllegalStateException("Unknown Drop");
        }
    }
}

