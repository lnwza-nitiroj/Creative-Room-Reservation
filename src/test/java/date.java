import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

  public abstract class date {
      
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date());
    }
    
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return sdf.format(new Date());
    }
    
    public static String getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(new Date());
    }
    
    public static String getCurrentyear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }
    
    public static String getCurrentDayofyear() {
        SimpleDateFormat sdf = new SimpleDateFormat("DDD");
        return sdf.format(new Date());
    }
}