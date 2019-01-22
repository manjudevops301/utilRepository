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
		print 'Uploading artifactory is success'
		deployToTomcat();
		print 'Build Management Success'
	}
}

def executeCode() {
	git url: prop.JAVA_APP_REPO_GIT_URL,
    branch: prop.BRANCH
}

def executePOMFile() {
	pom = readMavenPom file: prop.POM_FILE
	artifactId=pom.artifactId
	version=pom.version
}

def executeSonarScan() {
	bat prop.SONAR_SCAN+' '+prop.SONAR_HOST+' '+prop.SONAR_TEST+' '+prop.SONAR_IGNORE
}

def executeMavenBuild() {
	bat prop.MAVEN_BUILD
}

def uploadArtifactory() {
	commonUtil.uploadWarArtifactory();
}

def deployToTomcat() {
	print 'Deploying war file to docker file'
	/*bat prop.TOMCAT_DEPLOY+' '+prop.TOMCAT_LOCATION*/
	bat prop.DOCKER_TOMCAT_STOP
	bat prop.DOCKER_FILE_BUILD
	bat prop.DOCKER_TOMCAT_RUN
}
return this