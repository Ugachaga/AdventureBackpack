package com.darkona.adventurebackpack.develop;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentTranslation;
public class ChatHandler
{
	  public static void sendServerMessage(String string)
	  {
	    TextComponentTranslation translation = new TextComponentTranslation(string, new Object[0]);
	    Minecraft.getMinecraft().getIntegratedServer().getPlayerList().sendChatMsg(translation);
	  }
  }
