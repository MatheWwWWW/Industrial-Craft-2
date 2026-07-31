/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.DoublePlantBlock
 *  net.minecraft.world.level.block.FarmBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.item.misc.tfbp.bp;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class CultivatedBluePrint
implements ITerraformerBP {
    public static final ITerraformerBP INSTANCE = new CultivatedBluePrint();

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
        return 4000;
    }

    @Override
    public int getRadius(ItemStack stack) {
        return 40;
    }

    @Override
    public void onInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
        if (world.m_46472_() == Level.f_46430_) {
            IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:cultivate_end"));
        }
    }

    @Override
    public boolean terraform(ItemStack stack, Level world, BlockPos position, ITerraformer terraformer) {
        BlockPos target = terraformer.getFirstSolidBlockFrom(world, position.m_6630_(10));
        if (target.m_123342_() == -1) {
            return false;
        }
        if (terraformer.switchGround(world, target, Blocks.f_49992_.m_49966_(), Blocks.f_50493_.m_49966_(), true, false)) {
            return true;
        }
        BlockState state = world.m_8055_(target);
        if (state.m_60734_() == Blocks.f_50493_) {
            world.m_46597_(target, Blocks.f_50440_.m_49966_());
            return true;
        }
        return state.m_60734_() == Blocks.f_50440_ && this.growPlantsOn(world, target.m_7494_());
    }

    public boolean growPlantsOn(Level world, BlockPos pos) {
        BlockState state = world.m_8055_(pos);
        if (state.m_60734_() != Blocks.f_50016_ && (state.m_60734_() != Blocks.f_50359_ || world.f_46441_.m_188503_(4) != 0)) {
            return false;
        }
        BlockState plant = ((Block)((Holder)Registry.f_122824_.m_203561_(IC2Tags.TERRAFORMER_FLOWERS).m_213653_(world.m_213780_()).get()).m_203334_()).m_49966_();
        if (plant.m_60734_() instanceof CropBlock) {
            world.m_46597_(pos.m_7495_(), (BlockState)Blocks.f_50093_.m_49966_().m_61124_((Property)FarmBlock.f_53243_, (Comparable)Integer.valueOf(7)));
            world.m_46597_(pos, plant);
            return true;
        }
        if (plant.m_60734_() instanceof DoublePlantBlock) {
            DoublePlantBlock.m_153173_((LevelAccessor)world, (BlockState)plant, (BlockPos)pos, (int)3);
            return true;
        }
        return false;
    }
}

