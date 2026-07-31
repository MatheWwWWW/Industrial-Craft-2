/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.tiles.nv.StoneBasicMachineTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.XPSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BasicStoneMachineContainer
extends ContainerComponent<StoneBasicMachineTileEntity> {
    public static final Vec2i FUEL_POS = new Vec2i(176, 0);
    public static final Box2i FUEL_BOX = new Box2i(56, 36, 14, 14);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i PROGRESS_BOX = new Box2i(79, 34, 24, 16);

    public BasicStoneMachineContainer(StoneBasicMachineTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createFuelSlot(key, 0, 56, 53, key.allowsLavaFuel()));
        this.m_38897_(new FilterSlot(key, 1, 56, 17, T -> key.getRecipeList().getRecipe(T, false) != null));
        this.m_38897_(new XPSlot(key, 2, 116, 35));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new FuelComponent(FUEL_BOX, key, FUEL_POS, true));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
    }

    @Override
    public ResourceLocation getTexture() {
        return ((StoneBasicMachineTileEntity)this.getHolder()).getTexture();
    }
}

