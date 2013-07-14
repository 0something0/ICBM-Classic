package icbm.zhapin.jiqi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.prefab.network.IPacketReceiver;
import universalelectricity.prefab.tile.IRotatable;
import calclavia.lib.TileEntityUniversalElectrical;

import com.google.common.io.ByteArrayDataInput;

public class TYinDaoQi extends TileEntityUniversalElectrical implements
		IPacketReceiver, IRotatable {
	private byte fangXiang = 3;

	@Override
	public void handlePacketData(INetworkManager network, int packetType,
			Packet250CustomPayload packet, EntityPlayer player,
			ByteArrayDataInput dataStream) {

	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		this.fangXiang = par1NBTTagCompound.getByte("fangXiang");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setByte("fangXiang", this.fangXiang);
	}

	@Override
	public ForgeDirection getDirection() {
		return ForgeDirection.getOrientation(this.fangXiang);
	}

	@Override
	public void setDirection(ForgeDirection facingDirection) {
		this.fangXiang = (byte) facingDirection.ordinal();
	}
}
