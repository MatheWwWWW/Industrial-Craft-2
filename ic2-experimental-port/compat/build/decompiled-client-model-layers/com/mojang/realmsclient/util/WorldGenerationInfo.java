/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.util;

import com.mojang.realmsclient.util.LevelType;

public class WorldGenerationInfo {
    private final String f_167627_;
    private final LevelType f_167628_;
    private final boolean f_167629_;

    public WorldGenerationInfo(String p_167631_, LevelType p_167632_, boolean p_167633_) {
        this.f_167627_ = p_167631_;
        this.f_167628_ = p_167632_;
        this.f_167629_ = p_167633_;
    }

    public String m_167634_() {
        return this.f_167627_;
    }

    public LevelType m_167635_() {
        return this.f_167628_;
    }

    public boolean m_167636_() {
        return this.f_167629_;
    }
}

