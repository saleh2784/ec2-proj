pipeline {
    agent any

    stages {
        stage('Initialize') {
            steps {
                cleanWs()
            }
        }
        stage('Get SCM') {
            steps {
                git "https://github.com/saleh2784/ec2-proj.git"
                sh "cat Jenkinsfile"
            }
        }
        stage('docker clean-old-versions'){
            steps {
                sh "docker ps"
                // sh "docker kill nodewebapp"
                // sh "docker rm nodewebapp"
            }
        }
        stage('docker build'){
            steps {
                sh "docker build -t ec2app . "
                sh "docker images"
            }
        }
        stage('docker Deploy'){
            steps {
                sh "docker run -itd --name ec2app ec2app:latest &"  
            }
        }
    }   
}
