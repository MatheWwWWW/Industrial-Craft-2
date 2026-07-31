/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.vertex.PoseStack
 *  ic2.api.items.electric.ElectricItem
 *  ic2.core.item.wearable.armor.electric.ElectricPackArmor
 *  ic2.core.item.wearable.base.IC2ElectricJetpackBase
 *  ic2.core.item.wearable.base.IC2JetpackBase
 *  ic2.core.item.wearable.base.IC2JetpackBase$HoverMode
 *  ic2.core.item.wearable.base.IC2ModularElectricArmor
 *  ic2.core.utils.helpers.StackUtil
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.client.gui.overlay.ForgeGui
 *  net.minecraftforge.client.gui.overlay.IGuiOverlay
 */
package trinsdar.gravisuit.util.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.items.electric.ElectricItem;
import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.utils.helpers.StackUtil;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import trinsdar.gravisuit.items.armor.IGravitationJetpack;
import trinsdar.gravisuit.items.armor.IHasOverlay;
import trinsdar.gravisuit.util.GravisuitConfig;

public class GraviSuitOverlay
implements IGuiOverlay {
    public static Minecraft mc;
    public static Font fontRenderer;
    static int offset;
    int xPos = offset;
    int yPos1 = offset;
    int yPos2;
    int yPos3;
    int yPos4;

    public GraviSuitOverlay(Minecraft mc) {
        GraviSuitOverlay.mc = mc;
        fontRenderer = mc.f_91062_;
    }

    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        IHasOverlay overlay;
        LocalPlayer player = GraviSuitOverlay.mc.f_91074_;
        assert (player != null);
        ItemStack stackArmor = player.m_6844_(EquipmentSlot.CHEST);
        Item itemArmor = stackArmor.m_41720_();
        if (GravisuitConfig.CLIENT.POSITIONS == GravisuitConfig.Client.Positions.BOTTOMLEFT || GravisuitConfig.CLIENT.POSITIONS == GravisuitConfig.Client.Positions.BOTTOMRIGHT) {
            Objects.requireNonNull(fontRenderer);
            this.yPos1 = screenHeight - (9 * 2 + 5);
        }
        Objects.requireNonNull(fontRenderer);
        this.yPos2 = this.yPos1 + 9 + 2;
        Objects.requireNonNull(fontRenderer);
        this.yPos3 = this.yPos2 + 9 + 2;
        Objects.requireNonNull(fontRenderer);
        this.yPos4 = this.yPos3 + 9 + 2;
        if (itemArmor instanceof IHasOverlay && (overlay = (IHasOverlay)itemArmor).isEnabled(stackArmor)) {
            IC2ModularElectricArmor armor;
            IC2JetpackBase base;
            CompoundTag tag = overlay.getArmorNBT(stackArmor, true);
            if (itemArmor instanceof IC2ModularElectricArmor && (base = (armor = (IC2ModularElectricArmor)itemArmor).getJetpack(stackArmor)) != null) {
                itemArmor = base;
            }
            int currentCharge = ElectricItem.MANAGER.getCharge(stackArmor);
            int maxCapacity = ElectricItem.MANAGER.getCapacity(stackArmor);
            int energyLevel = (int)Math.round((double)currentCharge / (double)maxCapacity * 100.0);
            String energyString = "message.info.energy";
            MutableComponent energyToDisplay = GraviSuitOverlay.formatComplexMessage(ChatFormatting.YELLOW, energyString, GraviSuitOverlay.getEnergyTextColor(energyLevel), energyLevel + "%");
            boolean isEngineOn = !tag.m_128471_("disabled");
            String engineStatus = isEngineOn ? "message.info.on" : "message.info.off";
            ChatFormatting engineStatusColor = isEngineOn ? ChatFormatting.GREEN : ChatFormatting.RED;
            String engineString = "message.info.jetpack.engine";
            MutableComponent engineToDisplay = GraviSuitOverlay.formatComplexMessage(ChatFormatting.YELLOW, engineString, engineStatusColor, engineStatus);
            String hoverModeS = GraviSuitOverlay.getWorkStatus(stackArmor);
            ChatFormatting hoverModeC = GraviSuitOverlay.getWorkStatusColor(stackArmor);
            String hoverString = "message.info.jetpack.hover";
            MutableComponent hoverToDisplay = GraviSuitOverlay.formatComplexMessage(ChatFormatting.YELLOW, hoverString, hoverModeC, hoverModeS);
            boolean isGraviEngineOn = tag.m_128471_("engine_on");
            String graviEngineStatus = isGraviEngineOn ? "message.info.on" : "message.info.off";
            ChatFormatting graviEngineStatusColor = isGraviEngineOn ? ChatFormatting.GREEN : ChatFormatting.RED;
            String graviEngineString = "message.info.gravitation";
            MutableComponent graviEngineToDisplay = GraviSuitOverlay.formatComplexMessage(ChatFormatting.AQUA, graviEngineString, graviEngineStatusColor, graviEngineStatus);
            if (itemArmor instanceof ElectricPackArmor) {
                fontRenderer.m_92763_(poseStack, (Component)energyToDisplay, (float)GraviSuitOverlay.getXOffset(energyToDisplay.getString(), gui.getMinecraft().m_91268_()), (float)this.yPos1, 0);
            }
            if (itemArmor instanceof IC2ElectricJetpackBase) {
                fontRenderer.m_92763_(poseStack, (Component)energyToDisplay, (float)GraviSuitOverlay.getXOffset(energyToDisplay.getString(), gui.getMinecraft().m_91268_()), (float)this.yPos1, 0);
                fontRenderer.m_92763_(poseStack, (Component)engineToDisplay, (float)GraviSuitOverlay.getXOffset(engineToDisplay.getString(), gui.getMinecraft().m_91268_()), (float)this.yPos2, 0);
                fontRenderer.m_92763_(poseStack, (Component)hoverToDisplay, (float)GraviSuitOverlay.getXOffset(hoverToDisplay.getString(), gui.getMinecraft().m_91268_()), (float)this.yPos3, 0);
            }
            if (itemArmor instanceof IGravitationJetpack) {
                fontRenderer.m_92763_(poseStack, (Component)graviEngineToDisplay, (float)GraviSuitOverlay.getXOffset(graviEngineToDisplay.getString(), gui.getMinecraft().m_91268_()), (float)this.yPos4, 0);
            }
        }
    }

    private static int getXOffset(String value, Window window) {
        return switch (GravisuitConfig.CLIENT.POSITIONS) {
            default -> throw new IncompatibleClassChangeError();
            case GravisuitConfig.Client.Positions.TOPLEFT, GravisuitConfig.Client.Positions.BOTTOMLEFT -> offset;
            case GravisuitConfig.Client.Positions.TOPRIGHT, GravisuitConfig.Client.Positions.BOTTOMRIGHT -> window.m_85445_() - 3 - fontRenderer.m_92895_(value);
            case GravisuitConfig.Client.Positions.TOPMIDDLE -> (int)((float)window.m_85445_() * 0.5f) - fontRenderer.m_92895_(value) / 2;
        };
    }

    public boolean or(Item compare, Item ... items) {
        for (Item item : items) {
            if (compare != item) continue;
            return true;
        }
        return false;
    }

    public static ChatFormatting getEnergyTextColor(double energyLevel) {
        if (energyLevel == 100.0) {
            return ChatFormatting.GREEN;
        }
        if (energyLevel <= 100.0 && energyLevel > 50.0) {
            return ChatFormatting.GOLD;
        }
        if (energyLevel <= 50.0) {
            return ChatFormatting.RED;
        }
        return null;
    }

    public static MutableComponent formatSimpleMessage(ChatFormatting color, String text) {
        return Component.m_237115_((String)text).m_130940_(color);
    }

    public static MutableComponent formatComplexMessage(ChatFormatting color1, String text1, ChatFormatting color2, String text2) {
        return GraviSuitOverlay.formatSimpleMessage(color1, text1).m_7220_((Component)GraviSuitOverlay.formatSimpleMessage(color2, text2));
    }

    private static IC2JetpackBase.HoverMode getHoverStatus(ItemStack stack) {
        CompoundTag tag = stack.m_41720_() instanceof IC2ModularElectricArmor ? StackUtil.getNbtData((ItemStack)stack).m_128469_("jetpack_data") : StackUtil.getNbtData((ItemStack)stack);
        return IC2JetpackBase.HoverMode.byIndex((int)tag.m_128445_("HoverMode"));
    }

    public static String getWorkStatus(ItemStack stack) {
        IC2JetpackBase.HoverMode mode = GraviSuitOverlay.getHoverStatus(stack);
        if (mode == IC2JetpackBase.HoverMode.BASIC) {
            return "message.info.basic";
        }
        if (mode == IC2JetpackBase.HoverMode.ADV) {
            return "message.info.adv";
        }
        return "message.info.off";
    }

    public static ChatFormatting getWorkStatusColor(ItemStack stack) {
        IC2JetpackBase.HoverMode mode = GraviSuitOverlay.getHoverStatus(stack);
        if (mode == IC2JetpackBase.HoverMode.BASIC) {
            return ChatFormatting.GREEN;
        }
        if (mode == IC2JetpackBase.HoverMode.ADV) {
            return ChatFormatting.AQUA;
        }
        return ChatFormatting.RED;
    }

    @Deprecated
    public static int getCharge(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData((ItemStack)stack);
        int e = nbt.m_128451_("charge");
        return e;
    }

    static {
        offset = 3;
    }
}

