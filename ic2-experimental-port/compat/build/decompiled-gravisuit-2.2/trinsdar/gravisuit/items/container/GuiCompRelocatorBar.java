/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  ic2.core.inventory.gui.IC2Screen
 *  ic2.core.inventory.gui.components.GuiWidget
 *  ic2.core.inventory.gui.components.GuiWidget$ActionRequest
 *  ic2.core.utils.helpers.StackUtil
 *  ic2.core.utils.math.geometry.Box2i
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package trinsdar.gravisuit.items.container;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import java.awt.Color;
import java.util.Arrays;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.network.PacketRelocator;
import trinsdar.gravisuit.util.GravisuitLang;

public class GuiCompRelocatorBar
extends GuiWidget {
    ItemStack relocator;
    Player player;
    int y;
    String name;
    InteractionHand hand;
    private final Box2i TEXT_BUTTON;
    private final Box2i DELETE_BUTTON;

    public GuiCompRelocatorBar(InteractionHand hand, int y, Player player, String name, ItemStack relocator) {
        super(new Box2i(3, 3 + y * 11, 170, 11));
        this.TEXT_BUTTON = new Box2i(4, 4 + y * 11, 156, 9);
        this.DELETE_BUTTON = new Box2i(163, 4 + y * 11, 9, 9);
        this.relocator = relocator;
        this.hand = hand;
        this.y = 3 + y * 18;
        this.name = name;
        this.player = player;
    }

    protected void addRequests(Set<GuiWidget.ActionRequest> set) {
        set.addAll(Arrays.asList(GuiWidget.ActionRequest.TOOLTIP, GuiWidget.ActionRequest.DRAW_BACKGROUND, GuiWidget.ActionRequest.MOUSE_INPUT, GuiWidget.ActionRequest.DRAW_FOREGROUND));
    }

    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return false;
        }
        if (this.TEXT_BUTTON.isInBox(mouseX, mouseY)) {
            ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(this.name);
            CompoundTag nbt = StackUtil.getNbtData((ItemStack)this.relocator);
            CompoundTag map = nbt.m_128469_("Locations");
            boolean successful = false;
            boolean close = false;
            if (map.m_128441_(this.name)) {
                successful = true;
                CompoundTag entry = map.m_128469_(this.name);
                location = ItemRelocator.TeleportData.fromNBT(entry, this.name);
            }
            if (successful) {
                boolean shift;
                boolean bl = shift = Screen.m_96638_() || nbt.m_128445_("mode") != 0;
                if (shift) {
                    GravisuitClassic.NETWORK.sendToServer((Object)new PacketRelocator(location, PacketRelocator.TeleportFunction.ADDDEFAULT, this.hand));
                    this.player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorAddDefaultTeleport, ChatFormatting.GREEN, new Object[]{Component.m_237113_((String)this.name).m_130940_(ChatFormatting.YELLOW)}), false);
                    nbt.m_128359_("DefaultLocation", this.name);
                } else {
                    GravisuitClassic.NETWORK.sendToServer((Object)new PacketRelocator(location, PacketRelocator.TeleportFunction.TELEPORT, this.hand));
                    close = true;
                }
            } else {
                this.player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorNotContainsTeleport, new ChatFormatting[]{ChatFormatting.RED}), false);
            }
            this.player.m_216990_(SoundEvents.f_12490_);
            if (close) {
                this.player.m_6915_();
            }
            return successful;
        }
        if (this.DELETE_BUTTON.isInBox(mouseX, mouseY)) {
            ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(this.name);
            CompoundTag nbt = StackUtil.getNbtData((ItemStack)this.relocator);
            CompoundTag map = nbt.m_128469_("Locations");
            boolean successful = map.m_128441_(this.name);
            if (successful) {
                GravisuitClassic.NETWORK.sendToServer((Object)new PacketRelocator(location, PacketRelocator.TeleportFunction.REMOVEDESTINATION, this.hand));
                this.player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorRemoveTeleport, ChatFormatting.GREEN, new Object[]{Component.m_237113_((String)this.name).m_130940_(ChatFormatting.YELLOW)}), false);
                map.m_128473_(this.name);
            } else {
                this.player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorNotContainsTeleport, new ChatFormatting[]{ChatFormatting.RED}), false);
            }
            this.player.m_216990_(SoundEvents.f_12490_);
            return successful;
        }
        return super.onMouseClick(mouseX, mouseY, mouseButton);
    }

    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        CompoundTag map;
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        CompoundTag nbt = StackUtil.getNbtData((ItemStack)this.relocator);
        int textureY = 116;
        if (nbt.m_128441_("DefaultLocation") && nbt.m_128461_("DefaultLocation").equals(this.name)) {
            textureY = 155;
        }
        if (this.TEXT_BUTTON.isInBox(mouseX, mouseY)) {
            textureY = 129;
        }
        if (this.DELETE_BUTTON.isInBox(mouseX, mouseY)) {
            textureY = 142;
        }
        if ((map = nbt.m_128469_("Locations")).m_128441_(this.name)) {
            this.gui.drawTextureRegion(matrix, (float)(x + this.box.getX()), (float)(y + this.box.getY()), 0.0f, (float)textureY, (float)this.box.getWidth(), (float)this.box.getHeight());
        }
    }

    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        CompoundTag nbt = StackUtil.getNbtData((ItemStack)this.relocator);
        CompoundTag map = nbt.m_128469_("Locations");
        if (map.m_128441_(this.name)) {
            this.gui.drawString(matrix, (Component)Component.m_237113_((String)this.name), 4, this.box.getY() + 2, Color.WHITE.getRGB());
        }
    }

    private int bX(IC2Screen gui, int position) {
        return gui.getGuiLeft() + position;
    }

    private int bY(IC2Screen gui, int position) {
        return gui.getGuiTop() + position;
    }

    public static boolean within(int value, int low, int high) {
        return value >= low && value <= high;
    }
}

