/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.data.BuiltinRegistries
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
 *  net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider
 */
package ic2.core.item.misc.tfbp.bp;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

public class IrrigationBluePrint
implements ITerraformerBP {
    public static final IrrigationBluePrint INSTANCE = new IrrigationBluePrint();
    Map<Block, Block> logToLeaves = CollectionUtils.createMap();

    public void init() {
        this.logToLeaves.clear();
        for (ConfiguredFeature feature : BuiltinRegistries.f_123861_) {
            SimpleStateProvider leaves;
            if (!(feature.f_65378_() instanceof TreeConfiguration)) continue;
            TreeConfiguration config = (TreeConfiguration)feature.f_65378_();
            SimpleStateProvider log = config.f_68185_ instanceof SimpleStateProvider ? (SimpleStateProvider)config.f_68185_ : null;
            SimpleStateProvider simpleStateProvider = leaves = config.f_161213_ instanceof SimpleStateProvider ? (SimpleStateProvider)config.f_161213_ : null;
            if (log == null || leaves == null) continue;
            this.logToLeaves.put(log.m_213972_(null, null).m_60734_(), leaves.m_213972_(null, null).m_60734_());
        }
    }

    @Override
    public void onInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
    }

    @Override
    public boolean canInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isRandomized(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnergyUsage(ItemStack stack) {
        return 3000;
    }

    @Override
    public int getRadius(ItemStack stack) {
        return 60;
    }

    @Override
    public boolean terraform(ItemStack stack, Level world, BlockPos position, ITerraformer terraformer) {
        if (world.f_46441_.m_188503_(48000) == 0) {
            world.m_6106_().m_5565_(true);
            return true;
        }
        BlockPos target = terraformer.getFirstBlockFrom(world, position.m_6630_(10));
        if (target.m_123342_() == -1) {
            return false;
        }
        if (terraformer.switchGround(world, target, Blocks.f_49992_.m_49966_(), Blocks.f_50493_.m_49966_(), true, false)) {
            terraformer.switchGround(world, target, Blocks.f_49992_.m_49966_(), Blocks.f_50493_.m_49966_(), true, false);
            return true;
        }
        BlockState state = world.m_8055_(target);
        if (state.m_60734_() == Blocks.f_50359_) {
            return this.spreadGrass(world, position.m_122029_(), terraformer) || this.spreadGrass(world, position.m_122024_(), terraformer) || this.spreadGrass(world, position.m_122012_(), terraformer) || this.spreadGrass(world, position.m_122019_(), terraformer);
        }
        if (state.m_60734_() instanceof BonemealableBlock && !(state.m_60734_() instanceof CropBlock)) {
            ((BonemealableBlock)state.m_60734_()).m_214148_((ServerLevel)world, world.f_46441_, target, state);
            return true;
        }
        if (state.m_204336_(BlockTags.f_13106_)) {
            BlockPos logPos = target.m_7494_();
            world.m_46597_(logPos, state);
            this.spawnLeaves(world, target.m_7494_(), state);
            this.spawnLeaves(world, target.m_122029_(), state);
            this.spawnLeaves(world, target.m_122024_(), state);
            this.spawnLeaves(world, target.m_122012_(), state);
            this.spawnLeaves(world, target.m_122024_(), state);
            return true;
        }
        if (state.m_60734_() instanceof CropBlock) {
            CropBlock crop = (CropBlock)state.m_60734_();
            if (crop.m_52307_(state)) {
                return false;
            }
            world.m_46597_(target, (BlockState)state.m_61124_((Property)crop.m_7959_(), (Comparable)Integer.valueOf((Integer)state.m_61143_((Property)crop.m_7959_()) + 1)));
            return true;
        }
        if (state.m_60734_() == Blocks.f_50083_) {
            world.m_7471_(target, false);
            return true;
        }
        return false;
    }

    public void spawnLeaves(Level world, BlockPos pos, BlockState state) {
        Block leave = this.logToLeaves.get(state.m_60734_());
        if (leave != null) {
            world.m_46597_(pos, leave.m_49966_());
        }
    }

    public boolean spreadGrass(Level world, BlockPos pos, ITerraformer terraformer) {
        if (world.f_46441_.m_188499_()) {
            return false;
        }
        BlockPos target = terraformer.getFirstBlockFrom(world, pos);
        BlockState state = world.m_8055_(target);
        if (state.m_60734_() == Blocks.f_50493_) {
            world.m_46597_(target, Blocks.f_50440_.m_49966_());
            return true;
        }
        if (state.m_60734_() == Blocks.f_50034_) {
            world.m_46597_(target, Blocks.f_50359_.m_49966_());
            return true;
        }
        return false;
    }
}

