pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from repo...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building...'
                sh './gradlew clean shadowJar'
            }
        }
        
        stage('Archive') {
            steps {
                echo 'Archiving artifacts...'
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            }
        }
    }
}
        
