import kotlin.ranges.IntRange;

import javax.management.Query;
import java.util.*;
import java.util.concurrent.DelayQueue;

public class Main {
    public static void main(String[] args) {
        HashMap<String, String[]> contacts = new HashMap<>();
        contacts.put("Асет", new String[]{"Максим", "Едыль", "Жора", "Ильяс"});
        contacts.put("Максим", new String[]{"Абоба", "Асет", "Ансар"});
        contacts.put("Едыль", new String[]{"Ансар"});
        contacts.put("Жора", new String[]{"Ясос"});
        contacts.put("Ильяс", new String[]{"Ясос"});
        contacts.put("Абоба", new String[]{"Продавец манго"});
        contacts.put("Ансар", new String[]{"Дархан"});
        contacts.put("Дархан", new String[]{"Ясос"});
        contacts.put("Ясос", new String[]{"Дархан"});
        search("Асет", contacts);
    }
    public static boolean search(String name, HashMap<String, String[]> contacts){
        ArrayDeque<String> search_names = new ArrayDeque<>(Arrays.asList(contacts.get(name)));
        List<String> searched = new ArrayList<>();
        while (!search_names.isEmpty()){
            String person = search_names.removeFirst();
            if (!searched.contains(person)){
                if (person.equals("Продавец манго")){
                    System.out.println("Продавец был найден");
                    return true;
                }
                else{
                    search_names.addAll(Arrays.asList(contacts.get(person)));
                    searched.add(person);
                }
            }

        }
        return false;
    }




}
