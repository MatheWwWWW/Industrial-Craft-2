/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntConsumer
 *  javax.annotation.Nullable
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.systems;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeSource;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.slf4j.Logger;

@DontObfuscate
public class RenderSystem {
    static final Logger f_69376_ = LogUtils.getLogger();
    private static final ConcurrentLinkedQueue<RenderCall> f_69384_ = Queues.newConcurrentLinkedQueue();
    private static final Tesselator f_69379_ = new Tesselator();
    private static final int f_69378_ = 1024;
    private static boolean f_69382_;
    @Nullable
    private static Thread f_69380_;
    @Nullable
    private static Thread f_69385_;
    private static int f_69377_;
    private static boolean f_69381_;
    private static double f_69383_;
    private static final AutoStorageIndexBuffer f_157153_;
    private static final AutoStorageIndexBuffer f_157155_;
    private static final AutoStorageIndexBuffer f_157154_;
    private static Matrix3f f_200905_;
    private static Matrix4f f_157142_;
    private static Matrix4f f_157143_;
    private static PoseStack f_157141_;
    private static Matrix4f f_157140_;
    private static Matrix4f f_157156_;
    private static final int[] f_157152_;
    private static final float[] f_157145_;
    private static float f_157148_;
    private static float f_157147_;
    private static final float[] f_157146_;
    private static FogShape f_202038_;
    private static final Vector3f[] f_157150_;
    private static float f_157149_;
    private static float f_157151_;
    private static String f_187550_;
    @Nullable
    private static ShaderInstance f_157144_;

    public static void m_69579_() {
        if (f_69385_ != null || f_69380_ == Thread.currentThread()) {
            throw new IllegalStateException("Could not initialize render thread");
        }
        f_69385_ = Thread.currentThread();
    }

    public static boolean m_69586_() {
        return Thread.currentThread() == f_69385_;
    }

    public static boolean m_69587_() {
        return f_69381_ || RenderSystem.m_69586_();
    }

    public static void m_69577_(boolean p_69578_) {
        boolean $$1;
        boolean bl = $$1 = f_69385_ == Thread.currentThread();
        if (f_69380_ != null || f_69385_ == null || $$1 == p_69578_) {
            throw new IllegalStateException("Could not initialize tick thread");
        }
        f_69380_ = Thread.currentThread();
    }

    public static boolean m_69584_() {
        return true;
    }

    public static void m_187551_() {
        if (!RenderSystem.m_69583_()) {
            throw RenderSystem.m_187556_();
        }
    }

    public static void m_187553_() {
        if (f_69381_ || RenderSystem.m_69584_()) {
            return;
        }
        throw RenderSystem.m_187556_();
    }

    public static void m_187555_() {
        if (f_69381_ || RenderSystem.m_69586_()) {
            return;
        }
        throw RenderSystem.m_187556_();
    }

    public static void m_187554_() {
        if (!RenderSystem.m_69586_()) {
            throw RenderSystem.m_187556_();
        }
    }

    public static void m_187552_() {
        if (!RenderSystem.m_69584_()) {
            throw RenderSystem.m_187556_();
        }
    }

    private static IllegalStateException m_187556_() {
        return new IllegalStateException("Rendersystem called from wrong thread");
    }

    public static boolean m_69583_() {
        return true;
    }

    public static void m_69879_(RenderCall p_69880_) {
        f_69384_.add(p_69880_);
    }

    public static void m_69495_(long p_69496_) {
        GLFW.glfwPollEvents();
        RenderSystem.m_69884_();
        Tesselator.m_85913_().m_85915_().m_85729_();
        GLFW.glfwSwapBuffers((long)p_69496_);
        GLFW.glfwPollEvents();
    }

    public static void m_69884_() {
        f_69382_ = true;
        while (!f_69384_.isEmpty()) {
            RenderCall $$0 = f_69384_.poll();
            $$0.m_83909_();
        }
        f_69382_ = false;
    }

    public static void m_69830_(int p_69831_) {
        double $$1 = f_69383_ + 1.0 / (double)p_69831_;
        double $$2 = GLFW.glfwGetTime();
        while ($$2 < $$1) {
            GLFW.glfwWaitEventsTimeout((double)($$1 - $$2));
            $$2 = GLFW.glfwGetTime();
        }
        f_69383_ = $$2;
    }

    public static void m_69465_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84507_();
    }

    public static void m_69482_() {
        RenderSystem.m_187553_();
        GlStateManager.m_84513_();
    }

    public static void m_69488_(int p_69489_, int p_69490_, int p_69491_, int p_69492_) {
        RenderSystem.m_187553_();
        GlStateManager.m_84501_();
        GlStateManager.m_84168_(p_69489_, p_69490_, p_69491_, p_69492_);
    }

    public static void m_69471_() {
        RenderSystem.m_187553_();
        GlStateManager.m_84495_();
    }

    public static void m_69456_(int p_69457_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84323_(p_69457_);
    }

    public static void m_69458_(boolean p_69459_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84298_(p_69459_);
    }

    public static void m_69478_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84525_();
    }

    public static void m_69461_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84519_();
    }

    public static void m_69408_(GlStateManager.SourceFactor p_69409_, GlStateManager.DestFactor p_69410_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84328_(p_69409_.f_84751_, p_69410_.f_84646_);
    }

    public static void m_69405_(int p_69406_, int p_69407_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84328_(p_69406_, p_69407_);
    }

    public static void m_69416_(GlStateManager.SourceFactor p_69417_, GlStateManager.DestFactor p_69418_, GlStateManager.SourceFactor p_69419_, GlStateManager.DestFactor p_69420_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84335_(p_69417_.f_84751_, p_69418_.f_84646_, p_69419_.f_84751_, p_69420_.f_84646_);
    }

    public static void m_69411_(int p_69412_, int p_69413_, int p_69414_, int p_69415_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84335_(p_69412_, p_69413_, p_69414_, p_69415_);
    }

    public static void m_69403_(int p_69404_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84379_(p_69404_);
    }

    public static void m_69481_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84091_();
    }

    public static void m_69464_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84094_();
    }

    public static void m_69860_(int p_69861_, int p_69862_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84516_(p_69861_, p_69862_);
    }

    public static void m_69486_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84097_();
    }

    public static void m_69469_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84100_();
    }

    public static void m_69863_(float p_69864_, float p_69865_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84136_(p_69864_, p_69865_);
    }

    public static void m_69479_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84107_();
    }

    public static void m_69462_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84108_();
    }

    public static void m_69835_(GlStateManager.LogicOp p_69836_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84532_(p_69836_.f_84715_);
    }

    public static void m_69388_(int p_69389_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84538_(p_69389_);
    }

    public static void m_69493_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84109_();
    }

    public static void m_69472_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84110_();
    }

    public static void m_69937_(int p_69938_, int p_69939_, int p_69940_) {
        GlStateManager.m_84331_(p_69938_, p_69939_, p_69940_);
    }

    public static void m_69454_(int p_69455_) {
        RenderSystem.m_187553_();
        GlStateManager.m_84541_(p_69455_);
    }

    public static void m_157184_(int p_157185_) {
        RenderSystem.m_69396_(p_157185_);
    }

    public static void m_69396_(int p_69397_) {
        GlStateManager.m_84544_(p_69397_);
    }

    public static void m_69949_(int p_69950_, int p_69951_, int p_69952_, int p_69953_) {
        RenderSystem.m_187553_();
        GlStateManager.m_84430_(p_69950_, p_69951_, p_69952_, p_69953_);
    }

    public static void m_69444_(boolean p_69445_, boolean p_69446_, boolean p_69447_, boolean p_69448_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84300_(p_69445_, p_69446_, p_69447_, p_69448_);
    }

    public static void m_69925_(int p_69926_, int p_69927_, int p_69928_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84426_(p_69926_, p_69927_, p_69928_);
    }

    public static void m_69929_(int p_69930_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84550_(p_69930_);
    }

    public static void m_69931_(int p_69932_, int p_69933_, int p_69934_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84452_(p_69932_, p_69933_, p_69934_);
    }

    public static void m_69430_(double p_69431_) {
        RenderSystem.m_187553_();
        GlStateManager.m_84121_(p_69431_);
    }

    public static void m_69424_(float p_69425_, float p_69426_, float p_69427_, float p_69428_) {
        RenderSystem.m_187553_();
        GlStateManager.m_84318_(p_69425_, p_69426_, p_69427_, p_69428_);
    }

    public static void m_69432_(int p_69433_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84553_(p_69433_);
    }

    public static void m_69421_(int p_69422_, boolean p_69423_) {
        RenderSystem.m_187553_();
        GlStateManager.m_84266_(p_69422_, p_69423_);
    }

    public static void m_157445_(float p_157446_) {
        RenderSystem.m_187554_();
        RenderSystem.m_157171_(p_157446_);
    }

    private static void m_157171_(float p_157172_) {
        f_157148_ = p_157172_;
    }

    public static float m_157200_() {
        RenderSystem.m_187554_();
        return f_157148_;
    }

    public static void m_157443_(float p_157444_) {
        RenderSystem.m_187554_();
        RenderSystem.m_157169_(p_157444_);
    }

    private static void m_157169_(float p_157170_) {
        f_157147_ = p_157170_;
    }

    public static float m_157199_() {
        RenderSystem.m_187554_();
        return f_157147_;
    }

    public static void m_157438_(float p_157439_, float p_157440_, float p_157441_, float p_157442_) {
        RenderSystem.m_187554_();
        RenderSystem.m_157164_(p_157439_, p_157440_, p_157441_, p_157442_);
    }

    public static void m_157434_(float p_157435_, float p_157436_, float p_157437_) {
        RenderSystem.m_157438_(p_157435_, p_157436_, p_157437_, 1.0f);
    }

    private static void m_157164_(float p_157165_, float p_157166_, float p_157167_, float p_157168_) {
        RenderSystem.f_157146_[0] = p_157165_;
        RenderSystem.f_157146_[1] = p_157166_;
        RenderSystem.f_157146_[2] = p_157167_;
        RenderSystem.f_157146_[3] = p_157168_;
    }

    public static float[] m_157198_() {
        RenderSystem.m_187554_();
        return f_157146_;
    }

    public static void m_202160_(FogShape p_202161_) {
        RenderSystem.m_187554_();
        RenderSystem.m_202039_(p_202161_);
    }

    private static void m_202039_(FogShape p_202040_) {
        f_202038_ = p_202040_;
    }

    public static FogShape m_202041_() {
        RenderSystem.m_187554_();
        return f_202038_;
    }

    public static void m_157450_(Vector3f p_157451_, Vector3f p_157452_) {
        RenderSystem.m_187554_();
        RenderSystem.m_157173_(p_157451_, p_157452_);
    }

    public static void m_157173_(Vector3f p_157174_, Vector3f p_157175_) {
        RenderSystem.f_157150_[0] = p_157174_;
        RenderSystem.f_157150_[1] = p_157175_;
    }

    public static void m_157461_(ShaderInstance p_157462_) {
        RenderSystem.m_187554_();
        if (p_157462_.f_173313_ != null) {
            p_157462_.f_173313_.m_142276_(f_157150_[0]);
        }
        if (p_157462_.f_173314_ != null) {
            p_157462_.f_173314_.m_142276_(f_157150_[1]);
        }
    }

    public static void m_157429_(float p_157430_, float p_157431_, float p_157432_, float p_157433_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> RenderSystem.m_157159_(p_157430_, p_157431_, p_157432_, p_157433_));
        } else {
            RenderSystem.m_157159_(p_157430_, p_157431_, p_157432_, p_157433_);
        }
    }

    private static void m_157159_(float p_157160_, float p_157161_, float p_157162_, float p_157163_) {
        RenderSystem.f_157145_[0] = p_157160_;
        RenderSystem.f_157145_[1] = p_157161_;
        RenderSystem.f_157145_[2] = p_157162_;
        RenderSystem.f_157145_[3] = p_157163_;
    }

    public static float[] m_157197_() {
        RenderSystem.m_187554_();
        return f_157145_;
    }

    public static void m_157186_(int p_157187_, int p_157188_, int p_157189_) {
        RenderSystem.m_187554_();
        GlStateManager.m_157053_(p_157187_, p_157188_, p_157189_, 0L);
    }

    public static void m_69832_(float p_69833_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                f_157151_ = p_69833_;
            });
        } else {
            f_157151_ = p_69833_;
        }
    }

    public static float m_157202_() {
        RenderSystem.m_187554_();
        return f_157151_;
    }

    public static void m_69854_(int p_69855_, int p_69856_) {
        RenderSystem.m_187553_();
        GlStateManager.m_84522_(p_69855_, p_69856_);
    }

    public static void m_69871_(int p_69872_, int p_69873_, int p_69874_, int p_69875_, int p_69876_, int p_69877_, ByteBuffer p_69878_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84219_(p_69872_, p_69873_, p_69874_, p_69875_, p_69876_, p_69877_, p_69878_);
    }

    public static void m_69519_(int p_69520_, Consumer<String> p_69521_) {
        RenderSystem.m_187554_();
        p_69521_.accept(GlStateManager.m_84089_(p_69520_));
    }

    public static String m_69517_() {
        RenderSystem.m_187551_();
        return String.format(Locale.ROOT, "LWJGL version %s", GLX.m_69340_());
    }

    public static String m_69516_() {
        return f_187550_;
    }

    public static TimeSource.NanoTimeSource m_240147_() {
        RenderSystem.m_187551_();
        return GLX.m_69346_()::getAsLong;
    }

    public static void m_69580_(int p_69581_, boolean p_69582_) {
        RenderSystem.m_187551_();
        GLX.m_69343_(p_69581_, p_69582_);
        f_187550_ = GLX.m_69359_();
    }

    public static void m_69900_(GLFWErrorCallbackI p_69901_) {
        RenderSystem.m_187551_();
        GLX.m_69352_(p_69901_);
    }

    public static void m_69881_(int p_69882_) {
        RenderSystem.m_187554_();
        GLX.m_69347_(p_69882_, true, true, true);
    }

    public static String m_69518_() {
        RenderSystem.m_187554_();
        return "Using framebuffer using OpenGL 3.2";
    }

    public static void m_69902_(int p_69903_, int p_69904_, int p_69905_, int p_69906_) {
        RenderSystem.m_187551_();
        GlStateManager.m_84109_();
        GlStateManager.m_84121_(1.0);
        GlStateManager.m_84513_();
        GlStateManager.m_84323_(515);
        f_157142_.m_27624_();
        f_157143_.m_27624_();
        f_157140_.m_27624_();
        f_157156_.m_27624_();
        GlStateManager.m_84430_(p_69903_, p_69904_, p_69905_, p_69906_);
    }

    public static int m_69839_() {
        if (f_69377_ == -1) {
            RenderSystem.m_187555_();
            int $$0 = GlStateManager.m_84092_(3379);
            for (int $$1 = Math.max(32768, $$0); $$1 >= 1024; $$1 >>= 1) {
                GlStateManager.m_84209_(32868, 0, 6408, $$1, $$1, 0, 6408, 5121, null);
                int $$2 = GlStateManager.m_84384_(32868, 0, 4096);
                if ($$2 == 0) continue;
                f_69377_ = $$1;
                return $$1;
            }
            f_69377_ = Math.max($$0, 1024);
            f_69376_.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", (Object)f_69377_);
        }
        return f_69377_;
    }

    public static void m_157208_(int p_157209_, IntSupplier p_157210_) {
        GlStateManager.m_84480_(p_157209_, p_157210_.getAsInt());
    }

    public static void m_157211_(Supplier<Integer> p_157212_) {
        GlStateManager.m_157068_(p_157212_.get());
    }

    public static void m_69525_(int p_69526_, ByteBuffer p_69527_, int p_69528_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84256_(p_69526_, p_69527_, p_69528_);
    }

    public static void m_69529_(int p_69530_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84496_(p_69530_);
    }

    public static void m_157213_(int p_157214_) {
        RenderSystem.m_187554_();
        GlStateManager.m_157076_(p_157214_);
    }

    public static void m_69543_(int p_69544_, int p_69545_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84467_(p_69544_, p_69545_);
    }

    public static void m_69540_(int p_69541_, IntBuffer p_69542_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84263_(p_69541_, p_69542_);
    }

    public static void m_69549_(int p_69550_, IntBuffer p_69551_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84351_(p_69550_, p_69551_);
    }

    public static void m_69555_(int p_69556_, IntBuffer p_69557_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84404_(p_69556_, p_69557_);
    }

    public static void m_69561_(int p_69562_, IntBuffer p_69563_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84438_(p_69562_, p_69563_);
    }

    public static void m_69537_(int p_69538_, FloatBuffer p_69539_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84348_(p_69538_, p_69539_);
    }

    public static void m_69546_(int p_69547_, FloatBuffer p_69548_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84401_(p_69547_, p_69548_);
    }

    public static void m_69552_(int p_69553_, FloatBuffer p_69554_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84435_(p_69553_, p_69554_);
    }

    public static void m_69558_(int p_69559_, FloatBuffer p_69560_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84461_(p_69559_, p_69560_);
    }

    public static void m_69564_(int p_69565_, boolean p_69566_, FloatBuffer p_69567_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84269_(p_69565_, p_69566_, p_69567_);
    }

    public static void m_69568_(int p_69569_, boolean p_69570_, FloatBuffer p_69571_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84354_(p_69569_, p_69570_, p_69571_);
    }

    public static void m_69572_(int p_69573_, boolean p_69574_, FloatBuffer p_69575_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84407_(p_69573_, p_69574_, p_69575_);
    }

    public static void m_69920_(IntSupplier p_69921_, int p_69922_) {
        RenderSystem.m_187554_();
        int $$2 = p_69921_.getAsInt();
        RenderSystem.m_157453_(1, $$2);
    }

    public static void m_69936_() {
        RenderSystem.m_187554_();
        RenderSystem.m_157453_(1, 0);
    }

    public static void m_69914_(Vector3f p_69915_, Vector3f p_69916_, Matrix4f p_69917_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84290_(p_69915_, p_69916_, p_69917_);
    }

    public static void m_69911_(Vector3f p_69912_, Vector3f p_69913_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84287_(p_69912_, p_69913_);
    }

    public static void m_69908_(Vector3f p_69909_, Vector3f p_69910_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84360_(p_69909_, p_69910_);
    }

    public static void m_69395_() {
        f_69381_ = true;
    }

    public static void m_69494_() {
        f_69381_ = false;
        if (!f_69384_.isEmpty()) {
            RenderSystem.m_69884_();
        }
        if (!f_69384_.isEmpty()) {
            throw new IllegalStateException("Recorded to render queue during initialization");
        }
    }

    public static void m_69531_(Consumer<Integer> p_69532_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> p_69532_.accept(GlStateManager.m_84537_()));
        } else {
            p_69532_.accept(GlStateManager.m_84537_());
        }
    }

    public static void m_157215_(Consumer<Integer> p_157216_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> p_157216_.accept(GlStateManager.m_157089_()));
        } else {
            p_157216_.accept(GlStateManager.m_157089_());
        }
    }

    public static Tesselator m_69883_() {
        RenderSystem.m_187554_();
        return f_69379_;
    }

    public static void m_69453_() {
        RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    @Deprecated
    public static void m_69890_(Runnable p_69891_) {
        boolean $$1 = Minecraft.m_91085_();
        if (!$$1) {
            p_69891_.run();
            return;
        }
        OptionInstance<GraphicsStatus> $$2 = Minecraft.m_91087_().f_91066_.m_232060_();
        GraphicsStatus $$3 = $$2.m_231551_();
        $$2.m_231514_(GraphicsStatus.FANCY);
        p_69891_.run();
        $$2.m_231514_($$3);
    }

    public static void m_157427_(Supplier<ShaderInstance> p_157428_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                f_157144_ = (ShaderInstance)p_157428_.get();
            });
        } else {
            f_157144_ = p_157428_.get();
        }
    }

    @Nullable
    public static ShaderInstance m_157196_() {
        RenderSystem.m_187554_();
        return f_157144_;
    }

    public static int m_157205_(int p_157206_) {
        return GlStateManager.m_157059_(p_157206_);
    }

    public static void m_157456_(int p_157457_, ResourceLocation p_157458_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> RenderSystem.m_157179_(p_157457_, p_157458_));
        } else {
            RenderSystem.m_157179_(p_157457_, p_157458_);
        }
    }

    public static void m_157179_(int p_157180_, ResourceLocation p_157181_) {
        if (p_157180_ >= 0 && p_157180_ < f_157152_.length) {
            TextureManager $$2 = Minecraft.m_91087_().m_91097_();
            AbstractTexture $$3 = $$2.m_118506_(p_157181_);
            RenderSystem.f_157152_[p_157180_] = $$3.m_117963_();
        }
    }

    public static void m_157453_(int p_157454_, int p_157455_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> RenderSystem.m_157176_(p_157454_, p_157455_));
        } else {
            RenderSystem.m_157176_(p_157454_, p_157455_);
        }
    }

    public static void m_157176_(int p_157177_, int p_157178_) {
        if (p_157177_ >= 0 && p_157177_ < f_157152_.length) {
            RenderSystem.f_157152_[p_157177_] = p_157178_;
        }
    }

    public static int m_157203_(int p_157204_) {
        RenderSystem.m_187554_();
        if (p_157204_ >= 0 && p_157204_ < f_157152_.length) {
            return f_157152_[p_157204_];
        }
        return 0;
    }

    public static void m_157425_(Matrix4f p_157426_) {
        Matrix4f $$1 = p_157426_.m_27658_();
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                f_157142_ = $$1;
            });
        } else {
            f_157142_ = $$1;
        }
    }

    public static void m_200918_(Matrix3f p_200919_) {
        Matrix3f $$1 = p_200919_.m_8183_();
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                f_200905_ = $$1;
            });
        } else {
            f_200905_ = $$1;
        }
    }

    public static void m_157459_(Matrix4f p_157460_) {
        Matrix4f $$1 = p_157460_.m_27658_();
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                f_157156_ = $$1;
            });
        } else {
            f_157156_ = $$1;
        }
    }

    public static void m_157423_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> f_157156_.m_27624_());
        } else {
            f_157156_.m_27624_();
        }
    }

    public static void m_157182_() {
        Matrix4f $$0 = f_157141_.m_85850_().m_85861_().m_27658_();
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                f_157140_ = $$0;
            });
        } else {
            f_157140_ = $$0;
        }
    }

    public static void m_157183_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> RenderSystem.m_157157_());
        } else {
            RenderSystem.m_157157_();
        }
    }

    private static void m_157157_() {
        f_157143_ = f_157142_;
    }

    public static void m_157424_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> RenderSystem.m_157158_());
        } else {
            RenderSystem.m_157158_();
        }
    }

    private static void m_157158_() {
        f_157142_ = f_157143_;
    }

    public static Matrix4f m_157192_() {
        RenderSystem.m_187554_();
        return f_157142_;
    }

    public static Matrix3f m_200906_() {
        RenderSystem.m_187554_();
        return f_200905_;
    }

    public static Matrix4f m_157190_() {
        RenderSystem.m_187554_();
        return f_157140_;
    }

    public static PoseStack m_157191_() {
        return f_157141_;
    }

    public static Matrix4f m_157207_() {
        RenderSystem.m_187554_();
        return f_157156_;
    }

    public static AutoStorageIndexBuffer m_221941_(VertexFormat.Mode p_221942_) {
        RenderSystem.m_187554_();
        return switch (p_221942_) {
            case VertexFormat.Mode.QUADS -> f_157155_;
            case VertexFormat.Mode.LINES -> f_157154_;
            default -> f_157153_;
        };
    }

    public static void m_157447_(long p_157448_, float p_157449_) {
        float $$2 = ((float)(p_157448_ % 24000L) + p_157449_) / 24000.0f;
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                f_157149_ = $$2;
            });
        } else {
            f_157149_ = $$2;
        }
    }

    public static float m_157201_() {
        RenderSystem.m_187554_();
        return f_157149_;
    }

    private static /* synthetic */ void m_202147_(Vector3f p_202148_, Vector3f p_202149_) {
        GlStateManager.m_84360_(p_202148_, p_202149_);
    }

    private static /* synthetic */ void m_202150_(Vector3f p_202151_, Vector3f p_202152_) {
        GlStateManager.m_84287_(p_202151_, p_202152_);
    }

    private static /* synthetic */ void m_202153_(Vector3f p_202154_, Vector3f p_202155_, Matrix4f p_202156_) {
        GlStateManager.m_84290_(p_202154_, p_202155_, p_202156_);
    }

    private static /* synthetic */ void m_202159_() {
        RenderSystem.m_157453_(1, 0);
    }

    private static /* synthetic */ void m_202157_(IntSupplier p_202158_) {
        int $$1 = p_202158_.getAsInt();
        RenderSystem.m_157453_(1, $$1);
    }

    private static /* synthetic */ void m_202100_(int p_202101_, boolean p_202102_, FloatBuffer p_202103_) {
        GlStateManager.m_84407_(p_202101_, p_202102_, p_202103_);
    }

    private static /* synthetic */ void m_202096_(int p_202097_, boolean p_202098_, FloatBuffer p_202099_) {
        GlStateManager.m_84354_(p_202097_, p_202098_, p_202099_);
    }

    private static /* synthetic */ void m_202092_(int p_202093_, boolean p_202094_, FloatBuffer p_202095_) {
        GlStateManager.m_84269_(p_202093_, p_202094_, p_202095_);
    }

    private static /* synthetic */ void m_202089_(int p_202090_, FloatBuffer p_202091_) {
        GlStateManager.m_84461_(p_202090_, p_202091_);
    }

    private static /* synthetic */ void m_202083_(int p_202084_, FloatBuffer p_202085_) {
        GlStateManager.m_84435_(p_202084_, p_202085_);
    }

    private static /* synthetic */ void m_202077_(int p_202078_, FloatBuffer p_202079_) {
        GlStateManager.m_84401_(p_202078_, p_202079_);
    }

    private static /* synthetic */ void m_202068_(int p_202069_, FloatBuffer p_202070_) {
        GlStateManager.m_84348_(p_202069_, p_202070_);
    }

    private static /* synthetic */ void m_202086_(int p_202087_, IntBuffer p_202088_) {
        GlStateManager.m_84438_(p_202087_, p_202088_);
    }

    private static /* synthetic */ void m_202080_(int p_202081_, IntBuffer p_202082_) {
        GlStateManager.m_84404_(p_202081_, p_202082_);
    }

    private static /* synthetic */ void m_202074_(int p_202075_, IntBuffer p_202076_) {
        GlStateManager.m_84351_(p_202075_, p_202076_);
    }

    private static /* synthetic */ void m_202065_(int p_202066_, IntBuffer p_202067_) {
        GlStateManager.m_84263_(p_202066_, p_202067_);
    }

    private static /* synthetic */ void m_202071_(int p_202072_, int p_202073_) {
        GlStateManager.m_84467_(p_202072_, p_202073_);
    }

    private static /* synthetic */ void m_202059_(int p_202060_) {
        GlStateManager.m_157076_(p_202060_);
    }

    private static /* synthetic */ void m_202057_(int p_202058_) {
        GlStateManager.m_84496_(p_202058_);
    }

    private static /* synthetic */ void m_202055_(Supplier p_202056_) {
        GlStateManager.m_157068_((Integer)p_202056_.get());
    }

    private static /* synthetic */ void m_202052_(int p_202053_, IntSupplier p_202054_) {
        GlStateManager.m_84480_(p_202053_, p_202054_.getAsInt());
    }

    private static /* synthetic */ void m_202117_(int p_202118_) {
        GLX.m_69347_(p_202118_, true, true, true);
    }

    private static /* synthetic */ void m_202049_(int p_202050_, Consumer p_202051_) {
        String $$2 = GlStateManager.m_84089_(p_202050_);
        p_202051_.accept($$2);
    }

    private static /* synthetic */ void m_202109_(int p_202110_, int p_202111_, int p_202112_, int p_202113_, int p_202114_, int p_202115_, ByteBuffer p_202116_) {
        GlStateManager.m_84219_(p_202110_, p_202111_, p_202112_, p_202113_, p_202114_, p_202115_, p_202116_);
    }

    private static /* synthetic */ void m_202106_(int p_202107_, int p_202108_) {
        GlStateManager.m_84522_(p_202107_, p_202108_);
    }

    private static /* synthetic */ void m_202045_(int p_202046_, int p_202047_, int p_202048_) {
        GlStateManager.m_157053_(p_202046_, p_202047_, p_202048_, 0L);
    }

    private static /* synthetic */ void m_202136_(Vector3f p_202137_, Vector3f p_202138_) {
        RenderSystem.m_157173_(p_202137_, p_202138_);
    }

    private static /* synthetic */ void m_202132_(FogShape p_202133_) {
        RenderSystem.m_202039_(p_202133_);
    }

    private static /* synthetic */ void m_157363_(float p_157364_, float p_157365_, float p_157366_, float p_157367_) {
        RenderSystem.m_157164_(p_157364_, p_157365_, p_157366_, p_157367_);
    }

    private static /* synthetic */ void m_157368_(float p_157369_) {
        RenderSystem.m_157169_(p_157369_);
    }

    private static /* synthetic */ void m_157370_(float p_157371_) {
        RenderSystem.m_157171_(p_157371_);
    }

    private static /* synthetic */ void m_157242_(int p_157243_, boolean p_157244_) {
        GlStateManager.m_84266_(p_157243_, p_157244_);
    }

    private static /* synthetic */ void m_157252_(int p_157253_) {
        GlStateManager.m_84553_(p_157253_);
    }

    private static /* synthetic */ void m_157245_(float p_157246_, float p_157247_, float p_157248_, float p_157249_) {
        GlStateManager.m_84318_(p_157246_, p_157247_, p_157248_, p_157249_);
    }

    private static /* synthetic */ void m_157250_(double p_157251_) {
        GlStateManager.m_84121_(p_157251_);
    }

    private static /* synthetic */ void m_157409_(int p_157410_, int p_157411_, int p_157412_) {
        GlStateManager.m_84452_(p_157410_, p_157411_, p_157412_);
    }

    private static /* synthetic */ void m_157407_(int p_157408_) {
        GlStateManager.m_84550_(p_157408_);
    }

    private static /* synthetic */ void m_157403_(int p_157404_, int p_157405_, int p_157406_) {
        GlStateManager.m_84426_(p_157404_, p_157405_, p_157406_);
    }

    private static /* synthetic */ void m_157254_(boolean p_157255_, boolean p_157256_, boolean p_157257_, boolean p_157258_) {
        GlStateManager.m_84300_(p_157255_, p_157256_, p_157257_, p_157258_);
    }

    private static /* synthetic */ void m_157418_(int p_157419_, int p_157420_, int p_157421_, int p_157422_) {
        GlStateManager.m_84430_(p_157419_, p_157420_, p_157421_, p_157422_);
    }

    private static /* synthetic */ void m_157222_(int p_157223_) {
        GlStateManager.m_84544_(p_157223_);
    }

    private static /* synthetic */ void m_157259_(int p_157260_) {
        GlStateManager.m_84541_(p_157260_);
    }

    private static /* synthetic */ void m_157414_(int p_157415_, int p_157416_, int p_157417_) {
        GlStateManager.m_84331_(p_157415_, p_157416_, p_157417_);
    }

    private static /* synthetic */ void m_157217_(int p_157218_) {
        GlStateManager.m_84538_(p_157218_);
    }

    private static /* synthetic */ void m_157331_(GlStateManager.LogicOp p_157332_) {
        GlStateManager.m_84532_(p_157332_.f_84715_);
    }

    private static /* synthetic */ void m_157339_(float p_157340_, float p_157341_) {
        GlStateManager.m_84136_(p_157340_, p_157341_);
    }

    private static /* synthetic */ void m_157336_(int p_157337_, int p_157338_) {
        GlStateManager.m_84516_(p_157337_, p_157338_);
    }

    private static /* synthetic */ void m_157224_(int p_157225_) {
        GlStateManager.m_84379_(p_157225_);
    }

    private static /* synthetic */ void m_157237_(int p_157238_, int p_157239_, int p_157240_, int p_157241_) {
        GlStateManager.m_84335_(p_157238_, p_157239_, p_157240_, p_157241_);
    }

    private static /* synthetic */ void m_157232_(GlStateManager.SourceFactor p_157233_, GlStateManager.DestFactor p_157234_, GlStateManager.SourceFactor p_157235_, GlStateManager.DestFactor p_157236_) {
        GlStateManager.m_84335_(p_157233_.f_84751_, p_157234_.f_84646_, p_157235_.f_84751_, p_157236_.f_84646_);
    }

    private static /* synthetic */ void m_157229_(int p_157230_, int p_157231_) {
        GlStateManager.m_84328_(p_157230_, p_157231_);
    }

    private static /* synthetic */ void m_157226_(GlStateManager.SourceFactor p_157227_, GlStateManager.DestFactor p_157228_) {
        GlStateManager.m_84328_(p_157227_.f_84751_, p_157228_.f_84646_);
    }

    private static /* synthetic */ void m_157263_(boolean p_157264_) {
        GlStateManager.m_84298_(p_157264_);
    }

    private static /* synthetic */ void m_157261_(int p_157262_) {
        GlStateManager.m_84323_(p_157262_);
    }

    private static /* synthetic */ void m_157269_(int p_157270_, int p_157271_, int p_157272_, int p_157273_) {
        GlStateManager.m_84501_();
        GlStateManager.m_84168_(p_157270_, p_157271_, p_157272_, p_157273_);
    }

    static {
        f_69377_ = -1;
        f_69383_ = Double.MIN_VALUE;
        f_157153_ = new AutoStorageIndexBuffer(1, 1, java.util.function.IntConsumer::accept);
        f_157155_ = new AutoStorageIndexBuffer(4, 6, (p_157398_, p_157399_) -> {
            p_157398_.accept(p_157399_ + 0);
            p_157398_.accept(p_157399_ + 1);
            p_157398_.accept(p_157399_ + 2);
            p_157398_.accept(p_157399_ + 2);
            p_157398_.accept(p_157399_ + 3);
            p_157398_.accept(p_157399_ + 0);
        });
        f_157154_ = new AutoStorageIndexBuffer(4, 6, (p_157401_, p_157402_) -> {
            p_157401_.accept(p_157402_ + 0);
            p_157401_.accept(p_157402_ + 1);
            p_157401_.accept(p_157402_ + 2);
            p_157401_.accept(p_157402_ + 3);
            p_157401_.accept(p_157402_ + 2);
            p_157401_.accept(p_157402_ + 1);
        });
        f_200905_ = new Matrix3f();
        f_157142_ = new Matrix4f();
        f_157143_ = new Matrix4f();
        f_157141_ = new PoseStack();
        f_157140_ = new Matrix4f();
        f_157156_ = new Matrix4f();
        f_157152_ = new int[12];
        f_157145_ = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        f_157147_ = 1.0f;
        f_157146_ = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        f_202038_ = FogShape.SPHERE;
        f_157150_ = new Vector3f[2];
        f_157151_ = 1.0f;
        f_187550_ = "Unknown";
        f_157142_.m_27624_();
        f_157143_.m_27624_();
        f_157140_.m_27624_();
        f_157156_.m_27624_();
    }

    public static final class AutoStorageIndexBuffer {
        private final int f_157465_;
        private final int f_157466_;
        private final IndexGenerator f_157467_;
        private int f_157468_;
        private VertexFormat.IndexType f_157469_ = VertexFormat.IndexType.BYTE;
        private int f_157470_;

        AutoStorageIndexBuffer(int p_157472_, int p_157473_, IndexGenerator p_157474_) {
            this.f_157465_ = p_157472_;
            this.f_157466_ = p_157473_;
            this.f_157467_ = p_157474_;
        }

        public boolean m_221944_(int p_221945_) {
            return p_221945_ <= this.f_157470_;
        }

        public void m_221946_(int p_221947_) {
            if (this.f_157468_ == 0) {
                this.f_157468_ = GlStateManager.m_84537_();
            }
            GlStateManager.m_84480_(34963, this.f_157468_);
            this.m_157476_(p_221947_);
        }

        private void m_157476_(int p_157477_) {
            if (this.m_221944_(p_157477_)) {
                return;
            }
            p_157477_ = Mth.m_144941_(p_157477_ * 2, this.f_157466_);
            f_69376_.debug("Growing IndexBuffer: Old limit {}, new limit {}.", (Object)this.f_157470_, (Object)p_157477_);
            VertexFormat.IndexType $$1 = VertexFormat.IndexType.m_166933_(p_157477_);
            int $$2 = Mth.m_144941_(p_157477_ * $$1.f_166924_, 4);
            GlStateManager.m_157070_(34963, $$2, 35048);
            ByteBuffer $$3 = GlStateManager.m_157090_(34963, 35001);
            if ($$3 == null) {
                throw new RuntimeException("Failed to map GL buffer");
            }
            this.f_157469_ = $$1;
            IntConsumer $$4 = this.m_157478_($$3);
            for (int $$5 = 0; $$5 < p_157477_; $$5 += this.f_157466_) {
                this.f_157467_.m_157487_($$4, $$5 * this.f_157465_ / this.f_157466_);
            }
            GlStateManager.m_157098_(34963);
            this.f_157470_ = p_157477_;
        }

        private IntConsumer m_157478_(ByteBuffer p_157479_) {
            switch (this.f_157469_) {
                case BYTE: {
                    return p_157486_ -> p_157479_.put((byte)p_157486_);
                }
                case SHORT: {
                    return p_157482_ -> p_157479_.putShort((short)p_157482_);
                }
            }
            return p_157479_::putInt;
        }

        public VertexFormat.IndexType m_157483_() {
            return this.f_157469_;
        }

        static interface IndexGenerator {
            public void m_157487_(IntConsumer var1, int var2);
        }
    }
}

