/*
 * PRBIViLog.java
 * Created on 2014/12/18 by macchan
 * Copyright(c) 2014 Yoshiaki Matsuzawa, Shizuoka University
 */
package pres.core.model;


import clib.common.filesystem.CPath;

/**
 * PRBIViLog
 * 2014 12/18たなか追加
 * 正直PRFileLogと統合してもいいかも
 * 引数にtimestampを追加しただけ
 */
public class PRBIViLog extends PRLog {

	private CPath path;

	/**
	 * Constructor
	 */
	public PRBIViLog(Long timestamp, PRLogType type, PRLogSubType subType,
			CPath path, Object... args) {
		super(timestamp, type, subType);
		addArgument(path);
		addArguments(args);
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public CPath getPath() {
		return path;
	}
}
