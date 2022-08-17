pipeline {
    agent any
    parameters {
        string defaultValue: '300', name: 'INTERVAL'
    }
    environment {
        CRED = credentials('credentials')
        CONFIG = credentials('config')
    }

    stages {
        stage('Initialize') {
            steps {
                cleanWs()
                sh "docker kill ec2app || true"
                sh "docker rm ec2app || true"
                sh "docker rmi -f ec2app || true"
            }
        }
        stage('Get SCM') {
            steps {
                git "https://github.com/saleh2784/ec2-proj.git"
            }
        }
        stage('docker build'){
            steps {
                sh "docker build -t ec2app . "
                sh "docker images"
            }
        }
        stage('docker Run & Deploy'){
            steps {
                sh "docker run -itd --name ec2app --env INTERVAL=${params.INTERVAL} ec2app:latest &"  
            }
        }
    }   
}

// https://github.com/ranazrad/machineScanner
// credentials

