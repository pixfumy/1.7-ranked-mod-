package racemod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.world.WorldEvent.Load;

public class EnderEyeBreakEventHandler {
	public Long eyeSeed;
	public Random eyeRandom;
	public ExampleWorldSavedData seedInfo;
	public HashSet<EntityEnderEye> eyeSet = new HashSet<EntityEnderEye>();
	
	@SubscribeEvent
	public void onEyeThrown(EnteringChunk e) {
		if (e.entity instanceof EntityEnderEye) {
			EntityEnderEye eye = (EntityEnderEye) e.entity;
			try {
				if (eye != null && !eyeSet.contains(eye)) {
					seedInfo = ExampleWorldSavedData.get((World)DimensionManager.getWorld(0));
					eyeSet.add(eye);
					Field shatterOrDrop;
					int seedResult = Math.abs((int)seedInfo.updateEyeSeed()) % (int)Math.pow(16.0D, 4.0D);
					shatterOrDrop = eye.getClass().getDeclaredField("field_70221_f"); //shatterOrDrop, or obfuscated field_70221_f, 
					shatterOrDrop.setAccessible(true);
					boolean newShatterOrDrop = seedResult % 5 > 0;
					shatterOrDrop.setBoolean(eye, newShatterOrDrop);
					shatterOrDrop.setAccessible(false);
					System.out.println(" " + newShatterOrDrop);
				}
				
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}