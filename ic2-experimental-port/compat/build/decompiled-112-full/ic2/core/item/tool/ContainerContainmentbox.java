/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.item.tool;

import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.item.tool.HandHeldContainmentbox;
import ic2.core.slot.SlotRadioactive;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerContainmentbox
extends ContainerHandHeldInventory<HandHeldContainmentbox> {
    protected static final int height = 166;

    public ContainerContainmentbox(EntityPlayer player, HandHeldContainmentbox box) {
        super(box);
        int i;
        for (i = 0; i < 4; ++i) {
            this.func_75146_a(new SlotRadioactive(box, i, 53 + i * 18, 19));
        }
        for (i = 4; i < 8; ++i) {
            this.func_75146_a(new SlotRadioactive(box, i, 53 + (i - 4) * 18, 37));
        }
        for (i = 8; i < 12; ++i) {
            this.func_75146_a(new SlotRadioactive(box, i, 53 + (i - 8) * 18, 55));
        }
        this.addPlayerInventorySlots(player, 166);
    }
}

