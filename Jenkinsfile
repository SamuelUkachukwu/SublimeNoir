pipeline{
    agent any

    stages{
        stage('GetProject'){
            steps{
               echo 'Cloning project from Github'
            }
        }
        stage('Build'){
            steps{
                sh 'mvn clean:clean'
                sh 'mvn dependency:copy-dependencies'
                sh 'mvn compiler:compile'
            }
        }
        stage('Exec'){
            steps{
                sh 'mvn exec:java'
            }
        }
    }
}