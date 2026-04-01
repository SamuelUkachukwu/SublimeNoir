pipeline{
    agent any

    stages{
        stage('GetProject'){
            steps{
                git branch: 'main',
                url: 'https://github.com/SamuelUkachukwu/SublimeNoir.git'
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