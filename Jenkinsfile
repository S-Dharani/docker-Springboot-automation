pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

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

        stage('Run Application') {
            steps {
                sh '''
                # Stop the existing application if running
                pkill -f student_details || true

                # Run the new application
                nohup java -jar target/*.jar > app.log 2>&1 &

                # Wait for application to start
                sleep 10

                # Verify the application is running
                pgrep -f student_details
                '''
            }
            post {
                success {
                    echo 'Application Started Successfully'
                }
                failure {
                    echo 'Application Failed to Start'
                }
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
