import java.sql.SQLOutput;
import java.util.*;

public class Main {
    final static  String letters = "RLRFR";
    final static int lenght = 100;
    final static int threads = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main (String[] args){
        
        for (int i=0; i< lenght; i++) {
            new Thread(() -> {
                String route = generateRoute (letters, lenght);
                int freq = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq){
                    if(sizeToFreq.containsKey(freq)){
                    sizeToFreq.put(freq,sizeToFreq.get(freq)+1);
                } else{
                 sizeToFreq.put(freq,1);
                }
            }
        }).start();
            
    }

    Map.Entry<Integer,Integer> max = sizeToFreq
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .get();
    System.out.println ("Самое частое количество повторений" + max.getKey() + "(встретилось" + max.getValue() +" раз)");

    System.out.println("Другие размеры: ");
    sizeToFreq
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(e -> System.out.println("-"+ e.getKey() + "(" + e.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}
