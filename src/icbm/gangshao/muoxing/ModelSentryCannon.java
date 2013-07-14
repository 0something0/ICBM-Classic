// Date: 2/17/2013 3:30:42 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package icbm.gangshao.muoxing;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSentryCannon extends ModelBase {
	// fields
	ModelRenderer BaseYawR;
	ModelRenderer BaseYawRPlate;
	ModelRenderer RightBrace;
	ModelRenderer RightBraceF;
	ModelRenderer RightBraceF2;
	ModelRenderer LeftBrace;
	ModelRenderer LeftBraceF;
	ModelRenderer LeftBraceF2;
	ModelRenderer FrontPlate;
	ModelRenderer SideDecor;
	ModelRenderer midPlate;
	ModelRenderer AmmoBox;
	ModelRenderer rod;
	ModelRenderer radioIntena;
	ModelRenderer Camera;
	ModelRenderer Camera2;
	ModelRenderer Camera3;
	ModelRenderer CannonBody;
	ModelRenderer CannonBody2;
	ModelRenderer CannonBody3;
	ModelRenderer CannonBody4;
	ModelRenderer Barrel;
	ModelRenderer BarrelCap;

	public ModelSentryCannon() {
		textureWidth = 128;
		textureHeight = 128;

		BaseYawR = new ModelRenderer(this, 0, 119);
		BaseYawR.addBox(-3F, -3F, -3F, 6, 3, 6);
		BaseYawR.setRotationPoint(0F, 24F, 0F);
		BaseYawR.setTextureSize(128, 128);
		BaseYawR.mirror = true;
		setRotation(BaseYawR, 0F, 0F, 0F);
		BaseYawRPlate = new ModelRenderer(this, 0, 104);
		BaseYawRPlate.addBox(-5F, -3F, -5F, 10, 4, 10);
		BaseYawRPlate.setRotationPoint(0F, 20F, 0F);
		BaseYawRPlate.setTextureSize(128, 128);
		BaseYawRPlate.mirror = true;
		setRotation(BaseYawRPlate, 0F, 0F, 0F);
		RightBrace = new ModelRenderer(this, 0, 77);
		RightBrace.addBox(-5F, -3F, -3F, 1, 8, 8);
		RightBrace.setRotationPoint(0F, 12F, 0F);
		RightBrace.setTextureSize(128, 128);
		RightBrace.mirror = true;
		setRotation(RightBrace, 0F, 0F, 0F);
		RightBraceF = new ModelRenderer(this, 19, 85);
		RightBraceF.addBox(-5F, -2F, -4F, 1, 7, 1);
		RightBraceF.setRotationPoint(0F, 12F, 0F);
		RightBraceF.setTextureSize(128, 128);
		RightBraceF.mirror = true;
		setRotation(RightBraceF, 0F, 0F, 0F);
		RightBraceF2 = new ModelRenderer(this, 24, 86);
		RightBraceF2.addBox(-5F, -1F, -5F, 1, 6, 1);
		RightBraceF2.setRotationPoint(0F, 12F, 0F);
		RightBraceF2.setTextureSize(128, 128);
		RightBraceF2.mirror = true;
		setRotation(RightBraceF2, 0F, 0F, 0F);
		LeftBrace = new ModelRenderer(this, 41, 77);
		LeftBrace.addBox(4F, -3F, -3F, 1, 8, 8);
		LeftBrace.setRotationPoint(0F, 12F, 0F);
		LeftBrace.setTextureSize(128, 128);
		LeftBrace.mirror = true;
		setRotation(LeftBrace, 0F, 0F, 0F);
		LeftBraceF = new ModelRenderer(this, 35, 85);
		LeftBraceF.addBox(4F, -2F, -4F, 1, 7, 1);
		LeftBraceF.setRotationPoint(0F, 12F, 0F);
		LeftBraceF.setTextureSize(128, 128);
		LeftBraceF.mirror = true;
		setRotation(LeftBraceF, 0F, 0F, 0F);
		LeftBraceF2 = new ModelRenderer(this, 30, 86);
		LeftBraceF2.addBox(4F, -1F, -5F, 1, 6, 1);
		LeftBraceF2.setRotationPoint(0F, 12F, 0F);
		LeftBraceF2.setTextureSize(128, 128);
		LeftBraceF2.mirror = true;
		setRotation(LeftBraceF2, 0F, 0F, 0F);
		FrontPlate = new ModelRenderer(this, 42, 108);
		FrontPlate.addBox(-4F, -2F, -6F, 8, 4, 1);
		FrontPlate.setRotationPoint(0F, 20F, 0F);
		FrontPlate.setTextureSize(128, 128);
		FrontPlate.mirror = true;
		setRotation(FrontPlate, 0F, 0F, 0F);
		SideDecor = new ModelRenderer(this, 61, 92);
		SideDecor.addBox(-6F, -3F, -3F, 1, 4, 7);
		SideDecor.setRotationPoint(0F, 20F, 0F);
		SideDecor.setTextureSize(128, 128);
		SideDecor.mirror = true;
		setRotation(SideDecor, 0F, 0F, 0F);
		midPlate = new ModelRenderer(this, 0, 94);
		midPlate.addBox(-4F, -4F, -4F, 8, 1, 8);
		midPlate.setRotationPoint(0F, 20F, 0F);
		midPlate.setTextureSize(128, 128);
		midPlate.mirror = true;
		setRotation(midPlate, 0F, 0F, 0F);
		AmmoBox = new ModelRenderer(this, 61, 79);
		AmmoBox.addBox(5F, -5F, -3F, 3, 5, 7);
		AmmoBox.setRotationPoint(0F, 20F, 0F);
		AmmoBox.setTextureSize(128, 128);
		AmmoBox.mirror = true;
		setRotation(AmmoBox, 0F, 0F, 0F);
		rod = new ModelRenderer(this, 33, 34);
		rod.addBox(-6F, -1.5F, -1.5F, 12, 3, 3);
		rod.setRotationPoint(0F, 12F, 1F);
		rod.setTextureSize(128, 128);
		rod.mirror = true;
		setRotation(rod, 0F, 0F, 0F);
		radioIntena = new ModelRenderer(this, 66, 43);
		radioIntena.addBox(2F, -8F, 6F, 1, 6, 1);
		radioIntena.setRotationPoint(0F, 12F, 0F);
		radioIntena.setTextureSize(128, 128);
		radioIntena.mirror = true;
		setRotation(radioIntena, 0F, 0F, 0F);
		Camera = new ModelRenderer(this, 41, 16);
		Camera.addBox(-9.5F, -2.5F, -2F, 4, 4, 4);
		Camera.setRotationPoint(0F, 12F, 1F);
		Camera.setTextureSize(128, 128);
		Camera.mirror = true;
		setRotation(Camera, 0F, 0F, 0F);
		Camera2 = new ModelRenderer(this, 41, 11);
		Camera2.addBox(-9F, -1.5F, -2.5F, 3, 2, 1);
		Camera2.setRotationPoint(0F, 12F, 1F);
		Camera2.setTextureSize(128, 128);
		Camera2.mirror = true;
		setRotation(Camera2, 0F, 0F, 0F);
		Camera3 = new ModelRenderer(this, 59, 18);
		Camera3.addBox(-9F, 1.5F, -2F, 3, 1, 4);
		Camera3.setRotationPoint(0F, 12F, 1F);
		Camera3.setTextureSize(128, 128);
		Camera3.mirror = true;
		setRotation(Camera3, 0F, 0F, 0F);
		CannonBody = new ModelRenderer(this, 0, 31);
		CannonBody.addBox(-3.5F, -2.5F, -1.5F, 7, 5, 9);
		CannonBody.setRotationPoint(0F, 12F, 0F);
		CannonBody.setTextureSize(128, 128);
		CannonBody.mirror = true;
		setRotation(CannonBody, 0F, 0F, 0F);
		CannonBody2 = new ModelRenderer(this, 19, 46);
		CannonBody2.addBox(-2.5F, -1.5F, -6.5F, 5, 3, 5);
		CannonBody2.setRotationPoint(0F, 12F, 0F);
		CannonBody2.setTextureSize(128, 128);
		CannonBody2.mirror = true;
		setRotation(CannonBody2, 0F, 0F, 0F);
		CannonBody3 = new ModelRenderer(this, 0, 47);
		CannonBody3.addBox(-2F, -2.1F, -6.5F, 4, 1, 5);
		CannonBody3.setRotationPoint(0F, 12F, 0F);
		CannonBody3.setTextureSize(128, 128);
		CannonBody3.mirror = true;
		setRotation(CannonBody3, 0F, 0F, 0F);
		CannonBody4 = new ModelRenderer(this, 40, 47);
		CannonBody4.addBox(-2F, 1F, -6.5F, 4, 1, 5);
		CannonBody4.setRotationPoint(0F, 12F, 0F);
		CannonBody4.setTextureSize(128, 128);
		CannonBody4.mirror = true;
		setRotation(CannonBody4, 0F, 0F, 0F);
		Barrel = new ModelRenderer(this, 10, 55);
		Barrel.addBox(-1F, -1F, -14.5F, 2, 2, 8);
		Barrel.setRotationPoint(0F, 12F, 0F);
		Barrel.setTextureSize(128, 128);
		Barrel.mirror = true;
		setRotation(Barrel, 0F, 0F, 0F);
		BarrelCap = new ModelRenderer(this, 32, 56);
		BarrelCap.addBox(-1.5F, -1.5F, -16.5F, 3, 3, 2);
		BarrelCap.setRotationPoint(0F, 12F, 0F);
		BarrelCap.setTextureSize(128, 128);
		BarrelCap.mirror = true;
		setRotation(BarrelCap, 0F, 0F, 0F);
	}

	/** Renders the yaw only rotation part */
	public void renderYaw(float f5) {
		BaseYawR.render(f5);
		BaseYawRPlate.render(f5);
		RightBrace.render(f5);
		RightBraceF.render(f5);
		RightBraceF2.render(f5);
		LeftBrace.render(f5);
		LeftBraceF.render(f5);
		LeftBraceF2.render(f5);
		FrontPlate.render(f5);
		SideDecor.render(f5);
		midPlate.render(f5);
		AmmoBox.render(f5);

	}

	/**
	 * Renders the yaw and pitch rotation part
	 * 
	 * @param f5
	 */
	public void renderYawPitch(float f5) {
		rod.render(f5);
		radioIntena.render(f5);
		Camera.render(f5);
		Camera2.render(f5);
		Camera3.render(f5);
		CannonBody.render(f5);
		CannonBody2.render(f5);
		CannonBody3.render(f5);
		CannonBody4.render(f5);
		Barrel.render(f5);
		BarrelCap.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
