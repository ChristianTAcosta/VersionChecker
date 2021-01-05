package com.dynious.versionchecker.client.gui;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.dynious.versionchecker.api.Update;
import com.dynious.versionchecker.handler.RemoveHandler;
import com.dynious.versionchecker.helper.ModHelper;
import com.dynious.versionchecker.lib.Resources;
import com.dynious.versionchecker.lib.Strings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;

public class GuiButtonDownloaded extends GuiButton
{
    private Update update;
    private boolean ticked;

    public GuiButtonDownloaded(int id, int x, int y)
    {
        super(id, x, y, 20, 20, "");
    }

    public void setUpdate(Update update)
    {
        this.visible = update != null && !update.isDirectLink;
       // ticked = update != null && RemoveHandler.filesToDelete.contains( ModHelper.getModContainer(update.MOD_ID).getSource());
        ticked = false;
        this.update = update;
    }
    


    public void onButtonClicked()
    {
        ticked = !ticked;
       
        if(!update.isNewMod())
        {
        File file = ModHelper.getModContainer(update.MOD_ID).getSource();
        if (ticked)
        {
            RemoveHandler.filesToDelete.add(file);
        }
        else
        {
            RemoveHandler.filesToDelete.remove(file);
        }
       }
        update.setDownloaded(ticked);
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y, float partialTicks)
    {
        if (this.visible)
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(Resources.GUI_BUTTON_TICK);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
            int k = 0;
            int j = 0;
            if (flag)
            {
                k += this.height;
            }
            if (ticked)
            {
                j += this.width;
            }
            Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, j, k, this.width, this.height, 40, 40);
            this.drawString(Minecraft.getMinecraft().fontRenderer, I18n.translateToLocal(Strings.MARK_DL), this.x + this.width + 5, this.y + this.height / 2 - 4, 0xFFFFFF);
        }
    }
}
