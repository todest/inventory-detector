package cn.todest.mcmod.inventorydetector.Common.event;

import cn.todest.mcmod.inventorydetector.Common.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Cancelable
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class InventoryDetectEventLoader {
    public static Map<Item, Integer> ItemCount;
    private static NonNullList<ItemStack> previous;
    private static NonNullList<ItemStack> LatestInventory;
    private static boolean isCanceled = false;
    private static boolean isCanceledPermanent = false;

    public static void setCanceled(boolean cancel, boolean permanent) {
        isCanceled = cancel;
        if (permanent) {
            isCanceledPermanent = cancel;
        }
    }

    public static void syncPrevious() {
        previous = NonNullList.create();
        previous.addAll(LatestInventory);
    }

    public static void Init() {
        previous = null;
        LatestInventory = NonNullList.create();
    }

    public NonNullList<ItemStack> checkDiffWithInventory(NonNullList<ItemStack> pre, NonNullList<ItemStack> now) {
        NonNullList<Item> preItem = NonNullList.create();
        NonNullList<Item> nowItem = NonNullList.create();
        NonNullList<ItemStack> inventoryItemAddedList = NonNullList.create();
        for (ItemStack itemStack : pre) {
            for (int i = 0; i < itemStack.getCount(); i++) {
                preItem.add(itemStack.getItem());
            }
        }
        for (ItemStack itemStack : now) {
            for (int i = 0; i < itemStack.getCount(); i++) {
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
                    itemStack.setCount(itemStack.getCount() + 1);
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
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (isCanceled || isCanceledPermanent || player == null) {
            System.out.println(isCanceled);
            return;
        }
        LatestInventory = player.inventory.mainInventory;
        if (previous == null) {
            if (ItemCount == null) {
                ItemCount = new HashMap<>();
            }
            Config.bake();
            syncPrevious();
        } else {
            NonNullList<ItemStack> inventoryChange = checkDiffWithInventory(previous, LatestInventory);
            syncPrevious();
            for (ItemStack itemStack : inventoryChange) {
                Item item = itemStack.getItem();
                if (Config.RecordedItems.get().contains(item.toString())) {
                    ItemCount.put(item, itemStack.getCount() + (ItemCount.get(item) == null ? 0 : ItemCount.get(item)));
                    Config.save();
                }
            }
        }
    }
}
