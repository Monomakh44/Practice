package ru.home.chatweather;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Inject;
import java.io.IOException;

@Service
@Component
@EnableScheduling
public class Sheduler
{
    @Inject
    private ChatWeather chatWeather;
    @Scheduled(cron = " 0 22 11 * * *")
    public void computePrice()
    {
        String chat_id = String.valueOf("1022153589");
        chatWeather.sendMessage.setChatId("1022153589");
        String city = "";
        for (int j = 0; j < ChatWeather.arr.size(); j++)
        {
            if (ChatWeather.arr.get(j) != null)
            {
                city = ChatWeather.arr.get(j);
                try
                {
                    chatWeather.sendMessage.setText(Weather.getWeather(city, chatWeather.model));
                    chatWeather.execute(chatWeather.sendMessage);
                }catch(IOException e)
                {
                    e.printStackTrace();
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
