/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.item.upgrade;

import com.google.common.base.Supplier;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.gui.EnumCycleHandler;
import ic2.core.gui.GuiElement;
import ic2.core.gui.MouseButton;
import ic2.core.gui.VanillaButton;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.init.Localization;
import ic2.core.item.upgrade.HandHeldAdvancedUpgrade;
import ic2.core.item.upgrade.NbtSettings;
import ic2.core.proxy.ClientEnvProxy;
import ic2.core.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class AdvancedUpgradeScreenFactory
implements ClientEnvProxy.ScreenFactory<DynamicContainer<HandHeldAdvancedUpgrade>> {
    @Override
    public AbstractContainerScreen<DynamicContainer<HandHeldAdvancedUpgrade>> create(final DynamicContainer<HandHeldAdvancedUpgrade> dynamicContainer, Inventory inventory, Component component) {
        final DynamicGui<HandHeldAdvancedUpgrade> dynamicGui = DynamicGui.create(dynamicContainer, inventory, component);
        if (Util.inDev()) {
            dynamicGui.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(dynamicGui, 10, 62, 50, 20, new EnumCycleHandler<NbtSettings>(NbtSettings.VALUES, ((HandHeldAdvancedUpgrade)dynamicContainer.base).nbt){

                @Override
                public void onClick(MouseButton mouseButton) {
                    super.onClick(mouseButton);
                    ((HandHeldAdvancedUpgrade)dynamicContainer.base).nbt = (NbtSettings)((Object)this.getCurrentValue());
                    IC2.network.get(false).sendHandHeldInvField((ContainerBase<?>)((Object)dynamicGui.getContainer()), "nbt");
                }
            }).withText("ic2.upgrade.advancedGUI.nbt")).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){
                private final String NBT = Localization.translate("ic2.upgrade.advancedGUI.nbt");

                public String get() {
                    return Localization.translate("ic2.upgrade.advancedGUI.nbt.desc", Localization.translate(((HandHeldAdvancedUpgrade)dynamicContainer.base).nbt.name), ChatFormatting.GRAY, Localization.translate(((HandHeldAdvancedUpgrade)dynamicContainer.base).nbt.name + ".desc", this.NBT));
                }
            }));
        }
        return dynamicGui;
    }
}

