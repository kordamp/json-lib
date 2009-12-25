#!/bin/bash -e
VERSION=$(show-pom-version pom.xml)
GROUPID="org.kohsuke.stapler"
ARTIFACTID="json-lib"
REPO_URL="java-net:/maven2-repository/trunk/repository/"
REPOSITORYID="java.net"

mkdir build || true
ant clean source.jar.jdk5
mv target/${ARTIFACTID}-${VERSION}-jdk15-sources.jar build
ant javadoc.jdk5
mv target/${ARTIFACTID}-${VERSION}-jdk15-javadoc.jar build
ant jar.jdk5
mv target/${ARTIFACTID}-${VERSION}-jdk15.jar build

cmd=deploy:deploy-file
#cmd=install:install-file

mvn $cmd -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=javadoc \
    -Dfile=build/${ARTIFACTID}-${VERSION}-jdk15-javadoc.jar

mvn $cmd -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=sources \
    -Dfile=build/${ARTIFACTID}-${VERSION}-jdk15-sources.jar

mvn $cmd -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -Dfile=build/${ARTIFACTID}-${VERSION}-jdk15.jar

mvn $cmd -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=pom \
    -DgeneratePom=false \
    -Dfile=pom.xml
