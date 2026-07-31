/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mojang.authlib.properties.PropertyMap
 *  com.mojang.authlib.properties.PropertyMap$Serializer
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  joptsimple.ArgumentAcceptingOptionSpec
 *  joptsimple.NonOptionArgumentSpec
 *  joptsimple.OptionParser
 *  joptsimple.OptionSet
 *  joptsimple.OptionSpec
 *  joptsimple.OptionSpecBuilder
 *  org.slf4j.Logger
 */
package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.UUIDUtil;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.NativeModuleLister;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

public class Main {
    static final Logger f_129630_ = LogUtils.getLogger();

    @DontObfuscate
    public static void main(String[] p_129642_) {
        Main.m_239872_(p_129642_, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public static void m_239872_(String[] p_239873_, boolean p_239874_) {
        Thread $$69;
        void $$67;
        SharedConstants.m_142977_();
        if (p_239874_) {
            SharedConstants.m_214358_();
        }
        OptionParser $$2 = new OptionParser();
        $$2.allowsUnrecognizedOptions();
        $$2.accepts("demo");
        $$2.accepts("disableMultiplayer");
        $$2.accepts("disableChat");
        $$2.accepts("fullscreen");
        $$2.accepts("checkGlErrors");
        OptionSpecBuilder $$3 = $$2.accepts("jfrProfile");
        ArgumentAcceptingOptionSpec $$4 = $$2.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec $$5 = $$2.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo((Object)25565, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec $$6 = $$2.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo((Object)new File("."), (Object[])new File[0]);
        ArgumentAcceptingOptionSpec $$7 = $$2.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec $$8 = $$2.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec $$9 = $$2.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec $$10 = $$2.accepts("proxyPort").withRequiredArg().defaultsTo((Object)"8080", (Object[])new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec $$11 = $$2.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec $$12 = $$2.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec $$13 = $$2.accepts("username").withRequiredArg().defaultsTo((Object)("Player" + Util.m_137550_() % 1000L), (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$14 = $$2.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec $$15 = $$2.accepts("xuid").withOptionalArg().defaultsTo((Object)"", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$16 = $$2.accepts("clientId").withOptionalArg().defaultsTo((Object)"", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$17 = $$2.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec $$18 = $$2.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec $$19 = $$2.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo((Object)854, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec $$20 = $$2.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo((Object)480, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec $$21 = $$2.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
        ArgumentAcceptingOptionSpec $$22 = $$2.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
        ArgumentAcceptingOptionSpec $$23 = $$2.accepts("userProperties").withRequiredArg().defaultsTo((Object)"{}", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$24 = $$2.accepts("profileProperties").withRequiredArg().defaultsTo((Object)"{}", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$25 = $$2.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec $$26 = $$2.accepts("userType").withRequiredArg().defaultsTo((Object)User.Type.LEGACY.m_193808_(), (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$27 = $$2.accepts("versionType").withRequiredArg().defaultsTo((Object)"release", (Object[])new String[0]);
        NonOptionArgumentSpec $$28 = $$2.nonOptions();
        OptionSet $$29 = $$2.parse(p_239873_);
        List $$30 = $$29.valuesOf((OptionSpec)$$28);
        if (!$$30.isEmpty()) {
            System.out.println("Completely ignored arguments: " + $$30);
        }
        String $$31 = (String)Main.m_129638_($$29, $$9);
        Proxy $$32 = Proxy.NO_PROXY;
        if ($$31 != null) {
            try {
                $$32 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress($$31, (int)((Integer)Main.m_129638_($$29, $$10))));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        final String $$33 = (String)Main.m_129638_($$29, $$11);
        final String $$34 = (String)Main.m_129638_($$29, $$12);
        if (!$$32.equals(Proxy.NO_PROXY) && Main.m_129636_($$33) && Main.m_129636_($$34)) {
            Authenticator.setDefault(new Authenticator(){

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication($$33, $$34.toCharArray());
                }
            });
        }
        int $$35 = (Integer)Main.m_129638_($$29, $$19);
        int $$36 = (Integer)Main.m_129638_($$29, $$20);
        OptionalInt $$37 = Main.m_129634_((Integer)Main.m_129638_($$29, $$21));
        OptionalInt $$38 = Main.m_129634_((Integer)Main.m_129638_($$29, $$22));
        boolean $$39 = $$29.has("fullscreen");
        boolean $$40 = $$29.has("demo");
        boolean $$41 = $$29.has("disableMultiplayer");
        boolean $$42 = $$29.has("disableChat");
        String $$43 = (String)Main.m_129638_($$29, $$18);
        Gson $$44 = new GsonBuilder().registerTypeAdapter(PropertyMap.class, (Object)new PropertyMap.Serializer()).create();
        PropertyMap $$45 = GsonHelper.m_13794_($$44, (String)Main.m_129638_($$29, $$23), PropertyMap.class);
        PropertyMap $$46 = GsonHelper.m_13794_($$44, (String)Main.m_129638_($$29, $$24), PropertyMap.class);
        String $$47 = (String)Main.m_129638_($$29, $$27);
        File $$48 = (File)Main.m_129638_($$29, $$6);
        File $$49 = $$29.has((OptionSpec)$$7) ? (File)Main.m_129638_($$29, $$7) : new File($$48, "assets/");
        File $$50 = $$29.has((OptionSpec)$$8) ? (File)Main.m_129638_($$29, $$8) : new File($$48, "resourcepacks/");
        String $$51 = $$29.has((OptionSpec)$$14) ? (String)$$14.value($$29) : UUIDUtil.m_235879_((String)$$13.value($$29)).toString();
        String $$52 = $$29.has((OptionSpec)$$25) ? (String)$$25.value($$29) : null;
        String $$53 = (String)$$29.valueOf((OptionSpec)$$15);
        String $$54 = (String)$$29.valueOf((OptionSpec)$$16);
        String $$55 = (String)Main.m_129638_($$29, $$4);
        Integer $$56 = (Integer)Main.m_129638_($$29, $$5);
        if ($$29.has((OptionSpec)$$3)) {
            JvmProfiler.f_185340_.m_183425_(Environment.CLIENT);
        }
        CrashReport.m_127529_();
        Bootstrap.m_135870_();
        Bootstrap.m_135889_();
        Util.m_137584_();
        String $$57 = (String)$$26.value($$29);
        User.Type $$58 = User.Type.m_92561_($$57);
        if ($$58 == null) {
            f_129630_.warn("Unrecognized user type: {}", (Object)$$57);
        }
        User $$59 = new User((String)$$13.value($$29), $$51, (String)$$17.value($$29), Main.m_195486_($$53), Main.m_195486_($$54), $$58);
        GameConfig $$60 = new GameConfig(new GameConfig.UserData($$59, $$45, $$46, $$32), new DisplayData($$35, $$36, $$37, $$38, $$39), new GameConfig.FolderData($$48, $$50, $$49, $$52), new GameConfig.GameData($$40, $$43, $$47, $$41, $$42), new GameConfig.ServerData($$55, $$56));
        Thread $$61 = new Thread("Client Shutdown Thread"){

            @Override
            public void run() {
                Minecraft $$0 = Minecraft.m_91087_();
                if ($$0 == null) {
                    return;
                }
                IntegratedServer $$1 = $$0.m_91092_();
                if ($$1 != null) {
                    $$1.m_7570_(true);
                }
            }
        };
        $$61.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_129630_));
        Runtime.getRuntime().addShutdownHook($$61);
        try {
            Thread.currentThread().setName("Render thread");
            RenderSystem.m_69579_();
            RenderSystem.m_69395_();
            Minecraft $$62 = new Minecraft($$60);
            RenderSystem.m_69494_();
        }
        catch (SilentInitException $$63) {
            f_129630_.warn("Failed to create window: ", (Throwable)$$63);
            return;
        }
        catch (Throwable $$64) {
            CrashReport $$65 = CrashReport.m_127521_($$64, "Initializing game");
            CrashReportCategory $$66 = $$65.m_127514_("Initialization");
            NativeModuleLister.m_184679_($$66);
            Minecraft.m_167872_(null, null, $$60.f_101908_.f_101927_, null, $$65);
            Minecraft.m_91332_($$65);
            return;
        }
        if ($$67.m_91267_()) {
            Thread $$68 = new Thread("Game thread", (Minecraft)$$67){
                final /* synthetic */ Minecraft f_129652_;
                {
                    this.f_129652_ = minecraft;
                    super(p_129654_);
                }

                @Override
                public void run() {
                    try {
                        RenderSystem.m_69577_(true);
                        this.f_129652_.m_91374_();
                    }
                    catch (Throwable $$0) {
                        f_129630_.error("Exception in client thread", $$0);
                    }
                }
            };
            $$68.start();
            while ($$67.m_91396_()) {
            }
        } else {
            $$69 = null;
            try {
                RenderSystem.m_69577_(false);
                $$67.m_91374_();
            }
            catch (Throwable $$70) {
                f_129630_.error("Unhandled game exception", $$70);
            }
        }
        BufferUploader.m_166835_();
        try {
            $$67.m_91395_();
            if ($$69 != null) {
                $$69.join();
            }
        }
        catch (InterruptedException $$71) {
            f_129630_.error("Exception during client thread shutdown", (Throwable)$$71);
        }
        finally {
            $$67.m_91393_();
        }
    }

    private static Optional<String> m_195486_(String p_195487_) {
        return p_195487_.isEmpty() ? Optional.empty() : Optional.of(p_195487_);
    }

    private static OptionalInt m_129634_(@Nullable Integer p_129635_) {
        return p_129635_ != null ? OptionalInt.of(p_129635_) : OptionalInt.empty();
    }

    @Nullable
    private static <T> T m_129638_(OptionSet p_129639_, OptionSpec<T> p_129640_) {
        try {
            return (T)p_129639_.valueOf(p_129640_);
        }
        catch (Throwable $$2) {
            ArgumentAcceptingOptionSpec $$3;
            List $$4;
            if (p_129640_ instanceof ArgumentAcceptingOptionSpec && !($$4 = ($$3 = (ArgumentAcceptingOptionSpec)p_129640_).defaultValues()).isEmpty()) {
                return (T)$$4.get(0);
            }
            throw $$2;
        }
    }

    private static boolean m_129636_(@Nullable String p_129637_) {
        return p_129637_ != null && !p_129637_.isEmpty();
    }

    static {
        System.setProperty("java.awt.headless", "true");
    }
}

