pipeline {
    agent any
    environment {
        QODANA_TOKEN = credentials('qodana-token')
    }
    stages {
        stage('Qodana') {
            steps {
                script {
                    // Use Windows-style path with double backslashes for Docker
                    def workspacePath = bat(script: 'cd /d %WORKSPACE% && echo %CD%', returnStdout: true).trim()
                    def qodanaResultsPath = "${workspacePath}\\qodana-results"

                    // Run Docker with updated syntax
                    bat """
                    docker run --rm ^
                      -v "${workspacePath}:/data/project" ^
                      -v "${qodanaResultsPath}:/data/results" ^
                      -e QODANA_TOKEN="${QODANA_TOKEN}" ^
                      jetbrains/qodana-jvm qodana
                    """
                }
            }
        }
    }
}
