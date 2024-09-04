pipeline {
    agent any
    environment {
        QODANA_TOKEN = credentials('qodana-token')
        WORKSPACE_PATH = "${env.WORKSPACE}"
        QODANA_RESULTS_PATH = "${env.WORKSPACE}\\qodana-results"
    }
    stages {
        stage('Setup') {
            steps {
                bat 'echo %WORKSPACE_PATH%'
                bat 'echo %QODANA_RESULTS_PATH%'
            }
        }
        stage('Run Qodana') {
            steps {
                bat """
                docker run --rm ^
                    -v "%WORKSPACE_PATH%:/data/project" ^
                    -v "%QODANA_RESULTS_PATH%:/data/results" ^
                    -e QODANA_TOKEN=%QODANA_TOKEN% ^
                    jetbrains/qodana-jvm qodana
                """
            }
        }
    }
}
