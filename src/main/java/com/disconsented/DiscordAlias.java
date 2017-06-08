package com.disconsented;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import java.util.AbstractMap;

public class DiscordAlias {
    public static void main(String [] args)
    {
        Config config = Config.loadConfig();
        DiscordAPI api = Javacord.getApi(config.getEmail(), config.getPassword());

        api.connect(new FutureCallback<DiscordAPI>() {
            @Override
            public void onSuccess(DiscordAPI result) {
                api.registerListener(new MessageCreateListener() {
                    @Override
                    public void onMessageCreate(DiscordAPI api, Message message) {
                        if(message.getAuthor().isYourself() && message.getContent().startsWith("/")){
                            final String[] split = message.getContent().substring(1).split(" ");
                            for (AbstractMap.SimpleEntry pair:
                                 config.getAlises()) {
                                if(pair.getKey().equals(split[0])){
                                    String editedMessage = String.format(pair.getValue().toString(), split);
                                    System.out.println(editedMessage);
                                    message.edit(editedMessage);
                                    break;
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        System.out.println("yeay");
    }
}
