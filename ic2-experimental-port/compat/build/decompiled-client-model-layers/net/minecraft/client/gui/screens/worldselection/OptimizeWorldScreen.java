/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.worldselection;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.WorldStem;
import net.minecraft.util.Mth;
import net.minecraft.util.worldupdate.WorldUpgrader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;

public class OptimizeWorldScreen
extends Screen {
    private static final Logger f_101298_ = LogUtils.getLogger();
    private static final Object2IntMap<ResourceKey<Level>> f_101299_ = (Object2IntMap)Util.m_137469_(new Object2IntOpenCustomHashMap(Util.m_137583_()), p_101324_ -> {
        p_101324_.put(Level.f_46428_, -13408734);
        p_101324_.put(Level.f_46429_, -10075085);
        p_101324_.put(Level.f_46430_, -8943531);
        p_101324_.defaultReturnValue(-2236963);
    });
    private final BooleanConsumer f_101300_;
    private final WorldUpgrader f_101301_;

    @Nullable
    public static OptimizeWorldScreen m_101315_(Minecraft p_101316_, BooleanConsumer p_101317_, DataFixer p_101318_, LevelStorageSource.LevelStorageAccess p_101319_, boolean p_101320_) {
        WorldStem $$5 = p_101316_.m_231466_().m_233119_(p_101319_, false);
        try {
            WorldData $$6 = $$5.f_206895_();
            p_101319_.m_78287_($$5.f_206894_(), $$6);
            OptimizeWorldScreen optimizeWorldScreen = new OptimizeWorldScreen(p_101317_, p_101318_, p_101319_, $$6.m_5926_(), p_101320_, $$6.m_5961_());
            if ($$5 != null) {
                $$5.close();
            }
            return optimizeWorldScreen;
        }
        catch (Throwable throwable) {
            try {
                if ($$5 != null) {
                    try {
                        $$5.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            catch (Exception $$7) {
                f_101298_.warn("Failed to load datapacks, can't optimize world", (Throwable)$$7);
                return null;
            }
        }
    }

    private OptimizeWorldScreen(BooleanConsumer p_194064_, DataFixer p_194065_, LevelStorageSource.LevelStorageAccess p_194066_, LevelSettings p_194067_, boolean p_194068_, WorldGenSettings p_194069_) {
        super(Component.m_237110_("optimizeWorld.title", p_194067_.m_46917_()));
        this.f_101300_ = p_194064_;
        this.f_101301_ = new WorldUpgrader(p_194066_, p_194065_, p_194069_, p_194068_);
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 150, 200, 20, CommonComponents.f_130656_, p_101322_ -> {
            this.f_101301_.m_18820_();
            this.f_101300_.accept(false);
        }));
    }

    @Override
    public void m_86600_() {
        if (this.f_101301_.m_18829_()) {
            this.f_101300_.accept(true);
        }
    }

    @Override
    public void m_7379_() {
        this.f_101300_.accept(false);
    }

    @Override
    public void m_7861_() {
        this.f_101301_.m_18820_();
    }

    @Override
    public void m_6305_(PoseStack p_101311_, int p_101312_, int p_101313_, float p_101314_) {
        this.m_7333_(p_101311_);
        OptimizeWorldScreen.m_93215_(p_101311_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 0xFFFFFF);
        int $$4 = this.f_96543_ / 2 - 150;
        int $$5 = this.f_96543_ / 2 + 150;
        int $$6 = this.f_96544_ / 4 + 100;
        int $$7 = $$6 + 10;
        OptimizeWorldScreen.m_93215_(p_101311_, this.f_96547_, this.f_101301_.m_18837_(), this.f_96543_ / 2, $$6 - this.f_96547_.f_92710_ - 2, 0xA0A0A0);
        if (this.f_101301_.m_18834_() > 0) {
            OptimizeWorldScreen.m_93172_(p_101311_, $$4 - 1, $$6 - 1, $$5 + 1, $$7 + 1, -16777216);
            OptimizeWorldScreen.m_93243_(p_101311_, this.f_96547_, Component.m_237110_("optimizeWorld.info.converted", this.f_101301_.m_18835_()), $$4, 40, 0xA0A0A0);
            OptimizeWorldScreen.m_93243_(p_101311_, this.f_96547_, Component.m_237110_("optimizeWorld.info.skipped", this.f_101301_.m_18836_()), $$4, 40 + this.f_96547_.f_92710_ + 3, 0xA0A0A0);
            OptimizeWorldScreen.m_93243_(p_101311_, this.f_96547_, Component.m_237110_("optimizeWorld.info.total", this.f_101301_.m_18834_()), $$4, 40 + (this.f_96547_.f_92710_ + 3) * 2, 0xA0A0A0);
            int $$8 = 0;
            for (ResourceKey $$9 : this.f_101301_.m_18832_()) {
                int $$10 = Mth.m_14143_(this.f_101301_.m_18827_($$9) * (float)($$5 - $$4));
                OptimizeWorldScreen.m_93172_(p_101311_, $$4 + $$8, $$6, $$4 + $$8 + $$10, $$7, f_101299_.getInt((Object)$$9));
                $$8 += $$10;
            }
            int $$11 = this.f_101301_.m_18835_() + this.f_101301_.m_18836_();
            OptimizeWorldScreen.m_93208_(p_101311_, this.f_96547_, $$11 + " / " + this.f_101301_.m_18834_(), this.f_96543_ / 2, $$6 + 2 * this.f_96547_.f_92710_ + 2, 0xA0A0A0);
            OptimizeWorldScreen.m_93208_(p_101311_, this.f_96547_, Mth.m_14143_(this.f_101301_.m_18833_() * 100.0f) + "%", this.f_96543_ / 2, $$6 + ($$7 - $$6) / 2 - this.f_96547_.f_92710_ / 2, 0xA0A0A0);
        }
        super.m_6305_(p_101311_, p_101312_, p_101313_, p_101314_);
    }
}

