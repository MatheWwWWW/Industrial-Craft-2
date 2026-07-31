/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.platform.player;

import ic2.core.utils.collection.CollectionUtils;

public enum KeyHelper implements CollectionUtils.IIndexEnum
{
    ALT_KEY(0),
    BOOST_KEY(1),
    MODE_KEY(2),
    SIDE_INV_KEY(3),
    HUD_KEY(4),
    TOGGLE_KEY(5),
    JUMP_KEY(6),
    FORWARD_KEY(7),
    BACKWARD_KEY(8),
    SNEAK_KEY(9),
    RIGHT_CLICK(10),
    BLOCK_CLICK(-1),
    BLOCK_LEFT_CLICK(-1);

    int index;

    private KeyHelper(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return this.index;
    }
}

