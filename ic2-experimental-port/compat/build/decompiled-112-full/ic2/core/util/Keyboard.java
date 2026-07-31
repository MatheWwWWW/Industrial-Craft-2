/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.util;

import ic2.api.util.IKeyboard;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Keyboard
implements IKeyboard {
    protected final List<IKeyWatcher> watchers = new ArrayList<IKeyWatcher>();
    private final Map<EntityPlayer, Set<Key>> playerKeys = new WeakHashMap<EntityPlayer, Set<Key>>();

    @Override
    public boolean isAltKeyDown(EntityPlayer player) {
        return this.get(player, Key.alt);
    }

    @Override
    public boolean isBoostKeyDown(EntityPlayer player) {
        return this.get(player, Key.boost);
    }

    @Override
    public boolean isForwardKeyDown(EntityPlayer player) {
        return this.get(player, Key.forward);
    }

    @Override
    public boolean isJumpKeyDown(EntityPlayer player) {
        return this.get(player, Key.jump);
    }

    @Override
    public boolean isModeSwitchKeyDown(EntityPlayer player) {
        return this.get(player, Key.modeSwitch);
    }

    @Override
    public boolean isSideinventoryKeyDown(EntityPlayer player) {
        return this.get(player, Key.sideInventory);
    }

    @Override
    public boolean isHudModeKeyDown(EntityPlayer player) {
        return this.get(player, Key.hubMode);
    }

    @Override
    public boolean isSneakKeyDown(EntityPlayer player) {
        return player.func_70093_af();
    }

    public void sendKeyUpdate() {
    }

    public void processKeyUpdate(EntityPlayer player, int keyState) {
        this.playerKeys.put(player, Key.fromInt(keyState));
    }

    public void removePlayerReferences(EntityPlayer player) {
        this.playerKeys.remove(player);
    }

    private boolean get(EntityPlayer player, Key key) {
        Set<Key> keys = this.playerKeys.get(player);
        if (keys == null) {
            return false;
        }
        return keys.contains((Object)key);
    }

    public void addKeyWatcher(IKeyWatcher watcher) {
        this.watchers.add(watcher);
    }

    public boolean isKeyDown(EntityPlayer player, IKeyWatcher watcher) {
        return this.get(player, watcher.getRepresentation());
    }

    protected static enum Key {
        alt,
        boost,
        forward,
        modeSwitch,
        jump,
        sideInventory,
        hubMode;

        public static final Key[] keys;

        public static int toInt(Iterable<Key> keySet) {
            int ret = 0;
            for (Key key : keySet) {
                ret |= 1 << key.ordinal();
            }
            return ret;
        }

        public static Set<Key> fromInt(int keyState) {
            EnumSet<Key> ret = EnumSet.noneOf(Key.class);
            for (int i = 0; keyState != 0 && i < keys.length; ++i, keyState >>= 1) {
                if ((keyState & 1) == 0) continue;
                ret.add(keys[i]);
            }
            return ret;
        }

        static {
            keys = Key.values();
        }
    }

    public static interface IKeyWatcher {
        @SideOnly(value=Side.CLIENT)
        public void checkForKey(Set<Key> var1);

        public Key getRepresentation();
    }
}

