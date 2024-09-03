pipeline {
    environment {
        QODANA_TOKEN = credentials('qodana-token')
    }
    agent any
    stages {
        stage('Qodana') {
            steps {
                script {
                    // Adjust the paths to use Windows-style absolute paths
                    def workspacePath = bat(script: 'cd /d %WORKSPACE%', returnStdout: true).trim()
                    def qodanaResultsPath = "${workspacePath}/qodana-results"

                    sh """
                    docker run --rm \
                      -v "${workspacePath}:/data/project" \
                      -v "${qodanaResultsPath}:/data/results" \
                      -e QODANA_TOKEN="${QODANA_TOKEN}" \
                      jetbrains/qodana-jvm qodana
                    """
                }
            }
        }
    }
}
