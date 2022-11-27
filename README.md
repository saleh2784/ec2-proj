# Manage Jenkins :
install these Plugins in jenkins :
1. Pipeline: Stage View
2. Pipeline Utility Steps
3. Workspace Cleanup

# Manage Credentials and environment in jenkins = 

1. config file for ec2 (AWS)
2. credentials file for ec2 (AWS)
3. dockerhube credentials : DOCKERHUB_CREDENTIALS = credentials('docker-hub')
4. Github credentials : GIT_AUTH = credentials('github')
5. Docker name : DOCKER = 'ec2app'

# How to run the Pipeline: 

1. go to jenkins 
2. create new jop (name :"ec2-app") & (choose Pipeline)
3. choose Pipeline script fro SCM
4. In SCM choose Git
5. put this repo in the Repository URL = https://github.com/saleh2784/ec2-proj.git
6. in branch put : */development
7. in the script patch = Jenkinsfile
8. in Build Triggers mark "GitHub hook trigger for GITScm polling" 
9. Run the pipeline with parameters
10. Enter the INTERVAL "defualt 300"
11. Choose the branch ('main', 'development', 'PROD', 'saleh') "defualt development"
12. Enter the tag "defualt 1"
13. Run the pipeline


# How to run the ArgoCd: 
&& link for help : https://argo-cd.readthedocs.io/en/stable/getting_started/
# install argocd from my repo files : ## 
Create a new namespace:
kubectl apply -f namespace.yaml
Install argocd
kubectl apply -f argocd.yaml

# OR fro the link below:

Create a new namespace:
kubectl create namespace argocd
Install argocd vial the link:
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

Change the argocd services to type loadbalancer using kubectl PATCH
kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'

Port Forwarding:
kubectl port-forward svc/argocd-server -n argocd 8080:443

to get the initial password (user is: admin )
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo

Create a GitHub webhook, on the settings page of the current GitHub repository:

create-the-webhook-in-the-git-provider : https://github.com/saleh2784/ec2-proj.git



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



