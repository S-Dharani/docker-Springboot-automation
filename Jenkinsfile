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
        stage('Build JAR') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy Application') {
            when {
                expression { params.ACTION == 'DEPLOY' }
            }
            steps {
                sh 'docker compose up --build -d'
            }
        }

<<<<<<< HEAD
        stage('Remove Application') {
            when {
                expression { params.ACTION == 'REMOVE' }
            }
            steps {
                sh 'docker compose down'
                sh 'docker image prune -af'
=======
        stage('Stop Old Application') {
            steps {
                sh '''
                    pkill -f student_details || true
                    sleep 5
                '''
            }
        }

        stage('Run Application') {
            steps {
                sh '''
                    cd "$WORKSPACE"

                    echo "Current Workspace:"
                    pwd
                    ls -l
                    ls -l target

                    export BUILD_ID=dontKillMe
                    export JENKINS_NODE_COOKIE=dontKillMe

                    nohup java -jar target/student_details-0.0.1-SNAPSHOT.jar > target/app.log 2>&1 < /dev/null &

                    sleep 20
                '''
            }
        }

        stage('Verify Application') {
            steps {
                sh '''
                    ps -ef | grep student_details | grep -v grep

                    echo "Application Log:"
                    tail -20 target/app.log
                '''
>>>>>>> 43412acab8177a1e22a5f9f8fb5b9a5e863d0de6
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