FROM dockerfile/java:oracle-java8

ADD kusokora.jar /opt/kusokora/
EXPOSE 8080
WORKDIR /opt/kusokora/
CMD ["java", "-Xms512m", "-Xmx1g", "-jar", "kusokora.jar"]