/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.misc;

import ic2.api.events.RetextureEvent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class UVHelper {
    final float[] uvs;
    int rotation;

    public UVHelper(float[] uvsIn, int rotationIn) {
        this.uvs = uvsIn;
        this.rotation = rotationIn;
    }

    public static UVHelper fromSprite(TextureAtlasSprite sprite) {
        return new UVHelper(new float[]{sprite.m_118409_(), sprite.m_118411_(), sprite.m_118410_(), sprite.m_118412_()}, 0);
    }

    public static UVHelper fromSprite(TextureAtlasSprite sprite, int rotation) {
        return new UVHelper(new float[]{sprite.m_118409_(), sprite.m_118411_(), sprite.m_118410_(), sprite.m_118412_()}, rotation);
    }

    public static UVHelper fromSpriteScaled(TextureAtlasSprite sprite, float maxX, float maxY, int rotation) {
        return new UVHelper(new float[]{sprite.m_118367_((double)maxX), sprite.m_118393_((double)maxY), sprite.m_118367_((double)(16.0f - maxX)), sprite.m_118393_((double)(16.0f - maxY))}, rotation);
    }

    public float getVertexU(int indexIn) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }
        int i = this.getVertexRotated(indexIn);
        return this.uvs[i != 0 && i != 1 ? 2 : 0];
    }

    public float getVertexV(int indexIn) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }
        int i = this.getVertexRotated(indexIn);
        return this.uvs[i != 0 && i != 3 ? 3 : 1];
    }

    public static float[] rotateUVs(float[] uvs, RetextureEvent.Rotation rotation) {
        float[] array = new float[4];
        switch (rotation) {
            case ROTATION_180: 
            case ROTATION_0: {
                System.arraycopy(uvs, 0, array, 0, 4);
                break;
            }
            case ROTATION_270: 
            case ROTATION_90: {
                array[0] = uvs[1];
                array[1] = uvs[0];
                array[2] = uvs[3];
                array[3] = uvs[2];
            }
        }
        return array;
    }

    private int getVertexRotated(int indexIn) {
        return (indexIn + this.rotation / 90) % 4;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void rotate(int rotations) {
        this.rotation = (this.rotation + rotations) % 360;
    }
}

