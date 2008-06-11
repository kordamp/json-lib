#!/bin/sh
VERSION="1.0.2"
GROUPID="net.sf.json-lib"
ARTIFACTID="json-lib-ext-spring"
REPO_URL="scp://ssh.sf.net/home/groups/j/js/json-lib/htdocs/m2/repo/release"
REPOSITORYID="sf_release"

mkdir build
mvn clean javadoc:jar source:jar package
mv target/${ARTIFACTID}-${VERSION}-sources.jar build
mv target/${ARTIFACTID}-${VERSION}-javadoc.jar build
mv target/${ARTIFACTID}-${VERSION}.jar build

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=javadoc \
    -Dfile=build/${ARTIFACTID}-${VERSION}-javadoc.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=sources \
    -Dfile=build/${ARTIFACTID}-${VERSION}-sources.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -Dfile=build/${ARTIFACTID}-${VERSION}.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=pom \
    -DgeneratePom=false \
    -Dfile=pom.xml
