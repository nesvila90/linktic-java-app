FROM openjdk:11-jre-slim
ENV APP_FILE bootstrap.jar
ENV APP_HOME /
#EXPOSE 9090:8080
COPY $APP_FILE $APP_HOME/
WORKDIR $APP_HOME
#ENTRYPOINT ["sh", "-c"]
ENV JAVA_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.rmi.port=9010 -Djava.rmi.server.hostname=0.0.0.0 -Dcom.sun.management.jmxremote.ssl=false"
CMD ["java", "-jar", "bootstrap.jar"]
