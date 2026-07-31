/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.GlazedTerracottaBlock
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.blocks;

import ic2.api.blocks.wrench.ChestWrenchHandler;
import ic2.api.blocks.wrench.DispenserWrenchHandler;
import ic2.api.blocks.wrench.HopperWrenchHandler;
import ic2.api.blocks.wrench.HorizontalWrenchHandler;
import ic2.api.blocks.wrench.InvertedHorizontalWrenchHandler;
import ic2.api.blocks.wrench.ObserverBlockWrenchHandler;
import ic2.api.blocks.wrench.PillarWrenchHandler;
import ic2.api.blocks.wrench.PistonWrenchHandler;
import ic2.api.blocks.wrench.StairWrenchHandler;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public interface IWrenchable {
    public Direction getFacing(BlockState var1, Level var2, BlockPos var3);

    public boolean canSetFacing(BlockState var1, Level var2, BlockPos var3, Player var4, Direction var5);

    public boolean setFacing(BlockState var1, Level var2, BlockPos var3, Player var4, Direction var5);

    public boolean doSpecialAction(BlockState var1, Level var2, BlockPos var3, Direction var4, Player var5, Vec3 var6);

    public AABB hasSpecialAction(BlockState var1, Level var2, BlockPos var3, Direction var4, Player var5, Vec3 var6);

    public boolean canRemoveBlock(BlockState var1, Level var2, BlockPos var3, Player var4);

    public double getDropRate(BlockState var1, Level var2, BlockPos var3, Player var4);

    public List<ItemStack> getDrops(BlockState var1, Level var2, BlockPos var3, Player var4);

    public static final class WrenchRegistry {
        public static final WrenchRegistry INSTANCE = new WrenchRegistry();
        Map<Block, IWrenchable> wrenches = new Object2ObjectOpenHashMap();

        public void init() {
            this.registerWrenchHandler(PistonWrenchHandler.INSTANCE, Blocks.f_50039_, Blocks.f_50032_);
            this.registerWrenchHandler(ObserverBlockWrenchHandler.INSTANCE, Blocks.f_50455_);
            this.registerWrenchHandler(ChestWrenchHandler.INSTANCE, Blocks.f_50087_, Blocks.f_50265_, Blocks.f_50325_);
            this.registerWrenchHandler(HorizontalWrenchHandler.INSTANCE, Blocks.f_50094_, Blocks.f_50620_, Blocks.f_50619_);
            this.registerWrenchHandler(InvertedHorizontalWrenchHandler.INSTANCE, Blocks.f_50146_, Blocks.f_50328_);
            this.registerWrenchHandler(PillarWrenchHandler.INSTANCE, Blocks.f_50003_, Blocks.f_50001_, Blocks.f_50004_, Blocks.f_50002_, Blocks.f_49999_, Blocks.f_50000_, Blocks.f_50008_, Blocks.f_50006_, Blocks.f_50009_, Blocks.f_50007_, Blocks.f_50010_, Blocks.f_50005_);
            this.registerWrenchHandler(DispenserWrenchHandler.INSTANCE, Blocks.f_50061_, Blocks.f_50286_);
            this.registerWrenchHandler(HopperWrenchHandler.INSTANCE, Blocks.f_50332_);
            for (Block block : ForgeRegistries.BLOCKS) {
                if (block instanceof StairBlock) {
                    this.registerWrenchHandler(StairWrenchHandler.INSTANCE, block);
                    continue;
                }
                if (!(block instanceof GlazedTerracottaBlock)) continue;
                this.registerWrenchHandler(InvertedHorizontalWrenchHandler.INSTANCE, block);
            }
        }

        public void registerWrenchHandler(IWrenchable wrench, Block ... blocks) {
            for (Block block : blocks) {
                this.wrenches.put(block, wrench);
            }
        }

        public IWrenchable getWrenchable(BlockState state) {
            IWrenchable result;
            Block block;
            IWrenchable wrench = this.wrenches.get(state.m_60734_());
            return wrench != null ? wrench : ((block = state.m_60734_()) instanceof IWrenchable ? (result = (IWrenchable)block) : null);
        }
    }
}

