package littleMaidMobX.gui;

import java.util.Iterator;
import java.util.List;

import littleMaidMobX.LittleMaidMobX;
import littleMaidMobX.aimodes.TriggerSelect;
import littleMaidMobX.util.helper.ClientHelper;
import littleMaidMobX.util.helper.Helper;
import littleMaidMobX.inventory.ContainerTriggerSelect;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiTriggerSelect extends GuiContainer {

	protected float scrolleWeaponset;
	protected float scrolleContainer;
	private static InventoryBasic inventory1 = new InventoryBasic("tmpsel", false, 40);
	private static InventoryBasic inventory2 = new InventoryBasic("tmpwep", false, 32);
	private GuiTextField searchField;
	private int lastX;
	private int lastY;
	private boolean ismousePress;
	private int isScrolled;
	public GuiIFF owner;
	private GuiButton[] guiButton = new GuiButton[3];
	private ContainerTriggerSelect inventoryTrigger;
	private boolean field_147057_D;
	private int selectPage;
	protected EntityPlayer target;
	protected static final ResourceLocation fguiTex =
			new ResourceLocation(LittleMaidMobX.DOMAIN, "textures/gui/container/littlemaidtrigger.png");


	public GuiTriggerSelect(EntityPlayer entityplayer, GuiIFF guiowner) {
		super(new ContainerTriggerSelect(entityplayer));
		ySize = 216;
		owner = guiowner;
		inventoryTrigger = (ContainerTriggerSelect) inventorySlots;
		target = entityplayer;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		guiButton[0] = new GuiButton(100, guiLeft + 7, guiTop + 193, 20, 20, "<");
		guiButton[1] = new GuiButton(101, guiLeft + 35, guiTop + 193, 106, 20, TriggerSelect.selector.get(0));
		guiButton[2] = new GuiButton(102, guiLeft + 149, guiTop + 193, 20, 20, ">");
		
		searchField = new GuiTextField(fontRendererObj, guiLeft + 81, guiTop + 6, 89, fontRendererObj.FONT_HEIGHT);
		searchField.setMaxStringLength(15);
        searchField.setEnableBackgroundDrawing(false);
        searchField.setVisible(true);
        searchField.setTextColor(16777215);
        searchField.setCanLoseFocus(false);
        searchField.setFocused(true);
        searchField.setText("");
        searchField.width = 89;
        searchField.xPosition = this.guiLeft + (83 /*default left*/ + 89 /*default width*/) - this.searchField.width;
        updateTriggerSearch();
        
		buttonList.add(guiButton[0]);
		buttonList.add(guiButton[1]);
		buttonList.add(guiButton[2]);
		guiButton[1].enabled = false;
		selectPage = 0;
	}

	@Override
	protected void keyTyped(char c, int i)
	{
		if (i == 1)
		{
			mc.displayGuiScreen(owner);
		}
		else
        {
            if (this.field_147057_D)
            {
                this.field_147057_D = false;
                this.searchField.setText("");
            }

            if (!this.checkHotbarKeys(i))
            {
                if (this.searchField.textboxKeyTyped(c, i))
                {
                    this.updateTriggerSearch();
                }
                else
                {
                    super.keyTyped(c, i);
                }
            }
        }
	}

	@Override
	public void onGuiClosed()
	{
		setItemList();
		super.onGuiClosed();
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		setItemList();
		if (guibutton.id == 100) {
			if (--selectPage < 0) {
				selectPage = TriggerSelect.selector.size() - 1;
			}
		}
		if (guibutton.id == 101) {
			// Sword Select
		}
		if (guibutton.id == 102) {
			if (++selectPage >= TriggerSelect.selector.size()) {
				selectPage = 0;
			}
		}
		String ls = TriggerSelect.selector.get(selectPage);
		guiButton[1].displayString = ls;
		inventoryTrigger.setWeaponSelect(Helper.getPlayerName(target), ls);
		inventoryTrigger.setWeaponlist(0.0F);
	}

	@Override
	protected void handleMouseClick(Slot slot, int i, int j, int flag) {
		field_147057_D = true;
		boolean var5 = flag == 1;
		flag = i == -999 && flag == 0 ? 4 : flag;
		if (slot != null) {
			if (slot.inventory == inventory1 && flag == 0) {
				InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
				ItemStack itemstack1 = inventoryplayer.getItemStack();
				ItemStack itemstack4 = slot.getStack();
				if (itemstack1 != null && itemstack4 != null
						&& itemstack1.getItem() == itemstack4.getItem()) {
					
					if (j != 0) {
						inventoryplayer.setItemStack(null);
					}
				} else if (itemstack1 != null) {
					inventoryplayer.setItemStack(null);
				} else if (itemstack4 == null) {
					inventoryplayer.setItemStack(null);
				} else {
					inventoryplayer.setItemStack(ItemStack.copyItemStack(itemstack4));
				}
			}
			else
			{
				inventorySlots.slotClick(slot.slotNumber, j, flag, mc.thePlayer);
				ItemStack itemstack = inventorySlots.getSlot(slot.slotNumber).getStack();
//				mc.playerController.sendSlotPacket(itemstack,
//								(slot.slotNumber - inventorySlots.inventorySlots.size()) + 9 + 36);
			}
		}
		else
		{
			InventoryPlayer inventoryplayer1 = mc.thePlayer.inventory;
			inventoryplayer1.setItemStack(null);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		mc.fontRenderer.drawString("Item selection", 8, 6, 0x404040);
		mc.fontRenderer.drawString("Trigger Items", 8, 110, 0x404040);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (lastY < height / 2) {
				int j = (inventoryTrigger.itemList.size() / 8 - 5) + 1;
				if (i > 0) {
					i = 1;
				}
				if (i < 0) {
					i = -1;
				}
				scrolleContainer -= (double) i / (double) j;
				if (scrolleContainer < 0.0F) {
					scrolleContainer = 0.0F;
				}
				if (scrolleContainer > 1.0F) {
					scrolleContainer = 1.0F;
				}
				inventoryTrigger.scrollTo(scrolleContainer);
			} else {
				int j = (inventoryTrigger.weaponSelect.size() / 8 - 4) + 1;
				if (i > 0) {
					i = 1;
				}
				if (i < 0) {
					i = -1;
				}
				if (j > 0) {
					scrolleWeaponset -= (double) i / (double) j;
				} else {
					scrolleWeaponset = 0.0F;
				}
				if (scrolleWeaponset < 0.0F) {
					scrolleWeaponset = 0.0F;
				}
				if (scrolleWeaponset > 1.0F) {
					scrolleWeaponset = 1.0F;
				}
				inventoryTrigger.setWeaponlist(scrolleWeaponset);
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		lastX = i;
		lastY = j;
		boolean flag = Mouse.isButtonDown(0);
		int k = guiLeft;
		int l = guiTop;
		int i1 = k + 155;
		int j1 = l + 17;
		int k1 = i1 + 14;
		int l1 = j1 + 90;
		if (!flag) {
			isScrolled = 0;
		}
		if (!ismousePress && flag && i >= i1 && j >= j1 && i < k1 && j < l1) {
			isScrolled = 1;
		}
		if (isScrolled == 1) {
			scrolleContainer = (float) (j - (j1 + 8)) / ((float) (l1 - j1) - 16F);
			if (scrolleContainer < 0.0F) {
				scrolleContainer = 0.0F;
			}
			if (scrolleContainer > 1.0F) {
				scrolleContainer = 1.0F;
			}
			inventoryTrigger.scrollTo(scrolleContainer);
		}
		j1 = l + 120;
		l1 = j1 + 72;
		if (!ismousePress && flag && i >= i1 && j >= j1 && i < k1 && j < l1) {
			isScrolled = 2;
		}
		if (isScrolled == 2) {
			scrolleWeaponset = (float) (j - (j1 + 8)) / ((float) (l1 - j1) - 16F);
			if (scrolleWeaponset < 0.0F) {
				scrolleWeaponset = 0.0F;
			}
			if (scrolleWeaponset > 1.0F) {
				scrolleWeaponset = 1.0F;
			}
			inventoryTrigger.setWeaponlist(scrolleWeaponset);
		}
		ismousePress = flag;
		super.drawScreen(i, j, f);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(2896 /* GL_LIGHTING */);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ClientHelper.setTexture(fguiTex);
		int l = guiLeft;
		int i1 = guiTop;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		int j1 = l + 155;
		int k1 = i1 + 17;
		int l1 = k1 + 88 + 2;
		// scrolleWeaponset = 1.0F;
		// scrolleContainer = 0.5F;
		drawTexturedModalRect(l + 154,
				i1 + 17 + (int) ((float) (l1 - k1 - 17) * scrolleContainer),
				176, 0, 16, 16);
		drawTexturedModalRect(l + 154,
				i1 + 120 + (int) ((float) (l1 - k1 - 35) * scrolleWeaponset),
				176, 0, 16, 16);
		searchField.drawTextBox();
	}

	private void setItemList() {
		List list1 = inventoryTrigger.getItemList();
		list1.clear();
		for (int i = 0; i < inventoryTrigger.weaponSelect.size(); i++) {
			ItemStack is = inventoryTrigger.weaponSelect.get(i);
			if (is != null && !list1.contains(is.getItem())) {
				list1.add(is.getItem());
			}
		}
	}

	public static InventoryBasic getInventory1() {
		return inventory1;
	}

	public static InventoryBasic getInventory2() {
		return inventory2;
	}
	
	 private void updateTriggerSearch()
	 {
	     inventoryTrigger.itemList.clear();

	     Iterator iterator = Item.itemRegistry.iterator();

	     while (iterator.hasNext())
	     {
	    	 Item item = (Item)iterator.next();
	    	 if (item != null)
	    	 {
	    		 item.getSubItems(item, (CreativeTabs)null, inventoryTrigger.itemList);
	    	 }
	     }
	    updateFilteredItems(inventoryTrigger);
	}
	 
	private void updateFilteredItems(ContainerTriggerSelect containerTrigger)
	{
		Iterator iterator;
	    Enchantment[] aenchantment = Enchantment.enchantmentsList;
	    int j = aenchantment.length;

	    iterator = containerTrigger.itemList.iterator();
	    String s1 = this.searchField.getText().toLowerCase();

	        while (iterator.hasNext())
	        {
	            ItemStack itemstack = (ItemStack)iterator.next();
	            boolean flag = false;
	            Iterator iterator1 = itemstack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();

	            while (true)
	            {
	                if (iterator1.hasNext())
	                {
	                    String s = (String)iterator1.next();

	                    if (!s.toLowerCase().contains(s1))
	                    {
	                        continue;
	                    }

	                    flag = true;
	                }

	                if (!flag)
	                {
	                    iterator.remove();
	                }

	                break;
	            }
	        }

	        scrolleWeaponset = 0.0F;
	        containerTrigger.scrollTo(0.0F);
	    }

}
