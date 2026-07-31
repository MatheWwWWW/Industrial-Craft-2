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

import ic2.core.block.machines.components.luv.FusionReactorComponent;
import ic2.core.block.machines.tiles.luv.FusionReactorTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.item.reactor.ReactorUraniumRod;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FusionReactorContainer
extends ContainerComponent<FusionReactorTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/luv/gui_fusion_reactor.png");
    public static final Vec2i TANK_POS = new Vec2i(176, 31);
    public static final Vec2i BUTTON_OFFSET_INV = new Vec2i(143, 74);
    public static final Vec2i BUTTON_OFFSET_PREVIEW = new Vec2i(154, 63);
    public static final Vec2i BUTTON_OFFSET_COMPARATOR = new Vec2i(121, 74);

    public FusionReactorContainer(FusionReactorTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createClassSlot(key, 0, 58, 18, ReactorUraniumRod.class));
        this.m_38897_(FilterSlot.createClassSlot(key, 1, 102, 62, ReactorUraniumRod.class));
        this.m_38897_(FilterSlot.createClassSlot(key, 2, 58, 40, ReactorUraniumRod.class));
        this.m_38897_(FilterSlot.createClassSlot(key, 3, 102, 40, ReactorUraniumRod.class));
        this.m_38897_(FilterSlot.createClassSlot(key, 4, 80, 18, ReactorUraniumRod.class));
        this.m_38897_(FilterSlot.createClassSlot(key, 5, 80, 62, ReactorUraniumRod.class));
        this.m_38897_(new FilterSlot(key, 6, 80, 40, key::isValidFuel));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 7);
        this.addComponent(new TankComponent(new Box2i(8, 18, 16, 58), TANK_POS, new FusionReactorTileEntity.Tank(key, true)));
        this.addComponent(new TankComponent(new Box2i(152, 19, 16, 58), TANK_POS, new FusionReactorTileEntity.Tank(key, false)));
        this.addComponent(new FusionReactorComponent(key));
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return BUTTON_OFFSET_INV;
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return BUTTON_OFFSET_PREVIEW;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return BUTTON_OFFSET_COMPARATOR;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setYSize(173);
    }
}

