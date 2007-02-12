#!/bin/sh
VERSION="1.0"
GROUPID="net.sf.json-lib"
ARTIFACTID="json-lib"
REPO_URL="scp://ssh.sf.net/home/groups/j/js/json-lib/htdocs/m2/repo/release"
REPOSITORYID="sf_release"

mkdir build
ant source.jar
mv target/json-lib-${VERSION}-jdk13-sources.jar build
ant source.jar.jdk15
mv target/json-lib-${VERSION}-jdk15-sources.jar build
ant javadoc
mv target/json-lib-${VERSION}-jdk13-javadoc.jar build
ant javadoc.jdk15
mv target/json-lib-${VERSION}-jdk15-javadoc.jar build
ant jar
mv target/json-lib-${VERSION}-jdk13.jar build
ant jar.jdk15
mv target/json-lib-${VERSION}-jdk15.jar build

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=jdk13-javadoc \
    -Dfile=build/json-lib-${VERSION}-jdk13-javadoc.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=jdk15-javadoc \
    -Dfile=build/json-lib-${VERSION}-jdk15-javadoc.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=jdk13-sources \
    -Dfile=build/json-lib-${VERSION}-jdk13-sources.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DgeneratePom=false \
    -Dclassifier=jdk15-sources \
    -Dfile=build/json-lib-${VERSION}-jdk15-sources.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -Dclassifier=jdk15 \
    -Dfile=build/json-lib-${VERSION}-jdk15.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -Dclassifier=jdk13 \
    -Dfile=build/json-lib-${VERSION}-jdk13.jar

mvn deploy:deploy-file -DrepositoryId=${REPOSITORYID} \
    -Durl=${REPO_URL} \
    -DgroupId=${GROUPID} \
    -DartifactId=${ARTIFACTID} \
    -Dversion=${VERSION} \
    -Dpackaging=pom \
    -DgeneratePom=false \
    -Dfile=pom.xml