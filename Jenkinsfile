def IMAGE
pipeline {
    agent any
    stages {

        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Tag image') {
              steps {
               script {
                                      GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
                                      MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
                                      MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
                                      PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
                                      IMAGE="${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION}"
                                  }
                sh "docker build -t arthurelul/hello-img:${IMAGE} ."
              }
        }
               stage('Push image') {
              steps {
              sh "docker login docker.io -u arthurelul -p prodeng100"
              sh "docker push arthurelul/hello-img:${IMAGE}"
              }
        }

    }

}