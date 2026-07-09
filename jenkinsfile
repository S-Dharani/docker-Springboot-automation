pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
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

        stage('Run Application') {
            steps {
                sh '''
                pkill -f spring_app_sak || true

                nohup java -jar target/*.jar > app.log 2>&1 &

                sleep 10

                pgrep -f spring_app_sak
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
    }
}
