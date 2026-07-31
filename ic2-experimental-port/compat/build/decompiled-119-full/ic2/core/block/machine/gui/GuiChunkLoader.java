/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.material.MaterialColor
 */
package ic2.core.block.machine.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerChunkLoader;
import ic2.core.block.machine.tileentity.TileEntityChunkloader;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.util.LogCategory;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.MaterialColor;

public class GuiChunkLoader
extends Ic2Gui<ContainerChunkLoader> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guichunkloader.png");

    public GuiChunkLoader(ContainerChunkLoader containerChunkLoader, Inventory inventory, Component component) {
        super(containerChunkLoader, inventory, component, 250);
        this.addElement(EnergyGauge.asBolt(this, 12, 125, (Ic2TileEntity)containerChunkLoader.base));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        ChunkPos chunkPos = new ChunkPos(((TileEntityChunkloader)((ContainerChunkLoader)this.f_97732_).base).m_58899_());
        LongSet longSet = ((TileEntityChunkloader)((ContainerChunkLoader)this.f_97732_).base).getLoadedChunks();
        int n3 = 0;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {
                ChunkPos chunkPos2 = new ChunkPos(chunkPos.f_45578_ + i, chunkPos.f_45579_ + j);
                int n4 = -this.f_97735_ + 89 + 16 * i;
                int n5 = -this.f_97736_ + 80 + 16 * j;
                this.drawChunkAt(poseStack, n4, n5, chunkPos2);
                if (longSet.contains(chunkPos2.m_45588_())) {
                    this.drawColoredRect(poseStack, n4, n5, 16, 16, 0x3000FF00);
                    ++n3;
                    continue;
                }
                this.drawColoredRect(poseStack, n4, n5, 16, 16, 0x30FF0000);
            }
        }
        this.drawTrimmedString(poseStack, 8, 58, n3 + " / " + ((TileEntityChunkloader)((ContainerChunkLoader)this.f_97732_).base).getMaxChunks(), 15, 0x404040);
        super.drawForegroundLayer(poseStack, n, n2);
    }

    private void drawChunkAt(PoseStack poseStack, int n, int n2, ChunkPos chunkPos) {
        Level level = ((TileEntityChunkloader)((ContainerChunkLoader)this.f_97732_).base).m_58904_();
        LevelChunk levelChunk = level.m_6325_(chunkPos.f_45578_, chunkPos.f_45579_);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                mutableBlockPos.m_122178_(chunkPos.f_45578_ << 4 | i, levelChunk.m_5885_(Heightmap.Types.WORLD_SURFACE, i, j), chunkPos.f_45579_ << 4 | j);
                BlockState blockState = levelChunk.m_8055_((BlockPos)mutableBlockPos);
                if (blockState.m_60795_()) {
                    mutableBlockPos.m_122173_(Direction.DOWN);
                    blockState = levelChunk.m_8055_((BlockPos)mutableBlockPos);
                }
                this.drawColoredRect(poseStack, n + i, n2 + j, 1, 1, this.getColor(blockState, level, (BlockPos)mutableBlockPos));
            }
        }
    }

    private int getColor(BlockState blockState, Level level, BlockPos blockPos) {
        MaterialColor materialColor = blockState.m_60780_((BlockGetter)level, blockPos);
        if (materialColor == null) {
            IC2.log.error(LogCategory.General, "BlockState " + blockState + " does not have a MapColor set. Please report to the mod author of that mod.");
            return 0;
        }
        return materialColor.f_76396_ | 0xFF000000;
    }

    @Override
    public boolean m_6375_(double d, double d2, int n) {
        if (n == 0) {
            ChunkPos chunkPos = new ChunkPos(((TileEntityChunkloader)((ContainerChunkLoader)this.f_97732_).base).m_58899_());
            for (int i = -4; i <= 4; ++i) {
                for (int j = -4; j <= 4; ++j) {
                    if (!(d - (double)this.f_97735_ > (double)(89 + 16 * i)) || !(d - (double)this.f_97735_ <= (double)(89 + 16 * i + 16)) || !(d2 - (double)this.f_97736_ > (double)(80 + 16 * j)) || !(d2 - (double)this.f_97736_ <= (double)(80 + 16 * j + 16))) continue;
                    this.changeChunk(new ChunkPos(chunkPos.f_45578_ + i, chunkPos.f_45579_ + j));
                    return true;
                }
            }
        }
        return super.m_6375_(d, d2, n);
    }

    private void changeChunk(ChunkPos chunkPos) {
        ChunkPos chunkPos2 = new ChunkPos(((TileEntityChunkloader)((ContainerChunkLoader)this.f_97732_).base).m_58899_());
        IC2.network.get(false).initiateClientTileEntityEvent((BlockEntity)((ContainerChunkLoader)this.f_97732_).base, chunkPos.f_45578_ - chunkPos2.f_45578_ + 8 & 0xF | (chunkPos.f_45579_ - chunkPos2.f_45579_ + 8 & 0xF) << 4);
    }
}

