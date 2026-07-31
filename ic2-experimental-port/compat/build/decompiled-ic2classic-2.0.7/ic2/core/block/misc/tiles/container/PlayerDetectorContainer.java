/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.tiles.container;

import ic2.core.block.machines.components.misc.PlayerDetectorComponent;
import ic2.core.block.misc.tiles.PlayerDetectorTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlayerDetectorContainer
extends ContainerComponent<PlayerDetectorTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/misc/gui_player_detector.png");

    public PlayerDetectorContainer(PlayerDetectorTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -17);
        this.addComponent(new PlayerDetectorComponent(key));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(109, 50, 60, 14)));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 149);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public int getInventorySize() {
        return 0;
    }
}

