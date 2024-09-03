pipeline {
    environment {
        QODANA_TOKEN = credentials('qodana-token')
    }
    agent {
        docker {
            image 'jetbrains/qodana-jvm'
            args '-v /data/project:/data/project'
        }
    }
    stages {
        stage('Qodana') {
            steps {
                script {
                    // Running Qodana within the Docker container
                    bat 'qodana'
                }
            }
        }
    }
}
