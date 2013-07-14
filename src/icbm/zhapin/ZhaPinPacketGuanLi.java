package icbm.zhapin;

import icbm.core.ZhuYaoICBM;
import icbm.zhapin.dianqi.ItLeiDaQiang;
import icbm.zhapin.dianqi.ItLeiSheZhiBiao;
import icbm.zhapin.dianqi.ItYaoKong;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.network.PacketManager;

import com.google.common.io.ByteArrayDataInput;

/**
 * This class is used for sending and receiving packets between the server and
 * the client. You can directly use this by registering this packet manager with
 * NetworkMod. Example:
 * 
 * @NetworkMod(channels = { "BasicComponents" }, clientSideRequired = true,
 *                      serverSideRequired = false, packetHandler =
 *                      PacketManager.class)
 * 
 *                      Check out {@link #BasicComponents} for better reference.
 * 
 * @author Calclavia
 */
public class ZhaPinPacketGuanLi extends PacketManager {
	public enum ZhaPinPacketType {
		UNSPECIFIED, TILEENTITY, RADAR_GUN, LASER_DESIGNATOR, REMOTE;

		public static ZhaPinPacketType get(int id) {
			if (id >= 0 && id < ZhaPinPacketType.values().length) {
				return ZhaPinPacketType.values()[id];
			}
			return UNSPECIFIED;
		}
	}

	@Override
	public void handlePacketData(INetworkManager network, int packetType,
			Packet250CustomPayload packet, EntityPlayer player,
			ByteArrayDataInput dataStream) {
		try {
			ZhaPinPacketType icbmPacketType = ZhaPinPacketType.get(packetType);

			if (icbmPacketType == ZhaPinPacketType.RADAR_GUN) {
				if (player.inventory.getCurrentItem().getItem() instanceof ItLeiDaQiang) {
					ItemStack itemStack = player.inventory.getCurrentItem();
					((ItLeiDaQiang) player.inventory.getCurrentItem().getItem())
							.setLink(
									itemStack,
									new Vector3(dataStream.readInt(),
											dataStream.readInt(), dataStream
													.readInt()));
					ZhuYaoZhaPin.itLeiDaQiang.discharge(itemStack,
							ItLeiDaQiang.YONG_DIAN_LIANG, true);
				}
			} else if (icbmPacketType == ZhaPinPacketType.LASER_DESIGNATOR) {
				if (player.inventory.getCurrentItem().getItem() instanceof ItLeiSheZhiBiao) {
					ItemStack itemStack = player.inventory.getCurrentItem();
					Vector3 position = new Vector3(dataStream.readInt(),
							dataStream.readInt(), dataStream.readInt());

					((ItLeiSheZhiBiao) ZhuYaoZhaPin.itLeiSheZhiBiao)
							.setLauncherCountDown(itemStack, 119);

					player.worldObj
							.playSoundEffect(
									position.intX(),
									player.worldObj.getHeightValue(
											position.intX(), position.intZ()),
									position.intZ(),
									ZhuYaoICBM.PREFIX + "airstrike",
									5.0F,
									(1.0F + (player.worldObj.rand.nextFloat() - player.worldObj.rand
											.nextFloat()) * 0.2F) * 0.7F);

					player.worldObj.spawnEntityInWorld(new EGuang(
							player.worldObj, position, 5 * 20, 0F, 1F, 0F));

					ZhuYaoZhaPin.itLeiDaQiang.discharge(itemStack,
							ItLeiSheZhiBiao.YONG_DIAN_LIANG, true);
				}
			} else if (icbmPacketType == ZhaPinPacketType.REMOTE) {
				ItemStack itemStack = player.inventory.getCurrentItem();
				ZhuYaoZhaPin.itYaoKong.discharge(itemStack,
						ItYaoKong.YONG_DIAN_LIANG, true);
			}
		} catch (Exception e) {
			ZhuYaoICBM.LOGGER.severe("ICBM item packet receive failed!");
			e.printStackTrace();
		}
	}
}
