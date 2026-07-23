pipeline {
    agent any

    parameters {
        choice(
            name: 'ACTION',
            choices: ['DEPLOY', 'REMOVE'],
            description: 'Choose whether to deploy or remove containers'
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
                sh 'docker compose up -d'
            }
        }

        stage('Remove Application') {
            when {
                expression { params.ACTION == 'REMOVE' }
            }
            steps {
                sh '''
                    docker compose down
                    docker image prune -af
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
