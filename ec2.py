import boto3
import os
import time


def get_online_instance():
    ec2 = boto3.resource('ec2')
    for instance in ec2.instances.all():
        kube_machine = False
    for tag in instance.tags:
        if tag['Value'] == "1" and tag['Key'] == "k8s.io/role/master" and instance.state["Code"] == 16:
            # print("the instance id is :", instance.id)
            # print("the status for the instance is : ", instance.state["Name"])
            kube_machine = True

    if kube_machine == True:
        for tag in instance.tags:
            if tag['Key'] == "Name":
                print("the name for the instance is : ", tag['Value'])
                print("the instance id is :", instance.id)
                # print("{0} {1}".format(tag['Value'], instance.id))


interval = os.environ.get('INTERVAL')
if interval == None:
    get_online_instance()
else:
    while True:
        get_online_instance()
        print("Sleep for {0} seconds".format(interval))
        time.sleep(int(interval))
get_online_instance()

# import boto3
#
# ec2 = boto3.resource('ec2')
#
# for instance in ec2.instances.all():
#     kube_machine = False
#     for tag in instance.tags:
#         if tag['Value'] == "1" and tag['Key'] == "k8s.io/role/master" and instance.state["Code"] == 16:
#             # print("the instance id is :", instance.id)
#             # print("the status for the instance is : ", instance.state["Name"])
#             kube_machine = True
#
#     if kube_machine == True:
#         for tag in instance.tags:
#             if tag['Key'] == "Name":
#                 print("the name for the instance is : ", tag['Value'])
#                 print("the instance id is :", instance.id)
#                 # print("{0} {1}".format(tag['Value'], instance.id))
