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

import ic2.core.block.machines.containers.lv.BasicMachineContainer;
import ic2.core.block.machines.tiles.mv.RareEarthCentrifugeTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.gui.components.simple.SpeedComponent;
import ic2.core.inventory.gui.components.simple.SubProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RareEarthCentrifugeContainer
extends ContainerComponent<RareEarthCentrifugeTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_rare_earth_centrifuge.png");
    public static final Box2i SUB_PROGRESS_BOX = new Box2i(7, 12, 162, 2);
    public static final Vec2i SUB_PROGRESS_POS = new Vec2i(7, 168);
    public static final Vec2i BUTTON_OFFSET = new Vec2i(0, 11);
    public static final Vec2i PREVIEW_OFFSET = new Vec2i(11, 0);
    public static final Vec2i COMPARATOR_OFFSET = new Vec2i(11, 11);

    public RareEarthCentrifugeContainer(RareEarthCentrifugeTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 56, 53));
        this.m_38897_(new FilterSlot(key, 1, 56, 17, key.gui_Filter));
        this.m_38897_(FilterSlot.createOutputSlot(key, 2, 116, 35));
        this.m_38897_(new UpgradeSlot(key, 3, 153, 19));
        this.m_38897_(new UpgradeSlot(key, 4, 153, 37));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(BasicMachineContainer.CHARGE_BOX, key, BasicMachineContainer.CHARGE_POS, true));
        this.addComponent(new ProgressComponent(BasicMachineContainer.PROGRESS_BOX, key, BasicMachineContainer.PROGRESS_POS, false));
        this.addComponent(new SubProgressComponent(SUB_PROGRESS_BOX, key, SUB_PROGRESS_POS, false));
        this.addComponent(new SpeedComponent(key, key.getSpeedName()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setContainerOffset(0, -3);
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return BUTTON_OFFSET;
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return PREVIEW_OFFSET;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return COMPARATOR_OFFSET;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

