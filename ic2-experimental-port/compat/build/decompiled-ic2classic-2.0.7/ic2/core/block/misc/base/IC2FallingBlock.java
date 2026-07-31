/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.FallingBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.misc.base;

import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2FallingBlock
extends FallingBlock
implements IBlockModel,
IAutoCreator {
    ResourceLocation id;
    String name;

    public IC2FallingBlock(String name, BlockBehaviour.Properties properties) {
        super(properties);
        this.name = name;
        this.id = GameData.checkPrefix((String)name, (boolean)false);
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("resources/dust").get(this.name);
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        BlockEntity tile = (BlockEntity)builder.m_78982_(LootContextParams.f_81462_);
        ItemStack stack = this.createDrop(state, (ItemStack)builder.m_78982_(LootContextParams.f_81463_), builder.m_78962_().f_46441_, tile);
        if (!stack.m_41619_()) {
            if (tile instanceof Nameable && ((Nameable)tile).m_8077_()) {
                stack.m_41714_(((Nameable)tile).m_7770_());
            }
            drops.add(stack);
        }
        this.addExtraDrops(drops, state, stack, builder.m_78962_().m_213780_());
        if (tile instanceof IDropProvider) {
            ((IDropProvider)tile).addDrops(drops);
        }
        return drops;
    }

    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, @Nullable BlockEntity tile) {
        return new ItemStack((ItemLike)this);
    }

    public void addExtraDrops(List<ItemStack> list, BlockState state, ItemStack tool, RandomSource rand) {
    }
}

