def execute() {
	stage('stageCheckoutProject') {
		executeCode();
		executePOMFile();
		print 'Checkout Project Success'
	}
	
	stage('stageBuildAutomation') {
		executeSonarScan();
		executeMavenBuild();
		print 'Build Automation Success'
    }
	
	stage('stageBuildManagement') {
		uploadArtifactory();
		deployToTomcat();
		print 'Build Management Success'
	}
}

def executeCode() {
	git url: props.JAVA_APP_REPO_GIT_URL,
    branch: props.BRANCH
}

def executePOMFile() {
	pom = readMavenPom file: props.POM_FILE
	artifactId=pom.artifactId
	version=pom.version
}

def executeSonarScan() {
	sh props.SONAR_SCAN+' '+props.SONAR_HOST+' '+props.SONAR_TEST+' '+props.SONAR_IGNORE
}

def executeMavenBuild() {
	sh props.MAVEN_BUILD
}

def uploadArtifactory() {
	commonUtility.uploadWarArtifactory();
}

def deployToTomcat() {
	/*sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION*/
	sh props.DOCKER_TOMCAT_STOP
	sh props.DOCKER_FILE_BUILD
	sh props.DOCKER_TOMCAT_RUN
}
return this