package ronproeditor.ext;

import ronproeditor.REApplication;
import src.coco.controller.CCCompileErrorKindLoader;
import src.coco.controller.CCCompileErrorLoader;
import src.coco.model.CCCompileErrorManager;
import src.coco.view.CCMainFrame2;
import clib.common.filesystem.CDirectory;

public class RECocoViewerManager {
	REApplication application;

	private static String PPV_ROOT_DIR = ".ppv";// MyProjects/.ppv�t�H���_�ɓW�J����
	private static String KINDS_FILE = "ext/cocoviewer/ErrorKinds.csv"; // ext����ErrorKinds
	// private static String KINDS_FILE = "MyErrorKinds.csv";
	private static String DATA_FILE = "CompileErrorLog.csv";

	private int errorKindsCount;

	public RECocoViewerManager(REApplication application) {
		this.application = application;

	}

	public void openCocoViewer() {
		CCCompileErrorManager manager = new CCCompileErrorManager();
		loadData(manager);

		CDirectory ppvRoot = application.getSourceManager().getCRootDirectory()
				.findOrCreateDirectory(PPV_ROOT_DIR);
		CDirectory libDir = application.getLibraryManager().getDir();
		manager.setBase(ppvRoot);
		manager.setLibDir(libDir);
		new CCMainFrame2(manager, errorKindsCount).setVisible(true);
	}

	private void loadData(CCCompileErrorManager manager) {
		String ppvRootPath = application.getSourceManager().getCRootDirectory()
				.findOrCreateDirectory(PPV_ROOT_DIR).getAbsolutePath()
				.toString()
				+ "/";

		CCCompileErrorKindLoader kindLoader = new CCCompileErrorKindLoader(
				manager);
		kindLoader.load(KINDS_FILE);
		errorKindsCount = kindLoader.getLines();

		CCCompileErrorLoader errorLoader = new CCCompileErrorLoader(manager);
		errorLoader.load(ppvRootPath + DATA_FILE);
	}
}