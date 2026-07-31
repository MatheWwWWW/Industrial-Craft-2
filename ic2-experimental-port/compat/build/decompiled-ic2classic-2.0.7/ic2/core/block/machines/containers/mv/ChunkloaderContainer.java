/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.mv;

import ic2.core.block.machines.components.mv.ChunkloaderComponent;
import ic2.core.block.machines.tiles.mv.ChunkloaderTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChunkloaderContainer
extends ContainerComponent<ChunkloaderTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_chunkloader.png");
    public static final Box2i CHARGE_BOX = new Box2i(53, 22, 24, 16);
    public static final Vec2i CHARGE_POS = new Vec2i(122, 0);
    public static final Vec2i OFFSET = new Vec2i(-11, 0);

    public ChunkloaderContainer(ChunkloaderTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addHiddenPlayerInventory(player.m_150109_());
        this.addComponent(new ChunkloaderComponent(key));
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(121, 114);
        screen.clearFlag(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return OFFSET;
    }
}

