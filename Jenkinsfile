pipeline {
    agent any

    parameters {
        choice(
            name: 'ACTION',
            choices: ['DEPLOY', 'REMOVE'],
            description: 'Choose whether to deploy or remove the application'
        )
    }

    tools {
        maven 'maven'
    }

    environment {
        APP_NAME = "springboot-app"
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

        stage('Deploy Application') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh '''
                    docker compose down || true
                    docker compose up -d
                '''
            }
        }

        stage('Verify Deployment') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh '''
                    echo "Running Containers:"
                    docker ps

                    echo "Docker Compose Status:"
                    docker compose ps
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

                    echo "Application removed successfully."
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully.'
        }

        failure {
            echo 'Pipeline execution failed.'
        }

        always {
            echo 'Pipeline completed.'
        }
    }
}
