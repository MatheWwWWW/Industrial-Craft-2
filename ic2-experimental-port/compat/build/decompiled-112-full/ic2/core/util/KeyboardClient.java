/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.util;

import ic2.core.IC2;
import ic2.core.util.Keyboard;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class KeyboardClient
extends Keyboard {
    private static final String keyCategory = "IC2";
    private final Minecraft mc = Minecraft.func_71410_x();
    private final KeyBinding altKey = new KeyBinding("ALT Key", 56, "IC2");
    private final KeyBinding boostKey = new KeyBinding("Boost Key", 29, "IC2");
    private final KeyBinding modeSwitchKey = new KeyBinding("Mode Switch Key", 50, "IC2");
    private final KeyBinding sideinventoryKey = new KeyBinding("Side Inventory Key", 46, "IC2");
    private final KeyBinding expandinfo = new KeyBinding("Hub Expand Key", 45, "IC2");
    private static boolean registeredKeys = false;
    private int lastKeyState = 0;

    public KeyboardClient() {
        if (!registeredKeys) {
            registeredKeys = true;
            ClientRegistry.registerKeyBinding((KeyBinding)this.altKey);
            ClientRegistry.registerKeyBinding((KeyBinding)this.boostKey);
            ClientRegistry.registerKeyBinding((KeyBinding)this.modeSwitchKey);
            ClientRegistry.registerKeyBinding((KeyBinding)this.sideinventoryKey);
            ClientRegistry.registerKeyBinding((KeyBinding)this.expandinfo);
        }
    }

    @Override
    public void sendKeyUpdate() {
        int currentKeyState;
        EnumSet<Keyboard.Key> keys = EnumSet.noneOf(Keyboard.Key.class);
        GuiScreen currentScreen = Minecraft.func_71410_x().field_71462_r;
        if (currentScreen == null || currentScreen.field_146291_p) {
            if (GameSettings.func_100015_a((KeyBinding)this.altKey)) {
                keys.add(Keyboard.Key.alt);
            }
            if (GameSettings.func_100015_a((KeyBinding)this.boostKey)) {
                keys.add(Keyboard.Key.boost);
            }
            if (GameSettings.func_100015_a((KeyBinding)this.mc.field_71474_y.field_74351_w)) {
                keys.add(Keyboard.Key.forward);
            }
            if (GameSettings.func_100015_a((KeyBinding)this.modeSwitchKey)) {
                keys.add(Keyboard.Key.modeSwitch);
            }
            if (GameSettings.func_100015_a((KeyBinding)this.mc.field_71474_y.field_74314_A)) {
                keys.add(Keyboard.Key.jump);
            }
            if (GameSettings.func_100015_a((KeyBinding)this.sideinventoryKey)) {
                keys.add(Keyboard.Key.sideInventory);
            }
            if (GameSettings.func_100015_a((KeyBinding)this.expandinfo)) {
                keys.add(Keyboard.Key.hubMode);
            }
            for (Keyboard.IKeyWatcher watcher : this.watchers) {
                watcher.checkForKey(keys);
            }
        }
        if ((currentKeyState = Keyboard.Key.toInt(keys)) != this.lastKeyState) {
            IC2.network.get(false).initiateKeyUpdate(currentKeyState);
            super.processKeyUpdate(IC2.platform.getPlayerInstance(), currentKeyState);
            this.lastKeyState = currentKeyState;
        }
    }
}

