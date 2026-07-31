/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.mojang.blaze3d.vertex.PoseStack
 *  ic2.core.inventory.gui.IC2Screen
 *  ic2.core.inventory.gui.components.GuiWidget
 *  ic2.core.inventory.gui.components.GuiWidget$ActionRequest
 *  ic2.core.utils.helpers.StackUtil
 *  ic2.core.utils.math.geometry.Box2i
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package trinsdar.gravisuit.items.container;

import com.google.common.base.Strings;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Arrays;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.core.BlockPos;
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

public class GuiCompRelocatorAdd
extends GuiWidget {
    ItemStack relocator;
    Player player;
    InteractionHand hand;
    int y;
    private Box2i BOX = new Box2i(59, 43, 58, 13);
    private EditBox textBox;
    String name;

    public GuiCompRelocatorAdd(InteractionHand hand, Player player) {
        super(new Box2i(59, 43, 58, 13));
        this.relocator = player.m_21120_(hand);
        this.hand = hand;
        this.y = 18;
        this.player = player;
    }

    protected void addRequests(Set<GuiWidget.ActionRequest> set) {
        set.addAll(Arrays.asList(GuiWidget.ActionRequest.GUI_INIT, GuiWidget.ActionRequest.MOUSE_INPUT, GuiWidget.ActionRequest.TOOLTIP, GuiWidget.ActionRequest.KEY_INPUT, GuiWidget.ActionRequest.DRAW_BACKGROUND));
    }

    public void init(IC2Screen gui) {
        super.init(gui);
        this.textBox = new EditBox(gui.getFont(), this.bX(gui, 14), this.bY(gui, 19), 148, 16, (Component)Component.m_237119_());
        this.textBox.m_94199_(32500);
        gui.m_142416_((GuiEventListener)this.textBox);
    }

    public boolean isMouseOver(int x, int y) {
        return super.isMouseOver(x, y);
    }

    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.isWithinSetButton(mouseX, mouseY) && mouseButton == 0) {
            if (!Strings.isNullOrEmpty((String)this.textBox.m_94155_())) {
                CompoundTag nbt = StackUtil.getNbtData((ItemStack)this.relocator);
                long pos = new BlockPos(this.player.m_20182_()).m_121878_();
                String dimId = this.player.m_9236_().m_46472_().m_135782_().toString();
                String name = this.textBox.m_94155_();
                ItemRelocator.TeleportData location = new ItemRelocator.TeleportData(pos, dimId, name);
                CompoundTag map = nbt.m_128469_("Locations");
                boolean successful = false;
                if (map.m_128440_() < 11) {
                    successful = true;
                }
                if (map.m_128441_(name)) {
                    this.player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorContainsTeleport, new ChatFormatting[]{ChatFormatting.RED}), false);
                    this.player.m_216990_(SoundEvents.f_12490_);
                    this.player.m_6915_();
                    return true;
                }
                if (successful) {
                    GravisuitClassic.NETWORK.sendToServer((Object)new PacketRelocator(location, PacketRelocator.TeleportFunction.ADDDESTINATION, this.hand));
                    this.player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorAddTeleport, ChatFormatting.GREEN, new Object[]{Component.m_237113_((String)name).m_130940_(ChatFormatting.YELLOW)}), false);
                } else {
                    this.player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorMaxTeleport, new ChatFormatting[]{ChatFormatting.RED}), false);
                }
                this.player.m_216990_(SoundEvents.f_12490_);
                this.player.m_6915_();
                return true;
            }
            this.player.m_216990_(SoundEvents.f_12490_);
        }
        if (this.isWithinClearButton(mouseX, mouseY) && mouseButton == 0) {
            this.textBox.m_94144_("");
            this.player.m_216990_(SoundEvents.f_12490_);
        }
        return super.onMouseClick(mouseX, mouseY, mouseButton);
    }

    private boolean isWithinSetButton(int mouseX, int mouseY) {
        return GuiCompRelocatorAdd.within(mouseX, this.BOX.getX() + 1, this.BOX.getX() + 25) && GuiCompRelocatorAdd.within(mouseY, this.BOX.getY() + 1, this.BOX.getY() + 12);
    }

    private boolean isWithinClearButton(int mouseX, int mouseY) {
        return GuiCompRelocatorAdd.within(mouseX, this.BOX.getX() + 33, this.BOX.getX() + 57) && GuiCompRelocatorAdd.within(mouseY, this.BOX.getY() + 1, this.BOX.getY() + 12);
    }

    public boolean onKeyTyped(int keyCode) {
        int keyCompare = this.gui.getMinecraft().f_91066_.f_92092_.getKey().m_84873_();
        if (keyCompare == keyCode & this.textBox.m_93696_()) {
            return true;
        }
        return super.onKeyTyped(keyCode);
    }

    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        if (this.isWithinSetButton(mouseX, mouseY)) {
            this.gui.drawTextureRegion(matrix, (float)this.bX(this.gui, this.BOX.getX() + 1), (float)this.bY(this.gui, this.BOX.getY() + 1), 177.0f, 1.0f, 24.0f, 11.0f);
        }
        if (this.isWithinClearButton(mouseX, mouseY)) {
            this.gui.drawTextureRegion(matrix, (float)this.bX(this.gui, this.BOX.getX() + 33), (float)this.bY(this.gui, this.BOX.getY() + 1), 209.0f, 1.0f, 24.0f, 11.0f);
        }
        super.drawBackground(matrix, mouseX, mouseY, partialTicks);
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

