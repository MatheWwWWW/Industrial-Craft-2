/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.storage.container;

import ic2.core.block.storage.tiles.tank.TankTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TankContainer
extends ContainerComponent<TankTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/storage/gui_tank.png");
    public static final Box2i TANK_BOX = new Box2i(80, 14, 16, 58);

    public TankContainer(TankTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createFluidDrainSlot((IHasInventory)key, 0, 44, 14, (IFluidHandler)key.tank));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 1, 44, 56));
        this.m_38897_(FilterSlot.createFluidFillSlot((IHasInventory)key, 2, 116, 14, (IFluidHandler)key.tank));
        this.m_38897_(FilterSlot.createFluidOutputSlot(key, 3, 116, 56));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new TankComponent(TANK_BOX, (IFluidTank)key.tank));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setContainerOffset(0, -2);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

