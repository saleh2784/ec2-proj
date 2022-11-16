pipeline {
    agent any
    parameters {
        string(name: 'INTERVAL', defaultValue: '300' )
        choice choices: [ 'development', 'main',], name: 'branch'
        string(name: 'TAG', defaultValue: '1' )

    }
    environment {
//         CRED = credentials('credentials')
//         CONFIG = credentials('config')
        DOCKER = 'ec2app'
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
        // int TAG = readFile(file: 'tag.txt')
        
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
                sh "docker kill ${DOCKER}:${params.TAG} || true"
                // Removing exited containers 
                sh "docker ps -q -f status=exited | xargs --no-run-if-empty docker rm || true"
                //delete old images 
                sh 'docker image prune -fa || true'


            }
        }
        stage('Get SCM') {
            when {
                expression{
                    branch '${params.branch}'
                }
            }
            steps {
                git branch: "${params.branch}", url: 'https://github.com/saleh2784/ec2-proj.git'
            }
        }
        stage('helm') {
            
			steps {
			   
                // install yq
                // sh (script : """ apt install wget -y""", returnStdout: false)
                // sh (script : """wget https://github.com/mikefarah/yq/releases/latest/download/yq_linux_amd64 -O /usr/bin/yq &&\
                // chmod +x /usr/bin/yq""", returnStdout: false)
			    // need to check the path for the helm ## /home/jenkins/workspace/ec2/helm-lab
                dir('/home/jenkins/workspace/ec2/helm-lab/') {
                sh (script : """ cat values.yaml """)
                // sh (script : """ yq -i 'image.tag' = "${params.TAG}.${BUILD_NUMBER}" values.yaml """, returnStdout: false)
                sh (script : """ sed -i "s@#tag=.*@tag=${params.TAG}.${BUILD_NUMBER}@" values.yaml """, returnStdout: false)             
                }
                // sh (script : """ echo /home/jenkins/workspace/ec2/helm-lab/values.yaml """)
				// sh (script : """ cat ./helm-lab/values.yaml | yq eval -i 'image.tag' = ${params.TAG}.${BUILD_NUMBER}""", returnStdout: false)
                
			}
		}
        stage('docker build'){
            steps {
                // get the .aws credentials & config to use them in the container
                // sh "cat $CRED | tee credentials"
                // sh "cat $CONFIG | tee config"
                
                // build the image from the Dockerfile
                sh (script: "docker build -t ${DOCKER}:${params.TAG} . ", returnStdout: true)
                // sh "docker build -t ${DOCKER}:${TAG} . "

                // view the docker images
                sh "docker images"
            }
        }
        stage('docker Run & Deploy'){
            steps {
                // running the container with the Inteval time and with the build number (the name of the container include the build number)
                sh (script : "docker run -itd --name ${DOCKER} --env INTERVAL=${params.INTERVAL} ${DOCKER}:${params.TAG}", returnStdout: true  )
                // sh "docker run -itd --name saleh2784/${DOCKER}-${env.BUILD_NUMBER}:${tagname} --env INTERVAL=${params.INTERVAL} saleh2784/${DOCKER}-${env.BUILD_NUMBER} &"  

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
                sh """docker tag ${DOCKER}:${params.TAG} saleh2784/${DOCKER}:${params.TAG}.${BUILD_NUMBER}"""
			    // push the image with the Build_number to docker-hub
				sh (script : """docker push saleh2784/${DOCKER}:${params.TAG}.${BUILD_NUMBER}""", returnStdout: false)
                // echo the name of the image that i push to docker-hub 
                echo " my push image name is : ${DOCKER}:${params.TAG}.${BUILD_NUMBER}"
				// to download the image from the dockerhub run this command : "docker pull saleh2784/ec2app:tagname.build_number"
               
			}
		}
        // stage('helm') {
            
		// 	steps {
			   
        //         // install yq
        //         sh (script : """ apt install wget -y""", returnStdout: false)
        //         sh (script : """wget https://github.com/mikefarah/yq/releases/latest/download/yq_linux_amd64 -O /usr/bin/yq &&\
        //         chmod +x /usr/bin/yq""", returnStdout: false)
		// 	    // need to check the path for the helm
		// 		sh (script : """cat values.yaml | yq eval -i 'image.tag' = ${params.TAG}.${BUILD_NUMBER}""", returnStdout: false)
                
		// 	}
		// }
        stage('Git Push to Main'){
        steps{
            script{
                // GIT_CREDS = credentials(<git creds id>)
                sh '''
                    git add .
                    git commit -m "push to git"
                    // git push https://${GIT_CREDS_USR}:${GIT_CREDS_PSW}@bitbucket.org/<your repo>.git <branch>
                    git push https://github.com/saleh2784/ec2-proj.git main
                '''
            }
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
