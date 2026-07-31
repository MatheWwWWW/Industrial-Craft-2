/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  com.mojang.blaze3d.platform.Window
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.Options
 *  net.minecraft.client.gui.screens.ChatScreen
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RegisterKeyMappingsEvent
 *  net.minecraftforge.client.settings.IKeyConflictContext
 *  net.minecraftforge.client.settings.KeyConflictContext
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  org.lwjgl.glfw.GLFW
 */
package ic2.core.platform.player;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import ic2.core.IC2;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.KeyPacket;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.Keyboard;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@OnlyIn(value=Dist.CLIENT)
public class KeyboardClient
extends Keyboard {
    public KeyMapping altKey;
    public KeyMapping boostKey;
    public KeyMapping modeSwitchKey;
    public KeyMapping sideInventoryKey;
    public KeyMapping expandInfoKey;
    public KeyMapping toggleKey;
    private KeyMapping[] array;
    Minecraft mc;
    Window monitor;
    boolean enableGui;
    int lastKey = 0;

    @Override
    public void init() {
        this.altKey = new KeyMapping("key.ic2.alt", 342, "key.ic2.keys");
        this.boostKey = new KeyMapping("key.ic2.boost", 341, "key.ic2.keys");
        this.modeSwitchKey = new KeyMapping("key.ic2.mode", 77, "key.ic2.keys");
        this.sideInventoryKey = new KeyMapping("key.ic2.inv", 67, "key.ic2.keys");
        this.expandInfoKey = new KeyMapping("key.ic2.hud", 88, "key.ic2.keys");
        this.toggleKey = new KeyMapping("key.ic2.toggle", 86, "key.ic2.keys");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeys);
        this.mc = Minecraft.m_91087_();
        if (this.mc != null) {
            this.monitor = this.mc.m_91268_();
            Options setting = this.mc.f_91066_;
            this.array = new KeyMapping[]{this.altKey, this.boostKey, this.modeSwitchKey, this.sideInventoryKey, this.expandInfoKey, this.toggleKey, setting.f_92089_, setting.f_92085_};
        }
        this.enableGui = true;
    }

    public void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(this.altKey);
        event.register(this.boostKey);
        event.register(this.modeSwitchKey);
        event.register(this.sideInventoryKey);
        event.register(this.expandInfoKey);
        event.register(this.toggleKey);
    }

    @Override
    public MutableComponent getKeyName(KeyHelper helper) {
        switch (helper.getIndex()) {
            case 0: {
                return (MutableComponent)this.altKey.m_90863_();
            }
            case 1: {
                return (MutableComponent)this.boostKey.m_90863_();
            }
            case 2: {
                return (MutableComponent)this.modeSwitchKey.m_90863_();
            }
            case 3: {
                return (MutableComponent)this.sideInventoryKey.m_90863_();
            }
            case 4: {
                return (MutableComponent)this.expandInfoKey.m_90863_();
            }
            case 5: {
                return (MutableComponent)this.toggleKey.m_90863_();
            }
            case 6: {
                return (MutableComponent)this.mc.f_91066_.f_92089_.m_90863_();
            }
            case 7: {
                return (MutableComponent)this.mc.f_91066_.f_92085_.m_90863_();
            }
            case 8: {
                return (MutableComponent)this.mc.f_91066_.f_92087_.m_90863_();
            }
            case 9: {
                return (MutableComponent)this.mc.f_91066_.f_92090_.m_90863_();
            }
            case 10: {
                return (MutableComponent)this.mc.f_91066_.f_92095_.m_90863_();
            }
        }
        return super.getKeyName(helper);
    }

    @Override
    public int getKey(KeyHelper helper) {
        switch (helper.getIndex()) {
            case 0: {
                return this.altKey.getKey().m_84873_();
            }
            case 1: {
                return this.boostKey.getKey().m_84873_();
            }
            case 2: {
                return this.modeSwitchKey.getKey().m_84873_();
            }
            case 3: {
                return this.sideInventoryKey.getKey().m_84873_();
            }
            case 4: {
                return this.expandInfoKey.getKey().m_84873_();
            }
            case 5: {
                return this.toggleKey.getKey().m_84873_();
            }
            case 6: {
                return this.mc.f_91066_.f_92089_.getKey().m_84873_();
            }
            case 7: {
                return this.mc.f_91066_.f_92085_.getKey().m_84873_();
            }
            case 8: {
                return this.mc.f_91066_.f_92087_.getKey().m_84873_();
            }
            case 9: {
                return this.mc.f_91066_.f_92090_.getKey().m_84873_();
            }
            case 10: {
                return this.mc.f_91066_.f_92095_.getKey().m_84873_();
            }
        }
        return -1;
    }

    @Override
    public void sendKeyUpdate() {
        int binding = 0;
        if ((this.mc.f_91080_ == null || this.enableGui) && !(this.mc.f_91080_ instanceof ChatScreen)) {
            for (int i = 0; i < this.array.length; ++i) {
                binding |= this.isKeyPressed(this.array[i]) ? 1 << i : 0;
            }
        }
        if ((binding |= (this.mc.f_91080_ != null ? 1 : 0) << 31) != this.lastKey) {
            Player player = IC2.PLATFORM.getClientPlayerInstance();
            if (player != null) {
                PacketManager.INSTANCE.sendToServer(new KeyPacket(binding));
                this.processKeyUpdate(player, binding);
            }
            this.lastKey = binding;
        }
    }

    @Override
    public void processKeyUpdate(Player player, int keyState) {
        super.processKeyUpdate(player, keyState);
        if (player == IC2.PLATFORM.getClientPlayerInstance()) {
            PlayerHandler.getClientHandler().onKeyChanged(keyState);
        }
    }

    public boolean isKeyPressed(KeyMapping binding) {
        IKeyConflictContext context = binding.getKeyConflictContext();
        binding.setKeyConflictContext((IKeyConflictContext)(this.enableGui ? KeyConflictContext.UNIVERSAL : KeyConflictContext.IN_GAME));
        boolean result = this.isKeyDown(binding) && binding.isConflictContextAndModifierActive();
        binding.setKeyConflictContext(context);
        return result;
    }

    private boolean isKeyDown(KeyMapping binding) {
        if (binding.m_90862_()) {
            return false;
        }
        InputConstants.Key input = binding.getKey();
        return input.m_84868_() == InputConstants.Type.MOUSE ? GLFW.glfwGetMouseButton((long)this.monitor.m_85439_(), (int)input.m_84873_()) == 1 : InputConstants.m_84830_((long)this.monitor.m_85439_(), (int)input.m_84873_());
    }
}

