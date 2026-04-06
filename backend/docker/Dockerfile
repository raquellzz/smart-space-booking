FROM alpine:latest AS security_provider
RUN addgroup -S nonroot && adduser -S nonroot -G nonroot

FROM eclipse-temurin:25-alpine

COPY --from=security_provider /etc/passwd /etc/passwd
COPY --from=security_provider /etc/group /etc/group

WORKDIR /app

COPY ./target/*.jar /app/app.jar

RUN chown -R nonroot:nonroot /app

USER nonroot

ENV JAVA_OPTS=""

CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]