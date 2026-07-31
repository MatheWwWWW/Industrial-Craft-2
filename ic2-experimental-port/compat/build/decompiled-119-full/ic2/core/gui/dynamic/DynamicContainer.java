/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.gui.dynamic;

import ic2.core.ContainerBase;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.GuiEnvironment;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IHolographicSlotProvider;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotHologramSlot;
import ic2.core.slot.SlotInvSlot;
import ic2.core.util.Util;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DynamicContainer<T extends Container>
extends ContainerBase<T> {
    private static Map<Class<?>, List<String>> networkedFieldCache = new IdentityHashMap();
    final GuiParser.GuiNode guiNode;

    public static DynamicContainer<TileEntityInventory> create(int n, Inventory inventory, TileEntityInventory tileEntityInventory) {
        return new DynamicContainer<TileEntityInventory>(Ic2ScreenHandlers.DYNAMIC_BE, n, inventory, tileEntityInventory, GuiParser.parse(Util.getName(tileEntityInventory.getBlockType()), tileEntityInventory.getClass()));
    }

    public static <T extends Container> DynamicContainer<T> create(MenuType<DynamicContainer<T>> menuType, int n, Inventory inventory, T t, GuiParser.GuiNode guiNode) {
        return new DynamicContainer<T>(menuType, n, inventory, t, guiNode);
    }

    protected DynamicContainer(MenuType<DynamicContainer<T>> menuType, int n, Inventory inventory, T t, GuiParser.GuiNode guiNode) {
        super(menuType, n, inventory, t);
        this.guiNode = guiNode;
        this.initialize(inventory, guiNode, guiNode);
    }

    private void initialize(Inventory inventory, GuiParser.GuiNode guiNode, GuiParser.ParentNode parentNode) {
        block7: for (GuiParser.Node node : parentNode.getNodes()) {
            switch (node.getType()) {
                case environment: {
                    if (((GuiParser.EnvironmentNode)node).environment == GuiEnvironment.GAME) break;
                    continue block7;
                }
                case playerinventory: {
                    int n;
                    int n2;
                    GuiParser.Node node2 = (GuiParser.PlayerInventoryNode)node;
                    int n3 = (((GuiParser.PlayerInventoryNode)node2).style.width - 16) / 2;
                    int n4 = (((GuiParser.PlayerInventoryNode)node2).style.height - 16) / 2;
                    int n5 = ((GuiParser.PlayerInventoryNode)node2).style.width + ((GuiParser.PlayerInventoryNode)node2).spacing;
                    int n6 = ((GuiParser.PlayerInventoryNode)node2).style.height + ((GuiParser.PlayerInventoryNode)node2).spacing;
                    for (n2 = 0; n2 < 3; ++n2) {
                        for (n = 0; n < 9; ++n) {
                            this.m_38897_(new Slot((Container)inventory, n + n2 * 9 + 9, ((GuiParser.PlayerInventoryNode)node2).x + n * n5 + n3, ((GuiParser.PlayerInventoryNode)node2).y + n2 * n6 + n4));
                        }
                    }
                    for (n2 = 0; n2 < 9; ++n2) {
                        this.m_38897_(new Slot((Container)inventory, n2, ((GuiParser.PlayerInventoryNode)node2).x + n2 * n5 + n3, ((GuiParser.PlayerInventoryNode)node2).y + ((GuiParser.PlayerInventoryNode)node2).hotbarOffset + n4));
                    }
                    break;
                }
                case slot: {
                    if (!(this.base instanceof IInventorySlotHolder)) {
                        throw new RuntimeException("Invalid base " + this.base + " for slot elements");
                    }
                    GuiParser.Node node2 = (GuiParser.SlotNode)node;
                    InvSlot invSlot = ((IInventorySlotHolder)this.base).getInventorySlot(((GuiParser.SlotNode)node2).name);
                    if (invSlot == null) {
                        throw new RuntimeException("Invalid InvSlot name " + ((GuiParser.SlotNode)node2).name + " for base " + this.base);
                    }
                    int n4 = ((GuiParser.SlotNode)node2).x + (((GuiParser.SlotNode)node2).style.width - 16) / 2;
                    int n5 = ((GuiParser.SlotNode)node2).y + (((GuiParser.SlotNode)node2).style.height - 16) / 2;
                    this.m_38897_(new SlotInvSlot(invSlot, ((GuiParser.SlotNode)node2).index, n4, n5));
                    break;
                }
                case slotgrid: {
                    if (!(this.base instanceof IInventorySlotHolder)) {
                        throw new RuntimeException("Invalid base " + this.base + " for slot elements");
                    }
                    GuiParser.Node node2 = (GuiParser.SlotGridNode)node;
                    InvSlot invSlot = ((IInventorySlotHolder)this.base).getInventorySlot(((GuiParser.SlotGridNode)node2).name);
                    if (invSlot == null) {
                        throw new RuntimeException("Invalid InvSlot name " + ((GuiParser.SlotGridNode)node2).name + " for base " + this.base);
                    }
                    int n4 = invSlot.size();
                    if (n4 <= ((GuiParser.SlotGridNode)node2).offset) break;
                    int n5 = ((GuiParser.SlotGridNode)node2).x + (((GuiParser.SlotGridNode)node2).style.width - 16) / 2;
                    int n6 = ((GuiParser.SlotGridNode)node2).y + (((GuiParser.SlotGridNode)node2).style.height - 16) / 2;
                    GuiParser.SlotGridNode.SlotGridDimension slotGridDimension = ((GuiParser.SlotGridNode)node2).getDimension(n4);
                    int n = slotGridDimension.rows;
                    int n7 = slotGridDimension.cols;
                    int n8 = ((GuiParser.SlotGridNode)node2).style.width + ((GuiParser.SlotGridNode)node2).spacing;
                    int n9 = ((GuiParser.SlotGridNode)node2).style.height + ((GuiParser.SlotGridNode)node2).spacing;
                    int n10 = ((GuiParser.SlotGridNode)node2).offset;
                    if (!((GuiParser.SlotGridNode)node2).vertical) {
                        var17_20 = n6;
                        for (var18_21 = 0; var18_21 < n && n10 < n4; ++var18_21) {
                            var19_22 = n5;
                            for (var20_23 = 0; var20_23 < n7 && n10 < n4; ++var20_23) {
                                this.m_38897_(new SlotInvSlot(invSlot, n10, var19_22, var17_20));
                                ++n10;
                                var19_22 += n8;
                            }
                            var17_20 += n9;
                        }
                    } else {
                        var17_20 = n5;
                        for (var18_21 = 0; var18_21 < n7 && n10 < n4; ++var18_21) {
                            var19_22 = n6;
                            for (var20_23 = 0; var20_23 < n && n10 < n4; ++var20_23) {
                                this.m_38897_(new SlotInvSlot(invSlot, n10, var17_20, var19_22));
                                ++n10;
                                var19_22 += n9;
                            }
                            var17_20 += n8;
                        }
                    }
                    break;
                }
                case slothologram: {
                    if (!(this.base instanceof IHolographicSlotProvider)) {
                        throw new RuntimeException("Invalid base " + this.base + " for holographic slot elements");
                    }
                    GuiParser.Node node2 = (GuiParser.SlotHologramNode)node;
                    int n3 = ((GuiParser.SlotHologramNode)node2).x + (((GuiParser.SlotHologramNode)node2).style.width - 16) / 2;
                    int n4 = ((GuiParser.SlotHologramNode)node2).y + (((GuiParser.SlotHologramNode)node2).style.height - 16) / 2;
                    this.m_38897_(new SlotHologramSlot(((IHolographicSlotProvider)this.base).getStacksForName(((GuiParser.SlotHologramNode)node2).name), ((GuiParser.SlotHologramNode)node2).index, n3, n4, ((GuiParser.SlotHologramNode)node2).stackSizeLimit, this.getCallback()));
                    break;
                }
            }
            if (!(node instanceof GuiParser.ParentNode)) continue;
            this.initialize(inventory, guiNode, (GuiParser.ParentNode)node);
        }
    }

    protected SlotHologramSlot.ChangeCallback getCallback() {
        return null;
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = networkedFieldCache.get(this.base.getClass());
        if (list != null) {
            return list;
        }
        list = new ArrayList<String>();
        Class<?> clazz = this.base.getClass();
        do {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getAnnotation(GuiSynced.class) == null) continue;
                list.add(field.getName());
            }
        } while ((clazz = clazz.getSuperclass()) != BlockEntity.class && clazz != Object.class);
        list = list.isEmpty() ? Collections.emptyList() : new ArrayList<String>(list);
        networkedFieldCache.put(this.base.getClass(), list);
        return list;
    }
}

