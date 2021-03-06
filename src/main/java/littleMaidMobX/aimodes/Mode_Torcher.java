package littleMaidMobX.aimodes;

import littleMaidMobX.entity.EntityLittleMaid;
import littleMaidMobX.sound.EnumSound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Mode_Torcher extends ModeBase {
	
	public static final int mmode_Torcher = 0x0020;


	public Mode_Torcher(EntityLittleMaid pEntity) {
		super(pEntity);
		TriggerSelect.appendTriggerItem(null, "Torch", "");
	}

	@Override
	public int priority() {
		return 6200;
	}

	@Override
	public void init() {
		

	}

	@Override
	public void addEntityMode(EntityAITasks pDefaultMove, EntityAITasks pDefaultTargeting) {
		// Torcher:0x0020
		EntityAITasks[] ltasks = new EntityAITasks[2];
		ltasks[0] = pDefaultMove;
		ltasks[1] = pDefaultTargeting;
		
		owner.addMaidMode(ltasks, "Torcher", mmode_Torcher);
	}

	@Override
	public boolean changeMode(EntityPlayer pentityplayer) {
		ItemStack litemstack = owner.maidInventory.getStackInSlot(0);
		if (litemstack != null) {
			if (litemstack.getItem() == Item.getItemFromBlock(Blocks.torch) || TriggerSelect.checkItem(owner.getMaidMaster(), "Torch", litemstack)) {
				owner.setMaidMode("Torcher");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setMode(int pMode) {
		switch (pMode) {
		case mmode_Torcher :
			owner.setBloodsuck(false);
			owner.aiAttack.setEnable(false);
			owner.aiShooting.setEnable(false);
			return true;
		}
		
		return false;
	}

	@Override
	public int getNextEquipItem(int pMode) {
		int li;
		ItemStack litemstack;
		
		switch (pMode)
		{
		case mmode_Torcher : 
			for (li = 0; li < owner.maidInventory.maxInventorySize; li++)
			{
				litemstack = owner.maidInventory.getStackInSlot(li);
				if (litemstack == null) continue;
				
				
				if (litemstack.getItem() == Item.getItemFromBlock(Blocks.torch) || TriggerSelect.checkItem(owner.getMaidMaster(), "Torch", litemstack))
				{
					return li;
				}
			}
			break;
		}
		return -1;
	}

	@Override
	public boolean checkItemStack(ItemStack pItemStack)
	{
		String ls = owner.getMaidMaster();
		return (pItemStack.getItem() == Items.sugar
				|| pItemStack.getItem() == Item.getItemFromBlock(Blocks.torch)
				|| TriggerSelect.checkItem(ls, "Torch", pItemStack)
				|| TriggerSelect.checkItem(ls, "Pickup", pItemStack));
	}

	@Override
	public boolean isSearchBlock()
	{
		return true;
	}

	@Override
	public boolean shouldBlock(int pMode) {
		return owner.getCurrentEquippedItem() != null;
	}

	protected int getBlockLighting(int i, int j, int k) {
		World worldObj = owner.worldObj;
		if (worldObj.getBlock(i, j - 1, k).getMaterial().isSolid() && worldObj.getBlock(i, j, k) == Blocks.air) {
			return worldObj.getBlockLightValue(i, j, k);
		}
		return 32;
	}

	@Override
	public boolean checkBlock(int pMode, int px, int py, int pz)
	{
		if (owner.getHeldItem() == null)
		{
			return false;
		}
		if (owner.isFreedom() && owner.getHomePosition().getDistanceSquared(px, py, pz) > ModeBase.limitDistance_Freedom) {
			return false;
		}
		if (!owner.isFreedom() && owner.getMaidMasterEntity()!=null &&
				owner.getMaidMasterEntity().getDistanceSq(px, py, pz) > ModeBase.limitDistance_Follow) {
			return false;
		}
		// アイテムを置けない場合
		Item heldItem = owner.getHeldItem().getItem();
		if (heldItem instanceof ItemBlock) {
			if (!canPlaceItemBlockOnSide(owner.worldObj, px, py - 1, pz, 1, owner.maidAvatar, owner.getHeldItem(), (ItemBlock) heldItem)) {
				return false;
			}
		}

		int v = getBlockLighting(px, py, pz);
		if (v < 8 && canBlockBeSeen(px, py - 1, pz, true, true, true) && !owner.isMaidWait()) {
			if (owner.getNavigator().tryMoveToXYZ(px, py, pz, 1.0F) ) {
				//owner.playLittleMaidSound(LMM_EnumSound.findTarget_D, true);
				return true;
			}
		}
		return false;
		/*int v = getBlockLighting(px, py, pz);
		if (v < 8 && canBlockBeSeen(px, py - 1, pz, true, true, false))
		{
			if (owner.getNavigator().tryMoveToXYZ(px, py, pz, 1.0F) )
			{
				owner.playSound(EnumSound.findTarget_D, false);
				return true;
			}
		}
		return false;*/
	}
	
	@Override
	public boolean executeBlock(int pMode, int px, int py, int pz)
	{
		ItemStack lis = owner.getCurrentEquippedItem();
		if (lis == null) return false;

		if(lis.getItem()!=Item.getItemFromBlock(Blocks.torch)) return false;

		int li = lis.stackSize;
		// TODO:当たり判定をどうするか
		if (lis.getItem().onItemUse(lis, owner.maidAvatar, owner.worldObj, px, py - 1, pz, 1, 0.5F, 1.0F, 0.5F))
		{
			owner.setSwing(10, EnumSound.installation);
			//owner.addMaidExperience(0.32f);
			if (owner.maidAvatar.capabilities.isCreativeMode) {
				lis.stackSize = li;
			}
			if (lis.stackSize <= 0) {
				owner.maidInventory.setInventoryCurrentSlotContents(null);
				owner.getNextEquipItem();
			}
		}
		return false;
	}

	public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack, ItemBlock pItemBlock)
	{
		Block theBlock = par1World.getBlock(par2, par3, par4);
		
		if (Block.isEqualTo(theBlock, Blocks.snow)) {
			par5 = 1;
		} else if (!Block.isEqualTo(theBlock, Blocks.vine) && !Block.isEqualTo(theBlock, Blocks.tallgrass) &&
				!Block.isEqualTo(theBlock, Blocks.deadbush)) {
			if (par5 == 0) {
				--par3;
			}
			if (par5 == 1) {
				++par3;
			}
			if (par5 == 2) {
				--par4;
			}
			if (par5 == 3) {
				++par4;
			}
			if (par5 == 4) {
				--par2;
			}
			if (par5 == 5) {
				++par2;
			}
		}
		Material lmat = par1World.getBlock(par2, par3, par4).getMaterial();
		if (lmat instanceof MaterialLiquid) {
			return false;
		}
		
		return par1World.canPlaceEntityOnSide(Block.getBlockFromItem(pItemBlock), par2, par3, par4, false, par5, (Entity)null, par7ItemStack);
	}

	/*@Override
	public void updateAITick(int pMode)
	{
		if (pMode == mmode_Torcher && owner.getNextEquipItem())
		{
			ItemStack lis = owner.getCurrentEquippedItem();
			int lic = lis.stackSize;
			Item lii = lis.getItem();
			World lworld = owner.worldObj;
			
			
			int lxx = MathHelper.floor_double(owner.posX);
			int lyy = MathHelper.floor_double(owner.posY);
			int lzz = MathHelper.floor_double(owner.posZ);
			int lym = MathHelper.floor_float(owner.height) + 1;
//			mod_LMM_littleMaidMob.Debug("torch-s: %d, %d, %d", lxx, lyy, lzz);
			int ll = 8;
			int ltx = lxx, lty = lyy, ltz = lzz;
			int lil[] = {lyy, lyy - 1, lyy + 1};
			owner.maidAvatar.getValue();
			for (int x = -1; x < 2; x++) {
				for (int z = -1; z < 2; z++) {
					for (int lyi : lil) {
						int lv = lworld.getBlockLightValue(lxx + x, lyi, lzz + z);
						if (ll > lv && lii instanceof ItemBlock &&
								canPlaceItemBlockOnSide(lworld, lxx + x, lyi - 1, lzz + z, 1, owner.maidAvatar, lis, (ItemBlock)lii)
								&& canBlockBeSeen(lxx + x, lyi - 1, lzz + z, true, false, true)) {
//						if (ll > lv && lworld.getBlockMaterial(lxx + x, lyi - 1, lzz + z).isSolid()
//								&& (lworld.getBlockMaterial(lxx + x, lyi, lzz + z) == Material.air
//								|| lworld.getBlockId(lxx + x, lyi, lzz + z) == Block.snow.blockID)
//								&& canBlockBeSeen(lxx + x, lyi - 1, lzz + z, true, false, true)) {
							ll = lv;
							ltx = lxx + x;
							lty = lyi - 1;
							ltz = lzz + z;
//							mod_LMM_littleMaidMob.Debug("torch: %d, %d, %d: %d", ltx, lty, ltz, lv);
						}
					}
				}
			}
			
			if (ll < 8 && lis.tryPlaceItemIntoWorld(owner.maidAvatar, owner.worldObj, ltx, lty, ltz, 1, 0.5F, 1.0F, 0.5F)) {
//				mod_LMM_littleMaidMob.Debug("torch-inst: %d, %d, %d: %d", ltx, lty, ltz, ll);
				owner.setSwing(10, EnumSound.installation);
				owner.getNavigator().clearPathEntity();
				if (owner.maidAvatar.capabilities.isCreativeMode) {
					lis.stackSize = lic;
				}
				if (lis.stackSize <= 0) {
					owner.maidInventory.setInventoryCurrentSlotContents(null);
					owner.getNextEquipItem();
				}
			}

		}
	}*/

}
