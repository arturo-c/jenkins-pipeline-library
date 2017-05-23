#!/usr/bin/groovy
def call(command) {
        if (env.CONTAINER_NAME) {
                if ((command instanceof String) || (command instanceof GString)) {
                        command = kubectl(command)
                }

                if (command instanceof LinkedHashMap) {
                        command["script"] = kubectl(command["script"])
                }
        }

        sh(command)
}

def kubectl(command) {
        "kubectl exec -i ${env.HOSTNAME} -c ${env.CONTAINER_NAME} -- /bin/sh -c 'cd ${env.WORKSPACE} && DOCKER_API_VERSION=1.23 ${command}'"
}
