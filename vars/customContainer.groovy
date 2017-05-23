#!/usr/bin/groovy
def call(String name, Closure body) {
        withEnv(["CONTAINER_NAME=$name"]) {
                body()
        }
}
