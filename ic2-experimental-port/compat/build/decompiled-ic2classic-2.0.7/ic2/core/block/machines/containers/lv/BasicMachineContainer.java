/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.containers.lv;

import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.block.machines.tiles.lv.RecyclerTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.inventory.slot.XPSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BasicMachineContainer
extends ContainerComponent<BasicMachineTileEntity> {
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static final Box2i CHARGE_BOX = new Box2i(56, 36, 14, 14);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i PROGRESS_BOX = new Box2i(79, 34, 24, 16);

    public BasicMachineContainer(BasicMachineTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 56, 53));
        this.m_38897_(new FilterSlot(key, 1, 56, 17, T -> key.getRecipeList().getRecipe(T, false) != null));
        this.m_38897_(new XPSlot(key, 2, 116, 35));
        for (int i = 0; i < key.upgradeSlots; ++i) {
            this.m_38897_(new UpgradeSlot(key, 3 + i, 152, 8 + i * 18));
        }
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
    }

    @Override
    public ResourceLocation getTexture() {
        return ((BasicMachineTileEntity)this.getHolder()).getTexture();
    }

    @Override
    protected boolean m_38903_(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        if (this.getHolder() instanceof RecyclerTileEntity) {
            return this.moveItemStackToPriorizeUpgradeSlots(stack, startIndex, endIndex, reverseDirection);
        }
        return super.m_38903_(stack, startIndex, endIndex, reverseDirection);
    }
}

