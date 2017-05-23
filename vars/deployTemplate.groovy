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
