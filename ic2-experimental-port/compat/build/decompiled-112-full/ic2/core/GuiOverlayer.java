/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Post
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package ic2.core;

import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.api.item.IItemHudProvider;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.LinkedList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GuiOverlayer
extends Gui {
    private final Minecraft mc;
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIOverlay.png");

    public GuiOverlayer(Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onRenderHotBar(RenderGameOverlayEvent.Post event) {
        int charge;
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        ItemStack helm = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.HEAD);
        if (StackUtil.isEmpty(helm) || !(helm.func_77973_b() instanceof IItemHudProvider) || !((IItemHudProvider)helm.func_77973_b()).doesProvideHUD(helm)) {
            return;
        }
        HudMode hudMode = ((IItemHudProvider)helm.func_77973_b()).getHudMode(helm);
        if (!hudMode.shouldDisplay()) {
            return;
        }
        ItemStack boots = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.FEET);
        ItemStack legs = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.LEGS);
        ItemStack chestplate = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)2896);
        RenderItem renderItem = this.mc.func_175599_af();
        RenderHelper.func_74520_c();
        this.mc.func_110434_K().func_110577_a(background);
        this.func_73729_b(0, 0, 0, 0, 71, 69);
        renderItem.func_175042_a(helm, 5, 4);
        this.mc.field_71466_p.func_78276_b(GuiOverlayer.mapCharge(helm) + "%", 25, 9, 0xFFFFFF);
        if (StackUtil.getOrCreateNbtData(helm).func_74767_n("Nightvision")) {
            renderItem.func_175042_a(ItemName.nightvision_goggles.getItemStack(), 50, 4);
        }
        if (!StackUtil.isEmpty(chestplate) && (charge = GuiOverlayer.getCharge(chestplate)) >= 0) {
            this.mc.field_71466_p.func_78276_b(charge + "%", 25, 25, 0xFFFFFF);
            renderItem.func_175042_a(chestplate, 5, 20);
            NBTTagCompound nbtDatachestplate = StackUtil.getOrCreateNbtData(chestplate);
            if (nbtDatachestplate.func_74767_n("jetpack")) {
                ItemStack jetpack = nbtDatachestplate.func_74767_n("hoverMode") ? ItemName.jetpack_electric.getItemStack() : ItemName.jetpack.getItemStack();
                renderItem.func_175042_a(jetpack, 50, 20);
            }
        }
        if (!StackUtil.isEmpty(legs) && (charge = GuiOverlayer.getCharge(legs)) >= 0) {
            this.mc.field_71466_p.func_78276_b(charge + "%", 25, 41, 0xFFFFFF);
            renderItem.func_175042_a(legs, 5, 36);
        }
        if (!StackUtil.isEmpty(boots) && (charge = GuiOverlayer.getCharge(boots)) >= 0) {
            this.mc.field_71466_p.func_78276_b(charge + "%", 25, 56, 0xFFFFFF);
            renderItem.func_175042_a(boots, 5, 52);
        }
        if (hudMode.hasTooltip()) {
            int l;
            LinkedList<String> info;
            ItemStack rightItem = this.mc.field_71439_g.func_184614_ca();
            ItemStack leftItem = this.mc.field_71439_g.func_184592_cb();
            int nextLine = 83;
            if (!StackUtil.isEmpty(rightItem)) {
                renderItem.func_175042_a(rightItem, 5, 74);
                this.mc.field_71466_p.func_78276_b(rightItem.func_82833_r(), 30, 78, 0xFFFFFF);
                info = new LinkedList<String>();
                if (rightItem.func_77973_b() instanceof IItemHudInfo) {
                    info.addAll(((IItemHudInfo)rightItem.func_77973_b()).getHudInfo(rightItem, hudMode == HudMode.ADVANCED));
                    if (info.size() > 0) {
                        for (l = 0; l < info.size(); ++l) {
                            this.mc.field_71466_p.func_78276_b((String)info.get(l), 8, 83 + (l + 1) * 14, 0xFFFFFF);
                        }
                    }
                    nextLine += (info.size() + 1) * 14;
                } else {
                    info.addAll(rightItem.func_82840_a((EntityPlayer)this.mc.field_71439_g, () -> hudMode == HudMode.ADVANCED));
                    if (info.size() > 1) {
                        for (l = 1; l < info.size(); ++l) {
                            this.mc.field_71466_p.func_78276_b((String)info.get(l), 8, 83 + l * 14, 0xFFFFFF);
                        }
                    }
                    nextLine += info.size() * 14;
                }
                nextLine += 8;
            }
            if (!StackUtil.isEmpty(leftItem)) {
                renderItem.func_175042_a(leftItem, 5, nextLine - 9);
                this.mc.field_71466_p.func_78276_b(leftItem.func_82833_r(), 30, nextLine - 5, 0xFFFFFF);
                info = new LinkedList();
                if (leftItem.func_77973_b() instanceof IItemHudInfo) {
                    info.addAll(((IItemHudInfo)leftItem.func_77973_b()).getHudInfo(leftItem, hudMode == HudMode.ADVANCED));
                    if (info.size() > 0) {
                        for (l = 0; l < info.size(); ++l) {
                            this.mc.field_71466_p.func_78276_b((String)info.get(l), 8, nextLine + (l + 1) * 14, 0xFFFFFF);
                        }
                    }
                } else {
                    info.addAll(leftItem.func_82840_a((EntityPlayer)this.mc.field_71439_g, () -> hudMode == HudMode.ADVANCED));
                    if (info.size() > 1) {
                        for (l = 1; l < info.size(); ++l) {
                            this.mc.field_71466_p.func_78276_b((String)info.get(l), 8, nextLine + l * 14, 0xFFFFFF);
                        }
                    }
                }
            }
        }
        RenderHelper.func_74518_a();
    }

    private static final int getCharge(ItemStack stack) {
        Item item = stack.func_77973_b();
        assert (item != null);
        if (item instanceof IItemHudProvider.IItemHudBarProvider) {
            return ((IItemHudProvider.IItemHudBarProvider)item).getBarPercent(stack);
        }
        if (item instanceof IElectricItem) {
            return GuiOverlayer.mapCharge(stack);
        }
        if (item.func_77645_m()) {
            return (int)Util.map(1.0 - item.getDurabilityForDisplay(stack), 1.0, 100.0);
        }
        return -1;
    }

    private static final int mapCharge(ItemStack stack) {
        assert (stack.func_77973_b() instanceof IElectricItem);
        double charge = ElectricItem.manager.getCharge(stack);
        double maxCharge = charge + ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true);
        return (int)Util.map(charge, maxCharge, 100.0);
    }
}

