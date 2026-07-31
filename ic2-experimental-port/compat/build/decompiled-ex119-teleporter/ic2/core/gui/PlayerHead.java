/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.SkullBlockEntity
 *  org.apache.commons.lang3.StringUtils
 */
package ic2.core.gui;

import com.mojang.authlib.GameProfile;
import ic2.core.Ic2Gui;
import ic2.core.gui.ItemImage;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

public class PlayerHead
extends ItemImage {
    private static final Map<GameProfile, ItemStack> IMAGE_MAKER = Collections.synchronizedMap(new WeakHashMap());
    private final GameProfile player;

    public PlayerHead(Ic2Gui<?> ic2Gui, int n, int n2, GameProfile gameProfile) {
        super(ic2Gui, n, n2, new PlayerHeadSupplier(gameProfile));
        this.player = gameProfile;
    }

    @Override
    protected List<Component> getToolTip() {
        List<Component> list = super.getToolTip();
        if (StringUtils.isNotBlank((CharSequence)this.player.getName())) {
            list.add((Component)Component.m_237113_((String)this.player.getName()));
        }
        return list;
    }

    private static final class PlayerHeadSupplier
    implements Supplier<ItemStack> {
        private final GameProfile profile;

        PlayerHeadSupplier(GameProfile gameProfile) {
            this.profile = gameProfile;
        }

        @Override
        public ItemStack get() {
            CompletableFuture completableFuture = new CompletableFuture();
            SkullBlockEntity.m_155738_((GameProfile)this.profile, completableFuture::complete);
            try {
                return IMAGE_MAKER.computeIfAbsent((GameProfile)completableFuture.get(), gameProfile -> {
                    ItemStack itemStack = new ItemStack((ItemLike)Items.f_42680_);
                    StackUtil.getOrCreateNbtData(itemStack).m_128365_("SkullOwner", (Tag)NbtUtils.m_129230_((CompoundTag)new CompoundTag(), (GameProfile)gameProfile));
                    return itemStack;
                });
            }
            catch (InterruptedException | ExecutionException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}

