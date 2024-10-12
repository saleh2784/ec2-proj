# Use the official Jenkins LTS image with JDK 17 as the base image
FROM jenkins/jenkins:lts-jdk17

# Set environment variables if needed
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"  # Example: Skip setup wizard

# Install any additional tools or dependencies (optional)
USER root  # Switch to root to install additional software
RUN apt-get update && apt-get install -y \
    git \
    vim \
    curl \
    && apt-get clean

# Switch back to the Jenkins user
USER jenkins

# Expose ports
EXPOSE 8080 50000

# Set the default command to run Jenkins
CMD ["bash", "-c", "java -Djenkins.install.runSetupWizard=false -jar /usr/share/jenkins/jenkins.war"]

