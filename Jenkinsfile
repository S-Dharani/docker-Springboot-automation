pipeline {
    agent any

    parameters {
        choice(
            name: 'ACTION',
            choices: ['DEPLOY', 'REMOVE'],
            description: 'Select DEPLOY to deploy the application or REMOVE to remove it.'
        )
    }

    tools {
        maven 'maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh 'docker compose build'
            }
        }
        stage('Push Docker Image') {
           when {
                 expression { params.ACTION == 'DEPLOY' }
           }
           steps {
              sh '''
                docker tag application-springboot-app dharani1001/student-details:latest
            docker push dharani1001/student-details:latest
             '''
           }
        }
        stage('Deploy Application') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh 'docker compose up -d'
            }
        }

        stage('Verify Deployment') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh '''
                    docker compose ps
                    docker ps
                '''
            }
        }

        stage('Remove Application') {
            when {
                expression { params.ACTION == 'REMOVE' }
            }
            steps {
                sh '''
                    docker compose down --remove-orphans || true
                    docker rmi application-springboot-app || true
                    docker image prune -f || true
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline executed successfully."
        }

        failure {
            echo "Pipeline execution failed."
        }

        always {
            echo "Pipeline completed."
        }
    }
}
