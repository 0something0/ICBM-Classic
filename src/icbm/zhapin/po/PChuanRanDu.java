package icbm.zhapin.po;

import icbm.api.explosion.ExplosiveType;
import icbm.zhapin.ZhuYaoZhaPin;
import icbm.zhapin.zhapin.ZhaPin;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import universalelectricity.core.vector.Vector3;

public class PChuanRanDu extends PICBM {
	public static PChuanRanDu INSTANCE;

	public PChuanRanDu(int id, boolean isBadEffect, int color, String name) {
		super(id, isBadEffect, color, name);
		this.setIconIndex(6, 0);
	}

	@Override
	public void performEffect(EntityLivingBase entityLiving, int amplifier) {
		if (!(entityLiving instanceof EntityZombie)
				&& !(entityLiving instanceof EntityPigZombie)) {
			entityLiving.attackEntityFrom(DamageSource.magic, 1);
		}

		if (entityLiving.worldObj.rand.nextFloat() > 0.8) {
			// Poison things around it
			if (!ZhuYaoZhaPin.shiBaoHu(entityLiving.worldObj, new Vector3(
					entityLiving), ExplosiveType.ALL, ZhaPin.duQi)) {
				int r = 13;
				AxisAlignedBB entitySurroundings = AxisAlignedBB
						.getBoundingBox(entityLiving.posX - r,
								entityLiving.posY - r, entityLiving.posZ - r,
								entityLiving.posX + r, entityLiving.posY + r,
								entityLiving.posZ + r);
				List<EntityLivingBase> entities = entityLiving.worldObj
						.getEntitiesWithinAABB(EntityLivingBase.class,
								entitySurroundings);

				for (EntityLivingBase entity : entities) {
					if (entity != null && entity != entityLiving) {
						if (entity instanceof EntityPig) {
							EntityPigZombie newEntity = new EntityPigZombie(
									entity.worldObj);
							newEntity.setLocationAndAngles(entity.posX,
									entity.posY, entity.posZ,
									entity.rotationYaw, entity.rotationPitch);

							if (!entity.worldObj.isRemote) {
								entity.worldObj.spawnEntityInWorld(newEntity);
							}
							entity.setDead();
						} else if (entity instanceof EntityVillager) {
							EntityZombie newEntity = new EntityZombie(
									entity.worldObj);
							newEntity.setLocationAndAngles(entity.posX,
									entity.posY, entity.posZ,
									entity.rotationYaw, entity.rotationPitch);
							newEntity.setVillager(true);
							if (!entity.worldObj.isRemote) {
								entity.worldObj.spawnEntityInWorld(newEntity);
							}
							entity.setDead();
						}

						ZhuYaoZhaPin.DU_CHUAN_RAN.poisonEntity(new Vector3(
								entity), entity);
					}
				}
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		if (duration % (20 * 2) == 0) {
			return true;
		}

		return false;
	}
}
