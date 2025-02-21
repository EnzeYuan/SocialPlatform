#基础镜像
FROM openjdk:17
MAINTAINER enzo
WORKDIR /usr/local/java
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV TZ=Asia/Shanghai
EXPOSE 8080
ARG JAR_FILE
ADD ${JAR_FILE} ./socialPlatform-0.0.3-SNAPSHOT.jar

ENTRYPOINT ["java","-Dspring.config.location=/usr/local/java/application.yml;/usr/local/java/application.properties","-jar","/usr/local/java/socialPlatform-0.0.3-SNAPSHOT.jar"]