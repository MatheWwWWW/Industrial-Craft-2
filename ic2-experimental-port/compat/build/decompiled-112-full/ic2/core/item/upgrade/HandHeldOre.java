/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.upgrade;

import ic2.core.ContainerBase;
import ic2.core.gui.GuiDefaultBackground;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollableList;
import ic2.core.gui.SlotGrid;
import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.item.upgrade.HandHeldAdvancedUpgrade;
import ic2.core.item.upgrade.HandHeldUpgradeOption;
import ic2.core.slot.SlotHologramSlot;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandHeldOre
extends HandHeldUpgradeOption {
    public HandHeldOre(HandHeldAdvancedUpgrade upgradeGUI) {
        super(upgradeGUI, "ore");
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerEditOre();
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiEditOre();
    }

    @SideOnly(value=Side.CLIENT)
    public class GuiEditOre
    extends GuiDefaultBackground<ContainerEditOre> {
        public GuiEditOre() {
            super(new ContainerEditOre(), 200);
            this.addElement(HandHeldOre.this.getBackButton(this, 10, 96));
            ArrayList<ScrollableList.IListItem> items = new ArrayList<ScrollableList.IListItem>();
            for (String name : new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"}) {
                items.add(new ListItem(name));
            }
            this.addElement(new ScrollableList(this, 10, 30, 120, 60, items));
            this.addElement(new SlotGrid(this, 7, 7, 9, 1, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 117, 9, 3, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 175, 9, 1, SlotGrid.SlotStyle.Normal));
        }

        public class ListItem
        implements ScrollableList.IListItem {
            private final String number;

            public ListItem(String number) {
                this.number = number;
            }

            @Override
            public void onClick(MouseButton button) {
                System.out.println(this.number + " clicked with " + (Object)((Object)button));
            }

            public String func_176610_l() {
                return "Thing " + this.number;
            }
        }
    }

    public class ContainerEditOre
    extends ContainerHandHeldInventory<HandHeldOre> {
        static final int HEIGHT = 200;

        public ContainerEditOre() {
            super(HandHeldOre.this);
            this.addPlayerInventorySlots(HandHeldOre.this.player, 200);
            for (int slot = 0; slot < 9; slot = (int)((byte)(slot + 1))) {
                this.func_75146_a(new SlotHologramSlot(HandHeldOre.this.inventory, slot, 8 + 18 * slot, 8, 1, HandHeldOre.this.makeSaveCallback()));
            }
        }

        @Override
        public void func_75134_a(EntityPlayer player) {
            super.func_75134_a(player);
        }
    }
}

