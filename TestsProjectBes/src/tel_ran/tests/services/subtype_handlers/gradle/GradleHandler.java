package tel_ran.tests.services.subtype_handlers.gradle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import org.gradle.tooling.*;

public class GradleHandler {

	
	public static boolean buildAndTest(File gradleBuildFile) {
		GradleConnector connector = GradleConnector.newConnector();
		connector.forProjectDirectory(gradleBuildFile);
		ProjectConnection connection = connector.connect();

		boolean result = false;
		
		try {
			BuildLauncher launcher = connection.newBuild();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();
			launcher.forTasks("build");
			launcher.setStandardOutput(stream);
			launcher.setStandardError(err);

			try {
				launcher.run();
			} catch (BuildException e) {
				
			}

		if (err != null) {			
			String res2 = err.toString();
			if (res2.length() > 0) {
				result = false;
				System.out.println(err);
			} else {
				result = true;
			}
		}

	} finally {
		connection.close();
	}
		
	return result;
}

	
}