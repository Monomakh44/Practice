package ru.home.chatweather;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatWeather extends TelegramLongPollingBot
{
    SendMessage sendMessage = new SendMessage();
    Model model = new Model();
    String mess = "";
    int i = 0;
    public  static List<String> arr = new ArrayList<>();

    @Override
    public String getBotUsername() {return "ChatWeather";}

    @Override
    public String getBotToken() {
        return "5449167577:AAGO49ToDzcpiL5g-QCNW1ojrPNQIIAg2Us";
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.enableMarkdown(true);
        Message message = update.getMessage();
        String message_text = update.getMessage().getText();
        String chat_id = String.valueOf(update.getMessage().getChatId());
        if (update.hasMessage() && update.getMessage().hasText())
        {
             if (weather(message_text) && !message_text.equals("Подписаться"))
            {
                sendMessage.setChatId(chat_id);
                try
                {
                    sendMessage.setText(Weather.getWeather(message.getText(), model));
                } catch (IOException e)
                {
                    e.printStackTrace();
                    sendMessage.setText("Город не найден");
                }
                try
                {
                    execute(sendMessage);
                } catch (TelegramApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
            if (!message_text.equals("Подписаться") && weather(message_text))
            {
                mess = message_text;
            }
            if (message_text.equals("Подписаться") && weather(message_text))
            {
                for (int j = 0; j < arr.size(); )
                {
                    if (Objects.equals(mess, arr.get(j)))
                    {
                        mess = null;
                        sendMessage.setText("Вы уже подписаны на этот город");
                        try
                        {
                            execute(sendMessage);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    } else j++;
                }
                if (mess != null)
                {
                        arr.add(i, mess);
                        sendMessage.setText("Вы подписаны на город " + mess);
                        try
                        {
                            execute(sendMessage);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                        i++;
                }
            }
        }

    private boolean weather (String message_text){
        Pattern pattern = Pattern.compile("[а-яА-Я]{1}");
        Matcher matcher = pattern.matcher(message_text);
        if (matcher.find()) return true;
        return false;
    }
}
