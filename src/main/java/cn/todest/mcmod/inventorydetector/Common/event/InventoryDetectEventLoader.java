package cn.todest.mcmod.inventorydetector.Common.event;

import cn.todest.mcmod.inventorydetector.Common.config.Config;
import javafx.util.Pair;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Cancelable
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class InventoryDetectEventLoader {
    public static Map<Item, Integer> ItemCount;
    private static boolean isCanceled = false;
    private static NonNullList<ItemStack> previous;
    private static NonNullList<ItemStack> LatestInventory;

    public static void setCanceled(boolean cancel) {
        isCanceled = cancel;
    }

    public static void syncPrevious() {
        previous = NonNullList.create();
        previous.addAll(LatestInventory);
    }

    public NonNullList<ItemStack> checkDiffWithInventory(NonNullList<ItemStack> pre, NonNullList<ItemStack> now) {
        ArrayList<Pair<Item, Integer>> preItem = new ArrayList<>();
        ArrayList<Pair<Item, Integer>> nowItem = new ArrayList<>();
        NonNullList<ItemStack> inventoryItemAddedList = NonNullList.create();
        for (ItemStack itemStack : pre) {
            for (int j = 0; j < itemStack.getCount(); j++) {
                preItem.add(new Pair<>(itemStack.getItem(), j));
            }
        }
        for (ItemStack itemStack : now) {
            for (int j = 0; j < itemStack.getCount(); j++) {
                nowItem.add(new Pair<>(itemStack.getItem(), j));
            }
        }
        nowItem.removeAll(preItem);
        for (Pair<Item, Integer> pair : nowItem) {
            boolean flag = false;
            for (ItemStack itemStack : inventoryItemAddedList) {
                if (itemStack.getItem().equals(pair.getKey())) {
                    itemStack.setCount(itemStack.getCount() + 1);
                    flag = true;
                }
            }
            if (!flag) {
                inventoryItemAddedList.add(new ItemStack(pair.getKey(), 1));
            }
        }
        return inventoryItemAddedList;
    }

    @SubscribeEvent
    public void inventoryChangedEvent(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (isCanceled || player == null) {
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
