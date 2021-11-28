package cn.todest.mcmod.inventorydetector.common.events;

import cn.todest.mcmod.inventorydetector.common.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatEvent {
    private static int StartCount;
    private static int StopCount;

    public ChatEvent() {
        StartCount = 0;
        StopCount = 0;
    }

    @SubscribeEvent
    public void dungeonEvent(ClientChatReceivedEvent event) {
        if (!DetectorEvent.isAutomatic) {
            return;
        }
        String messages = event.message.getFormattedText();
        for (String string : Utils.DungeonStartMessages) {
            if (messages.contains(string)) {
                StartCount++;
            }
        }
        for (String string : Utils.DungeonStopMessages) {
            if (messages.contains(string)) {
                StopCount++;
            }
        }
        if (StartCount >= 3) {
            DetectorEvent.isCanceledPermanent = false;
            StartCount = 0;
        }
        if (StopCount >= 3) {
            DetectorEvent.isCanceledPermanent = true;
            StopCount = 0;
        }
    }
}
