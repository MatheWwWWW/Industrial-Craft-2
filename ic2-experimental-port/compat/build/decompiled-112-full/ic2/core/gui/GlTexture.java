/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GLAllocation
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.IReloadableResourceManager
 *  net.minecraft.client.resources.IResource
 *  net.minecraft.client.resources.IResourceManager
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package ic2.core.gui;

import ic2.core.IC2;
import ic2.core.util.LogCategory;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
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
        IResourceManager manager = Minecraft.func_71410_x().func_110442_L();
        if (manager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager)manager).func_110542_a(new IResourceManagerReloadListener(){

                public void func_110549_a(IResourceManager manager) {
                    for (GlTexture texture : textures.values()) {
                        if (texture == null) continue;
                        texture.close();
                    }
                    textures.clear();
                }
            });
        } else {
            IC2.log.warn(LogCategory.General, "The resource manager {} is not reloadable.", manager);
        }
    }

    public static GlTexture get(ResourceLocation identifier) {
        GlTexture ret = textures.get(identifier);
        if (ret != null) {
            return ret;
        }
        return GlTexture.add(identifier, new GlTexture(identifier));
    }

    public static GlTexture add(ResourceLocation identifier, GlTexture texture) {
        try {
            texture.load(Minecraft.func_71410_x().func_110442_L());
        }
        catch (IOException e) {
            IC2.log.warn(LogCategory.General, "Can't load texture %s", identifier);
            texture.close();
            texture = null;
        }
        textures.put(identifier, texture);
        return texture;
    }

    public GlTexture(ResourceLocation loc) {
        this.loc = loc;
    }

    protected void load(IResourceManager manager) throws IOException {
        IResource resource = manager.func_110536_a(this.loc);
        try (InputStream is = resource.func_110527_b();){
            this.load(ImageIO.read(is));
        }
    }

    protected void load(BufferedImage img) {
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.canvasWidth = Integer.highestOneBit((this.width - 1) * 2);
        this.canvasHeight = Integer.highestOneBit((this.height - 1) * 2);
        this.textureId = GlStateManager.func_179146_y();
        IntBuffer buffer = GLAllocation.func_74527_f((int)(this.canvasWidth * this.canvasHeight));
        int[] tmp = new int[this.canvasWidth * this.canvasHeight];
        img.getRGB(0, 0, this.width, this.height, tmp, 0, this.canvasWidth);
        buffer.put(tmp);
        buffer.flip();
        this.bind();
        GL11.glTexParameteri((int)3553, (int)33085, (int)0);
        GL11.glTexParameterf((int)3553, (int)33082, (float)0.0f);
        GL11.glTexParameterf((int)3553, (int)33083, (float)0.0f);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10496);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10496);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)this.canvasWidth, (int)this.canvasHeight, (int)0, (int)32993, (int)33639, (IntBuffer)buffer);
    }

    @Override
    public void close() {
        if (this.textureId == 0) {
            return;
        }
        GlStateManager.func_179150_h((int)this.textureId);
        this.textureId = 0;
    }

    public void bind() {
        if (this.textureId == 0) {
            throw new IllegalStateException("uninitialized texture");
        }
        GlStateManager.func_179144_i((int)this.textureId);
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

