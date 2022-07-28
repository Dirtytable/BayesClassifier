import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collector;

public class BayesClassifier {
    //Документы
    private ArrayList<String> documents;
    //Тип докомуента
    private ArrayList<String> typesOfDocuments;
    //Тип которые есть в классификаторе
    private ArrayList<String> types;
    //Количество типов которые есть в классификаторе
    private ArrayList<Integer> countOfTypes;
    //Слова
    private ArrayList<String> words;
    //Количество каждого слова
    private ArrayList<Integer> countOfWords;
    //Количество слов для каждого типа
    private ArrayList<ArrayList<Integer>> countOfWordsInTypes;
    //Апостериорная вероятности слов для каждого типа
    private ArrayList<ArrayList<Double>> valueOfWordsInTypes;
    //Конструктор для заполнения
    BayesClassifier(){
        documents = new ArrayList<>();
        typesOfDocuments = new ArrayList<>();
        types = new ArrayList<>();
        countOfTypes =new ArrayList<>();
        words = new ArrayList<>();
        countOfWords = new ArrayList<>();
        countOfWordsInTypes = new ArrayList<>();
        valueOfWordsInTypes = new ArrayList<>();
    }
    //Первый этап - добавление документа и его тип
    public void addDocument(String document, String type){
        documents.add(document);
        typesOfDocuments.add(type);
        //Подсчет количества типов
        countTypes(type);
    }
    //Подсчет количества типов
    public void countTypes(String type){
        if (!types.contains(type)){
            types.add(type);
            countOfTypes.add(1);
        }
        else {
            countOfTypes.set(types.indexOf(type), countOfTypes.get(types.indexOf(type))+1);
        }
    }
    /*Второй этап - натренировать классификатор
    * надо:
    * 1) разделить документ на слова;
    * 2) посчитать апостериорная вероятность каждого типа при данном слове
    * */
    public void train(){
        // Делим документы на слова
        splitDocuments();
        //Теперь у меня есть лист со всеми словами и его количеством
        //Счет апостериорная вероятность каждого типа.
        for (String type : types) {
            ArrayList<Double> wordInTypes = new ArrayList<>();
            //Посчитать P(c) априорная вероятность данного типа.
            double pc = countPc(type);
            //Посчитать P(c|x)  апостериорная вероятность
            for (String word : words) {
                //Посчитать P(x|c) правдоподобие данного слова для каждого типа.
                double pxc = countPxc(word, type);
                //Посчитать P(x) – априорнау вероятность данного слова.
                double px = countPx(word);
                //System.out.println("Px" + px);
                //System.out.println(px + " " + pxc + " " + pc);
                wordInTypes.add(((pxc * pc) / px));
            }
            //У меня есть вес определенного слова для каждого типа
            valueOfWordsInTypes.add(wordInTypes);
        }

    }
    //Разделитель документа на слова и подсчет каждого слова и слова в типе.
    public void splitDocuments() {
        for (int i = 0; i < documents.size(); i++) {
            String[] splitedDocument = documents.get(i).split(" ");
            for (int j = 0; j < splitedDocument.length; j++) {
                splitedDocument[j] = splitedDocument[j].toLowerCase();
            }
            for (String word : splitedDocument) {
                if (!words.contains(word)) {
                    ArrayList<Integer> wordInTypes = new ArrayList<>();
                    words.add(word);
                    countOfWords.add(1);
                    for (String type : types) {
                        if (!typesOfDocuments.get(i).equals(type)) {
                            wordInTypes.add(0);
                        } else {
                            wordInTypes.add(1);
                        }
                    }
                    countOfWordsInTypes.add(wordInTypes);
                } else {
                    countOfWords.set(words.indexOf(word), countOfWords.get(words.indexOf(word)) + 1);
                    //Здесь я считаю количество данного слова в каждом типе
                    //Я хочу вызвать слово countOfWordsInTypes.get(words.indexOf(word))
                    //Я обновляю количество этого слова в определенном типе set(types.indexOf(typesOfDocuments.get(i)), countOfWordsInTypes.get(words.indexOf(word)).get(types.indexOf(typesOfDocuments.get(i)))+1)
                    countOfWordsInTypes.get(words.indexOf(word)).set(types.indexOf(typesOfDocuments.get(i)), countOfWordsInTypes.get(words.indexOf(word)).get(types.indexOf(typesOfDocuments.get(i)))+1);
                }

            }
        }
    }
    //Подсчет априорной вероятности P(c) данного типа
    public double countPc(String type) {
        return (double)countOfTypes.get(types.indexOf(type)) / (double)documents.size();
    }
    //Подсчет правдоподобие данного слова в данном типе
    public double countPxc(String word, String type){
        return  (double)countOfWordsInTypes.get(words.indexOf(word)).get(types.indexOf(type)) / (double)countOfTypes.get(types.indexOf(type));
    }
    //Посчитать P(x) – априорную вероятность данного слова.
    public double countPx(String word){
        return (double)countOfWords.get(words.indexOf(word)) / (double)documents.size();
    }
    //Третий этап - самостоятельная классификация
    public void classify(String document){
        //У меня есть лист где буду храниится вес документа для каждого типа
        ArrayList<Double> valuesOfTypes = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            valuesOfTypes.add(0.0);
        }
        String[] wordsOfDocument = document.split(" ");
        for (int i = 0; i < wordsOfDocument.length; i++) {
            wordsOfDocument[i] = wordsOfDocument[i].toLowerCase();
        }
        //Прохожу по каждому слову
        for (String word : document.split(" ")) {
            //Здесь он должен считать вес данного слова для каждого типа.
            if (words.contains(word)) {
                //System.out.println(word);
                //Данное слово есть в листе и надо добвить его вес для каждого типа
               //System.out.println("Слово = " + word);
                for (int j = 0; j < types.size(); j++) {
                 //   System.out.println("Тип = " +types.get(j) + " вес=" + valueOfWordsInTypes.get(j).get(words.indexOf(word)));
                    valuesOfTypes.set(j, valuesOfTypes.get(j) + valueOfWordsInTypes.get(j).get(words.indexOf(word)));
                }
            }
        }
        //Даст максимальное значение Collections.max(valuesOfTypes)
        //Даст тиндекс типа с самым максимальным значением valuesOfTypes.indexOf(Collections.max(valuesOfTypes))
        //Дас назаввание типа с самым максимальным значением types.get(valuesOfTypes.indexOf(Collections.max(valuesOfTypes)))
        // return types.get(valuesOfTypes.indexOf(Collections.max(valuesOfTypes)));
        for (int i = 0; i < valuesOfTypes.size(); i++) {
            System.out.println("Вес типа " + types.get(i) + " = " + valuesOfTypes.get(i));
        }
        String typeOfDocument = types.get(valuesOfTypes.indexOf(Collections.max(valuesOfTypes)));
        System.out.println("Этот документ " + typeOfDocument);
    }
}