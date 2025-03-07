FROM openjdk:21-jdk-slim AS runtime

# Criar o usuário para execução da aplicação antes da instalação de pacotes
RUN addgroup --system spring && adduser --system --ingroup spring spring

# Atualização dos repositórios e instalação do Datadog Agent
RUN apt-get update \
  && apt-get install -y ca-certificates wget \
  && update-ca-certificates \
  && rm -rf /var/lib/apt/lists/*  # Remover cache para reduzir a imagem

WORKDIR /app

# Copiar o JAR da aplicação e garantir permissões
COPY target/*.jar app.jar
RUN chown spring:spring /app/app.jar

# Baixar o Datadog Java Agent e garantir permissões
RUN wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer \
  && chown spring:spring /app/dd-java-agent.jar

# Definir o usuário para execução da aplicação
USER spring

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Definir as variáveis de ambiente do Datadog (com valores padrão)
#ENV DD_API_KEY="${DD_API_KEY}" \
#    DD_APP_KEY="${DD_APP_KEY}" \
#    DD_HOSTNAME="${DD_HOSTNAME}" \
#    DD_SITE="${DD_SITE:-datadoghq.com}" \
#    DD_SERVICE="${DD_SERVICE:-my-spring-boot-service}" \
#    DD_ENV="${DD_ENV:-production}" \
#    DD_VERSION="${DD_VERSION:-1.0}" \
#    DD_TRACE_ENABLED="${DD_TRACE_ENABLED:-true}" \
#    DD_LOGS_ENABLED="${DD_LOGS_ENABLED:-true}" \
#    DD_TRACE_ANALYTICS_ENABLED="${DD_TRACE_ANALYTICS_ENABLED:-true}"

# Definir o comando de execução incluindo o Datadog Java Agent
ENTRYPOINT ["java", "-Xms128m", "-Xmx512m", \
     "-XX:+PrintFlagsFinal", "-XX:+UseZGC", \
     "-XX:SoftMaxHeapSize=400m", "-XX:+UseStringDeduplication", \
     "-XX:+AlwaysPreTouch", \
     "-javaagent:/app/dd-java-agent.jar", \
     "-jar", "/app/app.jar"]
