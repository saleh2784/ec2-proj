# ec2-proj
# create ec2.py code 
# create Dcokerfile 
# create pipeline Declarative
# put the pipeline as Jenkinsfile in github
# github wehbooks 
# branch (DEV / PROD) --- The above setup should allow you to run two Jenkins servers (DEV / PROD) where you can tests , upgrade and update your Jenkins dev env before you merge your changes into MAIN
# $JENKINSBUILDNUMBER --- -	Use jenkins to run your build on every push under branch development. Mark every image created with the number of the $JENKINSBUILDNUMBER. For example:yanivomc/k8stest:JENKINSBUILDNUMBER
# Create a manual step that depends on all build steps before it allows you to deploy your application to your local DOCKER Engine (by running docker stop , pull , run ) 

Manage Jenkins :
1. Manage Plugins :
"Install these plugins"
a. Pipeline: Stage View
b. Pipeline Utility Steps
c. Workspace Cleanup

2. Manage Credentials and environment = 

a. config file for ec2 (AWS)
b. credentials file for ec2 (AWS)
c. dockerhube credentials : DOCKERHUB_CREDENTIALS = credentials('docker-hub')
d. Github credentials : GIT_AUTH = credentials('github')
e. Docker name : DOCKER = 'ec2app'

How to run the Pipeline: 

1. go to jenkins 
2. create new jop "ec2-app" & chose Pipeline
3. choose Pipeline script fro SCM
4. In SCM choose Git
5. put this repo in the Repository URL = https://github.com/saleh2784/ec2-proj.git
6. in branch put : */main
7. in the script patch = Jenkinsfile
8. in Build Triggers mark "GitHub hook trigger for GITScm polling" 
9. Run the pipeline with parameters
10. Enter the INTERVAL "defualt 300"
11. Choose the branch ('main', 'DEV', 'PROD', 'saleh') "defualt development"
12. Enter the tag "defualt 1"
13. Run the pipeline

14. install argocd :
a. kubectl apply -f namespace.yaml
b. kubectl apply -f argocd.yaml
# Change the argocd services to type loadbalancer using kubectl PATCH
c. run: kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'
# Port Forwarding:
d. run : kubectl port-forward svc/argocd-server -n argocd 8080:443
# to get the initial password (user is: admin )
e. run: kubectl get pods -n argocd -l app.kubernetes.io/name=argocd-server -o name | cut -d'/' -f f. run : kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo
15. 