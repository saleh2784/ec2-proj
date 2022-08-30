pipeline {
    agent any
    parameters {
        string(name: 'INTERVAL', defaultValue: '300' )
    }
    environment {
        CRED = credentials('credentials')
        CONFIG = credentials('config')
        DOCKER = 'ec2app'
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
        int TAG = readFile(file: 'tag.txt')
        
    }

    stages {
        stage('Print Build Number') {
            steps {
                script {
                    echo "my Build number is : ${BUILD_NUMBER}"
                }
            }
        }
 
        stage('Initialize') {
            steps {
                cleanWs()
                // kill old containers !!!
                sh "docker kill ${DOCKER}:${TAG} || true"
                // Removing exited containers 
                sh "docker ps -q -f status=exited | xargs --no-run-if-empty docker rm || true"
                //delete old images unused 
                sh 'docker image prune -fa || true'

            }
        }
        stage('Get SCM') {
            steps {
                git branch: 'main', url: 'https://github.com/saleh2784/ec2-proj.git'
            }
        }
        stage('read file config') {
           steps {
               script {
                // read the tag version from the tag.txt
                   def tag  = readFile(file: 'tag.txt')
                   println(tag)
               }
           }
       }
        stage('docker build'){
            steps {
                // get the .aws credentials & config to use them in the container
                sh "cat $CRED | tee credentials"
                sh "cat $CONFIG | tee config"
                
                // build the image from the Dockerfile
                sh "docker build -t ${DOCKER}:${TAG} . "

                // view the docker images
                sh "docker images"
            }
        }
        stage('docker Run & Deploy'){
            steps {
                // running the container with the Inteval time and with the build number (the name of the container include the build number)
                sh "docker run -itd --name ${DOCKER} --env INTERVAL=${params.INTERVAL} ${DOCKER}:${TAG}" 
                // sh "docker run -itd --name saleh2784/${DOCKER}-${env.BUILD_NUMBER}:${tagname} --env INTERVAL=${params.INTERVAL} saleh2784/${DOCKER}-${env.BUILD_NUMBER} &"  

            }
        }
        post {
            always {
                echo 'One way or another, I have finished'
                sh "docker ${DOCKER}:${TAG} logs"
            success {
                echo 'I succeeded!'
            }
            unstable {
                echo 'I am unstable :/'
            }
            failure {
                echo 'I failed :('
            }
            changed {
                echo 'Things were different before...'
                }
            }
        }

        stage('DockerHub Login') {

			steps {
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin '
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin  ' 
			}
		}

		stage('Push the image to DockerHub') {

			steps {
			    echo "${DOCKER}:${TAG}.${BUILD_NUMBER}"
                // docker tag from the local repo
                sh 'docker tag ${DOCKER}:${TAG} saleh2784/${DOCKER}:${TAG}.${BUILD_NUMBER}'
			    // push the image with the Build_number to docker-hub
				sh 'docker push saleh2784/${DOCKER}:${TAG}.${BUILD_NUMBER}'
                // echo the name of the image that i push to docker-hub 
                echo " my bushe image name is : ${DOCKER}:${TAG}.${BUILD_NUMBER}"
				// to download the image from the dockerhub run this command : "docker pull saleh2784/ec2app:tagname.build_number"
               
			}
		}
    }
	post {
        always {
            // docker logout 
		    sh 'docker logout'
		}
    }
}

// used for reference:  https://github.com/ranazrad/machineScanner