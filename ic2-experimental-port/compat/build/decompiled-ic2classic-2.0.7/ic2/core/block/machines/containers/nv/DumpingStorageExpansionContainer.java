/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.tiles.nv.BufferStorageExpansionTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DumpingStorageExpansionContainer
extends ContainerComponent<BufferStorageExpansionTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_dumping_storage_expansion.png");
    public static final Vec2i OFFSET = new Vec2i(-11, 0);

    public DumpingStorageExpansionContainer(BufferStorageExpansionTileEntity key, Player player, int id) {
        super(key, player, id);
        for (int x = 0; x < 9; ++x) {
            this.m_38897_(new SlotBase(key.inventory, x, 8 + 18 * x, 17));
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -37);
    }

    @Override
    public int getInventorySize() {
        return 9;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setPlayerInventoryOffset(0, -37);
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return OFFSET;
    }
}

