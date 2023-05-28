package ess.ert.Lister;

import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dateo {

    public static String getServerData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE"); // Формат дня недели
        Date currentDate = new Date(); // Текущая дата

        return dateFormat.format(currentDate); // Возвращаем день недели в формате строки
    }

    public static String getDayData(boolean isDay) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // День недели

        // Преобразование числа дня недели в соответствующий порядковый номер с добавлением "st", "nd", "rd" или "th"
        String dayNumber;
        switch (dayOfWeek) {
            case 1:
                dayNumber = "Sun,";
                break;
            case 2:
                dayNumber = "пн";
                break;
            case 3:
                dayNumber = "вт";
                break;
            case 4:
                dayNumber = "ср";
                break;
            case 5:
                dayNumber = "чт";
                break;
            case 6:
                dayNumber = "пт";
                break;
            case 7:
                dayNumber = "сб";
                break;
            default:
                dayNumber = "";
                break;
        }

        String emoji;
        if (isDay) {
            emoji = formatText("&e☀&f/&7☽"); // Use a shorter emoji for day
        } else {
            emoji = formatText("&d☽&f/&7☀"); // Use a shorter emoji for night
        }

        return dayNumber + " " + emoji; // Return the day of the week and emoji as a string
    }

    private static String formatText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text.replace("&", "§"));
    }
}