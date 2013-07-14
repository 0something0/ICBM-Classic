package icbm.core;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ICBMTab extends CreativeTabs {
	public static final ICBMTab INSTANCE = new ICBMTab();

	public static ItemStack itemStack;

	public ICBMTab() {
		super(CreativeTabs.getNextID(), "ICBM");
	}

	@Override
	public ItemStack getIconItemStack() {
		if (itemStack == null) {
			itemStack = new ItemStack(Block.tnt);
		}

		return itemStack;
	}

}
