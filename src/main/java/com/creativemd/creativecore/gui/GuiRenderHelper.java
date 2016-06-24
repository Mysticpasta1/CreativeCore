package com.creativemd.creativecore.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.utils.ColorUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRenderHelper {
	
	public static GuiRenderHelper instance = new GuiRenderHelper(Minecraft.getMinecraft());
	
	public FontRenderer font;
	public RenderItem itemRenderer;
	
	public GuiRenderHelper(Minecraft mc)
	{
		this(mc.fontRendererObj, mc.getRenderItem());
	}
	
	public GuiRenderHelper(FontRenderer font, RenderItem itemRenderer)
	{
		this.font = font;
		this.itemRenderer = itemRenderer;
	}
	
	public void renderColorPlate(Color color, int width, int height)
	{
		renderColorPlate(0, 0, color, width, height);
	}
	
	public void renderColorPlate(int x, int y, Color color, int width, int height)
	{
		Gui.drawRect(x, y, width, height, ColorUtils.RGBAToInt(color));
	}
	
	public static void renderColorTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color)
	{
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        //GlStateManager.color(1, 1, 1, 1);
        vertexbuffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION);
        vertexbuffer.pos((double)x1, (double)y1, 0.0D).endVertex();
        vertexbuffer.pos((double)y3, (double)y3, 0.0D).endVertex();
        vertexbuffer.pos((double)x2, (double)y2, 0.0D).endVertex();
        //vertexbuffer.pos((double)x1, (double)y3, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	
	public int getFontHeight()
	{
		return font.FONT_HEIGHT;
	}
	
	public int getStringWidth(String text)
	{
		return font.getStringWidth(text);
	}
	
	public int drawStringWithShadow(String text, int width, int height, int color)
	{
		return drawStringWithShadow(text, 0, 0, width, height, color);
	}
	
	public int drawStringWithShadow(String text, int x, int y, int width, int height, int color)
	{
		return drawStringWithShadow(text, x, y, width, height, color, 0);
	}
	
	public int drawStringWithShadow(String text, int x, int y, int width, int height, int color, int additionalWidth)
	{
		int completeWidth = font.getStringWidth(text)+additionalWidth;
		font.drawStringWithShadow(text, width/2-completeWidth/2+additionalWidth, height/2-getFontHeight()/2, color);
		return completeWidth;
	}
	
	public void drawItemStackAndOverlay(ItemStack stack, int x, int y, int width, int height)
	{  
		drawItemStack(stack, x, y, width, height);
		GlStateManager.pushMatrix();
		itemRenderer.renderItemOverlays(font, stack, x, y);
		GlStateManager.disableLighting();
        //GlStateManager.enableDepth();
		GlStateManager.popMatrix();
		
	}
	
	public void drawItemStack(ItemStack stack, int x, int y, int width, int height)
	{  
		drawItemStack(stack, x, y, width, height, 0);
	}
	
	public void drawItemStack(ItemStack stack, int x, int y, int width, int height, int rotation)
	{        
		GlStateManager.pushMatrix();
		
		/*GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        int k = 240;
        int l = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k, (float)l);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);*/
        RenderHelper.enableGUIStandardItemLighting();
        
        GlStateManager.translate(x+8, y+8, 0);
        GlStateManager.rotate(rotation, 0, 0, 1);
        GlStateManager.scale(width/16D, height/16D, 1);
        GlStateManager.translate(-8, -8, -itemRenderer.zLevel-50);
        //GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        itemRenderer.renderItemAndEffectIntoGUI(stack, 0, 0);
        GlStateManager.disableDepth();
        //GlStateManager.enableDepth();
        //GlStateManager.enableLighting();
        //RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();
		
	}
}