#!/usr/bin/groovy
def call(Map parameters = [:], body) {
    def dockerImage = parameters.get('dockerImage', 'docker:1.11')
      podTemplate(label: 'docker',
            containers: [[name: 'docker', image: "${dockerImage}", command: '/bin/sh -c', args: 'cat', ttyEnabled: true, workingDir: '/home/jenkins/', envVars: [[key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]]],
            volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock'), secretVolume(secretName: 'jenkins-docker-cfg', mountPath: '/home/jenkins/.docker')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock'], [key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]) {

            body()

    }
}
