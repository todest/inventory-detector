package cn.todest.mcmod.inventorydetector.common.events;

import cn.todest.mcmod.inventorydetector.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Cancelable
public class DetectorEvent {
    public static Map<Item, Integer> ItemCount;
    private static ItemStack[] previous;
    private static ItemStack[] LatestInventory;
    private static boolean isCanceled = false;
    private static boolean isCanceledPermanent = false;

    public static void setCanceled(boolean cancel) {
        isCanceled = cancel;
    }

    public static void setCanceled(boolean cancel, boolean permanent) {
        setCanceled(cancel);
        if (permanent) {
            isCanceledPermanent = cancel;
        }
    }

    public static void syncPrevious() {
        if (LatestInventory == null) {
            previous = null;
            return;
        }
        previous = LatestInventory.clone();
    }

    public ArrayList<ItemStack> checkDiffWithInventory(ItemStack[] pre, ItemStack[] now) {
        ArrayList<Item> preItem = new ArrayList<Item>();
        ArrayList<Item> nowItem = new ArrayList<Item>();
        ArrayList<ItemStack> inventoryItemAddedList = new ArrayList<ItemStack>();
        for (ItemStack itemStack : pre) {
            if (itemStack == null) continue;
            for (int i = 0; i < itemStack.stackSize; i++) {
                preItem.add(itemStack.getItem());
            }
        }
        for (ItemStack itemStack : now) {
            if (itemStack == null) continue;
            for (int i = 0; i < itemStack.stackSize; i++) {
                nowItem.add(itemStack.getItem());
            }
        }
        for (Item item : preItem) {
            nowItem.remove(item);
        }
        for (Item item : nowItem) {
            boolean flag = false;
            for (ItemStack itemStack : inventoryItemAddedList) {
                if (itemStack.getItem().equals(item)) {
                    itemStack.stackSize = itemStack.stackSize + 1;
                    flag = true;
                }
            }
            if (!flag) {
                inventoryItemAddedList.add(new ItemStack(item, 1));
            }
        }
        return inventoryItemAddedList;
    }

    @SubscribeEvent
    public void inventoryChangedEvent(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (isCanceled || isCanceledPermanent || player == null) {
            return;
        }
        LatestInventory = player.inventory.mainInventory.clone();
        if (previous == null) {
            if (ItemCount == null) {
                ItemCount = new HashMap<Item, Integer>();
            }
            syncPrevious();
        } else {
            ArrayList<ItemStack> inventoryChange = checkDiffWithInventory(previous, LatestInventory);
            syncPrevious();
            for (ItemStack itemStack : inventoryChange) {
                Item item = itemStack.getItem();
                if (Utils.isRecordItem("diamond", "")) {
                    ItemCount.put(item,
                            itemStack.stackSize + (ItemCount.get(item) == null ? 0 : ItemCount.get(item))
                    );
                }
            }
        }
    }
}
