/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.mojang.brigadier.CommandDispatcher
 */
package net.minecraft.data.info;

import com.google.gson.JsonElement;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

public class CommandsReport
implements DataProvider {
    private final DataGenerator f_124042_;

    public CommandsReport(DataGenerator p_124045_) {
        this.f_124042_ = p_124045_;
    }

    @Override
    public void m_213708_(CachedOutput p_236199_) throws IOException {
        Path $$1 = this.f_124042_.m_236034_(DataGenerator.Target.REPORTS).resolve("commands.json");
        CommandDispatcher<CommandSourceStack> $$2 = new Commands(Commands.CommandSelection.ALL, new CommandBuildContext(RegistryAccess.f_123049_.get())).m_82094_();
        DataProvider.m_236072_(p_236199_, (JsonElement)ArgumentUtils.m_235414_($$2, $$2.getRoot()), $$1);
    }

    @Override
    public String m_6055_() {
        return "Command Syntax";
    }
}

