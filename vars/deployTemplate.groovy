#!/usr/bin/groovy
def call(body) {
	podTemplate(cloud: 'kubernetes', label: 'deploy', containers: [
            containerTemplate(name: 'dockers', image: 'docker:17.03', command: 'cat', ttyEnabled: true),
            containerTemplate(name: 'jnlp', image: 'artkon/jnlp-slave-docker:2.62', args: '${computer.jnlpmac} ${computer.name}'),
            containerTemplate(name: 'helm', image: 'lachlanevenson/k8s-helm', command: 'cat', ttyEnabled: true)],
            volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')]) {
        node('deploy') {
            body()
        }
    }
}

def ksh(command) {
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

def customContainer(String name, Closure body) {
	withEnv(["CONTAINER_NAME=$name"]) {
		body()
	}
}
