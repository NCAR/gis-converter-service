FROM maven:3-jdk-8-alpine AS maven

LABEL buildstage=true

ARG BUILDDIR=/usr/local/build

COPY src ${BUILDDIR}/src/
COPY pom.xml ${BUILDDIR}/
COPY maven-settings.xml ${BUILDDIR}/

WORKDIR ${BUILDDIR}

RUN mvn --settings maven-settings.xml --quiet package

FROM tomcat:8-jre8-alpine AS tomcat

ARG BUILDDIR=/usr/local/build
ARG TOMCATDIR=/usr/local/tomcat
ARG HOMEDIR=/usr/local/gis-converter-home

RUN rm -rf ${TOMCATDIR}/webapps/ROOT \
           ${TOMCATDIR}/webapps/docs \
           ${TOMCATDIR}/webapps/examples \
           ${TOMCATDIR}/webapps/host-manager \
           ${TOMCATDIR}/webapps/manager 

COPY --from=maven ${BUILDDIR}/target/converter-service.war ${TOMCATDIR}/webapps/ROOT.war

RUN mkdir -p ${HOMEDIR}/conf \
             ${HOMEDIR}/projection

COPY services.properties ${HOMEDIR}/conf/

VOLUME ["${TOMCATDIR}/temp", "${HOMEDIR}/scratch", "${HOMEDIR}/data"]

ENV JAVA_OPTS="-Dapplication.home=${HOMEDIR}"

