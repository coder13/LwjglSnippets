package net.va;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TextureLoading {

	public static int loadTexture(BufferedImage image, boolean blur, boolean clamp){
		int texID = GL11.glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texID);
		
		int width = image.getWidth(), height = image.getHeight();
		int[] rawPixels = new int[width*height];
		image.getRGB(0, 0, width, height, rawPixels, 0, width);
		
		ByteBuffer Pixels = BufferUtils.createByteBuffer(4*width*height);
		for (int i = 0; i < rawPixels.length; i++){
			Pixels.put((byte)(rawPixels[i] >> 16 & 0xff)); //red
			Pixels.put((byte)(rawPixels[i] >> 8  & 0xff)); //green
			Pixels.put((byte)(rawPixels[i]       & 0xff)); //blue
			Pixels.put((byte)(rawPixels[i] >> 24 & 0xff)); //alpha
		}
		Pixels.flip();

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		if (blur){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); 
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, clamp?GL_CLAMP:GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, clamp?GL_CLAMP:GL_REPEAT);

		glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, Pixels);
		return texID;
	}
	
}
