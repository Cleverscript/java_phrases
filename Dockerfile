FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY ./Phrase.java /app/Phrase.java
RUN javac Phrase.java
CMD ["java", "Phrase"]