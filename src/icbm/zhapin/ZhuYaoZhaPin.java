package icbm.zhapin;

import icbm.api.ICBM;
import icbm.api.explosion.ExplosiveType;
import icbm.core.ICBMFlags;
import icbm.core.ICBMTab;
import icbm.core.IChunkLoadHandler;
import icbm.core.SheDing;
import icbm.core.ZhuYaoICBM;
import icbm.zhapin.baozha.EBaoZha;
import icbm.zhapin.cart.EChe;
import icbm.zhapin.cart.ItChe;
import icbm.zhapin.dianqi.ItFaSheQi;
import icbm.zhapin.dianqi.ItJieJa;
import icbm.zhapin.dianqi.ItLeiDaQiang;
import icbm.zhapin.dianqi.ItLeiSheZhiBiao;
import icbm.zhapin.dianqi.ItYaoKong;
import icbm.zhapin.jiqi.BJiQi;
import icbm.zhapin.jiqi.BJiQi.JiQi;
import icbm.zhapin.jiqi.IBJiQi;
import icbm.zhapin.po.PChuanRanDu;
import icbm.zhapin.po.PDaDu;
import icbm.zhapin.po.PDongShang;
import icbm.zhapin.zhapin.BZhaDan;
import icbm.zhapin.zhapin.EShouLiuDan;
import icbm.zhapin.zhapin.EZhaDan;
import icbm.zhapin.zhapin.IBZhaDan;
import icbm.zhapin.zhapin.ItShouLiuDan;
import icbm.zhapin.zhapin.ZhaPin;
import icbm.zhapin.zhapin.ZhaPinRegistry;
import icbm.zhapin.zhapin.daodan.EDaoDan;
import icbm.zhapin.zhapin.daodan.ItDaoDan;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.item.ItemElectric;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.RecipeHelper;
import calclavia.lib.UniversalRecipes;
import calclavia.lib.flag.FlagRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ZhuYaoZhaPin.NAME, name = ZhuYaoZhaPin.NAME, version = ICBM.VERSION, dependencies = "after:ICBM|Sentry;after:AtomicScience", useMetadata = true)
@NetworkMod(channels = ZhuYaoZhaPin.CHANNEL, clientSideRequired = true, serverSideRequired = false, packetHandler = ZhaPinPacketGuanLi.class)
public class ZhuYaoZhaPin extends ZhuYaoICBM {
	public static final String NAME = ICBM.NAME + "|Explosion";
	public static final String CHANNEL = ICBM.NAME + "|E";

	@Instance(NAME)
	public static ZhuYaoZhaPin instance;

	@Metadata(NAME)
	public static ModMetadata metadata;

	@SidedProxy(clientSide = "icbm.zhapin.ClientProxy", serverSide = "icbm.zhapin.CommonProxy")
	public static CommonProxy proxy;
	public static Item Du;
	public static final int ENTITY_ID_PREFIX = 50;

	/**
	 * Settings and Configurations
	 */
	// Blocks
	public static Block bZhaDan;
	public static Block bJiQi;
	// Items
	public static Item itDaoDan;

	public static ItemElectric itJieJa;
	public static ItemElectric itLeiDaQiang;
	public static ItemElectric itYaoKong;
	public static ItemElectric itLeiSheZhiBiao;
	public static ItemElectric itFaSheQi;

	public static Item itShouLiuDan;
	public static Item itChe;

	public static final Du DU_DU = new Du("Chemical", 1, false);
	public static final Du DU_CHUAN_RAN = new Du("Contagious", 1, true);

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(proxy);

		SheDing.CONFIGURATION.load();
		bZhaDan = new BZhaDan(ICBM.BLOCK_ID_PREFIX + 3);
		bJiQi = new BJiQi(ICBM.BLOCK_ID_PREFIX + 4);

		// ITEMS
		itDaoDan = new ItDaoDan(ICBM.ITEM_ID_PREFIX + 3, "missile");

		itJieJa = new ItJieJa(ICBM.ITEM_ID_PREFIX + 5);
		itLeiDaQiang = new ItLeiDaQiang(ICBM.ITEM_ID_PREFIX + 6);
		itYaoKong = new ItYaoKong(ICBM.ITEM_ID_PREFIX + 7);
		itLeiSheZhiBiao = new ItLeiSheZhiBiao(ICBM.ITEM_ID_PREFIX + 8);
		itFaSheQi = new ItFaSheQi(ICBM.ITEM_ID_PREFIX + 11);

		itShouLiuDan = new ItShouLiuDan(ICBM.ITEM_ID_PREFIX + 12);
		itChe = new ItChe(ICBM.ITEM_ID_PREFIX + 11);

		/**
		 * Potion Effects
		 */
		PDaDu.INSTANCE = new PDaDu(22, true, 5149489, "toxin");
		PChuanRanDu.INSTANCE = new PChuanRanDu(23, false, 5149489, "virus");
		PDongShang.INSTANCE = new PDongShang(24, false, 5149489, "frostBite");

		SheDing.CONFIGURATION.save();

		ICBMTab.itemStack = new ItemStack(ZhuYaoZhaPin.bZhaDan);

		/**
		 * Dispenser Handler
		 */
		BlockDispenser.dispenseBehaviorRegistry.putObject(
				ZhuYaoZhaPin.itShouLiuDan, new IBehaviorDispenseItem() {
					@Override
					public ItemStack dispense(IBlockSource blockSource,
							ItemStack itemStack) {
						World world = blockSource.getWorld();

						if (!world.isRemote) {
							int x = blockSource.getXInt();
							int y = blockSource.getYInt();
							int z = blockSource.getZInt();
							EnumFacing enumFacing = EnumFacing
									.getFront(blockSource.getBlockMetadata());

							EShouLiuDan entity = new EShouLiuDan(world,
									new Vector3(x, y, z), itemStack
											.getItemDamage());
							entity.setThrowableHeading(
									enumFacing.getFrontOffsetX(),
									0.10000000149011612D,
									enumFacing.getFrontOffsetZ(), 0.5F, 1.0F);
							world.spawnEntityInWorld(entity);
						}

						itemStack.stackSize--;
						return itemStack;
					}
				});

		BlockDispenser.dispenseBehaviorRegistry.putObject(ZhuYaoZhaPin.itChe,
				new IBehaviorDispenseItem() {
					private final BehaviorDefaultDispenseItem defaultItemDispenseBehavior = new BehaviorDefaultDispenseItem();

					@Override
					public ItemStack dispense(IBlockSource blockSource,
							ItemStack itemStack) {
						World world = blockSource.getWorld();

						if (!world.isRemote) {
							int x = blockSource.getXInt();
							int y = blockSource.getYInt();
							int z = blockSource.getZInt();

							EnumFacing var3 = EnumFacing.getFront(blockSource
									.getBlockMetadata());
							World var4 = blockSource.getWorld();
							double var5 = blockSource.getX()
									+ var3.getFrontOffsetX() * 1.125F;
							double var7 = blockSource.getY();
							double var9 = blockSource.getZ()
									+ var3.getFrontOffsetZ() * 1.125F;
							int var11 = blockSource.getXInt()
									+ var3.getFrontOffsetX();
							int var12 = blockSource.getYInt();
							int var13 = blockSource.getZInt()
									+ var3.getFrontOffsetZ();
							int var14 = var4.getBlockId(var11, var12, var13);
							double var15;

							if (BlockRailBase.isRailBlock(var14)) {
								var15 = 0.0D;
							} else {
								if (var14 != 0
										|| !BlockRailBase.isRailBlock(var4
												.getBlockId(var11, var12 - 1,
														var13))) {
									return this.defaultItemDispenseBehavior
											.dispense(blockSource, itemStack);
								}

								var15 = -1.0D;
							}

							EChe var22 = new EChe(world, var5, var7 + var15,
									var9, itemStack.getItemDamage());
							world.spawnEntityInWorld(var22);
							world.playAuxSFX(1000, x, y, z, 0);
						}

						itemStack.stackSize--;
						return itemStack;
					}
				});

		/**
		 * Chunk loading handler.
		 */
		ForgeChunkManager.setForcedChunkLoadingCallback(this,
				new LoadingCallback() {
					@Override
					public void ticketsLoaded(List<Ticket> tickets, World world) {
						for (Ticket ticket : tickets) {
							if (ticket.getEntity() instanceof IChunkLoadHandler) {
								((IChunkLoadHandler) ticket.getEntity())
										.chunkLoaderInit(ticket);
							} else {
								if (ticket.getModData() != null) {
									Vector3 position = Vector3
											.readFromNBT(ticket.getModData());

									TileEntity tileEntity = position
											.getTileEntity(ticket.world);

									if (tileEntity instanceof IChunkLoadHandler) {
										((IChunkLoadHandler) tileEntity)
												.chunkLoaderInit(ticket);
									}
								}
							}
						}
					}
				});

		// -- Registering Blocks
		GameRegistry.registerBlock(bZhaDan, IBZhaDan.class, "bZhaDan");
		GameRegistry.registerBlock(bJiQi, IBJiQi.class, "bJiQi");

		ICBM.explosionManager = ZhaPinRegistry.class;

		ZhuYaoZhaPin.proxy.preInit();
	}

	@EventHandler
	public void load(FMLInitializationEvent evt) {
		super.init(evt);
		ZhuYaoICBM.setModMetadata(NAME, metadata);
	}

	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

		/**
		 * Add all Recipes
		 */
		// Rocket Launcher
		GameRegistry.addRecipe(new ShapedOreRecipe(itFaSheQi, new Object[] {
				"SCR", "SB ", 'R', itLeiDaQiang, 'C',
				new ItemStack(bJiQi, 1, JiQi.XiaoFaSheQi.ordinal() + 6), 'B',
				Block.stoneButton, 'S', UniversalRecipes.PRIMARY_METAL }));

		// Radar Gun
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.itLeiDaQiang), new Object[] { "@#!", " $!", "  !",
				'@', Block.glass, '!', UniversalRecipes.PRIMARY_METAL, '#',
				UniversalRecipes.CIRCUIT_T1, '$', Block.stoneButton }));
		// Remote
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.itYaoKong), new Object[] { "?@@", "@#$", "@@@",
				'@', UniversalRecipes.PRIMARY_METAL, '?', Item.redstone, '#',
				UniversalRecipes.CIRCUIT_T2, '$', Block.stoneButton }));
		// Laser Designator
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.itLeiSheZhiBiao), new Object[] { "!  ", " ? ",
				"  @", '@',
				ElectricItemHelper.getUncharged(ZhuYaoZhaPin.itYaoKong), '?',
				UniversalRecipes.CIRCUIT_T3, '!',
				ElectricItemHelper.getUncharged(ZhuYaoZhaPin.itLeiDaQiang) }));
		// Defuser
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.itJieJa), new Object[] { "I  ", " W ", "  C", 'C',
				UniversalRecipes.CIRCUIT_T2, 'W', UniversalRecipes.WRENCH, 'I',
				UniversalRecipes.WIRE }));
		// Missile Launcher Platform
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 0), new Object[] { "! !", "!C!", "!!!",
				'!', UniversalRecipes.SECONDARY_METAL, 'C',
				UniversalRecipes.CIRCUIT_T1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 1), new Object[] { "! !", "!C!", "!@!",
				'@', new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 0), '!',
				UniversalRecipes.PRIMARY_METAL, 'C',
				UniversalRecipes.CIRCUIT_T2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 2), new Object[] { "! !", "!C!", "!@!",
				'@', new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 1), '!',
				UniversalRecipes.PRIMARY_PLATE, 'C',
				UniversalRecipes.CIRCUIT_T3 }));
		// Missile Launcher Panel
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 3), new Object[] { "!!!", "!#!", "!?!",
				'#', UniversalRecipes.CIRCUIT_T1, '!', Block.glass, '?',
				UniversalRecipes.WIRE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 4), new Object[] { "!$!", "!#!", "!?!",
				'#', UniversalRecipes.CIRCUIT_T2, '!',
				UniversalRecipes.PRIMARY_METAL, '?', UniversalRecipes.WIRE,
				'$', new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 3) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 5), new Object[] { "!$!", "!#!", "!?!",
				'#', UniversalRecipes.CIRCUIT_T3, '!', Item.ingotGold, '?',
				UniversalRecipes.WIRE, '$',
				new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 4) }));
		// Missile Launcher Support Frame
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 6), new Object[] { "! !", "!!!", "! !",
				'!', UniversalRecipes.SECONDARY_METAL }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 7), new Object[] { "! !", "!@!", "! !",
				'!', UniversalRecipes.PRIMARY_METAL, '@',
				new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 6) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 8), new Object[] { "! !", "!@!", "! !",
				'!', UniversalRecipes.PRIMARY_PLATE, '@',
				new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 7) }));
		// Radar Station
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 9), new Object[] { "?@?", " ! ", "!#!",
				'@',
				ElectricItemHelper.getUncharged(ZhuYaoZhaPin.itLeiDaQiang),
				'!', UniversalRecipes.PRIMARY_PLATE, '#',
				UniversalRecipes.CIRCUIT_T1, '?', Item.ingotGold }));
		// EMP Tower
		RecipeHelper.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 10), new Object[] { "?W?", "@!@", "?#?",
				'?', UniversalRecipes.PRIMARY_PLATE, '!',
				UniversalRecipes.CIRCUIT_T3, '@', UniversalRecipes.BATTERY_BOX,
				'#', UniversalRecipes.MOTOR, 'W', UniversalRecipes.WIRE }),
				"EMP Tower", SheDing.CONFIGURATION, true);

		// Cruise Launcher
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ZhuYaoZhaPin.bJiQi, 1, 11), new Object[] { "?! ", "@@@", '@',
				UniversalRecipes.PRIMARY_PLATE, '!',
				new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 2), '?',
				new ItemStack(ZhuYaoZhaPin.bJiQi, 1, 8) }));

		// Missile Module
		// Find and try to add a recipe with fuel, then oil then coal.
		try {
			if (FluidRegistry.getFluid("fuel") != null && SheDing.USE_FUEL) {
				for (FluidContainerData data : FluidContainerRegistry
						.getRegisteredFluidContainerData()) {
					if (data.fluid != null) {
						if (data.fluid.getFluid() == FluidRegistry
								.getFluid("fuel")) {
							GameRegistry.addRecipe(new ShapedOreRecipe(
									new ItemStack(itDaoDan, 1,
											ZhaPin.missileModule.getID()),
									new Object[] { " @ ", "@#@", "@?@", '@',
											UniversalRecipes.PRIMARY_METAL,
											'?', data.filledContainer, '#',
											UniversalRecipes.CIRCUIT_T1 }));
						}
					}
				}
			} else if (FluidRegistry.getFluid("oil") != null
					&& SheDing.USE_FUEL) {
				for (FluidContainerData data : FluidContainerRegistry
						.getRegisteredFluidContainerData()) {
					if (data.fluid != null) {
						if (data.fluid.getFluid() == FluidRegistry
								.getFluid("oil")) {
							GameRegistry.addRecipe(new ShapedOreRecipe(
									new ItemStack(itDaoDan, 1,
											ZhaPin.missileModule.getID()),
									new Object[] { " @ ", "@#@", "@?@", '@',
											UniversalRecipes.PRIMARY_METAL,
											'?', data.filledContainer, '#',
											UniversalRecipes.CIRCUIT_T1 }));
						}
					}
				}
			} else {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
						itDaoDan, 1, ZhaPin.missileModule.getID()),
						new Object[] { " @ ", "@#@", "@?@", '@',
								UniversalRecipes.PRIMARY_METAL, '?', Item.coal,
								'#', UniversalRecipes.CIRCUIT_T1 }));
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to add missile module recipe!");
			e.printStackTrace();
		}

		// Homing
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itDaoDan, 1,
				ZhaPin.zhuiZhong.getID()), new Object[] { " B ", " C ", "BMB",
				'M', new ItemStack(itDaoDan, 1, ZhaPin.missileModule.getID()),
				'C', UniversalRecipes.CIRCUIT_T1, 'B',
				UniversalRecipes.SECONDARY_METAL }));
		// Anti-ballistic
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itDaoDan, 1,
				ZhaPin.fanDan.getID()), new Object[] { "!", "?", "@", '@',
				new ItemStack(itDaoDan, 1, ZhaPin.missileModule.getID()), '?',
				new ItemStack(ZhuYaoZhaPin.bZhaDan, 1, 0), '!',
				UniversalRecipes.CIRCUIT_T1 }));
		// Cluster
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itDaoDan, 1,
				ZhaPin.fenZhiDan.getID()), new Object[] { " ! ", " ? ", "!@!",
				'@', new ItemStack(itDaoDan, 1, ZhaPin.missileModule.getID()),
				'?', ZhaPin.qunDan.getItemStack(), '!',
				new ItemStack(ZhuYaoZhaPin.itDaoDan, 1, 0) }));
		// Nuclear Cluster
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itDaoDan, 1,
				ZhaPin.yuanZiFenZhiDan.getID()), new Object[] { " N ", "NCN",
				'C', new ItemStack(itDaoDan, 1, ZhaPin.fenZhiDan.getID()), 'N',
				ZhaPin.yuanZi.getItemStack() }));

		/**
		 * Add all explosive recipes.
		 */
		for (ZhaPin zhaPin : ZhaPinRegistry.getAllZhaPin()) {
			zhaPin.init();

			// Missile
			RecipeHelper.addRecipe(new ShapelessOreRecipe(new ItemStack(
					ZhuYaoZhaPin.itDaoDan, 1, zhaPin.getID()), new Object[] {
					new ItemStack(itDaoDan, 1, ZhaPin.missileModule.getID()),
					new ItemStack(ZhuYaoZhaPin.bZhaDan, 1, zhaPin.getID()) }),
					zhaPin.getUnlocalizedName() + " Missile",
					SheDing.CONFIGURATION, true);

			if (zhaPin.getTier() < 2) {
				// Grenade
				RecipeHelper
						.addRecipe(
								new ShapedOreRecipe(new ItemStack(
										ZhuYaoZhaPin.itShouLiuDan, 1, zhaPin
												.getID()),
										new Object[] {
												"?",
												"@",
												'@',
												new ItemStack(
														ZhuYaoZhaPin.bZhaDan,
														1, zhaPin.getID()),
												'?', Item.silk }),
								zhaPin.getUnlocalizedName() + " Grenade",
								SheDing.CONFIGURATION, true);
			}

			if (zhaPin.getTier() < 3) {
				// Minecart
				RecipeHelper.addRecipe(new ShapedOreRecipe(new ItemStack(
						ZhuYaoZhaPin.itChe, 1, zhaPin.getID()), new Object[] {
						"?", "@", '?',
						new ItemStack(ZhuYaoZhaPin.bZhaDan, 1, zhaPin.getID()),
						'@', Item.minecartEmpty }), zhaPin.getUnlocalizedName()
						+ " Minecart", SheDing.CONFIGURATION, true);
			}
		}

		EntityRegistry.registerGlobalEntityID(EZhaDan.class, "ICBMExplosive",
				EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EDaoDan.class, "ICBMMissile",
				EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EBaoZha.class,
				"ICBMProceduralExplosion",
				EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EFeiBlock.class,
				"ICBMGravityBlock", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EGuang.class, "ICBMLightBeam",
				EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(ESuiPian.class, "ICBMFragment",
				EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EShouLiuDan.class, "ICBMGrenade",
				EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EChe.class, "ICBMChe",
				EntityRegistry.findGlobalUniqueEntityId());

		EntityRegistry.registerModEntity(EZhaDan.class, "ICBMExplosive",
				ENTITY_ID_PREFIX, this, 50, 5, true);
		EntityRegistry.registerModEntity(EDaoDan.class, "ICBMMissile",
				ENTITY_ID_PREFIX + 1, this, 500, 1, true);
		EntityRegistry.registerModEntity(EBaoZha.class,
				"ICBMProceduralExplosion", ENTITY_ID_PREFIX + 2, this, 100, 5,
				true);
		EntityRegistry.registerModEntity(EFeiBlock.class, "ICBMGravityBlock",
				ENTITY_ID_PREFIX + 3, this, 50, 15, true);
		EntityRegistry.registerModEntity(EGuang.class, "ICBMLightBeam",
				ENTITY_ID_PREFIX + 4, this, 80, 5, true);
		EntityRegistry.registerModEntity(ESuiPian.class, "ICBMFragment",
				ENTITY_ID_PREFIX + 5, this, 40, 8, true);
		EntityRegistry.registerModEntity(EShouLiuDan.class, "ICBMGrenade",
				ENTITY_ID_PREFIX + 6, this, 50, 5, true);
		EntityRegistry.registerModEntity(EChe.class, "ICBMChe",
				ENTITY_ID_PREFIX + 8, this, 50, 4, true);

		ZhuYaoZhaPin.proxy.init();
	}

	@ForgeSubscribe
	public void enteringChunk(EnteringChunk evt) {
		if (evt.entity instanceof EDaoDan) {
			((EDaoDan) evt.entity)
					.updateLoadChunk(evt.newChunkX, evt.newChunkZ);
		}
	}

	/**
	 * Is a specific position being protected from a specific type of danger?
	 */
	public static boolean shiBaoHu(World world, Vector3 diDian,
			ExplosiveType type, ZhaPin zhaPin) {
		if (zhaPin != null) {
			if (FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME) != null) {
				if (FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME)
						.containsValue(world, ICBMFlags.FLAG_BAN_GLOBAL,
								"true", diDian)) {
					return true;
				}

				boolean baoHu = false;

				switch (type) {
				case ALL:
					baoHu = FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME)
							.containsValue(world, ICBMFlags.FLAG_BAN_MINECART,
									"true", diDian)
							|| FlagRegistry.getModFlag(
									FlagRegistry.DEFAULT_NAME).containsValue(
									world, ICBMFlags.FLAG_BAN_MISSILE, "true",
									diDian)
							|| FlagRegistry.getModFlag(
									FlagRegistry.DEFAULT_NAME).containsValue(
									world, ICBMFlags.FLAG_BAN_GRENADE, "true",
									diDian)
							|| FlagRegistry.getModFlag(
									FlagRegistry.DEFAULT_NAME).containsValue(
									world, ICBMFlags.FLAG_BAN_EXPLOSIVE,
									"true", diDian);
					break;
				case VEHICLE:
					baoHu = FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME)
							.containsValue(world, ICBMFlags.FLAG_BAN_MINECART,
									"true", diDian);
					break;
				case AIR:
					baoHu = FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME)
							.containsValue(world, ICBMFlags.FLAG_BAN_MISSILE,
									"true", diDian);
					break;
				case ITEM:
					baoHu = FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME)
							.containsValue(world, ICBMFlags.FLAG_BAN_GRENADE,
									"true", diDian);
					break;
				case BLOCK:
					baoHu = FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME)
							.containsValue(world, ICBMFlags.FLAG_BAN_EXPLOSIVE,
									"true", diDian);
					break;
				}

				return FlagRegistry.getModFlag(FlagRegistry.DEFAULT_NAME)
						.containsValue(world, zhaPin.qiZi, "true", diDian)
						|| baoHu;
			}
		}

		return false;
	}

	public static boolean shiBaoHu(World world, Vector3 diDian,
			ExplosiveType type, int haoMa) {
		return shiBaoHu(world, diDian, type, ZhaPinRegistry.get(haoMa));
	}

	@Override
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		super.serverStarting(event);
		ICommandManager commandManager = FMLCommonHandler.instance()
				.getMinecraftServerInstance().getCommandManager();
		ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager);
		serverCommandManager.registerCommand(new MingLing());
	}

	@Override
	protected String getChannel() {
		return CHANNEL;
	}
}
