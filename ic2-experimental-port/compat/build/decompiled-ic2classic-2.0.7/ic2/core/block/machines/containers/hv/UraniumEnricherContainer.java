/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.hv;

import ic2.core.block.machines.components.hv.UraniumEnricherComponent;
import ic2.core.block.machines.tiles.hv.UraniumEnchricherTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.gui.components.simple.SubProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class UraniumEnricherContainer
extends ContainerComponent<UraniumEnchricherTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/hv/gui_uranium_enricher.png");
    public static final Box2i CHARGE_BOX = new Box2i(8, 58, 14, 14);
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static final Box2i PROGRESS_BOX = new Box2i(97, 34, 51, 35);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 35);
    public static final Box2i SECOND_PROGRESS_BOX = new Box2i(62, 27, 24, 16);
    public static final Vec2i SECOND_PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i URANIUM_FUEL_BOX = new Box2i(90, 15, 5, 34);

    public UraniumEnricherContainer(UraniumEnchricherTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(new FilterSlot(key, 0, 81, 58, key::isValidInput));
        this.m_38897_(new FilterSlot(key, 1, 45, 27, key::isValidCatalyst));
        this.m_38897_(FilterSlot.createOutputSlot(key, 2, 152, 43));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
        this.addComponent(new SubProgressComponent(SECOND_PROGRESS_BOX, key, SECOND_PROGRESS_POS, false));
        this.addComponent(new UraniumEnricherComponent(URANIUM_FUEL_BOX, key));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

