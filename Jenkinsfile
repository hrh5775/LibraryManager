pipeline {
    agent any 

    stages {
        stage('Build') { 
            steps { 
                sh 'mvn clean -DskipTests=true package -U' 
            }
        }
        stage('Test'){
            steps {
                sh 'echo ""'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo "No docker, no cry ;)"'
            }
        }
    }
    post {
        always {
            archive '**/target/*.jar'
            archive '**/target/*.war'
            archive '**/target/*.ear'
        }
    }
}

/*
                sh 'mvn test -DskipTests=false'
                junit '**/target/surefire-reports/*.xml'
*/
