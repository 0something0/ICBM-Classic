package icbm.zhapin.cart;

import icbm.api.explosion.IExplosive;
import icbm.api.explosion.IExplosiveContainer;
import icbm.zhapin.ZhuYaoZhaPin;
import icbm.zhapin.zhapin.ZhaPinRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EChe extends EntityMinecartTNT implements IExplosiveContainer,
		IEntityAdditionalSpawnData {
	public int haoMa = 0;
	public NBTTagCompound nbtData = new NBTTagCompound();

	public EChe(World par1World) {
		super(par1World);
	}

	public EChe(World par1World, double x, double y, double z, int explosiveID) {
		super(par1World, x, y, z);
		this.haoMa = explosiveID;
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(this.haoMa);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		this.haoMa = data.readInt();
	}

	@Override
	protected void explodeCart(double par1) {
		this.worldObj.spawnParticle("hugeexplosion", this.posX, this.posY,
				this.posZ, 0.0D, 0.0D, 0.0D);
		this.getExplosiveType().createExplosion(this.worldObj, this.posX,
				this.posY, this.posZ, this);
		this.setDead();
	}

	public boolean interact(EntityPlayer par1EntityPlayer) {
		if (par1EntityPlayer.getCurrentEquippedItem() != null) {
			if (par1EntityPlayer.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID) {
				this.ignite();
				return true;
			}
		}
		return false;
	}

	@Override
	public void killMinecart(DamageSource par1DamageSource) {
		this.setDead();
		ItemStack itemstack = new ItemStack(Item.minecartEmpty, 1);

		if (this.entityName != null) {
			itemstack.setItemName(this.entityName);
		}

		this.entityDropItem(itemstack, 0.0F);

		double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;

		if (!par1DamageSource.isExplosion()) {
			this.entityDropItem(new ItemStack(ZhuYaoZhaPin.bZhaDan, 1,
					this.haoMa), 0.0F);
		}

		if (par1DamageSource.isFireDamage() || par1DamageSource.isExplosion()
				|| d0 >= 0.009999999776482582D) {
			this.explodeCart(d0);
		}
	}

	@Override
	public ItemStack getCartItem() {
		return new ItemStack(ZhuYaoZhaPin.itChe, 1, this.haoMa);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("haoMa", this.haoMa);
		this.nbtData = nbt.getCompoundTag("data");

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.haoMa = nbt.getInteger("haoMa");
		nbt.setTag("data", this.nbtData);

	}

	@Override
	public IExplosive getExplosiveType() {
		return ZhaPinRegistry.get(this.haoMa);
	}

	@Override
	public Block getDefaultDisplayTile() {
		return ZhuYaoZhaPin.bZhaDan;
	}

	@Override
	public int getDefaultDisplayTileData() {
		return this.haoMa;
	}

	@Override
	public NBTTagCompound getTagCompound() {
		return this.nbtData;
	}

}
