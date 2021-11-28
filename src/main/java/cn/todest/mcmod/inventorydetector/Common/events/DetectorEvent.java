package cn.todest.mcmod.inventorydetector.common.events;

import cn.todest.mcmod.inventorydetector.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Cancelable
public class DetectorEvent {
    public static Map<ItemStack, Integer> ItemCount;
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

    public ArrayList<ItemStack> removeDuplicate(ArrayList<ItemStack> pre, ArrayList<ItemStack> now) {
        for (ItemStack itemStack : pre) {
            Iterator<ItemStack> iterator = now.iterator();
            while (iterator.hasNext()) {
                ItemStack item = iterator.next();
                if (itemStack.getItem().equals(item.getItem()) &&
                        itemStack.getDisplayName().equals(item.getDisplayName())
                ) {
                    iterator.remove();
                    break;
                }
            }
        }
        return now;
    }

    public ArrayList<ItemStack> checkDiffWithInventory(ItemStack[] pre, ItemStack[] now) {
        ArrayList<ItemStack> preItem = new ArrayList<ItemStack>();
        ArrayList<ItemStack> nowItem = new ArrayList<ItemStack>();
        ArrayList<ItemStack> inventoryItemAddedList = new ArrayList<ItemStack>();
        for (ItemStack itemStack : pre) {
            if (itemStack == null) continue;
            ItemStack tempItem = itemStack.copy();
            tempItem.stackSize = 1;
            for (int i = 0; i < itemStack.stackSize; i++) {
                preItem.add(tempItem);
            }
        }
        for (ItemStack itemStack : now) {
            if (itemStack == null) continue;
            ItemStack tempItem = itemStack.copy();
            tempItem.stackSize = 1;
            for (int i = 0; i < itemStack.stackSize; i++) {
                nowItem.add(tempItem);
            }
        }
        nowItem = removeDuplicate(preItem, nowItem);
        for (ItemStack item : nowItem) {
            boolean flag = false;
            for (ItemStack itemStack : inventoryItemAddedList) {
                if (itemStack.getItem().equals(item.getItem()) && itemStack.getDisplayName().equals(item.getDisplayName())) {
                    itemStack.stackSize = itemStack.stackSize + 1;
                    flag = true;
                }
            }
            if (!flag) {
                inventoryItemAddedList.add(item);
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
        LatestInventory = player.inventory.mainInventory;
        if (previous == null) {
            if (ItemCount == null) {
                ItemCount = new HashMap<ItemStack, Integer>();
            }
            syncPrevious();
        } else {
//            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            ArrayList<ItemStack> inventoryChange = checkDiffWithInventory(previous, LatestInventory);
            syncPrevious();
            for (ItemStack itemStack : inventoryChange) {
                if (Utils.isRecordItem(itemStack.getItem().delegate.name(), itemStack.getDisplayName())) {
                    boolean isExist = false;
                    for (ItemStack key : ItemCount.keySet()) {
                        if (key.getItem().delegate.name().equals(itemStack.getItem().delegate.name())
                                && key.getDisplayName().equals(itemStack.getDisplayName())
                        ) {
                            ItemCount.put(key, itemStack.stackSize +
                                    (ItemCount.get(key) == null ? 0 : ItemCount.get(key))
                            );
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        ItemCount.put(itemStack, itemStack.stackSize +
                                (ItemCount.get(itemStack) == null ? 0 : ItemCount.get(itemStack))
                        );
                    }
                }
            }
        }
    }
}
