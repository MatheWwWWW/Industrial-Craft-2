/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.luv;

import ic2.core.block.machines.components.ev.TeleporterHubComponent;
import ic2.core.block.machines.tiles.luv.TeleporterHubTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TeleporterHubContainer
extends ContainerComponent<TeleporterHubTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/luv/gui_teleporter_hub.png");

    public TeleporterHubContainer(TeleporterHubTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 42);
        this.addComponent(new ChargebarComponent(new Box2i(154, 108, 14, 14), key, new Vec2i(176, 0), true));
        this.addComponent(new TeleporterHubComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 204);
        screen.setPlayerInventoryOffset(0, 5);
    }
}

