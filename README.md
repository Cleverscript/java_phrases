# Build простейшего docker image с JAVA приложением

Данное приложение рандомно выводит в консоль фразы из набора.

---

Создаем директорию  с проектом 
```bash
mkdir ~/java_phrases && cd java_phrases
```

Создаем в ней файл 
```bash
touch Phrase.java
```

Помещаем в него код на java
```java
import java.util.List;
import java.util.Random;

public class Phrase {
    private static final List<String> PHRASES = List.of(
        "The jorney of a thousad miles begins with a single step.",
        "The early bird catches the worm.",
        "Never give up on your dreams.",
        "A positive atitude can change everything.",
        "Your potential is limites."
    );

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        while (true) {
            String randomPhrase = PHRASES.get(RANDOM.nextInt(PHRASES.size()));
            System.out.println(randomPhrase);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
```
Первым с чего начинается Dockerfile является директива *FROM* которая указывает на основании какого образа будет создаваться наш образ. Это OS которая будет в нашем образе, например можно указать самую легковесную Linux => alpine

Но т.к. мы собираем в примере приложение на JAVA, то нам нужен JDK, а его образ в свою очередь, уже содержит OS. 

По этому достаточно просто указать образ JDK нужной нам версии и все!
```config
FROM openjdk:17-jdk-slim-buster
```

Далее нужно указать рабочую директорию которая будет создана в образе, и в которой будут выполнятся все команды внутри контейнера при его запуске
```config
WORKDIR /app
```

Затем нужно указать какой исполняемый файл приложения, следует скопировать с хост машины в образ и в какую директорию
```config
COPY ./Phrase.java /app/Phrase.java
```

И важный момент, указать какие консольные команды должен выполнить docker при запуске контейнера из нашего образа, в рабочей директории (т.е. /app)
```config
RUN javac Phrase.java
CMD ["java", "Phrase"]
```

Итоговый файл:
```config
FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY ./Phrase.java /app/Phrase.java
RUN javac Phrase.java
CMD ["java", "Phrase"]
```

Создаем образ (образ с именем javaphrases, а "." в конце команды это путь к Dockerfile который находится в той же директории где мы выполняем команду)
```bash
docker build -t javaphrases .
```

Далее образу присваиваем тег, по которому можно будет легко найти образ в dockerHub
```bash
docker tag javaphrases toorr2p/javaphrases
```
и теперь можем выгрузить образ на dockerHub
```bash
docker push toorr2p/javaphrases
```
после чего образ можно будет найти скачать и поднять контейнер
```bash
docker search toorr2p/javaphrases
```