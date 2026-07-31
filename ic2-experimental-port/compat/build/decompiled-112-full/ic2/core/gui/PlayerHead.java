/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTUtil
 *  net.minecraft.tileentity.TileEntitySkull
 *  org.apache.commons.lang3.StringUtils
 */
package ic2.core.gui;

import com.google.common.base.Supplier;
import com.mojang.authlib.GameProfile;
import ic2.core.GuiIC2;
import ic2.core.gui.ItemImage;
import ic2.core.util.StackUtil;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import org.apache.commons.lang3.StringUtils;

public class PlayerHead
extends ItemImage {
    private static final Map<GameProfile, Supplier<ItemStack>> IMAGE_MAKER = new WeakHashMap<GameProfile, Supplier<ItemStack>>();
    private final GameProfile player;

    public PlayerHead(GuiIC2<?> gui, int x, int y, GameProfile player) {
        this(gui, x, y, TileEntitySkull.func_174884_b((GameProfile)player), null);
    }

    private PlayerHead(GuiIC2<?> gui, int x, int y, GameProfile player, Void skip) {
        super(gui, x, y, (Supplier<ItemStack>)IMAGE_MAKER.computeIfAbsent(player, profile -> {
            ItemStack skull = new ItemStack(Items.field_151144_bL, 1, 3);
            StackUtil.getOrCreateNbtData(skull).func_74782_a("SkullOwner", (NBTBase)NBTUtil.func_180708_a((NBTTagCompound)new NBTTagCompound(), (GameProfile)profile));
            return () -> skull;
        }));
        this.player = player;
        assert (player.equals((Object)NBTUtil.func_152459_a((NBTTagCompound)((ItemStack)IMAGE_MAKER.get(player).get()).func_77978_p().func_74775_l("SkullOwner"))));
    }

    @Override
    protected List<String> getToolTip() {
        List<String> tooltip = super.getToolTip();
        if (StringUtils.isNotBlank((CharSequence)this.player.getName())) {
            tooltip.add(this.player.getName());
        }
        return tooltip;
    }
}

