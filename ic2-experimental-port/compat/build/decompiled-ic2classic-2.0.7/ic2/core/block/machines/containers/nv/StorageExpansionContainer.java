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

import ic2.core.block.machines.tiles.nv.StorageExpansionTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StorageExpansionContainer
extends ContainerComponent<StorageExpansionTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_storage_expansion.png");
    public static final Vec2i OFFSET = new Vec2i(-11, 0);

    public StorageExpansionContainer(StorageExpansionTileEntity key, Player player, int id) {
        super(key, player, id);
        for (int y = 0; y < 2; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.m_38897_(new SlotBase(key.inventory, x + y * 9, 8 + 18 * x, 18 + 18 * y));
            }
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -18);
    }

    @Override
    public int getInventorySize() {
        return 18;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setPlayerInventoryOffset(0, -18);
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return OFFSET;
    }
}

