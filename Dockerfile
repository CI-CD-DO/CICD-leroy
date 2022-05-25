FROM maven:3.8.5-openjdk-18

COPY . .
RUN mvn -N io.takari:maven:wrapper
RUN mvn package -Dnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=docker
WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
COPY --chown=1001:root target/*-runner /work/application
ENV LISTEN="0.0.0.0"
EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=${LISTEN}"]

