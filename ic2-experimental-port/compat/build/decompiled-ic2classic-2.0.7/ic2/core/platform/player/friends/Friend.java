/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.platform.player.friends;

import com.mojang.authlib.GameProfile;
import ic2.core.platform.player.friends.Action;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class Friend {
    private final UUID id;
    private final String name;
    private final Set<Action> validActions = CollectionUtils.createLinkedSet();

    public Friend(CompoundTag nbt) {
        this(nbt.m_128461_("name"), nbt.m_128342_("id"));
        for (StringTag entry : NBTListWrapper.wrap(nbt.m_128437_("actions", 8), StringTag.class)) {
            Action action = Action.getAction(entry.m_7916_());
            if (action == null) continue;
            this.validActions.add(action);
        }
    }

    public Friend(FriendlyByteBuf buffer) {
        this.id = buffer.m_130259_();
        this.name = buffer.m_130136_(Short.MAX_VALUE);
        int size = buffer.readByte();
        for (int i = 0; i < size; ++i) {
            Action action = Action.getAction(buffer.m_130136_(Short.MAX_VALUE));
            if (action == null) continue;
            this.validActions.add(action);
        }
    }

    public Friend(Player player) {
        this(player.m_7755_().getString(), player.m_20148_());
    }

    public Friend(GameProfile profile) {
        this(profile.getName(), profile.getId());
    }

    public Friend(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.m_130077_(this.id);
        buffer.m_130070_(this.name);
        buffer.writeByte(this.validActions.size());
        for (Action action : this.validActions) {
            buffer.m_130070_(action.getName());
        }
    }

    public String getName() {
        return this.name;
    }

    public UUID getId() {
        return this.id;
    }

    public Friend addAction(Action action) {
        this.validActions.add(action);
        return this;
    }

    public Friend removeAction(Action action) {
        this.validActions.remove(action);
        return this;
    }

    public boolean canApplyAction(Action action) {
        return this.validActions.contains(action);
    }

    public CompoundTag write(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (Action action : this.validActions) {
            list.add((Object)StringTag.m_129297_((String)action.getName()));
        }
        nbt.m_128362_("id", this.id);
        nbt.m_128359_("name", this.name);
        nbt.m_128365_("actions", (Tag)list);
        return nbt;
    }
}

