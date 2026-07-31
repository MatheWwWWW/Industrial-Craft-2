/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Matrix3f
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.gui.Font
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.infos.components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IMonitorRenderer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InventoryDisplayInfo
implements IDisplayInfo {
    Supplier<IHasInventory> inventoryProvider;
    Supplier<InvSettings> settingsProvider;
    BooleanSupplier aliveProvider;
    List<RenderObject> inv;
    InvSettings settings;

    public InventoryDisplayInfo(Supplier<IHasInventory> inventoryProvider, Supplier<InvSettings> settingsProvider, BooleanSupplier aliveProvider) {
        this.inventoryProvider = inventoryProvider;
        this.settingsProvider = settingsProvider;
        this.aliveProvider = aliveProvider;
    }

    public InventoryDisplayInfo(FriendlyByteBuf buffer) {
        this.inv = new ObjectArrayList();
        this.settings = new InvSettings(buffer);
        int count = buffer.m_130242_();
        for (int i = 0; i < count; ++i) {
            ItemStack stack = buffer.m_130267_();
            stack.m_41764_(buffer.m_130242_());
            this.inv.add(new RenderObject(stack));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack stack, int x, int y, int width, int height, IDisplayInfo.Alignment align, IMonitorRenderer helper) {
        stack.m_85836_();
        stack.m_85850_().m_85864_().m_8169_(Matrix3f.m_8174_((float)1.0f, (float)-1.0f, (float)1.0f));
        float xOff = 0.0f;
        float yOff = 0.0f;
        float objHeight = 0.0f;
        Font font = helper.getFont();
        if (align == IDisplayInfo.Alignment.CENTER) {
            int i;
            int m = this.inv.size();
            for (i = 0; i < m; ++i) {
                this.inv.get(i).computeBounds(font, this.settings);
            }
            m = this.inv.size();
            for (i = 0; i < m; ++i) {
                RenderObject obj = this.inv.get(i);
                objHeight = Math.max(objHeight, obj.height);
                if (obj.width + xOff > (float)width) {
                    xOff = 0.0f;
                    yOff += objHeight;
                    objHeight = 0.0f;
                    if (yOff >= (float)height) break;
                }
                helper.renderGuiItems(stack, obj.item, xOff + obj.itemOff, (float)y + yOff);
                if (this.settings.showCount) {
                    stack.m_85836_();
                    helper.renderGuiItemText(stack, obj.item, 0.0f, 0.0f, this.calculateTextScale(obj.item, stack, xOff + obj.itemOff, (float)y + yOff, obj.text));
                    stack.m_85849_();
                }
                if (this.settings.showCountSide || this.settings.showName) {
                    stack.m_85836_();
                    stack.m_85841_(0.75f, 0.75f, 0.75f);
                    stack.m_85837_((double)((xOff + obj.width * 0.5f) * 1.34f), (double)((float)y + yOff * 1.34f + 4.0f), 0.0);
                    float base = 20.0f;
                    if (this.settings.showCountSide) {
                        helper.getFont().m_92883_(stack, obj.count, (float)(-font.m_92895_(obj.count)) * 0.5f, base, -1);
                        base += 10.0f;
                    }
                    if (this.settings.showName) {
                        helper.getFont().m_92889_(stack, obj.name, (float)(-font.m_92852_((FormattedText)obj.name)) * 0.5f, base, -1);
                    }
                    stack.m_85849_();
                }
                xOff += obj.width;
            }
        } else {
            int count = height / 18;
            boolean inverted = align == IDisplayInfo.Alignment.RIGHT;
            int m = this.inv.size();
            for (int i = 0; i < count && i < m; ++i) {
                RenderObject obj = this.inv.get(i);
                int realY = y + i * 18;
                helper.renderGuiItems(stack, obj.item, x - (inverted ? 18 : 0), realY);
                if (this.settings.showCount) {
                    stack.m_85836_();
                    helper.renderGuiItemText(stack, obj.item, 0.0f, 0.0f, this.calculateTextScale(obj.item, stack, x - (inverted ? 18 : 0), realY, obj.text));
                    stack.m_85849_();
                }
                if (!this.settings.showCountSide && !this.settings.showName) continue;
                stack.m_85836_();
                stack.m_85841_(0.75f, 0.75f, 0.75f);
                stack.m_85837_(inverted ? (double)((float)(x - 21) * 1.34f) : (double)((float)x + 26.800001f), (double)((float)realY * 1.34f + 2.0f), 0.0);
                float base = 0.0f;
                if (this.settings.showName) {
                    font.m_92889_(stack, obj.name, inverted ? (float)(-font.m_92852_((FormattedText)obj.name)) : 0.0f, base, -1);
                    base += 10.0f;
                }
                if (this.settings.showCountSide) {
                    font.m_92883_(stack, obj.count, inverted ? (float)(-font.m_92895_(obj.count)) : 0.0f, base, -1);
                }
                stack.m_85849_();
            }
        }
        stack.m_85849_();
    }

    @OnlyIn(value=Dist.CLIENT)
    private String calculateTextScale(ItemStack stack, PoseStack matrix, float xOff, float zOff, String s) {
        int length = s.length() - 2;
        if (length >= 2) {
            matrix.m_85841_(0.5f, 0.5f, 0.0f);
            matrix.m_85837_((double)(xOff * 2.0f + 14.0f), (double)(zOff * 2.0f + 13.0f), 0.0);
        } else if (length == 0) {
            matrix.m_85841_(0.75f, 0.75f, 0.75f);
            matrix.m_85837_((double)(xOff * 1.34f + 3.0f), (double)(zOff * 1.34f + 4.0f), 0.0);
        } else {
            matrix.m_85837_((double)xOff, (double)zOff, 0.0);
        }
        return s;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getHeight(int width, IDisplayInfo.Alignment align) {
        if (align == IDisplayInfo.Alignment.CENTER) {
            float xOff = 0.0f;
            float yOff = 0.0f;
            float objHeight = 0.0f;
            int m = this.inv.size();
            for (int i = 0; i < m; ++i) {
                RenderObject obj = this.inv.get(i);
                objHeight = Math.max(objHeight, obj.height);
                if (!(obj.width + xOff > (float)width)) continue;
                xOff = 0.0f;
                yOff += objHeight;
                objHeight = 0.0f;
            }
            return (int)yOff;
        }
        return this.inv.size() * 18;
    }

    @Override
    public boolean isValid() {
        return this.aliveProvider.getAsBoolean();
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        ItemStack stack;
        IHasInventory inventory = this.inventoryProvider.get();
        InvSettings settings = this.settingsProvider.get();
        ObjectArrayList list = new ObjectArrayList();
        if (settings.mergeInv) {
            Object2IntLinkedOpenHashMap map = new Object2IntLinkedOpenHashMap();
            Object2ObjectMap<Item, ItemStack> refMap = CollectionUtils.createMap();
            int m = inventory.getSlotCount();
            for (int i = 0; i < m; ++i) {
                ItemStack stack2 = inventory.getStackInSlot(i);
                if (stack2.m_41619_()) continue;
                map.addTo((Object)stack2.m_41720_(), stack2.m_41613_());
                refMap.putIfAbsent(stack2.m_41720_(), stack2);
            }
            for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable((Object2IntMap)map)) {
                list.add(StackUtil.copyWithSize((ItemStack)refMap.get(entry.getKey()), entry.getIntValue()));
            }
        } else {
            int m = inventory.getSlotCount();
            for (int i = 0; i < m; ++i) {
                stack = inventory.getStackInSlot(i);
                if (stack.m_41619_()) continue;
                list.add(stack);
            }
        }
        settings.serialize(buffer);
        buffer.m_130130_(list.size());
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            stack = (ItemStack)list.get(i);
            buffer.m_130055_(stack);
            buffer.m_130130_(stack.m_41613_());
        }
    }

    @Override
    public Tag getServerData() {
        IHasInventory inventory = this.inventoryProvider.get();
        ListTag list = new ListTag();
        int m = inventory.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            CompoundTag data = stack.m_41739_(new CompoundTag());
            data.m_128405_("Count", stack.m_41613_());
            list.add((Object)data);
        }
        CompoundTag data = new CompoundTag();
        if (!list.isEmpty()) {
            data.m_128365_("items", (Tag)list);
        }
        return data;
    }

    public static class InvSettings {
        public boolean showCount;
        public boolean showCountSide;
        public boolean showName;
        public boolean mergeInv;

        public InvSettings(boolean showCount, boolean showCountSide, boolean showName, boolean mergeInv) {
            this.showCount = showCount;
            this.showCountSide = showCountSide;
            this.showName = showName;
            this.mergeInv = mergeInv;
        }

        public InvSettings(FriendlyByteBuf buffer) {
            this(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean());
        }

        public void serialize(FriendlyByteBuf buffer) {
            buffer.writeBoolean(this.showCount);
            buffer.writeBoolean(this.showCountSide);
            buffer.writeBoolean(this.showName);
            buffer.writeBoolean(this.mergeInv);
        }
    }

    public static class RenderObject {
        public ItemStack item;
        public String text;
        public String count;
        public Component name;
        public float width;
        public float height;
        public float itemOff;

        public RenderObject(ItemStack stack) {
            this.item = stack;
            this.text = this.generateText(stack);
            this.count = Integer.toString(stack.m_41613_());
            this.name = stack.m_41786_();
        }

        @OnlyIn(value=Dist.CLIENT)
        public void computeBounds(Font font, InvSettings setting) {
            float f;
            float f2;
            if (setting.showCountSide) {
                Objects.requireNonNull(font);
                f2 = 9.0f * 0.75f;
            } else {
                f2 = 0.0f;
            }
            float f3 = 18.0f + f2;
            if (setting.showName) {
                Objects.requireNonNull(font);
                f = 9.0f * 0.75f;
            } else {
                f = 0.0f;
            }
            this.height = f3 + f;
            this.width = 18.0f;
            if (setting.showCountSide) {
                this.width = Math.max(this.width, (float)font.m_92895_(this.count) * 0.75f + 4.0f);
            }
            if (setting.showName) {
                this.width = Math.max(this.width, (float)font.m_92852_((FormattedText)this.name) * 0.75f + 4.0f);
            }
            this.itemOff = this.width / 2.0f - 8.0f;
        }

        private String generateText(ItemStack stack) {
            int count = stack.m_41613_();
            if (count >= 2000000) {
                return count / 1000000 + "m";
            }
            if (count >= 2000) {
                return count / 1000 + "k";
            }
            if (count > 1) {
                return String.valueOf(count);
            }
            return "";
        }
    }
}

