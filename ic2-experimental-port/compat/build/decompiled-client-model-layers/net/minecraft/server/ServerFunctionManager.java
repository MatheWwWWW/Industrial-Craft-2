/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionLibrary;
import net.minecraft.world.level.GameRules;

public class ServerFunctionManager {
    private static final Component f_179958_ = Component.m_237115_("commands.debug.function.noRecursion");
    private static final ResourceLocation f_136099_ = new ResourceLocation("tick");
    private static final ResourceLocation f_136100_ = new ResourceLocation("load");
    final MinecraftServer f_136101_;
    @Nullable
    private ExecutionContext f_179959_;
    private List<CommandFunction> f_136105_ = ImmutableList.of();
    private boolean f_136106_;
    private ServerFunctionLibrary f_136107_;

    public ServerFunctionManager(MinecraftServer p_136110_, ServerFunctionLibrary p_136111_) {
        this.f_136101_ = p_136110_;
        this.f_136107_ = p_136111_;
        this.m_136125_(p_136111_);
    }

    public int m_136122_() {
        return this.f_136101_.m_129900_().m_46215_(GameRules.f_46152_);
    }

    public CommandDispatcher<CommandSourceStack> m_136127_() {
        return this.f_136101_.m_129892_().m_82094_();
    }

    public void m_136128_() {
        this.m_136115_(this.f_136105_, f_136099_);
        if (this.f_136106_) {
            this.f_136106_ = false;
            Collection<CommandFunction> $$0 = this.f_136107_.m_214327_(f_136100_);
            this.m_136115_($$0, f_136100_);
        }
    }

    private void m_136115_(Collection<CommandFunction> p_136116_, ResourceLocation p_136117_) {
        this.f_136101_.m_129905_().m_6521_(p_136117_::toString);
        for (CommandFunction $$2 : p_136116_) {
            this.m_136112_($$2, this.m_136129_());
        }
        this.f_136101_.m_129905_().m_7238_();
    }

    public int m_136112_(CommandFunction p_136113_, CommandSourceStack p_136114_) {
        return this.m_179960_(p_136113_, p_136114_, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int m_179960_(CommandFunction p_179961_, CommandSourceStack p_179962_, @Nullable TraceCallbacks p_179963_) {
        if (this.f_179959_ != null) {
            if (p_179963_ != null) {
                this.f_179959_.m_179975_(f_179958_.getString());
                return 0;
            }
            this.f_179959_.m_179972_(p_179961_, p_179962_);
            return 0;
        }
        try {
            this.f_179959_ = new ExecutionContext(p_179963_);
            int n = this.f_179959_.m_179977_(p_179961_, p_179962_);
            return n;
        }
        finally {
            this.f_179959_ = null;
        }
    }

    public void m_136120_(ServerFunctionLibrary p_136121_) {
        this.f_136107_ = p_136121_;
        this.m_136125_(p_136121_);
    }

    private void m_136125_(ServerFunctionLibrary p_136126_) {
        this.f_136105_ = ImmutableList.copyOf(p_136126_.m_214327_(f_136099_));
        this.f_136106_ = true;
    }

    public CommandSourceStack m_136129_() {
        return this.f_136101_.m_129893_().m_81325_(2).m_81324_();
    }

    public Optional<CommandFunction> m_136118_(ResourceLocation p_136119_) {
        return this.f_136107_.m_136089_(p_136119_);
    }

    public Collection<CommandFunction> m_214331_(ResourceLocation p_214332_) {
        return this.f_136107_.m_214327_(p_214332_);
    }

    public Iterable<ResourceLocation> m_136130_() {
        return this.f_136107_.m_136055_().keySet();
    }

    public Iterable<ResourceLocation> m_136131_() {
        return this.f_136107_.m_206891_();
    }

    public static interface TraceCallbacks {
        public void m_142256_(int var1, String var2);

        public void m_142279_(int var1, String var2, int var3);

        public void m_142255_(int var1, String var2);

        public void m_142147_(int var1, ResourceLocation var2, int var3);
    }

    class ExecutionContext {
        private int f_179965_;
        @Nullable
        private final TraceCallbacks f_179966_;
        private final Deque<QueuedCommand> f_179967_ = Queues.newArrayDeque();
        private final List<QueuedCommand> f_179968_ = Lists.newArrayList();

        ExecutionContext(TraceCallbacks p_179971_) {
            this.f_179966_ = p_179971_;
        }

        void m_179972_(CommandFunction p_179973_, CommandSourceStack p_179974_) {
            int $$2 = ServerFunctionManager.this.m_136122_();
            if (this.f_179967_.size() + this.f_179968_.size() < $$2) {
                this.f_179968_.add(new QueuedCommand(p_179974_, this.f_179965_, new CommandFunction.FunctionEntry(p_179973_)));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        int m_179977_(CommandFunction p_179978_, CommandSourceStack p_179979_) {
            int $$2 = ServerFunctionManager.this.m_136122_();
            int $$3 = 0;
            CommandFunction.Entry[] $$4 = p_179978_.m_77989_();
            for (int $$5 = $$4.length - 1; $$5 >= 0; --$$5) {
                this.f_179967_.push(new QueuedCommand(p_179979_, 0, $$4[$$5]));
            }
            while (!this.f_179967_.isEmpty()) {
                try {
                    QueuedCommand $$6 = this.f_179967_.removeFirst();
                    ServerFunctionManager.this.f_136101_.m_129905_().m_6521_($$6::toString);
                    this.f_179965_ = $$6.f_179980_;
                    $$6.m_179985_(ServerFunctionManager.this, this.f_179967_, $$2, this.f_179966_);
                    if (!this.f_179968_.isEmpty()) {
                        Lists.reverse(this.f_179968_).forEach(this.f_179967_::addFirst);
                        this.f_179968_.clear();
                    }
                }
                finally {
                    ServerFunctionManager.this.f_136101_.m_129905_().m_7238_();
                }
                if (++$$3 < $$2) continue;
                return $$3;
            }
            return $$3;
        }

        public void m_179975_(String p_179976_) {
            if (this.f_179966_ != null) {
                this.f_179966_.m_142255_(this.f_179965_, p_179976_);
            }
        }
    }

    public static class QueuedCommand {
        private final CommandSourceStack f_136133_;
        final int f_179980_;
        private final CommandFunction.Entry f_136134_;

        public QueuedCommand(CommandSourceStack p_179982_, int p_179983_, CommandFunction.Entry p_179984_) {
            this.f_136133_ = p_179982_;
            this.f_179980_ = p_179983_;
            this.f_136134_ = p_179984_;
        }

        public void m_179985_(ServerFunctionManager p_179986_, Deque<QueuedCommand> p_179987_, int p_179988_, @Nullable TraceCallbacks p_179989_) {
            block4: {
                try {
                    this.f_136134_.m_142134_(p_179986_, this.f_136133_, p_179987_, p_179988_, this.f_179980_, p_179989_);
                }
                catch (CommandSyntaxException $$4) {
                    if (p_179989_ != null) {
                        p_179989_.m_142255_(this.f_179980_, $$4.getRawMessage().getString());
                    }
                }
                catch (Exception $$5) {
                    if (p_179989_ == null) break block4;
                    p_179989_.m_142255_(this.f_179980_, $$5.getMessage());
                }
            }
        }

        public String toString() {
            return this.f_136134_.toString();
        }
    }
}

