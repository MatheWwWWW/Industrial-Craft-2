/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.MushroomBlock
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.misc.tfbp.bp;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MushroomBluePrint
implements ITerraformerBP {
    public static final ITerraformerBP INSTANCE = new MushroomBluePrint();

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
        return 8000;
    }

    @Override
    public int getRadius(ItemStack stack) {
        return 25;
    }

    @Override
    public void onInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
    }

    @Override
    public boolean terraform(ItemStack stack, Level world, BlockPos position, ITerraformer terraformer) {
        BlockPos target = terraformer.getFirstSolidBlockFrom(world, position.m_6630_(20));
        return target.m_123342_() != -1 && this.growBlockWithDependency((ServerLevel)world, target, Blocks.f_50180_, Blocks.f_50072_, terraformer);
    }

    public boolean growBlockWithDependency(ServerLevel world, BlockPos pos, Block id, Block dependency, ITerraformer terraformer) {
        Block above;
        if (dependency != Blocks.f_50016_) {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (int x = -1; x <= 1; ++x) {
                block1: for (int z = -1; z <= 1; ++z) {
                    for (int y = 5; y > -2; --y) {
                        mutable.m_122178_(pos.m_123341_() + x, pos.m_123342_() + y, pos.m_123343_() + z);
                        Block block = world.m_8055_((BlockPos)mutable).m_60734_();
                        if (dependency == Blocks.f_50195_) {
                            if (block == dependency || block == Blocks.f_50180_ || block == Blocks.f_50181_) continue block1;
                            if (block == Blocks.f_50016_) continue;
                            if (block == Blocks.f_50493_ || block == Blocks.f_50440_) {
                                BlockPos targetPos = mutable.m_7949_();
                                world.m_46597_(targetPos, dependency.m_49966_());
                                terraformer.setBiome((Level)world, targetPos, Biomes.f_48215_.m_135782_());
                                return true;
                            }
                        }
                        if (dependency != Blocks.f_50072_) continue;
                        if (block == Blocks.f_50072_ || block == Blocks.f_50073_) continue block1;
                        if (block == Blocks.f_50016_ || !this.growBlockWithDependency(world, mutable.m_7949_(), Blocks.f_50072_, Blocks.f_50195_, terraformer)) continue;
                        return true;
                    }
                }
            }
        }
        if (id != Blocks.f_50072_) {
            if (id == Blocks.f_50180_) {
                BlockPos up = pos.m_7494_();
                BlockState state = world.m_8055_(up);
                if (state.m_60734_() != Blocks.f_50072_ && state.m_60734_() != Blocks.f_50073_) {
                    return false;
                }
                if (((MushroomBlock)state.m_60734_()).m_221773_(world, up, state, world.f_46441_)) {
                    BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                    for (int x = -1; x <= 1; ++x) {
                        for (int z = -1; z <= 1; ++z) {
                            mutable.m_122178_(up.m_123341_() + x, up.m_123342_(), up.m_123343_() + z);
                            Block block = world.m_8055_((BlockPos)mutable).m_60734_();
                            if (block != Blocks.f_50072_ && block != Blocks.f_50073_) continue;
                            world.m_7471_(mutable.m_7949_(), false);
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        Block base = world.m_8055_(pos).m_60734_();
        if (base != Blocks.f_50195_) {
            if (base != Blocks.f_50180_ && base != Blocks.f_50181_) {
                return false;
            }
            world.m_46597_(pos, Blocks.f_50195_.m_49966_());
        }
        if ((above = world.m_8055_(pos.m_7494_()).m_60734_()) != Blocks.f_50016_ && above != Blocks.f_50359_) {
            return false;
        }
        Block shroom = Blocks.f_50072_;
        if (world.f_46441_.m_188499_()) {
            shroom = Blocks.f_50073_;
        }
        world.m_46597_(pos.m_7494_(), shroom.m_49966_());
        return true;
    }
}

