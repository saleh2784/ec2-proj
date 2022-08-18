# import boto3
# import os
# import time

# ec2 = boto3.resource('ec2')

# for instance in ec2.instances.all():
#     kube_machine = False
#     for tag in instance.tags:
#         if tag['Value'] == "1" and tag['Key'] == "k8s.io/role/master" and instance.state["Code"] == 16:
#             # print("the instance id is :", instance.id)
#             # print("the status for the instance is : ", instance.state["Name"])
#             kube_machine = True

#     if kube_machine == True:
#         for tag in instance.tags:
#             if tag['Key'] == "Name":
#                 print("the name for the instance is : ", tag['Value'])
#                 print("the instance id is :", instance.id)
#                 # print("{0} {1}".format(tag['Value'], instance.id))

# def get_online_machines():
#     ec2 = boto3.resource('ec2')
#     for instance in ec2.instances.all():
#         for tag in instance.tags:
#             if instance.state['Code'] == 16:
#                 if tag['Key'] == 'Name' and tag['Value'] == 'devops': #Replace Name with k8s.io/role/master and Value with 1
#                     print("Name: {0}".format(tag['Value']))  # Do an operation
#                     # Can be commented
#                     print("Id: {0}\nPlatform: {1}\nType: {2}\nPublic IPv4: {3}\nAMI: {4}\nState: {5}\n".format(instance.id, instance.platform, instance.instance_type, instance.public_ip_address, instance.image.id, instance.state))

# interval = os.environ.get('INTERVAL')
# if interval == None:
#     get_online_machines()
# else:
#     while True:
#         get_online_machines()
#         print("Sleep for {0} seconds".format(interval))
#         time.sleep(int(interval))
