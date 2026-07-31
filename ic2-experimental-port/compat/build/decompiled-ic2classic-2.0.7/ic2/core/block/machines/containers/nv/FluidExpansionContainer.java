/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.tiles.nv.TankExpansionTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidTank;

public class FluidExpansionContainer
extends ContainerComponent<TankExpansionTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_tank_expansion.png");
    public static Box2i TANK_BOX_FIRST = new Box2i(26, 17, 16, 58);
    public static Box2i TANK_BOX_SECOND = new Box2i(62, 17, 16, 58);
    public static Box2i TANK_BOX_THIRD = new Box2i(98, 17, 16, 58);
    public static Box2i TANK_BOX_FOURTH = new Box2i(134, 17, 16, 58);

    public FluidExpansionContainer(TankExpansionTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 4);
        this.addComponent(new TankComponent(TANK_BOX_FIRST, (IFluidTank)key.firstTank));
        this.addComponent(new TankComponent(TANK_BOX_SECOND, (IFluidTank)key.secondTank));
        this.addComponent(new TankComponent(TANK_BOX_THIRD, (IFluidTank)key.thirdTank));
        this.addComponent(new TankComponent(TANK_BOX_FOURTH, (IFluidTank)key.fourthTank));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(176, 170);
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

