#!/usr/bin/groovy
def customContainer(String name, Closure body) {
        withEnv(["CONTAINER_NAME=$name"]) {
                body()
        }
}
