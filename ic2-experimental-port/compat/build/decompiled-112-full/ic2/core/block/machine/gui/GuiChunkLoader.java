/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.block.material.MapColor
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.gui;

import com.google.common.collect.ImmutableSet;
import ic2.core.ChunkLoaderLogic;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.container.ContainerChunkLoader;
import ic2.core.block.machine.tileentity.TileEntityChunkloader;
import ic2.core.gui.EnergyGauge;
import ic2.core.util.Ic2BlockPos;
import ic2.core.util.LogCategory;
import java.io.IOException;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiChunkLoader
extends GuiIC2<ContainerChunkLoader> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIChunkLoader.png");

    public GuiChunkLoader(ContainerChunkLoader container) {
        super(container, 250);
        this.addElement(EnergyGauge.asBolt(this, 12, 125, (TileEntityBlock)container.base));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_174877_v());
        ImmutableSet<ChunkPos> loadedChunks = ((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).getLoadedChunks();
        int amountLoadedChunks = 0;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {
                ChunkPos currentChunk = new ChunkPos(mainChunk.field_77276_a + i, mainChunk.field_77275_b + j);
                int xpos = -this.field_147003_i + 89 + 16 * i;
                int ypos = -this.field_147009_r + 80 + 16 * j;
                this.drawChunkAt(xpos, ypos, currentChunk);
                if (loadedChunks.contains(currentChunk)) {
                    this.drawColoredRect(xpos, ypos, 16, 16, 0x3000FF00);
                    ++amountLoadedChunks;
                    continue;
                }
                this.drawColoredRect(xpos, ypos, 16, 16, 0x30FF0000);
            }
        }
        GlStateManager.func_179141_d();
        this.field_146289_q.func_78279_b(amountLoadedChunks + " / " + ChunkLoaderLogic.getInstance().getMaxChunksPerTicket(), 8, 16, 15, 0x404040);
        super.drawForegroundLayer(mouseX, mouseY);
    }

    private void drawChunkAt(int x, int y, ChunkPos chunkPos) {
        World world = ((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_145831_w();
        Chunk chunk = world.func_72964_e(chunkPos.field_77276_a, chunkPos.field_77275_b);
        Ic2BlockPos worldPos = new Ic2BlockPos();
        for (int cx = 0; cx < 16; ++cx) {
            worldPos.setX(chunkPos.field_77276_a << 4 | cx);
            for (int cz = 0; cz < 16; ++cz) {
                worldPos.setZ(chunkPos.field_77275_b << 4 | cz);
                worldPos.setY(chunk.func_76611_b(cx, cz));
                IBlockState state = chunk.func_177435_g((BlockPos)worldPos);
                if (state.func_177230_c().isAir(state, (IBlockAccess)world, (BlockPos)worldPos)) {
                    worldPos.moveDown();
                    state = chunk.func_177435_g((BlockPos)worldPos);
                }
                this.drawColoredRect(x + cx, y + cz, 1, 1, this.getColor(state, world, worldPos));
            }
        }
    }

    private int getColor(IBlockState state, World world, BlockPos pos) {
        MapColor color = state.func_185909_g((IBlockAccess)world, pos);
        if (color == null) {
            IC2.log.error(LogCategory.General, "BlockState " + state + " does not have a MapColor set. Please report to the mod author of that mod.");
            return 0;
        }
        return color.field_76291_p | 0xFF000000;
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_174877_v());
            for (int i = -4; i <= 4; ++i) {
                for (int j = -4; j <= 4; ++j) {
                    if (mouseX - this.field_147003_i <= 89 + 16 * i || mouseX - this.field_147003_i > 89 + 16 * i + 16 || mouseY - this.field_147009_r <= 80 + 16 * j || mouseY - this.field_147009_r > 80 + 16 * j + 16) continue;
                    this.changeChunk(new ChunkPos(mainChunk.field_77276_a + i, mainChunk.field_77275_b + j));
                    return;
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private void changeChunk(ChunkPos chunk) {
        ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_174877_v());
        IC2.network.get(false).initiateClientTileEntityEvent((TileEntity)((ContainerChunkLoader)this.container).base, chunk.field_77276_a - mainChunk.field_77276_a + 8 & 0xF | (chunk.field_77275_b - mainChunk.field_77275_b + 8 & 0xF) << 4);
    }
}

