package icbm.classic.client;

import icbm.classic.ICBMClassic;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 6/19/2017.
 */
public class SpikeMeshDefinition implements ItemMeshDefinition
{
    public final ModelResourceLocation base;
    public final ModelResourceLocation fire;
    public final ModelResourceLocation poison;

    public static SpikeMeshDefinition INSTANCE;

    private SpikeMeshDefinition()
    {
        base = new ModelResourceLocation(ICBMClassic.blockSpikes.getRegistryName(), "inventory");
        fire = new ModelResourceLocation(ICBMClassic.blockSpikes.getRegistryName() + "_fire", "inventory");
        poison = new ModelResourceLocation(ICBMClassic.blockSpikes.getRegistryName() + "_poison", "inventory");

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ICBMClassic.blockSpikes), base);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ICBMClassic.blockSpikes), base);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ICBMClassic.blockSpikes), fire);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ICBMClassic.blockSpikes), poison);
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ICBMClassic.blockSpikes), this);
    }

    public static void init()
    {
        INSTANCE = new SpikeMeshDefinition();
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack)
    {
        if (stack.getItemDamage() == 1)
        {
            return poison;
        }
        else if (stack.getItemDamage() == 2)
        {
            return fire;
        }
        return base;
    }
}
