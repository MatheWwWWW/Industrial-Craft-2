/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.tileentity.TileEntity
 */
package ic2.core.gui.dynamic;

import ic2.core.ContainerBase;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.gui.dynamic.GuiEnvironment;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IHolographicSlotProvider;
import ic2.core.network.GuiSynced;
import ic2.core.slot.SlotHologramSlot;
import ic2.core.slot.SlotInvSlot;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class DynamicContainer<T extends IInventory>
extends ContainerBase<T> {
    private static Map<Class<?>, List<String>> networkedFieldCache = new IdentityHashMap();

    public static <T extends IInventory> DynamicContainer<T> create(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
        return new DynamicContainer<T>(base, player, guiNode);
    }

    protected DynamicContainer(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
        super(base);
        this.initialize(player, guiNode, guiNode);
    }

    private void initialize(EntityPlayer player, GuiParser.GuiNode guiNode, GuiParser.ParentNode parentNode) {
        block7: for (GuiParser.Node rawNode : parentNode.getNodes()) {
            switch (rawNode.getType()) {
                case environment: {
                    if (((GuiParser.EnvironmentNode)rawNode).environment == GuiEnvironment.GAME) break;
                    continue block7;
                }
                case playerinventory: {
                    GuiParser.Node node = (GuiParser.PlayerInventoryNode)rawNode;
                    int xOffset = (((GuiParser.PlayerInventoryNode)node).style.width - 16) / 2;
                    int yOffset = (((GuiParser.PlayerInventoryNode)node).style.height - 16) / 2;
                    int width = ((GuiParser.PlayerInventoryNode)node).style.width + ((GuiParser.PlayerInventoryNode)node).spacing;
                    int height = ((GuiParser.PlayerInventoryNode)node).style.height + ((GuiParser.PlayerInventoryNode)node).spacing;
                    for (int row = 0; row < 3; ++row) {
                        for (int col = 0; col < 9; ++col) {
                            this.func_75146_a(new Slot((IInventory)player.field_71071_by, col + row * 9 + 9, ((GuiParser.PlayerInventoryNode)node).x + col * width + xOffset, ((GuiParser.PlayerInventoryNode)node).y + row * height + yOffset));
                        }
                    }
                    for (int col = 0; col < 9; ++col) {
                        this.func_75146_a(new Slot((IInventory)player.field_71071_by, col, ((GuiParser.PlayerInventoryNode)node).x + col * width + xOffset, ((GuiParser.PlayerInventoryNode)node).y + ((GuiParser.PlayerInventoryNode)node).hotbarOffset + yOffset));
                    }
                    break;
                }
                case slot: {
                    if (!(this.base instanceof IInventorySlotHolder)) {
                        throw new RuntimeException("Invalid base " + this.base + " for slot elements");
                    }
                    GuiParser.Node node = (GuiParser.SlotNode)rawNode;
                    InvSlot slot = ((IInventorySlotHolder)this.base).getInventorySlot(((GuiParser.SlotNode)node).name);
                    if (slot == null) {
                        throw new RuntimeException("Invalid InvSlot name " + ((GuiParser.SlotNode)node).name + " for base " + this.base);
                    }
                    int x = ((GuiParser.SlotNode)node).x + (((GuiParser.SlotNode)node).style.width - 16) / 2;
                    int y = ((GuiParser.SlotNode)node).y + (((GuiParser.SlotNode)node).style.height - 16) / 2;
                    this.func_75146_a(new SlotInvSlot(slot, ((GuiParser.SlotNode)node).index, x, y));
                    break;
                }
                case slotgrid: {
                    if (!(this.base instanceof IInventorySlotHolder)) {
                        throw new RuntimeException("Invalid base " + this.base + " for slot elements");
                    }
                    GuiParser.Node node = (GuiParser.SlotGridNode)rawNode;
                    InvSlot slot = ((IInventorySlotHolder)this.base).getInventorySlot(((GuiParser.SlotGridNode)node).name);
                    if (slot == null) {
                        throw new RuntimeException("Invalid InvSlot name " + ((GuiParser.SlotGridNode)node).name + " for base " + this.base);
                    }
                    int size = slot.size();
                    if (size <= ((GuiParser.SlotGridNode)node).offset) break;
                    int x0 = ((GuiParser.SlotGridNode)node).x + (((GuiParser.SlotGridNode)node).style.width - 16) / 2;
                    int y0 = ((GuiParser.SlotGridNode)node).y + (((GuiParser.SlotGridNode)node).style.height - 16) / 2;
                    GuiParser.SlotGridNode.SlotGridDimension dim = ((GuiParser.SlotGridNode)node).getDimension(size);
                    int rows = dim.rows;
                    int cols = dim.cols;
                    int width = ((GuiParser.SlotGridNode)node).style.width + ((GuiParser.SlotGridNode)node).spacing;
                    int height = ((GuiParser.SlotGridNode)node).style.height + ((GuiParser.SlotGridNode)node).spacing;
                    int idx = ((GuiParser.SlotGridNode)node).offset;
                    if (!((GuiParser.SlotGridNode)node).vertical) {
                        int y = y0;
                        for (int row = 0; row < rows && idx < size; ++row) {
                            int x = x0;
                            for (int col = 0; col < cols && idx < size; ++col) {
                                this.func_75146_a(new SlotInvSlot(slot, idx, x, y));
                                ++idx;
                                x += width;
                            }
                            y += height;
                        }
                    } else {
                        int x = x0;
                        for (int col = 0; col < cols && idx < size; ++col) {
                            int y = y0;
                            for (int row = 0; row < rows && idx < size; ++row) {
                                this.func_75146_a(new SlotInvSlot(slot, idx, x, y));
                                ++idx;
                                y += height;
                            }
                            x += width;
                        }
                    }
                    break;
                }
                case slothologram: {
                    if (!(this.base instanceof IHolographicSlotProvider)) {
                        throw new RuntimeException("Invalid base " + this.base + " for holographic slot elements");
                    }
                    GuiParser.Node node = (GuiParser.SlotHologramNode)rawNode;
                    int x = ((GuiParser.SlotHologramNode)node).x + (((GuiParser.SlotHologramNode)node).style.width - 16) / 2;
                    int y = ((GuiParser.SlotHologramNode)node).y + (((GuiParser.SlotHologramNode)node).style.height - 16) / 2;
                    this.func_75146_a(new SlotHologramSlot(((IHolographicSlotProvider)this.base).getStacksForName(((GuiParser.SlotHologramNode)node).name), ((GuiParser.SlotHologramNode)node).index, x, y, ((GuiParser.SlotHologramNode)node).stackSizeLimit, this.getCallback()));
                    break;
                }
            }
            if (!(rawNode instanceof GuiParser.ParentNode)) continue;
            this.initialize(player, guiNode, (GuiParser.ParentNode)rawNode);
        }
    }

    protected SlotHologramSlot.ChangeCallback getCallback() {
        return null;
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = networkedFieldCache.get(this.base.getClass());
        if (ret != null) {
            return ret;
        }
        ret = new ArrayList<String>();
        Class<?> cls = this.base.getClass();
        do {
            for (Field field : cls.getDeclaredFields()) {
                if (field.getAnnotation(GuiSynced.class) == null) continue;
                ret.add(field.getName());
            }
        } while ((cls = cls.getSuperclass()) != TileEntity.class && cls != Object.class);
        ret = ret.isEmpty() ? Collections.emptyList() : new ArrayList<String>(ret);
        networkedFieldCache.put(this.base.getClass(), ret);
        return ret;
    }
}

