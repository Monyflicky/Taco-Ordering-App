pipeline {
    agent any
    environment {
        QODANA_TOKEN = credentials('qodana-token')
    }
    stages {
        stage('Qodana') {
            steps {
                script {
                    // Define paths
                    def workspacePath = bat(script: 'cd /d %WORKSPACE% && echo %CD%', returnStdout: true).trim()
                    def qodanaResultsPath = "${workspacePath}\\qodana-results"

                    // Run Docker command in separate steps to avoid syntax issues
                    bat """
                    docker run --rm ^
                      -v "${workspacePath}:C:/data/project" ^
                      -v "${qodanaResultsPath}:C:/data/results" ^
                      -e QODANA_TOKEN="${qodana_token}" ^
                      jetbrains/qodana-jvm qodana
                    """
                }
            }
        }
    }
}
