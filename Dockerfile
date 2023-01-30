FROM azul/zulu-openjdk:17-latest


WORKDIR /app

ARG JAR_FILE

COPY /target/${JAR_FILE} /app/api.jar
COPY wait-for-it.sh /wait-for-it.sh
EXPOSE 8080

RUN chmod +x /wait-for-it.sh
CMD ["java", "-jar", "api.jar"]