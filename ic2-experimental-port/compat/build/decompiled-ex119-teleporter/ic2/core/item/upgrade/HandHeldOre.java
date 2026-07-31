/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.item.upgrade;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.ContainerBase;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiDefaultBackground;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollableList;
import ic2.core.gui.SlotGrid;
import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.item.upgrade.HandHeldAdvancedUpgrade;
import ic2.core.item.upgrade.HandHeldUpgradeOption;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotHologramSlot;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class HandHeldOre
extends HandHeldUpgradeOption {
    public HandHeldOre(HandHeldAdvancedUpgrade handHeldAdvancedUpgrade) {
        super(handHeldAdvancedUpgrade, "ore");
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerEditOre(n);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerEditOre(n);
    }

    public class ContainerEditOre
    extends ContainerHandHeldInventory<HandHeldOre> {
        static final int HEIGHT = 200;

        public ContainerEditOre(int n) {
            super(Ic2ScreenHandlers.ADVANCED_UPGRADE_EDIT_ORE, n, HandHeldOre.this);
            this.addPlayerInventorySlots(HandHeldOre.this.player.m_150109_(), 200);
            for (int n2 = 0; n2 < 9; n2 = (int)((byte)(n2 + 1))) {
                this.m_38897_(new SlotHologramSlot(HandHeldOre.this.inventory, n2, 8 + 18 * n2, 8, 1, HandHeldOre.this.makeSaveCallback()));
            }
        }

        @Override
        public void m_6877_(Player player) {
            super.m_6877_(player);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class GuiEditOre
    extends GuiDefaultBackground<ContainerEditOre> {
        public GuiEditOre(ContainerEditOre containerEditOre, Inventory inventory, Component component) {
            super(containerEditOre, inventory, component, 200);
            this.addElement(((HandHeldOre)containerEditOre.base).getBackButton(this, 10, 96));
            ArrayList<ScrollableList.IListItem> arrayList = new ArrayList<ScrollableList.IListItem>();
            for (String string : new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"}) {
                arrayList.add(new ListItem(string));
            }
            this.addElement(new ScrollableList((Ic2Gui<?>)this, 10, 30, 120, 60, (List<ScrollableList.IListItem>)arrayList));
            this.addElement(new SlotGrid(this, 7, 7, 9, 1, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 117, 9, 3, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 175, 9, 1, SlotGrid.SlotStyle.Normal));
        }

        public class ListItem
        implements ScrollableList.IListItem {
            private final String number;

            public ListItem(String string) {
                this.number = string;
            }

            @Override
            public void draw(PoseStack poseStack, int n, int n2, int n3, int n4, int n5, int n6) {
                GuiEditOre.this.drawString(poseStack, n + 2, n2 + 1, "Thing " + this.number, 0xFFFFFF, false);
            }

            @Override
            public boolean onClick(MouseButton mouseButton, int n, int n2) {
                System.out.println(this.number + " clicked with " + mouseButton);
                return false;
            }
        }
    }
}

