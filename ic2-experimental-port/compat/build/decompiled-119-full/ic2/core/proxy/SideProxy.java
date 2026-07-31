/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package ic2.core.proxy;

import ic2.core.audio.AudioManager;
import ic2.core.sound.SoundManager;
import ic2.core.util.Keyboard;
import java.io.File;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface SideProxy {
    public void preInit();

    public void onPostInit();

    public AudioManager getAudioManager();

    public SoundManager getSoundManager();

    public Keyboard getKeyboard();

    public boolean isSimulating();

    public boolean isRendering();

    public void requestTick(boolean var1, Runnable var2);

    public void onServerAvailable(MinecraftServer var1);

    public void displayError(String var1, Object ... var2);

    public void displayError(Exception var1, String var2, Object ... var3);

    public void playSoundSp(String var1, float var2, float var3);

    public void playSoundOnce(Entity var1, SoundEvent var2, float var3, float var4);

    public Player getPlayerInstance();

    public Level getWorld(MinecraftServer var1, ResourceLocation var2);

    public Level getPlayerWorld();

    public RecipeManager getRecipeManager();

    public File getMinecraftDir();

    public void messagePlayer(Player var1, String var2, Object ... var3);

    public <T extends BlockEntity> void registerRotorProvider(BlockEntityType<T> var1);
}

