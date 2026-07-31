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
package ic2.core.block.generators.containers;

import ic2.api.reactor.ISteamReactor;
import ic2.core.block.base.tiles.impls.BaseNuclearReactorTileEntity;
import ic2.core.block.generators.components.ReactorComponent;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidTank;

public class ReactorContainer
extends ContainerComponent<BaseNuclearReactorTileEntity> {
    public static final ResourceLocation[] TEXTURES_ELECTRIC = new ResourceLocation[]{new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor/gui6by3.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor/gui6by4.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor/gui6by5.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor/gui6by6.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor/gui6by7.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor/gui6by8.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor/gui6by9.png")};
    public static final ResourceLocation[] TEXTURES_STEAM = new ResourceLocation[]{new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor_steam/gui6by3.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor_steam/gui6by4.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor_steam/gui6by5.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor_steam/gui6by6.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor_steam/gui6by7.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor_steam/gui6by8.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/generators/reactor_steam/gui6by9.png")};
    public int size;
    public boolean isSteam;

    public ReactorContainer(BaseNuclearReactorTileEntity key, Player player, int id) {
        super(key, player, id);
        this.isSteam = key instanceof ISteamReactor;
        this.size = key.getWidth();
        int startX = 89 - 9 * this.size;
        int startY = 18;
        for (int i = 0; i < 54; ++i) {
            int x = i % 9;
            if (x >= this.size) continue;
            this.m_38897_(new FilterSlot(key, i, startX + 18 * x, startY + 18 * (i / 9), key::isUsefulItem));
        }
        if (this.isSteam) {
            ISteamReactor reactor = (ISteamReactor)((Object)key);
            if (this.size >= 8) {
                this.addComponent(new TankComponent(new Box2i(-10, 18, 12, 106), new Vec2i(216, 18), (IFluidTank)reactor.getWaterTank()));
                this.addComponent(new TankComponent(new Box2i(174, 18, 12, 106), new Vec2i(216, 18), (IFluidTank)reactor.getSteamTank()));
            } else {
                this.addComponent(new TankComponent(new Box2i(8, 18, 12, 106), new Vec2i(216, 18), (IFluidTank)reactor.getWaterTank()));
                this.addComponent(new TankComponent(new Box2i(156, 18, 12, 106), new Vec2i(216, 18), (IFluidTank)reactor.getSteamTank()));
            }
        }
        this.addComponent(new ReactorComponent(key, this.size));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 72);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        if (this.isSteam && this.size >= 8) {
            screen.setXSize(194);
        }
        screen.setYSize(238);
    }

    @Override
    public int getInventorySize() {
        return ((BaseNuclearReactorTileEntity)this.getHolder()).getWidth() * 6;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.isSteam ? TEXTURES_STEAM[this.size - 3] : TEXTURES_ELECTRIC[this.size - 3];
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return new Vec2i(11, -11);
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return new Vec2i(11, 0);
    }
}

