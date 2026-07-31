/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.containers.lv;

import ic2.api.tiles.readers.IEUStorage;
import ic2.core.IC2;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ElectrolyzerContainer<T extends BaseInventoryTileEntity & IEUStorage>
extends ContainerComponent<T> {
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i PROGRESS_BOX = new Box2i(79, 34, 24, 16);
    ResourceLocation texture;

    public ElectrolyzerContainer(T key, Player player, int id, ResourceLocation texture) {
        super(key, player, id);
        this.texture = texture;
        this.m_38897_(new FilterSlot((IHasInventory)key, 0, 54, 35, T -> this.isValidItem(T, true)));
        this.m_38897_(new FilterSlot((IHasInventory)key, 1, 112, 35, T -> this.isValidItem(T, false)));
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(PROGRESS_BOX, (IEUStorage)key, PROGRESS_POS, false));
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }

    public boolean isValidItem(ItemStack stack, boolean input) {
        return IC2.RECIPES.get().electrolyzer.getRecipe(stack, input, false) != null;
    }
}

