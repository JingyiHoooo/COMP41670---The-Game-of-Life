package lifegame.utils;

import lifegame.LifeGameApp;
import lifegame.card.*;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.IntStream;

//class with utility methods to enhance presentation/clean up code/avoid unnecessary program exits
public class Utils {

    //separator for information messages
    public static String split = "\n[INFO] ==================================================\n";

    //method to read config.properties file
    public static Properties readConfigProperties(String file) throws IOException {

        Properties properties = new Properties();
        InputStream configFileInputStream = Utils.class.getClassLoader().getResourceAsStream(file);
        if (configFileInputStream != null) {    //if there is no file loaded
            properties.load(configFileInputStream); //load file
        } else {
            throw new FileNotFoundException("config file: " + file + " is not found"); //otherwise notify file not loaded
        }

        return properties;
    }

    public static <T extends Card> List<T> initCardList(Class<T> tClass) throws Exception {
        String file;
        String className = tClass.getSimpleName();
        //get deck names and info
        switch (className) {
            case "HouseCard":
                file = "HouseCard.yml";
                break;
            case "CareerCard":
                file = "CareerCard.yml";
                break;
            case "CollegeCareerCard":
                file = "CollegeCareerCard.yml";
                break;
            case "ActionCard":
                return (List<T>) initActionCardList();
            default:
                System.out.println("[ERROR] Card type is invalid: " + className);
                return new ArrayList<>();
        }

        Constructor<T> constructor = tClass.getConstructor(String.class, int.class, int.class, int.class);

        Yaml yaml = new Yaml();
        Iterable<Object> cardObjects = yaml.load(Utils.class.getClassLoader().getResourceAsStream(file));
        ArrayList<T> list = new ArrayList<>();
        for (Object o : cardObjects) {
            LinkedHashMap map = (LinkedHashMap) o;
            T t = constructor.newInstance(map.values().toArray());
            list.add(t);
        }
        Collections.shuffle(list);
        return list;
    }

    //make decks
    private static List<ActionCard> initActionCardList() {
        List<ActionCard> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new CareerChangeCard());
        }

        for (int i = 0; i < 10; i++) {
            list.add(new PayCard());
        }

        for (int i = 0; i < 20; i++) {
            int amount = ((i % 5) + 1) * 10;
            list.add(new BankCard(amount));
            list.add(new GetCashCard(amount));
        }

        Collections.shuffle(list);
        return list;
    }

    //method for sanitised inputs to ensure program does not exit if an invalid input is entered.
    public static int inputNumChoices(int max) {
        int[] arr = new int[max];

        for(int i = 1; i <= max; i++)
            arr[i - 1] = i;

        return sanitisedInput(arr);
    }

    //method for sanitised inputs to ensure program does not exit if an invalid input is entered.
    public static int sanitisedInput(int... allowable) {
        String input;

        do {
            try {
                input = LifeGameApp.getScanner().next();
            }
            catch (NoSuchElementException ex) {
                input = null;
            }
        }
        while(!inArray(allowable, toInt(input)));

        return toInt(input);
    }

    //method for sanitised inputs to ensure program does not exit if an invalid input is entered.
    private static int toInt(String s) {
        try {
             return  Integer.parseInt(s);
        }
        catch(Exception ex) {
            return -10;
        }
    }

    //method for sanitised inputs to ensure program does not exit if an invalid input is entered.
    private static boolean inArray(int[] array, int toCheck) {
        for(int x : array)
            if(x == toCheck)
                return true;

        System.out.println("[INFO] Choose one of the required inputs: " + Arrays.toString(array));

        return false;
    }

    //just for test
    public static void main(String[] args) throws IOException {
        try {
//            initCardList(HouseCard.class).forEach(System.out::println);
//            initCardList(CareerCard.class).forEach(System.out::println);
            initCardList(CollegeCareerCard.class).forEach(System.out::println);
//            initCardList(ActionCard.class).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
