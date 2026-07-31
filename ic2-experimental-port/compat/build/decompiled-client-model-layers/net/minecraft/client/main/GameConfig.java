/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.properties.PropertyMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.platform.DisplayData;
import java.io.File;
import java.net.Proxy;
import javax.annotation.Nullable;
import net.minecraft.client.User;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.client.resources.DirectAssetIndex;

public class GameConfig {
    public final UserData f_101905_;
    public final DisplayData f_101906_;
    public final FolderData f_101907_;
    public final GameData f_101908_;
    public final ServerData f_101909_;

    public GameConfig(UserData p_101911_, DisplayData p_101912_, FolderData p_101913_, GameData p_101914_, ServerData p_101915_) {
        this.f_101905_ = p_101911_;
        this.f_101906_ = p_101912_;
        this.f_101907_ = p_101913_;
        this.f_101908_ = p_101914_;
        this.f_101909_ = p_101915_;
    }

    public static class UserData {
        public final User f_101942_;
        public final PropertyMap f_101943_;
        public final PropertyMap f_101944_;
        public final Proxy f_101945_;

        public UserData(User p_101947_, PropertyMap p_101948_, PropertyMap p_101949_, Proxy p_101950_) {
            this.f_101942_ = p_101947_;
            this.f_101943_ = p_101948_;
            this.f_101944_ = p_101949_;
            this.f_101945_ = p_101950_;
        }
    }

    public static class FolderData {
        public final File f_101916_;
        public final File f_101917_;
        public final File f_101918_;
        @Nullable
        public final String f_101919_;

        public FolderData(File p_101921_, File p_101922_, File p_101923_, @Nullable String p_101924_) {
            this.f_101916_ = p_101921_;
            this.f_101917_ = p_101922_;
            this.f_101918_ = p_101923_;
            this.f_101919_ = p_101924_;
        }

        public AssetIndex m_101925_() {
            return this.f_101919_ == null ? new DirectAssetIndex(this.f_101918_) : new AssetIndex(this.f_101918_, this.f_101919_);
        }
    }

    public static class GameData {
        public final boolean f_101926_;
        public final String f_101927_;
        public final String f_101928_;
        public final boolean f_101929_;
        public final boolean f_101930_;

        public GameData(boolean p_101932_, String p_101933_, String p_101934_, boolean p_101935_, boolean p_101936_) {
            this.f_101926_ = p_101932_;
            this.f_101927_ = p_101933_;
            this.f_101928_ = p_101934_;
            this.f_101929_ = p_101935_;
            this.f_101930_ = p_101936_;
        }
    }

    public static class ServerData {
        @Nullable
        public final String f_101937_;
        public final int f_101938_;

        public ServerData(@Nullable String p_101940_, int p_101941_) {
            this.f_101937_ = p_101940_;
            this.f_101938_ = p_101941_;
        }
    }
}

