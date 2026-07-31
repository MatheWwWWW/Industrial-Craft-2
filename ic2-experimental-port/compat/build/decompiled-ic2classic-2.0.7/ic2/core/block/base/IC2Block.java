/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.HitResult
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.base;

import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.IToolProvider;
import ic2.core.block.base.drops.IRarityProvider;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.plugins.IRegistryProvider;
import ic2.core.utils.tooltips.ILangHelper;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.GameData;

public abstract class IC2Block
extends Block
implements IAutoCreator,
IRarityProvider,
IRegistryProvider,
ILangHelper,
IToolProvider {
    Rarity rarityOverride = null;
    protected EnumMap<Tool.ToolType, Tool> harvestLevels = new EnumMap(Tool.ToolType.class);
    protected ResourceLocation id;

    public IC2Block(String blockName, BlockBehaviour.Properties properties) {
        super(properties);
        this.id = GameData.checkPrefix((String)blockName, (boolean)false);
    }

    @Override
    public void registerTools() {
        for (Tool tool : this.harvestLevels.values()) {
            tool.register(this);
        }
    }

    public IC2Block setHarvestTool(Tool tier) {
        this.harvestLevels.put(tier.getType(), tier);
        return this;
    }

    public IC2Block setOverrideRarity(Rarity rarity) {
        this.rarityOverride = rarity;
        return this;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return this.rarityOverride;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, world, pos, player);
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof Nameable && ((Nameable)tile).m_8077_()) {
            stack.m_41714_(((Nameable)tile).m_7770_());
        }
        return stack;
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

