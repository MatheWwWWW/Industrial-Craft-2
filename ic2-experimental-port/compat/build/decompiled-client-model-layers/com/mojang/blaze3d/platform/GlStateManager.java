/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  javax.annotation.Nullable
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL13
 *  org.lwjgl.opengl.GL14
 *  org.lwjgl.opengl.GL15
 *  org.lwjgl.opengl.GL20
 *  org.lwjgl.opengl.GL20C
 *  org.lwjgl.opengl.GL30
 *  org.lwjgl.opengl.GL32C
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 */
package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

@DontObfuscate
public class GlStateManager {
    private static final boolean f_198912_ = Util.m_137581_() == Util.OS.LINUX;
    public static final int f_157051_ = 12;
    private static final BlendState f_84066_ = new BlendState();
    private static final DepthState f_84067_ = new DepthState();
    private static final CullState f_84069_ = new CullState();
    private static final PolygonOffsetState f_84070_ = new PolygonOffsetState();
    private static final ColorLogicState f_84071_ = new ColorLogicState();
    private static final StencilState f_84073_ = new StencilState();
    private static final ScissorState f_84074_ = new ScissorState();
    private static int f_84076_;
    private static final TextureState[] f_84077_;
    private static final ColorMask f_84080_;

    public static void m_84495_() {
        RenderSystem.m_187555_();
        GlStateManager.f_84074_.f_84732_.m_84589_();
    }

    public static void m_84501_() {
        RenderSystem.m_187555_();
        GlStateManager.f_84074_.f_84732_.m_84592_();
    }

    public static void m_84168_(int p_84169_, int p_84170_, int p_84171_, int p_84172_) {
        RenderSystem.m_187555_();
        GL20.glScissor((int)p_84169_, (int)p_84170_, (int)p_84171_, (int)p_84172_);
    }

    public static void m_84507_() {
        RenderSystem.m_187555_();
        GlStateManager.f_84067_.f_84626_.m_84589_();
    }

    public static void m_84513_() {
        RenderSystem.m_187555_();
        GlStateManager.f_84067_.f_84626_.m_84592_();
    }

    public static void m_84323_(int p_84324_) {
        RenderSystem.m_187555_();
        if (p_84324_ != GlStateManager.f_84067_.f_84628_) {
            GlStateManager.f_84067_.f_84628_ = p_84324_;
            GL11.glDepthFunc((int)p_84324_);
        }
    }

    public static void m_84298_(boolean p_84299_) {
        RenderSystem.m_187554_();
        if (p_84299_ != GlStateManager.f_84067_.f_84627_) {
            GlStateManager.f_84067_.f_84627_ = p_84299_;
            GL11.glDepthMask((boolean)p_84299_);
        }
    }

    public static void m_84519_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84066_.f_84577_.m_84589_();
    }

    public static void m_84525_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84066_.f_84577_.m_84592_();
    }

    public static void m_84328_(int p_84329_, int p_84330_) {
        RenderSystem.m_187554_();
        if (p_84329_ != GlStateManager.f_84066_.f_84578_ || p_84330_ != GlStateManager.f_84066_.f_84579_) {
            GlStateManager.f_84066_.f_84578_ = p_84329_;
            GlStateManager.f_84066_.f_84579_ = p_84330_;
            GL11.glBlendFunc((int)p_84329_, (int)p_84330_);
        }
    }

    public static void m_84335_(int p_84336_, int p_84337_, int p_84338_, int p_84339_) {
        RenderSystem.m_187554_();
        if (p_84336_ != GlStateManager.f_84066_.f_84578_ || p_84337_ != GlStateManager.f_84066_.f_84579_ || p_84338_ != GlStateManager.f_84066_.f_84580_ || p_84339_ != GlStateManager.f_84066_.f_84581_) {
            GlStateManager.f_84066_.f_84578_ = p_84336_;
            GlStateManager.f_84066_.f_84579_ = p_84337_;
            GlStateManager.f_84066_.f_84580_ = p_84338_;
            GlStateManager.f_84066_.f_84581_ = p_84339_;
            GlStateManager.m_84388_(p_84336_, p_84337_, p_84338_, p_84339_);
        }
    }

    public static void m_84379_(int p_84380_) {
        RenderSystem.m_187554_();
        GL14.glBlendEquation((int)p_84380_);
    }

    public static int m_84381_(int p_84382_, int p_84383_) {
        RenderSystem.m_187554_();
        return GL20.glGetProgrami((int)p_84382_, (int)p_84383_);
    }

    public static void m_84423_(int p_84424_, int p_84425_) {
        RenderSystem.m_187554_();
        GL20.glAttachShader((int)p_84424_, (int)p_84425_);
    }

    public static void m_84421_(int p_84422_) {
        RenderSystem.m_187554_();
        GL20.glDeleteShader((int)p_84422_);
    }

    public static int m_84447_(int p_84448_) {
        RenderSystem.m_187554_();
        return GL20.glCreateShader((int)p_84448_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void m_157116_(int p_157117_, List<String> p_157118_) {
        RenderSystem.m_187554_();
        StringBuilder $$2 = new StringBuilder();
        for (String $$3 : p_157118_) {
            $$2.append($$3);
        }
        byte[] $$4 = $$2.toString().getBytes(Charsets.UTF_8);
        ByteBuffer $$5 = MemoryUtil.memAlloc((int)($$4.length + 1));
        $$5.put($$4);
        $$5.put((byte)0);
        $$5.flip();
        try (MemoryStack $$6 = MemoryStack.stackPush();){
            PointerBuffer $$7 = $$6.mallocPointer(1);
            $$7.put($$5);
            GL20C.nglShaderSource((int)p_157117_, (int)1, (long)$$7.address0(), (long)0L);
        }
        finally {
            MemoryUtil.memFree((Buffer)$$5);
        }
    }

    public static void m_84465_(int p_84466_) {
        RenderSystem.m_187554_();
        GL20.glCompileShader((int)p_84466_);
    }

    public static int m_84449_(int p_84450_, int p_84451_) {
        RenderSystem.m_187554_();
        return GL20.glGetShaderi((int)p_84450_, (int)p_84451_);
    }

    public static void m_84478_(int p_84479_) {
        RenderSystem.m_187554_();
        GL20.glUseProgram((int)p_84479_);
    }

    public static int m_84531_() {
        RenderSystem.m_187554_();
        return GL20.glCreateProgram();
    }

    public static void m_84484_(int p_84485_) {
        RenderSystem.m_187554_();
        GL20.glDeleteProgram((int)p_84485_);
    }

    public static void m_84490_(int p_84491_) {
        RenderSystem.m_187554_();
        GL20.glLinkProgram((int)p_84491_);
    }

    public static int m_84345_(int p_84346_, CharSequence p_84347_) {
        RenderSystem.m_187554_();
        return GL20.glGetUniformLocation((int)p_84346_, (CharSequence)p_84347_);
    }

    public static void m_84263_(int p_84264_, IntBuffer p_84265_) {
        RenderSystem.m_187554_();
        GL20.glUniform1iv((int)p_84264_, (IntBuffer)p_84265_);
    }

    public static void m_84467_(int p_84468_, int p_84469_) {
        RenderSystem.m_187554_();
        GL20.glUniform1i((int)p_84468_, (int)p_84469_);
    }

    public static void m_84348_(int p_84349_, FloatBuffer p_84350_) {
        RenderSystem.m_187554_();
        GL20.glUniform1fv((int)p_84349_, (FloatBuffer)p_84350_);
    }

    public static void m_84351_(int p_84352_, IntBuffer p_84353_) {
        RenderSystem.m_187554_();
        GL20.glUniform2iv((int)p_84352_, (IntBuffer)p_84353_);
    }

    public static void m_84401_(int p_84402_, FloatBuffer p_84403_) {
        RenderSystem.m_187554_();
        GL20.glUniform2fv((int)p_84402_, (FloatBuffer)p_84403_);
    }

    public static void m_84404_(int p_84405_, IntBuffer p_84406_) {
        RenderSystem.m_187554_();
        GL20.glUniform3iv((int)p_84405_, (IntBuffer)p_84406_);
    }

    public static void m_84435_(int p_84436_, FloatBuffer p_84437_) {
        RenderSystem.m_187554_();
        GL20.glUniform3fv((int)p_84436_, (FloatBuffer)p_84437_);
    }

    public static void m_84438_(int p_84439_, IntBuffer p_84440_) {
        RenderSystem.m_187554_();
        GL20.glUniform4iv((int)p_84439_, (IntBuffer)p_84440_);
    }

    public static void m_84461_(int p_84462_, FloatBuffer p_84463_) {
        RenderSystem.m_187554_();
        GL20.glUniform4fv((int)p_84462_, (FloatBuffer)p_84463_);
    }

    public static void m_84269_(int p_84270_, boolean p_84271_, FloatBuffer p_84272_) {
        RenderSystem.m_187554_();
        GL20.glUniformMatrix2fv((int)p_84270_, (boolean)p_84271_, (FloatBuffer)p_84272_);
    }

    public static void m_84354_(int p_84355_, boolean p_84356_, FloatBuffer p_84357_) {
        RenderSystem.m_187554_();
        GL20.glUniformMatrix3fv((int)p_84355_, (boolean)p_84356_, (FloatBuffer)p_84357_);
    }

    public static void m_84407_(int p_84408_, boolean p_84409_, FloatBuffer p_84410_) {
        RenderSystem.m_187554_();
        GL20.glUniformMatrix4fv((int)p_84408_, (boolean)p_84409_, (FloatBuffer)p_84410_);
    }

    public static int m_84398_(int p_84399_, CharSequence p_84400_) {
        RenderSystem.m_187554_();
        return GL20.glGetAttribLocation((int)p_84399_, (CharSequence)p_84400_);
    }

    public static void m_157061_(int p_157062_, int p_157063_, CharSequence p_157064_) {
        RenderSystem.m_187554_();
        GL20.glBindAttribLocation((int)p_157062_, (int)p_157063_, (CharSequence)p_157064_);
    }

    public static int m_84537_() {
        RenderSystem.m_187555_();
        return GL15.glGenBuffers();
    }

    public static int m_157089_() {
        RenderSystem.m_187555_();
        return GL30.glGenVertexArrays();
    }

    public static void m_84480_(int p_84481_, int p_84482_) {
        RenderSystem.m_187555_();
        GL15.glBindBuffer((int)p_84481_, (int)p_84482_);
    }

    public static void m_157068_(int p_157069_) {
        RenderSystem.m_187555_();
        GL30.glBindVertexArray((int)p_157069_);
    }

    public static void m_84256_(int p_84257_, ByteBuffer p_84258_, int p_84259_) {
        RenderSystem.m_187555_();
        GL15.glBufferData((int)p_84257_, (ByteBuffer)p_84258_, (int)p_84259_);
    }

    public static void m_157070_(int p_157071_, long p_157072_, int p_157073_) {
        RenderSystem.m_187555_();
        GL15.glBufferData((int)p_157071_, (long)p_157072_, (int)p_157073_);
    }

    @Nullable
    public static ByteBuffer m_157090_(int p_157091_, int p_157092_) {
        RenderSystem.m_187555_();
        return GL15.glMapBuffer((int)p_157091_, (int)p_157092_);
    }

    public static void m_157098_(int p_157099_) {
        RenderSystem.m_187555_();
        GL15.glUnmapBuffer((int)p_157099_);
    }

    public static void m_84496_(int p_84497_) {
        RenderSystem.m_187554_();
        if (f_198912_) {
            GL32C.glBindBuffer((int)34962, (int)p_84497_);
            GL32C.glBufferData((int)34962, (long)0L, (int)35048);
            GL32C.glBindBuffer((int)34962, (int)0);
        }
        GL15.glDeleteBuffers((int)p_84497_);
    }

    public static void m_84179_(int p_84180_, int p_84181_, int p_84182_, int p_84183_, int p_84184_, int p_84185_, int p_84186_, int p_84187_) {
        RenderSystem.m_187555_();
        GL20.glCopyTexSubImage2D((int)p_84180_, (int)p_84181_, (int)p_84182_, (int)p_84183_, (int)p_84184_, (int)p_84185_, (int)p_84186_, (int)p_84187_);
    }

    public static void m_157076_(int p_157077_) {
        RenderSystem.m_187554_();
        GL30.glDeleteVertexArrays((int)p_157077_);
    }

    public static void m_84486_(int p_84487_, int p_84488_) {
        RenderSystem.m_187555_();
        GL30.glBindFramebuffer((int)p_84487_, (int)p_84488_);
    }

    public static void m_84188_(int p_84189_, int p_84190_, int p_84191_, int p_84192_, int p_84193_, int p_84194_, int p_84195_, int p_84196_, int p_84197_, int p_84198_) {
        RenderSystem.m_187555_();
        GL30.glBlitFramebuffer((int)p_84189_, (int)p_84190_, (int)p_84191_, (int)p_84192_, (int)p_84193_, (int)p_84194_, (int)p_84195_, (int)p_84196_, (int)p_84197_, (int)p_84198_);
    }

    public static void m_157065_(int p_157066_, int p_157067_) {
        RenderSystem.m_187555_();
        GL30.glBindRenderbuffer((int)p_157066_, (int)p_157067_);
    }

    public static void m_157074_(int p_157075_) {
        RenderSystem.m_187555_();
        GL30.glDeleteRenderbuffers((int)p_157075_);
    }

    public static void m_84502_(int p_84503_) {
        RenderSystem.m_187555_();
        GL30.glDeleteFramebuffers((int)p_84503_);
    }

    public static int m_84543_() {
        RenderSystem.m_187555_();
        return GL30.glGenFramebuffers();
    }

    public static int m_157115_() {
        RenderSystem.m_187555_();
        return GL30.glGenRenderbuffers();
    }

    public static void m_157093_(int p_157094_, int p_157095_, int p_157096_, int p_157097_) {
        RenderSystem.m_187555_();
        GL30.glRenderbufferStorage((int)p_157094_, (int)p_157095_, (int)p_157096_, (int)p_157097_);
    }

    public static void m_157084_(int p_157085_, int p_157086_, int p_157087_, int p_157088_) {
        RenderSystem.m_187555_();
        GL30.glFramebufferRenderbuffer((int)p_157085_, (int)p_157086_, (int)p_157087_, (int)p_157088_);
    }

    public static int m_84508_(int p_84509_) {
        RenderSystem.m_187555_();
        return GL30.glCheckFramebufferStatus((int)p_84509_);
    }

    public static void m_84173_(int p_84174_, int p_84175_, int p_84176_, int p_84177_, int p_84178_) {
        RenderSystem.m_187555_();
        GL30.glFramebufferTexture2D((int)p_84174_, (int)p_84175_, (int)p_84176_, (int)p_84177_, (int)p_84178_);
    }

    public static int m_157114_() {
        RenderSystem.m_187554_();
        return GlStateManager.m_84092_(36006);
    }

    public static void m_84514_(int p_84515_) {
        RenderSystem.m_187554_();
        GL13.glActiveTexture((int)p_84515_);
    }

    public static void m_84388_(int p_84389_, int p_84390_, int p_84391_, int p_84392_) {
        RenderSystem.m_187554_();
        GL14.glBlendFuncSeparate((int)p_84389_, (int)p_84390_, (int)p_84391_, (int)p_84392_);
    }

    public static String m_84492_(int p_84493_, int p_84494_) {
        RenderSystem.m_187554_();
        return GL20.glGetShaderInfoLog((int)p_84493_, (int)p_84494_);
    }

    public static String m_84498_(int p_84499_, int p_84500_) {
        RenderSystem.m_187554_();
        return GL20.glGetProgramInfoLog((int)p_84499_, (int)p_84500_);
    }

    public static void m_84290_(Vector3f p_84291_, Vector3f p_84292_, Matrix4f p_84293_) {
        RenderSystem.m_187554_();
        Vector4f $$3 = new Vector4f(p_84291_);
        $$3.m_123607_(p_84293_);
        Vector4f $$4 = new Vector4f(p_84292_);
        $$4.m_123607_(p_84293_);
        RenderSystem.m_157450_(new Vector3f($$3), new Vector3f($$4));
    }

    public static void m_84287_(Vector3f p_84288_, Vector3f p_84289_) {
        RenderSystem.m_187554_();
        Matrix4f $$2 = new Matrix4f();
        $$2.m_27624_();
        $$2.m_27644_(Matrix4f.m_27632_(1.0f, -1.0f, 1.0f));
        $$2.m_27646_(Vector3f.f_122225_.m_122240_(-22.5f));
        $$2.m_27646_(Vector3f.f_122223_.m_122240_(135.0f));
        GlStateManager.m_84290_(p_84288_, p_84289_, $$2);
    }

    public static void m_84360_(Vector3f p_84361_, Vector3f p_84362_) {
        RenderSystem.m_187554_();
        Matrix4f $$2 = new Matrix4f();
        $$2.m_27624_();
        $$2.m_27646_(Vector3f.f_122225_.m_122240_(62.0f));
        $$2.m_27646_(Vector3f.f_122223_.m_122240_(185.5f));
        $$2.m_27646_(Vector3f.f_122225_.m_122240_(-22.5f));
        $$2.m_27646_(Vector3f.f_122223_.m_122240_(135.0f));
        GlStateManager.m_84290_(p_84361_, p_84362_, $$2);
    }

    public static void m_84091_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84069_.f_84621_.m_84592_();
    }

    public static void m_84094_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84069_.f_84621_.m_84589_();
    }

    public static void m_84516_(int p_84517_, int p_84518_) {
        RenderSystem.m_187554_();
        GL11.glPolygonMode((int)p_84517_, (int)p_84518_);
    }

    public static void m_84097_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84070_.f_84725_.m_84592_();
    }

    public static void m_84100_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84070_.f_84725_.m_84589_();
    }

    public static void m_84136_(float p_84137_, float p_84138_) {
        RenderSystem.m_187554_();
        if (p_84137_ != GlStateManager.f_84070_.f_84727_ || p_84138_ != GlStateManager.f_84070_.f_84728_) {
            GlStateManager.f_84070_.f_84727_ = p_84137_;
            GlStateManager.f_84070_.f_84728_ = p_84138_;
            GL11.glPolygonOffset((float)p_84137_, (float)p_84138_);
        }
    }

    public static void m_84107_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84071_.f_84603_.m_84592_();
    }

    public static void m_84108_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84071_.f_84603_.m_84589_();
    }

    public static void m_84532_(int p_84533_) {
        RenderSystem.m_187554_();
        if (p_84533_ != GlStateManager.f_84071_.f_84604_) {
            GlStateManager.f_84071_.f_84604_ = p_84533_;
            GL11.glLogicOp((int)p_84533_);
        }
    }

    public static void m_84538_(int p_84539_) {
        RenderSystem.m_187554_();
        if (f_84076_ != p_84539_ - 33984) {
            f_84076_ = p_84539_ - 33984;
            GlStateManager.m_84514_(p_84539_);
        }
    }

    public static void m_84109_() {
        RenderSystem.m_187555_();
        GlStateManager.f_84077_[GlStateManager.f_84076_].f_84800_ = true;
    }

    public static void m_84110_() {
        RenderSystem.m_187554_();
        GlStateManager.f_84077_[GlStateManager.f_84076_].f_84800_ = false;
    }

    public static void m_84160_(int p_84161_, int p_84162_, float p_84163_) {
        RenderSystem.m_187555_();
        GL11.glTexParameterf((int)p_84161_, (int)p_84162_, (float)p_84163_);
    }

    public static void m_84331_(int p_84332_, int p_84333_, int p_84334_) {
        RenderSystem.m_187555_();
        GL11.glTexParameteri((int)p_84332_, (int)p_84333_, (int)p_84334_);
    }

    public static int m_84384_(int p_84385_, int p_84386_, int p_84387_) {
        RenderSystem.m_187551_();
        return GL11.glGetTexLevelParameteri((int)p_84385_, (int)p_84386_, (int)p_84387_);
    }

    public static int m_84111_() {
        RenderSystem.m_187555_();
        return GL11.glGenTextures();
    }

    public static void m_84305_(int[] p_84306_) {
        RenderSystem.m_187555_();
        GL11.glGenTextures((int[])p_84306_);
    }

    public static void m_84541_(int p_84542_) {
        RenderSystem.m_187555_();
        GL11.glDeleteTextures((int)p_84542_);
        for (TextureState $$1 : f_84077_) {
            if ($$1.f_84801_ != p_84542_) continue;
            $$1.f_84801_ = -1;
        }
    }

    public static void m_84365_(int[] p_84366_) {
        RenderSystem.m_187555_();
        for (TextureState $$1 : f_84077_) {
            for (int $$2 : p_84366_) {
                if ($$1.f_84801_ != $$2) continue;
                $$1.f_84801_ = -1;
            }
        }
        GL11.glDeleteTextures((int[])p_84366_);
    }

    public static void m_84544_(int p_84545_) {
        RenderSystem.m_187555_();
        if (p_84545_ != GlStateManager.f_84077_[GlStateManager.f_84076_].f_84801_) {
            GlStateManager.f_84077_[GlStateManager.f_84076_].f_84801_ = p_84545_;
            GL11.glBindTexture((int)3553, (int)p_84545_);
        }
    }

    public static int m_157059_(int p_157060_) {
        if (p_157060_ >= 0 && p_157060_ < 12 && GlStateManager.f_84077_[p_157060_].f_84800_) {
            return GlStateManager.f_84077_[p_157060_].f_84801_;
        }
        return 0;
    }

    public static int m_157058_() {
        return f_84076_ + 33984;
    }

    public static void m_84209_(int p_84210_, int p_84211_, int p_84212_, int p_84213_, int p_84214_, int p_84215_, int p_84216_, int p_84217_, @Nullable IntBuffer p_84218_) {
        RenderSystem.m_187555_();
        GL11.glTexImage2D((int)p_84210_, (int)p_84211_, (int)p_84212_, (int)p_84213_, (int)p_84214_, (int)p_84215_, (int)p_84216_, (int)p_84217_, (IntBuffer)p_84218_);
    }

    public static void m_84199_(int p_84200_, int p_84201_, int p_84202_, int p_84203_, int p_84204_, int p_84205_, int p_84206_, int p_84207_, long p_84208_) {
        RenderSystem.m_187555_();
        GL11.glTexSubImage2D((int)p_84200_, (int)p_84201_, (int)p_84202_, (int)p_84203_, (int)p_84204_, (int)p_84205_, (int)p_84206_, (int)p_84207_, (long)p_84208_);
    }

    public static void m_84227_(int p_84228_, int p_84229_, int p_84230_, int p_84231_, long p_84232_) {
        RenderSystem.m_187554_();
        GL11.glGetTexImage((int)p_84228_, (int)p_84229_, (int)p_84230_, (int)p_84231_, (long)p_84232_);
    }

    public static void m_84430_(int p_84431_, int p_84432_, int p_84433_, int p_84434_) {
        RenderSystem.m_187555_();
        Viewport.INSTANCE.f_84806_ = p_84431_;
        Viewport.INSTANCE.f_84807_ = p_84432_;
        Viewport.INSTANCE.f_84808_ = p_84433_;
        Viewport.INSTANCE.f_84809_ = p_84434_;
        GL11.glViewport((int)p_84431_, (int)p_84432_, (int)p_84433_, (int)p_84434_);
    }

    public static void m_84300_(boolean p_84301_, boolean p_84302_, boolean p_84303_, boolean p_84304_) {
        RenderSystem.m_187554_();
        if (p_84301_ != GlStateManager.f_84080_.f_84608_ || p_84302_ != GlStateManager.f_84080_.f_84609_ || p_84303_ != GlStateManager.f_84080_.f_84610_ || p_84304_ != GlStateManager.f_84080_.f_84611_) {
            GlStateManager.f_84080_.f_84608_ = p_84301_;
            GlStateManager.f_84080_.f_84609_ = p_84302_;
            GlStateManager.f_84080_.f_84610_ = p_84303_;
            GlStateManager.f_84080_.f_84611_ = p_84304_;
            GL11.glColorMask((boolean)p_84301_, (boolean)p_84302_, (boolean)p_84303_, (boolean)p_84304_);
        }
    }

    public static void m_84426_(int p_84427_, int p_84428_, int p_84429_) {
        RenderSystem.m_187554_();
        if (p_84427_ != GlStateManager.f_84073_.f_84767_.f_84761_ || p_84427_ != GlStateManager.f_84073_.f_84767_.f_84762_ || p_84427_ != GlStateManager.f_84073_.f_84767_.f_84763_) {
            GlStateManager.f_84073_.f_84767_.f_84761_ = p_84427_;
            GlStateManager.f_84073_.f_84767_.f_84762_ = p_84428_;
            GlStateManager.f_84073_.f_84767_.f_84763_ = p_84429_;
            GL11.glStencilFunc((int)p_84427_, (int)p_84428_, (int)p_84429_);
        }
    }

    public static void m_84550_(int p_84551_) {
        RenderSystem.m_187554_();
        if (p_84551_ != GlStateManager.f_84073_.f_84768_) {
            GlStateManager.f_84073_.f_84768_ = p_84551_;
            GL11.glStencilMask((int)p_84551_);
        }
    }

    public static void m_84452_(int p_84453_, int p_84454_, int p_84455_) {
        RenderSystem.m_187554_();
        if (p_84453_ != GlStateManager.f_84073_.f_84769_ || p_84454_ != GlStateManager.f_84073_.f_84770_ || p_84455_ != GlStateManager.f_84073_.f_84771_) {
            GlStateManager.f_84073_.f_84769_ = p_84453_;
            GlStateManager.f_84073_.f_84770_ = p_84454_;
            GlStateManager.f_84073_.f_84771_ = p_84455_;
            GL11.glStencilOp((int)p_84453_, (int)p_84454_, (int)p_84455_);
        }
    }

    public static void m_84121_(double p_84122_) {
        RenderSystem.m_187555_();
        GL11.glClearDepth((double)p_84122_);
    }

    public static void m_84318_(float p_84319_, float p_84320_, float p_84321_, float p_84322_) {
        RenderSystem.m_187555_();
        GL11.glClearColor((float)p_84319_, (float)p_84320_, (float)p_84321_, (float)p_84322_);
    }

    public static void m_84553_(int p_84554_) {
        RenderSystem.m_187554_();
        GL11.glClearStencil((int)p_84554_);
    }

    public static void m_84266_(int p_84267_, boolean p_84268_) {
        RenderSystem.m_187555_();
        GL11.glClear((int)p_84267_);
        if (p_84268_) {
            GlStateManager.m_84118_();
        }
    }

    public static void m_157078_(int p_157079_, int p_157080_, int p_157081_, int p_157082_, long p_157083_) {
        RenderSystem.m_187554_();
        GL11.glDrawPixels((int)p_157079_, (int)p_157080_, (int)p_157081_, (int)p_157082_, (long)p_157083_);
    }

    public static void m_84238_(int p_84239_, int p_84240_, int p_84241_, boolean p_84242_, int p_84243_, long p_84244_) {
        RenderSystem.m_187554_();
        GL20.glVertexAttribPointer((int)p_84239_, (int)p_84240_, (int)p_84241_, (boolean)p_84242_, (int)p_84243_, (long)p_84244_);
    }

    public static void m_157108_(int p_157109_, int p_157110_, int p_157111_, int p_157112_, long p_157113_) {
        RenderSystem.m_187554_();
        GL30.glVertexAttribIPointer((int)p_157109_, (int)p_157110_, (int)p_157111_, (int)p_157112_, (long)p_157113_);
    }

    public static void m_84565_(int p_84566_) {
        RenderSystem.m_187554_();
        GL20.glEnableVertexAttribArray((int)p_84566_);
    }

    public static void m_84086_(int p_84087_) {
        RenderSystem.m_187554_();
        GL20.glDisableVertexAttribArray((int)p_84087_);
    }

    public static void m_157053_(int p_157054_, int p_157055_, int p_157056_, long p_157057_) {
        RenderSystem.m_187554_();
        GL11.glDrawElements((int)p_157054_, (int)p_157055_, (int)p_157056_, (long)p_157057_);
    }

    public static void m_84522_(int p_84523_, int p_84524_) {
        RenderSystem.m_187555_();
        GL11.glPixelStorei((int)p_84523_, (int)p_84524_);
    }

    public static void m_84219_(int p_84220_, int p_84221_, int p_84222_, int p_84223_, int p_84224_, int p_84225_, ByteBuffer p_84226_) {
        RenderSystem.m_187554_();
        GL11.glReadPixels((int)p_84220_, (int)p_84221_, (int)p_84222_, (int)p_84223_, (int)p_84224_, (int)p_84225_, (ByteBuffer)p_84226_);
    }

    public static void m_157100_(int p_157101_, int p_157102_, int p_157103_, int p_157104_, int p_157105_, int p_157106_, long p_157107_) {
        RenderSystem.m_187554_();
        GL11.glReadPixels((int)p_157101_, (int)p_157102_, (int)p_157103_, (int)p_157104_, (int)p_157105_, (int)p_157106_, (long)p_157107_);
    }

    public static int m_84118_() {
        RenderSystem.m_187554_();
        return GL11.glGetError();
    }

    public static String m_84089_(int p_84090_) {
        RenderSystem.m_187554_();
        return GL11.glGetString((int)p_84090_);
    }

    public static int m_84092_(int p_84093_) {
        RenderSystem.m_187555_();
        return GL11.glGetInteger((int)p_84093_);
    }

    static {
        f_84077_ = (TextureState[])IntStream.range(0, 12).mapToObj(p_157120_ -> new TextureState()).toArray(TextureState[]::new);
        f_84080_ = new ColorMask();
    }

    static class ScissorState {
        public final BooleanState f_84732_ = new BooleanState(3089);

        ScissorState() {
        }
    }

    static class BooleanState {
        private final int f_84585_;
        private boolean f_84586_;

        public BooleanState(int p_84588_) {
            this.f_84585_ = p_84588_;
        }

        public void m_84589_() {
            this.m_84590_(false);
        }

        public void m_84592_() {
            this.m_84590_(true);
        }

        public void m_84590_(boolean p_84591_) {
            RenderSystem.m_187555_();
            if (p_84591_ != this.f_84586_) {
                this.f_84586_ = p_84591_;
                if (p_84591_) {
                    GL11.glEnable((int)this.f_84585_);
                } else {
                    GL11.glDisable((int)this.f_84585_);
                }
            }
        }
    }

    static class DepthState {
        public final BooleanState f_84626_ = new BooleanState(2929);
        public boolean f_84627_ = true;
        public int f_84628_ = 513;

        DepthState() {
        }
    }

    static class BlendState {
        public final BooleanState f_84577_ = new BooleanState(3042);
        public int f_84578_ = 1;
        public int f_84579_ = 0;
        public int f_84580_ = 1;
        public int f_84581_ = 0;

        BlendState() {
        }
    }

    static class CullState {
        public final BooleanState f_84621_ = new BooleanState(2884);
        public int f_84622_ = 1029;

        CullState() {
        }
    }

    static class PolygonOffsetState {
        public final BooleanState f_84725_ = new BooleanState(32823);
        public final BooleanState f_84726_ = new BooleanState(10754);
        public float f_84727_;
        public float f_84728_;

        PolygonOffsetState() {
        }
    }

    static class ColorLogicState {
        public final BooleanState f_84603_ = new BooleanState(3058);
        public int f_84604_ = 5379;

        ColorLogicState() {
        }
    }

    static class TextureState {
        public boolean f_84800_;
        public int f_84801_;

        TextureState() {
        }
    }

    public static final class Viewport
    extends Enum<Viewport> {
        public static final /* enum */ Viewport INSTANCE = new Viewport();
        protected int f_84806_;
        protected int f_84807_;
        protected int f_84808_;
        protected int f_84809_;
        private static final /* synthetic */ Viewport[] $VALUES;

        public static Viewport[] values() {
            return (Viewport[])$VALUES.clone();
        }

        public static Viewport valueOf(String p_84816_) {
            return Enum.valueOf(Viewport.class, p_84816_);
        }

        public static int m_157126_() {
            return Viewport.INSTANCE.f_84806_;
        }

        public static int m_157127_() {
            return Viewport.INSTANCE.f_84807_;
        }

        public static int m_157128_() {
            return Viewport.INSTANCE.f_84808_;
        }

        public static int m_157129_() {
            return Viewport.INSTANCE.f_84809_;
        }

        private static /* synthetic */ Viewport[] m_157130_() {
            return new Viewport[]{INSTANCE};
        }

        static {
            $VALUES = Viewport.m_157130_();
        }
    }

    static class ColorMask {
        public boolean f_84608_ = true;
        public boolean f_84609_ = true;
        public boolean f_84610_ = true;
        public boolean f_84611_ = true;

        ColorMask() {
        }
    }

    static class StencilState {
        public final StencilFunc f_84767_ = new StencilFunc();
        public int f_84768_ = -1;
        public int f_84769_ = 7680;
        public int f_84770_ = 7680;
        public int f_84771_ = 7680;

        StencilState() {
        }
    }

    static class StencilFunc {
        public int f_84761_ = 519;
        public int f_84762_;
        public int f_84763_ = -1;

        StencilFunc() {
        }
    }

    @DontObfuscate
    public static final class DestFactor
    extends Enum<DestFactor> {
        public static final /* enum */ DestFactor CONSTANT_ALPHA = new DestFactor(32771);
        public static final /* enum */ DestFactor CONSTANT_COLOR = new DestFactor(32769);
        public static final /* enum */ DestFactor DST_ALPHA = new DestFactor(772);
        public static final /* enum */ DestFactor DST_COLOR = new DestFactor(774);
        public static final /* enum */ DestFactor ONE = new DestFactor(1);
        public static final /* enum */ DestFactor ONE_MINUS_CONSTANT_ALPHA = new DestFactor(32772);
        public static final /* enum */ DestFactor ONE_MINUS_CONSTANT_COLOR = new DestFactor(32770);
        public static final /* enum */ DestFactor ONE_MINUS_DST_ALPHA = new DestFactor(773);
        public static final /* enum */ DestFactor ONE_MINUS_DST_COLOR = new DestFactor(775);
        public static final /* enum */ DestFactor ONE_MINUS_SRC_ALPHA = new DestFactor(771);
        public static final /* enum */ DestFactor ONE_MINUS_SRC_COLOR = new DestFactor(769);
        public static final /* enum */ DestFactor SRC_ALPHA = new DestFactor(770);
        public static final /* enum */ DestFactor SRC_COLOR = new DestFactor(768);
        public static final /* enum */ DestFactor ZERO = new DestFactor(0);
        public final int f_84646_;
        private static final /* synthetic */ DestFactor[] $VALUES;

        public static DestFactor[] values() {
            return (DestFactor[])$VALUES.clone();
        }

        public static DestFactor valueOf(String p_84654_) {
            return Enum.valueOf(DestFactor.class, p_84654_);
        }

        private DestFactor(int p_84652_) {
            this.f_84646_ = p_84652_;
        }

        private static /* synthetic */ DestFactor[] m_157123_() {
            return new DestFactor[]{CONSTANT_ALPHA, CONSTANT_COLOR, DST_ALPHA, DST_COLOR, ONE, ONE_MINUS_CONSTANT_ALPHA, ONE_MINUS_CONSTANT_COLOR, ONE_MINUS_DST_ALPHA, ONE_MINUS_DST_COLOR, ONE_MINUS_SRC_ALPHA, ONE_MINUS_SRC_COLOR, SRC_ALPHA, SRC_COLOR, ZERO};
        }

        static {
            $VALUES = DestFactor.m_157123_();
        }
    }

    @DontObfuscate
    public static final class SourceFactor
    extends Enum<SourceFactor> {
        public static final /* enum */ SourceFactor CONSTANT_ALPHA = new SourceFactor(32771);
        public static final /* enum */ SourceFactor CONSTANT_COLOR = new SourceFactor(32769);
        public static final /* enum */ SourceFactor DST_ALPHA = new SourceFactor(772);
        public static final /* enum */ SourceFactor DST_COLOR = new SourceFactor(774);
        public static final /* enum */ SourceFactor ONE = new SourceFactor(1);
        public static final /* enum */ SourceFactor ONE_MINUS_CONSTANT_ALPHA = new SourceFactor(32772);
        public static final /* enum */ SourceFactor ONE_MINUS_CONSTANT_COLOR = new SourceFactor(32770);
        public static final /* enum */ SourceFactor ONE_MINUS_DST_ALPHA = new SourceFactor(773);
        public static final /* enum */ SourceFactor ONE_MINUS_DST_COLOR = new SourceFactor(775);
        public static final /* enum */ SourceFactor ONE_MINUS_SRC_ALPHA = new SourceFactor(771);
        public static final /* enum */ SourceFactor ONE_MINUS_SRC_COLOR = new SourceFactor(769);
        public static final /* enum */ SourceFactor SRC_ALPHA = new SourceFactor(770);
        public static final /* enum */ SourceFactor SRC_ALPHA_SATURATE = new SourceFactor(776);
        public static final /* enum */ SourceFactor SRC_COLOR = new SourceFactor(768);
        public static final /* enum */ SourceFactor ZERO = new SourceFactor(0);
        public final int f_84751_;
        private static final /* synthetic */ SourceFactor[] $VALUES;

        public static SourceFactor[] values() {
            return (SourceFactor[])$VALUES.clone();
        }

        public static SourceFactor valueOf(String p_84759_) {
            return Enum.valueOf(SourceFactor.class, p_84759_);
        }

        private SourceFactor(int p_84757_) {
            this.f_84751_ = p_84757_;
        }

        private static /* synthetic */ SourceFactor[] m_157124_() {
            return new SourceFactor[]{CONSTANT_ALPHA, CONSTANT_COLOR, DST_ALPHA, DST_COLOR, ONE, ONE_MINUS_CONSTANT_ALPHA, ONE_MINUS_CONSTANT_COLOR, ONE_MINUS_DST_ALPHA, ONE_MINUS_DST_COLOR, ONE_MINUS_SRC_ALPHA, ONE_MINUS_SRC_COLOR, SRC_ALPHA, SRC_ALPHA_SATURATE, SRC_COLOR, ZERO};
        }

        static {
            $VALUES = SourceFactor.m_157124_();
        }
    }

    public static final class LogicOp
    extends Enum<LogicOp> {
        public static final /* enum */ LogicOp AND = new LogicOp(5377);
        public static final /* enum */ LogicOp AND_INVERTED = new LogicOp(5380);
        public static final /* enum */ LogicOp AND_REVERSE = new LogicOp(5378);
        public static final /* enum */ LogicOp CLEAR = new LogicOp(5376);
        public static final /* enum */ LogicOp COPY = new LogicOp(5379);
        public static final /* enum */ LogicOp COPY_INVERTED = new LogicOp(5388);
        public static final /* enum */ LogicOp EQUIV = new LogicOp(5385);
        public static final /* enum */ LogicOp INVERT = new LogicOp(5386);
        public static final /* enum */ LogicOp NAND = new LogicOp(5390);
        public static final /* enum */ LogicOp NOOP = new LogicOp(5381);
        public static final /* enum */ LogicOp NOR = new LogicOp(5384);
        public static final /* enum */ LogicOp OR = new LogicOp(5383);
        public static final /* enum */ LogicOp OR_INVERTED = new LogicOp(5389);
        public static final /* enum */ LogicOp OR_REVERSE = new LogicOp(5387);
        public static final /* enum */ LogicOp SET = new LogicOp(5391);
        public static final /* enum */ LogicOp XOR = new LogicOp(5382);
        public final int f_84715_;
        private static final /* synthetic */ LogicOp[] $VALUES;

        public static LogicOp[] values() {
            return (LogicOp[])$VALUES.clone();
        }

        public static LogicOp valueOf(String p_84723_) {
            return Enum.valueOf(LogicOp.class, p_84723_);
        }

        private LogicOp(int p_84721_) {
            this.f_84715_ = p_84721_;
        }

        private static /* synthetic */ LogicOp[] m_157125_() {
            return new LogicOp[]{AND, AND_INVERTED, AND_REVERSE, CLEAR, COPY, COPY_INVERTED, EQUIV, INVERT, NAND, NOOP, NOR, OR, OR_INVERTED, OR_REVERSE, SET, XOR};
        }

        static {
            $VALUES = LogicOp.m_157125_();
        }
    }
}

