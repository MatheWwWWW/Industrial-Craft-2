/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.glfw.Callbacks
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWErrorCallback
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 *  org.lwjgl.glfw.GLFWImage
 *  org.lwjgl.glfw.GLFWImage$Buffer
 *  org.lwjgl.opengl.GL
 *  org.lwjgl.stb.STBImage
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.MacosUtil;
import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.SilentInitException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;

public final class Window
implements AutoCloseable {
    private static final Logger f_85345_ = LogUtils.getLogger();
    private final GLFWErrorCallback f_85346_ = GLFWErrorCallback.create(this::m_85382_);
    private final WindowEventHandler f_85347_;
    private final ScreenManager f_85348_;
    private final long f_85349_;
    private int f_85350_;
    private int f_85351_;
    private int f_85352_;
    private int f_85353_;
    private Optional<VideoMode> f_85354_;
    private boolean f_85355_;
    private boolean f_85356_;
    private int f_85357_;
    private int f_85358_;
    private int f_85359_;
    private int f_85360_;
    private int f_85361_;
    private int f_85362_;
    private int f_85363_;
    private int f_85364_;
    private double f_85365_;
    private String f_85366_ = "";
    private boolean f_85367_;
    private int f_85368_;
    private boolean f_85369_;

    public Window(WindowEventHandler p_85372_, ScreenManager p_85373_, DisplayData p_85374_, @Nullable String p_85375_, String p_85376_) {
        RenderSystem.m_187551_();
        this.f_85348_ = p_85373_;
        this.m_85451_();
        this.m_85403_("Pre startup");
        this.f_85347_ = p_85372_;
        Optional<VideoMode> $$5 = VideoMode.m_85333_(p_85375_);
        this.f_85354_ = $$5.isPresent() ? $$5 : (p_85374_.f_84007_.isPresent() && p_85374_.f_84008_.isPresent() ? Optional.of(new VideoMode(p_85374_.f_84007_.getAsInt(), p_85374_.f_84008_.getAsInt(), 8, 8, 8, 60)) : Optional.empty());
        this.f_85356_ = this.f_85355_ = p_85374_.f_84009_;
        Monitor $$6 = p_85373_.m_85271_(GLFW.glfwGetPrimaryMonitor());
        this.f_85359_ = p_85374_.f_84005_ > 0 ? p_85374_.f_84005_ : 1;
        this.f_85352_ = this.f_85359_;
        this.f_85360_ = p_85374_.f_84006_ > 0 ? p_85374_.f_84006_ : 1;
        this.f_85353_ = this.f_85360_;
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint((int)139265, (int)196609);
        GLFW.glfwWindowHint((int)139275, (int)221185);
        GLFW.glfwWindowHint((int)139266, (int)3);
        GLFW.glfwWindowHint((int)139267, (int)2);
        GLFW.glfwWindowHint((int)139272, (int)204801);
        GLFW.glfwWindowHint((int)139270, (int)1);
        this.f_85349_ = GLFW.glfwCreateWindow((int)this.f_85359_, (int)this.f_85360_, (CharSequence)p_85376_, (long)(this.f_85355_ && $$6 != null ? $$6.m_84954_() : 0L), (long)0L);
        if ($$6 != null) {
            VideoMode $$7 = $$6.m_84948_(this.f_85355_ ? this.f_85354_ : Optional.empty());
            this.f_85350_ = this.f_85357_ = $$6.m_84951_() + $$7.m_85332_() / 2 - this.f_85359_ / 2;
            this.f_85351_ = this.f_85358_ = $$6.m_84952_() + $$7.m_85335_() / 2 - this.f_85360_ / 2;
        } else {
            int[] $$8 = new int[1];
            int[] $$9 = new int[1];
            GLFW.glfwGetWindowPos((long)this.f_85349_, (int[])$$8, (int[])$$9);
            this.f_85350_ = this.f_85357_ = $$8[0];
            this.f_85351_ = this.f_85358_ = $$9[0];
        }
        GLFW.glfwMakeContextCurrent((long)this.f_85349_);
        Locale $$10 = Locale.getDefault(Locale.Category.FORMAT);
        Locale.setDefault(Locale.Category.FORMAT, Locale.ROOT);
        GL.createCapabilities();
        Locale.setDefault(Locale.Category.FORMAT, $$10);
        this.m_85453_();
        this.m_85452_();
        GLFW.glfwSetFramebufferSizeCallback((long)this.f_85349_, this::m_85415_);
        GLFW.glfwSetWindowPosCallback((long)this.f_85349_, this::m_85388_);
        GLFW.glfwSetWindowSizeCallback((long)this.f_85349_, this::m_85427_);
        GLFW.glfwSetWindowFocusCallback((long)this.f_85349_, this::m_85392_);
        GLFW.glfwSetCursorEnterCallback((long)this.f_85349_, this::m_85419_);
    }

    public int m_85377_() {
        RenderSystem.m_187554_();
        return GLX.m_69341_(this);
    }

    public boolean m_85411_() {
        return GLX.m_69355_(this);
    }

    public static void m_85407_(BiConsumer<Integer, String> p_85408_) {
        RenderSystem.m_187551_();
        try (MemoryStack $$1 = MemoryStack.stackPush();){
            PointerBuffer $$2 = $$1.mallocPointer(1);
            int $$3 = GLFW.glfwGetError((PointerBuffer)$$2);
            if ($$3 != 0) {
                long $$4 = $$2.get();
                String $$5 = $$4 == 0L ? "" : MemoryUtil.memUTF8((long)$$4);
                p_85408_.accept($$3, $$5);
            }
        }
    }

    public void m_85395_(InputStream p_85396_, InputStream p_85397_) {
        RenderSystem.m_187551_();
        try (MemoryStack $$2 = MemoryStack.stackPush();){
            if (p_85396_ == null) {
                throw new FileNotFoundException("icons/icon_16x16.png");
            }
            if (p_85397_ == null) {
                throw new FileNotFoundException("icons/icon_32x32.png");
            }
            IntBuffer $$3 = $$2.mallocInt(1);
            IntBuffer $$4 = $$2.mallocInt(1);
            IntBuffer $$5 = $$2.mallocInt(1);
            GLFWImage.Buffer $$6 = GLFWImage.mallocStack((int)2, (MemoryStack)$$2);
            ByteBuffer $$7 = this.m_85398_(p_85396_, $$3, $$4, $$5);
            if ($$7 == null) {
                throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }
            $$6.position(0);
            $$6.width($$3.get(0));
            $$6.height($$4.get(0));
            $$6.pixels($$7);
            ByteBuffer $$8 = this.m_85398_(p_85397_, $$3, $$4, $$5);
            if ($$8 == null) {
                throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }
            $$6.position(1);
            $$6.width($$3.get(0));
            $$6.height($$4.get(0));
            $$6.pixels($$8);
            $$6.position(0);
            GLFW.glfwSetWindowIcon((long)this.f_85349_, (GLFWImage.Buffer)$$6);
            STBImage.stbi_image_free((ByteBuffer)$$7);
            STBImage.stbi_image_free((ByteBuffer)$$8);
        }
        catch (IOException $$9) {
            f_85345_.error("Couldn't set icon", (Throwable)$$9);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private ByteBuffer m_85398_(InputStream p_85399_, IntBuffer p_85400_, IntBuffer p_85401_, IntBuffer p_85402_) throws IOException {
        RenderSystem.m_187551_();
        ByteBuffer $$4 = null;
        try {
            $$4 = TextureUtil.m_85303_(p_85399_);
            $$4.rewind();
            ByteBuffer byteBuffer = STBImage.stbi_load_from_memory((ByteBuffer)$$4, (IntBuffer)p_85400_, (IntBuffer)p_85401_, (IntBuffer)p_85402_, (int)0);
            return byteBuffer;
        }
        finally {
            if ($$4 != null) {
                MemoryUtil.memFree((Buffer)$$4);
            }
        }
    }

    public void m_85403_(String p_85404_) {
        this.f_85366_ = p_85404_;
    }

    private void m_85451_() {
        RenderSystem.m_187551_();
        GLFW.glfwSetErrorCallback(Window::m_85412_);
    }

    private static void m_85412_(int p_85413_, long p_85414_) {
        RenderSystem.m_187551_();
        String $$2 = "GLFW error " + p_85413_ + ": " + MemoryUtil.memUTF8((long)p_85414_);
        TinyFileDialogs.tinyfd_messageBox((CharSequence)"Minecraft", (CharSequence)($$2 + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions)."), (CharSequence)"ok", (CharSequence)"error", (boolean)false);
        throw new WindowInitFailed($$2);
    }

    public void m_85382_(int p_85383_, long p_85384_) {
        RenderSystem.m_187554_();
        String $$2 = MemoryUtil.memUTF8((long)p_85384_);
        f_85345_.error("########## GL ERROR ##########");
        f_85345_.error("@ {}", (Object)this.f_85366_);
        f_85345_.error("{}: {}", (Object)p_85383_, (Object)$$2);
    }

    public void m_85426_() {
        GLFWErrorCallback $$0 = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)this.f_85346_);
        if ($$0 != null) {
            $$0.free();
        }
    }

    public void m_85409_(boolean p_85410_) {
        RenderSystem.m_187555_();
        this.f_85369_ = p_85410_;
        GLFW.glfwSwapInterval((int)(p_85410_ ? 1 : 0));
    }

    @Override
    public void close() {
        RenderSystem.m_187554_();
        Callbacks.glfwFreeCallbacks((long)this.f_85349_);
        this.f_85346_.close();
        GLFW.glfwDestroyWindow((long)this.f_85349_);
        GLFW.glfwTerminate();
    }

    private void m_85388_(long p_85389_, int p_85390_, int p_85391_) {
        this.f_85357_ = p_85390_;
        this.f_85358_ = p_85391_;
    }

    private void m_85415_(long p_85416_, int p_85417_, int p_85418_) {
        if (p_85416_ != this.f_85349_) {
            return;
        }
        int $$3 = this.m_85441_();
        int $$4 = this.m_85442_();
        if (p_85417_ == 0 || p_85418_ == 0) {
            return;
        }
        this.f_85361_ = p_85417_;
        this.f_85362_ = p_85418_;
        if (this.m_85441_() != $$3 || this.m_85442_() != $$4) {
            this.f_85347_.m_5741_();
        }
    }

    private void m_85452_() {
        RenderSystem.m_187551_();
        int[] $$0 = new int[1];
        int[] $$1 = new int[1];
        GLFW.glfwGetFramebufferSize((long)this.f_85349_, (int[])$$0, (int[])$$1);
        this.f_85361_ = $$0[0] > 0 ? $$0[0] : 1;
        this.f_85362_ = $$1[0] > 0 ? $$1[0] : 1;
    }

    private void m_85427_(long p_85428_, int p_85429_, int p_85430_) {
        this.f_85359_ = p_85429_;
        this.f_85360_ = p_85430_;
    }

    private void m_85392_(long p_85393_, boolean p_85394_) {
        if (p_85393_ == this.f_85349_) {
            this.f_85347_.m_7440_(p_85394_);
        }
    }

    private void m_85419_(long p_85420_, boolean p_85421_) {
        if (p_85421_) {
            this.f_85347_.m_5740_();
        }
    }

    public void m_85380_(int p_85381_) {
        this.f_85368_ = p_85381_;
    }

    public int m_85434_() {
        return this.f_85368_;
    }

    public void m_85435_() {
        RenderSystem.m_69495_(this.f_85349_);
        if (this.f_85355_ != this.f_85356_) {
            this.f_85356_ = this.f_85355_;
            this.m_85431_(this.f_85369_);
        }
    }

    public Optional<VideoMode> m_85436_() {
        return this.f_85354_;
    }

    public void m_85405_(Optional<VideoMode> p_85406_) {
        boolean $$1 = !p_85406_.equals(this.f_85354_);
        this.f_85354_ = p_85406_;
        if ($$1) {
            this.f_85367_ = true;
        }
    }

    public void m_85437_() {
        if (this.f_85355_ && this.f_85367_) {
            this.f_85367_ = false;
            this.m_85453_();
            this.f_85347_.m_5741_();
        }
    }

    private void m_85453_() {
        boolean $$0;
        RenderSystem.m_187551_();
        boolean bl = $$0 = GLFW.glfwGetWindowMonitor((long)this.f_85349_) != 0L;
        if (this.f_85355_) {
            Monitor $$1 = this.f_85348_.m_85276_(this);
            if ($$1 == null) {
                f_85345_.warn("Failed to find suitable monitor for fullscreen mode");
                this.f_85355_ = false;
            } else {
                if (Minecraft.f_91002_) {
                    MacosUtil.m_182517_(this.f_85349_);
                }
                VideoMode $$2 = $$1.m_84948_(this.f_85354_);
                if (!$$0) {
                    this.f_85350_ = this.f_85357_;
                    this.f_85351_ = this.f_85358_;
                    this.f_85352_ = this.f_85359_;
                    this.f_85353_ = this.f_85360_;
                }
                this.f_85357_ = 0;
                this.f_85358_ = 0;
                this.f_85359_ = $$2.m_85332_();
                this.f_85360_ = $$2.m_85335_();
                GLFW.glfwSetWindowMonitor((long)this.f_85349_, (long)$$1.m_84954_(), (int)this.f_85357_, (int)this.f_85358_, (int)this.f_85359_, (int)this.f_85360_, (int)$$2.m_85341_());
            }
        } else {
            this.f_85357_ = this.f_85350_;
            this.f_85358_ = this.f_85351_;
            this.f_85359_ = this.f_85352_;
            this.f_85360_ = this.f_85353_;
            GLFW.glfwSetWindowMonitor((long)this.f_85349_, (long)0L, (int)this.f_85357_, (int)this.f_85358_, (int)this.f_85359_, (int)this.f_85360_, (int)-1);
        }
    }

    public void m_85438_() {
        this.f_85355_ = !this.f_85355_;
    }

    public void m_166447_(int p_166448_, int p_166449_) {
        this.f_85352_ = p_166448_;
        this.f_85353_ = p_166449_;
        this.f_85355_ = false;
        this.m_85453_();
    }

    private void m_85431_(boolean p_85432_) {
        RenderSystem.m_187554_();
        try {
            this.m_85453_();
            this.f_85347_.m_5741_();
            this.m_85409_(p_85432_);
            this.m_85435_();
        }
        catch (Exception $$1) {
            f_85345_.error("Couldn't toggle fullscreen", (Throwable)$$1);
        }
    }

    public int m_85385_(int p_85386_, boolean p_85387_) {
        int $$2;
        for ($$2 = 1; $$2 != p_85386_ && $$2 < this.f_85361_ && $$2 < this.f_85362_ && this.f_85361_ / ($$2 + 1) >= 320 && this.f_85362_ / ($$2 + 1) >= 240; ++$$2) {
        }
        if (p_85387_ && $$2 % 2 != 0) {
            ++$$2;
        }
        return $$2;
    }

    public void m_85378_(double p_85379_) {
        this.f_85365_ = p_85379_;
        int $$1 = (int)((double)this.f_85361_ / p_85379_);
        this.f_85363_ = (double)this.f_85361_ / p_85379_ > (double)$$1 ? $$1 + 1 : $$1;
        int $$2 = (int)((double)this.f_85362_ / p_85379_);
        this.f_85364_ = (double)this.f_85362_ / p_85379_ > (double)$$2 ? $$2 + 1 : $$2;
    }

    public void m_85422_(String p_85423_) {
        GLFW.glfwSetWindowTitle((long)this.f_85349_, (CharSequence)p_85423_);
    }

    public long m_85439_() {
        return this.f_85349_;
    }

    public boolean m_85440_() {
        return this.f_85355_;
    }

    public int m_85441_() {
        return this.f_85361_;
    }

    public int m_85442_() {
        return this.f_85362_;
    }

    public void m_166450_(int p_166451_) {
        this.f_85361_ = p_166451_;
    }

    public void m_166452_(int p_166453_) {
        this.f_85362_ = p_166453_;
    }

    public int m_85443_() {
        return this.f_85359_;
    }

    public int m_85444_() {
        return this.f_85360_;
    }

    public int m_85445_() {
        return this.f_85363_;
    }

    public int m_85446_() {
        return this.f_85364_;
    }

    public int m_85447_() {
        return this.f_85357_;
    }

    public int m_85448_() {
        return this.f_85358_;
    }

    public double m_85449_() {
        return this.f_85365_;
    }

    @Nullable
    public Monitor m_85450_() {
        return this.f_85348_.m_85276_(this);
    }

    public void m_85424_(boolean p_85425_) {
        InputConstants.m_84848_(this.f_85349_, p_85425_);
    }

    public static class WindowInitFailed
    extends SilentInitException {
        WindowInitFailed(String p_85455_) {
            super(p_85455_);
        }
    }
}

