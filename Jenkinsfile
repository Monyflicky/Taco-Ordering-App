pipeline {
   environment {
      QODANA_TOKEN=credentials('QODANA_TOKEN')
   }
   agent {
      docker {
         args '''
         -v "${WORKSPACE}":/data/project
         --entrypoint=""
         '''
         image 'jetbrains/qodana-jvm'
      }
   }
   stages {
      stage('Qodana') {
         steps {
            sh '''qodana'''
         }
      }
   }
}
