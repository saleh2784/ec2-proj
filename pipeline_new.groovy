pipeline {
    agent any
    parameters {
        choice(name: 'branch', choices: ['development', 'master'], description: 'Select the branch to build')
        string(name: 'TAG', defaultValue: '1', description: 'Build tag or version number')
        string(name: 'HOST', defaultValue: '10.50.10.5')
        string(name: 'USER_NAME', defaultValue: 'root')
        string(name: 'DOCKER', defaultValue: 'ec2app', description: 'Docker image name')
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
    }
    
    stages {
        stage('Print Build Number') {
            steps {
                script {
                    echo "My Build Number is: ${BUILD_NUMBER}"
                }
            }
        }
        
        stage('Get SCM') {
            when {
                expression {
                    params.branch == 'development' || params.branch == 'master'
                }
            }
            steps {
                git branch: "${params.branch}", url: 'https://github.com/saleh2784/ec2-proj.git'
            }
        }
        stage('Initialize') {
            steps {
                cleanWs()
                sshagent(['ssh-key']) {
                    // Run a shell command on the remote host
                    sh """
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'docker ps'
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'docker images'
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'docker ps -q -f status=exited | xargs --no-run-if-empty docker rm || true'
                        
                    """
                }

            }
        }
        stage('git-clone'){
            steps {
                sshagent(['ssh-key']) {
                    // Run a shell command on the remote host
                    sh """
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'git clone https://github.com/saleh2784/ec2-proj.git || true'
                    """
                }
            }
        }
        stage('docker build'){
            steps {
                sshagent(['ssh-key']) {
                    // Run a shell command on the remote host
                    sh """
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'docker build -t ${params.DOCKER}:${params.TAG} ./ec2-proj || exit 1' // Use params.DOCKER
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'docker images'
                    """
                }
            }
        }
        stage('DockerHub Login') {
			steps {
			    sshagent(['ssh-key']) {
                    // Run a shell command on the remote host
                    sh """
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    """
			    }
		    }
        }
        stage('Docker push') {
			steps {
			    sshagent(['ssh-key']) {
                    // Run a shell command on the remote host
                    sh """
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'docker tag ${params.DOCKER}:${params.TAG} saleh2784/${params.DOCKER}:${params.TAG}.${BUILD_NUMBER}'
                        ssh -o StrictHostKeyChecking=no ${params.USER_NAME}@${params.HOST} 'docker push saleh2784/${params.DOCKER}:${params.TAG}.${BUILD_NUMBER}'
                    """
                    echo " my push image name is : ${DOCKER}:${params.TAG}.${BUILD_NUMBER}"
			    }
		    }
        }
    }
}