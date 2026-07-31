/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;

public class ScoreContents
implements ComponentContents {
    private static final String f_237433_ = "*";
    private final String f_237434_;
    @Nullable
    private final EntitySelector f_237435_;
    private final String f_237436_;

    @Nullable
    private static EntitySelector m_237447_(String p_237448_) {
        try {
            return new EntitySelectorParser(new StringReader(p_237448_)).m_121377_();
        }
        catch (CommandSyntaxException commandSyntaxException) {
            return null;
        }
    }

    public ScoreContents(String p_237438_, String p_237439_) {
        this.f_237434_ = p_237438_;
        this.f_237435_ = ScoreContents.m_237447_(p_237438_);
        this.f_237436_ = p_237439_;
    }

    public String m_237440_() {
        return this.f_237434_;
    }

    @Nullable
    public EntitySelector m_237452_() {
        return this.f_237435_;
    }

    public String m_237453_() {
        return this.f_237436_;
    }

    private String m_237441_(CommandSourceStack p_237442_) throws CommandSyntaxException {
        List<? extends Entity> $$1;
        if (this.f_237435_ != null && !($$1 = this.f_237435_.m_121160_(p_237442_)).isEmpty()) {
            if ($$1.size() != 1) {
                throw EntityArgument.f_91436_.create();
            }
            return $$1.get(0).m_6302_();
        }
        return this.f_237434_;
    }

    private String m_237449_(String p_237450_, CommandSourceStack p_237451_) {
        Objective $$4;
        ServerScoreboard $$3;
        MinecraftServer $$2 = p_237451_.m_81377_();
        if ($$2 != null && ($$3 = $$2.m_129896_()).m_83461_(p_237450_, $$4 = $$3.m_83477_(this.f_237436_))) {
            Score $$5 = $$3.m_83471_(p_237450_, $$4);
            return Integer.toString($$5.m_83400_());
        }
        return "";
    }

    @Override
    public MutableComponent m_213698_(@Nullable CommandSourceStack p_237444_, @Nullable Entity p_237445_, int p_237446_) throws CommandSyntaxException {
        if (p_237444_ == null) {
            return Component.m_237119_();
        }
        String $$3 = this.m_237441_(p_237444_);
        String $$4 = p_237445_ != null && $$3.equals(f_237433_) ? p_237445_.m_6302_() : $$3;
        return Component.m_237113_(this.m_237449_($$4, p_237444_));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object p_237455_) {
        if (this == p_237455_) {
            return true;
        }
        if (!(p_237455_ instanceof ScoreContents)) return false;
        ScoreContents $$1 = (ScoreContents)p_237455_;
        if (!this.f_237434_.equals($$1.f_237434_)) return false;
        if (!this.f_237436_.equals($$1.f_237436_)) return false;
        return true;
    }

    public int hashCode() {
        int $$0 = this.f_237434_.hashCode();
        $$0 = 31 * $$0 + this.f_237436_.hashCode();
        return $$0;
    }

    public String toString() {
        return "score{name='" + this.f_237434_ + "', objective='" + this.f_237436_ + "'}";
    }
}

