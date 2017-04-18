package com.darkona.adventurebackpack.develop;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import com.darkona.adventurebackpack.develop.ChatHandler;

public class texturemsg {
    public static void loadDev() {
	}
    private static ArrayList<String> textureUUID = new ArrayList<String>();
	  static
	  {
		  //add uuid to list Graphic Designer who has worked on this mod
	    textureUUID.add("a06c96be-f9d0-4074-8cad-500a0c311761");
	    textureUUID.add("9932b533-00b3-4d05-bac8-288500df7c9d");
	  }

	  public static boolean istexture(String uuid)
	  {
	    return textureUUID.contains(uuid);
	  }

	  public static void handleJoin(EntityPlayer player)
	  {
	    if (istexture(player.getUniqueID().toString())) {
	      ChatHandler.sendServerMessage(TextFormatting.AQUA
																			+ "~~AdventureBackPack Mod Graphic Designer~~ "
																			+ TextFormatting.UNDERLINE
																			+ player.getDisplayName()
																			+ TextFormatting.AQUA
																			+ " has joined.");
	    }
	  }
	}
