/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.TextureUtil
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.PreparableReloadListener$PreparationBarrier
 *  net.minecraft.server.packs.resources.ReloadableResourceManager
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.util.profiling.ProfilerFiller
 *  org.lwjgl.opengl.GL11
 */
package ic2.core.gui;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import ic2.core.IC2;
import ic2.core.util.LogCategory;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.lwjgl.opengl.GL11;

public class GlTexture
implements Closeable {
    private static final Map<ResourceLocation, GlTexture> textures = new HashMap<ResourceLocation, GlTexture>();
    private final ResourceLocation loc;
    protected int textureId;
    protected int width;
    protected int height;
    protected int canvasWidth;
    protected int canvasHeight;

    public static void init() {
        ResourceManager resourceManager = Minecraft.m_91087_().m_91098_();
        if (resourceManager instanceof ReloadableResourceManager) {
            ((ReloadableResourceManager)resourceManager).m_7217_(new PreparableReloadListener(){

                public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) {
                    for (GlTexture glTexture : textures.values()) {
                        if (glTexture == null) continue;
                        glTexture.close();
                    }
                    textures.clear();
                    return CompletableFuture.completedFuture(null);
                }
            });
        } else {
            IC2.log.warn(LogCategory.General, "The resource manager {} is not reloadable.", resourceManager);
        }
    }

    public static GlTexture get(ResourceLocation resourceLocation) {
        GlTexture glTexture = textures.get(resourceLocation);
        if (glTexture != null) {
            return glTexture;
        }
        return GlTexture.add(resourceLocation, new GlTexture(resourceLocation));
    }

    public static GlTexture add(ResourceLocation resourceLocation, GlTexture glTexture) {
        try {
            glTexture.load(Minecraft.m_91087_().m_91098_());
        }
        catch (IOException iOException) {
            IC2.log.warn(LogCategory.General, "Can't load texture %s", resourceLocation);
            glTexture.close();
            glTexture = null;
        }
        textures.put(resourceLocation, glTexture);
        return glTexture;
    }

    public GlTexture(ResourceLocation resourceLocation) {
        this.loc = resourceLocation;
    }

    protected void load(ResourceManager resourceManager) throws IOException {
        Resource resource = resourceManager.m_215593_(this.loc);
        try (InputStream inputStream = resource.m_215507_();){
            this.load(ImageIO.read(inputStream));
        }
    }

    protected void load(BufferedImage bufferedImage) {
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        this.canvasWidth = Integer.highestOneBit((this.width - 1) * 2);
        this.canvasHeight = Integer.highestOneBit((this.height - 1) * 2);
        this.textureId = TextureUtil.m_85280_();
        IntBuffer intBuffer = ByteBuffer.allocateDirect(this.canvasWidth * this.canvasHeight * 4).asIntBuffer();
        int[] nArray = new int[this.canvasWidth * this.canvasHeight];
        bufferedImage.getRGB(0, 0, this.width, this.height, nArray, 0, this.canvasWidth);
        intBuffer.put(nArray);
        intBuffer.flip();
        this.bind();
        GL11.glTexParameteri((int)3553, (int)33085, (int)0);
        GL11.glTexParameterf((int)3553, (int)33082, (float)0.0f);
        GL11.glTexParameterf((int)3553, (int)33083, (float)0.0f);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10496);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10496);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)this.canvasWidth, (int)this.canvasHeight, (int)0, (int)32993, (int)33639, (IntBuffer)intBuffer);
    }

    @Override
    public void close() {
        if (this.textureId == 0) {
            return;
        }
        TextureUtil.m_85281_((int)this.textureId);
        this.textureId = 0;
    }

    public void bind() {
        if (this.textureId == 0) {
            throw new IllegalStateException("uninitialized texture");
        }
        RenderSystem.m_69396_((int)this.textureId);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCanvasWidth() {
        return this.canvasWidth;
    }

    public int getCanvasHeight() {
        return this.canvasHeight;
    }
}

