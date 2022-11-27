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

How to run the ArgoCd: 
## link for help : https://argo-cd.readthedocs.io/en/stable/getting_started/
## install argocd : 
# Create a new namespace:
kubectl apply -f namespace.yaml
# Install argocd
kubectl apply -f argocd.yaml
# OR 
# Create a new namespace:
kubectl create namespace argocd
# Install argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

# Change the argocd services to type loadbalancer using kubectl PATCH
kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'
# Port Forwarding:
kubectl port-forward svc/argocd-server -n argocd 8080:443
# to get the initial password (user is: admin )
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo
# you can change the default password via the commands below : 
argocd login <ARGOCD_SERVER>
argocd account update-password

# Create a GitHub webhook, on the settings page of the current GitHub repository, as instructed below:
https://argo-cd.readthedocs.io/en/stable/operator-manual/webhook/#1-create-the-webhook-in-the-git-provider
Note: Use your ArgoCD load-balancer DNS name and add /api/webhook


# Login to the ArgoCD server: :

https://[argocd_load-balancer_DNS_name]

# Create a Helm repository:

From the ArgoCD UI, Select Settings -> Connect Repo
In Choose your connection method: Select VIA HTTPS
Type: git
Project: default
Repository URL: Specify your GIT repository : https://github.com/saleh2784/ec2-proj.git
Username : optional
Password : optional
Select "Skip server verification"
Click Connect yuo will get "Connection Status" = "Successful"


# Create an Application in Argo CD Defined By a Helm Chart:

From the ArgoCD Main :
Select Applications
Click "New App"
Application Name: ec2-app
Project Name: default
Repository URL: https://github.com/saleh2784/ec2-proj.git
Revision: HEAD
Path: helm-lab
Cluster URL: Select the default value
Namespace: argocd
Click Create
Click Sync -> click on Synchronize



