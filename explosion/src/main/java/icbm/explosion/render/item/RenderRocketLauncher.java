package icbm.explosion.render.item;

import icbm.Reference;
import icbm.explosion.ICBMExplosion;
import icbm.explosion.model.tiles.MShouFaSheQi;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRocketLauncher implements IItemRenderer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.DOMAIN, Reference.MODEL_PATH + "rocket_launcher.png");

	public static final MShouFaSheQi MODEL = new MShouFaSheQi();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return item.itemID == ICBMExplosion.itemRocketLauncher.itemID;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return item.itemID == ICBMExplosion.itemRocketLauncher.itemID;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);

		if (type == ItemRenderType.INVENTORY)
		{
			GL11.glTranslatef(0, 1.5f, 0);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glScalef(0.8f, 1f, 0.8f);
			GL11.glTranslatef(0, 0.3f, 0);
		}
		else if (type == ItemRenderType.EQUIPPED)
		{

			/** Check to see if we should do a first person render or not. */
			boolean isThisEntity = false;
			boolean isFirstPerson = Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;

			if (data != null)
			{
				if (data.length >= 2)
				{
					isThisEntity = data[1] == Minecraft.getMinecraft().renderViewEntity;
				}
			}

			if (isThisEntity && isFirstPerson)
			{
				GL11.glTranslatef(0, 2f, 0);
				GL11.glRotatef(180, 0, 0, 1);
				GL11.glRotatef(20, 0, 1, 0);
			}
			else
			{
				float scale = 2f;
				GL11.glScalef(scale, scale, scale);
				GL11.glRotatef(-105, 0, 0, 1);
				GL11.glRotatef(-75, 0, 1, 0);
				GL11.glTranslatef(0.1f, -0.9f, 0.6f);
			}
		}
		else if (type == ItemRenderType.ENTITY)
		{
			GL11.glTranslatef(0, -0.5f, 0);
			GL11.glScalef(0.7f, 0.7f, 0.7f);
		}

		MODEL.render(0.0625F);

		GL11.glPopMatrix();
	}
}