pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
            post {
                success {
                    echo 'Build Success'
                }
                failure {
                    echo 'Build Failed'
                }
            }
        }

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
            }
        }
    }

    post {
        success {
            echo 'Pipeline Success'
        }
        failure {
            echo 'Pipeline Failed'
        }
        always {
            echo 'Pipeline Execution Completed'
        }
    }
}
