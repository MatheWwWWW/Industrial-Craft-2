/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.LootContext
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraftforge.common.Tags$Items
 *  net.minecraftforge.common.loot.IGlobalLootModifier
 */
package ic2.core.platform.recipes.helpers;

import com.mojang.serialization.Codec;
import ic2.core.platform.registries.IC2Items;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;

public class LootOverride
implements IGlobalLootModifier {
    public static final Codec<LootOverride> CODEC = Codec.unit(LootOverride::new);

    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState state;
        if (!(!context.m_78936_(LootContextParams.f_81461_) || (state = (BlockState)context.m_78953_(LootContextParams.f_81461_)).m_60734_() != Blocks.f_50034_ && state.m_60734_() != Blocks.f_50359_ || context.m_78936_(LootContextParams.f_81463_) && ((ItemStack)context.m_78953_(LootContextParams.f_81463_)).m_204117_(Tags.Items.SHEARS) || !(context.m_230907_().m_188500_() <= 0.1))) {
            generatedLoot.add((Object)new ItemStack((ItemLike)IC2Items.HEMP_SEEDS));
        }
        return generatedLoot;
    }

    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}

