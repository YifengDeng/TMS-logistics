FROM java:8
MAINTAINER czp
ADD logicstic_tms-0.0.1-SNAPSHOT.jar tms.jar
RUN sh -c 'touch /tms.jar'
EXPOSE 8081
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /tms.jar" ]", "-c", "java