/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

public class LevelResource {
    public static final LevelResource f_78174_ = new LevelResource("advancements");
    public static final LevelResource f_78175_ = new LevelResource("stats");
    public static final LevelResource f_78176_ = new LevelResource("playerdata");
    public static final LevelResource f_78177_ = new LevelResource("players");
    public static final LevelResource f_78178_ = new LevelResource("level.dat");
    public static final LevelResource f_230800_ = new LevelResource("level.dat_old");
    public static final LevelResource f_230801_ = new LevelResource("icon.png");
    public static final LevelResource f_230802_ = new LevelResource("session.lock");
    public static final LevelResource f_78179_ = new LevelResource("generated");
    public static final LevelResource f_78180_ = new LevelResource("datapacks");
    public static final LevelResource f_78181_ = new LevelResource("resources.zip");
    public static final LevelResource f_78182_ = new LevelResource(".");
    private final String f_78183_;

    private LevelResource(String p_78186_) {
        this.f_78183_ = p_78186_;
    }

    public String m_78187_() {
        return this.f_78183_;
    }

    public String toString() {
        return "/" + this.f_78183_;
    }
}

