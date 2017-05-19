#!/usr/bin/groovy
def call(body) {
podTemplate(label: 'docker',
        containers: [containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true)]) {
    body()
}
}
