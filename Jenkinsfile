pipeline {
    environment {
        QODANA_TOKEN = credentials('qodana-token')
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Checkout source code
                checkout scm
            }
        }
        stage('Run Qodana') {
            steps {
                script {
                    // Ensure you are using a Unix-like shell to run commands
                    def workspacePath = "${env.WORKSPACE}"
                    def qodanaResultsPath = "${env.WORKSPACE}/qodana-results"

                    bat """
                    docker run --rm \
                        -v "${workspacePath}:/data/project" \
                        -v "${qodanaResultsPath}:/data/results" \
                        -e QODANA_TOKEN="${qodana_token}" \
                        jetbrains/qodana-jvm qodana
                    """
                }
            }
        }
    }
}
