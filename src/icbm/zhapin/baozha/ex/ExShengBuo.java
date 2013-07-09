package icbm.zhapin.baozha.ex;

import icbm.core.ZhuYaoICBM;
import icbm.core.di.MICBM;
import icbm.zhapin.muoxing.daodan.MMChaoShengBuo;
import icbm.zhapin.muoxing.daodan.MMShengBuo;
import icbm.zhapin.zhapin.ZhaPin;
import icbm.zhapin.zhapin.daodan.DaoDan;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import universalelectricity.prefab.RecipeHelper;
import calclavia.lib.UniversalRecipes;

public class ExShengBuo extends DaoDan
{
	public ExShengBuo(String mingZi, int tier)
	{
		super(mingZi, tier);
	}

	@Override
	public void init()
	{
		if (this.getTier() == 3)
		{
			RecipeHelper.addRecipe(new ShapedOreRecipe(this.getItemStack(), new Object[] { " S ", "S S", " S ", 'S', ZhaPin.shengBuo.getItemStack() }), this.getUnlocalizedName(), ZhuYaoICBM.CONFIGURATION, true);
		}
		else
		{
			RecipeHelper.addRecipe(new ShapedOreRecipe(this.getItemStack(), new Object[] { "@?@", "?R?", "@?@", 'R', ZhaPin.tui.getItemStack(), '?', Block.music, '@', UniversalRecipes.SECONDARY_METAL }), this.getUnlocalizedName(), ZhuYaoICBM.CONFIGURATION, true);
		}
	}

	@Override
	public void createExplosion(World world, double x, double y, double z, Entity entity)
	{
		if (this.getTier() == 3)
		{
			new BzShengBuo(world, entity, x, y, z, 14, 20).setShockWave().explode();
		}
		else
		{
			new BzShengBuo(world, entity, x, y, z, 9, 10).explode();
		}
	}

	@Override
	public MICBM getMuoXing()
	{
		if (this.getTier() == 3)
		{
			return new MMChaoShengBuo();
		}
		else
		{
			return new MMShengBuo();
		}
	}

}